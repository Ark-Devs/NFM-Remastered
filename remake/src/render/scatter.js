// Scenery scattered over the stage: small drive-through ground cover inside
// the track (kept off roads and pieces), trees/rocks/bushes beyond the walls.
import * as THREE from 'three';
import { mergeGeometries } from 'three/addons/utils/BufferGeometryUtils.js';
import { S } from './space.js';

const lin = (hex) => new THREE.Color(hex).convertSRGBToLinear();

function colorize(g, fn) {
  g = g.index ? g.toNonIndexed() : g;
  const p = g.attributes.position;
  const c = new Float32Array(p.count * 3);
  const tmp = new THREE.Color();
  for (let i = 0; i < p.count; i++) { fn(tmp, p.getX(i), p.getY(i), p.getZ(i)); c[i * 3] = tmp.r; c[i * 3 + 1] = tmp.g; c[i * 3 + 2] = tmp.b; }
  g.setAttribute('color', new THREE.BufferAttribute(c, 3));
  return g;
}

function jitter(g, amt, rnd) {
  const p = g.attributes.position;
  const seen = new Map();
  for (let i = 0; i < p.count; i++) {
    const k = `${p.getX(i).toFixed(3)},${p.getY(i).toFixed(3)},${p.getZ(i).toFixed(3)}`;
    if (!seen.has(k)) seen.set(k, [(rnd() - 0.5) * amt, (rnd() - 0.5) * amt, (rnd() - 0.5) * amt]);
    const d = seen.get(k);
    p.setXYZ(i, p.getX(i) + d[0], p.getY(i) + d[1], p.getZ(i) + d[2]);
  }
  g.computeVertexNormals();
  return g;
}

/* ---------------------------------------------------------- shapes */
function grassTuft(base, tip) {
  const pos = [];
  const blades = 9;
  for (let i = 0; i < blades; i++) {
    const a = (i / blades) * Math.PI * 2 + Math.random();
    const r = 0.05 + Math.random() * 0.08;
    const lean = 0.15 + Math.random() * 0.2;
    const h = 0.6 + Math.random() * 0.5;
    const bx = Math.cos(a) * r, bz = Math.sin(a) * r;
    const w = 0.025;
    pos.push(bx - Math.sin(a) * w, 0, bz + Math.cos(a) * w, bx + Math.sin(a) * w, 0, bz - Math.cos(a) * w, bx + Math.cos(a) * lean, h, bz + Math.sin(a) * lean);
  }
  const g = new THREE.BufferGeometry();
  g.setAttribute('position', new THREE.Float32BufferAttribute(pos, 3));
  g.computeVertexNormals();
  return colorize(g, (c, x, y) => c.copy(base).lerp(tip, y / 1.1));
}

function rock(color, rnd, detail = 1) {
  const g = jitter(new THREE.IcosahedronGeometry(1, detail), 0.55, rnd);
  g.scale(1, 0.6, 1);
  g.translate(0, 0.25, 0);
  return colorize(g, (c, x, y) => c.copy(color).multiplyScalar(0.75 + y * 0.35));
}

function snowRock(color, rnd) {
  const g = jitter(new THREE.IcosahedronGeometry(1, 1), 0.5, rnd);
  g.scale(1, 0.65, 1);
  g.translate(0, 0.2, 0);
  const snow = lin(0xf2f5f8);
  return colorize(g, (c, x, y) => (y > 0.45 ? c.copy(snow) : c.copy(color)));
}

function pine(needles, trunkCol, snowy) {
  const trunk = new THREE.CylinderGeometry(0.12, 0.18, 1.6, 6).translate(0, 0.8, 0);
  const parts = [colorize(trunk, (c) => c.copy(trunkCol))];
  const snow = lin(0xf0f3f7);
  for (let i = 0; i < 4; i++) {
    const r = 1.5 - i * 0.3, h = 2.0 - i * 0.25, y = 1.3 + i * 1.15;
    const cone = new THREE.ConeGeometry(r, h, 7).translate(0, y + h / 2, 0);
    parts.push(colorize(cone, (c, x, yy) => {
      c.copy(needles).multiplyScalar(0.7 + ((yy - y) / h) * 0.45);
      if (snowy && yy - y > h * 0.35) c.lerp(snow, 0.75);
    }));
  }
  return mergeGeometries(parts);
}

function broadleaf(leaf, trunkCol, rnd) {
  const parts = [colorize(new THREE.CylinderGeometry(0.15, 0.25, 2.4, 6).translate(0, 1.2, 0), (c) => c.copy(trunkCol))];
  for (let i = 0; i < 3; i++) {
    const b = jitter(new THREE.IcosahedronGeometry(1.3 + rnd() * 0.5, 1), 0.5, rnd);
    b.translate((rnd() - 0.5) * 1.2, 3.0 + i * 0.6, (rnd() - 0.5) * 1.2);
    parts.push(colorize(b, (c, x, y) => c.copy(leaf).multiplyScalar(0.65 + (y - 2.5) * 0.15)));
  }
  return mergeGeometries(parts);
}

function bush(leaf, rnd) {
  const parts = [];
  for (let i = 0; i < 3; i++) {
    const b = jitter(new THREE.IcosahedronGeometry(0.6 + rnd() * 0.4, 1), 0.35, rnd);
    b.translate((rnd() - 0.5) * 1.0, 0.4 + rnd() * 0.2, (rnd() - 0.5) * 1.0);
    parts.push(colorize(b, (c, x, y) => c.copy(leaf).multiplyScalar(0.6 + y * 0.4)));
  }
  return mergeGeometries(parts);
}

function deadShrub(col, rnd) {
  const parts = [];
  for (let i = 0; i < 7; i++) {
    const len = 0.6 + rnd() * 0.8;
    const br = new THREE.CylinderGeometry(0.015, 0.035, len, 4).translate(0, len / 2, 0);
    br.rotateZ((rnd() - 0.5) * 1.6);
    br.rotateY(rnd() * Math.PI * 2);
    parts.push(colorize(br, (c) => c.copy(col)));
  }
  return mergeGeometries(parts);
}

function cactus(col) {
  const parts = [colorize(new THREE.CylinderGeometry(0.22, 0.25, 3.2, 8).translate(0, 1.6, 0), (c) => c.copy(col))];
  for (const s of [-1, 1]) {
    const arm = new THREE.CylinderGeometry(0.15, 0.15, 1.1, 7).translate(0, 0.55, 0);
    arm.translate(s * 0.55, 1.4 + (s > 0 ? 0.3 : 0), 0);
    parts.push(colorize(arm, (c) => c.copy(col).multiplyScalar(0.9)));
    const elbow = new THREE.CylinderGeometry(0.15, 0.15, 0.4, 7).rotateZ(Math.PI / 2).translate(s * 0.35, 1.4 + (s > 0 ? 0.3 : 0), 0);
    parts.push(colorize(elbow, (c) => c.copy(col).multiplyScalar(0.9)));
  }
  return mergeGeometries(parts);
}

/* ---------------------------------------------------------- placement */
// Occupancy grid over the track in 3 m cells: roads, ramps, piles, pieces.
function occupancy(terrain, trackers, objects) {
  const cell = 3;
  const W = Math.ceil((terrain.x1 - terrain.x0) / cell), H = Math.ceil((terrain.z1 - terrain.z0) / cell);
  const grid = new Uint8Array(W * H);
  const mark = (x0, x1, z0, z1) => {
    const i0 = Math.max(0, Math.floor((x0 - terrain.x0) / cell)), i1 = Math.min(W - 1, Math.floor((x1 - terrain.x0) / cell));
    const j0 = Math.max(0, Math.floor((z0 - terrain.z0) / cell)), j1 = Math.min(H - 1, Math.floor((z1 - terrain.z0) / cell));
    for (let j = j0; j <= j1; j++) for (let i = i0; i <= i1; i++) grid[j * W + i] = 1;
  };
  for (let t = 0; t < trackers.nt; t++) {
    const x = trackers.x[t] * S, z = -trackers.z[t] * S, rx = trackers.radx[t] * S + 2, rz = trackers.radz[t] * S + 2;
    if (rx > 400 || rz > 400) continue; // boundary walls
    mark(x - rx, x + rx, z - rz, z + rz);
  }
  for (const o of objects) {
    const r = Math.min(30, (o.conto.maxR || 0) * S * 0.6) + 1;
    const x = o.conto.x * S, z = -o.conto.z * S;
    if (o.wall) continue;
    mark(x - r, x + r, z - r, z + r);
  }
  return { free: (x, z) => {
    const i = Math.floor((x - terrain.x0) / cell), j = Math.floor((z - terrain.z0) / cell);
    if (i < 0 || j < 0 || i >= W || j >= H) return true;
    return !grid[j * W + i];
  } };
}

function instanced(geo, mat, transforms, shadows) {
  const mesh = new THREE.InstancedMesh(geo, mat, transforms.length);
  transforms.forEach((m, i) => mesh.setMatrixAt(i, m));
  mesh.castShadow = shadows;
  mesh.receiveShadow = true;
  mesh.computeBoundingSphere();
  return mesh;
}

export function buildScatter(terrain, pal, trackers, objects) {
  const group = new THREE.Group();
  const rnd = terrain.n.rnd;
  const biome = terrain.biome;
  const occ = occupancy(terrain, trackers, objects);
  const ground = pal.ground.clone();
  const matSolid = new THREE.MeshStandardMaterial({ vertexColors: true, roughness: 0.9, flatShading: true });
  const matGrass = new THREE.MeshStandardMaterial({ vertexColors: true, roughness: 0.95, side: THREE.DoubleSide });
  const _m = new THREE.Matrix4(), _q = new THREE.Quaternion(), _s = new THREE.Vector3(), _p = new THREE.Vector3(), _up = new THREE.Vector3(0, 1, 0);

  const place = (count, { inside, minD = 8, maxD = 500, scale = [1, 1], test }) => {
    const out = [];
    let tries = 0;
    while (out.length < count && tries++ < count * 12) {
      let x, z;
      if (inside) {
        x = THREE.MathUtils.lerp(terrain.x0 + 45, terrain.x1 - 45, rnd());
        z = THREE.MathUtils.lerp(terrain.z0 + 45, terrain.z1 - 45, rnd());
        if (!occ.free(x, z)) continue;
      } else {
        const a = rnd() * Math.PI * 2;
        const ex = (terrain.x1 - terrain.x0) / 2, ez = (terrain.z1 - terrain.z0) / 2;
        const d = minD + Math.pow(rnd(), 1.6) * (maxD - minD);
        x = terrain.cx + Math.cos(a) * (ex + d);
        z = terrain.cz + Math.sin(a) * (ez + d);
        if (terrain.outside(x, z) < minD) continue;
      }
      if (test && !test(x, z)) continue;
      const s = THREE.MathUtils.lerp(scale[0], scale[1], rnd());
      _p.set(x, terrain.heightAt(x, z) - 0.05, z);
      _q.setFromAxisAngle(_up, rnd() * Math.PI * 2);
      _s.set(s, s * (0.85 + rnd() * 0.3), s);
      out.push(new THREE.Matrix4().compose(_p, _q, _s));
    }
    return out;
  };
  // grass that grows in patches rather than evenly
  const patchy = (x, z) => terrain.n.fbm(x * 0.03, z * 0.03, 3) > 0.48;

  const add = (geo, mat, t, sh) => { if (t.length) group.add(instanced(geo, mat, t, sh)); };
  if (biome === 'grass') {
    const g1 = grassTuft(ground.clone().multiplyScalar(0.55), ground.clone().lerp(lin(0xb9d36a), 0.4));
    add(g1, matGrass, place(9000, { inside: true, scale: [0.5, 1.0], test: patchy }), false);
    add(g1, matGrass, place(5000, { inside: false, maxD: 160, scale: [0.6, 1.2] }), false);
    add(pine(lin(0x2f5a2c), lin(0x5a3b22), false), matSolid, place(900, { inside: false, minD: 15, maxD: 600, scale: [1.2, 2.4] }), true);
    add(broadleaf(lin(0x4f7a2e), lin(0x5a3b22), rnd), matSolid, place(600, { inside: false, minD: 12, maxD: 450, scale: [1.0, 1.9] }), true);
    add(bush(lin(0x3f6a2a), rnd), matSolid, place(700, { inside: false, minD: 8, maxD: 300, scale: [0.8, 1.6] }), true);
  } else if (biome === 'desert' || biome === 'canyon') {
    const dry = grassTuft(lin(0x8a6a3a), lin(0xd8bf86));
    add(dry, matGrass, place(2500, { inside: true, scale: [0.4, 0.8], test: patchy }), false);
    add(rock(ground.clone().multiplyScalar(0.7), rnd, 0), matSolid, place(2500, { inside: true, scale: [0.08, 0.22] }), false);
    add(deadShrub(lin(0x6b4a2e), rnd), matSolid, place(700, { inside: false, minD: 6, maxD: 400, scale: [0.8, 1.5] }), true);
    if (biome === 'desert') add(cactus(lin(0x4f6b35)), matSolid, place(250, { inside: false, minD: 10, maxD: 350, scale: [0.8, 1.6] }), true);
  } else if (biome === 'snow') {
    add(snowRock(lin(0x5d636b), rnd), matSolid, place(1500, { inside: true, scale: [0.06, 0.18] }), false);
    add(pine(lin(0x23402b), lin(0x4a3424), true), matSolid, place(1200, { inside: false, minD: 12, maxD: 600, scale: [1.2, 2.6] }), true);
  } else {
    const dry = grassTuft(ground.clone().multiplyScalar(0.5), ground.clone().lerp(lin(0xa8a070), 0.4));
    add(dry, matGrass, place(4000, { inside: true, scale: [0.4, 0.9], test: patchy }), false);
    add(rock(ground.clone().multiplyScalar(0.65), rnd, 0), matSolid, place(1500, { inside: true, scale: [0.08, 0.2] }), false);
    add(bush(lin(0x5b5a32), rnd), matSolid, place(700, { inside: false, minD: 8, maxD: 350, scale: [0.8, 1.6] }), true);
  }
  // boulders beyond the walls for every biome
  const rockCol = biome === 'canyon' ? lin(0x9c4f2e) : biome === 'desert' ? lin(0x9a6a45) : biome === 'snow' ? lin(0x5d636b) : lin(0x6d6a62);
  add(biome === 'snow' ? snowRock(rockCol, rnd) : rock(rockCol, rnd), matSolid, place(500, { inside: false, minD: 6, maxD: 500, scale: [1.0, 4.5] }), true);
  return group;
}
