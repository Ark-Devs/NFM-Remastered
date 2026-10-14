// Crease-aware Loop subdivision for the low-poly car models.
//
// Every output vertex is stored as a "stencil": a weighted sum of welded input
// vertices. When the physics dents the original model we re-evaluate the
// stencils, so the smooth surface deforms exactly like the original polygons.

function edgeKey(a, b) { return a < b ? a * 1048576 + b : b * 1048576 + a; }

// One Loop step on a triangle mesh whose vertices are stencils over base ids.
//   stencils: array of Map(baseId -> weight)
//   faces:    flat [a,b,c,...], faceTag: per-face tag (input polygon)
//   creases:  Set of edge keys that must stay sharp
function loopStep(stencils, faces, faceTag, creases) {
  const nf = faces.length / 3;
  const edges = new Map(); // key -> { a, b, opp: [] }
  for (let f = 0; f < nf; f++) {
    for (let k = 0; k < 3; k++) {
      const a = faces[f * 3 + k], b = faces[f * 3 + ((k + 1) % 3)], c = faces[f * 3 + ((k + 2) % 3)];
      const key = edgeKey(a, b);
      let e = edges.get(key);
      if (!e) { e = { a, b, opp: [] }; edges.set(key, e); }
      e.opp.push(c);
    }
  }
  const isCrease = (key, e) => creases.has(key) || e.opp.length !== 2;
  // vertex neighbourhoods
  const nv = stencils.length;
  const nbrs = Array.from({ length: nv }, () => []);
  const creaseNbrs = Array.from({ length: nv }, () => []);
  // Vertices on open edges (where a panel isn't stitched to its neighbour, e.g.
  // a corner landing mid-way along the next panel's edge) must not move, or the
  // neighbouring panel no longer meets it and a crack opens.
  const openV = new Uint8Array(nv);
  for (const [key, e] of edges) {
    nbrs[e.a].push(e.b); nbrs[e.b].push(e.a);
    if (isCrease(key, e)) { creaseNbrs[e.a].push(e.b); creaseNbrs[e.b].push(e.a); }
    if (e.opp.length === 1) { openV[e.a] = 1; openV[e.b] = 1; }
  }
  const mix = (terms) => {
    const out = new Map();
    for (const [s, w] of terms) for (const [id, sw] of s) out.set(id, (out.get(id) || 0) + sw * w);
    return out;
  };
  // new positions for existing vertices
  const out = new Array(nv);
  for (let v = 0; v < nv; v++) {
    if (openV[v]) { out[v] = stencils[v]; continue; }
    const c = creaseNbrs[v].length;
    if (c === 0) {
      const n = nbrs[v].length;
      if (n < 3) { out[v] = stencils[v]; continue; }
      const beta = n === 3 ? 3 / 16 : 3 / (8 * n);
      out[v] = mix([[stencils[v], 1 - n * beta], ...nbrs[v].map((u) => [stencils[u], beta])]);
    } else if (c === 2) {
      out[v] = mix([[stencils[v], 0.75], [stencils[creaseNbrs[v][0]], 0.125], [stencils[creaseNbrs[v][1]], 0.125]]);
    } else out[v] = stencils[v]; // corner
  }
  // edge points
  const edgeVert = new Map();
  const newCreases = new Set();
  for (const [key, e] of edges) {
    const id = out.length;
    if (isCrease(key, e)) {
      out.push(mix([[stencils[e.a], 0.5], [stencils[e.b], 0.5]]));
    } else {
      out.push(mix([[stencils[e.a], 0.375], [stencils[e.b], 0.375], [stencils[e.opp[0]], 0.125], [stencils[e.opp[1]], 0.125]]));
    }
    edgeVert.set(key, id);
    if (isCrease(key, e)) { newCreases.add(edgeKey(e.a, id)); newCreases.add(edgeKey(id, e.b)); }
  }
  const nfaces = [];
  const ntag = [];
  for (let f = 0; f < nf; f++) {
    const a = faces[f * 3], b = faces[f * 3 + 1], c = faces[f * 3 + 2];
    const ab = edgeVert.get(edgeKey(a, b)), bc = edgeVert.get(edgeKey(b, c)), ca = edgeVert.get(edgeKey(c, a));
    nfaces.push(a, ab, ca, ab, b, bc, ca, bc, c, ab, bc, ca);
    const t = faceTag[f];
    ntag.push(t, t, t, t);
  }
  return { stencils: out, faces: nfaces, faceTag: ntag, creases: newCreases };
}

// pos: flat positions of the per-polygon vertices; tris: triangles over them;
// tag(vertex) -> polygon id; sameLook(polyA, polyB) -> true if the two polygons
// share a material (colour) so they may be smoothed together.
export function subdivide({ pos, tris, polyOf, sameLook, levels, creaseDeg = 28 }) {
  // 1. weld coincident vertices
  const welded = [];
  const members = [];
  const weldOf = new Int32Array(pos.length / 3);
  const map = new Map();
  for (let i = 0; i < pos.length / 3; i++) {
    const key = `${Math.round(pos[i * 3] * 1000)},${Math.round(pos[i * 3 + 1] * 1000)},${Math.round(pos[i * 3 + 2] * 1000)}`;
    let w = map.get(key);
    if (w === undefined) { w = welded.length; map.set(key, w); welded.push([pos[i * 3], pos[i * 3 + 1], pos[i * 3 + 2]]); members.push([]); }
    members[w].push(i);
    weldOf[i] = w;
  }
  // 2. faces over welded ids, dropping degenerate triangles
  let faces = [];
  let faceTag = [];
  const faceN = [];
  for (let t = 0; t < tris.length; t += 3) {
    const a = weldOf[tris[t]], b = weldOf[tris[t + 1]], c = weldOf[tris[t + 2]];
    if (a === b || b === c || a === c) continue;
    const pa = welded[a], pb = welded[b], pc = welded[c];
    const e1 = [pb[0] - pa[0], pb[1] - pa[1], pb[2] - pa[2]];
    const e2 = [pc[0] - pa[0], pc[1] - pa[1], pc[2] - pa[2]];
    const n = [e1[1] * e2[2] - e1[2] * e2[1], e1[2] * e2[0] - e1[0] * e2[2], e1[0] * e2[1] - e1[1] * e2[0]];
    const l = Math.hypot(...n);
    if (l < 1e-9) continue;
    faces.push(a, b, c);
    faceTag.push(polyOf(tris[t]));
    faceN.push(n.map((x) => x / l));
  }
  // 3. creases: open/non-manifold edges, material boundaries, sharp folds
  const cosC = Math.cos((creaseDeg * Math.PI) / 180);
  const edgeFaces = new Map();
  for (let f = 0; f < faces.length / 3; f++) {
    for (let k = 0; k < 3; k++) {
      const key = edgeKey(faces[f * 3 + k], faces[f * 3 + ((k + 1) % 3)]);
      if (!edgeFaces.has(key)) edgeFaces.set(key, []);
      edgeFaces.get(key).push(f);
    }
  }
  let creases = new Set();
  for (const [key, fs] of edgeFaces) {
    if (fs.length !== 2) { creases.add(key); continue; }
    const [f1, f2] = fs;
    const n1 = faceN[f1], n2 = faceN[f2];
    const d = n1[0] * n2[0] + n1[1] * n2[1] + n1[2] * n2[2];
    if (faceTag[f1] !== faceTag[f2] && (!sameLook(faceTag[f1], faceTag[f2]) || d < cosC)) creases.add(key);
  }
  // 4. subdivide
  let stencils = welded.map((_, i) => new Map([[i, 1]]));
  for (let l = 0; l < levels; l++) {
    const r = loopStep(stencils, faces, faceTag, creases);
    stencils = r.stencils; faces = r.faces; faceTag = r.faceTag; creases = r.creases;
  }
  // flatten stencils
  const offs = new Int32Array(stencils.length + 1);
  let total = 0;
  stencils.forEach((s, i) => { offs[i] = total; total += s.size; });
  offs[stencils.length] = total;
  const ids = new Int32Array(total);
  const ws = new Float32Array(total);
  stencils.forEach((s, i) => { let k = offs[i]; for (const [id, w] of s) { ids[k] = id; ws[k] = w; k++; } });
  return { members, weldCount: welded.length, offs, ids, ws, faces, faceTag, vertCount: stencils.length };
}

// Evaluate stencils: weldedPos from the members' current positions, then
// every subdivided vertex as a weighted sum.
export function evalStencils(sub, memberPos, out) {
  const wp = sub._wp || (sub._wp = new Float64Array(sub.weldCount * 3));
  for (let w = 0; w < sub.weldCount; w++) {
    const m = sub.members[w];
    let x = 0, y = 0, z = 0;
    for (const i of m) { x += memberPos[i * 3]; y += memberPos[i * 3 + 1]; z += memberPos[i * 3 + 2]; }
    wp[w * 3] = x / m.length; wp[w * 3 + 1] = y / m.length; wp[w * 3 + 2] = z / m.length;
  }
  const { offs, ids, ws } = sub;
  for (let v = 0; v < sub.vertCount; v++) {
    let x = 0, y = 0, z = 0;
    for (let k = offs[v]; k < offs[v + 1]; k++) { const id = ids[k] * 3, w = ws[k]; x += wp[id] * w; y += wp[id + 1] * w; z += wp[id + 2] * w; }
    out[v * 3] = x; out[v * 3 + 1] = y; out[v * 3 + 2] = z;
  }
  return out;
}
