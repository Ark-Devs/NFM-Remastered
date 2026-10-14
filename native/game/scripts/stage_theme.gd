## Gives the stages with a story in their name a look of their own
## (assets/stage_themes.json): Centrifugal Rush really is under water, Space
## Walk is in space, the ghost stages are misty, the beach has a sea, the
## coliseum has a crowd and Four Dimensional Vertigo has an impossible sky.
## Everything here is visual; the race itself is untouched.
class_name StageTheme
extends Node3D

const PATH := "res://assets/stage_themes.json"
const S := 1.0 / 50.0
const GROUND_Y := -250.0 / 50.0

static var _themes = null

var view: RaceView
var theme := ""
var _decor: Array = []      # things that drift with time: [node, kind]


## The theme for a stage name ("" for an ordinary stage).
static func theme_for(stage_name: String) -> String:
	stage_name = stage_name.replace("|", ",") # the stage files write commas as |
	if _themes == null:
		_themes = {}
		if FileAccess.file_exists(PATH):
			var d = JSON.parse_string(FileAccess.get_file_as_string(PATH))
			if d is Dictionary: _themes = d
	return str(_themes.get(stage_name, {}).get("theme", "")) if _themes.get(stage_name) is Dictionary else ""


func apply(p_view: RaceView, p_theme: String, quality: String, preview: bool) -> void:
	view = p_view
	theme = p_theme
	var e: Environment = view.world_env.environment
	var sm: ShaderMaterial = e.sky.sky_material
	var sc: Scenery = view.scenery
	var lots := quality == "ultra"
	match theme:
		"underwater":
			sm.set_shader_parameter("top_color", Color(0.02, 0.14, 0.18))
			sm.set_shader_parameter("horizon_color", Color(0.06, 0.34, 0.38))
			sm.set_shader_parameter("ground_color", Color(0.03, 0.12, 0.13))
			sm.set_shader_parameter("cloud_cover", 0.0)
			sm.set_shader_parameter("underwater", 1.0)
			e.fog_light_color = Color(0.07, 0.33, 0.36)
			e.fog_depth_begin = 140.0
			e.fog_depth_end = 1600.0
			e.fog_density = 0.7
			e.fog_sky_affect = 0.35
			_sun(Color(0.75, 0.95, 0.95), 0.8)
			view.tint(Color(-0.03, 0.012, 0.025), 1.05)
			if not preview:
				if quality != "low": _caustics()
				_bubbles(320 if lots else 160)
		"space":
			sm.set_shader_parameter("top_color", Color(0.004, 0.005, 0.014))
			sm.set_shader_parameter("horizon_color", Color(0.03, 0.035, 0.07))
			sm.set_shader_parameter("ground_color", Color(0.02, 0.02, 0.03))
			sm.set_shader_parameter("cloud_cover", 0.0)
			sm.set_shader_parameter("space", 1.0)
			e.fog_light_color = Color(0.03, 0.035, 0.07)
			e.fog_depth_begin = maxf(e.fog_depth_begin, 1500.0)
			e.fog_depth_end = maxf(e.fog_depth_end, 6000.0)
			e.ambient_light_energy = 0.45
			_sun(Color(1.0, 0.98, 0.95), 1.4)
			view.tint(Color(-0.01, -0.005, 0.02), 0.92)
		"mist":
			e.fog_light_color = Color(0.42, 0.45, 0.55) if not view.night else Color(0.18, 0.22, 0.32)
			e.fog_depth_begin = 60.0
			e.fog_depth_end = maxf(1100.0, e.fog_depth_end * 0.45)
			e.fog_density = 0.8
			view.tint(Color(-0.01, 0.01, 0.025), 0.9)
			if not preview: _mist(5 if lots else 3)
		"beach":
			_sea(sc)
			view.tint(Color(0.012, 0.004, -0.004), 1.12)
		"coliseum":
			_stands(sc)
		"vertigo":
			sm.set_shader_parameter("vertigo", 1.0)
			sm.set_shader_parameter("cloud_cover", 0.1)
			e.fog_light_color = Color(0.3, 0.18, 0.42)
			view.tint(Color(0.015, -0.01, 0.03), 1.1)
			_floaters(sc, 260 if lots else 160)


func _process(_dt: float) -> void:
	for d in _decor:
		var n: Node3D = d[0]
		if not is_instance_valid(n): continue
		if d[1] == "follow" and view.camera:
			# particle emitters ride with the camera; their particles stay put in the world
			n.global_position = view.camera.global_position


func _sun(c: Color, energy: float) -> void:
	for l in view.find_children("*", "DirectionalLight3D", true, false):
		if l.light_cull_mask != RaceView.CAR_LAYER: # not the cars' own fill light
			l.light_color = c
			l.light_energy *= energy
			return


## Rippling caustic light on everything (a full-screen pass on the camera).
func _caustics() -> void:
	var q := MeshInstance3D.new()
	var m := QuadMesh.new()
	m.size = Vector2(2, 2)
	q.mesh = m
	q.extra_cull_margin = 16384.0
	q.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
	var mat := ShaderMaterial.new()
	mat.shader = preload("res://shaders/caustics.gdshader")
	q.material_override = mat
	view.camera.add_child(q)


static func _soft_dot(ring: bool) -> Texture2D:
	var g := Gradient.new()
	if ring:
		g.set_color(0, Color(1, 1, 1, 0.15))
		g.add_point(0.7, Color(1, 1, 1, 0.55))
		g.set_color(g.get_point_count() - 1, Color(1, 1, 1, 0.0))
	else:
		g.set_color(0, Color(1, 1, 1, 1))
		g.set_color(g.get_point_count() - 1, Color(1, 1, 1, 0))
	var t := GradientTexture2D.new()
	t.gradient = g
	t.fill = GradientTexture2D.FILL_RADIAL
	t.fill_from = Vector2(0.5, 0.5)
	t.fill_to = Vector2(1.0, 0.5)
	t.width = 64
	t.height = 64
	return t


func _cloud(amount: int, lifetime: float, box: Vector3, vel: Vector3, size: Vector2, color: Color, ring: bool) -> GPUParticles3D:
	var p := GPUParticles3D.new()
	p.amount = amount
	p.lifetime = lifetime
	p.preprocess = lifetime
	p.local_coords = false
	p.visibility_aabb = AABB(-box * 2.0, box * 4.0)
	var pm := ParticleProcessMaterial.new()
	pm.emission_shape = ParticleProcessMaterial.EMISSION_SHAPE_BOX
	pm.emission_box_extents = box
	pm.direction = vel.normalized() if vel != Vector3.ZERO else Vector3.UP
	pm.spread = 25.0
	pm.initial_velocity_min = vel.length() * 0.6
	pm.initial_velocity_max = vel.length() * 1.2
	pm.gravity = Vector3.ZERO
	pm.scale_min = size.x
	pm.scale_max = size.y
	var fade := Gradient.new()
	fade.set_color(0, Color(1, 1, 1, 0))
	fade.add_point(0.2, Color(1, 1, 1, 1))
	fade.add_point(0.8, Color(1, 1, 1, 1))
	fade.set_color(fade.get_point_count() - 1, Color(1, 1, 1, 0))
	var ft := GradientTexture1D.new()
	ft.gradient = fade
	pm.color_ramp = ft
	p.process_material = pm
	var quad := QuadMesh.new()
	var mat := StandardMaterial3D.new()
	mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
	mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
	mat.billboard_mode = BaseMaterial3D.BILLBOARD_PARTICLES
	mat.vertex_color_use_as_albedo = true
	mat.albedo_color = color
	mat.albedo_texture = _soft_dot(ring)
	mat.depth_draw_mode = BaseMaterial3D.DEPTH_DRAW_DISABLED
	# nothing right on the lens: fade in from a few metres out
	mat.distance_fade_mode = BaseMaterial3D.DISTANCE_FADE_PIXEL_ALPHA
	mat.distance_fade_min_distance = 6.0
	mat.distance_fade_max_distance = 16.0
	quad.material = mat
	p.draw_pass_1 = quad
	p.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
	add_child(p)
	_decor.append([p, "follow"])
	return p


func _bubbles(n: int) -> void:
	_cloud(n, 7.0, Vector3(40, 14, 40), Vector3(0, 2.2, 0), Vector2(0.04, 0.1), Color(0.85, 1.0, 1.0, 0.6), true)


## Low drifting mist: a few fog sheets stacked over the ground, riding with the camera.
func _mist(layers: int) -> void:
	var mat := ShaderMaterial.new()
	mat.shader = preload("res://shaders/mist.gdshader")
	mat.set_shader_parameter("mist_color", view.world_env.environment.fog_light_color.lightened(0.55))
	for i in layers:
		var m := MeshInstance3D.new()
		var pm := PlaneMesh.new()
		pm.size = Vector2(1400, 1400)
		m.mesh = pm
		m.material_override = mat
		m.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
		m.set_meta("y", GROUND_Y + 0.5 + i * 1.1)
		add_child(m)
		_decor.append([m, "follow_flat"])


## The sea round the beach: a big water plane with the track cut out of it.
func _sea(sc: Scenery) -> void:
	var w := MeshInstance3D.new()
	var m := PlaneMesh.new()
	m.size = Vector2(9000, 9000)
	m.subdivide_width = 0
	w.mesh = m
	var mat := ShaderMaterial.new()
	mat.shader = preload("res://shaders/water.gdshader")
	var r := _arena(sc, 40.0)
	mat.set_shader_parameter("hole", Vector4(r.position.x, r.end.x, r.position.y, r.end.y))
	w.material_override = mat
	w.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
	w.position = Vector3(sc.cx, GROUND_Y + 0.35, sc.cz)
	add_child(w)


## The area the race happens in: the road pieces plus a margin, kept inside
## the stage's own limits.
func _arena(sc: Scenery, margin: float) -> Rect2:
	var lim := Rect2(sc.x0, sc.z0, sc.x1 - sc.x0, sc.z1 - sc.z0)
	if sc.track.size == Vector2.ZERO: return lim
	return sc.track.grow(margin).intersection(lim)


## Grandstands all round the arena, packed with a crowd.
func _stands(sc: Scenery) -> void:
	var mat := ShaderMaterial.new()
	mat.shader = preload("res://shaders/crowd.gdshader")
	# right behind the stage's own walls, so nobody can drive into the crowd
	var r := sc.walls
	var gap := 3.0
	var tiers := 12
	var step := 3.2
	for side in 4:
		var along := r.size.x if side < 2 else r.size.y
		for t in tiers:
			var b := MeshInstance3D.new()
			var bm := BoxMesh.new()
			var depth := 4.0
			bm.size = Vector3(along + (gap + t * depth) * 2.0, step * (t + 1), depth)
			b.mesh = bm
			b.material_override = mat
			var y := GROUND_Y + step * (t + 1) / 2.0
			var off := gap + t * depth + depth / 2.0
			match side:
				0: b.position = Vector3(r.get_center().x, y, r.position.y - off)
				1: b.position = Vector3(r.get_center().x, y, r.end.y + off)
				2: b.position = Vector3(r.position.x - off, y, r.get_center().y); b.rotation.y = PI / 2
				3: b.position = Vector3(r.end.x + off, y, r.get_center().y); b.rotation.y = PI / 2
			add_child(b)


## Four Dimensional Vertigo: cubes hanging in the sky, slowly turning.
func _floaters(sc: Scenery, n: int) -> void:
	var rng := RandomNumberGenerator.new()
	rng.seed = 4
	var mm := MultiMesh.new()
	mm.transform_format = MultiMesh.TRANSFORM_3D
	mm.use_colors = true
	var box := BoxMesh.new()
	mm.mesh = box
	mm.instance_count = n
	for i in n:
		var a := rng.randf() * TAU
		var r := rng.randf_range(350.0, 1600.0) + maxf(sc.x1 - sc.x0, sc.z1 - sc.z0) * 0.5
		var s := rng.randf_range(6.0, 40.0)
		var basis := Basis.from_euler(Vector3(rng.randf() * TAU, rng.randf() * TAU, rng.randf() * TAU)).scaled(Vector3.ONE * s)
		mm.set_instance_transform(i, Transform3D(basis, Vector3(cos(a) * r, GROUND_Y + rng.randf_range(60.0, 420.0), sin(a) * r)))
		mm.set_instance_color(i, Color(0.55, 0.2, 0.75).lerp(Color(0.1, 0.75, 0.8), rng.randf()))
	var mi := MultiMeshInstance3D.new()
	mi.multimesh = mm
	mi.position = Vector3(sc.cx, 0, sc.cz)
	var mat := StandardMaterial3D.new()
	mat.vertex_color_use_as_albedo = true
	mat.emission_enabled = true
	mat.emission = Color(0.25, 0.1, 0.35)
	mat.roughness = 0.4
	mi.material_override = mat
	mi.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
	add_child(mi)
	_decor.append([mi, "spin"])


func _physics_process(dt: float) -> void:
	for d in _decor:
		if d[1] == "spin" and is_instance_valid(d[0]):
			(d[0] as Node3D).rotate_y(dt * 0.02)
		elif d[1] == "follow_flat" and is_instance_valid(d[0]) and view.camera:
			var p: Vector3 = view.camera.global_position
			(d[0] as Node3D).global_position = Vector3(p.x, float(d[0].get_meta("y", GROUND_Y + 1.5)), p.z)
