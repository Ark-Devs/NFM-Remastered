import * as THREE from 'three';
import { buildConto, parseWheels } from './meshbuild.js';
import { evalStencils } from './subdivide.js';
import { S, nfmMatrix, nfmRotation, carColorLinear } from './space.js';
import { createCarBodyMaterial, createGlassMaterial, createLightMaterial, createTireMaterial, createRimMaterial } from './materials.js';

const tireMat = createTireMaterial();
tireMat.map = treadTexture();
tireMat.bumpMap = tireMat.map;
tireMat.bumpScale = 1.5;
tireMat.color.set(0xb4b4b4);
const glowTex = radialTexture([[0, 'rgba(255,255,255,1)'], [0.2, 'rgba(255,255,255,0.55)'], [0.5, 'rgba(255,255,255,0.12)'], [1, 'rgba(255,255,255,0)']]);
const shadowTex = footprintTexture();

// Soft rectangular footprint (ambient occlusion under the chassis), not a round blob.
function footprintTexture() {
  const c = document.createElement('canvas');
  c.width = c.height = 128;
  const g = c.getContext('2d');
  g.filter = 'blur(10px)';
  g.fillStyle = '#fff';
  g.fillRect(26, 18, 76, 92);
  return new THREE.CanvasTexture(c);
}

function radialTexture(stops) {
  const c = document.createElement('canvas');
  c.width = c.height = 128;
  const g = c.getContext('2d');
  const grd = g.createRadialGradient(64, 64, 0, 64, 64, 64);
  for (const [o, col] of stops) grd.addColorStop(o, col);
  g.fillStyle = grd;
  g.fillRect(0, 0, 128, 128);
  return new THREE.CanvasTexture(c);
}

// Tread pattern laid out on the lathe UVs (u around the tyre, v across it).
function treadTexture() {
  const c = document.createElement('canvas');
  c.width = 1024; c.height = 128;
  const g = c.getContext('2d');
  g.fillStyle = '#2a2a2a';
  g.fillRect(0, 0, 1024, 128);
  // sidewalls: slightly smoother, lighter rubber with a moulded ring
  g.fillStyle = '#323232';
  g.fillRect(0, 0, 1024, 30); g.fillRect(0, 98, 1024, 30);
  g.fillStyle = '#3a3a3a';
  g.fillRect(0, 12, 1024, 3); g.fillRect(0, 113, 1024, 3);
  // tread blocks
  g.fillStyle = '#121212';
  for (let x = 0; x < 1024; x += 16) {
    g.fillRect(x, 34, 5, 26);
    g.fillRect(x + 8, 68, 5, 26);
  }
  g.fillRect(0, 61, 1024, 6); // centre groove
  const t = new THREE.CanvasTexture(c);
  t.colorSpace = THREE.SRGBColorSpace;
  t.wrapS = THREE.RepeatWrapping;
  t.anisotropy = 4;
  return t;
}
const tireCache = new Map();

function tireGeometry(radius, width) {
  const key = `${radius.toFixed(3)}|${width.toFixed(3)}`;
  if (tireCache.has(key)) return tireCache.get(key);
  // Rounded tire profile revolved around the axle.
  const r = radius, w = width / 2, sh = Math.min(w * 0.45, r * 0.18);
  const pts = [];
  const rimR = r * 0.68;
  pts.push(new THREE.Vector2(rimR, -w));
  for (let i = 0; i <= 6; i++) {
    const a = (i / 6) * (Math.PI / 2);
    pts.push(new THREE.Vector2(r - sh + Math.sin(a) * sh, -w + sh - Math.cos(a) * sh));
  }
  for (let i = 0; i <= 6; i++) {
    const a = (i / 6) * (Math.PI / 2);
    pts.push(new THREE.Vector2(r - sh + Math.cos(a) * sh, w - sh + Math.sin(a) * sh));
  }
  pts.push(new THREE.Vector2(rimR, w));
  const g = new THREE.LatheGeometry(pts, 32);
  g.rotateZ(Math.PI / 2); // axle along x
  tireCache.set(key, g);
  return g;
}

const sidewallMat = new THREE.MeshStandardMaterial({ color: 0x2b2b2b, roughness: 0.9, metalness: 0, side: THREE.DoubleSide });

// Hexagonal hub-cap cone (Wheels.make): six triangles from the rim hexagon on
// the tyre face to an apex on the axle, plus the flat hexagon behind it.
function hubcapGeometry(R, faceX, apexX, backing = true) {
  const pos = [];
  const hex = [];
  for (let i = 0; i < 6; i++) {
    const a = (i / 6) * Math.PI * 2;
    hex.push([Math.sin(a) * R, Math.cos(a) * R]);
  }
  for (let i = 0; i < 6; i++) {
    const [y0, z0] = hex[i], [y1, z1] = hex[(i + 1) % 6];
    pos.push(apexX, 0, 0, faceX, y0, z0, faceX, y1, z1); // cone facet
    if (backing) pos.push(faceX, 0, 0, faceX, y1, z1, faceX, y0, z0); // backing hexagon
  }
  const g = new THREE.BufferGeometry();
  g.setAttribute('position', new THREE.Float32BufferAttribute(pos, 3));
  g.computeVertexNormals();
  return g;
}

// Tyre sidewall in the wheel's YZ plane at x, with an optional hexagonal hole
// (same outline as the hub cap) so a dished cap can be seen inside it.
function sidewallGeometry(x, r, holeR) {
  const pos = [];
  const P = (a, rho) => [x, Math.sin(a) * rho, Math.cos(a) * rho];
  if (!holeR) {
    for (let k = 0; k < 32; k++) pos.push(x, 0, 0, ...P((k / 32) * Math.PI * 2, r), ...P(((k + 1) / 32) * Math.PI * 2, r));
  } else {
    const m = 6;
    for (let i = 0; i < 6; i++) {
      const a0 = (i / 6) * Math.PI * 2, a1 = ((i + 1) / 6) * Math.PI * 2;
      const h0 = P(a0, holeR), h1 = P(a1, holeR);
      for (let k = 0; k < m; k++) pos.push(...h0, ...P(a0 + ((a1 - a0) * k) / m, r), ...P(a0 + ((a1 - a0) * (k + 1)) / m, r));
      pos.push(...h0, ...P(a1, r), ...h1);
    }
  }
  const g = new THREE.BufferGeometry();
  g.setAttribute('position', new THREE.Float32BufferAttribute(pos, 3));
  g.computeVertexNormals();
  return g;
}

function buildWheel(w, envMap) {
  const group = new THREE.Group();
  const spin = new THREE.Group();
  group.add(spin);
  const radius = 12.557 * w.height * S;
  const width = Math.max(8 * Math.abs(w.width) * S, 0.08);
  const tire = new THREE.Mesh(tireGeometry(radius, width), tireMat);
  tire.castShadow = true;
  spin.add(tire);
  const outward = w.width >= 0 ? -1 : 1;
  const f = Math.abs(w.width); // Wheels.make: tyre spans x ± 4f
  const rimColor = new THREE.Color();
  rimColor.setRGB(w.rims.color[0] / 255, w.rims.color[1] / 255, w.rims.color[2] / 255, THREE.SRGBColorSpace);
  const rimMat = createRimMaterial(rimColor, envMap);
  rimMat.flatShading = true;
  // Hub cap exactly as Wheels.make builds it: a hexagonal cone in the rim
  // colour, base radius 10*size on the tyre face, apex at depth*f from the
  // centre: deeper than the face gives a spike, shallower gives a dish.
  const R = Math.min(radius * 0.95, Math.max(0.02, 10 * w.rims.size * S));
  const apexX = outward * w.rims.depth * f * S;
  const dish = apexX * outward < width / 2;
  // spikes sit just proud of the tyre face; dishes start flush with the
  // sidewall opening so no raised lip shows around the hole
  const faceX = outward * (dish ? width / 2 - 0.002 : width / 2 + 0.004);
  // solid rubber sidewalls (the original fills the whole tyre face); the outer
  // one gets a hexagonal opening for dished caps so the recess shows
  for (const sx of [-1, 1]) {
    const hole = dish && sx === outward ? R : 0;
    spin.add(new THREE.Mesh(sidewallGeometry(sx * (width / 2 - 0.002), radius * 0.985, hole), sidewallMat));
  }
  if (dish) rimMat.side = THREE.DoubleSide;
  const cone = new THREE.Mesh(hubcapGeometry(R, faceX, apexX, !dish), rimMat);
  cone.castShadow = true;
  spin.add(cone);
  // motion-blurred hub face, faded in at speed
  const blurMat = new THREE.MeshStandardMaterial({ color: rimColor.clone().multiplyScalar(0.7), roughness: 0.4, metalness: 0.8, transparent: true, opacity: 0, alphaMap: glowTex, depthWrite: false });
  const blur = new THREE.Mesh(new THREE.CircleGeometry(R * 0.97, 24), blurMat);
  blur.rotation.y = outward > 0 ? Math.PI / 2 : -Math.PI / 2;
  blur.position.x = faceX + outward * 0.006;
  group.add(blur);
  const spokes = [];
  group.userData = { w, spin, spokes, blurMat };
  return group;
}

const _m = new THREE.Matrix4();
const _bm = new THREE.Matrix4();
const _inv = new THREE.Matrix4();
const _lin = [0, 0, 0];

export class CarView {
  constructor(conto, radText, envMap, smooth = 2) {
    this.root = new THREE.Group();
    this.root.matrixAutoUpdate = false;
    // the stage tint baked into this car's colours (undone for display)
    this.snap = Array.from(conto.m.snap);
    this.parts = buildConto(conto, { car: true, smooth, snap: this.snap });
    this.bodyMat = createCarBodyMaterial(envMap);
    this.glassMat = createGlassMaterial(envMap);
    this.lightFMat = createLightMaterial(2.5);
    this.lightBMat = createLightMaterial(0.8);
    const mats = { body: this.bodyMat, glass: this.glassMat, lightF: this.lightFMat, lightB: this.lightBMat };
    this.meshes = {};
    this.bodyGroup = new THREE.Group();
    this.bodyGroup.matrixAutoUpdate = false;
    this.root.add(this.bodyGroup);
    for (const [k, part] of Object.entries(this.parts)) {
      const mesh = new THREE.Mesh(part.geometry, mats[k]);
      mesh.castShadow = k !== 'glass';
      mesh.receiveShadow = k === 'body';
      if (k === 'glass') mesh.renderOrder = 2;
      this.bodyGroup.add(mesh);
      this.meshes[k] = mesh;
    }
    this.wheels = parseWheels(radText).map((w) => {
      const g = buildWheel(w, envMap);
      g.position.set(w.x * S, -w.y * S, -w.z * S);
      g.matrixAutoUpdate = false;
      this.root.add(g);
      return g;
    });
    this.spinAngle = 0;

    this.setDetailBounds();
    this.addGlows();
    // soft contact shadow under the car, grounds it on the road
    const kx = conto.keyx, kz = conto.keyz;
    const wid = (Math.max(...kx) - Math.min(...kx)) * S * 1.25 + 0.25;
    const len = (Math.max(...kz) - Math.min(...kz)) * S * 1.15 + 0.3;
    this.shadowMat = new THREE.MeshBasicMaterial({ color: 0x000000, alphaMap: shadowTex, transparent: true, opacity: 0.45, depthWrite: false, polygonOffset: true, polygonOffsetFactor: -2, polygonOffsetUnits: -2 });
    this.contact = new THREE.Mesh(new THREE.PlaneGeometry(wid, len).rotateX(-Math.PI / 2), this.shadowMat);
    this.contact.position.set(0, -conto.grat * S + 0.03, -((Math.max(...kz) + Math.min(...kz)) / 2) * S);
    this.contact.renderOrder = 1;
    this.root.add(this.contact);
    this.baseGr = new Map();
    for (let i = 0; i < conto.npl; i++) if (conto.p[i]) this.baseGr.set(i, conto.p[i].gr);
    this.checksum = this.computeChecksum(conto);
    this.conto = conto;
    this.radius = conto.maxR * S;
  }

  // Car dimensions for the paint shader's door seams, handles and badges.
  setDetailBounds() {
    const d = this.bodyMat.userData.detail;
    const g = this.parts.body?.geometry;
    if (!g) return;
    g.computeBoundingBox();
    d.b0.copy(g.boundingBox.min);
    d.b1.copy(g.boundingBox.max);
    const zs = this.wheels.map((w) => -w.userData.w.z * S);
    if (zs.length) d.ax.set(Math.min(...zs), Math.max(...zs));
    d.cab.value = this.parts.glass ? 1 : 0;
  }

  // Glow halos on the head- and tail-light polygons.
  addGlows() {
    this.glows = [];
    for (const [k, color] of [['lightF', 0xfff1d6], ['lightB', 0xff2a1a]]) {
      const part = this.parts[k];
      if (!part) continue;
      const pos = part.geometry.attributes.position.array;
      const byPlane = new Map();
      const nverts = part.geometry.attributes.position.count;
      for (let i = 0; i < nverts; i++) {
        const pl = part.smooth ? part.smooth.outPoly[i] : part.src[i * 2];
        if (!byPlane.has(pl)) byPlane.set(pl, []);
        byPlane.get(pl).push(i);
      }
      let n = 0;
      for (const idx of byPlane.values()) {
        if (n++ >= 6) break;
        const c = new THREE.Vector3();
        const box = new THREE.Box3();
        for (const i of idx) { const v = new THREE.Vector3(pos[i * 3], pos[i * 3 + 1], pos[i * 3 + 2]); c.add(v); box.expandByPoint(v); }
        c.divideScalar(idx.length);
        const size = Math.max(0.25, box.getSize(new THREE.Vector3()).length() * 1.6);
        c.z += k === 'lightF' ? -0.04 : 0.04; // just outside the lens
        const mat = new THREE.SpriteMaterial({ map: glowTex, color, blending: THREE.AdditiveBlending, depthWrite: false, transparent: true, toneMapped: false, opacity: 0.5 });
        const sp = new THREE.Sprite(mat);
        sp.position.copy(c);
        sp.scale.setScalar(size);
        sp.userData = { back: k === 'lightB', base: size };
        this.bodyGroup.add(sp);
        this.glows.push(sp);
      }
    }
  }

  computeChecksum(c) {
    let s = 0;
    for (let i = 0; i < c.npl; i++) {
      const p = c.p[i];
      if (!p || p.wz !== 0) continue;
      for (let v = 0; v < p.n; v++) s = (s * 31 + p.ox[v] * 7 + p.oy[v] * 13 + p.oz[v] * 17) | 0;
      s = (s * 31 + p.c[0] + p.c[1] * 3 + p.c[2] * 5 + p.bfase * 11 + p.gr * 19) | 0;
    }
    return s;
  }

  // Pull dents, burnt colours and cracked glass from the physics model.
  syncDamage(conto) {
    const sum = this.computeChecksum(conto);
    if (sum === this.checksum) return false;
    this.checksum = sum;
    for (const [k, part] of Object.entries(this.parts)) {
      const g = part.geometry;
      const pos = g.attributes.position.array;
      const col = g.attributes.color.array;
      const extra = (g.attributes.dmg || g.attributes.crack).array;
      const src = part.src;
      if (part.smooth) {
        // rounded mesh: re-evaluate the subdivision stencils from the dented polygons
        const sm = part.smooth;
        for (let i = 0, n = src.length / 2; i < n; i++) {
          const p = conto.p[src[i * 2]], v = src[i * 2 + 1];
          sm.memberPos[i * 3] = p.ox[v] * S; sm.memberPos[i * 3 + 1] = -p.oy[v] * S; sm.memberPos[i * 3 + 2] = -p.oz[v] * S;
        }
        // polygon centre points used by the symmetric triangulation
        sm.centroids.forEach(([first, count], c) => {
          const o = (sm.nOrig + c) * 3;
          let x = 0, y = 0, z = 0;
          for (let k = first; k < first + count; k++) { x += sm.memberPos[k * 3]; y += sm.memberPos[k * 3 + 1]; z += sm.memberPos[k * 3 + 2]; }
          sm.memberPos[o] = x / count; sm.memberPos[o + 1] = y / count; sm.memberPos[o + 2] = z / count;
        });
        evalStencils(sm.sub, sm.memberPos, sm.subPos);
        for (let i = 0, n = sm.outSub.length; i < n; i++) {
          const s = sm.outSub[i];
          pos[i * 3] = sm.subPos[s * 3]; pos[i * 3 + 1] = sm.subPos[s * 3 + 1]; pos[i * 3 + 2] = sm.subPos[s * 3 + 2];
          const pi = sm.outPoly[i];
          const p = conto.p[pi];
          carColorLinear(p.c, this.snap, _lin);
          col[i * 3] = _lin[0]; col[i * 3 + 1] = _lin[1]; col[i * 3 + 2] = _lin[2];
          if (k === 'glass') extra[i] = Math.min(1, Math.max(0, (p.gr - (this.baseGr.get(pi) ?? 0) - 25) / 160));
          else extra[i] = Math.min(1, p.bfase / 65);
        }
        g.attributes.position.needsUpdate = true;
        g.attributes.color.needsUpdate = true;
        (g.attributes.dmg || g.attributes.crack).needsUpdate = true;
        part.smoother.update();
        g.computeBoundingSphere();
        continue;
      }
      for (let i = 0, n = src.length / 2; i < n; i++) {
        const p = conto.p[src[i * 2]];
        const v = src[i * 2 + 1];
        pos[i * 3] = p.ox[v] * S;
        pos[i * 3 + 1] = -p.oy[v] * S;
        pos[i * 3 + 2] = -p.oz[v] * S;
        carColorLinear(p.c, this.snap, _lin);
        col[i * 3] = _lin[0]; col[i * 3 + 1] = _lin[1]; col[i * 3 + 2] = _lin[2];
        if (k === 'glass') extra[i] = Math.min(1, Math.max(0, (p.gr - (this.baseGr.get(src[i * 2]) ?? 0) - 25) / 160));
        else extra[i] = Math.min(1, p.bfase / 65);
      }
      g.attributes.position.needsUpdate = true;
      g.attributes.color.needsUpdate = true;
      (g.attributes.dmg || g.attributes.crack).needsUpdate = true;
      part.smoother.update();
      g.computeBoundingSphere();
    }
    return true;
  }

  // state: interpolated {x,y,z,xz,xy,zy,wxz}; speed for wheel spin (per second)
  update(state, dt, speed, simag, braking, fixing, body = null, grounded = true, night = false) {
    nfmMatrix(state.x, state.y, state.z, state.xz, state.xy, state.zy, this.root.matrix);
    this.root.matrixWorldNeedsUpdate = true;
    // body rides on the visual suspension: local offset = inverse(wheels pose) * body pose
    if (body) {
      nfmMatrix(body.x, body.y, body.z, body.xz, body.xy, body.zy, _bm);
      this.bodyGroup.matrix.copy(_inv.copy(this.root.matrix).invert()).multiply(_bm);
    } else this.bodyGroup.matrix.identity();
    this.bodyGroup.matrixWorldNeedsUpdate = true;
    // wheel spin follows Mad.drive: 20 * speed / (154 * simag) degrees per tick
    this.spinAngle -= (20 * speed) / (154 * simag) * 25 * dt;
    for (const g of this.wheels) {
      const { w, spin } = g.userData;
      nfmRotation(w.steer ? state.wxz : 0, 0, 0, _m);
      _m.setPosition(w.x * S, -w.y * S, -w.z * S);
      g.matrix.copy(_m);
      g.matrixWorldNeedsUpdate = true;
      spin.rotation.x = (this.spinAngle * Math.PI) / 180;
      const { spokes, blurMat } = g.userData;
      const blur = THREE.MathUtils.smoothstep(Math.abs(speed), 35, 110);
      blurMat.opacity = blur * 0.95;
      for (const sp of spokes) sp.visible = blur < 0.75;
    }
    this.lightBMat.emissiveIntensity = braking ? 4.5 : 0.9;
    for (const sp of this.glows) {
      const on = sp.userData.back ? (braking ? 1 : 0.28) : (night ? 1 : 0.22);
      sp.material.opacity += (on - sp.material.opacity) * Math.min(1, dt * 12);
      sp.scale.setScalar(sp.userData.base * (0.8 + sp.material.opacity * 0.6));
    }
    // contact shadow fades out as soon as the car leaves the ground
    const target = grounded ? 0.45 : 0;
    this.shadowMat.opacity += (target - this.shadowMat.opacity) * Math.min(1, dt * 10);
    this.contact.visible = this.shadowMat.opacity > 0.02;
    if (fixing) {
      const t = performance.now() / 90;
      this.bodyMat.emissive.setRGB(0.2, 0.55, 1.0);
      this.bodyMat.emissiveIntensity = 0.6 + Math.sin(t) * 0.4;
    } else if (this.bodyMat.emissiveIntensity !== 0) {
      this.bodyMat.emissiveIntensity = 0;
    }
  }

  dispose() {
    for (const part of Object.values(this.parts)) part.geometry.dispose();
    this.bodyMat.dispose(); this.glassMat.dispose(); this.lightFMat.dispose(); this.lightBMat.dispose();
    this.shadowMat.dispose(); this.contact.geometry.dispose();
    for (const sp of this.glows) sp.material.dispose();
  }
}
