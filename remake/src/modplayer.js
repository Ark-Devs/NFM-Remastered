// ProTracker MOD player running in an AudioWorklet (the original game played
// its soundtracks with IBXM). Interpolated mixing with soft Amiga-style stereo.

const PROCESSOR = /* js */ `
const SINE = new Float32Array(64).map((_, i) => Math.sin((i / 64) * Math.PI * 2));
const PAL = 7093789.2;

class ModProcessor extends AudioWorkletProcessor {
  constructor() {
    super();
    this.mod = null;
    this.port.onmessage = (e) => { if (e.data.mod) this.load(e.data.mod); if (e.data.stop) this.mod = null; };
  }

  load(b) {
    const u8 = new Uint8Array(b);
    const sig = String.fromCharCode(u8[1080], u8[1081], u8[1082], u8[1083]);
    let nch = 4, nsamp = 31;
    if (/^\\dCHN$/.test(sig)) nch = +sig[0];
    else if (/^\\d\\dCH$/.test(sig)) nch = +sig.slice(0, 2);
    else if (sig === 'FLT8' || sig === 'OCTA') nch = 8;
    else if (!/^(M\\.K\\.|M!K!|FLT4|4CHN)$/.test(sig)) nsamp = 15;
    const word = (o) => (u8[o] << 8) | u8[o + 1];
    const samples = [null];
    let off = 20;
    for (let i = 0; i < nsamp; i++) {
      let ft = u8[off + 24] & 15; if (ft > 7) ft -= 16;
      samples.push({ len: word(off + 22) * 2, finetune: ft, vol: Math.min(64, u8[off + 25]), ls: word(off + 26) * 2, ll: word(off + 28) * 2, data: null });
      off += 30;
    }
    const songLen = u8[off];
    const orders = Array.from(u8.subarray(off + 2, off + 2 + 128));
    off += 2 + 128 + (nsamp === 31 ? 4 : 0);
    let npat = 0;
    for (let i = 0; i < 128; i++) npat = Math.max(npat, orders[i] + 1);
    const patterns = [];
    for (let p = 0; p < npat; p++) {
      const rows = [];
      for (let r = 0; r < 64; r++) {
        const row = [];
        for (let c = 0; c < nch; c++) {
          const o = off + ((p * 64 + r) * nch + c) * 4;
          const b0 = u8[o] || 0, b1 = u8[o + 1] || 0, b2 = u8[o + 2] || 0, b3 = u8[o + 3] || 0;
          row.push({ period: ((b0 & 15) << 8) | b1, sample: (b0 & 0xf0) | (b2 >> 4), fx: b2 & 15, param: b3 });
        }
        rows.push(row);
      }
      patterns.push(rows);
    }
    off += npat * 64 * nch * 4;
    for (let i = 1; i <= nsamp; i++) {
      const s = samples[i];
      const d = new Float32Array(s.len);
      for (let k = 0; k < s.len && off + k < u8.length; k++) d[k] = ((u8[off + k] << 24) >> 24) / 128;
      off += s.len;
      s.data = d;
      if (s.ll <= 2) s.ll = 0;
      if (s.ls + s.ll > s.len) s.ll = Math.max(0, s.len - s.ls);
    }
    this.mod = { nch, samples, songLen: Math.max(1, songLen), orders, patterns };
    this.ch = Array.from({ length: nch }, (_, i) => ({
      s: null, pos: 0, inc: 0, period: 0, vol: 0, target: 0, portaSpeed: 0, vibSpeed: 0, vibDepth: 0, vibPos: 0,
      tremSpeed: 0, tremDepth: 0, tremPos: 0, finetune: 0, loopRow: 0, loopCount: 0, note: null, offMem: 0, outVol: 0, outPeriod: 0,
      pan: (i % 4 === 0 || i % 4 === 3) ? 0.25 : 0.75,
    }));
    this.order = 0; this.row = 0; this.tick = 0; this.speed = 6; this.tempo = 125;
    this.tickLeft = 0; this.patDelay = 0; this.jump = -1; this.brk = -1;
  }

  fine(period, ft) { return ft ? period * Math.pow(2, -ft / 96) : period; }

  setInc(c, period) {
    c.outPeriod = period;
    c.inc = period > 0 ? PAL / (period * 2) / sampleRate : 0;
  }

  processRow() {
    const m = this.mod;
    const pat = m.patterns[m.orders[this.order]];
    if (!pat) { this.order = 0; this.row = 0; return; }
    const row = pat[this.row];
    for (let i = 0; i < m.nch; i++) {
      const c = this.ch[i];
      const n = row[i];
      c.note = n;
      if (n.sample > 0 && m.samples[n.sample]) {
        const s = m.samples[n.sample];
        c.s = s; c.vol = s.vol; c.finetune = s.finetune;
      }
      if (n.fx === 0xe && (n.param >> 4) === 5) c.finetune = (n.param & 15) > 7 ? (n.param & 15) - 16 : n.param & 15;
      if (n.period > 0) {
        const p = this.fine(n.period, c.finetune);
        if (n.fx === 3 || n.fx === 5) c.target = p;
        else if (!(n.fx === 0xe && (n.param >> 4) === 0xd)) {
          c.period = p; c.pos = 0; c.vibPos = 0;
          if (n.fx === 9) { if (n.param) c.offMem = n.param * 256; c.pos = c.offMem; }
        }
      }
      this.rowEffect(c, n);
      c.outVol = c.vol;
      this.setInc(c, c.period);
    }
  }

  rowEffect(c, n) {
    const x = n.param >> 4, y = n.param & 15;
    switch (n.fx) {
      case 3: if (n.param) c.portaSpeed = n.param; break;
      case 4: if (x) c.vibSpeed = x; if (y) c.vibDepth = y; break;
      case 7: if (x) c.tremSpeed = x; if (y) c.tremDepth = y; break;
      case 0xb: this.jump = n.param; break;
      case 0xc: c.vol = Math.min(64, n.param); break;
      case 0xd: this.brk = x * 10 + y; break;
      case 0xe:
        if (x === 1) c.period = Math.max(113, c.period - y);
        if (x === 2) c.period = Math.min(856 * 2, c.period + y);
        if (x === 6) {
          if (y === 0) c.loopRow = this.row;
          else if (c.loopCount === 0) { c.loopCount = y; this.brk = c.loopRow; this.loopJump = true; }
          else if (--c.loopCount > 0) { this.brk = c.loopRow; this.loopJump = true; }
        }
        if (x === 0xa) c.vol = Math.min(64, c.vol + y);
        if (x === 0xb) c.vol = Math.max(0, c.vol - y);
        if (x === 0xe) this.patDelay = y;
        break;
      case 0xf:
        if (n.param === 0) break;
        if (n.param < 32) this.speed = n.param; else this.tempo = n.param;
        break;
      default:
    }
  }

  tickEffects() {
    const m = this.mod;
    for (let i = 0; i < m.nch; i++) {
      const c = this.ch[i];
      const n = c.note;
      if (!n) continue;
      const x = n.param >> 4, y = n.param & 15;
      let period = c.period;
      switch (n.fx) {
        case 0: if (n.param) { const k = this.tick % 3; const semis = k === 0 ? 0 : k === 1 ? x : y; period = c.period * Math.pow(2, -semis / 12); } break;
        case 1: c.period = Math.max(113, c.period - n.param); period = c.period; break;
        case 2: c.period = Math.min(1712, c.period + n.param); period = c.period; break;
        case 3: case 5:
          if (c.target) {
            if (c.period < c.target) c.period = Math.min(c.target, c.period + c.portaSpeed);
            else c.period = Math.max(c.target, c.period - c.portaSpeed);
          }
          period = c.period;
          if (n.fx === 5) this.volSlide(c, x, y);
          break;
        case 4: case 6:
          c.vibPos = (c.vibPos + c.vibSpeed) & 63;
          period = c.period + (SINE[c.vibPos] * c.vibDepth * 2);
          if (n.fx === 6) this.volSlide(c, x, y);
          break;
        case 7:
          c.tremPos = (c.tremPos + c.tremSpeed) & 63;
          c.outVol = Math.max(0, Math.min(64, c.vol + SINE[c.tremPos] * c.tremDepth * 4));
          break;
        case 0xa: this.volSlide(c, x, y); break;
        case 0xe:
          if (x === 9 && y && this.tick % y === 0) c.pos = 0;
          if (x === 0xc && this.tick === y) c.vol = 0;
          if (x === 0xd && this.tick === y && n.period) { c.period = this.fine(n.period, c.finetune); c.pos = 0; period = c.period; }
          break;
        default:
      }
      if (n.fx !== 7) c.outVol = c.vol;
      this.setInc(c, period);
    }
  }

  volSlide(c, x, y) { c.vol = x ? Math.min(64, c.vol + x) : Math.max(0, c.vol - y); }

  advanceRow() {
    if (this.patDelay > 0) { this.patDelay--; return; }
    if (this.jump >= 0 || this.brk >= 0) {
      if (!this.loopJump) this.order = this.jump >= 0 ? this.jump : this.order + 1;
      this.row = this.brk >= 0 ? this.brk : 0;
      this.jump = -1; this.brk = -1; this.loopJump = false;
    } else if (++this.row >= 64) { this.row = 0; this.order++; }
    if (this.order >= this.mod.songLen) this.order = 0;
  }

  process(_, outputs) {
    const out = outputs[0];
    const L = out[0], R = out[1] || out[0];
    if (!this.mod) { L.fill(0); if (R !== L) R.fill(0); return true; }
    const m = this.mod;
    for (let i = 0; i < L.length; i++) {
      if (this.tickLeft <= 0) {
        if (this.tick === 0) this.processRow(); else this.tickEffects();
        if (++this.tick >= this.speed) { this.tick = 0; this.advanceRow(); }
        this.tickLeft = (sampleRate * 2.5) / this.tempo;
      }
      this.tickLeft--;
      let l = 0, r = 0;
      for (let k = 0; k < m.nch; k++) {
        const c = this.ch[k];
        const s = c.s;
        if (!s || !s.data || c.inc === 0) continue;
        const end = s.ll ? s.ls + s.ll : s.len;
        if (c.pos >= end) {
          if (s.ll) c.pos = s.ls + ((c.pos - s.ls) % s.ll); else continue;
        }
        const ip = c.pos | 0, f = c.pos - ip;
        let nx = ip + 1;
        if (nx >= end) nx = s.ll ? s.ls : ip;
        const v = (s.data[ip] * (1 - f) + s.data[nx] * f) * (c.outVol / 64);
        l += v * (1 - c.pan); r += v * c.pan;
        c.pos += c.inc;
      }
      L[i] = Math.tanh(l * 0.55);
      if (R !== L) R[i] = Math.tanh(r * 0.55);
    }
    return true;
  }
}
registerProcessor('nfm-mod', ModProcessor);
`;

let moduleUrl = null;

export class ModPlayer {
  static async create(ctx, bytes) {
    if (!moduleUrl) {
      moduleUrl = URL.createObjectURL(new Blob([PROCESSOR], { type: 'application/javascript' }));
    }
    if (!ctx.__nfmModLoaded) {
      await ctx.audioWorklet.addModule(moduleUrl);
      ctx.__nfmModLoaded = true;
    }
    const p = new ModPlayer();
    p.node = new AudioWorkletNode(ctx, 'nfm-mod', { outputChannelCount: [2] });
    const copy = bytes.buffer.slice(bytes.byteOffset, bytes.byteOffset + bytes.byteLength);
    p.node.port.postMessage({ mod: copy }, [copy]);
    return p;
  }
  connect(dest) { this.node.connect(dest); }
  start() {}
  stop() {
    try { this.node.port.postMessage({ stop: true }); this.node.disconnect(); } catch { /* ignore */ }
  }
}
