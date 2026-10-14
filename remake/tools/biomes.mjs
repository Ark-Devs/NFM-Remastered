import fs from 'node:fs'; import path from 'node:path'; import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js';
import { stagePalette } from '../src/render/environment.js';
import { biomeOf } from '../src/render/terrain.js';
const GAME = path.resolve('..'); const L = new TextDecoder('latin1');
const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.join(GAME, 'data/models.zip'))))) assets.models[k] = L.decode(v);
const world = new World(assets);
const out = {};
for (let id = 1; id <= 32; id++) {
  const text = L.decode(fs.readFileSync(path.join(GAME, `stages/${id}.txt`)));
  const def = world.loadStage({ id, name: '', text }, 7);
  const b = biomeOf(stagePalette(world.medium, def.env));
  const p = stagePalette(world.medium, def.env); const hsl = {}; p.ground.getHSL(hsl); const sk = {}; p.sky.getHSL(sk);
  console.log(id, b, 'ground', world.medium.cgrnd.join(','), 'h', Math.round(hsl.h*360), 's', hsl.s.toFixed(2), 'l', hsl.l.toFixed(2), 'sky', world.medium.csky.join(','), def.env.lightson ? 'NIGHT' : '');
}
console.log(out);
