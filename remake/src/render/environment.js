import * as THREE from 'three';
import { NOISE_GLSL } from './materials.js';
import { S } from './space.js';
import { Terrain } from './terrain.js';
import { buildScatter } from './scatter.js';

const _r = new THREE.Vector3();
const _u = new THREE.Vector3();
const _f = new THREE.Vector3();
const _up = new THREE.Vector3(0, 1, 0);
// Blend a colour toward its own grey by `amount` (0 = unchanged, 1 = grey).
function desat(c, amount) {
  const l = c.r * 0.2126 + c.g * 0.7152 + c.b * 0.0722;
  return c.clone().lerp(new THREE.Color(l, l, l), amount);
}

const col = (rgb) => new THREE.Color().setRGB(rgb[0] / 255, rgb[1] / 255, rgb[2] / 255, THREE.SRGBColorSpace);

export function stagePalette(medium, env) {
  const night = env.lightson;
  return {
    sky: col(medium.csky),
    fog: col(medium.cfade),
    clouds: col(medium.clds),
    ground: col(medium.cgrnd),
    polys: col(medium.cpol),
    cloudAmount: Math.min(1, (env.clouds?.[3] ?? 1) / 10),
    night,
  };
}

function makeSkyMaterial(pal, sunDir) {
  return new THREE.ShaderMaterial({
    side: THREE.BackSide,
    depthWrite: false,
    fog: false,
    uniforms: {
      uSky: { value: pal.sky.clone() },
      uHorizon: { value: pal.fog.clone() },
      uGround: { value: pal.ground.clone().lerp(pal.fog, 0.6) },
      uCloud: { value: pal.clouds.clone() },
      uCloudAmount: { value: pal.cloudAmount },
      uSunDir: { value: sunDir.clone() },
      uTime: { value: 0 },
      uNight: { value: pal.night ? 1 : 0 },
    },
    vertexShader: /* glsl */ `
      varying vec3 vDir;
      void main() {
        vDir = normalize(position);
        vec4 p = projectionMatrix * modelViewMatrix * vec4(position, 1.0);
        gl_Position = p.xyww;
      }`,
    fragmentShader: /* glsl */ `
      uniform vec3 uSky, uHorizon, uGround, uCloud, uSunDir;
      uniform float uCloudAmount, uTime, uNight;
      varying vec3 vDir;
      ${NOISE_GLSL}
      void main() {
        vec3 d = normalize(vDir);
        float h = d.y;
        vec3 sky = mix(uHorizon, uSky, smoothstep(-0.02, 0.45, h));
        sky = mix(sky, uSky * 0.75, smoothstep(0.45, 1.0, h));
        // sun
        float sd = max(dot(d, uSunDir), 0.0);
        vec3 sunCol = mix(vec3(1.0, 0.92, 0.78), vec3(0.75, 0.82, 1.0), uNight);
        sky += sunCol * (pow(sd, 900.0) * 30.0 * (1.0 - uNight * 0.6) + pow(sd, 18.0) * 0.25 + pow(sd, 3.0) * 0.06);
        // clouds on a dome
        if (h > 0.0) {
          vec2 uv = d.xz / (h + 0.12) * 1.4 + vec2(uTime * 0.004, uTime * 0.0015);
          float n = nfm_fbm(vec3(uv, uTime * 0.01));
          float cov = mix(0.62, 0.38, uCloudAmount);
          float c = smoothstep(cov, cov + 0.22, n) * smoothstep(0.0, 0.18, h);
          float light = 0.75 + 0.35 * pow(sd, 4.0) + 0.15 * nfm_noise(vec3(uv * 3.0, 1.0));
          sky = mix(sky, uCloud * light, c * 0.92);
        }
        // stars at night
        if (uNight > 0.5 && h > 0.0) {
          float st = step(0.9975, nfm_hash(floor(d * 600.0)));
          sky += vec3(st) * 0.8 * smoothstep(0.05, 0.3, h);
        }
        sky = mix(sky, uGround, smoothstep(0.0, -0.08, h));
        gl_FragColor = vec4(sky, 1.0);
        #include <tonemapping_fragment>
        #include <colorspace_fragment>
      }`,
  });
}

function makeGroundMaterial(pal, envMap) {
  const mat = new THREE.MeshStandardMaterial({ color: 0xffffff, roughness: 0.95, metalness: 0, envMap, envMapIntensity: 0.3, polygonOffset: true, polygonOffsetFactor: 2, polygonOffsetUnits: 8 });
  mat.onBeforeCompile = (shader) => {
    shader.uniforms.uGround = { value: pal.ground };
    shader.uniforms.uPolys = { value: pal.polys };
    shader.vertexShader = shader.vertexShader
      .replace('#include <common>', '#include <common>\nvarying vec3 vWorldP;')
      .replace('#include <worldpos_vertex>', '#include <worldpos_vertex>\nvWorldP = (modelMatrix * vec4(transformed, 1.0)).xyz;');
    shader.fragmentShader = shader.fragmentShader
      .replace('#include <common>', `#include <common>
uniform vec3 uGround, uPolys;
varying vec3 vWorldP;
${NOISE_GLSL}`)
      .replace('#include <color_fragment>', `#include <color_fragment>
{
  vec3 p = vWorldP;
  float big = nfm_fbm(p * 0.006);
  float mid = nfm_fbm(p * 0.05);
  float fine = nfm_noise(p * 1.7) * 0.6 + nfm_noise(p * 6.0) * 0.4;
  vec3 c = mix(uGround, uPolys, smoothstep(0.42, 0.62, big));
  c *= 0.82 + mid * 0.3;
  c *= 0.9 + fine * 0.18;
  diffuseColor.rgb = c;
}`);
  };
  return mat;
}

// Seeded mountain ring around the stage, like Medium.newmountains.
function makeMountains(seed, bounds, pal) {
  let s = seed || 12345;
  const rnd = () => { s = (s * 16807) % 2147483647; return (s & 0xffffff) / 0x1000000; };
  const cx = ((bounds.minx + bounds.maxx) / 2) * S;
  const cz = -((bounds.minz + bounds.maxz) / 2) * S;
  const span = Math.max(bounds.maxx - bounds.minx, bounds.maxz - bounds.minz) * S;
  const radius = Math.max(span * 0.9, 900);
  const segs = 180;
  const pos = [], colors = [], idx = [];
  const rows = 6;
  const heights = [];
  let h = 0.5;
  for (let i = 0; i < segs; i++) {
    h = THREE.MathUtils.clamp(h + (rnd() - 0.5) * 0.35, 0.15, 1);
    heights.push(h * (0.6 + rnd() * 0.4));
  }
  const base = pal.ground.clone().multiplyScalar(0.55).lerp(pal.fog, 0.35);
  const top = pal.fog.clone().lerp(pal.sky, 0.2);
  for (let i = 0; i <= segs; i++) {
    const a = (i / segs) * Math.PI * 2;
    const hh = heights[i % segs] * radius * 0.16;
    for (let r = 0; r <= rows; r++) {
      const t = r / rows;
      const rr = radius * (1 + t * 0.25 * (0.6 + 0.4 * Math.sin(a * 7 + seed)));
      const y = -5 + Math.sin(t * Math.PI) * hh * (0.75 + 0.25 * Math.sin(a * 23 + r));
      pos.push(cx + Math.cos(a) * rr, y, cz + Math.sin(a) * rr);
      const c = base.clone().lerp(top, Math.min(1, (y + 5) / (radius * 0.16)) * 0.9);
      colors.push(c.r, c.g, c.b);
    }
  }
  for (let i = 0; i < segs; i++) for (let r = 0; r < rows; r++) {
    const a = i * (rows + 1) + r, b = (i + 1) * (rows + 1) + r;
    idx.push(a, b, a + 1, b, b + 1, a + 1);
  }
  const g = new THREE.BufferGeometry();
  g.setAttribute('position', new THREE.Float32BufferAttribute(pos, 3));
  g.setAttribute('color', new THREE.Float32BufferAttribute(colors, 3));
  g.setIndex(idx);
  g.computeVertexNormals();
  // Distant: colours are already blended toward the fog, so skip scene fog.
  const m = new THREE.MeshStandardMaterial({ vertexColors: true, roughness: 1, side: THREE.DoubleSide, flatShading: true, fog: false });
  return new THREE.Mesh(g, m);
}

export class Environment {
  constructor(renderer, scene) {
    this.renderer = renderer;
    this.scene = scene;
    this.group = new THREE.Group();
    scene.add(this.group);
    this.pmrem = new THREE.PMREMGenerator(renderer);
    this.envRT = null;
  }

  build(medium, env, bounds, def = null, trackers = null) {
    this.dispose();
    const pal = stagePalette(medium, env);
    this.pal = pal;
    const night = pal.night;
    const sunDir = new THREE.Vector3(-0.45, night ? 0.5 : 0.62, 0.35).normalize();
    this.sunDir = sunDir;

    this.skyMat = makeSkyMaterial(pal, sunDir);
    this.sky = new THREE.Mesh(new THREE.SphereGeometry(1, 48, 24), this.skyMat);
    this.sky.frustumCulled = false;
    this.sky.renderOrder = -10;
    this.group.add(this.sky);

    // Reflections: render the sky (plus a ground disc) into a PMREM env map.
    const envScene = new THREE.Scene();
    // Light that reaches the cars is far less coloured than the painted sky
    // (strongly tinted stages otherwise dye every car), so light with a
    // desaturated copy of the palette.
    const lit = { ...pal, sky: desat(pal.sky, 1), fog: desat(pal.fog, 1), clouds: desat(pal.clouds, 1), ground: desat(pal.ground, 1) };
    this.lit = lit;
    const envSky = new THREE.Mesh(new THREE.SphereGeometry(100, 48, 24), makeSkyMaterial(lit, sunDir));
    envScene.add(envSky);
    const disc = new THREE.Mesh(new THREE.CircleGeometry(100, 32).rotateX(-Math.PI / 2), new THREE.MeshBasicMaterial({ color: lit.ground.clone().multiplyScalar(0.6) }));
    disc.position.y = -2;
    envScene.add(disc);
    this.envRT = this.pmrem.fromScene(envScene, 0.02);
    this.envMap = this.envRT.texture;
    this.scene.environment = this.envMap;
    this.scene.environmentIntensity = night ? 0.2 : 0.55;
    envSky.geometry.dispose();

    // Fog: original fades geometry to the fog colour from 'fadefrom'.
    const fade = Math.max(2500, env.fadefrom || 5000);
    this.scene.fog = new THREE.Fog(pal.fog, fade * 2.5 * S, fade * 14 * S);

    // Lights
    this.sun = new THREE.DirectionalLight(night ? 0xc8ccd6 : 0xffffff, night ? 0.35 : 3.8);
    this.sun.position.copy(sunDir).multiplyScalar(400);
    this.sun.castShadow = true;
    this.sun.shadow.bias = -0.0004;
    this.sun.shadow.normalBias = 0.04;
    const sc = this.sun.shadow.camera;
    sc.left = -90; sc.right = 90; sc.top = 90; sc.bottom = -90; sc.near = 10; sc.far = 1200;
    this.group.add(this.sun, this.sun.target);
    this.hemi = new THREE.HemisphereLight(lit.sky, lit.ground, night ? 0.15 : 0.35);
    this.group.add(this.hemi);

    // Terrain: flat inside the track, hills and mountains beyond the walls
    this.terrain = new Terrain(bounds, pal, env.mountains || 1234, this.envMap);
    this.ground = this.terrain.mesh;
    this.group.add(this.ground);
    if (def && trackers) {
      this.scatter = buildScatter(this.terrain, pal, trackers, def.objects);
      this.group.add(this.scatter);
    }
    return this.envMap;
  }

  setShadowQuality(q) {
    if (!this.sun) return;
    this.sun.shadow.mapSize.set(q.shadows, q.shadows);
    const sc = this.sun.shadow.camera;
    sc.left = -q.shadowRange; sc.right = q.shadowRange; sc.top = q.shadowRange; sc.bottom = -q.shadowRange;
    sc.updateProjectionMatrix();
    this.sun.shadow.radius = q.softShadows ? 3 : 1;
    this.sun.shadow.map?.dispose();
    this.sun.shadow.map = null;
  }

  update(camera, focus, time) {
    if (!this.sky) return;
    this.sky.position.copy(camera.position);
    this.sky.scale.setScalar(camera.far * 0.9);
    this.skyMat.uniforms.uTime.value = time;
    // keep the shadow frustum centred on the action
    // Snap the shadow centre to whole shadow-map texels so shadows don't
    // shimmer (flicker) as the camera moves.
    const sh = this.sun.shadow;
    const texel = (sh.camera.right - sh.camera.left) / sh.mapSize.x;
    const right = _r.crossVectors(_up, this.sunDir).normalize();
    const up2 = _u.crossVectors(this.sunDir, right);
    const a = Math.round(focus.dot(right) / texel) * texel;
    const b = Math.round(focus.dot(up2) / texel) * texel;
    const c = focus.dot(this.sunDir);
    _f.copy(right).multiplyScalar(a).addScaledVector(up2, b).addScaledVector(this.sunDir, c);
    this.sun.target.position.copy(_f);
    this.sun.position.copy(_f).addScaledVector(this.sunDir, 400);
  }

  dispose() {
    this.group.traverse((o) => { if (o.geometry) o.geometry.dispose(); });
    this.group.clear();
    this.envRT?.dispose();
    this.envRT = null;
  }
}
