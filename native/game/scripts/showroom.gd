## The car studio behind the menus: dark cyclorama, turntable, long softbox
## highlights; cars slide on and off, locked cars stand as silhouettes.
class_name Showroom
extends Node3D

var sim: NfmSim
var camera := Camera3D.new()
var turntable := Node3D.new()
var cars: Array = []      # [{view, x0, x1, t, leaving}]
var turn := 0.6
var mode := "select"
var slot := -1
var locked := false
var size := 3.0
var height := 1.5         # tallest point of the car, so trucks stay in frame
var silhouette: ShaderMaterial
var lineup: Array = []    # the room's cars side by side: [Node3D]
var _lineup_key := ""
var lineup_light := DirectionalLight3D.new()
var env: Environment


func setup(p_sim: NfmSim) -> void:
	sim = p_sim
	var e := Environment.new()
	e.background_mode = Environment.BG_COLOR
	e.background_color = Color(0.024, 0.027, 0.031)
	e.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
	e.ambient_light_color = Color(0.5, 0.55, 0.65)
	e.ambient_light_energy = 0.12
	e.tonemap_mode = Environment.TONE_MAPPER_AGX
	e.glow_enabled = true
	e.glow_intensity = 0.3
	e.ssao_enabled = true
	e.fog_enabled = true
	e.fog_mode = Environment.FOG_MODE_DEPTH
	e.fog_light_color = Color(0.024, 0.027, 0.031)
	e.fog_depth_begin = 16.0
	e.fog_depth_end = 42.0
	# reflections: a dark room with three long strip lights overhead
	var sky := Sky.new()
	var sm := ShaderMaterial.new()
	var sh := Shader.new()
	sh.code = """
shader_type sky;
void sky() {
	vec3 d = EYEDIR;
	float strips = 0.0;
	if (d.y > 0.35) {
		vec2 p = d.xz / d.y;
		for (int i = -1; i <= 1; i++) {
			strips += (1.0 - smoothstep(0.05, 0.08, abs(p.x - float(i) * 0.45))) * step(abs(p.y), 1.6);
		}
	}
	float side = smoothstep(0.75, 0.9, abs(d.x)) * smoothstep(0.5, 0.0, abs(d.y - 0.15));
	COLOR = vec3(0.012, 0.013, 0.016) + vec3(1.0) * strips * 4.0 + vec3(0.6, 0.65, 0.75) * side * 1.2;
}
"""
	sm.shader = sh
	sky.sky_material = sm
	e.sky = sky
	e.reflected_light_source = Environment.REFLECTION_SOURCE_SKY
	env = e
	var we := WorldEnvironment.new()
	we.environment = e
	add_child(we)
	var floor := MeshInstance3D.new()
	var pm := PlaneMesh.new()
	pm.size = Vector2(80, 80)
	floor.mesh = pm
	var fm := StandardMaterial3D.new()
	fm.albedo_color = Color(0.012, 0.013, 0.015)
	fm.roughness = 0.55
	fm.clearcoat_enabled = true
	fm.clearcoat = 0.25
	floor.material_override = fm
	add_child(floor)
	var table := MeshInstance3D.new()
	var cyl := CylinderMesh.new()
	cyl.top_radius = 4.6; cyl.bottom_radius = 4.64; cyl.height = 0.07; cyl.radial_segments = 96
	table.mesh = cyl
	table.position.y = 0.035
	var tm := StandardMaterial3D.new()
	tm.albedo_color = Color(0.1, 0.105, 0.12)
	tm.metallic = 0.8
	tm.roughness = 0.38
	table.material_override = tm
	add_child(table)
	var seam := MeshInstance3D.new()
	var torus := TorusMesh.new()
	torus.inner_radius = 4.66; torus.outer_radius = 4.685; torus.rings = 96; torus.ring_segments = 6
	seam.mesh = torus
	seam.position.y = 0.006
	var sem := StandardMaterial3D.new()
	sem.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
	sem.albedo_color = Color(0.6, 0.64, 0.71)
	seam.material_override = sem
	add_child(seam)
	var key := SpotLight3D.new()
	key.position = Vector3(3, 13, 5)
	key.look_at_from_position(key.position, Vector3.ZERO, Vector3.UP)
	key.spot_range = 40.0
	key.spot_angle = 26.0
	key.light_energy = 9.0
	key.shadow_enabled = true
	add_child(key)
	var rim := DirectionalLight3D.new()
	rim.rotation_degrees = Vector3(-25, -140, 0)
	rim.light_color = Color(0.79, 0.84, 1.0)
	rim.light_energy = 0.9
	add_child(rim)
	turntable.position.y = 0.07
	add_child(turntable)
	# soft light across the whole row when a room's cars are lined up
	lineup_light.rotation_degrees = Vector3(-38, 18, 0)
	lineup_light.light_energy = 1.1
	lineup_light.light_color = Color(0.9, 0.93, 1.0)
	lineup_light.visible = false
	add_child(lineup_light)
	add_child(camera)
	silhouette = ShaderMaterial.new()
	var ssh := Shader.new()
	ssh.code = "shader_type spatial; render_mode cull_disabled; void fragment() { ALBEDO = vec3(0.01); ROUGHNESS = 0.3; METALLIC = 0.4; }"
	silhouette.shader = ssh


func show_car(p_slot: int, dir := 0, p_locked := false) -> void:
	_clear_lineup()
	if mode == "lineup":
		mode = "menu"
	for c in cars:
		if not c.leaving:
			c.leaving = true
			c.x0 = c.view.position.x
			c.x1 = -dir * 16.0
			c.t = 0.0 if dir != 0 else 1.0
	var v := CarView.new()
	v.setup(sim, p_slot, -1)
	var g := sim.car_grat(p_slot)
	v.update_pose(Transform3D(Basis(), Vector3(0, g * (1.0 / 50.0), 0)), 0, 0, 0, Vector3.ZERO)
	# centre the footprint on the turntable
	var aabb := AABB()
	for mi in v.body.get_children():
		if mi is MeshInstance3D:
			aabb = mi.get_aabb() if aabb.size == Vector3.ZERO else aabb.merge(mi.get_aabb())
	var holder := Node3D.new()
	holder.add_child(v)
	v.position = Vector3(-aabb.get_center().x, g / 50.0, -aabb.get_center().z)
	if p_locked:
		for mi in v.body.get_children():
			if mi is MeshInstance3D:
				mi.material_override = silhouette
	holder.position.x = dir * 16.0
	turntable.add_child(holder)
	cars.append({view = holder, x0 = dir * 16.0, x1 = 0.0, t = 0.0 if dir != 0 else 1.0, leaving = false})
	size = maxf(aabb.size.x, aabb.size.z) * 0.5
	height = aabb.size.y
	slot = p_slot
	locked = p_locked


## A room's cars parked side by side, each with its driver's name above it
## and a tag (HOST, READY). entries: [{slot, name, tag}]
func show_lineup(entries: Array) -> void:
	var key := str(entries)
	if key == _lineup_key and mode == "lineup":
		return
	_lineup_key = key
	for c in cars:
		c.view.queue_free()
	cars.clear()
	slot = -1
	_clear_lineup()
	_lineup_key = key
	mode = "lineup"
	lineup_light.visible = true
	env.fog_depth_begin = 34.0 # a long row stays clear
	env.fog_depth_end = 70.0
	var n := entries.size()
	# names grow with the row so they read at the same size from further back
	var grow := _lineup_dist(n) / 19.0
	for i in n:
		var e: Dictionary = entries[i]
		var v := CarView.new()
		v.setup(sim, int(e.slot), -1)
		var g := sim.car_grat(int(e.slot))
		v.update_pose(Transform3D(Basis(), Vector3(0, g * (1.0 / 50.0), 0)), 0, 0, 0, Vector3.ZERO)
		var aabb := AABB()
		for mi in v.body.get_children():
			if mi is MeshInstance3D:
				aabb = mi.get_aabb() if aabb.size == Vector3.ZERO else aabb.merge(mi.get_aabb())
		var holder := Node3D.new()
		holder.add_child(v)
		v.position = Vector3(-aabb.get_center().x, g / 50.0, -aabb.get_center().z)
		var off := i - (n - 1) / 2.0
		holder.position = Vector3(off * _gap(n), 0.07, -absf(off) * 0.7)
		holder.rotation.y = PI + 0.55 # front three-quarters toward the camera
		var name_l := _tag3d(str(e.name), int(46 * grow), Color(0.953, 0.949, 0.937))
		var top: float = g / 50.0 + aabb.end.y # the roof, wherever the body sits
		name_l.position = Vector3(0, top + 0.8 * grow, 0)
		holder.add_child(name_l)
		if str(e.get("tag", "")) != "":
			var tag_l := _tag3d(str(e.tag), int(30 * grow), Color(0.35, 0.82, 0.48) if e.tag == "READY" else Color(0.914, 0.769, 0.416))
			tag_l.position = Vector3(0, top + 0.32 * grow, 0)
			holder.add_child(tag_l)
		add_child(holder)
		lineup.append(holder)


const LINEUP_GAP := 5.3


## Small rooms get more room between cars; big ones pack tighter.
func _gap(n: int) -> float:
	return 6.8 if n <= 3 else LINEUP_GAP


func _lineup_dist(n: int) -> float:
	return clampf(maxf(2.0, n) * _gap(n) * 0.95, 19.0, 30.0)


func _tag3d(text: String, size: int, col: Color) -> Label3D:
	var l := Label3D.new()
	l.text = text
	l.font = UI.cond()
	l.font_size = size
	l.pixel_size = 0.008
	l.modulate = col
	l.outline_size = 10
	l.outline_modulate = Color(0, 0, 0, 0.85)
	l.billboard = BaseMaterial3D.BILLBOARD_ENABLED
	l.no_depth_test = true
	return l


## After an online race: the top three on dark stepped plinths, winner in the
## middle and highest, each with the driver's name and their place cut into
## the plinth. entries: [{slot, name}] in finishing order (up to 3).
func show_podium(entries: Array) -> void:
	for c in cars:
		c.view.queue_free()
	cars.clear()
	slot = -1
	_clear_lineup()
	mode = "podium"
	lineup_light.visible = true
	var spots := [[0.0, 1.6], [-6.4, 1.15], [6.4, 0.85]] # x, plinth height: 1st, 2nd, 3rd
	var plinth_mat := StandardMaterial3D.new()
	plinth_mat.albedo_color = Color(0.05, 0.053, 0.06)
	plinth_mat.metallic = 0.7
	plinth_mat.roughness = 0.35
	for i in mini(3, entries.size()):
		var e: Dictionary = entries[i]
		var x: float = spots[i][0]
		var h: float = spots[i][1]
		var plinth := MeshInstance3D.new()
		var box := BoxMesh.new()
		box.size = Vector3(5.4, h, 4.6)
		plinth.mesh = box
		plinth.material_override = plinth_mat
		plinth.position = Vector3(x, h * 0.5, 0)
		add_child(plinth)
		lineup.append(plinth)
		# a thin lit edge along the top front: orange for the winner, steel for the rest
		var edge := MeshInstance3D.new()
		var eb := BoxMesh.new()
		eb.size = Vector3(5.4, 0.03, 0.03)
		edge.mesh = eb
		var em := StandardMaterial3D.new()
		em.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
		em.albedo_color = Color(1.0, 0.353, 0.122) if i == 0 else Color(0.6, 0.64, 0.71)
		edge.material_override = em
		edge.position = Vector3(x, h, 2.3)
		add_child(edge)
		lineup.append(edge)
		var place := Label3D.new()
		place.text = ["1ST", "2ND", "3RD"][i]
		place.font = UI.display()
		place.font_size = 96
		place.pixel_size = minf(0.01, h * 0.0085) # the place fits its plinth
		place.modulate = Color(1.0, 0.353, 0.122) if i == 0 else Color(0.953, 0.949, 0.937, 0.7)
		place.position = Vector3(x, h * 0.5, 2.31)
		add_child(place)
		lineup.append(place)
		var v := CarView.new()
		v.setup(sim, int(e.slot), -1)
		var g := sim.car_grat(int(e.slot))
		v.update_pose(Transform3D(Basis(), Vector3(0, g * (1.0 / 50.0), 0)), 0, 0, 0, Vector3.ZERO)
		var aabb := AABB()
		for mi in v.body.get_children():
			if mi is MeshInstance3D:
				aabb = mi.get_aabb() if aabb.size == Vector3.ZERO else aabb.merge(mi.get_aabb())
		var holder := Node3D.new()
		holder.add_child(v)
		v.position = Vector3(-aabb.get_center().x, g / 50.0, -aabb.get_center().z)
		# big cars (trucks) shrink to stand on their plinth like the rest
		var fit := minf(1.0, minf(4.2 / maxf(0.1, maxf(aabb.size.x, aabb.size.z)), 2.0 / maxf(0.1, aabb.size.y)))
		holder.scale = Vector3.ONE * fit
		holder.position = Vector3(x, h, -0.3)
		holder.rotation.y = PI + (0.3 if i == 0 else (0.5 if i == 1 else -0.5))
		var name_l := _tag3d(str(e.name), 56, Color(1.0, 0.353, 0.122) if i == 0 else Color(0.953, 0.949, 0.937))
		name_l.position = Vector3(x, h + (g / 50.0 + aabb.end.y) * fit + 0.9, -0.3)
		add_child(name_l)
		lineup.append(name_l)
		add_child(holder)
		lineup.append(holder)


func _clear_lineup() -> void:
	lineup_light.visible = false
	if env:
		env.fog_depth_begin = 16.0
		env.fog_depth_end = 42.0
	for h in lineup:
		h.queue_free()
	lineup.clear()
	_lineup_key = ""


func _process(dt: float) -> void:
	if not visible:
		return
	if mode == "podium":
		camera.position = Vector3(0, 4.6, 20.0)
		camera.look_at(Vector3(0, 1.6, 0), Vector3.UP)
		camera.h_offset = 0.0
		camera.v_offset = -1.3
		camera.fov = 40.0
		return
	if mode == "lineup":
		# the whole row in view, in the band between the title and the room's panels;
		# a long row widens the lens instead of backing off into the fog
		var w := maxf(2.0, lineup.size()) * _gap(lineup.size())
		var d := _lineup_dist(lineup.size())
		camera.fov = clampf(2.0 * rad_to_deg(atan((w * 0.6) / d / 1.78)), 30.0, 58.0)
		camera.position = Vector3(0, d * 0.2, d)
		camera.look_at(Vector3(0, 0.6, 0), Vector3.UP)
		camera.h_offset = 0.0
		camera.v_offset = -d * tan(deg_to_rad(camera.fov * 0.5)) * 0.12
		return
	camera.v_offset = 0.0
	turn += dt * (0.12 if mode == "menu" else 0.3)
	for c in cars.duplicate():
		c.t = minf(1.0, c.t + dt / 0.55)
		var e: float = 1.0 - pow(1.0 - c.t, 3.0)
		c.view.position.x = lerpf(c.x0, c.x1, e)
		c.view.rotation.y = turn
		if c.leaving and c.t >= 1.0:
			c.view.queue_free()
			cars.erase(c)
	var t := Time.get_ticks_msec() / 1000.0
	if mode == "menu":
		var d := maxf(11.0, maxf(size * 4.0, height * 4.4))
		var a := 0.25 + sin(t * 0.05) * 0.15
		camera.position = Vector3(sin(a) * d, 1.0, cos(a) * d)
		camera.look_at(Vector3(0, 0.9, 0), Vector3.UP)
		camera.h_offset = -d * 0.17
		camera.fov = 32.0
	else:
		# far enough for the car's length and its height (monster trucks, lorries)
		# distance that keeps the whole height in view (34° lens), with a margin
		var fit_h := height * 1.45 / tan(deg_to_rad(17.0)) + size
		var d := maxf(11.0, maxf(size * 3.9, fit_h))
		camera.position = Vector3(sin(0.35) * d, d * 0.2, cos(0.35) * d)
		camera.look_at(Vector3(0, height * 0.32 - 0.35, 0), Vector3.UP)
		camera.h_offset = 0.0
		camera.fov = 34.0


## The studio's environment and lights must leave the scene entirely while a
## stage is shown (Godot uses the first WorldEnvironment it finds).
func set_active(on: bool) -> void:
	visible = on
	if on and not is_inside_tree() and _parent:
		_parent.add_child(self)
	elif not on and is_inside_tree():
		_parent = get_parent()
		_parent.remove_child(self)

var _parent: Node
