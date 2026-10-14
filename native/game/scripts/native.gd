## Creates the native (C++) objects. Godot 4.7 hands back plain-C extension
## RefCounted objects holding one reference too many, so release it here;
## otherwise they are never freed.
class_name Native
extends RefCounted


static func sim() -> NfmSim:
	var s := NfmSim.new()
	s.unreference()
	return s


static func mod() -> NfmMod:
	var m := NfmMod.new()
	m.unreference()
	return m
