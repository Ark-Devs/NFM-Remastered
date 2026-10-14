## Game highlight (killcam), after the original (GameSparker fase -3,
## Medium.around / transaround, xtGraphics.levelhigh): the 12 s around the
## wasting replayed at normal speed, the camera orbiting a car 20-28 m out at
## 2 degrees a frame while slowly rising and sinking. On the impact the replay
## freezes (3.2 s) at the instant the two cars meet: sparks hang, the wreck
## catches fire and flickers, white flashes; when you wasted someone the
## camera sweeps from your car to the wreck. Hold Enter to skip.
##
## The sim is deterministic, so a second sim re-runs the race from the
## recorded inputs to the start of the window; the real race keeps running
## underneath, so online play stays in lockstep.
class_name Killcam
extends Node

const TICK := 1.0 / 25.0
const FRAMES := 300
const HOLD := 80            # frames frozen on the impact (original: 67 / 20)
const SKIP_HOLD := 0.9      # seconds Enter must be held to skip
const S := 1.0 / 50.0       # NFM units to metres

var main
var ev: Dictionary
var done: Callable
var rsim: NfmSim
var view: RaceView
var log: Dictionary
var start := 0             # sim tick of frame 0
var when := 0              # frame index at which the impact tick has been simulated
var n4 := 0                # replay frame
var n5 := 0                # freeze / camera-sweep counter
var acc := 0.0
var freeze_alpha := 1.0    # where between the last two ticks the cars are closest
var flash_left := 0
var hero := 0              # the car the camera follows (you)
var debris: GPUParticles3D
# orbit state, per 25 Hz frame (prev / now), drawn interpolated each render frame
var adv := 0.0
var vert := false
var vxz := 0.0
var p_adv := 0.0
var p_vxz := 0.0
var aim := [0, 0, 0.0]     # orbit centre: car a, car b, blend (transaround)
var p_aim := [0, 0, 0.0]
var skip_t := 0.0
var ui: Control
var caption: Label
var flash: ColorRect
var black: ColorRect
var skip_bar: ColorRect


## log: {stage, grid, humans, landk, seed, dm, fixes, me, inputs}
## p_ev: {tick, strike, victim, killer, you_died}
func play(p_main, p_log: Dictionary, p_ev: Dictionary, p_done: Callable) -> void:
	main = p_main
	log = p_log
	ev = p_ev
	done = p_done
	var strike := int(ev.get("strike", ev.tick))
	start = maxi(0, strike - (229 if ev.you_died else 200))
	when = strike - start
	hero = ev.victim if ev.you_died else (ev.killer if ev.killer >= 0 else log.me)
	rsim = NfmSim.new()
	main.assets.load_cars(rsim)
	var st: Dictionary = main.assets.stages[log.stage]
	rsim.load_stage(st.data, st.id, log.grid.size(), log.seed)
	rsim.set_deathmatch(log.dm)
	rsim.set_fix_limit(log.fixes)
	rsim.start_race(PackedInt32Array(log.grid), log.humans, log.landk, log.seed)
	rsim.set_focus(log.me)
	# inputs[k] drives the step that produces tick k + 1
	for k in start:
		rsim.step(_inputs(k))
		if k % 100 == 0: rsim.take_events()
	rsim.take_events()
	# the original's random start: height direction, height, angle
	vert = randf() <= 0.45
	adv = randf() * 900.0
	vxz = randf() * 360.0 + (90.0 if ev.you_died else 0.0)
	p_adv = adv
	p_vxz = vxz
	aim = [hero, hero, 0.0]
	p_aim = aim.duplicate()
	view = RaceView.new()
	view.share_env = true          # same stage as the race underneath: use its sky
	view.free_camera = true
	add_child(view)
	view.setup(rsim, log.me, main.settings.quality)
	_build_debris()
	_build_ui()


func _inputs(k: int) -> PackedInt32Array:
	if k < log.inputs.size(): return log.inputs[k]
	var z := PackedInt32Array()
	z.resize(8)
	return z


func _build_ui() -> void:
	ui = Control.new()
	UI.full_rect(ui)
	ui.mouse_filter = Control.MOUSE_FILTER_IGNORE
	var title := UI.shadowed(UI.label("GAME HIGHLIGHT", UI.cond(), 22, UI.DIM))
	title.set_anchors_preset(Control.PRESET_CENTER_TOP)
	title.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	title.position = Vector2(-300, 24)
	title.size = Vector2(600, 30)
	ui.add_child(title)
	caption = UI.shadowed(UI.label("WASTED!" if ev.you_died else "YOU WASTED 'EM!", UI.display(), 56))
	caption.set_anchors_preset(Control.PRESET_CENTER_TOP)
	caption.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	caption.position = Vector2(-500, 56)
	caption.size = Vector2(1000, 70)
	ui.add_child(caption)
	# hold-to-skip: a label and a bar that fills while Enter is held
	var skip_label := UI.shadowed(UI.label("HOLD ENTER TO SKIP", UI.body_bold(), 13, UI.DIM))
	skip_label.set_anchors_preset(Control.PRESET_BOTTOM_RIGHT)
	skip_label.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	skip_label.position = Vector2(-260, -54)
	skip_label.size = Vector2(220, 20)
	ui.add_child(skip_label)
	var track := ColorRect.new()
	track.color = Color(1, 1, 1, 0.15)
	track.set_anchors_preset(Control.PRESET_BOTTOM_RIGHT)
	track.position = Vector2(-260, -30)
	track.size = Vector2(220, 4)
	ui.add_child(track)
	skip_bar = ColorRect.new()
	skip_bar.color = UI.ACCENT
	skip_bar.size = Vector2(0, 4)
	track.add_child(skip_bar)
	black = ColorRect.new()
	UI.full_rect(black)
	black.color = Color(0, 0, 0, 1)
	black.mouse_filter = Control.MOUSE_FILTER_IGNORE
	ui.add_child(black)
	flash = ColorRect.new()
	UI.full_rect(flash)
	flash.color = Color(1, 1, 1, 0)
	flash.mouse_filter = Control.MOUSE_FILTER_IGNORE
	ui.add_child(flash)
	main.ui_layer.add_child(ui)


## Chunks of bodywork thrown out of the impact; drift slowly through the freeze.
func _build_debris() -> void:
	debris = GPUParticles3D.new()
	debris.amount = 48
	debris.lifetime = 2.4
	debris.one_shot = true
	debris.explosiveness = 1.0
	debris.emitting = false
	debris.local_coords = false
	var pm := ParticleProcessMaterial.new()
	pm.emission_shape = ParticleProcessMaterial.EMISSION_SHAPE_SPHERE
	pm.emission_sphere_radius = 0.6
	pm.direction = Vector3.UP
	pm.spread = 75.0
	pm.initial_velocity_min = 3.0
	pm.initial_velocity_max = 9.0
	pm.gravity = Vector3(0, -9.8, 0)
	pm.angular_velocity_min = -360.0
	pm.angular_velocity_max = 360.0
	pm.scale_min = 0.5
	pm.scale_max = 1.6
	debris.process_material = pm
	var chunk := BoxMesh.new()
	chunk.size = Vector3(0.22, 0.05, 0.16)
	var m := StandardMaterial3D.new()
	m.albedo_color = Color(0.18, 0.18, 0.19)
	m.metallic = 0.7
	m.roughness = 0.4
	chunk.material = m
	debris.draw_pass_1 = chunk
	view.add_child(debris)


func _process(delta: float) -> void:
	if view == null:
		return
	var dt := minf(delta, 0.1)
	# hold Enter to skip
	if Input.is_physical_key_pressed(KEY_ENTER) or Input.is_physical_key_pressed(KEY_KP_ENTER):
		skip_t += dt
		if skip_t >= SKIP_HOLD:
			finish()
			return
	else:
		skip_t = maxf(0.0, skip_t - dt * 2.0)
	skip_bar.size.x = 220.0 * clampf(skip_t / SKIP_HOLD, 0.0, 1.0)
	acc += dt
	while acc >= TICK:
		acc -= TICK
		_frame()
		if view == null:
			return
	var a := acc / TICK
	var moving := _moving()
	view.frozen = not moving
	for p in [view.sparks, view.dust, view.smoke]:
		if p: p.speed_scale = 1.0 if moving else 0.0
	debris.speed_scale = 1.0 if moving else 0.12
	view.frame(dt if moving else 0.0, a if moving else freeze_alpha, false)
	_place_camera(a)
	for c in view.cars: c.flicker()
	_test_shot()


## the replay advances only outside the freeze
func _moving() -> bool:
	return n5 == 0 or n5 == HOLD


## One 25 Hz frame of the original's highlight loop.
func _frame() -> void:
	p_adv = adv
	p_vxz = vxz
	p_aim = aim.duplicate()
	if ev.you_died:
		aim = [hero, hero, 0.0]
		if n5 in [3, 31, 66]: flash_left = 1
	else:
		var wreck: int = ev.victim
		if n5 == 0: aim = [hero, hero, 0.0]
		elif n5 < 20: aim = [hero, wreck, n5 / 20.0]
		else: aim = [wreck, wreck, 0.0]
		if n5 == 9 or n5 == 11 or n5 == 58: flash_left = 1
	_orbit_step()
	# the hit registers at collision range, a few metres before the bodies
	# meet: keep playing while the two cars are still closing, then freeze at
	# their closest
	if n4 >= when and n5 != HOLD:
		if n5 > 0 or not _closing():
			if n5 == 0: _impact()
			n5 += 1
	black.color.a = 1.0 if n4 <= 2 else 0.0
	flash.color.a = 1.0 if flash_left > 0 else 0.0
	flash_left = maxi(0, flash_left - 1)
	if n4 < 50:
		caption.modulate = Color(0.42, 0.69, 1.0) if n4 % 2 == 0 else Color(0.06, 0.19, 0.38)
	else:
		caption.modulate = Color(0.85, 0.9, 1.0)
	if _moving():
		rsim.step(_inputs(start + n4))
		view.after_step()
		var e: PackedFloat32Array = rsim.take_events()
		for i in range(0, e.size(), 10):
			var type := int(e[i])
			var v := e.slice(i + 1, i + 10)
			view.on_event(type, v)
			if type == 1: main.audio.on_event(type, v, view) # crashes only: no "wasted" voice
		n4 += 1
		if n4 >= FRAMES:
			finish()


## The freeze begins: hold at the instant between the last two ticks where the
## two cars are closest, break the wreck open and set it alight.
func _impact() -> void:
	var k: int = view.stride
	var v: int = ev.victim
	var o: int = ev.killer if ev.killer >= 0 else v
	var pv := _godot(view.prev, v * k)
	var cv := _godot(view.curr, v * k)
	var po := _godot(view.prev, o * k)
	var co := _godot(view.curr, o * k)
	# closest approach of two points moving in straight lines over the tick
	var d0 := pv - po
	var dd := (cv - co) - d0
	freeze_alpha = 1.0
	if dd.length_squared() > 0.0001 and o != v:
		freeze_alpha = clampf(-d0.dot(dd) / dd.length_squared(), 0.0, 1.0)
	var at := pv.lerp(cv, freeze_alpha).lerp(po.lerp(co, freeze_alpha), 0.5)
	for i in 24:
		view.emit(view.sparks, at + Vector3.UP * 0.5, Vector3(randf_range(-6, 6), randf_range(1, 7), randf_range(-6, 6)))
	view.cars[v].ignite()


## still getting closer this tick (and not too long after the hit)?
func _closing() -> bool:
	if ev.killer < 0 or n4 > when + 8:
		return false
	var k: int = view.stride
	var gp := _godot(view.prev, ev.victim * k).distance_to(_godot(view.prev, ev.killer * k))
	var gc := _godot(view.curr, ev.victim * k).distance_to(_godot(view.curr, ev.killer * k))
	return gc < gp - 0.05


func _godot(s: PackedFloat32Array, o: int) -> Vector3:
	return Vector3(s[o] * S, -s[o + 1] * S, -s[o + 2] * S)


## Medium.around / transaround: orbit radius max(1000, 500 + adv) units,
## height adv above the centre (sinking and rising between -500 and 900),
## 2 degrees a frame.
func _orbit_step() -> void:
	adv += -2.0 if vert else 2.0
	if adv > 900.0: vert = true
	if adv < -500.0: vert = false
	vxz += 2.0


## Each render frame: the orbit about where the cars are drawn right now, so
## the car stays locked in shot (no jitter between ticks).
func _place_camera(a: float) -> void:
	var ad := lerpf(p_adv, adv, a)
	var ang := deg_to_rad(lerpf(p_vxz, vxz, a))
	var c := _aim_point(p_aim).lerp(_aim_point(aim), a)
	var dist := maxf(1000.0, 500.0 + ad) * S
	var y := c.y + ad * S
	var ground: float = view.scenery.height_at(c.x, c.z) if view.scenery else c.y - 0.5
	if y < ground + 0.8:
		y = ground + 0.8
		vert = false
	var p := Vector3(c.x - dist * cos(ang), y, c.z - dist * sin(ang))
	if view.scenery:
		p.y = maxf(p.y, view.scenery.height_at(p.x, p.z) + 1.0)
	var cam: Camera3D = view.camera
	cam.global_position = p
	cam.look_at(c + Vector3.UP * 0.5, Vector3.UP)


func _aim_point(spec: Array) -> Vector3:
	var pa: Vector3 = view.cars[spec[0]].global_position
	var pb: Vector3 = view.cars[spec[1]].global_position
	return pa.lerp(pb, float(spec[2]))


func _test_shot() -> void:
	# test: --killcamshot=<png> saves a frame from the freeze on the impact
	var shot: String = main._arg(OS.get_cmdline_user_args(), "--killcamshot=")
	if shot == "" or has_meta("shot"): return
	if n5 == (45 if ev.you_died else 40):
		set_meta("shot", true)
		await RenderingServer.frame_post_draw
		get_viewport().get_texture().get_image().save_png(shot)
		print("killcam shot ", shot)
		get_tree().quit()


func finish() -> void:
	if view == null:
		return
	view.queue_free()
	view = null
	rsim = null
	if ui: ui.queue_free()
	var d := done
	queue_free()
	d.call()
