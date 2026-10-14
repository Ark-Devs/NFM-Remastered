// Deathmatch AI check: power when attacking, match length, fix limit.
#include <dirent.h>
#include <cstdio>
#include <fstream>
#include <sstream>
#include "../src/sim.h"
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
int main(int, char **argv) {
  setvbuf(stdout, nullptr, _IONBF, 0);
  Sim sim;
  DIR *d = opendir(argv[1]);
  for (dirent *e; (e = readdir(d));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") sim.add_model(n, slurp(std::string(argv[1]) + "/" + n)); }
  closedir(d);
  sim.build_world();
  sim.deathmatch = true;
  for (int fl : {-1, 0}) {
    sim.fixLimit = fl;
    double pw = 0; long pwN = 0, ticks = 0; int ended = 0, races = 0, fixes = 0;
    for (int sid : {2, 6, 12, 21}) for (int seed = 1; seed <= 3; seed++) for (int np : {2, 7}) {
      sim.load_stage(slurp(std::string(argv[2]) + "/" + std::to_string(sid) + ".txt"), sid, np, seed * 31 + sid);
      std::vector<int> grid{13, 0, 1, 2, 5, 6, 11}; grid.resize(np);
      sim.start_race(grid, 0, 0.3f, seed * 977 + sid);
      std::vector<int> was(np, 0);
      int t = 0;
      for (; t < 6000; t++) {
        sim.step({}); sim.events.clear();
        for (int i = 0; i < np; i++) { int a = sim.controls[i]->attack; if (a > 0 && !was[i]) { pw += sim.mads[i]->power; pwN++; } was[i] = a > 0; }
        int alive = 0; for (int i = 0; i < np; i++) alive += !sim.mads[i]->dest;
        if (alive <= 1) { ended++; break; }
      }
      for (auto &c : sim.combat) fixes += c.fixes;
      ticks += t; races++;
    }
    printf("fixes %-9s: power when an attack starts %.0f/98, ended %d/%d, avg length %.0fs, repairs used %d\n", fl < 0 ? "unlimited" : "off", pw / std::max(1L, pwN), ended, races, ticks / 25.0 / races, fixes);
  }
}
