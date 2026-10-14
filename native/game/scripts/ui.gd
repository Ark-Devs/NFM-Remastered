## Shared look for every screen: fonts, colours and small widget builders.
class_name UI
extends RefCounted

const INK := Color(0.953, 0.949, 0.937)
const DIM := Color(0.953, 0.949, 0.937, 0.56)
const FAINT := Color(0.953, 0.949, 0.937, 0.3)
const ACCENT := Color(1.0, 0.353, 0.122)
const GOLD := Color(0.914, 0.769, 0.416)
const GLASS := Color(0.035, 0.04, 0.047, 0.62)
const GOOD := Color(0.35, 0.82, 0.48)
const BAD := Color(1.0, 0.3, 0.3)

static var _fonts := {}


static func font(name: String) -> FontFile:
	if not _fonts.has(name):
		_fonts[name] = load("res://fonts/%s.ttf" % name)
	return _fonts[name]


static func display() -> FontFile: return font("BarlowCondensed-ExtraBoldItalic")
static func display_bold() -> FontFile: return font("BarlowCondensed-BoldItalic")
static func cond() -> FontFile: return font("BarlowCondensed-SemiBold")
static func body() -> FontFile: return font("Barlow-Medium")
static func body_bold() -> FontFile: return font("Barlow-SemiBold")


static func label(text: String, f: Font, size: int, color := INK, spacing := 0) -> Label:
	var l := Label.new()
	l.text = text
	l.add_theme_font_override("font", f)
	l.add_theme_font_size_override("font_size", size)
	l.add_theme_color_override("font_color", color)
	if spacing != 0:
		var ls := LabelSettings.new()
		ls.font = f
		ls.font_size = size
		ls.font_color = color
		l.label_settings = ls
	l.mouse_filter = Control.MOUSE_FILTER_IGNORE
	return l


static func shadowed(l: Label, strength := 0.6) -> Label:
	l.add_theme_color_override("font_shadow_color", Color(0, 0, 0, strength))
	l.add_theme_constant_override("shadow_offset_x", 0)
	l.add_theme_constant_override("shadow_offset_y", 2)
	l.add_theme_constant_override("shadow_outline_size", 8)
	return l


static func panel_style(color := GLASS, border_left := 0, border_color := ACCENT) -> StyleBoxFlat:
	var s := StyleBoxFlat.new()
	s.bg_color = color
	s.border_width_left = border_left
	s.border_color = border_color
	s.content_margin_left = 16
	s.content_margin_right = 16
	s.content_margin_top = 10
	s.content_margin_bottom = 10
	return s


static func bar(width: float, height: float, fill: Color) -> Array:
	var bg := ColorRect.new()
	bg.color = Color(1, 1, 1, 0.12)
	bg.custom_minimum_size = Vector2(width, height)
	bg.mouse_filter = Control.MOUSE_FILTER_IGNORE
	var fg := ColorRect.new()
	fg.color = fill
	fg.size = Vector2(0, height)
	fg.mouse_filter = Control.MOUSE_FILTER_IGNORE
	bg.add_child(fg)
	return [bg, fg]


static func full_rect(c: Control) -> Control:
	c.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
	return c


static func fmt_time(ticks: int) -> String:
	var ms := ticks * 40
	return "%d:%02d.%02d" % [ms / 60000, (ms % 60000) / 1000, (ms % 1000) / 10]


static func ordinal(n: int) -> String:
	var s := "th"
	if n % 100 < 11 or n % 100 > 13:
		match n % 10:
			1: s = "st"
			2: s = "nd"
			3: s = "rd"
	return str(n) + s
