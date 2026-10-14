// Headless check: load the original models, run an all-AI race on a stage.
#include <dirent.h>
#include <chrono>
#include <cstdio>
#include <fstream>
#include <sstream>
#include "../src/sim.h"

static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }

int main(int argc, char **argv) {
  std::string models = argc > 1 ? argv[1] : "/tmp/nfm_models";
  std::string stage = argc > 2 ? argv[2] : "../../stages/1.txt";
  int ticks = argc > 3 ? atoi(argv[3]) : 3000;
  setvbuf(stdout, nullptr, _IONBF, 0);
  Sim sim;
  DIR *d = opendir(models.c_str());
  for (dirent *e; (e = readdir(d));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") sim.add_model(n, slurp(models + "/" + n)); }
  closedir(d);
  if (getenv("CUSTOM")) { std::string cdir = getenv("CUSTOM"); DIR *cd2 = opendir(cdir.c_str()); for (dirent *e; cd2 && (e = readdir(cd2));) { std::string n = e->d_name; if (n.size() > 6 && n.substr(n.size() - 6) == " C.rad") sim.add_custom(n, n.substr(0, n.size() - 6), slurp(cdir + "/" + n)); } if (cd2) closedir(cd2); }
  auto t0 = std::chrono::steady_clock::now();
  sim.build_world();
  auto t1 = std::chrono::steady_clock::now();
  printf("world: %zu cars, %.0f ms\n", sim.carList.size(), std::chrono::duration<double, std::milli>(t1 - t0).count());
  for (auto &c : sim.carList) {
    ContO *b = sim.base[c.slot];
    if (getenv("QUIET")) continue;
    printf("  %d %s npl=%d grat=%d maxR=%d errd=%d %s\n", c.slot, c.name.c_str(), b->npl, b->grat, b->maxR, (int)b->errd, b->errd ? b->err.substr(0, 160).c_str() : "");
  }
  sim.load_stage(slurp(stage), 1, 7, 42);
  printf("stage: %zu objects, %d checkpoints, %d laps\n", sim.objects.size(), sim.cp->nsp, sim.cp->nlaps);
  sim.start_race({14, 0, 1, 2, 3, 4, 5}, 0 /* all AI */, 0.3f, 42);
  auto t2 = std::chrono::steady_clock::now();
  int crashes = 0;
  for (int i = 0; i < ticks && !sim.finished; i++) {
    sim.step({});
    for (auto &e : sim.events) if (e.type == EV_CRASH) crashes++;
    sim.events.clear();

    if (!getenv("QUIET") && i % 500 == 0) printf("t=%d car0 (%d,%d,%d) speed %.1f clear %d hash %08x\n", i, sim.cars[0]->x, sim.cars[0]->y, sim.cars[0]->z, sim.mads[0]->speed, sim.mads[0]->clear, sim.state_hash());
  }
  auto t3 = std::chrono::steady_clock::now();
  printf("ran %d ticks in %.1f ms (%.3f ms/tick)\n", sim.tick, std::chrono::duration<double, std::milli>(t3 - t2).count(), std::chrono::duration<double, std::milli>(t3 - t2).count() / std::max(1, sim.tick));
  printf("finished=%d kind=%d who=%d rescues=%d\n", sim.finished, sim.resultKind, sim.resultWho, sim.rescues);
  for (int i = 0; i < sim.nplayers; i++) printf("  %d %-16s pos %d clear %d dest %d hit %d\n", i, sim.cd->names[sim.sc[i]].c_str(), sim.cp->pos[i] + 1, sim.mads[i]->clear, (int)sim.mads[i]->dest, sim.mads[i]->hitmag);
  printf("hash %08x\n", sim.state_hash());
}
