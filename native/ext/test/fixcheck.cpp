// Fix limit check: car A (damaged) is put in a repair hoop, then car B, while
// A (now out of fixes) waits right next to the hoop. Each car should repair
// once and only once.
#include <dirent.h>
#include <cstdio>
#include <fstream>
#include <sstream>
#include "../src/sim.h"
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
static void put(Sim &s, int i, int x, int y, int z) {
  ContO *c = s.cars[i]; Mad *m = s.mads[i];
  c->x = x; c->z = z; c->y = y - c->grat; c->xz = 0; c->xy = 0; c->zy = 0;
  m->speed = 0; for (int k = 0; k < 4; k++) { m->scx[k] = 0; m->scy[k] = 0; m->scz[k] = 0; }
}
int main(int, char **argv) {
  Sim s;
  DIR *dd = opendir(argv[1]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") s.add_model(n, slurp(std::string(argv[1]) + "/" + n)); }
  closedir(dd);
  s.build_world();
  for (int dm = 0; dm < 2; dm++) {
    s.deathmatch = dm; s.fixLimit = 1;
    int sid = 2;
    s.load_stage(slurp(std::string(argv[2]) + "/" + std::to_string(sid) + ".txt"), sid, 2, 7);
    if (s.cp->fn == 0) { printf("stage has no hoop\n"); return 0; }
    s.start_race({0, 1}, 3u, 0.3f, 7);
    if (!dm) { s.mads[0]->fixes = 1; s.mads[1]->fixes = 1; }
    std::vector<int> idle{0, 0};
    while (s.xt.starcnt > 0) s.step(idle);
    int hx = s.cp->fx[0], hy = s.cp->fy[0], hz = s.cp->fz[0];
    auto tryFix = [&](int i) {
      s.mads[i]->hitmag = s.cd->maxmag[s.mads[i]->cn] / 2;
      for (int t = 0; t < 40; t++) { put(s, i, hx, hy, hz); s.step(idle); }
      int left = 100 - 100 * s.mads[i]->hitmag / s.cd->maxmag[s.mads[i]->cn];
      return left;
    };
    int a1 = tryFix(0);
    put(s, 0, hx + 300, hy, hz); // A parks right next to the hoop, out of fixes
    int b1 = tryFix(1);
    int a2 = tryFix(0);
    printf("%s, 1 fix per car: car A after its fix %d%% health, car B (A parked by the hoop) %d%%, car A second try %d%% (fixes used A %d, B %d)\n",
      dm ? "deathmatch" : "race", a1, b1, a2, s.combat[0].fixes, s.combat[1].fixes);
  }
}
