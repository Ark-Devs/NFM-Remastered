## Loads the game's data (models, sounds, stages, custom cars, music) from
## res://assets and feeds the models to the native simulation.
class_name Assets
extends RefCounted

## Left out of the game: 1 and 17 are repeats (17 is NFM2's remake of 5, 11 of 1),
## 28-32 repeat community stages, the rest are filler.
const HIDDEN_ORIGINALS := [1, 17, 28, 29, 30, 31, 32]
const HIDDEN_STAGES := ["Another Race In Nowhere", "Crazy Level", "Example Stage - with all the parts used in it",
	"Death Pit", "Fair Ground", "Farmer Teds Revenge", "MASSIVE", "Madness In The Sky", "Mind Breaker", "Need For DUI",
	"Quake Ownage", "Rocket Arena", "The Criss Cross Crash V2", "The Evil Clown", "The Frozen Freeway", "Bamboo Cascades"]
## Shown under a new name (by file name). Rainbow Road's ramps are pass-through,
## so it's a pure stamina run with no stunts to refill power.
const RENAMED := {"Rainbow Road": "The Stamina Run"}
const SOLO_STAGES := ["Bamboo Cascades", "Mind Breaker", "MASSIVE"]

var manifest: Dictionary
var sounds := {}            # name -> PackedByteArray (wav)
var stages: Array = []      # [{id, file, name, custom, solo, data}]


static func read_bytes(path: String) -> PackedByteArray:
	return FileAccess.get_file_as_bytes(path)


static func unzip(path: String) -> Dictionary:
	var out := {}
	var z := ZIPReader.new()
	if z.open(path) != OK:
		push_error("cannot open " + path)
		return out
	for f in z.get_files():
		out[f] = z.read_file(f)
	z.close()
	return out


func load_all(sim: NfmSim) -> void:
	manifest = JSON.parse_string(FileAccess.get_file_as_string("res://assets/manifest.json"))
	load_cars(sim)
	var snd := unzip("res://assets/sounds.zip")
	for name in snd:
		sounds[name.get_basename()] = snd[name]
	stages.clear()
	var nums: Array = []
	for f in manifest.stages:
		nums.append(int(f.get_basename()))
	nums.sort()
	for n in nums:
		if n in HIDDEN_ORIGINALS:
			continue
		var data := read_bytes("res://assets/stages/%d.txt" % n)
		var t: String = sim.stage_title(data)
		stages.append({id = n, file = "stages/%d.txt" % n, name = t if t != "" else "Stage %d" % n, custom = false, solo = false, data = data})
	for f in manifest.mystages:
		if f.get_basename() in HIDDEN_STAGES:
			continue
		var data := read_bytes("res://assets/mystages/" + f)
		var t: String = sim.stage_title(data)
		if t == "":
			t = f.get_basename()
		t = RENAMED.get(f.get_basename(), t)
		stages.append({id = -1, file = "mystages/" + f, name = t, custom = true, solo = t in SOLO_STAGES, data = data})


var _models := {}


## Every car into a sim, ready to race (also used for the killcam's replay sim).
func load_cars(sim: NfmSim) -> void:
	if _models.is_empty():
		_models = unzip("res://assets/models.zip")
	for name in _models:
		sim.add_model(name, _models[name])
	for f in manifest.mycars:
		sim.add_custom(f.trim_suffix(" C.rad"), read_bytes("res://assets/mycars/" + f))
	sim.build_world()
	# community-car balance overrides (same file for everyone, so online play stays in sync)
	if FileAccess.file_exists("res://assets/car_balance.json"):
		sim.apply_balance(FileAccess.get_file_as_string("res://assets/car_balance.json"))


func music(name: String) -> PackedByteArray:
	for f in manifest.music:
		if f.to_lower() == name.to_lower() + ".zip":
			var files := unzip("res://assets/music/" + f)
			for k in files:
				return files[k]
	return PackedByteArray()


var _stage_music = null


## Community stage -> track (assets/stage_music.json), by stage file name.
func stage_music() -> Dictionary:
	if _stage_music == null:
		_stage_music = {}
		if FileAccess.file_exists("res://assets/stage_music.json"):
			var d = JSON.parse_string(FileAccess.get_file_as_string("res://assets/stage_music.json"))
			if d is Dictionary: _stage_music = d
	return _stage_music


func custom_music(name: String) -> PackedByteArray:
	var path := "res://assets/mystages/mymusic/" + name + ".zip"
	if not FileAccess.file_exists(path):
		path = "res://assets/mystages/mymusic/" + name + ".mod.zip" # e.g. airy.mod.zip
	if not FileAccess.file_exists(path):
		return PackedByteArray()
	var files := unzip(path)
	for k in files:
		return files[k]
	return PackedByteArray()
