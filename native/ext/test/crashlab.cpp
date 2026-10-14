// Crash lab: measures, in the real physics, how many full-power rams each car
// survives and how hard each car hits. A victim sits on a long flat straight;
// the attacker is placed 160 m behind it and drives straight in at full power.
// Positions reset after every hit; damage carries over until the victim is
// wasted. Side (T-bone) and head-on (nose to nose) hits are both tested.
//
// Damage in NFM is per vertex near the impact point, and dents move vertices
// away, so repeat hits on one spot can fade to nothing: a duel stops once
// three hits in a row do (almost) no damage, and the victim counts as having
// survived (40 hits).
//
// usage: crashlab <models dir> <mycars dir> <stages dir> <out.json>
#include <dirent.h>
#include <algorithm>
#include <cmath>
#include <cstdio>
#include <fstream>
#include <sstream>
#include "../src/sim.h"

static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }

struct Duel { double hits = 0, pct3 = 0; bool wasted = false; };

static void place(Sim &s, int i, int x, int z, int xz) {
  ContO *c = s.cars[i];
  Mad *m = s.mads[i];
  c->x = x; c->z = z; c->y = 250 - c->grat;
  c->xz = xz; c->xy = 0; c->zy = 0;
  m->pxy = 0; m->pzy = 0; m->speed = 0;
  for (int k = 0; k < 4; k++) { m->scx[k] = 0; m->scy[k] = 0; m->scz[k] = 0; }
  m->cxz = xz; m->mxz = xz;
  m->power = 98;
}

// attacker (grid 0) rams victim (grid 1) until the victim is wasted (max 40 hits)
static Duel duel(Sim &s, const std::string &stage, int attacker, int victim, bool headOn, int seed) {
  uint32_t rng = 2654435761u * (uint32_t)(seed + 1) + (uint32_t)victim * 40503u + (uint32_t)attacker;
  auto rnd = [&](int n) { rng = rng * 1664525u + 1013904223u; return (int)((rng >> 8) % (uint32_t)n); };
  s.load_stage(stage, 1, 2, seed);
  s.start_race({attacker, victim}, 3u, 0.3f, seed);
  std::vector<int> idle{0, 0}, go{4, 0};
  while (s.xt.starcnt > 0) s.step(idle);
  Duel d;
  int maxmag = s.cd->maxmag[s.mads[1]->cn];
  int weak = 0;
  for (int hit = 0; hit < 40 && !s.mads[1]->dest; hit++) {
    // every hit lands somewhere new: random victim angle, small sideways offset
    place(s, 1, 0, 20000, headOn ? 180 : rnd(360));
    place(s, 0, rnd(301) - 150, 12000, 0);
    for (int t = 0; t < 12; t++) s.step(idle); // settle on the road
    int before = s.mads[1]->hitmag;
    int contact = -1;
    for (int t = 0; t < 600; t++) {
      s.mads[0]->power = 98;
      s.step(go);
      s.events.clear();
      if (contact < 0 && s.mads[1]->hitmag != before) contact = t;
      if (contact >= 0 && t - contact > 25) break;
      if (s.mads[1]->dest) break;
    }
    if (contact < 0) break; // a hit that does nothing at all
    double pct = 100.0 * (s.mads[1]->hitmag - before) / maxmag;
    d.hits++;
    if (hit < 3) d.pct3 = std::min(100.0, 100.0 * s.mads[1]->hitmag / maxmag);
    weak = pct < 0.3 ? weak + 1 : 0;
    if (weak >= 3) break;
  }
  d.wasted = s.mads[1]->dest;
  if (d.wasted && d.hits <= 3) d.pct3 = 100;
  if (!d.wasted) d.hits = 40;
  return d;
}

// matrix mode: every car rams every car (3 runs each, median), printed as
// "attacker|victim|hits" lines for the shared-scale fit (tools/fit_bars.mjs)
int main(int argc, char **argv) {
  setvbuf(stdout, nullptr, _IONBF, 0);
  Sim s;
  DIR *dd = opendir(argv[1]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") s.add_model(n, slurp(std::string(argv[1]) + "/" + n)); }
  closedir(dd);
  dd = opendir(argv[2]);
  for (dirent *e; dd && (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 6 && n.substr(n.size() - 6) == " C.rad") s.add_custom(n, n.substr(0, n.size() - 6), slurp(std::string(argv[2]) + "/" + n)); }
  if (dd) closedir(dd);
  s.build_world();
  std::string stage = slurp(std::string(argv[3]) + "/1.txt"); // long flat straight
  FILE *o = fopen(argv[4], "w");
  for (auto &a : s.carList) {
    for (auto &v : s.carList) {
      std::vector<double> h;
      for (int k = 0; k < 3; k++) h.push_back(duel(s, stage, a.slot, v.slot, false, 500 + k * 7 + v.slot).hits);
      std::sort(h.begin(), h.end());
      fprintf(o, "%s|%s|%.0f\n", a.name.c_str(), v.name.c_str(), h[1]);
    }
    printf(".");
  }
  fclose(o);
  printf("\n");
}
