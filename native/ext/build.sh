#!/bin/sh
# Builds the native game library (game/bin/nfm.dll) with MSYS2 g++ (UCRT64).
# Regenerate the translated physics first with: node remake/tools/j2cpp.mjs
set -e
cd "$(dirname "$0")"
export PATH=/c/msys64/ucrt64/bin:$PATH
OPT=${OPT:--O2}
mkdir -p obj
# the big generated file only rebuilds when it changes
if [ ! -f obj/nfm_gen.o ] || [ src/gen/nfm_gen.cpp -nt obj/nfm_gen.o ] || [ src/jrt.h -nt obj/nfm_gen.o ] || [ src/bridges.h -nt obj/nfm_gen.o ]; then
  g++ -std=c++20 $OPT -fwrapv -fno-strict-aliasing -Iinclude -Isrc -c src/gen/nfm_gen.cpp -o obj/nfm_gen.o
fi
g++ -std=c++20 $OPT -fwrapv -fno-strict-aliasing -shared -static -static-libgcc -static-libstdc++ \
  -Iinclude -Isrc src/*.cpp obj/nfm_gen.o -o ../game/bin/nfm.dll
echo built ../game/bin/nfm.dll
