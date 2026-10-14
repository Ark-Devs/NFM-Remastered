// Stand-ins for the parts of xtGraphics / Record the physics talk to. Sounds
// and effects become events that the sim queues for the Godot side.
#pragma once
#include "jrt.h"

struct SimEvents {
  virtual ~SimEvents() {}
  virtual void crash(float, int) {}
  virtual void skid(int, float) {}
  virtual void scrape(int, int, int) {}
  virtual void gscrape(int, int, int) {}
  virtual void carfixed() {}
  virtual void dust(ContO *, int, float, float, float, int, int, float, int, bool) {}
  virtual void sprk(ContO *, float, float, float, float, float, float, int) {}
};

struct soundClip {
  SimEvents *ev = nullptr;
  void play() { if (ev) ev->carfixed(); }
};

struct xtGraphics {
  SimEvents *ev = nullptr;
  int im = 0;
  int multion = 0;
  bool lan = false;
  Arr<jbool> isbot{false, false, false, false, false, false, false, false};
  bool mutes = false;
  int starcnt = 0;
  int laptime = 0;
  int fastestlap = 0;
  int beststunt = 0;
  Arr<int> dcrashes = Arr<int>(8);
  soundClip *carfixed = nullptr;
  void crash(float f, int n) { if (ev) ev->crash(f, n); }
  void skid(int n, float f) { if (ev) ev->skid(n, f); }
  void scrape(int x, int y, int z) { if (ev) ev->scrape(x, y, z); }
  void gscrape(int x, int y, int z) { if (ev) ev->gscrape(x, y, z); }
};

struct Record {
  Arr<int> dest = Arr<int>(8);
  Arr<int> fix = Arr<int>(8);
  int powered = 0;
  int wasted = 0;
  bool hcaught = false;
  int whenwasted = 0;
  int closefinish = 0;
  void recx(int, float, int) {}
  void recy(int, float, bool, int) {}
  void recz(int, float, int) {}
  void cotchinow(int) {}
};
