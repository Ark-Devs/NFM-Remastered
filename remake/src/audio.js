// Sound, using the original WAV samples from data/sounds.zip. Trigger rules and
// cooldowns follow xtGraphics (crash, skid, scrape, gscrape, playsounds).
export class Audio {
  constructor(soundFiles) {
    this.files = soundFiles;
    this.ctx = null;
    this.buffers = {};
    this.muted = false;
    this.volume = 0.8;
    this.musicVolume = 0.5;
  }

  async init() {
    if (this.ctx) return;
    const AC = window.AudioContext || window.webkitAudioContext;
    this.ctx = new AC();
    this.master = this.ctx.createGain();
    this.master.gain.value = this.volume;
    this.master.connect(this.ctx.destination);
    this.sfx = this.ctx.createGain();
    this.sfx.connect(this.master);
    this.musicGain = this.ctx.createGain();
    this.musicGain.gain.value = this.musicVolume;
    this.musicGain.connect(this.master);
    await Promise.all(Object.entries(this.files).map(async ([name, bytes]) => {
      try {
        const copy = bytes.buffer.slice(bytes.byteOffset, bytes.byteOffset + bytes.byteLength);
        this.buffers[name.replace(/\.wav$/i, '')] = await this.ctx.decodeAudioData(copy);
      } catch (e) {
        console.warn('sound', name, e);
      }
    }));
  }

  resume() { this.ctx?.resume(); }

  setMuted(m) {
    this.muted = m;
    if (this.master) this.master.gain.value = m ? 0 : this.volume;
  }

  play(name, gain = 1, rate = 1) {
    if (!this.ctx || !this.buffers[name]) return null;
    const src = this.ctx.createBufferSource();
    src.buffer = this.buffers[name];
    src.playbackRate.value = rate;
    const g = this.ctx.createGain();
    g.gain.value = gain;
    src.connect(g).connect(this.sfx);
    src.start();
    return src;
  }

  loop(name, gain = 0) {
    if (!this.ctx || !this.buffers[name]) return null;
    const src = this.ctx.createBufferSource();
    src.buffer = this.buffers[name];
    src.loop = true;
    const g = this.ctx.createGain();
    g.gain.value = gain;
    src.connect(g).connect(this.sfx);
    src.start();
    return { src, g };
  }

  // ----- race session -----
  startRace(signature) {
    this.stopRace();
    this.engine = [];
    for (let i = 0; i < 5; i++) this.engine.push(this.loop(`${signature}${i}`, 0));
    this.air = [0, 1, 2, 3].map((i) => this.loop('air' + i, 0));
    this.wastedLoop = this.loop('wasted', 0);
    this.state = { bfcrash: 0, bfskid: 0, bfscrape: 0, bfsc1: 0, bfsc2: 0, crshturn: 0, skflg: 0, dskflg: 0, pwait: 7, level: 0, lastLevel: 0, airTarget: -1 };
  }

  stopRace() {
    for (const l of [...(this.engine || []), ...(this.air || []), this.wastedLoop]) {
      if (!l) continue;
      try { l.src.stop(); } catch { /* already stopped */ }
    }
    this.engine = null;
    this.air = null;
    this.wastedLoop = null;
  }

  crash(f, n) {
    const s = this.state;
    if (!s || s.bfcrash !== 0) return;
    const a = Math.abs(f);
    if (n === 0 || n === -1) {
      const turn = n === -1 ? 2 : s.crshturn;
      if (a > 25 && a < 170) { this.play('lowcrash' + (turn + 1), 0.9); s.bfcrash = 2; }
      if (a >= 170) { this.play('crash' + (turn + 1), 1); s.bfcrash = 2; }
      if (n === 0 && a > 25) s.crshturn = (s.crshturn + 1) % 3;
    }
    if (n === 1) { this.play('tires', 0.9); s.bfcrash = 3; }
  }

  skid(type, mag) {
    const s = this.state;
    if (!s || s.bfcrash !== 0 || s.bfskid !== 0 || mag <= 150) return;
    if (type === 0) { this.play('skid' + (s.skflg + 1), 0.55); s.skflg = (s.skflg + 1) % 3; } else { this.play('dustskid' + (s.dskflg + 1), 0.6); s.dskflg = (s.dskflg + 1) % 3; }
    s.bfskid = 35;
  }

  scrape(x, y, z) {
    const s = this.state;
    if (!s || s.bfscrape !== 0 || Math.sqrt(x * x + y * y + z * z) / 10 <= 10) return;
    this.play('scrape' + (Math.random() > 0.5 ? 1 : 2), 0.7);
    s.bfscrape = 5;
  }

  gscrape(x, y, z) {
    const s = this.state;
    if (!s || !(s.bfsc1 === 0 || s.bfsc2 === 0) || Math.sqrt(x * x + y * y + z * z) / 10 <= 15) return;
    this.play('scrape3', 0.6);
    if (s.bfsc1 === 0) { s.bfsc1 = 12; s.bfsc2 = 6; } else { s.bfsc2 = 12; s.bfsc1 = 6; }
  }

  // Called once per physics tick: engine level selection from xtGraphics.playsounds.
  tick(mad, control, cd, racing) {
    const s = this.state;
    if (!s || !this.engine) return;
    for (const k of ['bfcrash', 'bfskid', 'bfscrape', 'bfsc1', 'bfsc2']) if (s[k] > 0) s[k]--;
    const sw = cd.swits[mad.cn];
    const speed = Math.abs(mad.speed);
    let level = 0; // 0 = idle loop
    let frac = 0;
    let air = false;
    if (racing && !mad.dest) {
      const throttle = (control.up && mad.speed > 0) || (control.down && mad.speed < 10);
      if (throttle && mad.mtouch) {
        if (mad.capsized) level = 4;
        else if (speed <= sw[0]) { frac = speed / sw[0]; level = 1 + Math.min(2, Math.trunc(3 * frac)); }
        else if (speed <= sw[1]) { frac = (speed - sw[0]) / (sw[1] - sw[0]); level = 1 + Math.min(2, Math.trunc(3 * frac)); }
        else { frac = (speed - sw[1]) / (sw[2] - sw[1]); level = 1 + Math.min(3, Math.trunc(3 * frac)); }
        if (mad.power >= 98 && speed > sw[1]) level = 4;
      } else if (!mad.mtouch && !mad.wtouch) air = true;
    }
    const t = this.ctx.currentTime;
    const rate = 0.92 + 0.18 * (frac * 3 - Math.trunc(frac * 3));
    this.engine.forEach((l, i) => {
      if (!l) return;
      l.g.gain.setTargetAtTime(i === level ? 0.55 : 0, t, 0.05);
      l.src.playbackRate.setTargetAtTime(i === 0 ? 1 : rate, t, 0.08);
    });
    if (air && s.airTarget < 0) s.airTarget = Math.floor(Math.random() * 4);
    if (!air) s.airTarget = -1;
    this.air.forEach((l, i) => l && l.g.gain.setTargetAtTime(i === s.airTarget ? 0.4 : 0, t, 0.1));
    if (this.wastedLoop) this.wastedLoop.g.gain.setTargetAtTime(mad.cntdest > 0 && mad.cntdest < 7 ? 0.5 : 0, t, 0.05);
  }

  // ----- music (ProTracker modules) -----
  async playMusic(modBytes, ModPlayer) {
    this.stopMusic();
    if (!this.ctx || !modBytes) return;
    try {
      this.music = await ModPlayer.create(this.ctx, modBytes);
      this.music.connect(this.musicGain);
      this.music.start();
    } catch (e) {
      console.warn('music', e);
      this.music = null;
    }
  }

  stopMusic() {
    this.music?.stop();
    this.music = null;
  }

  setMusicVolume(v) {
    this.musicVolume = v;
    if (this.musicGain) this.musicGain.gain.value = v;
  }
}
