## Sound: the original WAVs (engine gears, crashes, skids, scrapes, voice
## countdown) and the ProTracker soundtracks through the native MOD player.
class_name GameAudio
extends Node

var streams := {}            # name -> AudioStreamWAV
var settings: Settings
var assets: Assets
var engine: Array = []       # 5 looping players (idle + 4 gears)
var air: Array = []
var wasted_loop: AudioStreamPlayer
var sfx_bus := 0
var muted := false
var st := {}
var swits := PackedInt32Array([50, 120, 200])
var music_player: AudioStreamPlayer
var music_gen: AudioStreamGeneratorPlayback
var mod: NfmMod
var music_on := false
var last_hit := 0.0
var focus := 0
var pool: Array = []         # one-shot players
var deathmatch := false      # no course: no checkpoint / lap sounds


func setup(p_assets: Assets, p_settings: Settings) -> void:
	assets = p_assets
	settings = p_settings
	for name in assets.sounds:
		var s := AudioStreamWAV.load_from_buffer(assets.sounds[name])
		if s:
			streams[name] = s
	# menu sounds are made here: the game's own WAVs are race sounds
	streams["ui_move"] = _tones([1500.0], 0.035, 0.22)
	streams["ui_ok"] = _tones([880.0, 1320.0], 0.06, 0.3)
	streams["ui_join"] = _tones([660.0, 880.0, 1100.0], 0.07, 0.3)
	streams["ui_reward"] = _tones([523.0, 659.0, 784.0, 1046.0], 0.09, 0.32, 0.35)
	for i in 12:
		var p := AudioStreamPlayer.new()
		add_child(p)
		pool.append(p)
	music_player = AudioStreamPlayer.new()
	var gen := AudioStreamGenerator.new()
	gen.mix_rate = 44100
	gen.buffer_length = 0.25
	music_player.stream = gen
	music_player.volume_db = linear_to_db(0.5)
	add_child(music_player)
	AudioServer.set_bus_volume_db(0, linear_to_db(settings.volume))


## A short UI sound: soft sine notes one after another, each with a fast
## attack and exponential decay (the last one rings for `tail` seconds).
func _tones(freqs: Array, note: float, amp: float, tail := 0.0) -> AudioStreamWAV:
	var rate := 44100
	var data := PackedByteArray()
	for k in freqs.size():
		var len_s: float = note + (tail if k == freqs.size() - 1 else 0.0)
		var n := int(len_s * rate)
		for i in n:
			var t := float(i) / rate
			var env := minf(1.0, t / 0.003) * exp(-t / (len_s * 0.35))
			var v := sin(TAU * freqs[k] * t) * 0.85 + sin(TAU * freqs[k] * 2.0 * t) * 0.15
			var s := int(clampf(v * env * amp, -1.0, 1.0) * 32767.0)
			data.append(s & 0xff)
			data.append((s >> 8) & 0xff)
	var w := AudioStreamWAV.new()
	w.format = AudioStreamWAV.FORMAT_16_BITS
	w.mix_rate = rate
	w.stereo = false
	w.data = data
	return w


func play(name: String, gain := 1.0, pitch := 1.0) -> void:
	if not streams.has(name):
		return
	for p in pool:
		if not p.playing:
			p.stream = streams[name]
			p.volume_db = linear_to_db(maxf(gain, 0.0001))
			p.pitch_scale = pitch
			p.play()
			return


func _loop(name: String) -> AudioStreamPlayer:
	if not streams.has(name):
		return null
	var s: AudioStreamWAV = streams[name].duplicate()
	s.loop_mode = AudioStreamWAV.LOOP_FORWARD
	s.loop_begin = 0
	s.loop_end = int(s.get_length() * s.mix_rate)
	var p := AudioStreamPlayer.new()
	p.stream = s
	p.volume_db = -80.0
	add_child(p)
	p.play()
	return p


func start_race(sim: NfmSim, p_focus: int) -> void:
	stop_race()
	focus = p_focus
	var slot: int = sim.grid()[focus]
	var sig := int(sim.car_stats(slot)[5])
	swits = sim.car_swits(slot)
	for i in 5:
		engine.append(_loop("%d%d" % [sig, i]))
	for i in 4:
		air.append(_loop("air%d" % i))
	wasted_loop = _loop("wasted")
	st = {bfcrash = 0, bfskid = 0, bfscrape = 0, bfsc1 = 0, bfsc2 = 0, crshturn = 0, skflg = 0, dskflg = 0, air_target = -1}
	last_hit = 0.0


func stop_race() -> void:
	for l in engine + air + [wasted_loop]:
		if l:
			l.queue_free()
	engine.clear()
	air.clear()
	wasted_loop = null


func pause(on: bool) -> void:
	for l in engine + air + [wasted_loop]:
		if l:
			l.stream_paused = on


func toggle_mute() -> void:
	muted = not muted
	AudioServer.set_bus_mute(0, muted)


func crash(f: float, n: int) -> void:
	if st.is_empty() or st.bfcrash != 0:
		return
	var a := absf(f)
	if n == 0 or n == -1:
		var turn: int = 2 if n == -1 else st.crshturn
		if a > 25 and a < 170:
			play("lowcrash%d" % (turn + 1), 0.9); st.bfcrash = 2
		if a >= 170:
			play("crash%d" % (turn + 1), 1.0); st.bfcrash = 2
		if n == 0 and a > 25:
			st.crshturn = (st.crshturn + 1) % 3
	if n == 1:
		play("tires", 0.9); st.bfcrash = 3


func on_event(type: int, v: PackedFloat32Array, _view) -> void:
	if st.is_empty():
		return
	match type:
		1: if focus == 0: crash(v[0], int(v[1]))
		2:
			if focus == 0 and st.bfcrash == 0 and st.bfskid == 0 and v[1] > 150:
				if int(v[0]) == 0:
					play("skid%d" % (st.skflg + 1), 0.55); st.skflg = (st.skflg + 1) % 3
				else:
					play("dustskid%d" % (st.dskflg + 1), 0.6); st.dskflg = (st.dskflg + 1) % 3
				st.bfskid = 35
		3:
			if focus == 0 and st.bfscrape == 0 and Vector3(v[0], v[1], v[2]).length() / 10.0 > 10.0:
				play("scrape%d" % (1 if randf() > 0.5 else 2), 0.7); st.bfscrape = 5
		4:
			if focus == 0 and (st.bfsc1 == 0 or st.bfsc2 == 0) and Vector3(v[0], v[1], v[2]).length() / 10.0 > 15.0:
				play("scrape3", 0.6)
				if st.bfsc1 == 0: st.bfsc1 = 12; st.bfsc2 = 6
				else: st.bfsc2 = 12; st.bfsc1 = 6
		5: play("carfixed", 0.8)
		8: if not deathmatch: play("checkpoint", 0.8)
		9: if not deathmatch: play("checkpoint", 0.9)
		10: play("firewasted", 1.0 if int(v[0]) == focus else 0.4)
		11: play("powerup", 0.8)
		12: play(["", "one", "two", "three"][clampi(int(v[0]), 0, 3)])
		13: play("go")
	for k in ["bfcrash", "bfskid", "bfscrape", "bfsc1", "bfsc2"]:
		pass


## Per frame: engine gear loops from speed / throttle, like the original.
func frame(s: PackedFloat32Array, o: int, input: int, dt: float, racing: bool) -> void:
	if engine.is_empty():
		return
	for k in ["bfcrash", "bfskid", "bfscrape", "bfsc1", "bfsc2"]:
		if st[k] > 0:
			st[k] -= 1
	# remote players' own crashes are heard from their damage going up
	if focus != 0:
		var hit := s[o + 9]
		if hit - last_hit > 0.01:
			crash((hit - last_hit) * 4000.0, 0)
		last_hit = hit
	var speed := absf(s[o + 7])
	var mtouch := s[o + 24] > 0.5
	var grounded := s[o + 14] > 0.5
	var capsized := s[o + 15] > 0.5
	var dest := s[o + 10] > 0.5
	var level := 0
	var frac := 0.0
	var in_air := false
	if racing and not dest:
		var throttle := (input & 4 != 0 and s[o + 7] > 0) or (input & 8 != 0 and s[o + 7] < 10)
		if throttle and mtouch:
			if capsized:
				level = 4
			elif speed <= swits[0]:
				frac = speed / maxf(1, swits[0]); level = 1 + mini(2, int(3 * frac))
			elif speed <= swits[1]:
				frac = (speed - swits[0]) / maxf(1, swits[1] - swits[0]); level = 1 + mini(2, int(3 * frac))
			else:
				frac = (speed - swits[1]) / maxf(1, swits[2] - swits[1]); level = 1 + mini(3, int(3 * frac))
			if s[o + 8] >= 98 and speed > swits[1]:
				level = 4
		elif not grounded:
			in_air = true
	var rate := 0.92 + 0.18 * (frac * 3.0 - floorf(frac * 3.0))
	for i in engine.size():
		var l: AudioStreamPlayer = engine[i]
		if l == null:
			continue
		var target := 0.55 if i == level else 0.0
		var cur := db_to_linear(l.volume_db)
		l.volume_db = linear_to_db(maxf(0.0001, lerpf(cur, target, minf(1.0, dt * 20.0))))
		l.pitch_scale = lerpf(l.pitch_scale, 1.0 if i == 0 else rate, minf(1.0, dt * 12.0))
	if in_air and st.air_target < 0:
		st.air_target = randi() % 4
	if not in_air:
		st.air_target = -1
	for i in air.size():
		if air[i]:
			var cur := db_to_linear(air[i].volume_db)
			air[i].volume_db = linear_to_db(maxf(0.0001, lerpf(cur, 0.4 if i == st.air_target else 0.0, minf(1.0, dt * 10.0))))
	if wasted_loop:
		var cd := s[o + 25]
		wasted_loop.volume_db = linear_to_db(0.5 if cd > 0 and cd < 7 else 0.0001)


func play_music(bytes: PackedByteArray) -> void:
	stop_music()
	if not settings.music or bytes.is_empty():
		return
	mod = Native.mod()
	if not mod.load(bytes):
		mod = null
		return
	music_player.pitch_scale = 1.0
	music_player.volume_db = linear_to_db(0.5)
	music_player.play()
	music_gen = music_player.get_stream_playback()
	music_on = true


## The last lap: the stage's track lifts a little, a touch faster and louder.
func final_lap() -> void:
	if music_on:
		create_tween().tween_property(music_player, "pitch_scale", 1.045, 1.5)
		create_tween().tween_property(music_player, "volume_db", linear_to_db(0.6), 1.5)


func play_stage_music(stage: Dictionary, soundtrack: String) -> void:
	var bytes := PackedByteArray()
	if stage.id > 0:
		bytes = assets.music("stage%d" % stage.id)
	else:
		# community stages: their tracks lived on the old online servers, so each
		# gets one from assets/stage_music.json ("my:<mymusic track>" or
		# "nfm:<original track>"); otherwise the track its file names, if present
		var pick: String = assets.stage_music().get(str(stage.file).get_file().get_basename(), "")
		if pick.begins_with("nfm:"): bytes = assets.music(pick.substr(4))
		elif pick.begins_with("my:"): bytes = assets.custom_music(pick.substr(3))
		if bytes.is_empty() and soundtrack != "":
			bytes = assets.custom_music(soundtrack)
	play_music(bytes)


func play_menu_music() -> void:
	if music_on and mod and _menu_playing:
		return
	play_music(assets.music("interface"))
	_menu_playing = true

var _menu_playing := false


func stop_music() -> void:
	music_on = false
	_menu_playing = false
	music_player.stop()
	music_gen = null
	mod = null


func _process(_dt: float) -> void:
	if music_on and music_gen and mod:
		var n := music_gen.get_frames_available()
		if n > 0:
			music_gen.push_buffer(mod.render(n, 44100.0))
