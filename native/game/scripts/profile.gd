## Your online career: what you've done in online races against other
## players (AI cars don't count), your level and title. Kept on this PC
## (user://profile.json). Shown to others in the hall only if you choose to
## make your stats public.
class_name Profile
extends RefCounted

const PATH := "user://profile.json"
## [level reached, title]
const TITLES := [[1, "Rookie"], [4, "Street Racer"], [8, "Waster"], [14, "Wrecker"], [20, "Speed Demon"],
	[30, "Madman"], [45, "Legend of Madness"]]

var races := 0
var wins := 0
var wastes := 0
var wrecked := 0          # times your car was wasted
var damage := 0.0         # damage dealt to other cars, in % of a car (100 = one whole car)
var xp := 0


func load_save() -> void:
	if not FileAccess.file_exists(PATH):
		return
	var d = JSON.parse_string(FileAccess.get_file_as_string(PATH))
	if not d is Dictionary:
		return
	races = int(d.get("races", 0))
	wins = int(d.get("wins", 0))
	wastes = int(d.get("wastes", 0))
	wrecked = int(d.get("wrecked", 0))
	damage = float(d.get("damage", 0.0))
	xp = int(d.get("xp", 0))


func save() -> void:
	var f := FileAccess.open(PATH, FileAccess.WRITE)
	if f:
		f.store_string(JSON.stringify(to_dict()))


func to_dict() -> Dictionary:
	return {races = races, wins = wins, wastes = wastes, wrecked = wrecked, damage = snappedf(damage, 0.1), xp = xp}


## XP for one online race: taking part, placing, wasting and hurting other
## players (wastes and damage here are against players only).
static func race_xp(place: int, players: int, wasted_others: int, dealt: float, was_wasted: bool) -> int:
	var x := 20
	if place == 1: x += 60
	elif place <= 3 and players > 2: x += 25
	x += wasted_others * 15
	x += int(minf(dealt, 300.0) / 10.0)
	if was_wasted: x -= 5
	return maxi(5, x)


## Count one online race (wastes and damage against players only). Returns the XP.
func record(place: int, players: int, wasted_others: int, dealt: float, was_wasted: bool) -> int:
	var gain := race_xp(place, players, wasted_others, dealt, was_wasted)
	races += 1
	if place == 1: wins += 1
	wastes += wasted_others
	damage += dealt
	if was_wasted: wrecked += 1
	xp += gain
	save()
	return gain


## Level from XP: each level needs a bit more than the last.
static func level_of(points: int) -> int:
	return 1 + int(floor(sqrt(points / 60.0)))


static func title_of(level: int) -> String:
	var t: String = TITLES[0][1]
	for row in TITLES:
		if level >= row[0]: t = row[1]
	return t


func level() -> int:
	return level_of(xp)


func title() -> String:
	return title_of(level())
