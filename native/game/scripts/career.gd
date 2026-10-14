## Career: every stage and every car in one long climb, laid out in
## assets/career.json. Stages run from easiest to hardest over four tours;
## each stage has a boss car (the weakest cars come first, the strongest
## last), and you win the boss by winning its race: cross the line first or
## waste the whole field. Classic cars also turn up as menaces on the stages
## they made famous, where the original stage AI gives them their old habits.
class_name Career
extends RefCounted

const PATH := "user://career.json"
const LAYOUT := "res://assets/career.json"

var chapters: Array = []    # [{title, blurb, stages: [{index, key, rivals, menace, coach, style, pool}]}]
var starters: Array = []
var s := {}


func build(assets: Assets, sim: NfmSim) -> void:
	var stages: Array = assets.stages
	var layout = JSON.parse_string(FileAccess.get_file_as_string(LAYOUT))
	var car_of := {}
	for i in sim.car_count():
		var slot := sim.car_slot(i)
		car_of[sim.car_name(slot)] = slot
	var stage_of := {}
	for i in stages.size():
		stage_of[stages[i].name] = i
	var slots := func(names: Array) -> Array:
		var out := []
		for n in names:
			if car_of.has(n): out.append(car_of[n])
			else: push_warning("career: no car called " + n)
		return out
	starters = slots.call(layout.starters)
	chapters.clear()
	var used_stages := {}
	var used_cars := {}
	for c in starters: used_cars[c] = true
	for t in layout.tours:
		var ch := {title = t.title, blurb = t.blurb, stages = []}
		for e in t.stages:
			if not stage_of.has(e.name):
				push_warning("career: no stage called " + e.name)
				continue
			var i: int = stage_of[e.name]
			used_stages[i] = true
			var boss: Array = slots.call(e.boss)
			for b in boss: used_cars[b] = true
			ch.stages.append({index = i, key = stage_key(stages[i]), rivals = boss, menace = slots.call(e.get("menace", [])),
				coach = str(e.get("coach", "")), style = str(e.get("style", "")), pool = []})
		chapters.append(ch)
	# stages and cars added after the layout was written go in the last tour,
	# before its finale
	var last: Array = chapters[chapters.size() - 1].stages
	for i in stages.size():
		if not used_stages.has(i) and not stages[i].solo:
			last.insert(last.size() - 1, {index = i, key = stage_key(stages[i]), rivals = [], menace = [], coach = "", style = "", pool = []})
	for i in sim.car_count():
		var slot := sim.car_slot(i)
		if not used_cars.has(slot):
			for k in range(last.size() - 2, -1, -1):
				if last[k].rivals.size() < 2:
					last[k].rivals.append(slot)
					break
	# the cars you can have by each stage: the starters plus every boss before it
	var pool := starters.duplicate()
	for ch in chapters:
		for e in ch.stages:
			e.pool = pool.duplicate()
			pool.append_array(e.rivals)
	load_save()


## Coach Insano's line for a stage (by its index in the stage list), "" if none.
func coach_for(index: int) -> String:
	for ch in chapters:
		for e in ch.stages:
			if e.index == index: return e.coach
	return ""


static func stage_key(st: Dictionary) -> String:
	return ("c:" + st.file) if st.custom else ("o:%d" % st.id)


func fresh() -> Dictionary:
	return {v = 2, won = [], cars = starters.duplicate(), best = {}, chapter = 0, car = 0}


func load_save() -> void:
	s = fresh()
	if FileAccess.file_exists(PATH):
		var d = JSON.parse_string(FileAccess.get_file_as_string(PATH))
		if typeof(d) == TYPE_DICTIONARY and int(d.get("v", 0)) == 2:
			s = d
			s.cars = s.cars.map(func(x): return int(x))
			s.car = int(s.get("car", 0))
			s.chapter = int(s.get("chapter", 0))


func save() -> void:
	var f := FileAccess.open(PATH, FileAccess.WRITE)
	if f: f.store_string(JSON.stringify(s))


func reset() -> void:
	s = fresh()
	save()


func owns(slot: int) -> bool: return slot in s.cars


## Every car in the order the career gives them out: starters, then each stage's bosses.
func unlock_order() -> Array:
	var out := starters.duplicate()
	for ch in chapters:
		for e in ch.stages:
			for r in e.rivals:
				if r not in out: out.append(r)
	return out
func won(key: String) -> bool: return key in s.won


func chapter_open(c: int) -> bool:
	if c <= 0: return true
	var prev: Array = chapters[c - 1].stages
	return won(prev[prev.size() - 1].key)


func stage_open(c: int, k: int) -> bool:
	if not chapter_open(c): return false
	return k == 0 or won(chapters[c].stages[k - 1].key)


func frontier(c: int) -> int:
	var st: Array = chapters[c].stages
	var k := 0
	while k < st.size() - 1 and won(st[k].key): k += 1
	return k


func summary() -> String:
	if s.won.is_empty():
		return "Pick a starter car and get going."
	var c: int = clampi(s.chapter, 0, chapters.size() - 1)
	return "%s · Stage %d of %d · %d cars" % [chapters[c].title, frontier(c) + 1, chapters[c].stages.size(), s.cars.size()]


func source(slot: int) -> Dictionary:
	for c in chapters.size():
		var st: Array = chapters[c].stages
		for k in st.size():
			if slot in st[k].rivals:
				return {chapter = c, stage = k}
	return {}


func lock_text(slot: int, assets: Assets) -> String:
	var src := source(slot)
	if src.is_empty(): return "Locked"
	var e: Dictionary = chapters[src.chapter].stages[src.stage]
	return "Win it on %s (%s, stage %d). Win the race to take it." % [assets.stages[e.index].name, chapters[src.chapter].title, src.stage + 1]


## Grid slots from the front of the start: pole, the second row, the third,
## then slot 0 at the back (where the player starts).
const FRONT_FIRST := [6, 4, 5, 3, 1, 2, 0]


## The field: the boss, any classic menace, then cars from the same stretch of
## the career (the latest ones you could have by now), never twice. The
## bosses start at the front (two bosses: pole and the row behind), menaces
## right after them; the player starts at the back.
func grid_for(c: int, k: int, player: int) -> Array:
	var e: Dictionary = chapters[c].stages[k]
	var field := []
	for r in e.rivals + e.menace:
		if r != player and r not in field and field.size() < 6: field.append(r)
	var recent: Array = e.pool.slice(maxi(0, e.pool.size() - 9)).filter(func(x): return x != player and x not in field)
	recent.shuffle()
	var older: Array = e.pool.slice(0, maxi(0, e.pool.size() - 9)).filter(func(x): return x != player and x not in field)
	older.shuffle()
	for x in recent + older:
		if field.size() >= 6: break
		field.append(x)
	var n := field.size() + 1
	var grid := []
	grid.resize(n)
	grid[0] = player
	var slots := FRONT_FIRST.filter(func(s): return s != 0 and s < n)
	for i in field.size():
		grid[slots[i]] = field[i]
	return grid


func record(c: int, k: int, r: Dictionary, laptime: int, wrecked: Array) -> Dictionary:
	var e: Dictionary = chapters[c].stages[k]
	var out := {cars = [], first_win = false, best = false, chapter_done = false}
	var win: bool = r.kind == 3 or (r.kind == 1 and r.position == 1)
	if win:
		out.first_win = not won(e.key)
		if out.first_win: s.won.append(e.key)
		if r.kind == 1 and (not s.best.has(e.key) or laptime < s.best[e.key]):
			out.best = s.best.has(e.key)
			s.best[e.key] = laptime
		var st: Array = chapters[c].stages
		if out.first_win and k + 1 < st.size(): out.next = {chapter = c, stage = k + 1}
		if out.first_win and k + 1 == st.size():
			out.chapter_done = true
			if c + 1 < chapters.size(): out.next = {chapter = c + 1, stage = 0}
			else: out.game_done = true
	for slot in e.rivals:
		if owns(slot): continue
		if win:
			s.cars.append(slot)
			out.cars.append(slot)
	out.chapter = c
	save()
	return out


func earned_lines(out: Dictionary, assets: Assets, sim: NfmSim) -> Array:
	var lines := []
	if out.get("first_win", false) and out.has("next"):
		var n = out.next
		lines.append("STAGE UNLOCKED   " + assets.stages[chapters[n.chapter].stages[n.stage].index].name.to_upper())
	if out.get("chapter_done", false):
		lines.append("TOUR COMPLETE   " + chapters[out.chapter].title.to_upper())
	if out.get("game_done", false):
		lines.append("WOOOHOOO! YOU FINISHED THE GAME. YOU TRULY ARE A RADICAL GAMER!")
	for slot in out.get("cars", []):
		lines.append("NEW CAR   " + sim.car_name(slot).to_upper())
	if out.get("best", false):
		lines.append("NEW BEST TIME")
	return lines
