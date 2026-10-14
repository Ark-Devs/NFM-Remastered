#include "sim.h"
#include <cstdio>
#include <sstream>

const std::string JNULL = std::string("\x01<null>\x01");

const char *CAR_FILES[16] = {"2000tornados", "formula7", "canyenaro", "lescrab", "nimi", "maxrevenge", "leadoxide", "koolkat", "drifter", "policecops", "mustang", "king", "audir8", "masheen", "radicalone", "drmonster"};
const char *PIECE_FILES[68] = {"road", "froad", "twister2", "twister1", "turn", "offroad", "bumproad", "offturn", "nroad", "nturn", "roblend", "noblend", "rnblend", "roadend", "offroadend", "hpground", "ramp30", "cramp35", "dramp15", "dhilo15", "slide10", "takeoff", "sramp22", "offbump", "offramp", "sofframp", "halfpipe", "spikes", "rail", "thewall", "checkpoint", "fixpoint", "offcheckpoint", "sideoff", "bsideoff", "uprise", "riseroad", "sroad", "soffroad", "tside", "launchpad", "thenet", "speedramp", "offhill", "slider", "uphill", "roll1", "roll2", "roll3", "roll4", "roll5", "roll6", "opile1", "opile2", "aircheckpoint", "tree1", "tree2", "tree3", "tree4", "tree5", "tree6", "tree7", "tree8", "cac1", "cac2", "cac3", "8sroad", "8soffroad"};

static const int XSTART[8] = {0, -350, 350, 0, -350, 350, 0, 0};
static const int ZSTART[8] = {-760, -380, -380, 0, 380, 380, 760, 0};

Sim *g_sim = nullptr;

// ---- seeded Math.random() (xorshift64*: fast, same sequence everywhere)
static uint64_t g_rng = 0x9E3779B97F4A7C15ULL;
void jrandom_seed(int64_t seed) { g_rng = (uint64_t)seed * 0x9E3779B97F4A7C15ULL + 0x632BE59BD9B4E019ULL; if (!g_rng) g_rng = 1; }
// While a Sim runs: it is the current sim (spark / dust callbacks) and its own
// random state is the global one; the state is saved back afterwards.
struct SimScope {
  Sim *s;
  explicit SimScope(Sim *p) : s(p) { g_sim = p; g_rng = p->rng; }
  ~SimScope() { s->rng = g_rng; }
};

double jrandom() {
  g_rng ^= g_rng >> 12; g_rng ^= g_rng << 25; g_rng ^= g_rng >> 27;
  return (double)((g_rng * 0x2545F4914F6CDD1DULL) >> 11) * (1.0 / 9007199254740992.0);
}

// ContO's particle hooks (the original drew these in Java2D)
void ContO::dust(int wheel, float x, float y, float z, int vx, int vz, float mag, int tilt, bool capsized) {
  if (g_sim) g_sim->dust(this, wheel, x, y, z, vx, vz, mag, tilt, capsized);
}
void ContO::sprk(float x, float y, float z, float vx, float vy, float vz, int kind) {
  if (g_sim) g_sim->sprk(this, x, y, z, vx, vy, vz, kind);
}

std::vector<std::string> split_text_lines(const std::string &text) {
  std::vector<std::string> out;
  size_t a = 0;
  for (size_t k = 0; k < text.size(); k++) {
    if (text[k] == '\n' || text[k] == '\r') {
      out.push_back(text.substr(a, k - a));
      if (text[k] == '\r' && k + 1 < text.size() && text[k + 1] == '\n') k++;
      a = k + 1;
    }
  }
  if (a < text.size()) out.push_back(text.substr(a));
  return out;
}

// GameSparker.getint: values separated by ',' or ')'
static int getint(const std::string &key, const std::string &line, int idx) {
  int count = 0;
  std::string s;
  for (size_t i = key.size() + 1; i < line.size(); i++) {
    char ch = line[i];
    if (ch == ',' || ch == ')') { count++; i++; }
    if (count != idx) continue;
    if (i < line.size()) s += line[i];
  }
  // parseInt: leading digits
  size_t k = 0;
  while (k < s.size() && (unsigned char)s[k] <= ' ') k++;
  bool neg = false;
  if (k < s.size() && (s[k] == '-' || s[k] == '+')) { neg = s[k] == '-'; k++; }
  if (k >= s.size() || s[k] < '0' || s[k] > '9') throw std::runtime_error("bad number in " + line);
  int64_t v = 0;
  while (k < s.size() && s[k] >= '0' && s[k] <= '9') { v = v * 10 + (s[k] - '0'); if (v > 3000000000LL) break; k++; }
  return (int)(neg ? -v : v);
}

static std::vector<double> nums_in(const std::string &line) {
  std::vector<double> out;
  size_t a = line.find('('), b = line.rfind(')');
  if (a == std::string::npos || b == std::string::npos || b <= a) return out;
  std::string in = line.substr(a + 1, b - a - 1);
  std::stringstream ss(in);
  std::string item;
  while (std::getline(ss, item, ',')) {
    char *end = nullptr;
    std::string t = jtrim(item);
    double v = strtod(t.c_str(), &end);
    out.push_back(end && *end == 0 && !t.empty() ? v : NAN);
  }
  return out;
}

std::vector<WheelInfo> parse_wheels(const std::string &text) {
  std::vector<WheelInfo> wheels;
  int rimc[3] = {120, 120, 120};
  float rimSize = 2, rimDepth = 3;
  double div = 1, iwid = 1, sc[3] = {1, 1, 1};
  for (auto &raw : split_text_lines(text)) {
    std::string line = jtrim(raw);
    auto v = nums_in(line);
    auto n0 = [&]() { return v.empty() ? 0.0 : v[0]; };
    if (jstarts(line, "div(")) div = n0() / 10;
    if (jstarts(line, "idiv(")) div = n0() / 100;
    if (jstarts(line, "iwid(")) iwid = n0() / 100;
    if (jstarts(line, "ScaleX(")) sc[0] = n0() / 100;
    if (jstarts(line, "ScaleY(")) sc[1] = n0() / 100;
    if (jstarts(line, "ScaleZ(")) sc[2] = n0() / 100;
    if (jstarts(line, "rims(") && v.size() >= 5) {
      float size = (float)std::max(0.0, v[3] / 10);
      rimc[0] = (int)v[0]; rimc[1] = (int)v[1]; rimc[2] = (int)v[2];
      rimSize = size;
      rimDepth = (float)std::max(-25.0 * size, std::min(41.0 * size, v[4] / 10));
    }
    if (jstarts(line, "w(") && v.size() >= 6) {
      WheelInfo w;
      w.x = (float)(v[0] * div * iwid * sc[0]);
      w.y = (float)(v[1] * div * sc[1]);
      w.z = (float)(v[2] * div * sc[2]);
      w.steer = v[3] == 11;
      w.width = (float)(v[4] * div * iwid / 10);
      w.height = (float)(v[5] * div / 10);
      w.rim[0] = rimc[0]; w.rim[1] = rimc[1]; w.rim[2] = rimc[2];
      w.rimSize = rimSize; w.rimDepth = rimDepth;
      if (w.height > 0 && std::fabs(w.width) > 0) wheels.push_back(w);
    }
  }
  return wheels;
}

Sim::Sim() {
  g_sim = this;
  xt.ev = this;
  carfixedClip.ev = this;
  xt.carfixed = &carfixedClip;
}

void Sim::build_world() {
  SimScope scope(this);
  g_arena = nullptr;
  jrandom_seed(12345);
  m = jnew<Medium>();
  t = jnew<Trackers>();
  cd = jnew<CarDefine>(base, m, t, nullptr);
  // Same matching rule as GameSparker.loadbase: last name the entry starts with wins.
  for (auto &[entry, text] : models) {
    int slot = 0;
    for (int i = 0; i < N_CAR_FILES; i++) if (jstarts(entry, CAR_FILES[i])) slot = i;
    for (int i = 0; i < N_PIECE_FILES; i++) if (jstarts(entry, PIECE_FILES[i])) slot = i + PIECE_BASE;
    m->loadnew = false;
    base[slot] = ContO::create0(text, m, t);
    baseText[slot] = text;
  }
  carList.clear();
  for (int i = 0; i < N_CAR_FILES; i++) carList.push_back({i, cd->names[i], false});
  int slot = FIRST_CUSTOM_SLOT;
  for (auto &car : customs) {
    if (slot >= PIECE_BASE) break;
    try {
      m->loadnew = true;
      ContO *c = ContO::create0(car.text, m, t);
      m->loadnew = false;
      if (c->errd || c->npl <= 60 || c->maxR < 120) continue;
      auto &k = c->keyz, &x = c->keyx;
      if (k[0] < 0 || x[0] > 0 || k[1] < 0 || x[1] < 0 || k[2] > 0 || x[2] > 0 || k[3] > 0 || x[3] < 0) continue;
      c->shadow = true; c->noline = false; c->decor = false; c->tnt = 0; c->disp = 0;
      // ride height from the lowest real wheel (NFM World cars list dummy wheels last)
      auto real = parse_wheels(car.text);
      if (!real.empty()) {
        int g = INT32_MIN;
        for (auto &w : real) g = std::max(g, (int)std::trunc(w.y + 13 * w.height));
        c->grat = g;
      }
      base[slot] = c;
      baseText[slot] = car.text;
      cd->loadstat(car.text, car.name, c->maxR, c->roofat, c->wh, slot);
      if (cd->names[slot].empty()) { base[slot] = nullptr; continue; }
      apply_direct_physics(car.text, slot);
      carList.push_back({slot, cd->names[slot], true});
      slot++;
    } catch (const std::exception &e) {
      m->loadnew = false;
      fprintf(stderr, "custom car failed %s: %s\n", car.file.c_str(), e.what());
    }
  }
  models.clear();
}

// NFM World cars state tuned physics directly (swits(...), grip(...)...).
void Sim::add_custom(const std::string &file, const std::string &name, const std::string &text) {
  std::string shown = name;
  for (auto &l : split_text_lines(text)) {
    std::string t = jtrim(l);
    if (jstarts(t, "name(")) { size_t e = t.find(')'); if (e != std::string::npos) shown = jtrim(t.substr(5, e - 5)); break; }
  }
  customs.push_back({file, shown, text});
}

int Sim::apply_balance(const std::string &json) {
  // small reader for {"Name": {"key": number, ...}, ...}: names, keys and numbers only
  int changed = 0;
  size_t p = 0;
  auto str = [&](std::string &out) -> bool {
    size_t a = json.find('"', p);
    if (a == std::string::npos) return false;
    size_t b = json.find('"', a + 1);
    if (b == std::string::npos) return false;
    out = json.substr(a + 1, b - a - 1);
    p = b + 1;
    return true;
  };
  std::string name, key;
  while (str(name)) {
    size_t open = json.find('{', p), close = json.find('}', p);
    if (open == std::string::npos || close == std::string::npos) break;
    p = open + 1;
    int slot = -1;
    for (auto &c : carList) if (c.name == name) slot = c.slot;
    while (true) {
      size_t q = json.find('"', p);
      if (q == std::string::npos || q > close) break;
      if (!str(key)) break;
      size_t colon = json.find(':', p);
      double v = std::strtod(json.c_str() + colon + 1, nullptr);
      p = colon + 1;
      if (slot < 0) continue;
      if (key == "dammult") { cd->dammult[slot] = (float)v; changed++; }
      else if (key == "maxmag") { cd->maxmag[slot] = (int)v; changed++; }
    }
    p = close + 1;
  }
  return changed;
}

void Sim::apply_direct_physics(const std::string &text, int slot) {
  auto lines = split_text_lines(text);
  auto num = [&](const char *key) -> std::vector<double> {
    std::string k = std::string(key) + "(";
    for (auto &raw : lines) {
      std::string l = jtrim(raw);
      if (jstarts(l, k)) return nums_in(l.substr(0, l.find(')') + 1));
    }
    return {};
  };
  auto ok = [](const std::vector<double> &v) { if (v.empty()) return false; for (double x : v) if (!std::isfinite(x)) return false; return true; };
  auto sw = num("swits");
  if (ok(sw) && sw.size() >= 3) for (int i = 0; i < 3; i++) cd->swits[slot][i] = (int)std::trunc(sw[i]);
  auto ac = num("acelf");
  if (ok(ac) && ac.size() >= 3) for (int i = 0; i < 3; i++) cd->acelf[slot][i] = (float)ac[i];
  auto seti = [&](const char *key, Arr<int> &arr) { auto v = num(key); if (ok(v)) arr[slot] = (int)std::trunc(v[0]); };
  auto setf = [&](const char *key, Arr<float> &arr) { auto v = num(key); if (ok(v)) arr[slot] = (float)v[0]; };
  seti("handb", cd->handb); setf("airs", cd->airs); seti("airc", cd->airc);
  seti("turn", cd->turn); setf("grip", cd->grip); setf("bounce", cd->bounce);
  setf("moment", cd->moment); setf("comprad", cd->comprad); seti("push", cd->push);
  seti("revpush", cd->revpush); seti("lift", cd->lift); seti("revlift", cd->revlift);
  seti("powerloss", cd->powerloss); seti("clrad", cd->clrad); setf("dammult", cd->dammult);
  seti("maxmag", cd->maxmag); setf("dishandle", cd->dishandle); setf("outdam", cd->outdam);
}

// Port of GameSparker.loadstage (offline).
bool Sim::load_stage(const std::string &text, int id, int np, uint64_t seed) {
  SimScope scope(this);
  // everything from the previous stage / race goes
  cars = Arr<ContO *>();
  mads = Arr<Mad *>();
  controls.clear();
  objects.clear();
  stageArena.reset();
  g_arena = &stageArena;
  jrandom_seed((int64_t)seed);
  // Medium's random() keeps a little state between calls: start it fresh
  m->cntrn = 0;
  m->trn = 0;
  stageId = id;
  nplayersStage = np;
  env = StageEnv();
  cp = jnew<CheckPoints>();
  cp->stage = id;
  t->nt = 0;
  m->resdown = 0; m->rescnt = 5; m->lightson = false; m->noelec = 0; m->ground = 250; m->trk = 0;
  m->snap[0] = m->snap[1] = m->snap[2] = 0;
  int maxr = 0, maxl = 100, maxt = 0, maxb = 100;
  for (auto &raw : split_text_lines(text)) {
    std::string line = jtrim(raw);
    try {
      if (jstarts(line, "name(")) cp->name = line.substr(5, line.size() > 6 ? line.size() - 6 : 0);
      if (jstarts(line, "snap")) { for (int i = 0; i < 3; i++) env.snap[i] = getint("snap", line, i); m->setsnap(env.snap[0], env.snap[1], env.snap[2]); }
      if (jstarts(line, "sky")) { for (int i = 0; i < 3; i++) env.sky[i] = getint("sky", line, i); m->setsky(env.sky[0], env.sky[1], env.sky[2]); }
      if (jstarts(line, "ground")) { for (int i = 0; i < 3; i++) env.ground[i] = getint("ground", line, i); m->setgrnd(env.ground[0], env.ground[1], env.ground[2]); }
      if (jstarts(line, "polys")) { for (int i = 0; i < 3; i++) env.polys[i] = getint("polys", line, i); env.hasPolys = true; m->setpolys(env.polys[0], env.polys[1], env.polys[2]); }
      if (jstarts(line, "fog")) { for (int i = 0; i < 3; i++) env.fog[i] = getint("fog", line, i); m->setfade(env.fog[0], env.fog[1], env.fog[2]); }
      if (jstarts(line, "texture")) { for (int i = 0; i < 4; i++) env.texture[i] = getint("texture", line, i); m->setexture(env.texture[0], env.texture[1], env.texture[2], env.texture[3]); }
      if (jstarts(line, "clouds")) { for (int i = 0; i < 5; i++) env.clouds[i] = getint("clouds", line, i); m->setcloads(env.clouds[0], env.clouds[1], env.clouds[2], env.clouds[3], env.clouds[4]); }
      if (jstarts(line, "density")) env.density = getint("density", line, 0);
      if (jstarts(line, "fadefrom")) { env.fadefrom = getint("fadefrom", line, 0); m->fadfrom(env.fadefrom); }
      if (jstarts(line, "lightson")) { m->lightson = true; env.lightson = true; }
      if (jstarts(line, "mountains")) env.mountains = getint("mountains", line, 0);
      if (jstarts(line, "set")) {
        int sid = getint("set", line, 0);
        if (np == 8) { if (sid == 47) sid = 76; if (sid == 48) sid = 77; }
        if (sid >= 65 && sid <= 75 && cp->notb) continue;
        int slot = sid + 46;
        if (slot < 0 || slot >= N_SLOTS || !base[slot]) continue;
        ContO *b = base[slot];
        int x = getint("set", line, 1), z = getint("set", line, 2), rot = getint("set", line, 3);
        StageObject o;
        o.slot = slot; o.rot = rot;
        o.c = ContO::create1(b, x, m->ground - b->grat, z, rot);
        objects.push_back(o);
        if (line.find(")p") != std::string::npos) {
          int n = cp->n;
          cp->x[n] = x; cp->z[n] = z; cp->y[n] = 0; cp->typ[n] = 0;
          if (line.find(")pt") != std::string::npos) cp->typ[n] = -1;
          if (line.find(")pr") != std::string::npos) cp->typ[n] = -2;
          if (line.find(")po") != std::string::npos) cp->typ[n] = -3;
          if (line.find(")ph") != std::string::npos) cp->typ[n] = -4;
          cp->n++;
        }
      }
      if (jstarts(line, "chk")) {
        int slot = getint("chk", line, 0) + 46;
        if (slot < 0 || slot >= N_SLOTS || !base[slot]) continue;
        ContO *b = base[slot];
        int y = m->ground - b->grat;
        if (slot == 110) y = getint("chk", line, 4);
        int x = getint("chk", line, 1), z = getint("chk", line, 2), rot = getint("chk", line, 3);
        StageObject o;
        o.slot = slot; o.rot = rot;
        o.c = ContO::create1(b, x, y, z, rot);
        int n = cp->n;
        cp->x[n] = x; cp->z[n] = z; cp->y[n] = y;
        cp->typ[n] = rot == 0 ? 1 : 2;
        cp->pcs = cp->n++;
        o.c->checkpoint = cp->nsp + 1;
        o.checkpoint = o.c->checkpoint;
        cp->nsp++;
        objects.push_back(o);
      }
      if (cp->nfix != 5 && jstarts(line, "fix")) {
        int slot = getint("fix", line, 0) + 46;
        if (slot < 0 || slot >= N_SLOTS || !base[slot]) continue;
        int x = getint("fix", line, 1), z = getint("fix", line, 2), y = getint("fix", line, 3), rot = getint("fix", line, 4);
        StageObject o;
        o.slot = slot; o.rot = rot; o.fix = true;
        o.c = ContO::create1(base[slot], x, y, z, rot);
        int fn = cp->fn;
        cp->fx[fn] = x; cp->fz[fn] = z; cp->fy[fn] = y;
        o.c->elec = true;
        cp->roted[fn] = rot != 0;
        o.c->roted = rot != 0;
        cp->special[fn] = line.find(")s") != std::string::npos;
        cp->fn++;
        objects.push_back(o);
      }
      if (!cp->notb && jstarts(line, "pile")) {
        StageObject o;
        o.pile = true;
        o.c = ContO::create2(getint("pile", line, 0), getint("pile", line, 1), getint("pile", line, 2), m, t, getint("pile", line, 3), getint("pile", line, 4), m->ground);
        objects.push_back(o);
      }
      if (jstarts(line, "nlaps")) cp->nlaps = std::min(15, std::max(1, getint("nlaps", line, 0)));
      if (jstarts(line, "soundtrack")) {
        size_t a = line.find('('), b = line.find(')');
        std::vector<std::string> args;
        if (a != std::string::npos && b != std::string::npos && b > a) {
          std::stringstream ss(line.substr(a + 1, b - a - 1));
          std::string it;
          while (std::getline(ss, it, ',')) args.push_back(jtrim(it));
        }
        if (!args.empty()) env.soundtrack = args[0] == "custom" ? (args.size() > 1 ? args[1] : "") : args[0];
      }
      if (jstarts(line, "maxr") || jstarts(line, "maxl") || jstarts(line, "maxt") || jstarts(line, "maxb")) {
        std::string key = line.substr(0, 4);
        int count = getint(key, line, 0), pos = getint(key, line, 1), start = getint(key, line, 2);
        ContO *wall = base[85];
        if (!wall) continue;
        int wy = m->ground - wall->grat;
        int rotFor = key == "maxr" ? 0 : key == "maxl" ? 180 : key == "maxt" ? 90 : -90;
        for (int i = 0; i < count; i++) {
          int along = i * 4800 + start;
          StageObject o;
          o.slot = 85; o.rot = rotFor; o.wall = true;
          o.c = (key == "maxr" || key == "maxl") ? ContO::create1(wall, pos, wy, along, rotFor) : ContO::create1(wall, along, wy, pos, rotFor);
          objects.push_back(o);
        }
        int n = t->nt;
        t->y[n] = -5000; t->rady[n] = 7100; t->dam[n] = 167; t->decor[n] = false; t->skd[n] = 0;
        if (key == "maxr") { maxr = pos; t->x[n] = pos + 500; t->radx[n] = 600; t->z[n] = count * 4800 / 2 + start - 2400; t->radz[n] = count * 4800 / 2; t->xy[n] = 90; t->zy[n] = 0; }
        if (key == "maxl") { maxl = pos; t->x[n] = pos - 500; t->radx[n] = 600; t->z[n] = count * 4800 / 2 + start - 2400; t->radz[n] = count * 4800 / 2; t->xy[n] = -90; t->zy[n] = 0; }
        if (key == "maxt") { maxt = pos; t->z[n] = pos + 500; t->radz[n] = 600; t->x[n] = count * 4800 / 2 + start - 2400; t->radx[n] = count * 4800 / 2; t->zy[n] = 90; t->xy[n] = 0; }
        if (key == "maxb") { maxb = pos; t->z[n] = pos - 500; t->radz[n] = 600; t->x[n] = count * 4800 / 2 + start - 2400; t->radx[n] = count * 4800 / 2; t->zy[n] = -90; t->xy[n] = 0; }
        t->nt++;
      }
    } catch (const std::exception &e) {
      fprintf(stderr, "stage line skipped: %s (%s)\n", line.c_str(), e.what());
    }
  }
  t->devidetrackers(maxl, maxr - maxl, maxb, maxt - maxb);
  cp->calprox();
  if (cp->nlaps < 1) cp->nlaps = 3;
  bounds[0] = maxl; bounds[1] = maxr; bounds[2] = maxb; bounds[3] = maxt;
  if (!(maxr > maxl && maxt > maxb) && !objects.empty()) {
    int x0 = INT32_MAX, x1 = INT32_MIN, z0 = INT32_MAX, z1 = INT32_MIN;
    for (auto &o : objects) { x0 = std::min(x0, o.c->x); x1 = std::max(x1, o.c->x); z0 = std::min(z0, o.c->z); z1 = std::max(z1, o.c->z); }
    const int pad = 6000;
    bounds[0] = x0 - pad; bounds[1] = x1 + pad; bounds[2] = z0 - pad; bounds[3] = z1 + pad;
  }
  return true;
}

void Sim::start_race(const std::vector<int> &grid, uint32_t humanMask, float landk, uint64_t seed) {
  SimScope scope(this);
  g_arena = &stageArena;
  jrandom_seed((int64_t)seed ^ 0x5bd1e995);
  m->cntrn = 0;
  m->trn = 0;
  nplayers = (int)std::min<size_t>(8, grid.size());
  sc = std::vector<int>(grid.begin(), grid.begin() + nplayers);
  human = humanMask;
  cars = Arr<ContO *>(nplayers);
  mads = Arr<Mad *>(nplayers);
  controls.clear();
  xt.im = 0;
  xt.multion = 0;
  xt.starcnt = 130;
  xt.laptime = 0;
  for (int i = 0; i < 8; i++) { rec.dest[i] = 0; rec.fix[i] = 0; xt.dcrashes[i] = 0; xt.isbot[i] = false; }
  rec.powered = 0; rec.wasted = 0; rec.hcaught = false; rec.whenwasted = 0; rec.closefinish = 0;
  for (int i = 0; i < nplayers; i++) controls.push_back(jnew<Control>(m));
  for (int i = 0; i < nplayers; i++) controls[i]->reset(cp, sc[i]);
  for (int i = 0; i < nplayers; i++) {
    ContO *b = base[sc[i]];
    ContO *car = ContO::create1(b, XSTART[i], 250 - b->grat, ZSTART[i], 0);
    car->dist = 1;
    Mad *mad = jnew<Mad>(cd, m, &rec, &xt, i);
    mad->reseto(sc[i], car, cp);
    mad->landk = landk;
    if (deathmatch) mad->fixes = fixLimit;
    cars[i] = car;
    mads[i] = mad;
  }
  cp->hunt = deathmatch ? nplayers : 0;
  carVersion.assign(nplayers, 0);
  tick = 0;
  finished = false;
  decided = false;
  resultKind = resultWho = resultPos = 0;
  lastClear = lastNlaps = lastPowerup = 0;
  lastCapsized = false;
  lastBad = -1000;
  lastPower = 0;
  lastDest.assign(nplayers, false);
  wrecked.assign(nplayers, false);
  ai.assign(nplayers, AiProgress());
  rescues = 0;
  combat.assign(nplayers, Combat());
  pairDealt.assign(nplayers * nplayers, 0.0f);
  lapStart.assign(nplayers, 0);
  bestLap.assign(nplayers, -1);
  lastLaps.assign(nplayers, 0);
  events.clear();
}

void Sim::push(int type, std::initializer_list<float> v) {
  SimEvent e{type, {}};
  int k = 0;
  for (float f : v) { if (k < 9) e.v[k++] = f; }
  events.push_back(e);
}

int Sim::carIndex(ContO *c) const {
  for (int i = 0; i < nplayers; i++) if (cars[i] == c) return i;
  return -1;
}

void Sim::dust(ContO *c, int wheel, float x, float y, float z, int vx, int vz, float mag, int tilt, bool capsized) {
  push(EV_DUST, {(float)carIndex(c), (float)wheel, x, y, z, (float)vx, (float)vz, mag, (float)(tilt | (capsized ? 1 << 16 : 0))});
}
void Sim::sprk(ContO *c, float x, float y, float z, float vx, float vy, float vz, int kind) {
  push(EV_SPARK, {(float)carIndex(c), x, y, z, vx, vy, vz, (float)kind});
}

void Sim::step(const std::vector<int> &inputs) {
  SimScope scope(this);
  if (nplayers == 0) return;
  g_arena = &stageArena;
  int n = nplayers;
  tick++;
  // cars flagged newcar after a fix are rebuilt from the clean model
  for (int i = 0; i < n; i++) {
    if (!mads[i]->newcar) continue;
    ContO *old = cars[i];
    ContO *c = ContO::create1(base[mads[i]->cn], old->x, old->y, old->z, 0);
    c->xz = old->xz; c->xy = old->xy; c->zy = old->zy;
    c->dist = 1;
    cars[i] = c;
    mads[i]->newcar = false;
    combat[i].fixes++;
    carVersion[i]++;
  }
  for (int i = 0; i < n; i++) {
    if (!(human & (1u << i))) continue;
    int b = i < (int)inputs.size() ? inputs[i] : 0;
    Control *u = controls[i];
    u->left = b & 1; u->right = b & 2; u->up = b & 4; u->down = b & 8; u->handb = b & 16;
    u->lookback = (b & 32) ? 1 : 0;
  }
  try {
    if (xt.starcnt == 0) {
      for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) if (i != j) {
        int hi = mads[i]->hitmag, hj = mads[j]->hitmag;
        mads[i]->colide(cars[i], mads[j], cars[j]);
        credit(i, j, mads[j]->hitmag - hj);
        credit(j, i, mads[i]->hitmag - hi);
      }
      // hangout: keep every car just short of wasted, so crashes leave dents
      // and smoke but nobody is knocked out
      if (freeroam)
        for (int i = 0; i < n; i++) {
          int cap = (int)(cd->maxmag[mads[i]->cn] * 0.8f);
          if (mads[i]->hitmag > cap) mads[i]->hitmag = cap;
        }
      for (int i = 0; i < n; i++) {
        int h = mads[i]->hitmag;
        mads[i]->drive(controls[i], cars[i], t, cp);
        if (mads[i]->hitmag > h) { int mx = cd->maxmag[mads[i]->cn]; combat[i].world += std::min(100.0f * (mads[i]->hitmag - h) / mx, std::max(0.0f, 100.0f * (mx - h) / mx)); }
      }
      cp->checkstat(mads, cars, &rec, n, 0, 0);
      for (int i = 0; i < n; i++) if (!(human & (1u << i))) controls[i]->preform(mads[i], cars[i], cp, t);
      rescue_stuck_ai();
      // the original counts this down in its draw call (Medium.d), which the
      // native sim never makes: without it the hoops stay off for good
      if (m->noelec > 0) m->noelec--;
      if (!finished) xt.laptime++;
      // every car's laps, for the fastest-lap award (a lap counts from the start
      // line or from the end of the previous lap)
      for (int i = 0; i < n; i++) {
        int laps = cp->nsp > 0 ? cp->clear[i] / cp->nsp : 0;
        if (laps > lastLaps[i]) {
          int took = tick - lapStart[i];
          if (!mads[i]->dest && (bestLap[i] < 0 || took < bestLap[i])) bestLap[i] = took;
          lapStart[i] = tick;
          lastLaps[i] = laps;
        }
      }
    } else {
      xt.starcnt--;
      if (xt.starcnt == 0) push(EV_GO, {});
      else if (xt.starcnt % 30 == 0 && xt.starcnt <= 90) push(EV_COUNT, {(float)(xt.starcnt / 30)});
    }
  } catch (const std::exception &e) {
    fprintf(stderr, "physics step error: %s\n", e.what());
  }
  // ContO.fixit animation counter (advanced by the renderer in Java)
  for (int i = 0; i < n; i++) {
    ContO *c = cars[i];
    if (c->fix) {
      if (c->fcnt > 7) { c->fcnt = 0; c->fix = false; } else c->fcnt++;
    }
  }
  detect_events();
}

// AI stuck on community stages: put it back toward its next checkpoint.
void Sim::rescue_stuck_ai() {
  if (cp->nsp < 2 || noRescue || deathmatch) return;
  for (int i = 0; i < nplayers; i++) {
    if (human & (1u << i)) continue;
    Mad *mad = mads[i];
    ContO *car = cars[i];
    if (mad->dest || cp->clear[i] >= cp->nlaps * cp->nsp) continue;
    AiProgress &p = ai[i];
    double d = std::hypot((double)(car->x - cp->x[mad->point]), (double)(car->z - cp->z[mad->point]));
    if (mad->clear != p.clear || mad->point != p.point || d < p.bestD - 300) {
      if (mad->clear != p.clear) p.tries = 0;
      p.clear = mad->clear; p.point = mad->point; p.bestD = d; p.at = tick;
      p.lx = car->x; p.lz = car->z; p.moved = tick;
      continue;
    }
    // a car driving around isn't stuck straight away: one that has barely
    // moved is moved on after 10 s, one that keeps driving without getting
    // anywhere (lost, or circling a pit) after 30 s, or 60 s while it's
    // hunting. One in a fight (hit or been hit in the last 15 s) is left be:
    // on wasting stages that's what the AI is there for
    if (std::hypot((double)(car->x - p.lx), (double)(car->z - p.lz)) > 1500) {
      p.lx = car->x; p.lz = car->z; p.moved = tick;
    }
    bool still = tick - p.moved >= 250 && tick - p.at >= 250;
    bool fighting = tick - std::max(combat[i].lastDealtAt, combat[i].lastHitAt) < 375;
    bool lost = !fighting && tick - p.at >= (controls[i]->attack != 0 ? 1500 : 750);
    if (!still && !lost) continue;
    int tgt = next_checkpoint_index(mad);
    rescue_to(i, tgt, std::min(2, p.tries++));
    p.bestD = 1e30; p.at = tick; p.moved = tick;
  }
}

int Sim::next_checkpoint_index(Mad *mad) {
  int k = mad->pcleared;
  for (int n = 0; n < cp->n; n++) {
    k = (k + 1) % cp->n;
    if (cp->typ[k] > 0) return k;
  }
  return 0;
}

void Sim::rescue_to(int i, int tgt, int level) {
  bool skip = level >= 1;
  ContO *car = cars[i];
  Mad *mad = mads[i];
  int from = mad->clear > 0 ? mad->pcleared : -1;
  double fx = from >= 0 ? cp->x[from] : 0, fz = from >= 0 ? cp->z[from] : 0;
  int fy = skip ? (cp->y[tgt] ? cp->y[tgt] : 250) : (from >= 0 && cp->y[from] ? cp->y[from] : 250);
  double dx = cp->x[tgt] - fx, dz = cp->z[tgt] - fz;
  double len = std::hypot(dx, dz);
  if (len == 0) len = 1;
  double back = level >= 2 ? 0 : 1200;
  double ax = skip ? cp->x[tgt] - (dx / len) * back : fx + (dx / len) * 600;
  double az = skip ? cp->z[tgt] - (dz / len) * back : fz + (dz / len) * 600;
  double spread = level >= 2 ? 0 : (i - 3) * 120;
  car->x = (int)std::lround(ax + spread * (dz / len));
  car->z = (int)std::lround(az - spread * (dx / len));
  car->y = std::min(fy, 250) - car->grat - 200;
  car->xz = (int)std::lround(std::atan2(-dx, dz) * 180.0 / 3.141592653589793);
  car->xy = 0; car->zy = 0;
  mad->pxy = 0; mad->pzy = 0;
  mad->speed = 0;
  for (int k = 0; k < 4; k++) { mad->scx[k] = 0; mad->scy[k] = 0; mad->scz[k] = 0; }
  mad->cxz = car->xz; mad->mxz = car->xz;
  rescues++;
  push(EV_RESCUE, {(float)i});
}

void Sim::detect_events() {
  Mad *p = mads[focus];
  if (p->clear != lastClear) {
    if (p->clear > lastClear) push(EV_CHECKPOINT, {(float)p->clear});
    lastClear = p->clear;
  }
  if (p->nlaps != lastNlaps) {
    if (p->nlaps > lastNlaps && p->nlaps < cp->nlaps) push(EV_LAP, {(float)(p->nlaps + 1)});
    lastNlaps = p->nlaps;
  }
  if (p->power == 98 && lastPower < 98 && p->powerup > 0) push(EV_POWERUP, {p->powerup});
  // a stunt is scored the tick the car has been down long enough (trcnt 9,
  // then 10 for one tick before it resets): send what the stunt was made of,
  // so the HUD can name it the way the original did
  if (p->trcnt == 10 && p->powerup > 0 && !p->dest)
    push(EV_STUNT, {p->powerup, (float)p->travxy, (float)p->travzy, (float)p->travxz,
                    p->ftab ? 1.0f : 0.0f, p->btab ? 1.0f : 0.0f, p->rtab ? 1.0f : 0.0f, p->surfer ? 1.0f : 0.0f, p->power});
  lastPowerup = (int)std::trunc(p->powerup);
  // Bad Landing: the car has come to rest on its roof (the physics starts its
  // flip-back count then), not merely upside down in the air mid-stunt
  if (p->capcnt == 1 && !lastCapsized && !p->dest && tick - lastBad > 60) { push(EV_BADLANDING, {}); lastBad = tick; }
  lastCapsized = p->capcnt != 0;
  lastPower = p->power;
  for (int i = 0; i < nplayers; i++) {
    if (mads[i]->dest && !lastDest[i]) {
      Combat &v = combat[i];
      v.diedAt = tick;
      // the last car to hit it in the past 6 s gets the kill, else the crash did it
      v.killedBy = (v.lastHitBy >= 0 && tick - v.lastHitAt < 150) ? v.lastHitBy : -1;
      if (v.killedBy >= 0) combat[v.killedBy].kills++;
      bool byYou = i != 0 && mads[i]->lastcolido > 0;
      if (byYou) wrecked[i] = true;
      push(EV_WASTED, {(float)i, byYou ? 1.0f : 0.0f});
    }
    lastDest[i] = mads[i]->dest;
  }
  // cars wasted other than the focus car (the original counted from car 0,
  // which was always the player; online the focus can be any slot)
  int othersDown = 0;
  for (int i = 0; i < nplayers; i++)
    if (i != focus && mads[i]->dest) othersDown++;
  if (!finished && !freeroam) {
    int total = cp->nlaps * cp->nsp;
    if (p->dest) finish(2, focus);
    else if (deathmatch) { if (othersDown >= nplayers - 1 && nplayers > 1) finish(3, focus); }
    else if (p->clear >= total) finish(1, focus);
    else if (othersDown >= nplayers - 1 && nplayers > 1) finish(3, focus);
    else {
      for (int i = 0; i < nplayers; i++) {
        if (i == focus) continue;
        if (cp->clear[i] >= total && !mads[i]->dest) { finish(4, i); break; }
      }
    }
  }
  check_decided();
}

// Over for everyone: a car crossed the line, or at most one is left. Depends
// only on the race itself, so every PC in an online race sees it together.
void Sim::check_decided() {
  if (decided || nplayers < 2 || freeroam) return;
  int total = cp->nlaps * cp->nsp;
  int alive = 0, last = -1, winner = -2;
  for (int i = 0; i < nplayers; i++)
    if (!mads[i]->dest) { alive++; last = i; }
  if (!deathmatch)
    for (int i = 0; i < nplayers; i++)
      if (cp->clear[i] >= total && !mads[i]->dest) { winner = i; break; }
  if (winner == -2 && alive <= 1) winner = last;
  if (winner == -2) return;
  decided = true;
  push(EV_DECIDED, {(float)winner});
}

void Sim::credit(int by, int to, int amount) {
  if (amount <= 0 || (mads[to]->dest && combat[to].diedAt >= 0)) return;
  int mx = cd->maxmag[mads[to]->cn];
  // only the health it had left counts: a finishing blow can not do 300%
  float pct = std::min(100.0f * amount / mx, std::max(0.0f, 100.0f * (mx - (mads[to]->hitmag - amount)) / mx));
  combat[by].dealt += pct;
  combat[by].lastDealtAt = tick;
  pairDealt[by * nplayers + to] += pct;
  combat[to].taken += pct;
  combat[to].lastHitBy = by;
  combat[to].lastHitAt = tick;
}

void Sim::finish(int kind, int who) {
  finished = true;
  resultKind = kind;
  resultWho = who;
  resultPos = cp->pos[focus] + 1;
  push(EV_FINISH, {(float)kind, (float)who, (float)resultPos});
}

// Order-sensitive checksum of everything that decides the race, compared
// between players to catch a desync early.
uint32_t Sim::state_hash() const {
  uint32_t h = 2166136261u;
  auto mix = [&](int64_t v) { for (int k = 0; k < 8; k++) { h ^= (uint8_t)(v >> (k * 8)); h *= 16777619u; } };
  mix(tick);
  for (int i = 0; i < nplayers; i++) {
    ContO *c = cars[i];
    Mad *md = mads[i];
    mix(c->x); mix(c->y); mix(c->z); mix(c->xz); mix(c->xy); mix(c->zy);
    float s = md->speed; int32_t si; memcpy(&si, &s, 4); mix(si);
    mix(md->hitmag); mix(md->clear); mix(md->dest);
  }
  return h;
}
