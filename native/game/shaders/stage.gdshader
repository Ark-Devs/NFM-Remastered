// Track pieces: vertex colour from the original models plus restrained,
// world-space surface detail chosen by CUSTOM0 = (roughness, metalness, kind).
// kind: 0 painted, 1 asphalt, 2 rock/dirt, 3 foliage, 4 metal, 5 road paint, 6 concrete
shader_type spatial;
render_mode cull_disabled;

#include "res://shaders/noise.gdshaderinc"

uniform float glow = 0.0;          // checkpoint / repair hoop highlight
uniform vec3 glow_color : source_color = vec3(1.0, 0.85, 0.4);

varying vec3 wpos;
varying vec3 surf;

void vertex() {
	wpos = (MODEL_MATRIX * vec4(VERTEX, 1.0)).xyz;
	surf = CUSTOM0.xyz;
}

void fragment() {
	vec3 c = COLOR.rgb;
	vec3 p = wpos;
	float rough = surf.x;
	float kind = surf.z;
	if (kind > 0.5 && kind < 1.5) {
		// asphalt: the stage grey pulled to real tarmac value, aggregate, patches, sealed cracks
		float l0 = dot(c, vec3(0.2126, 0.7152, 0.0722));
		c = mix(vec3(l0), c, 0.7) * 0.36;
		float grain = nfm_noise(p * 18.0) * 0.5 + nfm_noise(p * 53.0) * 0.5;
		float speck = smoothstep(0.78, 0.9, nfm_noise(p * 70.0));
		float patches = smoothstep(0.52, 0.6, nfm_fbm(p * 0.06 + 3.1));
		float crackN = abs(nfm_noise(p * 0.7 + 7.0) - 0.5);
		float crack = (1.0 - smoothstep(0.0, 0.006, crackN)) * smoothstep(0.6, 0.75, nfm_fbm(p * 0.2));
		float wear = nfm_fbm(p * 0.35 + 11.0);
		c *= 0.86 + grain * 0.22 + speck * 0.16;
		c *= mix(1.0, 0.8, patches);
		c *= 1.0 - crack * 0.22;
		c *= mix(0.88, 1.04, wear);
		rough = clamp(surf.x - grain * 0.08 - speck * 0.15 - crack * 0.25 - (1.0 - wear) * 0.1, 0.45, 1.0);
	} else if (kind > 4.5 && kind < 5.5) {
		float wearP = smoothstep(0.35, 0.75, nfm_fbm(p * 0.8 + 5.0)) * 0.55 + nfm_noise(p * 30.0) * 0.12;
		c = mix(c * 0.85, vec3(0.05), wearP * 0.6);
		rough = mix(0.55, 0.85, wearP);
	} else if (kind > 5.5) {
		float pores = nfm_noise(p * 35.0);
		float panel = nfm_noise(floor(p * 0.5) + 0.5);
		float streak = nfm_noise(vec3(p.x * 2.5, p.y * 0.15, p.z * 2.5));
		float grime = 1.0 - smoothstep(0.0, 1.2, p.y);
		c *= 0.9 + pores * 0.12 + (panel - 0.5) * 0.08;
		c *= 1.0 - smoothstep(0.55, 0.8, streak) * 0.18;
		c *= 1.0 - grime * 0.25;
		rough = clamp(surf.x + pores * 0.08, 0.7, 1.0);
	} else if (kind > 3.5 && kind < 4.5) {
		float brush = nfm_noise(vec3(p.x * 40.0, p.y * 2.0, p.z * 40.0));
		float ox = smoothstep(0.6, 0.8, nfm_fbm(p * 1.3));
		c *= 0.92 + brush * 0.1;
		c = mix(c, c * vec3(0.75, 0.68, 0.6), ox * 0.6);
		rough = clamp(surf.x + brush * 0.1 + ox * 0.35, 0.3, 0.9);
	} else if (kind > 1.5 && kind < 2.5) {
		float n = nfm_fbm(p * 0.9);
		float n2 = nfm_noise(p * 7.0);
		c *= 0.7 + n * 0.45 + n2 * 0.12;
		rough = 0.95;
	} else if (kind > 2.5 && kind < 3.5) {
		float n = nfm_fbm(p * 2.5);
		c *= 0.65 + n * 0.6;
		rough = 0.85;
	} else {
		float n = nfm_noise(p * 3.0) * 0.5 + nfm_noise(p * 11.0) * 0.5;
		c *= 0.9 + n * 0.16;
	}
	ALBEDO = c;
	ROUGHNESS = rough;
	METALLIC = surf.y;
	EMISSION = glow_color * glow;
}
