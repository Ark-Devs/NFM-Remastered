// Translates the decompiled Need for Madness game-logic classes (tools/java/*.java)
// into C++ for the native build (native/ext/src/gen/nfm_gen.{h,cpp}), keeping
// Java's numeric semantics: 32-bit wrapping ints (-fwrapv), real float maths,
// saturating float->int casts, truncating division, reference arrays.
// Math.random() becomes the seeded jrandom() so networked races stay in sync.
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { classes, loadClass, promote, isInt, NUMERIC } from './javaparse.mjs';

const ROOT = path.dirname(fileURLToPath(import.meta.url));
const OUT_DIR = path.join(ROOT, '..', '..', 'native', 'ext', 'src', 'gen');

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
// Hand-written in native/ext/src (bridges.h / sim.cpp) instead of stubbed.
const MANUAL = { ContO: ['dust', 'sprk'] };
// Hand-written stand-ins (bridges.h).
const TYPE_ONLY = ['Record', 'xtGraphics'];

const CPP_KEYWORDS = new Set(['and', 'or', 'not', 'xor', 'auto', 'register', 'union', 'signed', 'unsigned', 'template', 'typename', 'operator', 'friend', 'inline', 'extern', 'struct', 'delete', 'export', 'mutable', 'sizeof', 'typedef', 'namespace', 'using', 'virtual', 'explicit', 'bool', 'asm', 'compl', 'bitand', 'bitor', 'not_eq', 'and_eq', 'or_eq', 'xor_eq', 'const_cast', 'dynamic_cast', 'static_cast', 'reinterpret_cast', 'NULL', 'small', 'near', 'far', 'min', 'max', 'y0', 'y1', 'j0', 'j1', 'jn', 'yn']);
const id = (n) => (CPP_KEYWORDS.has(n) ? n + '_' : n);

const KNOWN = new Set([...Object.keys(TARGETS), ...TYPE_ONLY]);
function ctype(t) {
  if (t.endsWith('[]')) {
    const et = t.slice(0, -2);
    if (et === 'byte' && !t.slice(0, -2).endsWith('[]')) return 'std::string'; // raw model / stage text
    return `Arr<${et === 'boolean' ? 'jbool' : ctype(et)}>`;
  }
  switch (t) {
    case 'int': case 'short': case 'byte': case 'char': return 'int';
    case 'long': return 'int64_t';
    case 'float': return 'float';
    case 'double': return 'double';
    case 'boolean': return 'bool';
    case 'void': return 'void';
    case 'String': return 'std::string';
    case 'Color': return 'Color';
    case 'Random': return 'std::shared_ptr<JRandom>';
    case 'DataInputStream': return 'std::shared_ptr<DataInputStream>';
    default: return KNOWN.has(t) || t === 'soundClip' ? `${t}*` : 'void*';
  }
}
const isObjType = (t) => ctype(t).endsWith('*') || t.startsWith('std::shared_ptr');
const isArrType = (t) => t.endsWith('[]') && ctype(t) !== 'std::string';
function nullFor(t) {
  if (t === 'String') return 'JNULL';
  if (isArrType(t)) return `${ctype(t)}()`;
  if (t === 'Random' || t === 'DataInputStream') return 'nullptr';
  return 'nullptr';
}
function defaultFor(t) {
  if (NUMERIC.has(t)) return '0';
  if (t === 'boolean') return 'false';
  if (t === 'Color') return 'Color()';
  return nullFor(t);
}

function fieldName(cls, f) {
  const c = classes[cls];
  return c && c.methods.some((m) => m.name === f) ? 'f_' + f : id(f);
}

function numLit(e) {
  let v = e.v;
  if (e.nt === 'int') return /^0[xX]/.test(v) ? v : String(v);
  if (e.nt === 'long') return v + 'LL';
  if (/^0[xX]/.test(v)) return v;
  if (!/[.eE]/.test(v)) v += '.0';
  else if (/^\d+[eE]/.test(v)) v = v.replace(/^(\d+)/, '$1.0');
  if (v.endsWith('.')) v += '0';
  return e.nt === 'float' ? v + 'f' : v;
}

class Emitter {
  constructor(cls) {
    this.cls = cls;
    this.scopes = [];
    this.warnings = [];
    this.labels = [];
  }
  push() { this.scopes.push(new Map()); }
  pop() { this.scopes.pop(); }
  declare(n, t) { this.scopes[this.scopes.length - 1].set(n, t); }
  lookupLocal(n) { for (let i = this.scopes.length - 1; i >= 0; i--) if (this.scopes[i].has(n)) return this.scopes[i].get(n); return null; }

  fieldType(clsName, f) {
    if (clsName && clsName.endsWith('[]') && f === 'length') return 'int';
    const c = classes[clsName];
    if (c && f in c.fieldTypes) return c.fieldTypes[f];
    this.warnings.push(`unknown field ${clsName}.${f}`);
    return 'unknown';
  }

  // expression in a context expecting type t (handles null / literals)
  exAs(e, t) {
    if (e.k === 'null') return nullFor(t);
    if (e.k === 'arrinit') return this.arrInit({ ...e, type: t });
    const [c, et] = this.ex(e);
    if (isInt(t) && !isInt(et) && NUMERIC.has(et)) return `jI(${c})`;
    if (t === 'float' && (et === 'double')) return `(float)(${c})`;
    return c;
  }

  // Returns [cppCode, javaType]
  ex(e) {
    switch (e.k) {
      case 'num': return [numLit(e), e.nt];
      case 'str': return [`std::string(${e.v})`, 'String'];
      case 'chr': return [`((int)${e.v})`, 'char'];
      case 'bool': return [e.v, 'boolean'];
      case 'null': return ['nullptr', 'null'];
      case 'this': return ['this', this.cls.name];
      case 'paren': { const [c, t] = this.ex(e.e); return ['(' + c + ')', t]; }
      case 'name': {
        const lt = this.lookupLocal(e.v);
        if (lt) return [id(e.v), lt];
        if (e.v in this.cls.fieldTypes) return ['this->' + fieldName(this.cls.name, e.v), this.cls.fieldTypes[e.v]];
        if (/^[A-Z]/.test(e.v)) return [e.v, 'class:' + e.v];
        this.warnings.push('unknown name ' + e.v);
        return [id(e.v), 'unknown'];
      }
      case 'field': {
        const [oc, ot] = this.ex(e.obj);
        if (ot === 'class:Math' && e.name === 'PI') return ['3.141592653589793', 'double'];
        if (ot.endsWith('[]') && e.name === 'length') return [`${oc}.length()`, 'int'];
        if (ot.startsWith('class:')) { this.warnings.push('static field ' + ot + '.' + e.name); return [`${ot.slice(6)}::${e.name}`, 'unknown']; }
        return [`${oc}->${fieldName(ot, e.name)}`, this.fieldType(ot, e.name)];
      }
      case 'index': {
        const [oc, ot] = this.ex(e.obj);
        const [ic] = this.ex(e.idx);
        return [`${oc}[${ic}]`, ot.endsWith('[]') ? ot.slice(0, -2) : 'unknown'];
      }
      case 'call': return this.call(e);
      case 'new': return this.newObj(e);
      case 'newarr': {
        const full = e.ty + '[]'.repeat(e.dims.length + e.extra);
        // innermost element type for jnewA: the base type wrapped in the unsized dims
        let inner = e.ty + '[]'.repeat(e.extra);
        const elem = inner === 'boolean' ? 'jbool' : ctype(inner);
        return [`jnewA<${elem}>(${e.dims.map((d) => this.ex(d)[0]).join(', ')})`, full];
      }
      case 'arrinit': return [this.arrInit(e), e.type];
      case 'cast': {
        const [c, t] = this.ex(e.e);
        const ty = e.ty;
        if (ty === 'int') return [isInt(t) ? (t === 'long' ? `(int)(${c})` : c) : `jI(${c})`, 'int'];
        if (ty === 'short') return [`(int)(int16_t)(${isInt(t) ? c : `jI(${c})`})`, 'int'];
        if (ty === 'byte') return [`(int)(int8_t)(${isInt(t) ? c : `jI(${c})`})`, 'int'];
        if (ty === 'char') return [`(int)(uint16_t)(${isInt(t) ? c : `jI(${c})`})`, 'char'];
        if (ty === 'long') return [isInt(t) ? `(int64_t)(${c})` : `jL(${c})`, 'long'];
        if (ty === 'float') return [`(float)(${c})`, 'float'];
        if (ty === 'double') return [`(double)(${c})`, 'double'];
        return [`((${ctype(ty)})(${c}))`, ty];
      }
      case 'unary': {
        const [c, t] = this.ex(e.e);
        if (e.op === '!') return ['!' + c, 'boolean'];
        return [`(${e.op}${c})`, t === 'char' ? 'int' : t];
      }
      case 'preinc': { const [c, t] = this.ex(e.e); return [`(${e.op}${c})`, t]; }
      case 'postinc': { const [c, t] = this.ex(e.e); return [`(${c}${e.op})`, t]; }
      case 'instanceof': return ['false', 'boolean'];
      case 'cond': {
        const [cc] = this.ex(e.c);
        if (e.a.k === 'null' || e.b.k === 'null') {
          const other = e.a.k === 'null' ? e.b : e.a;
          const [oc, ot] = this.ex(other);
          const n = nullFor(ot);
          return [e.a.k === 'null' ? `(${cc} ? ${n} : ${oc})` : `(${cc} ? ${oc} : ${n})`, ot];
        }
        const [ac, at] = this.ex(e.a);
        const [bc, bt] = this.ex(e.b);
        let t = at;
        if (NUMERIC.has(at) && NUMERIC.has(bt)) t = promote(at, bt);
        return [`(${cc} ? ${ac} : ${bc})`, t];
      }
      case 'bin': return this.bin(e);
      case 'assign': return this.assign(e);
      default: throw new Error('expr kind ' + e.k);
    }
  }

  bin(e) {
    const op = e.op;
    if ((op === '==' || op === '!=') && (e.l.k === 'null' || e.r.k === 'null')) {
      const other = e.l.k === 'null' ? e.r : e.l;
      const [oc, ot] = this.ex(other);
      let test;
      if (ot === 'String') test = `jisnull(${oc})`;
      else if (isArrType(ot)) test = `${oc}.isNull()`;
      else test = `(${oc} == nullptr)`;
      return [op === '==' ? test : `!${test}`, 'boolean'];
    }
    const [lc, lt] = this.ex(e.l);
    const [rc, rt] = this.ex(e.r);
    if (['==', '!=', '<', '>', '<=', '>=', '&&', '||'].includes(op)) return [`(${lc} ${op} ${rc})`, 'boolean'];
    if (op === '&' || op === '|' || op === '^') {
      if (lt === 'boolean') return [`((bool)(${lc} ${op} ${rc}))`, 'boolean'];
      return [`(${lc} ${op} ${rc})`, promote(lt, rt)];
    }
    const t = promote(lt, rt);
    if (op === '+' && t === 'String') return [`jcat(${lt === 'char' ? `jchr(${lc})` : lc}, ${rt === 'char' ? `jchr(${rc})` : rc})`, 'String'];
    if (op === '/' && isInt(t)) return [`jdiv(${lc}, ${rc})`, t];
    if (op === '%') {
      if (isInt(t)) return [`jmod(${lc}, ${rc})`, t];
      return [t === 'float' ? `(float)std::fmod((double)(${lc}), (double)(${rc}))` : `std::fmod(${lc}, ${rc})`, t];
    }
    if (op === '<<' || op === '>>') return [`(${lc} ${op} (${rc} & ${lt === 'long' ? 63 : 31}))`, lt === 'long' ? 'long' : 'int'];
    if (op === '>>>') return [`jushr(${lc}, ${rc})`, lt === 'long' ? 'long' : 'int'];
    return [`(${lc} ${op} ${rc})`, t];
  }

  assign(e) {
    const [lc, lt] = this.ex(e.l);
    if (e.op === '=') return [`${lc} = ${this.exAs(e.r, lt)}`, lt];
    const [rc, rt] = this.ex(e.r);
    const bop = e.op.slice(0, -1);
    if (bop === '>>>') return [`${lc} = jushr(${lc}, ${rc})`, lt];
    if (isInt(lt)) {
      if (bop === '/' && isInt(rt)) return [`${lc} = jdiv(${lc}, ${rc})`, lt];
      if (bop === '%' && isInt(rt)) return [`${lc} = jmod(${lc}, ${rc})`, lt];
      if (!isInt(rt)) return [`${lc} = ${lt === 'long' ? 'jL' : 'jI'}(${lc} ${bop === '%' ? '+' : bop} (${rc}))`, lt];
      if (bop === '<<' || bop === '>>') return [`${lc} ${e.op} (${rc} & 31)`, lt];
      return [`${lc} ${e.op} ${rc}`, lt];
    }
    if (lt === 'String' && bop === '+') return [`${lc} = jcat(${lc}, ${rt === 'char' ? `jchr(${rc})` : rc})`, lt];
    if (bop === '%') return [`${lc} = std::fmod(${lc}, ${rc})`, lt];
    if (lt === 'float') return [`${lc} = (float)(${lc} ${bop} (${rc}))`, lt];
    return [`${lc} ${e.op} ${rc}`, lt];
  }

  argsFor(e, cls, name) {
    const c = cls && classes[cls];
    const m = c && c.methods.find((x) => x.name === name && x.params.length === e.args.length);
    return e.args.map((a, i) => (m ? this.exAs(a, m.params[i].type) : (a.k === 'null' ? 'nullptr' : this.ex(a)[0]))).join(', ');
  }

  call(e) {
    if (!e.obj) {
      const rt = this.cls.methodTypes[e.name] ?? 'unknown';
      return [`this->${id(e.name)}(${this.argsFor(e, this.cls.name, e.name)})`, rt];
    }
    const [oc, ot] = this.ex(e.obj);
    const args = e.args.map((a) => this.ex(a));
    const ac = args.map((a) => a[0]).join(', ');
    const t0 = args[0]?.[1];
    if (ot === 'class:Math') {
      const d = args.map((a) => `(double)(${a[0]})`).join(', ');
      switch (e.name) {
        case 'abs': return [`jabs(${ac})`, t0 === 'char' || t0 === 'short' || t0 === 'byte' ? 'int' : t0];
        case 'max': case 'min': {
          const t = promote(t0, args[1][1]);
          const ct = ctype(t);
          return [`std::${e.name}<${ct}>((${ct})(${args[0][0]}), (${ct})(${args[1][0]}))`, t];
        }
        case 'round': return t0 === 'float' ? [`jround((float)(${ac}))`, 'int'] : [`jround((double)(${ac}))`, 'long'];
        case 'random': return ['jrandom()', 'double'];
        case 'toRadians': return [`((double)(${ac}) / 180.0 * 3.141592653589793)`, 'double'];
        case 'toDegrees': return [`((double)(${ac}) * 180.0 / 3.141592653589793)`, 'double'];
        default: return [`std::${e.name}(${d})`, 'double'];
      }
    }
    if (ot === 'class:Integer' && (e.name === 'valueOf' || e.name === 'parseInt')) return [`jParseInt(${ac})`, 'int'];
    if (ot === 'class:Float' && (e.name === 'valueOf' || e.name === 'parseFloat')) return [`jParseFloat(${ac})`, 'float'];
    if (ot === 'class:Color') {
      if (e.name === 'getHSBColor') return [`Color::getHSBColor(${ac})`, 'Color'];
      if (e.name === 'RGBtoHSB') return [`Color::RGBtoHSB(${args.slice(0, 3).map((a) => a[0]).join(', ')}, ${e.args[3].k === 'null' ? 'Arr<float>()' : args[3][0]})`, 'float[]'];
    }
    if (ot === 'class:System' || ot === 'class:Thread' || ot === 'class:Console' || (oc.includes('System') && e.name === 'println')) return ['(void)0', 'void'];
    if (e.name === 'intValue') return [isInt(ot) ? oc : `jI(${oc})`, 'int'];
    if (e.name === 'floatValue') return [`(float)(${oc})`, 'float'];
    if (ot === 'String') {
      switch (e.name) {
        case 'equals': return [`(${oc} == ${ac})`, 'boolean'];
        case 'equalsIgnoreCase': return [`jequalsIC(${oc}, ${ac})`, 'boolean'];
        case 'length': return [`((int)(${oc}).size())`, 'int'];
        case 'startsWith': return [`jstarts(${oc}, ${ac})`, 'boolean'];
        case 'endsWith': return [`jends(${oc}, ${ac})`, 'boolean'];
        case 'trim': return [`jtrim(${oc})`, 'String'];
        case 'charAt': return [`jcharAt(${oc}, ${ac})`, 'char'];
        case 'indexOf': return [`jindexOf(${oc}, ${ac})`, 'int'];
        case 'substring': return [`jsubstr(${oc}, ${ac})`, 'String'];
        case 'toLowerCase': return [`jlower(${oc})`, 'String'];
        case 'toUpperCase': return [`jupper(${oc})`, 'String'];
        case 'replace': return [`jreplace(${oc}, ${ac})`, 'String'];
        default: this.warnings.push('String.' + e.name); return [`/*?*/${oc}.${e.name}(${ac})`, 'unknown'];
      }
    }
    if (ot === 'Color') return [`${oc}.${e.name}(${ac})`, 'int'];
    if (ot === 'Random') return [`${oc}->${e.name}(${ac})`, e.name === 'nextDouble' ? 'double' : e.name === 'nextFloat' ? 'float' : 'int'];
    if (ot === 'DataInputStream') return [`${oc}->${e.name}(${ac})`, e.name === 'readLine' ? 'String' : 'void'];
    const c = classes[ot];
    let rt = 'unknown';
    if (c && e.name in c.methodTypes) rt = c.methodTypes[e.name];
    else this.warnings.push(`unknown method ${ot}.${e.name}`);
    return [`${oc}->${id(e.name)}(${this.argsFor(e, ot, e.name)})`, rt];
  }

  newObj(e) {
    const args = e.args.map((a) => this.ex(a));
    const ac = args.map((a) => a[0]).join(', ');
    const ty = e.ty;
    if (ty === 'Random') return [`std::make_shared<JRandom>(${ac})`, 'Random'];
    if (ty === 'Color') return [`Color(${ac})`, 'Color'];
    if (ty === 'DataInputStream') return [`std::make_shared<DataInputStream>(${ac})`, 'DataInputStream'];
    if (ty === 'ByteArrayInputStream') return [args[0][0], 'bytes'];
    if (classes[ty]) {
      const ctors = classes[ty].methods.filter((m) => m.name === '<init>');
      if (ctors.length > 1) {
        const idx = ctors.findIndex((m) => m.params.length === e.args.length);
        if (idx < 0) throw new Error('no ctor match ' + ty);
        const ca = e.args.map((a, i) => this.exAs(a, ctors[idx].params[i].type)).join(', ');
        return [`${ty}::create${idx}(${ca})`, ty];
      }
      const ca = ctors[0] ? e.args.map((a, i) => this.exAs(a, ctors[0].params[i].type)).join(', ') : ac;
      return [`jnew<${ty}>(${ca})`, ty];
    }
    this.warnings.push('new ' + ty);
    return [`nullptr /* new ${ty} */`, ty];
  }

  arrInit(e) {
    const et = e.type.slice(0, -2);
    const els = e.els.map((x) => (x.k === 'arrinit' ? this.arrInit({ ...x, type: et }) : this.exAs(x, et)));
    const ce = et === 'boolean' ? 'jbool' : ctype(et);
    return `Arr<${ce}>{${els.map((x) => (isInt(et) || et === 'float' || et === 'double' ? `(${ce})(${x})` : x)).join(', ')}}`;
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
        const init = v.init ? this.exAs(v.init, v.type) : defaultFor(v.type);
        return `${I}${ctype(v.type)} ${id(v.name)} = ${init};\n`;
      }).join('');
      case 'expr': return `${I}${this.ex(s.e)[0]};\n`;
      case 'if': {
        let out = `${I}if (${this.ex(s.c)[0]}) ${this.sub(s.th, ind)}`;
        if (s.el) out = out.replace(/\n$/, '') + ` else ${this.sub(s.el, ind)}`;
        return out;
      }
      case 'while': return `${I}while (${this.ex(s.c)[0]}) ${this.loopBody(s.body, ind)}${this.loopEnd(ind)}`;
      case 'do': return `${I}do ${this.loopBody(s.body, ind).replace(/\n$/, '')} while (${this.ex(s.c)[0]});\n${this.loopEnd(ind)}`;
      case 'for': {
        this.push();
        let init = '';
        if (s.init?.k === 'local') {
          const ty = s.init.vars[0].type;
          init = ctype(ty) + ' ' + s.init.vars.map((v) => { this.declare(v.name, v.type); return `${id(v.name)} = ${v.init ? this.exAs(v.init, v.type) : defaultFor(v.type)}`; }).join(', ');
        } else if (s.init) init = s.init.es.map((x) => this.ex(x)[0]).join(', ');
        const c = s.c ? this.ex(s.c)[0] : '';
        const u = s.upd.map((x) => this.ex(x)[0]).join(', ');
        const out = `${I}for (${init}; ${c}; ${u}) ${this.loopBody(s.body, ind)}${this.loopEnd(ind)}`;
        this.pop();
        return out;
      }
      case 'foreach': {
        this.push();
        this.declare(s.v.name, s.v.type);
        const [it] = this.ex(s.it);
        const out = `${I}for (int _i_${s.v.name} = 0, _n_${s.v.name} = (${it}).length(); _i_${s.v.name} < _n_${s.v.name}; _i_${s.v.name}++) {\n${I}  ${ctype(s.v.type)} ${id(s.v.name)} = (${it})[_i_${s.v.name}];\n${I}  ${this.loopBody(s.body, ind + 1)}${I}}\n${this.loopEnd(ind)}`;
        this.pop();
        return out;
      }
      case 'return': {
        if (!s.e) return `${I}return;\n`;
        return `${I}return ${this.exAs(s.e, this.retType)};\n`;
      }
      case 'break': case 'continue': {
        if (!s.label) return `${I}${s.k};\n`;
        return `${I}goto ${s.k === 'break' ? 'L_brk_' : 'L_cont_'}${s.label};\n`;
      }
      case 'throw': return `${I}throw std::runtime_error("java exception");\n`;
      case 'label': {
        this.labels.push(s.name);
        const out = this.st(s.body, ind);
        this.labels.pop();
        return out + `${I}L_brk_${s.name}: ;\n`;
      }
      case 'try': {
        let out = `${I}try ${this.sub(s.body, ind)}`;
        out = out.replace(/\n$/, '');
        if (s.catches.length) {
          this.push();
          const c = s.catches[0];
          this.declare(c.nm, c.ty);
          out += ` catch (const std::exception &${id(c.nm)}) ${this.sub(c.body, ind)}`;
          this.pop();
        } else out += ' catch (...) { throw; }\n';
        if (s.fin) out += this.st(s.fin, ind);
        return out;
      }
      default: throw new Error('stmt ' + s.k);
    }
  }
  // Loop bodies get an end-of-body label so `continue label;` can jump there.
  loopBody(body, ind) {
    const label = this.pendingLabel();
    const inner = this.sub(body, ind);
    if (!label) return inner;
    return `{\n${'  '.repeat(ind + 1)}${inner.replace(/\n$/, '')}\n${'  '.repeat(ind + 1)}L_cont_${label}: ;\n${'  '.repeat(ind)}}\n`;
  }
  pendingLabel() {
    // the innermost label applies to the loop statement it directly wraps
    const l = this.labels[this.labels.length - 1];
    if (l && !this.usedLabels?.has(l)) { (this.usedLabels ||= new Set()).add(l); return l; }
    return null;
  }
  loopEnd() { return ''; }
  sub(s, ind) {
    if (s.k === 'block') return this.st(s, ind).replace(/^\s*/, '');
    return '{\n' + this.st(s, ind + 1) + '  '.repeat(ind) + '}\n';
  }

  method(m) {
    this.push();
    for (const p of m.params) this.declare(p.name, p.type);
    this.retType = m.ret;
    this.usedLabels = new Set();
    const body = this.st(m.body, 0).replace(/^\s*/, '');
    this.pop();
    return { params: m.params.map((p) => `${ctype(p.type)} ${id(p.name)}`).join(', '), body };
  }
}

/* ------------------------------------------------------------------ main */
for (const n of TYPE_ONLY) loadClass(n, false);
for (const n of Object.keys(TARGETS)) loadClass(n, true);

const hdr = [];
const src = [];
let totalWarn = 0;
const sig = (m) => m.params.map((p) => ctype(p.type)).join(',');

for (const [name, keep] of Object.entries(TARGETS)) {
  const cls = classes[name];
  const em = new Emitter(cls);
  const decl = [];
  for (const f of cls.fields) decl.push(`  ${ctype(f.type)} ${fieldName(name, f.name)};`);
  em.push();
  const fieldInit = cls.fields.map((f) => `  this->${fieldName(name, f.name)} = ${f.init ? em.exAs(f.init, f.type) : defaultFor(f.type)};\n`).join('');
  em.pop();
  const ctors = cls.methods.filter((m) => m.name === '<init>' && m.body);
  if (ctors.length <= 1) {
    const c = ctors[0];
    const { params, body } = c ? em.method(c) : { params: '', body: '{\n}\n' };
    decl.push(`  ${name}(${params});`);
    src.push(`${name}::${name}(${params}) {\n${fieldInit}  ${body.replace(/^\{\n/, '').replace(/\}\n$/, '').trimStart()}}\n`);
  } else {
    decl.push(`  ${name}();`);
    src.push(`${name}::${name}() {\n${fieldInit}}\n`);
    ctors.forEach((c, i) => {
      const { params, body } = em.method(c);
      const argNames = c.params.map((p) => id(p.name)).join(', ');
      decl.push(`  void _init${i}(${params});`);
      decl.push(`  static ${name} *create${i}(${params}) { auto *o = jnew<${name}>(); o->_init${i}(${argNames}); return o; }`);
      src.push(`void ${name}::_init${i}(${params}) ${body.trimEnd()}\n`);
    });
  }
  const seen = new Set();
  for (const m of cls.methods) {
    if (m.name === '<init>') continue;
    const key = m.name + '(' + sig(m) + ')';
    if (seen.has(key)) continue;
    seen.add(key);
    const kept = m.body && (!keep || keep.includes(m.name));
    const manual = MANUAL[name]?.includes(m.name);
    if (kept) {
      const { params, body } = em.method(m);
      decl.push(`  ${ctype(m.ret)} ${id(m.name)}(${params});`);
      src.push(`${ctype(m.ret)} ${name}::${id(m.name)}(${params}) ${body.trimEnd()}\n`);
    } else {
      const params = m.params.map((p) => `${ctype(p.type)} ${id(p.name)}`).join(', ');
      decl.push(`  ${ctype(m.ret)} ${id(m.name)}(${params});`);
      if (!manual) {
        const unnamed = m.params.map((p) => ctype(p.type)).join(', ');
        src.push(`${ctype(m.ret)} ${name}::${id(m.name)}(${unnamed}) { ${m.ret === 'void' ? '' : `return ${defaultFor(m.ret)}; `}}\n`);
      }
    }
  }
  hdr.push(`struct ${name} {\n${decl.join('\n')}\n};\n`);
  const uniq = [...new Set(em.warnings)];
  totalWarn += uniq.length;
  console.log(`${name}: ${cls.methods.length} methods${uniq.length ? ', warnings:\n   ' + uniq.join('\n   ') : ''}`);
}

fs.mkdirSync(OUT_DIR, { recursive: true });
const banner = '// AUTO-GENERATED by remake/tools/j2cpp.mjs from remake/tools/java/*.java. Do not edit.\n';
fs.writeFileSync(path.join(OUT_DIR, 'nfm_gen.h'), `${banner}#pragma once\n#include "../jrt.h"\n\n${[...KNOWN, 'soundClip'].map((n) => `struct ${n};`).join('\n')}\n\n#include "../bridges.h"\n\n${hdr.join('\n')}`);
fs.writeFileSync(path.join(OUT_DIR, 'nfm_gen.cpp'), `${banner}#include "nfm_gen.h"\n#pragma GCC diagnostic ignored "-Wunused-label"\n#pragma GCC diagnostic ignored "-Wunused-variable"\n#pragma GCC diagnostic ignored "-Wunused-but-set-variable"\n#pragma GCC diagnostic ignored "-Wparentheses"\n\n${src.join('\n')}`);
console.log(totalWarn ? `done with ${totalWarn} warnings` : 'done');
