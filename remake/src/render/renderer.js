import * as THREE from 'three';
import { EffectComposer } from 'three/addons/postprocessing/EffectComposer.js';
import { RenderPass } from 'three/addons/postprocessing/RenderPass.js';
import { GTAOPass } from 'three/addons/postprocessing/GTAOPass.js';
import { UnrealBloomPass } from 'three/addons/postprocessing/UnrealBloomPass.js';
import { OutputPass } from 'three/addons/postprocessing/OutputPass.js';
import { ShaderPass } from 'three/addons/postprocessing/ShaderPass.js';
import { FXAAPass } from 'three/addons/postprocessing/FXAAPass.js';

// scale: render resolution relative to the screen (Ultra supersamples).
// shadowRange: half-size of the sun shadow area in metres; smaller = sharper shadows.
export const QUALITY = {
  low: { label: 'Low', smooth: 1, scale: 0.6, shadows: 1024, shadowRange: 120, softShadows: false, ao: false, msaa: 0, fxaa: true, bloom: false },
  medium: { label: 'Medium', smooth: 1, scale: 0.85, shadows: 2048, shadowRange: 90, softShadows: true, ao: false, msaa: 4, fxaa: false, bloom: true },
  high: { label: 'High', smooth: 2, scale: 1, shadows: 4096, shadowRange: 65, softShadows: true, ao: true, aoSamples: 12, msaa: 4, fxaa: false, bloom: true, probe: 128, probeEvery: 3 },
  ultra: { label: 'Ultra (RTX)', smooth: 2, scale: 1.5, shadows: 4096, shadowRange: 38, softShadows: true, ao: true, aoSamples: 24, msaa: 8, fxaa: false, bloom: true, probe: 512, probeEvery: 1 },
};

// Final grade: vignette, slight chromatic fringe and film grain.
const GradeShader = {
  uniforms: { tDiffuse: { value: null }, uTime: { value: 0 }, uVignette: { value: 0.32 }, uShake: { value: 0 } },
  vertexShader: 'varying vec2 vUv; void main(){ vUv = uv; gl_Position = projectionMatrix * modelViewMatrix * vec4(position,1.0); }',
  fragmentShader: /* glsl */ `
    uniform sampler2D tDiffuse; uniform float uTime, uVignette, uShake;
    varying vec2 vUv;
    float rnd(vec2 p){ return fract(sin(dot(p, vec2(12.9898,78.233)) + uTime) * 43758.5453); }
    void main(){
      vec2 c = vUv - 0.5;
      float r2 = dot(c, c);
      float ca = 0.0003 + uShake * 0.0012;
      vec3 col;
      col.r = texture2D(tDiffuse, vUv + c * ca).r;
      col.g = texture2D(tDiffuse, vUv).g;
      col.b = texture2D(tDiffuse, vUv - c * ca).b;
      // gentle contrast + saturation lift so the scene doesn't look hazy
      float lum = dot(col, vec3(0.2126, 0.7152, 0.0722));
      col = mix(vec3(lum), col, 1.06);
      col = (col - 0.5) * 1.07 + 0.5;
      col = max(col, 0.0);
      col *= 1.0 - uVignette * smoothstep(0.08, 0.6, r2);
      col += (rnd(vUv * 731.0) - 0.5) * 0.008;
      gl_FragColor = vec4(col, 1.0);
    }`,
};

const SanitizeShader = {
  uniforms: { tDiffuse: { value: null } },
  vertexShader: 'varying vec2 vUv; void main(){ vUv = uv; gl_Position = projectionMatrix * modelViewMatrix * vec4(position,1.0); }',
  fragmentShader: /* glsl */ `
    uniform sampler2D tDiffuse; varying vec2 vUv;
    void main(){
      vec4 c = texture2D(tDiffuse, vUv);
      if (any(isnan(c)) || any(isinf(c))) c = vec4(0.0, 0.0, 0.0, 1.0);
      gl_FragColor = vec4(clamp(c.rgb, 0.0, 60.0), c.a);
    }`,
};

export class Renderer {
  constructor(canvas) {
    this.canvas = canvas;
    this.renderer = new THREE.WebGLRenderer({ canvas, antialias: false, powerPreference: 'high-performance', stencil: false });
    this.renderer.outputColorSpace = THREE.SRGBColorSpace;
    this.renderer.toneMapping = THREE.NeutralToneMapping; // keeps paint colours true (ACES greyed them)
    this.renderer.toneMappingExposure = 1.0;
    this.renderer.shadowMap.enabled = true;
    this.renderer.shadowMap.type = THREE.PCFSoftShadowMap;
    this.scene = new THREE.Scene();
    this.camera = new THREE.PerspectiveCamera(58, 1, 0.3, 6000);
    this.hidden = []; // objects hidden from the AO normal pass (sky, particles)
    this.quality = 'high';
    this.setQuality(this.quality);
    window.addEventListener('resize', () => this.resize());
  }

  setQuality(name) {
    this.quality = name;
    const q = QUALITY[name];
    this.q = q;
    const dpr = Math.min(3, (window.devicePixelRatio || 1) * q.scale);
    this.renderer.shadowMap.type = q.softShadows ? THREE.PCFSoftShadowMap : THREE.PCFShadowMap;
    this.renderer.setPixelRatio(dpr);
    this.composer?.dispose();
    const size = this.renderer.getDrawingBufferSize(new THREE.Vector2());
    const rt = new THREE.WebGLRenderTarget(Math.max(1, size.x), Math.max(1, size.y), { type: THREE.HalfFloatType, samples: q.msaa });
    this.composer = new EffectComposer(this.renderer, rt);
    this.composer.addPass(new RenderPass(this.scene, this.camera));
    this.aoPass = null;
    if (q.ao) {
      const ao = new GTAOPass(this.scene, this.camera, size.x, size.y);
      ao.updateGtaoMaterial({ radius: 1.2, distanceExponent: 1.5, thickness: 2, scale: 1.1, samples: q.aoSamples });
      ao.blendIntensity = 0.85;
      const render = ao.render.bind(ao);
      ao.render = (...args) => {
        const vis = this.hidden.map((o) => o.visible);
        this.hidden.forEach((o) => { o.visible = false; });
        render(...args);
        this.hidden.forEach((o, i) => { o.visible = vis[i]; });
      };
      this.composer.addPass(ao);
      this.aoPass = ao;
    }
    // Clamp HDR and drop NaN/Inf pixels: one bad pixel would otherwise be blurred
    // across the whole frame by the bloom.
    this.composer.addPass(new ShaderPass(SanitizeShader));
    if (q.bloom) {
      this.bloom = new UnrealBloomPass(new THREE.Vector2(size.x / 2, size.y / 2), 0.28, 0.4, 1.0);
      this.composer.addPass(this.bloom);
    }
    this.composer.addPass(new OutputPass());
    this.grade = new ShaderPass(GradeShader);
    this.composer.addPass(this.grade);
    if (q.fxaa) this.composer.addPass(new FXAAPass());
    // shadow filter type / resolution changes need fresh shader programs
    this.scene.traverse((o) => { const m = o.material; if (m) for (const x of Array.isArray(m) ? m : [m]) x.needsUpdate = true; });
    this.onQuality?.(q);
    this.resize();
  }

  resize() {
    const w = this.canvas.clientWidth || window.innerWidth;
    const h = this.canvas.clientHeight || window.innerHeight;
    this.renderer.setSize(w, h, false);
    this.composer.setSize(w, h);
    this.camera.aspect = w / h;
    this.camera.updateProjectionMatrix();
    this.onResize?.(w, h);
  }

  render(time, shake = 0) {
    this.grade.uniforms.uTime.value = time % 100;
    this.grade.uniforms.uShake.value = shake;
    this.composer.render();
  }
}
