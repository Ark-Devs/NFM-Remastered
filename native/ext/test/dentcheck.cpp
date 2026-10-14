// For every car: after spawning and the countdown, how far have its model
// points moved from the clean model, and has it taken any damage?
#include <dirent.h>
#include <cmath>
#include <cstdio>
#include <fstream>
#include <sstream>
#include "../src/sim.h"
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
int main(int, char **argv) {
  Sim s;
  DIR *dd = opendir(argv[1]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") s.add_model(n, slurp(std::string(argv[1]) + "/" + n)); }
  closedir(dd);
  dd = opendir(argv[2]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 6 && n.substr(n.size() - 6) == " C.rad") s.add_custom(n, n.substr(0, n.size() - 6), slurp(std::string(argv[2]) + "/" + n)); }
  closedir(dd);
  s.build_world();
  std::string stage = slurp(std::string(argv[3]) + "/2.txt");
  for (auto &car : s.carList) {
    s.load_stage(stage, 2, 1, 1);
    s.start_race({car.slot}, 1u, 0.3f, 1);
    ContO *b = s.base[car.slot], *c = s.cars[0];
    auto maxMove = [&]() { double m = 0; for (int i = 0; i < c->npl && i < b->npl; i++) for (int j = 0; j < c->p[i]->n; j++) m = std::max(m, std::hypot(std::hypot(c->p[i]->ox[j] - b->p[i]->ox[j], c->p[i]->oy[j] - b->p[i]->oy[j]), c->p[i]->oz[j] - b->p[i]->oz[j])); return m; };
    double atSpawn = maxMove();
    for (int t = 0; t < 140; t++) s.step({0});
    double after = maxMove();
    if (after > 1 || atSpawn > 1 || s.mads[0]->hitmag > 0)
      printf("%-24s moved at spawn %6.1f  after countdown %6.1f  hitmag %d  (grat %d)\n", car.name.c_str(), atSpawn, after, s.mads[0]->hitmag, b->grat);
  }
  printf("(cars not listed: no change)\n");
}
