// Minimal Java runtime pieces needed by the translated game logic (src/gen/*).

export class Color {
  constructor(r, g, b) {
    this.r = r & 255;
    this.g = g & 255;
    this.b = b & 255;
  }
  getRed() { return this.r; }
  getGreen() { return this.g; }
  getBlue() { return this.b; }

  // java.awt.Color.HSBtoRGB
  static getHSBColor(hue, saturation, brightness) {
    let r = 0, g = 0, b = 0;
    if (saturation === 0) {
      r = g = b = Math.trunc(brightness * 255 + 0.5);
    } else {
      const h = (hue - Math.floor(hue)) * 6;
      const f = h - Math.floor(h);
      const p = brightness * (1 - saturation);
      const q = brightness * (1 - saturation * f);
      const t = brightness * (1 - saturation * (1 - f));
      switch (Math.trunc(h)) {
        case 0: r = brightness * 255 + 0.5; g = t * 255 + 0.5; b = p * 255 + 0.5; break;
        case 1: r = q * 255 + 0.5; g = brightness * 255 + 0.5; b = p * 255 + 0.5; break;
        case 2: r = p * 255 + 0.5; g = brightness * 255 + 0.5; b = t * 255 + 0.5; break;
        case 3: r = p * 255 + 0.5; g = q * 255 + 0.5; b = brightness * 255 + 0.5; break;
        case 4: r = t * 255 + 0.5; g = p * 255 + 0.5; b = brightness * 255 + 0.5; break;
        case 5: r = brightness * 255 + 0.5; g = p * 255 + 0.5; b = q * 255 + 0.5; break;
        default:
      }
      r = Math.trunc(r); g = Math.trunc(g); b = Math.trunc(b);
    }
    return new Color(r, g, b);
  }

  // java.awt.Color.RGBtoHSB
  static RGBtoHSB(r, g, b, hsbvals) {
    const out = hsbvals || new Float32Array(3);
    const cmax = Math.max(r, g, b);
    const cmin = Math.min(r, g, b);
    const brightness = cmax / 255;
    const saturation = cmax !== 0 ? (cmax - cmin) / cmax : 0;
    let hue;
    if (saturation === 0) hue = 0;
    else {
      const redc = (cmax - r) / (cmax - cmin);
      const greenc = (cmax - g) / (cmax - cmin);
      const bluec = (cmax - b) / (cmax - cmin);
      if (r === cmax) hue = bluec - greenc;
      else if (g === cmax) hue = 2 + redc - bluec;
      else hue = 4 + greenc - redc;
      hue /= 6;
      if (hue < 0) hue += 1;
    }
    out[0] = hue;
    out[1] = saturation;
    out[2] = brightness;
    return out;
  }
}

// java.util.Random (48-bit LCG), exact.
export class JRandom {
  constructor(seed) {
    this.seed = (BigInt(Math.trunc(seed)) ^ 0x5DEECE66Dn) & ((1n << 48n) - 1n);
  }
  next(bits) {
    this.seed = (this.seed * 0x5DEECE66Dn + 0xBn) & ((1n << 48n) - 1n);
    // (int)(seed >>> (48 - bits)): only a full 32-bit draw can come out negative.
    const r = Number(this.seed >> BigInt(48 - bits));
    return bits >= 32 ? r | 0 : r;
  }
  nextDouble() {
    const hi = this.next(26) >>> 0;
    const lo = this.next(27) >>> 0;
    return (hi * 2 ** 27 + lo) * 2 ** -53;
  }
  nextFloat() { return (this.next(24) >>> 0) / (1 << 24); }
  nextInt(bound) {
    if (bound === undefined) return this.next(32);
    if ((bound & -bound) === bound) return Number((BigInt(bound) * BigInt(this.next(31))) >> 31n);
    let bits, val;
    do { bits = this.next(31); val = bits % bound; } while (bits - val + (bound - 1) < 0);
    return val;
  }
}

// DataInputStream over text (ContO / CarDefine read .rad files line by line).
export class DataInputStream {
  constructor(src) {
    const text = typeof src === 'string' ? src : new TextDecoder('latin1').decode(src);
    this.lines = text.split(/\r\n|\n|\r/);
    if (this.lines.length && this.lines[this.lines.length - 1] === '') this.lines.pop();
    this.i = 0;
  }
  readLine() { return this.i < this.lines.length ? this.lines[this.i++] : null; }
  close() {}
}

function makeArray(type, n) {
  switch (type) {
    case 'int': case 'short': case 'byte': case 'char': return new Int32Array(n);
    case 'float': return new Float32Array(n);
    case 'double': return new Float64Array(n);
    case 'boolean': return new Array(n).fill(false);
    default: return new Array(n).fill(null);
  }
}

// new T[d0][d1]...[] (extra = number of trailing unsized dimensions)
export function jArray(type, dims, extra) {
  const build = (level) => {
    const n = dims[level];
    if (level === dims.length - 1) return extra > 0 ? new Array(n).fill(null) : makeArray(type, n);
    const a = new Array(n);
    for (let i = 0; i < n; i++) a[i] = build(level + 1);
    return a;
  };
  return build(0);
}

export function jParseInt(s) {
  if (typeof s !== 'string' || !/^[+-]?\d+$/.test(s)) throw new Error('NumberFormatException: ' + s);
  return parseInt(s, 10) | 0;
}

export function jParseFloat(s) {
  const t = String(s).trim();
  if (!/^[+-]?(\d+\.?\d*|\.\d+)([eE][+-]?\d+)?[fFdD]?$/.test(t)) throw new Error('NumberFormatException: ' + s);
  return Math.fround(parseFloat(t));
}

// Java (int) narrowing of a float/double: truncate toward zero, saturate at the
// int range, NaN -> 0. (JS `x | 0` would wrap around instead of saturating.)
export function jI(x) {
  if (x >= 2147483647) return 2147483647;
  if (x <= -2147483648) return -2147483648;
  return x | 0;
}
