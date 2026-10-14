import { Assets } from './game/assets.js';
import { World } from './game/world.js';
import { Race, TICK_MS } from './game/race.js';
import { buildCareer } from './game/career.js';
import { Renderer, QUALITY } from './render/renderer.js';
import { GameView } from './render/gameview.js';
import { Showroom } from './render/showroom.js';
import { Hud } from './ui/hud.js';
import { Front } from './ui/front.js';
import { Input } from './input.js';
import { Audio } from './audio.js';
import { ModPlayer } from './modplayer.js';

const ui = document.getElementById('ui');
const canvas = document.getElementById('view');

const store = {
  get(k, d) { try { const v = localStorage.getItem('nfmr.' + k); return v === null ? d : JSON.parse(v); } catch { return d; } },
  set(k, v) { try { localStorage.setItem('nfmr.' + k, JSON.stringify(v)); } catch { /* storage unavailable */ } },
};

const app = {
  state: 'loading',
  settings: { car: store.get('car', 14), stage: store.get('stage', 0), opponents: store.get('opponents', 6), quality: store.get('quality', 'high'), music: store.get('music', true), impact: store.get('impact', 'weighty'), body: store.get('body', 'smooth') },
};
if (import.meta.env.DEV) window.nfm = app;

function h(html) {
  const t = document.createElement('template');
  t.innerHTML = html.trim();
  return t.content.firstElementChild;
}
const esc = (s) => String(s).replace(/[&<>"]/g, (c) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' }[c]));

async function boot() {
  const screen = h(`<div class="screen title-screen"><div class="logo big"><div class="l1">NEED FOR MADNESS</div><div class="l2">REMASTERED</div></div><div class="msg">Starting…</div></div>`);
  ui.appendChild(screen);
  const msg = screen.querySelector('.msg');
  try {
    app.renderer = new Renderer(canvas);
    app.renderer.setQuality(QUALITY[app.settings.quality] ? app.settings.quality : 'high');
    app.renderer.bodyStyle = app.settings.body;
    app.assets = new Assets();
    await app.assets.load((m) => { msg.textContent = m + '…'; });
    msg.textContent = 'Building cars…';
    await new Promise((r) => setTimeout(r, 0));
    app.world = new World(app.assets);
    app.career = buildCareer(app.assets, app.world);
    app.input = new Input();
    app.audio = new Audio(app.assets.sounds);
    app.showroom = new Showroom(app.renderer);
    app.front = new Front(app, { ui, store, startRace, playMenuMusic });
    app.renderer.onResize = () => (app.view || app.preview?.view)?.onResize();
    app.renderer.onQuality = (q) => (app.view || app.preview?.view)?.env.setShadowQuality(q);
    msg.innerHTML = '<span class="press">PRESS ENTER</span>';
    await new Promise((res) => {
      const start = () => { window.removeEventListener('keydown', onKey); screen.removeEventListener('click', start); res(); };
      const onKey = (e) => { if (e.code === 'Enter' || e.code === 'Space') start(); };
      screen.addEventListener('click', start);
      window.addEventListener('keydown', onKey);
    });
    msg.textContent = 'Loading sound…';
    await app.audio.init();
    screen.remove();
    requestAnimationFrame(loop);
    app.front.mainMenu();
  } catch (e) {
    console.error(e);
    msg.innerHTML = `<div class="err">Could not start: ${esc(e.message)}\n\nMake sure this folder sits inside the Need for Madness game folder and run "npm run dev".</div>`;
  }
}

async function playMenuMusic() {
  if (!app.settings.music || app.menuMusic) return;
  app.menuMusic = true;
  const bytes = await app.assets.music('interface');
  if (app.menuMusic && app.state !== 'race') app.audio.playMusic(bytes, ModPlayer);
}

/* -------------------------------------------------------------------- race */
function pickFrom(pool, n, exclude) {
  const avail = pool.filter((s) => s !== exclude && app.world.base[s]);
  const out = [];
  let bag = [];
  while (out.length < n) {
    if (!bag.length) bag = avail.length ? [...avail] : [0];
    out.push(bag.splice(Math.floor(Math.random() * bag.length), 1)[0]);
  }
  return out;
}

// opts: { career: {chapter, stage} } or {} for free play; grid: reuse a grid
async function startRace(opts = {}, grid = null) {
  const { world, assets, settings, renderer, career } = app;
  app.front.clear();
  endRace();
  app.showroom.leave();
  app.menuMusic = false;
  const at = opts.career;
  const entry = at ? career.chapters[at.chapter].stages[at.stage] : null;
  const stage = assets.stages[entry ? entry.index : settings.stage];
  const playerCar = at ? career.s.car ?? 0 : settings.car;
  let rivals = [];
  let opponents = grid;
  const nOpp = stage.solo ? 0 : at ? 6 : settings.opponents;
  if (!opponents) {
    if (at) {
      // the stage's rivals always line up; the rest come from the same stretch of the career
      rivals = entry.rivals.filter((s) => s !== playerCar && world.base[s]);
      opponents = [...rivals, ...pickFrom(entry.pool, Math.max(0, nOpp - rivals.length), playerCar)].slice(0, nOpp);
    } else {
      opponents = pickFrom(world.carList.filter((c) => !c.custom).map((c) => c.slot), nOpp, playerCar);
    }
  } else if (at) rivals = entry.rivals.filter((s) => opponents.includes(s));
  const nplayers = 1 + opponents.length;
  const def = world.loadStage(stage, nplayers);
  const race = new Race(world, def, { stage, playerCar, opponents, nplayers, landk: settings.impact === 'classic' ? 1 : 0.3 });
  race.rivals = rivals;
  race.careerAt = at;
  app.race = race;
  app.raceOpts = opts;
  app.grid = opponents;
  app.view = new GameView(renderer, world, race);
  app.hud = new Hud(ui);
  app.hud.setTrack(def);
  app.acc = 0;
  app.paused = false;
  app.state = 'race';
  app.lastMissed = 0;
  app.audio.startRace(world.cd.enginsignature[playerCar]);
  wireRaceEvents(race);
  const sub = rivals.length
    ? `RIVAL: ${rivals.map((s) => world.cd.names[s]).join(' · ')}`
    : `${def.cp.nlaps} LAP${def.cp.nlaps > 1 ? 'S' : ''} · ${nplayers} CAR${nplayers > 1 ? 'S' : ''}`;
  app.hud.message(stage.name, sub, 2.6);
  app.audio.stopMusic();
  if (settings.music) {
    let bytes = null;
    if (stage.id > 0) bytes = await assets.music('stage' + stage.id);
    else if (def.env.soundtrack) bytes = await assets.customMusic(def.env.soundtrack);
    if (app.race === race) app.audio.playMusic(bytes, ModPlayer);
  }
}

function wireRaceEvents(race) {
  const { audio, hud } = app;
  const ev = race.events;
  ev.on('count', (n) => { audio.play(['', 'one', 'two', 'three'][n]); hud.message(String(n)); });
  ev.on('go', () => { audio.play('go'); hud.message('GO!', '', 0.9); });
  ev.on('checkpoint', (clear) => {
    const cp = race.cp;
    if (clear % cp.nsp === 0) return; // lap events announce the finish line
    audio.play('checkpoint', 0.8);
    hud.toast(`Checkpoint ${clear % cp.nsp} / ${cp.nsp}`);
  });
  ev.on('lap', (lap) => { audio.play('checkpoint', 0.9); hud.message(lap === race.cp.nlaps ? 'FINAL LAP' : `LAP ${lap}`, '', 1.4); });
  ev.on('crash', (f, n) => {
    audio.crash(f, n);
    // hit-stop: a big impact briefly slows time so it lands with weight
    if (Math.abs(f) >= 170 && app.settings.impact !== 'classic') app.hitStop = Math.min(0.14, 0.06 + Math.abs(f) / 4000);
  });
  ev.on('skid', (n, f) => audio.skid(n, f));
  ev.on('scrape', (x, y, z) => audio.scrape(x, y, z));
  ev.on('gscrape', (x, y, z) => audio.gscrape(x, y, z));
  ev.on('carfixed', () => { audio.play('carfixed', 0.8); hud.toast('Car fixed!'); });
  ev.on('stunt', (p) => { if (p > 0) { audio.play('powerup', 0.8); hud.toast(`Stunt! Power +${p}%`); } });
  ev.on('wasted', (i, byYou) => {
    if (i === 0) { audio.play('firewasted'); hud.message('WASTED', 'your car is destroyed', 2.5); return; }
    audio.play('firewasted', 0.4);
    const name = race.world.cd.names[race.sc[i]];
    const rival = race.rivals.includes(race.sc[i]) && !app.career.owns(race.sc[i]);
    if (rival && byYou) hud.message('RIVAL WRECKED', `${name} is yours`, 2.2);
    else hud.toast(`${name} wasted!`);
  });
  ev.on('finish', (r) => setTimeout(() => { if (app.race === race) showResults(r); }, r.kind === 'wasted' ? 3500 : 2500));
}

function endRace() {
  if (!app.race) return;
  app.view?.dispose();
  app.hud?.destroy();
  app.audio.stopRace();
  app.view = null;
  app.hud = null;
  app.race = null;
}

function toMenu(fn) {
  app.audio.ctx?.resume();
  app.paused = false;
  endRace();
  app.audio.stopMusic();
  app.menuMusic = false;
  fn();
}

/* ---------------------------------------------------------------- overlays */
function pause(on) {
  if (app.state !== 'race' || (app.race?.finished && on)) return;
  app.paused = on;
  if (on) {
    app.audio.ctx?.suspend();
    const mode = app.raceOpts.career ? 'career' : 'free';
    app.front.card(`<div class="rtitle">PAUSED</div><div class="rsub">${esc(app.race.stage.name)}</div>`, [
      ['Resume', () => pause(false)],
      ['Restart race', () => { app.audio.ctx?.resume(); startRace(app.raceOpts, app.grid); }],
      [`Graphics: ${QUALITY[app.settings.quality].label}`, cycleQuality],
      ['Quit to stage select', () => toMenu(() => app.front.stageSelect(mode, app.raceOpts.career))],
    ], 'pause');
  } else {
    app.front.clear();
    app.input.take('Escape'); app.input.take('KeyP'); // the key that closed the menu
    app.audio.ctx?.resume();
  }
}

function cycleQuality() {
  const keys = Object.keys(QUALITY);
  const next = keys[(keys.indexOf(app.settings.quality) + 1) % keys.length];
  app.settings.quality = next;
  store.set('quality', next);
  app.renderer.setQuality(next);
  pause(true);
}

function showResults(r) {
  const race = app.race;
  const at = race.careerAt;
  let earned = null;
  if (at) {
    const wreckedRivals = [...race.wrecked].map((i) => race.sc[i]).filter((s) => race.rivals.includes(s));
    earned = app.career.record(at.chapter, at.stage, r, { laptime: race.xt.laptime, wreckedRivals });
  }
  const { html, win } = app.front.results(r, race, earned);
  const mode = at ? 'career' : 'free';
  const buttons = [];
  if (at) {
    const next = earned.nextStage;
    const onward = () => toMenu(() => app.front.reveal(earned.cars, () => app.front.stageSelect('career', next || at)));
    if (win || earned.cars.length) buttons.push([next ? 'Continue' : earned.cars.length ? 'Continue' : 'Stage select', onward]);
    buttons.push(['Race again', () => startRace(app.raceOpts, app.grid)]);
    if (!(win || earned.cars.length)) buttons.push(['Stage select', onward]);
  } else {
    buttons.push(['Race again', () => startRace(app.raceOpts, app.grid)]);
    buttons.push(['New opponents', () => startRace(app.raceOpts)]);
    buttons.push(['Stage select', () => toMenu(() => app.front.stageSelect(mode))]);
  }
  buttons.push(['Main menu', () => toMenu(() => app.front.mainMenu())]);
  app.front.card(html, buttons, 'results');
}

/* -------------------------------------------------------------------- loop */
let last = performance.now();
let elapsed = 0;
function loop(now) {
  requestAnimationFrame(loop);
  const dt = Math.min(0.1, (now - last) / 1000);
  last = now;
  elapsed += dt;
  const { input, renderer } = app;
  if (input.take('KeyM')) app.audio.setMuted(!app.audio.muted);

  if (app.state === 'stages') {
    app.preview?.view.frameOrbit(dt);
    renderer.render(elapsed, 0);
    input.clearPressed();
    return;
  }
  if (app.state !== 'race' || !app.race) {
    app.showroom.frame(dt, elapsed);
    renderer.render(elapsed, 0);
    input.clearPressed();
    return;
  }
  if (!app.paused && (input.take('Escape') || input.take('KeyP'))) pause(true);
  const race = app.race;
  const view = app.view;
  const ctl = input.state();
  if (input.take('KeyC')) app.hud.camera(view.cycleCamera());
  if (!app.paused) {
    let timeScale = 1;
    if (app.hitStop > 0) { app.hitStop -= dt; timeScale = 0.25; }
    app.acc += dt * 1000 * timeScale;
    let steps = 0;
    while (app.acc >= TICK_MS && steps < 5) {
      view.beforeStep();
      race.step(ctl);
      view.afterStep();
      app.audio.tick(race.player, race.controls[0], app.world.cd, race.countdown === 0 && !race.finished);
      const missed = race.player.missedcp;
      if (missed > 0 && app.lastMissed <= 0) app.hud.toast('Missed a checkpoint!');
      app.lastMissed = missed;
      app.acc -= TICK_MS;
      steps++;
    }
    if (steps === 5) app.acc = 0;
  }
  const alpha = app.paused ? 1 : app.acc / TICK_MS;
  view.frame(app.paused ? 0 : dt, alpha, ctl);
  app.hud.update(dt, race, renderer.camera, app.world);
  renderer.render(elapsed, view.shake);
  input.clearPressed();
}

if (import.meta.env.DEV) {
  // Advance frames by hand (rAF pauses while the tab is hidden).
  window.nfmFrames = (n = 1, ms = 16.7) => { for (let i = 0; i < n; i++) loop(last + ms); };
}

boot();
