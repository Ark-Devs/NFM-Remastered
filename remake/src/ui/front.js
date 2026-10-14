// Front end: main menu, car select, stage select, options, results and the
// new-car reveal. Everything is keyboard driven (arrows / Enter / Esc) and
// clickable, and sits over a live 3D scene: the studio for cars, the actual
// stage for stage select.
import { QUALITY } from '../render/renderer.js';
import { GameView } from '../render/gameview.js';
import { Race } from '../game/race.js';
import { ordinal, fmtTime } from './hud.js';

const esc = (s) => String(s).replace(/[&<>"]/g, (c) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;' }[c]));
function h(html) {
  const t = document.createElement('template');
  t.innerHTML = html.trim();
  return t.content.firstElementChild;
}
const pad2 = (n) => String(n).padStart(2, '0');
const ARROW_L = '<svg viewBox="0 0 24 48"><path d="M20 2 L4 24 L20 46" fill="none" stroke="currentColor" stroke-width="2"/></svg>';
const ARROW_R = '<svg viewBox="0 0 24 48"><path d="M4 2 L20 24 L4 46" fill="none" stroke="currentColor" stroke-width="2"/></svg>';
const LOCK = '<svg viewBox="0 0 24 24" class="lock"><path d="M7 10V7a5 5 0 0 1 10 0v3" fill="none" stroke="currentColor" stroke-width="2"/><rect x="4.5" y="10" width="15" height="11" rx="1.5" fill="currentColor"/></svg>';

export function carStats(cd, slot) {
  const clamp = (v) => Math.max(0.04, Math.min(1, v));
  return [
    ['Top speed', clamp((cd.swits[slot][2] - 200) / 115)],
    ['Acceleration', clamp((cd.acelf[slot][0] + cd.acelf[slot][1] + cd.acelf[slot][2] - 13) / 13)],
    ['Stunts', clamp((cd.airs[slot] * cd.airc[slot]) / 130)],
    ['Strength', clamp((cd.moment[slot] - 0.7) / 2.3)],
    ['Endurance', clamp(cd.maxmag[slot] / 20000)],
  ];
}

export class Front {
  constructor(app, { ui, store, startRace, playMenuMusic }) {
    this.app = app;
    this.ui = ui;
    this.store = store;
    this.startRace = startRace;
    this.playMenuMusic = playMenuMusic;
    this.el = null;
    this.keys = null;
    window.addEventListener('keydown', (e) => {
      if (!this.keys || e.target instanceof HTMLInputElement) return;
      if (this.keys(e.code, e) !== false) e.preventDefault();
    });
  }

  /* ---------------------------------------------------------- plumbing */
  mount(el, keys) {
    this.clear();
    this.el = el;
    this.keys = keys;
    this.ui.appendChild(el);
  }

  clear() {
    this.el?.remove();
    this.el = null;
    this.keys = null;
    clearTimeout(this.previewTimer);
  }

  prompts(list) {
    return `<div class="prompts">${list.map(([k, label, act]) => `<button class="prompt" ${act ? `data-act="${act}"` : 'tabindex="-1"'}>${k.split(' ').map((x) => `<kbd>${x}</kbd>`).join('')}<span>${label}</span></button>`).join('')}</div>`;
  }

  wirePrompts(el, acts) {
    el.querySelectorAll('.prompt[data-act]').forEach((b) => { b.onclick = () => acts[b.dataset.act]?.(); });
  }

  sfx(name = 'checkpoint', vol = 0.35) { this.app.audio.play(name, vol); }

  studio(mode) {
    const app = this.app;
    this.disposePreview();
    app.showroom.enter(mode);
  }

  /* --------------------------------------------------------- main menu */
  mainMenu(focus = 0) {
    const app = this.app;
    app.state = 'menu';
    this.studio('menu');
    app.audio.stopRace();
    if (app.showroom.slot !== this.menuCar() || app.showroom.locked) app.showroom.show(app.world, this.menuCar());
    this.playMenuMusic();
    const career = app.career;
    const c = career.s.chapter ?? 0;
    const chapter = career.chapters[c];
    const careerSub = career.s.won.length
      ? `${chapter.title} · Stage ${career.frontier(c) + 1} of ${chapter.stages.length} · ${career.s.cars.length} cars`
      : 'Start from the bottom. Win your way up.';
    const items = [
      ['CAREER', careerSub, () => this.carSelect('career')],
      ['FREE PLAY', 'Every car, every stage, unlocked', () => this.carSelect('free')],
      ['OPTIONS', 'Graphics, sound and handling feel', () => this.options()],
    ];
    const el = h(`<div class="screen menu fade-in">
      <div class="logo"><div class="l1">NEED FOR MADNESS</div><div class="l2">REMASTERED</div></div>
      <nav class="mainnav">${items.map(([t, s], i) => `<button class="mitem" data-i="${i}"><span class="t">${t}</span><span class="s">${esc(s)}</span></button>`).join('')}</nav>
      ${this.prompts([['↑ ↓', 'Select'], ['Enter', 'Confirm']])}
    </div>`);
    let sel = focus;
    const btns = [...el.querySelectorAll('.mitem')];
    const draw = () => btns.forEach((b, i) => b.classList.toggle('on', i === sel));
    btns.forEach((b, i) => {
      b.onmouseenter = () => { sel = i; draw(); };
      b.onclick = () => items[i][2]();
    });
    draw();
    this.mount(el, (code) => {
      if (code === 'ArrowUp' || code === 'KeyW') { sel = (sel + items.length - 1) % items.length; draw(); }
      else if (code === 'ArrowDown' || code === 'KeyS') { sel = (sel + 1) % items.length; draw(); }
      else if (code === 'Enter' || code === 'Space') items[sel][2]();
      else return false;
    });
  }

  menuCar() {
    const s = this.app.settings;
    return this.app.world.base[s.car] ? s.car : 14;
  }

  /* -------------------------------------------------------- car select */
  carSelect(mode) {
    const app = this.app;
    const { world, career, settings } = app;
    app.state = 'cars';
    this.studio('select');
    const list = world.carList;
    const isLocked = (slot) => mode === 'career' && !career.owns(slot);
    let idx = Math.max(0, list.findIndex((c) => c.slot === (mode === 'career' ? (career.s.car ?? 0) : settings.car)));
    if (isLocked(list[idx].slot)) idx = list.findIndex((c) => !isLocked(c.slot));

    const el = h(`<div class="screen cars fade-in">
      <header class="crumbs"><span class="mode">${mode === 'career' ? 'CAREER' : 'FREE PLAY'}</span><span class="sep">/</span><span>SELECT CAR</span></header>
      <div class="count"></div>
      <button class="chev left">${ARROW_L}</button><button class="chev right">${ARROW_R}</button>
      <div class="carinfo">
        <div class="tag"></div>
        <div class="name"></div>
        <div class="lockline"></div>
      </div>
      <div class="statbox"></div>
      <div class="rail"><i></i></div>
      ${this.prompts([['← →', 'Change car', 'next'], ['Enter', 'Choose', 'ok'], ['Esc', 'Back', 'back']])}
    </div>`);
    const $ = (s) => el.querySelector(s);
    const draw = (dir = 0) => {
      const c = list[idx];
      const locked = isLocked(c.slot);
      app.showroom.show(world, c.slot, { dir, locked });
      $('.count').innerHTML = `<b>${pad2(idx + 1)}</b> / ${pad2(list.length)}`;
      $('.tag').textContent = c.custom ? 'COMMUNITY CAR' : 'ORIGINAL CAR';
      const name = $('.name');
      name.textContent = locked ? '? ? ?' : c.name;
      name.classList.remove('swap'); void name.offsetWidth; name.classList.add('swap');
      const src = locked ? career.carSource(c.slot) : null;
      $('.lockline').innerHTML = locked
        ? `${LOCK}<span>${src ? `Out on <b>${esc(src.name)}</b> — ${esc(career.chapters[src.chapter].title)}, stage ${src.stage + 1}. Beat it or wreck it there to take it.` : 'Locked'}</span>`
        : '';
      el.classList.toggle('locked', locked);
      $('.statbox').innerHTML = carStats(world.cd, c.slot).map(([n, v]) => `<div class="stat"><span>${n}</span><div class="sbar">${locked ? '' : `<i style="width:${(v * 100).toFixed(1)}%"></i>`}</div><em>${locked ? '—' : Math.round(v * 10)}</em></div>`).join('');
      $('.rail i').style.left = `${(idx / Math.max(1, list.length - 1)) * 100}%`;
    };
    const step = (d) => { idx = (idx + d + list.length) % list.length; draw(d); this.sfx('checkpoint', 0.18); };
    const choose = () => {
      const c = list[idx];
      if (isLocked(c.slot)) { el.classList.remove('deny'); void el.offsetWidth; el.classList.add('deny'); return; }
      if (mode === 'career') { career.s.car = c.slot; career.save(); } else { settings.car = c.slot; this.store.set('car', c.slot); }
      this.sfx('powerup', 0.4);
      this.stageSelect(mode);
    };
    const back = () => this.mainMenu(mode === 'career' ? 0 : 1);
    $('.chev.left').onclick = () => step(-1);
    $('.chev.right').onclick = () => step(1);
    this.wirePrompts(el, { next: () => step(1), ok: choose, back });
    draw(0);
    this.mount(el, (code) => {
      if (code === 'ArrowLeft' || code === 'KeyA') step(-1);
      else if (code === 'ArrowRight' || code === 'KeyD') step(1);
      else if (code === 'Enter' || code === 'Space') choose();
      else if (code === 'Escape' || code === 'Backspace') back();
      else return false;
    });
  }

  /* ------------------------------------------------------ stage select */
  stageSelect(mode, at = null) {
    const app = this.app;
    const { assets, career, settings } = app;
    app.state = 'stages';
    app.showroom.leave();
    const careerMode = mode === 'career';
    let ch = careerMode ? (at?.chapter ?? career.s.chapter ?? 0) : 0;
    let k = careerMode ? (at?.stage ?? career.s.stage?.[ch] ?? career.frontier(ch)) : Math.min(settings.stage, assets.stages.length - 1);
    let row = careerMode ? 1 : 0;
    if (careerMode && !career.chapterOpen(ch)) { ch = 0; k = career.frontier(0); }

    const rows = careerMode ? ['CHAPTER', 'STAGE'] : ['STAGE', 'OPPONENTS'];
    const el = h(`<div class="screen stages fade-in">
      <header class="crumbs"><span class="mode">${careerMode ? 'CAREER' : 'FREE PLAY'}</span><span class="sep">/</span><span>SELECT STAGE</span></header>
      <div class="loadtag">Loading stage…</div>
      <button class="chev left">${ARROW_L}</button><button class="chev right">${ARROW_R}</button>
      <div class="lockveil">${LOCK}<span class="why"></span></div>
      <div class="stageinfo">
        <div class="tag"></div>
        <div class="name"></div>
        <div class="facts"></div>
        <div class="rival"></div>
      </div>
      <div class="rows">${rows.map((r, i) => `<div class="row" data-i="${i}"><span class="rl">${r}</span><button class="ra l">‹</button><span class="rv"></span><button class="ra r">›</button></div>`).join('')}</div>
      ${this.prompts([['↑ ↓', 'Row'], ['← →', 'Change', null], ['Enter', 'Race', 'ok'], ['Esc', 'Back', 'back']])}
    </div>`);
    const $ = (s) => el.querySelector(s);
    const entry = () => (careerMode ? career.chapters[ch].stages[k] : null);
    const stageIndex = () => (careerMode ? entry().index : k);
    const open = () => !careerMode || career.stageOpen(ch, k);

    const draw = (reload = true) => {
      const s = assets.stages[stageIndex()];
      const locked = !open();
      const n = careerMode ? career.chapters[ch].stages.length : assets.stages.length;
      $('.tag').textContent = careerMode ? `${career.chapters[ch].title.toUpperCase()} · STAGE ${pad2(k + 1)} OF ${n}` : `${s.custom ? 'COMMUNITY STAGE' : `STAGE ${pad2(s.id)}`}`;
      const name = $('.name');
      name.textContent = s.name;
      name.classList.remove('swap'); void name.offsetWidth; name.classList.add('swap');
      const facts = [];
      const laps = /nlaps\((\d+)\)/.exec(s.text)?.[1];
      if (laps) facts.push(`${laps} ${laps === '1' ? 'lap' : 'laps'}`);
      if (/lightson\(\)/.test(s.text)) facts.push('Night');
      if (s.solo) facts.push('Solo time run');
      if (s.custom && !careerMode) facts.push('Community');
      if (careerMode) {
        const best = career.s.best[entry().key];
        if (career.won(entry().key)) facts.push('Won');
        if (best) facts.push(`Best ${fmtTime(best)}`);
      }
      $('.facts').innerHTML = facts.map((f) => `<span>${esc(f)}</span>`).join('');
      const rv = $('.rival');
      if (careerMode && entry().rivals.length) {
        rv.style.display = '';
        rv.innerHTML = `<div class="rk">RIVAL${entry().rivals.length > 1 ? 'S' : ''} ON THIS STAGE</div>` + entry().rivals.map((slot) => {
          const owned = career.owns(slot);
          return `<div class="rc${owned ? ' owned' : ''}"><span>${esc(app.world.cd.names[slot])}</span><em>${owned ? 'In your garage' : 'Beat it or wreck it to take it'}</em></div>`;
        }).join('');
      } else rv.style.display = 'none';
      el.classList.toggle('locked', locked);
      const prev = careerMode && k > 0 ? assets.stages[career.chapters[ch].stages[k - 1].index].name : '';
      $('.why').innerHTML = careerMode && !career.chapterOpen(ch)
        ? `Win <b>${esc(career.chapters[ch - 1].title)}</b> to open this chapter`
        : `Win <b>${esc(prev)}</b> to unlock`;
      const vals = careerMode ? [career.chapters[ch].title, `${pad2(k + 1)}  ${s.name}`] : [`${pad2(k + 1)}  ${s.name}`, s.solo ? 'Solo' : String(settings.opponents)];
      el.querySelectorAll('.row').forEach((r, i) => { r.classList.toggle('on', i === row); r.querySelector('.rv').textContent = vals[i]; });
      if (reload) this.queuePreview(stageIndex(), el, careerMode ? career.s.car ?? 0 : settings.car);
    };
    const change = (d) => {
      if (careerMode && row === 0) {
        const nc = Math.max(0, Math.min(career.chapters.length - 1, ch + d));
        if (nc === ch) return;
        career.s.stage = { ...(career.s.stage || {}), [ch]: k };
        ch = nc; k = career.s.stage?.[ch] ?? career.frontier(ch);
      } else if (!careerMode && row === 1) {
        settings.opponents = Math.max(0, Math.min(6, settings.opponents + d));
        this.store.set('opponents', settings.opponents);
        draw(false);
        return;
      } else {
        const n = careerMode ? career.chapters[ch].stages.length : assets.stages.length;
        k = (k + d + n) % n;
      }
      this.sfx('checkpoint', 0.18);
      draw();
    };
    const go = () => {
      if (!open()) { el.classList.remove('deny'); void el.offsetWidth; el.classList.add('deny'); return; }
      if (careerMode) {
        career.s.chapter = ch;
        career.s.stage = { ...(career.s.stage || {}), [ch]: k };
        career.save();
        this.disposePreview();
        this.clear();
        this.startRace({ career: { chapter: ch, stage: k } });
      } else {
        settings.stage = k;
        this.store.set('stage', k);
        this.disposePreview();
        this.clear();
        this.startRace({});
      }
    };
    const back = () => this.carSelect(mode);
    $('.chev.left').onclick = () => change(-1);
    $('.chev.right').onclick = () => change(1);
    el.querySelectorAll('.row').forEach((r, i) => {
      r.onclick = () => { row = i; draw(false); };
      r.querySelector('.l').onclick = (e) => { e.stopPropagation(); row = i; change(-1); };
      r.querySelector('.r').onclick = (e) => { e.stopPropagation(); row = i; change(1); };
    });
    this.wirePrompts(el, { ok: go, back });
    this.mount(el, (code) => {
      if (code === 'ArrowLeft' || code === 'KeyA') change(-1);
      else if (code === 'ArrowRight' || code === 'KeyD') change(1);
      else if (code === 'ArrowUp' || code === 'KeyW') { row = (row + rows.length - 1) % rows.length; draw(false); }
      else if (code === 'ArrowDown' || code === 'KeyS') { row = (row + 1) % rows.length; draw(false); }
      else if (code === 'Enter' || code === 'Space') go();
      else if (code === 'Escape' || code === 'Backspace') back();
      else return false;
    });
    draw(); // after mount: mounting cancels any queued preview
  }

  // Build the real stage behind the menu (after a short pause so flicking
  // through stages stays quick).
  queuePreview(index, el, car) {
    clearTimeout(this.previewTimer);
    el.classList.add('loading');
    this.previewTimer = setTimeout(() => {
      if (this.el !== el) return;
      const app = this.app;
      const stage = app.assets.stages[index];
      this.disposePreview();
      try {
        const def = app.world.loadStage(stage, 1);
        const race = new Race(app.world, def, { stage, playerCar: app.world.base[car] ? car : 0, opponents: [], nplayers: 1 });
        app.preview = { view: new GameView(app.renderer, app.world, race), race };
      } catch (e) {
        console.warn('stage preview failed', e);
      }
      el.classList.remove('loading');
    }, 220);
  }

  disposePreview() {
    const app = this.app;
    if (!app.preview) return;
    app.preview.view.dispose();
    app.preview = null;
  }

  /* ----------------------------------------------------------- options */
  options() {
    const app = this.app;
    const { settings } = app;
    app.state = 'menu';
    this.studio('menu');
    const rows = [
      ['GRAPHICS', () => QUALITY[settings.quality].label, (d) => {
        const keys = Object.keys(QUALITY);
        const v = keys[Math.max(0, Math.min(keys.length - 1, keys.indexOf(settings.quality) + d))];
        settings.quality = v; this.store.set('quality', v); app.renderer.setQuality(v);
      }],
      ['CAR BODIES', () => (settings.body === 'smooth' ? 'Smoothed' : 'Original shape'), () => {
        settings.body = settings.body === 'smooth' ? 'original' : 'smooth'; this.store.set('body', settings.body);
        app.renderer.bodyStyle = settings.body; app.showroom.show(app.world, this.menuCar());
      }],
      ['IMPACT FEEL', () => (settings.impact === 'weighty' ? 'Weighty' : 'Classic NFM'), () => {
        settings.impact = settings.impact === 'weighty' ? 'classic' : 'weighty'; this.store.set('impact', settings.impact);
      }],
      ['MUSIC', () => (settings.music ? 'On' : 'Off'), () => {
        settings.music = !settings.music; this.store.set('music', settings.music);
        if (settings.music) this.playMenuMusic(); else { app.audio.stopMusic(); app.menuMusic = false; }
      }],
      ['RESET CAREER', () => (this.confirmReset ? 'Press Enter again to wipe' : `${app.career.s.won.length} stages won`), null, () => {
        if (this.confirmReset) { app.career.reset(); this.confirmReset = false; } else this.confirmReset = true;
      }],
    ];
    this.confirmReset = false;
    const el = h(`<div class="screen menu options fade-in">
      <div class="logo small"><div class="l1">OPTIONS</div></div>
      <div class="optlist">${rows.map(([l], i) => `<div class="row" data-i="${i}"><span class="rl">${l}</span><button class="ra l">‹</button><span class="rv"></span><button class="ra r">›</button></div>`).join('')}</div>
      <div class="controls">
        <div class="ck">CONTROLS</div>
        <div><kbd>↑</kbd><kbd>↓</kbd><kbd>←</kbd><kbd>→</kbd> or <kbd>W</kbd><kbd>A</kbd><kbd>S</kbd><kbd>D</kbd> drive · in the air: stunts</div>
        <div><kbd>Space</kbd> handbrake · <kbd>C</kbd> camera · <kbd>Z</kbd> look back</div>
        <div><kbd>Esc</kbd> pause · <kbd>M</kbd> mute</div>
      </div>
      ${this.prompts([['↑ ↓', 'Select'], ['← →', 'Change'], ['Esc', 'Back', 'back']])}
    </div>`);
    let sel = 0;
    const els = [...el.querySelectorAll('.row')];
    const draw = () => els.forEach((r, i) => { r.classList.toggle('on', i === sel); r.querySelector('.rv').textContent = rows[i][1](); r.classList.toggle('danger', i === 4 && this.confirmReset); });
    const change = (d) => { const r = rows[sel]; (r[2] || r[3])?.(d); this.sfx('checkpoint', 0.18); draw(); };
    els.forEach((r, i) => {
      r.onclick = () => { sel = i; draw(); };
      r.querySelector('.l').onclick = (e) => { e.stopPropagation(); sel = i; change(-1); };
      r.querySelector('.r').onclick = (e) => { e.stopPropagation(); sel = i; change(1); };
    });
    const back = () => this.mainMenu(2);
    this.wirePrompts(el, { back });
    draw();
    this.mount(el, (code) => {
      if (code === 'ArrowUp' || code === 'KeyW') { sel = (sel + rows.length - 1) % rows.length; if (sel !== 4) this.confirmReset = false; draw(); }
      else if (code === 'ArrowDown' || code === 'KeyS') { sel = (sel + 1) % rows.length; this.confirmReset = false; draw(); }
      else if (code === 'ArrowLeft' || code === 'KeyA') { if (sel !== 4) change(-1); }
      else if (code === 'ArrowRight' || code === 'KeyD') { if (sel !== 4) change(1); }
      else if (code === 'Enter' || code === 'Space') change(1);
      else if (code === 'Escape' || code === 'Backspace') back();
      else return false;
    });
  }

  /* ---------------------------------------------------- race overlays */
  // buttons: [label, fn, primary]
  card(html, buttons, cls = '') {
    const el = h(`<div class="screen overlay ${cls} fade-in"><div class="card">${html}<div class="col">${buttons.map(([l], i) => `<button class="mitem small" data-i="${i}"><span class="t">${l}</span></button>`).join('')}</div></div></div>`);
    let sel = 0;
    const btns = [...el.querySelectorAll('.col .mitem')];
    const draw = () => btns.forEach((b, i) => b.classList.toggle('on', i === sel));
    btns.forEach((b, i) => { b.onmouseenter = () => { sel = i; draw(); }; b.onclick = () => buttons[i][1](); });
    draw();
    this.mount(el, (code) => {
      if (code === 'ArrowUp' || code === 'KeyW') { sel = (sel + btns.length - 1) % btns.length; draw(); }
      else if (code === 'ArrowDown' || code === 'KeyS') { sel = (sel + 1) % btns.length; draw(); }
      else if (code === 'Enter' || code === 'Space') buttons[sel][1]();
      else if (code === 'Escape' && cls === 'pause') buttons[0][1]();
      else return false;
    });
    return el;
  }

  results(r, race, earned) {
    const cd = this.app.world.cd;
    const titles = { finished: r.position === 1 ? 'YOU WON' : `${ordinal(r.position).toUpperCase()} PLACE`, wasted: 'WASTED', allwasted: 'ALL WASTED', lost: 'YOU LOST' };
    const subs = { finished: `Finished in ${fmtTime(race.xt.laptime)}`, wasted: 'Your car was destroyed', allwasted: 'You wrecked the whole field', lost: `${cd.names[race.sc[r.who]]} crossed the line first` };
    const order = [...Array(race.nplayers).keys()].sort((a, b) => race.cp.pos[a] - race.cp.pos[b]);
    const rows = race.nplayers > 1 ? order.map((i) => {
      const m = race.mads[i];
      const laps = Math.min(race.cp.nlaps, m.nlaps + (m.clear >= race.cp.nlaps * race.cp.nsp ? 0 : 1));
      return `<div class="${i === 0 ? 'me' : ''}${race.rivals?.includes(race.sc[i]) ? ' rv' : ''}"><b>${ordinal(race.cp.pos[i] + 1)}</b><span>${esc(cd.names[race.sc[i]])}${i === 0 ? ' <small>YOU</small>' : race.rivals?.includes(race.sc[i]) ? ' <small>RIVAL</small>' : ''}</span><em>${m.dest ? 'Wasted' : `${laps}/${race.cp.nlaps}`}</em></div>`;
    }).join('') : '';
    const lines = [];
    if (earned) {
      if (earned.firstWin && earned.nextStage) {
        const ch = this.app.career.chapters[earned.nextStage.chapter];
        const sname = this.app.assets.stages[ch.stages[earned.nextStage.stage].index].name;
        lines.push(`<div class="earn"><span>STAGE UNLOCKED</span><b>${esc(sname)}</b></div>`);
      }
      if (earned.chapterDone) lines.push(`<div class="earn"><span>CHAPTER COMPLETE</span><b>${esc(this.app.career.chapters[race.careerAt.chapter].title)}</b></div>`);
      for (const slot of earned.cars) lines.push(`<div class="earn car"><span>NEW CAR</span><b>${esc(cd.names[slot])}</b></div>`);
      if (earned.bestTime) lines.push(`<div class="earn"><span>NEW BEST TIME</span><b>${fmtTime(race.xt.laptime)}</b></div>`);
    }
    const win = r.kind === 'allwasted' || (r.kind === 'finished' && r.position === 1);
    return { html: `<div class="rtitle ${win ? 'win' : 'loss'}">${titles[r.kind]}</div><div class="rsub">${subs[r.kind]}</div>${lines.join('')}${rows ? `<div class="standings">${rows}</div>` : ''}`, win };
  }

  // New car: the studio, the car on the turntable, its name.
  reveal(slots, done) {
    const app = this.app;
    if (!slots.length) { done(); return; }
    const slot = slots[0];
    app.state = 'reveal';
    this.studio('reveal');
    app.showroom.show(app.world, slot, { dir: 1 });
    this.sfx('powerup', 0.7);
    const el = h(`<div class="screen reveal fade-in">
      <div class="rv-k">NEW CAR</div>
      <div class="rv-n">${esc(app.world.cd.names[slot])}</div>
      <div class="rv-s">Taken from its owner. It's in your garage now.</div>
      ${this.prompts([['Enter', 'Continue', 'ok']])}
    </div>`);
    const next = () => this.reveal(slots.slice(1), done);
    this.wirePrompts(el, { ok: next });
    this.mount(el, (code) => {
      if (code === 'Enter' || code === 'Space' || code === 'Escape') next();
      else return false;
    });
  }
}
