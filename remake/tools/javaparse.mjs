// Shared Java front end for the translators (j2js.mjs, j2cpp.mjs): lexer, parser and
// class/field/method type tables for the decompiled sources in tools/java/.
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

export const JAVA_DIR = path.join(path.dirname(fileURLToPath(import.meta.url)), 'java');

/* ------------------------------------------------------------------ lexer */
const OPS = ['>>>=', '<<=', '>>=', '>>>', '==', '!=', '<=', '>=', '&&', '||', '++', '--', '+=', '-=', '*=', '/=', '%=', '&=', '|=', '^=', '<<', '>>', '->', '::',
  '{', '}', '(', ')', '[', ']', ';', ',', '.', '=', '<', '>', '!', '~', '?', ':', '+', '-', '*', '/', '&', '|', '^', '%', '@'];

export function lex(src) {
  const toks = [];
  let i = 0;
  const n = src.length;
  while (i < n) {
    const c = src[i];
    if (c === ' ' || c === '\t' || c === '\r' || c === '\n') { i++; continue; }
    if (src.startsWith('//', i)) { while (i < n && src[i] !== '\n') i++; continue; }
    if (src.startsWith('/*', i)) { i = src.indexOf('*/', i + 2) + 2; continue; }
    if (c === '"' || c === "'") {
      let j = i + 1;
      while (src[j] !== c) { if (src[j] === '\\') j++; j++; }
      toks.push({ t: c === '"' ? 'str' : 'chr', v: src.slice(i, j + 1) });
      i = j + 1;
      continue;
    }
    if (/[0-9]/.test(c) || (c === '.' && /[0-9]/.test(src[i + 1]))) {
      const m = /^(0[xX][0-9a-fA-F]+|\d+\.\d*(?:[eE][+-]?\d+)?|\.\d+(?:[eE][+-]?\d+)?|\d+(?:[eE][+-]?\d+)?)([fFdDlL]?)/.exec(src.slice(i));
      const body = m[1], suf = m[2].toLowerCase();
      let nt = 'int';
      if (suf === 'f') nt = 'float';
      else if (suf === 'd' || /[.eE]/.test(body) && !/^0[xX]/.test(body)) nt = 'double';
      else if (suf === 'l') nt = 'long';
      toks.push({ t: 'num', v: body, nt });
      i += m[0].length;
      continue;
    }
    if (/[A-Za-z_$]/.test(c)) {
      let j = i + 1;
      while (j < n && /[A-Za-z0-9_$]/.test(src[j])) j++;
      toks.push({ t: 'id', v: src.slice(i, j) });
      i = j;
      continue;
    }
    const op = OPS.find((o) => src.startsWith(o, i));
    if (!op) throw new Error('lex error at ' + src.slice(i, i + 30));
    toks.push({ t: 'op', v: op });
    i += op.length;
  }
  toks.push({ t: 'eof', v: '' });
  return toks;
}

/* ----------------------------------------------------------------- parser */
export const PRIMS = new Set(['int', 'float', 'double', 'boolean', 'long', 'short', 'byte', 'char', 'void']);
const MODS = new Set(['public', 'private', 'protected', 'static', 'final', 'abstract', 'synchronized', 'transient', 'volatile', 'native', 'strictfp']);

export class Parser {
  constructor(toks) { this.toks = toks; this.p = 0; }
  peek(o = 0) { return this.toks[this.p + o]; }
  next() { return this.toks[this.p++]; }
  is(v, o = 0) { const t = this.peek(o); return (t.t === 'op' || t.t === 'id') && t.v === v; }
  eat(v) { if (!this.is(v)) throw new Error(`expected ${v} got ${this.peek().v} near ${this.ctx()}`); return this.next(); }
  ctx() { return this.toks.slice(Math.max(0, this.p - 8), this.p + 8).map((t) => t.v).join(' '); }

  parseClass() {
    while (!this.is('class')) this.next();
    this.eat('class');
    const name = this.next().v;
    while (!this.is('{')) this.next();
    this.eat('{');
    const fields = [], methods = [];
    while (!this.is('}')) {
      if (this.is('@')) { this.next(); this.next(); continue; }
      if (this.is(';')) { this.next(); continue; }
      while (this.peek().t === 'id' && MODS.has(this.peek().v)) this.next();
      // constructor?
      if (this.peek().v === name && this.is('(', 1)) {
        this.next();
        methods.push(this.parseMethodRest('<init>', null));
        continue;
      }
      const type = this.parseType();
      const mname = this.next().v;
      if (this.is('(')) { methods.push(this.parseMethodRest(mname, type)); continue; }
      // field(s)
      let fname = mname;
      for (;;) {
        let ftype = type;
        while (this.is('[')) { this.next(); this.eat(']'); ftype += '[]'; }
        let init = null;
        if (this.is('=')) { this.next(); init = this.is('{') ? this.parseArrayInit(ftype) : this.parseExpr(); }
        fields.push({ name: fname, type: ftype, init });
        if (this.is(',')) { this.next(); fname = this.next().v; continue; }
        break;
      }
      this.eat(';');
    }
    return { name, fields, methods };
  }

  parseMethodRest(name, ret) {
    this.eat('(');
    const params = [];
    while (!this.is(')')) {
      while (this.is('final')) this.next();
      let t = this.parseType();
      const pn = this.next().v;
      while (this.is('[')) { this.next(); this.eat(']'); t += '[]'; }
      params.push({ name: pn, type: t });
      if (this.is(',')) this.next();
    }
    this.eat(')');
    if (this.is('throws')) { this.next(); this.parseType(); while (this.is(',')) { this.next(); this.parseType(); } }
    let body = null;
    const start = this.p;
    if (this.is(';')) this.next();
    else body = this.parseBlock();
    return { name, ret, params, body, tokStart: start };
  }

  parseType() {
    let t = this.next().v;
    while (this.is('.') && this.peek(1).t === 'id') { this.next(); t += '.' + this.next().v; }
    if (this.is('<')) { let d = 0; do { if (this.is('<')) d++; if (this.is('>')) d--; this.next(); } while (d > 0); }
    while (this.is('[') && this.is(']', 1)) { this.next(); this.next(); t += '[]'; }
    return t;
  }

  // Lookahead: does a local variable declaration start here?
  isLocalDecl() {
    const save = this.p;
    try {
      while (this.is('final')) this.next();
      const t = this.peek();
      if (t.t !== 'id') return false;
      if (!PRIMS.has(t.v) && !/^[A-Z]/.test(t.v)) return false;
      this.parseType();
      const nm = this.peek();
      if (nm.t !== 'id') return false;
      const after = this.peek(1);
      return after.t === 'op' && ['=', ';', ',', '[', ':'].includes(after.v);
    } catch { return false; } finally { this.p = save; }
  }

  parseLocalDecl() {
    while (this.is('final')) this.next();
    const type = this.parseType();
    const vars = [];
    for (;;) {
      const name = this.next().v;
      let t = type;
      while (this.is('[')) { this.next(); this.eat(']'); t += '[]'; }
      let init = null;
      if (this.is('=')) { this.next(); init = this.is('{') ? this.parseArrayInit(t) : this.parseExpr(); }
      vars.push({ name, type: t, init });
      if (this.is(',')) { this.next(); continue; }
      break;
    }
    return { k: 'local', vars };
  }

  parseBlock() {
    this.eat('{');
    const body = [];
    while (!this.is('}')) body.push(this.parseStmt());
    this.eat('}');
    return { k: 'block', body };
  }

  parseStmt() {
    const t = this.peek();
    if (this.is('{')) return this.parseBlock();
    if (this.is(';')) { this.next(); return { k: 'empty' }; }
    if (t.t === 'id' && this.peek(1).t === 'op' && this.peek(1).v === ':' && !PRIMS.has(t.v)) {
      this.next(); this.next();
      return { k: 'label', name: t.v, body: this.parseStmt() };
    }
    if (t.t === 'id') {
      switch (t.v) {
        case 'if': {
          this.next(); this.eat('('); const c = this.parseExpr(); this.eat(')');
          const th = this.parseStmt();
          let el = null;
          if (this.is('else')) { this.next(); el = this.parseStmt(); }
          return { k: 'if', c, th, el };
        }
        case 'while': { this.next(); this.eat('('); const c = this.parseExpr(); this.eat(')'); return { k: 'while', c, body: this.parseStmt() }; }
        case 'do': { this.next(); const body = this.parseStmt(); this.eat('while'); this.eat('('); const c = this.parseExpr(); this.eat(')'); this.eat(';'); return { k: 'do', c, body }; }
        case 'for': {
          this.next(); this.eat('(');
          let init = null;
          if (this.isLocalDecl()) {
            const save = this.p;
            const d = this.parseLocalDecl();
            if (this.is(':')) { // enhanced for
              this.next(); const it = this.parseExpr(); this.eat(')');
              return { k: 'foreach', v: d.vars[0], it, body: this.parseStmt() };
            }
            init = d;
            void save;
          } else if (!this.is(';')) {
            const es = [this.parseExpr()];
            while (this.is(',')) { this.next(); es.push(this.parseExpr()); }
            init = { k: 'exprs', es };
          }
          this.eat(';');
          const c = this.is(';') ? null : this.parseExpr();
          this.eat(';');
          const upd = [];
          while (!this.is(')')) { upd.push(this.parseExpr()); if (this.is(',')) this.next(); }
          this.eat(')');
          return { k: 'for', init, c, upd, body: this.parseStmt() };
        }
        case 'return': { this.next(); const e = this.is(';') ? null : this.parseExpr(); this.eat(';'); return { k: 'return', e }; }
        case 'break': case 'continue': {
          this.next(); let label = null;
          if (this.peek().t === 'id') label = this.next().v;
          this.eat(';'); return { k: t.v, label };
        }
        case 'throw': { this.next(); const e = this.parseExpr(); this.eat(';'); return { k: 'throw', e }; }
        case 'try': {
          this.next(); const body = this.parseBlock(); const catches = []; let fin = null;
          while (this.is('catch')) {
            this.next(); this.eat('('); const ty = this.parseType(); while (this.is('|')) { this.next(); this.parseType(); }
            const nm = this.next().v; this.eat(')'); catches.push({ ty, nm, body: this.parseBlock() });
          }
          if (this.is('finally')) { this.next(); fin = this.parseBlock(); }
          return { k: 'try', body, catches, fin };
        }
        default:
      }
    }
    if (this.isLocalDecl()) { const d = this.parseLocalDecl(); this.eat(';'); return d; }
    const e = this.parseExpr();
    this.eat(';');
    return { k: 'expr', e };
  }

  parseArrayInit(type) {
    this.eat('{');
    const els = [];
    const et = type.endsWith('[]') ? type.slice(0, -2) : type;
    while (!this.is('}')) {
      els.push(this.is('{') ? this.parseArrayInit(et) : this.parseExpr());
      if (this.is(',')) this.next();
    }
    this.eat('}');
    return { k: 'arrinit', type, els };
  }

  parseExpr() { return this.parseAssign(); }

  parseAssign() {
    const lhs = this.parseTernary();
    const t = this.peek();
    if (t.t === 'op' && ['=', '+=', '-=', '*=', '/=', '%=', '&=', '|=', '^=', '<<=', '>>=', '>>>='].includes(t.v)) {
      this.next();
      const rhs = this.parseAssign();
      return { k: 'assign', op: t.v, l: lhs, r: rhs };
    }
    return lhs;
  }

  parseTernary() {
    const c = this.parseBin(0);
    if (this.is('?')) {
      this.next();
      const a = this.parseTernary();
      this.eat(':');
      const b = this.parseTernary();
      return { k: 'cond', c, a, b };
    }
    return c;
  }

  parseBin(level) {
    const LEVELS = [['||'], ['&&'], ['|'], ['^'], ['&'], ['==', '!='], ['<', '>', '<=', '>=', 'instanceof'], ['<<', '>>', '>>>'], ['+', '-'], ['*', '/', '%']];
    if (level >= LEVELS.length) return this.parseUnary();
    let l = this.parseBin(level + 1);
    for (;;) {
      const t = this.peek();
      if ((t.t === 'op' || t.t === 'id') && LEVELS[level].includes(t.v)) {
        this.next();
        if (t.v === 'instanceof') { const ty = this.parseType(); l = { k: 'instanceof', e: l, ty }; continue; }
        const r = this.parseBin(level + 1);
        l = { k: 'bin', op: t.v, l, r };
      } else return l;
    }
  }

  isCast() {
    if (!this.is('(')) return false;
    const t = this.peek(1);
    if (t.t !== 'id') return false;
    if (PRIMS.has(t.v)) {
      // (int) x   or (int[]) x
      let j = 2;
      while (this.is('[', j) && this.is(']', j + 1)) j += 2;
      return this.is(')', j);
    }
    if (!/^[A-Z]/.test(t.v)) return false;
    let j = 2;
    while (this.is('.', j) && this.peek(j + 1).t === 'id') j += 2;
    while (this.is('[', j) && this.is(']', j + 1)) j += 2;
    if (!this.is(')', j)) return false;
    const after = this.peek(j + 1);
    return after.t === 'id' || after.t === 'num' || after.t === 'str' || after.t === 'chr' || (after.t === 'op' && ['(', '!', '~'].includes(after.v));
  }

  parseUnary() {
    const t = this.peek();
    if (t.t === 'op' && ['+', '-', '!', '~'].includes(t.v)) { this.next(); return { k: 'unary', op: t.v, e: this.parseUnary() }; }
    if (t.t === 'op' && (t.v === '++' || t.v === '--')) { this.next(); return { k: 'preinc', op: t.v, e: this.parseUnary() }; }
    if (this.isCast()) {
      this.eat('(');
      const ty = this.parseType();
      this.eat(')');
      return { k: 'cast', ty, e: this.parseUnary() };
    }
    return this.parsePostfix(this.parsePrimary());
  }

  parsePostfix(e) {
    for (;;) {
      if (this.is('.')) {
        this.next();
        const name = this.next().v;
        if (this.is('(')) e = { k: 'call', obj: e, name, args: this.parseArgs() };
        else e = { k: 'field', obj: e, name };
      } else if (this.is('[')) {
        this.next(); const idx = this.parseExpr(); this.eat(']');
        e = { k: 'index', obj: e, idx };
      } else if (this.is('++') || this.is('--')) {
        e = { k: 'postinc', op: this.next().v, e };
      } else return e;
    }
  }

  parseArgs() {
    this.eat('(');
    const args = [];
    while (!this.is(')')) { args.push(this.parseExpr()); if (this.is(',')) this.next(); }
    this.eat(')');
    return args;
  }

  parsePrimary() {
    const t = this.next();
    if (t.t === 'num') return { k: 'num', v: t.v, nt: t.nt };
    if (t.t === 'str') return { k: 'str', v: t.v };
    if (t.t === 'chr') return { k: 'chr', v: t.v };
    if (t.t === 'op' && t.v === '(') { const e = this.parseExpr(); this.eat(')'); return { k: 'paren', e }; }
    if (t.t === 'id') {
      if (t.v === 'true' || t.v === 'false') return { k: 'bool', v: t.v };
      if (t.v === 'null') return { k: 'null' };
      if (t.v === 'this') return { k: 'this' };
      if (t.v === 'new') {
        let ty = this.next().v;
        while (this.is('.')) { this.next(); ty += '.' + this.next().v; }
        if (this.is('[')) {
          const dims = [];
          let extra = 0;
          while (this.is('[')) {
            this.next();
            if (this.is(']')) { this.next(); extra++; continue; }
            dims.push(this.parseExpr()); this.eat(']');
          }
          const full = ty + '[]'.repeat(dims.length + extra);
          if (this.is('{')) return this.parseArrayInit(full);
          return { k: 'newarr', ty, dims, extra };
        }
        const args = this.parseArgs();
        if (this.is('{')) throw new Error('anonymous class not supported: ' + this.ctx());
        return { k: 'new', ty, args };
      }
      if (this.is('(')) return { k: 'call', obj: null, name: t.v, args: this.parseArgs() };
      return { k: 'name', v: t.v };
    }
    throw new Error('unexpected token ' + t.v + ' near ' + this.ctx());
  }
}

/* ------------------------------------------------------- type information */
export const classes = {};
export function loadClass(name, full) {
  const src = fs.readFileSync(path.join(JAVA_DIR, name + '.java'), 'utf8');
  const p = new Parser(lex(src));
  let cls;
  if (full) cls = p.parseClass();
  else {
    // Fields and method signatures only: skip method bodies by brace matching.
    cls = parseSignatures(p, name);
  }
  classes[name] = cls;
  cls.fieldTypes = Object.fromEntries(cls.fields.map((f) => [f.name, f.type]));
  cls.methodTypes = {};
  for (const m of cls.methods) if (m.name !== '<init>' && !(m.name in cls.methodTypes)) cls.methodTypes[m.name] = m.ret;
  return cls;
}

function parseSignatures(p, name) {
  // Replace the body parser with a brace skipper.
  p.parseBlock = function () {
    this.eat('{');
    let d = 1;
    while (d > 0) { const t = this.next(); if (t.t === 'op' && t.v === '{') d++; if (t.t === 'op' && t.v === '}') d--; }
    return null;
  };
  const origArr = p.parseArrayInit.bind(p);
  p.parseArrayInit = function (type) { return origArr(type); };
  return p.parseClass();
}

export const INTEGRAL = new Set(['int', 'long', 'short', 'byte', 'char']);
export const NUMERIC = new Set(['int', 'long', 'short', 'byte', 'char', 'float', 'double']);
export function promote(a, b) {
  if (a === 'String' || b === 'String') return 'String';
  if (a === 'double' || b === 'double') return 'double';
  if (a === 'float' || b === 'float') return 'float';
  if (a === 'long' || b === 'long') return 'long';
  if (NUMERIC.has(a) && NUMERIC.has(b)) return 'int';
  if (a === 'unknown' || b === 'unknown') return 'unknown';
  return 'int';
}
export const isInt = (t) => INTEGRAL.has(t);
// Java lets a field and a method share a name; JS does not, so such fields get a prefix.
export function fieldName(cls, f) {
  const c = classes[cls];
  return c && c.methods.some((m) => m.name === f) ? 'f_' + f : f;
}

export const LIB_FIELDS = { length: 'int' };
export const STRING_METHODS = { startsWith: 'boolean', endsWith: 'boolean', trim: 'String', length: 'int', charAt: 'char', indexOf: 'int', equals: 'boolean', replace: 'String', substring: 'String', toLowerCase: 'String', toUpperCase: 'String', equalsIgnoreCase: 'boolean' };

