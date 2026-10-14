import * as THREE from 'three';

// Shared 3D value-noise / fbm used by the material patches.
export const NOISE_GLSL = /* glsl */ `
float nfm_hash(vec3 p) {
  p = fract(p * 0.3183099 + 0.1);
  p *= 17.0;
  return fract(p.x * p.y * p.z * (p.x + p.y + p.z));
}
float nfm_noise(vec3 x) {
  vec3 i = floor(x);
  vec3 f = fract(x);
  f = f * f * (3.0 - 2.0 * f);
  return mix(mix(mix(nfm_hash(i + vec3(0,0,0)), nfm_hash(i + vec3(1,0,0)), f.x),
                 mix(nfm_hash(i + vec3(0,1,0)), nfm_hash(i + vec3(1,1,0)), f.x), f.y),
             mix(mix(nfm_hash(i + vec3(0,0,1)), nfm_hash(i + vec3(1,0,1)), f.x),
                 mix(nfm_hash(i + vec3(0,1,1)), nfm_hash(i + vec3(1,1,1)), f.x), f.y), f.z);
}
float nfm_fbm(vec3 p) {
  float v = 0.0, a = 0.5;
  for (int i = 0; i < 4; i++) { v += a * nfm_noise(p); p *= 2.03; a *= 0.5; }
  return v;
}
`;

// Car body: per-vertex surface params (roughness, metalness, clearcoat) and a
// damage amount that adds scratches to bare metal, soot and lost gloss.
export function createCarBodyMaterial(envMap) {
  const mat = new THREE.MeshPhysicalMaterial({
    vertexColors: true,
    side: THREE.DoubleSide,
    roughness: 0.3,
    metalness: 0.2,
    clearcoat: 1,
    clearcoatRoughness: 0.14,
    envMap,
    envMapIntensity: 0.95,
  });
  // Per-car dimensions for the panel-detail lines (set by CarView).
  const detail = { b0: new THREE.Vector3(-1, -1, -2), b1: new THREE.Vector3(1, 1, 2), ax: new THREE.Vector2(-1.3, 1.3), cab: { value: 0 } };
  mat.userData.detail = detail;
  mat.onBeforeCompile = (shader) => {
    shader.uniforms.uB0 = { value: detail.b0 };
    shader.uniforms.uB1 = { value: detail.b1 };
    shader.uniforms.uAx = { value: detail.ax };
    shader.uniforms.uCab = detail.cab;
    shader.vertexShader = shader.vertexShader
      .replace('#include <common>', `#include <common>
attribute vec3 surf;
attribute float dmg;
varying vec3 vSurf;
varying float vDmg;
varying vec3 vObjPos;
varying vec3 vObjN;`)
      .replace('#include <begin_vertex>', `#include <begin_vertex>
vSurf = surf; vDmg = dmg; vObjPos = position; vObjN = normal;`);
    shader.fragmentShader = shader.fragmentShader
      .replace('#include <common>', `#include <common>
varying vec3 vSurf;
varying float vDmg;
varying vec3 vObjPos;
varying vec3 vObjN;
uniform vec3 uB0, uB1;
uniform vec2 uAx;
uniform float uCab;
#define NFM_SEAM(d) (1.0 - smoothstep(0.0, max(0.006, fwidth(d) * 1.2), abs(d)))
${NOISE_GLSL}
float nfm_scratch;
float nfm_soot;
float nfm_grime;
float nfm_seam;
float nfm_chrome;`)
      .replace('#include <color_fragment>', `#include <color_fragment>
{
  vec3 p = vObjPos * 3.0;
  float n = nfm_fbm(p * 2.0);
  float streak = nfm_noise(vec3(vObjPos.x * 1.5, vObjPos.y * 22.0, vObjPos.z * 1.5));
  float d = clamp(vDmg, 0.0, 1.0);
  // the original palette is cartoon-saturated; pull it toward real paint
  float lum0 = dot(diffuseColor.rgb, vec3(0.2126, 0.7152, 0.0722));
  diffuseColor.rgb = mix(vec3(lum0), diffuseColor.rgb, 0.96);
  nfm_scratch = smoothstep(0.62, 0.7, streak * 0.6 + n * 0.55) * smoothstep(0.05, 0.35, d);
  nfm_soot = smoothstep(0.35, 0.9, n + d * 0.6 - 0.25) * smoothstep(0.3, 1.0, d);
  diffuseColor.rgb = mix(diffuseColor.rgb, vec3(0.62, 0.6, 0.58), nfm_scratch);
  diffuseColor.rgb = mix(diffuseColor.rgb, diffuseColor.rgb * 0.18 + vec3(0.012, 0.01, 0.008), nfm_soot * 0.85);
  // light road grime only along the very bottom edge, mirrored left/right so
  // both sides of the car match
  vec3 gp = vec3(abs(vObjPos.x), vObjPos.y, vObjPos.z);
  float grimeN = nfm_fbm(gp * 4.0 + 7.0);
  nfm_grime = smoothstep(uB0.y + 0.22, uB0.y + 0.02, vObjPos.y + (grimeN - 0.5) * 0.06) * 0.45;
  diffuseColor.rgb = mix(diffuseColor.rgb, vec3(0.16, 0.13, 0.1), nfm_grime * 0.5);
  nfm_seam = 0.0;
  nfm_chrome = 0.0;
}`)
      .replace('#include <roughnessmap_fragment>', `#include <roughnessmap_fragment>
roughnessFactor = mix(vSurf.x, 0.75, clamp(vDmg * 1.2, 0.0, 1.0));
roughnessFactor = mix(roughnessFactor, 0.85, nfm_grime);
roughnessFactor = mix(roughnessFactor, 0.8, nfm_seam);
roughnessFactor = mix(roughnessFactor, 0.12, nfm_chrome);
roughnessFactor = mix(roughnessFactor, 0.35, nfm_scratch);
roughnessFactor = mix(roughnessFactor, 0.95, nfm_soot);`)
      .replace('#include <metalnessmap_fragment>', `#include <metalnessmap_fragment>
metalnessFactor = mix(vSurf.y, 0.9, nfm_scratch) * (1.0 - nfm_soot);
metalnessFactor = mix(metalnessFactor, 1.0, nfm_chrome);`)
      .replace('#include <lights_physical_fragment>', `#include <lights_physical_fragment>
material.clearcoat = vSurf.z * (1.0 - clamp(vDmg * 1.5, 0.0, 1.0)) * (1.0 - nfm_scratch) * (1.0 - nfm_grime) * (1.0 - nfm_seam);`);
  };
  mat.customProgramCacheKey = () => 'nfm-car-body';
  return mat;
}

// Glass: opaque black tint (no visible interior), glossy and reflective;
// the 'crack' attribute frosts and fractures it.
export function createGlassMaterial(envMap) {
  const mat = new THREE.MeshPhysicalMaterial({
    color: 0x010102,
    roughness: 0.04,
    metalness: 0.0,
    side: THREE.DoubleSide,
    envMap,
    envMapIntensity: 0.7,
    clearcoat: 1,
    clearcoatRoughness: 0.02,
    polygonOffset: true,
    polygonOffsetFactor: -2,
    polygonOffsetUnits: -8,
  });
  mat.onBeforeCompile = (shader) => {
    shader.vertexShader = shader.vertexShader
      .replace('#include <common>', `#include <common>
attribute float crack;
varying float vCrack;
varying vec3 vObjPos;`)
      .replace('#include <begin_vertex>', `#include <begin_vertex>
vCrack = crack; vObjPos = position;`);
    shader.fragmentShader = shader.fragmentShader
      .replace('#include <common>', `#include <common>
varying float vCrack;
varying vec3 vObjPos;
${NOISE_GLSL}`)
      .replace('#include <color_fragment>', `#include <color_fragment>
{
  float c = clamp(vCrack, 0.0, 1.0);
  float n = nfm_fbm(vObjPos * 9.0);
  float lines = 1.0 - smoothstep(0.0, 0.04, abs(n - 0.5));
  float lines2 = 1.0 - smoothstep(0.0, 0.03, abs(nfm_fbm(vObjPos * 17.0 + 3.1) - 0.5));
  float frac = max(lines, lines2 * step(0.6, c)) * smoothstep(0.05, 0.45, c);
  diffuseColor.rgb = mix(diffuseColor.rgb, vec3(0.55, 0.6, 0.62), frac * 0.6 + c * 0.15);
  diffuseColor.a = mix(diffuseColor.a, 0.92, clamp(frac + c * 0.4, 0.0, 1.0));
}`);
  };
  mat.customProgramCacheKey = () => 'nfm-glass';
  return mat;
}

// Head / tail lights: vertex colour drives the emissive glow.
export function createLightMaterial(intensity) {
  const mat = new THREE.MeshStandardMaterial({
    vertexColors: true,
    side: THREE.DoubleSide,
    roughness: 0.2,
    metalness: 0,
    emissive: 0xffffff,
    emissiveIntensity: intensity,
    // lights sit on the body panel; the original drew them over it, so win depth ties
    polygonOffset: true,
    polygonOffsetFactor: -4,
    polygonOffsetUnits: -16,
  });
  mat.onBeforeCompile = (shader) => {
    shader.fragmentShader = shader.fragmentShader.replace('#include <emissivemap_fragment>', `#include <emissivemap_fragment>
totalEmissiveRadiance *= vColor.rgb;`);
  };
  mat.customProgramCacheKey = () => 'nfm-light';
  return mat;
}

// Stage pieces: world-space procedural detail. surf = (roughness, metalness, kind)
// kind: 0 generic painted, 1 road asphalt, 2 rock/dirt, 3 foliage, 4 metal.
export function createStageMaterial(envMap) {
  const mat = new THREE.MeshStandardMaterial({
    vertexColors: true,
    side: THREE.DoubleSide,
    roughness: 0.8,
    metalness: 0,
    envMap,
    envMapIntensity: 0.6,
  });
  mat.onBeforeCompile = (shader) => {
    shader.vertexShader = shader.vertexShader
      .replace('#include <common>', `#include <common>
attribute vec3 surf;
varying vec3 vSurf;
varying vec3 vWorldP;`)
      .replace('#include <worldpos_vertex>', `#include <worldpos_vertex>
vSurf = surf;
#ifdef USE_INSTANCING
vWorldP = (modelMatrix * instanceMatrix * vec4(transformed, 1.0)).xyz;
#else
vWorldP = (modelMatrix * vec4(transformed, 1.0)).xyz;
#endif`);
    shader.fragmentShader = shader.fragmentShader
      .replace('#include <common>', `#include <common>
varying vec3 vSurf;
varying vec3 vWorldP;
${NOISE_GLSL}
float nfm_rough;`)
      .replace('#include <color_fragment>', `#include <color_fragment>
{
  float kind = vSurf.z;
  vec3 p = vWorldP;
  nfm_rough = vSurf.x;
  if (kind > 0.5 && kind < 1.5) {
    // asphalt: the stage's grey pulled down to real tarmac value (keeps its tint),
    // fine aggregate, resurfaced patches, sealed cracks and rubber/oil darkening
    float l0 = dot(diffuseColor.rgb, vec3(0.2126, 0.7152, 0.0722));
    diffuseColor.rgb = mix(vec3(l0), diffuseColor.rgb, 0.7) * 0.36;
    float grain = nfm_noise(p * 18.0) * 0.5 + nfm_noise(p * 53.0) * 0.5;
    float speck = smoothstep(0.78, 0.9, nfm_noise(p * 70.0));
    float patches = smoothstep(0.52, 0.6, nfm_fbm(p * 0.06 + 3.1));
    float crackN = abs(nfm_noise(p * 0.7 + 7.0) - 0.5);
    float crack = (1.0 - smoothstep(0.0, 0.006, crackN)) * smoothstep(0.6, 0.75, nfm_fbm(p * 0.2));
    float wear = nfm_fbm(p * 0.35 + 11.0);
    diffuseColor.rgb *= 0.86 + grain * 0.22 + speck * 0.16;
    diffuseColor.rgb *= mix(1.0, 0.8, patches);   // newer, darker tarmac
    diffuseColor.rgb *= 1.0 - crack * 0.22;        // tar-sealed cracks
    diffuseColor.rgb *= mix(0.88, 1.04, wear);
    nfm_rough = clamp(vSurf.x - grain * 0.08 - speck * 0.15 - crack * 0.25 - (1.0 - wear) * 0.1, 0.45, 1.0);
  } else if (kind > 4.5 && kind < 5.5) {
    // road paint: worn through in places, dirtied
    float wearP = smoothstep(0.35, 0.75, nfm_fbm(p * 0.8 + 5.0)) * 0.55 + nfm_noise(p * 30.0) * 0.12;
    vec3 tarmac = vec3(0.05);
    diffuseColor.rgb = mix(diffuseColor.rgb * 0.85, tarmac, wearP * 0.6);
    nfm_rough = mix(0.55, 0.85, wearP);
  } else if (kind > 5.5) {
    // concrete: pores, form-panel tone shifts, rain streaks and grime near the ground
    float pores = nfm_noise(p * 35.0);
    float panel = nfm_noise(floor(p * 0.5) + 0.5);
    float streak = nfm_noise(vec3(p.x * 2.5, p.y * 0.15, p.z * 2.5));
    float grime = 1.0 - smoothstep(0.0, 1.2, p.y);
    diffuseColor.rgb *= 0.9 + pores * 0.12 + (panel - 0.5) * 0.08;
    diffuseColor.rgb *= 1.0 - smoothstep(0.55, 0.8, streak) * 0.18;
    diffuseColor.rgb *= 1.0 - grime * 0.25;
    nfm_rough = clamp(vSurf.x + pores * 0.08, 0.7, 1.0);
  } else if (kind > 3.5 && kind < 4.5) {
    // galvanised / painted steel: faint brushing and dull oxidation spots
    float brush = nfm_noise(vec3(p.x * 40.0, p.y * 2.0, p.z * 40.0));
    float ox = smoothstep(0.6, 0.8, nfm_fbm(p * 1.3));
    diffuseColor.rgb *= 0.92 + brush * 0.1;
    diffuseColor.rgb = mix(diffuseColor.rgb, diffuseColor.rgb * vec3(0.75, 0.68, 0.6), ox * 0.6);
    nfm_rough = clamp(vSurf.x + brush * 0.1 + ox * 0.35, 0.3, 0.9);
  } else if (kind > 1.5 && kind < 2.5) {
    float n = nfm_fbm(p * 0.9);
    float n2 = nfm_noise(p * 7.0);
    diffuseColor.rgb *= 0.7 + n * 0.45 + n2 * 0.12;
    nfm_rough = 0.95;
  } else if (kind > 2.5 && kind < 3.5) {
    float n = nfm_fbm(p * 2.5);
    diffuseColor.rgb *= 0.65 + n * 0.6;
    nfm_rough = 0.85;
  } else {
    float n = nfm_noise(p * 3.0) * 0.5 + nfm_noise(p * 11.0) * 0.5;
    diffuseColor.rgb *= 0.9 + n * 0.16;
  }
}`)
      .replace('#include <roughnessmap_fragment>', `#include <roughnessmap_fragment>
roughnessFactor = nfm_rough;`)
      .replace('#include <metalnessmap_fragment>', `#include <metalnessmap_fragment>
metalnessFactor = vSurf.y;`);
  };
  mat.customProgramCacheKey = () => 'nfm-stage';
  return mat;
}

export function createTireMaterial() {
  return new THREE.MeshStandardMaterial({ color: 0x141414, roughness: 0.92, metalness: 0 });
}

export function createRimMaterial(color, envMap) {
  return new THREE.MeshPhysicalMaterial({ color, roughness: 0.22, metalness: 0.95, envMap, envMapIntensity: 1.2, clearcoat: 0.5 });
}
