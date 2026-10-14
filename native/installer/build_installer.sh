#!/bin/sh
# Builds native/build/NeedForMadness-Setup.exe from the exported game.
# Export the game first (Godot --export-release "Windows Desktop").
set -e
cd "$(dirname "$0")"
/c/msys64/mingw64/bin/makensis.exe -V2 nfm.nsi
