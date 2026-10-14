// The race loop: a faithful port of GameSparker's offline race tick (25 Hz),
// with the original Mad physics, Control AI and CheckPoints standings.
import { Mad } from '../gen/Mad.js';
import { Control } from '../gen/Control.js';
import { ContO } from '../gen/ContO.js';
import { XSTART, ZSTART } from './world.js';

export const TICK_MS = 40; // the original ran at a steady 25 frames per second

// Stand-ins for xtGraphics / Record: the parts the physics talk to. Sound and
// visual events are forwarded to listeners instead of being drawn in Java2D.
class XtBridge {
  constructor(events) {
    this.events = events;
    this.im = 0;
    this.multion = 0;
    this.lan = false;
    this.isbot = [false, false, false, false, false, false, false, false];
    this.mutes = false;
    this.starcnt = 0;
    this.laptime = 0;
    this.fastestlap = 0;
    this.beststunt = 0;
    this.dcrashes = new Int32Array(8);
    this.carfixed = { play: () => events.emit('carfixed') };
  }
  crash(f, n) { this.events.emit('crash', f, n); }
  skid(n, f) { this.events.emit('skid', n, f); }
  scrape(x, y, z) { this.events.emit('scrape', x, y, z); }
  gscrape(x, y, z) { this.events.emit('gscrape', x, y, z); }
}

class RecordBridge {
  constructor() {
    this.dest = new Int32Array(8);
    this.fix = new Int32Array(8);
    this.powered = 0;
    this.wasted = 0;
    this.hcaught = false;
    this.whenwasted = 0;
    this.closefinish = 0;
  }
  recx() {}
  recy() {}
  recz() {}
  cotchinow() {}
}

export class Emitter {
  constructor() { this.l = {}; }
  on(ev, fn) { (this.l[ev] ||= []).push(fn); }
  emit(ev, ...a) { for (const fn of this.l[ev] || []) fn(...a); }
}

export class Race {
  constructor(world, stageDef, { stage, playerCar, opponents, nplayers = 7, landk = 1 }) {
    this.world = world;
    this.stage = stage;
    this.def = stageDef;
    this.cp = stageDef.cp;
    this.events = new Emitter();
    this.xt = new XtBridge(this.events);
    this.rec = new RecordBridge();
    this.nplayers = nplayers;
    this.sc = [playerCar, ...opponents].slice(0, nplayers);
    this.cars = [];
    this.mads = [];
    this.controls = [];
    const m = world.medium;
    // ContO.dust / ContO.sprk spawned Java2D particles; forward them to the renderer.
    const events = this.events;
    ContO.prototype.dust = function (wheel, x, y, z, vx, vz, mag, tilt, capsized) { events.emit('dust', this, wheel, x, y, z, vx, vz, mag, tilt, capsized); };
    ContO.prototype.sprk = function (x, y, z, vx, vy, vz, kind) { events.emit('spark', this, x, y, z, vx, vy, vz, kind); };
    for (let i = 0; i < nplayers; i++) this.controls.push(new Control(m));
    for (let i = 0; i < nplayers; i++) this.controls[i].reset(this.cp, this.sc[i]);
    for (let i = 0; i < nplayers; i++) {
      const base = world.base[this.sc[i]];
      const car = ContO.create1(base, XSTART[i], 250 - base.grat, ZSTART[i], 0);
      car.dist = 1;
      const mad = new Mad(world.cd, m, this.rec, this.xt, i);
      mad.reseto(this.sc[i], car, this.cp);
      mad.landk = landk;
      this.cars.push(car);
      this.mads.push(mad);
    }
    this.carVersion = new Int32Array(nplayers); // bumps when a car is rebuilt (repaired)
    this.xt.starcnt = 130;
    this.tick = 0;
    this.finished = false;
    this.result = null;
    this.lastClear = 0;
    this.lastDest = new Array(nplayers).fill(false);
    this.lastNlaps = 0;
    this.lastPower = 0;
    // career stats
    this.stats = { wastedByYou: 0, bestStunt: 0, stuntTotal: 0, repairs: 0 };
    this.wrecked = new Set(); // grid indices the player wasted
    this.lastPowerup = 0;
    // AI rescue: per car, best progress seen and when it last improved
    this.aiProgress = Array.from({ length: nplayers }, () => ({ clear: -1, point: -1, bestD: Infinity, at: 0, tries: 0 }));
    this.rescues = 0;
  }

  get player() { return this.mads[0]; }
  get playerCar() { return this.cars[0]; }
  get countdown() { return this.xt.starcnt; }

  step(input) {
    const { cars, mads, controls, cp, xt } = this;
    const n = this.nplayers;
    this.tick++;

    // GameSparker: cars flagged newcar after a fix are rebuilt from the clean model.
    for (let i = 0; i < n; i++) {
      if (!mads[i].newcar) continue;
      const old = cars[i];
      const c = ContO.create1(this.world.base[mads[i].cn], old.x, old.y, old.z, 0);
      c.xz = old.xz; c.xy = old.xy; c.zy = old.zy;
      c.dist = 1;
      cars[i] = c;
      mads[i].newcar = false;
      this.carVersion[i]++;
      if (i === 0) this.stats.repairs++;
    }

    // Player input goes into control 0.
    const u = controls[0];
    u.left = input.left; u.right = input.right; u.up = input.up; u.down = input.down; u.handb = input.handb;
    u.lookback = input.lookback;

    if (xt.starcnt === 0) {
      for (let i = 0; i < n; i++) for (let j = 0; j < n; j++) if (i !== j) mads[i].colide(cars[i], mads[j], cars[j]);
      for (let i = 0; i < n; i++) mads[i].drive(controls[i], cars[i], this.world.trackers, cp);
      cp.checkstat(mads, cars, this.rec, n, 0, 0);
      for (let i = 1; i < n; i++) controls[i].preform(mads[i], cars[i], cp, this.world.trackers);
      this.rescueStuckAI();
      if (!this.finished) xt.laptime++;
    } else {
      xt.starcnt--;
      if (xt.starcnt === 0) this.events.emit('go');
      else if (xt.starcnt % 30 === 0 && xt.starcnt <= 90) this.events.emit('count', xt.starcnt / 30);
    }

    // ContO.fixit animation counter (was advanced by the renderer in Java).
    for (let i = 0; i < n; i++) {
      const c = cars[i];
      if (c.fix) {
        if (c.fcnt > 7) { c.fcnt = 0; c.fix = false; } else c.fcnt++;
      }
    }

    this.detectEvents();
  }

  // The original AI was tuned for the official stages and can get stuck on
  // community ones (puzzle pieces, falls, flips in corners). If an AI car makes
  // no progress toward its next checkpoint for a while, put it back on the
  // last checkpoint it cleared, facing the next one. Physics are untouched.
  rescueStuckAI() {
    const { cp, mads, cars } = this;
    if (cp.nsp < 2 || this.noRescue) return;
    for (let i = 1; i < this.nplayers; i++) {
      const mad = mads[i], car = cars[i];
      if (mad.dest || cp.clear[i] >= cp.nlaps * cp.nsp) continue;
      const p = this.aiProgress[i];
      // progress = a checkpoint cleared, a new waypoint targeted, or closing in
      // on the waypoint the AI itself is steering for (Mad.point)
      const d = Math.hypot(car.x - cp.x[mad.point], car.z - cp.z[mad.point]);
      if (mad.clear !== p.clear || mad.point !== p.point || d < p.bestD - 300) {
        if (mad.clear !== p.clear) p.tries = 0;
        p.clear = mad.clear; p.point = mad.point; p.bestD = d; p.at = this.tick;
        continue;
      }
      if (this.tick - p.at < 250) continue; // ~10 s stuck
      const tgt = this.nextCheckpointIndex(mad);
      // 1st: retry from the last checkpoint. 2nd: skip the obstacle, drop it
      // before the next checkpoint. 3rd+: put it in the gate itself (puzzle
      // stages with raised checkpoints the AI can't work out).
      this.rescueTo(i, tgt, Math.min(2, p.tries++));
      p.bestD = Infinity; p.at = this.tick;
    }
  }

  // index (into cp.x/z/typ) of the next real checkpoint this car must take
  nextCheckpointIndex(mad) {
    const cp = this.cp;
    let k = mad.pcleared;
    for (let n = 0; n < cp.n; n++) {
      k = (k + 1) % cp.n;
      if (cp.typ[k] > 0) return k;
    }
    return 0;
  }

  rescueTo(i, tgt, level = 0) {
    const skip = level >= 1;
    const { cp, cars, mads } = this;
    const car = cars[i], mad = mads[i];
    // restart from the last checkpoint cleared (or the start grid)
    const from = mad.clear > 0 ? mad.pcleared : -1;
    const fx = from >= 0 ? cp.x[from] : 0, fz = from >= 0 ? cp.z[from] : 0;
    const fy = skip ? cp.y[tgt] || 250 : from >= 0 && cp.y[from] ? cp.y[from] : 250;
    const dx = cp.x[tgt] - fx, dz = cp.z[tgt] - fz;
    const len = Math.hypot(dx, dz) || 1;
    // normal: just past the last checkpoint; skip: 1200 units before the next one
    const back = level >= 2 ? 0 : 1200;
    const ax = skip ? cp.x[tgt] - (dx / len) * back : fx + (dx / len) * 600;
    const az = skip ? cp.z[tgt] - (dz / len) * back : fz + (dz / len) * 600;
    const spread = level >= 2 ? 0 : (i - 3) * 120;
    car.x = Math.round(ax + spread * (dz / len));
    car.z = Math.round(az - spread * (dx / len));
    car.y = Math.min(fy, 250) - car.grat - 200;
    // NFM forward for heading xz is (-sin xz, cos xz)
    car.xz = Math.round((Math.atan2(-dx, dz) * 180) / Math.PI);
    car.xy = 0; car.zy = 0;
    mad.pxy = 0; mad.pzy = 0;
    mad.speed = 0;
    for (let k = 0; k < 4; k++) { mad.scx[k] = 0; mad.scy[k] = 0; mad.scz[k] = 0; }
    mad.cxz = car.xz; mad.mxz = car.xz;
    this.rescues++;
    this.events.emit('rescue', i);
  }

  detectEvents() {
    const p = this.mads[0];
    const cp = this.cp;
    if (p.clear !== this.lastClear) {
      if (p.clear > this.lastClear) this.events.emit('checkpoint', p.clear);
      this.lastClear = p.clear;
    }
    if (p.nlaps !== this.lastNlaps) {
      if (p.nlaps > this.lastNlaps && p.nlaps < cp.nlaps) this.events.emit('lap', p.nlaps + 1);
      this.lastNlaps = p.nlaps;
    }
    if (p.power === 98 && this.lastPower < 98 && p.powerup > 0) this.events.emit('powerup', p.powerup);
    // Mad.drive sets powerup when a stunt lands; each new value is one stunt
    if (p.powerup !== this.lastPowerup) {
      this.lastPowerup = p.powerup;
      const v = Math.trunc(p.powerup);
      if (v > 0) {
        this.stats.bestStunt = Math.max(this.stats.bestStunt, v);
        this.stats.stuntTotal += v;
        this.events.emit('stunt', v);
      }
    }
    this.lastPower = p.power;
    for (let i = 0; i < this.nplayers; i++) {
      if (this.mads[i].dest && !this.lastDest[i]) {
        // lastcolido counts down after the player hits a car: they caused it
        const byYou = i !== 0 && this.mads[i].lastcolido > 0;
        if (byYou) { this.stats.wastedByYou++; this.wrecked.add(i); }
        this.events.emit('wasted', i, byYou);
      }
      this.lastDest[i] = this.mads[i].dest;
    }
    if (!this.finished) {
      const total = cp.nlaps * cp.nsp;
      if (p.dest) this.finish('wasted');
      else if (p.clear >= total) this.finish('finished');
      else if (cp.wasted >= this.nplayers - 1 && this.nplayers > 1) this.finish('allwasted');
      else {
        for (let i = 1; i < this.nplayers; i++) {
          if (cp.clear[i] >= total && !this.mads[i].dest) { this.finish('lost', i); break; }
        }
      }
    }
  }

  finish(kind, who = 0) {
    this.finished = true;
    this.result = { kind, who, position: this.cp.pos[0] + 1 };
    this.events.emit('finish', this.result);
  }
}
