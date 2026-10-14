// Moving-clash lab: both cars drive at each other and cross paths at a random
// angle (110-250 degrees apart). On every clash each car gets its own random
// power (full, two thirds, one third) and speed (full, two thirds, one third
// of its top speed), so fights at every pace are covered, not just flat out.
// Each starts the same distance from the crossing point and the quicker one
// waits, so they arrive together.
// In NFM only the car with the bigger power x speed x weight deals damage in a
// clash, so this shows what really happens when two cars meet in a fight.
// Positions reset after each clash; damage carries over until one is wasted.
//
// usage: joust <models> <mycars> <stages> pair <A> <B>
//        joust <models> <mycars> <stages> all <out.txt>
//   all: "A|B|winsA|winsB|dmgAtoB|dmgBtoA" per pairing (avg % per clash)
#include <dirent.h>
#include <algorithm>
#include <cmath>
#include <cstdio>
#include <cstring>
#include <fstream>
#include <map>
#include <sstream>
#include "../src/sim.h"
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
static const double PI = 3.141592653589793;
#ifdef JTRACE
#define TRACE(...) (fprintf(stderr, __VA_ARGS__), fprintf(stderr, "\n"))
#else
#define TRACE(...) ((void)0)
#endif
static const int MX = 0, MZ = 30000, R = 9000; // crossing point, run-up (NFM units)

static void place(Sim &s, int i, int x, int z, int xz) {
  ContO *c = s.cars[i]; Mad *m = s.mads[i];
  c->x = x; c->z = z; c->y = 250 - c->grat; c->xz = xz; c->xy = 0; c->zy = 0;
  m->pxy = 0; m->pzy = 0; m->speed = 0;
  for (int k = 0; k < 4; k++) { m->scx[k] = 0; m->scy[k] = 0; m->scz[k] = 0; }
  m->cxz = xz; m->mxz = xz; m->power = 98;
}
// start a car on the circle around the crossing point, facing it
static void startAt(Sim &s, int i, double ang, int side) {
  double dx = std::sin(ang), dz = std::cos(ang); // direction of travel
  int xz = (int)std::lround(-ang * 180.0 / PI);  // NFM: xz turns the other way
  place(s, i, (int)(MX - dx * R + side), (int)(MZ - dz * R), xz);
}
static const float POWER[3] = {98.0f, 65.0f, 33.0f};
static const float SPEED[3] = {1.0f, 0.66f, 0.33f};
// throttle that holds a car at (or below) its speed level
static int drive(Sim &s, int i, int lvl) {
  float cap = SPEED[lvl] * s.cd->swits[s.mads[i]->cn][2];
  return lvl == 0 || s.mads[i]->speed < cap ? 4 : 0;
}
// ticks a car needs to cover the run-up from a standstill at a power / speed level
static std::map<int, int> runup;
static int runupTicks(Sim &s, const std::string &stage, int slot, int pl, int sl) {
  int key = slot * 16 + pl * 4 + sl;
  if (runup.count(key)) return runup[key];
  s.load_stage(stage, 1, 2, 1); s.start_race({slot, slot}, 3u, 0.3f, 1);
  std::vector<int> idle{0, 0};
  while (s.xt.starcnt > 0) s.step(idle);
  place(s, 0, 0, MZ - R, 0); place(s, 1, 40000, 0, 0);
  for (int t = 0; t < 12; t++) s.step(idle);
  int t = 0;
  for (; t < 1500 && s.cars[0]->z < MZ; t++) { s.mads[0]->power = POWER[pl]; s.step({drive(s, 0, sl), 0}); s.events.clear(); }
  return runup[key] = t;
}
struct J { double dAB = 0, dBA = 0; int clashes = 0, winner = -1; };
static J joust(Sim &s, const std::string &stage, int A, int B, int seed) {
  // run-up times for every level, measured before the duel (it reuses the sim)
  for (int p = 0; p < 3; p++) for (int q = 0; q < 3; q++) { runupTicks(s, stage, A, p, q); runupTicks(s, stage, B, p, q); }
  uint32_t rng = 2654435761u * (uint32_t)(seed + 1);
  auto rnd = [&](int n) { rng = rng * 1664525u + 1013904223u; return (int)((rng >> 8) % (uint32_t)n); };
  s.deathmatch = false;
  s.load_stage(stage, 1, 2, seed); s.start_race({A, B}, 3u, 0.3f, seed);
  std::vector<int> idle{0, 0};
  while (s.xt.starcnt > 0) s.step(idle);
  int ma = s.cd->maxmag[s.mads[0]->cn], mb = s.cd->maxmag[s.mads[1]->cn];
  J j; int quiet = 0;
  for (int c = 0; c < 60 && !s.mads[0]->dest && !s.mads[1]->dest; c++) {
    double angA = rnd(360) * PI / 180.0;
    double angB = angA + PI + (rnd(141) - 70) * PI / 180.0; // 110-250 deg apart: head-on to wide crossings
    int pa = rnd(3), sa = rnd(3), pb = rnd(3), sb = rnd(3);
    int ta = runup[A * 16 + pa * 4 + sa], tb = runup[B * 16 + pb * 4 + sb];
    startAt(s, 0, angA, rnd(121) - 60);
    startAt(s, 1, angB, rnd(121) - 60);
    for (int t = 0; t < 12; t++) s.step(idle);
    int ha = s.mads[0]->hitmag, hb = s.mads[1]->hitmag, contact = -1;
    int wa = std::max(0, tb - ta), wb = std::max(0, ta - tb); // the quicker car waits
    for (int t = 0; t < 1600; t++) {
      std::vector<int> in{t >= wa ? drive(s, 0, sa) : 0, t >= wb ? drive(s, 1, sb) : 0};
      s.mads[0]->power = POWER[pa]; s.mads[1]->power = POWER[pb];
      s.step(in); s.events.clear();
      if (contact < 0 && (s.mads[0]->hitmag != ha || s.mads[1]->hitmag != hb)) contact = t;
      if (contact >= 0 && t - contact > 25) break;
      if (s.mads[0]->dest || s.mads[1]->dest) break;
    }
    double da = 100.0 * (s.mads[1]->hitmag - hb) / mb, db = 100.0 * (s.mads[0]->hitmag - ha) / ma;
    if (contact < 0 || (da < 0.3 && db < 0.3)) { if (++quiet >= 8) break; continue; } // missed: try again
    quiet = 0;
    j.dAB += da; j.dBA += db;
    j.clashes++;
  }
  if (j.clashes) { j.dAB /= j.clashes; j.dBA /= j.clashes; }
  j.winner = s.mads[1]->dest && !s.mads[0]->dest ? 0 : s.mads[0]->dest && !s.mads[1]->dest ? 1 : -1;
  return j;
}
int main(int argc, char **argv) {
  setvbuf(stdout, nullptr, _IONBF, 0);
  Sim s;
  DIR *dd = opendir(argv[1]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") s.add_model(n, slurp(std::string(argv[1]) + "/" + n)); }
  closedir(dd);
  dd = opendir(argv[2]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 6 && n.substr(n.size() - 6) == " C.rad") s.add_custom(n, n.substr(0, n.size() - 6), slurp(std::string(argv[2]) + "/" + n)); }
  closedir(dd);
  s.build_world();
  std::string stage = slurp(std::string(argv[3]) + "/1.txt");
  auto slotOf = [&](const char *name) { for (auto &c : s.carList) if (c.name == name) return c.slot; return -1; };
  if (!strcmp(argv[4], "pair")) {
    int wa = 0, wb = 0; double ab = 0, ba = 0, cl = 0;
    for (int k = 0; k < 10; k++) { J j = joust(s, stage, slotOf(argv[5]), slotOf(argv[6]), 100 + k); ab += j.dAB; ba += j.dBA; cl += j.clashes; if (j.winner == 0) wa++; if (j.winner == 1) wb++; }
    printf("%-14s vs %-14s 10 fights: wins %2d-%-2d  avg damage per clash %4.1f%% vs %4.1f%%  (%.0f clashes per fight)\n", argv[5], argv[6], wa, wb, ab / 10, ba / 10, cl / 10);
    return 0;
  }
  FILE *o = fopen(argv[5], "w");
  int nc = (int)s.carList.size();
  for (int a = 0; a < nc; a++) {
    for (int b = a + 1; b < nc; b++) {
      double ab = 0, ba = 0; int wa = 0, wb = 0;
      for (int k = 0; k < 3; k++) { J j = joust(s, stage, s.carList[a].slot, s.carList[b].slot, 500 + k + a * 7 + b); ab += j.dAB; ba += j.dBA; if (j.winner == 0) wa++; if (j.winner == 1) wb++; }
      fprintf(o, "%s|%s|%d|%d|%.2f|%.2f\n", s.carList[a].name.c_str(), s.carList[b].name.c_str(), wa, wb, ab / 3, ba / 3);
    }
    printf(".");
  }
  fclose(o);
  printf("\n");
}
