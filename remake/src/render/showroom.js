import * as THREE from 'three';
import { CarView } from './car.js';
import { ContO } from '../gen/ContO.js';
import { S } from './space.js';

// A photo studio: dark cyclorama, long overhead softboxes (they draw the
// highlight lines across the paint), a turntable, and a key light for shadows.
function studioEnvironment(renderer) {
  const scene = new THREE.Scene();
  const room = new THREE.Mesh(new THREE.BoxGeometry(40, 16, 40), new THREE.MeshBasicMaterial({ color: 0x101114, side: THREE.BackSide }));
  room.position.y = 7;
  scene.add(room);
  const floor = new THREE.Mesh(new THREE.PlaneGeometry(40, 40).rotateX(-Math.PI / 2), new THREE.MeshBasicMaterial({ color: 0x050506 }));
  floor.position.y = -0.9;
  scene.add(floor);
  const panel = (w, h, intensity, x, y, z, rx, ry) => {
    const m = new THREE.Mesh(new THREE.PlaneGeometry(w, h), new THREE.MeshBasicMaterial({ color: new THREE.Color(1, 1, 1).multiplyScalar(intensity), side: THREE.DoubleSide }));
    m.position.set(x, y, z); m.rotation.set(rx, ry, 0);
    scene.add(m);
  };
  // three long strips overhead, two tall softboxes either side, a dim back wall card
  for (const x of [-2.6, 0, 2.6]) panel(1.1, 18, 7, x, 9, 0, Math.PI / 2, 0);
  panel(5, 7, 2.2, -12, 4, 3, 0, Math.PI / 2);
  panel(5, 7, 1.4, 12, 4, -3, 0, -Math.PI / 2);
  panel(20, 3, 0.35, 0, 2.5, -19, 0, 0);
  const pmrem = new THREE.PMREMGenerator(renderer);
  const rt = pmrem.fromScene(scene, 0.02);
  pmrem.dispose();
  return rt;
}

const easeOut = (t) => 1 - Math.pow(1 - t, 3);

export class Showroom {
  constructor(renderer) {
    this.r = renderer;
    this.group = new THREE.Group();
    this.envRT = studioEnvironment(renderer.renderer);
    this.envMap = this.envRT.texture;

    const floor = new THREE.Mesh(
      new THREE.CircleGeometry(40, 96).rotateX(-Math.PI / 2),
      new THREE.MeshPhysicalMaterial({ color: 0x060607, roughness: 0.6, metalness: 0, clearcoat: 0.2, clearcoatRoughness: 0.35, envMap: this.envMap, envMapIntensity: 0.08 }),
    );
    floor.receiveShadow = true;
    this.group.add(floor);
    // turntable: a low brushed-metal disc with a thin lit seam at its base
    const TT = 4.6;
    const table = new THREE.Mesh(
      new THREE.CylinderGeometry(TT, TT + 0.04, 0.07, 128),
      new THREE.MeshStandardMaterial({ color: 0x1a1b1f, roughness: 0.38, metalness: 0.8, envMap: this.envMap, envMapIntensity: 0.6 }),
    );
    table.position.y = 0.035;
    table.receiveShadow = true;
    this.group.add(table);
    const seam = new THREE.Mesh(new THREE.TorusGeometry(TT + 0.06, 0.012, 6, 160).rotateX(Math.PI / 2), new THREE.MeshBasicMaterial({ color: 0x9aa3b5 }));
    seam.position.y = 0.006;
    this.group.add(seam);
    this.lift = 0.07;

    const key = new THREE.SpotLight(0xffffff, 230, 40, 0.42, 0.85, 1.5);
    key.position.set(3, 13, 5);
    key.castShadow = true;
    key.shadow.mapSize.set(2048, 2048);
    key.shadow.bias = -0.0002;
    key.shadow.radius = 4;
    this.group.add(key, key.target);
    const rim = new THREE.DirectionalLight(0xc9d6ff, 1.1);
    rim.position.set(-7, 5, -9);
    this.group.add(rim);
    this.group.add(new THREE.HemisphereLight(0xd8e2ff, 0x0a0a0a, 0.22));

    this.turn = 0.6;
    this.cars = []; // { view, group, x0, x1, t, leaving }
    this.mode = 'select';
    this.active = false;
    this.silhouette = new THREE.MeshStandardMaterial({ color: 0x050506, roughness: 0.3, metalness: 0.4, envMap: this.envMap, envMapIntensity: 0.35 });
  }

  // dir: -1/1 slides the new car in from that side; 0 swaps in place
  show(world, slot, { dir = 0, locked = false } = {}) {
    for (const c of this.cars) if (!c.leaving) { c.leaving = true; c.x0 = c.group.position.x; c.x1 = -dir * 16; c.t = dir ? 0 : 1; }
    const base = world.base[slot];
    const conto = ContO.create1(base, 0, 0, 0, 0);
    const view = new CarView(conto, world.baseText[slot], this.envMap, this.r.bodyStyle === 'original' ? 0 : 2);
    view.root.traverse((o) => {
      const m = o.material;
      if (!m || o === view.contact) return;
      if (locked) {
        if (view.glows.includes(o)) { o.visible = false; return; }
        if (o.isMesh) o.material = this.silhouette;
      } else if (m.envMap) {
        // a studio reflection map is far brighter than an outdoor sky: tone it
        // down so paint reads as paint, not chrome
        m.envMapIntensity *= 0.45;
      }
    });
    if (locked) view.glows.forEach((g) => { g.visible = false; });
    // stand the car on the turntable: wheel bottoms sit at y = grat in NFM space
    view.update({ x: 0, y: -base.grat - this.lift / S, z: 0, xz: 0, xy: 0, zy: 0, wxz: 0 }, 0, 0, 1, false, false);
    // centre the car's footprint on the turntable (model origins aren't centred)
    const box = new THREE.Box3().setFromObject(view.root);
    const inner = new THREE.Group();
    inner.position.set(-(box.min.x + box.max.x) / 2, 0, -(box.min.z + box.max.z) / 2);
    inner.add(view.root);
    const group = new THREE.Group();
    group.add(inner);
    group.position.x = dir * 16;
    this.group.add(group);
    this.cars.push({ view, group, x0: dir * 16, x1: 0, t: dir ? 0 : 1, leaving: false, locked });
    this.carSize = base.maxR * S;
    this.slot = slot;
    this.locked = locked;
    this.updateHidden();
  }

  updateHidden() {
    this.r.hidden = this.cars.flatMap((c) => [...c.view.glows, c.view.contact]);
  }

  enter(mode = 'select') {
    this.mode = mode;
    const scene = this.r.scene;
    if (!this.active) scene.add(this.group);
    scene.environment = this.envMap;
    scene.environmentIntensity = 0.5;
    this.r.renderer.toneMappingExposure = 0.85;
    scene.background = new THREE.Color(0x060708);
    scene.fog = new THREE.Fog(0x060708, 16, 42);
    this.updateHidden();
    this.active = true;
  }

  leave() {
    const scene = this.r.scene;
    scene.remove(this.group);
    scene.background = null;
    scene.fog = null;
    scene.environment = null;
    this.r.renderer.toneMappingExposure = 1.0;
    this.r.camera.filmOffset = 0;
    this.r.camera.updateProjectionMatrix();
    this.active = false;
  }

  frame(dt, time) {
    if (!this.active) return;
    this.turn += dt * (this.mode === 'menu' ? 0.12 : 0.3);
    for (const c of [...this.cars]) {
      c.t = Math.min(1, c.t + dt / 0.55);
      c.group.position.x = c.x0 + (c.x1 - c.x0) * easeOut(c.t);
      c.group.rotation.y = this.turn;
      c.view.update({ x: 0, y: -c.view.conto.grat - this.lift / S, z: 0, xz: 0, xy: 0, zy: 0, wxz: c.locked ? 0 : Math.sin(time * 0.7) * 20 }, dt, 0, 1, false, false);
      if (c.leaving && c.t >= 1) {
        this.group.remove(c.group);
        c.view.dispose();
        this.cars.splice(this.cars.indexOf(c), 1);
        this.updateHidden();
      }
    }
    const cam = this.r.camera;
    const size = this.carSize || 3;
    let fov = 34, offset = 0;
    if (this.mode === 'menu') {
      // low three-quarter, the car sitting right of the menu
      const d = Math.max(11, size * 3.8);
      const a = 0.25 + Math.sin(time * 0.05) * 0.15;
      cam.position.set(Math.sin(a) * d, 1.0, Math.cos(a) * d);
      cam.lookAt(0, 0.9, 0);
      offset = -6;
      fov = 32;
    } else if (this.mode === 'reveal') {
      const d = Math.max(7, size * 2.6);
      cam.position.set(Math.sin(0.5) * d, d * 0.16, Math.cos(0.5) * d);
      cam.lookAt(0, 0.5, 0);
    } else {
      // car selection: centred, a touch high in frame to leave room for the info below
      const d = Math.max(10, size * 3.4);
      cam.position.set(Math.sin(0.35) * d, d * 0.2, Math.cos(0.35) * d);
      cam.lookAt(0, -0.2, 0);
    }
    if (cam.fov !== fov || cam.filmOffset !== offset) { cam.fov = fov; cam.filmOffset = offset; cam.updateProjectionMatrix(); }
  }
}
