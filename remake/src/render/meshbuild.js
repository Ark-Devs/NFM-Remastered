// Turns ContO polygon models into three.js geometry.
import * as THREE from 'three';
import { S, srgbToLinear, carColorLinear } from './space.js';
import { subdivide, evalStencils } from './subdivide.js';

const _v2 = [];
// Triangulate one polygon (NFM local coords). Returns triangles as index triples.
function triangulate(xs, ys, zs, n) {
  if (n < 3) return [];
  if (n === 3) return [[0, 1, 2]];
  // Newell normal to choose a projection plane
  let nx = 0, ny = 0, nz = 0;
  for (let i = 0; i < n; i++) {
    const j = (i + 1) % n;
    nx += (ys[i] - ys[j]) * (zs[i] + zs[j]);
    ny += (zs[i] - zs[j]) * (xs[i] + xs[j]);
    nz += (xs[i] - xs[j]) * (ys[i] + ys[j]);
  }
  const ax = Math.abs(nx), ay = Math.abs(ny), az = Math.abs(nz);
  const pts = [];
  for (let i = 0; i < n; i++) {
    if (ax >= ay && ax >= az) pts.push(new THREE.Vector2(ys[i], zs[i]));
    else if (ay >= az) pts.push(new THREE.Vector2(zs[i], xs[i]));
    else pts.push(new THREE.Vector2(xs[i], ys[i]));
  }
  let tris = [];
  try {
    tris = THREE.ShapeUtils.triangulateShape(pts, []);
  } catch {
    tris = [];
  }
  // Outlines with zero-width spikes (cut-outs around lights, vents) legitimately
  // give fewer than n-2 triangles; only fall back to a fan if earcut failed outright.
  if (!tris.length) {
    for (let i = 1; i < n - 1; i++) tris.push([0, i, i + 1]);
  }
  void _v2;
  return tris;
}

const LIGHT_FRONT = 1, LIGHT_BACK = 2;

function classifyCarPlane(p) {
  if (p.glass === 1) return 'glass';
  if (p.light === LIGHT_FRONT || p.light === LIGHT_BACK) return p.light === LIGHT_FRONT ? 'lightF' : 'lightB';
  return 'body';
}

// Surface parameters for car polygons: [roughness, metalness, clearcoat]
function carSurface(p) {
  if (p.colnum === 1 || p.colnum === 2) return [0.42, 0.22, 0.55]; // metallic paint
  const [r, g, b] = p.c;
  const lum = (r * 0.3 + g * 0.59 + b * 0.11) / 255;
  if (lum < 0.13) return [0.85, 0.0, 0.0]; // black plastic / rubber trim
  if (Math.abs(r - g) < 12 && Math.abs(g - b) < 12 && lum > 0.55) return [0.35, 0.7, 0.1]; // brushed metal greys
  return [0.55, 0.1, 0.3]; // painted panels
}

// Stage pieces: [roughness, metalness, kind]
// kind: 0 painted, 1 asphalt, 2 rock/dirt, 3 foliage, 4 metal, 5 road paint, 6 concrete
function stageSurface(p, modelName) {
  if (/^tree|^cac/.test(modelName)) return [0.85, 0, 3];
  if (p.glass === 2 || /pile|offroad|offhill|offbump|offturn|hpground|offramp|sofframp|offcheck/.test(modelName)) return [0.95, 0, 2];
  const [r, g, b] = p.c;
  const grey = Math.abs(r - g) < 12 && Math.abs(g - b) < 12;
  if (p.road) {
    // light lines and orange kerb markers painted onto the tarmac
    if ((grey && r > 165) || (r > 180 && g > 110 && g < 170 && b < 60)) return [0.6, 0, 5];
    return [0.85, 0, 1];
  }
  if (/rail|fence|pipe|tube|crane|tower/.test(modelName) && (grey || r < 60)) return [0.4, 0.75, 4];
  if (grey) return [0.88, 0, 6];
  return [0.7, 0.02, 0];
}

// Track models stack markings and panels on top of larger polygons in the same
// plane; the original's painter sort drew them in order, but in a depth buffer
// they z-fight. Lift each overlapping smaller polygon a little off the bigger
// one it sits on (up for flat ones, away from the model centre otherwise).
// Returns per-plane offsets in NFM units, or null when nothing overlaps.
const DECAL_LIFT = 1.5; // NFM units (~3 cm) per layer
function decalLifts(conto) {
  const info = [];
  let cx = 0, cy = 0, cz = 0, cn = 0;
  for (let pi = 0; pi < conto.npl; pi++) {
    const p = conto.p[pi];
    if (!p || p.n < 3) { info.push(null); continue; }
    let nx = 0, ny = 0, nz = 0;
    const bb = [Infinity, -Infinity, Infinity, -Infinity, Infinity, -Infinity];
    for (let v = 0; v < p.n; v++) {
      const w = (v + 1) % p.n;
      nx += (p.oy[v] - p.oy[w]) * (p.oz[v] + p.oz[w]);
      ny += (p.oz[v] - p.oz[w]) * (p.ox[v] + p.ox[w]);
      nz += (p.ox[v] - p.ox[w]) * (p.oy[v] + p.oy[w]);
      bb[0] = Math.min(bb[0], p.ox[v]); bb[1] = Math.max(bb[1], p.ox[v]);
      bb[2] = Math.min(bb[2], p.oy[v]); bb[3] = Math.max(bb[3], p.oy[v]);
      bb[4] = Math.min(bb[4], p.oz[v]); bb[5] = Math.max(bb[5], p.oz[v]);
      cx += p.ox[v]; cy += p.oy[v]; cz += p.oz[v]; cn++;
    }
    const l = Math.hypot(nx, ny, nz);
    if (l < 1e-6) { info.push(null); continue; }
    nx /= l; ny /= l; nz /= l;
    info.push({ n: [nx, ny, nz], d: nx * p.ox[0] + ny * p.oy[0] + nz * p.oz[0], bb, area: l / 2, layer: 0 });
  }
  if (!cn) return null;
  cx /= cn; cy /= cn; cz /= cn;
  // biggest first, so smaller polygons stack on top of what they overlap
  const order = info.map((_, i) => i).filter((i) => info[i]).sort((a, b) => info[b].area - info[a].area);
  let any = false;
  for (let oi = 0; oi < order.length; oi++) {
    const A = info[order[oi]];
    for (let oj = 0; oj < oi; oj++) {
      const B = info[order[oj]];
      const dot = A.n[0] * B.n[0] + A.n[1] * B.n[1] + A.n[2] * B.n[2];
      if (Math.abs(dot) < 0.999 || Math.abs(A.d - Math.sign(dot) * B.d) > 1) continue;
      let overlap = true;
      for (let k = 0; k < 3 && overlap; k++) {
        const thin = A.n[k] * A.n[k] > 0.99; // the axis the plane is flat in
        if (Math.min(A.bb[k * 2 + 1], B.bb[k * 2 + 1]) - Math.max(A.bb[k * 2], B.bb[k * 2]) < (thin ? -1 : 1)) overlap = false;
      }
      if (overlap && B.layer + 1 > A.layer) { A.layer = B.layer + 1; any = true; }
    }
  }
  if (!any) return null;
  const lifts = new Float32Array(conto.npl * 3);
  for (let pi = 0; pi < conto.npl; pi++) {
    const A = info[pi];
    if (!A || !A.layer) continue;
    let [nx, ny, nz] = A.n;
    if (Math.abs(ny) > 0.7) { if (ny > 0) { nx = -nx; ny = -ny; nz = -nz; } } // NFM y is down
    else {
      const mx = (A.bb[0] + A.bb[1]) / 2 - cx, my = (A.bb[2] + A.bb[3]) / 2 - cy, mz = (A.bb[4] + A.bb[5]) / 2 - cz;
      if (nx * mx + ny * my + nz * mz < 0) { nx = -nx; ny = -ny; nz = -nz; }
    }
    const s = A.layer * DECAL_LIFT;
    lifts[pi * 3] = nx * s; lifts[pi * 3 + 1] = ny * s; lifts[pi * 3 + 2] = nz * s;
  }
  return lifts;
}

// Orient triangles so face normals point away from the model's centre
// (consistent winding is needed for smooth normals).
function orientTriangle(pos, i0, i1, i2, cx, cy, cz, preferUp) {
  const ax = pos[i0 * 3], ay = pos[i0 * 3 + 1], az = pos[i0 * 3 + 2];
  const e1x = pos[i1 * 3] - ax, e1y = pos[i1 * 3 + 1] - ay, e1z = pos[i1 * 3 + 2] - az;
  const e2x = pos[i2 * 3] - ax, e2y = pos[i2 * 3 + 1] - ay, e2z = pos[i2 * 3 + 2] - az;
  const nx = e1y * e2z - e1z * e2y, ny = e1z * e2x - e1x * e2z, nz = e1x * e2y - e1y * e2x;
  const len = Math.hypot(nx, ny, nz) || 1;
  if (preferUp && Math.abs(ny) / len > 0.7) return ny >= 0;
  const fx = (ax + pos[i1 * 3] + pos[i2 * 3]) / 3 - cx;
  const fy = (ay + pos[i1 * 3 + 1] + pos[i2 * 3 + 1]) / 3 - cy;
  const fz = (az + pos[i1 * 3 + 2] + pos[i2 * 3 + 2]) / 3 - cz;
  return nx * fx + ny * fy + nz * fz >= 0;
}

// Make every triangle face the same way as its neighbours (shared edges run
// in opposite directions), then flip each connected piece so it faces out,
// judged at its outermost triangle where "outward" is unambiguous. Facing by
// "away from the model centre" alone got panels wrong when tall fins or
// spoilers pulled the centre around, and a backwards panel self-shadows dark.
function orientConsistently(pos, tris) {
  const nt = tris.length / 3;
  if (!nt) return;
  const key = (i) => `${Math.round(pos[i * 3] * 1000)},${Math.round(pos[i * 3 + 1] * 1000)},${Math.round(pos[i * 3 + 2] * 1000)}`;
  const vid = new Map();
  const w = new Int32Array(tris.length);
  for (let i = 0; i < tris.length; i++) {
    const k = key(tris[i]);
    if (!vid.has(k)) vid.set(k, vid.size);
    w[i] = vid.get(k);
  }
  const edges = new Map(); // undirected edge -> [tri, ...]
  for (let t = 0; t < nt; t++) {
    for (let e = 0; e < 3; e++) {
      const a = w[t * 3 + e], b = w[t * 3 + ((e + 1) % 3)];
      if (a === b) continue;
      const ek = a < b ? `${a}_${b}` : `${b}_${a}`;
      if (!edges.has(ek)) edges.set(ek, []);
      edges.get(ek).push(t);
    }
  }
  const dirOf = (t, a, b) => { // +1 if tri t runs a->b, -1 if b->a
    for (let e = 0; e < 3; e++) {
      const x = w[t * 3 + e], y = w[t * 3 + ((e + 1) % 3)];
      if (x === a && y === b) return 1;
      if (x === b && y === a) return -1;
    }
    return 0;
  };
  const flip = (t) => {
    [tris[t * 3 + 1], tris[t * 3 + 2]] = [tris[t * 3 + 2], tris[t * 3 + 1]];
    [w[t * 3 + 1], w[t * 3 + 2]] = [w[t * 3 + 2], w[t * 3 + 1]];
  };
  const comp = new Int32Array(nt).fill(-1);
  let nc = 0;
  for (let s = 0; s < nt; s++) {
    if (comp[s] >= 0) continue;
    const members = [s];
    comp[s] = nc;
    for (let q = 0; q < members.length; q++) {
      const t = members[q];
      for (let e = 0; e < 3; e++) {
        const a = w[t * 3 + e], b = w[t * 3 + ((e + 1) % 3)];
        if (a === b) continue;
        const list = edges.get(a < b ? `${a}_${b}` : `${b}_${a}`);
        if (!list || list.length !== 2) continue; // only stitch clean 2-panel edges
        for (const u of list) {
          if (u === t || comp[u] >= 0) continue;
          if (dirOf(u, a, b) === 1) flip(u); // neighbour must run b->a
          comp[u] = nc;
          members.push(u);
        }
      }
    }
    // outward check at the triangle farthest from this piece's centre
    let cx = 0, cy = 0, cz = 0;
    for (const t of members) for (let e = 0; e < 3; e++) { const i = tris[t * 3 + e] * 3; cx += pos[i]; cy += pos[i + 1]; cz += pos[i + 2]; }
    const cnt = members.length * 3;
    cx /= cnt; cy /= cnt; cz /= cnt;
    let best = members[0], bestD = -1;
    for (const t of members) {
      let fx = 0, fy = 0, fz = 0;
      for (let e = 0; e < 3; e++) { const i = tris[t * 3 + e] * 3; fx += pos[i]; fy += pos[i + 1]; fz += pos[i + 2]; }
      const d = (fx / 3 - cx) ** 2 + (fy / 3 - cy) ** 2 + (fz / 3 - cz) ** 2;
      if (d > bestD) { bestD = d; best = t; }
    }
    const [i0, i1, i2] = [tris[best * 3] * 3, tris[best * 3 + 1] * 3, tris[best * 3 + 2] * 3];
    const e1 = [pos[i1] - pos[i0], pos[i1 + 1] - pos[i0 + 1], pos[i1 + 2] - pos[i0 + 2]];
    const e2 = [pos[i2] - pos[i0], pos[i2 + 1] - pos[i0 + 1], pos[i2 + 2] - pos[i0 + 2]];
    const n = [e1[1] * e2[2] - e1[2] * e2[1], e1[2] * e2[0] - e1[0] * e2[2], e1[0] * e2[1] - e1[1] * e2[0]];
    const f = [(pos[i0] + pos[i1] + pos[i2]) / 3 - cx, (pos[i0 + 1] + pos[i1 + 1] + pos[i2 + 1]) / 3 - cy, (pos[i0 + 2] + pos[i1 + 2] + pos[i2 + 2]) / 3 - cz];
    if (members.length > 1 && n[0] * f[0] + n[1] * f[1] + n[2] * f[2] < 0) for (const t of members) flip(t);
    nc++;
  }
}

class PartBuilder {
  constructor() {
    this.pos = []; this.col = []; this.surf = []; this.extra = []; this.src = []; // src: plane, vertex
    this.tris = [];
  }
}

// Build geometry parts from a ContO. For cars, wheel polygons are skipped (we
// draw real wheels) and a vertex->(plane,vertex) map is kept for live damage.
export function buildConto(conto, { car = false, modelName = '', smooth = 0, snap = [0, 0, 0] } = {}) {
  // stage pieces: gr(-13) polygons are the "FINISH" lettering, shown only on the
  // final checkpoint of the race, so they go in their own part
  const parts = car ? { body: new PartBuilder(), glass: new PartBuilder(), lightF: new PartBuilder(), lightB: new PartBuilder() } : { body: new PartBuilder(), finish: new PartBuilder() };
  const lin = [0, 0, 0];
  let cx = 0, cy = 0, cz = 0, cn = 0;
  for (let pi = 0; pi < conto.npl; pi++) {
    const p = conto.p[pi];
    if (!p || p.n < 3) continue;
    if (car && p.wz !== 0) continue;
    for (let v = 0; v < p.n; v++) { cx += p.ox[v]; cy += p.oy[v]; cz += p.oz[v]; cn++; }
  }
  if (cn) { cx = (cx / cn) * S; cy = -(cy / cn) * S; cz = -(cz / cn) * S; }
  const lifts = car ? null : decalLifts(conto);

  for (let pi = 0; pi < conto.npl; pi++) {
    const p = conto.p[pi];
    if (!p || p.n < 3) continue;
    if (car && p.wz !== 0) continue;
    const kind = car ? classifyCarPlane(p) : p.gr === -13 ? 'finish' : 'body';
    const part = parts[kind];
    const base = part.pos.length / 3;
    if (car) carColorLinear(p.c, snap, lin); else srgbToLinear(p.c[0], p.c[1], p.c[2], lin);
    const surf = car ? carSurface(p) : stageSurface(p, modelName);
    const lx = lifts ? lifts[pi * 3] : 0, ly = lifts ? lifts[pi * 3 + 1] : 0, lz = lifts ? lifts[pi * 3 + 2] : 0;
    for (let v = 0; v < p.n; v++) {
      part.pos.push((p.ox[v] + lx) * S, -(p.oy[v] + ly) * S, -(p.oz[v] + lz) * S);
      part.col.push(lin[0], lin[1], lin[2]);
      part.surf.push(surf[0], surf[1], surf[2]);
      part.extra.push(0);
      part.src.push(pi, v);
    }
    for (const [a, b, c] of triangulate(p.ox, p.oy, p.oz, p.n)) {
      const i0 = base + a, i1 = base + b, i2 = base + c;
      if (orientTriangle(part.pos, i0, i1, i2, cx, cy, cz, !car)) part.tris.push(i0, i1, i2);
      else part.tris.push(i0, i2, i1);
    }
  }

  const out = {};
  for (const [k, part] of Object.entries(parts)) {
    if (!part.tris.length) continue;
    if (car) orientConsistently(part.pos, part.tris);
    // lights stay crisp (their exact box shapes matter); body and glass get rounded
    if (car && smooth > 0 && (k === 'body' || k === 'glass')) { out[k] = buildSmoothPart(conto, part, k, smooth, snap); continue; }
    const g = new THREE.BufferGeometry();
    g.setAttribute('position', new THREE.Float32BufferAttribute(part.pos, 3));
    g.setAttribute('color', new THREE.Float32BufferAttribute(part.col, 3));
    g.setAttribute('surf', new THREE.Float32BufferAttribute(part.surf, 3));
    if (car) g.setAttribute(k === 'glass' ? 'crack' : 'dmg', new THREE.Float32BufferAttribute(part.extra, 1));
    g.setIndex(part.tris);
    const smoother = new CreaseNormals(g, car ? 38 : 25);
    smoother.update();
    g.computeBoundingSphere();
    out[k] = { geometry: g, src: new Int32Array(part.src), smoother };
  }
  return out;
}

// Is polygon [first, first+n) convex (and free of duplicate/spike points)?
function isConvex(pos, first, n) {
  let nx = 0, ny = 0, nz = 0;
  const P = (k) => (first + ((k + n) % n)) * 3;
  for (let k = 0; k < n; k++) {
    const a = P(k), b = P(k + 1);
    nx += (pos[a + 1] - pos[b + 1]) * (pos[a + 2] + pos[b + 2]);
    ny += (pos[a + 2] - pos[b + 2]) * (pos[a] + pos[b]);
    nz += (pos[a] - pos[b]) * (pos[a + 1] + pos[b + 1]);
  }
  const nl = Math.hypot(nx, ny, nz);
  if (nl < 1e-9) return false;
  for (let k = 0; k < n; k++) {
    const a = P(k - 1), b = P(k), c = P(k + 1);
    const e1 = [pos[b] - pos[a], pos[b + 1] - pos[a + 1], pos[b + 2] - pos[a + 2]];
    const e2 = [pos[c] - pos[b], pos[c + 1] - pos[b + 1], pos[c + 2] - pos[b + 2]];
    if (Math.hypot(...e1) < 1e-6 || Math.hypot(...e2) < 1e-6) return false;
    const cx = e1[1] * e2[2] - e1[2] * e2[1], cy = e1[2] * e2[0] - e1[0] * e2[2], cz = e1[0] * e2[1] - e1[1] * e2[0];
    if ((cx * nx + cy * ny + cz * nz) / nl < -1e-6) return false;
  }
  return true;
}

// Subdivided (rounded) version of a car part. Output vertex = (subdivided
// vertex, source polygon) so each panel keeps its own colour and damage.
function buildSmoothPart(conto, part, kind, levels, snap) {
  const src = new Int32Array(part.src);
  const look = (pi) => {
    const p = conto.p[pi];
    return `${p.c[0]},${p.c[1]},${p.c[2]}|${carSurface(p).join(',')}`;
  };
  // Re-triangulate every polygon as a fan around its centroid. Unlike an
  // ear-clipping split, this is mirror-symmetric, so a car's left and right
  // sides subdivide identically.
  const nOrig = src.length / 2;
  const tpos = part.pos.slice();
  const polyOfV = Array.from({ length: nOrig }, (_, i) => src[i * 2]);
  const centroids = []; // [first, count] ranges of original vertices
  const triOf = new Map(); // plane -> its original (earcut) triangles
  for (let t = 0; t < part.tris.length; t += 3) {
    const pl = src[part.tris[t] * 2];
    if (!triOf.has(pl)) triOf.set(pl, []);
    triOf.get(pl).push(part.tris[t], part.tris[t + 1], part.tris[t + 2]);
  }
  const tris = [];
  const nrm = (a, b, c) => {
    const e1 = [tpos[b * 3] - tpos[a * 3], tpos[b * 3 + 1] - tpos[a * 3 + 1], tpos[b * 3 + 2] - tpos[a * 3 + 2]];
    const e2 = [tpos[c * 3] - tpos[a * 3], tpos[c * 3 + 1] - tpos[a * 3 + 1], tpos[c * 3 + 2] - tpos[a * 3 + 2]];
    return [e1[1] * e2[2] - e1[2] * e2[1], e1[2] * e2[0] - e1[0] * e2[2], e1[0] * e2[1] - e1[1] * e2[0]];
  };
  for (let i = 0; i < nOrig;) {
    const pl = src[i * 2];
    let j = i;
    while (j < nOrig && src[j * 2] === pl) j++;
    const n = j - i;
    // Concave outlines (cut-outs around lights/vents) keep their exact earcut
    // split: a centre fan would fill the holes. Convex ones get the symmetric fan.
    if (n === 3 || !triOf.has(pl) || !isConvex(tpos, i, n)) {
      if (triOf.has(pl)) tris.push(...triOf.get(pl));
      i = j;
      continue;
    }
    const c = tpos.length / 3;
    let cx = 0, cy = 0, cz = 0;
    for (let k = i; k < j; k++) { cx += tpos[k * 3]; cy += tpos[k * 3 + 1]; cz += tpos[k * 3 + 2]; }
    tpos.push(cx / n, cy / n, cz / n);
    polyOfV.push(pl);
    centroids.push([i, n]);
    const ref = nrm(...triOf.get(pl).slice(0, 3));
    for (let k = 0; k < n; k++) {
      const a = i + k, b = i + ((k + 1) % n);
      const fn = nrm(c, a, b);
      if (fn[0] * ref[0] + fn[1] * ref[1] + fn[2] * ref[2] >= 0) tris.push(c, a, b);
      else tris.push(c, b, a);
    }
    i = j;
  }
  const sub = subdivide({
    pos: tpos,
    tris,
    polyOf: (i) => polyOfV[i],
    sameLook: (a, b) => look(a) === look(b),
    levels,
  });
  const subPos = evalStencils(sub, tpos, new Float64Array(sub.vertCount * 3));
  const keyMap = new Map();
  const outSub = [];
  const outPoly = [];
  const index = [];
  for (let f = 0; f < sub.faces.length / 3; f++) {
    const poly = sub.faceTag[f];
    for (let k = 0; k < 3; k++) {
      const sv = sub.faces[f * 3 + k];
      const key = sv * 4096 + poly;
      let o = keyMap.get(key);
      if (o === undefined) { o = outSub.length; keyMap.set(key, o); outSub.push(sv); outPoly.push(poly); }
      index.push(o);
    }
  }
  const n = outSub.length;
  const pos = new Float32Array(n * 3), col = new Float32Array(n * 3), surf = new Float32Array(n * 3), extra = new Float32Array(n);
  const lin = [0, 0, 0];
  for (let i = 0; i < n; i++) {
    const s = outSub[i];
    pos[i * 3] = subPos[s * 3]; pos[i * 3 + 1] = subPos[s * 3 + 1]; pos[i * 3 + 2] = subPos[s * 3 + 2];
    const p = conto.p[outPoly[i]];
    carColorLinear(p.c, snap, lin);
    col.set(lin, i * 3);
    surf.set(carSurface(p), i * 3);
  }
  const g = new THREE.BufferGeometry();
  g.setAttribute('position', new THREE.BufferAttribute(pos, 3));
  g.setAttribute('color', new THREE.BufferAttribute(col, 3));
  g.setAttribute('surf', new THREE.BufferAttribute(surf, 3));
  g.setAttribute(kind === 'glass' ? 'crack' : 'dmg', new THREE.BufferAttribute(extra, 1));
  g.setIndex(index);
  const smoother = new CreaseNormals(g, 40);
  smoother.update();
  g.computeBoundingSphere();
  return {
    geometry: g, src, smoother,
    smooth: { sub, outSub: Int32Array.from(outSub), outPoly: Int32Array.from(outPoly), memberPos: new Float64Array(tpos.length), subPos, centroids, nOrig },
  };
}

// Smooth normals across edges flatter than `creaseDeg`, kept updatable so a
// deformed (damaged) car re-shades correctly.
export class CreaseNormals {
  constructor(geometry, creaseDeg) {
    this.g = geometry;
    this.cos = Math.cos((creaseDeg * Math.PI) / 180);
    const pos = geometry.attributes.position.array;
    const idx = geometry.index.array;
    const nv = pos.length / 3;
    this.ntri = idx.length / 3;
    this.faceN = new Float32Array(this.ntri * 3);
    // group vertices that share a position
    const map = new Map();
    this.group = new Int32Array(nv);
    for (let i = 0; i < nv; i++) {
      const key = `${Math.round(pos[i * 3] * 400)},${Math.round(pos[i * 3 + 1] * 400)},${Math.round(pos[i * 3 + 2] * 400)}`;
      let gid = map.get(key);
      if (gid === undefined) { gid = map.size; map.set(key, gid); }
      this.group[i] = gid;
    }
    // triangles touching each position group
    const lists = Array.from({ length: map.size }, () => []);
    this.vertTri = new Int32Array(nv).fill(-1);
    for (let t = 0; t < this.ntri; t++) {
      for (let k = 0; k < 3; k++) {
        const v = idx[t * 3 + k];
        lists[this.group[v]].push(t);
        this.vertTri[v] = t;
      }
    }
    this.groupTris = lists;
    if (!geometry.attributes.normal) geometry.setAttribute('normal', new THREE.Float32BufferAttribute(new Float32Array(nv * 3), 3));
  }

  update() {
    const pos = this.g.attributes.position.array;
    const idx = this.g.index.array;
    const fn = this.faceN;
    for (let t = 0; t < this.ntri; t++) {
      const a = idx[t * 3] * 3, b = idx[t * 3 + 1] * 3, c = idx[t * 3 + 2] * 3;
      const e1x = pos[b] - pos[a], e1y = pos[b + 1] - pos[a + 1], e1z = pos[b + 2] - pos[a + 2];
      const e2x = pos[c] - pos[a], e2y = pos[c + 1] - pos[a + 1], e2z = pos[c + 2] - pos[a + 2];
      let nx = e1y * e2z - e1z * e2y, ny = e1z * e2x - e1x * e2z, nz = e1x * e2y - e1y * e2x;
      const l = Math.hypot(nx, ny, nz) || 1;
      fn[t * 3] = nx / l; fn[t * 3 + 1] = ny / l; fn[t * 3 + 2] = nz / l;
    }
    const nrm = this.g.attributes.normal.array;
    const nv = pos.length / 3;
    for (let v = 0; v < nv; v++) {
      const own = this.vertTri[v];
      if (own < 0) { nrm[v * 3] = 0; nrm[v * 3 + 1] = 1; nrm[v * 3 + 2] = 0; continue; }
      const ox = fn[own * 3], oy = fn[own * 3 + 1], oz = fn[own * 3 + 2];
      let sx = 0, sy = 0, sz = 0;
      for (const t of this.groupTris[this.group[v]]) {
        const tx = fn[t * 3], ty = fn[t * 3 + 1], tz = fn[t * 3 + 2];
        if (tx * ox + ty * oy + tz * oz >= this.cos) { sx += tx; sy += ty; sz += tz; }
      }
      let l = Math.hypot(sx, sy, sz);
      // degenerate polygons have no face normal: fall back so the shader never
      // normalises a zero vector (NaN pixels turned reflections black)
      if (l < 1e-6) { sx = ox; sy = oy; sz = oz; l = Math.hypot(sx, sy, sz); }
      if (l < 1e-6) { sx = 0; sy = 1; sz = 0; l = 1; }
      nrm[v * 3] = sx / l; nrm[v * 3 + 1] = sy / l; nrm[v * 3 + 2] = sz / l;
    }
    this.g.attributes.normal.needsUpdate = true;
  }
}

// Parse w(...) and rims(...) lines from a .rad so we can build real wheels.
export function parseWheels(text) {
  const wheels = [];
  let rims = { color: [120, 120, 120], size: 2, depth: 3 };
  let div = 1, iwid = 1;
  const sc = [1, 1, 1];
  for (const raw of text.split(/\r\n|\n|\r/)) {
    const line = raw.trim();
    const nums = () => line.slice(line.indexOf('(') + 1, line.lastIndexOf(')')).split(',').map(Number);
    if (line.startsWith('div(')) div = nums()[0] / 10;
    if (line.startsWith('idiv(')) div = nums()[0] / 100;
    if (line.startsWith('iwid(')) iwid = nums()[0] / 100;
    if (line.startsWith('ScaleX(')) sc[0] = nums()[0] / 100;
    if (line.startsWith('ScaleY(')) sc[1] = nums()[0] / 100;
    if (line.startsWith('ScaleZ(')) sc[2] = nums()[0] / 100;
    if (line.startsWith('rims(')) {
      const v = nums();
      const size = Math.max(0, v[3] / 10);
      // Wheels.setrims limits depth to between -25x and 41x the size
      const depth = Math.max(-25 * size, Math.min(41 * size, v[4] / 10));
      rims = { color: [v[0], v[1], v[2]], size, depth };
    }
    if (line.startsWith('w(')) {
      const v = nums();
      wheels.push({
        x: v[0] * div * iwid * sc[0], y: v[1] * div * sc[1], z: v[2] * div * sc[2],
        steer: v[3] === 11,
        width: (v[4] * div * iwid) / 10,
        height: (v[5] * div) / 10,
        rims: { ...rims },
      });
    }
  }
  // NFM World cars may list extra (sometimes zero-size) wheels; draw the real ones
  return wheels.filter((w) => w.height > 0 && Math.abs(w.width) > 0);
}
