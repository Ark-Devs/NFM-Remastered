// Head / tail lamps: their own colour, glowing; brightens when lit (brakes, night).
shader_type spatial;
render_mode cull_disabled;

uniform float intensity = 0.6;

void fragment() {
	ALBEDO = COLOR.rgb * 0.5;
	ROUGHNESS = 0.2;
	EMISSION = COLOR.rgb * intensity;
}
