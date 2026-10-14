// The game simulation: world (models + car definitions), stage loading and the
// 25 Hz race loop. Ported from remake/src/game/world.js and race.js; the
// physics themselves are the translated original (gen/nfm_gen.*).
#pragma once
#include <string>
#include <vector>
#include "gen/nfm_gen.h"

extern const char *CAR_FILES[16];
extern const char *PIECE_FILES[68];
constexpr int N_CAR_FILES = 16;
constexpr int N_PIECE_FILES = 68;
constexpr int FIRST_CUSTOM_SLOT = 16;
constexpr int PIECE_BASE = 56;
constexpr int N_SLOTS = PIECE_BASE + N_PIECE_FILES;

struct CarEntry { int slot; std::string name; bool custom; };

struct WheelInfo { float x, y, z; bool steer; float width, height; int rim[3]; float rimSize, rimDepth; };
std::vector<WheelInfo> parse_wheels(const std::string &text);
std::vector<std::string> split_text_lines(const std::string &text);

struct StageObject {
  int slot = -1;
  ContO *c = nullptr;
  int rot = 0;
  int checkpoint = 0;
  bool fix = false, pile = false, wall = false;
};

struct StageEnv {
  int sky[3] = {170, 220, 255}, fog[3] = {255, 220, 220}, ground[3] = {205, 200, 200};
  int polys[3] = {0, 0, 0};
  bool hasPolys = false;
  int clouds[5] = {210, 210, 210, 1, -1000};
  int texture[4] = {0, 0, 0, 50};
  int fadefrom = 5000, density = 5, mountains = 0, snap[3] = {0, 0, 0};
  bool lightson = false;
  std::string soundtrack;
};

// One event the renderer / audio should know about (fixed-size record).
struct SimEvent {
  int type;
  float v[9];
};
enum EventType {
  EV_CRASH = 1, EV_SKID, EV_SCRAPE, EV_GSCRAPE, EV_CARFIXED, EV_DUST, EV_SPARK,
  EV_CHECKPOINT, EV_LAP, EV_WASTED, EV_STUNT, EV_COUNT, EV_GO, EV_FINISH, EV_RESCUE, EV_POWERUP,
  EV_DECIDED, // the race is over for everyone: v[0] = winning slot, -1 if nobody
  EV_BADLANDING, // our car landed on its roof
};

struct Sim : SimEvents {
  // world
  Medium *m = nullptr;
  Trackers *t = nullptr;
  CarDefine *cd = nullptr;
  Arr<ContO *> base = Arr<ContO *>(N_SLOTS);
  std::vector<std::string> baseText = std::vector<std::string>(N_SLOTS);
  std::vector<CarEntry> carList;
  std::vector<std::pair<std::string, std::string>> models; // name -> text, before build
  struct Custom { std::string file, name, text; };
  std::vector<Custom> customs;

  // stage
  JArena stageArena;
  CheckPoints *cp = nullptr;
  std::vector<StageObject> objects;
  StageEnv env;
  int bounds[4] = {0, 0, 0, 0}; // minx maxx minz maxz
  int stageId = 0;
  int nplayersStage = 7;

  // race
  int nplayers = 0;
  std::vector<int> sc;
  Arr<ContO *> cars;
  Arr<Mad *> mads;
  std::vector<Control *> controls;
  std::vector<int> carVersion;
  xtGraphics xt;
  Record rec;
  soundClip carfixedClip;
  uint32_t human = 1; // bit i: grid slot i is driven by a person
  int tick = 0;
  bool finished = false;
  bool decided = false; // over for every car (same tick on every PC), not just the focus car
  int resultKind = 0, resultWho = 0, resultPos = 0; // 1 finished 2 wasted 3 allwasted 4 lost
  int lastClear = 0, lastNlaps = 0, lastPowerup = 0;
  bool lastCapsized = false;
  int lastBad = -1000; // our car: when it last showed a Bad Landing
  float lastPower = 0;
  std::vector<bool> lastDest;
  std::vector<bool> wrecked;
  struct AiProgress { int clear = -1, point = -1; double bestD = 1e30; int at = 0, tries = 0; int lx = 0, lz = 0, moved = 0; };
  std::vector<AiProgress> ai;
  int rescues = 0;
  bool noRescue = false;
  // this sim's own random state: the generated code draws from one global
  // generator, so each sim swaps its state in while it runs (the killcam's
  // replay sim runs alongside the race and must not share the sequence)
  uint64_t rng = 0x9E3779B97F4A7C15ULL;
  bool freeroam = false;   // hangout: no finish, cars dent but are never wasted
  bool deathmatch = false; // last car standing: AI hunts, laps never finish the race
  int fixLimit = -1;       // deathmatch: repairs per car (-1 unlimited, 0 none)
  int focus = 0; // grid slot whose events (checkpoints, laps) are reported
  // per car, damage as % of the victim's health: dealt to others, taken from
  // cars, taken from the world (walls, landings); fixes; kills; who wasted it
  struct Combat { float dealt = 0, taken = 0, world = 0; int fixes = 0, kills = 0, killedBy = -2, diedAt = -1, lastHitBy = -1, lastHitAt = -1000, lastDealtAt = -1000; };
  std::vector<Combat> combat;
  std::vector<float> pairDealt; // [by * n + to]: damage one car did to another, in % of the victim
  // each car's laps: the tick its current lap began and its best full lap (-1 = none yet)
  std::vector<int> lapStart, bestLap, lastLaps;
  std::vector<SimEvent> events;

  Sim();
  void add_model(const std::string &name, const std::string &text) { models.push_back({name, text}); }
  // a community car names itself with name(...) in its file; the file name is the fallback
  void add_custom(const std::string &file, const std::string &name, const std::string &text);
  void build_world();
  void apply_direct_physics(const std::string &text, int slot);
  // community-car balance overrides, JSON {"Car": {"dammult": 0.45, "maxmag": 22000}},
  // applied after build_world; the same file for everyone, so online play stays in sync
  int apply_balance(const std::string &json);
  bool load_stage(const std::string &text, int id, int nplayers, uint64_t seed);
  void start_race(const std::vector<int> &grid, uint32_t humanMask, float landk, uint64_t seed);
  void step(const std::vector<int> &inputs); // per grid slot: bit0 left 1 right 2 up 3 down 4 handb 5 lookback
  uint32_t state_hash() const;

 private:
  void rescue_stuck_ai();
  void credit(int by, int to, int amount);
  int next_checkpoint_index(Mad *mad);
  void rescue_to(int i, int tgt, int level);
  void detect_events();
  void finish(int kind, int who);
  void check_decided();
  void push(int type, std::initializer_list<float> v);

 public:
  // SimEvents: forwarded from the physics
  void crash(float f, int n) override { push(EV_CRASH, {f, (float)n}); }
  void skid(int n, float f) override { push(EV_SKID, {(float)n, f}); }
  void scrape(int x, int y, int z) override { push(EV_SCRAPE, {(float)x, (float)y, (float)z}); }
  void gscrape(int x, int y, int z) override { push(EV_GSCRAPE, {(float)x, (float)y, (float)z}); }
  void carfixed() override { push(EV_CARFIXED, {}); }
  void dust(ContO *c, int wheel, float x, float y, float z, int vx, int vz, float mag, int tilt, bool capsized) override;
  void sprk(ContO *c, float x, float y, float z, float vx, float vy, float vz, int kind) override;
  int carIndex(ContO *c) const;
};

extern Sim *g_sim; // ContO::dust / sprk forward here
