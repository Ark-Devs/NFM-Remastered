// Game-AI deathmatch fights: both cars driven by the game's own deathmatch AI
// (original attack logic in hunter mode), real arenas, no fixes, 5 min cap.
// Complements ramfight.cpp (player-like ram bot): each catches matchups the
// other misses, and fit_bars.mjs uses both.
//
// usage: aifights <models> <mycars> <stages> all <out.txt> [part parts]
//   "A|B|winsA|winsB|dmgAtoB|dmgBtoA|seconds|hitsA|hitsB" (6 fights per pairing)
#include <dirent.h>
#include <cstdio>
#include <cstring>
#include <fstream>
#include <sstream>
#include "../src/sim.h"
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
static const int ARENAS[] = {2, 3, 4, 6, 8, 12, 13, 21, 22, 24};
struct Res { int wa = 0, wb = 0, ha = 0, hb = 0; double da = 0, db = 0, secs = 0; };
static Res fight(Sim &s, const std::string &dir, int A, int B, int n, int seed0) {
  Res r;
  for (int k = 0; k < n; k++) {
    int sid = ARENAS[(seed0 + k * 3) % 10];
    bool sw = k % 2;
    s.load_stage(slurp(dir + "/" + std::to_string(sid) + ".txt"), sid, 2, seed0 * 31 + k);
    s.start_race(sw ? std::vector<int>{B, A} : std::vector<int>{A, B}, 0u, 0.3f, seed0 * 17 + k);
    float last[2] = {0, 0};
    int quietFor[2] = {99, 99}, hits[2] = {0, 0};
    int t = 0;
    for (; t < 7600; t++) {
      s.step({}); s.events.clear();
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
  FILE *o = fopen(argv[5], "w");
  int part = argc > 7 ? atoi(argv[6]) : 0, parts = argc > 7 ? atoi(argv[7]) : 1, idx = 0;
  std::string only = argc > 8 ? argv[8] : ""; // only pairings with this car
  int nc = (int)s.carList.size();
  for (int a = 0; a < nc; a++)
    for (int b = a + 1; b < nc; b++) {
      if (!only.empty() && s.carList[a].name != only && s.carList[b].name != only) continue;
      if (idx++ % parts != part) continue;
      Res r = fight(s, dir, s.carList[a].slot, s.carList[b].slot, 6, a * 61 + b);
      fprintf(o, "%s|%s|%d|%d|%.1f|%.1f|%.1f|%d|%d\n", s.carList[a].name.c_str(), s.carList[b].name.c_str(), r.wa, r.wb, r.da, r.db, r.secs, r.ha, r.hb);
      fflush(o);
    }
  fclose(o);
}
