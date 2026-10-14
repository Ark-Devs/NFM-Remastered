// Java runtime pieces the translated game logic (gen/nfm_gen.*) relies on,
// with Java's numeric rules: saturating float->int casts, truncating integer
// division (and 0 for division by zero, as the JS port did), reference arrays.
#pragma once
#include <algorithm>
#include <cmath>
#include <cstdint>
#include <cstdlib>
#include <cstring>
#include <memory>
#include <stdexcept>
#include <string>
#include <vector>

struct JIndexError : std::runtime_error { JIndexError() : std::runtime_error("index out of range") {} };
struct JFormatError : std::runtime_error { JFormatError(const std::string &s) : std::runtime_error("NumberFormatException: " + s) {} };

// boolean[] elements (std::vector<bool> can't hand out references)
struct jbool {
  bool v = false;
  jbool() {}
  jbool(bool b) : v(b) {}
  operator bool() const { return v; }
};

template <class T> constexpr bool jIsObj = std::is_pointer_v<T>;
template <class T> struct Arr;
template <class T> struct jIsArr : std::false_type {};
template <class T> struct jIsArr<Arr<T>> : std::true_type {};

// Java array: a shared reference to a fixed-size buffer. Out-of-range reads of
// numbers give 0 and writes are dropped (what the JS typed arrays did); for
// object / array elements an out-of-range access throws, like Java.
template <class T>
struct Arr {
  std::shared_ptr<std::vector<T>> p;
  Arr() {}
  explicit Arr(int n) : p(std::make_shared<std::vector<T>>(n < 0 ? 0 : n)) {}
  Arr(std::initializer_list<T> l) : p(std::make_shared<std::vector<T>>(l)) {}
  int length() const { return p ? (int)p->size() : 0; }
  bool isNull() const { return !p; }
  T &operator[](int i) const {
    if (!p || (unsigned)i >= p->size()) {
      if constexpr (jIsObj<T> || jIsArr<T>::value) throw JIndexError();
      static thread_local T dummy;
      dummy = T();
      return dummy;
    }
    return (*p)[i];
  }
  T *data() const { return p ? p->data() : nullptr; }
  bool operator==(const Arr &o) const { return p == o.p; }
  bool operator!=(const Arr &o) const { return p != o.p; }
};

template <class E> Arr<E> jnewA(int n) { return Arr<E>(n); }
template <class E> Arr<Arr<E>> jnewA(int n, int m) {
  Arr<Arr<E>> a(n);
  for (int i = 0; i < n; i++) a[i] = Arr<E>(m);
  return a;
}
template <class E> Arr<Arr<Arr<E>>> jnewA(int n, int m, int k) {
  Arr<Arr<Arr<E>>> a(n);
  for (int i = 0; i < n; i++) a[i] = jnewA<E>(m, k);
  return a;
}

// ---- numbers
inline int jI(double x) {
  if (std::isnan(x)) return 0;
  if (x >= 2147483647.0) return 2147483647;
  if (x <= -2147483648.0) return -2147483648;
  return (int)x;
}
inline int64_t jL(double x) {
  if (std::isnan(x)) return 0;
  if (x >= 9.2233720368547758e18) return INT64_MAX;
  if (x <= -9.2233720368547758e18) return INT64_MIN;
  return (int64_t)x;
}
template <class A, class B> inline auto jdiv(A a, B b) -> decltype(a / b) {
  if (b == 0) return 0;
  if constexpr (std::is_same_v<decltype(a / b), int>) { if (a == INT32_MIN && b == -1) return INT32_MIN; }
  return a / b;
}
template <class A, class B> inline auto jmod(A a, B b) -> decltype(a % b) {
  if (b == 0) return 0;
  if constexpr (std::is_same_v<decltype(a % b), int>) { if (b == -1) return 0; }
  return a % b;
}
inline int jushr(int a, int b) { return (int)((uint32_t)a >> (b & 31)); }
inline int64_t jushr(int64_t a, int b) { return (int64_t)((uint64_t)a >> (b & 63)); }
template <class T> inline T jabs(T x) { return x < 0 ? -x : x; }
inline int jround(float x) { return jI(std::floor((double)x + 0.5)); }
inline int64_t jround(double x) { return jL(std::floor(x + 0.5)); }

// Seeded stand-in for Math.random(): every machine in a networked race uses
// the same seed, so the physics' random wobble comes out identical.
struct JRandom;
void jrandom_seed(int64_t seed);
double jrandom();

// java.util.Random (48-bit LCG), exact.
struct JRandom {
  int64_t seed;
  explicit JRandom(int64_t s) : seed((s ^ 0x5DEECE66DLL) & ((1LL << 48) - 1)) {}
  int next(int bits) {
    seed = (int64_t)(((uint64_t)seed * 0x5DEECE66DULL + 0xBULL) & ((1ULL << 48) - 1));
    return (int)(seed >> (48 - bits));
  }
  double nextDouble() { return (((int64_t)next(26) << 27) + next(27)) * (1.0 / (1LL << 53)); }
  float nextFloat() { return next(24) / (float)(1 << 24); }
  int nextInt() { return next(32); }
  int nextInt(int bound) {
    if ((bound & -bound) == bound) return (int)(((int64_t)bound * (int64_t)next(31)) >> 31);
    int bits, val;
    do { bits = next(31); val = bits % bound; } while (bits - val + (bound - 1) < 0);
    return val;
  }
};

// ---- strings (Java null String = a sentinel value)
extern const std::string JNULL;
inline bool jisnull(const std::string &s) { return s.size() == JNULL.size() && s == JNULL; }
inline bool jstarts(const std::string &s, const std::string &p) { return s.size() >= p.size() && s.compare(0, p.size(), p) == 0; }
inline bool jends(const std::string &s, const std::string &p) { return s.size() >= p.size() && s.compare(s.size() - p.size(), p.size(), p) == 0; }
inline std::string jtrim(const std::string &s) {
  size_t a = 0, b = s.size();
  while (a < b && (unsigned char)s[a] <= ' ') a++;
  while (b > a && (unsigned char)s[b - 1] <= ' ') b--;
  return s.substr(a, b - a);
}
inline int jcharAt(const std::string &s, int i) {
  if (i < 0 || (size_t)i >= s.size()) throw JIndexError();
  return (unsigned char)s[i];
}
inline int jindexOf(const std::string &s, const std::string &x, int from = 0) {
  if (from < 0) from = 0;
  size_t r = s.find(x, from);
  return r == std::string::npos ? -1 : (int)r;
}
inline int jindexOf(const std::string &s, int ch, int from = 0) { return jindexOf(s, std::string(1, (char)ch), from); }
inline std::string jsubstr(const std::string &s, int a) {
  if (a < 0 || (size_t)a > s.size()) throw JIndexError();
  return s.substr(a);
}
inline std::string jsubstr(const std::string &s, int a, int b) {
  if (a < 0 || b > (int)s.size() || a > b) throw JIndexError();
  return s.substr(a, b - a);
}
inline std::string jlower(std::string s) { for (auto &c : s) c = (char)std::tolower((unsigned char)c); return s; }
inline std::string jupper(std::string s) { for (auto &c : s) c = (char)std::toupper((unsigned char)c); return s; }
inline bool jequalsIC(const std::string &a, const std::string &b) { return jlower(a) == jlower(b); }
inline std::string jreplace(const std::string &s, const std::string &a, const std::string &b) {
  if (a.empty()) return s;
  std::string out;
  size_t i = 0, j;
  while ((j = s.find(a, i)) != std::string::npos) { out += s.substr(i, j - i); out += b; i = j + a.size(); }
  return out + s.substr(i);
}
inline std::string jreplace(const std::string &s, int a, int b) { return jreplace(s, std::string(1, (char)a), std::string(1, (char)b)); }
inline std::string jstr(const std::string &s) { return jisnull(s) ? "null" : s; }
inline std::string jstr(const char *s) { return s; }
inline std::string jchr(int c) { return std::string(1, (char)c); }
inline std::string jstr(const std::exception &e) { return e.what(); }
inline std::string jstr(int v) { return std::to_string(v); }
inline std::string jstr(int64_t v) { return std::to_string(v); }
inline std::string jstr(bool v) { return v ? "true" : "false"; }
inline std::string jstr(double v) { char b[64]; snprintf(b, sizeof b, "%g", v); return b; }
inline std::string jstr(float v) { return jstr((double)v); }
template <class A, class B> inline std::string jcat(const A &a, const B &b) { return jstr(a) + jstr(b); }

inline int jParseInt(const std::string &s0) {
  const std::string &s = s0;
  if (s.empty() || jisnull(s)) throw JFormatError(s);
  size_t i = (s[0] == '-' || s[0] == '+') ? 1 : 0;
  if (i == s.size()) throw JFormatError(s);
  int64_t v = 0;
  for (size_t k = i; k < s.size(); k++) {
    if (s[k] < '0' || s[k] > '9') throw JFormatError(s);
    v = v * 10 + (s[k] - '0');
    if (v > 2147483648LL) throw JFormatError(s);
  }
  if (s[0] == '-') v = -v;
  if (v > INT32_MAX || v < INT32_MIN) throw JFormatError(s);
  return (int)v;
}
inline float jParseFloat(const std::string &s0) {
  std::string s = jtrim(s0);
  if (!s.empty() && (s.back() == 'f' || s.back() == 'F' || s.back() == 'd' || s.back() == 'D')) s.pop_back();
  if (s.empty()) throw JFormatError(s0);
  char *end = nullptr;
  double v = strtod(s.c_str(), &end);
  if (!end || *end) throw JFormatError(s0);
  return (float)v;
}

// java.awt.Color subset
struct Color {
  int r = 0, g = 0, b = 0;
  Color() {}
  Color(int R, int G, int B) : r(R & 255), g(G & 255), b(B & 255) {}
  int getRed() const { return r; }
  int getGreen() const { return g; }
  int getBlue() const { return b; }
  static Color getHSBColor(float hue, float saturation, float brightness) {
    int R = 0, G = 0, B = 0;
    if (saturation == 0) {
      R = G = B = (int)(brightness * 255.0f + 0.5f);
    } else {
      float h = (hue - (float)std::floor(hue)) * 6.0f;
      float f = h - (float)std::floor(h);
      float p = brightness * (1.0f - saturation);
      float q = brightness * (1.0f - saturation * f);
      float t = brightness * (1.0f - saturation * (1.0f - f));
      switch ((int)h) {
        case 0: R = (int)(brightness * 255.0f + 0.5f); G = (int)(t * 255.0f + 0.5f); B = (int)(p * 255.0f + 0.5f); break;
        case 1: R = (int)(q * 255.0f + 0.5f); G = (int)(brightness * 255.0f + 0.5f); B = (int)(p * 255.0f + 0.5f); break;
        case 2: R = (int)(p * 255.0f + 0.5f); G = (int)(brightness * 255.0f + 0.5f); B = (int)(t * 255.0f + 0.5f); break;
        case 3: R = (int)(p * 255.0f + 0.5f); G = (int)(q * 255.0f + 0.5f); B = (int)(brightness * 255.0f + 0.5f); break;
        case 4: R = (int)(t * 255.0f + 0.5f); G = (int)(p * 255.0f + 0.5f); B = (int)(brightness * 255.0f + 0.5f); break;
        case 5: R = (int)(brightness * 255.0f + 0.5f); G = (int)(p * 255.0f + 0.5f); B = (int)(q * 255.0f + 0.5f); break;
      }
    }
    return Color(R, G, B);
  }
  static Arr<float> RGBtoHSB(int r, int g, int b, Arr<float> out) {
    if (out.isNull()) out = Arr<float>(3);
    int cmax = std::max({r, g, b}), cmin = std::min({r, g, b});
    float brightness = cmax / 255.0f;
    float saturation = cmax != 0 ? (float)(cmax - cmin) / (float)cmax : 0;
    float hue;
    if (saturation == 0) hue = 0;
    else {
      float redc = (float)(cmax - r) / (float)(cmax - cmin);
      float greenc = (float)(cmax - g) / (float)(cmax - cmin);
      float bluec = (float)(cmax - b) / (float)(cmax - cmin);
      if (r == cmax) hue = bluec - greenc;
      else if (g == cmax) hue = 2.0f + redc - bluec;
      else hue = 4.0f + greenc - redc;
      hue /= 6.0f;
      if (hue < 0) hue += 1.0f;
    }
    out[0] = hue; out[1] = saturation; out[2] = brightness;
    return out;
  }
};

// DataInputStream over model / stage text, read line by line.
struct DataInputStream {
  std::vector<std::string> lines;
  size_t i = 0;
  explicit DataInputStream(const std::string &text) {
    size_t a = 0;
    for (size_t k = 0; k < text.size(); k++) {
      if (text[k] == '\n' || text[k] == '\r') {
        lines.push_back(text.substr(a, k - a));
        if (text[k] == '\r' && k + 1 < text.size() && text[k + 1] == '\n') k++;
        a = k + 1;
      }
    }
    if (a < text.size()) lines.push_back(text.substr(a));
  }
  std::string readLine() { return i < lines.size() ? lines[i++] : JNULL; }
  void close() {}
};

// Objects the game logic allocates go to the current arena (a race's stage
// pieces, cars, planes) and are destroyed together when it is reset; with no
// arena set (world models, car definitions) they live for the whole session.
struct JArena {
  std::vector<std::pair<void *, void (*)(void *)>> objs;
  void reset() {
    for (auto it = objs.rbegin(); it != objs.rend(); ++it) it->second(it->first);
    objs.clear();
  }
  ~JArena() { reset(); }
};
inline JArena *g_arena = nullptr;
template <class T, class... A>
T *jnew(A &&...a) {
  T *o = new T(std::forward<A>(a)...);
  if (g_arena) g_arena->objs.push_back({o, [](void *p) { delete (T *)p; }});
  return o;
}
