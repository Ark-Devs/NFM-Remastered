## Everything visible in a race: sky, light and fog from the stage's own
## colours, terrain, the track pieces, the cars and the chase camera.
class_name RaceView
extends Node3D

const S := 1.0 / 50.0
## The original's ground plane is at y = 250 (NFM units, y down).
const GROUND_Y := -250.0 / 50.0
const CAMERAS := [
	{name = "Chase", dist = 520.0, height = 175.0, look = 40.0, look_up = 1.0},
	{name = "Close", dist = 400.0, height = 135.0, look = 40.0, look_up = 0.9},
	{name = "Classic NFM", dist = 800.0, height = 250.0, look = 40.0, look_up = 1.0},
	{name = "Bumper", bumper = true},
]

var sim: NfmSim
var env_data: PackedInt32Array
var cars: Array = []          # CarView per grid slot
var prev: PackedFloat32Array
var curr: PackedFloat32Array
var stride := 24
var camera := Camera3D.new()
var cam_mode := 0
var cam_yaw := 0.0
var cam_init := false
var look_yaw := 0.0
var shake := 0.0
var focus := 0
var watch := -1               # spectating: the car the camera follows instead of ours (-1 = ours)
var night := false
var deathmatch := false        # no course to follow: checkpoints stay dark
var checkpoints: Array = []   # [{mesh, index, mat}]
var fixes: Array = []
var time := 0.0
var susp: Array = []
var sparks: GPUParticles3D
var dust: GPUParticles3D
var smoke: GPUParticles3D
var world_env: WorldEnvironment
var share_env := false     # killcam: the race underneath already sets the sky
var free_camera := false   # killcam: camera placed from outside
var frozen := false        # killcam freeze: no new smoke
var scenery: Scenery
var sun: DirectionalLight3D
var headlight: SpotLight3D
var quality := "high"
var stagger := false        # menu preview: build a step per frame


func setup(p_sim: NfmSim, p_focus: int, p_quality: String) -> void:
	sim = p_sim
	focus = p_focus
	quality = p_quality
	env_data = sim.stage_env()
	stride = sim.car_state_size()
	night = env_data[28] != 0
	var lg := "--framelog" in OS.get_cmdline_user_args()
	var t0 := Time.get_ticks_msec()
	_build_environment()
	if lg: print("step env %d" % (Time.get_ticks_msec() - t0)); t0 = Time.get_ticks_msec()
	scenery = Scenery.new()
	scenery.stagger = stagger
	var theme := StageTheme.theme_for(sim.stage_name())
	scenery.theme = theme
	add_child(scenery)
	await scenery.setup(sim, env_data, quality)
	if "--nolook" not in OS.get_cmdline_user_args(): _grade(scenery.biome)
	if stagger and not is_inside_tree(): return
	if lg: print("step scenery %d" % (Time.get_ticks_msec() - t0)); t0 = Time.get_ticks_msec()
	_build_stage()
	if lg: print("step stage %d" % (Time.get_ticks_msec() - t0)); t0 = Time.get_ticks_msec()
	if stagger:
		await get_tree().process_frame
		if not is_inside_tree(): return
	_build_cars()
	if "--nolook" not in OS.get_cmdline_user_args(): _car_look()
	if lg: print("step cars %d" % (Time.get_ticks_msec() - t0))
	if not stagger:
		_build_effects() # a still aerial preview has no smoke or sparks
	camera.fov = 58.0
	camera.near = 0.3
	camera.far = 6000.0
	add_child(camera)
	camera.make_current()
	if theme != "" and "--notheme" not in OS.get_cmdline_user_args():
		var th := StageTheme.new()
		add_child(th)
		th.apply(self, theme, quality, stagger)
	curr = sim.car_states()
	prev = curr


## Intel integrated / Arc graphics (--intel pretends, for testing).
static func intel_gpu() -> bool:
	var who := (RenderingServer.get_video_adapter_vendor() + " " + RenderingServer.get_video_adapter_name()).to_lower()
	return "--intel" in OS.get_cmdline_user_args() or who.contains("intel")


func env_color(i: int) -> Color:
	return Color8(env_data[i], env_data[i + 1], env_data[i + 2])


func _build_environment() -> void:
	var sky_c := env_color(0)
	var fog_c := env_color(3)
	var ground_c := env_color(6)
	var e := Environment.new()
	e.background_mode = Environment.BG_SKY
	var sky := Sky.new()
	# the stage's sky colour, deepened overhead, with sun and clouds
	var sm := ShaderMaterial.new()
	sm.shader = preload("res://shaders/sky.gdshader")
	var top := sky_c.darkened(0.6 if night else 0.32)
	top.s = minf(1.0, top.s * 1.25)
	sm.set_shader_parameter("top_color", top)
	sm.set_shader_parameter("horizon_color", sky_c.lerp(fog_c, 0.45).lightened(0.05))
	sm.set_shader_parameter("ground_color", ground_c.darkened(0.45))
	sm.set_shader_parameter("night", 1.0 if night else 0.0)
	sm.set_shader_parameter("cloud_cover", 0.25 if night else 0.45)
	sky.sky_material = sm
	e.sky = sky
	e.ambient_light_source = Environment.AMBIENT_SOURCE_SKY
	e.ambient_light_energy = 0.4 if night else 1.0
	e.reflected_light_source = Environment.REFLECTION_SOURCE_SKY
	e.tonemap_mode = Environment.TONE_MAPPER_AGX
	e.tonemap_exposure = 1.0
	e.glow_enabled = true
	e.glow_intensity = 0.35
	e.glow_bloom = 0.05
	e.glow_hdr_threshold = 1.2
	if quality != "low":
		e.ssao_enabled = true
		e.ssao_radius = 1.2
		e.ssao_intensity = 1.6
	if quality == "high" or quality == "ultra":
		e.ssil_enabled = true
	if quality == "ultra":
		# SDFGI glitches on Intel graphics (cars flashing white), so it's only
		# used where it works; Ultra keeps everything else either way
		e.sdfgi_enabled = not RaceView.intel_gpu()
		e.ssr_enabled = true
	# the original faded distant polygons into the fog colour from fadefrom
	var fade := float(env_data[22])
	e.fog_enabled = true
	e.fog_mode = Environment.FOG_MODE_DEPTH
	e.fog_light_color = fog_c
	e.fog_depth_begin = maxf(fade * 7.0 * S, 350.0)
	e.fog_depth_end = maxf(fade * 48.0 * S, 3200.0)
	e.fog_depth_curve = 1.3
	e.fog_density = 0.75
	e.fog_aerial_perspective = 0.35
	e.fog_sky_affect = 0.0
	e.adjustment_enabled = true
	e.adjustment_saturation = 1.12
	e.adjustment_contrast = 1.05
	# test: --off=ssil,ssao,glow,fog measures what each effect costs
	var off := []
	for a in OS.get_cmdline_user_args(): if a.begins_with("--off="): off = Array(a.substr(6).split(","))
	if "ssil" in off: e.ssil_enabled = false
	if "ssao" in off: e.ssao_enabled = false
	if "glow" in off: e.glow_enabled = false
	if "fog" in off: e.fog_enabled = false
	world_env = WorldEnvironment.new()
	world_env.environment = e
	if not share_env:
		add_child(world_env)
	sun = DirectionalLight3D.new()
	sun.rotation_degrees = Vector3(-36, 35, 0)
	sun.light_energy = 0.18 if night else 1.65
	sun.light_color = Color(0.78, 0.82, 0.95) if night else Color(1.0, 0.98, 0.94)
	sun.shadow_enabled = true
	sun.directional_shadow_mode = DirectionalLight3D.SHADOW_PARALLEL_4_SPLITS
	sun.directional_shadow_max_distance = 140.0 if quality != "low" else 70.0
	sun.shadow_blur = 1.2
	if "shadow" in off: sun.shadow_enabled = false
	add_child(sun)


func _build_terrain() -> void:
	var b := [env_data[31], env_data[32], env_data[33], env_data[34]] # minx maxx minz maxz (NFM)
	var cx: float = (b[0] + b[1]) * 0.5 * S
	var cz: float = -(b[2] + b[3]) * 0.5 * S
	var hx: float = maxf(absf(b[1] - b[0]) * 0.5 * S, 60.0) + 45.0
	var hz: float = maxf(absf(b[3] - b[2]) * 0.5 * S, 60.0) + 45.0
	# big enough for the whole course (some drag strips run for kilometres),
	# with hills around it; the grid gets coarser, not denser, when it grows
	var size := maxf(3200.0, maxf(hx, hz) * 2.0 + 1600.0)
	var n := 128 if size <= 3200.0 else 192
	var noise := FastNoiseLite.new()
	noise.seed = 7
	noise.frequency = 0.004
	noise.fractal_octaves = 5
	var st := SurfaceTool.new()
	st.begin(Mesh.PRIMITIVE_TRIANGLES)
	var ground_c := env_color(6)
	var verts := PackedVector3Array()
	var cols := PackedColorArray()
	for j in n + 1:
		for i in n + 1:
			var x: float = cx - size * 0.5 + size * i / n
			var z: float = cz - size * 0.5 + size * j / n
			# flat inside the course, rising into hills beyond it
			var ox := maxf(absf(x - cx) - hx, 0.0)
			var oz := maxf(absf(z - cz) - hz, 0.0)
			var away := sqrt(ox * ox + oz * oz)
			var rise := smoothstep(0.0, 260.0, away)
			var h := (noise.get_noise_2d(x, z) * 0.5 + 0.5) * 180.0 * rise + away * 0.06 * rise
			verts.append(Vector3(x, GROUND_Y + h - 0.02, z))
			var shade := 0.82 + noise.get_noise_2d(x * 6.0, z * 6.0) * 0.12
			cols.append(Color(ground_c.r * shade, ground_c.g * shade, ground_c.b * shade))
	for j in n:
		for i in n:
			var a := j * (n + 1) + i
			for k in [a, a + 1, a + n + 1, a + 1, a + n + 2, a + n + 1]:
				st.set_color(cols[k])
				st.add_vertex(verts[k])
	st.generate_normals()
	var mi := MeshInstance3D.new()
	mi.mesh = st.commit()
	var mat := StandardMaterial3D.new()
	mat.vertex_color_use_as_albedo = true
	mat.roughness = 0.95
	mi.material_override = mat
	add_child(mi)


func _build_stage() -> void:
	var objs: PackedFloat32Array = sim.stage_objects()
	var by_slot := {}
	var stage_mat := Meshes.stage_material()
	var count := objs.size() / 16
	for i in count:
		var o := i * 16
		var slot := int(objs[o])
		var flags := int(objs[o + 1])
		var cpi := int(objs[o + 2])
		var t := Transform3D(Basis(Vector3(objs[o + 3], objs[o + 4], objs[o + 5]), Vector3(objs[o + 6], objs[o + 7], objs[o + 8]), Vector3(objs[o + 9], objs[o + 10], objs[o + 11])), Vector3(objs[o + 12], objs[o + 13], objs[o + 14]))
		if flags & 2 != 0 or flags & 1 != 0 or cpi > 0:
			# piles are unique; checkpoints / repair hoops glow on their own
			# piles are unique shapes; checkpoints and hoops use the base model + placement
			var parts: Array = sim.object_mesh(i) if flags & 2 != 0 else sim.piece_mesh(slot)
			var built := Meshes.build(parts)
			var mat := stage_mat
			if flags & 1 != 0 or cpi > 0:
				mat = ShaderMaterial.new()
				mat.shader = Meshes.stage_shader
				mat.set_shader_parameter("glow_color", Color(0.25, 0.6, 1.0) if flags & 1 != 0 else Color(1.0, 0.85, 0.35))
			var finish: MeshInstance3D = null
			for part in built:
				var mi := MeshInstance3D.new()
				mi.mesh = built[part]
				mi.material_override = mat
				mi.transform = t
				add_child(mi)
				if part == Meshes.FINISH:
					finish = mi
					mi.visible = false
			if cpi > 0:
				checkpoints.append({index = cpi, mat = mat, finish = finish, pos = t.origin})
			elif flags & 1 != 0:
				var light := OmniLight3D.new()
				light.light_color = Color(0.3, 0.65, 1.0)
				light.omni_range = 40.0
				light.position = t.origin + Vector3(0, 3, 0)
				add_child(light)
				fixes.append({mat = mat, light = light})
			continue
		if not by_slot.has(slot):
			by_slot[slot] = []
		by_slot[slot].append(t)
	for slot in by_slot:
		var parts: Array = sim.piece_mesh(slot)
		var built := Meshes.build(parts)
		# cut into map tiles so pieces off screen (or out of shadow range) are skipped
		var cells := {}
		for t in by_slot[slot]:
			var key := Vector2i(floori(t.origin.x / Scenery.tile()), floori(t.origin.z / Scenery.tile()))
			if not cells.has(key): cells[key] = []
			cells[key].append(t)
		for part in built:
			if part == Meshes.FINISH:
				continue
			for key in cells:
				var list: Array = cells[key]
				var mm := MultiMesh.new()
				mm.transform_format = MultiMesh.TRANSFORM_3D
				mm.mesh = built[part]
				mm.instance_count = list.size()
				for k in list.size():
					mm.set_instance_transform(k, list[k])
				var mmi := MultiMeshInstance3D.new()
				mmi.multimesh = mm
				mmi.material_override = stage_mat
				add_child(mmi)


func _build_cars() -> void:
	var grid: PackedInt32Array = sim.grid()
	for i in grid.size():
		var cv := CarView.new()
		cv.setup(sim, grid[i], i)
		add_child(cv)
		cars.append(cv)
		susp.append({comp = 0.0, comp_v = 0.0, roll = 0.0, roll_v = 0.0, pitch = 0.0, pitch_v = 0.0, vy = 0.0, last_y = 0.0, grounded = true})
	if night:
		headlight = SpotLight3D.new()
		headlight.spot_range = 90.0
		headlight.spot_angle = 32.0
		headlight.light_energy = 6.0
		headlight.shadow_enabled = true
		headlight.position = Vector3(0, 0.6, -1.5)
		cars[focus].add_child(headlight)


## Cars keep their showroom look on every stage: a neutral light that only
## lights cars (a coloured stage no longer washes the paint out) and the
## studio's strip-light reflections in the paint. Layer 2 = cars.
const CAR_LAYER := 2


## Test knob: a multiplier from the command line (default 1).
static func _knob(prefix: String, def: float) -> float:
	for a in OS.get_cmdline_user_args():
		if a.begins_with(prefix): return float(a.substr(prefix.length()))
	return def


func _car_look() -> void:
	var fill := DirectionalLight3D.new()
	fill.rotation_degrees = Vector3(-50, -150, 0)
	fill.light_color = Color(1.0, 0.99, 0.97)
	fill.light_energy = (0.4 if night else 0.7) * _knob("--fill=", 1.0)
	fill.light_cull_mask = CAR_LAYER
	fill.shadow_enabled = false
	add_child(fill)
	for c in cars:
		c.body_mat.set_shader_parameter("studio", (0.6 if night else 1.0) * _knob("--studio=", 1.0))
		for mi in c.find_children("*", "VisualInstance3D", true, false):
			mi.layers |= CAR_LAYER


## A light per-stage colour grade from the stage's biome: warm deserts, cool
## snow, fresh greens, blue nights. Barely there; it just sets the mood.
func _grade(biome: String) -> void:
	var tb: Array = {"desert": [Color(0.02, 0.0, -0.02), 1.06], "canyon": [Color(0.025, 0.0, -0.02), 1.07],
		"snow": [Color(-0.015, 0.0, 0.025), 1.02], "grass": [Color(0.0, 0.012, -0.01), 1.1],
		"dirt": [Color(0.012, 0.004, -0.012), 1.04]}.get(biome, [Color(), 1.0])
	var t: Color = tb[0]
	if night: t = Color(-0.02, -0.005, 0.03)
	tint(t, 1.12 * float(tb[1]) / 1.06 if tb[1] != 1.0 else 1.12)


## The colour grade: t is added to the midtones, sat is the saturation.
func tint(t: Color, sat: float) -> void:
	var e := world_env.environment
	var g := Gradient.new()
	g.set_color(0, Color(maxf(0.0, t.r * 0.5), maxf(0.0, t.g * 0.5), maxf(0.0, t.b * 0.5)))
	g.add_point(0.5, Color(0.5 + t.r, 0.5 + t.g, 0.5 + t.b))
	g.set_color(g.get_point_count() - 1, Color(1.0, 1.0, 1.0))
	var tex := GradientTexture1D.new()
	tex.gradient = g
	tex.width = 256
	e.adjustment_color_correction = tex
	e.adjustment_saturation = sat


func _particles(amount: int, lifetime: float, color: Color, size: float, gravity: float, unshaded: bool) -> GPUParticles3D:
	var p := GPUParticles3D.new()
	p.amount = amount
	p.lifetime = lifetime
	p.emitting = true
	p.one_shot = false
	p.explosiveness = 0.0
	p.amount_ratio = 1.0
	p.local_coords = false
	var pm := ParticleProcessMaterial.new()
	pm.gravity = Vector3(0, gravity, 0)
	pm.damping_min = 1.0
	pm.damping_max = 2.0
	pm.scale_min = 0.6
	pm.scale_max = 1.4
	var ramp := Gradient.new()
	ramp.set_color(0, color)
	ramp.set_color(1, Color(color.r, color.g, color.b, 0.0))
	var gt := GradientTexture1D.new()
	gt.gradient = ramp
	pm.color_ramp = gt
	p.process_material = pm
	var quad := QuadMesh.new()
	quad.size = Vector2(size, size)
	var m := StandardMaterial3D.new()
	m.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
	m.billboard_mode = BaseMaterial3D.BILLBOARD_PARTICLES
	m.vertex_color_use_as_albedo = true
	m.albedo_texture = _soft_dot()
	if unshaded:
		m.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
		m.emission_enabled = true
		m.emission = color
		m.emission_energy_multiplier = 3.0
	quad.material = m
	p.draw_pass_1 = quad
	p.visibility_aabb = AABB(Vector3(-4000, -500, -4000), Vector3(8000, 2000, 8000))
	# emit_particle() only: nothing spawns on its own
	pm.emission_shape = ParticleProcessMaterial.EMISSION_SHAPE_POINT
	p.amount_ratio = 1.0
	p.emitting = false
	add_child(p)
	return p


static var _dot: GradientTexture2D
## A soft round sprite so smoke, dust and sparks aren't square.
static func _soft_dot() -> GradientTexture2D:
	if _dot == null:
		var g := Gradient.new()
		g.set_color(0, Color(1, 1, 1, 1))
		g.set_color(1, Color(1, 1, 1, 0))
		_dot = GradientTexture2D.new()
		_dot.gradient = g
		_dot.fill = GradientTexture2D.FILL_RADIAL
		_dot.fill_from = Vector2(0.5, 0.5)
		_dot.fill_to = Vector2(1.0, 0.5)
		_dot.width = 64
		_dot.height = 64
	return _dot


func _build_effects() -> void:
	sparks = _particles(600, 0.45, Color(1.0, 0.75, 0.35), 0.06, -14.0, true)
	var g := env_color(6)
	dust = _particles(500, 1.6, Color(g.r * 0.8, g.g * 0.78, g.b * 0.74, 0.35), 1.6, 0.4, false)
	smoke = _particles(400, 2.4, Color(0.08, 0.08, 0.08, 0.55), 2.2, 1.2, false)
	if "--noparticles" in OS.get_cmdline_user_args():
		sparks.visible = false; dust.visible = false; smoke.visible = false


func emit(p: GPUParticles3D, pos: Vector3, vel: Vector3, col := Color(1, 1, 1, 1)) -> void:
	p.emit_particle(Transform3D(Basis(), pos), vel, col, Color(), GPUParticles3D.EMIT_FLAG_POSITION | GPUParticles3D.EMIT_FLAG_VELOCITY)


## Called after each 25 Hz simulation step.
func after_step() -> void:
	prev = curr
	curr = sim.car_states()
	if headlight:
		headlight.visible = curr[focus * stride + 10] < 0.5
	# events the renderer cares about
	for i in cars.size():
		if curr[i * stride + 10] > 0.5:
			cars[i].ignite() # wasted cars burn
		var v := int(curr[i * stride + 16])
		if v != cars[i].version:
			cars[i].version = v
			cars[i].sync_damage()
	_damage_tick += 1
	if _damage_tick % 2 == 0:
		# distant cars sync less often
		for i in cars.size():
			if i == focus or cars[i].global_position.distance_to(camera.global_position) < 80.0 or _damage_tick % 8 == 0:
				if absf(curr[i * stride + 9] - prev[i * stride + 9]) > 0.0001:
					cars[i].sync_damage()

var _damage_tick := 0


func on_event(type: int, v: PackedFloat32Array) -> void:
	match type:
		7: # spark: car, x, y, z, vx, vy, vz, kind
			var pos := Vector3(v[1] * S, -v[2] * S, -v[3] * S)
			for k in 3:
				emit(sparks, pos, Vector3(v[4], -v[5], -v[6]) * S * 25.0 * 0.4 + Vector3(randf_range(-2, 2), randf_range(0, 3), randf_range(-2, 2)))
		6: # dust: car, wheel, x, y, z, vx, vz, mag
			if randf() < 0.5:
				var pos := Vector3(v[2] * S, -v[3] * S, -v[4] * S)
				emit(dust, pos, Vector3(v[5] * S * 4.0, 0.6, -v[6] * S * 4.0))
		15: # a stuck AI car put back on the track: jump straight there (no
			# slide across the map) and land in a puff of dust, so it's seen
			var o := int(v[0]) * stride
			for k in stride:
				prev[o + k] = curr[o + k]
			var at := Vector3(curr[o] * S, -curr[o + 1] * S, -curr[o + 2] * S)
			for k in 12:
				emit(dust, at, Vector3(randf_range(-3, 3), randf_range(0.5, 2.5), randf_range(-3, 3)))
		1: # crash
			if v[0] > 150.0:
				shake = minf(1.0, shake + v[0] / 900.0)


## A low, slow arc round the boss from its front three-quarter, pushing in a
## little: the car shot from a racing game's intro.
func _showcase_camera(dt: float) -> void:
	showcase_t += dt
	var car: CarView = cars[showcase]
	var b: AABB = car.body_bounds()
	var size := maxf(b.size.z, b.size.x)
	var t := car.global_transform
	var fwd := -t.basis.z
	fwd.y = 0.0
	fwd = fwd.normalized()
	var side := fwd.cross(Vector3.UP)
	var a := deg_to_rad(-55.0 + showcase_t * 30.0)
	var r := size * (2.2 - minf(showcase_t, 3.0) * 0.12)
	var dir := (fwd * cos(a) + side * sin(a)).normalized()
	var centre := car.global_position + Vector3.UP * b.size.y * 0.45
	camera.global_position = centre + dir * r + Vector3.UP * (0.35 + b.size.y * 0.25)
	camera.look_at(centre, Vector3.UP)
	camera.fov = 42.0


## look: 0 ahead, 1 look back swinging round the right, -1 round the left.
func frame(dt: float, alpha: float, look: int) -> void:
	time += dt
	for i in cars.size():
		var o := i * stride
		var a := prev; var b := curr
		var x := lerpf(a[o], b[o], alpha)
		var y := lerpf(a[o + 1], b[o + 1], alpha)
		var z := lerpf(a[o + 2], b[o + 2], alpha)
		var xz := _angle_lerp(a[o + 3], b[o + 3], alpha)
		var xy := _angle_lerp(a[o + 4], b[o + 4], alpha)
		var zy := _angle_lerp(a[o + 5], b[o + 5], alpha)
		var wxz := lerpf(a[o + 6], b[o + 6], alpha)
		var lean := _suspension(i, b, o, dt)
		cars[i].update_pose(Meshes.pose(x, y, z, xz, xy, zy), wxz, b[o + 7], dt, lean)
		cars[i].set_damage(b[o + 9], b[o + 17] > 0.5)
		cars[i].set_lights(false, night, b[o + 10] > 0.5)
		# smoke from badly damaged / wasted cars
		if (b[o + 9] > 0.55 or b[o + 10] > 0.5) and not frozen:
			if randf() < 0.35:
				emit(smoke, cars[i].global_position + Vector3(0, 0.6, 0), Vector3(randf_range(-0.3, 0.3), 1.2, randf_range(-0.3, 0.3)))
	if not free_camera:
		_update_camera(dt, alpha, look)
		for c in cars: c.flicker()
	var info: PackedInt32Array = sim.race_info()
	var nsp := maxi(1, info[7])
	var clear := int(curr[focus * stride + 11])
	var next := (clear % nsp) + 1
	var last := clear == info[8] * nsp - 1
	for c in checkpoints:
		var on: bool = c.index == next and not deathmatch
		c.mat.set_shader_parameter("glow", (0.35 + 0.3 * sin(time * 6.0)) if on else 0.0)
		if c.finish:
			c.finish.visible = on and last
	for f in fixes:
		var flick := 0.5 + 0.5 * sin(time * 9.0 + sin(time * 23.0) * 2.0)
		f.mat.set_shader_parameter("glow", 0.4 + flick * 0.8)
		f.light.light_energy = 1.5 + flick * 2.0
	shake = maxf(0.0, shake - dt * 2.2)


## Stage-select preview: no racing, the camera circles the whole course.
var _orbit := {}
func frame_orbit(dt: float) -> void:
	time += dt
	if _orbit.is_empty():
		var b := [env_data[31], env_data[32], env_data[33], env_data[34]]
		var objs: PackedFloat32Array = sim.stage_objects()
		# frame the bulk of the course: a few far-flung pieces mustn't shrink it to a dot
		var xs := []
		var zs := []
		for i in range(0, objs.size(), 16):
			xs.append(objs[i + 12]); zs.append(objs[i + 14])
		xs.sort(); zs.sort()
		var lo := Vector3(INF, 0, INF)
		var hi := Vector3(-INF, 0, -INF)
		if xs.size() > 0:
			var k0 := int(xs.size() * 0.05)
			var k1 := xs.size() - 1 - k0
			lo = Vector3(xs[k0], 0, zs[k0])
			hi = Vector3(xs[k1], 0, zs[k1])
		if lo.x == INF:
			lo = Vector3(-40, 0, -40); hi = Vector3(40, 0, 40)
		var r := maxf(maxf(hi.x - lo.x, hi.z - lo.z) * 0.5, 60.0)
		var narrow := minf(hi.x - lo.x, hi.z - lo.z) * 0.5
		_orbit = {c = (lo + hi) * 0.5, r = r, a = 0.7, glide = 0.0, axis = Vector3.ZERO}
		# a long thin strip (drag races) is a line from far away: glide along it
		# close up instead of circling the whole thing
		if r > maxf(narrow, 60.0) * 3.0:
			_orbit.glide = r * 0.85
			_orbit.axis = Vector3(1, 0, 0) if hi.x - lo.x > hi.z - lo.z else Vector3(0, 0, 1)
			r = maxf(narrow, 60.0) * 1.6
			_orbit.r = r
		elif r > 1500.0 and cars.size() > 0:
			# pieces kilometres apart: show the start area instead of a field of dots
			var start := Meshes.pose(curr[0], curr[1], curr[2], 0, 0, 0).origin
			_orbit.c = Vector3(start.x, 0, start.z)
			r = 30.0
			_orbit.r = r
		# seen from far above: haze only beyond the course, and a draw distance
		# long enough for the longest drag strips
		var e := world_env.environment
		var view_d := sqrt(pow(r * 1.3 + 40.0, 2) + pow(r * 0.55 + 25.0, 2)) + r
		e.fog_depth_begin = maxf(e.fog_depth_begin, view_d)
		e.fog_depth_end = maxf(e.fog_depth_end, view_d * 2.2)
		camera.far = maxf(camera.far, view_d * 2.5)
		for i in cars.size():
			var o := i * stride
			cars[i].update_pose(Meshes.pose(curr[o], curr[o + 1], curr[o + 2], curr[o + 3], curr[o + 4], curr[o + 5]), 0, 0, 0, Vector3.ZERO)
	_orbit.a += dt * 0.06
	var d: float = _orbit.r * 1.3 + 40.0
	var c: Vector3 = _orbit.c
	if _orbit.glide > 0.0:
		c += _orbit.axis * sin(time * 0.05) * _orbit.glide
	camera.global_position = c + Vector3(sin(_orbit.a) * d, _orbit.r * 0.55 + 25.0, cos(_orbit.a) * d)
	camera.look_at(c, Vector3.UP)
	camera.fov = 50.0


func _angle_lerp(a: float, b: float, t: float) -> float:
	var d := fmod(b - a, 360.0)
	if d > 180.0: d -= 360.0
	if d < -180.0: d += 360.0
	return a + d * t


## A soft visual spring: landings compress, cornering rolls (physics untouched).
func _suspension(i: int, s: PackedFloat32Array, o: int, dt: float) -> Vector3:
	var sp: Dictionary = susp[i]
	var grounded := s[o + 14] > 0.5
	var y := s[o + 1]
	var vy := (y - float(sp.last_y)) / maxf(dt, 0.001) if dt > 0 else 0.0
	sp.last_y = y
	if grounded and not sp.grounded:
		sp.comp_v -= clampf(absf(float(sp.vy)) * 0.0006, 0.0, 1.8)
	sp.grounded = grounded
	sp.vy = vy
	var w := 14.0
	sp.comp_v += (-w * w * sp.comp - 2.0 * 0.45 * w * sp.comp_v) * dt
	sp.comp = clampf(sp.comp + sp.comp_v * dt, -0.12, 0.08)
	return Vector3(sp.comp, 0.0, 0.0)


## Career boss intro: the slot the camera circles (-1 = off) and for how long.
var showcase := -1
var showcase_t := 0.0


func _update_camera(dt: float, alpha: float, look: int) -> void:
	if showcase >= 0 and showcase < cars.size():
		_showcase_camera(dt)
		return
	var who := watch if watch >= 0 else focus
	var o := who * stride
	var car: CarView = cars[who]
	var mode: Dictionary = CAMERAS[cam_mode]
	var target_look := 180.0 * look
	look_yaw += (target_look - look_yaw) * minf(1.0, dt * 10.0)
	var cxz := _angle_lerp(prev[o + 20], curr[o + 20], alpha)
	var pos_car := car.global_position
	if mode.get("bumper", false):
		# just past the front of this car's body, at bumper height, so long
		# noses and tall cabs never end up in front of the lens
		var t := car.global_transform
		var b: AABB = car.body_bounds()
		var p := t * Vector3(0, clampf(b.position.y + b.size.y * 0.3, 0.35, 1.2), b.position.z - 0.45)
		var l := t * Vector3(0, 0.1, -40.0)
		if absf(look_yaw) > 90.0:
			l = p * 2.0 - l
		camera.global_position = p
		camera.look_at(l, Vector3.UP)
	else:
		var target := cxz + look_yaw
		if not cam_init:
			cam_yaw = target
			cam_init = true
		cam_yaw = _angle_lerp(cam_yaw, target, 1.0 - exp(-dt * 7.0))
		var n := deg_to_rad(cam_yaw)
		var fwd := Vector3(-sin(n), 0, -cos(n))
		var p: Vector3 = pos_car - fwd * mode.dist * S
		p.y = pos_car.y + mode.height * S
		var l: Vector3 = pos_car + fwd * mode.look * S
		l.y += mode.look_up
		camera.global_position = p
		camera.look_at(l, Vector3.UP)
	if shake > 0.0:
		var s := shake * 0.12
		camera.global_position += Vector3(randf_range(-0.5, 0.5), randf_range(-0.5, 0.5), 0) * s
	var speed := absf(curr[o + 7])
	var fov := 58.0 + minf(5.0, speed / 60.0)
	camera.fov += (fov - camera.fov) * minf(1.0, dt * 3.0)


## Where the focused car's next checkpoint is (null if the stage has none).
func next_checkpoint():
	var info: PackedInt32Array = sim.race_info()
	var nsp := maxi(1, info[7])
	var next := (int(curr[focus * stride + 11]) % nsp) + 1
	for c in checkpoints:
		if c.index == next: return c.pos
	return null


func cycle_camera() -> String:
	cam_mode = (cam_mode + 1) % CAMERAS.size()
	return CAMERAS[cam_mode].name
