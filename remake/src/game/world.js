// Builds the game world exactly the way GameSparker.loadbase / loadstage did,
// using the translated logic classes in src/gen.
import { Medium } from '../gen/Medium.js';
import { Trackers } from '../gen/Trackers.js';
import { CheckPoints } from '../gen/CheckPoints.js';
import { CarDefine } from '../gen/CarDefine.js';
import { ContO } from '../gen/ContO.js';
import { parseWheels } from '../render/meshbuild.js';

export const CAR_FILES = ['2000tornados', 'formula7', 'canyenaro', 'lescrab', 'nimi', 'maxrevenge', 'leadoxide', 'koolkat', 'drifter', 'policecops', 'mustang', 'king', 'audir8', 'masheen', 'radicalone', 'drmonster'];
export const PIECE_FILES = ['road', 'froad', 'twister2', 'twister1', 'turn', 'offroad', 'bumproad', 'offturn', 'nroad', 'nturn', 'roblend', 'noblend', 'rnblend', 'roadend', 'offroadend', 'hpground', 'ramp30', 'cramp35', 'dramp15', 'dhilo15', 'slide10', 'takeoff', 'sramp22', 'offbump', 'offramp', 'sofframp', 'halfpipe', 'spikes', 'rail', 'thewall', 'checkpoint', 'fixpoint', 'offcheckpoint', 'sideoff', 'bsideoff', 'uprise', 'riseroad', 'sroad', 'soffroad', 'tside', 'launchpad', 'thenet', 'speedramp', 'offhill', 'slider', 'uphill', 'roll1', 'roll2', 'roll3', 'roll4', 'roll5', 'roll6', 'opile1', 'opile2', 'aircheckpoint', 'tree1', 'tree2', 'tree3', 'tree4', 'tree5', 'tree6', 'tree7', 'tree8', 'cac1', 'cac2', 'cac3', '8sroad', '8soffroad'];
export const FIRST_CUSTOM_SLOT = 16;
export const PIECE_BASE = 56;

export const XSTART = [0, -350, 350, 0, -350, 350, 0, 0];
export const ZSTART = [-760, -380, -380, 0, 380, 380, 760, 0];

function getint(key, line, idx) {
  // GameSparker.getint: values separated by ',' or ')'.
  let count = 0;
  let s = '';
  for (let i = key.length + 1; i < line.length; i++) {
    const ch = line.charAt(i);
    if (ch === ',' || ch === ')') { count++; i++; }
    if (count !== idx) continue;
    if (i < line.length) s += line.charAt(i);
  }
  const v = parseInt(s, 10);
  if (Number.isNaN(v)) throw new Error(`bad number in "${line}"`);
  return v;
}

export class World {
  constructor(assets) {
    this.assets = assets;
    this.medium = new Medium();
    this.trackers = new Trackers();
    this.base = new Array(PIECE_BASE + PIECE_FILES.length).fill(null);
    this.baseText = new Array(this.base.length).fill(null);
    this.cd = new CarDefine(this.base, this.medium, this.trackers, null);
    this.carList = [];
    this.loadModels();
  }

  loadModels() {
    const m = this.medium;
    // Same matching rule as loadbase: last name the entry starts with wins.
    for (const [entry, text] of Object.entries(this.assets.models)) {
      let slot = 0;
      for (let i = 0; i < CAR_FILES.length; i++) if (entry.startsWith(CAR_FILES[i])) slot = i;
      for (let i = 0; i < PIECE_FILES.length; i++) if (entry.startsWith(PIECE_FILES[i])) slot = i + PIECE_BASE;
      m.loadnew = false;
      this.base[slot] = ContO.create0(text, m, this.trackers);
      this.baseText[slot] = text;
    }
    for (let i = 0; i < CAR_FILES.length; i++) this.carList.push({ slot: i, name: this.cd.names[i], custom: false });

    // Custom cars from mycars/ go into slots 16.. like CarDefine's loader.
    let slot = FIRST_CUSTOM_SLOT;
    for (const car of this.assets.customCars) {
      if (slot >= PIECE_BASE) break;
      try {
        m.loadnew = true;
        const c = ContO.create0(car.text, m, this.trackers);
        m.loadnew = false;
        if (c.errd || c.npl <= 60 || c.maxR < 120) continue;
        const k = c.keyz, x = c.keyx;
        if (k[0] < 0 || x[0] > 0 || k[1] < 0 || x[1] < 0 || k[2] > 0 || x[2] > 0 || k[3] > 0 || x[3] < 0) continue;
        c.shadow = true; c.noline = false; c.decor = false; c.tnt = 0; c.disp = 0;
        // ContO takes the ride height from the last wheel it builds. NFM World
        // cars can list extra zero-size wheels last, which drops the car until
        // its real wheels sink into the road; use the lowest real wheel instead.
        const real = parseWheels(car.text);
        if (real.length) c.grat = Math.max(...real.map((w) => Math.trunc(w.y + 13 * w.height)));
        this.base[slot] = c;
        this.baseText[slot] = car.text;
        this.cd.loadstat(car.text, car.name, c.maxR, c.roofat, c.wh, slot);
        if (this.cd.names[slot] === '') { this.base[slot] = null; continue; }
        this.applyDirectPhysics(car.text, slot);
        this.carList.push({ slot, name: this.cd.names[slot], custom: true });
        slot++;
      } catch (e) {
        m.loadnew = false;
        console.warn('Custom car failed', car.file, e);
      }
    }
  }

  // Cars from NFM World list their tuned physics directly (swits(...), grip(...)
  // ...); when present these override the values derived from stat()/physics().
  applyDirectPhysics(text, slot) {
    const cd = this.cd;
    const num = (key) => {
      const m = new RegExp(`^\\s*${key}\\(([^)]*)\\)`, 'm').exec(text);
      return m ? m[1].split(',').map(Number) : null;
    };
    const set = (key, arr, conv = (v) => v) => {
      const v = num(key);
      if (v && v.every((x) => Number.isFinite(x))) arr[slot] = conv(v[0]);
    };
    const sw = num('swits');
    if (sw && sw.length >= 3) for (let i = 0; i < 3; i++) cd.swits[slot][i] = Math.trunc(sw[i]);
    const ac = num('acelf');
    if (ac && ac.length >= 3) for (let i = 0; i < 3; i++) cd.acelf[slot][i] = Math.fround(ac[i]);
    const int = (v) => Math.trunc(v);
    const flt = (v) => Math.fround(v);
    set('handb', cd.handb, int); set('airs', cd.airs, flt); set('airc', cd.airc, int);
    set('turn', cd.turn, int); set('grip', cd.grip, flt); set('bounce', cd.bounce, flt);
    set('moment', cd.moment, flt); set('comprad', cd.comprad, flt); set('push', cd.push, int);
    set('revpush', cd.revpush, int); set('lift', cd.lift, int); set('revlift', cd.revlift, int);
    set('powerloss', cd.powerloss, int); set('clrad', cd.clrad, int); set('dammult', cd.dammult, flt);
    set('maxmag', cd.maxmag, int); set('dishandle', cd.dishandle, flt); set('outdam', cd.outdam, flt);
  }

  // Port of GameSparker.loadstage (offline). Returns the stage description.
  loadStage(stage, nplayers) {
    const m = this.medium;
    const t = this.trackers;
    const cp = new CheckPoints();
    cp.stage = stage.id;
    cp.name = stage.name;
    t.nt = 0;
    m.resdown = 0; m.rescnt = 5; m.lightson = false; m.noelec = 0; m.ground = 250; m.trk = 0;
    m.snap[0] = m.snap[1] = m.snap[2] = 0;
    const objects = []; // { model: slot, conto, kind }
    const env = { sky: [170, 220, 255], fog: [255, 220, 220], ground: [205, 200, 200], polys: null, clouds: [210, 210, 210, 1, -1000], texture: [0, 0, 0, 50], fadefrom: 5000, density: 5, mountains: 0, lightson: false, soundtrack: null, nlaps: 3 };
    let maxr = 0, maxl = 100, maxt = 0, maxb = 100;
    const walls = [];

    for (const raw of stage.text.split(/\r\n|\n|\r/)) {
      const line = raw.trim();
      try {
        if (line.startsWith('snap')) m.setsnap(getint('snap', line, 0), getint('snap', line, 1), getint('snap', line, 2));
        if (line.startsWith('sky')) { env.sky = [0, 1, 2].map((i) => getint('sky', line, i)); m.setsky(...env.sky); }
        if (line.startsWith('ground')) { env.ground = [0, 1, 2].map((i) => getint('ground', line, i)); m.setgrnd(...env.ground); }
        if (line.startsWith('polys')) { env.polys = [0, 1, 2].map((i) => getint('polys', line, i)); m.setpolys(...env.polys); }
        if (line.startsWith('fog')) { env.fog = [0, 1, 2].map((i) => getint('fog', line, i)); m.setfade(...env.fog); }
        if (line.startsWith('texture')) { env.texture = [0, 1, 2, 3].map((i) => getint('texture', line, i)); m.setexture(...env.texture); }
        if (line.startsWith('clouds')) { env.clouds = [0, 1, 2, 3, 4].map((i) => getint('clouds', line, i)); m.setcloads(...env.clouds); }
        if (line.startsWith('density')) env.density = getint('density', line, 0);
        if (line.startsWith('fadefrom')) { env.fadefrom = getint('fadefrom', line, 0); m.fadfrom(env.fadefrom); }
        if (line.startsWith('lightson')) { m.lightson = true; env.lightson = true; }
        if (line.startsWith('mountains')) env.mountains = getint('mountains', line, 0);
        if (line.startsWith('set')) {
          let id = getint('set', line, 0);
          if (nplayers === 8) { if (id === 47) id = 76; if (id === 48) id = 77; }
          if (id >= 65 && id <= 75 && cp.notb) continue;
          const slot = id + 46;
          const base = this.base[slot];
          if (!base) continue;
          const x = getint('set', line, 1), z = getint('set', line, 2), rot = getint('set', line, 3);
          const o = ContO.create1(base, x, m.ground - base.grat, z, rot);
          objects.push({ slot, conto: o, rot });
          if (line.indexOf(')p') !== -1) {
            cp.x[cp.n] = x; cp.z[cp.n] = z; cp.y[cp.n] = 0; cp.typ[cp.n] = 0;
            if (line.indexOf(')pt') !== -1) cp.typ[cp.n] = -1;
            if (line.indexOf(')pr') !== -1) cp.typ[cp.n] = -2;
            if (line.indexOf(')po') !== -1) cp.typ[cp.n] = -3;
            if (line.indexOf(')ph') !== -1) cp.typ[cp.n] = -4;
            cp.n++;
          }
        }
        if (line.startsWith('chk')) {
          const slot = getint('chk', line, 0) + 46;
          const base = this.base[slot];
          let y = m.ground - base.grat;
          if (slot === 110) y = getint('chk', line, 4);
          const x = getint('chk', line, 1), z = getint('chk', line, 2), rot = getint('chk', line, 3);
          const o = ContO.create1(base, x, y, z, rot);
          cp.x[cp.n] = x; cp.z[cp.n] = z; cp.y[cp.n] = y;
          cp.typ[cp.n] = rot === 0 ? 1 : 2;
          cp.pcs = cp.n++;
          o.checkpoint = cp.nsp + 1;
          cp.nsp++;
          objects.push({ slot, conto: o, rot, checkpoint: o.checkpoint });
        }
        if (cp.nfix !== 5 && line.startsWith('fix')) {
          const slot = getint('fix', line, 0) + 46;
          const x = getint('fix', line, 1), z = getint('fix', line, 2), y = getint('fix', line, 3), rot = getint('fix', line, 4);
          const o = ContO.create1(this.base[slot], x, y, z, rot);
          cp.fx[cp.fn] = x; cp.fz[cp.fn] = z; cp.fy[cp.fn] = y;
          o.elec = true;
          cp.roted[cp.fn] = rot !== 0;
          o.roted = rot !== 0;
          cp.special[cp.fn] = line.indexOf(')s') !== -1;
          cp.fn++;
          objects.push({ slot, conto: o, rot, fix: true });
        }
        if (!cp.notb && line.startsWith('pile')) {
          const o = ContO.create2(getint('pile', line, 0), getint('pile', line, 1), getint('pile', line, 2), m, t, getint('pile', line, 3), getint('pile', line, 4), m.ground);
          objects.push({ slot: -1, conto: o, rot: 0, pile: true });
        }
        if (line.startsWith('nlaps')) cp.nlaps = Math.min(15, Math.max(1, getint('nlaps', line, 0)));
        if (line.startsWith('soundtrack')) {
          // original: soundtrack(file.mod,vol,size); NFM World: soundtrack(custom,file.mod)
          const args = (/^soundtrack\(([^)]*)\)/.exec(line)?.[1] || '').split(',').map((s) => s.trim());
          env.soundtrack = args[0] === 'custom' ? args[1] || null : args[0] || null;
        }
        if (line.startsWith('maxr') || line.startsWith('maxl') || line.startsWith('maxt') || line.startsWith('maxb')) {
          const key = line.slice(0, 4);
          const count = getint(key, line, 0), pos = getint(key, line, 1), start = getint(key, line, 2);
          const wall = this.base[85];
          const wy = m.ground - wall.grat;
          const rotFor = { maxr: 0, maxl: 180, maxt: 90, maxb: -90 }[key];
          for (let i = 0; i < count; i++) {
            const along = i * 4800 + start;
            const o = (key === 'maxr' || key === 'maxl') ? ContO.create1(wall, pos, wy, along, rotFor) : ContO.create1(wall, along, wy, pos, rotFor);
            objects.push({ slot: 85, conto: o, rot: rotFor, wall: true });
          }
          const n = t.nt;
          t.y[n] = -5000; t.rady[n] = 7100; t.dam[n] = 167; t.decor[n] = false; t.skd[n] = 0;
          if (key === 'maxr') { maxr = pos; t.x[n] = pos + 500; t.radx[n] = 600; t.z[n] = count * 4800 / 2 + start - 2400; t.radz[n] = count * 4800 / 2; t.xy[n] = 90; t.zy[n] = 0; }
          if (key === 'maxl') { maxl = pos; t.x[n] = pos - 500; t.radx[n] = 600; t.z[n] = count * 4800 / 2 + start - 2400; t.radz[n] = count * 4800 / 2; t.xy[n] = -90; t.zy[n] = 0; }
          if (key === 'maxt') { maxt = pos; t.z[n] = pos + 500; t.radz[n] = 600; t.x[n] = count * 4800 / 2 + start - 2400; t.radx[n] = count * 4800 / 2; t.zy[n] = 90; t.xy[n] = 0; }
          if (key === 'maxb') { maxb = pos; t.z[n] = pos - 500; t.radz[n] = 600; t.x[n] = count * 4800 / 2 + start - 2400; t.radx[n] = count * 4800 / 2; t.zy[n] = -90; t.xy[n] = 0; }
          t.nt++;
          walls.push({ key, count, pos, start });
        }
      } catch (e) {
        console.warn('Stage line skipped:', line, e.message);
      }
    }
    t.devidetrackers(maxl, maxr - maxl, maxb, maxt - maxb);
    cp.calprox();
    if (cp.nlaps < 1) cp.nlaps = 3;
    env.nlaps = cp.nlaps;
    let bounds = { minx: maxl, maxx: maxr, minz: maxb, maxz: maxt };
    if (!(maxr > maxl && maxt > maxb) && objects.length) {
      // No boundary walls: size the (visual) track area from the pieces themselves
      // so the terrain's hills stay clear of the course.
      let x0 = Infinity, x1 = -Infinity, z0 = Infinity, z1 = -Infinity;
      for (const o of objects) {
        x0 = Math.min(x0, o.conto.x); x1 = Math.max(x1, o.conto.x);
        z0 = Math.min(z0, o.conto.z); z1 = Math.max(z1, o.conto.z);
      }
      const pad = 6000;
      bounds = { minx: x0 - pad, maxx: x1 + pad, minz: z0 - pad, maxz: z1 + pad };
    }
    return { cp, objects, env, bounds, walls };
  }
}
