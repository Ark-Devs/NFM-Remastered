// Keyboard + gamepad input mapped onto the original Control flags.
const KEYS = {
  left: ['ArrowLeft', 'KeyA'],
  right: ['ArrowRight', 'KeyD'],
  up: ['ArrowUp', 'KeyW'],
  down: ['ArrowDown', 'KeyS'],
  handb: ['Space'],
  lookback: ['KeyZ', 'KeyX'],
};

export class Input {
  constructor() {
    this.down = new Set();
    this.pressed = new Set();
    window.addEventListener('keydown', (e) => {
      if (e.repeat) return;
      this.down.add(e.code);
      this.pressed.add(e.code);
      if (['ArrowUp', 'ArrowDown', 'ArrowLeft', 'ArrowRight', 'Space'].includes(e.code) && !(e.target instanceof HTMLInputElement)) e.preventDefault();
    });
    window.addEventListener('keyup', (e) => this.down.delete(e.code));
    window.addEventListener('blur', () => this.down.clear());
    this.padPrev = {};
  }

  // Edge-triggered: true once per key press.
  take(code) {
    const had = this.pressed.has(code);
    this.pressed.delete(code);
    return had;
  }

  clearPressed() { this.pressed.clear(); }

  pad() {
    const pads = navigator.getGamepads ? navigator.getGamepads() : [];
    for (const p of pads) if (p && p.mapping === 'standard') return p;
    return null;
  }

  state() {
    const k = (name) => KEYS[name].some((c) => this.down.has(c));
    const s = { left: k('left'), right: k('right'), up: k('up'), down: k('down'), handb: k('handb'), lookback: k('lookback') ? 1 : 0 };
    const p = this.pad();
    if (p) {
      const ax = p.axes[0] || 0;
      if (ax < -0.35 || p.buttons[14]?.pressed) s.left = true;
      if (ax > 0.35 || p.buttons[15]?.pressed) s.right = true;
      if (p.buttons[7]?.value > 0.2 || p.buttons[0]?.pressed || p.buttons[12]?.pressed) s.up = true;
      if (p.buttons[6]?.value > 0.2 || p.buttons[13]?.pressed) s.down = true;
      if (p.buttons[1]?.pressed || p.buttons[2]?.pressed) s.handb = true;
      if (p.buttons[4]?.pressed) s.lookback = 1;
      const edge = (i, code) => { const now = !!p.buttons[i]?.pressed; if (now && !this.padPrev[i]) this.pressed.add(code); this.padPrev[i] = now; };
      edge(3, 'KeyC');
      edge(9, 'Escape');
    }
    return s;
  }
}
