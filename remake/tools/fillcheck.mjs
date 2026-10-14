// Compares the original's even-odd polygon fill with our triangulation.
import fs from 'node:fs'; import path from 'node:path'; import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js';
import { buildConto } from '../src/render/meshbuild.js';
const GAME = path.resolve('..'); const L = new TextDecoder('latin1');
const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.join(GAME, 'data/models.zip'))))) assets.models[k] = L.decode(v);
for (const f of fs.readdirSync(path.join(GAME, 'mycars')).filter((f) => f.endsWith('.rad'))) assets.customCars.push({ file: f, name: f.slice(0, -4), text: L.decode(fs.readFileSync(path.join(GAME, 'mycars', f))) });
const world = new World(assets);
const only = process.argv[2];
for (const car of world.carList) {
  if (only && car.name !== only) continue;
  const c = world.base[car.slot];
  const parts = buildConto(c, { car: true });
  const trisByPlane = new Map();
  for (const p of Object.values(parts)) {
    const idx = p.geometry.index.array;
    for (let t = 0; t < idx.length; t += 3) {
      const pl = p.src[idx[t] * 2];
      if (!trisByPlane.has(pl)) trisByPlane.set(pl, []);
      trisByPlane.get(pl).push([p.src[idx[t] * 2 + 1], p.src[idx[t + 1] * 2 + 1], p.src[idx[t + 2] * 2 + 1]]);
    }
  }
  const bad = [];
  for (let i = 0; i < c.npl; i++) {
    const p = c.p[i];
    if (!p || p.n < 3 || p.wz !== 0) continue;
    let nx = 0, ny = 0, nz = 0;
    for (let k = 0; k < p.n; k++) { const j = (k + 1) % p.n; nx += (p.oy[k] - p.oy[j]) * (p.oz[k] + p.oz[j]); ny += (p.oz[k] - p.oz[j]) * (p.ox[k] + p.ox[j]); nz += (p.ox[k] - p.ox[j]) * (p.oy[k] + p.oy[j]); }
    const ax = Math.abs(nx), ay = Math.abs(ny), az = Math.abs(nz);
    const P = (k) => (ax >= ay && ax >= az ? [p.oy[k], p.oz[k]] : ay >= az ? [p.oz[k], p.ox[k]] : [p.ox[k], p.oy[k]]);
    const pts = Array.from({ length: p.n }, (_, k) => P(k));
    const evenOdd = (x, y) => { let inside = false; for (let k = 0, j = p.n - 1; k < p.n; j = k++) { const [xi, yi] = pts[k], [xj, yj] = pts[j]; if ((yi > y) !== (yj > y) && x < ((xj - xi) * (y - yi)) / (yj - yi) + xi) inside = !inside; } return inside; };
    const inTri = (x, y, [a, b, cc]) => { const [x1, y1] = pts[a], [x2, y2] = pts[b], [x3, y3] = pts[cc]; const d = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1); if (Math.abs(d) < 1e-9) return false; const u = ((x - x1) * (y3 - y1) - (x3 - x1) * (y - y1)) / d, v = ((x2 - x1) * (y - y1) - (x - x1) * (y2 - y1)) / d; return u >= 0 && v >= 0 && u + v <= 1; };
    const tris = trisByPlane.get(i) || [];
    let minx = Infinity, maxx = -Infinity, miny = Infinity, maxy = -Infinity;
    for (const [x, y] of pts) { minx = Math.min(minx, x); maxx = Math.max(maxx, x); miny = Math.min(miny, y); maxy = Math.max(maxy, y); }
    let orig = 0, missing = 0, extra = 0;
    const N = 40;
    for (let a = 0; a < N; a++) for (let b = 0; b < N; b++) {
      const x = minx + ((a + 0.5) / N) * (maxx - minx), y = miny + ((b + 0.5) / N) * (maxy - miny);
      const o = evenOdd(x, y), m = tris.some((t) => inTri(x, y, t));
      if (o) orig++;
      if (o && !m) missing++;
      if (!o && m) extra++;
    }
    if (orig && (missing / orig > 0.05 || extra / orig > 0.05)) bad.push(`p${i} n=${p.n} col=${Array.from(p.c).join('/')} missing=${(100 * missing / orig).toFixed(0)}% extra=${(100 * extra / orig).toFixed(0)}%`);
  }
  if (bad.length) console.log(car.name + ':\n  ' + bad.join('\n  '));
}
