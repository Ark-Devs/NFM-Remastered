// Ram-bot fights: both cars are driven like a player chases in deathmatch —
// full throttle straight at the rival (aiming a little ahead of it), handbrake
// for tight turns, back out when stuck. Real arenas, no fixes, until one is
// wasted (5 min cap). Power also sets top speed, and players keep it up with
// stunts, so in each fight each car randomly keeps its bar full (lots of
// stunts), keeps it at two thirds, or lets it drain.
//
// usage: ramfight <models> <mycars> <stages> pair <A> <B> [fights]
//        ramfight <models> <mycars> <stages> all <out.txt> [part parts]
//   all: "A|B|winsA|winsB|dmgAtoB|dmgBtoA|seconds" (damage = total % over the fights)
#define _USE_MATH_DEFINES
#include <dirent.h>
#include <cmath>
#include <cstdio>
#include <cstring>
#include <fstream>
#include <sstream>
#include "../src/sim.h"
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
static const int ARENAS[] = {2, 3, 4, 6, 8, 12, 13, 21, 22, 24};
static int LEFT_TURNS = 1; // sign of xz change when steering left (measured at start)

static double wrap(double a) { while (a > 180) a -= 360; while (a <= -180) a += 360; return a; }
// NFM: a car at heading xz moves along (-sin xz, cos xz)
static double heading_to(Sim &s, int i, int j) {
  ContO *a = s.cars[i], *b = s.cars[j];
  Mad *mb = s.mads[j];
  double lead = std::min(25.0, std::hypot(b->x - a->x, b->z - a->z) / 300.0); // ticks ahead
  double vx = -std::sin(b->xz * M_PI / 180) * mb->speed, vz = std::cos(b->xz * M_PI / 180) * mb->speed;
  double tx = b->x + vx * lead * 0.5, tz = b->z + vz * lead * 0.5;
  return std::atan2(-(tx - a->x), tz - a->z) * 180 / M_PI;
}
struct Bot { int stuck = 0, back = 0; };
static int input(Sim &s, int i, int j, Bot &b) {
  Mad *m = s.mads[i];
  if (b.back > 0) { b.back--; return 8 | (b.back % 20 < 10 ? 1 : 2); }
  if (std::fabs(m->speed) < 15) { if (++b.stuck > 50) { b.stuck = 0; b.back = 30; } } else b.stuck = 0;
  double diff = wrap(heading_to(s, i, j) - s.cars[i]->xz);
  int in = 4;
  if (diff * LEFT_TURNS > 4) in |= 1; else if (diff * LEFT_TURNS < -4) in |= 2;
  if (std::fabs(diff) > 70 && m->speed > 120) in |= 16;
  return in;
}
// ha / hb: separate hits landed (damage after 8+ quiet ticks starts a new hit)
struct Res { int wa = 0, wb = 0, ha = 0, hb = 0; double da = 0, db = 0, secs = 0; };
static Res fight(Sim &s, const std::string &dir, int A, int B, int n, int seed0) {
  Res r;
  for (int k = 0; k < n; k++) {
    int sid = ARENAS[(seed0 + k * 3) % 10];
    bool sw = k % 2;
    s.load_stage(slurp(dir + "/" + std::to_string(sid) + ".txt"), sid, 2, seed0 * 31 + k);
    s.start_race(sw ? std::vector<int>{B, A} : std::vector<int>{A, B}, 3u, 0.3f, seed0 * 17 + k);
    Bot b0, b1;
    float last[2] = {0, 0};
    int quietFor[2] = {99, 99}, hits[2] = {0, 0};
    // power plan per car this fight: 0 kept full, 1 kept at 2/3, 2 drains
    uint32_t h = (uint32_t)(seed0 * 7919 + k * 104729 + 12345);
    int plan[2] = {(int)(h % 3), (int)((h / 3) % 3)};
    int t = 0;
    for (; t < 7600; t++) {
      std::vector<int> in{0, 0};
      if (s.xt.starcnt == 0) { in[0] = input(s, 0, 1, b0); in[1] = input(s, 1, 0, b1); }
      for (int c = 0; c < 2; c++) {
        if (plan[c] == 0) s.mads[c]->power = 98;
        else if (plan[c] == 1 && s.mads[c]->power < 65) s.mads[c]->power = 65;
      }
      s.step(in); s.events.clear();
      for (int c = 0; c < 2; c++) {
        if (s.combat[c].dealt > last[c] + 0.01f) { if (quietFor[c] >= 8) hits[c]++; quietFor[c] = 0; last[c] = s.combat[c].dealt; }
        else quietFor[c]++;
      }
      if (s.mads[0]->dest || s.mads[1]->dest) break;
    }
    int ia = sw ? 1 : 0, ib = 1 - ia;
    r.da += s.combat[ia].dealt; r.db += s.combat[ib].dealt; r.secs += std::max(0, t - 130) / 25.0;
    r.ha += hits[ia]; r.hb += hits[ib];
    if (s.mads[ia]->dest != s.mads[ib]->dest) { if (s.mads[ib]->dest) r.wa++; else r.wb++; }
  }
  return r;
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
  s.apply_balance(slurp("../game/assets/car_balance.json")); // same balance as the game
  s.deathmatch = true; s.fixLimit = 0;
  std::string dir = argv[3];
  // which way does "left" turn the heading?
  s.load_stage(slurp(dir + "/1.txt"), 1, 2, 1); s.start_race({0, 1}, 3u, 0.3f, 1);
  while (s.xt.starcnt > 0) s.step({0, 0});
  for (int t = 0; t < 40; t++) s.step({4, 0});
  int x0 = s.cars[0]->xz;
  for (int t = 0; t < 15; t++) s.step({5, 0});
  LEFT_TURNS = wrap(s.cars[0]->xz - x0) > 0 ? 1 : -1;
  auto slotOf = [&](const char *name) { for (auto &c : s.carList) if (c.name == name) return c.slot; return -1; };
  if (!strcmp(argv[4], "pair")) {
    int n = argc > 7 ? atoi(argv[7]) : 20;
    Res r = fight(s, dir, slotOf(argv[5]), slotOf(argv[6]), n, 1);
    printf("%-14s vs %-14s %d ram-bot fights: wins %2d-%-2d  damage dealt %5.0f%% vs %5.0f%%  (avg %.0fs)\n", argv[5], argv[6], n, r.wa, r.wb, r.da, r.db, r.secs / n);
    return 0;
  }
  FILE *o = fopen(argv[5], "w");
  int nc = (int)s.carList.size();
  int part = argc > 7 ? atoi(argv[6]) : 0, parts = argc > 7 ? atoi(argv[7]) : 1, idx = 0;
  std::string only = argc > 8 ? argv[8] : ""; // only pairings with this car
  for (int a = 0; a < nc; a++) {
    for (int b = a + 1; b < nc; b++) {
      if (!only.empty() && s.carList[a].name != only && s.carList[b].name != only) continue;
      if (idx++ % parts != part) continue; // split across processes
      Res r = fight(s, dir, s.carList[a].slot, s.carList[b].slot, 6, a * 53 + b);
      fprintf(o, "%s|%s|%d|%d|%.1f|%.1f|%.1f|%d|%d\n", s.carList[a].name.c_str(), s.carList[b].name.c_str(), r.wa, r.wb, r.da, r.db, r.secs, r.ha, r.hb);
      fflush(o);
    }
    printf(".");
  }
  fclose(o);
  printf("\n");
}
