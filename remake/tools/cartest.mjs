import fs from 'node:fs'; import path from 'node:path'; import { unzipSync } from 'fflate';
import { World } from '../src/game/world.js';
import { ContO } from '../src/gen/ContO.js';
const dir = process.argv[2];
const L = new TextDecoder('latin1');
const assets = { models: {}, customCars: [] };
for (const [k, v] of Object.entries(unzipSync(fs.readFileSync(path.resolve('../data/models.zip'))))) assets.models[k] = L.decode(v);
const world = new World(assets);
for (const f of fs.readdirSync(dir).filter((f) => f.endsWith('.rad'))) {
  const text = L.decode(fs.readFileSync(path.join(dir, f)));
  const name = (/^name\((.*)\)/m.exec(text) || [])[1] || f;
  world.medium.loadnew = true;
  let c; try { c = ContO.create0(text, world.medium, world.trackers); } catch (e) { console.log(f, 'PARSE ERROR', e.message); continue; }
  world.medium.loadnew = false;
  const k = c.keyz, x = c.keyx;
  const wheelsOk = !(k[0] < 0 || x[0] > 0 || k[1] < 0 || x[1] < 0 || k[2] > 0 || x[2] > 0 || k[3] > 0 || x[3] < 0);
  const lines = text.split(/\r?\n/).filter((l) => /^<p>/.test(l.trim())).length;
  const hasStat = /^stat\(/m.test(text), hasPhys = /^physics\(/m.test(text), hasSwits = /^swits\(/m.test(text);
  console.log(`${f.padEnd(22)} ${name.padEnd(18)} polys=${lines} npl=${c.npl} maxR=${c.maxR} errd=${c.errd} wheelsOk=${wheelsOk} stat=${hasStat} phys=${hasPhys} direct=${hasSwits}`);
}
