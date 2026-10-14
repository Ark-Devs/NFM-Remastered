// Beach stage: the sea beyond the track. A flat plane with moving ripples,
// sky-coloured at grazing angles, a sun glint, and a hole where the track is
// so it never floods the road. Forward+.
shader_type spatial;
render_mode cull_disabled, specular_schlick_ggx;

#include "res://shaders/noise.gdshaderinc"

uniform vec3 deep : source_color = vec3(0.03, 0.32, 0.42);
uniform vec3 shallow : source_color = vec3(0.16, 0.7, 0.68);
uniform vec4 hole = vec4(-300.0, 300.0, -300.0, 300.0);  // x0, x1, z0, z1 kept dry
uniform float shore = 60.0;                             // metres of shallow water off the track

varying vec3 wpos;

void vertex() {
	wpos = (MODEL_MATRIX * vec4(VERTEX, 1.0)).xyz;
}

void fragment() {
	float dx = max(max(hole.x - wpos.x, 0.0), wpos.x - hole.y);
	float dz = max(max(hole.z - wpos.z, 0.0), wpos.z - hole.w);
	float d = length(vec2(dx, dz));
	if (d <= 0.5) discard;
	vec2 p = wpos.xz * 0.06;
	float t = TIME * 0.25;
	float h1 = nfm_noise(vec3(p + vec2(t, t * 0.6), 0.0));
	float h2 = nfm_noise(vec3(p * 2.3 - vec2(t * 0.8, t), 3.0));
	// gentle ripples: strong normals turn the whole sea into glitter
	vec3 nrm = normalize(vec3((h1 - 0.5) * 0.12 + (h2 - 0.5) * 0.06, 1.0, (h2 - 0.5) * 0.12 - (h1 - 0.5) * 0.06));
	NORMAL = normalize((VIEW_MATRIX * vec4(nrm, 0.0)).xyz);
	vec3 col = mix(shallow, deep, smoothstep(0.0, shore * 3.0, d));
	// foam where the waves meet the sand
	float foam = smoothstep(8.0, 1.0, d + (h1 - 0.5) * 8.0);
	ALBEDO = mix(col, vec3(0.92, 0.95, 0.95), foam * 0.8);
	ROUGHNESS = mix(0.18, 0.6, foam);
	METALLIC = 0.0;
	SPECULAR = 0.35;
}
