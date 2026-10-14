// Turns ContO polygon models into triangle data for Godot.
// Godot space: metres, y up, -z forward. NFM: ~2 cm units, y down, z forward.
#pragma once
#include <vector>
#include "gen/nfm_gen.h"

constexpr float S = 1.0f / 50.0f;

enum MeshPart { PART_BODY = 0, PART_GLASS, PART_LIGHT_F, PART_LIGHT_B, PART_FINISH, PART_COUNT };
// Per vertex: position(3) normal(3) colour(3, linear) surf(3) = 12 floats.
constexpr int VERT_FLOATS = 12;

struct MeshOut {
  std::vector<float> parts[PART_COUNT];
};

// car: split glass / lights, true paint colours (stage snap tint undone)
void build_conto_mesh(ContO *c, bool car, const char *modelName, const int snap[3], MeshOut &out);
// Flattened for transfer: [nparts, (part, nverts, verts...)...]
std::vector<float> pack_mesh(const MeshOut &m);

// 3x4 transform (basis columns x,y,z then origin) for an NFM pose.
void nfm_transform(double x, double y, double z, double xz, double xy, double zy, float out[12]);
