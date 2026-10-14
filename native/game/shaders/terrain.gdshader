// Ground: biome detail up close (snow sparkle, sand ripples, patchy grass,
// gravel) plus the stage's own ground "polys" patches.
shader_type spatial;

#include "res://shaders/noise.gdshaderinc"

uniform int biome = 3;   // 0 snow, 1 desert, 2 grass, 3 dirt / canyon
uniform vec3 polys_color : source_color = vec3(0.5);

varying vec3 wpos;
varying vec3 wnrm;

void vertex() {
	wpos = (MODEL_MATRIX * vec4(VERTEX, 1.0)).xyz;
	wnrm = normalize((MODEL_MATRIX * vec4(NORMAL, 0.0)).xyz);
}

void fragment() {
	vec3 p = wpos;
	float big = nfm_fbm(p * 0.006);
	float mid = nfm_fbm(p * 0.05);
	float fine = nfm_noise(p * 1.7) * 0.6 + nfm_noise(p * 6.0) * 0.4;
	float flatness = smoothstep(0.75, 0.95, wnrm.y);
	vec3 c = COLOR.rgb;
	c = mix(c, polys_color, smoothstep(0.45, 0.65, big) * 0.6 * flatness);
	float r = 0.95;
	if (biome == 0) {
		c *= 0.93 + mid * 0.1;
		float sp = step(0.985, nfm_noise(p * 90.0));
		c += sp * 0.35 * flatness;
		r = 0.7 - sp * 0.4;
	} else if (biome == 1) {
		float rip = sin(p.x * 3.2 + p.z * 1.1 + nfm_noise(p * 0.3) * 6.0) * 0.5 + 0.5;
		c *= 0.86 + rip * 0.1 * flatness + mid * 0.12;
	} else if (biome == 2) {
		float blades = nfm_noise(vec3(p.x * 9.0, 0.0, p.z * 9.0));
		vec3 dirt = c * vec3(1.05, 0.85, 0.65);
		c = mix(c, dirt, smoothstep(0.62, 0.8, mid) * 0.6);
		c *= 0.82 + blades * 0.25;
	} else {
		c *= 0.8 + fine * 0.3 + mid * 0.1;
	}
	c *= 0.9 + fine * 0.15;
	ALBEDO = c;
	ROUGHNESS = r;
}
