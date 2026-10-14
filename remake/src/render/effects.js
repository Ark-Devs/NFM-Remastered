import * as THREE from 'three';
import { S, toThree } from './space.js';

function softTexture(kind) {
  const c = document.createElement('canvas');
  c.width = c.height = 128;
  const g = c.getContext('2d');
  if (kind === 'smoke') {
    const img = g.createImageData(128, 128);
    for (let y = 0; y < 128; y++) for (let x = 0; x < 128; x++) {
      const dx = (x - 64) / 64, dy = (y - 64) / 64;
      const r = Math.sqrt(dx * dx + dy * dy);
      let n = 0, a = 0.5, f = 3;
      for (let o = 0; o < 4; o++) { n += a * (Math.sin(x * f * 0.05 + Math.cos(y * f * 0.043) * 2.1 + o) * 0.5 + 0.5); a *= 0.5; f *= 2.1; }
      const v = Math.max(0, 1 - r) ** 1.6 * (0.55 + 0.45 * n);
      const i = (y * 128 + x) * 4;
      img.data[i] = img.data[i + 1] = img.data[i + 2] = 255;
      img.data[i + 3] = Math.min(255, v * 255);
    }
    g.putImageData(img, 0, 0);
  } else {
    const grd = g.createRadialGradient(64, 64, 0, 64, 64, 64);
    grd.addColorStop(0, 'rgba(255,255,255,1)');
    grd.addColorStop(0.25, 'rgba(255,255,255,0.8)');
    grd.addColorStop(1, 'rgba(255,255,255,0)');
    g.fillStyle = grd;
    g.fillRect(0, 0, 128, 128);
  }
  const t = new THREE.CanvasTexture(c);
  t.colorSpace = THREE.SRGBColorSpace;
  return t;
}

// Billboarded particle pool drawn as GL points.
class ParticlePool {
  constructor(max, texture, additive) {
    this.max = max;
    this.n = 0;
    this.pos = new Float32Array(max * 3);
    this.vel = new Float32Array(max * 3);
    this.col = new Float32Array(max * 4);
    this.size = new Float32Array(max);
    this.grow = new Float32Array(max);
    this.life = new Float32Array(max);
    this.maxLife = new Float32Array(max);
    this.drag = new Float32Array(max);
    this.grav = new Float32Array(max);
    this.alpha0 = new Float32Array(max);
    this.rot = new Float32Array(max);
    const g = new THREE.BufferGeometry();
    this.aPos = new THREE.BufferAttribute(this.pos, 3).setUsage(THREE.DynamicDrawUsage);
    this.aCol = new THREE.BufferAttribute(this.col, 4).setUsage(THREE.DynamicDrawUsage);
    this.aSize = new THREE.BufferAttribute(this.size, 1).setUsage(THREE.DynamicDrawUsage);
    this.aRot = new THREE.BufferAttribute(this.rot, 1).setUsage(THREE.DynamicDrawUsage);
    g.setAttribute('position', this.aPos);
    g.setAttribute('pcolor', this.aCol);
    g.setAttribute('psize', this.aSize);
    g.setAttribute('prot', this.aRot);
    g.setDrawRange(0, 0);
    this.material = new THREE.ShaderMaterial({
      transparent: true,
      depthWrite: false,
      blending: additive ? THREE.AdditiveBlending : THREE.NormalBlending,
      uniforms: { map: { value: texture }, uScale: { value: 800 }, fogColor: { value: new THREE.Color() }, fogNear: { value: 1 }, fogFar: { value: 2000 } },
      vertexShader: /* glsl */ `
        attribute vec4 pcolor; attribute float psize; attribute float prot;
        uniform float uScale;
        varying vec4 vColor; varying float vRot; varying float vDepth;
        void main() {
          vColor = pcolor; vRot = prot;
          vec4 mv = modelViewMatrix * vec4(position, 1.0);
          vDepth = -mv.z;
          gl_PointSize = clamp(psize * uScale / max(-mv.z, 0.1), 1.0, 900.0);
          gl_Position = projectionMatrix * mv;
        }`,
      fragmentShader: /* glsl */ `
        uniform sampler2D map; uniform vec3 fogColor; uniform float fogNear, fogFar;
        varying vec4 vColor; varying float vRot; varying float vDepth;
        void main() {
          vec2 uv = gl_PointCoord - 0.5;
          float c = cos(vRot), s = sin(vRot);
          uv = mat2(c, -s, s, c) * uv + 0.5;
          vec4 t = texture2D(map, uv);
          vec4 col = vec4(vColor.rgb * t.rgb, vColor.a * t.a);
          float f = smoothstep(fogNear, fogFar, vDepth);
          ${additive ? 'col.rgb *= (1.0 - f);' : 'col.rgb = mix(col.rgb, fogColor, f);'}
          if (col.a < 0.003) discard;
          gl_FragColor = col;
        }`,
    });
    this.points = new THREE.Points(g, this.material);
    this.points.frustumCulled = false;
    this.points.renderOrder = additive ? 5 : 4;
  }

  spawn(p, v, color, alpha, size, grow, life, drag = 1, grav = 0) {
    let i;
    if (this.n < this.max) i = this.n++;
    else i = Math.floor(Math.random() * this.max);
    this.pos[i * 3] = p.x; this.pos[i * 3 + 1] = p.y; this.pos[i * 3 + 2] = p.z;
    this.vel[i * 3] = v.x; this.vel[i * 3 + 1] = v.y; this.vel[i * 3 + 2] = v.z;
    this.col[i * 4] = color.r; this.col[i * 4 + 1] = color.g; this.col[i * 4 + 2] = color.b; this.col[i * 4 + 3] = alpha;
    this.alpha0[i] = alpha;
    this.size[i] = size; this.grow[i] = grow;
    this.life[i] = 0; this.maxLife[i] = life;
    this.drag[i] = drag; this.grav[i] = grav;
    this.rot[i] = Math.random() * 6.28;
  }

  update(dt) {
    let i = 0;
    while (i < this.n) {
      this.life[i] += dt;
      if (this.life[i] >= this.maxLife[i]) {
        const last = --this.n;
        if (i !== last) this.copy(last, i);
        continue;
      }
      const k = Math.exp(-this.drag[i] * dt);
      this.vel[i * 3] *= k; this.vel[i * 3 + 1] = this.vel[i * 3 + 1] * k + this.grav[i] * dt; this.vel[i * 3 + 2] *= k;
      this.pos[i * 3] += this.vel[i * 3] * dt;
      this.pos[i * 3 + 1] += this.vel[i * 3 + 1] * dt;
      this.pos[i * 3 + 2] += this.vel[i * 3 + 2] * dt;
      this.size[i] += this.grow[i] * dt;
      const t = this.life[i] / this.maxLife[i];
      this.col[i * 4 + 3] = this.alpha0[i] * Math.min(1, t * 8) * (1 - t) * (1 - t);
      this.rot[i] += dt * 0.4;
      i++;
    }
    this.points.geometry.setDrawRange(0, this.n);
    for (const a of [this.aPos, this.aCol, this.aSize, this.aRot]) { a.needsUpdate = true; a.addUpdateRange(0, this.n * a.itemSize); }
  }

  copy(from, to) {
    for (const [arr, w] of [[this.pos, 3], [this.vel, 3], [this.col, 4]]) for (let k = 0; k < w; k++) arr[to * w + k] = arr[from * w + k];
    for (const arr of [this.size, this.grow, this.life, this.maxLife, this.drag, this.grav, this.alpha0, this.rot]) arr[to] = arr[from];
  }
}

// Sparks as glowing velocity-stretched streaks.
class SparkPool {
  constructor(max) {
    this.max = max; this.n = 0;
    this.p = new Float32Array(max * 3); this.v = new Float32Array(max * 3);
    this.life = new Float32Array(max); this.maxLife = new Float32Array(max);
    this.verts = new Float32Array(max * 6); this.cols = new Float32Array(max * 6);
    const g = new THREE.BufferGeometry();
    this.aPos = new THREE.BufferAttribute(this.verts, 3).setUsage(THREE.DynamicDrawUsage);
    this.aCol = new THREE.BufferAttribute(this.cols, 3).setUsage(THREE.DynamicDrawUsage);
    g.setAttribute('position', this.aPos);
    g.setAttribute('color', this.aCol);
    this.lines = new THREE.LineSegments(g, new THREE.LineBasicMaterial({ vertexColors: true, transparent: true, blending: THREE.AdditiveBlending, depthWrite: false, toneMapped: false }));
    this.lines.frustumCulled = false;
  }
  spawn(pos, vel, life) {
    const i = this.n < this.max ? this.n++ : Math.floor(Math.random() * this.max);
    this.p.set([pos.x, pos.y, pos.z], i * 3);
    this.v.set([vel.x, vel.y, vel.z], i * 3);
    this.life[i] = 0; this.maxLife[i] = life;
  }
  update(dt, groundY) {
    let i = 0;
    while (i < this.n) {
      this.life[i] += dt;
      if (this.life[i] >= this.maxLife[i]) {
        const last = --this.n;
        if (i !== last) {
          this.p.copyWithin(i * 3, last * 3, last * 3 + 3); this.v.copyWithin(i * 3, last * 3, last * 3 + 3);
          this.life[i] = this.life[last]; this.maxLife[i] = this.maxLife[last];
        }
        continue;
      }
      this.v[i * 3 + 1] -= 9.8 * dt;
      for (let k = 0; k < 3; k++) this.p[i * 3 + k] += this.v[i * 3 + k] * dt;
      if (this.p[i * 3 + 1] < groundY) { this.p[i * 3 + 1] = groundY; this.v[i * 3 + 1] *= -0.35; this.v[i * 3] *= 0.6; this.v[i * 3 + 2] *= 0.6; }
      const t = this.life[i] / this.maxLife[i];
      const heat = 1 - t;
      const stretch = 0.025;
      for (let k = 0; k < 3; k++) {
        this.verts[i * 6 + k] = this.p[i * 3 + k];
        this.verts[i * 6 + 3 + k] = this.p[i * 3 + k] - this.v[i * 3 + k] * stretch;
      }
      const r = 6 * heat, g = 3.2 * heat * heat, b = 1.2 * heat * heat * heat;
      this.cols.set([r, g, b, r * 0.3, g * 0.2, b * 0.1], i * 6);
      i++;
    }
    this.lines.geometry.setDrawRange(0, this.n * 2);
    this.aPos.needsUpdate = true; this.aCol.needsUpdate = true;
  }
}

// Flying body fragments (from the original "chip" effect and explosions).
class DebrisPool {
  constructor(max) {
    this.max = max; this.n = 0;
    const g = new THREE.BufferGeometry();
    g.setAttribute('position', new THREE.Float32BufferAttribute([0, 0.12, 0, -0.12, -0.08, 0.05, 0.13, -0.06, -0.04, 0.02, 0.0, 0.14, 0, 0.12, 0], 3));
    g.setIndex([0, 1, 2, 0, 2, 3, 0, 3, 1, 1, 3, 2]);
    g.computeVertexNormals();
    this.mesh = new THREE.InstancedMesh(g, new THREE.MeshStandardMaterial({ roughness: 0.5, metalness: 0.4, side: THREE.DoubleSide }), max);
    this.mesh.count = 0;
    this.mesh.castShadow = true;
    this.mesh.instanceMatrix.setUsage(THREE.DynamicDrawUsage);
    this.items = [];
    this._m = new THREE.Matrix4(); this._q = new THREE.Quaternion(); this._s = new THREE.Vector3(); this._e = new THREE.Euler();
  }
  spawn(pos, vel, color, size, life = 4) {
    if (this.items.length >= this.max) this.items.shift();
    this.items.push({ p: pos.clone(), v: vel.clone(), r: new THREE.Vector3(Math.random() * 6, Math.random() * 6, Math.random() * 6), w: new THREE.Vector3((Math.random() - 0.5) * 20, (Math.random() - 0.5) * 20, (Math.random() - 0.5) * 20), c: color.clone(), s: size, life, t: 0 });
  }
  update(dt, groundY) {
    this.items = this.items.filter((d) => (d.t += dt) < d.life);
    this.items.forEach((d, i) => {
      d.v.y -= 9.8 * dt;
      d.p.addScaledVector(d.v, dt);
      if (d.p.y < groundY + 0.03) { d.p.y = groundY + 0.03; d.v.y *= -0.3; d.v.x *= 0.7; d.v.z *= 0.7; d.w.multiplyScalar(0.7); }
      d.r.addScaledVector(d.w, dt);
      const fade = Math.min(1, (d.life - d.t) * 2);
      this._q.setFromEuler(this._e.set(d.r.x, d.r.y, d.r.z));
      this._m.compose(d.p, this._q, this._s.setScalar(d.s * fade));
      this.mesh.setMatrixAt(i, this._m);
      this.mesh.setColorAt(i, d.c);
    });
    this.mesh.count = this.items.length;
    this.mesh.instanceMatrix.needsUpdate = true;
    if (this.mesh.instanceColor) this.mesh.instanceColor.needsUpdate = true;
  }
}

// Persistent rubber marks laid while tyres skid on tarmac.
class SkidMarks {
  constructor(max) {
    this.max = max; this.next = 0;
    this.pos = new Float32Array(max * 4 * 3);
    this.alpha = new Float32Array(max * 4);
    const idx = [];
    for (let i = 0; i < max; i++) idx.push(i * 4, i * 4 + 1, i * 4 + 2, i * 4 + 1, i * 4 + 3, i * 4 + 2);
    const g = new THREE.BufferGeometry();
    g.setAttribute('position', new THREE.BufferAttribute(this.pos, 3).setUsage(THREE.DynamicDrawUsage));
    g.setAttribute('alpha', new THREE.BufferAttribute(this.alpha, 1).setUsage(THREE.DynamicDrawUsage));
    g.setIndex(idx);
    this.mesh = new THREE.Mesh(g, new THREE.ShaderMaterial({
      transparent: true, depthWrite: false, polygonOffset: true, polygonOffsetFactor: -4, polygonOffsetUnits: -4,
      vertexShader: 'attribute float alpha; varying float vA; void main(){ vA = alpha; gl_Position = projectionMatrix * modelViewMatrix * vec4(position,1.0); }',
      fragmentShader: 'varying float vA; void main(){ gl_FragColor = vec4(0.02,0.02,0.02, vA); }',
    }));
    this.mesh.frustumCulled = false;
    this.mesh.renderOrder = 1;
    this.last = new Map();
  }
  add(key, p, width, strength, now) {
    const prev = this.last.get(key);
    this.last.set(key, { p: p.clone(), t: now });
    if (!prev || now - prev.t > 0.35 || prev.p.distanceTo(p) > 6 || prev.p.distanceTo(p) < 0.05) return;
    const dir = p.clone().sub(prev.p).setY(0).normalize();
    const side = new THREE.Vector3(-dir.z, 0, dir.x).multiplyScalar(width / 2);
    const i = this.next;
    this.next = (this.next + 1) % this.max;
    const quad = [prev.p.clone().add(side), prev.p.clone().sub(side), p.clone().add(side), p.clone().sub(side)];
    quad.forEach((q, k) => { this.pos.set([q.x, q.y + 0.02, q.z], (i * 4 + k) * 3); this.alpha[i * 4 + k] = 0.55 * strength; });
    this.mesh.geometry.attributes.position.needsUpdate = true;
    this.mesh.geometry.attributes.alpha.needsUpdate = true;
  }
  clear() { this.pos.fill(0); this.alpha.fill(0); this.last.clear(); this.mesh.geometry.attributes.position.needsUpdate = true; }
}

const _p = new THREE.Vector3();
const _v = new THREE.Vector3();
const _c = new THREE.Color();

export class Effects {
  constructor(scene) {
    this.group = new THREE.Group();
    scene.add(this.group);
    this.smoke = new ParticlePool(2500, softTexture('smoke'), false);
    this.glow = new ParticlePool(1500, softTexture('soft'), true);
    this.sparks = new SparkPool(1500);
    this.debris = new DebrisPool(400);
    this.skids = new SkidMarks(3000);
    this.group.add(this.skids.mesh, this.smoke.points, this.glow.points, this.sparks.lines, this.debris.mesh);
    this.flashes = [];
    for (let i = 0; i < 4; i++) {
      const light = new THREE.PointLight(0xffaa55, 0, 30, 2);
      this.group.add(light);
      this.flashes.push({ light, t: 1, dur: 1, intensity: 0 });
    }
    this.groundY = -250 * S;
    this.dirt = new THREE.Color(0.45, 0.38, 0.3);
    this.time = 0;
  }

  setStage(palette) {
    this.dirt = palette.ground.clone().multiplyScalar(0.8);
    this.smoke.material.uniforms.fogColor.value.copy(palette.fog);
    this.skids.clear();
  }

  setFog(fog) {
    for (const p of [this.smoke, this.glow]) { p.material.uniforms.fogNear.value = fog.near; p.material.uniforms.fogFar.value = fog.far; }
  }

  // ContO.dust: wheel contact puff. mag ~1.0 tarmac skid, >=1.1 dirt / landing.
  dust(carKey, wheel, x, y, z, vx, vz, mag, simag, onRoad) {
    toThree(x, y, z, _p);
    _v.set(vx * 25 * S * 0.35 + (Math.random() - 0.5), 0.6 + Math.random() * 0.8, -vz * 25 * S * 0.35 + (Math.random() - 0.5));
    const rel = mag / (simag || 1);
    if (onRoad && rel < 1.13) {
      const g = 0.75 + Math.random() * 0.15;
      _c.setRGB(g, g, g);
      this.smoke.spawn(_p, _v, _c, 0.35, 0.6 + Math.random() * 0.4, 2.2, 1.6 + Math.random(), 1.2, 0.4);
      this.skids.add(carKey * 4 + wheel, _p, 0.32, 1, this.time);
    } else {
      _c.copy(this.dirt).multiplyScalar(0.9 + Math.random() * 0.3);
      this.smoke.spawn(_p, _v, _c, 0.55, 0.7 + Math.random() * 0.5, 2.6 * rel, 1.4 + Math.random() * 0.8, 1.5, -1.5);
    }
  }

  // ContO.sprk: kind 0 wall scrape, 1 ground scrape, 2 car-to-car hit.
  spark(x, y, z, vx, vy, vz, kind) {
    toThree(x, y, z, _p);
    const n = kind === 2 ? 26 : 10;
    const bv = new THREE.Vector3(vx * 25 * S, -vy * 25 * S, -vz * 25 * S).multiplyScalar(0.5);
    for (let i = 0; i < n; i++) {
      _v.copy(bv).add(new THREE.Vector3((Math.random() - 0.5) * 9, Math.random() * 6, (Math.random() - 0.5) * 9));
      this.sparks.spawn(_p, _v, 0.25 + Math.random() * 0.5);
    }
    _c.setRGB(4, 2.2, 0.8);
    this.glow.spawn(_p, new THREE.Vector3(), _c, 0.6, kind === 2 ? 1.6 : 0.8, 2, 0.12);
    if (kind === 2) this.flash(_p, 0xffaa55, 25, 0.12);
  }

  // Flashes reuse a fixed pool of lights: adding/removing lights would force
  // every material to recompile (a visible hitch).
  flash(pos, color, intensity, dur) {
    let f = this.flashes.find((x) => x.t >= x.dur);
    if (!f) f = this.flashes.reduce((a, b) => (a.t / a.dur > b.t / b.dur ? a : b));
    f.light.color.set(color);
    f.light.position.copy(pos);
    f.t = 0; f.dur = dur; f.intensity = intensity;
  }

  chip(pos, color) {
    _v.set((Math.random() - 0.5) * 6, 2 + Math.random() * 4, (Math.random() - 0.5) * 6);
    this.debris.spawn(pos, _v, color, 0.6 + Math.random() * 0.6, 3);
  }

  explode(pos, colors) {
    this.flash(pos, 0xff8833, 400, 0.6);
    for (let i = 0; i < 60; i++) {
      _v.set((Math.random() - 0.5) * 14, Math.random() * 12, (Math.random() - 0.5) * 14);
      _c.setRGB(6, 2.5 + Math.random(), 0.6);
      this.glow.spawn(pos, _v, _c, 0.8, 1.5 + Math.random() * 2, 5, 0.6 + Math.random() * 0.6, 2.5, 2);
    }
    for (let i = 0; i < 40; i++) {
      _v.set((Math.random() - 0.5) * 6, 2 + Math.random() * 5, (Math.random() - 0.5) * 6);
      _c.setRGB(0.05, 0.045, 0.04);
      this.smoke.spawn(pos, _v, _c, 0.8, 2 + Math.random() * 2, 3.5, 3 + Math.random() * 2, 1.2, 1.2);
    }
    for (let i = 0; i < 40; i++) {
      _v.set((Math.random() - 0.5) * 18, 4 + Math.random() * 12, (Math.random() - 0.5) * 18);
      this.debris.spawn(pos, _v, colors[i % colors.length], 0.8 + Math.random() * 1.6, 6);
    }
    for (let i = 0; i < 50; i++) this.sparks.spawn(pos, new THREE.Vector3((Math.random() - 0.5) * 25, Math.random() * 18, (Math.random() - 0.5) * 25), 0.6 + Math.random());
  }

  // Ongoing smoke / fire from a damaged or burning car.
  burn(pos, damage, destroyed, dt) {
    const rate = destroyed ? 40 : damage > 0.85 ? 22 : damage > 0.55 ? 10 : 0;
    let count = rate * dt;
    while (count > 0) {
      if (Math.random() < count) {
        _v.set((Math.random() - 0.5) * 0.8, 1.5 + Math.random() * 1.5, (Math.random() - 0.5) * 0.8);
        const dark = destroyed ? 0.04 : 0.12 + (1 - damage) * 0.4;
        _c.setRGB(dark, dark * 0.97, dark * 0.95);
        this.smoke.spawn(pos, _v, _c, destroyed ? 0.75 : 0.45, 0.8, 2.4, 2.5 + Math.random() * 1.5, 0.8, 1.6);
        if (destroyed || damage > 0.85) {
          _v.set((Math.random() - 0.5) * 1.2, 1 + Math.random() * 2.5, (Math.random() - 0.5) * 1.2);
          _c.setRGB(5, 1.8 + Math.random() * 0.8, 0.4);
          this.glow.spawn(pos, _v, _c, 0.7, 0.9 + Math.random() * 0.8, -0.6, 0.45 + Math.random() * 0.35, 1, 2);
        }
      }
      count -= 1;
    }
  }

  update(dt) {
    this.time += dt;
    this.smoke.update(dt);
    this.glow.update(dt);
    this.sparks.update(dt, this.groundY);
    this.debris.update(dt, this.groundY);
    for (const f of this.flashes) {
      f.t += dt;
      f.light.intensity = f.intensity * Math.max(0, 1 - f.t / f.dur);
    }
  }

  setViewport(height, fovDeg) {
    const scale = height / (2 * Math.tan((fovDeg * Math.PI) / 360));
    this.smoke.material.uniforms.uScale.value = scale;
    this.glow.material.uniforms.uScale.value = scale;
  }
}
