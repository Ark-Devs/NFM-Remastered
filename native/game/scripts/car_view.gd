## One car on screen: body / glass / lamps from the native mesh builder,
## spinning and steering wheels, damage that dents the real geometry.
class_name CarView
extends Node3D

const S := 1.0 / 50.0

var sim: NfmSim
var index := -1          # grid slot in the race (-1 = showroom)
var slot := 0            # car model
var body := Node3D.new() # everything that leans with the visual suspension
var meshes := {}         # part -> ArrayMesh
var body_mat: ShaderMaterial
var glass_mat: ShaderMaterial
var lightf_mat: ShaderMaterial
var lightb_mat: ShaderMaterial
var wheels: Array = []   # [{node, spin, steer}]
var spin_angle := 0.0
var version := -1
var simag := 0.9

static var _tire_mat: StandardMaterial3D


func setup(p_sim: NfmSim, p_slot: int, p_index := -1) -> void:
	sim = p_sim
	slot = p_slot
	index = p_index
	add_child(body)
	body_mat = ShaderMaterial.new(); body_mat.shader = Meshes.body_shader
	glass_mat = ShaderMaterial.new(); glass_mat.shader = Meshes.glass_shader
	lightf_mat = ShaderMaterial.new(); lightf_mat.shader = Meshes.light_shader
	lightb_mat = ShaderMaterial.new(); lightb_mat.shader = Meshes.light_shader
	lightf_mat.set_shader_parameter("intensity", 0.5)
	lightb_mat.set_shader_parameter("intensity", 0.35)
	var parts: Array = sim.car_mesh_base(slot) if index < 0 else sim.race_car_mesh(index)
	_apply_parts(parts, true)
	var st: PackedFloat32Array = sim.car_stats(slot)
	simag = st[6] if st.size() > 6 else 0.9
	_build_wheels()


func _apply_parts(parts: Array, create: bool) -> void:
	var bottom := 0.0
	for p in parts:
		var part := int(p[0])
		if create or not meshes.has(part):
			var built := Meshes.build([p])
			var m: ArrayMesh = built[part]
			var mat: Material = body_mat
			match part:
				Meshes.GLASS: mat = glass_mat
				Meshes.LIGHT_F: mat = lightf_mat
				Meshes.LIGHT_B: mat = lightb_mat
			m.surface_set_material(0, mat)
			meshes[part] = m
			var mi := MeshInstance3D.new()
			mi.mesh = m
			body.add_child(mi)
		else:
			Meshes.rebuild(meshes[part], p)
		if part == Meshes.BODY:
			var vs: PackedVector3Array = p[1]
			for v in vs:
				bottom = min(bottom, v.y)
	body_mat.set_shader_parameter("bottom_y", bottom)


## The body's box in the car's own space (forward is -z), cached.
var _bounds := AABB()


func body_bounds() -> AABB:
	if _bounds.size == Vector3.ZERO:
		for mi in body.get_children():
			if mi is MeshInstance3D:
				var bb: AABB = (body.transform * mi.transform) * mi.get_aabb()
				_bounds = bb if _bounds.size == Vector3.ZERO else _bounds.merge(bb)
		if _bounds.size == Vector3.ZERO:
			_bounds = AABB(Vector3(-1, 0, -2), Vector3(2, 1.4, 4))
	return _bounds


func _tire_material() -> StandardMaterial3D:
	if _tire_mat == null:
		_tire_mat = StandardMaterial3D.new()
		_tire_mat.albedo_color = Color(0.03, 0.03, 0.032)
		_tire_mat.roughness = 0.92
	return _tire_mat


## A rim seen from outside (facing +x): a dark recessed barrel, tapered
## spokes standing proud of it, a bright outer lip and a centre cap. Metal in
## the car's own rim colour. Wheels of the same size and colour share a mesh.
static var _rim_cache := {}


static func _rim_mesh(r: float, col: Color, spokes: int) -> ArrayMesh:
	var key := "%.4f|%s|%d" % [r, col.to_html(), spokes]
	if _rim_cache.has(key):
		return _rim_cache[key]
	var metal := StandardMaterial3D.new()
	metal.albedo_color = col
	metal.metallic = 0.9
	metal.roughness = 0.22
	metal.cull_mode = BaseMaterial3D.CULL_DISABLED
	var dark := StandardMaterial3D.new()
	dark.albedo_color = col.darkened(0.78)
	dark.metallic = 0.6
	dark.roughness = 0.55
	dark.cull_mode = BaseMaterial3D.CULL_DISABLED
	var cap := StandardMaterial3D.new()
	cap.albedo_color = col.darkened(0.35)
	cap.metallic = 0.95
	cap.roughness = 0.15
	cap.cull_mode = BaseMaterial3D.CULL_DISABLED
	var depth := r * 0.18          # how far the barrel sits behind the spokes
	var seg := 32
	var ring := func(st: SurfaceTool, r0: float, r1: float, x: float) -> void:
		for k in seg:
			var a0 := TAU * k / seg
			var a1 := TAU * (k + 1) / seg
			var p := [Vector3(x, cos(a0) * r0, sin(a0) * r0), Vector3(x, cos(a0) * r1, sin(a0) * r1), Vector3(x, cos(a1) * r1, sin(a1) * r1), Vector3(x, cos(a1) * r0, sin(a1) * r0)]
			for idx in [0, 1, 2, 0, 2, 3]:
				st.set_normal(Vector3.RIGHT)
				st.add_vertex(p[idx])
	var band := func(st: SurfaceTool, rr: float, x0: float, x1: float, inward: bool) -> void:
		for k in seg:
			var a0 := TAU * k / seg
			var a1 := TAU * (k + 1) / seg
			var n0 := Vector3(0, cos(a0), sin(a0)) * (-1.0 if inward else 1.0)
			var n1 := Vector3(0, cos(a1), sin(a1)) * (-1.0 if inward else 1.0)
			var p := [Vector3(x0, cos(a0) * rr, sin(a0) * rr), Vector3(x1, cos(a0) * rr, sin(a0) * rr), Vector3(x1, cos(a1) * rr, sin(a1) * rr), Vector3(x0, cos(a1) * rr, sin(a1) * rr)]
			var nn := [n0, n0, n1, n1]
			for idx in [0, 1, 2, 0, 2, 3]:
				st.set_normal(nn[idx])
				st.add_vertex(p[idx])
	var mesh := ArrayMesh.new()
	# 1. barrel: recessed dark face and the inner wall of the lip
	var st := SurfaceTool.new()
	st.begin(Mesh.PRIMITIVE_TRIANGLES)
	ring.call(st, 0.0, r * 0.86, -depth)
	band.call(st, r * 0.86, -depth, 0.0, true)
	st.commit(mesh)
	mesh.surface_set_material(0, dark)
	# 2. metal: outer lip, its rim edge, and the spokes
	st = SurfaceTool.new()
	st.begin(Mesh.PRIMITIVE_TRIANGLES)
	ring.call(st, r * 0.86, r, 0.0)
	band.call(st, r, -depth * 0.6, 0.0, false)
	for k in spokes:
		var a := TAU * k / spokes
		var dir := Vector3(0, cos(a), sin(a))
		var side := Vector3(0, -sin(a), cos(a))
		var r0 := r * 0.2
		var r1 := r * 0.87
		var w0 := r * 0.13
		var w1 := r * 0.07
		var front := [dir * r0 - side * w0, dir * r1 - side * w1, dir * r1 + side * w1, dir * r0 + side * w0]
		# spoke face slopes from the hub (proud) down into the lip
		var fx := [depth * 0.35, 0.0, 0.0, depth * 0.35]
		var back := -depth
		var f3 := []
		var b3 := []
		for q in 4:
			f3.append(front[q] + Vector3(fx[q], 0, 0))
			b3.append(front[q] + Vector3(back, 0, 0))
		for idx in [0, 1, 2, 0, 2, 3]:
			st.set_normal((Vector3.RIGHT * 2.0 - dir * 0.6).normalized())
			st.add_vertex(f3[idx])
		for e in [[0, 1], [3, 2]]:
			var n := (side * (-1.0 if e[0] == 0 else 1.0)).normalized()
			var quad := [f3[e[0]], f3[e[1]], b3[e[1]], b3[e[0]]]
			for idx in [0, 1, 2, 0, 2, 3]:
				st.set_normal(n)
				st.add_vertex(quad[idx])
	st.commit(mesh)
	mesh.surface_set_material(1, metal)
	# 3. centre cap standing slightly proud
	st = SurfaceTool.new()
	st.begin(Mesh.PRIMITIVE_TRIANGLES)
	ring.call(st, 0.0, r * 0.22, depth * 0.45)
	band.call(st, r * 0.22, 0.0, depth * 0.45, false)
	st.commit(mesh)
	mesh.surface_set_material(2, cap)
	_rim_cache[key] = mesh
	return mesh


func _build_wheels() -> void:
	var w: PackedFloat32Array = sim.wheels(slot)
	for i in range(0, w.size(), 11):
		var pos := Vector3(w[i], w[i + 1], w[i + 2])
		var steer := w[i + 3] > 0.5
		var width_raw := w[i + 4]
		var radius := 12.557 * w[i + 5] * S
		var width := maxf(8.0 * absf(width_raw) * S, 0.08)
		var node := Node3D.new()
		node.position = pos
		var spin := Node3D.new()
		node.add_child(spin)
		var tire := MeshInstance3D.new()
		var cyl := CylinderMesh.new()
		cyl.top_radius = radius; cyl.bottom_radius = radius; cyl.height = width
		cyl.radial_segments = 28; cyl.rings = 1
		tire.mesh = cyl
		tire.rotation_degrees = Vector3(0, 0, 90)
		tire.material_override = _tire_material()
		spin.add_child(tire)
		# rim on the outer face, in the model's rim colour
		var outward := -1.0 if width_raw >= 0 else 1.0
		var rim_r := minf(radius * 0.95, maxf(0.02, 10.0 * w[i + 9] * S))
		var rim := MeshInstance3D.new()
		rim.mesh = _rim_mesh(rim_r, Color8(int(w[i + 6]), int(w[i + 7]), int(w[i + 8])), 5 + slot % 3)
		rim.position = Vector3(outward * (width / 2.0 + 0.002), 0, 0)
		rim.scale = Vector3(outward, 1, 1) # built facing +x; mirror for the left side
		spin.add_child(rim)
		add_child(node)
		wheels.append({node = node, spin = spin, steer = steer})


## state: interpolated pose; speed in NFM units per tick.
func update_pose(t: Transform3D, wxz: float, speed: float, dt: float, lean: Vector3) -> void:
	transform = t
	body.transform = Transform3D(Basis.from_euler(Vector3(deg_to_rad(lean.y), 0, deg_to_rad(lean.z))), Vector3(0, lean.x, 0))
	spin_angle -= (20.0 * speed) / (154.0 * simag) * 25.0 * dt
	var steer_basis := Meshes.pose(0, 0, 0, wxz, 0, 0).basis
	for w in wheels:
		w.node.basis = steer_basis if w.steer else Basis()
		w.spin.rotation.x = deg_to_rad(spin_angle)


func set_damage(d: float, fixing: bool) -> void:
	body_mat.set_shader_parameter("damage", d)
	glass_mat.set_shader_parameter("damage", d)
	body_mat.set_shader_parameter("fix_glow", 0.8 if fixing else 0.0)


## A wasted car's lights die with it (in the original they stayed on).
func set_lights(brake: bool, night: bool, dead := false) -> void:
	lightb_mat.set_shader_parameter("intensity", 0.0 if dead else (2.2 if brake else 0.35))
	lightf_mat.set_shader_parameter("intensity", 0.0 if dead else (2.0 if night else 0.5))


## Re-reads the dented geometry from the simulation.
## A wasted car burns: flames licking up out of the body and a flickering
## orange light. The flames keep moving even while the killcam is frozen.
var fire: GPUParticles3D
var fire_light: OmniLight3D
var embers: GPUParticles3D


func ignite() -> void:
	if fire:
		return
	var b := body_bounds()
	var top := b.position.y + b.size.y * 0.7
	var tex := _soft_dot()
	fire = GPUParticles3D.new()
	fire.amount = 160
	fire.lifetime = 0.9
	fire.local_coords = false
	var pm := ParticleProcessMaterial.new()
	pm.emission_shape = ParticleProcessMaterial.EMISSION_SHAPE_BOX
	pm.emission_box_extents = Vector3(b.size.x * 0.32, 0.12, b.size.z * 0.3)
	pm.direction = Vector3.UP
	pm.spread = 12.0
	pm.initial_velocity_min = 1.4
	pm.initial_velocity_max = 3.0
	pm.gravity = Vector3(0, 2.0, 0)
	pm.scale_min = 0.7
	pm.scale_max = 1.5
	var sc := CurveTexture.new()
	var curve := Curve.new()
	curve.add_point(Vector2(0, 0.55)); curve.add_point(Vector2(0.25, 1.0)); curve.add_point(Vector2(1, 0.1))
	sc.curve = curve
	pm.scale_curve = sc
	var ramp := GradientTexture1D.new()
	var g := Gradient.new()
	# hot yellow core, orange, deep red, then dark smoke (mixed, not added, so
	# the flames stay orange against bright ground and sky)
	g.set_color(0, Color(1.0, 0.85, 0.3, 0.95))
	g.add_point(0.2, Color(1.0, 0.45, 0.06, 0.95))
	g.add_point(0.5, Color(0.85, 0.16, 0.02, 0.85))
	g.add_point(0.75, Color(0.25, 0.08, 0.04, 0.6))
	g.set_color(g.get_point_count() - 1, Color(0.08, 0.07, 0.07, 0.0))
	ramp.gradient = g
	pm.color_ramp = ramp
	fire.process_material = pm
	var q := QuadMesh.new()
	q.size = Vector2(1.3, 1.3)
	var mat := StandardMaterial3D.new()
	mat.shading_mode = BaseMaterial3D.SHADING_MODE_UNSHADED
	mat.blend_mode = BaseMaterial3D.BLEND_MODE_MIX
	mat.transparency = BaseMaterial3D.TRANSPARENCY_ALPHA
	mat.billboard_mode = BaseMaterial3D.BILLBOARD_PARTICLES
	mat.vertex_color_use_as_albedo = true
	mat.albedo_texture = tex
	mat.disable_receive_shadows = true
	q.material = mat
	fire.draw_pass_1 = q
	fire.position = Vector3(b.get_center().x, top, b.get_center().z)
	body.add_child(fire)
	# a few glowing embers thrown up out of the flames
	embers = GPUParticles3D.new()
	embers.amount = 30
	embers.lifetime = 1.6
	embers.local_coords = false
	var em: ParticleProcessMaterial = pm.duplicate()
	em.initial_velocity_min = 2.5
	em.initial_velocity_max = 5.0
	em.spread = 35.0
	em.gravity = Vector3(0, -2.0, 0)
	em.scale_min = 0.08
	em.scale_max = 0.16
	em.scale_curve = null
	embers.process_material = em
	embers.draw_pass_1 = q
	embers.position = fire.position
	body.add_child(embers)
	fire_light = OmniLight3D.new()
	fire_light.light_color = Color(1.0, 0.55, 0.2)
	fire_light.omni_range = 9.0
	fire_light.light_energy = 3.0
	fire_light.position = fire.position + Vector3.UP * 0.8
	body.add_child(fire_light)


func flicker() -> void:
	if fire_light:
		var t := Time.get_ticks_msec() / 1000.0
		fire_light.light_energy = 2.6 + sin(t * 23.0) * 0.6 + sin(t * 37.0 + 1.3) * 0.4 + randf() * 0.6


static var _dot: Texture2D


static func _soft_dot() -> Texture2D:
	if _dot == null:
		var g := Gradient.new()
		g.set_color(0, Color(1, 1, 1, 1))
		g.set_color(1, Color(1, 1, 1, 0))
		var t := GradientTexture2D.new()
		t.gradient = g
		t.fill = GradientTexture2D.FILL_RADIAL
		t.fill_from = Vector2(0.5, 0.5)
		t.fill_to = Vector2(0.5, 0.0)
		t.width = 64
		t.height = 64
		_dot = t
	return _dot


func sync_damage() -> void:
	if index >= 0:
		_apply_parts(sim.race_car_mesh(index), false)
