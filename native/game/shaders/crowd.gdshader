// Riot Coliseum: a packed grandstand. Each seat is a little coloured figure
// that bobs now and then; from the track it reads as a living crowd. Forward+.
shader_type spatial;

uniform vec3 concrete : source_color = vec3(0.42, 0.4, 0.38);
uniform float seat = 0.9;    // metres per seat along the stand
uniform float excite = 0.0;  // 0..1, raised for a moment after a big hit

varying vec3 wpos;
varying vec3 wnrm;

float hash(vec2 p) { return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453); }

void vertex() {
	wpos = (MODEL_MATRIX * vec4(VERTEX, 1.0)).xyz;
	wnrm = normalize((MODEL_MATRIX * vec4(NORMAL, 0.0)).xyz);
}

void fragment() {
	vec3 c = concrete;
	if (wnrm.y < 0.5) {
		// people on the risers, in rows along the stand (one of x / z is
		// constant on each face, so their sum runs along it)
		vec2 g = vec2(wpos.x + wpos.z, wpos.y * 0.75) / seat;
		float far = smoothstep(0.25, 0.6, fwidth(g.x));   // too small to draw: just a crowd colour
		vec2 cell = floor(g);
		vec2 f = fract(g);
		float r = hash(cell);
		vec3 shirt = 0.5 + 0.5 * cos(6.2831 * (r + vec3(0.0, 0.33, 0.67)));
		float bob = sin(TIME * (3.0 + r * 4.0) + r * 40.0) * (0.15 + excite * 0.35);
		float body = step(abs(f.x - 0.5), 0.28) * step(f.y, 0.75 + bob * 0.3);
		float head = step(length(f - vec2(0.5, 0.82 + bob * 0.3)), 0.16);
		float here = step(0.12, r);   // a few empty seats
		c = mix(c * 0.8, mix(shirt * 0.8, vec3(0.85, 0.66, 0.5), head), max(body, head) * here);
		c = mix(c, vec3(0.42, 0.36, 0.38), far);
	}
	ALBEDO = c;
	ROUGHNESS = 0.9;
}
