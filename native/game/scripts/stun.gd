## Asks a public STUN server how this PC's UDP port looks from the internet
## (its public IP and port after the router's address translation). Only that
## one question is asked; no game data ever goes to the server.
class_name Stun
extends RefCounted

const SERVERS := [["stun.l.google.com", 19302], ["stun1.l.google.com", 19302], ["stun.cloudflare.com", 3478]]
const MAGIC := 0x2112A442


## Returns {ip, port} for packets sent from local_port, or {} if unreachable.
## Runs on a worker thread (it blocks while waiting for the answer).
static func query(local_port: int, timeout_ms := 1500) -> Dictionary:
	var udp := PacketPeerUDP.new()
	if udp.bind(local_port) != OK:
		return {}
	var tid := Crypto.new().generate_random_bytes(12)
	var req := PackedByteArray([0x00, 0x01, 0x00, 0x00, 0x21, 0x12, 0xA4, 0x42])
	req.append_array(tid)
	for s in SERVERS:
		var ip := IP.resolve_hostname(s[0], IP.TYPE_IPV4)
		if ip == "":
			continue
		udp.set_dest_address(ip, s[1])
		var start := Time.get_ticks_msec()
		var sent := 0
		while Time.get_ticks_msec() - start < timeout_ms:
			if sent == 0 or Time.get_ticks_msec() - sent > 400:
				udp.put_packet(req)
				sent = Time.get_ticks_msec()
			if udp.get_available_packet_count() > 0:
				var r := _parse(udp.get_packet(), tid)
				if not r.is_empty():
					udp.close()
					return r
			OS.delay_msec(15)
	udp.close()
	return {}


static func _parse(p: PackedByteArray, tid: PackedByteArray) -> Dictionary:
	if p.size() < 20 or p[0] != 0x01 or p[1] != 0x01 or p.slice(8, 20) != tid:
		return {}
	var off := 20
	while off + 4 <= p.size():
		var t := (p[off] << 8) | p[off + 1]
		var n := (p[off + 2] << 8) | p[off + 3]
		var v := off + 4
		if (t == 0x0020 or t == 0x0001) and n >= 8 and p[v + 1] == 0x01:
			var port := (p[v + 2] << 8) | p[v + 3]
			var a := [p[v + 4], p[v + 5], p[v + 6], p[v + 7]]
			if t == 0x0020: # XOR-MAPPED-ADDRESS
				port ^= MAGIC >> 16
				a = [a[0] ^ 0x21, a[1] ^ 0x12, a[2] ^ 0xA4, a[3] ^ 0x42]
			return {ip = "%d.%d.%d.%d" % a, port = port}
		off = v + ((n + 3) & ~3)
	return {}
