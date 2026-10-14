// Translates the decompiled Need for Madness game-logic classes (tools/java/*.java)
// into JavaScript modules in src/gen/, preserving Java numeric semantics:
// integer division truncates, (int) casts truncate, int[]/float[] arrays keep
// their element precision (Int32Array / Float32Array).
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { classes, loadClass, promote, isInt, NUMERIC, fieldName, LIB_FIELDS, STRING_METHODS } from './javaparse.mjs';

const ROOT = path.dirname(fileURLToPath(import.meta.url));
const OUT_DIR = path.join(ROOT, '..', 'src', 'gen');

// Which classes to translate, and which of their methods to keep (null = all).
const TARGETS = {
  Mad: null,
  Control: null,
  CheckPoints: null,
  Trackers: null,
  Wheels: null,
  Plane: ['<init>', 'deltafntyp', 'loadprojf', 'rot', 'spy'],
  ContO: ['<init>', 'getvalue', 'getpy', 'py', 'rot'],
  CarDefine: ['loadstat', 'getvalue', 'getSvalue'],
  Medium: ['<init>', 'setsnap', 'setsky', 'setcloads', 'setgrnd', 'setexture', 'setpolys', 'setfade', 'fadfrom', 'sin', 'cos', 'random', 'rot'],
};
// Classes only scanned for field / method types.
const TYPE_ONLY = ['Record', 'xtGraphics'];

/* ---------------------------------------------------------------- emitter */
class Emitter {
  constructor(cls) {
    this.cls = cls;
    this.scopes = [];
    this.warnings = [];
    this.uses = new Set();
  }
  push() { this.scopes.push(new Map()); }
  pop() { this.scopes.pop(); }
  declare(n, t) { this.scopes[this.scopes.length - 1].set(n, t); }
  lookupLocal(n) { for (let i = this.scopes.length - 1; i >= 0; i--) if (this.scopes[i].has(n)) return this.scopes[i].get(n); return null; }

  fieldType(clsName, f) {
    if (clsName && clsName.endsWith('[]') && f === 'length') return 'int';
    if (clsName === 'String' && f === 'length') return 'int';
    const c = classes[clsName];
    if (c && f in c.fieldTypes) return c.fieldTypes[f];
    if (f in LIB_FIELDS) return LIB_FIELDS[f];
    this.warnings.push(`unknown field ${clsName}.${f}`);
    return 'unknown';
  }

  // Returns [jsCode, javaType]
  ex(e) {
    switch (e.k) {
      case 'num': {
        const v = e.v.replace(/[fFdDlL]$/, '');
        return [/^0[xX]/.test(v) ? String(parseInt(v, 16)) : v, e.nt];
      }
      case 'str': return [e.v, 'String'];
      case 'chr': return [e.v, 'char'];
      case 'bool': return [e.v, 'boolean'];
      case 'null': return ['null', 'null'];
      case 'this': return ['this', this.cls.name];
      case 'paren': { const [c, t] = this.ex(e.e); return ['(' + c + ')', t]; }
      case 'name': {
        const lt = this.lookupLocal(e.v);
        if (lt) return [e.v, lt];
        if (e.v in this.cls.fieldTypes) return ['this.' + fieldName(this.cls.name, e.v), this.cls.fieldTypes[e.v]];
        if (/^[A-Z]/.test(e.v)) return [e.v, 'class:' + e.v];
        this.warnings.push('unknown name ' + e.v);
        return [e.v, 'unknown'];
      }
      case 'field': {
        const [oc, ot] = this.ex(e.obj);
        if (ot === 'class:Math' && e.name === 'PI') return ['Math.PI', 'double'];
        if (ot === 'class:System' && e.name === 'out') return ['console', 'class:Console'];
        if (ot.startsWith('class:')) return [`${oc}.${e.name}`, 'unknown'];
        return [`${oc}.${fieldName(ot, e.name)}`, this.fieldType(ot, e.name)];
      }
      case 'index': {
        const [oc, ot] = this.ex(e.obj);
        const [ic] = this.ex(e.idx);
        return [`${oc}[${ic}]`, ot.endsWith('[]') ? ot.slice(0, -2) : 'unknown'];
      }
      case 'call': return this.call(e);
      case 'new': return this.newObj(e);
      case 'newarr': return [this.newArr(e.ty, e.dims.map((d) => this.ex(d)[0]), e.extra), e.ty + '[]'.repeat(e.dims.length + e.extra)];
      case 'arrinit': return [this.arrInit(e), e.type];
      case 'cast': {
        const [c, t] = this.ex(e.e);
        const ty = e.ty;
        if (isInt(ty)) {
          if (isInt(t)) return [c, ty];
          if (ty === 'long') return [`Math.trunc(${c})`, ty];
          this.uses.add("jI"); return [`jI(${c})`, ty];
        }
        return [c, ty === 'float' || ty === 'double' ? ty : ty];
      }
      case 'unary': {
        const [c, t] = this.ex(e.e);
        if (e.op === '!') return ['!' + c, 'boolean'];
        return [e.op + (c.startsWith(e.op) ? ' ' : '') + c, t === 'char' ? 'int' : t];
      }
      case 'preinc': { const [c, t] = this.ex(e.e); return [e.op + c, t]; }
      case 'postinc': { const [c, t] = this.ex(e.e); return [c + e.op, t]; }
      case 'instanceof': { const [c] = this.ex(e.e); return [`(${c} instanceof ${e.ty})`, 'boolean']; }
      case 'cond': {
        const [cc] = this.ex(e.c);
        const [ac, at] = this.ex(e.a);
        const [bc, bt] = this.ex(e.b);
        let t = at;
        if (NUMERIC.has(at) && NUMERIC.has(bt)) t = promote(at, bt);
        else if (at === 'null') t = bt;
        return [`(${cc} ? ${ac} : ${bc})`, t];
      }
      case 'bin': {
        const [lc, lt] = this.ex(e.l);
        const [rc, rt] = this.ex(e.r);
        const op = e.op;
        if (['==', '!=', '<', '>', '<=', '>=', '&&', '||'].includes(op)) {
          const jsop = op === '==' ? '===' : op === '!=' ? '!==' : op;
          return [`${lc} ${jsop} ${rc}`, 'boolean'];
        }
        if (op === '&' || op === '|' || op === '^') {
          if (lt === 'boolean') return [`(${lc} ${op} ${rc}) !== 0`.replace(/^/, '') , 'boolean'];
          return [`(${lc} ${op} ${rc})`, promote(lt, rt)];
        }
        const t = promote(lt, rt);
        if (op === '/' && isInt(t)) return [`((${lc}) / (${rc}) | 0)`, t];
        if (op === '+' && t === 'String') return [`${lc} + ${rc}`, 'String'];
        return [`${lc} ${op} ${rc}`, t];
      }
      case 'assign': {
        const [lc, lt] = this.ex(e.l);
        const [rc, rt] = this.ex(e.r);
        // Plain float variables/fields round to 32-bit like Java; Float32Array elements already do.
        const fround = lt === 'float' && e.l.k !== 'index';
        if (e.op === '=') return [fround ? `${lc} = Math.fround(${rc})` : `${lc} = ${rc}`, lt];
        const bop = e.op.slice(0, -1);
        if (isInt(lt) && (!isInt(rt) || bop === '/')) {
          if (bop === '/' && isInt(rt)) return [`${lc} = ((${lc}) / (${rc}) | 0)`, lt];
          this.uses.add("jI"); return [`${lc} = jI(${lc} ${bop} (${rc}))`, lt];
        }
        if (fround) return [`${lc} = Math.fround(${lc} ${bop} (${rc}))`, lt];
        return [`${lc} ${e.op} ${rc}`, lt];
      }
      default: throw new Error('expr kind ' + e.k);
    }
  }

  call(e) {
    const args = e.args.map((a) => this.ex(a));
    const ac = args.map((a) => a[0]).join(', ');
    if (!e.obj) {
      const rt = this.cls.methodTypes[e.name] ?? 'unknown';
      return [`this.${e.name}(${ac})`, rt];
    }
    const [oc, ot] = this.ex(e.obj);
    if (ot === 'class:Math') {
      const t0 = args[0]?.[1];
      switch (e.name) {
        case 'abs': return [`Math.abs(${ac})`, t0];
        case 'max': case 'min': return [`Math.${e.name}(${ac})`, promote(t0, args[1][1])];
        case 'round': return [`Math.floor(${ac} + 0.5)`, t0 === 'float' ? 'int' : 'long'];
        default: return [`Math.${e.name}(${ac})`, 'double'];
      }
    }
    if (ot === 'class:Integer' && (e.name === 'valueOf' || e.name === 'parseInt')) { this.uses.add('jParseInt'); return [`jParseInt(${ac})`, 'int']; }
    if (ot === 'class:Float' && (e.name === 'valueOf' || e.name === 'parseFloat')) { this.uses.add('jParseFloat'); return [`jParseFloat(${ac})`, 'float']; }
    if (ot === 'class:Color') { this.uses.add('Color'); return [`Color.${e.name}(${ac})`, e.name === 'getHSBColor' ? 'Color' : 'unknown']; }
    if (ot === 'class:Console') return [`console.log(${ac})`, 'void'];
    if (ot === 'class:System' || ot === 'class:Thread') return [`void 0`, 'void'];
    if (e.name === 'intValue') return [isInt(ot) ? oc : (this.uses.add("jI"), `jI(${oc})`), 'int'];
    if (e.name === 'floatValue') return [oc, 'float'];
    if (ot === 'String') {
      if (e.name === 'equals') return [`(${oc} === ${ac})`, 'boolean'];
      if (e.name === 'length') return [`${oc}.length`, 'int'];
      if (e.name === 'replace') return [`${oc}.split(${args[0][0]}).join(${args[1][0]})`, 'String'];
      return [`${oc}.${e.name}(${ac})`, STRING_METHODS[e.name] ?? 'unknown'];
    }
    if (ot === 'Color') return [`${oc}.${e.name}(${ac})`, 'int'];
    if (ot === 'Random') return [`${oc}.${e.name}(${ac})`, e.name === 'nextDouble' ? 'double' : e.name === 'nextFloat' ? 'float' : 'int'];
    if (ot === 'DataInputStream') return [`${oc}.${e.name}(${ac})`, e.name === 'readLine' ? 'String' : 'void'];
    const c = classes[ot];
    let rt = 'unknown';
    if (c && e.name in c.methodTypes) rt = c.methodTypes[e.name];
    else this.warnings.push(`unknown method ${ot}.${e.name}`);
    return [`${oc}.${e.name}(${ac})`, rt];
  }

  newObj(e) {
    const args = e.args.map((a) => this.ex(a));
    const ac = args.map((a) => a[0]).join(', ');
    const ty = e.ty;
    if (ty === 'Random') { this.uses.add('JRandom'); return [`new JRandom(${ac})`, 'Random']; }
    if (ty === 'Color') { this.uses.add('Color'); return [`new Color(${ac})`, 'Color']; }
    if (ty === 'DataInputStream') { this.uses.add('DataInputStream'); return [`new DataInputStream(${ac})`, 'DataInputStream']; }
    if (ty === 'ByteArrayInputStream') return [args[0][0], 'bytes'];
    if (classes[ty]) {
      this.uses.add('class:' + ty);
      const ctors = classes[ty].methods.filter((m) => m.name === '<init>');
      if (ctors.length > 1) {
        const idx = ctors.findIndex((m) => m.params.length === args.length);
        if (idx < 0) throw new Error('no ctor match ' + ty);
        return [`${ty}.create${idx}(${ac})`, ty];
      }
      return [`new ${ty}(${ac})`, ty];
    }
    return [`new ${ty}(${ac})`, ty];
  }

  newArr(ty, dims, extra) {
    this.uses.add('jArray');
    return `jArray('${ty}', [${dims.join(', ')}], ${extra})`;
  }

  arrInit(e) {
    const et = e.type.slice(0, -2);
    const els = e.els.map((x) => (x.k === 'arrinit' ? this.arrInit(x) : this.ex(x)[0]));
    if (!et.endsWith('[]')) {
      if (isInt(et)) return `Int32Array.of(${els.join(', ')})`;
      if (et === 'float') return `Float32Array.of(${els.join(', ')})`;
      if (et === 'double') return `Float64Array.of(${els.join(', ')})`;
    }
    return `[${els.join(', ')}]`;
  }

  defaultFor(t) {
    if (NUMERIC.has(t)) return '0';
    if (t === 'boolean') return 'false';
    return 'null';
  }

  /* statements */
  st(s, ind) {
    const I = '  '.repeat(ind);
    switch (s.k) {
      case 'block': {
        this.push();
        const out = s.body.map((x) => this.st(x, ind + 1)).join('');
        this.pop();
        return `${I}{\n${out}${I}}\n`;
      }
      case 'empty': return '';
      case 'local': return s.vars.map((v) => {
        this.declare(v.name, v.type);
        const init = v.init ? (v.init.k === 'arrinit' ? this.arrInit(v.init) : this.coerce(v.init, v.type)) : this.defaultFor(v.type);
        return `${I}let ${v.name} = ${init};\n`;
      }).join('');
      case 'expr': return `${I}${this.ex(s.e)[0]};\n`;
      case 'if': {
        let out = `${I}if (${this.ex(s.c)[0]}) ${this.sub(s.th, ind)}`;
        if (s.el) out = out.replace(/\n$/, '') + ` else ${this.sub(s.el, ind)}`;
        return out;
      }
      case 'while': return `${I}while (${this.ex(s.c)[0]}) ${this.sub(s.body, ind)}`;
      case 'do': return `${I}do ${this.sub(s.body, ind).replace(/\n$/, '')} while (${this.ex(s.c)[0]});\n`;
      case 'for': {
        this.push();
        let init = '';
        if (s.init?.k === 'local') {
          init = 'let ' + s.init.vars.map((v) => { this.declare(v.name, v.type); return `${v.name} = ${v.init ? this.coerce(v.init, v.type) : this.defaultFor(v.type)}`; }).join(', ');
        } else if (s.init) init = s.init.es.map((x) => this.ex(x)[0]).join(', ');
        const c = s.c ? this.ex(s.c)[0] : '';
        const u = s.upd.map((x) => this.ex(x)[0]).join(', ');
        const out = `${I}for (${init}; ${c}; ${u}) ${this.sub(s.body, ind)}`;
        this.pop();
        return out;
      }
      case 'foreach': {
        this.push();
        this.declare(s.v.name, s.v.type);
        const out = `${I}for (const ${s.v.name} of ${this.ex(s.it)[0]}) ${this.sub(s.body, ind)}`;
        this.pop();
        return out;
      }
      case 'return': return `${I}return${s.e ? ' ' + this.ex(s.e)[0] : ''};\n`;
      case 'break': case 'continue': return `${I}${s.k}${s.label ? ' ' + s.label : ''};\n`;
      case 'throw': return `${I}throw ${this.ex(s.e)[0]};\n`;
      case 'label': return `${I}${s.name}: ${this.sub(s.body, ind)}`;
      case 'try': {
        let out = `${I}try ${this.sub(s.body, ind)}`;
        if (s.catches.length) {
          this.push();
          const c = s.catches[0];
          this.declare(c.nm, c.ty);
          out = out.replace(/\n$/, '') + ` catch (${c.nm}) ${this.sub(c.body, ind)}`;
          this.pop();
        }
        if (s.fin) out = out.replace(/\n$/, '') + ` finally ${this.sub(s.fin, ind)}`;
        return out;
      }
      default: throw new Error('stmt ' + s.k);
    }
  }
  sub(s, ind) {
    if (s.k === 'block') return this.st(s, ind).replace(/^\s*/, '');
    return '{\n' + this.st(s, ind + 1) + '  '.repeat(ind) + '}\n';
  }
  coerce(expr, type) {
    const [c, t] = this.ex(expr);
    if (isInt(type) && !isInt(t) && NUMERIC.has(t)) { this.uses.add("jI"); return `jI(${c})`; }
    if (type === 'float' && t !== 'int') return `Math.fround(${c})`;
    return c;
  }

  method(m) {
    this.push();
    for (const p of m.params) this.declare(p.name, p.type);
    const body = this.st(m.body, 1).replace(/^\s*/, '');
    this.pop();
    const params = m.params.map((p) => p.name).join(', ');
    return { params, body };
  }
}

/* ------------------------------------------------------------------ main */
for (const n of TYPE_ONLY) loadClass(n, false);
for (const n of Object.keys(TARGETS)) loadClass(n, true);

const RUNTIME_IMPORTS = ['jI', 'Color', 'JRandom', 'DataInputStream', 'jArray', 'jParseInt', 'jParseFloat'];
let totalWarn = 0;
fs.mkdirSync(OUT_DIR, { recursive: true });
for (const [name, keep] of Object.entries(TARGETS)) {
  const cls = classes[name];
  const em = new Emitter(cls);
  const lines = [];
  // field initialisers
  em.push();
  const fieldInit = cls.fields.map((f) => {
    const init = f.init ? (f.init.k === 'arrinit' ? em.arrInit(f.init) : em.coerce(f.init, f.type)) : em.defaultFor(f.type);
    return `    this.${fieldName(name, f.name)} = ${init};\n`;
  }).join('');
  em.pop();
  const ctors = cls.methods.filter((m) => m.name === '<init>' && m.body);
  const methods = cls.methods.filter((m) => m.name !== '<init>' && m.body && (!keep || keep.includes(m.name)));
  lines.push(`export class ${name} {`);
  if (ctors.length <= 1) {
    const c = ctors[0];
    const { params, body } = c ? em.method(c) : { params: '', body: '{\n}\n' };
    lines.push(`  constructor(${params}) {\n${fieldInit}    ${body.replace(/^\{\n/, '').replace(/\}\n$/, '').trimStart()}  }`);
  } else {
    lines.push(`  constructor() {\n${fieldInit}  }`);
    ctors.forEach((c, i) => {
      const { params, body } = em.method(c);
      lines.push(`  _init${i}(${params}) ${body.trimEnd()}`);
      lines.push(`  static create${i}(...a) { const o = new ${name}(); o._init${i}(...a); return o; }`);
    });
  }
  for (const m of methods) {
    const { params, body } = em.method(m);
    lines.push(`  ${m.name}(${params}) ${body.trimEnd()}`);
  }
  lines.push('}');
  const imports = [];
  const rt = RUNTIME_IMPORTS.filter((r) => em.uses.has(r));
  if (rt.length) imports.push(`import { ${rt.join(', ')} } from '../runtime/java.js';`);
  for (const u of em.uses) if (u.startsWith('class:') && u.slice(6) !== name) imports.push(`import { ${u.slice(6)} } from './${u.slice(6)}.js';`);
  const out = `// AUTO-GENERATED by tools/j2js.mjs from tools/java/${name}.java. Do not edit.\n/* eslint-disable */\n${imports.join('\n')}\n\n${lines.join('\n\n')}\n`;
  fs.writeFileSync(path.join(OUT_DIR, name + '.js'), out);
  const uniq = [...new Set(em.warnings)];
  totalWarn += uniq.length;
  console.log(`${name}: ${methods.length} methods${uniq.length ? ', warnings:\n   ' + uniq.join('\n   ') : ''}`);
}
console.log(totalWarn ? `done with ${totalWarn} warnings` : 'done');
