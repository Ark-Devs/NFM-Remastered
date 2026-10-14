## Epic Online Services: the online room list and relayed connections.
##
## Players sign in silently with a device login (no Epic account needed). A
## host's room is an Epic lobby carrying the room's settings as attributes, so
## the room browser can list rooms from anywhere. Races then run over Epic's
## peer-to-peer connections, which try a direct path first and fall back to
## Epic's relay servers when two players can't reach each other.
##
## If Epic can't be reached (no internet, a blocked country, Epic down) the
## game falls back to direct connections and IP room codes.
class_name Online
extends Node

const BUCKET := "nfm-remastered:1"   # every room of this game version
const SOCKET := "nfm"

signal ready_changed

var state := "off"        # off | starting | ready | failed
var user_id := ""         # this player's Epic product user id
var lobby: HLobby         # the room this player is in, if it's an Epic one
var _log_ok := false


func is_ready() -> bool:
	return state == "ready"


## Sign in to Epic (once). Safe to call again; returns true when ready.
func start_async(display_name: String) -> bool:
	if state == "ready":
		return true
	if state == "starting":
		await ready_changed
		return state == "ready"
	state = "starting"
	var cfg = JSON.parse_string(FileAccess.get_file_as_string("res://assets/eos.json"))
	if not cfg is Dictionary:
		return _fail("no Epic settings")
	var creds := HCredentials.new()
	for k in ["product_name", "product_version", "product_id", "sandbox_id", "deployment_id", "client_id", "client_secret"]:
		creds.set(k, str(cfg.get(k, "")))
	HLog.log_level = HLog.LogLevel.DEBUG if "--eoslog" in OS.get_cmdline_user_args() else HLog.LogLevel.ERROR
	if not await HPlatform.setup_eos_async(creds):
		return _fail("platform setup failed")
	# keep the same player id between launches: reuse this PC's device id
	# (tests running two copies on one PC ask for a fresh one with --eosfresh)
	if "--eosfresh" in OS.get_cmdline_user_args():
		EOS.Connect.ConnectInterface.delete_device_id(EOS.Connect.DeleteDeviceIdOptions.new())
		await IEOS.connect_interface_delete_device_id_callback
	var made := EOS.Connect.CreateDeviceIdOptions.new()
	made.device_model = "%s %s" % [OS.get_name(), OS.get_model_name()]
	EOS.Connect.ConnectInterface.create_device_id(made)
	var cr = await IEOS.connect_interface_create_device_id_callback
	if not EOS.is_success(cr) and cr.result_code != EOS.Result.DuplicateNotAllowed:
		return _fail("device id: %s" % EOS.result_str(cr))
	var opts := EOS.Connect.LoginOptions.new()
	opts.credentials = EOS.Connect.Credentials.new()
	opts.credentials.type = EOS.ExternalCredentialType.DeviceidAccessToken
	opts.credentials.token = null
	opts.user_login_info = EOS.Connect.UserLoginInfo.new()
	opts.user_login_info.display_name = display_name.substr(0, 32) if display_name.strip_edges() != "" else "Player"
	if not await HAuth.login_game_services_async(opts):
		return _fail("login failed")
	user_id = HAuth.product_user_id
	# a player can be in the hall and a room at once; Epic allows only one
	# lobby with presence, so none of ours use it
	HLobbies.presence_enabled = false
	state = "ready"
	ready_changed.emit()
	return true


func _fail(why: String) -> bool:
	push_warning("Epic online services unavailable: " + why)
	state = "failed"
	ready_changed.emit()
	return false


## Host: open an Epic lobby for a room. Returns the lobby id, or "" on failure.
func host_async(max_players: int, attrs: Dictionary) -> String:
	await leave_async()
	var o := EOS.Lobby.CreateLobbyOptions.new()
	o.bucket_id = BUCKET
	o.max_lobby_members = clampi(max_players, 2, 64)
	o.permission_level = EOS.Lobby.LobbyPermissionLevel.PublicAdvertised # every room is listed; a password locks it
	o.enable_join_by_id = true
	o.presence_enabled = false
	o.disable_host_migration = false # if the host leaves, the room moves to another player
	o.enable_rtc_room = true # the room's voice channel
	o.local_rtc_options = {flags = 0}
	o.local_user_id = user_id
	var l := await HLobbies.create_lobby_async(o)
	if l == null:
		return ""
	lobby = l
	await update_async(attrs)
	return l.lobby_id


## Host: change the room's listed attributes.
func update_async(attrs: Dictionary) -> void:
	if lobby == null or not lobby.is_valid():
		return
	for k in attrs:
		var v = attrs[k]
		lobby.add_attribute(k, v if typeof(v) in [TYPE_STRING, TYPE_INT, TYPE_BOOL, TYPE_FLOAT] else str(v))
	await lobby.update_async()


## Join a room's lobby by id. Returns the host's user id, or "" on failure.
func join_async(lobby_id: String) -> String:
	await leave_async()
	HLobbies.local_rtc_options = {flags = 0} # voice, no data channel
	var l = await HLobbies.join_by_id_async(lobby_id)
	if l == null:
		return ""
	lobby = l
	return l.owner_product_user_id


## Public rooms of this game. Each is a Dictionary of the room's attributes
## plus lobby_id, slots_free and owner.
func search_async() -> Array:
	var out := []
	var found = await HLobbies.search_by_bucket_id_async(BUCKET)
	if found == null:
		return out
	for l in found:
		var d := {lobby_id = l.lobby_id, owner = l.owner_product_user_id, slots_free = l.available_slots}
		for a in l.attributes:
			d[str(a.key).to_lower()] = a.value # Epic hands keys back in capitals
		out.append(d)
	return out


# ---------------------------------------------------------------- the hall
## The main lobby everyone online sits in: who's online, global chat, room
## news. It's an Epic lobby (up to 64 players; when one fills, another opens).
## A chat message rides on the sender's own lobby entry (a numbered "say"
## attribute); everyone else's game sees the entry change and shows it.
const HUB_BUCKET := "nfm-remastered-hall:1"
const HUB_SIZE := 64

signal hub_message(msg: Dictionary)   # {t = "chat"|"room"|"join"|"leave", n = name, ...}
signal hub_changed                    # members came or went

var hub: HLobby
var hub_log := []                     # recent hall lines, so returning to the hall shows them
var _hub_names := {}                  # product user id -> name, to spot arrivals and departures


func _ready() -> void:
	hub_message.connect(func(m):
		hub_log.append(m)
		if hub_log.size() > 80: hub_log.pop_front())


## Everyone online: the members of every hall.
func online_count_async() -> int:
	var found = await HLobbies.search_by_bucket_id_async(HUB_BUCKET)
	var n := 0
	if found:
		for l in found:
			n += l.max_members - l.available_slots
	if hub and hub.is_valid():
		n = maxi(n, hub.members.size())
	return n


func hub_join_async(name: String, car: int) -> bool:
	if hub and hub.is_valid():
		return true
	if not await start_async(name):
		return false
	_my_name = name
	_my_car = car
	var best := _pick_hall(await HLobbies.search_by_bucket_id_async(HUB_BUCKET), "", 0)
	var l: HLobby = null
	if best:
		l = await HLobbies.join_async(best)
	if l == null:
		var o := EOS.Lobby.CreateLobbyOptions.new()
		o.bucket_id = HUB_BUCKET
		o.max_lobby_members = HUB_SIZE
		o.permission_level = EOS.Lobby.LobbyPermissionLevel.PublicAdvertised
		o.enable_join_by_id = true
		o.presence_enabled = false
		o.disable_host_migration = false # the hall lives on when its first player leaves
		o.local_user_id = user_id
		l = await HLobbies.create_lobby_async(o)
	if l == null:
		return false
	await _enter_hall(l)
	_hub_merge_loop()
	return true


var _my_name := ""
var _my_car := 0


## The hall to be in: the busiest with room; between equally busy ones the
## oldest id, so players who started separate halls all pick the same one.
## Only halls better than the current one (id, members) count.
static func _pick_hall(found, current_id: String, current_members: int) -> HLobby:
	var best: HLobby = null
	var best_n := current_members
	var best_id := current_id
	if found == null:
		return null
	for l in found:
		if l.lobby_id == current_id or l.available_slots <= 0:
			continue
		var n: int = l.max_members - l.available_slots
		if n > best_n or (n == best_n and (best_id == "" or l.lobby_id < best_id)):
			best = l
			best_n = n
			best_id = l.lobby_id
	return best


func _enter_hall(l: HLobby) -> void:
	hub = l
	await _set_my_entry({name = _my_name, car = _my_car, stats = _my_stats()})
	_hub_names = _member_names()
	# don't replay what was said before we came in
	_seen_says = {}
	var says := _member_says()
	for id in says: _seen_says[id] = int(str(says[id]).get_slice("|", 0))
	hub.lobby_updated.connect(_on_hub_updated)
	hub_changed.emit()


## A new hall takes a few seconds to show up in searches, so two players
## arriving together can open separate halls. Every so often, move to a
## busier (or equally busy but older) hall if there is one: split halls merge.
func _hub_merge_loop() -> void:
	while hub and hub.is_valid():
		await get_tree().create_timer(15.0).timeout
		if hub == null or not hub.is_valid():
			return
		var target := _pick_hall(await HLobbies.search_by_bucket_id_async(HUB_BUCKET), hub.lobby_id, hub.members.size())
		if target == null or hub == null:
			continue
		var old := hub
		hub = null
		old.lobby_updated.disconnect(_on_hub_updated)
		await old.leave_async()
		var j = await HLobbies.join_async(target)
		if j == null:
			if await hub_join_async(_my_name, _my_car): return # start over
			return
		await _enter_hall(j)


## Our stats as others see them: empty when we keep them private.
func _my_stats() -> String:
	var main = get_parent()
	if not main.settings.stats_public:
		return ""
	var d: Dictionary = main.profile.to_dict()
	d.lvl = main.profile.level()
	return JSON.stringify(d)


## After a race or a privacy change: refresh what the hall knows about us.
func hub_refresh_stats() -> void:
	if hub and hub.is_valid():
		await _set_my_entry({stats = _my_stats()})


## Everyone in the hall: [{id, name, car, stats}] (stats empty if private)
func hub_members() -> Array:
	var out := []
	if hub == null or not hub.is_valid():
		return out
	for m in hub.members:
		var a: Dictionary = _mattr(m, "name")
		var c: Dictionary = _mattr(m, "car")
		var st: Dictionary = _mattr(m, "stats")
		var stats = JSON.parse_string(str(st.get("value", ""))) if not st.is_empty() and str(st.value) != "" else null
		out.append({id = m.product_user_id, name = str(a.get("value", "Player")), car = int(c.get("value", 0)), stats = stats if stats is Dictionary else {}})
	return out


func hub_say(msg: Dictionary) -> void:
	if hub == null or not hub.is_valid():
		return
	_say_seq += 1
	hub_message.emit(msg) # our own line shows straight away
	await _set_my_entry({say = "%d|%s" % [_say_seq, JSON.stringify(msg)]})


var _say_seq := 0
var _seen_says := {}                 # product user id -> last message number shown


## Save our own entry in the hall (name, car, latest message). Done as its own
## small update: bundled into the plugin's full lobby update, the hall's
## creator's entry never reached the others.
var _entry_busy := false

func _set_my_entry(values: Dictionary) -> bool:
	if hub == null or not hub.is_valid():
		return false
	while _entry_busy:
		await get_tree().process_frame
	_entry_busy = true
	var o := EOS.Lobby.UpdateLobbyModificationOptions.new()
	o.lobby_id = hub.lobby_id
	o.local_user_id = user_id
	var r = EOS.Lobby.LobbyInterface.update_lobby_modification(o)
	var ok := EOS.is_success(r)
	if ok:
		var mod: EOSGLobbyModification = r.lobby_modification
		for k in values:
			mod.add_member_attribute(k, values[k], EOS.Lobby.LobbyAttributeVisibility.Public)
		var u := EOS.Lobby.UpdateLobbyOptions.new()
		u.lobby_modification = mod
		EOS.Lobby.LobbyInterface.update_lobby(u)
		ok = EOS.is_success(await IEOS.lobby_interface_update_lobby_callback)
	_entry_busy = false
	return ok


func _member_says() -> Dictionary:
	var d := {}
	if hub == null or not hub.is_valid():
		return d
	for m in hub.members:
		var a: Dictionary = _mattr(m, "say")
		if not a.is_empty():
			d[m.product_user_id] = str(a.value)
	return d


func hub_leave_async() -> void:
	if hub == null:
		return
	var l := hub
	hub = null
	_hub_names = {}
	_seen_says = {}
	if l.is_valid():
		await l.leave_async()


## A member's attribute by name; Epic hands the names back in capitals.
static func _mattr(m: HLobbyMember, key: String) -> Dictionary:
	for a in m.attributes:
		if str(a.key).to_lower() == key:
			return a
	return {}


## Members whose name has arrived (a new member's name comes a moment after
## they do, so they're announced once it's here).
func _member_names() -> Dictionary:
	var d := {}
	if hub == null or not hub.is_valid():
		return d
	for m in hub.members:
		var a: Dictionary = _mattr(m, "name")
		if not a.is_empty():
			d[m.product_user_id] = str(a.value)
	return d


func _on_hub_updated() -> void:
	if hub == null:
		return
	var now := _member_names()
	for id in now:
		if not _hub_names.has(id) and id != user_id:
			hub_message.emit({t = "join", n = now[id]})
	for id in _hub_names:
		if not now.has(id):
			hub_message.emit({t = "leave", n = _hub_names[id]})
	_hub_names = now
	var says := _member_says()
	for id in says:
		if id == user_id:
			continue
		var raw: String = says[id]
		var seq := int(raw.get_slice("|", 0))
		if seq > int(_seen_says.get(id, 0)):
			_seen_says[id] = seq
			var d = JSON.parse_string(raw.substr(raw.find("|") + 1))
			if d is Dictionary: hub_message.emit(d)
	hub_changed.emit()


# --------------------------------------------------------------- voice
var _mic_on := false


## Room voice: is the room's voice channel up?
func voice_ready() -> bool:
	return lobby != null and lobby.is_valid() and lobby.rtc_room_name != "" and lobby.rtc_room_connected


## Send our microphone to the room or stop sending.
func set_mic(on: bool) -> void:
	if not voice_ready() or on == _mic_on:
		return
	_mic_on = on
	var o := EOS.RTCAudio.UpdateSendingOptions.new()
	o.local_user_id = user_id
	o.room_name = lobby.rtc_room_name
	o.audio_status = EOS.RTCAudio.AudioStatus.Enabled if on else EOS.RTCAudio.AudioStatus.Disabled
	EOS.RTCAudio.RTCAudioInterface.update_sending(o)


func mic_on() -> bool:
	return _mic_on and voice_ready()


## Product user ids of everyone talking right now.
func talking() -> Array:
	var out := []
	if lobby == null or not lobby.is_valid():
		return out
	for m in lobby.members:
		if m.rtc_state.get("is_talking", false):
			out.append(m.product_user_id)
	return out


func leave_async() -> void:
	_mic_on = false
	if lobby == null:
		return
	var l := lobby
	lobby = null
	if l.is_valid():
		if l.is_owner(user_id):
			await l.destroy_async()
		else:
			await l.leave_async()
