// Dumps each car's collision settings as JSON (name -> {moment, push, revpush, lift, revlift, dammult, maxmag, clrad, top}).
#include <dirent.h>
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
  auto *c = s.cd;
  printf("{\n");
  bool first = true;
  for (auto &car : s.carList) {
    int k = car.slot;
    printf("%s\"%s\": {\"moment\": %.2f, \"push\": %d, \"revpush\": %d, \"lift\": %d, \"revlift\": %d, \"dammult\": %.3f, \"maxmag\": %d, \"clrad\": %d, \"top\": %d}", first ? "" : ",\n", car.name.c_str(), c->moment[k], c->push[k], c->revpush[k], c->lift[k], c->revlift[k], c->dammult[k], c->maxmag[k], c->clrad[k], c->swits[k][2]);
    first = false;
  }
  printf("\n}\n");
}
