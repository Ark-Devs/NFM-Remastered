// Conversion between Need for Madness world space and three.js space.
// NFM: x right, y DOWN, z forward, integer units (~2 cm), angles in degrees.
// three: x right, y up, camera looks down -z. Mapping (x, -y, -z) is a proper
// rotation, so nothing is mirrored.
import * as THREE from 'three';

export const S = 1 / 50; // one three.js unit is about one metre

export function toThree(x, y, z, out = new THREE.Vector3()) {
  return out.set(x * S, -y * S, -z * S);
}

const D2R = Math.PI / 180;

// Rotation used by ContO / Plane.d: roll (xy), then pitch (zy), then yaw (xz).
// Returns it already converted into three.js space.
export function nfmRotation(xz, xy, zy, out = new THREE.Matrix4()) {
  const cy = Math.cos(xz * D2R), sy = Math.sin(xz * D2R);
  const cp = Math.cos(zy * D2R), sp = Math.sin(zy * D2R);
  const cr = Math.cos(xy * D2R), sr = Math.sin(xy * D2R);
  // Ry * Rp * Rr in NFM space
  const Ry = [[cy, 0, -sy], [0, 1, 0], [sy, 0, cy]];
  const Rp = [[1, 0, 0], [0, cp, -sp], [0, sp, cp]];
  const Rr = [[cr, -sr, 0], [sr, cr, 0], [0, 0, 1]];
  const mul = (a, b) => a.map((row, i) => [0, 1, 2].map((j) => row[0] * b[0][j] + row[1] * b[1][j] + row[2] * b[2][j]));
  const R = mul(mul(Ry, Rp), Rr);
  // conjugate by C = diag(1, -1, -1)
  const c = [1, -1, -1];
  const m = R.map((row, i) => row.map((v, j) => v * c[i] * c[j]));
  out.set(
    m[0][0], m[0][1], m[0][2], 0,
    m[1][0], m[1][1], m[1][2], 0,
    m[2][0], m[2][1], m[2][2], 0,
    0, 0, 0, 1,
  );
  return out;
}

export function nfmMatrix(x, y, z, xz, xy, zy, out = new THREE.Matrix4()) {
  nfmRotation(xz, xy, zy, out);
  out.elements[12] = x * S;
  out.elements[13] = -y * S;
  out.elements[14] = -z * S;
  return out;
}

// Shortest signed angle difference in degrees.
export function angleLerp(a, b, t) {
  let d = (b - a) % 360;
  if (d > 180) d -= 360;
  if (d < -180) d += 360;
  return a + d * t;
}

const _srgb = new THREE.Color();
export function srgbToLinear(r, g, b, out) {
  _srgb.setRGB(r / 255, g / 255, b / 255, THREE.SRGBColorSpace);
  out[0] = _srgb.r; out[1] = _srgb.g; out[2] = _srgb.b;
  return out;
}

// Stages tint every polygon with snap(r,g,b) (Plane: c = oc + oc * snap / 100),
// the original's stand-in for lighting. Cars are lit for real here, so undo it
// to show their true paint colour.
export function carColorLinear(c, snap, out) {
  const un = (v, s) => Math.max(0, Math.min(255, v / Math.max(0.2, 1 + s / 100)));
  return srgbToLinear(un(c[0], snap[0]), un(c[1], snap[1]), un(c[2], snap[2]), out);
}
