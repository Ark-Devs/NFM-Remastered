## In-race HUD: position, lap, time, speed, power and damage, messages.
class_name Hud
extends Control

var pos_l: Label
var lap_l: Label
var wasted_l: Label       # how many of the other cars are wasted
var time_l: Label
var speed_l: Label
var power_fill: ColorRect
var dmg_fill: ColorRect
var msg_l: Label
var sub_l: Label
var toast_l: Label
var cam_l: Label
var players_l: Label
var deathmatch := false
var alive := 0
var dmg_l: Label
var low_l: Label          # LOW HEALTH warning at 10% and below
var fixes_left := -1      # deathmatch with limited repairs; -1 = no limit shown
var pointer: Pointer
var map: MapView
var msg_t := 0.0
var toast_t := 0.0
var cam_t := 0.0
var stunt_l: Label          # named stunts, wastes, bad landings: big, lower middle
var stunt_sub: Label
var stunt_t := 0.0
var coach_box: Control      # Coach Insano's remarks, bottom middle
var coach_l: Label
var coach_t := 0.0


func _init() -> void:
	UI.full_rect(self)
	mouse_filter = Control.MOUSE_FILTER_IGNORE
	pos_l = UI.shadowed(UI.label("", UI.display(), 64))
	pos_l.position = Vector2(28, 14)
	add_child(pos_l)
	lap_l = UI.shadowed(UI.label("", UI.cond(), 24))
	lap_l.position = Vector2(30, 86)
	add_child(lap_l)
	wasted_l = UI.shadowed(UI.label("", UI.cond(), 20, UI.DIM))
	wasted_l.position = Vector2(30, 116)
	add_child(wasted_l)
	time_l = UI.shadowed(UI.label("", UI.cond(), 32))
	time_l.set_anchors_preset(Control.PRESET_TOP_RIGHT)
	time_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	time_l.position = Vector2(-230, 18)
	time_l.size = Vector2(200, 40)
	add_child(time_l)
	players_l = UI.shadowed(UI.label("", UI.body_bold(), 15, UI.DIM))
	players_l.set_anchors_preset(Control.PRESET_TOP_RIGHT)
	players_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	players_l.position = Vector2(-330, 62)
	players_l.size = Vector2(300, 200)
	add_child(players_l)
	var bl := VBoxContainer.new()
	bl.set_anchors_preset(Control.PRESET_BOTTOM_LEFT)
	bl.position = Vector2(28, -112)
	bl.add_theme_constant_override("separation", 6)
	add_child(bl)
	bl.add_child(UI.shadowed(UI.label("POWER", UI.body_bold(), 13)))
	var pb := UI.bar(300, 6, Color(0.31, 0.71, 1.0))
	bl.add_child(pb[0]); power_fill = pb[1]
	dmg_l = UI.shadowed(UI.label("HEALTH", UI.body_bold(), 13))
	bl.add_child(dmg_l)
	var db := UI.bar(300, 6, UI.GOOD)
	bl.add_child(db[0]); dmg_fill = db[1]
	speed_l = UI.shadowed(UI.label("0", UI.display(), 72))
	speed_l.set_anchors_preset(Control.PRESET_BOTTOM_RIGHT)
	speed_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	speed_l.position = Vector2(-260, -120)
	speed_l.size = Vector2(230, 80)
	add_child(speed_l)
	var unit := UI.shadowed(UI.label("KM/H", UI.body_bold(), 13, UI.DIM))
	unit.set_anchors_preset(Control.PRESET_BOTTOM_RIGHT)
	unit.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	unit.position = Vector2(-130, -40)
	unit.size = Vector2(100, 20)
	add_child(unit)
	msg_l = UI.shadowed(UI.label("", UI.display(), 96), 0.7)
	msg_l.set_anchors_preset(Control.PRESET_CENTER_TOP)
	msg_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	msg_l.position = Vector2(-600, 200)
	msg_l.size = Vector2(1200, 110)
	_outline(msg_l, 14)
	add_child(msg_l)
	sub_l = UI.shadowed(UI.label("", UI.body_bold(), 18))
	sub_l.set_anchors_preset(Control.PRESET_CENTER_TOP)
	sub_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	sub_l.position = Vector2(-600, 310)
	sub_l.size = Vector2(1200, 30)
	_outline(sub_l, 7)
	add_child(sub_l)
	stunt_l = UI.label("", UI.display(), 50)
	stunt_l.set_anchors_preset(Control.PRESET_CENTER)
	stunt_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	stunt_l.position = Vector2(-700, -118)
	stunt_l.size = Vector2(1400, 64)
	_outline(stunt_l, 12)
	stunt_l.modulate.a = 0.0
	add_child(stunt_l)
	stunt_sub = UI.label("", UI.display_bold(), 24, UI.GOLD)
	stunt_sub.set_anchors_preset(Control.PRESET_CENTER)
	stunt_sub.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	stunt_sub.position = Vector2(-700, -58)
	stunt_sub.size = Vector2(1400, 30)
	_outline(stunt_sub, 7)
	stunt_sub.modulate.a = 0.0
	add_child(stunt_sub)
	coach_box = HBoxContainer.new()
	coach_box.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
	coach_box.position = Vector2(-380, -96)
	coach_box.size = Vector2(760, 60)
	coach_box.alignment = BoxContainer.ALIGNMENT_CENTER
	coach_box.add_theme_constant_override("separation", 12)
	coach_box.modulate.a = 0.0
	var badge := UI.label("COACH
INSANO", UI.display(), 17, UI.ACCENT)
	badge.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	badge.add_theme_constant_override("line_spacing", -6)
	_outline(badge, 6)
	coach_box.add_child(badge)
	coach_l = UI.label("", UI.display_bold(), 24)
	coach_l.autowrap_mode = TextServer.AUTOWRAP_WORD_SMART
	coach_l.custom_minimum_size = Vector2(560, 0)
	_outline(coach_l, 7)
	coach_box.add_child(coach_l)
	add_child(coach_box)
	toast_l = UI.shadowed(UI.label("", UI.cond(), 24))
	toast_l.set_anchors_preset(Control.PRESET_CENTER_TOP)
	toast_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	toast_l.position = Vector2(-400, 140)
	toast_l.size = Vector2(800, 34)
	add_child(toast_l)
	pointer = Pointer.new()
	pointer.set_anchors_preset(Control.PRESET_CENTER_TOP)
	pointer.position = Vector2(-150, 14)
	pointer.size = Vector2(300, 110)
	add_child(pointer)
	map = MapView.new()
	map.set_anchors_preset(Control.PRESET_BOTTOM_LEFT)
	map.position = Vector2(28, -330)
	map.size = Vector2(196, 196)
	map.visible = false
	add_child(map)
	low_l = UI.shadowed(UI.label("LOW HEALTH", UI.display(), 44, UI.BAD), 0.8)
	low_l.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
	low_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	low_l.position = Vector2(-300, -150)
	low_l.size = Vector2(600, 60)
	low_l.visible = false
	add_child(low_l)
	cam_l = UI.shadowed(UI.label("", UI.body_bold(), 15, UI.DIM))
	cam_l.set_anchors_preset(Control.PRESET_CENTER_BOTTOM)
	cam_l.horizontal_alignment = HORIZONTAL_ALIGNMENT_CENTER
	cam_l.position = Vector2(-200, -40)
	cam_l.size = Vector2(400, 24)
	add_child(cam_l)


## Big banner words with no box behind them: a dark outline keeps them
## readable over a bright sky or sand.
static func _outline(l: Label, size: int) -> void:
	l.add_theme_constant_override("outline_size", size)
	l.add_theme_color_override("font_outline_color", Color(0.02, 0.02, 0.03, 0.85))


func message(text: String, sub := "", secs := 1.2) -> void:
	msg_l.text = text.to_upper()
	# long stage names shrink to fit instead of running off the screen
	msg_l.add_theme_font_size_override("font_size", 96 if text.length() <= 22 else maxi(50, 96 * 22 / text.length()))
	sub_l.text = sub.to_upper()
	msg_t = secs
	msg_l.modulate.a = 1.0
	sub_l.modulate.a = 1.0
	msg_l.scale = Vector2(1.15, 1.15)
	msg_l.pivot_offset = msg_l.size / 2.0


## Deathmatch: a health bar per opponent (top right), coloured like the
## results graph. rows: [{index, name, color}]
var rival_rows: Array = []


func setup_rivals(rows: Array) -> void:
	var box := VBoxContainer.new()
	box.set_anchors_preset(Control.PRESET_TOP_RIGHT)
	box.position = Vector2(-300, 70)
	box.add_theme_constant_override("separation", 6)
	add_child(box)
	for r in rows:
		var l := UI.shadowed(UI.label(r.name.to_upper(), UI.body_bold(), 13, r.color))
		box.add_child(l)
		var b := UI.bar(270, 6, r.color)
		box.add_child(b[0])
		rival_rows.append({index = r.index, label = l, fill = b[1], name = r.name.to_upper(), color = r.color})


func update_rivals(state: PackedFloat32Array, stride: int) -> void:
	for r in rival_rows:
		var o: int = r.index * stride
		var dead: bool = state[o + 10] > 0.5
		var hp := 0.0 if dead else 1.0 - clampf(state[o + 9], 0.0, 1.0)
		r.fill.size.x = 270.0 * hp
		r.label.text = r.name + ("   WASTED" if dead else "   %d%%" % int(round(hp * 100.0)))
		r.label.modulate.a = 0.4 if dead else 1.0


## The original's guide arrow: points at the next checkpoint or at a car.
class Pointer extends Control:
	var angle := 0.0
	var text := ""
	var color := Color.WHITE
	var on := false

	func _init() -> void:
		mouse_filter = Control.MOUSE_FILTER_IGNORE

	func show_target(a: float, t: String, c: Color) -> void:
		angle = a; text = t; color = c; on = true
		queue_redraw()

	func hide_target() -> void:
		if on:
			on = false
			queue_redraw()

	func _draw() -> void:
		if not on: return
		var c := Vector2(size.x / 2.0, 38)
		var r := Transform2D(angle, c)
		# the same dart as your marker on the radar, tip first
		var tip := [Vector2(0, -28), Vector2(20, 22), Vector2(0, 11), Vector2(-20, 22)]
		var pts := PackedVector2Array()
		var shadow := PackedVector2Array()
		for p in tip:
			pts.append(r * p)
			shadow.append(r * p + Vector2(2, 3))
		draw_colored_polygon(shadow, Color(0, 0, 0, 0.45))
		draw_colored_polygon(pts, color)
		pts.append(pts[0])
		draw_polyline(pts, Color(0, 0, 0, 0.6), 1.5, true)
		var f := ThemeDB.fallback_font
		var w := f.get_string_size(text, HORIZONTAL_ALIGNMENT_LEFT, -1, 14).x
		draw_string(f, Vector2((size.x - w) / 2.0 + 1, 92), text, HORIZONTAL_ALIGNMENT_LEFT, -1, 14, Color(0, 0, 0, 0.7))
		draw_string(f, Vector2((size.x - w) / 2.0, 91), text, HORIZONTAL_ALIGNMENT_LEFT, -1, 14, Color(1, 1, 1, 0.92))


## Round radar: centred on you and turning with your view, the road as a
## line, rivals as dots (on the rim when out of range).
class MapView extends Control:
	const RANGE := 260.0          # metres from the centre to the rim
	var path := PackedVector2Array()
	var me := Vector2.ZERO
	var heading := 0.0
	var cars: Array = []           # [{p: Vector2, color}]
	var next_cp := Vector2.INF

	func _init() -> void:
		mouse_filter = Control.MOUSE_FILTER_IGNORE

	func setup(p_path: PackedVector2Array) -> void:
		path = p_path

	func show_at(p_me: Vector2, p_heading: float, p_cars: Array, p_next: Vector2) -> void:
		me = p_me; heading = p_heading; cars = p_cars; next_cp = p_next
		queue_redraw()

	func _to_radar(w: Vector2) -> Vector2:
		return (w - me).rotated(-heading) * (size.x * 0.5 / RANGE)

	func _draw() -> void:
		var r := size.x * 0.5
		var c := Vector2(r, r)
		draw_circle(c, r, Color(0.02, 0.025, 0.03, 0.62))
		draw_set_transform(c)
		# road: dark casing, then the lighter surface, clipped to the disc
		var pts := PackedVector2Array()
		for p in path:
			pts.append(_to_radar(p))
		if pts.size() > 1:
			pts.append(pts[0])
			for k in pts.size() - 1:
				var s := _clip(pts[k], pts[k + 1], r - 3.0)
				if s.size() == 2: draw_line(s[0], s[1], Color(0, 0, 0, 0.55), 9.0, true)
			for k in pts.size() - 1:
				var s := _clip(pts[k], pts[k + 1], r - 3.0)
				if s.size() == 2: draw_line(s[0], s[1], Color(0.78, 0.8, 0.84, 0.9), 5.0, true)
		if next_cp != Vector2.INF:
			var q := _to_radar(next_cp)
			if q.length() > r - 8.0: q = q.normalized() * (r - 8.0)
			draw_circle(q, 5.5, Color(0, 0, 0, 0.6))
			draw_circle(q, 4.0, Color(1.0, 0.85, 0.3))
		for car in cars:
			var q := _to_radar(car.p)
			if q.length() > r - 7.0:
				# out of range: a small pip on the rim pointing at it
				var d := q.normalized()
				var e := d * (r - 7.0)
				draw_colored_polygon(PackedVector2Array([e + d * 5.0, e + d.orthogonal() * 4.0 - d * 3.0, e - d.orthogonal() * 4.0 - d * 3.0]), car.color)
			else:
				draw_circle(q, 5.5, Color(0, 0, 0, 0.7))
				draw_circle(q, 4.0, car.color)
		# you: an arrow pointing the way you look
		var me_pts := PackedVector2Array([Vector2(0, -9), Vector2(6.5, 7), Vector2(0, 3.5), Vector2(-6.5, 7)])
		draw_colored_polygon(me_pts, Color(1.0, 0.42, 0.1))
		me_pts.append(me_pts[0])
		draw_polyline(me_pts, Color(0, 0, 0, 0.7), 1.2, true)
		draw_set_transform(Vector2.ZERO)
		draw_arc(c, r - 1.0, 0.0, TAU, 64, Color(1, 1, 1, 0.22), 1.5, true)

	## The part of segment a-b inside a circle of radius rad around the centre.
	func _clip(a: Vector2, b: Vector2, rad: float) -> PackedVector2Array:
		var d := b - a
		var A := d.dot(d)
		if A < 0.0001:
			return PackedVector2Array([a, b]) if a.length() <= rad else PackedVector2Array()
		var B := 2.0 * a.dot(d)
		var C := a.dot(a) - rad * rad
		var disc := B * B - 4.0 * A * C
		if disc <= 0.0: return PackedVector2Array()
		var sq := sqrt(disc)
		var t0 := maxf(0.0, (-B - sq) / (2.0 * A))
		var t1 := minf(1.0, (-B + sq) / (2.0 * A))
		if t0 >= t1: return PackedVector2Array()
		return PackedVector2Array([a + d * t0, a + d * t1])


## A small microphone, drawn, shown while your mic is sending.
class MicIcon extends Control:
	func _draw() -> void:
		var c := UI.ACCENT
		draw_rect(Rect2(6, 0, 8, 13), c, true)
		draw_circle(Vector2(10, 0), 4.0, c)
		draw_circle(Vector2(10, 13), 4.0, c)
		draw_arc(Vector2(10, 11), 8.0, 0.0, PI, 16, c, 2.0)
		draw_line(Vector2(10, 19), Vector2(10, 24), c, 2.0)
		draw_line(Vector2(5, 24), Vector2(15, 24), c, 2.0)


var mic: MicIcon


func show_mic(on: bool) -> void:
	if mic == null:
		mic = MicIcon.new()
		mic.set_anchors_preset(Control.PRESET_BOTTOM_LEFT)
		mic.position = Vector2(370, -112)
		mic.size = Vector2(20, 26)
		add_child(mic)
	mic.visible = on


## Hold Tab: everyone in the race, leader first.
var board: PanelContainer
var board_rows: VBoxContainer


func show_board(rows: Array) -> void:
	if board == null:
		board = PanelContainer.new()
		board.add_theme_stylebox_override("panel", UI.panel_style(Color(0.03, 0.034, 0.04, 0.9), 3))
		board_rows = VBoxContainer.new()
		board_rows.add_theme_constant_override("separation", 4)
		board.add_child(board_rows)
		add_child(board)
	board.visible = true
	for ch in board_rows.get_children(): ch.free()
	var widths := [60, 260, 190, 110, 90, 80]
	var head := ["", "DRIVER", "CAR", "STATUS", "WASTED", "PING"]
	var add_row := func(cells: Array, font: Font, size: int, col: Color):
		var h := HBoxContainer.new()
		h.add_theme_constant_override("separation", 12)
		for i in cells.size():
			var l := UI.label(str(cells[i]), font, size, col if i != 3 or cells[i] != "WASTED" else UI.BAD)
			l.custom_minimum_size = Vector2(widths[i], 0)
			l.clip_text = true
			h.add_child(l)
		board_rows.add_child(h)
	add_row.call(head, UI.body_bold(), 12, UI.FAINT)
	for r in rows:
		add_row.call([r.place, r.name, r.car, r.status, r.wastes, r.ping], UI.cond(), 21, UI.ACCENT if r.me else UI.INK)
	board.reset_size()
	board.position = (get_viewport_rect().size - board.size) / 2.0


func hide_board() -> void:
	if board: board.visible = false


## Bottom left, above the power bar: who's talking on voice chat.
var speak_l: Label


func speaking(names: Array) -> void:
	if speak_l == null:
		speak_l = UI.shadowed(UI.label("", UI.cond(), 18, UI.ACCENT))
		speak_l.set_anchors_preset(Control.PRESET_BOTTOM_LEFT)
		speak_l.position = Vector2(34, -150)
		add_child(speak_l)
	speak_l.text = "" if names.is_empty() else "SPEAKING  " + "   ".join(names)


## Top right, under the clock: who wasted whom, newest at the top. Each line
## stays a few seconds, then fades.
var feed_box: VBoxContainer


func feed(text: String, mine := false) -> void:
	if feed_box == null:
		feed_box = VBoxContainer.new()
		feed_box.set_anchors_preset(Control.PRESET_TOP_RIGHT)
		feed_box.position = Vector2(-560, 70)
		feed_box.custom_minimum_size = Vector2(530, 0)
		feed_box.add_theme_constant_override("separation", 2)
		add_child(feed_box)
	var l := UI.shadowed(UI.label(text, UI.cond(), 21, UI.ACCENT if mine else UI.INK))
	l.horizontal_alignment = HORIZONTAL_ALIGNMENT_RIGHT
	l.custom_minimum_size = Vector2(530, 0)
	feed_box.add_child(l)
	feed_box.move_child(l, 0)
	while feed_box.get_child_count() > 4:
		feed_box.get_child(feed_box.get_child_count() - 1).free()
	var tw := l.create_tween()
	tw.tween_interval(5.0)
	tw.tween_property(l, "modulate:a", 0.0, 0.8)
	tw.tween_callback(l.queue_free)


## A big lower-middle callout: a named stunt, a waste, a bad landing.
func stunt(text: String, sub := "", color := UI.INK, secs := 1.8) -> void:
	stunt_l.text = text.to_upper()
	stunt_l.add_theme_color_override("font_color", color)
	stunt_sub.text = sub.to_upper()
	stunt_t = secs
	stunt_l.modulate.a = 1.0
	stunt_sub.modulate.a = 1.0
	stunt_l.pivot_offset = stunt_l.size / 2.0
	stunt_l.scale = Vector2(1.35, 1.35)


## Career boss intro: letterbox bars slide in, the race HUD steps aside and a
## big name card sits low on the left. cinema("", "", "") puts it all back.
var _cine: Control
var _cine_hidden: Array = []


func cinema(kicker: String, name: String, sub: String) -> void:
	if name == "":
		if _cine and is_instance_valid(_cine):
			var c := _cine
			_cine = null
			var tw := c.create_tween()
			tw.tween_property(c, "modulate:a", 0.0, 0.3)
			tw.tween_callback(c.queue_free)
		for n in _cine_hidden:
			if is_instance_valid(n): n.visible = true
		_cine_hidden = []
		return
	for n in get_children():
		if n is CanvasItem and n.visible:
			n.visible = false
			_cine_hidden.append(n)
	_cine = Control.new()
	UI.full_rect(_cine)
	_cine.mouse_filter = Control.MOUSE_FILTER_IGNORE
	add_child(_cine)
	var h := get_viewport_rect().size.y * 0.11
	for top in [true, false]:
		var bar := ColorRect.new()
		bar.color = Color(0, 0, 0, 0.92)
		bar.set_anchors_preset(Control.PRESET_TOP_WIDE if top else Control.PRESET_BOTTOM_WIDE)
		bar.custom_minimum_size = Vector2(0, h)
		bar.size.y = h
		bar.position.y = -h if top else get_viewport_rect().size.y
		_cine.add_child(bar)
		bar.create_tween().tween_property(bar, "position:y", 0.0 if top else get_viewport_rect().size.y - h, 0.35).set_trans(Tween.TRANS_CUBIC).set_ease(Tween.EASE_OUT)
	var card := VBoxContainer.new()
	card.set_anchors_preset(Control.PRESET_BOTTOM_LEFT)
	card.position = Vector2(64, -h - 230)
	card.add_theme_constant_override("separation", -4)
	var k := UI.label(kicker, UI.display(), 30, UI.ACCENT)
	_outline(k, 8)
	card.add_child(k)
	var n := UI.label(name, UI.display(), 96 if name.length() <= 16 else 64)
	_outline(n, 14)
	card.add_child(n)
	var s := UI.label(sub, UI.display_bold(), 24, UI.DIM)
	_outline(s, 7)
	card.add_child(s)
	_cine.add_child(card)
	card.modulate.a = 0.0
	card.position.x = -200
	var tw2 := card.create_tween().set_parallel()
	tw2.tween_property(card, "position:x", 64.0, 0.5).set_delay(0.25).set_trans(Tween.TRANS_CUBIC).set_ease(Tween.EASE_OUT)
	tw2.tween_property(card, "modulate:a", 1.0, 0.4).set_delay(0.25)


## Coach Insano says something (bottom middle, a few seconds).
func coach(text: String, secs := 4.0) -> void:
	if text == "": return
	coach_l.text = text
	coach_t = secs
	coach_box.modulate.a = 1.0


func toast(text: String, secs := 1.6) -> void:
	toast_l.text = text
	toast_t = secs
	toast_l.modulate.a = 1.0


func camera(name: String) -> void:
	cam_l.text = "Camera: " + name
	cam_t = 1.5


func update_hud(dt: float, state: PackedFloat32Array, o: int, info: PackedInt32Array, nplayers: int) -> void:
	var nsp := maxi(1, info[7])
	var nlaps := info[8]
	var pos := int(state[o + 13])
	if deathmatch:
		pos_l.text = "%d LEFT" % alive
		lap_l.text = "LAST CAR STANDING"
	else:
		pos_l.text = "%s" % UI.ordinal(pos) if nplayers > 1 else ""
		var lap := mini(nlaps, int(state[o + 12]) + 1)
		lap_l.text = "LAP %d / %d" % [lap, nlaps]
	# the other cars wasted so far, out of how many there are
	var stride := state.size() / maxi(1, nplayers)
	var down := 0
	for i in nplayers:
		if i * stride != o and state[i * stride + 10] > 0.5: down += 1
	wasted_l.visible = nplayers > 1 and not deathmatch
	wasted_l.text = "WASTED  %d / %d" % [down, nplayers - 1]
	wasted_l.add_theme_color_override("font_color", UI.ACCENT if down > 0 else UI.DIM)
	time_l.text = UI.fmt_time(info[6])
	speed_l.text = str(int(absf(state[o + 7]) * 0.6))
	var pw := clampf(state[o + 8] / 98.0, 0.0, 1.0)
	power_fill.size.x = 300.0 * pw
	power_fill.color = Color(1.0, 0.82, 0.24) if pw >= 0.99 else Color(0.31, 0.71, 1.0)
	# health runs 100 -> 0 (0 once wasted); the bar empties and turns red
	var hp := 0.0 if state[o + 10] > 0.5 else 1.0 - clampf(state[o + 9], 0.0, 1.0)
	var hp_pct := int(ceil(hp * 100.0))
	dmg_l.text = "HEALTH   %d%%" % hp_pct + ("" if fixes_left < 0 else ("   ·   NO FIXES" if fixes_left == 0 else "   ·   FIXES LEFT %d" % fixes_left))
	dmg_fill.size.x = 300.0 * hp
	dmg_fill.color = UI.BAD.lerp(UI.GOOD, clampf((hp - 0.1) / 0.6, 0.0, 1.0))
	low_l.visible = hp > 0.0 and hp_pct <= 10
	if low_l.visible:
		low_l.modulate.a = 0.55 + 0.45 * sin(Time.get_ticks_msec() / 110.0)
	if msg_t > 0.0:
		msg_t -= dt
		msg_l.scale = msg_l.scale.lerp(Vector2.ONE, minf(1.0, dt * 12.0))
		if msg_t < 0.3:
			msg_l.modulate.a = maxf(0.0, msg_t / 0.3)
			sub_l.modulate.a = msg_l.modulate.a
	if stunt_t > 0.0:
		stunt_t -= dt
		stunt_l.scale = stunt_l.scale.lerp(Vector2.ONE, minf(1.0, dt * 14.0))
		if stunt_t < 0.4:
			stunt_l.modulate.a = maxf(0.0, stunt_t / 0.4)
			stunt_sub.modulate.a = stunt_l.modulate.a
	if coach_t > 0.0:
		coach_t -= dt
		if coach_t < 0.5:
			coach_box.modulate.a = maxf(0.0, coach_t / 0.5)
	if toast_t > 0.0:
		toast_t -= dt
		if toast_t < 0.3:
			toast_l.modulate.a = maxf(0.0, toast_t / 0.3)
	if cam_t > 0.0:
		cam_t -= dt
		cam_l.modulate.a = clampf(cam_t, 0.0, 1.0)
