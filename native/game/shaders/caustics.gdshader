// Underwater stage: dancing caustic light on everything that faces up. A
// full-screen pass that rebuilds each pixel's world position from the depth
// buffer, so it lies on the road, the ground and the cars alike. Forward+.
shader_type spatial;
render_mode unshaded, blend_add, depth_draw_never, depth_test_disabled, cull_disabled, fog_disabled, shadows_disabled;

#include "res://shaders/noise.gdshaderinc"

uniform sampler2D depth_tex : hint_depth_texture, filter_nearest;
uniform sampler2D normal_tex : hint_normal_roughness_texture, filter_nearest;
uniform vec3 tint : source_color = vec3(0.5, 0.95, 0.9);
uniform float strength = 0.45;
uniform float scale = 0.45;      // caustic cells per metre
uniform float reach = 200.0;     // metres before it fades out

void vertex() {
	POSITION = vec4(VERTEX.xy, 1.0, 1.0);
}

// caustic web: two drifting layers of ridged noise; where both ridges meet
// the light gathers into bright wavy lines
float ridge(vec3 p) { return 1.0 - abs(nfm_noise(p) * 2.0 - 1.0); }

float caustic(vec2 p, float t) {
	float a = ridge(vec3(p, t * 0.35));
	float b = ridge(vec3(p * 1.37 + 5.2, t * 0.28 + 3.0));
	return pow(a * b, 6.0);
}

void fragment() {
	float depth = texture(depth_tex, SCREEN_UV).r;
	if (depth <= 0.000001) discard; // the sky
	vec4 v = INV_PROJECTION_MATRIX * vec4(SCREEN_UV * 2.0 - 1.0, depth, 1.0);
	v.xyz /= v.w;
	vec3 world = (INV_VIEW_MATRIX * vec4(v.xyz, 1.0)).xyz;
	vec3 n = texture(normal_tex, SCREEN_UV).xyz * 2.0 - 1.0;
	vec3 nw = normalize((INV_VIEW_MATRIX * vec4(n, 0.0)).xyz);
	float up = smoothstep(0.15, 0.8, nw.y);
	float fade = 1.0 - smoothstep(reach * 0.35, reach, length(v.xyz));
	float c = caustic(world.xz * scale, TIME) + caustic(world.xz * scale * 0.5 + 7.0, TIME * 0.7) * 0.5;
	ALBEDO = tint * min(c, 1.5) * strength * up * fade;
}
