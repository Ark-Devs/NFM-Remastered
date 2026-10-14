// Car paint: per-vertex (roughness, metalness, clearcoat) in CUSTOM0; damage
// adds scratches through to bare metal, soot and lost gloss.
shader_type spatial;
render_mode cull_disabled;

#include "res://shaders/noise.gdshaderinc"

uniform float damage = 0.0;
uniform float bottom_y = -0.6;   // body underside, for road grime
uniform float fix_glow = 0.0;
uniform float studio = 0.0;     // races: reflections of overhead strip lights, like the car studio

varying vec3 obj;
varying vec3 surf;

void vertex() {
	obj = VERTEX;
	surf = CUSTOM0.xyz;
}

void fragment() {
	vec3 c = COLOR.rgb;
	float n = nfm_fbm(obj * 6.0);
	float streak = nfm_noise(vec3(obj.x * 1.5, obj.y * 22.0, obj.z * 1.5));
	float d = clamp(damage, 0.0, 1.0);
	float lum0 = dot(c, vec3(0.2126, 0.7152, 0.0722));
	c = mix(vec3(lum0), c, 0.96);
	float scratch = smoothstep(0.62, 0.7, streak * 0.6 + n * 0.55) * smoothstep(0.05, 0.35, d);
	float soot = smoothstep(0.35, 0.9, n + d * 0.6 - 0.25) * smoothstep(0.3, 1.0, d);
	c = mix(c, vec3(0.62, 0.6, 0.58), scratch);
	c = mix(c, c * 0.18 + vec3(0.012, 0.01, 0.008), soot * 0.85);
	vec3 gp = vec3(abs(obj.x), obj.y, obj.z);
	float grime = smoothstep(bottom_y + 0.22, bottom_y + 0.02, obj.y + (nfm_fbm(gp * 4.0 + 7.0) - 0.5) * 0.06) * 0.45;
	c = mix(c, vec3(0.16, 0.13, 0.1), grime * 0.5);
	if (studio > 0.0) {
		// showroom paint: a touch richer, so stage haze and tinted sky light don't wash it out
		float l = dot(c, vec3(0.2126, 0.7152, 0.0722));
		c = max(mix(vec3(l), c, 1.0 + 0.12 * studio), vec3(0.0));
	}
	ALBEDO = c;
	float r = mix(surf.x, 0.75, clamp(d * 1.2, 0.0, 1.0));
	r = mix(r, 0.85, grime);
	r = mix(r, 0.35, scratch);
	ROUGHNESS = mix(r, 0.95, soot);
	METALLIC = mix(surf.y, 0.9, scratch) * (1.0 - soot);
	CLEARCOAT = surf.z * (1.0 - clamp(d * 1.5, 0.0, 1.0)) * (1.0 - scratch) * (1.0 - grime);
	CLEARCOAT_ROUGHNESS = 0.14;
	EMISSION = vec3(0.6, 0.85, 1.0) * fix_glow;
	if (studio > 0.0) {
		// the long strip lights and side panels the car studio's paint reflects,
		// so cars keep their showroom shine on any stage
		vec3 nw = normalize((INV_VIEW_MATRIX * vec4(NORMAL, 0.0)).xyz);
		vec3 vw = normalize((INV_VIEW_MATRIX * vec4(VIEW, 0.0)).xyz);
		vec3 rw = reflect(-vw, nw);
		float strips = 0.0;
		if (rw.y > 0.35) {
			vec2 sp = rw.xz / rw.y;
			for (int i = -1; i <= 1; i++) {
				strips += (1.0 - smoothstep(0.05, 0.09, abs(sp.x - float(i) * 0.45))) * step(abs(sp.y), 1.6);
			}
		}
		float side = smoothstep(0.75, 0.9, abs(rw.x)) * smoothstep(0.5, 0.0, abs(rw.y - 0.15));
		float fres = 0.25 + 0.75 * pow(1.0 - clamp(dot(NORMAL, VIEW), 0.0, 1.0), 3.0);
		float gloss = (1.0 - ROUGHNESS) * (0.4 + 0.6 * CLEARCOAT);
		EMISSION += c * 0.07 * studio * (1.0 - soot);
		EMISSION += (vec3(1.0) * strips * 0.55 + vec3(0.6, 0.65, 0.75) * side * 0.35) * fres * gloss * studio;
	}
}
