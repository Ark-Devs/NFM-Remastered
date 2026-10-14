// ProTracker MOD player (the original game played its soundtracks with IBXM).
// Interpolated mixing with soft Amiga-style stereo. Port of remake/src/modplayer.js.
#pragma once
#include <cstdint>
#include <string>
#include <vector>

struct ModPlayer {
  struct Sample { int len = 0, finetune = 0, vol = 0, ls = 0, ll = 0; std::vector<float> data; };
  struct Note { int period = 0, sample = 0, fx = 0, param = 0; };
  struct Chan {
    Sample *s = nullptr;
    double pos = 0, inc = 0, period = 0, target = 0;
    int vol = 0, portaSpeed = 0, vibSpeed = 0, vibDepth = 0, vibPos = 0, tremSpeed = 0, tremDepth = 0, tremPos = 0;
    int finetune = 0, loopRow = 0, loopCount = 0, offMem = 0;
    double outVol = 0;
    const Note *note = nullptr;
    float pan = 0.5f;
  };
  bool loaded = false;
  int nch = 4, songLen = 1;
  std::vector<Sample> samples; // 1-based
  std::vector<int> orders;
  std::vector<std::vector<Note>> patterns; // [pattern][row * nch + ch]
  std::vector<Chan> ch;
  int order = 0, row = 0, tick = 0, speed = 6, tempo = 125, patDelay = 0, jump = -1, brk = -1;
  bool delaying = false; // repeating a row for EEx: its notes and its EEx are not re-read
  bool loopJump = false;
  double tickLeft = 0, rate = 44100;

  bool load(const std::vector<uint8_t> &b);
  void render(float *lr, int frames, double sampleRate); // interleaved stereo

 private:
  double fine(double period, int ft);
  void setInc(Chan &c, double period);
  void processRow();
  void rowEffect(Chan &c, const Note &n);
  void tickEffects();
  void volSlide(Chan &c, int x, int y);
  void advanceRow();
};
