import fs from 'node:fs'; import path from 'node:path'; import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js'; import { Race } from '../src/game/race.js';
const dir = process.argv[2];
const L = new TextDecoder('latin1');
const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.resolve('../data/models.zip'))))) assets.models[k] = L.decode(v);
const world = new World(assets);
for (const f of fs.readdirSync(dir).filter((f) => f.endsWith('.txt'))) {
  const text = L.decode(fs.readFileSync(path.join(dir, f)));
  const name = (/^\s*name\(([^)]*)\)/m.exec(text) || [])[1] || f;
  const ids = [...text.matchAll(/^\s*(set|chk|fix)\((\d+),/gm)].map((m) => +m[2] + 46);
  const unknown = [...new Set(ids.filter((s) => !world.base[s]))].map((s) => s - 46);
  let res = '';
  try {
    const stage = { id: -1, name, text };
    const def = world.loadStage(stage, 7);
    const b = def.bounds;
    const walls = b.maxx > b.minx && b.maxz > b.minz;
    let finished = 'no', ticks = 0;
    const race = new Race(world, def, { stage, playerCar: 14, opponents: [0, 1, 2, 3, 4, 5] });
    for (; ticks < 3000 && !race.finished; ticks++) { const ai = race.controls[0]; if (race.countdown === 0) ai.preform(race.mads[0], race.cars[0], race.cp, world.trackers); race.step({ left: ai.left, right: ai.right, up: ai.up, down: ai.down, handb: ai.handb }); }
    const maxClear = Math.max(...Array.from(race.cp.clear).slice(0, 7));
    res = `objs=${def.objects.length} cps=${def.cp.nsp} laps=${def.cp.nlaps} walls=${walls} unknownPieces=[${unknown}] aiMaxClear=${maxClear} ${race.finished ? race.result.kind : ''}`;
  } catch (e) { res = 'ERROR ' + e.message; }
  console.log(`${f.padEnd(40)} ${name.slice(0, 32).padEnd(32)} ${res}`);
}
