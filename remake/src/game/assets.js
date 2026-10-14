import { unzipSync } from 'fflate';

const BASE = (import.meta.env.BASE_URL || '/') + 'nfm/';
const latin1 = new TextDecoder('latin1');

// Puzzle and marathon stages the original AI can't get round, even with the
// stuck-rescue; these run as solo time challenges.
const SOLO_STAGES = new Set(['Bamboo Cascades', 'Mind Breaker', 'MASSIVE']);

async function fetchBytes(rel) {
  const res = await fetch(BASE + rel.split('/').map(encodeURIComponent).join('/'));
  if (!res.ok) throw new Error(`Could not load ${rel} (${res.status})`);
  return new Uint8Array(await res.arrayBuffer());
}

async function fetchText(rel) {
  return latin1.decode(await fetchBytes(rel));
}

export class Assets {
  async load(onProgress = () => {}) {
    onProgress('Reading game files');
    this.manifest = JSON.parse(await fetchText('manifest.json'));

    onProgress('Unpacking 3D models');
    const models = unzipSync(await fetchBytes('data/models.zip'));
    this.models = {};
    for (const [name, bytes] of Object.entries(models)) this.models[name] = latin1.decode(bytes);

    onProgress('Unpacking sounds');
    this.sounds = unzipSync(await fetchBytes('data/sounds.zip'));

    onProgress('Loading custom cars');
    this.customCars = [];
    // Only cars whose file name ends in " C" are used in the remake.
    for (const file of this.manifest.mycars.filter((f) => / C\.rad$/i.test(f))) {
      try {
        const text = await fetchText('mycars/' + file);
        const named = /^\s*name\(([^)]*)\)/m.exec(text);
        let name = named ? named[1].trim() : file.replace(/ C\.rad$/i, '');
        // names saved as UTF-8 (e.g. "Ragnarök") arrive here latin1-decoded
        try { name = decodeURIComponent(escape(name)); } catch { /* already plain */ }
        this.customCars.push({ file, name, text });
      } catch (e) {
        console.warn(e);
      }
    }

    onProgress('Reading stage list');
    this.stages = [];
    const nums = this.manifest.stages.map((f) => parseInt(f, 10)).filter((n) => !Number.isNaN(n)).sort((a, b) => a - b);
    for (const n of nums) {
      const text = await fetchText(`stages/${n}.txt`);
      this.stages.push({ id: n, file: `stages/${n}.txt`, name: stageName(text, `Stage ${n}`), text });
    }
    for (const f of this.manifest.mystages) {
      const text = await fetchText('mystages/' + f);
      const name = stageName(text, f.replace(/.txt$/i, ''));
      this.stages.push({ id: -1, file: 'mystages/' + f, name, custom: true, solo: SOLO_STAGES.has(name), text });
    }
  }

  async music(name) {
    const file = this.manifest.music.find((f) => f.toLowerCase() === name.toLowerCase() + '.zip');
    if (!file) return null;
    const files = unzipSync(await fetchBytes('music/' + file));
    const first = Object.values(files)[0];
    return first || null;
  }

  // Custom stages name a module in mystages/mymusic/ (stored as name.mod.zip).
  async customMusic(name) {
    try {
      const files = unzipSync(await fetchBytes('mystages/mymusic/' + name + '.zip'));
      return Object.values(files)[0] || null;
    } catch {
      return null;
    }
  }
}

function stageName(text, fallback) {
  const m = /^\s*name\((.*)\)\s*$/m.exec(text);
  if (!m) return fallback;
  let name = m[1].replace(/\|/g, ',');
  // names saved as UTF-8 (e.g. "Ridin' High") arrive here latin1-decoded
  try { name = decodeURIComponent(escape(name)); } catch { /* already plain */ }
  return name;
}
