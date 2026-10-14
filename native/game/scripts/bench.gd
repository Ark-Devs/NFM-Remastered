## Performance test (--bench=TAG): the same moments every run, with GPU time,
## frame time and a screenshot of each, saved to production/qa/evidence/perf/TAG.
## Run with --fixed-fps 60 so every run renders exactly the same frames.
class_name Bench
extends Node

const STAGES := [0, 9]       # a desert course and a green one
## Results go next to the game (installed copy) or into the project (development).
static func out_dir() -> String:
	if OS.has_feature("editor"):
		return ProjectSettings.globalize_path("res://../../production/qa/evidence/perf/")
	return OS.get_executable_path().get_base_dir() + "/benchmark/"

var main
var tag := ""
var results := []


func run(p_main, p_tag: String) -> void:
	main = p_main
	tag = p_tag
	DisplayServer.window_set_vsync_mode(DisplayServer.VSYNC_DISABLED)
	Engine.max_fps = 0
	seed(1)
	for t in main._arg(OS.get_cmdline_user_args(), "--try=").split(",", false):
		match t:
			"ssilq1": RenderingServer.environment_set_ssil_quality(RenderingServer.ENV_SSIL_QUALITY_MEDIUM, true, 0.5, 4, 50.0, 300.0)
			"ssilblur2": RenderingServer.environment_set_ssil_quality(RenderingServer.ENV_SSIL_QUALITY_HIGH, true, 0.5, 2, 50.0, 300.0)
			"shadowq2": RenderingServer.directional_soft_shadow_filter_set_quality(RenderingServer.SHADOW_QUALITY_SOFT_MEDIUM)
			"glowlin": RenderingServer.environment_glow_set_use_bicubic_upscale(false)
		print("bench: trying ", t)
	DirAccess.make_dir_recursive_absolute(out_dir() + tag)
	RenderingServer.viewport_set_measure_render_time(get_viewport().get_viewport_rid(), true)
	print("bench: GPU ", RenderingServer.get_video_adapter_name(), " / ", RenderingServer.get_video_adapter_vendor(), "  quality ", main.settings.quality)
	main.front.main_menu()
	await _wait(150)
	await _measure("menu")
	var stages: Array = STAGES
	if main._arg(OS.get_cmdline_user_args(), "--benchstages=") != "":
		stages = Array(main._arg(OS.get_cmdline_user_args(), "--benchstages=").split(",")).map(func(x): return int(x))
	for s in stages:
		await _scene(s, "start", 105, -1, false)
		await _scene(s, "pack", 380, 3, false)
		await _scene(s, "crash", 260, 1, true)
	var f := FileAccess.open(out_dir() + tag + "/results.json", FileAccess.WRITE)
	f.store_string(JSON.stringify(results, "  "))
	f.close()
	get_tree().quit()


## One moment of a race: run to `tick`, hold there, measure, screenshot.
func _scene(stage: int, name: String, tick: int, watch: int, crash: bool) -> void:
	seed(1)
	main.start_race({stage = stage, car = 14, opponents = 6, seed = 777})
	var hit := false
	while int(main.sim.race_info()[0]) < tick:
		if crash and not hit and int(main.sim.race_info()[0]) >= 150:
			hit = true
			main.sim.debug_set_damage(1, 1.5)          # a rival wrecked: fire and smoke
			main.sim.debug_set_damage(0, 0.75)         # our car badly damaged: smoke
		await get_tree().process_frame
	main.bench_hold = true
	if watch >= 0:
		main.view.watch = watch
		main.view.cam_init = false
	await _wait(45)
	await _measure("s%d_%s" % [stage, name])
	main.bench_hold = false
	main.end_race()
	await _wait(10)


func _wait(frames: int) -> void:
	for i in frames:
		await get_tree().process_frame


func _measure(name: String) -> void:
	var rid := get_viewport().get_viewport_rid()
	var gpu := 0.0
	var n := 180
	var t0 := Time.get_ticks_usec()
	for i in n:
		await RenderingServer.frame_post_draw
		gpu += RenderingServer.viewport_get_measured_render_time_gpu(rid)
	var wall := (Time.get_ticks_usec() - t0) / 1000.0 / n
	var img := get_viewport().get_texture().get_image()
	img.save_png(out_dir() + tag + "/" + name + ".png")
	var r := {scene = name, gpu_ms = snappedf(gpu / n, 0.01), frame_ms = snappedf(wall, 0.01), fps = snappedf(1000.0 / wall, 0.1),
		draw_calls = Performance.get_monitor(Performance.RENDER_TOTAL_DRAW_CALLS_IN_FRAME),
		primitives = Performance.get_monitor(Performance.RENDER_TOTAL_PRIMITIVES_IN_FRAME)}
	results.append(r)
	print("bench: ", r)
