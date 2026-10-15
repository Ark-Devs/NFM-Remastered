## Front end: title, main menu, car select, stage select, options,
## multiplayer rooms, pause and results. Keyboard (arrows / Enter / Esc),
## gamepad and mouse all work.
class_name Front
extends Control

var main
var sim: NfmSim
var showroom: Showroom
var preview: RaceView
var screen: Control
var keys: Callable           # (keycode) -> bool handled
var overlay := false
var mode := "free"           # free | career | mp
var _preview_timer: SceneTreeTimer
var career: Career


func setup(p_main) -> void:
	main = p_main
	sim = main.sim
	UI.full_rect(self)
	mouse_filter = Control.MOUSE_FILTER_IGNORE
	showroom = Showroom.new()
	showroom.setup(sim)
	main.add_child(showroom)
	career = Career.new()
	career.build(main.assets, sim)
	main.net.lobby_changed.connect(func(): if _lobby_refresh.is_valid(): _lobby_refresh.call())
	main.net.status.connect(func(t):
		_last_status = t
		if _lobby_status: _lobby_status.text = t)
	main.net.race_start.connect(func(opts): main.start_race(opts))
	main.net.ended.connect(func(reason): main.end_race(); multiplayer_menu(reason))
	main.net.back_to_lobby.connect(func():
		# a decided race: let each player leave their results when they're ready
		if main.racing and main.race_decided:
			return
		if not main.racing:
			return # already out of the race (on the podium or in the room)
		main.end_race()
		lobby())

var _lobby_refresh: Callable
var _last_status := ""
var _lobby_status: Label


func has_overlay() -> bool:
	return overlay


func clear() -> void:
	if screen:
		screen.queue_free()
		screen = null
	keys = Callable()
	overlay = false
	_lobby_refresh = Callable()
	_lobby_status = null


func _mount(c: Control, k: Callable) -> void:
	clear()
	screen = c
	keys = k
	add_child(c)
	c.modulate.a = 0.0
	create_tween().tween_property(c, "modulate:a", 1.0, 0.25)


## After a race the hands are still on the driving keys: for a moment the
## results menu ignores every key, and a key that was already held down when
## it opened only counts once it has been let go and pressed again.
var _guard_until := 0
var _stale := {}


func _guard(ms := 1000) -> void:
	_guard_until = Time.get_ticks_msec() + ms
	_stale = {}
	var codes := [KEY_ENTER, KEY_KP_ENTER, KEY_SPACE, KEY_ESCAPE, KEY_UP, KEY_DOWN, KEY_LEFT, KEY_RIGHT]
	for a in main.settings.keys: codes.append_array(main.settings.keys[a])
	for k in codes:
		if Input.is_physical_key_pressed(k): _stale[k] = true


func _unhandled_input(e: InputEvent) -> void:
	if e is InputEventKey:
		if not e.pressed:
			_stale.erase(e.physical_keycode)
		elif Time.get_ticks_msec() < _guard_until or (e.echo and _stale.has(e.physical_keycode)):
			get_viewport().set_input_as_handled()
			return
		else:
			_stale.erase(e.physical_keycode) # a fresh press: the key was let go
	# rebinding: the next key pressed goes to the action being changed
	if _capture.is_valid() and e is InputEventKey and e.pressed and not e.echo:
		var cb := _capture
		_capture = Callable()
		cb.call(e.physical_keycode)
		get_viewport().set_input_as_handled()
		return
	if not keys.is_valid():
		return
	var code := -1
	if e is InputEventKey and e.pressed:
		code = e.physical_keycode
	elif e is InputEventJoypadButton and e.pressed:
		code = {JOY_BUTTON_DPAD_UP: KEY_UP, JOY_BUTTON_DPAD_DOWN: KEY_DOWN, JOY_BUTTON_DPAD_LEFT: KEY_LEFT, JOY_BUTTON_DPAD_RIGHT: KEY_RIGHT, JOY_BUTTON_A: KEY_ENTER, JOY_BUTTON_B: KEY_ESCAPE, JOY_BUTTON_START: KEY_ESCAPE}.get(e.button_index, -1)
	if code == -1:
		return
	if code == KEY_W: code = KEY_UP
	if code == KEY_S: code = KEY_DOWN
	if code == KEY_A: code = KEY_LEFT
	if code == KEY_D: code = KEY_RIGHT
	if code == KEY_SPACE or code == KEY_KP_ENTER: code = KEY_ENTER
	if code == KEY_BACKSPACE: code = KEY_ESCAPE
	if keys.call(code):
		get_viewport().set_input_as_handled()


func _sfx() -> void:
	main.audio.play("ui_move", 0.6)


# ------------------------------------------------------------- 3D backdrops
func _studio(m: String) -> void:
	_drop_preview()
	showroom.set_active(true)
	if not showroom.lineup.is_empty():
		showroom._clear_lineup()
		if showroom.slot < 0:
			showroom.show_car(main.settings.car)
	showroom.mode = m
	showroom.camera.make_current()


func _drop_preview() -> void:
	if preview:
		preview.queue_free()
		preview = null


# ----------------------------------------------------------------- widgets
func _base() -> Control:
	var c := Control.new()
	UI.full_rect(c)
	c.mouse_filter = Control.MOUSE_FILTER_IGNORE
	return c


func _shade(c: Control, left_heavy: bool) -> void:
	var tr := TextureRect.new()
	UI.full_rect(tr)
	var g := Gradient.new()
	var gt := GradientTexture2D.new()
	if left_heavy:
		g.set_color(0, Color(0, 0, 0, 0.82)); g.set_color(1, Color(0, 0, 0, 0))
		g.set_offset(1, 0.62)
		gt.fill_from = Vector2(0, 0); gt.fill_to = Vector2(1, 0)
	else:
		g.set_color(0, Color(0, 0, 0, 0)); g.set_color(1, Color(0, 0, 0, 0.78))
		g.set_offset(0, 0.5)
		gt.fill_from = Vector2(0, 0); gt.fill_to = Vector2(0, 1)
	gt.gradient = g
	tr.texture = gt
	tr.stretch_mode = TextureRect.STRETCH_SCALE
	tr.mouse_filter = Control.MOUSE_FILTER_IGNORE
	c.add_child(tr)


func _logo(c: Control, small := false) -> void:
	var v := VBoxContainer.new()
	v.position = Vector2(96, 80)
	v.add_theme_constant_override("separation", 6)
	v.add_child(UI.label("NEED FOR MADNESS", UI.display(), 48 if small else 84))
	if not small:
		var r := UI.label("R E M A S T E R E D", UI.cond(), 20, UI.ACCENT)
		v.add_child(r)
	c.add_child(v)


func _crumbs(c: Control, a: String, b: String) -> void:
	var h := HBoxContainer.new()
	h.position = Vector2(56, 34)
	h.add_child(UI.label(a, UI.cond(), 16, UI.INK))
	h.add_child(UI.label("   /   ", UI.cond(), 16, UI.FAINT))
	h.add_child(UI.label(b, UI.cond(), 16, UI.DIM))
	c.add_child(h)


## A vertical menu of big italic items with an accent bar on the selected one.
func _menu(parent: Control, items: Array, pos: Vector2, size := 46, sel := 0) -> Dictionary:
	var v := VBoxContainer.new()
	v.position = pos
	v.add_theme_constant_override("separation", 4)
	parent.add_child(v)
	var rows := []
	for i in items.size():
		var row := HBoxContainer.new()
		row.add_theme_constant_override("separation", 18)
		row.mouse_filter = Control.MOUSE_FILTER_STOP
		var bar := ColorRect.new()
		bar.color = UI.ACCENT
		bar.custom_minimum_size = Vector2(3, size * 0.8)
		row.add_child(bar)
		var col := VBoxContainer.new()
		col.add_theme_constant_override("separation", 0)
		var t := UI.label(items[i][0], UI.display(), size, UI.FAINT)
		col.add_child(t)
		var sub: Label = null
		if items[i].size() > 2 and items[i][2] != "":
			sub = UI.label(items[i][2], UI.body(), 16, UI.DIM)
			col.add_child(sub)
		row.add_child(col)
		v.add_child(row)
		rows.append({row = row, bar = bar, t = t, sub = sub, fn = items[i][1]})
	var st := {sel = sel, rows = rows}
	var draw := func():
		for i in rows.size():
			var on: bool = i == st.sel
			rows[i].bar.visible = on
			rows[i].t.add_theme_color_override("font_color", UI.INK if on else UI.FAINT)
			if rows[i].sub:
				rows[i].sub.visible = on
	st.draw = draw
	for i in rows.size():
		rows[i].row.mouse_entered.connect(func(): st.sel = i; draw.call())
		rows[i].row.gui_input.connect(func(ev): if ev is InputEventMouseButton and ev.pressed and ev.button_index == MOUSE_BUTTON_LEFT: rows[i].fn.call())
	draw.call()
	return st


func _menu_keys(st: Dictionary, code: int) -> bool:
	if st.rows.is_empty(): return true # a card with nothing to pick
	match code:
		KEY_UP: st.sel = (st.sel + st.rows.size() - 1) % st.rows.size(); st.draw.call(); _sfx(); return true
		KEY_DOWN: st.sel = (st.sel + 1) % st.rows.size(); st.draw.call(); _sfx(); return true
		KEY_ENTER: st.rows[st.sel].fn.call(); return true
	return false


## Option rows: label ‹ value ›, left/right to change.
func _rows(parent: Control, rows: Array, pos: Vector2, width := 520) -> Dictionary:
	var v := VBoxContainer.new()
	v.position = pos
	v.add_theme_constant_override("separation", 4)
	parent.add_child(v)
	var made := []
	for r in rows:
		var p := PanelContainer.new()
		p.custom_minimum_size = Vector2(width, 48)
		var h := HBoxContainer.new()
		h.add_theme_constant_override("separation", 10)
		var l := UI.label(r[0], UI.body_bold(), 13, UI.DIM)
		l.custom_minimum_size = Vector2(170, 0)
		l.vertical_alignment = VERTICAL_ALIGNMENT_CENTER
		h.add_child(l)
		var lt := UI.label("‹", UI.body(), 26, UI.FAINT)
		h.add_child(lt)
		var val := UI.label("", UI.cond(), 20)
		val.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
		val.size_flags_horizontal = Control.SIZE_EXPAND_FILL
		val.clip_text = true
		h.add_child(val)
		var rt := UI.label("›", UI.body(), 26, UI.FAINT)
		h.add_child(rt)
		p.add_child(h)
		p.mouse_filter = Control.MOUSE_FILTER_STOP
		v.add_child(p)
		made.append({p = p, val = val, get = r[1], change = r[2]})
	var st := {sel = 0, rows = made}
	var draw := func():
		for i in made.size():
			var on: bool = i == st.sel
			made[i].p.add_theme_stylebox_override("panel", UI.panel_style(Color(0.08, 0.085, 0.095, 0.85) if on else UI.GLASS, 3 if on else 0))
			made[i].val.text = str(made[i].get.call())
	st.draw = draw
	for i in made.size():
		made[i].p.gui_input.connect(func(ev):
			if ev is InputEventMouseButton and ev.pressed:
				st.sel = i
				var left: bool = ev.position.x < made[i].p.size.x * 0.6
				if made[i].change.is_valid(): made[i].change.call(-1 if left else 1)
				draw.call())
	draw.call()
	return st


func _rows_keys(st: Dictionary, code: int) -> bool:
	match code:
		KEY_UP: st.sel = (st.sel + st.rows.size() - 1) % st.rows.size(); st.draw.call(); return true
		KEY_DOWN: st.sel = (st.sel + 1) % st.rows.size(); st.draw.call(); return true
		KEY_LEFT, KEY_RIGHT:
			var r = st.rows[st.sel]
			if r.change.is_valid():
				r.change.call(-1 if code == KEY_LEFT else 1)
				_sfx()
			st.draw.call()
			return true
	return false


# ------------------------------------------------------------------- title
## The game's opening: the logo builds up on black, then the black melts away
## to the studio while the logo glides into its place on the title screen.
## Keys do nothing until PRESS ENTER appears.
const TITLE_Y := -250.0  # where the logo rests on the title screen, above the car


func splash() -> void:
	title(true)


func title(intro := false) -> void:
	_studio("menu")
	showroom.show_car(main.settings.car)
	var c := _base()
	_shade(c, true)
	var cover := ColorRect.new()
	UI.full_rect(cover)
	cover.color = Color(0.02, 0.02, 0.025)
	cover.modulate.a = 1.0 if intro else 0.0
	c.add_child(cover)
	var box := Control.new()
	box.set_anchors_preset(Control.PRESET_CENTER)
	c.add_child(box)
	# name, stripe and REMASTERED move and scale together as one logo
	var logo := Control.new()
	logo.position.y = -40.0 if intro else TITLE_Y
	logo.pivot_offset = Vector2(0, -40)
	box.add_child(logo)
	var l := UI.label("NEED FOR MADNESS", UI.display(), 110)
	l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	l.position = Vector2(-700, -140)
	l.size = Vector2(1400, 140)
	logo.add_child(l)
	var bar := ColorRect.new()
	bar.color = UI.ACCENT
	bar.position = Vector2(-260, 6)
	bar.size = Vector2(520, 8)
	bar.pivot_offset = Vector2(260, 4)
	logo.add_child(bar)
	var r := UI.label("R E M A S T E R E D", UI.cond(), 24, UI.ACCENT)
	r.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	r.position = Vector2(-400, 24)
	r.size = Vector2(800, 34)
	logo.add_child(r)
	var p := UI.label("PRESS ENTER", UI.cond(), 20)
	p.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	p.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
	p.position = Vector2(-400, -150)
	p.size = Vector2(800, 30)
	c.add_child(p)
	var st := {ready = not intro, music = false}
	var music_in := func(secs: float):
		if st.music:
			return
		st.music = true
		main.audio.play_menu_music()
		var mp: AudioStreamPlayer = main.audio.music_player
		var full := linear_to_db(0.5)
		mp.volume_db = -40.0
		create_tween().tween_property(mp, "volume_db", full, secs).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_OUT)
	var pulse := func():
		st.ready = true
		var pt := p.create_tween()
		pt.tween_property(p, "modulate:a", 1.0, 0.6)
		pt.tween_callback(func():
			var tw := p.create_tween().set_loops() # dies with the label
			tw.tween_property(p, "modulate:a", 0.3, 1.1)
			tw.tween_property(p, "modulate:a", 1.0, 1.1))
	var intro_tw: Tween
	if intro:
		l.modulate.a = 0.0
		bar.scale.x = 0.0
		r.modulate.a = 0.0
		p.modulate.a = 0.0
		logo.scale = Vector2(1.45, 1.45)
		intro_tw = c.create_tween()
		intro_tw.tween_interval(0.3)
		intro_tw.tween_property(l, "modulate:a", 1.0, 0.5)
		intro_tw.parallel().tween_property(logo, "scale", Vector2(1.27, 1.27), 0.6).set_trans(Tween.TRANS_QUART).set_ease(Tween.EASE_OUT)
		intro_tw.tween_property(bar, "scale:x", 1.0, 0.35).set_trans(Tween.TRANS_CUBIC).set_ease(Tween.EASE_OUT)
		intro_tw.tween_property(r, "modulate:a", 1.0, 0.4)
		intro_tw.tween_interval(0.9)
		intro_tw.tween_callback(func(): music_in.call(1.8))
		intro_tw.tween_property(cover, "modulate:a", 0.0, 1.6).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
		intro_tw.parallel().tween_property(logo, "scale", Vector2.ONE, 1.6).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
		intro_tw.parallel().tween_property(logo, "position:y", TITLE_Y, 1.6).set_trans(Tween.TRANS_SINE).set_ease(Tween.EASE_IN_OUT)
		intro_tw.tween_callback(pulse)
	else:
		music_in.call(0.6)
		pulse.call()
	_mount(c, func(code):
		if not st.ready:
			return true # the intro plays in full; keys work once PRESS ENTER shows
		if code == KEY_ENTER or code == KEY_ESCAPE:
			main_menu()
			return true
		return false)


func main_menu(sel := 0) -> void:
	mode = "free"
	main.audio.play_menu_music()
	_studio("menu")
	if showroom.slot != main.settings.car or showroom.locked:
		showroom.show_car(main.settings.car)
	var c := _base()
	_shade(c, true)
	_logo(c)
	var cs := career.summary()
	var st := _menu(c, [
		["CAREER", func(): car_select("career"), cs],
		["FREE PLAY", func(): car_select("free"), ""],
		["DEATHMATCH", func(): deathmatch(), ""],
		["MULTIPLAYER", func(): multiplayer_menu(), ""],
		["OPTIONS", func(): options(), ""],
		["CONTROLS", func(): controls(func(): main_menu(5)), ""],
		["CREDITS", func(): credits(), ""],
		["QUIT", func(): get_tree().quit(), ""],
	], Vector2(96, 330), 46, sel)
	_mount(c, func(code): return _menu_keys(st, code))


# --------------------------------------------------------------- car select
func car_select(p_mode: String, back := Callable()) -> void:
	# lambdas below copy plain locals; keep selection state in a shared dictionary
	var sel := {}
	mode = p_mode
	_studio("select")
	var count: int = sim.car_count()
	var list := []
	for i in count:
		list.append(sim.car_slot(i))
	var current: int = career.s.car if mode == "career" else (main.net.my_car if mode == "mp" else main.settings.car)
	sel.idx = maxi(0, list.find(current))
	var is_locked := func(s: int) -> bool: return mode == "career" and not career.owns(s)
	# cars in career order: the starters, then weakest to strongest; in the
	# career your own cars come first, the locked ones after
	var order := career.unlock_order()
	var rank := func(s: int) -> int:
		var k := order.find(s)
		return (k if k >= 0 else 999) + (1000 if mode == "career" and not career.owns(s) else 0)
	list.sort_custom(func(a, b): return rank.call(a) < rank.call(b))
	sel.idx = maxi(0, list.find(current))
	if is_locked.call(list[sel.idx]):
		sel.idx = 0
	var c := _base()
	_shade(c, false)
	_crumbs(c, {"career": "CAREER", "free": "FREE PLAY", "mp": "MULTIPLAYER"}[mode], "SELECT CAR")
	var count_l := UI.label("", UI.cond(), 26)
	count_l.set_anchors_preset(Control.PRESET_TOP_RIGHT)
	count_l.position = Vector2(-160, 26)
	c.add_child(count_l)
	var info := VBoxContainer.new()
	info.set_anchors_preset(Control.PRESET_BOTTOM_LEFT)
	info.position = Vector2(56, -260)
	var tag := UI.label("", UI.body_bold(), 13, UI.ACCENT)
	info.add_child(tag)
	var name_l := UI.shadowed(UI.label("", UI.display(), 84))
	info.add_child(name_l)
	var lock_l := UI.label("", UI.body(), 17, UI.DIM)
	lock_l.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
	lock_l.custom_minimum_size = Vector2(620, 0)
	info.add_child(lock_l)
	c.add_child(info)
	var show_stats := _stat_bars(c)
	var draw := func(dir: int):
		var s: int = list[sel.idx]
		var lk: bool = is_locked.call(s)
		showroom.show_car(s, dir, lk)
		count_l.text = "%02d / %02d" % [sel.idx + 1, list.size()]
		tag.text = "COMMUNITY CAR" if sim.car_custom(s) else "ORIGINAL CAR"
		name_l.text = "? ? ?" if lk else sim.car_name(s).to_upper()
		lock_l.text = career.lock_text(s, main.assets) if lk else ""
		show_stats.call(s, lk)
	var step := func(d: int):
		sel.idx = (sel.idx + d + list.size()) % list.size()
		draw.call(d)
		_sfx()
	var choose := func():
		var s: int = list[sel.idx]
		if is_locked.call(s):
			var tw := create_tween()
			tw.tween_property(info, "position:x", 70.0, 0.06)
			tw.tween_property(info, "position:x", 42.0, 0.08)
			tw.tween_property(info, "position:x", 56.0, 0.06)
			return
		main.audio.play("ui_ok", 0.7)
		if mode == "career":
			career.s.car = s; career.save()
			stage_select("career")
		elif mode == "mp":
			main.net.my_car = s
			if back.is_valid(): back.call(s)
		else:
			main.settings.car = s; main.settings.save()
			stage_select("free")
	draw.call(0)
	_mount(c, func(code):
		match code:
			KEY_LEFT: step.call(-1); return true
			KEY_RIGHT: step.call(1); return true
			KEY_ENTER: choose.call(); return true
			KEY_ESCAPE:
				if mode == "mp" and back.is_valid(): back.call(-1)
				else: main_menu(0 if mode == "career" else 1)
				return true
		return false)


## The car's stat bars (bottom right); returns func(slot, locked) to show a car.
func _stat_bars(c: Control) -> Callable:
	var stats := VBoxContainer.new()
	stats.set_anchors_preset(Control.PRESET_BOTTOM_RIGHT)
	stats.position = Vector2(-460, -320)
	stats.add_theme_constant_override("separation", 12)
	c.add_child(stats)
	var bars := []
	for n in ["TOP SPEED", "ACCELERATION", "HANDLING", "STUNTS", "STRENGTH", "ENDURANCE", "POWER HOLD"]:
		var h := HBoxContainer.new()
		h.add_theme_constant_override("separation", 12)
		var l := UI.label(n, UI.body_bold(), 13, UI.DIM)
		l.custom_minimum_size = Vector2(120, 0)
		h.add_child(l)
		var b := UI.bar(260, 4, UI.INK)
		h.add_child(b[0])
		var num := UI.label("", UI.cond(), 18)
		h.add_child(num)
		stats.add_child(h)
		bars.append([b[1], num])
	return func(s: int, lk := false):
		var vals := car_bar_values(sim, s)
		for k in vals.size():
			var fill: ColorRect = bars[k][0]
			fill.create_tween().tween_property(fill, "size:x", 0.0 if lk else 260.0 * vals[k], 0.35)
			bars[k][1].text = "—" if lk else "%.1f" % (vals[k] * 10.0)


## The seven selector bars, 0..1.
static func car_bar_values(p_sim: NfmSim, s: int) -> Array:
	var st: PackedFloat32Array = p_sim.car_stats(s)
	# strength and endurance are measured, not computed: in NFM damage depends
	# on the victim's model points near the hit, dents soak repeat hits, and in
	# a moving clash only the car with more power x speed x weight deals damage.
	# Every car fights every car in real arenas twice over: with a player-like
	# ram bot (native/ext/test/ramfight.cpp: chase and ram, power kept full, at
	# 2/3 or draining) and with the game's own deathmatch AI (aifights.cpp).
	# fit_bars.mjs fits damage per hit = exp(STR[attacker] - END[victim]) on
	# one step size, held to the fight record, so the higher STR + END won 96%
	# of pairings. Other cars fall back to an estimate.
	var lab: Dictionary = _crash_stats().get(p_sim.car_name(s), {})
	var ram: float = st[3] * st[0] * (0.9 + 0.05 * st[8])
	var tough: float = st[4] / maxf(st[7], 0.05)
	var strength: float = lab.get("strength", clampf((ram - 200.0) / 580.0, 0.04, 1.0))
	var endurance: float = lab.get("endurance", clampf(log(tough / 5000.0) / log(36.0), 0.04, 1.0) * 0.6)
	# hand-set display values (assets/bar_overrides.json, 0-10): change only
	# what the bar shows, never how the car drives
	var ov: Dictionary = _bar_overrides().get(p_sim.car_name(s), {})
	if ov.has("strength"): strength = float(ov.strength) / 10.0
	if ov.has("endurance"): endurance = float(ov.endurance) / 10.0
	# acceleration, handling and stunts use the original game's formulas;
	# power hold: how slowly the power bar drains between stunts
	return [clampf((st[0] - 200.0) / 150.0, 0.04, 1.0), clampf(st[9] / 7700.0, 0.04, 1.0), clampf(st[11], 0.04, 1.0),
		clampf((st[10] + 28.0) / 139.0, 0.04, 1.0), clampf(strength, 0.04, 1.0),
		clampf(endurance, 0.04, 1.0), clampf(log(st[12] / 2500000.0) / log(6.68), 0.04, 1.0)]


static var _lab_cache = null


static var _override_cache = null


static func _bar_overrides() -> Dictionary:
	if _override_cache == null:
		_override_cache = {}
		var f := FileAccess.open("res://assets/bar_overrides.json", FileAccess.READ)
		if f:
			var d = JSON.parse_string(f.get_as_text())
			if d is Dictionary: _override_cache = d
	return _override_cache


static func _crash_stats() -> Dictionary:
	if _lab_cache == null:
		_lab_cache = {}
		var f := FileAccess.open("res://assets/crash_stats.json", FileAccess.READ)
		if f:
			var d = JSON.parse_string(f.get_as_text())
			if d is Dictionary: _lab_cache = d
	return _lab_cache


# ------------------------------------------------------------- stage select
func stage_select(p_mode: String, at := {}, on_pick := Callable()) -> void:
	# lambdas below copy plain locals; keep selection state in a shared dictionary
	var sel := {}
	mode = p_mode
	showroom.set_active(false)
	var stages: Array = main.assets.stages
	var cm := mode == "career"
	sel.ch = at.get("chapter", career.s.get("chapter", 0)) if cm else 0
	sel.k = at.get("stage", career.frontier(sel.ch)) if cm else clampi(main.settings.stage if mode == "free" else main.net.room_setup.stage, 0, stages.size() - 1)
	if cm and not career.chapter_open(sel.ch):
		sel.ch = 0; sel.k = career.frontier(0)
	var c := _base()
	_shade(c, false)
	_crumbs(c, {"career": "CAREER", "free": "FREE PLAY", "mp": "MULTIPLAYER"}[mode], "SELECT STAGE")
	var info := VBoxContainer.new()
	info.set_anchors_preset(Control.PRESET_BOTTOM_LEFT)
	info.position = Vector2(56, -420)
	var tag := UI.label("", UI.body_bold(), 13, UI.ACCENT)
	info.add_child(tag)
	var name_l := UI.shadowed(UI.label("", UI.display(), 72))
	info.add_child(name_l)
	var facts := UI.label("", UI.body_bold(), 15, UI.DIM)
	info.add_child(facts)
	var rival := UI.label("", UI.cond(), 22, UI.INK)
	info.add_child(rival)
	var coach := UI.label("", UI.display_bold(), 24, Color(1, 1, 1, 0.82))
	coach.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
	coach.custom_minimum_size = Vector2(820, 0)
	info.add_child(coach)
	c.add_child(info)
	var lock_l := UI.shadowed(UI.label("", UI.body_bold(), 20))
	lock_l.set_anchors_preset(Control.PRESET_CENTER)
	lock_l.position = Vector2(-400, -60)
	lock_l.size = Vector2(800, 40)
	lock_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	c.add_child(lock_l)
	var loading := UI.label("LOADING STAGE...", UI.body_bold(), 13, UI.DIM)
	loading.set_anchors_preset(Control.PRESET_TOP_RIGHT)
	loading.position = Vector2(-220, 34)
	c.add_child(loading)
	var entry := func() -> Dictionary: return career.chapters[sel.ch].stages[sel.k] if cm else {}
	var index := func() -> int: return entry.call().index if cm else sel.k
	var open := func() -> bool: return not cm or career.stage_open(sel.ch, sel.k)
	var rows: Array
	if cm:
		rows = [["TOUR", func(): return career.chapters[sel.ch].title, func(d): _chapter.call(d)], ["STAGE", func(): return "%02d  %s" % [sel.k + 1, stages[index.call()].name], func(d): _stage_step.call(d)]]
	elif mode == "mp":
		rows = [["STAGE", func(): return "%02d  %s" % [sel.k + 1, stages[sel.k].name], func(d): _stage_step.call(d)], ["AI CARS", func(): return str(main.net.room_setup.ai), func(d): main.net.room_setup.ai = clampi(main.net.room_setup.ai + d, 0, 7)]]
	else:
		rows = [["STAGE", func(): return "%02d  %s" % [sel.k + 1, stages[sel.k].name], func(d): _stage_step.call(d)], ["OPPONENTS", func(): return "Solo" if stages[sel.k].solo else str(main.settings.opponents), func(d): main.settings.opponents = clampi(main.settings.opponents + d, 0, 6); main.settings.save()]]
	var rst := _rows(c, rows, Vector2(0, 0), 460)
	rst.rows[0].p.get_parent().set_anchors_preset(Control.PRESET_BOTTOM_RIGHT)
	rst.rows[0].p.get_parent().position = Vector2(-516, -230)
	var draw := func(reload: bool):
		var st: Dictionary = stages[index.call()]
		var n: int = career.chapters[sel.ch].stages.size() if cm else stages.size()
		tag.text = ("%s · STAGE %02d OF %d" % [career.chapters[sel.ch].title.to_upper(), sel.k + 1, n]) if cm else ("COMMUNITY STAGE" if st.custom else "STAGE %02d" % st.id)
		name_l.text = st.name.to_upper()
		var text: String = st.data.get_string_from_ascii()
		var f := []
		var m := RegEx.create_from_string("nlaps\\((\\d+)\\)").search(text)
		if m: f.append("%s LAPS" % m.get_string(1))
		if text.contains("lightson"): f.append("NIGHT")
		if st.solo: f.append("SOLO TIME RUN")
		if cm:
			var key: String = entry.call().key
			if career.won(key): f.append("WON")
			if career.s.best.has(key): f.append("BEST " + UI.fmt_time(career.s.best[key]))
		if cm and entry.call().style != "":
			f.append("RACE IT" if entry.call().style == "race" else "WASTE THEM")
		facts.text = "   ·   ".join(f)
		rival.text = ""
		coach.text = ""
		if cm:
			var e: Dictionary = entry.call()
			var names := []
			for r in e.rivals:
				names.append(sim.car_name(r).to_upper() + ("  (yours)" if career.owns(r) else ""))
			if names.size():
				rival.text = ("BOSSES: " if names.size() > 1 else "BOSS: ") + ", ".join(names)
			if e.menace.size():
				rival.text += "\nWATCH OUT FOR: " + ", ".join(e.menace.map(func(x): return sim.car_name(x).to_upper()))
			if e.coach != "": coach.text = "“" + e.coach + "”"
		lock_l.text = "" if open.call() else ("Win %s to open this tour" % career.chapters[sel.ch - 1].title if cm and not career.chapter_open(sel.ch) else "Win %s to unlock" % stages[career.chapters[sel.ch].stages[sel.k - 1].index].name if cm else "")
		rst.draw.call()
		if reload:
			loading.visible = true
			_queue_preview(index.call(), loading)
	_stage_step = func(d: int):
		var n: int = career.chapters[sel.ch].stages.size() if cm else stages.size()
		sel.k = (sel.k + d + n) % n
		draw.call(true)
	_chapter = func(d: int):
		var nc := clampi(sel.ch + d, 0, career.chapters.size() - 1)
		if nc == sel.ch: return
		sel.ch = nc
		sel.k = career.frontier(sel.ch)
		draw.call(true)
	var go := func():
		if not open.call():
			return
		_drop_preview()
		if cm:
			career.s.chapter = sel.ch; career.save()
			main.start_race({stage = index.call(), car = career.s.car, opponents = 6, career = {chapter = sel.ch, stage = sel.k}, grid = career.grid_for(sel.ch, sel.k, career.s.car)})
		elif mode == "mp":
			main.net.set_room_setup({stage = sel.k})
			if on_pick.is_valid(): on_pick.call()
		else:
			main.settings.stage = sel.k; main.settings.save()
			main.start_race({stage = sel.k, car = main.settings.car, opponents = main.settings.opponents})
	_mount(c, func(code):
		if code == KEY_ENTER: go.call(); return true
		if code == KEY_ESCAPE:
			_drop_preview()
			if mode == "mp" and on_pick.is_valid(): on_pick.call()
			else: car_select(mode)
			return true
		return _rows_keys(rst, code))
	draw.call(true)

var _stage_step: Callable
var _chapter: Callable


func _queue_preview(index: int, loading: Label) -> void:
	var my := Time.get_ticks_msec()
	_preview_req = my
	# wait until the player stops on a stage: scrolling past never builds anything
	await get_tree().create_timer(0.4).timeout
	if _preview_req != my or screen == null:
		return
	_drop_preview()
	var st: Dictionary = main.assets.stages[index]
	var t0 := Time.get_ticks_msec()
	sim.load_stage(st.data, st.id, 1, 1)
	if "--framelog" in OS.get_cmdline_user_args(): print("step load_stage %d ms" % (Time.get_ticks_msec() - t0))
	sim.start_race(PackedInt32Array([main.settings.car]), 1, 0.3, 1)
	var pv := RaceView.new()
	pv.stagger = true # a little per frame, so scrolling stays smooth
	preview = pv
	main.add_child(pv)
	pv.set_process(false)
	await pv.setup(sim, 0, main.settings.quality)
	if preview != pv or not is_instance_valid(pv):
		return # scrolled on: this one was thrown away
	if is_instance_valid(loading): loading.visible = false

var _preview_req := 0


func _process(dt: float) -> void:
	if preview and is_instance_valid(preview) and not main.racing:
		preview.frame_orbit(dt)


# ----------------------------------------------------------------- options
# --------------------------------------------------------------- deathmatch
## Setup for last-car-standing: arena, your car, how many rivals and which car
## each one drives (or random). Kept for the session.
var dm := {stage = -1, car = -1, rivals = [-1, -1, -1], fixes = -1}   # rival -1 = random, fixes -1 = unlimited
const DM_FIXES := [-1, 0, 1, 2, 3, 5]
var _dm_show := Callable()


func deathmatch(sel_row := 0) -> void:
	mode = "dm"
	_studio("menu")
	var stages: Array = main.assets.stages
	var arenas := []
	for i in stages.size():
		if not stages[i].solo: arenas.append(i)
	if not dm.stage in arenas: dm.stage = arenas[0]
	var cars := _cars_in_order()
	if not dm.car in cars: dm.car = main.settings.car
	var c := _base()
	_shade(c, true)
	_crumbs(c, "DEATHMATCH", "SETUP")
	var t := UI.label("DEATHMATCH", UI.display(), 56)
	t.position = Vector2(96, 76)
	c.add_child(t)
	var sub := UI.label("Every car is out to waste you (and each other). Last one left wins.", UI.body(), 17, UI.DIM)
	sub.position = Vector2(98, 146)
	c.add_child(sub)
	var name_of := func(s: int) -> String: return "Random" if s < 0 else sim.car_name(s)
	var cycle := func(cur: int, d: int, allow_random: bool) -> int:
		var opts: Array = ([-1] if allow_random else []) + cars
		return opts[(opts.find(cur) + d + opts.size()) % opts.size()]
	var rows := [
		["ARENA", func(): return stages[dm.stage].name, func(d): dm.stage = arenas[(arenas.find(dm.stage) + d + arenas.size()) % arenas.size()]],
		["YOUR CAR", func(): return sim.car_name(dm.car), func(d): dm.car = cycle.call(dm.car, d, false); showroom.show_car(dm.car, d); _dm_show.call()],
		["FIXES", func(): return "Unlimited" if dm.fixes < 0 else ("Off" if dm.fixes == 0 else "%d per car" % dm.fixes), func(d): dm.fixes = DM_FIXES[(DM_FIXES.find(dm.fixes) + d + DM_FIXES.size()) % DM_FIXES.size()]],
		["RIVALS", func(): return str(dm.rivals.size()), func(d):
			var n := clampi(dm.rivals.size() + d, 1, 6)
			if n == dm.rivals.size(): return
			if n > dm.rivals.size(): dm.rivals.append(-1)
			else: dm.rivals.pop_back()
			deathmatch.call_deferred(3)],
	]
	for i in dm.rivals.size():
		rows.append(["RIVAL %d" % (i + 1), func(): return name_of.call(dm.rivals[i]), func(d):
			dm.rivals[i] = cycle.call(dm.rivals[i], d, true)
			if dm.rivals[i] >= 0: showroom.show_car(dm.rivals[i], d)
			_dm_show.call()])
	var rst := _rows(c, rows, Vector2(96, 200), 560)
	rst.sel = clampi(sel_row, 0, rows.size() - 1)
	rst.draw.call()
	var go_l := UI.label("FIGHT  ›", UI.display(), 40, UI.ACCENT)
	go_l.position = Vector2(98, 216 + rows.size() * 52)
	go_l.mouse_filter = Control.MOUSE_FILTER_STOP
	go_l.gui_input.connect(func(ev): if ev is InputEventMouseButton and ev.pressed: main.start_race(_dm_opts()))
	c.add_child(go_l)
	var bars := _stat_bars(c)
	var show_row := func():
		var s: int = dm.car
		var random: bool = rst.sel >= 4 and dm.rivals[rst.sel - 4] < 0
		if rst.sel >= 4 and not random: s = dm.rivals[rst.sel - 4]
		if showroom.slot != s: showroom.show_car(s)
		bars.call(s, random)
	_dm_show = show_row
	show_row.call()
	_mount(c, func(code):
		match code:
			KEY_ENTER:
				main.start_race(_dm_opts())
				return true
			KEY_ESCAPE:
				main_menu(2)
				return true
		var used := _rows_keys(rst, code)
		if code == KEY_UP or code == KEY_DOWN: show_row.call()
		return used)


## Every car, starters first then weakest to strongest (the career's order).
func _cars_in_order() -> Array:
	var cars := career.unlock_order()
	for i in sim.car_count():
		if not sim.car_slot(i) in cars: cars.append(sim.car_slot(i))
	return cars


## Race options for the current deathmatch setup; random rivals are drawn fresh.
func _dm_opts() -> Dictionary:
	var cars := []
	for i in sim.car_count(): cars.append(sim.car_slot(i))
	var grid := [dm.car]
	for r in dm.rivals:
		grid.append(r if r >= 0 else cars[randi() % cars.size()])
	return {stage = dm.stage, car = dm.car, grid = grid, deathmatch = true, fixes = dm.fixes}


func options() -> void:
	_studio("menu")
	var s: Settings = main.settings
	var c := _base()
	_shade(c, true)
	var t := UI.label("OPTIONS", UI.display(), 56)
	t.position = Vector2(96, 80)
	c.add_child(t)
	var qs := ["low", "medium", "high", "ultra"]
	var name_edit := LineEdit.new()
	var rst := _rows(c, [
		["GRAPHICS", func(): return s.quality.capitalize(), func(d): s.quality = qs[clampi(qs.find(s.quality) + d, 0, 3)]; s.save(); main.apply_quality()],
		["RENDER SCALE", func(): return ("Auto  (%d%%)" % roundi(main.render_scale() * 100.0)) if s.render_scale <= 0.0 else "%d%%" % roundi(s.render_scale * 100.0), func(d): s.render_scale = Settings.RENDER_SCALES[clampi(Settings.RENDER_SCALES.find(s.render_scale) + d, 0, Settings.RENDER_SCALES.size() - 1)]; s.save(); main.apply_quality()],
		["RENDERER", func():
			var now: String = RenderingServer.get_current_rendering_driver_name()
			var pick: String = {"auto": "Auto", "d3d12": "DirectX 12", "vulkan": "Vulkan"}[s.renderer if s.renderer in ["auto", "d3d12", "vulkan"] else "auto"]
			var running: String = {"d3d12": "DirectX 12", "vulkan": "Vulkan"}.get(now, now)
			var want: String = "vulkan" if s.renderer == "auto" else s.renderer
			return pick + ("" if want == now else "  (restart to use)") + ("  (" + running + ")" if s.renderer == "auto" else ""),
			func(d): var opts := ["auto", "d3d12", "vulkan"]; main.set_renderer(opts[(maxi(0, opts.find(s.renderer)) + d + 3) % 3])],
["RESOLUTION", func():
			var r: Vector2i = s.resolution
			if r == Vector2i.ZERO:
				var n: Vector2i = main.screen_size()
				return "Native  (%d × %d)" % [n.x, n.y]
			return "%d × %d" % [r.x, r.y],
			func(d):
				var opts: Array = main.resolution_options()
				s.resolution = opts[clampi(maxi(0, opts.find(s.resolution)) + d, 0, opts.size() - 1)]
				s.save(); main.apply_display()],
				["DISPLAY", func(): return "Fullscreen" if s.fullscreen else "Window", func(_d): s.fullscreen = not s.fullscreen; s.save(); main.apply_display()],
		["CAMERA", func(): return RaceView.CAMERAS[clampi(s.camera, 0, RaceView.CAMERAS.size() - 1)].name, func(d): s.camera = (clampi(s.camera, 0, RaceView.CAMERAS.size() - 1) + d + RaceView.CAMERAS.size()) % RaceView.CAMERAS.size(); s.save()],
		["IMPACT FEEL", func(): return "Weighty" if s.impact == "weighty" else "Classic NFM", func(_d): s.impact = "classic" if s.impact == "weighty" else "weighty"; s.save()],
		["MUSIC", func(): return "On" if s.music else "Off", func(_d):
			s.music = not s.music; s.save()
			if s.music: main.audio.play_menu_music()
			else: main.audio.stop_music()],
		["VOLUME", func(): return "%d%%" % int(round(s.volume * 100)), func(d): s.volume = clampf(s.volume + d * 0.1, 0.0, 1.0); s.save(); AudioServer.set_bus_volume_db(0, linear_to_db(maxf(0.0001, s.volume)))],
		["VOICE CHAT", func(): return {"open": "Open mic", "ptt": "Push to talk (%s)" % Settings.key_names(s.keys.get("talk", [])), "off": "Off"}.get(s.voice, "Open mic"),
			func(d): var vs := ["open", "ptt", "off"]; s.voice = vs[(vs.find(s.voice) + d + 3) % 3]; s.save()],
		["ONLINE STATS", func(): return "Public" if s.stats_public else "Private (only your name shows)", func(_d):
			s.stats_public = not s.stats_public; s.save(); main.online.hub_refresh_stats()],
		["RESET CAREER", func(): return "Press Enter twice" if not _confirm_reset else "Press Enter again to wipe", Callable()],
	], Vector2(96, 200), 560)
	var nl := UI.label("YOUR NAME (shown to friends online)", UI.body_bold(), 13, UI.DIM)
	nl.position = Vector2(720, 204)
	c.add_child(nl)
	name_edit.text = s.player_name
	name_edit.max_length = 16
	name_edit.position = Vector2(720, 224)
	name_edit.custom_minimum_size = Vector2(360, 40)
	name_edit.add_theme_font_override("font", UI.cond())
	name_edit.add_theme_font_size_override("font_size", 22)
	name_edit.text_changed.connect(func(tx): s.player_name = tx; s.save())
	c.add_child(name_edit)
	_confirm_reset = false
	_mount(c, func(code):
		if name_edit.has_focus():
			if code == KEY_ESCAPE or code == KEY_ENTER: name_edit.release_focus(); return true
			return false
		if code == KEY_ESCAPE: main_menu(4); return true
		if code == KEY_ENTER and rst.sel == 8:
			if _confirm_reset: career.reset(); _confirm_reset = false
			else: _confirm_reset = true
			rst.draw.call()
			return true
		return _rows_keys(rst, code))

var _confirm_reset := false
var _capture: Callable


## Controls: every action with its keys. Enter rebinds (press the new key).
func controls(back: Callable, in_race := false) -> void:
	if not in_race:
		_studio("menu")
	var s: Settings = main.settings
	var c := _base()
	if in_race:
		var dim := ColorRect.new()
		UI.full_rect(dim)
		dim.color = Color(0, 0, 0, 0.6)
		c.add_child(dim)
	else:
		_shade(c, true)
	var t := UI.label("CONTROLS", UI.display(), 56)
	t.position = Vector2(96, 70)
	c.add_child(t)
	var hint := UI.label("Choose a control and press Enter, then press the new key.", UI.body(), 16, UI.DIM)
	hint.position = Vector2(98, 138)
	c.add_child(hint)
	var rows := []
	for a in Settings.ACTIONS:
		var action: String = a[0]
		rows.append([a[1].to_upper(), func(): return Settings.key_names(s.keys.get(action, [])), Callable()])
	rows.append(["RESET TO DEFAULTS", func(): return "Press Enter", Callable()])
	var rst := _rows(c, rows, Vector2(96, 180), 640)
	var pad := UI.label("GAMEPAD\nLeft stick / D-pad: steer   ·   RT or A: accelerate   ·   LT: brake / reverse\nB or X: handbrake   ·   LB: look back   ·   Y: camera   ·   Start: menu", UI.body(), 15, UI.DIM)
	pad.position = Vector2(780, 180)
	pad.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
	pad.custom_minimum_size = Vector2(420, 0)
	c.add_child(pad)
	_mount(c, func(code):
		if code == KEY_ESCAPE: back.call(); return true
		if code == KEY_ENTER:
			if rst.sel == rows.size() - 1:
				s.reset_keys()
				hint.text = "Controls reset to the defaults."
				rst.draw.call()
				return true
			var action: String = Settings.ACTIONS[rst.sel][0]
			var label: String = Settings.ACTIONS[rst.sel][1]
			hint.text = "Press the new key for %s   (Esc to cancel)" % label
			rst.rows[rst.sel].val.text = "..."
			_capture = func(k: int):
				if k != KEY_ESCAPE:
					s.bind(action, k)
					hint.text = "%s is now %s." % [label, OS.get_keycode_string(k)]
				else:
					hint.text = "Choose a control and press Enter, then press the new key."
				rst.draw.call()
			return true
		return _rows_keys(rst, code))
	if in_race:
		overlay = true


# ----------------------------------------------------------------- credits
## Everyone whose work is in the game, from assets/credits.json. Rolls up on
## its own; Up / Down to scroll by hand, Esc or Enter to go back.
func credits() -> void:
	_studio("menu")
	var c := _base()
	_shade(c, true)
	var data = JSON.parse_string(FileAccess.get_file_as_string("res://assets/credits.json"))
	if not data is Dictionary:
		data = {sections = []}
	var clip := Control.new()
	UI.full_rect(clip)
	clip.clip_contents = true
	c.add_child(clip)
	var v := VBoxContainer.new()
	v.position = Vector2(96, 0)
	v.custom_minimum_size = Vector2(900, 0)
	v.add_theme_constant_override("separation", 6)
	clip.add_child(v)
	var gap := func(h: int):
		var sp := Control.new()
		sp.custom_minimum_size = Vector2(0, h)
		v.add_child(sp)
	v.add_child(UI.label("NEED FOR MADNESS", UI.display(), 64))
	v.add_child(UI.label("R E M A S T E R E D", UI.cond(), 20, UI.ACCENT))
	gap.call(40)
	for sec in data.sections:
		var lines: Array = sec.get("lines", [])
		if sec.get("remaster", false):
			var who: String = data.get("remaster_by", "")
			if who == "":
				continue
			lines = [["Remastered by", who]]
			for l in data.get("remaster_links", []):
				lines.append(l)
		if sec.title != "":
			v.add_child(UI.label(sec.title, UI.display_bold(), 30, UI.ACCENT))
		if sec.has("note"):
			var note := UI.label(sec.note, UI.body(), 15, UI.DIM)
			note.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
			note.custom_minimum_size = Vector2(860, 0)
			v.add_child(note)
		for ln in lines:
			if ln[0] == "":
				# a line of its own, not a name / credit pair
				var one := UI.label(ln[1], UI.body_bold(), 20)
				one.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
				one.custom_minimum_size = Vector2(860, 0)
				v.add_child(one)
				continue
			var h := HBoxContainer.new()
			var a := UI.label(ln[0], UI.body_bold(), 18)
			a.custom_minimum_size = Vector2(400, 0)
			h.add_child(a)
			h.add_child(UI.label(ln[1], UI.body(), 18, UI.FAINT if ln[1] == "unknown" else UI.DIM))
			v.add_child(h)
		gap.call(34)
	# license notices that have to ship with the game: the engine's, and NFM
	# World's for the community cars
	for l in [["GODOT ENGINE LICENSE", Engine.get_license_text()],
			["NFM WORLD LICENSE", FileAccess.get_file_as_string("res://assets/nfm_world_license.txt")]]:
		v.add_child(UI.label(l[0], UI.display_bold(), 22, UI.DIM))
		var lic := UI.label(l[1], UI.body(), 12, UI.FAINT)
		lic.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
		lic.custom_minimum_size = Vector2(860, 0)
		v.add_child(lic)
		gap.call(24)
	gap.call(200)
	var hint := UI.label("UP / DOWN  SCROLL      ESC  BACK", UI.cond(), 15, UI.FAINT)
	hint.set_anchors_preset(Control.PRESET_BOTTOM_RIGHT)
	hint.position = Vector2(-330, -44)
	c.add_child(hint)
	var st := {y = 140.0, manual = 0.0}
	v.position.y = st.y
	var roll := func(dt: float):
		if not is_instance_valid(v):
			return
		st.manual = maxf(0.0, st.manual - dt)
		if st.manual == 0.0:
			st.y -= 38.0 * dt
		st.y = clampf(st.y, minf(0.0, 700.0 - v.size.y), 140.0)
		v.position.y = st.y
	var tick := get_tree().process_frame
	var step := func(): roll.call(get_process_delta_time())
	tick.connect(step)
	c.tree_exiting.connect(func(): tick.disconnect(step))
	_mount(c, func(code):
		if code == KEY_ESCAPE or code == KEY_ENTER or code == KEY_BACKSPACE:
			main_menu(6)
			return true
		if code == KEY_UP or code == KEY_DOWN:
			st.y += 120.0 if code == KEY_UP else -120.0
			st.manual = 3.0
			return true
		return false)


# ------------------------------------------------------------- multiplayer
func multiplayer_menu(message := "") -> void:
	hall(message)


## The hall: where everyone online hangs out. Open rooms on the left, chat in
## the middle, who's online on the right.
func hall(message := "") -> void:
	mode = "mp"
	main.audio.play_menu_music()
	_studio("menu")
	var net: Net = main.net
	var online: Online = main.online
	var c := _base()
	_shade(c, true)
	var dark := Color(0.03, 0.034, 0.04, 0.88)
	var t := UI.label("MULTIPLAYER", UI.display(), 48)
	t.position = Vector2(96, 54)
	c.add_child(t)
	var count_l := UI.label("", UI.cond(), 20, UI.ACCENT)
	count_l.position = Vector2(98, 114)
	c.add_child(count_l)
	if message != "":
		var m := UI.label(message, UI.body_bold(), 16, UI.ACCENT)
		m.position = Vector2(300, 118)
		c.add_child(m)
	var panel := func(x: float, w: float, title: String) -> Control:
		var p := Panel.new()
		p.position = Vector2(x, 156)
		p.size = Vector2(w, 660)
		p.add_theme_stylebox_override("panel", UI.panel_style(dark))
		c.add_child(p)
		var h := UI.label(title, UI.body_bold(), 13, UI.DIM)
		h.position = Vector2(18, 12)
		p.add_child(h)
		return p
	# --- rooms
	var rooms_p: Control = panel.call(96, 560, "ROOMS")
	var rooms_v := VBoxContainer.new()
	rooms_v.position = Vector2(12, 40)
	rooms_v.size = Vector2(536, 600)
	rooms_v.add_theme_constant_override("separation", 6)
	rooms_p.add_child(rooms_v)
	# --- chat
	var chat_p: Control = panel.call(676, 540, "CHAT")
	var log := RichTextLabel.new()
	log.bbcode_enabled = true
	log.scroll_following = true
	log.position = Vector2(18, 40)
	log.size = Vector2(504, 552)
	log.add_theme_font_override("normal_font", UI.body())
	log.add_theme_font_override("bold_font", UI.body_bold())
	log.add_theme_font_size_override("normal_font_size", 16)
	log.add_theme_font_size_override("bold_font_size", 16)
	log.add_theme_color_override("default_color", UI.INK)
	chat_p.add_child(log)
	var say := LineEdit.new()
	say.position = Vector2(12, 604)
	say.size = Vector2(516, 44)
	say.max_length = 120
	say.placeholder_text = "Say something to everyone online"
	say.add_theme_font_override("font", UI.body())
	say.add_theme_font_size_override("font_size", 17)
	chat_p.add_child(say)
	# --- who's online
	var who_p: Control = panel.call(1236, 300, "ONLINE")
	var who_v := VBoxContainer.new()
	who_v.position = Vector2(18, 40)
	who_v.size = Vector2(264, 600)
	who_v.add_theme_constant_override("separation", 2)
	who_p.add_child(who_v)

	var esc := func(s: String) -> String: return s.replace("[", "[lb]")
	var line_of := func(m: Dictionary) -> String:
		var n: String = esc.call(str(m.get("n", "?")))
		match str(m.get("t", "")):
			"chat": return "[color=#ff5a1f][b]%s[/b][/color]  %s" % [n, esc.call(str(m.get("m", "")))]
			"join": return "[color=#8a8a86]%s joined[/color]" % n
			"leave": return "[color=#8a8a86]%s left[/color]" % n
			"room": return "[color=#e9c46a]%s opened a room:[/color] %s  [color=#8a8a86]%s · %s%s[/color]" % [n, esc.call(str(m.get("room", ""))), _mode_text(str(m.get("mode", "race"))), esc.call(str(m.get("stage", ""))), " · locked" if m.get("locked", false) else ""]
			"note": return "[color=#8a8a86]%s[/color]" % esc.call(str(m.get("m", "")))
		return ""
	var add_line := func(m: Dictionary):
		if is_instance_valid(log): log.append_text(line_of.call(m) + "\n")
	for m in online.hub_log: add_line.call(m)
	var on_msg := func(m): add_line.call(m)
	online.hub_message.connect(on_msg)
	c.tree_exiting.connect(func(): if online.hub_message.is_connected(on_msg): online.hub_message.disconnect(on_msg))

	var st := {sel = 0, rooms = [], online = [], last_say = 0}
	var draw_who := func():
		if not is_instance_valid(who_v): return
		for ch in who_v.get_children(): ch.queue_free()
		for p in online.hub_members():
			var row := VBoxContainer.new()
			row.add_theme_constant_override("separation", -4)
			var me: bool = p.id == online.user_id
			row.add_child(UI.label(str(p.name) + ("  (you)" if me else ""), UI.cond(), 20, UI.ACCENT if me else UI.INK))
			var line := sim.car_name(int(p.car))
			if not p.stats.is_empty():
				var lv := int(p.stats.get("lvl", 1))
				line = "Lv %d %s  ·  %s" % [lv, Profile.title_of(lv), line]
			row.add_child(UI.label(line, UI.body(), 13, UI.FAINT))
			row.mouse_filter = Control.MOUSE_FILTER_STOP
			row.gui_input.connect(func(ev): if ev is InputEventMouseButton and ev.pressed: _profile_card(p))
			who_v.add_child(row)
	var join_room := func(r: Dictionary):
		if r.get("racing", false) or int(r.get("players", 0)) >= int(r.get("max", 8)): return
		net.lan.stop_listening()
		if r.get("locked", false):
			_room_password(r)
			return
		net.join_room(str(r.code), _name(), main.settings.car)
		lobby()
	var draw_rooms := func():
		if not is_instance_valid(rooms_v): return
		for ch in rooms_v.get_children(): ch.queue_free()
		var rooms: Array = st.online.duplicate()
		var codes := {}
		for r in rooms: codes[str(r.code)] = true
		for r in net.lan.list():
			if not codes.has(str(r.code)):
				var lr: Dictionary = r.duplicate()
				lr.lan = true
				rooms.append(lr)
		st.rooms = rooms
		st.sel = clampi(st.sel, 0, rooms.size())
		var rows := [{create = true}] + rooms
		for i in rows.size():
			var r: Dictionary = rows[i]
			var on: bool = i == st.sel
			var p := PanelContainer.new()
			p.custom_minimum_size = Vector2(536, 58)
			p.add_theme_stylebox_override("panel", UI.panel_style(Color(0.09, 0.095, 0.105, 0.95) if on else Color(0, 0, 0, 0.25), 3 if on else 0))
			var v := VBoxContainer.new()
			v.add_theme_constant_override("separation", -2)
			if r.get("create", false):
				v.add_child(UI.label("+  CREATE A ROOM", UI.display_bold(), 24, UI.ACCENT))
			else:
				var busy: bool = r.get("racing", false) or int(r.get("players", 0)) >= int(r.get("max", 8))
				v.add_child(UI.label(str(r.get("name", "Room")) + ("   [LOCKED]" if r.get("locked", false) else "") + ("   [LAN]" if r.get("lan", false) else ""), UI.cond(), 22, UI.FAINT if busy else UI.INK))
				v.add_child(UI.label("%s  ·  %s  ·  %d/%d  ·  %s" % [_mode_text(str(r.get("mode", "race"))), str(r.get("stage", "")),
					int(r.get("players", 0)), int(r.get("max", 8)), "Racing" if r.get("racing", false) else ("Full" if busy else "Open")], UI.body(), 13, UI.DIM))
			p.add_child(v)
			p.mouse_filter = Control.MOUSE_FILTER_STOP
			p.gui_input.connect(func(ev):
				if ev is InputEventMouseButton and ev.pressed:
					st.sel = i
					if r.get("create", false): room_settings(false)
					elif ev.double_click: join_room.call(r))
			rooms_v.add_child(p)
		if rooms.is_empty():
			var e := UI.label("No open rooms yet. Make one and it shows up for everyone.", UI.body(), 15, UI.FAINT)
			e.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
			e.custom_minimum_size = Vector2(520, 0)
			rooms_v.add_child(e)
	var refresh := func():
		if not is_instance_valid(c): return
		if online.is_ready():
			st.online = (await online.search_async()).filter(func(r): return int(r.get("v", 0)) == 1 and str(r.get("code", "")) != "")
			var n: int = await online.online_count_async()
			if is_instance_valid(count_l): count_l.text = "%d ONLINE" % n
		draw_rooms.call()
		draw_who.call()
	var send := func():
		var text := say.text.strip_edges()
		if text == "" or Time.get_ticks_msec() - st.last_say < 1500: return
		st.last_say = Time.get_ticks_msec()
		say.text = ""
		online.hub_say({t = "chat", n = _name(), m = text})
	say.text_submitted.connect(func(_t): send.call())
	_esc_leaves(say, func(): say.release_focus())
	online.hub_changed.connect(draw_who)
	c.tree_exiting.connect(func(): if online.hub_changed.is_connected(draw_who): online.hub_changed.disconnect(draw_who))
	var tm := Timer.new()
	tm.wait_time = 5.0
	tm.autostart = true
	tm.timeout.connect(refresh)
	c.add_child(tm)
	var tm2 := Timer.new()
	tm2.wait_time = 0.5
	tm2.autostart = true
	tm2.timeout.connect(func(): draw_rooms.call() if not st.online.is_empty() or not net.lan.list().is_empty() else null)
	c.add_child(tm2)
	net.lan.listen()
	draw_rooms.call()
	_mount(c, func(code):
		if say.has_focus(): return false
		match code:
			KEY_UP: st.sel = maxi(0, st.sel - 1); _sfx(); draw_rooms.call(); return true
			KEY_DOWN: st.sel = mini(st.rooms.size(), st.sel + 1); _sfx(); draw_rooms.call(); return true
			KEY_ENTER:
				if st.sel == 0: room_settings(false)
				else: join_room.call(st.rooms[st.sel - 1])
				return true
			KEY_TAB: say.grab_focus(); return true
			KEY_ESCAPE:
				net.lan.stop_listening()
				online.hub_leave_async()
				main_menu(3)
				return true
		return false)
	# join the hall (and sign in) in the background
	if online.hub == null:
		add_line.call({t = "note", m = "Joining the hall..."})
		if await online.hub_join_async(_name(), main.settings.car):
			add_line.call({t = "note", m = "You're in. Say hi."})
		else:
			add_line.call({t = "note", m = "Couldn't reach the online hall. Rooms on your network still show up here."})
	refresh.call()


func _name() -> String:
	var n: String = main.settings.player_name.strip_edges()
	return n if n != "" else "Player"


## Host a room straight away with the current settings (test hooks use this).
func _host() -> void:
	main.net.host_room(_name(), main.settings.car, mp_room.duplicate())
	lobby()


## Room settings kept for the session, so the next room starts from the last one.
var mp_room := Net.default_setup()
const MP_FIXES := [-1, 0, 1, 2, 3, 5]


func _fixes_text(n: int) -> String:
	return "Unlimited" if n < 0 else ("Off" if n == 0 else "%d per car" % n)


func _mode_text(m: String) -> String:
	return {"dm": "Deathmatch", "hangout": "Hangout"}.get(m, "Race")


## Create a room (or, for the host of a room, change its settings).
func room_settings(editing: bool, sel_row := 0) -> void:
	_studio("menu")
	var net: Net = main.net
	if editing:
		mp_room = net.room_setup.duplicate()
	var stages: Array = main.assets.stages
	var usable := []
	for i in stages.size():
		if not stages[i].solo: usable.append(i)
	if not int(mp_room.stage) in usable: mp_room.stage = usable[0]
	var cars := _cars_in_order()
	var car := {v = net.my_car if editing else main.settings.car}
	if not car.v in cars: car.v = cars[0]
	showroom.show_car(car.v)
	var c := _base()
	_shade(c, true)
	_crumbs(c, "MULTIPLAYER", "ROOM SETTINGS" if editing else "CREATE A ROOM")
	var t := UI.label("ROOM SETTINGS" if editing else "CREATE A ROOM", UI.display(), 56)
	t.position = Vector2(96, 76)
	c.add_child(t)
	var name_l := UI.label("ROOM NAME", UI.body_bold(), 13, UI.DIM)
	name_l.position = Vector2(98, 150)
	c.add_child(name_l)
	var name_e := LineEdit.new()
	name_e.position = Vector2(96, 172)
	name_e.custom_minimum_size = Vector2(560, 44)
	name_e.max_length = 28
	name_e.placeholder_text = "%s's room" % _name()
	name_e.text = str(mp_room.name)
	name_e.add_theme_font_override("font", UI.cond())
	name_e.add_theme_font_size_override("font_size", 22)
	name_e.text_changed.connect(func(tx): mp_room.name = tx)
	name_e.text_submitted.connect(func(_tx): name_e.release_focus())
	c.add_child(name_e)
	var pw_l := UI.label("PASSWORD (leave empty for an open room)", UI.body_bold(), 13, UI.DIM)
	pw_l.position = Vector2(690, 150)
	c.add_child(pw_l)
	var pw_e := LineEdit.new()
	pw_e.position = Vector2(688, 172)
	pw_e.custom_minimum_size = Vector2(330, 44)
	pw_e.max_length = 24
	pw_e.placeholder_text = "No password"
	pw_e.secret = true
	pw_e.text = str(mp_room.get("password", ""))
	pw_e.add_theme_font_override("font", UI.cond())
	pw_e.add_theme_font_size_override("font_size", 22)
	pw_e.text_changed.connect(func(tx): mp_room.password = tx)
	pw_e.text_submitted.connect(func(_tx): pw_e.release_focus())
	c.add_child(pw_e)
	var cyc := func(list: Array, cur, d: int):
		return list[(list.find(cur) + d + list.size()) % list.size()]
	var rows := [
		["MODE", func(): return _mode_text(mp_room.mode) + ("  (free roam, no AI)" if mp_room.mode == "hangout" else ""), func(d): mp_room.mode = cyc.call(["race", "dm", "hangout"], mp_room.mode, d)],
		["STAGE", func(): return stages[int(mp_room.stage)].name, func(d): mp_room.stage = cyc.call(usable, int(mp_room.stage), d)],
		["AI CARS", func(): return "None" if int(mp_room.ai) == 0 else str(mp_room.ai), func(d): mp_room.ai = clampi(int(mp_room.ai) + d, 0, 7)],
		["FIXES", func(): return _fixes_text(int(mp_room.fixes)), func(d): mp_room.fixes = cyc.call(MP_FIXES, int(mp_room.fixes), d)],
		["MAX PLAYERS", func(): return str(mp_room.max_players), func(d): mp_room.max_players = clampi(int(mp_room.max_players) + d, 2, Net.MAX_PLAYERS)],
		["YOUR CAR", func(): return sim.car_name(car.v), func(d): car.v = cyc.call(cars, car.v, d); showroom.show_car(car.v, d)],
	]
	var rst := _rows(c, rows, Vector2(96, 236), 560)
	rst.sel = clampi(sel_row, 0, rows.size() - 1)
	rst.draw.call()
	var go := func():
		mp_room.name = name_e.text.strip_edges()
		mp_room.password = pw_e.text
		if editing:
			net.set_room_setup(mp_room.duplicate())
			if car.v != net.my_car:
				_mp_car(car.v)
				return
			lobby()
		else:
			net.host_room(_name(), car.v, mp_room.duplicate())
			lobby()
	var go_l := UI.label("SAVE  ›" if editing else "CREATE ROOM  ›", UI.display(), 40, UI.ACCENT)
	go_l.position = Vector2(98, 268 + rows.size() * 56)
	go_l.mouse_filter = Control.MOUSE_FILTER_STOP
	go_l.gui_input.connect(func(ev): if ev is InputEventMouseButton and ev.pressed: go.call())
	c.add_child(go_l)
	var back := func():
		if editing: lobby()
		else: multiplayer_menu()
	var back_l := UI.label("‹  BACK", UI.display(), 40, UI.DIM)
	back_l.position = Vector2(470, 268 + rows.size() * 56)
	back_l.mouse_filter = Control.MOUSE_FILTER_STOP
	back_l.gui_input.connect(func(ev): if ev is InputEventMouseButton and ev.pressed: back.call())
	c.add_child(back_l)
	# Esc while typing in a box leaves the box (a second Esc goes back)
	for e in [name_e, pw_e]:
		e.gui_input.connect(func(ev):
			if ev is InputEventKey and ev.pressed and ev.keycode == KEY_ESCAPE:
				e.release_focus()
				e.accept_event())
	var bars := _stat_bars(c)
	bars.call(car.v, false)
	_mount(c, func(code):
		if name_e.has_focus() or pw_e.has_focus():
			return false
		match code:
			KEY_ENTER:
				go.call()
				return true
			KEY_ESCAPE:
				back.call()
				return true
		var used := _rows_keys(rst, code)
		bars.call(car.v, false)
		return used)


## Esc inside a text box goes back too (the box would otherwise keep the key).
func _esc_leaves(edit: LineEdit, back: Callable) -> void:
	edit.gui_input.connect(func(ev):
		if ev is InputEventKey and ev.pressed and ev.keycode == KEY_ESCAPE:
			edit.accept_event()
			back.call_deferred())


## A player's profile, opened from the hall's online list.
func _profile_card(p: Dictionary) -> void:
	var st: Dictionary = p.get("stats", {})
	var me: bool = p.id == main.online.user_id
	if me:
		st = main.profile.to_dict()
		st.lvl = main.profile.level()
	var box := VBoxContainer.new()
	box.add_theme_constant_override("separation", 6)
	if st.is_empty():
		box.add_child(UI.label("This player keeps their stats private.", UI.body(), 17, UI.DIM))
	else:
		var lv := int(st.get("lvl", 1))
		box.add_child(UI.label("LEVEL %d   %s" % [lv, Profile.title_of(lv).to_upper()], UI.cond(), 24, UI.ACCENT))
		var g := GridContainer.new()
		g.columns = 2
		g.add_theme_constant_override("h_separation", 40)
		var races := maxi(1, int(st.get("races", 0)))
		for row in [["Races", str(st.get("races", 0))], ["Wins", "%d  (%d%%)" % [int(st.get("wins", 0)), int(100.0 * int(st.get("wins", 0)) / races)]],
				["Players wasted", str(st.get("wastes", 0))],
				["Times wasted", str(st.get("wrecked", 0))], ["Damage dealt", "%d cars' worth" % int(float(st.get("damage", 0.0)) / 100.0)]]:
			g.add_child(UI.label(row[0], UI.body(), 16, UI.DIM))
			g.add_child(UI.label(row[1], UI.body_bold(), 16, UI.INK))
		box.add_child(g)
		if me and not main.settings.stats_public:
			box.add_child(UI.label("Only you can see this. Turn on public stats in Options to show it.", UI.body(), 14, UI.FAINT))
	_card(str(p.name), sim.car_name(int(p.car)), box, [["Close", func(): hall()]], 0, func(): hall())


## A locked room: ask for its password, then join.
func _room_password(r: Dictionary) -> void:
	var c := _base()
	_shade(c, true)
	_crumbs(c, "FIND A ROOM", "PASSWORD")
	var t := UI.label(str(r.get("name", "Room")), UI.display(), 56)
	t.position = Vector2(96, 80)
	c.add_child(t)
	var sub := UI.label("This room is locked. Type its password to join.", UI.body(), 17, UI.DIM)
	sub.position = Vector2(98, 150)
	c.add_child(sub)
	var edit := LineEdit.new()
	edit.position = Vector2(96, 200)
	edit.custom_minimum_size = Vector2(420, 52)
	edit.secret = true
	edit.placeholder_text = "Password"
	edit.text = main._arg(OS.get_cmdline_user_args(), "--joinpw=") # test hook
	edit.add_theme_font_override("font", UI.cond())
	edit.add_theme_font_size_override("font_size", 26)
	c.add_child(edit)
	var go := func():
		if edit.text == "": return
		main.net.join_room(str(r.code), _name(), main.settings.car, edit.text)
		lobby()
	edit.text_submitted.connect(func(_t): go.call())
	_mount(c, func(code):
		if code == KEY_ESCAPE: hall(); return true
		if code == KEY_ENTER: go.call(); return true
		return false)
	_esc_leaves(edit, func(): hall())
	edit.grab_focus()


## A room: everyone's car lined up in the studio with their name over it,
## and below it the players, the room chat and what you can do.
func lobby() -> void:
	var net: Net = main.net
	_studio("menu")
	var c := _base()
	var dark := Color(0.03, 0.034, 0.04, 0.9)
	# top: room name, status, settings, countdown
	var top := ColorRect.new()
	top.color = Color(0, 0, 0, 0.55)
	top.position = Vector2(0, 0)
	top.size = Vector2(4000, 200)
	top.mouse_filter = Control.MOUSE_FILTER_IGNORE
	c.add_child(top)
	var t := UI.label("ROOM", UI.display(), 48)
	t.position = Vector2(96, 40)
	c.add_child(t)
	var status_l := UI.label("", UI.body(), 15, UI.DIM)
	status_l.position = Vector2(98, 100)
	c.add_child(status_l)
	var setup_l := UI.label("", UI.cond(), 20)
	setup_l.position = Vector2(98, 126)
	c.add_child(setup_l)
	var count_l := UI.label("", UI.cond(), 20, UI.ACCENT)
	count_l.position = Vector2(98, 154)
	c.add_child(count_l)
	var tick := func():
		if not is_instance_valid(count_l): return
		if not net.is_host and net.players.is_empty():
			count_l.text = "" # still connecting
			return
		var left: float = net.auto_left()
		if left >= 0.0:
			count_l.text = "RACE STARTS IN %d:%02d" % [int(left) / 60, int(left) % 60]
		elif net.players.size() < 2:
			var wait := "%d minutes" % int(net.auto_start_secs / 60.0) if net.auto_start_secs >= 120.0 else "%d seconds" % int(net.auto_start_secs)
			count_l.text = "Waiting for another player. The race starts by itself %s after someone joins." % wait
		else:
			count_l.text = ""
	var tm := Timer.new()
	tm.wait_time = 0.25
	tm.autostart = true
	tm.timeout.connect(tick)
	c.add_child(tm)
	# bottom panels
	var panel := func(x: float, w: float, title: String) -> Control:
		var p := Panel.new()
		p.position = Vector2(x, 590)
		p.size = Vector2(w, 280)
		p.add_theme_stylebox_override("panel", UI.panel_style(dark))
		c.add_child(p)
		var h := UI.label(title, UI.body_bold(), 13, UI.DIM)
		h.position = Vector2(18, 10)
		p.add_child(h)
		return p
	var who_p: Control = panel.call(96, 440, "PLAYERS")
	var who_h := who_p.get_child(0) as Label
	var list := VBoxContainer.new()
	list.position = Vector2(14, 36)
	list.size = Vector2(412, 236)
	list.add_theme_constant_override("separation", 2)
	who_p.add_child(list)
	var chat_p: Control = panel.call(556, 560, "ROOM CHAT")
	var log := RichTextLabel.new()
	log.bbcode_enabled = true
	log.scroll_following = true
	log.position = Vector2(18, 34)
	log.size = Vector2(524, 182)
	log.add_theme_font_override("normal_font", UI.body())
	log.add_theme_font_override("bold_font", UI.body_bold())
	log.add_theme_font_size_override("normal_font_size", 15)
	log.add_theme_font_size_override("bold_font_size", 15)
	chat_p.add_child(log)
	var say := LineEdit.new()
	say.position = Vector2(12, 224)
	say.size = Vector2(536, 42)
	say.max_length = 120
	say.placeholder_text = "Say something to the room"
	say.add_theme_font_override("font", UI.body())
	say.add_theme_font_size_override("font_size", 16)
	chat_p.add_child(say)
	panel.call(1136, 400, "")
	var esc := func(x: String) -> String: return x.replace("[", "[lb]")
	var add_line := func(m: Dictionary):
		if not is_instance_valid(log): return
		if m.has("note"): log.append_text("[color=#8a8a86]%s[/color]\n" % esc.call(str(m.note)))
		else: log.append_text("[color=#ff5a1f][b]%s[/b][/color]  %s\n" % [esc.call(str(m.get("n", "?"))), esc.call(str(m.get("m", "")))])
	for m in net.room_log: add_line.call(m)
	var on_chat := func(m): add_line.call(m)
	net.chat.connect(on_chat)
	c.tree_exiting.connect(func(): if net.chat.is_connected(on_chat): net.chat.disconnect(on_chat))
	say.text_submitted.connect(func(_t):
		net.say(say.text)
		say.text = "")
	_esc_leaves(say, func(): say.release_focus())
	# actions
	var items := []
	if net.is_host:
		items = [["START NOW", func(): if net.players.size() >= 1: net.start_race(), ""],
			["ROOM SETTINGS", func(): room_settings(true), ""],
			["CHANGE CAR", func(): car_select("mp", func(s): _mp_car(s)), ""],
			["CLOSE ROOM", func(): net.leave(); multiplayer_menu(), ""]]
	else:
		items = [["READY", func(): net.set_ready(not net.my_ready()), ""],
			["CHANGE CAR", func(): car_select("mp", func(s): _mp_car(s)), ""],
			["LEAVE ROOM", func(): net.leave(); multiplayer_menu(), ""]]
	var st := _menu(c, items, Vector2(1170, 624), 30)
	var talk_marks := []
	var voice_l := UI.label("", UI.body(), 14, UI.DIM)
	voice_l.position = Vector2(1200, 830)
	c.add_child(voice_l)
	var mic_i := Hud.MicIcon.new()
	mic_i.position = Vector2(1172, 828)
	mic_i.size = Vector2(20, 26)
	c.add_child(mic_i)
	var vt := Timer.new()
	vt.wait_time = 0.15
	vt.autostart = true
	vt.timeout.connect(func():
		if not is_instance_valid(voice_l): return
		var who: Array = main.online.talking()
		for m in talk_marks:
			if is_instance_valid(m): m.text = "SPEAKING" if str(m.get_meta("uid", "")) in who and str(m.get_meta("uid", "")) != "" else ""
		mic_i.visible = main.online.mic_on()
		if not main.online.voice_ready():
			voice_l.text = "Voice chat: connecting..." if net.transport == "eos" else ""
		else:
			voice_l.text = {"open": "Voice: mic open", "ptt": "Voice: hold %s to talk" % Settings.key_names(main.settings.keys.get("talk", [])), "off": "Voice: mic off"}.get(main.settings.voice, ""))
	c.add_child(vt)
	var refresh := func():
		if not is_instance_valid(list): return
		for ch in list.get_children(): ch.queue_free()
		talk_marks.clear()
		who_h.text = "PLAYERS  %d / %d" % [net.players.size(), int(net.room_setup.max_players)]
		var ids: Array = net.players.keys()
		ids.sort()
		var entries := []
		for id in ids:
			var p: Dictionary = net.players[id]
			var tag := "HOST" if id == 1 else ("READY" if p.get("ready", false) else "")
			entries.append({slot = int(p.car), name = str(p.name), tag = tag})
			var row := HBoxContainer.new()
			row.add_theme_constant_override("separation", 10)
			var nm := UI.label(str(p.name), UI.cond(), 21, UI.INK)
			nm.custom_minimum_size = Vector2(150, 0)
			nm.clip_text = true
			row.add_child(nm)
			var car_l := UI.label(sim.car_name(int(p.car)), UI.body(), 14, UI.DIM)
			car_l.custom_minimum_size = Vector2(130, 0)
			car_l.clip_text = true
			row.add_child(car_l)
			row.add_child(UI.label(tag, UI.body_bold(), 12, UI.GOLD if id == 1 else UI.GOOD))
			var talk_l := UI.label("", UI.body_bold(), 12, UI.ACCENT)
			talk_l.set_meta("uid", net.peer_user(id))
			row.add_child(talk_l)
			talk_marks.append(talk_l)
			if net.is_host and id != 1:
				var kick := UI.label("REMOVE", UI.body_bold(), 12, UI.FAINT)
				kick.mouse_filter = Control.MOUSE_FILTER_STOP
				kick.gui_input.connect(func(ev): if ev is InputEventMouseButton and ev.pressed: net.kick(id))
				kick.mouse_entered.connect(func(): kick.add_theme_color_override("font_color", UI.BAD))
				kick.mouse_exited.connect(func(): kick.add_theme_color_override("font_color", UI.FAINT))
				row.add_child(kick)
			list.add_child(row)
		if entries.is_empty():
			entries.append({slot = net.my_car, name = net.my_name, tag = ""})
		showroom.show_lineup(entries)
		setup_l.text = "%s   ·   %s   ·   AI CARS %d   ·   FIXES %s   ·   %s" % [_mode_text(net.room_setup.mode).to_upper(),
			main.assets.stages[clampi(int(net.room_setup.stage), 0, main.assets.stages.size() - 1)].name, int(net.room_setup.ai),
			_fixes_text(int(net.room_setup.fixes)).to_upper(), "LOCKED" if (str(net.room_setup.get("password", "")) != "" or net.room_setup.get("locked", false)) else "OPEN"]
		t.text = str(net.room_setup.name) if str(net.room_setup.name) != "" else "ROOM"
		if not net.is_host:
			st.rows[0].t.text = "NOT READY" if net.my_ready() else "READY"
			if net.players.is_empty():
				setup_l.text = ""
		tick.call()
	_mount(c, func(code):
		if say.has_focus(): return false
		if code == KEY_TAB: say.grab_focus(); return true
		return _menu_keys(st, code))
	# mounting clears the old screen's hooks, so attach these afterwards
	_lobby_status = status_l
	_lobby_refresh = refresh
	status_l.text = _last_status
	refresh.call()


func _mp_car(s: int) -> void:
	if s >= 0:
		main.net.my_car = s
		if main.net.is_host:
			main.net.players[1].car = s
			main.net._broadcast_lobby()
		else:
			main.net._send(1, Net.HELLO, {name = _name(), car = s, v = 1})
	lobby()


# ------------------------------------------------------------ race overlays
func _card(html_title: String, sub: String, extra: Control, items: Array, sel := 0, on_back := Callable()) -> void:
	var c := _base()
	var dim := ColorRect.new()
	UI.full_rect(dim)
	dim.color = Color(0, 0, 0, 0.55)
	c.add_child(dim)
	var p := PanelContainer.new()
	var sb := UI.panel_style(Color(0.047, 0.05, 0.06, 0.92))
	sb.border_width_top = 3
	sb.border_color = UI.ACCENT
	sb.content_margin_left = 40; sb.content_margin_right = 40; sb.content_margin_top = 30; sb.content_margin_bottom = 26
	p.add_theme_stylebox_override("panel", sb)
	p.set_anchors_preset(Control.PRESET_CENTER)
	p.custom_minimum_size = Vector2(560, 0)
	var v := VBoxContainer.new()
	v.add_theme_constant_override("separation", 8)
	v.add_child(UI.label(html_title, UI.display(), 60))
	if sub != "":
		v.add_child(UI.label(sub, UI.body(), 17, UI.DIM))
	if extra:
		v.add_child(extra)
	var holder := Control.new()
	holder.custom_minimum_size = Vector2(480, items.size() * 40 + 10)
	v.add_child(holder)
	p.add_child(v)
	c.add_child(p)
	var st := _menu(holder, items, Vector2(0, 6), 26, sel)
	_mount(c, func(code):
		if code == KEY_ESCAPE and on_back.is_valid(): on_back.call(); return true
		return _menu_keys(st, code))
	overlay = true
	await get_tree().process_frame
	p.position = (get_viewport_rect().size - p.size) / 2.0


func pause_menu(sel := 0) -> void:
	_card("PAUSED", main.assets.stages[main.race_opts.stage].name, null, [
		["Resume", func(): main.set_paused(false)],
		["Restart race", func(): main.set_paused(false); main.start_race(main.race_opts)],
		["Options", func(): race_options(func(): pause_menu(2))],
		["Controls", func(): controls(func(): pause_menu(3), true)],
		["Quit to menu", func(): main.set_paused(false); main.end_race(); main_menu()],
	], sel, func(): main.set_paused(false))


## Online the race can't stop for everyone, so Esc opens this over the race.
func net_menu(sel := 0) -> void:
	var net: Net = main.net
	var items := [["Resume", func(): clear()], ["Options", func(): race_options(func(): net_menu(1))], ["Controls", func(): controls(func(): net_menu(2), true)]]
	var sub := "The race keeps going for everyone while this is open."
	if net.is_host:
		# ending it for everyone is only fair once the other players are out
		# (wasted), leaving just AI cars, or in a hangout
		if _others_out() or main.race_opts.get("freeroam", false):
			items.append(["End race for everyone", func(): main.end_race(); net.end_race_for_all(); lobby()])
			items.append(["Close room", func(): main.end_race(); net.leave(); multiplayer_menu("You closed the room")])
		else:
			sub += "
The race can only be ended once the other players are out."
	else:
		items.append(["Leave race", func(): main.end_race(); net.leave(); multiplayer_menu("You left the race")])
	_card("MENU", sub, null, items, sel, func(): clear())


## Online: every other player's car is wasted (only AI cars, or nobody, left).
func _others_out() -> bool:
	var view: RaceView = main.view
	if view == null:
		return true
	for id in main.net.players:
		var g := int(main.net.players[id].get("grid", -1))
		if g < 0 or g == view.focus: continue
		if view.curr[g * view.stride + 10] < 0.5:
			return false
	return true


## Graphics / sound / camera while racing.
func race_options(back: Callable) -> void:
	var s: Settings = main.settings
	var qs := ["low", "medium", "high", "ultra"]
	var c := _base()
	var dim := ColorRect.new()
	UI.full_rect(dim)
	dim.color = Color(0, 0, 0, 0.6)
	c.add_child(dim)
	var t := UI.label("OPTIONS", UI.display(), 56)
	t.position = Vector2(96, 110)
	c.add_child(t)
	var rows := [
		["GRAPHICS", func(): return s.quality.capitalize() + "  (applies next race)", func(d): s.quality = qs[clampi(qs.find(s.quality) + d, 0, 3)]; s.save(); main.apply_quality()],
		["RENDER SCALE", func(): return ("Auto  (%d%%)" % roundi(main.render_scale() * 100.0)) if s.render_scale <= 0.0 else "%d%%" % roundi(s.render_scale * 100.0), func(d): s.render_scale = Settings.RENDER_SCALES[clampi(Settings.RENDER_SCALES.find(s.render_scale) + d, 0, Settings.RENDER_SCALES.size() - 1)]; s.save(); main.apply_quality()],
		["CAMERA", func(): return RaceView.CAMERAS[clampi(s.camera, 0, RaceView.CAMERAS.size() - 1)].name, func(d):
			s.camera = (clampi(s.camera, 0, RaceView.CAMERAS.size() - 1) + d + RaceView.CAMERAS.size()) % RaceView.CAMERAS.size(); s.save()
			if main.view: main.view.cam_mode = s.camera],
		["MUSIC", func(): return "On" if s.music else "Off", func(_d):
			s.music = not s.music; s.save()
			if not s.music: main.audio.stop_music()
			elif main.racing: main.audio.play_stage_music(main.assets.stages[main.race_opts.stage], main.sim.stage_soundtrack())],
		["VOLUME", func(): return "%d%%" % int(round(s.volume * 100)), func(d): s.volume = clampf(s.volume + d * 0.1, 0.0, 1.0); s.save(); AudioServer.set_bus_volume_db(0, linear_to_db(maxf(0.0001, s.volume)))],
["RESOLUTION", func():
			var r: Vector2i = s.resolution
			if r == Vector2i.ZERO:
				var n: Vector2i = main.screen_size()
				return "Native  (%d × %d)" % [n.x, n.y]
			return "%d × %d" % [r.x, r.y],
			func(d):
				var opts: Array = main.resolution_options()
				s.resolution = opts[clampi(maxi(0, opts.find(s.resolution)) + d, 0, opts.size() - 1)]
				s.save(); main.apply_display()],
				["DISPLAY", func(): return "Fullscreen" if s.fullscreen else "Window", func(_d): s.fullscreen = not s.fullscreen; s.save(); main.apply_display()],
	]
	var rst := _rows(c, rows, Vector2(96, 200), 620)
	_mount(c, func(code):
		if code == KEY_ESCAPE or code == KEY_ENTER: back.call(); return true
		return _rows_keys(rst, code))
	overlay = true


## The result straight after a race: won / place / wasted... Enter watches the
## killcam (when there is one), or goes on to the full results.
func outcome(r: Dictionary, has_killcam: bool, watch: Callable, skip: Callable) -> void:
	var dmr: bool = main.race_opts.get("deathmatch", false)
	var titles := {1: "YOU WON" if r.position == 1 else UI.ordinal(r.position).to_upper() + " PLACE", 2: "WASTED", 3: "LAST CAR STANDING" if dmr else "ALL WASTED", 4: "YOU LOST"}
	var info: PackedInt32Array = sim.race_info()
	var subs := {1: "Finished in " + UI.fmt_time(info[6]), 2: "Your car was destroyed", 3: "You wrecked the whole field", 4: "%s crossed the line first" % main._driver_name(r.who)}
	# no choices: the result shows for a moment, then the highlight plays
	# (hold Enter there to skip it), then the results
	main.hud.message(titles[r.kind], subs[r.kind], 2.6)
	var race_view = main.view
	await get_tree().create_timer(2.6).timeout
	# the player may have quit to the menu (or restarted) meanwhile: then this
	# race's highlight and results are gone, and so must this be. (Clearing
	# here used to wipe the main menu, leaving only the turntable.)
	if not main.racing or main.view != race_view:
		return
	if overlay: clear() # a pause menu over the end of the race
	if has_killcam: watch.call()
	else: skip.call()


func results(r: Dictionary) -> void:
	if main.race_opts.get("net", false):
		_online_results(r)
		return
	var dmr: bool = main.race_opts.get("deathmatch", false)
	var titles := {1: "YOU WON" if r.position == 1 else UI.ordinal(r.position).to_upper() + " PLACE", 2: "WASTED", 3: "LAST CAR STANDING" if dmr else "ALL WASTED", 4: "YOU LOST"}
	var info: PackedInt32Array = sim.race_info()
	var grid: PackedInt32Array = sim.grid()
	var subs := {1: "Finished in " + UI.fmt_time(info[6]), 2: "Your car was destroyed", 3: "You wrecked the whole field", 4: "%s crossed the line first" % sim.car_name(grid[r.who])}
	var win: bool = r.kind == 3 or (r.kind == 1 and r.position == 1)
	var box := VBoxContainer.new()
	var earned := {}
	if main.race_opts.has("career"):
		earned = career.record(main.race_opts.career.chapter, main.race_opts.career.stage, r, info[6], main.wrecked_rivals())
		for line in career.earned_lines(earned, main.assets, sim):
			var l := UI.label(line, UI.cond(), 22, UI.GOLD if line.begins_with("NEW CAR") else UI.ACCENT)
			box.add_child(l)
	var states: PackedFloat32Array = sim.car_states()
	var stride: int = sim.car_state_size()
	var order: Array = range(grid.size())
	order.sort_custom(func(a, b): return states[a * stride + 13] < states[b * stride + 13])
	if dmr:
		subs[2] = "Your car was destroyed. %d left standing" % _alive_count(states, stride, grid.size())
	if dmr:
		box.add_child(_dm_report(grid, r))
	elif grid.size() > 1:
		for i in order:
			var who: String = main._driver_name(i)
			var line := "%s   %s%s" % [UI.ordinal(int(states[i * stride + 13])), who, "  (you)" if i == main.view.focus else ""]
			if states[i * stride + 10] > 0.5:
				line += "   (wasted)"
			box.add_child(UI.label(line, UI.body_bold() if i == main.view.focus else UI.body(), 17, UI.INK if i == main.view.focus else UI.DIM))
	var items := []
	if main.race_opts.get("net", false):
		if main.net.is_host:
			items = [["Back to room", func(): main.end_race(); main.net.race_over(); main.net._broadcast_lobby(); lobby()]]
		else:
			items = [["Back to room", func(): main.end_race(); main.net.race_over(); lobby()]]
	elif main.race_opts.has("career"):
		var at = main.race_opts.career
		var next = earned.get("next", null)
		items = [["Continue", func(): main.end_race(); _reveal(earned.get("cars", []), func(): stage_select("career", next if next else at))], ["Race again", func(): main.start_race(main.race_opts)], ["Main menu", func(): main.end_race(); main_menu()]]
	elif dmr:
		items = [["Rematch", func(): main.start_race(main.race_opts)], ["New random rivals", func(): main.start_race(_dm_opts())], ["Change setup", func(): main.end_race(); deathmatch()], ["Main menu", func(): main.end_race(); main_menu(2)]]
	else:
		items = [["Race again", func(): main.start_race(main.race_opts)], ["New opponents", func(): var o: Dictionary = main.race_opts.duplicate(); o.erase("grid"); o.erase("seed"); main.start_race(o)], ["Stage select", func(): main.end_race(); stage_select("free")], ["Main menu", func(): main.end_race(); main_menu()]]
	_card(titles[r.kind], subs[r.kind], box, items)
	_guard()


## Online, once the race is decided: the top three on the podium, every
## player's numbers and the awards, then a rematch vote or back to the room.
var _podium_data := {}
var _podium_xp := 0


func _online_results(r: Dictionary) -> void:
	var net: Net = main.net
	var grid: PackedInt32Array = sim.grid()
	var states: PackedFloat32Array = sim.car_states()
	var stride: int = sim.car_state_size()
	var cs: PackedFloat32Array = main.final_stats if not main.final_stats.is_empty() else sim.combat_stats()
	var laps: PackedInt32Array = sim.best_laps()
	var order: Array = range(grid.size())
	order.sort_custom(func(a, b): return states[a * stride + 13] < states[b * stride + 13])
	var rows := []
	for i in order:
		rows.append({slot = i, car = int(grid[i]), name = main._short_name(i), human = _is_player(i),
			place = int(states[i * stride + 13]), wasted = states[i * stride + 10] > 0.5,
			wastes = int(cs[i * 8 + 4]), dealt = cs[i * 8 + 0], lap = laps[i] if i < laps.size() else -1, me = i == main.view.focus})
	# awards: only counted when someone actually earned them
	var awards := []
	var best := func(key: String, low: bool, min_ok: float):
		var pick = null
		for row in rows:
			var v: float = float(row[key])
			if (low and v < 0) or (not low and v < min_ok): continue
			if pick == null or (v < float(pick[key]) if low else v > float(pick[key])): pick = row
		return pick
	var tw = best.call("wastes", false, 1)
	if tw: awards.append(["TOP WASTER", tw.name, "%d wasted" % tw.wastes])
	var md = best.call("dealt", false, 1)
	if md: awards.append(["MOST DAMAGE", md.name, "%d%% dealt" % int(round(md.dealt))])
	var fl = best.call("lap", true, 0)
	if fl: awards.append(["FASTEST LAP", fl.name, UI.fmt_time(int(fl.lap))])
	# the profile counts only what was done to other players, not AI cars
	var pair: PackedFloat32Array = sim.pair_dealt()
	var n := grid.size()
	for row in rows:
		if row.me:
			var people := 0
			var downed := 0
			var hurt := 0.0
			for o in rows:
				if not o.human: continue
				people += 1
				if o.slot == row.slot: continue
				if int(cs[o.slot * 8 + 5]) == row.slot: downed += 1
				if row.slot * n + o.slot < pair.size(): hurt += pair[row.slot * n + o.slot]
			_podium_xp = main.profile.record(int(row.place), people, downed, hurt, bool(row.wasted))
			main.online.hub_refresh_stats()
	_podium_data = {rows = rows, awards = awards, title = {1: "YOU WON" if r.position == 1 else UI.ordinal(r.position).to_upper() + " PLACE", 2: "WASTED", 3: "YOU WIN", 4: "YOU LOST"}.get(r.kind, "RESULTS"),
		stage = main.assets.stages[main.race_opts.stage].name}
	main.end_race()
	net.race_over()
	podium()


func _is_player(slot: int) -> bool:
	for id in main.net.players:
		if int(main.net.players[id].get("grid", -1)) == slot: return true
	return false


func podium() -> void:
	var net: Net = main.net
	var d: Dictionary = _podium_data
	_studio("menu")
	var top := []
	for row in d.rows:
		if top.size() < 3: top.append({slot = row.car, name = row.name})
	showroom.show_podium(top)
	var c := _base()
	var dark := Color(0.03, 0.034, 0.04, 0.9)
	var shade := ColorRect.new()
	shade.color = Color(0, 0, 0, 0.55)
	shade.size = Vector2(4000, 150)
	shade.mouse_filter = Control.MOUSE_FILTER_IGNORE
	c.add_child(shade)
	var t := UI.label(d.title, UI.display(), 48)
	t.position = Vector2(96, 36)
	c.add_child(t)
	var sub := UI.label("%s      +%d XP   ·   LEVEL %d  %s" % [str(d.stage), _podium_xp, main.profile.level(), main.profile.title().to_upper()], UI.cond(), 20, UI.DIM)
	sub.position = Vector2(98, 96)
	c.add_child(sub)
	var panel := func(x: float, w: float, title: String) -> Control:
		var p := Panel.new()
		p.position = Vector2(x, 600)
		p.size = Vector2(w, 270)
		p.add_theme_stylebox_override("panel", UI.panel_style(dark))
		c.add_child(p)
		var h := UI.label(title, UI.body_bold(), 13, UI.DIM)
		h.position = Vector2(18, 10)
		p.add_child(h)
		return p
	# every player's numbers
	var res_p: Control = panel.call(96, 660, "RESULTS")
	var grid_l := GridContainer.new()
	grid_l.columns = 5
	grid_l.position = Vector2(18, 36)
	grid_l.add_theme_constant_override("h_separation", 26)
	grid_l.add_theme_constant_override("v_separation", 2)
	res_p.add_child(grid_l)
	for h in ["", "DRIVER", "WASTED", "DAMAGE", "BEST LAP"]:
		grid_l.add_child(UI.label(h, UI.body_bold(), 12, UI.FAINT))
	for row in d.rows:
		var col: Color = UI.ACCENT if row.me else (UI.INK if row.human else UI.DIM)
		grid_l.add_child(UI.label(UI.ordinal(int(row.place)), UI.cond(), 20, col))
		var nm := UI.label("%s  %s" % [row.name, "(wasted)" if row.wasted else ""], UI.cond(), 20, col)
		nm.custom_minimum_size = Vector2(250, 0)
		nm.clip_text = true
		grid_l.add_child(nm)
		grid_l.add_child(UI.label(str(row.wastes), UI.body(), 15, col))
		grid_l.add_child(UI.label("%d%%" % int(round(row.dealt)), UI.body(), 15, col))
		grid_l.add_child(UI.label(UI.fmt_time(int(row.lap)) if int(row.lap) >= 0 else "-", UI.body(), 15, col))
	# awards
	var aw_p: Control = panel.call(776, 400, "AWARDS")
	var aw := VBoxContainer.new()
	aw.position = Vector2(18, 38)
	aw.add_theme_constant_override("separation", 12)
	aw_p.add_child(aw)
	for a in d.awards:
		var v := VBoxContainer.new()
		v.add_theme_constant_override("separation", -2)
		v.add_child(UI.label(a[0], UI.body_bold(), 12, UI.GOLD))
		var h := HBoxContainer.new()
		h.add_theme_constant_override("separation", 12)
		h.add_child(UI.label(a[1], UI.cond(), 24, UI.INK))
		h.add_child(UI.label(a[2], UI.body(), 15, UI.DIM))
		v.add_child(h)
		aw.add_child(v)
	if d.awards.is_empty():
		aw.add_child(UI.label("No awards this time.", UI.body(), 15, UI.FAINT))
	# next
	var act_p: Control = panel.call(1196, 340, "")
	var votes_l := UI.label("", UI.cond(), 20, UI.ACCENT)
	votes_l.position = Vector2(30, 190)
	act_p.add_child(votes_l)
	var items := [["REMATCH", func(): net.vote_rematch(not net.players.get(net.peer.get_unique_id() if net.peer else -1, {}).get("rematch", false)), ""],
		["BACK TO ROOM", func():
			if net.peer and net.players.has(net.peer.get_unique_id()): net.vote_rematch(false)
			lobby(), ""]]
	var st := _menu(c, items, Vector2(1226, 626), 30)
	var refresh := func():
		if not is_instance_valid(votes_l): return
		if not net.in_room:
			multiplayer_menu("The room closed")
			return
		var v: Array = net.rematch_votes()
		var mine: bool = net.players.get(net.peer.get_unique_id() if net.peer else -1, {}).get("rematch", false)
		st.rows[0].t.text = "CANCEL REMATCH" if mine else "REMATCH"
		votes_l.text = "REMATCH VOTES  %d / %d" % [v[0], v[1]]
		var left: float = net.auto_left()
		if left >= 0.0 and v[0] < v[1]:
			votes_l.text += "\nNEXT RACE IN %d:%02d" % [int(left) / 60, int(left) % 60]
	var tm := Timer.new()
	tm.wait_time = 0.5
	tm.autostart = true
	tm.timeout.connect(refresh)
	c.add_child(tm)
	_mount(c, func(code): return _menu_keys(st, code))
	_guard()
	_lobby_refresh = refresh
	refresh.call()
	# test: --podiumshot=PATH saves the screen, then votes for a rematch
	var shot: String = main._arg(OS.get_cmdline_user_args(), "--podiumshot=")
	if shot != "":
		await get_tree().create_timer(4.0).timeout
		await RenderingServer.frame_post_draw
		get_viewport().get_texture().get_image().save_png(shot)
		print("podium shot saved")
		net.vote_rematch(true)


const DM_COLORS := [Color(1.0, 0.42, 0.1), Color(0.3, 0.8, 1.0), Color(0.55, 0.95, 0.35), Color(1.0, 0.4, 0.75), Color(1.0, 0.85, 0.25), Color(0.7, 0.55, 1.0), Color(0.85, 0.85, 0.85)]


## After a deathmatch: who was closest to winning, a health-over-time graph
## and each car's numbers.
func _dm_report(grid: PackedInt32Array, r: Dictionary) -> Control:
	var me: int = main.view.focus
	var cs: PackedFloat32Array = main.final_stats if not main.final_stats.is_empty() else sim.combat_stats()
	var n := grid.size()
	var v := VBoxContainer.new()
	v.add_theme_constant_override("separation", 10)
	var stat := func(i: int, k: int) -> float: return cs[i * 8 + k]
	# the headline: how close it was
	var line := ""
	if r.kind == 2:
		var killer := int(stat.call(me, 5))
		var best := -1
		for i in n:
			if i != me and stat.call(i, 7) > 0.0 and (best < 0 or stat.call(i, 7) < stat.call(best, 7)): best = i
		if killer >= 0:
			line = "%s finished you off with %d%% health left." % [sim.car_name(grid[killer]), int(round(stat.call(killer, 7)))]
		else:
			line = "You crashed out."
		if best >= 0 and best != killer:
			line += "  Closest to going down: %s at %d%%." % [sim.car_name(grid[best]), int(round(stat.call(best, 7)))]
	else:
		line = "You survived with %d%% health." % int(round(stat.call(me, 7)))
	v.add_child(UI.label(line, UI.cond(), 22, UI.ACCENT))
	var g := HealthGraph.new()
	g.custom_minimum_size = Vector2(900, 190)
	g.samples = main.health_log
	g.me = me
	g.colors = DM_COLORS
	v.add_child(g)
	var t := GridContainer.new()
	t.columns = 8
	t.add_theme_constant_override("h_separation", 22)
	t.add_theme_constant_override("v_separation", 4)
	for h in ["CAR", "HEALTH", "DEALT", "TAKEN", "CRASHES", "FIXES", "WASTED", "FATE"]:
		t.add_child(UI.label(h, UI.body_bold(), 12, UI.DIM))
	var order: Array = range(n)
	order.sort_custom(func(a, b):
		var da: float = stat.call(a, 6); var db: float = stat.call(b, 6)
		if (da < 0.0) != (db < 0.0): return da < 0.0
		return da > db if da >= 0.0 else stat.call(a, 7) > stat.call(b, 7))
	for i in order:
		var you: bool = i == me
		var font := UI.body_bold() if you else UI.body()
		var col: Color = DM_COLORS[i % DM_COLORS.size()]
		t.add_child(UI.label(("■  " + main._driver_name(i) + ("  (you)" if you else "")), font, 16, col))
		t.add_child(UI.label("%d%%" % int(round(stat.call(i, 7))), font, 16))
		t.add_child(UI.label("%d%%" % int(round(stat.call(i, 0))), font, 16))
		t.add_child(UI.label("%d%%" % int(round(stat.call(i, 1))), font, 16))
		t.add_child(UI.label("%d%%" % int(round(stat.call(i, 2))), font, 16))
		t.add_child(UI.label(str(int(stat.call(i, 3))), font, 16))
		t.add_child(UI.label(str(int(stat.call(i, 4))), font, 16))
		var died := int(stat.call(i, 6))
		var by := int(stat.call(i, 5))
		var fate := "standing"
		if died >= 0:
			fate = ("by " + sim.car_name(grid[by]) if by >= 0 else "crashed") + "  " + UI.fmt_time(maxi(0, died - 130))
		t.add_child(UI.label(fate, font, 16, UI.INK if died < 0 else UI.DIM))
	v.add_child(t)
	var key := UI.label("DEALT / TAKEN: damage to and from other cars, in % of the victim's health.  CRASHES: damage from walls and landings.", UI.body(), 12, UI.FAINT)
	v.add_child(key)
	return v


## Health of every car over the match; yours drawn thick.
class HealthGraph extends Control:
	var samples: Array = []
	var me := 0
	var colors: Array = []

	func _draw() -> void:
		var w := size.x
		var h := size.y
		draw_rect(Rect2(Vector2.ZERO, size), Color(1, 1, 1, 0.03))
		for y in [0.0, 0.5, 1.0]:
			draw_line(Vector2(0, h * y), Vector2(w, h * y), Color(1, 1, 1, 0.08), 1.0)
		draw_string(ThemeDB.fallback_font, Vector2(4, 12), "100%", HORIZONTAL_ALIGNMENT_LEFT, -1, 10, Color(1, 1, 1, 0.35))
		draw_string(ThemeDB.fallback_font, Vector2(4, h - 4), "0%", HORIZONTAL_ALIGNMENT_LEFT, -1, 10, Color(1, 1, 1, 0.35))
		if samples.size() < 2:
			return
		var n: int = (samples[0] as PackedFloat32Array).size()
		var order: Array = range(n)
		order.erase(me)
		order.append(me)
		for i in order:
			var pts := PackedVector2Array()
			for k in samples.size():
				var row: PackedFloat32Array = samples[k]
				pts.append(Vector2(w * k / float(samples.size() - 1), h * (1.0 - row[i] / 100.0)))
				if row[i] <= 0.0: break
			var c: Color = colors[i % colors.size()]
			if pts.size() >= 2:
				draw_polyline(pts, c if i == me else Color(c, 0.85), 3.5 if i == me else 1.8, true)


func _alive_count(states: PackedFloat32Array, stride: int, n: int) -> int:
	var a := 0
	for i in n:
		if states[i * stride + 10] < 0.5: a += 1
	return a


func _reveal(cars: Array, done: Callable) -> void:
	if cars.is_empty():
		done.call()
		return
	_studio("select")
	showroom.show_car(cars[0], 1)
	main.audio.play("ui_reward", 0.8)
	var c := _base()
	_shade(c, false)
	var k := UI.label("N E W   C A R", UI.cond(), 20, UI.ACCENT)
	k.set_anchors_preset(Control.PRESET_CENTER_TOP)
	k.position = Vector2(-150, 90)
	k.size = Vector2(300, 30)
	k.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	c.add_child(k)
	var n := UI.shadowed(UI.label(sim.car_name(cars[0]).to_upper(), UI.display(), 100))
	n.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
	n.position = Vector2(-700, -230)
	n.size = Vector2(1400, 110)
	n.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	c.add_child(n)
	var s := UI.label("You won it! It's in your garage now.", UI.body(), 18, UI.DIM)
	s.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
	s.position = Vector2(-400, -110)
	s.size = Vector2(800, 30)
	s.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	c.add_child(s)
	_mount(c, func(code):
		if code == KEY_ENTER or code == KEY_ESCAPE:
			_reveal(cars.slice(1), done)
			return true
		return false)
