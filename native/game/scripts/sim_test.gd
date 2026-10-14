extends SceneTree

func _init() -> void:
	var sim := NfmSim.new()
	var a := Assets.new()
	var t0 := Time.get_ticks_msec()
	a.load_all(sim)
	print("assets + world: ", Time.get_ticks_msec() - t0, " ms, cars ", sim.car_count(), ", stages ", a.stages.size(), ", sounds ", a.sounds.size())
	for i in [0, 14, 16, sim.car_count() - 1]:
		var slot := sim.car_slot(i)
		print("  car ", slot, " ", sim.car_name(slot), " stats ", sim.car_stats(slot), " wheels ", sim.wheels(slot).size() / 11)
	var st: Dictionary = a.stages[0]
	sim.load_stage(st.data, st.id, 7, 1234)
	print("stage ", sim.stage_name(), " env ", sim.stage_env())
	var objs: PackedFloat32Array = sim.stage_objects()
	print("objects ", objs.size() / 15)
	var mesh: Array = sim.piece_mesh(56)
	for p in mesh:
		print("  road part ", p[0], " verts ", (p[1] as PackedVector3Array).size(), " first ", (p[1] as PackedVector3Array)[0], " n ", (p[2] as PackedVector3Array)[0], " c ", (p[3] as PackedColorArray)[0])
	sim.start_race(PackedInt32Array([14, 0, 1, 2, 3, 4, 5]), 1, 0.3, 99)
	var inputs := PackedInt32Array([4, 0, 0, 0, 0, 0, 0])
	t0 = Time.get_ticks_usec()
	for i in 600:
		sim.step(inputs)
	var ev: PackedFloat32Array = sim.take_events()
	print("600 ticks: ", (Time.get_ticks_usec() - t0) / 1000.0, " ms, events ", ev.size() / 10, " info ", sim.race_info())
	var s: PackedFloat32Array = sim.car_states()
	print("car0 state ", s.slice(0, sim.car_state_size()))
	print("car mesh parts ", sim.race_car_mesh(0).size(), " hash ", sim.state_hash())
	quit()
