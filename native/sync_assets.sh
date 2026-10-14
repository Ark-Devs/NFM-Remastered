#!/bin/sh
# Copies the game data the remake uses into the Godot project (game/assets/),
# so the exported .exe carries everything, and writes a manifest of it.
set -e
cd "$(dirname "$0")"
SRC=..
DST=game/assets
mkdir -p "$DST/stages" "$DST/mystages/mymusic" "$DST/mycars" "$DST/music"
cp "$SRC/data/models.zip" "$SRC/data/sounds.zip" "$DST/"
cp "$SRC"/stages/*.txt "$DST/stages/"
cp "$SRC"/mystages/*.txt "$DST/mystages/"
cp "$SRC"/mystages/mymusic/* "$DST/mystages/mymusic/" 2>/dev/null || true
cp "$SRC"/mycars/*" C.rad" "$DST/mycars/"
cp "$SRC"/music/*.zip "$DST/music/"
node -e "
const fs=require('fs'),p=require('path');const d='$DST';
const ls=(x,re)=>fs.readdirSync(p.join(d,x)).filter(f=>re.test(f)).sort();
fs.writeFileSync(p.join(d,'manifest.json'),JSON.stringify({stages:ls('stages',/\.txt$/),mystages:ls('mystages',/\.txt$/),mymusic:ls('mystages/mymusic',/\.zip$/i),mycars:ls('mycars',/ C\.rad$/i),music:ls('music',/\.zip$/)},null,1));"
du -sh "$DST"
