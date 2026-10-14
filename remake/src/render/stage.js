// Stage pieces: instanced per model, with special handling for checkpoints,
// repair hoops and rock piles.
import * as THREE from 'three';
import { buildConto } from './meshbuild.js';
import { nfmMatrix } from './space.js';
import { createStageMaterial } from './materials.js';
import { PIECE_BASE, PIECE_FILES } from '../game/world.js';
import { mergeGeometries } from 'three/addons/utils/BufferGeometryUtils.js';

const _m = new THREE.Matrix4();

export class StageView {
  constructor(world, def, envMap) {
    this.group = new THREE.Group();
    this.material = createStageMaterial(envMap);
    this.geoCache = new Map();
    this.checkpoints = [];
    this.fixes = [];
    const bySlot = new Map();
    const piles = [];
    for (const o of def.objects) {
      if (o.pile) { piles.push(o.conto); continue; }
      if (o.checkpoint || o.fix) { this.addSpecial(world, o); continue; }
      if (!bySlot.has(o.slot)) bySlot.set(o.slot, []);
      bySlot.get(o.slot).push(o);
    }
    for (const [slot, list] of bySlot) {
      const geo = this.geometryFor(world, slot);
      if (!geo) continue;
      const mesh = new THREE.InstancedMesh(geo, this.material, list.length);
      list.forEach((o, i) => {
        nfmMatrix(o.conto.x, o.conto.y, o.conto.z, o.rot, 0, 0, _m);
        mesh.setMatrixAt(i, _m);
      });
      mesh.castShadow = true;
      mesh.receiveShadow = true;
      mesh.computeBoundingSphere();
      this.group.add(mesh);
    }
    if (piles.length) {
      const geos = piles.map((c) => {
        const g = buildConto(c, { modelName: 'pile' }).body.geometry;
        g.applyMatrix4(nfmMatrix(c.x, c.y, c.z, 0, 0, 0, _m));
        g.deleteAttribute('normal');
        return g;
      });
      const merged = mergeGeometries(geos);
      merged.computeVertexNormals();
      const mesh = new THREE.Mesh(merged, this.material);
      mesh.castShadow = true;
      mesh.receiveShadow = true;
      this.group.add(mesh);
    }
  }

  geometryFor(world, slot, part = 'body') {
    const key = slot + ':' + part;
    if (!this.geoCache.has(key)) {
      const base = world.base[slot];
      const name = PIECE_FILES[slot - PIECE_BASE] || '';
      const built = base ? buildConto(base, { modelName: name }) : {};
      this.geoCache.set(slot + ':body', built.body?.geometry ?? null);
      this.geoCache.set(slot + ':finish', built.finish?.geometry ?? null);
    }
    return this.geoCache.get(key);
  }

  addSpecial(world, o) {
    const geo = this.geometryFor(world, o.slot);
    if (!geo) return;
    const mat = this.material.clone();
    mat.onBeforeCompile = this.material.onBeforeCompile;
    mat.customProgramCacheKey = this.material.customProgramCacheKey;
    mat.emissive = new THREE.Color(o.fix ? 0x3aa0ff : 0xffe066);
    mat.emissiveIntensity = 0;
    const mesh = new THREE.Mesh(geo, mat);
    mesh.matrixAutoUpdate = false;
    nfmMatrix(o.conto.x, o.conto.y, o.conto.z, o.rot, 0, 0, mesh.matrix);
    mesh.castShadow = true;
    mesh.receiveShadow = true;
    this.group.add(mesh);
    if (o.fix) {
      const light = new THREE.PointLight(0x4aa8ff, 0, 40, 2);
      light.position.setFromMatrixPosition(mesh.matrix);
      light.position.y += 3;
      this.group.add(light);
      this.fixes.push({ mesh, light });
    } else {
      const fgeo = this.geometryFor(world, o.slot, 'finish');
      let finish = null;
      if (fgeo) {
        finish = new THREE.Mesh(fgeo, mat);
        finish.matrixAutoUpdate = false;
        finish.matrix.copy(mesh.matrix);
        finish.visible = false;
        this.group.add(finish);
      }
      this.checkpoints.push({ mesh, finish, index: o.checkpoint });
    }
  }

  // nextCheckpoint: 1-based index of the checkpoint the player must take next;
  // lastCheck: it is the final one of the race (shows the FINISH lettering).
  update(time, nextCheckpoint, lastCheck = false) {
    for (const c of this.checkpoints) {
      const on = c.index === nextCheckpoint;
      if (c.finish) c.finish.visible = on && lastCheck;
      c.mesh.material.emissiveIntensity = on ? 0.35 + 0.3 * Math.sin(time * 6) : 0;
    }
    for (const f of this.fixes) {
      const flick = 0.5 + 0.5 * Math.sin(time * 9 + Math.sin(time * 23) * 2);
      f.mesh.material.emissiveIntensity = 0.4 + flick * 0.8;
      f.light.intensity = 30 + flick * 40;
    }
  }

  dispose() {
    for (const g of this.geoCache.values()) g?.dispose();
    this.group.traverse((o) => { if (o.isMesh && o.material !== this.material) o.material.dispose?.(); });
    this.material.dispose();
  }
}
