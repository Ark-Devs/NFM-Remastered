## Player preferences, saved to user://settings.json.
class_name Settings
extends RefCounted

const PATH := "user://settings.json"

var car := 14
var stage := 0
var opponents := 6
var quality := "high"
var render_scale := 0.0   # 3D resolution, 1.0 = the screen's own; 0 = Auto (set by GRAPHICS)
const RENDER_SCALES := [0.0, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]
var music := true
var impact := "weighty"
var player_name := ""
var volume := 0.8
var fullscreen := true
var camera := 0           # RaceView.CAMERAS index
var map := false          # race map shown
var fps := false          # frame rate counter shown (F12)
var stats_public := true  # others in the hall can see your online stats
var voice := "open"       # room voice chat: open (mic always on) | ptt (hold the talk key) | off

## Rebindable controls: action -> keys (physical keycodes). Menus always also
## accept arrows / Enter / Esc.
const ACTIONS := [
	["up", "Accelerate"], ["down", "Brake / reverse"], ["left", "Steer left"], ["right", "Steer right"],
	["handb", "Handbrake"], ["lookleft", "Look back (swing left)"], ["lookback", "Look back (swing right)"], ["camera", "Change camera"], ["arrow", "Arrow: track / cars"], ["map", "Show / hide map"], ["scores", "Scoreboard (hold)"], ["talk", "Push to talk"], ["pause", "Menu / pause"], ["mute", "Mute sound"],
]
const DEFAULT_KEYS := {
	up = [KEY_UP, KEY_W], down = [KEY_DOWN, KEY_S], left = [KEY_LEFT, KEY_A], right = [KEY_RIGHT, KEY_D],
	handb = [KEY_SPACE], lookleft = [KEY_Z], lookback = [KEY_X], camera = [KEY_C], arrow = [KEY_Q], map = [KEY_N], scores = [KEY_TAB], talk = [KEY_V], pause = [KEY_ESCAPE, KEY_P], mute = [KEY_M],
}
var keys: Dictionary = DEFAULT_KEYS.duplicate(true)


## Is any key of this action held down?
func held(action: String) -> bool:
	for k in keys.get(action, []):
		if Input.is_physical_key_pressed(k):
			return true
	return false


## Which action a key triggers ("" if none).
func action_for(code: int) -> String:
	for a in keys:
		if code in keys[a]:
			return a
	return ""


## Puts a key on an action as its main key; it leaves any other action.
func bind(action: String, code: int) -> void:
	for a in keys:
		keys[a] = keys[a].filter(func(k): return k != code)
	var list: Array = keys.get(action, []).duplicate()
	if list.is_empty():
		list.append(code)
	else:
		list[0] = code
	keys[action] = list
	save()


func reset_keys() -> void:
	keys = DEFAULT_KEYS.duplicate(true)
	save()


static func key_names(list: Array) -> String:
	var names := []
	for k in list:
		names.append(OS.get_keycode_string(k))
	return "  /  ".join(names) if not names.is_empty() else "—"


func load_save() -> void:
	if not FileAccess.file_exists(PATH):
		player_name = OS.get_environment("USERNAME").substr(0, 16)
		return
	var d = JSON.parse_string(FileAccess.get_file_as_string(PATH))
	if typeof(d) != TYPE_DICTIONARY:
		return
	car = int(d.get("car", car))
	stage = int(d.get("stage", stage))
	opponents = int(d.get("opponents", opponents))
	quality = str(d.get("quality", quality))
	render_scale = minf(1.0, float(d.get("render_scale", render_scale)))
	music = bool(d.get("music", music))
	impact = str(d.get("impact", impact))
	player_name = str(d.get("player_name", player_name))
	volume = float(d.get("volume", volume))
	fullscreen = bool(d.get("fullscreen", fullscreen))
	camera = int(d.get("camera", camera))
	map = bool(d.get("map", map))
	fps = bool(d.get("fps", fps))
	voice = str(d.get("voice", voice))
	stats_public = bool(d.get("stats_public", stats_public))
	var saved = d.get("keys", {})
	if typeof(saved) == TYPE_DICTIONARY:
		for a in DEFAULT_KEYS:
			if saved.has(a) and typeof(saved[a]) == TYPE_ARRAY:
				keys[a] = saved[a].map(func(k): return int(k))
		# Z and X used to share one look-back; now each swings its own way
		if not saved.has("lookleft"):
			keys.lookback = keys.lookback.filter(func(k): return k != KEY_Z)
			if keys.lookback.is_empty(): keys.lookback = [KEY_X]
			if KEY_Z not in keys.values().reduce(func(a, b): return a + b, []): keys.lookleft = [KEY_Z]
		# Tab used to be the map key; it's the scoreboard now
		if keys.map == [KEY_TAB] and not saved.has("scores"):
			keys.map = [KEY_N]


func save() -> void:
	var f := FileAccess.open(PATH, FileAccess.WRITE)
	if f:
		f.store_string(JSON.stringify({car = car, stage = stage, opponents = opponents, quality = quality, render_scale = render_scale, music = music, impact = impact, player_name = player_name, volume = volume, fullscreen = fullscreen, camera = camera, map = map, fps = fps, voice = voice, stats_public = stats_public, keys = keys}))
