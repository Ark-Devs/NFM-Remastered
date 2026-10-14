// Headless smoke test: load the real assets, run a race with the AI driving every car.
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js';
import { Race } from '../src/game/race.js';

const GAME = path.resolve(path.dirname(fileURLToPath(import.meta.url)), '..', '..');
const latin1 = new TextDecoder('latin1');
const read = (r) => latin1.decode(fs.readFileSync(path.join(GAME, r)));

const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.join(GAME, 'data/models.zip'))))) assets.models[k] = latin1.decode(v);
for (const f of fs.readdirSync(path.join(GAME, 'mycars')).filter((f) => f.endsWith('.rad'))) assets.customCars.push({ file: f, name: f.slice(0, -4), text: read('mycars/' + f) });

const world = new World(assets);
console.log('cars:', world.carList.map((c) => c.name).join(', '));
const stageNum = Number(process.argv[2] || 1);
const stage = { id: stageNum, name: 'test', text: read(`stages/${stageNum}.txt`) };
const def = world.loadStage(stage, 7);
console.log(`stage ${stageNum}: objects=${def.objects.length} trackers=${world.trackers.nt} checkpoints=${def.cp.nsp} laps=${def.cp.nlaps}`);
const race = new Race(world, def, { stage, playerCar: 14, opponents: [0, 1, 2, 3, 4, 5] });
let crashes = 0;
race.events.on('crash', () => crashes++);
race.events.on('finish', (r) => console.log('finish', r, 'at tick', race.tick));
// Let the AI drive the player car too (use a spare Control).
const t0 = Date.now();
for (let i = 0; i < 25 * 240 && !race.finished; i++) {
  const ai = race.controls[0];
  if (race.countdown === 0) ai.preform(race.mads[0], race.cars[0], race.cp, world.trackers);
  race.step({ left: ai.left, right: ai.right, up: ai.up, down: ai.down, handb: ai.handb, lookback: 0 });
  if (i % 500 === 0) console.log(`t=${i} p0 pos=(${race.cars[0].x},${race.cars[0].y},${race.cars[0].z}) speed=${race.mads[0].speed.toFixed(1)} clear=${race.cp.clear.slice(0, 7).join(',')} power=${race.mads[0].power.toFixed(0)}`);
}
console.log(`ticks=${race.tick} crashes=${crashes} ms=${Date.now() - t0}`);
