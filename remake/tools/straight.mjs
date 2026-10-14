import fs from 'node:fs'; import path from 'node:path'; import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js'; import { Race } from '../src/game/race.js';
const GAME = path.resolve('..'); const L = new TextDecoder('latin1'); const read = (r) => L.decode(fs.readFileSync(path.join(GAME, r)));
const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.join(GAME, 'data/models.zip'))))) assets.models[k] = L.decode(v);
for (const f of fs.readdirSync(path.join(GAME, 'mycars')).filter((f) => f.endsWith('.rad'))) assets.customCars.push({ file: f, name: f.slice(0, -4), text: read('mycars/' + f) });
const world = new World(assets);
for (const [car, landk] of [[14, 1], [14, 0.3], [world.carList.find(c=>c.name==='Mustang').slot, 1], [world.carList.find(c=>c.name==='Mustang').slot, 0.3]]) {
  const stage = { id: 1, name: 't', text: read('stages/1.txt') };
  const race = new Race(world, world.loadStage(stage, 7), { stage, playerCar: car, opponents: [2, 10, 4, 13, 15, 12], landk });
  let crashes = 0; race.events.on('crash', () => crashes++);
  let t = 0;
  for (; t < 1500 && !race.player.dest; t++) race.step({ up: true });
  console.log(world.cd.names[car], 'landk', landk, 'ticks', t, 'dest', race.player.dest, 'hitmag', race.player.hitmag, '/', world.cd.maxmag[car], 'crashes', crashes, 'pos', race.cars[0].z);
}
