// Extra detail the original models never had: a cabin interior behind the
// glass and a proper underside (floor, exhaust, driveshaft, suspension, brakes).
import * as THREE from 'three';
import { S } from './space.js';

const M = {
  cabin: new THREE.MeshStandardMaterial({ color: 0x0d0d0e, roughness: 0.9, side: THREE.BackSide }),
  seat: new THREE.MeshStandardMaterial({ color: 0x1b1b1d, roughness: 0.75 }),
  seatTrim: new THREE.MeshStandardMaterial({ color: 0x5a1010, roughness: 0.6 }),
  dash: new THREE.MeshStandardMaterial({ color: 0x141416, roughness: 0.55, metalness: 0.1 }),
  wheel: new THREE.MeshStandardMaterial({ color: 0x0c0c0c, roughness: 0.5 }),
  floor: new THREE.MeshStandardMaterial({ color: 0x1a1a1a, roughness: 0.85, metalness: 0.3 }),
  metal: new THREE.MeshStandardMaterial({ color: 0x6b6660, roughness: 0.45, metalness: 0.85 }),
  rusty: new THREE.MeshStandardMaterial({ color: 0x4a3a2e, roughness: 0.8, metalness: 0.5 }),
  dark: new THREE.MeshStandardMaterial({ color: 0x0a0a0a, roughness: 0.9 }),
  spring: new THREE.MeshStandardMaterial({ color: 0xc8a020, roughness: 0.4, metalness: 0.7 }),
  caliper: new THREE.MeshStandardMaterial({ color: 0xb01818, roughness: 0.35, metalness: 0.3 }),
};

function box(w, h, d, mat, x, y, z) {
  const m = new THREE.Mesh(new THREE.BoxGeometry(w, h, d), mat);
  m.position.set(x, y, z);
  m.castShadow = true;
  return m;
}

function cyl(r, len, mat, axis = 'z', seg = 12) {
  const m = new THREE.Mesh(new THREE.CylinderGeometry(r, r, len, seg), mat);
  if (axis === 'z') m.rotation.x = Math.PI / 2;
  if (axis === 'x') m.rotation.z = Math.PI / 2;
  m.castShadow = true;
  return m;
}

// Interior fitted under the glass. Front of the car is -z in three space.
export function addInterior(view) {
  const glass = view.parts.glass?.geometry;
  if (!glass) return null;
  glass.computeBoundingBox();
  const gb = glass.boundingBox.clone();
  const size = gb.getSize(new THREE.Vector3());
  if (size.x < 0.6 || size.z < 0.5) return null;
  const g = new THREE.Group();
  const cx = (gb.min.x + gb.max.x) / 2;
  const w = size.x * 0.82;
  const floorY = gb.min.y - Math.min(0.45, size.y * 0.9);
  const topY = gb.max.y - 0.06;
  const zFront = gb.min.z + size.z * 0.12, zBack = gb.max.z - size.z * 0.08;
  const len = zBack - zFront;
  // dark cabin tub (seen from inside: back faces)
  const tub = new THREE.Mesh(new THREE.BoxGeometry(w, topY - floorY, len), M.cabin);
  tub.position.set(cx, (topY + floorY) / 2, (zFront + zBack) / 2);
  g.add(tub);
  // dashboard
  const dashH = Math.min(0.2, (topY - floorY) * 0.35);
  g.add(box(w * 0.98, dashH, Math.min(0.35, len * 0.22), M.dash, cx, floorY + (topY - floorY) * 0.45, zFront + Math.min(0.17, len * 0.11)));
  // seats (driver on the left, -x)
  const seatW = Math.min(0.5, w * 0.36);
  const seatZ = zFront + len * 0.62;
  const sh = (topY - floorY);
  for (const sx of [-1, 1]) {
    const x = cx + sx * w * 0.24;
    g.add(box(seatW, sh * 0.16, len * 0.28, M.seat, x, floorY + sh * 0.15, seatZ - len * 0.05));
    const back = box(seatW, sh * 0.62, 0.1, M.seat, x, floorY + sh * 0.45, seatZ + len * 0.1);
    back.rotation.x = -0.18;
    g.add(back);
    g.add(box(seatW * 0.5, sh * 0.5, 0.11, M.seatTrim, x, floorY + sh * 0.46, seatZ + len * 0.1 - 0.005));
    g.add(box(seatW * 0.6, sh * 0.14, 0.12, M.seat, x, floorY + sh * 0.84, seatZ + len * 0.13));
  }
  // steering wheel + column on the driver's side
  const swR = Math.min(0.18, seatW * 0.4);
  const sw = new THREE.Mesh(new THREE.TorusGeometry(swR, swR * 0.12, 8, 20), M.wheel);
  sw.position.set(cx - w * 0.24, floorY + sh * 0.6, zFront + len * 0.3);
  sw.rotation.x = -0.35;
  g.add(sw);
  const col = cyl(0.025, len * 0.2, M.wheel, 'z');
  col.position.set(sw.position.x, sw.position.y - 0.03, sw.position.z - len * 0.1);
  g.add(col);
  // gear lever / centre console
  g.add(box(w * 0.14, sh * 0.25, len * 0.35, M.dash, cx, floorY + sh * 0.12, zFront + len * 0.45));
  view.bodyGroup.add(g);
  return g;
}

// Underbody in the body frame, suspension and brakes in each wheel's frame.
export function addUnderbody(view, conto) {
  const body = view.parts.body?.geometry;
  if (!body) return;
  body.computeBoundingBox();
  const bb = body.boundingBox;
  const wheels = view.wheels.map((g) => g.userData.w);
  if (!wheels.length) return;
  const xs = wheels.map((w) => Math.abs(w.x) * S);
  const zs = wheels.map((w) => -w.z * S);
  const halfTrack = Math.min(...xs);
  const zF = Math.min(...zs), zR = Math.max(...zs);
  const axleY = -Math.max(...wheels.map((w) => w.y)) * S;
  const floorY = Math.max(bb.min.y + 0.03, axleY - 0.05);
  const g = new THREE.Group();
  const floorW = Math.max(0.4, halfTrack * 1.5);
  const floorL = Math.max(0.6, (zR - zF) * 1.05);
  g.add(box(floorW, 0.05, floorL, M.floor, 0, floorY, (zF + zR) / 2));
  // exhausts running back to tips just past the rear
  for (const sx of [-1, 1]) {
    const len = (bb.max.z - (zF + zR) / 2) * 0.98;
    const pipe = cyl(0.045, len, M.rusty, 'z');
    pipe.position.set(sx * floorW * 0.3, floorY - 0.07, (zF + zR) / 2 + len / 2);
    g.add(pipe);
    const muff = cyl(0.11, 0.5, M.metal, 'z', 16);
    muff.position.set(sx * floorW * 0.3, floorY - 0.08, zR - 0.1);
    g.add(muff);
    const tip = cyl(0.06, 0.12, M.metal, 'z', 16);
    tip.position.set(sx * floorW * 0.3, floorY - 0.07, bb.max.z + 0.02);
    g.add(tip);
    const hole = new THREE.Mesh(new THREE.CircleGeometry(0.048, 16), M.dark);
    hole.position.set(sx * floorW * 0.3, floorY - 0.07, bb.max.z + 0.081);
    g.add(hole);
  }
  // driveshaft, sump, gearbox, fuel tank
  const shaft = cyl(0.04, (zR - zF) * 0.8, M.metal, 'z');
  shaft.position.set(0, floorY - 0.05, (zF + zR) / 2);
  g.add(shaft);
  g.add(box(floorW * 0.35, 0.12, 0.45, M.rusty, 0, floorY - 0.08, zF + 0.35));
  g.add(box(floorW * 0.25, 0.14, 0.4, M.metal, 0, floorY - 0.08, zF + 0.85));
  g.add(box(floorW * 0.55, 0.12, 0.4, M.floor, 0, floorY - 0.07, zR - 0.45));
  view.bodyGroup.add(g);

  // per-wheel: control arms to the chassis and a coil-over
  for (const wg of view.wheels) {
    const w = wg.userData.w;
    const out = w.x < 0 ? -1 : 1;
    const wx = Math.abs(w.x) * S;
    const r = 12.557 * w.height * S;
    const reach = Math.max(0.15, wx - floorW * 0.45);
    for (const dy of [-r * 0.35, r * 0.25]) {
      const arm = box(reach, 0.035, 0.07, M.metal, -out * reach / 2, dy, 0);
      wg.add(arm);
    }
    const coil = new THREE.Mesh(new THREE.CylinderGeometry(0.06, 0.06, r * 1.1, 10, 1, true), M.spring);
    coil.position.set(-out * reach * 0.55, r * 0.35, 0.06);
    coil.rotation.z = out * 0.25;
    wg.add(coil);
    const damper = cyl(0.025, r * 1.2, M.metal, 'y');
    damper.position.copy(coil.position);
    damper.rotation.z = coil.rotation.z;
    wg.add(damper);
  }
}
