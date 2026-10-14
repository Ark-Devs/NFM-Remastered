// Career: the original's way of progressing, stretched over every car and stage.
//
// Stages open one win at a time (win = cross the line first, or waste the
// whole field). Cars are never bought or ticked off a checklist: each one is
// out on a particular stage as that stage's rival, and you take it by beating
// it in that race or by wrecking it yourself.
//
//   Classic Madness  - stages 1-10, the original NFM1 cars and unlock order
//   Madness Returns  - stages 11-27, the rest of the original cars
//   Beyond Madness   - stages 28-32 and every community stage, with the
//                      community cars as rivals, weakest first
// The first two are open from the start (as in the original); the third opens
// once Madness Returns is won.

const KEY = 'nfmr.career2';
const STARTERS = [0, 1, 2, 3, 4];
// original stage id -> rival car slots (the original's unlock points)
const ORIGINAL_RIVALS = {
  2: [5], 4: [6], 6: [11], 8: [14], 10: [15],
  11: [7], 13: [8], 15: [9], 17: [10], 19: [12], 21: [13],
};

export const CHAPTERS_META = [
  { id: 'classic', title: 'Classic Madness', blurb: 'Where it started. Ten stages, the original crew.' },
  { id: 'returns', title: 'Madness Returns', blurb: 'Seventeen stages. Bigger jumps, harder rivals.' },
  { id: 'beyond', title: 'Beyond Madness', blurb: 'The arenas and the community\'s wildest tracks.' },
];

// Rough overall strength, used only to order the community rivals.
function carRating(cd, s) {
  const speed = (cd.swits[s][2] - 200) / 115;
  const accel = (cd.acelf[s][0] + cd.acelf[s][1] + cd.acelf[s][2] - 13) / 13;
  const strength = (cd.moment[s] - 0.7) / 2.3;
  const endurance = cd.maxmag[s] / 20000;
  return speed * 1.2 + accel + strength * 0.8 + endurance * 0.6;
}

export function buildCareer(assets, world) {
  const stageIdx = (pred) => assets.stages.findIndex(pred);
  const original = (id) => stageIdx((s) => !s.custom && s.id === id);
  const entry = (i, rivals = []) => ({ index: i, key: stageKey(assets.stages[i]), rivals: [...rivals] });

  const ch1 = [], ch2 = [], ch3 = [];
  for (let id = 1; id <= 10; id++) { const i = original(id); if (i >= 0) ch1.push(entry(i, ORIGINAL_RIVALS[id])); }
  for (let id = 11; id <= 27; id++) { const i = original(id); if (i >= 0) ch2.push(entry(i, ORIGINAL_RIVALS[id])); }
  for (let id = 28; id <= 40; id++) { const i = original(id); if (i >= 0) ch3.push(entry(i)); }
  assets.stages.forEach((s, i) => { if (s.custom) ch3.push(entry(i)); });

  // community cars, weakest first: the late Madness Returns stages that have
  // no original rival left take the first few, Beyond Madness the rest
  const custom = world.carList.filter((c) => c.custom).map((c) => c.slot)
    .sort((a, b) => carRating(world.cd, a) - carRating(world.cd, b));
  for (const e of ch2) if (!e.rivals.length && ch2.indexOf(e) >= 12 && custom.length) e.rivals.push(custom.shift());
  const open = ch3.filter((e) => !assets.stages[e.index].solo); // solo stages have no grid
  custom.forEach((slot, j) => {
    const k = Math.min(open.length - 1, Math.floor((j * open.length) / custom.length));
    if (k >= 0) open[k].rivals.push(slot);
  });

  const chapters = [ch1, ch2, ch3].map((stages, c) => ({ ...CHAPTERS_META[c], stages })).filter((c) => c.stages.length);

  // opponents for a stage: its rivals first, then cars from the same stretch
  // of the career (starters and earlier rivals)
  for (const ch of chapters) {
    const pool = [...STARTERS];
    if (ch.id === 'returns') pool.push(5, 6, 11, 14, 15); // the Classic Madness rivals
    if (ch.id === 'beyond') pool.push(...Object.values(ORIGINAL_RIVALS).flat());
    for (const e of ch.stages) {
      e.pool = [...new Set(pool)];
      pool.push(...e.rivals);
    }
  }
  return new Career(chapters, assets);
}

export function stageKey(s) { return s.custom ? 'c:' + s.file : 'o:' + s.id; }

export class Career {
  constructor(chapters, assets) {
    this.chapters = chapters;
    this.assets = assets;
    this.s = this.load() || this.fresh();
  }

  fresh() { return { v: 2, won: [], cars: [...STARTERS], best: {}, chapter: 0, stage: {} }; }

  load() {
    try {
      const s = JSON.parse(localStorage.getItem(KEY));
      if (s && s.v === 2) return s;
    } catch { /* storage unavailable */ }
    return null;
  }

  save() { try { localStorage.setItem(KEY, JSON.stringify(this.s)); } catch { /* storage unavailable */ } }
  reset() { this.s = this.fresh(); this.save(); }

  owns(slot) { return this.s.cars.includes(slot); }
  won(key) { return this.s.won.includes(key); }

  chapterOpen(c) {
    if (c <= 1) return true;
    const prev = this.chapters[c - 1];
    return this.won(prev.stages[prev.stages.length - 1].key);
  }

  stageOpen(c, k) {
    if (!this.chapterOpen(c)) return false;
    return k === 0 || this.won(this.chapters[c].stages[k - 1].key);
  }

  // furthest open stage of a chapter
  frontier(c) {
    const st = this.chapters[c].stages;
    let k = 0;
    while (k < st.length - 1 && this.won(st[k].key)) k++;
    return k;
  }

  wonCount(c) { return this.chapters[c].stages.filter((e) => this.won(e.key)).length; }

  // where a locked car is out to be won
  carSource(slot) {
    for (let c = 0; c < this.chapters.length; c++) {
      const ch = this.chapters[c];
      const k = ch.stages.findIndex((e) => e.rivals.includes(slot));
      if (k >= 0) return { chapter: c, stage: k, entry: ch.stages[k], name: this.assets.stages[ch.stages[k].index].name };
    }
    return null;
  }

  // Apply a finished race. Returns what it earned.
  record(c, k, result, { laptime, wreckedRivals = [] }) {
    const e = this.chapters[c].stages[k];
    const out = { stageWon: false, firstWin: false, nextStage: null, cars: [], bestTime: false, chapterDone: false };
    const won = result.kind === 'allwasted' || (result.kind === 'finished' && result.position === 1);
    if (won) {
      out.stageWon = true;
      out.firstWin = !this.won(e.key);
      if (out.firstWin) this.s.won.push(e.key);
      if (result.kind === 'finished' && (!this.s.best[e.key] || laptime < this.s.best[e.key])) {
        out.bestTime = !!this.s.best[e.key];
        this.s.best[e.key] = laptime;
      }
      const st = this.chapters[c].stages;
      if (out.firstWin && k + 1 < st.length) out.nextStage = { chapter: c, stage: k + 1 };
      if (out.firstWin && k + 1 === st.length) {
        out.chapterDone = true;
        if (this.chapters[c + 1] && c + 1 >= 2) out.nextStage = { chapter: c + 1, stage: 0 };
      }
    }
    // rivals: beat them (win the race) or wreck them yourself
    for (const slot of e.rivals) {
      if (this.owns(slot)) continue;
      if (won || wreckedRivals.includes(slot)) { this.s.cars.push(slot); out.cars.push(slot); }
    }
    this.save();
    return out;
  }
}
