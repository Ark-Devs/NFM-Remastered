## Room list for players on the same network (Wi-Fi, LAN party).
##
## A public room's host announces it once a second on the local network; the
## room browser listens and lists what it hears. Rooms not heard from for a
## few seconds drop off the list. Private rooms are never announced.
##
## The online room list (Epic Online Services) will sit beside this one; the
## browser shows rooms from both.
class_name LanRooms
extends Node

const PORT := 47712
const EVERY := 1.0       # seconds between announcements
const FORGET := 4000     # ms without hearing a room before it leaves the list

var _out: PacketPeerUDP
var _in: PacketPeerUDP
var _clock := 0.0
var _ad := {}            # what this PC announces (empty = nothing)
var rooms := {}          # room code -> {info..., seen = msec}


## Host: start (or update) announcing this room.
func advertise(info: Dictionary) -> void:
	_ad = info
	if _out == null:
		_out = PacketPeerUDP.new()
		_out.set_broadcast_enabled(true)
		_out.set_dest_address("255.255.255.255", PORT)
	_clock = EVERY # announce straight away


func stop_advertising() -> void:
	_ad = {}
	if _out:
		_out.close()
		_out = null


## Browser: start listening. Returns false if this PC can't listen (another
## copy of the game is already browsing).
func listen() -> bool:
	if _in:
		return true
	_in = PacketPeerUDP.new()
	if _in.bind(PORT, "0.0.0.0") != OK:
		_in = null
		return false
	rooms.clear()
	return true


func stop_listening() -> void:
	if _in:
		_in.close()
		_in = null
	rooms.clear()


## Rooms heard recently, newest-joinable first.
func list() -> Array:
	var now := Time.get_ticks_msec()
	var out := []
	for k in rooms.keys():
		if now - int(rooms[k].seen) > FORGET:
			rooms.erase(k)
		else:
			out.append(rooms[k])
	out.sort_custom(func(a, b): return int(a.get("players", 0)) > int(b.get("players", 0)))
	return out


func _process(dt: float) -> void:
	if _out and not _ad.is_empty():
		_clock += dt
		if _clock >= EVERY:
			_clock = 0.0
			_out.put_packet(JSON.stringify(_ad).to_utf8_buffer())
	if _in:
		while _in.get_available_packet_count() > 0:
			var d = JSON.parse_string(_in.get_packet().get_string_from_utf8())
			if d is Dictionary and d.get("nfm", 0) == 1 and str(d.get("code", "")) != "":
				d.seen = Time.get_ticks_msec()
				rooms[str(d.code)] = d
