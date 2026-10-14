// Dark tinted glass (no visible interior): glossy, and frosted / cracked by damage.
shader_type spatial;
render_mode cull_disabled;

#include "res://shaders/noise.gdshaderinc"

uniform float damage = 0.0;
varying vec3 obj;

void vertex() {
	obj = VERTEX;
}

void fragment() {
	float c = clamp(damage, 0.0, 1.0);
	float n = nfm_fbm(obj * 9.0);
	float lines = 1.0 - smoothstep(0.0, 0.04, abs(n - 0.5));
	float lines2 = 1.0 - smoothstep(0.0, 0.03, abs(nfm_fbm(obj * 17.0 + 3.1) - 0.5));
	float frac = max(lines, lines2 * step(0.6, c)) * smoothstep(0.05, 0.45, c);
	ALBEDO = mix(vec3(0.004, 0.004, 0.006), vec3(0.55, 0.6, 0.62), frac * 0.6 + c * 0.15);
	ROUGHNESS = mix(0.04, 0.5, frac);
	METALLIC = 0.0;
	SPECULAR = 0.6;
	CLEARCOAT = 1.0;
	CLEARCOAT_ROUGHNESS = 0.02;
}
