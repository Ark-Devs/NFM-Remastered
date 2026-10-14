const ORD = (n) => n + (n % 10 === 1 && n % 100 !== 11 ? 'st' : n % 10 === 2 && n % 100 !== 12 ? 'nd' : n % 10 === 3 && n % 100 !== 13 ? 'rd' : 'th');
export const ordinal = ORD;

export function fmtTime(ticks) {
  const ms = ticks * 40;
  const m = Math.floor(ms / 60000);
  const s = Math.floor((ms % 60000) / 1000);
  const cs = Math.floor((ms % 1000) / 10);
  return `${m}:${String(s).padStart(2, '0')}.${String(cs).padStart(2, '0')}`;
}

const CAR_COLORS = ['#ffc23d', '#ff4d4d', '#4fb6ff', '#4ade80', '#c084fc', '#f472b6', '#fb923c', '#e5e7eb'];

export class Hud {
  constructor(root) {
    this.el = document.createElement('div');
    this.el.className = 'hud';
    this.el.innerHTML = `
      <div class="tl"><div class="pos"></div><div class="lap"></div></div>
      <div class="tr"><div class="t"></div><div class="best"></div></div>
      <svg class="arrow" viewBox="-10 -10 20 20"><path d="M0,-8 L6,6 L0,3 L-6,6 Z" fill="#ffc23d" stroke="#000" stroke-width="0.8"/></svg>
      <div class="bl">
        <div class="meter power"><div class="lbl"><span>POWER</span><span class="pv"></span></div><div class="track"><div class="fill"></div></div></div>
        <div class="meter dmg"><div class="lbl"><span>DAMAGE</span><span class="dv"></span></div><div class="track"><div class="fill"></div></div></div>
      </div>
      <div class="br"><div class="speed"><div class="v">0</div><div class="u">KM/H</div></div><canvas class="minimap" width="380" height="380"></canvas></div>
      <div class="msg-center"></div>
      <div class="toast" style="display:none"></div>
      <div class="camtag"></div>`;
    root.appendChild(this.el);
    const q = (s) => this.el.querySelector(s);
    this.$ = { pos: q('.pos'), lap: q('.lap'), t: q('.tr .t'), best: q('.best'), arrow: q('.arrow'), pv: q('.pv'), dv: q('.dv'), power: q('.power'), pfill: q('.power .fill'), dfill: q('.dmg .fill'), speed: q('.speed .v'), map: q('.minimap'), msg: q('.msg-center'), toast: q('.toast'), cam: q('.camtag') };
    this.mapCtx = this.$.map.getContext('2d');
    this.toastT = 0;
    this.msgT = 0;
    this.camT = 0;
  }

  setTrack(def) {
    // collect road-ish piece positions for the radar
    this.trackPts = def.objects.filter((o) => !o.pile && !o.wall && o.slot >= 56 && o.slot < 56 + 54 && o.slot !== 87 && o.slot !== 86).map((o) => [o.conto.x, o.conto.z]);
    const b = def.bounds;
    this.bounds = b;
  }

  message(text, sub = '', secs = 1.2) {
    this.$.msg.innerHTML = `${text}${sub ? `<span class="s">${sub}</span>` : ''}`;
    this.$.msg.classList.remove('pop');
    void this.$.msg.offsetWidth;
    this.$.msg.classList.add('pop');
    this.msgT = secs;
  }

  toast(text, secs = 1.6) {
    this.$.toast.textContent = text;
    this.$.toast.style.display = '';
    this.toastT = secs;
  }

  camera(name) { this.$.cam.textContent = `Camera: ${name}`; this.camT = 1.5; }

  update(dt, race, camera, world) {
    const p = race.player;
    const cp = race.cp;
    const n = race.nplayers;
    const place = Math.min(n, cp.pos[0] + 1);
    this.$.pos.innerHTML = `${place}<sup>${ORD(place).slice(-2)}</sup> <small>/ ${n}</small>`;
    const lap = Math.min(cp.nlaps, p.nlaps + 1);
    const cpi = (p.clear % cp.nsp) || 0;
    this.$.lap.innerHTML = `LAP ${lap}<span>/${cp.nlaps}</span> &nbsp; CP ${cpi}<span>/${cp.nsp}</span>`;
    this.$.t.textContent = fmtTime(race.xt.laptime);
    this.$.best.textContent = race.cp.wasted > 0 ? `Wasted: ${race.cp.wasted}` : '';
    const power = Math.max(0, Math.min(98, p.power)) / 98;
    this.$.pfill.style.width = `${power * 100}%`;
    this.$.pv.textContent = `${Math.round(power * 100)}%`;
    this.$.power.classList.toggle('full', p.power >= 98);
    const dmg = Math.min(1, p.hitmag / world.cd.maxmag[p.cn]);
    this.$.dfill.style.width = `${dmg * 100}%`;
    this.$.dfill.style.backgroundPosition = `${-dmg * 200}px 0`;
    this.$.dv.textContent = p.dest ? 'WASTED' : `${Math.round(dmg * 100)}%`;
    this.$.speed.textContent = Math.round(Math.abs(p.speed) * 1.15);

    // arrow toward the point the original guide arrow used (Mad.point)
    const car = race.cars[0];
    const tx = cp.x[p.point] ?? 0, tz = cp.z[p.point] ?? 0;
    const camYaw = Math.atan2(-camera.matrixWorld.elements[8], -camera.matrixWorld.elements[10]);
    const dirThree = Math.atan2(tx - car.x, -(tz - car.z));
    const rel = camYaw - dirThree;
    this.$.arrow.style.transform = `rotate(${rel}rad)`;
    this.$.arrow.style.display = race.finished ? 'none' : '';

    this.drawMap(race);

    if (this.msgT > 0 && (this.msgT -= dt) <= 0) this.$.msg.innerHTML = '';
    if (this.toastT > 0 && (this.toastT -= dt) <= 0) this.$.toast.style.display = 'none';
    if (this.camT > 0 && (this.camT -= dt) <= 0) this.$.cam.textContent = '';
  }

  drawMap(race) {
    const g = this.mapCtx;
    const W = 380;
    g.clearRect(0, 0, W, W);
    const car = race.cars[0];
    const heading = (race.mads[0].cxz * Math.PI) / 180;
    const scale = W / 36000;
    g.save();
    g.beginPath();
    g.arc(W / 2, W / 2, W / 2 - 2, 0, Math.PI * 2);
    g.clip();
    g.translate(W / 2, W / 2);
    g.rotate(heading);
    const plot = (x, z) => [(x - car.x) * scale, -(z - car.z) * scale];
    g.fillStyle = 'rgba(255,255,255,0.28)';
    for (const [x, z] of this.trackPts || []) {
      const [px, py] = plot(x, z);
      if (Math.abs(px) > W || Math.abs(py) > W) continue;
      g.fillRect(px - 5, py - 5, 10, 10);
    }
    const cp = race.cp;
    for (let i = 0; i < cp.n; i++) {
      if (cp.typ[i] <= 0) continue;
      const [px, py] = plot(cp.x[i], cp.z[i]);
      g.fillStyle = 'rgba(255,194,61,0.8)';
      g.fillRect(px - 4, py - 4, 8, 8);
    }
    for (let i = race.nplayers - 1; i >= 0; i--) {
      const c = race.cars[i];
      const [px, py] = plot(c.x, c.z);
      g.fillStyle = race.mads[i].dest ? '#444' : CAR_COLORS[i];
      g.beginPath();
      g.arc(px, py, i === 0 ? 9 : 7, 0, Math.PI * 2);
      g.fill();
      g.strokeStyle = '#000';
      g.lineWidth = 2;
      g.stroke();
    }
    g.restore();
  }

  destroy() { this.el.remove(); }
}
