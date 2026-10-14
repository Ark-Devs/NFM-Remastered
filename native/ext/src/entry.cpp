// GDExtension entry point: exposes the native game simulation to Godot as
// the NfmSim class.
#include "gdx.h"
#include "mesh.h"
#include "modplayer.h"
#include "sim.h"

using namespace gdx;

static std::string bytes_str(const Val &v) { return std::string(v.bytes.begin(), v.bytes.end()); }

// Names may be saved as UTF-8 or as Latin-1: hand Godot valid UTF-8 either way.
static std::string to_utf8(const std::string &s) {
  bool ok = true;
  for (size_t i = 0; i < s.size() && ok;) {
    unsigned char c = s[i];
    int n = c < 0x80 ? 0 : (c >> 5) == 6 ? 1 : (c >> 4) == 14 ? 2 : (c >> 3) == 30 ? 3 : -1;
    if (n < 0 || i + n >= s.size() + (n ? 0 : 1)) { ok = false; break; }
    for (int k = 1; k <= n; k++) if (i + k >= s.size() || ((unsigned char)s[i + k] >> 6) != 2) ok = false;
    i += n + 1;
  }
  if (ok) return s;
  std::string o;
  for (unsigned char c : s) { if (c < 0x80) o += (char)c; else { o += (char)(0xC0 | (c >> 6)); o += (char)(0x80 | (c & 63)); } }
  return o;
}

// Mesh parts as Godot-ready arrays: [[part, vertices, normals, colours, custom0 (rgba)], ...]
static Val mesh_val(const MeshOut &m) {
  std::vector<Val> parts;
  for (int k = 0; k < PART_COUNT; k++) {
    const auto &v = m.parts[k];
    if (v.empty()) continue;
    size_t n = v.size() / VERT_FLOATS;
    std::vector<float> pos(n * 3), nor(n * 3), col(n * 4), cus(n * 4);
    for (size_t i = 0; i < n; i++) {
      const float *s = &v[i * VERT_FLOATS];
      memcpy(&pos[i * 3], s, 12);
      memcpy(&nor[i * 3], s + 3, 12);
      col[i * 4] = s[6]; col[i * 4 + 1] = s[7]; col[i * 4 + 2] = s[8]; col[i * 4 + 3] = 1;
      cus[i * 4] = s[9]; cus[i * 4 + 1] = s[10]; cus[i * 4 + 2] = s[11]; cus[i * 4 + 3] = 0;
    }
    parts.push_back(Val::List({Val::Int(k), Val::Vec3s(pos), Val::Vec3s(nor), Val::Colors(col), Val::Floats(cus)}));
  }
  return Val::List(parts);
}

struct NfmSim {
  GDExtensionObjectPtr owner = nullptr;
  Sim sim;
};

static std::vector<float> car_state(Sim &s, int i) {
  ContO *c = s.cars[i];
  Mad *md = s.mads[i];
  float maxmag = (float)std::max(1, s.cd->maxmag[md->cn]);
  return {
    (float)c->x, (float)c->y, (float)c->z, (float)c->xz, (float)c->xy, (float)c->zy, (float)c->wxz,
    md->speed, md->power, md->hitmag / maxmag, md->dest ? 1.0f : 0.0f,
    (float)md->clear, (float)md->nlaps, (float)(s.cp->pos[i] + 1),
    (md->mtouch || md->wtouch) ? 1.0f : 0.0f, md->capsized ? 1.0f : 0.0f,
    (float)s.carVersion[i], c->fix ? 1.0f : 0.0f, (float)md->cn, (float)md->missedcp,
    (float)md->cxz, (float)c->grat, (float)md->lastcolido, s.wrecked[i] ? 1.0f : 0.0f,
    md->mtouch ? 1.0f : 0.0f, (float)md->cntdest,
  };
}
constexpr int CAR_STATE_FLOATS = 26;

struct NfmMod {
  GDExtensionObjectPtr owner = nullptr;
  ModPlayer mod;
};

static void register_mod();

static void initialize(void *, GDExtensionInitializationLevel level) {
  if (level != GDEXTENSION_INITIALIZATION_SCENE) return;
  using R = ClassReg<NfmSim>;
  R::reg("NfmSim", "RefCounted");
  register_mod();
  auto none = [](auto) { return Val(); };
  (void)none;

  // ---- world
  R::method("add_model", NONE, {{STRING, "name"}, {BYTES, "data"}}, [](NfmSim &s, std::vector<Val> &a) { s.sim.add_model(a[0].s, bytes_str(a[1])); return Val(); });
  R::method("add_custom", NONE, {{STRING, "file"}, {BYTES, "data"}}, [](NfmSim &s, std::vector<Val> &a) {
    std::string text = bytes_str(a[1]);
    std::string name = a[0].s;
    // the car names itself with name(...)
    for (auto &l : split_text_lines(text)) { std::string t = jtrim(l); if (jstarts(t, "name(")) { size_t e = t.find(')'); if (e != std::string::npos) name = jtrim(t.substr(5, e - 5)); break; } }
    s.sim.add_custom(a[0].s, name, text);
    return Val();
  });
  R::method("apply_balance", INT, {{STRING, "json"}}, [](NfmSim &s, std::vector<Val> &a) { return Val::Int(s.sim.apply_balance(a[0].s)); });
  R::method("build_world", INT, {}, [](NfmSim &s, std::vector<Val> &) { s.sim.build_world(); return Val::Int((int64_t)s.sim.carList.size()); });
  R::method("car_count", INT, {}, [](NfmSim &s, std::vector<Val> &) { return Val::Int((int64_t)s.sim.carList.size()); });
  R::method("car_slot", INT, {{INT, "i"}}, [](NfmSim &s, std::vector<Val> &a) { return Val::Int(s.sim.carList.at(a[0].i).slot); });
  R::method("car_name", STRING, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) { return Val::String(to_utf8(s.sim.cd->names[(int)a[0].i])); });
  R::method("car_custom", BOOL, {{INT, "slot"}}, [](NfmSim &, std::vector<Val> &a) { return Val::Bool(a[0].i >= FIRST_CUSTOM_SLOT); });
  R::method("car_stats", F32, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) {
    int k = (int)a[0].i;
    CarDefine *cd = s.sim.cd;
    return Val::Floats({(float)cd->swits[k][2], cd->acelf[k][0] + cd->acelf[k][1] + cd->acelf[k][2], cd->airs[k] * cd->airc[k], cd->moment[k], (float)cd->maxmag[k], (float)cd->enginsignature[k], cd->simag[k], cd->dammult[k], (float)cd->push[k],
                        cd->acelf[k][0] * cd->acelf[k][1] * cd->acelf[k][2] * cd->grip[k], cd->airs[k] * cd->airc[k] * cd->bounce[k], cd->dishandle[k], (float)cd->powerloss[k]});
  });
  R::method("car_mesh_base", ARRAY, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) {
    MeshOut m;
    int snap[3] = {0, 0, 0};
    ContO *c = s.sim.base[(int)a[0].i];
    if (c) build_conto_mesh(c, true, "", snap, m);
    return mesh_val(m);
  });
  R::method("piece_mesh", ARRAY, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) {
    MeshOut m;
    int slot = (int)a[0].i;
    ContO *c = s.sim.base[slot];
    if (c) build_conto_mesh(c, false, slot >= PIECE_BASE ? PIECE_FILES[slot - PIECE_BASE] : "", s.sim.env.snap, m);
    return mesh_val(m);
  });
  R::method("car_swits", I32, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) { int k = (int)a[0].i; return Val::Ints({s.sim.cd->swits[k][0], s.sim.cd->swits[k][1], s.sim.cd->swits[k][2]}); });
  R::method("car_grat", INT, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) { ContO *c = s.sim.base[(int)a[0].i]; return Val::Int(c ? c->grat : 0); });
  R::method("car_keyz", I32, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) {
    ContO *c = s.sim.base[(int)a[0].i];
    std::vector<int32_t> v;
    if (c) for (int k = 0; k < 4; k++) { v.push_back(c->keyx[k]); v.push_back(c->keyz[k]); }
    if (c) v.push_back(c->maxR);
    return Val::Ints(v);
  });
  // per wheel: x y z (Godot metres), steer, width, height (NFM), rim r g b, rim size, rim depth
  R::method("wheels", F32, {{INT, "slot"}}, [](NfmSim &s, std::vector<Val> &a) {
    std::vector<float> o;
    for (auto &w : parse_wheels(s.sim.baseText[(int)a[0].i])) {
      o.insert(o.end(), {w.x * S, -w.y * S, -w.z * S, w.steer ? 1.0f : 0.0f, w.width, w.height, (float)w.rim[0], (float)w.rim[1], (float)w.rim[2], w.rimSize, w.rimDepth});
    }
    return Val::Floats(o);
  });

  // ---- stage
  R::method("load_stage", BOOL, {{BYTES, "data"}, {INT, "id"}, {INT, "nplayers"}, {INT, "seed"}}, [](NfmSim &s, std::vector<Val> &a) {
    return Val::Bool(s.sim.load_stage(bytes_str(a[0]), (int)a[1].i, (int)a[2].i, (uint64_t)a[3].i));
  });
  R::method("stage_env", I32, {}, [](NfmSim &s, std::vector<Val> &) {
    StageEnv &e = s.sim.env;
    std::vector<int32_t> v;
    // the colours as the original drew them: tinted by the stage snap(), and
    // the ground patches mixed with texture()
    Medium *m = s.sim.m;
    for (int k = 0; k < 3; k++) v.push_back(m->csky[k]);
    for (int k = 0; k < 3; k++) v.push_back(m->cfade[k]);
    for (int k = 0; k < 3; k++) v.push_back(m->cgrnd[k]);
    for (int k = 0; k < 3; k++) v.push_back(m->cpol[k]);
    v.push_back(1);
    for (int k : e.clouds) v.push_back(k);
    for (int k : e.texture) v.push_back(k);
    v.push_back(e.fadefrom); v.push_back(e.density); v.push_back(e.mountains);
    for (int k : e.snap) v.push_back(k);
    v.push_back(e.lightson);
    v.push_back(s.sim.cp->nlaps); v.push_back(s.sim.cp->nsp);
    for (int k : s.sim.bounds) v.push_back(k);
    return Val::Ints(v);
  });
  R::method("stage_soundtrack", STRING, {}, [](NfmSim &s, std::vector<Val> &) { return Val::String(s.sim.env.soundtrack); });
  R::method("stage_name", STRING, {}, [](NfmSim &s, std::vector<Val> &) { return Val::String(jisnull(s.sim.cp->name) ? "" : to_utf8(s.sim.cp->name)); });
  // name(...) line of a stage file, as the game lists it ('|' stands for ',')
  R::method("stage_title", STRING, {{BYTES, "data"}}, [](NfmSim &, std::vector<Val> &a) {
    for (auto &l : split_text_lines(bytes_str(a[0]))) {
      std::string t = jtrim(l);
      if (jstarts(t, "name(") && t.size() > 6 && t.back() == ')') return Val::String(to_utf8(jreplace(t.substr(5, t.size() - 6), "|", ",")));
    }
    return Val::String("");
  });
  // per object (16 floats): slot, flags (1 fix, 2 pile, 4 wall), checkpoint, transform (12), radius (NFM)
  R::method("stage_objects", F32, {}, [](NfmSim &s, std::vector<Val> &) {
    std::vector<float> o;
    for (auto &ob : s.sim.objects) {
      float tr[12];
      // placed pieces have their points pre-rotated by the placement angle and
      // xz = 0 (ContO(contO, x, y, z, rot)); the shared base mesh needs that angle
      nfm_transform(ob.c->x, ob.c->y, ob.c->z, ob.pile ? 0 : ob.rot, 0, 0, tr);
      o.push_back((float)ob.slot);
      o.push_back((float)((ob.fix ? 1 : 0) | (ob.pile ? 2 : 0) | (ob.wall ? 4 : 0)));
      o.push_back((float)ob.checkpoint);
      o.insert(o.end(), tr, tr + 12);
      o.push_back((float)ob.c->maxR);
    }
    return Val::Floats(o);
  });
  R::method("object_mesh", ARRAY, {{INT, "index"}}, [](NfmSim &s, std::vector<Val> &a) {
    MeshOut m;
    auto &ob = s.sim.objects.at(a[0].i);
    build_conto_mesh(ob.c, false, ob.pile ? "pile" : (ob.slot >= PIECE_BASE ? PIECE_FILES[ob.slot - PIECE_BASE] : ""), s.sim.env.snap, m);
    return mesh_val(m);
  });
  // collision zones the track is made of: per tracker x z radx radz (NFM units)
  R::method("trackers", I32, {}, [](NfmSim &s, std::vector<Val> &) {
    Trackers *t = s.sim.t;
    std::vector<int32_t> v;
    for (int i = 0; i < t->nt; i++) { v.push_back(t->x[i]); v.push_back(t->z[i]); v.push_back(t->radx[i]); v.push_back(t->radz[i]); }
    return Val::Ints(v);
  });
  // checkpoints: n, then per point x z y typ (NFM units)
  R::method("checkpoints", I32, {}, [](NfmSim &s, std::vector<Val> &) {
    CheckPoints *cp = s.sim.cp;
    std::vector<int32_t> v{cp->n};
    for (int i = 0; i < cp->n; i++) { v.push_back(cp->x[i]); v.push_back(cp->z[i]); v.push_back(cp->y[i]); v.push_back(cp->typ[i]); }
    return Val::Ints(v);
  });

  // ---- race
  R::method("start_race", NONE, {{I32, "grid"}, {INT, "human_mask"}, {FLOAT, "landk"}, {INT, "seed"}}, [](NfmSim &s, std::vector<Val> &a) {
    std::vector<int> grid(a[0].i32.begin(), a[0].i32.end());
    s.sim.start_race(grid, (uint32_t)a[1].i, (float)a[2].f, (uint64_t)a[3].i);
    return Val();
  });
  R::method("set_focus", NONE, {{INT, "index"}}, [](NfmSim &s, std::vector<Val> &a) { s.sim.focus = (int)a[0].i; return Val(); });
  // per car 8 floats: dealt %, taken from cars %, taken from world %, fixes, kills, killed by (-1 crash, -2 alive), died at tick, health left %
  R::method("combat_stats", F32, {}, [](NfmSim &s, std::vector<Val> &) {
    std::vector<float> o;
    Sim &m = s.sim;
    for (int i = 0; i < (int)m.combat.size(); i++) {
      auto &c = m.combat[i];
      float left = m.mads[i]->dest ? 0.0f : std::max(0.0f, 100.0f - 100.0f * m.mads[i]->hitmag / m.cd->maxmag[m.mads[i]->cn]);
      o.insert(o.end(), {c.dealt, c.taken, c.world, (float)c.fixes, (float)c.kills, (float)c.killedBy, (float)c.diedAt, left});
    }
    return Val::Floats(o);
  });
  // each car's best full lap in ticks, -1 if it has not finished one
  // damage each car did to each other car: [by * n + to]
  R::method("pair_dealt", F32, {}, [](NfmSim &s, std::vector<Val> &) { return Val::Floats(s.sim.pairDealt); });
  R::method("best_laps", I32, {}, [](NfmSim &s, std::vector<Val> &) { return Val::Ints(s.sim.bestLap); });
  // test hook (screenshots): set a car's damage as a fraction of its capacity
  R::method("debug_set_damage", NONE, {{INT, "i"}, {FLOAT, "frac"}}, [](NfmSim &s, std::vector<Val> &a) { Mad *m = s.sim.mads[(int)a[0].i]; m->hitmag = (int)(a[1].f * s.sim.cd->maxmag[m->cn]); return Val(); });
  R::method("set_freeroam", NONE, {{BOOL, "on"}}, [](NfmSim &s, std::vector<Val> &a) { s.sim.freeroam = a[0].b; return Val(); });
  R::method("set_fix_limit", NONE, {{INT, "n"}}, [](NfmSim &s, std::vector<Val> &a) { s.sim.fixLimit = (int)a[0].i; return Val(); });
  // tick of the last car-on-car hit each car took (-1000 = none): the killcam freezes on it
  R::method("last_hit_ticks", I32, {}, [](NfmSim &s, std::vector<Val> &) {
    std::vector<int32_t> o;
    for (auto &c : s.sim.combat) o.push_back(c.lastHitAt);
    return Val::Ints(o);
  });
  // which car hit each car last (-1 = none yet)
  R::method("last_hit_by", I32, {}, [](NfmSim &s, std::vector<Val> &) {
    std::vector<int32_t> o;
    for (auto &c : s.sim.combat) o.push_back(c.lastHitBy);
    return Val::Ints(o);
  });
  R::method("set_deathmatch", NONE, {{BOOL, "on"}}, [](NfmSim &s, std::vector<Val> &a) { s.sim.deathmatch = a[0].b; return Val(); });
  R::method("set_no_rescue", NONE, {{BOOL, "off"}}, [](NfmSim &s, std::vector<Val> &a) { s.sim.noRescue = a[0].b; return Val(); });
  R::method("step", NONE, {{I32, "inputs"}}, [](NfmSim &s, std::vector<Val> &a) {
    std::vector<int> in(a[0].i32.begin(), a[0].i32.end());
    s.sim.step(in);
    return Val();
  });
  // queued events (10 floats each: type, 9 values); clears the queue
  R::method("take_events", F32, {}, [](NfmSim &s, std::vector<Val> &) {
    std::vector<float> o;
    o.reserve(s.sim.events.size() * 10);
    for (auto &e : s.sim.events) { o.push_back((float)e.type); o.insert(o.end(), e.v, e.v + 9); }
    s.sim.events.clear();
    return Val::Floats(o);
  });
  R::method("car_states", F32, {}, [](NfmSim &s, std::vector<Val> &) {
    std::vector<float> o;
    for (int i = 0; i < s.sim.nplayers; i++) { auto v = car_state(s.sim, i); o.insert(o.end(), v.begin(), v.end()); }
    return Val::Floats(o);
  });
  R::method("car_state_size", INT, {}, [](NfmSim &, std::vector<Val> &) { return Val::Int(CAR_STATE_FLOATS); });
  R::method("race_car_mesh", ARRAY, {{INT, "index"}}, [](NfmSim &s, std::vector<Val> &a) {
    MeshOut m;
    build_conto_mesh(s.sim.cars[(int)a[0].i], true, "", s.sim.env.snap, m);
    return mesh_val(m);
  });
  R::method("race_info", I32, {}, [](NfmSim &s, std::vector<Val> &) {
    Sim &x = s.sim;
    return Val::Ints({x.tick, x.xt.starcnt, x.finished, x.resultKind, x.resultWho, x.resultPos, x.xt.laptime, x.cp ? x.cp->nsp : 0, x.cp ? x.cp->nlaps : 0, x.nplayers, x.rescues});
  });
  R::method("grid", I32, {}, [](NfmSim &s, std::vector<Val> &) { return Val::Ints(std::vector<int32_t>(s.sim.sc.begin(), s.sim.sc.end())); });
  R::method("state_hash", INT, {}, [](NfmSim &s, std::vector<Val> &) { return Val::Int(s.sim.state_hash()); });
  R::method("pose_transform", F32, {{FLOAT, "x"}, {FLOAT, "y"}, {FLOAT, "z"}, {FLOAT, "xz"}, {FLOAT, "xy"}, {FLOAT, "zy"}}, [](NfmSim &, std::vector<Val> &a) {
    float tr[12];
    nfm_transform(a[0].f, a[1].f, a[2].f, a[3].f, a[4].f, a[5].f, tr);
    return Val::Floats(std::vector<float>(tr, tr + 12));
  });
}

static void register_mod() {
  using M = ClassReg<NfmMod>;
  M::reg("NfmMod", "RefCounted");
  M::method("load", BOOL, {{BYTES, "data"}}, [](NfmMod &m, std::vector<Val> &a) { return Val::Bool(m.mod.load(a[0].bytes)); });
  // stereo frames for AudioStreamGeneratorPlayback.push_buffer
  M::method("render", V2, {{INT, "frames"}, {FLOAT, "rate"}}, [](NfmMod &m, std::vector<Val> &a) {
    std::vector<float> out((size_t)std::max<int64_t>(0, a[0].i) * 2);
    m.mod.render(out.data(), (int)a[0].i, a[1].f);
    return Val::Vec2s(out);
  });
}

static void deinitialize(void *, GDExtensionInitializationLevel) {}

extern "C" __declspec(dllexport) GDExtensionBool nfm_init(GDExtensionInterfaceGetProcAddress get, GDExtensionClassLibraryPtr lib, GDExtensionInitialization *init) {
  load_api(get, lib);
  init->initialize = initialize;
  init->deinitialize = deinitialize;
  init->minimum_initialization_level = GDEXTENSION_INITIALIZATION_SCENE;
  init->userdata = nullptr;
  return true;
}
