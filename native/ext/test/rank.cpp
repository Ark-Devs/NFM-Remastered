// Measures how good every car really is: each car races (AI-driven) against
// the same field of six original cars on several stages and seeds, and is
// scored on finishing position, opponents wrecked and its own survival.
// Writes JSON {name: score} for the career order.
#include <dirent.h>
#include <algorithm>
#include <cstdio>
#include <fstream>
#include <sstream>
#include "../src/sim.h"

static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }

int main(int argc, char **argv) {
  setvbuf(stdout, nullptr, _IONBF, 0);
  std::string models = argv[1], customs = argv[2], stages = argv[3], out = argv[4];
  Sim sim;
  DIR *d = opendir(models.c_str());
  for (dirent *e; (e = readdir(d));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") sim.add_model(n, slurp(models + "/" + n)); }
  closedir(d);
  d = opendir(customs.c_str());
  for (dirent *e; d && (e = readdir(d));) { std::string n = e->d_name; if (n.size() > 6 && n.substr(n.size() - 6) == " C.rad") sim.add_custom(n, n.substr(0, n.size() - 6), slurp(customs + "/" + n)); }
  if (d) closedir(d);
  sim.build_world();
  const int field[6] = {0, 1, 2, 3, 5, 6};          // Tornado Shark, Formula 7, Caninaro, La Vita Crab, MAX Revenge, Lead Oxide
  const int stageIds[6] = {1, 2, 5, 9, 12, 16};      // straights, jumps, a fight-heavy stage, longer tracks
  const int seeds = 3;
  FILE *o = fopen(out.c_str(), "w");
  fprintf(o, "{\n");
  bool first = true;
  for (auto &car : sim.carList) {
    double score = 0, pos = 0, wrecks = 0, deaths = 0;
    int races = 0;
    for (int sid : stageIds) {
      std::string text = slurp(stages + "/" + std::to_string(sid) + ".txt");
      for (int seed = 1; seed <= seeds; seed++) {
        sim.load_stage(text, sid, 7, seed * 7919 + sid);
        std::vector<int> grid{car.slot};
        for (int f : field) grid.push_back(f == car.slot ? 4 : f);
        sim.start_race(grid, 0, 0.3f, seed * 104729 + sid);
        int total = sim.cp->nlaps * sim.cp->nsp;
        for (int t = 0; t < 7000; t++) {
          sim.step({});
          sim.events.clear();
          if (sim.cp->clear[0] >= total || sim.mads[0]->dest) break;
        }
        int p = sim.cp->pos[0] + 1;
        int w = 0;
        for (int i = 1; i < sim.nplayers; i++) if (sim.wrecked[i]) w++;
        bool dead = sim.mads[0]->dest;
        pos += p; wrecks += w; deaths += dead;
        // points: finishing high, wrecking rivals, staying alive
        score += (8 - p) + 2.0 * w - (dead ? 3.0 : 0.0);
        races++;
      }
    }
    printf("%-26s score %6.2f  avg pos %.2f  wrecks/race %.2f  wasted %.0f%%\n", car.name.c_str(), score / races, pos / races, wrecks / races, 100.0 * deaths / races);
    fprintf(o, "%s  \"%s\": %.3f", first ? "" : ",\n", car.name.c_str(), score / races);
    first = false;
  }
  fprintf(o, "\n}\n");
  fclose(o);
}
