#include "modplayer.h"
#include <algorithm>
#include <cmath>
#include <cstring>

static const double PAL = 7093789.2;
static float SINE[64];
static bool sineInit = false;

bool ModPlayer::load(const std::vector<uint8_t> &b) {
  loaded = false;
  if (b.size() < 1084) return false;
  if (!sineInit) { for (int i = 0; i < 64; i++) SINE[i] = (float)std::sin(i / 64.0 * 3.141592653589793 * 2); sineInit = true; }
  auto u8 = [&](size_t o) -> int { return o < b.size() ? b[o] : 0; };
  char sig[5] = {(char)u8(1080), (char)u8(1081), (char)u8(1082), (char)u8(1083), 0};
  nch = 4;
  int nsamp = 31;
  std::string s(sig);
  if (isdigit((unsigned char)sig[0]) && s.substr(1) == "CHN") nch = sig[0] - '0';
  else if (isdigit((unsigned char)sig[0]) && isdigit((unsigned char)sig[1]) && s.substr(2) == "CH") nch = (sig[0] - '0') * 10 + (sig[1] - '0');
  else if (s == "FLT8" || s == "OCTA") nch = 8;
  else if (!(s == "M.K." || s == "M!K!" || s == "FLT4" || s == "4CHN")) nsamp = 15;
  if (nch < 1 || nch > 32) return false;
  auto word = [&](size_t o) { return (u8(o) << 8) | u8(o + 1); };
  samples.assign(nsamp + 1, Sample());
  size_t off = 20;
  for (int i = 1; i <= nsamp; i++) {
    Sample &sm = samples[i];
    int ft = u8(off + 24) & 15;
    if (ft > 7) ft -= 16;
    sm.len = word(off + 22) * 2; sm.finetune = ft; sm.vol = std::min(64, u8(off + 25));
    sm.ls = word(off + 26) * 2; sm.ll = word(off + 28) * 2;
    off += 30;
  }
  songLen = std::max(1, u8(off));
  orders.assign(128, 0);
  for (int i = 0; i < 128; i++) orders[i] = u8(off + 2 + i);
  off += 2 + 128 + (nsamp == 31 ? 4 : 0);
  int npat = 0;
  for (int i = 0; i < 128; i++) npat = std::max(npat, orders[i] + 1);
  patterns.assign(npat, std::vector<Note>(64 * nch));
  for (int p = 0; p < npat; p++) {
    for (int r = 0; r < 64; r++) {
      for (int c = 0; c < nch; c++) {
        size_t o = off + ((size_t)(p * 64 + r) * nch + c) * 4;
        int b0 = u8(o), b1 = u8(o + 1), b2 = u8(o + 2), b3 = u8(o + 3);
        patterns[p][r * nch + c] = {((b0 & 15) << 8) | b1, (b0 & 0xf0) | (b2 >> 4), b2 & 15, b3};
      }
    }
  }
  off += (size_t)npat * 64 * nch * 4;
  for (int i = 1; i <= nsamp; i++) {
    Sample &sm = samples[i];
    sm.data.assign(sm.len, 0.0f);
    for (int k = 0; k < sm.len && off + k < b.size(); k++) sm.data[k] = (int8_t)b[off + k] / 128.0f;
    off += sm.len;
    if (sm.ll <= 2) sm.ll = 0;
    if (sm.ls + sm.ll > sm.len) sm.ll = std::max(0, sm.len - sm.ls);
  }
  ch.assign(nch, Chan());
  for (int i = 0; i < nch; i++) ch[i].pan = (i % 4 == 0 || i % 4 == 3) ? 0.25f : 0.75f;
  order = 0; row = 0; tick = 0; speed = 6; tempo = 125;
  tickLeft = 0; patDelay = 0; delaying = false; jump = -1; brk = -1; loopJump = false;
  loaded = true;
  return true;
}

double ModPlayer::fine(double period, int ft) { return ft ? period * std::pow(2.0, -ft / 96.0) : period; }

void ModPlayer::setInc(Chan &c, double period) { c.inc = period > 0 ? PAL / (period * 2) / rate : 0; }

void ModPlayer::processRow() {
  if (order >= (int)orders.size() || orders[order] >= (int)patterns.size()) { order = 0; row = 0; return; }
  auto &pat = patterns[orders[order]];
  for (int i = 0; i < nch; i++) {
    Chan &c = ch[i];
    const Note &n = pat[row * nch + i];
    c.note = &n;
    if (n.sample > 0 && n.sample < (int)samples.size()) {
      Sample &s = samples[n.sample];
      c.s = &s; c.vol = s.vol; c.finetune = s.finetune;
    }
    if (n.fx == 0xe && (n.param >> 4) == 5) c.finetune = (n.param & 15) > 7 ? (n.param & 15) - 16 : n.param & 15;
    if (n.period > 0) {
      double p = fine(n.period, c.finetune);
      if (n.fx == 3 || n.fx == 5) c.target = p;
      else if (!(n.fx == 0xe && (n.param >> 4) == 0xd)) {
        c.period = p; c.pos = 0; c.vibPos = 0;
        if (n.fx == 9) { if (n.param) c.offMem = n.param * 256; c.pos = c.offMem; }
      }
    }
    rowEffect(c, n);
    c.outVol = c.vol;
    setInc(c, c.period);
  }
}

void ModPlayer::rowEffect(Chan &c, const Note &n) {
  int x = n.param >> 4, y = n.param & 15;
  switch (n.fx) {
    case 3: if (n.param) c.portaSpeed = n.param; break;
    case 4: if (x) c.vibSpeed = x; if (y) c.vibDepth = y; break;
    case 7: if (x) c.tremSpeed = x; if (y) c.tremDepth = y; break;
    case 0xb: jump = n.param; break;
    case 0xc: c.vol = std::min(64, n.param); break;
    case 0xd: brk = x * 10 + y; break;
    case 0xe:
      if (x == 1) c.period = std::max(113.0, c.period - y);
      if (x == 2) c.period = std::min(856.0 * 2, c.period + y);
      if (x == 6) {
        if (y == 0) c.loopRow = row;
        else if (c.loopCount == 0) { c.loopCount = y; brk = c.loopRow; loopJump = true; }
        else if (--c.loopCount > 0) { brk = c.loopRow; loopJump = true; }
      }
      if (x == 0xa) c.vol = std::min(64, c.vol + y);
      if (x == 0xb) c.vol = std::max(0, c.vol - y);
      // pattern delay: set once; re-reading it on every repeat would hold the
      // song on this row for ever (Grapefruit Power's track got stuck)
      if (x == 0xe && !delaying) patDelay = y;
      break;
    case 0xf:
      if (n.param == 0) break;
      if (n.param < 32) speed = n.param; else tempo = n.param;
      break;
    default: break;
  }
}

void ModPlayer::volSlide(Chan &c, int x, int y) { c.vol = x ? std::min(64, c.vol + x) : std::max(0, c.vol - y); }

void ModPlayer::tickEffects() {
  for (int i = 0; i < nch; i++) {
    Chan &c = ch[i];
    const Note *n = c.note;
    if (!n) continue;
    int x = n->param >> 4, y = n->param & 15;
    double period = c.period;
    switch (n->fx) {
      case 0:
        if (n->param) { int k = tick % 3; int semis = k == 0 ? 0 : k == 1 ? x : y; period = c.period * std::pow(2.0, -semis / 12.0); }
        break;
      case 1: c.period = std::max(113.0, c.period - n->param); period = c.period; break;
      case 2: c.period = std::min(1712.0, c.period + n->param); period = c.period; break;
      case 3: case 5:
        if (c.target) {
          if (c.period < c.target) c.period = std::min(c.target, c.period + c.portaSpeed);
          else c.period = std::max(c.target, c.period - c.portaSpeed);
        }
        period = c.period;
        if (n->fx == 5) volSlide(c, x, y);
        break;
      case 4: case 6:
        c.vibPos = (c.vibPos + c.vibSpeed) & 63;
        period = c.period + SINE[c.vibPos] * c.vibDepth * 2;
        if (n->fx == 6) volSlide(c, x, y);
        break;
      case 7:
        c.tremPos = (c.tremPos + c.tremSpeed) & 63;
        c.outVol = std::max(0.0, std::min(64.0, c.vol + SINE[c.tremPos] * c.tremDepth * 4.0));
        break;
      case 0xa: volSlide(c, x, y); break;
      case 0xe:
        if (x == 9 && y && tick % y == 0) c.pos = 0;
        if (x == 0xc && tick == y) c.vol = 0;
        if (x == 0xd && tick == y && n->period) { c.period = fine(n->period, c.finetune); c.pos = 0; period = c.period; }
        break;
      default: break;
    }
    if (n->fx != 7) c.outVol = c.vol;
    setInc(c, period);
  }
}

void ModPlayer::advanceRow() {
  if (patDelay > 0) { patDelay--; delaying = true; return; }
  delaying = false;
  if (jump >= 0 || brk >= 0) {
    if (!loopJump) order = jump >= 0 ? jump : order + 1;
    row = brk >= 0 ? std::min(63, brk) : 0;
    jump = -1; brk = -1; loopJump = false;
  } else if (++row >= 64) { row = 0; order++; }
  if (order >= songLen) order = 0;
}

void ModPlayer::render(float *lr, int frames, double sampleRate) {
  rate = sampleRate;
  if (!loaded) { memset(lr, 0, sizeof(float) * 2 * frames); return; }
  for (int i = 0; i < frames; i++) {
    if (tickLeft <= 0) {
      if (tick == 0 && !delaying) processRow(); else tickEffects();
      if (++tick >= speed) { tick = 0; advanceRow(); }
      tickLeft += rate * 2.5 / tempo;
    }
    tickLeft--;
    double l = 0, r = 0;
    for (int k = 0; k < nch; k++) {
      Chan &c = ch[k];
      Sample *s = c.s;
      if (!s || s->data.empty() || c.inc == 0) continue;
      int end = s->ll ? s->ls + s->ll : s->len;
      if (c.pos >= end) {
        if (s->ll) c.pos = s->ls + std::fmod(c.pos - s->ls, (double)s->ll); else continue;
      }
      int ip = (int)c.pos;
      double f = c.pos - ip;
      int nx = ip + 1;
      if (nx >= end) nx = s->ll ? s->ls : ip;
      if (ip >= (int)s->data.size() || nx >= (int)s->data.size()) continue;
      double v = (s->data[ip] * (1 - f) + s->data[nx] * f) * (c.outVol / 64.0);
      l += v * (1 - c.pan); r += v * c.pan;
      c.pos += c.inc;
    }
    lr[i * 2] = (float)std::tanh(l * 0.55);
    lr[i * 2 + 1] = (float)std::tanh(r * 0.55);
  }
}
