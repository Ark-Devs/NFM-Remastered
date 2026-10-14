// Prints "file name|display name" for every car whose two names differ.
#include <dirent.h>
#include <cstdio>
#include <fstream>
#include <sstream>
#include "../src/sim.h"
// same conversion as entry.cpp car_name()
static std::string to_utf8(const std::string &s) {
  bool ok = true;
  for (size_t i = 0; i < s.size() && ok;) {
    unsigned char c = s[i];
    int n = c < 0x80 ? 0 : (c >> 5) == 6 ? 1 : (c >> 4) == 14 ? 2 : (c >> 3) == 30 ? 3 : -1;
    if (n < 0 || i + n >= s.size() + (n ? 0 : 1)) { ok = false; break; }
    for (int k = 1; k <= n; k++) if (i + k >= s.size() || ((unsigned char)s[i + k] >> 6) != 2) ok = false;
    i += n + 1;
  }
  if (ok) return s;
  std::string o;
  for (unsigned char c : s) { if (c < 0x80) o += (char)c; else { o += (char)(0xC0 | (c >> 6)); o += (char)(0x80 | (c & 63)); } }
  return o;
}
static std::string slurp(const std::string &p) { std::ifstream f(p, std::ios::binary); std::stringstream s; s << f.rdbuf(); return s.str(); }
int main(int, char **argv) {
  Sim s;
  DIR *dd = opendir(argv[1]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 4 && n.substr(n.size() - 4) == ".rad") s.add_model(n, slurp(std::string(argv[1]) + "/" + n)); }
  closedir(dd);
  dd = opendir(argv[2]);
  for (dirent *e; (e = readdir(dd));) { std::string n = e->d_name; if (n.size() > 6 && n.substr(n.size() - 6) == " C.rad") s.add_custom(n, n.substr(0, n.size() - 6), slurp(std::string(argv[2]) + "/" + n)); }
  closedir(dd);
  s.build_world();
  for (auto &c : s.carList) { std::string shown = to_utf8(s.cd->names[c.slot]); if (shown != c.name) printf("%s|%s\n", c.name.c_str(), shown.c_str()); }
}
