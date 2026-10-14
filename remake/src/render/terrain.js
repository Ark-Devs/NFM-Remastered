// Visual terrain around the track. The physics ground is perfectly flat, so the
// terrain stays flat inside the track walls (plus a margin) and only rises into
// hills and mountains beyond them, where no car can reach.
import * as THREE from 'three';
import { NOISE_GLSL } from './materials.js';
import { S } from './space.js';

/* -------- small deterministic 2D noise (JS side, for heights/placement) -------- */
export function makeNoise(seed) {
  let s = (seed | 0) || 1;
  const rnd = () => { s = (s * 16807) % 2147483647; return (s & 0xffffff) / 0x1000000; };
  const perm = new Uint8Array(512);
  const p = Array.from({ length: 256 }, (_, i) => i);
  for (let i = 255; i > 0; i--) { const j = Math.floor(rnd() * (i + 1)); [p[i], p[j]] = [p[j], p[i]]; }
  for (let i = 0; i < 512; i++) perm[i] = p[i & 255];
  const grad = (h, x, y) => ((h & 1) ? -x : x) + ((h & 2) ? -y : y);
  const fade = (t) => t * t * t * (t * (t * 6 - 15) + 10);
  const noise = (x, y) => {
    const X = Math.floor(x) & 255, Y = Math.floor(y) & 255;
    x -= Math.floor(x); y -= Math.floor(y);
    const u = fade(x), v = fade(y);
    const a = perm[X] + Y, b = perm[X + 1] + Y;
    const n = THREE.MathUtils.lerp(
      THREE.MathUtils.lerp(grad(perm[a], x, y), grad(perm[b], x - 1, y), u),
      THREE.MathUtils.lerp(grad(perm[a + 1], x, y - 1), grad(perm[b + 1], x - 1, y - 1), u), v);
    return n * 0.5 + 0.5; // 0..1
  };
  const fbm = (x, y, oct = 5) => { let v = 0, a = 0.5, f = 1; for (let i = 0; i < oct; i++) { v += a * noise(x * f, y * f); f *= 2.03; a *= 0.5; } return v / 0.97; };
  const ridged = (x, y, oct = 5) => { let v = 0, a = 0.5, f = 1; for (let i = 0; i < oct; i++) { const n = 1 - Math.abs(noise(x * f, y * f) * 2 - 1); v += a * n * n; f *= 2.1; a *= 0.5; } return v; };
  return { noise, fbm, ridged, rnd };
}

// Classify the stage from its ground colour.
export function biomeOf(pal) {
  // The original's ground colours are stylised: icy blues, red rock, sand,
  // green and purple night/fantasy grounds.
  const hsl = {};
  pal.ground.getHSL(hsl);
  const h = hsl.h * 360;
  if (h >= 175 && h <= 245 && hsl.l > 0.5) return 'snow';
  if ((h <= 28 || h >= 340) && hsl.s > 0.4) return 'canyon';
  if (h > 28 && h <= 62 && hsl.s > 0.2) return 'desert';
  if (h > 62 && h < 170 && hsl.s > 0.12) return 'grass';
  return 'dirt';
}

const BIOME = {
  snow: { hills: 30, mountains: 520, rock: 0x6b7178, peak: 0xf4f7fb, low: null },
  desert: { hills: 18, mountains: 320, rock: 0x9a6a45, peak: 0xc99b6a, low: null },
  grass: { hills: 34, mountains: 460, rock: 0x6d6a62, peak: 0xeef2f6, low: 0x4f6b2e },
  dirt: { hills: 26, mountains: 400, rock: 0x6a5e52, peak: 0xb8b2aa, low: null },
  canyon: { hills: 22, mountains: 340, rock: 0x9c4f2e, peak: 0xc8825a, low: null },
};

export class Terrain {
  constructor(bounds, pal, seed, envMap) {
    this.biome = biomeOf(pal);
    this.cfg = BIOME[this.biome];
    this.n = makeNoise(seed || 1234);
    // track rectangle in three space, plus a flat safety margin
    const m = 45;
    this.x0 = bounds.minx * S - m; this.x1 = bounds.maxx * S + m;
    this.z0 = -bounds.maxz * S - m; this.z1 = -bounds.minz * S + m;
    if (!(this.x1 > this.x0) || !(this.z1 > this.z0)) { this.x0 = -300; this.x1 = 300; this.z0 = -300; this.z1 = 300; }
    this.cx = (this.x0 + this.x1) / 2; this.cz = (this.z0 + this.z1) / 2;
    this.groundY = -250 * S;
    this.mesh = this.build(pal, envMap);
  }

  // Distance outside the flat track rectangle (0 inside).
  outside(x, z) {
    const dx = Math.max(this.x0 - x, 0, x - this.x1);
    const dz = Math.max(this.z0 - z, 0, z - this.z1);
    return Math.hypot(dx, dz);
  }

  heightAt(x, z) {
    const d = this.outside(x, z);
    if (d <= 0) return this.groundY;
    const { n, cfg } = this;
    const hills = THREE.MathUtils.smoothstep(d, 0, 140) * (n.fbm(x * 0.004, z * 0.004) * cfg.hills + 2);
    let dunes = 0;
    if (this.biome === 'desert') dunes = THREE.MathUtils.smoothstep(d, 20, 200) * Math.pow(n.fbm(x * 0.008 + 3, z * 0.003), 2) * 26;
    let mount = THREE.MathUtils.smoothstep(d, 260, 1500) * (Math.pow(n.ridged(x * 0.0011, z * 0.0011), 1.6) * cfg.mountains + 40);
    if (this.biome === 'canyon') {
      // mesas: flat-topped terraces with steep sides
      const step = 55;
      const t = mount / step;
      mount = (Math.floor(t) + THREE.MathUtils.smoothstep(t - Math.floor(t), 0.75, 1)) * step;
    }
    return this.groundY + hills + dunes + mount;
  }

  build(pal, envMap) {
    const N = 300;
    const half = Math.max(2600, Math.max(this.x1 - this.x0, this.z1 - this.z0) * 2.5);
    const pos = new Float32Array(N * N * 3);
    const col = new Float32Array(N * N * 3);
    // denser near the middle, coarser toward the horizon
    const warp = (u) => Math.sign(u) * Math.pow(Math.abs(u), 1.8);
    for (let j = 0; j < N; j++) {
      for (let i = 0; i < N; i++) {
        const x = this.cx + warp((i / (N - 1)) * 2 - 1) * half;
        const z = this.cz + warp((j / (N - 1)) * 2 - 1) * half;
        const k = (j * N + i) * 3;
        pos[k] = x; pos[k + 1] = this.heightAt(x, z); pos[k + 2] = z;
      }
    }
    const idx = [];
    for (let j = 0; j < N - 1; j++) for (let i = 0; i < N - 1; i++) {
      const a = j * N + i, b = a + 1, c = a + N, d = c + 1;
      idx.push(a, c, b, b, c, d);
    }
    const g = new THREE.BufferGeometry();
    g.setAttribute('position', new THREE.BufferAttribute(pos, 3));
    g.setIndex(idx);
    g.computeVertexNormals();
    // vertex colours: ground near, rock on slopes, snow/lighter on peaks
    const nrm = g.attributes.normal.array;
    const ground = pal.ground.clone();
    const rock = new THREE.Color(this.cfg.rock).convertSRGBToLinear().lerp(ground, 0.25);
    const peak = new THREE.Color(this.cfg.peak).convertSRGBToLinear();
    const low = this.cfg.low ? new THREE.Color(this.cfg.low).convertSRGBToLinear().lerp(ground, 0.5) : ground.clone().multiplyScalar(0.85);
    const c = new THREE.Color();
    for (let v = 0; v < N * N; v++) {
      const y = pos[v * 3 + 1] - this.groundY;
      const slope = 1 - nrm[v * 3 + 1];
      const d = this.outside(pos[v * 3], pos[v * 3 + 2]);
      c.copy(ground).lerp(low, THREE.MathUtils.smoothstep(d, 30, 300) * 0.8);
      c.lerp(rock, THREE.MathUtils.smoothstep(slope, 0.12, 0.35));
      if (this.biome !== 'desert' && this.biome !== 'canyon') c.lerp(peak, THREE.MathUtils.smoothstep(y + this.n.noise(pos[v * 3] * 0.01, pos[v * 3 + 2] * 0.01) * 60, 200, 330) * (1 - THREE.MathUtils.smoothstep(slope, 0.45, 0.7) * 0.6));
      else c.lerp(peak, THREE.MathUtils.smoothstep(y, 80, 260) * 0.5);
      col[v * 3] = c.r; col[v * 3 + 1] = c.g; col[v * 3 + 2] = c.b;
    }
    g.setAttribute('color', new THREE.BufferAttribute(col, 3));
    const mat = makeTerrainMaterial(this.biome, pal, envMap);
    const mesh = new THREE.Mesh(g, mat);
    mesh.receiveShadow = true;
    mesh.castShadow = false;
    return mesh;
  }
}

// Ground shading: biome detail up close, aerial perspective far away.
function makeTerrainMaterial(biome, pal, envMap) {
  const mat = new THREE.MeshStandardMaterial({
    vertexColors: true, roughness: 0.95, metalness: 0, envMap, envMapIntensity: 0.3,
    polygonOffset: true, polygonOffsetFactor: 2, polygonOffsetUnits: 8, fog: false,
  });
  const biomeId = { snow: 0, desert: 1, grass: 2, dirt: 3, canyon: 3 }[biome];
  mat.onBeforeCompile = (shader) => {
    shader.uniforms.uPolys = { value: pal.polys };
    shader.uniforms.uHaze = { value: pal.fog };
    shader.uniforms.uBiome = { value: biomeId };
    shader.vertexShader = shader.vertexShader
      .replace('#include <common>', '#include <common>\nvarying vec3 vWorldP;\nvarying vec3 vWorldN;')
      .replace('#include <worldpos_vertex>', '#include <worldpos_vertex>\nvWorldP = (modelMatrix * vec4(transformed, 1.0)).xyz;\nvWorldN = normalize(mat3(modelMatrix) * objectNormal);');
    shader.fragmentShader = shader.fragmentShader
      .replace('#include <common>', `#include <common>
uniform vec3 uPolys, uHaze;
uniform float uBiome;
varying vec3 vWorldP;
varying vec3 vWorldN;
${NOISE_GLSL}
float nfm_r;`)
      .replace('#include <color_fragment>', `#include <color_fragment>
{
  vec3 p = vWorldP;
  float big = nfm_fbm(p * 0.006);
  float mid = nfm_fbm(p * 0.05);
  float fine = nfm_noise(p * 1.7) * 0.6 + nfm_noise(p * 6.0) * 0.4;
  float flatness = smoothstep(0.75, 0.95, vWorldN.y);
  vec3 c = diffuseColor.rgb;
  // the stage's own ground "polys" patches, like the original's ground tiles
  c = mix(c, uPolys, smoothstep(0.45, 0.65, big) * 0.6 * flatness);
  nfm_r = 0.95;
  if (uBiome < 0.5) {            // snow: soft drifts + sparkle
    c *= 0.93 + mid * 0.1;
    float sp = step(0.985, nfm_noise(p * 90.0));
    c += sp * 0.35 * flatness;
    nfm_r = 0.7 - sp * 0.4;
  } else if (uBiome < 1.5) {     // desert: wind ripples in the sand
    float rip = sin(p.x * 3.2 + p.z * 1.1 + nfm_noise(p * 0.3) * 6.0) * 0.5 + 0.5;
    c *= 0.86 + rip * 0.1 * flatness + mid * 0.12;
  } else if (uBiome < 2.5) {     // grass: patchy blades and dirt
    float blades = nfm_noise(vec3(p.x * 9.0, 0.0, p.z * 9.0));
    vec3 dirt = c * vec3(1.05, 0.85, 0.65);
    c = mix(c, dirt, smoothstep(0.62, 0.8, mid) * 0.6);
    c *= 0.82 + blades * 0.25;
  } else {                       // dirt / gravel
    c *= 0.8 + fine * 0.3 + mid * 0.1;
  }
  c *= 0.9 + fine * 0.15;
  diffuseColor.rgb = c;
}`)
      .replace('#include <roughnessmap_fragment>', '#include <roughnessmap_fragment>\nroughnessFactor = nfm_r;')
      .replace('#include <dithering_fragment>', `#include <dithering_fragment>
{
  // aerial perspective: distance hazes into the sky colour but far peaks stay visible
  float dist = length(vWorldP - cameraPosition);
  float haze = 1.0 - exp(-dist * 0.00075);
  haze *= mix(1.0, 0.8, smoothstep(30.0, 400.0, vWorldP.y + 5.0));
  gl_FragColor.rgb = mix(gl_FragColor.rgb, uHaze, clamp(haze, 0.0, 0.9));
}`);
  };
  mat.customProgramCacheKey = () => 'nfm-terrain';
  return mat;
}
