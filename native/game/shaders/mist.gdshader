// Misty stages: a layer of ground fog. Several of these sheets stacked a
// metre or two apart read as low drifting mist from a car's height; they fade
// out near the camera and in the distance. Forward+.
shader_type spatial;
render_mode unshaded, blend_mix, depth_draw_never, cull_disabled, shadows_disabled;

#include "res://shaders/noise.gdshaderinc"

uniform vec3 mist_color : source_color = vec3(0.85, 0.88, 0.95);
uniform float thickness = 0.55;   // most opaque a sheet gets
uniform float drift = 0.012;      // how fast the patches move

varying vec3 wpos;

void vertex() {
	wpos = (MODEL_MATRIX * vec4(VERTEX, 1.0)).xyz;
}

void fragment() {
	vec2 p = wpos.xz * 0.018 + vec2(TIME * drift, TIME * drift * 0.6);
	float n = nfm_fbm(vec3(p, wpos.y * 0.1));
	float a = smoothstep(0.35, 0.75, n) * thickness;
	float my_z = -VERTEX.z;
	// never a sheet right in front of the lens
	a *= smoothstep(6.0, 25.0, my_z);
	a *= 1.0 - smoothstep(300.0, 650.0, my_z);   // no visible sheet edge in the distance
	ALBEDO = mist_color;
	ALPHA = a;
}
