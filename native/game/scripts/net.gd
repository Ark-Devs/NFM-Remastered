## Online rooms with no game server.
##
## A room is an Epic lobby (listed in the hall) run from the host's PC; on a
## home network without Epic it's found by a LAN broadcast instead. Every
## packet is encrypted with AES-256 and authenticated with HMAC-SHA256 under
## keys from the room's own key, with sequence numbers against replays.
##
## Races run in lockstep: players only send their controls, every PC runs
## the same deterministic simulation, and state fingerprints are compared
## every second to catch a desync. See _begin for how controls travel.
class_name Net
extends Node

signal lobby_changed
signal status(text: String)
signal race_start(opts: Dictionary)
signal ended(reason: String)
signal back_to_lobby
signal chat(msg: Dictionary)       # a room chat line {n, m} or a note {note}

var room_log := []                  # this room's chat, so the screen can be rebuilt

const PORT := 47713
const MAX_PLAYERS := 8
const B32 := "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"   # no 0/O/1/I
enum { HELLO = 1, LOBBY, START, INPUT, FRAME, HASH, BYE, KICK, SETUP, READY, CHAT, RDY, VOTE, PING, PONG, PINGS, GONE }

var main
var peer: MultiplayerPeer       # ENet (direct) or Epic peer-to-peer
var transport := "enet"         # "eos" when the room runs through Epic
var _epic_attrs := {}           # what the Epic room list last got from us
var is_host := false
var active := false           # a networked race is running
var in_room := false
var room_key := PackedByteArray()
var enc_key := PackedByteArray()
var mac_key := PackedByteArray()
var send_seq := 0
var recv_seq := {}            # peer id -> last sequence number seen
var code := ""
var players := {}             # peer id -> {name, car, grid}
var my_name := ""
var my_car := 14
## The host's room settings, sent to everyone in the room.
var room_setup := default_setup()
var lan: LanRooms
## Auto-start: once a second player is in, the race starts by itself after
## AUTO_START seconds (the host can still start earlier).
const AUTO_START := 180.0
const ALL_READY_SECS := 5.0
var auto_start_secs := AUTO_START   # tests can shorten it with --autostart=SECONDS
var auto_at := 0              # host: msec when the race starts by itself (0 = not counting)
var auto_deadline := 0        # everyone: the same moment on this PC's clock
var upnp: UPNP
var upnp_ok := false
var pub_ip := ""           # how the host / we look from the internet
var pub_port := 0
var stun_ip := ""
var reply_code := ""       # joiner: send this to the host if direct joining fails
var _punches: Array = []
var _keepalive := 0
var _local_port := 0
var _attempts: Array = []
var _join_deadline := 0
var _connecting := false
var lan_ip := ""
var _thread: Thread

# lockstep
const MESH_SOCKET := "nfmrace"
var next_tick := 1            # next tick to simulate
var sent_tick := 0            # last tick we've sent our controls for
var delay := 3                # ticks between pressing a key and the race using it
var inbox := {}               # tick -> PackedInt32Array controls for every grid slot
var have := {}                # tick -> bit mask of slots whose controls are in
var humans_mask := 0
var gone_slots := {}          # slot -> last tick of that player's controls (they left)
var pending_gone := {}        # slot -> agreeing on that tick
var slot_uids := {}           # slot -> Epic user id
var slot_names := {}          # slot -> player name (kept after they leave)
var mesh: MultiplayerPeer      # direct connections to the other players (Epic)
var mesh_seq := {}
var host_gone := false
var direct_count := 0          # race messages that came straight from another player
var old_host_uid := ""
var my_grid := 0
var nplayers := 0
var hashes := {}              # tick -> {peer: hash}
var send_clock := 0.0
var desync_reported := false


func _exit_tree() -> void:
	_drop_peer() # unhook the connection before this node goes away


func setup(p_main) -> void:
	main = p_main
	lan = LanRooms.new()
	add_child(lan)
	for a in OS.get_cmdline_user_args():
		if a.begins_with("--autostart="):
			auto_start_secs = float(a.substr(12))


static func default_setup() -> Dictionary:
	return {name = "", mode = "race", stage = 0, ai = 3, fixes = -1, max_players = MAX_PLAYERS, password = ""}


## The room settings as other players see them: the password itself stays
## with the host, they only learn that the room is locked.
func _shared_setup() -> Dictionary:
	var s := room_setup.duplicate()
	s.erase("password")
	s.locked = str(room_setup.get("password", "")) != ""
	return s


static func _pw_hash(pw: String) -> String:
	return "" if pw == "" else Crypto.new().hmac_digest(HashingContext.HASH_SHA256, "nfm-room-password".to_utf8_buffer(), pw.to_utf8_buffer()).hex_encode()


## Seconds until the race starts by itself, or -1 when not counting down.
func auto_left() -> float:
	if auto_deadline == 0:
		return -1.0
	return maxf(0.0, (auto_deadline - Time.get_ticks_msec()) / 1000.0)


## Host: list this room on the local network and, through Epic, online.
func _advertise() -> void:
	if not is_host or not in_room or code == "":
		lan.stop_advertising()
		return
	var st: Dictionary = main.assets.stages[clampi(int(room_setup.stage), 0, main.assets.stages.size() - 1)]
	var info := {nfm = 1, v = 1, code = code, name = room_setup.name, host = my_name,
		mode = room_setup.mode, stage = st.name, players = players.size(),
		max = int(room_setup.max_players), ai = int(room_setup.ai), fixes = int(room_setup.fixes), racing = active,
		locked = str(room_setup.get("password", "")) != ""}
	lan.advertise(info)
	if transport == "eos" and info != _epic_attrs:
		_epic_attrs = info.duplicate()
		main.online.update_async(info)


# ------------------------------------------------------------------ crypto
func _derive(key: PackedByteArray) -> void:
	room_key = key
	enc_key = _sha256(key + "nfm-enc".to_utf8_buffer())
	mac_key = _sha256(key + "nfm-mac".to_utf8_buffer())


func _sha256(b: PackedByteArray) -> PackedByteArray:
	var h := HashingContext.new()
	h.start(HashingContext.HASH_SHA256)
	h.update(b)
	return h.finish()


func _seal(plain: PackedByteArray) -> PackedByteArray:
	var crypto := Crypto.new()
	var iv := crypto.generate_random_bytes(16)
	var pad := 16 - plain.size() % 16
	var p := plain.duplicate()
	for i in pad:
		p.append(pad)
	var aes := AESContext.new()
	aes.start(AESContext.MODE_CBC_ENCRYPT, enc_key, iv)
	var ct := aes.update(p)
	aes.finish()
	var body := iv + ct
	return body + crypto.hmac_digest(HashingContext.HASH_SHA256, mac_key, body)


func _open(pkt: PackedByteArray) -> PackedByteArray:
	if pkt.size() < 16 + 16 + 32 or (pkt.size() - 48) % 16 != 0:
		return PackedByteArray()
	var body := pkt.slice(0, pkt.size() - 32)
	var mac := pkt.slice(pkt.size() - 32)
	var crypto := Crypto.new()
	if not crypto.constant_time_compare(mac, crypto.hmac_digest(HashingContext.HASH_SHA256, mac_key, body)):
		return PackedByteArray()
	var aes := AESContext.new()
	aes.start(AESContext.MODE_CBC_DECRYPT, enc_key, body.slice(0, 16))
	var p := aes.update(body.slice(16))
	aes.finish()
	var pad := p[p.size() - 1] if p.size() > 0 else 0
	if pad < 1 or pad > 16:
		return PackedByteArray()
	return p.slice(0, p.size() - pad)


func _send(to: int, type: int, data: Variant) -> void:
	if peer == null:
		return
	send_seq += 1
	var msg := PackedByteArray()
	msg.resize(5)
	msg.encode_u32(0, send_seq)
	msg[4] = type
	msg.append_array(var_to_bytes(data))
	peer.set_target_peer(to)
	peer.set_transfer_mode(MultiplayerPeer.TRANSFER_MODE_RELIABLE)
	peer.put_packet(_seal(msg))


# -------------------------------------------------------------- room codes
static func _b32(bytes: PackedByteArray) -> String:
	var out := ""
	var bits := 0
	var acc := 0
	for b in bytes:
		acc = (acc << 8) | b
		bits += 8
		while bits >= 5:
			bits -= 5
			out += B32[(acc >> bits) & 31]
	if bits > 0:
		out += B32[(acc << (5 - bits)) & 31]
	var grouped := ""
	for i in out.length():
		if i > 0 and i % 6 == 0:
			grouped += "-"
		grouped += out[i]
	return grouped


static func _unb32(s: String) -> PackedByteArray:
	var out := PackedByteArray()
	var bits := 0
	var acc := 0
	for ch in s.to_upper():
		var v := B32.find(ch)
		if v < 0:
			continue
		acc = ((acc << 5) | v) & 0xFFFF
		bits += 5
		if bits >= 8:
			bits -= 8
			out.append((acc >> bits) & 255)
	return out


static func _ip_bytes(ip: String) -> PackedByteArray:
	var b := PackedByteArray([0, 0, 0, 0])
	var parts := ip.split(".")
	if parts.size() == 4:
		for i in 4:
			b[i] = int(parts[i])
	return b


static func _ip_str(b: PackedByteArray, o: int) -> String:
	return "%d.%d.%d.%d" % [b[o], b[o + 1], b[o + 2], b[o + 3]]


## Room code: public address + port, home-network address + port, room key.
func _make_code() -> String:
	var b := _ip_bytes(pub_ip) + PackedByteArray([pub_port >> 8, pub_port & 255])
	b += _ip_bytes(lan_ip) + PackedByteArray([PORT >> 8, PORT & 255])
	return _b32(b + room_key)


## Reply code (joiner -> host): the joiner's public address and port, with a
## short check so a mistyped code is caught.
func _make_reply(ip: String, port: int) -> String:
	var b := _ip_bytes(ip) + PackedByteArray([port >> 8, port & 255])
	var check := Crypto.new().hmac_digest(HashingContext.HASH_SHA256, mac_key, b).slice(0, 2)
	return _b32(b + check)


func _local_ip() -> String:
	for ip in IP.get_local_addresses():
		if ip.count(".") == 3 and not ip.begins_with("127.") and not ip.begins_with("169.254."):
			if ip.begins_with("192.168.") or ip.begins_with("10.") or ip.begins_with("172."):
				return ip
	return "127.0.0.1"


# -------------------------------------------------------------------- host
func host_room(name: String, car: int, setup := {}) -> void:
	leave()
	my_name = name
	my_car = car
	is_host = true
	room_setup = default_setup()
	room_setup.merge(setup, true)
	if str(room_setup.name).strip_edges() == "":
		room_setup.name = "%s's room" % name
	auto_at = 0
	auto_deadline = 0
	_derive(Crypto.new().generate_random_bytes(12))
	in_room = true
	players = {1: {name = name, car = car}}
	lan_ip = _local_ip()
	pub_ip = lan_ip
	pub_port = PORT
	code = ""
	status.emit("Setting up your room...")
	lobby_changed.emit()
	_host_start()


## Host: open the room through Epic if it can be reached, else directly.
func _host_start() -> void:
	if not "--noepic" in OS.get_cmdline_user_args() and await main.online.start_async(my_name):
		if await _host_eos():
			return
	if not in_room or not is_host:
		return
	# the router port (UPnP) and the public address (STUN) are found before
	# the room opens its port, so the STUN answer describes that exact port
	_thread = Thread.new()
	_thread.start(_host_probe.bind("--noupnp" in OS.get_cmdline_user_args()))


func _host_eos() -> bool:
	var lobby_id: String = await main.online.host_async(MAX_PLAYERS, {v = 1})
	if lobby_id == "" or not in_room or not is_host:
		return false
	var p := EOSGMultiplayerPeer.new()
	if p.create_server(Online.SOCKET) != OK:
		await main.online.leave_async()
		return false
	p.set_auto_accept_connection_requests(true)
	peer = p
	transport = "eos"
	peer.peer_connected.connect(_on_peer_connected)
	peer.peer_disconnected.connect(_on_peer_disconnected)
	code = _b32(lobby_id.hex_decode() + room_key)
	status.emit("Room open. Players anywhere can find it in the room list.")
	var st: Dictionary = main.assets.stages[clampi(int(room_setup.stage), 0, main.assets.stages.size() - 1)]
	main.online.hub_say({t = "room", n = my_name, room = room_setup.name, mode = room_setup.mode, stage = st.name,
		locked = str(room_setup.get("password", "")) != ""})
	_advertise()
	lobby_changed.emit()
	return true


func _host_probe(skip_upnp: bool) -> void:
	var u: UPNP = null
	var ok := false
	var ip := ""
	if not skip_upnp:
		u = UPNP.new()
		if u.discover(2000, 2, "InternetGatewayDevice") == UPNP.UPNP_RESULT_SUCCESS and u.get_gateway() and u.get_gateway().is_valid_gateway():
			if u.add_port_mapping(PORT, PORT, "Need for Madness", "UDP", 0) == UPNP.UPNP_RESULT_SUCCESS:
				ok = true
				ip = u.query_external_address()
	var mapped := {} if skip_upnp else Stun.query(PORT)
	call_deferred("_host_ready", ok, ip, u, mapped)


func _host_ready(ok: bool, ip: String, u: UPNP, mapped: Dictionary) -> void:
	if _thread:
		_thread.wait_to_finish()
		_thread = null
	if not in_room or not is_host:
		return
	upnp = u
	upnp_ok = ok
	peer = ENetMultiplayerPeer.new()
	if peer.create_server(PORT, MAX_PLAYERS - 1) != OK:
		status.emit("Couldn't open the game's network port (is another copy already hosting?)")
		peer = null
		return
	peer.peer_connected.connect(_on_peer_connected)
	peer.peer_disconnected.connect(_on_peer_disconnected)
	if ok and ip != "":
		pub_ip = ip
		pub_port = PORT
		status.emit("The online room list couldn't be reached, so only players on your network can see this room.")
	elif not mapped.is_empty():
		pub_ip = mapped.ip
		pub_port = mapped.port
		stun_ip = IP.resolve_hostname(Stun.SERVERS[0][0], IP.TYPE_IPV4)
		status.emit("The online room list couldn't be reached, so only players on your network can see this room.")
	else:
		status.emit("The online room list couldn't be reached, so only players on your network can see this room.")
	code = _make_code()
	_advertise()
	lobby_changed.emit()


## Host: a friend couldn't get in directly, so send packets toward their
## public address for a while. That opens a path through this router; their
## game is knocking from the other side at the same time.
func punch(reply: String) -> bool:
	var b := _unb32(reply)
	if b.size() < 8:
		return false
	var addr := b.slice(0, 6)
	var check := Crypto.new().hmac_digest(HashingContext.HASH_SHA256, mac_key, addr).slice(0, 2)
	if check != b.slice(6, 8):
		return false
	var ip := _ip_str(b, 0)
	var port := (b[4] << 8) | b[5]
	_punches.append({ip = ip, port = port, until = Time.get_ticks_msec() + 30000, next = 0})
	status.emit("Connecting to your friend, should only take a few seconds...")
	return true


func _punch_tick() -> void:
	if not peer is ENetMultiplayerPeer or not is_host or peer.host == null:
		return
	var now := Time.get_ticks_msec()
	for p in _punches.duplicate():
		if now > p.until:
			_punches.erase(p)
		elif now >= p.next:
			peer.host.socket_send(p.ip, p.port, PackedByteArray([0x4E, 0x46, 0x4D]))
			p.next = now + 250
	# keep the router's mapping for our port alive while the room is open
	if stun_ip != "" and now >= _keepalive:
		peer.host.socket_send(stun_ip, Stun.SERVERS[0][1], PackedByteArray([0]))
		_keepalive = now + 15000


# -------------------------------------------------------------------- join
var my_pw := ""                  # hash of the password this player typed for a locked room


func join_room(room_code: String, name: String, car: int, password := "") -> void:
	leave()
	var b := _unb32(room_code)
	if b.size() < 24:
		status.emit("That room code isn't complete. Ask for it again and paste the whole thing.")
		return
	my_name = name
	my_car = car
	is_host = false
	my_pw = _pw_hash(password)
	if b.size() >= 28:
		# an Epic room: 16-byte lobby id, then the room key
		_derive(b.slice(16, 28))
		code = room_code
		reply_code = ""
		in_room = true
		_join_eos(b.slice(0, 16).hex_encode())
		return
	pub_ip = _ip_str(b, 0)
	pub_port = (b[4] << 8) | b[5]
	lan_ip = _ip_str(b, 6)
	var lan_port := (b[10] << 8) | b[11]
	_derive(b.slice(12, 24))
	code = room_code
	reply_code = ""
	in_room = true
	_local_port = 47714 + randi() % 400
	# 1) same Wi-Fi / this PC: direct
	_attempts = [[lan_ip, lan_port, 2500], ["127.0.0.1", lan_port, 1200]]
	_join_deadline = 0
	status.emit("Connecting to the room...")
	# learn our own public address first (for the reply code), then connect
	# from that same port
	_thread = Thread.new()
	_thread.start(func(): call_deferred("_join_mapped", Stun.query(_local_port)))


func _join_eos(lobby_id: String) -> void:
	status.emit("Connecting to the room...")
	if not await main.online.start_async(my_name):
		status.emit("Couldn't reach the online service. Check your internet connection and try again.")
		in_room = false
		lobby_changed.emit()
		return
	var owner: String = await main.online.join_async(lobby_id)
	if not in_room or is_host:
		return
	if owner == "":
		status.emit("Couldn't find that room. It may have closed.")
		in_room = false
		lobby_changed.emit()
		return
	var p := EOSGMultiplayerPeer.new()
	if p.create_client(Online.SOCKET, owner) != OK:
		status.emit("Couldn't connect to the room.")
		in_room = false
		lobby_changed.emit()
		return
	peer = p
	transport = "eos"
	_connecting = true
	peer.peer_connected.connect(func(id):
		if id == 1:
			_connecting = false
			_send(1, HELLO, {name = my_name, car = my_car, v = 1, pw = my_pw}))
	peer.peer_disconnected.connect(func(id):
		if id != 1: return
		if players.is_empty(): call_deferred("_end", "Couldn't connect to the room.")
		else: _on_peer_disconnected(id))
	var attempt := peer
	get_tree().create_timer(30.0).timeout.connect(func():
		if peer == attempt and players.is_empty() and in_room:
			call_deferred("_end", "Couldn't connect to the room. The host's game may have closed."))


func _join_mapped(mapped: Dictionary) -> void:
	if _thread:
		_thread.wait_to_finish()
		_thread = null
	if not in_room or is_host:
		return
	if not mapped.is_empty():
		reply_code = _make_reply(mapped.ip, mapped.port)
	_next_attempt()


## Tries the next address; after the local ones, keeps knocking on the host's
## public address (each knock also opens our own router) for up to 90 s.
func _next_attempt() -> void:
	if not in_room or is_host or not players.is_empty() or _connecting or _thread:
		return
	if _attempts.is_empty():
		if _join_deadline == 0:
			_join_deadline = Time.get_ticks_msec() + 90000
			if reply_code != "":
				status.emit("Couldn't get in directly. Send this REPLY CODE to the host (they paste it with ADD A REPLY CODE):")
			else:
				status.emit("Connecting to the room...")
			lobby_changed.emit()
		if Time.get_ticks_msec() > _join_deadline:
			status.emit("Couldn't reach the room. Check the code is right and the host's game is open. If it still fails, both of you can use a free LAN tool like ZeroTier or Radmin VPN and host again.")
			in_room = false
			lobby_changed.emit()
			return
		_attempts = [[pub_ip, pub_port, 5000]]
	var a: Array = _attempts.pop_front()
	_connect(a[0], a[1], a[2])


func _connect(ip: String, port: int, wait_ms: int) -> void:
	_drop_peer()
	peer = ENetMultiplayerPeer.new()
	if peer.create_client(ip, port, 0, 0, 0, _local_port) != OK:
		peer = null
		get_tree().create_timer(0.3).timeout.connect(_next_attempt)
		return
	_connecting = true
	var attempt := peer
	var give_up := func():
		if peer == attempt and players.is_empty() and in_room and _connecting:
			_connecting = false
			_drop_peer()
			_next_attempt()
	peer.peer_connected.connect(func(id):
		if id == 1:
			_connecting = false
			peer.get_peer(1).set_timeout(0, 30000, 60000)
			_send(1, HELLO, {name = my_name, car = my_car, v = 1, pw = my_pw}))
	peer.peer_disconnected.connect(func(id):
		if id != 1: return
		if players.is_empty(): give_up.call()
		else: _on_peer_disconnected(id))
	get_tree().create_timer(wait_ms / 1000.0).timeout.connect(give_up)


func _on_peer_connected(_id: int) -> void:
	pass # wait for an authenticated HELLO


func _on_peer_disconnected(id: int) -> void:
	print("net: peer %d disconnected (host=%s, tick %d)" % [id, is_host, next_tick])
	if not in_room:
		return
	if is_host:
		if active and players.has(id):
			_player_gone(int(players[id].get("grid", -1)))
		var gone: String = players[id].name if players.has(id) else ""
		if players.erase(id):
			if gone != "": _post({note = "%s left" % gone})
			_broadcast_lobby()
			if active:
				main.hud.toast("%s left the race" % "A player")
	elif id == 1:
		if active and mesh and players.has(1):
			# the race goes on between the rest of us; a new host takes the room after
			var hs := int(players[1].get("grid", 0))
			old_host_uid = str(slot_uids.get(hs, ""))
			host_gone = true
			_drop_peer.call_deferred() # not inside Epic's own disconnect callback
			players.erase(1)
			_player_gone(hs)
		else:
			call_deferred("_end", "The host closed the room")


func _broadcast_lobby() -> void:
	if is_host:
		_update_auto()
	var list := []
	for id in players:
		list.append([id, players[id].name, players[id].car, players[id].get("ready", false), players[id].get("rematch", false)])
	for id in players:
		if id != 1:
			_send(id, LOBBY, {players = list, setup = _shared_setup(), auto = auto_left()})
	_advertise()
	lobby_changed.emit()


## Host: start the auto-start countdown when a second player is in the room,
## and stop it if they leave or a race is running.
func _update_auto() -> void:
	if active or players.size() < 2:
		auto_at = 0
	else:
		if auto_at == 0:
			auto_at = Time.get_ticks_msec() + int(auto_start_secs * 1000.0)
		# everyone ready (the host always is): go in a few seconds
		var all_ready := true
		for id in players:
			if id != 1 and not players[id].get("ready", false): all_ready = false
		if all_ready:
			auto_at = mini(auto_at, Time.get_ticks_msec() + int(ALL_READY_SECS * 1000.0))
	auto_deadline = auto_at


## Host: add a line to the room chat and send it to everyone.
func _post(msg: Dictionary) -> void:
	if not is_host:
		return
	room_log.append(msg)
	chat.emit(msg)
	for id in players:
		if id != 1:
			_send(id, CHAT, msg)


## Say something in the room chat.
func say(text: String) -> void:
	text = text.strip_edges().substr(0, 120)
	if text == "":
		return
	if is_host:
		_post({n = my_name, m = text})
	else:
		_send(1, CHAT, {m = text})


## After a race: vote for a rematch (same room, same settings). When every
## player in the room has voted, the host starts it a moment later.
func vote_rematch(on: bool) -> void:
	var me := peer.get_unique_id() if peer else -1
	if players.has(me):
		players[me].rematch = on
	if is_host:
		_check_rematch()
		_broadcast_lobby()
	else:
		_send(1, VOTE, on)
		lobby_changed.emit()


func rematch_votes() -> Array:
	var n := 0
	for id in players:
		if players[id].get("rematch", false): n += 1
	return [n, players.size()]


var _rematch_pending := false


func _check_rematch() -> void:
	if not is_host or active or _rematch_pending or players.size() < 2:
		return
	for id in players:
		if not players[id].get("rematch", false):
			return
	_rematch_pending = true
	_post({note = "Everyone voted for a rematch"})
	get_tree().create_timer(2.0).timeout.connect(func():
		_rematch_pending = false
		if is_host and in_room and not active:
			start_race())


## Round trip time to each player in ms (the host measures, then shares).
var pings := {}
var _ping_clock := 0


func _ping_tick() -> void:
	var now := Time.get_ticks_msec()
	if now < _ping_clock or players.size() < 2:
		return
	_ping_clock = now + 1000
	for id in players:
		if id != 1: _send(id, PING, now)
	var share := pings.duplicate()
	share[1] = 0
	for id in players:
		if id != 1: _send(id, PINGS, share)


## The Epic user behind a room peer id (voice indicators), or "".
func peer_user(id: int) -> String:
	if transport != "eos" or peer == null:
		return ""
	if id == peer.get_unique_id():
		return main.online.user_id
	return (peer as EOSGMultiplayerPeer).get_peer_user_id(id)


## A player: ready or not.
func set_ready(on: bool) -> void:
	if not is_host and players.has(peer.get_unique_id() if peer else -1):
		players[peer.get_unique_id()].ready = on
		_send(1, RDY, on)
		lobby_changed.emit()


func my_ready() -> bool:
	var me := peer.get_unique_id() if peer else -1
	return players.has(me) and players[me].get("ready", false)


## Host: remove a player from the room.
func kick(id: int) -> void:
	if not is_host or id == 1 or not players.has(id):
		return
	var who: String = players[id].name
	_send(id, KICK, {reason = "The host removed you from the room."})
	players.erase(id)
	_post({note = "%s was removed by the host" % who})
	_broadcast_lobby()
	get_tree().create_timer(0.5).timeout.connect(func():
		if peer and peer.get_connection_status() == MultiplayerPeer.CONNECTION_CONNECTED:
			peer.disconnect_peer(id))


## Host: change some of the room's settings (only the keys given).
func set_room_setup(changes: Dictionary) -> void:
	room_setup.merge(changes, true)
	if is_host:
		_broadcast_lobby()


## Closes the connection without triggering our own disconnect handlers.
func _drop_peer() -> void:
	if peer == null:
		return
	var p := peer
	peer = null
	for c in p.peer_connected.get_connections(): p.peer_connected.disconnect(c.callable)
	for c in p.peer_disconnected.get_connections(): p.peer_disconnected.disconnect(c.callable)
	p.close()


func leave() -> void:
	var was := in_room
	in_room = false
	if peer and was and peer.get_connection_status() == MultiplayerPeer.CONNECTION_CONNECTED:
		for id in players:
			if id != peer.get_unique_id():
				_send(id, BYE, {})
		peer.poll()
	_drop_peer()
	_close_mesh()
	host_gone = false
	if upnp and upnp_ok:
		upnp.delete_port_mapping(PORT, "UDP")
	upnp_ok = false
	lan.stop_advertising()
	if transport == "eos":
		main.online.leave_async()
	transport = "enet"
	_epic_attrs = {}
	auto_at = 0
	auto_deadline = 0
	_punches.clear()
	_attempts.clear()
	_connecting = false
	reply_code = ""
	stun_ip = ""
	in_room = false
	active = false
	is_host = false
	players.clear()
	room_log.clear()
	recv_seq.clear()
	send_seq = 0
	inbox.clear()
	have.clear()


func _end(reason: String) -> void:
	var was := in_room
	if not was:
		return
	leave()
	if was:
		ended.emit(reason)


# ---------------------------------------------------------------- polling
func _process(dt: float) -> void:
	if peer == null or host_gone:
		_poll_mesh() # the host left mid-race: the others race on directly
		if active and not pending_gone.is_empty(): _process_gone()
		return
	peer.poll()
	while peer and peer.get_available_packet_count() > 0:
		var from := peer.get_packet_peer()
		var raw := peer.get_packet()
		var plain := _open(raw)
		if plain.size() < 5:
			continue # not from someone with the room key
		var seq := plain.decode_u32(0)
		if seq <= int(recv_seq.get(from, 0)):
			continue # replayed
		recv_seq[from] = seq
		var type := plain[4]
		var data = bytes_to_var(plain.slice(5))
		_handle(from, type, data)
	if is_host:
		_punch_tick()
		_ping_tick()
	_poll_mesh()
	if active and not pending_gone.is_empty():
		_process_gone()
	# a race that ended locally: count down again for the next one
	if is_host and in_room and not active and auto_at == 0 and players.size() >= 2:
		_broadcast_lobby()
	if is_host and in_room and not active and auto_at > 0 and Time.get_ticks_msec() >= auto_at:
		start_race()


func _handle(from: int, type: int, data) -> void:
	match type:
		HELLO:
			if not is_host or typeof(data) != TYPE_DICTIONARY:
				return
			if not players.has(from) and str(room_setup.get("password", "")) != "" and str(data.get("pw", "")) != _pw_hash(room_setup.password):
				_send(from, KICK, {reason = "Wrong password."})
				return
			if players.size() >= int(room_setup.get("max_players", MAX_PLAYERS)) or active:
				_send(from, KICK, {reason = "The room is full or already racing."})
				return
			if not players.has(from) and peer is ENetMultiplayerPeer and peer.get_peer(from):
				peer.get_peer(from).set_timeout(0, 30000, 60000)
			var was_in := players.has(from)
			var ready: bool = players[from].get("ready", false) if was_in else false
			players[from] = {name = str(data.get("name", "Player")).substr(0, 16), car = clampi(int(data.get("car", 0)), 0, 55), ready = ready}
			if not was_in:
				_post({note = "%s joined" % players[from].name})
			_broadcast_lobby()
			main.audio.play("ui_join", 0.7)
		LOBBY:
			if is_host:
				return
			if active:
				# the host ended the race: everyone goes back to the room
				active = false
				back_to_lobby.emit()
			players.clear()
			for p in data.players:
				players[int(p[0])] = {name = p[1], car = p[2], ready = p[3] if p.size() > 3 else false, rematch = p[4] if p.size() > 4 else false}
			room_setup = default_setup()
			room_setup.merge(data.setup, true)
			var left := float(data.get("auto", -1.0))
			auto_deadline = 0 if left < 0.0 else Time.get_ticks_msec() + int(left * 1000.0)
			status.emit("In the room. Waiting for the host to start.")
			lobby_changed.emit()
		KICK:
			call_deferred("_end", str(data.get("reason", "Removed from the room")))
		BYE:
			if from == 1 and not is_host:
				call_deferred("_end", "The host closed the room")
			elif is_host:
				_on_peer_disconnected(from)
		START:
			if is_host:
				return
			_begin(data)
		INPUT, GONE:
			if active and typeof(data) == TYPE_ARRAY:
				_race_msg(type, data, from)
		HASH:
			_check_hash(from, int(data[0]), int(data[1]))
		CHAT:
			if typeof(data) != TYPE_DICTIONARY:
				return
			if is_host and players.has(from):
				_post({n = players[from].name, m = str(data.get("m", "")).substr(0, 120)})
			elif not is_host:
				room_log.append(data)
				chat.emit(data)
		RDY:
			if is_host and players.has(from) and not active:
				players[from].ready = bool(data)
				_broadcast_lobby()
		PING:
			if not is_host: _send(1, PONG, data)
		PONG:
			if is_host and players.has(from):
				pings[from] = Time.get_ticks_msec() - int(data)
		PINGS:
			if not is_host and typeof(data) == TYPE_DICTIONARY:
				pings = {}
				for k in data: pings[int(k)] = int(data[k])
		VOTE:
			if is_host and players.has(from) and not active:
				players[from].rematch = bool(data)
				_check_rematch()
				_broadcast_lobby()


# ------------------------------------------------------------------- races
## Host: start a race for everyone in the room.
func start_race() -> void:
	if not is_host:
		return
	var ids: Array = players.keys()
	ids.sort()
	var grid := []
	var humans := 0
	for i in ids.size():
		players[ids[i]].grid = i
		grid.append(players[ids[i]].car)
		humans |= 1 << i
	var pool: Array = range(16)
	pool.shuffle()
	var hangout: bool = room_setup.mode == "hangout"
	for i in mini(0 if hangout else int(room_setup.ai), MAX_PLAYERS - ids.size()):
		grid.append(pool[i])
	var seat := {}
	for id in ids:
		seat[id] = players[id].grid
	auto_at = 0
	auto_deadline = 0
	for id in players:
		players[id].ready = false
		players[id].rematch = false
	var uids := {}
	for id in ids: uids[players[id].grid] = peer_user(id)
	var opts := {stage = room_setup.stage, grid = grid, humans = humans, seed = randi(), seats = seat, landk = 0.3,
		delay = _pick_delay(), uids = uids,
		deathmatch = room_setup.mode == "dm", fixes = int(room_setup.fixes), freeroam = hangout}
	for id in ids:
		if id != 1:
			_send(id, START, opts)
	_begin(opts)


## The race's networking: every player sends their controls for a tick a
## few ticks ahead (the same delay for everyone, host included, so nobody has
## an edge) straight to every other player through Epic, and through the host
## as a backup. Each PC steps a tick once it has every player's controls for
## it, so all of them run the same race.
##
## If a player leaves mid-race (the host too), the others agree on the last
## tick of that player's controls; their car is wasted there and the rest race on.
func _begin(opts: Dictionary) -> void:
	var me := peer.get_unique_id()
	my_grid = int(opts.seats.get(me, 0))
	slot_names = {}
	for id in opts.seats:
		if players.has(int(id)):
			players[int(id)].grid = int(opts.seats[id])
			slot_names[int(opts.seats[id])] = players[int(id)].name
	nplayers = opts.grid.size()
	humans_mask = int(opts.humans)
	delay = int(opts.get("delay", 3))
	slot_uids = {}
	var uids: Dictionary = opts.get("uids", {})
	for k in uids: slot_uids[int(k)] = str(uids[k])
	inbox.clear()
	have.clear()
	gone_slots.clear()
	pending_gone.clear()
	hashes.clear()
	next_tick = 1
	sent_tick = delay
	send_clock = 0.0
	desync_reported = false
	host_gone = false
	active = true
	auto_deadline = 0
	# nobody steers for the first few ticks: that's the delay
	for t in range(1, delay + 1):
		for s in nplayers:
			if humans_mask & (1 << s): _store(s, t, 0)
	_open_mesh()
	print("net: race delay %d ticks (%d ms), direct links %s" % [delay, delay * 40, "on" if mesh else "off"])
	race_start.emit({stage = opts.stage, grid = opts.grid, humans = opts.humans, seed = opts.seed, me = my_grid, net = true, landk = opts.get("landk", 0.3),
		deathmatch = opts.get("deathmatch", false), fixes = int(opts.get("fixes", -1)), freeroam = opts.get("freeroam", false)})
	_advertise()


## Host: the delay for this race, from the slowest connection in the room.
func _pick_delay() -> int:
	var worst := 0
	for id in pings: worst = maxi(worst, int(pings[id]))
	# one way to the farthest player plus a margin for jitter, in 40 ms ticks
	return clampi(int(ceil((worst * 0.6 + 30.0) / 40.0)), 2, 10)


# ---------------------------------------------------------------- mesh
## Epic: a direct connection to every other player for the controls.
func _open_mesh() -> void:
	_close_mesh()
	if transport != "eos" or slot_uids.is_empty():
		return
	var m := EOSGMultiplayerPeer.new()
	if m.create_mesh(MESH_SOCKET) != OK:
		return
	m.set_auto_accept_connection_requests(true)
	for s in slot_uids:
		var uid: String = slot_uids[s]
		if s != my_grid and uid != "" and uid != main.online.user_id:
			m.add_mesh_peer(uid)
	mesh = m
	mesh_seq.clear()
	mesh.peer_disconnected.connect(func(pid):
		var uid := (mesh as EOSGMultiplayerPeer).get_peer_user_id(pid) if mesh else ""
		for s in slot_uids:
			if slot_uids[s] == uid: _player_gone(s))


func _close_mesh() -> void:
	if mesh == null:
		return
	var m := mesh
	mesh = null
	for c in m.peer_disconnected.get_connections(): m.peer_disconnected.disconnect(c.callable)
	m.close()


func _poll_mesh() -> void:
	if mesh == null:
		return
	mesh.poll()
	while mesh and mesh.get_available_packet_count() > 0:
		var from := mesh.get_packet_peer()
		var plain := _open(mesh.get_packet())
		if plain.size() < 5:
			continue
		var seq := plain.decode_u32(0)
		if seq <= int(mesh_seq.get(from, 0)):
			continue
		mesh_seq[from] = seq
		direct_count += 1
		_race_msg(plain[4], bytes_to_var(plain.slice(5)))


## Send a race message to every other player: direct, and through the host.
func _race_send(type: int, data: Variant) -> void:
	if mesh:
		send_seq += 1
		var msg := PackedByteArray()
		msg.resize(5)
		msg.encode_u32(0, send_seq)
		msg[4] = type
		msg.append_array(var_to_bytes(data))
		mesh.set_target_peer(0)
		mesh.set_transfer_mode(MultiplayerPeer.TRANSFER_MODE_RELIABLE)
		mesh.put_packet(_seal(msg))
	if host_gone or peer == null:
		return
	if is_host:
		for id in players:
			if id != 1: _send(id, type, data)
	else:
		_send(1, type, data)


## A race message, however it arrived. The host passes on what clients send.
func _race_msg(type: int, data, from := 0) -> void:
	if is_host and from > 1:
		for id in players:
			if id != 1 and id != from: _send(id, type, data)
	match type:
		INPUT:
			_store(int(data[0]), int(data[1]), int(data[2]))
		GONE:
			_gone_report(data)


# ---------------------------------------------------------------- controls
func _store(slot: int, tick: int, bits: int) -> void:
	if tick < next_tick:
		return
	var bit := 1 << slot
	if int(have.get(tick, 0)) & bit:
		return
	if not inbox.has(tick):
		var a := PackedInt32Array()
		a.resize(8)
		inbox[tick] = a
	inbox[tick][slot] = bits
	have[tick] = int(have.get(tick, 0)) | bit


## Called by the race loop every frame: our controls, one tick every 40 ms,
## never more than the delay ahead of the race.
func pump(dt: float) -> void:
	send_clock = minf(send_clock + dt, 0.2)
	while send_clock >= 0.04 and sent_tick < next_tick + delay:
		send_clock -= 0.04
		sent_tick += 1
		var bits: int = main.local_input()
		_store(my_grid, sent_tick, bits)
		_race_send(INPUT, [my_grid, sent_tick, bits])


## Every player still in the race has sent their controls for the next tick.
func can_step() -> bool:
	if not pending_gone.is_empty():
		return false # agreeing where a player who left stopped
	var need := humans_mask
	for s in gone_slots:
		if next_tick > int(gone_slots[s]): need &= ~(1 << s)
	return (int(have.get(next_tick, 0)) & need) == need


func inputs_for_tick() -> PackedInt32Array:
	var t := next_tick
	var out: PackedInt32Array = inbox.get(t, PackedInt32Array([0, 0, 0, 0, 0, 0, 0, 0]))
	for s in gone_slots:
		if t > int(gone_slots[s]): out[s] = 0
		# leaving counts as being wasted, on the same tick on every PC
		if t == int(gone_slots[s]) + 1: main.sim.debug_set_damage(s, 1.5)
	inbox.erase(t)
	have.erase(t)
	next_tick += 1
	if t % 25 == 0 and not host_gone:
		var h: int = main.sim.state_hash()
		if is_host: _check_hash(1, t, h)
		else: _send(1, HASH, [t, h])
	return out


func alpha() -> float:
	return 0.5 if can_step() else 0.0


# ---------------------------------------------------------------- leaving mid-race
## A player's PC dropped out of the race: tell the others what we have of
## their controls; once every remaining player has said, the last tick any
## of us has is where that car stops.
func _player_gone(slot: int) -> void:
	if not active or slot == my_grid or gone_slots.has(slot) or pending_gone.has(slot):
		return
	pending_gone[slot] = {reports = {}, last = -1, at = Time.get_ticks_msec()}
	var hist := {}
	var last := next_tick - 1
	for t in inbox:
		if int(have.get(t, 0)) & (1 << slot):
			hist[t] = inbox[t][slot]
			last = maxi(last, t)
	var report := [slot, my_grid, last, hist]
	_race_send(GONE, report)
	_gone_report(report)
	if main.hud: main.hud.toast("%s left the race" % slot_names.get(slot, "A player"))


func _gone_report(data) -> void:
	var slot := int(data[0])
	var by := int(data[1])
	if gone_slots.has(slot):
		return
	if not pending_gone.has(slot):
		_player_gone(slot) # we hadn't noticed yet: add our own report too
		if not pending_gone.has(slot): return
	var hist: Dictionary = data[3]
	for t in hist: _store(slot, int(t), int(hist[t]))
	var p: Dictionary = pending_gone[slot]
	p.reports[by] = true
	p.last = maxi(int(p.last), int(data[2]))
	_settle_gone(slot)


func _settle_gone(slot: int) -> void:
	var p: Dictionary = pending_gone[slot]
	var waiting := false
	for s in nplayers:
		if not (humans_mask & (1 << s)) or s == slot or gone_slots.has(s) or pending_gone.has(s) and s != slot:
			continue
		if not p.reports.has(s): waiting = true
	# give up waiting after a few seconds rather than freeze the race
	if waiting and Time.get_ticks_msec() - int(p.at) < 4000:
		return
	gone_slots[slot] = int(p.last)
	pending_gone.erase(slot)


func _process_gone() -> void:
	for s in pending_gone.keys(): _settle_gone(s)


## Host: stop the race for everyone and return to the room.
func end_race_for_all() -> void:
	active = false
	_close_mesh()
	_broadcast_lobby()


func race_over() -> void:
	print("net: race over, %d messages arrived direct" % direct_count)
	active = false
	_close_mesh()
	if host_gone:
		_take_over()


# ---------------------------------------------------------------- new host
## The host left: Epic picks a new owner for the room's lobby. That player's
## PC opens the room; everyone else connects to it.
func _take_over() -> void:
	host_gone = false
	var lobby: HLobby = main.online.lobby
	if transport != "eos" or lobby == null or not lobby.is_valid():
		call_deferred("_end", "The host left")
		return
	status.emit("The host left. Moving the room to another player...")
	var deadline := Time.get_ticks_msec() + 30000
	while in_room and Time.get_ticks_msec() < deadline:
		var owner: String = lobby.owner_product_user_id
		if owner == main.online.user_id:
			_become_host()
			return
		if owner != "" and owner != old_host_uid:
			_follow_host(owner)
			return
		await get_tree().create_timer(0.5).timeout
	call_deferred("_end", "The host left")


func _become_host() -> void:
	var p := EOSGMultiplayerPeer.new()
	if p.create_server(Online.SOCKET) != OK:
		call_deferred("_end", "The host left")
		return
	p.set_auto_accept_connection_requests(true)
	_drop_peer()
	peer = p
	peer.peer_connected.connect(_on_peer_connected)
	peer.peer_disconnected.connect(_on_peer_disconnected)
	is_host = true
	room_setup.erase("locked")
	room_setup.password = "" # only the old host knew it
	players = {1: {name = my_name, car = my_car}}
	recv_seq.clear()
	pings.clear()
	print("net: took over as host")
	_post({note = "You're the host now"})
	status.emit("You're the host now.")
	_advertise()
	_broadcast_lobby()


func _follow_host(owner: String) -> void:
	for attempt in 20:
		if not in_room: return
		var p := EOSGMultiplayerPeer.new()
		if p.create_client(Online.SOCKET, owner) == OK:
			_drop_peer()
			peer = p
			recv_seq.clear()
			players.clear()
			var ok := {v = false}
			peer.peer_connected.connect(func(id):
				if id == 1:
					ok.v = true
					_send(1, HELLO, {name = my_name, car = my_car, v = 1, pw = my_pw}))
			peer.peer_disconnected.connect(func(id): if id == 1 and ok.v: _on_peer_disconnected(id))
			await get_tree().create_timer(3.0).timeout
			if ok.v:
				status.emit("In the room. The new host is %s." % _uid_name(owner))
				return
		await get_tree().create_timer(1.0).timeout
	call_deferred("_end", "The host left")


func _uid_name(uid: String) -> String:
	for s in slot_uids:
		if slot_uids[s] == uid: return str(slot_names.get(s, "another player"))
	return "another player"


func _check_hash(from: int, tick: int, h: int) -> void:
	if not is_host:
		return
	if not hashes.has(tick):
		hashes[tick] = {}
	hashes[tick][from] = h
	var mine = hashes[tick].get(1)
	if mine != null:
		if hashes[tick].size() >= players.size():
			var same := true
			for k in hashes[tick]: same = same and hashes[tick][k] == mine
			print("sync check tick %d: %s" % [tick, "MATCH" if same else "DESYNC"])
		for k in hashes[tick]:
			if hashes[tick][k] != mine and not desync_reported:
				desync_reported = true
				main.hud.toast("Warning: a player's game went out of sync")
	if hashes.size() > 20:
		var keys: Array = hashes.keys()
		keys.sort()
		hashes.erase(keys[0])


