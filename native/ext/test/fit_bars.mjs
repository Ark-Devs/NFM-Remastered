// Strength and Endurance bars from the ram-bot fights (ramfight.exe "all":
// "A|B|winsA|winsB|dmgAtoB|dmgBtoA|seconds|hitsA|hitsB", damage = total % of
// the victim's health over 6 fights, hits = separate hits each car landed),
// or from the clash lab (joust.exe: per-clash damage, no hit counts).
//
// Fit: damage per hit A lands on B = exp(strength[A] - endurance[B]).
// Per hit, not per minute: a car that wins in seconds (MASHEEN) would
// otherwise look fragile, since any scratch it takes lands in a short fight.
// Both bars share one step size, so they read against each other:
//   your Strength vs their Endurance -> how many hits you need to waste them,
//   Strength + Endurance -> who wins a straight fight.
// The fit also pulls each Strength + Endurance onto the car's fight rating
// (Bradley-Terry over who actually won), so totals follow the fight record:
// one-shot hitters like MASHEEN would otherwise be underrated.
// Each bar gets its own zero so both use 0..10; the offset between them is
// reported so the "equal bars" rule stays exact.
//
// usage: node fit_bars.mjs <fights.txt> [more fights.txt ...] <out crash_stats.json>
import fs from 'fs';

const EPS = 0.2; // floor: % per hit (a hit that barely scratches)
// any number of fight files (e.g. ram-bot + game-AI), output path last
const inputs = process.argv.slice(2, -1);
const lines = inputs.flatMap((p) => fs.readFileSync(p, 'utf8').trim().split(/\r?\n/).map((l) => l.split('|')));
const FIGHTS = lines[0].length >= 7 ? 6 : 3; // fights per pairing (ramfight 6, joust 3)
const rows = [];
// per hit: ramfight lines carry hit counts (8th / 9th fields); joust lines are
// already per clash. A car that never landed a hit gives no per-hit sample.
for (const [a, b, , , ab, ba, , ha, hb] of lines) {
  const per = (d, h) => (h === undefined ? +d : +h > 0 ? +d / +h : null);
  const x = per(ab, ha), y = per(ba, hb);
  if (x !== null) rows.push({ a, v: b, y: Math.log(Math.max(EPS, x)) });
  if (y !== null) rows.push({ a: b, v: a, y: Math.log(Math.max(EPS, y)) });
}
const cars = [...new Set(rows.map((r) => r.a))];
const P = Object.fromEntries(cars.map((c) => [c, 0]));
const T = Object.fromEntries(cars.map((c) => [c, 0]));
for (let it = 0; it < 400; it++) {
  for (const c of cars) { let s = 0, n = 0; for (const r of rows) if (r.a === c) { s += r.y + T[r.v]; n++; } P[c] = s / n; }
  for (const c of cars) { let s = 0, n = 0; for (const r of rows) if (r.v === c) { s += P[r.a] - r.y; n++; } T[c] = s / n; }
  const m = cars.reduce((s, c) => s + T[c], 0) / cars.length; // pin the free offset
  for (const c of cars) { T[c] -= m; P[c] -= m; }
}

// totals must follow the fight record too: Bradley-Terry rating from wins,
// then refit with a penalty pulling each STR + END onto that rating
{
  const W = {}, G = {};
  for (const c of cars) { W[c] = 0.5; G[c] = {}; }
  for (const [a, b, wa, wb] of lines) {
    const d = FIGHTS - +wa - +wb;
    W[a] += +wa + d / 2; W[b] += +wb + d / 2;
    G[a][b] = (G[a][b] || 0) + FIGHTS; G[b][a] = (G[b][a] || 0) + FIGHTS;
  }
  let R = Object.fromEntries(cars.map((c) => [c, 1]));
  for (let it = 0; it < 500; it++) {
    const nr = {};
    for (const c of cars) { let q = 0; for (const o in G[c]) q += G[c][o] / (R[c] + R[o]); nr[c] = W[c] / q; }
    const g = Math.exp(cars.reduce((q, c) => q + Math.log(nr[c]), 0) / cars.length);
    for (const c of cars) R[c] = nr[c] / g;
  }
  const rt = Object.fromEntries(cars.map((c) => [c, Math.log(R[c])]));
  const mr = cars.reduce((q, c) => q + rt[c], 0) / cars.length;
  const ms = cars.reduce((q, c) => q + P[c] + T[c], 0) / cars.length;
  let sxy = 0, sxx = 0;
  for (const c of cars) { sxy += (rt[c] - mr) * (P[c] + T[c] - ms); sxx += (rt[c] - mr) ** 2; }
  const S = Object.fromEntries(cars.map((c) => [c, ms + (rt[c] - mr) * sxy / sxx]));
  const LAMBDA = 60; // weight of the fight record against the ~102 damage samples per car
  const byA = {}, byV = {};
  for (const r of rows) { (byA[r.a] ||= []).push(r); (byV[r.v] ||= []).push(r); }
  for (let it = 0; it < 600; it++) {
    for (const c of cars) {
      // minimise sum over its damage rows + LAMBDA*(P+T-S)^2, jointly in P and T
      let sa = 0, na = byA[c].length, sv = 0, nv = byV[c].length;
      for (const r of byA[c]) sa += r.y + T[r.v];
      for (const r of byV[c]) sv += P[r.a] - r.y;
      // normal equations: (na+L) P + L T = sa + L S ; L P + (nv+L) T = sv + L S
      const L = LAMBDA, det = (na + L) * (nv + L) - L * L;
      const rhs1 = sa + L * S[c], rhs2 = sv + L * S[c];
      P[c] = (rhs1 * (nv + L) - L * rhs2) / det;
      T[c] = ((na + L) * rhs2 - L * rhs1) / det;
    }
  }
}
let se = 0, st = 0;
const mean = rows.reduce((s, r) => s + r.y, 0) / rows.length;
for (const r of rows) { se += (r.y - (P[r.a] - T[r.v])) ** 2; st += (r.y - mean) ** 2; }
console.log(`fit: R² ${(1 - se / st).toFixed(2)}, typical error x${Math.exp(Math.sqrt(se / rows.length)).toFixed(2)} damage per clash`);

let ok = 0, tot = 0, okBig = 0, big = 0;
for (const [a, b, wa, wb] of lines) {
  if (+wa === +wb) continue;
  const d = (P[a] + T[a]) - (P[b] + T[b]);
  tot++; if ((d > 0) === (+wa > +wb)) ok++;
  if (Math.abs(d) > 0.5) { big++; if ((d > 0) === (+wa > +wb)) okBig++; }
}
console.log(`STR+END picks the winner of ${(100 * ok / tot).toFixed(0)}% of ${tot} pairings (${(100 * okBig / big).toFixed(0)}% of ${big} clear-cut ones)`);

const pv = Object.values(P), tv = Object.values(T);
const k = Math.max(Math.max(...pv) - Math.min(...pv), Math.max(...tv) - Math.min(...tv));
const p0 = Math.min(...pv), t0 = Math.min(...tv);
const barP = (x) => (x - p0) / k, barT = (x) => (x - t0) / k;
const out = {};
for (const c of cars) out[c] = { strength: +barP(P[c]).toFixed(3), endurance: +barT(T[c]).toFixed(3) };
fs.writeFileSync(process.argv[process.argv.length - 1], JSON.stringify(out, null, 1));
// clashes needed when STR bar == END bar: damage = exp(p0 - t0) at bar 0 each
const step = Math.exp(k / 10);
const atEqual = 100 / Math.exp(p0 - t0);
console.log(`one bar step = x${step.toFixed(2)}; your Strength bar equal to their Endurance bar -> about ${atEqual.toFixed(1)} units (clashes / minutes) to waste them`);
const hits = (a, v) => 100 / Math.exp(P[a] - T[v]);
for (const [a, v] of [['M A S H E E N', 'Cross'], ['Cross', 'M A S H E E N'], ['Jet Blade', 'The Raven'], ['The Raven', 'Jet Blade'], ['Jet Blade', 'Vigilante'], ['Vigilante', 'Jet Blade']])
  if (P[a] !== undefined) console.log(`  ${a} needs ~${hits(a, v).toFixed(1)} units to waste ${v}`);
const wins = {};
for (const [a, b, wa, wb] of lines) { wins[a] = (wins[a] || 0) + +wa; wins[b] = (wins[b] || 0) + +wb; }
console.log('\ncar                       STR  END  total   fights won');
cars.sort((a, b) => (P[b] + T[b]) - (P[a] + T[a])).forEach((c) => console.log(c.padEnd(24), (barP(P[c]) * 10).toFixed(1).padStart(5), (barT(T[c]) * 10).toFixed(1).padStart(5), (barP(P[c]) * 10 + barT(T[c]) * 10).toFixed(1).padStart(6), `   ${wins[c]}/${(cars.length - 1) * FIGHTS * inputs.length}`));
