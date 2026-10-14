## Builds Godot meshes from the native mesh builder's part arrays and holds
## the shared materials.
class_name Meshes
extends RefCounted

enum { BODY, GLASS, LIGHT_F, LIGHT_B, FINISH }

const S := 1.0 / 50.0

static var stage_shader: Shader = preload("res://shaders/stage.gdshader")
static var body_shader: Shader = preload("res://shaders/car_body.gdshader")
static var glass_shader: Shader = preload("res://shaders/glass.gdshader")
static var light_shader: Shader = preload("res://shaders/light.gdshader")
static var _stage_mat: ShaderMaterial


static func stage_material() -> ShaderMaterial:
	if _stage_mat == null:
		_stage_mat = ShaderMaterial.new()
		_stage_mat.shader = stage_shader
	return _stage_mat


## parts: [[part, verts, normals, colours, custom0], ...] -> {part: ArrayMesh}
static func build(parts: Array) -> Dictionary:
	var out := {}
	for p in parts:
		var arrays := []
		arrays.resize(Mesh.ARRAY_MAX)
		arrays[Mesh.ARRAY_VERTEX] = p[1]
		arrays[Mesh.ARRAY_NORMAL] = p[2]
		arrays[Mesh.ARRAY_COLOR] = p[3]
		arrays[Mesh.ARRAY_CUSTOM0] = p[4]
		var m := ArrayMesh.new()
		m.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays, [], {}, Mesh.ARRAY_CUSTOM_RGBA_FLOAT << Mesh.ARRAY_FORMAT_CUSTOM0_SHIFT)
		out[int(p[0])] = m
	return out


## Replaces the vertex data of an existing mesh built by build() (car damage).
static func rebuild(mesh: ArrayMesh, part: Array) -> void:
	var arrays := []
	arrays.resize(Mesh.ARRAY_MAX)
	arrays[Mesh.ARRAY_VERTEX] = part[1]
	arrays[Mesh.ARRAY_NORMAL] = part[2]
	arrays[Mesh.ARRAY_COLOR] = part[3]
	arrays[Mesh.ARRAY_CUSTOM0] = part[4]
	var mat := mesh.surface_get_material(0) if mesh.get_surface_count() > 0 else null
	mesh.clear_surfaces()
	mesh.add_surface_from_arrays(Mesh.PRIMITIVE_TRIANGLES, arrays, [], {}, Mesh.ARRAY_CUSTOM_RGBA_FLOAT << Mesh.ARRAY_FORMAT_CUSTOM0_SHIFT)
	if mat:
		mesh.surface_set_material(0, mat)


## NFM pose -> Godot transform: roll (xy), pitch (zy), yaw (xz), y down / z
## forward flipped into Godot's y up / -z forward.
static func pose(x: float, y: float, z: float, xz: float, xy: float, zy: float) -> Transform3D:
	var cy := cos(deg_to_rad(xz)); var sy := sin(deg_to_rad(xz))
	var cp := cos(deg_to_rad(zy)); var sp := sin(deg_to_rad(zy))
	var cr := cos(deg_to_rad(xy)); var sr := sin(deg_to_rad(xy))
	# R = Ry * Rp * Rr (NFM space)
	var a00 := cy; var a01 := -sy * sp; var a02 := -sy * cp
	var a10 := 0.0; var a11 := cp; var a12 := -sp
	var a20 := sy; var a21 := cy * sp; var a22 := cy * cp
	var r00 := a00 * cr + a01 * sr; var r01 := -a00 * sr + a01 * cr; var r02 := a02
	var r10 := a10 * cr + a11 * sr; var r11 := -a10 * sr + a11 * cr; var r12 := a12
	var r20 := a20 * cr + a21 * sr; var r21 := -a20 * sr + a21 * cr; var r22 := a22
	# conjugate by diag(1, -1, -1); Basis takes columns
	var b := Basis(Vector3(r00, -r10, -r20), Vector3(-r01, r11, r21), Vector3(-r02, r12, r22))
	return Transform3D(b, Vector3(x * S, -y * S, -z * S))


static func srgb(c: Array) -> Color:
	return Color8(int(c[0]), int(c[1]), int(c[2]))
