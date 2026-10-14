## Names a landed stunt the way the original games did ("Forward Loop!!",
## "Who are you again...?", "You're a superstar!!!") from what the car did in
## the air: loops (pitch), rollspins (roll), spins (yaw), tabletops and surfing.
class_name Stunts
extends RefCounted

## Power gained -> the grade on the end of the name.
const GRADES := [[35.0, "!"], [70.0, "!!"], [1e9, "!!!"]]
## The word under the name, by how much power the stunt was worth.
const PRAISE := [[25.0, "Nice"], [40.0, "Cool"], [55.0, "Radical"], [75.0, "Ripping"], [100.0, "Awesome"], [130.0, "Amazing"], [1e9, "Wicked"]]


## v: the stunt event's values [powerup, travxy, travzy, travxz, ftab, btab, rtab, surfer, power].
## Returns [name, praise].
static func describe(v: PackedFloat32Array) -> Array:
	var up := v[0]
	var roll := absf(v[1])
	var pitch := v[2]
	var spin := absf(v[3])
	var parts := []
	if absf(pitch) > 90.0:
		parts.append(_count(absf(pitch), ("Forward" if pitch > 0.0 else "Backward") + " Loop", "Massive " + ("Forward" if pitch > 0.0 else "Backward") + " Looping"))
	elif v[4] > 0.5 or v[5] > 0.5:
		parts.append("Tabletop")
	if roll > 90.0:
		parts.append(_count(roll, "Rollspin", "Massive Roll Spinning"))
	elif v[6] > 0.5 and not parts.has("Tabletop"):
		parts.append("Tabletop")
	if spin > 90.0:
		var deg := int(round(spin / 180.0)) * 180
		parts.append("900 and beyond" if deg >= 900 else str(maxi(180, deg)))
	if v[7] > 0.5:
		parts.append("Surf Style")
	var name := ""
	# the special ones fill the power bar on their own
	if up >= 200.0:
		name = "You're a superstar!!!"
	elif up >= 150.0:
		name = "What the...?" if absf(pitch) > 90.0 and roll > 90.0 else "Who are you again...?"
	else:
		if parts.is_empty(): parts.append("Off the lip")
		name = " + ".join(parts.slice(0, 2)) + _grade(up)
	return [name, _praise(up)]


static func _count(deg: float, one: String, massive: String) -> String:
	var n := maxi(1, int(round(deg / 360.0)))
	return [one, one, "Double " + one, "Triple " + one][n] if n < 4 else massive


static func _grade(up: float) -> String:
	for g in GRADES:
		if up < g[0]: return g[1]
	return "!"


static func _praise(up: float) -> String:
	for p in PRAISE:
		if up < p[0]: return p[1]
	return ""
