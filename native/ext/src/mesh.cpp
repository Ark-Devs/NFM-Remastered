#include "mesh.h"
#include <algorithm>
#include <cmath>
#include <cstring>
#include <regex>
#include <unordered_map>

static float srgb_to_linear(float v) {
  v /= 255.0f;
  return v <= 0.04045f ? v / 12.92f : std::pow((v + 0.055f) / 1.055f, 2.4f);
}

// ---- triangulation: ear clipping on the polygon's dominant plane
static bool point_in_tri(const float *a, const float *b, const float *c, const float *p) {
  auto cross = [](const float *o, const float *u, const float *v) { return (u[0] - o[0]) * (v[1] - o[1]) - (u[1] - o[1]) * (v[0] - o[0]); };
  float d1 = cross(p, a, b), d2 = cross(p, b, c), d3 = cross(p, c, a);
  bool neg = d1 < 0 || d2 < 0 || d3 < 0, pos = d1 > 0 || d2 > 0 || d3 > 0;
  return !(neg && pos);
}

static void triangulate(const Arr<int> &xs, const Arr<int> &ys, const Arr<int> &zs, int n, std::vector<int> &tris) {
  tris.clear();
  if (n < 3) return;
  if (n == 3) { tris = {0, 1, 2}; return; }
  double nx = 0, ny = 0, nz = 0;
  for (int i = 0; i < n; i++) {
    int j = (i + 1) % n;
    nx += (double)(ys[i] - ys[j]) * (zs[i] + zs[j]);
    ny += (double)(zs[i] - zs[j]) * (xs[i] + xs[j]);
    nz += (double)(xs[i] - xs[j]) * (ys[i] + ys[j]);
  }
  double ax = std::fabs(nx), ay = std::fabs(ny), az = std::fabs(nz);
  std::vector<float> pts(n * 2);
  double sign;
  for (int i = 0; i < n; i++) {
    if (ax >= ay && ax >= az) { pts[i * 2] = (float)ys[i]; pts[i * 2 + 1] = (float)zs[i]; }
    else if (ay >= az) { pts[i * 2] = (float)zs[i]; pts[i * 2 + 1] = (float)xs[i]; }
    else { pts[i * 2] = (float)xs[i]; pts[i * 2 + 1] = (float)ys[i]; }
  }
  // orientation of the projected outline
  double area = 0;
  for (int i = 0; i < n; i++) { int j = (i + 1) % n; area += (double)pts[i * 2] * pts[j * 2 + 1] - (double)pts[j * 2] * pts[i * 2 + 1]; }
  sign = area >= 0 ? 1 : -1;
  std::vector<int> idx(n);
  for (int i = 0; i < n; i++) idx[i] = i;
  int guard = 0;
  while (idx.size() > 3 && guard++ < n * n) {
    bool cut = false;
    int m = (int)idx.size();
    for (int k = 0; k < m; k++) {
      int i0 = idx[(k + m - 1) % m], i1 = idx[k], i2 = idx[(k + 1) % m];
      const float *a = &pts[i0 * 2], *b = &pts[i1 * 2], *c = &pts[i2 * 2];
      double cr = ((double)b[0] - a[0]) * ((double)c[1] - a[1]) - ((double)b[1] - a[1]) * ((double)c[0] - a[0]);
      if (cr * sign <= 0) continue; // reflex or degenerate
      bool inside = false;
      for (int q = 0; q < m && !inside; q++) {
        int iq = idx[q];
        if (iq == i0 || iq == i1 || iq == i2) continue;
        const float *p = &pts[iq * 2];
        if ((p[0] == a[0] && p[1] == a[1]) || (p[0] == b[0] && p[1] == b[1]) || (p[0] == c[0] && p[1] == c[1])) continue;
        if (point_in_tri(a, b, c, p)) inside = true;
      }
      if (inside) continue;
      tris.push_back(i0); tris.push_back(i1); tris.push_back(i2);
      idx.erase(idx.begin() + k);
      cut = true;
      break;
    }
    if (!cut) {
      // only degenerate corners left (zero-width spikes around cut-outs): drop one
      bool dropped = false;
      for (int k = 0; k < m; k++) {
        int i0 = idx[(k + m - 1) % m], i1 = idx[k], i2 = idx[(k + 1) % m];
        const float *a = &pts[i0 * 2], *b = &pts[i1 * 2], *c = &pts[i2 * 2];
        double cr = ((double)b[0] - a[0]) * ((double)c[1] - a[1]) - ((double)b[1] - a[1]) * ((double)c[0] - a[0]);
        if (std::fabs(cr) < 1e-6) { idx.erase(idx.begin() + k); dropped = true; break; }
      }
      if (!dropped) break;
    }
  }
  if (idx.size() == 3) {
    const float *a = &pts[idx[0] * 2], *b = &pts[idx[1] * 2], *c = &pts[idx[2] * 2];
    double cr = ((double)b[0] - a[0]) * ((double)c[1] - a[1]) - ((double)b[1] - a[1]) * ((double)c[0] - a[0]);
    if (std::fabs(cr) > 1e-9) { tris.push_back(idx[0]); tris.push_back(idx[1]); tris.push_back(idx[2]); }
  }
  if (tris.empty()) for (int i = 1; i < n - 1; i++) { tris.push_back(0); tris.push_back(i); tris.push_back(i + 1); }
}

// ---- surfaces
static int classify_car(Plane *p) {
  if (p->glass == 1) return PART_GLASS;
  if (p->light == 1) return PART_LIGHT_F;
  if (p->light == 2) return PART_LIGHT_B;
  return PART_BODY;
}

static void car_surface(Plane *p, float s[3]) {
  if (p->colnum == 1 || p->colnum == 2) { s[0] = 0.42f; s[1] = 0.22f; s[2] = 0.55f; return; }
  int r = p->c[0], g = p->c[1], b = p->c[2];
  float lum = (r * 0.3f + g * 0.59f + b * 0.11f) / 255.0f;
  if (lum < 0.13f) { s[0] = 0.85f; s[1] = 0; s[2] = 0; return; }
  if (std::abs(r - g) < 12 && std::abs(g - b) < 12 && lum > 0.55f) { s[0] = 0.35f; s[1] = 0.7f; s[2] = 0.1f; return; }
  s[0] = 0.55f; s[1] = 0.1f; s[2] = 0.3f;
}

// kind: 0 painted, 1 asphalt, 2 rock/dirt, 3 foliage, 4 metal, 5 road paint, 6 concrete
static void stage_surface(Plane *p, const std::string &name, float s[3]) {
  auto starts = [&](const char *x) { return name.rfind(x, 0) == 0; };
  auto has = [&](const char *x) { return name.find(x) != std::string::npos; };
  if (starts("tree") || starts("cac")) { s[0] = 0.85f; s[1] = 0; s[2] = 3; return; }
  if (p->glass == 2 || has("pile") || has("offroad") || has("offhill") || has("offbump") || has("offturn") || has("hpground") || has("offramp") || has("sofframp") || has("offcheck")) { s[0] = 0.95f; s[1] = 0; s[2] = 2; return; }
  int r = p->c[0], g = p->c[1], b = p->c[2];
  bool grey = std::abs(r - g) < 12 && std::abs(g - b) < 12;
  if (p->road) {
    if ((grey && r > 165) || (r > 180 && g > 110 && g < 170 && b < 60)) { s[0] = 0.6f; s[1] = 0; s[2] = 5; return; }
    s[0] = 0.85f; s[1] = 0; s[2] = 1; return;
  }
  if ((has("rail") || has("fence") || has("pipe") || has("tube") || has("crane") || has("tower")) && (grey || r < 60)) { s[0] = 0.4f; s[1] = 0.75f; s[2] = 4; return; }
  if (grey) { s[0] = 0.88f; s[1] = 0; s[2] = 6; return; }
  s[0] = 0.7f; s[1] = 0.02f; s[2] = 0;
}

// Stacked coplanar decals (markings on tarmac) z-fight in a depth buffer: lift
// each overlapping smaller polygon a little off the one it sits on.
static std::vector<float> decal_lifts(ContO *c) {
  struct Info { bool ok = false; double n[3], d, area, bb[6]; int layer = 0; };
  std::vector<Info> info(c->npl);
  double cx = 0, cy = 0, cz = 0; int cn = 0;
  for (int pi = 0; pi < c->npl; pi++) {
    Plane *p = c->p[pi];
    if (!p || p->n < 3) continue;
    double nx = 0, ny = 0, nz = 0;
    double bb[6] = {1e30, -1e30, 1e30, -1e30, 1e30, -1e30};
    for (int v = 0; v < p->n; v++) {
      int w = (v + 1) % p->n;
      nx += (double)(p->oy[v] - p->oy[w]) * (p->oz[v] + p->oz[w]);
      ny += (double)(p->oz[v] - p->oz[w]) * (p->ox[v] + p->ox[w]);
      nz += (double)(p->ox[v] - p->ox[w]) * (p->oy[v] + p->oy[w]);
      bb[0] = std::min(bb[0], (double)p->ox[v]); bb[1] = std::max(bb[1], (double)p->ox[v]);
      bb[2] = std::min(bb[2], (double)p->oy[v]); bb[3] = std::max(bb[3], (double)p->oy[v]);
      bb[4] = std::min(bb[4], (double)p->oz[v]); bb[5] = std::max(bb[5], (double)p->oz[v]);
      cx += p->ox[v]; cy += p->oy[v]; cz += p->oz[v]; cn++;
    }
    double l = std::sqrt(nx * nx + ny * ny + nz * nz);
    if (l < 1e-6) continue;
    Info &I = info[pi];
    I.ok = true;
    I.n[0] = nx / l; I.n[1] = ny / l; I.n[2] = nz / l;
    I.d = I.n[0] * p->ox[0] + I.n[1] * p->oy[0] + I.n[2] * p->oz[0];
    I.area = l / 2;
    memcpy(I.bb, bb, sizeof bb);
  }
  if (!cn) return {};
  cx /= cn; cy /= cn; cz /= cn;
  std::vector<int> order;
  for (int i = 0; i < c->npl; i++) if (info[i].ok) order.push_back(i);
  std::stable_sort(order.begin(), order.end(), [&](int a, int b) { return info[a].area > info[b].area; });
  bool any = false;
  for (size_t oi = 0; oi < order.size(); oi++) {
    Info &A = info[order[oi]];
    for (size_t oj = 0; oj < oi; oj++) {
      Info &B = info[order[oj]];
      double dot = A.n[0] * B.n[0] + A.n[1] * B.n[1] + A.n[2] * B.n[2];
      if (std::fabs(dot) < 0.999 || std::fabs(A.d - (dot < 0 ? -1 : 1) * B.d) > 1) continue;
      bool overlap = true;
      for (int k = 0; k < 3 && overlap; k++) {
        bool thin = A.n[k] * A.n[k] > 0.99;
        if (std::min(A.bb[k * 2 + 1], B.bb[k * 2 + 1]) - std::max(A.bb[k * 2], B.bb[k * 2]) < (thin ? -1 : 1)) overlap = false;
      }
      if (overlap && B.layer + 1 > A.layer) { A.layer = B.layer + 1; any = true; }
    }
  }
  if (!any) return {};
  std::vector<float> lifts(c->npl * 3, 0.0f);
  for (int pi = 0; pi < c->npl; pi++) {
    Info &A = info[pi];
    if (!A.ok || !A.layer) continue;
    double nx = A.n[0], ny = A.n[1], nz = A.n[2];
    if (std::fabs(ny) > 0.7) { if (ny > 0) { nx = -nx; ny = -ny; nz = -nz; } } // NFM y is down
    else {
      double mx = (A.bb[0] + A.bb[1]) / 2 - cx, my = (A.bb[2] + A.bb[3]) / 2 - cy, mz = (A.bb[4] + A.bb[5]) / 2 - cz;
      if (nx * mx + ny * my + nz * mz < 0) { nx = -nx; ny = -ny; nz = -nz; }
    }
    double s = A.layer * 1.5;
    lifts[pi * 3] = (float)(nx * s); lifts[pi * 3 + 1] = (float)(ny * s); lifts[pi * 3 + 2] = (float)(nz * s);
  }
  return lifts;
}

// ---- normals: average face normals of coincident vertices across soft edges
static void crease_normals(std::vector<float> &v, float creaseDeg) {
  size_t nv = v.size() / VERT_FLOATS;
  // face normal per triangle (vertices are in triangle order)
  std::vector<float> fn(nv / 3 * 3);
  for (size_t t = 0; t < nv / 3; t++) {
    float *a = &v[(t * 3) * VERT_FLOATS], *b = &v[(t * 3 + 1) * VERT_FLOATS], *c = &v[(t * 3 + 2) * VERT_FLOATS];
    float e1[3] = {b[0] - a[0], b[1] - a[1], b[2] - a[2]}, e2[3] = {c[0] - a[0], c[1] - a[1], c[2] - a[2]};
    float n[3] = {e1[1] * e2[2] - e1[2] * e2[1], e1[2] * e2[0] - e1[0] * e2[2], e1[0] * e2[1] - e1[1] * e2[0]};
    float l = std::sqrt(n[0] * n[0] + n[1] * n[1] + n[2] * n[2]);
    if (l < 1e-12f) { n[0] = 0; n[1] = 1; n[2] = 0; l = 1; }
    // triangles are clockwise (Godot front faces), so the outward normal is c x b
    fn[t * 3] = -n[0] / l; fn[t * 3 + 1] = -n[1] / l; fn[t * 3 + 2] = -n[2] / l;
  }
  std::unordered_map<int64_t, std::vector<int>> groups;
  auto key = [&](const float *p) {
    int64_t x = (int64_t)std::lround(p[0] * 2000), y = (int64_t)std::lround(p[1] * 2000), z = (int64_t)std::lround(p[2] * 2000);
    return (x * 73856093) ^ (y * 19349663) ^ (z * 83492791);
  };
  for (size_t i = 0; i < nv; i++) groups[key(&v[i * VERT_FLOATS])].push_back((int)i);
  float cosT = std::cos(creaseDeg * 3.14159265f / 180.0f);
  for (auto &[k, members] : groups) {
    for (int i : members) {
      const float *ni = &fn[(i / 3) * 3];
      float s[3] = {0, 0, 0};
      for (int j : members) {
        const float *nj = &fn[(j / 3) * 3];
        float d = ni[0] * nj[0] + ni[1] * nj[1] + ni[2] * nj[2];
        if (std::fabs(d) >= cosT) { float sg = d < 0 ? -1.0f : 1.0f; s[0] += nj[0] * sg; s[1] += nj[1] * sg; s[2] += nj[2] * sg; }
      }
      float l = std::sqrt(s[0] * s[0] + s[1] * s[1] + s[2] * s[2]);
      float *out = &v[i * VERT_FLOATS + 3];
      if (l < 1e-6f) { out[0] = ni[0]; out[1] = ni[1]; out[2] = ni[2]; }
      else { out[0] = s[0] / l; out[1] = s[1] / l; out[2] = s[2] / l; }
    }
  }
}

void build_conto_mesh(ContO *c, bool car, const char *modelName, const int snap[3], MeshOut &out) {
  for (auto &p : out.parts) p.clear();
  std::string name = modelName ? modelName : "";
  std::vector<float> lifts = car ? std::vector<float>() : decal_lifts(c);
  // centroid for "face outward" orientation
  double cx = 0, cy = 0, cz = 0; int cn = 0;
  for (int pi = 0; pi < c->npl; pi++) {
    Plane *p = c->p[pi];
    if (!p || p->n < 3 || (car && p->wz != 0)) continue;
    for (int v = 0; v < p->n; v++) { cx += p->ox[v]; cy += p->oy[v]; cz += p->oz[v]; cn++; }
  }
  if (cn) { cx /= cn; cy /= cn; cz /= cn; }
  std::vector<int> tris;
  for (int pi = 0; pi < c->npl; pi++) {
    Plane *p = c->p[pi];
    if (!p || p->n < 3) continue;
    if (car && p->wz != 0) continue; // wheels are drawn separately
    int part = car ? classify_car(p) : (p->gr == -13 ? PART_FINISH : PART_BODY);
    float col[3];
    if (car) {
      for (int k = 0; k < 3; k++) {
        float un = (float)p->c[k] / std::max(0.2f, 1.0f + snap[k] / 100.0f);
        col[k] = srgb_to_linear(std::clamp(un, 0.0f, 255.0f));
      }
    } else for (int k = 0; k < 3; k++) col[k] = srgb_to_linear((float)std::clamp(p->c[k], 0, 255));
    float surf[3];
    if (car) car_surface(p, surf); else stage_surface(p, name, surf);
    float lx = lifts.empty() ? 0 : lifts[pi * 3], ly = lifts.empty() ? 0 : lifts[pi * 3 + 1], lz = lifts.empty() ? 0 : lifts[pi * 3 + 2];
    triangulate(p->ox, p->oy, p->oz, p->n, tris);
    auto &dst = out.parts[part];
    for (size_t t = 0; t + 2 < tris.size(); t += 3) {
      int ia = tris[t], ib = tris[t + 1], ic = tris[t + 2];
      float P[3][3];
      int ids[3] = {ia, ib, ic};
      for (int k = 0; k < 3; k++) {
        int i = ids[k];
        P[k][0] = (p->ox[i] + lx) * S; P[k][1] = -(p->oy[i] + ly) * S; P[k][2] = -(p->oz[i] + lz) * S;
      }
      // face away from the model centre (flat stage faces point up)
      float e1[3] = {P[1][0] - P[0][0], P[1][1] - P[0][1], P[1][2] - P[0][2]}, e2[3] = {P[2][0] - P[0][0], P[2][1] - P[0][1], P[2][2] - P[0][2]};
      float n[3] = {e1[1] * e2[2] - e1[2] * e2[1], e1[2] * e2[0] - e1[0] * e2[2], e1[0] * e2[1] - e1[1] * e2[0]};
      float l = std::sqrt(n[0] * n[0] + n[1] * n[1] + n[2] * n[2]);
      if (l < 1e-12f) continue;
      bool flip;
      if (!car && std::fabs(n[1]) / l > 0.7f) flip = n[1] < 0;
      else {
        float fx = (P[0][0] + P[1][0] + P[2][0]) / 3 - (float)(cx * S), fy = (P[0][1] + P[1][1] + P[2][1]) / 3 - (float)(-cy * S), fz = (P[0][2] + P[1][2] + P[2][2]) / 3 - (float)(-cz * S);
        flip = n[0] * fx + n[1] * fy + n[2] * fz < 0;
      }
      // Godot's front faces wind clockwise
      int order[3] = {0, 2, 1};
      if (flip) { order[1] = 1; order[2] = 2; }
      for (int k : order) {
        dst.insert(dst.end(), {P[k][0], P[k][1], P[k][2], 0, 0, 0, col[0], col[1], col[2], surf[0], surf[1], surf[2]});
      }
    }
  }
  for (int k = 0; k < PART_COUNT; k++) if (!out.parts[k].empty()) crease_normals(out.parts[k], car ? 38.0f : 25.0f);
  // glass and lamps lie on the body panels (the original painted them over
  // the paint): lift them a centimetre so they always win the depth test
  if (car) {
    for (int k : {PART_GLASS, PART_LIGHT_F, PART_LIGHT_B}) {
      auto &v = out.parts[k];
      for (size_t i = 0; i < v.size(); i += VERT_FLOATS) for (int a = 0; a < 3; a++) v[i + a] += v[i + 3 + a] * 0.012f;
    }
  }
}

std::vector<float> pack_mesh(const MeshOut &m) {
  std::vector<float> o;
  int np = 0;
  for (auto &p : m.parts) if (!p.empty()) np++;
  o.push_back((float)np);
  for (int k = 0; k < PART_COUNT; k++) {
    if (m.parts[k].empty()) continue;
    o.push_back((float)k);
    o.push_back((float)(m.parts[k].size() / VERT_FLOATS));
    o.insert(o.end(), m.parts[k].begin(), m.parts[k].end());
  }
  return o;
}

// NFM rotation: roll (xy), pitch (zy), yaw (xz), conjugated into Godot space.
void nfm_transform(double x, double y, double z, double xz, double xy, double zy, float out[12]) {
  const double D = 3.141592653589793 / 180.0;
  double cyw = std::cos(xz * D), syw = std::sin(xz * D);
  double cp = std::cos(zy * D), sp = std::sin(zy * D);
  double cr = std::cos(xy * D), sr = std::sin(xy * D);
  double Ry[3][3] = {{cyw, 0, -syw}, {0, 1, 0}, {syw, 0, cyw}};
  double Rp[3][3] = {{1, 0, 0}, {0, cp, -sp}, {0, sp, cp}};
  double Rr[3][3] = {{cr, -sr, 0}, {sr, cr, 0}, {0, 0, 1}};
  double A[3][3], R[3][3];
  for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) { A[i][j] = 0; for (int k = 0; k < 3; k++) A[i][j] += Ry[i][k] * Rp[k][j]; }
  for (int i = 0; i < 3; i++) for (int j = 0; j < 3; j++) { R[i][j] = 0; for (int k = 0; k < 3; k++) R[i][j] += A[i][k] * Rr[k][j]; }
  const double c[3] = {1, -1, -1};
  // columns of the basis
  for (int j = 0; j < 3; j++) for (int i = 0; i < 3; i++) out[j * 3 + i] = (float)(R[i][j] * c[i] * c[j]);
  out[9] = (float)(x * S); out[10] = (float)(-y * S); out[11] = (float)(-z * S);
}
