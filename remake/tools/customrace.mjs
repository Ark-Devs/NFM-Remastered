import fs from 'node:fs'; import path from 'node:path'; import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js'; import { Race } from '../src/game/race.js';
const GAME = path.resolve('..'); const L = new TextDecoder('latin1');
const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.join(GAME, 'data/models.zip'))))) assets.models[k] = L.decode(v);
for (const f of fs.readdirSync(path.join(GAME, 'mycars')).filter((f) => / C\.rad$/i.test(f))) {
  const text = L.decode(fs.readFileSync(path.join(GAME, 'mycars', f)));
  const m = /^\s*name\(([^)]*)\)/m.exec(text);
  assets.customCars.push({ file: f, name: m ? m[1].trim() : f.replace(/ C\.rad$/, ''), text });
}
const world = new World(assets);
const customs = world.carList.filter((c) => c.custom);
console.log('custom cars loaded:', customs.length, '/', assets.customCars.length);
console.log(customs.map((c) => `${c.name}[spd ${world.cd.swits[c.slot][2]} grip ${world.cd.grip[c.slot].toFixed(0)}]`).join(', '));
const stage = { id: 7, name: 't', text: L.decode(fs.readFileSync(path.join(GAME, 'stages/7.txt'))) };
for (let k = 0; k < customs.length; k += 7) {
  const grid = customs.slice(k, k + 7).map((c) => c.slot);
  while (grid.length < 7) grid.push(grid.length);
  const race = new Race(world, world.loadStage(stage, 7), { stage, playerCar: grid[0], opponents: grid.slice(1) });
  let err = null;
  try { for (let t = 0; t < 2500 && !race.finished; t++) { const ai = race.controls[0]; if (race.countdown === 0) ai.preform(race.mads[0], race.cars[0], race.cp, world.trackers); race.step({ left: ai.left, right: ai.right, up: ai.up, down: ai.down, handb: ai.handb }); } } catch (e) { err = e.message; }
  console.log(`grid ${k / 7}: ticks=${race.tick} clears=${race.cp.clear.slice(0, 7).join(',')} wasted=${race.cp.wasted} ${err ? 'ERROR ' + err : 'ok'}`);
}
