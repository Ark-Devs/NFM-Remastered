// 1v1 AI deathmatches (no fixes) with exact damage per car.
// mode "pair <A> <B>": 40 fights between two cars, summary printed.
// mode "all <out>":    every pairing of every car, 4 fights each, written as
//                      "A|B|winsA|winsB|dmgAtoB|dmgBtoA|seconds" (damage in % of victim health).
#include <dirent.h>
#include <cstdio>
#include <cstring>
#include <fstream>
#include <sstream>
#include "../src/sim.h"
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
struct Res { int wa = 0, wb = 0; double da = 0, db = 0, secs = 0; };
static const int ARENAS[] = {2, 3, 4, 6, 8, 12, 13, 21, 22, 24};
static Res fight(Sim &s, const std::string &dir, int A, int B, int n, int seed0) {
  Res r;
  for (int m = 0; m < n; m++) {
    int sid = ARENAS[(seed0 + m * 3) % 10];
    bool sw = m % 2;
    s.load_stage(slurp(dir + "/" + std::to_string(sid) + ".txt"), sid, 2, seed0 * 31 + m);
    s.start_race(sw ? std::vector<int>{B, A} : std::vector<int>{A, B}, 0u, 0.3f, seed0 * 17 + m);
    int t = 0;
    for (; t < 7500; t++) { s.step({}); s.events.clear(); if (s.mads[0]->dest || s.mads[1]->dest) break; }
    int ia = sw ? 1 : 0, ib = 1 - ia;
    r.da += s.combat[ia].dealt; r.db += s.combat[ib].dealt; r.secs += std::max(0, t - 130) / 25.0;
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
  s.deathmatch = true; s.fixLimit = 0;
  std::string dir = argv[3];
  auto slotOf = [&](const char *name) { for (auto &c : s.carList) if (c.name == name) return c.slot; return -1; };
  if (!strcmp(argv[4], "pair")) {
    Res r = fight(s, dir, slotOf(argv[5]), slotOf(argv[6]), 40, 1);
    printf("%s vs %s, 40 AI fights: wins %d-%d; damage dealt per minute of fighting %.0f%% vs %.0f%%\n", argv[5], argv[6], r.wa, r.wb, 60 * r.da / r.secs, 60 * r.db / r.secs);
    return 0;
  }
  FILE *o = fopen(argv[5], "w");
  int nc = (int)s.carList.size();
  for (int a = 0; a < nc; a++) {
    for (int b = a + 1; b < nc; b++) {
      Res r = fight(s, dir, s.carList[a].slot, s.carList[b].slot, 4, a * 53 + b);
      fprintf(o, "%s|%s|%d|%d|%.1f|%.1f|%.1f\n", s.carList[a].name.c_str(), s.carList[b].name.c_str(), r.wa, r.wb, r.da, r.db, r.secs);
    }
    printf(".");
  }
  fclose(o);
  printf("\n");
}
