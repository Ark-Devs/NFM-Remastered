## Terrain and scenery around the track (port of remake/src/render/terrain.js
## and scatter.js). The physics ground is perfectly flat, so the terrain stays
## flat inside the track area and only rises into hills and mountains beyond
## it; scenery is kept off roads, ramps and pieces.
class_name Scenery
extends Node3D

const S := 1.0 / 50.0
const GROUND_Y := -250.0 / 50.0
## Terrain sits this far under the track surface so road pieces lying flat on
## the ground never fight it for depth.
const UNDER_ROAD := 0.12
const BIOME := {
	snow = {hills = 30.0, mountains = 520.0, rock = Color8(0x6b, 0x71, 0x78), peak = Color8(0xf4, 0xf7, 0xfb), low = null},
	desert = {hills = 18.0, mountains = 320.0, rock = Color8(0x9a, 0x6a, 0x45), peak = Color8(0xc9, 0x9b, 0x6a), low = null},
	grass = {hills = 34.0, mountains = 460.0, rock = Color8(0x6d, 0x6a, 0x62), peak = Color8(0xee, 0xf2, 0xf6), low = Color8(0x4f, 0x6b, 0x2e)},
	dirt = {hills = 26.0, mountains = 400.0, rock = Color8(0x6a, 0x5e, 0x52), peak = Color8(0xb8, 0xb2, 0xaa), low = null},
	canyon = {hills = 22.0, mountains = 340.0, rock = Color8(0x9c, 0x4f, 0x2e), peak = Color8(0xc8, 0x82, 0x5a), low = null},
	# themed stages (assets/stage_themes.json)
	sea = {hills = 26.0, mountains = 380.0, rock = Color8(0x3e, 0x5e, 0x62), peak = Color8(0x8f, 0xb0, 0xa4), low = Color8(0xb9, 0xa8, 0x7a)},
	moon = {hills = 14.0, mountains = 260.0, rock = Color8(0x55, 0x57, 0x5e), peak = Color8(0x9a, 0x9c, 0xa4), low = null},
}

var biome := "dirt"
var theme := ""          # a themed stage's look ("underwater", "space", ...), set before setup
var cfg: Dictionary
var x0 := -300.0
var x1 := 300.0
var z0 := -300.0
var z1 := 300.0
var cx := 0.0
var cz := 0.0
var noise := FastNoiseLite.new()
var ridged := FastNoiseLite.new()
var rng := RandomNumberGenerator.new()
var ground: Color        # sRGB stage ground colour
var polys: Color
var track := Rect2()     # where the road pieces are (metres, x/z), for themed set dressing
var walls := Rect2()     # the stage's boundary walls (metres, x/z)
var density := 1.0       # scenery amount (lower on low graphics)


## bounds: NFM minx maxx minz maxz
## A menu preview: seen from high above, so it uses a lighter ground mesh and
## half the small scenery, and builds over a couple of frames.
var stagger := false


func _attach(n: Node) -> void:
	add_child(n)


func setup(sim: NfmSim, env: PackedInt32Array, quality: String) -> void:
	ground = Color8(env[6], env[7], env[8])
	polys = Color8(env[9], env[10], env[11]) if env[12] != 0 else ground
	track = Rect2()
	biome = {"underwater": "sea", "space": "moon"}.get(theme, biome_of(ground))
	if theme == "underwater": ground = ground.lerp(Color8(0xb4, 0xa4, 0x7c), 0.55)    # sea-floor sand
	if theme == "space": ground = ground.lerp(Color8(0x74, 0x74, 0x7c), 0.75)         # moon dust
	if theme == "underwater" or theme == "space": polys = polys.lerp(ground, 0.7)
	cfg = BIOME[biome]
	density = {"low": 0.35, "medium": 0.65, "high": 1.0, "ultra": 1.3}.get(quality, 1.0)
	if stagger: density *= 0.5
	var m := 45.0
	x0 = env[31] * S - m; x1 = env[32] * S + m
	z0 = -env[34] * S - m; z1 = -env[33] * S + m
	if not (x1 > x0 and z1 > z0):
		x0 = -300; x1 = 300; z0 = -300; z1 = 300
	walls = Rect2(x0 + m, z0 + m, x1 - x0 - m * 2.0, z1 - z0 - m * 2.0)
	cx = (x0 + x1) * 0.5
	cz = (z0 + z1) * 0.5
	var seed := absi(env[31] * 7 + env[34] * 13 + env[6] * 31) + 1
	noise.seed = seed
	noise.noise_type = FastNoiseLite.TYPE_PERLIN
	noise.fractal_type = FastNoiseLite.FRACTAL_FBM
	noise.fractal_octaves = 5
	noise.frequency = 1.0
	ridged.seed = seed + 1
	ridged.noise_type = FastNoiseLite.TYPE_PERLIN
	ridged.fractal_type = FastNoiseLite.FRACTAL_RIDGED
	ridged.fractal_octaves = 5
	ridged.frequency = 1.0
	rng.seed = seed
	_occupancy(sim) # reads the stage, so it stays on the main thread
	_build_terrain()
	if stagger:
		await get_tree().process_frame
		if not is_inside_tree(): return # the preview was thrown away
	_scatter()


static func biome_of(c: Color) -> String:
	# HSL from the stage's ground colour (the originals are stylised: icy blues,
	# red rock, sand, green, purple night grounds)
	var mx := maxf(c.r, maxf(c.g, c.b))
	var mn := minf(c.r, minf(c.g, c.b))
	var l := (mx + mn) / 2.0
	var d := mx - mn
	var s := 0.0 if d == 0.0 else d / (1.0 - absf(2.0 * l - 1.0))
	var h := c.h * 360.0
	if h >= 175.0 and h <= 245.0 and l > 0.5: return "snow"
	if (h <= 28.0 or h >= 340.0) and s > 0.4: return "canyon"
	if h > 28.0 and h <= 62.0 and s > 0.2: return "desert"
	if h > 62.0 and h < 170.0 and s > 0.12: return "grass"
	return "dirt"


func _fbm(x: float, z: float) -> float:
	return noise.get_noise_2d(x, z) * 0.5 + 0.5


## Distance outside the flat track rectangle (0 inside).
func outside(x: float, z: float) -> float:
	var dx := maxf(maxf(x0 - x, 0.0), x - x1)
	var dz := maxf(maxf(z0 - z, 0.0), z - z1)
	return sqrt(dx * dx + dz * dz)


func height_at(x: float, z: float) -> float:
	var d := outside(x, z)
	if d <= 0.0:
		return GROUND_Y - UNDER_ROAD
	var hills: float = smoothstep(0.0, 110.0, d) * (_fbm(x * 0.004, z * 0.004) * cfg.hills * 1.3 + 2.5)
	if theme == "beach":
		# a low coast that drops under the sea, with the odd sand bar
		hills = smoothstep(0.0, 60.0, d) * (_fbm(x * 0.006, z * 0.006) * 5.0 - 3.2)
	var dunes := 0.0
	if biome == "desert" and theme != "beach":
		dunes = smoothstep(20.0, 200.0, d) * pow(_fbm(x * 0.008 + 3.0, z * 0.003), 2.0) * 26.0
	var r := ridged.get_noise_2d(x * 0.0011, z * 0.0011) * 0.5 + 0.5
	var mount: float = smoothstep(230.0, 1350.0, d) * (pow(r, 1.4) * cfg.mountains * 1.15 + 60.0)
	if theme == "beach":
		mount = smoothstep(700.0, 2200.0, d) * (pow(r, 1.4) * cfg.mountains + 20.0) - smoothstep(0.0, 400.0, d) * 2.0
	if biome == "canyon":
		var step := 55.0
		var t: float = mount / step
		mount = (floorf(t) + smoothstep(0.75, 1.0, t - floorf(t))) * step
	return GROUND_Y - UNDER_ROAD + hills + dunes + mount


func _build_terrain() -> void:
	var n := 150 if stagger else 260
	var half := maxf(3200.0, maxf(x1 - x0, z1 - z0) * 2.8)
	var pos := PackedVector3Array()
	pos.resize(n * n)
	for j in n:
		for i in n:
			var u := float(i) / (n - 1) * 2.0 - 1.0
			var v := float(j) / (n - 1) * 2.0 - 1.0
			# denser near the middle, coarser toward the horizon
			var x := cx + signf(u) * pow(absf(u), 1.8) * half
			var z := cz + signf(v) * pow(absf(v), 1.8) * half
			pos[j * n + i] = Vector3(x, height_at(x, z), z)
	var idx := PackedInt32Array()
	idx.resize((n - 1) * (n - 1) * 6)
	var k := 0
	for j in n - 1:
		for i in n - 1:
			var a := j * n + i
			# clockwise for Godot's front faces, seen from above
			idx[k] = a; idx[k + 1] = a + 1; idx[k + 2] = a + n
			idx[k + 3] = a + 1; idx[k + 4] = a + n + 1; idx[k + 5] = a + n
			k += 6
	# normals from the grid
	var nrm := PackedVector3Array()
	nrm.resize(n * n)
	for j in n:
		for i in n:
			var l := pos[j * n + maxi(i - 1, 0)]
			var r := pos[j * n + mini(i + 1, n - 1)]
			var d := pos[maxi(j - 1, 0) * n + i]
			var f := pos[mini(j + 1, n - 1) * n + i]
			nrm[j * n + i] = (f - d).cross(r - l).normalized()
	# colours: ground near, rock on slopes, snow / lighter on peaks
	var g := ground.srgb_to_linear()
	var rock: Color = cfg.rock.srgb_to_linear().lerp(g, 0.25)
	var peak: Color = cfg.peak.srgb_to_linear()
	var low: Color = cfg.low.srgb_to_linear().lerp(g, 0.5) if cfg.low != null else g * 0.85
	var cols := PackedColorArray()
	cols.resize(n * n)
	for v in n * n:
		var p := pos[v]
		var y := p.y - GROUND_Y
		var slope := 1.0 - nrm[v].y
		var d := outside(p.x, p.z)
		var c := g.lerp(low, smoothstep(30.0, 300.0, d) * 0.8)
		c = c.lerp(rock, smoothstep(0.12, 0.35, slope))
		if biome != "desert" and biome != "canyon":
			c = c.lerp(peak, smoothstep(200.0, 330.0, y + _fbm(p.x * 0.01, p.z * 0.01) * 60.0) * (1.0 - smoothstep(0.45, 0.7, slope) * 0.6))
		else:
			c = c.lerp(peak, smoothstep(80.0, 260.0, y) * 0.5)
		cols[v] = c
	var arrays := []
	arrays.resize(Mesh.ARRAY_MAX)
	arrays[Mesh.ARRAY_VERTEX] = pos
	arrays[Mesh.ARRAY_NORMAL] = nrm
	arrays[Mesh.ARRAY_COLOR] = cols
	arrays[Mesh.ARRAY_INDEX] = idx
	var mesh := ArrayMesh.new()
	mesh.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays)
	var mat := ShaderMaterial.new()
	mat.shader = preload("res://shaders/terrain.gdshader")
	mat.set_shader_parameter("biome", {"snow": 0, "desert": 1, "grass": 2, "dirt": 3, "canyon": 3, "sea": 1, "moon": 3}[biome])
	mat.set_shader_parameter("polys_color", polys.srgb_to_linear())
	mesh.surface_set_material(0, mat)
	var mi := MeshInstance3D.new()
	mi.mesh = mesh
	mi.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
	_attach(mi)


# ------------------------------------------------------------------ props
## Builds one prop mesh from primitive parts: [mesh, transform, colour fn].
func _prop(parts: Array, jitter := 0.0) -> ArrayMesh:
	var pos := PackedVector3Array()
	var nrm := PackedVector3Array()
	var cols := PackedColorArray()
	for part in parts:
		var arr: Array = (part[0] as PrimitiveMesh).get_mesh_arrays()
		var t: Transform3D = part[1]
		var colfn: Callable = part[2]
		var v: PackedVector3Array = arr[Mesh.ARRAY_VERTEX]
		var idx: PackedInt32Array = arr[Mesh.ARRAY_INDEX]
		var moved := {}
		for i in idx:
			var p := v[i]
			if jitter > 0.0:
				var key := p.snapped(Vector3(0.001, 0.001, 0.001))
				if not moved.has(key):
					moved[key] = Vector3(rng.randf() - 0.5, rng.randf() - 0.5, rng.randf() - 0.5) * jitter
				p += moved[key]
			p = t * p
			pos.append(p)
			cols.append(colfn.call(p))
	# flat normals (faceted, like hand-made low-poly props); flip to clockwise
	for i in range(0, pos.size(), 3):
		var a := pos[i]; var b := pos[i + 1]; var c := pos[i + 2]
		pos[i + 1] = c; pos[i + 2] = b
		var nn := (b - a).cross(c - a).normalized()
		nrm.append(nn); nrm.append(nn); nrm.append(nn)
	var arrays := []
	arrays.resize(Mesh.ARRAY_MAX)
	arrays[Mesh.ARRAY_VERTEX] = pos
	arrays[Mesh.ARRAY_NORMAL] = nrm
	arrays[Mesh.ARRAY_COLOR] = cols
	var m := ArrayMesh.new()
	m.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays)
	return m


static func _cyl(top: float, bottom: float, h: float, seg: int) -> CylinderMesh:
	var c := CylinderMesh.new()
	c.top_radius = top; c.bottom_radius = bottom; c.height = h
	c.radial_segments = seg; c.rings = 1
	return c


static func _ball(r: float, seg := 7) -> SphereMesh:
	var s := SphereMesh.new()
	s.radius = r; s.height = r * 2.0
	s.radial_segments = seg; s.rings = 4
	return s


func _pine(needles: Color, trunk: Color, snowy: bool) -> ArrayMesh:
	var snow := Color8(0xf0, 0xf3, 0xf7).srgb_to_linear()
	var parts := [[_cyl(0.12, 0.18, 1.6, 6), Transform3D(Basis(), Vector3(0, 0.8, 0)), func(_p): return trunk]]
	for i in 4:
		var r := 1.5 - i * 0.3
		var h := 2.0 - i * 0.25
		var y := 1.3 + i * 1.15
		parts.append([_cyl(0.0, r, h, 7), Transform3D(Basis(), Vector3(0, y + h / 2.0, 0)), func(p):
			var c: Color = needles * (0.7 + (p.y - y) / h * 0.45)
			if snowy and p.y - y > h * 0.35:
				c = c.lerp(snow, 0.75)
			return c])
	return _prop(parts)


func _broadleaf(leaf: Color, trunk: Color) -> ArrayMesh:
	var parts := [[_cyl(0.15, 0.25, 2.4, 6), Transform3D(Basis(), Vector3(0, 1.2, 0)), func(_p): return trunk]]
	for i in 3:
		parts.append([_ball(1.3 + rng.randf() * 0.5), Transform3D(Basis(), Vector3((rng.randf() - 0.5) * 1.2, 3.0 + i * 0.6, (rng.randf() - 0.5) * 1.2)), func(p): return leaf * (0.65 + (p.y - 2.5) * 0.15)])
	return _prop(parts, 0.35)


func _bush(leaf: Color) -> ArrayMesh:
	var parts := []
	for i in 3:
		parts.append([_ball(0.6 + rng.randf() * 0.4), Transform3D(Basis(), Vector3((rng.randf() - 0.5), 0.4 + rng.randf() * 0.2, (rng.randf() - 0.5))), func(p): return leaf * (0.6 + p.y * 0.4)])
	return _prop(parts, 0.25)


func _rock(col: Color, snowy := false) -> ArrayMesh:
	var snow := Color8(0xf2, 0xf5, 0xf8).srgb_to_linear()
	return _prop([[_ball(1.0, 6), Transform3D(Basis().scaled(Vector3(1, 0.6, 1)), Vector3(0, 0.25, 0)), func(p):
		if snowy and p.y > 0.45: return snow
		return col * (0.75 + p.y * 0.35)]], 0.5)


## Sea floor: a clump of kelp ribbons.
func _kelp(col: Color) -> ArrayMesh:
	var parts := []
	for i in 4:
		var h := 3.0 + rng.randf() * 4.0
		var b := Basis(Vector3.UP, rng.randf() * TAU) * Basis(Vector3.FORWARD, (rng.randf() - 0.5) * 0.3)
		parts.append([_cyl(0.05, 0.16, h, 4), Transform3D(b, Vector3((rng.randf() - 0.5) * 0.8, h / 2.0, (rng.randf() - 0.5) * 0.8)), func(p): return col * (0.55 + p.y / h * 0.6)])
	return _prop(parts, 0.15)


## Sea floor: a lump of coral, in one of a few reef colours.
func _coral(col: Color) -> ArrayMesh:
	var parts := []
	for i in 5:
		var r := 0.35 + rng.randf() * 0.4
		parts.append([_ball(r, 5), Transform3D(Basis().scaled(Vector3(1, 1.4, 1)), Vector3((rng.randf() - 0.5) * 1.2, r + rng.randf() * 0.6, (rng.randf() - 0.5) * 1.2)), func(p): return col * (0.6 + p.y * 0.3)])
	return _prop(parts, 0.3)


func _dead_shrub(col: Color) -> ArrayMesh:
	var parts := []
	for i in 7:
		var len := 0.6 + rng.randf() * 0.8
		var b := Basis(Vector3.UP, rng.randf() * TAU) * Basis(Vector3.FORWARD, (rng.randf() - 0.5) * 1.6)
		parts.append([_cyl(0.015, 0.035, len, 4), Transform3D(b, b * Vector3(0, len / 2.0, 0)), func(_p): return col])
	return _prop(parts)


func _cactus(col: Color) -> ArrayMesh:
	var parts := [[_cyl(0.22, 0.25, 3.2, 8), Transform3D(Basis(), Vector3(0, 1.6, 0)), func(_p): return col]]
	for s in [-1.0, 1.0]:
		var y := 1.4 + (0.3 if s > 0 else 0.0)
		parts.append([_cyl(0.15, 0.15, 1.1, 7), Transform3D(Basis(), Vector3(s * 0.55, y + 0.55, 0)), func(_p): return col * 0.9])
		parts.append([_cyl(0.15, 0.15, 0.4, 7), Transform3D(Basis(Vector3.FORWARD, PI / 2), Vector3(s * 0.35, y, 0)), func(_p): return col * 0.9])
	return _prop(parts)


func _tuft(base: Color, tip: Color) -> ArrayMesh:
	var pos := PackedVector3Array()
	var cols := PackedColorArray()
	var nrm := PackedVector3Array()
	for i in 9:
		var a := float(i) / 9.0 * TAU + rng.randf()
		var r := 0.05 + rng.randf() * 0.08
		var lean := 0.15 + rng.randf() * 0.2
		var h := 0.6 + rng.randf() * 0.5
		var b := Vector3(cos(a) * r, 0, sin(a) * r)
		var w := Vector3(-sin(a), 0, cos(a)) * 0.025
		pos.append_array([b - w, Vector3(cos(a) * lean, h, sin(a) * lean), b + w])
		cols.append_array([base, tip, base])
		var nn := Vector3(cos(a), 0.4, sin(a)).normalized()
		nrm.append_array([nn, nn, nn])
	var arrays := []
	arrays.resize(Mesh.ARRAY_MAX)
	arrays[Mesh.ARRAY_VERTEX] = pos
	arrays[Mesh.ARRAY_NORMAL] = nrm
	arrays[Mesh.ARRAY_COLOR] = cols
	var m := ArrayMesh.new()
	m.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays)
	return m


# -------------------------------------------------------------- placement
var _cell := 3.0
var _w := 0
var _h := 0
var _grid := PackedByteArray()


func _occupancy(sim: NfmSim) -> void:
	_w = int(ceil((x1 - x0) / _cell))
	_h = int(ceil((z1 - z0) / _cell))
	_grid = PackedByteArray()
	_grid.resize(_w * _h)
	var tr: PackedInt32Array = sim.trackers()
	for i in range(0, tr.size(), 4):
		var x := tr[i] * S
		var z := -tr[i + 1] * S
		var rx := tr[i + 2] * S + 2.0
		var rz := tr[i + 3] * S + 2.0
		if rx > 400.0 or rz > 400.0:
			continue # boundary walls
		_mark(x - rx, x + rx, z - rz, z + rz)
		var r := Rect2(x - rx, z - rz, rx * 2.0, rz * 2.0)
		track = r if track.size == Vector2.ZERO else track.merge(r)
	var objs: PackedFloat32Array = sim.stage_objects()
	for i in range(0, objs.size(), 16):
		if int(objs[i + 1]) & 4 != 0:
			continue
		var r := minf(30.0, objs[i + 15] * S * 0.6) + 1.0
		_mark(objs[i + 12] - r, objs[i + 12] + r, objs[i + 14] - r, objs[i + 14] + r)


func _mark(ax: float, bx: float, az: float, bz: float) -> void:
	var i0 := maxi(0, int((ax - x0) / _cell)); var i1 := mini(_w - 1, int((bx - x0) / _cell))
	var j0 := maxi(0, int((az - z0) / _cell)); var j1 := mini(_h - 1, int((bz - z0) / _cell))
	for j in range(j0, j1 + 1):
		for i in range(i0, i1 + 1):
			_grid[j * _w + i] = 1


func _free(x: float, z: float) -> bool:
	var i := int((x - x0) / _cell)
	var j := int((z - z0) / _cell)
	if i < 0 or j < 0 or i >= _w or j >= _h:
		return true
	return _grid[j * _w + i] == 0


## Transforms for `count` props: inside the course (off the track) or beyond it.
func _place(count: int, inside: bool, min_d := 8.0, max_d := 500.0, scale := Vector2(1, 1), patchy := false) -> Array:
	count = int(count * density)
	var out := []
	var tries := 0
	while out.size() < count and tries < count * 12:
		tries += 1
		var x: float
		var z: float
		if inside:
			x = lerpf(x0 + 45.0, x1 - 45.0, rng.randf())
			z = lerpf(z0 + 45.0, z1 - 45.0, rng.randf())
			if not _free(x, z):
				continue
		else:
			var a := rng.randf() * TAU
			var d := min_d + pow(rng.randf(), 1.6) * (max_d - min_d)
			x = cx + cos(a) * ((x1 - x0) / 2.0 + d)
			z = cz + sin(a) * ((z1 - z0) / 2.0 + d)
			if outside(x, z) < min_d:
				continue
		if patchy and _fbm(x * 0.03, z * 0.03) < 0.48:
			continue
		var s := lerpf(scale.x, scale.y, rng.randf())
		var b := Basis(Vector3.UP, rng.randf() * TAU).scaled(Vector3(s, s * (0.85 + rng.randf() * 0.3), s))
		out.append(Transform3D(b, Vector3(x, height_at(x, z) - 0.05, z)))
	return out


## Cells the scenery is cut into, so what's off screen (or outside the sun's
## shadow range) is skipped instead of drawn every frame.
const TILE := 120.0


## Tile size in use: --noopt (benchmark comparison) draws scenery the old way, one
## piece for the whole map.
static func tile() -> float:
	return 1.0e9 if "--noopt" in OS.get_cmdline_user_args() else TILE


func _add(mesh: ArrayMesh, transforms: Array, shadows: bool, two_sided := false) -> void:
	if transforms.is_empty():
		return
	var mat := StandardMaterial3D.new()
	mat.vertex_color_use_as_albedo = true
	mat.roughness = 0.92
	if two_sided:
		mat.cull_mode = BaseMaterial3D.CULL_DISABLED
	if "--scenerystats" in OS.get_cmdline_user_args():
		var tri := 0
		for si in mesh.get_surface_count(): tri += mesh.surface_get_array_len(si) / 3
		print("scenery: %d tris x %d = %d  shadows=%s" % [tri, transforms.size(), tri * transforms.size(), shadows])
	var cells := {}
	for t in transforms:
		var k := Vector2i(floori(t.origin.x / tile()), floori(t.origin.z / tile()))
		if not cells.has(k): cells[k] = []
		cells[k].append(t)
	for k in cells:
		var list: Array = cells[k]
		var mm := MultiMesh.new()
		mm.transform_format = MultiMesh.TRANSFORM_3D
		mm.mesh = mesh
		mm.instance_count = list.size()
		for i in list.size():
			mm.set_instance_transform(i, list[i])
		var mi := MultiMeshInstance3D.new()
		mi.multimesh = mm
		mi.material_override = mat
		mi.cast_shadow = GeometryInstance3D.SHADOW_CASTING_SETTING_ON if shadows else GeometryInstance3D.SHADOW_CASTING_SETTING_OFF
		_attach(mi)


func _lin(hex: int) -> Color:
	return Color.hex((hex << 8) | 0xff).srgb_to_linear()


func _scatter() -> void:
	var g := ground.srgb_to_linear()
	match biome:
		"sea":
			_add(_kelp(_lin(0x2f6a3a)), _place(1100, false, 6, 420, Vector2(0.8, 1.6)), true, true)
			_add(_kelp(_lin(0x4a7a2a)), _place(500, true, 10, 500, Vector2(0.5, 1.0), true), false, true)
			for c in [0xd8604a, 0xe0a040, 0xb05a9a]:
				_add(_coral(_lin(c)), _place(260, false, 4, 360, Vector2(0.8, 1.8)), true)
			_add(_rock(_lin(0x4e6a6a)), _place(1200, true, 8, 500, Vector2(0.08, 0.25)), false)
			_add(_rock(_lin(0x3e5a5c)), _place(80, false, 60, 700, Vector2(10.0, 26.0)), true)
		"moon":
			_add(_rock(_lin(0x6a6c74)), _place(2600, true, 8, 500, Vector2(0.08, 0.3)), false)
			_add(_rock(_lin(0x5a5c64)), _place(900, false, 4, 500, Vector2(0.8, 3.0)), true)
			_add(_rock(_lin(0x4e5058)), _place(120, false, 60, 800, Vector2(12.0, 30.0)), true)
		"grass":
			var t := _tuft(g * 0.55, g.lerp(_lin(0xb9d36a), 0.4))
			_add(t, _place(9000, true, 8, 500, Vector2(0.5, 1.0), true), false, true)
			_add(t, _place(5000, false, 8, 160, Vector2(0.6, 1.2)), false, true)
			_add(_pine(_lin(0x2f5a2c), _lin(0x5a3b22), false), _place(1000, false, 10, 260, Vector2(1.6, 3.2)), true)
			_add(_pine(_lin(0x2a5228), _lin(0x5a3b22), false), _place(950, false, 200, 900, Vector2(2.4, 4.2)), false)
			_add(_broadleaf(_lin(0x4f7a2e), _lin(0x5a3b22)), _place(750, false, 9, 300, Vector2(1.4, 2.6)), true)
			_add(_bush(_lin(0x3f6a2a)), _place(900, false, 3, 200, Vector2(1.0, 2.0)), true)
			_add(_rock(_lin(0x7a766c)), _place(70, false, 60, 700, Vector2(10.0, 26.0)), true)
		"desert", "canyon":
			_add(_tuft(_lin(0x8a6a3a), _lin(0xd8bf86)), _place(2500, true, 8, 500, Vector2(0.4, 0.8), true), false, true)
			_add(_rock(g * 0.7), _place(2500, true, 8, 500, Vector2(0.08, 0.22)), false)
			_add(_dead_shrub(_lin(0x6b4a2e)), _place(1000, false, 4, 400, Vector2(1.0, 2.0)), true)
			if biome == "desert":
				_add(_cactus(_lin(0x4f6b35)), _place(700, false, 5, 380, Vector2(1.0, 2.2)), true)
			# towering stone monoliths and outcrops on the horizon
			var stone := _lin(0x9c4f2e) if biome == "canyon" else _lin(0xa8744a)
			_add(_rock(stone), _place(90, false, 50, 800, Vector2(12.0, 34.0)), true)
		"snow":
			_add(_rock(_lin(0x5d636b), true), _place(1500, true, 8, 500, Vector2(0.06, 0.18)), false)
			_add(_pine(_lin(0x23402b), _lin(0x4a3424), true), _place(1400, false, 9, 300, Vector2(1.6, 3.4)), true)
			_add(_pine(_lin(0x1f3a27), _lin(0x4a3424), true), _place(1100, false, 250, 1000, Vector2(2.6, 4.4)), false)
			_add(_rock(_lin(0x5d636b), true), _place(70, false, 60, 700, Vector2(10.0, 28.0)), true)
		_:
			_add(_tuft(g * 0.5, g.lerp(_lin(0xa8a070), 0.4)), _place(4000, true, 8, 500, Vector2(0.4, 0.9), true), false, true)
			_add(_rock(g * 0.65), _place(1500, true, 8, 500, Vector2(0.08, 0.2)), false)
			_add(_bush(_lin(0x5b5a32)), _place(1400, false, 3, 350, Vector2(1.0, 2.0)), true)
			_add(_broadleaf(_lin(0x5d6b33), _lin(0x5a3b22)), _place(420, false, 9, 400, Vector2(1.3, 2.4)), true)
			_add(_rock(_lin(0x766a5c)), _place(70, false, 60, 700, Vector2(10.0, 24.0)), true)
	# boulders beyond the walls for every biome
	var rc := _lin(0x9c4f2e) if biome == "canyon" else _lin(0x9a6a45) if biome == "desert" else _lin(0x5d636b) if biome == "snow" else _lin(0x6d6a62)
	_add(_rock(rc, biome == "snow"), _place(800, false, 3, 500, Vector2(1.2, 5.5)), true)
