import fs from 'node:fs'; import path from 'node:path'; import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js'; import { Race } from '../src/game/race.js';
const dir = process.argv[2]; const only = process.argv[3];
const L = new TextDecoder('latin1');
const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.resolve('../data/models.zip'))))) assets.models[k] = L.decode(v);
const world = new World(assets);
for (const f of fs.readdirSync(dir).filter((f) => f.endsWith('.txt') && !f.startsWith('Example') && (!only || f.includes(only)))) {
  const text = L.decode(fs.readFileSync(path.join(dir, f)));
  const stage = { id: -1, name: f, text };
  const def = world.loadStage(stage, 7);
  const race = new Race(world, def, { stage, playerCar: 14, opponents: [0, 1, 2, 3, 4, 5] });
  race.finish = () => {}; // keep running after someone finishes
  race.noRescue = process.env.NORESCUE === '1';
  const total = def.cp.nlaps * def.cp.nsp;
  let t = 0, firstDone = null;
  for (; t < 12000; t++) {
    race.step({}); // player car idles; we only judge the AI
    const done = [1, 2, 3, 4, 5, 6].filter((i) => race.mads[i].clear >= total || race.mads[i].dest);
    if (firstDone === null && done.length) firstDone = t;
    if (done.length === 6) break;
  }
  const finished = [1, 2, 3, 4, 5, 6].filter((i) => race.mads[i].clear >= total).length;
  const wasted = [1, 2, 3, 4, 5, 6].filter((i) => race.mads[i].dest).length;
  const alive = [1, 2, 3, 4, 5, 6].filter((i) => !race.mads[i].dest);
  const prog = alive.length ? alive.reduce((s, i) => s + Math.min(1, race.mads[i].clear / total), 0) / alive.length : 0;
  console.log(`${f.slice(0, 30).padEnd(30)} fin=${finished}/6 wasted=${wasted} avgProgress(alive)=${(prog * 100).toFixed(0)}% rescues=${race.rescues}`);
}
