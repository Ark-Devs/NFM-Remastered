import * as THREE from 'three';
import { Environment } from './environment.js';
import { StageView } from './stage.js';
import { CarView } from './car.js';
import { Effects } from './effects.js';
import { S, angleLerp, toThree, srgbToLinear } from './space.js';

const CAMERAS = [
  { name: 'Chase', dist: 520, height: 175, look: 40, lookUp: 1.0 },
  { name: 'Close', dist: 400, height: 135, look: 35, lookUp: 0.9 },
  { name: 'Classic NFM', dist: 800, height: 250, look: 60 },
  { name: 'Bumper', bumper: true },
];

function snapshot(c) {
  return { x: c.x, y: c.y, z: c.z, xz: c.xz, xy: c.xy, zy: c.zy, wxz: c.wxz };
}

// One Euro filter: heavy smoothing for small jitter, almost none for fast moves.
class OneEuro {
  constructor() { this.prev = null; this.dPrev = 0; this.raw = 0; }
  filter(x, dt, minCut, beta) {
    this.raw = x;
    if (this.prev === null) { this.prev = x; return x; }
    const alpha = (cut) => 1 / (1 + 1 / (2 * Math.PI * cut * dt));
    const dx = (x - this.prev) / dt;
    this.dPrev += alpha(1.0) * (dx - this.dPrev);
    const cut = minCut + beta * Math.abs(this.dPrev);
    this.prev += alpha(cut) * (x - this.prev);
    return this.prev;
  }
}

const _v = new THREE.Vector3();
const _w = new THREE.Vector3();
const _lin = [0, 0, 0];

export class GameView {
  constructor(renderer, world, race) {
    this.r = renderer;
    this.world = world;
    this.race = race;
    const scene = renderer.scene;
    this.env = new Environment(renderer.renderer, scene);
    this.envMap = this.env.build(world.medium, race.def.env, race.def.bounds, race.def, world.trackers);
    this.env.setShadowQuality(renderer.q);
    this.stage = new StageView(world, race.def, this.envMap);
    scene.add(this.stage.group);
    this.effects = new Effects(scene);
    this.effects.setStage(this.env.pal);
    this.effects.setFog(scene.fog);
    this.baseHidden = [this.env.sky, this.effects.smoke.points, this.effects.glow.points, this.effects.sparks.lines, this.effects.skids.mesh];
    renderer.hidden = this.baseHidden;
    this.cars = race.cars.map((c, i) => this.makeCar(i));
    this.prev = race.cars.map(snapshot);
    this.refreshHidden();
    this.curr = race.cars.map(snapshot);
    this.prevCxz = race.mads.map((m) => m.cxz);
    this.versions = Array.from(race.carVersion);
    this.camMode = 0;
    this.camPos = new THREE.Vector3();
    this.camLook = new THREE.Vector3();
    this.camInit = false;
    this.lookYaw = 0;
    this.shake = 0;
    this.time = 0;
    this.burnPos = new THREE.Vector3();
    // Visual suspension state per car (physics is untouched).
    this.susp = race.cars.map((c) => ({ y: new OneEuro(), xy: new OneEuro(), zy: new OneEuro(), comp: 0, compV: 0, roll: 0, rollV: 0, wasGround: true, vy: 0 }));
    this.headGroup = new THREE.Group();
    this.headGroup.matrixAutoUpdate = false;
    scene.add(this.headGroup);
    if (race.def.env.lightson) this.addHeadlights();
    this.hookEvents();
    this.onResize();
    // Compile every shader now so nothing compiles mid-race.
    renderer.renderer.compile(scene, renderer.camera);
  }

  makeCar(i) {
    const slot = this.race.sc[i];
    const smooth = this.r.bodyStyle === 'original' ? 0 : this.r.q.smooth ?? 1;
    const view = new CarView(this.race.cars[i], this.world.baseText[slot], this.envMap, smooth);
    this.r.scene.add(view.root);
    if (this.cars) queueMicrotask(() => this.refreshHidden());
    return view;
  }

  // Headlights live in their own group (following the player) so repairing the
  // car never adds lights to the scene.
  // Real reflections for the player's car: a cube camera renders the actual
  // surroundings (road, cars, walls) every few frames (High/Ultra only).
  updateProbe() {
    const q = this.r.q;
    if (!q.probe) {
      if (this.probe) { this.setPlayerEnv(this.envMap); this.probe.rt.dispose(); this.probe = null; }
      return;
    }
    if (!this.probe || this.probe.size !== q.probe) {
      this.probe?.rt.dispose();
      const rt = new THREE.WebGLCubeRenderTarget(q.probe, { type: THREE.HalfFloatType, generateMipmaps: true, minFilter: THREE.LinearMipmapLinearFilter });
      const cam = new THREE.CubeCamera(0.5, 1500, rt);
      this.probe = { rt, cam, size: q.probe, n: 0, owner: null };
    }
    const p = this.probe;
    if (p.owner !== this.cars[0]) { p.owner = this.cars[0]; this.setPlayerEnv(p.rt.texture); }
    if (p.n++ % q.probeEvery !== 0) return;
    const car = this.cars[0];
    p.cam.position.setFromMatrixPosition(car.root.matrix);
    p.cam.position.y += 0.6;
    car.root.visible = false;
    const fx = this.effects.group.visible;
    this.effects.group.visible = false;
    const sm = this.r.renderer.shadowMap;
    const auto = sm.autoUpdate;
    sm.autoUpdate = false; // reuse this frame's shadow map
    // capture with the neutral (desaturated) sky so stylised sky colours don't
    // dye the paint; real objects (cars, track, ground) are still reflected
    const u = this.env.skyMat.uniforms, lit = this.env.lit;
    const saved = [u.uSky.value.clone(), u.uHorizon.value.clone(), u.uCloud.value.clone(), u.uGround.value.clone()];
    u.uSky.value.copy(lit.sky); u.uHorizon.value.copy(lit.fog); u.uCloud.value.copy(lit.clouds); u.uGround.value.copy(lit.ground).multiplyScalar(0.5);
    // terrain, scenery and track pieces are left out so the stage can't tint the car
    const env = this.env;
    if (env.ground) env.ground.visible = false;
    if (env.scatter) env.scatter.visible = false;
    this.stage.group.visible = false;
    p.cam.update(this.r.renderer, this.r.scene);
    if (env.ground) env.ground.visible = true;
    if (env.scatter) env.scatter.visible = true;
    this.stage.group.visible = true;
    u.uSky.value.copy(saved[0]); u.uHorizon.value.copy(saved[1]); u.uCloud.value.copy(saved[2]); u.uGround.value.copy(saved[3]);
    sm.autoUpdate = auto;
    p.rt.texture.needsPMREMUpdate = true; // re-filter for rough reflections
    this.effects.group.visible = fx;
    car.root.visible = true;
  }

  setPlayerEnv(tex) {
    const car = this.cars[0];
    for (const m of [car.bodyMat, car.glassMat]) { m.envMap = tex; m.envMapIntensity = tex === this.envMap ? m.userData.baseEnv ?? m.envMapIntensity : (m.userData.baseEnv ??= m.envMapIntensity) * 0.85; m.needsUpdate = true; }
  }

  // Glow sprites and contact-shadow quads must not feed the AO normal pass.
  refreshHidden() {
    this.r.hidden = [...this.baseHidden, ...this.cars.flatMap((c) => [...c.glows, c.contact])];
  }

  addHeadlights() {
    const car = { root: this.headGroup };
    for (const side of [-0.6, 0.6]) {
      const spot = new THREE.SpotLight(0xfff2d6, 120, 120, 0.42, 0.45, 1.6);
      spot.position.set(side, 0.4, -2.2);
      spot.target.position.set(side * 1.5, -0.8, -30);
      spot.castShadow = side < 0;
      spot.shadow.mapSize.set(1024, 1024);
      car.root.add(spot, spot.target);
    }
  }

  hookEvents() {
    const ev = this.race.events;
    const playerCar = () => this.race.cars[0];
    ev.on('dust', (conto, wheel, x, y, z, vx, vz, mag, tilt, capsized) => {
      const idx = this.race.cars.indexOf(conto);
      if (idx < 0) return;
      if (!this.nearCamera(x, y, z)) return;
      const onRoad = this.surfaceAt(conto) === 0;
      this.effects.dust(idx, wheel, x, y, z, vx, vz, mag, this.world.cd.simag[this.race.mads[idx].cn], onRoad);
    });
    ev.on('spark', (conto, x, y, z, vx, vy, vz, kind) => {
      if (!this.nearCamera(x, y, z)) return;
      this.effects.spark(x, y, z, vx, vy, vz, kind);
    });
    ev.on('crash', (f) => {
      this.shake = Math.min(1.2, this.shake + Math.abs(f) / 300);
      // body jolts on its springs from the hit
      const s = this.susp[0];
      const a = Math.min(220, Math.abs(f));
      s.rollV += (Math.random() < 0.5 ? -1 : 1) * a * 0.35;
      s.compV += a * 0.6;
      void playerCar;
    });
    ev.on('wasted', (i) => {
      const c = this.race.cars[i];
      toThree(c.x, c.y, c.z, _v);
      const cols = [];
      for (let k = 0; k < c.npl && cols.length < 8; k += 7) {
        const p = c.p[k];
        if (!p) continue;
        srgbToLinear(p.c[0], p.c[1], p.c[2], _lin);
        cols.push(new THREE.Color(_lin[0], _lin[1], _lin[2]));
      }
      this.effects.explode(_v, cols.length ? cols : [new THREE.Color(0.2, 0.2, 0.2)]);
      if (i === 0) this.shake = 1.5;
    });
  }

  // Which skid type (0 road, 1 dirt...) the car is over, like Mad.drive's n24 lookup.
  surfaceAt(conto) {
    const t = this.world.trackers;
    if (!t.sect) return 0;
    let sx = Math.trunc((conto.x - t.sx) / 3000); sx = Math.max(0, Math.min(t.ncx, sx));
    let sz = Math.trunc((conto.z - t.sz) / 3000); sz = Math.max(0, Math.min(t.ncz, sz));
    let skd = 1;
    for (const n of t.sect[sx][sz]) {
      if (Math.abs(t.zy[n]) === 90 || Math.abs(t.xy[n]) === 90) continue;
      if (Math.abs(conto.x - t.x[n]) >= t.radx[n] || Math.abs(conto.z - t.z[n]) >= t.radz[n]) continue;
      skd = t.skd[n];
    }
    return skd;
  }

  nearCamera(x, y, z) {
    toThree(x, y, z, _w);
    return _w.distanceToSquared(this.r.camera.position) < 250 * 250;
  }

  onResize() {
    const h = this.r.renderer.getDrawingBufferSize(new THREE.Vector2()).y;
    this.effects.setViewport(h, this.r.camera.fov);
  }

  cycleCamera() {
    this.camMode = (this.camMode + 1) % CAMERAS.length;
    this.camInit = false;
    return CAMERAS[this.camMode].name;
  }

  beforeStep() {
    this.prev = this.race.cars.map(snapshot);
    this.prevCxz = this.race.mads.map((m) => m.cxz);
  }

  afterStep() {
    const race = this.race;
    this.curr = race.cars.map(snapshot);
    for (let i = 0; i < race.nplayers; i++) {
      if (race.carVersion[i] !== this.versions[i]) {
        // repaired: same model topology, so just reload the clean shape in place
        // (rebuilding the whole mesh caused a hitch)
        this.cars[i].conto = race.cars[i];
        this.cars[i].checksum = NaN;
        this.versions[i] = race.carVersion[i];
      }
      const conto = race.cars[i];
      // far-away cars refresh their dents less often (they're tiny on screen)
      toThree(conto.x, conto.y, conto.z, _w);
      const far = _w.distanceToSquared(this.r.camera.position) > 120 * 120;
      if (!far || (race.tick + i) % 4 === 0) this.cars[i].syncDamage(conto);
      // Landing: compress the suspension in proportion to the fall speed.
      const s = this.susp[i];
      const vy = this.curr[i].y - this.prev[i].y; // NFM units per tick, + is falling
      const ground = race.mads[i].mtouch;
      if (ground && !s.wasGround && s.vy > 5) s.compV += Math.min(s.vy, 70) * 25 * 0.32;
      s.wasGround = ground;
      s.vy = vy;
      // Plane.chip: a panel just took a hit, throw off a fragment.
      let chips = 0;
      for (let k = 0; k < conto.npl; k++) {
        const p = conto.p[k];
        if (!p || p.chip !== 1) continue;
        p.chip = 0;
        if (chips++ > 5 || p.wz !== 0) continue;
        const v = Math.floor(Math.random() * p.n);
        _v.set(p.ox[v] * S, -p.oy[v] * S, -p.oz[v] * S).applyMatrix4(this.cars[i].root.matrix);
        srgbToLinear(p.c[0], p.c[1], p.c[2], _lin);
        this.effects.chip(_v, new THREE.Color(_lin[0], _lin[1], _lin[2]));
      }
    }
  }

  // Body pose = physics pose with high-frequency jitter filtered out, plus a
  // sprung compression/roll. Wheels keep the exact physics pose.
  suspension(i, st, dt) {
    const s = this.susp[i];
    if (dt <= 0) return { ...st, y: st.y + s.comp, xy: st.xy + s.roll };
    const unwrap = (f, raw) => (f.prev === null ? raw : f.prev + (((raw - f.prev) % 360) + 540) % 360 - 180);
    const fy = s.y.filter(st.y, dt, 1.4, 0.012);
    const fxy = s.xy.filter(unwrap(s.xy, st.xy), dt, 1.6, 0.03);
    const fzy = s.zy.filter(unwrap(s.zy, st.zy), dt, 1.6, 0.03);
    // never drift far from the true pose (flips, big hits stay exact)
    const clampTo = (v, raw, lim) => Math.max(raw - lim, Math.min(raw + lim, v));
    const yy = clampTo(fy, st.y, 22);
    const xy = clampTo(fxy, s.xy.raw, 9);
    const zy = clampTo(fzy, s.zy.raw, 9);
    // spring: compression (NFM units, + = body lower) and roll kick (degrees)
    const w = 15, z = 0.32;
    s.compV += (-w * w * s.comp - 2 * z * w * s.compV) * dt;
    s.comp = Math.max(-25, Math.min(35, s.comp + s.compV * dt));
    // weight transfer: squat/dive from acceleration, lean from cornering (grounded only)
    const mad = this.race.mads[i];
    const grounded = mad.mtouch && !mad.capsized;
    const spd = this.race.countdown === 0 ? mad.speed : 0;
    const accel = (spd - (s.lastSpeed ?? spd)) / dt; // speed units per second
    s.lastSpeed = spd;
    s.accelF = (s.accelF ?? 0) + (accel - (s.accelF ?? 0)) * Math.min(1, dt * 6);
    const pitchT = grounded ? Math.max(-3.5, Math.min(2.5, s.accelF * 0.012)) : 0;
    const leanT = grounded ? Math.max(-4.5, Math.min(4.5, st.wxz * Math.min(1, Math.abs(spd) / 160) * 0.13)) : 0;
    const wr = 11, zr = 0.3;
    s.rollV += (-wr * wr * (s.roll - leanT) - 2 * zr * wr * s.rollV) * dt;
    s.roll = Math.max(-9, Math.min(9, s.roll + s.rollV * dt));
    s.pitch = s.pitch ?? 0; s.pitchV = s.pitchV ?? 0;
    s.pitchV += (-wr * wr * (s.pitch - pitchT) - 2 * 0.4 * wr * s.pitchV) * dt;
    s.pitch = Math.max(-6, Math.min(6, s.pitch + s.pitchV * dt));
    return { ...st, y: yy + s.comp, xy: xy + s.roll, zy: zy - s.comp * 0.06 + s.pitch };
  }

  frame(dt, alpha, input) {
    this.time += dt;
    const race = this.race;
    const cam = this.r.camera;
    for (let i = 0; i < race.nplayers; i++) {
      const a = this.prev[i], b = this.curr[i];
      const st = {
        x: a.x + (b.x - a.x) * alpha, y: a.y + (b.y - a.y) * alpha, z: a.z + (b.z - a.z) * alpha,
        xz: angleLerp(a.xz, b.xz, alpha), xy: angleLerp(a.xy, b.xy, alpha), zy: angleLerp(a.zy, b.zy, alpha), wxz: a.wxz + (b.wxz - a.wxz) * alpha,
      };
      const mad = race.mads[i];
      const ctl = race.controls[i];
      const braking = (ctl.down && mad.speed > 5) || ctl.handb;
      const fixing = race.cars[i].fix;
      const body = this.suspension(i, st, dt);
      this.cars[i].update(st, dt, race.countdown === 0 ? mad.speed : 0, this.world.cd.simag[mad.cn], braking, fixing, body, (mad.mtouch || mad.wtouch) && !mad.capsized, race.def.env.lightson);
      if (i === 0) {
        this.playerState = body;
        this.headGroup.matrix.copy(this.cars[0].root.matrix);
        this.headGroup.matrixWorldNeedsUpdate = true;
      }
      // smoke and fire from heavy damage
      const dmg = mad.hitmag / this.world.cd.maxmag[mad.cn];
      if (dmg > 0.55 || mad.dest) {
        const conto = race.cars[i];
        this.burnPos.set(0, -conto.grat * S * 0.2 + 0.3, -(conto.keyz[0] || 60) * S * 0.75).applyMatrix4(this.cars[i].root.matrix);
        if (this.burnPos.distanceToSquared(cam.position) < 300 * 300) this.effects.burn(this.burnPos, Math.min(1, dmg), mad.dest, dt);
      }
    }
    this.updateCamera(dt, alpha, input);
    this.updateProbe();
    const next = (race.player.clear % race.cp.nsp) + 1;
    this.stage.update(this.time, next, race.player.clear === race.cp.nlaps * race.cp.nsp - 1);
    this.env.update(cam, this.camLook, this.time);
    this.effects.update(dt);
    this.shake = Math.max(0, this.shake - dt * 2.2);
  }

  // Stage-select preview: no racing, the camera circles the whole stage.
  frameOrbit(dt) {
    this.time += dt;
    if (!this.orbit) {
      let x0 = Infinity, x1 = -Infinity, z0 = Infinity, z1 = -Infinity;
      for (const o of this.race.def.objects) {
        if (o.pile) continue;
        x0 = Math.min(x0, o.conto.x); x1 = Math.max(x1, o.conto.x);
        z0 = Math.min(z0, o.conto.z); z1 = Math.max(z1, o.conto.z);
      }
      if (!Number.isFinite(x0)) { x0 = z0 = -2000; x1 = z1 = 2000; }
      const r = Math.max(x1 - x0, z1 - z0, 3000) * 0.5 * S;
      this.orbit = { c: toThree((x0 + x1) / 2, 0, (z0 + z1) / 2, new THREE.Vector3()), r, a: 0.7 };
      for (let i = 0; i < this.race.nplayers; i++) {
        this.cars[i].update(this.curr[i], 0, 0, 1, false, false, this.curr[i], true, this.race.def.env.lightson);
      }
    }
    const o = this.orbit;
    o.a += dt * 0.06;
    const cam = this.r.camera;
    const d = o.r * 1.3 + 40;
    cam.position.set(o.c.x + Math.sin(o.a) * d, o.c.y + o.r * 0.55 + 25, o.c.z + Math.cos(o.a) * d);
    this.camLook.copy(o.c);
    cam.lookAt(o.c);
    if (cam.fov !== 50) { cam.fov = 50; cam.updateProjectionMatrix(); }
    this.stage.update(this.time, 0, false);
    this.env.update(cam, this.camLook, this.time);
  }

  updateCamera(dt, alpha, input) {
    const cam = this.r.camera;
    const race = this.race;
    const st = this.playerState;
    const mode = CAMERAS[this.camMode];
    const cxz = angleLerp(this.prevCxz[0], race.mads[0].cxz, alpha);
    // look back swings round like Medium.follow's bcxz
    const targetYaw = input.lookback ? 180 : 0;
    this.lookYaw += (targetYaw - this.lookYaw) * Math.min(1, dt * 10);
    const car = toThree(st.x, st.y, st.z, new THREE.Vector3());
    if (mode.bumper) {
      const m = this.cars[0].root.matrix;
      const conto = race.cars[0];
      const front = Math.max(...conto.keyz) * S;
      const pos = new THREE.Vector3(0, 0.35 - conto.grat * S * 0.1, -front * 0.6).applyMatrix4(m);
      const look = new THREE.Vector3(0, 0.1, -front * 0.6 - 20).applyMatrix4(m);
      if (this.lookYaw > 90) look.copy(pos).multiplyScalar(2).sub(look);
      cam.position.copy(pos);
      cam.lookAt(look);
      this.camLook.copy(car);
    } else {
      // Camera is held rigidly at a fixed distance/height from the car; only the
      // heading is smoothed. (Smoothing the position made it trail further
      // behind - and sit lower - the faster you went.)
      const target = cxz + this.lookYaw;
      if (!this.camInit) { this.camYaw = target; this.camInit = true; }
      this.camYaw = angleLerp(this.camYaw, target, 1 - Math.exp(-dt * 7));
      const n = (this.camYaw * Math.PI) / 180;
      // NFM forward for heading n is (-sin n, 0, cos n); the camera sits behind it.
      const fwd = new THREE.Vector3(-Math.sin(n), 0, -Math.cos(n));
      const pos = car.clone().addScaledVector(fwd, -mode.dist * S);
      pos.y = car.y + mode.height * S;
      const look = car.clone().addScaledVector(fwd, mode.look * S);
      look.y += mode.lookUp ?? 0.9;
      this.camPos.copy(pos);
      this.camLook.copy(look);
      cam.position.copy(pos);
      cam.lookAt(look);
    }
    if (this.shake > 0) {
      const s = this.shake * 0.12;
      cam.position.x += (Math.random() - 0.5) * s;
      cam.position.y += (Math.random() - 0.5) * s;
      cam.rotation.z += (Math.random() - 0.5) * s * 0.15;
    }
    // speed FOV kick
    const speed = Math.abs(race.mads[0].speed);
    const fov = 58 + Math.min(5, speed / 60);
    if (Math.abs(cam.fov - fov) > 0.05) { cam.fov += (fov - cam.fov) * Math.min(1, dt * 3); cam.updateProjectionMatrix(); }
  }

  dispose() {
    const scene = this.r.scene;
    for (const c of this.cars) { scene.remove(c.root); c.dispose(); }
    scene.remove(this.stage.group);
    this.stage.dispose();
    scene.remove(this.effects.group);
    this.env.dispose();
    scene.fog = null;
    scene.environment = null;
  }
}
