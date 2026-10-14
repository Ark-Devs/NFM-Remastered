## Entry point: loads the game, runs menus and races. The simulation steps at
## the original 25 Hz; rendering interpolates between steps.
extends Node

const TICK := 0.04

var sim := Native.sim()
var assets := Assets.new()
var settings := Settings.new()
var view: RaceView
var hud: Hud
var ui_layer := CanvasLayer.new()
var racing := false
var bench_hold := false   # performance test: hold the race on one moment
var intro_hold := 0.0     # career: seconds the grid waits while the camera shows the boss
var _intro_fresh := false # the accelerate key has been let go since the intro began
## Online: the race is over for everyone (someone crossed the line or at most
## one car is left). Until then a player who was wasted keeps watching.
var race_decided := false
var _wasted_at := 0           # msec our car was wasted in an online race (0 = still racing)
var _spec_label: Label
var paused := false
var acc := 0.0
var hit_stop := 0.0
var race_opts := {}
var audio: GameAudio
var net: Net
var online: Online
var profile := Profile.new()
var front: Front
var shot_path := ""
var shot_frames := -1


func _ready() -> void:
	add_child(ui_layer)
	settings.load_save()
	apply_display()
	apply_quality()
	assets.load_all(sim)
	audio = GameAudio.new()
	add_child(audio)
	audio.setup(assets, settings)
	profile.load_save()
	online = Online.new()
	add_child(online)
	net = Net.new()
	add_child(net)
	net.setup(self)
	front = Front.new()
	ui_layer.add_child(front)
	front.setup(self)
	_setup_fps()
	var args := OS.get_cmdline_user_args()
	if _arg(args, "--damage=") != "":
		_test_damage = float(_arg(args, "--damage=")) # test: hold your car at this damage
	if _arg(args, "--car=") != "":
		settings.car = int(_arg(args, "--car=")) # test: open on this car (not saved)
	if _arg(args, "--stage=") != "":
		settings.stage = int(_arg(args, "--stage=")) # test: open on this stage (not saved)
	for i in args.size():
		if args[i] == "--shot" and i + 1 < args.size():
			shot_path = args[i + 1]
	if "--quickrace" in args:
		var stage := 0
		for a in args:
			if a.begins_with("--stage="):
				stage = int(a.substr(8))
		start_race({stage = stage, car = 14, opponents = 6})
	elif _arg(args, "--careerrace=") != "":
		# test: a career stage, --careerrace=TOUR,STAGE (both from 0)
		var at := _arg(args, "--careerrace=").split(",")
		var c := {chapter = int(at[0]), stage = int(at[1])}
		start_race({stage = front.career.chapters[c.chapter].stages[c.stage].index, car = 0, opponents = 6, career = c, grid = front.career.grid_for(c.chapter, c.stage, 0)})
	elif _arg(args, "--menu=stages=") != "":
		# test: the career stage select at TOUR,STAGE
		var at := _arg(args, "--menu=stages=").split(",")
		front.stage_select("career", {chapter = int(at[0]), stage = int(at[1])})
	elif _arg(args, "--carsheet=") != "":
		# test: picture of every car in the selector, saved to the given folder
		front.car_select("free")
		_car_sheet(_arg(args, "--carsheet="))
	elif "--menu=cars" in args:
		front.car_select("free")
	elif "--menu=options" in args:
		front.options()
	elif "--menu=career" in args:
		front.car_select("career")
	elif "--menu=stages" in args:
		front.stage_select("free")
	elif "--menu=dm" in args:
		front.deathmatch()
	elif "--menu=main" in args:
		front.main_menu()
	elif "--menu=host" in args:
		front.mp_room.password = _arg(args, "--roompw=") # test: lock the room
		if _arg(args, "--roomai=") != "": front.mp_room.ai = int(_arg(args, "--roomai="))
		if _arg(args, "--roommode=") != "": front.mp_room.mode = _arg(args, "--roommode=")
		front._host()
	elif _arg(args, "--hubtest=") != "":
		_hub_test(_arg(args, "--hubtest="))
	elif "--rtctest" in args:
		print("rtc: ready=", await online.start_async("Tester"))
		var o := EOS.Lobby.CreateLobbyOptions.new()
		o.bucket_id = "nfm-rtc-test"
		o.max_lobby_members = 4
		o.enable_rtc_room = true
		o.local_rtc_options = {flags = 0}
		o.local_user_id = online.user_id
		EOS.Lobby.LobbyInterface.create_lobby(o)
		var ret = await IEOS.lobby_interface_create_lobby_callback
		print("rtc: create result=", EOS.result_str(ret))
		get_tree().quit()
	elif _arg(args, "--previewall=") != "":
		# test: every stage's preview: build time and a picture
		var dir := _arg(args, "--previewall=")
		front.showroom.set_active(false)
		var only := Array(_arg(args, "--only=").split(",", false)).map(func(x): return int(x))
		for i in assets.stages.size():
			if only.size() and i not in only: continue
			var t0 := Time.get_ticks_msec()
			var st: Dictionary = assets.stages[i]
			sim.load_stage(st.data, st.id, 1, 1)
			sim.start_race(PackedInt32Array([settings.car]), 1, 0.3, 1)
			var pv := RaceView.new()
			add_child(pv)
			pv.setup(sim, 0, settings.quality)
			pv.set_process(false)
			var built := Time.get_ticks_msec() - t0
			for f in 20:
				pv.frame_orbit(0.016)
				await get_tree().process_frame
			await RenderingServer.frame_post_draw
			get_viewport().get_texture().get_image().save_png("%s/%02d.png" % [dir, i])
			print("preview %02d %-32s build %d ms" % [i, st.name, built])
			pv.queue_free()
			await get_tree().process_frame
		get_tree().quit()
	elif _arg(args, "--bench=") != "":
		if _arg(args, "--bq=") != "": settings.quality = _arg(args, "--bq="); apply_quality()
		var b := Bench.new()
		add_child(b)
		b.run(self, _arg(args, "--bench="))
	elif "--eostest" in args:
		_eos_test()
	elif "--menu=createroom" in args:
		front.room_settings(false)
	elif _arg(args, "--roomtest=") != "":
		# test: join the first listed room, say something, get ready
		front.hall()
		await get_tree().create_timer(14.0).timeout
		_tap(KEY_DOWN)
		await get_tree().create_timer(0.5).timeout
		_tap(KEY_ENTER)
		await get_tree().create_timer(6.0).timeout
		net.say(_arg(args, "--roomtest="))
		await get_tree().create_timer(2.0).timeout
		net.set_ready(true)
	elif "--podiumtest" in args:
		front._studio("menu")
		front.showroom.show_podium([{slot = sim.car_slot(20), name = "Shady"}, {slot = sim.car_slot(4), name = "Velo"}, {slot = sim.car_slot(11), name = "reed"}])
	elif _arg(args, "--lineuptest=") != "":
		var n := int(_arg(args, "--lineuptest="))
		front._studio("menu")
		var e := []
		for i in n: e.append({slot = sim.car_slot(i * 5 % sim.car_count()), name = "Player %d" % (i + 1), tag = "HOST" if i == 0 else ("READY" if i % 2 == 1 else "")})
		front.showroom.show_lineup(e)
	elif "--menu=hall" in args:
		front.hall()
		if "--profilecard" in args:
			await get_tree().create_timer(12.0).timeout
			front._profile_card({id = online.user_id, name = front._name(), car = settings.car, stats = {}})
	elif _arg(args, "--autohost=") != "":
		# test: host, write the room code to a file, start once a friend is in
		var path := _arg(args, "--autohost=")
		net.lobby_changed.connect(func():
			var f := FileAccess.open(path, FileAccess.WRITE)
			f.store_string(net.code)
			f.close()
			if net.players.size() >= 2 and not racing:
				net.start_race())
		front._host()
	elif _arg(args, "--autojoin=") != "":
		var code := FileAccess.get_file_as_string(_arg(args, "--autojoin=")).strip_edges()
		net.join_room(code, "Tester", 3)
		front.lobby()
	elif _arg(args, "--keys=") != "":
		front.title() # test: the keys below are pressed from the title screen
	else:
		front.splash()
	if _arg(args, "--keys=") != "":
		_schedule_keys(_arg(args, "--keys="))


## Test: join the hall as NAME, say hello, print what arrives for 30 s.
func _hub_test(name: String) -> void:
	online.hub_message.connect(func(m): print("hub: msg ", m))
	online.hub_changed.connect(func(): print("hub: members ", online.hub_members().map(func(x): return x.name)))
	print("hub: joined=", await online.hub_join_async(name, 3))
	for i in 10:
		await get_tree().create_timer(5.0).timeout
		online.hub_say({t = "chat", n = name, m = "hello %d from %s" % [i, name]})
		for m in online.hub.members: print("hub: raw ", name, " sees ", m.product_user_id.substr(0, 6), " ", m.attributes)
	await get_tree().create_timer(3.0).timeout
	await online.hub_leave_async()
	get_tree().quit()


## Test: sign in to Epic, open a room, find it in a search, close it.
func _eos_test() -> void:
	print("eos: signing in")
	print("eos: ready=", await online.start_async("Tester"), " user=", online.user_id)
	if online.is_ready():
		var id := await online.host_async(4, {v = 1, name = "Test room", mode = "race", stage = "Test", players = 1, max = 4})
		print("eos: lobby=", id)
		for i in 4:
			await get_tree().create_timer(3.0).timeout
			var f := await online.search_async()
			print("eos: after ", (i + 1) * 3, "s search found ", f.size(), " ", f)
		var found := await online.search_async()
		print("eos: search found ", found.size(), " ", found)
		await online.leave_async()
	get_tree().quit()


## Test helper: press keys like a player, 1.5 s apart (e.g. ENTER,DOWN,ENTER).
func _schedule_keys(list: String) -> void:
	var t := 1.5
	for k in list.split(","):
		var code: int = {"ENTER": KEY_ENTER, "DOWN": KEY_DOWN, "UP": KEY_UP, "LEFT": KEY_LEFT, "RIGHT": KEY_RIGHT, "ESC": KEY_ESCAPE, "TAB": KEY_TAB, "F12": KEY_F12}.get(k, OS.find_keycode_from_string(k) if k.length() == 1 else 0)
		if code:
			get_tree().create_timer(t).timeout.connect(_tap.bind(code))
		t += 1.5


## Presses and releases a key, like a quick tap.
func _tap(code: int) -> void:
	var down := InputEventKey.new()
	down.physical_keycode = code
	down.keycode = code
	down.pressed = true
	Input.parse_input_event(down)
	await get_tree().create_timer(0.05).timeout
	var up := InputEventKey.new()
	up.physical_keycode = code
	up.keycode = code
	up.pressed = false
	Input.parse_input_event(up)


## Same as _arg, usable without a main node.
static func _arg_s(prefix: String) -> String:
	for a in OS.get_cmdline_user_args():
		if a.begins_with(prefix): return a.substr(prefix.length())
	return ""


func _arg(args: PackedStringArray, prefix: String) -> String:
	for a in args:
		if a.begins_with(prefix):
			return a.substr(prefix.length())
	return ""


## opts: {stage, car, opponents | grid, humans, seed, net, career}
func start_race(opts: Dictionary) -> void:
	end_race()
	front.clear()
	race_opts = opts
	var st: Dictionary = assets.stages[opts.stage]
	var grid: PackedInt32Array
	if opts.has("grid"):
		grid = PackedInt32Array(opts.grid)
	else:
		grid = PackedInt32Array([opts.car])
		var pool: Array = range(16)
		pool.erase(opts.car)
		pool.shuffle()
		var n: int = 0 if st.solo else opts.get("opponents", 6)
		for i in n:
			grid.append(pool[i % pool.size()])
		opts.grid = Array(grid)
	var seed: int = opts.get("seed", randi())
	opts.seed = seed
	sim.load_stage(st.data, st.id, grid.size(), seed)
	var landk: float = opts.get("landk", 1.0 if settings.impact == "classic" else 0.3)
	var dm: bool = opts.get("deathmatch", false)
	sim.set_deathmatch(dm)
	sim.set_fix_limit(int(opts.get("fixes", -1)))
	sim.set_freeroam(opts.get("freeroam", false))
	sim.start_race(grid, opts.get("humans", 1), landk, seed)
	# everything the killcam needs to re-run this race exactly
	replay_log = {stage = opts.stage, grid = Array(grid), humans = opts.get("humans", 1), landk = landk, seed = seed, dm = dm, fixes = int(opts.get("fixes", -1)), me = opts.get("me", 0), inputs = []}
	wastings = []
	_pos_hist = {}
	_tags = {}
	race_decided = false
	_wasted_at = 0
	_burst_taken = PackedFloat32Array()
	health_log = []
	final_stats = PackedFloat32Array()
	var me: int = opts.get("me", 0)
	sim.set_focus(me)
	view = RaceView.new()
	view.deathmatch = dm
	add_child(view)
	front.showroom.set_active(false)
	if OS.get_cmdline_user_args().has("--debugscene"):
		print("showroom in tree: ", front.showroom.is_inside_tree(), " visible: ", front.showroom.visible, " envs: ", get_tree().root.find_children("*", "WorldEnvironment", true, false).size())
	front._drop_preview()
	view.setup(sim, me, settings.quality)
	if opts.get("net", false):
		_name_tags(me)
	view.cam_mode = clampi(settings.camera, 0, RaceView.CAMERAS.size() - 1)
	hud = Hud.new()
	ui_layer.add_child(hud)
	var info: PackedInt32Array = sim.race_info()
	hud.deathmatch = dm
	audio.deathmatch = dm
	hud.fixes_left = int(opts.get("fixes", -1)) if dm else -1
	arrow_target = -2 if dm else -1
	_setup_map(dm)
	map_on = settings.map
	hud.map.visible = map_on
	if dm:
		var rows := []
		for i in grid.size():
			if i != me: rows.append({index = i, name = sim.car_name(grid[i]), color = front.DM_COLORS[i % front.DM_COLORS.size()]})
		hud.setup_rivals(rows)
	hud.alive = grid.size()
	hud.message(st.name, ("DEATHMATCH · %d CARS" % grid.size()) if dm else ("%d LAPS · %d CARS" % [info[8], grid.size()]), 2.6)
	intro_hold = 0.0
	if opts.has("career") and not opts.get("net", false):
		_boss_reveal(opts.career, grid, st.name, info[8])
	if not dm: hud.coach(front.career.coach_for(opts.stage), 6.0)
	racing = true
	paused = false
	acc = 0.0
	audio.start_race(sim, me)
	audio.play_stage_music(st, sim.stage_soundtrack())


## Career: before the countdown the camera swings over to the stage's boss,
## the way the originals introduced M A S H E E N and friends.
func _boss_reveal(at: Dictionary, grid: PackedInt32Array, stage_name: String, laps: int) -> void:
	var e: Dictionary = front.career.chapters[at.chapter].stages[at.stage]
	var b := -1
	for i in range(1, grid.size()):
		if grid[i] in e.rivals:
			b = i
			break
	if b < 0:
		return
	var names: Array = e.rivals.map(func(x): return sim.car_name(x).to_upper())
	intro_hold = 3.6
	_intro_fresh = false
	view.showcase = b
	view.showcase_t = 0.0
	var sub := "%s   ·   %d LAPS" % [stage_name.to_upper(), laps]
	if e.menace.size():
		sub += "
WATCH OUT FOR " + "  &  ".join(e.menace.map(func(x): return sim.car_name(x).to_upper()))
	hud.cinema("THE BOSS" if names.size() == 1 else "THE BOSSES", "  &  ".join(names), sub)


func end_race() -> void:
	racing = false
	if killcam:
		# race ended mid-replay (e.g. the host ended it online): drop the replay
		if killcam.view: killcam.view.queue_free()
		if killcam.ui: killcam.ui.queue_free()
		killcam.queue_free()
		killcam = null
	if view:
		view.queue_free()
		view = null
	if hud:
		hud.queue_free()
		hud = null
	audio.stop_race()


func local_input() -> int:
	var b := 0
	if settings.held("left"): b |= 1
	if settings.held("right"): b |= 2
	if settings.held("up"): b |= 4
	if settings.held("down"): b |= 8
	if settings.held("handb"): b |= 16
	if settings.held("lookback") or settings.held("lookleft"): b |= 32
	for pad in Input.get_connected_joypads():
		var ax := Input.get_joy_axis(pad, JOY_AXIS_LEFT_X)
		if ax < -0.35 or Input.is_joy_button_pressed(pad, JOY_BUTTON_DPAD_LEFT): b |= 1
		if ax > 0.35 or Input.is_joy_button_pressed(pad, JOY_BUTTON_DPAD_RIGHT): b |= 2
		if Input.get_joy_axis(pad, JOY_AXIS_TRIGGER_RIGHT) > 0.2 or Input.is_joy_button_pressed(pad, JOY_BUTTON_A): b |= 4
		if Input.get_joy_axis(pad, JOY_AXIS_TRIGGER_LEFT) > 0.2: b |= 8
		if Input.is_joy_button_pressed(pad, JOY_BUTTON_B) or Input.is_joy_button_pressed(pad, JOY_BUTTON_X): b |= 16
		if Input.is_joy_button_pressed(pad, JOY_BUTTON_LEFT_SHOULDER): b |= 32
	return b


## Small frame rate counter in the top right corner, over everything. F12
## shows or hides it anywhere in the game.
var _fps_label: Label


func _setup_fps() -> void:
	var layer := CanvasLayer.new()
	layer.layer = 100
	add_child(layer)
	_fps_label = UI.label("", UI.cond(), 15, UI.DIM)
	_fps_label.set_anchors_preset(Control.PRESET_TOP_RIGHT)
	_fps_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	_fps_label.position = Vector2(-90, 6)
	_fps_label.size = Vector2(80, 20)
	_fps_label.visible = settings.fps
	layer.add_child(_fps_label)
	var t := Timer.new()
	t.wait_time = 0.25
	t.autostart = true
	t.timeout.connect(func(): _fps_label.text = "%d FPS" % Engine.get_frames_per_second())
	add_child(t)


func _input(e: InputEvent) -> void:
	if e is InputEventKey and e.pressed and not e.echo and e.keycode == KEY_F12:
		settings.fps = not settings.fps
		_fps_label.visible = settings.fps
		settings.save()
		get_viewport().set_input_as_handled()


func _unhandled_input(e: InputEvent) -> void:
	if not racing or not (e is InputEventKey) or not e.pressed or e.echo:
		return
	# spectating (our car wasted, online): steering left / right switches
	# between the cars still racing. Not during the career boss intro, which
	# also points the camera at another car.
	if view and view.watch >= 0 and _wasted_at > 0 and not race_decided:
		match settings.action_for(e.physical_keycode):
			"left": _spectate(-1); get_viewport().set_input_as_handled(); return
			"right": _spectate(1); get_viewport().set_input_as_handled(); return
	match settings.action_for(e.physical_keycode):
		"camera":
			hud.camera(view.cycle_camera())
			settings.camera = view.cam_mode
			settings.save()
		"pause":
			if front.has_overlay():
				return
			if net.active:
				front.net_menu()
			else:
				set_paused(true)
		"mute":
			audio.toggle_mute()
		"arrow":
			_cycle_arrow()
		"map":
			map_on = not map_on
			hud.map.visible = map_on
			settings.map = map_on
			settings.save()


# ------------------------------------------------------------ arrow + map
## What the arrow points at: -1 next checkpoint, -2 nearest car, else a grid slot.
var arrow_target := -1
var _test_damage := -1.0
var map_on := false


func _arrow_options() -> Array:
	var o := [] if hud.deathmatch else [-1]
	o.append(-2)
	for i in view.cars.size():
		if i != view.focus and view.curr[i * view.stride + 10] < 0.5: o.append(i)
	return o


func _arrow_name(t: int) -> String:
	if t == -1: return "NEXT CHECKPOINT"
	if t == -2: return "NEAREST CAR"
	return sim.car_name(sim.grid()[t]).to_upper()


func _cycle_arrow() -> void:
	var o := _arrow_options()
	arrow_target = o[(o.find(arrow_target) + 1) % o.size()]
	hud.toast("Arrow: " + _arrow_name(arrow_target).capitalize())


func _car_color(i: int) -> Color:
	if i == view.focus: return UI.ACCENT
	return front.DM_COLORS[i % front.DM_COLORS.size()] if hud.deathmatch else Color(0.9, 0.92, 0.95)


func _update_arrow_and_map() -> void:
	var me: int = view.focus
	var my_pos: Vector3 = view.cars[me].global_position
	var o := _arrow_options()
	if not arrow_target in o:
		arrow_target = -2 if o.has(-2) and o.size() > 1 else -1
	var target = null
	var label := ""
	var col := Color(1.0, 0.85, 0.3)
	var t := arrow_target
	if t == -2:
		var best := INF
		for i in o:
			if i >= 0 and view.cars[i].global_position.distance_to(my_pos) < best:
				best = view.cars[i].global_position.distance_to(my_pos)
				t = i
	if t == -1:
		target = view.next_checkpoint()
		label = "NEXT CHECKPOINT"
	elif t >= 0:
		target = view.cars[t].global_position
		label = sim.car_name(sim.grid()[t]).to_upper()
		col = _car_color(t)
	if target == null or view.curr[me * view.stride + 10] > 0.5:
		hud.pointer.hide_target()
	else:
		var d: Vector3 = target - my_pos
		# relative to where the car's nose points on the ground (not the camera,
		# which lags and looks back; heading only, so a flipped car stays right)
		var nose: Vector3 = -view.cars[me].global_transform.basis.z
		var f2 := Vector2(nose.x, nose.z).normalized()
		var d2 := Vector2(d.x, d.z)
		# the arrow sits at the top of the screen and points the way the target
		# lies on screen: straight ahead = tip down at it, behind = tip up,
		# left / right as they are (PI - angle flips only the vertical)
		hud.pointer.show_target(PI - atan2(d2.dot(Vector2(-f2.y, f2.x)), d2.dot(f2)), "%s   %d m" % [label, int(Vector2(d.x, d.z).length())], col)
	if map_on:
		var cars := []
		for i in view.cars.size():
			if i == me or view.curr[i * view.stride + 10] > 0.5: continue
			var p: Vector3 = view.cars[i].global_position
			cars.append({p = Vector2(p.x, p.z), color = _car_color(i)})
		var fwd: Vector3 = -view.cars[me].global_transform.basis.z
		var nc = view.next_checkpoint()
		hud.map.show_at(Vector2(my_pos.x, my_pos.z), atan2(fwd.x, -fwd.z), cars, Vector2(nc.x, nc.z) if nc != null and not hud.deathmatch else Vector2.INF)


func _setup_map(dm: bool) -> void:
	var path := PackedVector2Array()
	var cp: PackedInt32Array = sim.checkpoints()
	const S := 1.0 / 50.0
	for k in cp[0]:
		path.append(Vector2(cp[1 + k * 4] * S, -cp[2 + k * 4] * S))
	hud.map.setup(path)


func set_paused(on: bool) -> void:
	paused = on
	audio.pause(on)
	if on:
		front.pause_menu()
	else:
		front.clear()


## Room voice: open mic, push to talk (the talk key), or off. Typing in a
## chat box never opens a push-to-talk mic.
func _update_mic() -> void:
	if "--voicelog" in OS.get_cmdline_user_args() and Engine.get_process_frames() % 300 == 0:
		print("voice: ready=", online.voice_ready(), " mic=", online.mic_on(), " room=", online.lobby.rtc_room_name if online.lobby else "-")
	if not online.voice_ready():
		return
	var on := false
	match settings.voice:
		"open": on = true
		"ptt":
			var f := get_viewport().gui_get_focus_owner()
			on = settings.held("talk") and not (f is LineEdit)
	online.set_mic(on)


var _worst := 0.0


## Menus run at 60 fps (no point burning the GPU on a menu); races run free.
const MENU_FPS := 60
var _uncapped := "--bench=" in " ".join(OS.get_cmdline_user_args())


func _process(delta: float) -> void:
	var cap := 0 if racing or _uncapped else MENU_FPS
	if Engine.max_fps != cap: Engine.max_fps = cap
	if "--framelog" in OS.get_cmdline_user_args():
		_worst = maxf(_worst, delta)
		if Engine.get_process_frames() % 60 == 0:
			print("frame: worst %.0f ms" % (_worst * 1000.0))
			_worst = 0.0
	_update_mic()
	if shot_path != "" and not racing:
		_maybe_shot()
	if not racing:
		return
	var dt := minf(delta, 0.1)
	if intro_hold > 0.0 and not paused:
		intro_hold -= dt
		# skipping takes a fresh press: keys still held from the last race don't count
		if local_input() & 4 == 0: _intro_fresh = true
		if intro_hold <= 0.0 or (_intro_fresh and local_input() & 4 != 0 and intro_hold < 3.0):
			intro_hold = 0.0
			view.showcase = -1
			view.camera.fov = 58.0
			hud.cinema("", "", "")
			hud.message(assets.stages[race_opts.stage].name, "%d LAPS · %d CARS" % [sim.race_info()[8], sim.grid().size()], 1.6)
			view.watch = -1
			view.cam_init = false
	elif not paused and not bench_hold:
		var scale := 1.0
		if hit_stop > 0.0:
			hit_stop -= dt
			scale = 0.25
		if net.active:
			# lockstep: step only when the host's frame for the tick is here
			net.pump(dt)
			var steps := 0
			while steps < 4 and net.can_step():
				_step(net.inputs_for_tick())
				steps += 1
			acc = 0.0 if steps > 0 else minf(acc + dt, TICK)
		else:
			acc += dt * scale
			var steps := 0
			while acc >= TICK and steps < 5:
				var inputs := PackedInt32Array()
				inputs.resize(8)
				inputs[0] = local_input()
				_step(inputs)
				acc -= TICK
				steps += 1
			if steps == 5:
				acc = 0.0
	var alpha := 1.0 if paused else acc / TICK
	view.frame(0.0 if paused else dt, alpha, -1 if settings.held("lookleft") else (1 if local_input() & 32 != 0 else 0))
	var info: PackedInt32Array = sim.race_info()
	_update_arrow_and_map()
	if int(sim.race_info()[0]) == 200 and "--stunttest" in OS.get_cmdline_user_args():
		# test: a big named stunt and a coach remark on screen
		_race_event(11, PackedFloat32Array([95, 740, -650, 30, 0, 1, 1, 0, 80, 0]))
		_coach_at = 0
		_remark("test", "Did you see that?! Unbelievable!")
	if int(sim.race_info()[0]) == 300 and _arg(OS.get_cmdline_user_args(), "--outcomeshot=") != "":
		# test: the post-race result card, saved to a picture
		front.outcome({kind = 3, who = 0, position = 1}, true, func(): pass, func(): pass)
		await get_tree().create_timer(1.0).timeout
		await RenderingServer.frame_post_draw
		get_viewport().get_texture().get_image().save_png(_arg(OS.get_cmdline_user_args(), "--outcomeshot="))
		get_tree().quit()
	if killcam == null and "--killcamtest" in OS.get_cmdline_user_args():
		# test: replay the first real car-on-car wasting, 3 s after it happens
		for w in wastings:
			if w.killer >= 0 and int(sim.race_info()[0]) == w.tick + 75:
				_play_killcam({tick = w.tick, strike = w.strike, victim = w.victim, killer = w.killer, you_died = false}, func(): pass)
				break
	if _test_damage >= 0.0 and int(sim.race_info()[1]) == 0:
		sim.debug_set_damage(view.focus, _test_damage) # test: --damage=0.93
	if hud.deathmatch:
		if hud.fixes_left >= 0:
			var cs: PackedFloat32Array = sim.combat_stats()
			hud.fixes_left = maxi(0, int(race_opts.get("fixes", -1)) - int(cs[view.focus * 8 + 3]))
		var alive := 0
		for i in info[9]:
			if view.curr[i * view.stride + 10] < 0.5: alive += 1
		hud.alive = alive
		hud.update_rivals(view.curr, view.stride)
	var shown: int = view.watch if view.watch >= 0 and not race_decided else view.focus
	hud.update_hud(dt, view.curr, shown * view.stride, info, info[9])
	hud.show_mic(online.mic_on())
	if (settings.held("scores") or "--showboard" in OS.get_cmdline_user_args()) and not front.has_overlay():
		hud.show_board(_board_rows())
	else:
		hud.hide_board()
	if net.active and Engine.get_process_frames() % 6 == 0:
		var names := []
		var talking: Array = online.talking()
		for id in net.players:
			var uid: String = net.peer_user(id)
			if uid != "" and uid in talking: names.append(str(net.players[id].name))
		hud.speaking(names)
	if _wasted_at > 0 and not race_decided and Time.get_ticks_msec() - _wasted_at > 2500:
		var w: int = view.watch
		if w < 0 or view.curr[w * view.stride + 10] > 0.5:
			_spectate(1) # start watching, or move on when the watched car is wasted
	audio.frame(view.curr, view.focus * view.stride, local_input(), dt, info[1] == 0 and info[2] == 0)
	if shot_path != "":
		_maybe_shot()


## Health of every car, sampled twice a second for the deathmatch graph.
var health_log: Array = []
## combat_stats() captured the moment the race was decided.
var final_stats := PackedFloat32Array()


## Recorded for the killcam: the inputs of every tick, and every wasting.
var replay_log := {}
var wastings: Array = []
var killcam: Killcam = null


## Per car: damage taken so far, the tick it last went up, and the tick its
## current burst of damage began. A crash between two cars lasts several
## ticks; the killcam freezes on the first, while they are still touching.
var _burst_taken := PackedFloat32Array()
var _burst_last := PackedInt32Array()
var _burst_start := PackedInt32Array()
var _big_hits := {}          # victim * 8 + attacker -> [damage, tick]


func _track_bursts() -> void:
	var cs: PackedFloat32Array = sim.combat_stats()
	var n := cs.size() / 8
	var now := int(sim.race_info()[0])
	var by: PackedInt32Array = sim.last_hit_by()
	var at: PackedInt32Array = sim.last_hit_ticks()
	if _burst_taken.size() != n:
		_burst_taken.resize(n); _burst_last.resize(n); _burst_start.resize(n)
		_burst_last.fill(-1000); _burst_start.fill(-1000)
		_big_hits = {}
	for i in n:
		var taken := cs[i * 8 + 1]
		var jump := taken - _burst_taken[i]
		if jump > 0.01:
			if now - _burst_last[i] > 8: _burst_start[i] = now
			_burst_last[i] = now
			# per attacker: the biggest single-tick hit from it in the last 6 s
			# (that tick is the impact, the two cars touching)
			if at[i] == now and by[i] >= 0:
				var key: int = i * 8 + by[i]
				var old: Array = _big_hits.get(key, [0.0, -1000])
				if now - int(old[1]) > 150 or jump >= float(old[0]):
					_big_hits[key] = [jump, now]
		_burst_taken[i] = taken


func _step(inputs: PackedInt32Array) -> void:
	# test: --wasteslot=SLOT@TICK[,SLOT@TICK] wrecks those cars then (same on every PC)
	var ws := _arg(OS.get_cmdline_user_args(), "--wasteslot=")
	for w in ws.split(",", false): # several: 0@250,2@500
		if int(sim.race_info()[0]) == int(w.get_slice("@", 1)):
			sim.debug_set_damage(int(w.get_slice("@", 0)), 1.5)
	sim.step(inputs)
	_track_throws()
	var sa := _arg(OS.get_cmdline_user_args(), "--shotat=")
	if sa != "":
		var times := Array(sa.split(",")).map(func(x): return int(float(x) * 25.0))
		var t := int(sim.race_info()[0])
		if t in times:
			_shot_at(t, t == times.max())
	replay_log.inputs.append(inputs)
	_track_bursts()
	if "--killcamtest" in OS.get_cmdline_user_args():
		replay_log.get_or_add("hashes", {})[int(sim.race_info()[0])] = sim.state_hash()
		replay_log.get_or_add("states", {})[int(sim.race_info()[0])] = sim.car_states()
	view.after_step()
	if final_stats.is_empty() and int(sim.race_info()[0]) % 12 == 0:
		var s: PackedFloat32Array = sim.car_states()
		var st: int = sim.car_state_size()
		var row := PackedFloat32Array()
		for i in s.size() / st:
			row.append(0.0 if s[i * st + 10] > 0.5 else 100.0 * (1.0 - clampf(s[i * st + 9], 0.0, 1.0)))
		health_log.append(row)
	var ev: PackedFloat32Array = sim.take_events()
	for i in range(0, ev.size(), 10):
		var type := int(ev[i])
		var v := ev.slice(i + 1, i + 10)
		view.on_event(type, v)
		audio.on_event(type, v, view)
		_race_event(type, v)


func _race_event(type: int, v: PackedFloat32Array) -> void:
	var info: PackedInt32Array = sim.race_info()
	match type:
		12: # count
			hud.message(str(int(v[0])))
		13: # go
			hud.message("GO!", "", 0.9)
			_coach_said = {}
			_coach_at = 0
		8: # checkpoint
			var nsp := maxi(1, info[7])
			if int(v[0]) % nsp != 0 and not hud.deathmatch:
				hud.toast("Checkpoint %d / %d" % [int(v[0]) % nsp, nsp])
		9: # lap
			if not hud.deathmatch:
				hud.message("FINAL LAP" if int(v[0]) == info[8] else "LAP %d" % int(v[0]), "", 1.4)
				if int(v[0]) == info[8] and info[8] > 1:
					audio.final_lap()
					_remark("last_lap", "Last lap. Don't blow it now!" if _place() == 1 else "Last lap! Time to get mad!", true)
		10: # wasted
			_note_wasting(int(v[0]))
			var who := int(v[0])
			if who == view.focus:
				if net.active:
					hud.message("WASTED", "your car is destroyed. The results show when the race is over", 2.5)
					_wasted_at = Time.get_ticks_msec()
				else:
					hud.message("WASTED", "your car is destroyed", 2.5)
			var cs: PackedFloat32Array = sim.combat_stats()
			var killer := int(cs[who * 8 + 5])
			var mine := who == view.focus or killer == view.focus
			if killer == view.focus and who != view.focus:
				_my_wastes.append(Time.get_ticks_msec())
				_my_wastes = _my_wastes.filter(func(t): return Time.get_ticks_msec() - t < 6000)
				var n := _my_wastes.size()
				var head: String = ["", "YOU WASTED 'EM!", "DOUBLE WASTE!", "TRIPLE WASTE!"][n] if n < 4 else "MADNESS!!!"
				hud.stunt(head, "%s is history" % _short_name(who), UI.ACCENT, 2.2)
				_remark("waste", ["That's gotta hurt!", "Ouch! Who's next?", "Radical! Keep 'em coming!", "Now that's madness!", "They never saw it coming."][randi() % 5])
			if killer >= 0:
				hud.feed("%s  wasted  %s" % [_short_name(killer), _short_name(who)], mine)
			else:
				hud.feed("%s  wrecked" % _short_name(who), mine)
		11: # stunt: named the way the originals did
			var d := Stunts.describe(v)
			var maxed := v.size() > 8 and v[8] >= 98.0
			var special: bool = v[0] >= 150.0
			if v[0] < 12.0 and not maxed:
				hud.toast("%s  +%d%% power" % [d[0], int(v[0])]) # a little hop: no fanfare
			else: hud.stunt(d[0], ("POWER TO THE MAX!" if maxed else "%s!   +%d%% power" % [d[1], int(v[0])]), UI.GOLD if special else UI.INK, 2.0 if special else 1.5)
			if special: _remark("special", "Did you see that?! Unbelievable!")
			elif maxed: _remark("max", "Power to the max! Now go hit something.")
		18: # bad landing
			hud.stunt("BAD LANDING!", "", UI.BAD, 1.3)
			_remark("bad", "Land on your wheels, rookie!")
		5: # car fixed
			hud.toast("Car fixed!")
			_remark("fixed", "Good as new. Now go get them!")
		1: # crash: a big hit briefly slows time so it lands with weight
			if absf(v[0]) >= 170.0 and settings.impact != "classic" and not net.active:
				hit_stop = minf(0.14, 0.06 + absf(v[0]) / 4000.0)
		14: # finish (this car's race is over)
			# online, results wait until the race is over for everyone
			if not net.active:
				_show_result({kind = int(v[0]), who = int(v[1]), position = int(v[2])})
			elif int(v[0]) == 1:
				# our car is home; the results wait for everyone else
				_own_finish.call_deferred(int(v[2]))
		17: # the race is decided for everyone (online: same tick on every PC)
			if net.active and not race_decided:
				race_decided = true
				if _spec_label and is_instance_valid(_spec_label): _spec_label.visible = false
				var ri: PackedInt32Array = sim.race_info()
				_show_result({kind = ri[3], who = ri[4], position = ri[5]})


func _own_finish(pos: int) -> void:
	if not race_decided:
		hud.message("YOU WON" if pos == 1 else UI.ordinal(pos).to_upper() + " PLACE", "The results show when the race is over", 3.0)


## Coach Insano: one remark of each kind per race, and never two in a row
## too close together, so he stays a character and not a chatterbox.
var _coach_said := {}
var _coach_at := 0
var _my_wastes: Array = []


func _remark(kind: String, text: String, force := false) -> void:
	if hud == null or (_coach_said.has(kind) and not force): return
	if not force and Time.get_ticks_msec() - _coach_at < 9000: return
	_coach_said[kind] = true
	_coach_at = Time.get_ticks_msec()
	hud.coach(text, 3.5)


## Our place in the race right now (1 = leading).
func _place() -> int:
	var st: PackedFloat32Array = sim.car_states()
	var n: int = sim.car_state_size()
	return int(st[view.focus * n + 13])


## Online, after our car is wasted: follow the next (or previous) car still
## racing, with its driver's name on screen.
func _spectate(step: int) -> void:
	var alive := []
	for i in sim.race_info()[9]:
		if i != view.focus and view.curr[i * view.stride + 10] < 0.5:
			alive.append(i)
	if alive.is_empty():
		view.watch = -1
		if _spec_label: _spec_label.visible = false
		return
	var at := alive.find(view.watch)
	view.watch = alive[(at + step + alive.size()) % alive.size()] if at >= 0 else alive[0]
	for g in _tags:
		if is_instance_valid(_tags[g]): _tags[g].visible = g != view.watch # its name is already on screen
	view.cam_init = false
	if _spec_label == null or not is_instance_valid(_spec_label):
		_spec_label = UI.label("", UI.cond(), 24, UI.INK)
		_spec_label.set_anchors_preset(Control.PRESET_CENTER_TOP)
		_spec_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
		_spec_label.position = Vector2(-400, 150)
		_spec_label.size = Vector2(800, 30)
		UI.shadowed(_spec_label)
		hud.add_child(_spec_label)
	_spec_label.visible = true
	_spec_label.text = "SPECTATING   %s" % _driver_name(view.watch)


## Everyone in the race for the Tab scoreboard, leader first.
func _board_rows() -> Array:
	var st: PackedFloat32Array = view.curr
	var n: int = sim.race_info()[9]
	var cs: PackedFloat32Array = sim.combat_stats()
	var grid: PackedInt32Array = sim.grid()
	var order: Array = range(n)
	order.sort_custom(func(a, b): return st[a * view.stride + 13] < st[b * view.stride + 13])
	var rows := []
	for i in order:
		var o: int = i * view.stride
		var wasted: bool = st[o + 10] > 0.5
		var ping := "AI"
		for id in net.players:
			if int(net.players[id].get("grid", -1)) == i:
				ping = "HOST" if id == 1 else ("%d ms" % net.pings[id] if net.pings.has(id) else "-")
		if not net.active: ping = "-" if i == view.focus else "AI"
		rows.append({place = UI.ordinal(int(st[o + 13])), name = _short_name(i), car = sim.car_name(grid[i]),
			status = "WASTED" if wasted else "Racing", wastes = int(cs[i * 8 + 4]), ping = ping, me = i == view.focus})
	return rows


var _tags := {}   # grid slot -> its name tag


## Online: a player's name floating over each other player's car.
func _name_tags(me: int) -> void:
	for id in net.players:
		var g := int(net.players[id].get("grid", -1))
		if g < 0 or g == me or g >= view.cars.size():
			continue
		var car: CarView = view.cars[g]
		var l := Label3D.new()
		l.text = str(net.players[id].name)
		l.font = UI.cond()
		l.font_size = 30
		l.pixel_size = 0.0016
		l.fixed_size = true # same size on screen near or far
		l.billboard = BaseMaterial3D.BILLBOARD_ENABLED
		l.outline_size = 8
		l.outline_modulate = Color(0, 0, 0, 0.8)
		l.modulate = Color(0.953, 0.949, 0.937, 0.9)
		var b: AABB = car.body_bounds()
		l.position = Vector3(0, b.end.y + 0.6, 0)
		car.body.add_child(l)
		_tags[g] = l


## A player's name online, or the car's name, for short lines like the kill feed.
func _short_name(slot: int) -> String:
	for id in net.players:
		if int(net.players[id].get("grid", -1)) == slot:
			return str(net.players[id].name)
	if race_opts.get("net", false) and net.slot_names.has(slot):
		return str(net.slot_names[slot])
	return sim.car_name(sim.grid()[slot])


## Who's driving a grid slot: a player's name online, otherwise the car's name.
func _driver_name(slot: int) -> String:
	for id in net.players:
		if int(net.players[id].get("grid", -1)) == slot:
			return "%s  (%s)" % [net.players[id].name, sim.car_name(sim.grid()[slot])]
	return sim.car_name(sim.grid()[slot])


## The result card, then the killcam (if there's a moment worth showing),
## then the results screen.
func _show_result(r: Dictionary) -> void:
	final_stats = sim.combat_stats()
	# result card first, then (Enter) the killcam, then the results,
	# with a short breath between each step
	var moment := _killcam_moment()
	get_tree().create_timer(1.0 if r.kind == 2 else 0.25).timeout.connect(func():
		if not racing: return
		var to_results := func():
			await get_tree().create_timer(0.5).timeout
			if racing: front.results(r)
		var to_killcam := func():
			await get_tree().create_timer(0.5).timeout
			if racing: _play_killcam(moment, func():
				await get_tree().create_timer(0.6).timeout
				if racing: front.results(r))
		front.outcome(r, not moment.is_empty(), to_killcam, to_results))


func _car_sheet(dir: String) -> void:
	for i in sim.car_count():
		for f in 45:
			await get_tree().process_frame
		await RenderingServer.frame_post_draw
		get_viewport().get_texture().get_image().save_png("%s/car_%02d.png" % [dir, i])
		_tap(KEY_RIGHT)
	get_tree().quit()


## A wasting, scored for style: how fast the attacker was going, plus a bonus
## for a car in the air.
func _note_wasting(victim: int) -> void:
	var cs: PackedFloat32Array = sim.combat_stats()
	var killer := int(cs[victim * 8 + 5])
	var st: PackedFloat32Array = sim.car_states()
	var n: int = sim.car_state_size()
	var style := 0.0
	if killer >= 0:
		style = absf(st[killer * n + 7]) + (150.0 if st[killer * n + 14] < 0.5 else 0.0)
	style += 100.0 if st[victim * n + 14] < 0.5 else 0.0
	# the strike that did it: the victim's last hit taken (it is only marked
	# wasted a moment later, by when the attacker has often bounced away)
	var now := int(sim.race_info()[0])
	var strike := now
	if killer >= 0 and victim < _burst_start.size():
		# the tick of the biggest hit it took from its killer: the impact itself
		var big: Array = _big_hits.get(victim * 8 + killer, [0.0, -1000])
		var bt := int(big[1])
		if bt > now - 150 and bt <= now: strike = bt
	# how hard: where the victim was when the blow landed, and how far it has
	# been thrown since (kept updated for a couple of seconds after it's wasted)
	var from := _pos_at(strike, victim)
	wastings.append({tick = now, strike = strike, victim = victim, killer = killer, style = style, from = from, throw = _pos_at(now, victim).distance_to(from)})


## Which moment the killcam shows: your most ferocious waste (the victim thrown
## the furthest), else you getting wasted; nothing if neither happened.
func _killcam_moment() -> Dictionary:
	var me: int = view.focus
	var best := {}
	for w in wastings:
		if w.killer == me and (best.is_empty() or w.throw > best.throw):
			best = w
	if not best.is_empty():
		return {tick = best.tick, strike = best.strike, victim = best.victim, killer = best.killer, you_died = false}
	for w in wastings:
		if w.victim == me:
			return {tick = w.tick, strike = w.strike, victim = w.victim, killer = w.killer, you_died = true}
	return {}


## Recent car positions (NFM units), for measuring how far a car was thrown.
const POS_KEEP := 300
var _pos_hist := {}   # tick -> PackedFloat32Array of x, y, z per car


func _pos_at(tick: int, slot: int) -> Vector3:
	var p: PackedFloat32Array = _pos_hist.get(tick, PackedFloat32Array())
	if p.size() < slot * 3 + 3:
		var st: PackedFloat32Array = sim.car_states()
		var n: int = sim.car_state_size()
		return Vector3(st[slot * n], st[slot * n + 1], st[slot * n + 2])
	return Vector3(p[slot * 3], p[slot * 3 + 1], p[slot * 3 + 2])


func _track_throws() -> void:
	var t := int(sim.race_info()[0])
	var st: PackedFloat32Array = sim.car_states()
	var n: int = sim.car_state_size()
	var p := PackedFloat32Array()
	for i in st.size() / n:
		p.append_array([st[i * n], st[i * n + 1], st[i * n + 2]])
	_pos_hist[t] = p
	_pos_hist.erase(t - POS_KEEP)
	for w in wastings:
		if t - int(w.tick) <= 60: # still flying: keep the furthest it got
			w.throw = maxf(w.throw, Vector3(p[w.victim * 3], p[w.victim * 3 + 1], p[w.victim * 3 + 2]).distance_to(w.from))


func _play_killcam(moment: Dictionary, then: Callable) -> void:
	view.visible = false
	hud.visible = false
	audio.pause(true)
	killcam = Killcam.new()
	add_child(killcam)
	killcam.play(self, replay_log, moment, func():
		killcam = null
		if view:
			view.visible = true
			view.camera.make_current()
		if hud: hud.visible = true
		audio.pause(false)
		then.call())


## Test: --shotat=12,25 --shotto=PATH saves PATH_12.png etc at those race times.
func _shot_at(tick: int, last: bool) -> void:
	await RenderingServer.frame_post_draw
	var p := _arg(OS.get_cmdline_user_args(), "--shotto=")
	get_viewport().get_texture().get_image().save_png("%s_%d.png" % [p, tick / 25])
	if last:
		await get_tree().create_timer(8.0).timeout # results / outcome screens
		get_viewport().get_texture().get_image().save_png("%s_end.png" % p)
		get_tree().quit()


func _maybe_shot() -> void:
	# --shotsec=N: N seconds after launch instead of a frame count
	var secs := _arg(OS.get_cmdline_user_args(), "--shotsec=")
	if secs != "":
		if Time.get_ticks_msec() / 1000.0 >= float(secs) and shot_frames != 0:
			shot_frames = 1
		else:
			return
	if shot_frames < 0:
		shot_frames = int(_arg(OS.get_cmdline_user_args(), "--shotframes=")) if _arg(OS.get_cmdline_user_args(), "--shotframes=") != "" else (150 if racing else 90)
	shot_frames -= 1
	if shot_frames == 0:
		await RenderingServer.frame_post_draw
		get_viewport().get_texture().get_image().save_png(shot_path)
		print("saved ", shot_path)
		get_tree().quit()


## The 3D resolution in use: the player's choice, or Auto from GRAPHICS.
func render_scale() -> float:
	if settings.render_scale > 0.0: return settings.render_scale
	return {"low": 0.7, "medium": 0.85}.get(settings.quality, 1.0)


func apply_quality() -> void:
	match settings.quality:
		"low": get_viewport().msaa_3d = Viewport.MSAA_DISABLED
		"medium": get_viewport().msaa_3d = Viewport.MSAA_2X
		"high": get_viewport().msaa_3d = Viewport.MSAA_4X
		"ultra": get_viewport().msaa_3d = Viewport.MSAA_8X
	# RENDER SCALE: below 100% upscales with FSR (sharpened, so it stays crisp)
	var sc := render_scale()
	get_viewport().scaling_3d_scale = sc
	get_viewport().scaling_3d_mode = Viewport.SCALING_3D_MODE_FSR if sc < 0.999 else Viewport.SCALING_3D_MODE_BILINEAR
	get_viewport().fsr_sharpness = 0.2
	# one anti-aliasing method: MSAA alone gives the same picture as MSAA + SMAA
	# (checked frame by frame) for less work. --noopt keeps the old pair for comparison.
	get_viewport().screen_space_aa = Viewport.SCREEN_SPACE_AA_SMAA if settings.quality != "low" and "--noopt" in OS.get_cmdline_user_args() else Viewport.SCREEN_SPACE_AA_DISABLED


func apply_display() -> void:
	if "--windowed" in OS.get_cmdline_user_args():
		# automated test runs: never take keyboard focus from whoever is using the PC
		DisplayServer.window_set_flag(DisplayServer.WINDOW_FLAG_NO_FOCUS, true)
		DisplayServer.window_set_mode(DisplayServer.WINDOW_MODE_WINDOWED)
		DisplayServer.window_set_size(Vector2i(1600, 900))
		return
	if settings.fullscreen:
		DisplayServer.window_set_mode(DisplayServer.WINDOW_MODE_FULLSCREEN)
	else:
		DisplayServer.window_set_mode(DisplayServer.WINDOW_MODE_WINDOWED)


## Grid slots of this race's rivals that the player wrecked themselves.
func wrecked_rivals() -> Array:
	var out := []
	var s: PackedFloat32Array = sim.car_states()
	var st := sim.car_state_size()
	var grid: PackedInt32Array = sim.grid()
	for i in grid.size():
		if s[i * st + 23] > 0.5:
			out.append(grid[i])
	return out
