import { defineConfig } from 'vite';
import fs from 'node:fs';
import path from 'node:path';

// The remake reads the original game's assets (models, stages, cars, sounds, music)
// straight from the game folder one level up, served under /nfm/.
const GAME_DIR = path.resolve(__dirname, '..');
const ASSET_DIRS = ['data/models.zip', 'data/sounds.zip', 'stages', 'mystages', 'mycars', 'music'];

function listDir(rel, ext) {
  const dir = path.join(GAME_DIR, rel);
  if (!fs.existsSync(dir)) return [];
  return fs.readdirSync(dir).filter((f) => f.toLowerCase().endsWith(ext));
}

function manifest() {
  return {
    stages: listDir('stages', '.txt'),
    mystages: listDir('mystages', '.txt'),
    mycars: listDir('mycars', '.rad'),
    music: listDir('music', '.zip'),
  };
}

function nfmAssets() {
  return {
    name: 'nfm-assets',
    configureServer(server) {
      // Dev-only: save a canvas snapshot (POSTed as a data URL) for inspection.
      server.middlewares.use('/__shot', (req, res) => {
        let body = '';
        req.on('data', (d) => { body += d; });
        req.on('end', () => {
          const b64 = body.replace(/^data:image\/png;base64,/, '');
          fs.mkdirSync(path.join(__dirname, '.shots'), { recursive: true });
          fs.writeFileSync(path.join(__dirname, '.shots', 'shot.png'), Buffer.from(b64, 'base64'));
          res.end('ok');
        });
      });
      server.middlewares.use('/nfm/', (req, res, next) => {
        const rel = decodeURIComponent(req.url.split('?')[0]).replace(/^\/+/, '');
        if (rel === 'manifest.json') {
          res.setHeader('Content-Type', 'application/json');
          res.end(JSON.stringify(manifest()));
          return;
        }
        const file = path.join(GAME_DIR, rel);
        if (!file.startsWith(GAME_DIR) || !ASSET_DIRS.some((d) => rel.startsWith(d)) || !fs.existsSync(file)) return next();
        res.setHeader('Cache-Control', 'no-cache');
        fs.createReadStream(file).pipe(res);
      });
    },
    generateBundle() {
      // Production build: copy the assets next to the bundle.
      const emit = (rel) => this.emitFile({ type: 'asset', fileName: 'nfm/' + rel, source: fs.readFileSync(path.join(GAME_DIR, rel)) });
      emit('data/models.zip');
      emit('data/sounds.zip');
      const m = manifest();
      for (const f of m.stages) emit('stages/' + f);
      for (const f of m.mystages) emit('mystages/' + f);
      for (const f of m.mycars) emit('mycars/' + f);
      for (const f of m.music) emit('music/' + f);
      this.emitFile({ type: 'asset', fileName: 'nfm/manifest.json', source: JSON.stringify(m) });
    },
  };
}

export default defineConfig({
  plugins: [nfmAssets()],
  server: { port: 5173 },
  build: { target: 'es2022', chunkSizeWarningLimit: 2000 },
});
