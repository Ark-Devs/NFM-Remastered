// Stage sky: the stage's own sky / haze colours as a gradient, a sun with a
// soft halo, and a layer of fair-weather clouds. Static (no TIME) so the
// reflection map is built once per stage. Forward+.
shader_type sky;

#include "res://shaders/noise.gdshaderinc"

group_uniforms colours;
uniform vec3 top_color : source_color = vec3(0.35, 0.55, 0.85);
uniform vec3 horizon_color : source_color = vec3(0.75, 0.82, 0.9);
uniform vec3 ground_color : source_color = vec3(0.4, 0.38, 0.35);
group_uniforms clouds;
uniform float cloud_cover : hint_range(0.0, 1.0) = 0.45;   // fraction of sky with cloud
uniform float cloud_scale = 1.6;                            // larger = smaller clouds
group_uniforms light;
uniform float night : hint_range(0.0, 1.0) = 0.0;
uniform float sun_size = 1400.0;                            // sharpness of the sun disc
uniform float halo = 0.35;                                  // glow around the sun
group_uniforms themes;
uniform float space = 0.0;       // Space Walk: black sky, deep star field, a planet
uniform float underwater = 0.0;  // Centrifugal Rush: rippling light from the surface above
uniform float vertigo = 0.0;     // Four Dimensional Vertigo: a bent, banded, impossible sky

void sky() {
	vec3 d = EYEDIR;
	float h = d.y;
	vec3 c = mix(horizon_color, top_color, smoothstep(-0.02, 0.5, h));
	c = mix(c, ground_color, smoothstep(0.0, -0.2, h));
	float sd = 0.0;
	if (LIGHT0_ENABLED) {
		sd = max(dot(d, LIGHT0_DIRECTION), 0.0);
		vec3 sun = LIGHT0_COLOR * (pow(sd, sun_size) * 18.0 + pow(sd, 10.0) * halo);
		c += sun * (1.0 - night * 0.85);
	}
	if (space > 0.0) {
		// a denser star field right down to the horizon, and a ringless blue planet
		vec3 g2 = floor(d * 420.0);
		float r2 = fract(sin(dot(g2, vec3(41.31, 17.77, 91.17))) * 24634.6345);
		c += vec3(0.8, 0.86, 1.0) * step(0.9975, r2) * (0.4 + 0.6 * fract(r2 * 53.0)) * space;
		vec3 pd = normalize(vec3(0.62, 0.22, -0.5));
		float pc = dot(d, pd);
		if (pc > 0.982) {
			vec3 q = normalize(d - pd * pc);
			float lat = dot(q, normalize(cross(pd, vec3(0.0, 1.0, 0.0))));
			vec3 land = mix(vec3(0.08, 0.22, 0.55), vec3(0.75, 0.82, 0.9), smoothstep(0.55, 0.75, nfm_fbm(vec3(lat * 6.0, pc * 40.0, 2.0))));
			float lit = LIGHT0_ENABLED ? clamp(dot(q, LIGHT0_DIRECTION) * 0.8 + 0.35, 0.05, 1.0) : 0.6;
			c = mix(c, land * lit, smoothstep(0.982, 0.9835, pc));
		}
		c += vec3(0.25, 0.45, 0.9) * pow(max(pc, 0.0), 220.0) * 0.6 * space;
	}
	if (underwater > 0.0 && h > 0.0) {
		// the surface far above: bright shifting bands of light, darker toward the horizon
		vec2 p = d.xz / (h + 0.15) * 2.2;
		float w = nfm_fbm(vec3(p * 2.0, 0.6)) * 0.6 + nfm_noise(vec3(p * 7.0, 2.2)) * 0.4;
		c += vec3(0.35, 0.75, 0.72) * smoothstep(0.5, 0.78, w) * smoothstep(0.15, 0.85, h) * 0.55 * underwater;
		c += vec3(0.5, 0.85, 0.8) * pow(max(h, 0.0), 6.0) * 0.35 * underwater;
	}
	if (vertigo > 0.0) {
		// twisted bands and a glowing grid on the horizon
		float a = atan(d.x, d.z);
		float band = sin(a * 5.0 + h * 14.0 + nfm_fbm(vec3(d * 3.0)) * 6.0);
		c = mix(c, mix(vec3(0.32, 0.08, 0.45), vec3(0.05, 0.55, 0.65), band * 0.5 + 0.5), 0.8 * vertigo * smoothstep(-0.1, 0.12, h));
		float gx = abs(fract(a * 6.0 / 3.14159) - 0.5);
		float gy = abs(fract(h * 22.0) - 0.5);
		c += vec3(0.9, 0.4, 1.0) * (smoothstep(0.47, 0.5, gx) + smoothstep(0.47, 0.5, gy)) * smoothstep(0.25, 0.0, abs(h)) * 0.35 * vertigo;
	}
	// night: stars and a moon (clouds drift over them)
	if (night > 0.0 && h > 0.0) {
		vec3 g = floor(d * 260.0);
		float r = fract(sin(dot(g, vec3(12.9898, 78.233, 37.719))) * 43758.5453);
		float star = step(0.9965, r) * smoothstep(0.0, 0.25, h);
		c += vec3(0.85, 0.9, 1.0) * star * (0.6 + 0.4 * fract(r * 97.0)) * night;
		vec3 moon_dir = normalize(vec3(-0.45, 0.55, -0.7));
		float md = max(dot(d, moon_dir), 0.0);
		c += vec3(0.85, 0.88, 0.95) * (smoothstep(0.99955, 0.9997, md) * 1.6 + pow(md, 60.0) * 0.12) * night;
	}
	// clouds: fbm projected on a high flat layer, fading toward the horizon
	if (h > 0.0) {
		vec2 p = d.xz / (h + 0.12) * cloud_scale;
		float n = nfm_fbm(vec3(p, 0.37) * 1.7) * 0.75 + nfm_noise(vec3(p * 6.0, 1.3)) * 0.25;
		float cover = smoothstep(0.62 - cloud_cover * 0.32, 0.86, n);
		cover *= smoothstep(0.0, 0.12, h);
		// lit tops, slightly darker bellies, brighter near the sun
		vec3 cloud = mix(vec3(0.78, 0.8, 0.84), vec3(1.0), smoothstep(0.55, 0.95, n));
		cloud = mix(cloud, horizon_color, 0.25) + vec3(1.0, 0.95, 0.85) * pow(sd, 6.0) * 0.4;
		cloud *= mix(1.0, 0.18, night);
		c = mix(c, cloud, cover * 0.88);
	}
	COLOR = c;
}
