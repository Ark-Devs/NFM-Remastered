// Minimal C++ layer over Godot's GDExtension C interface: enough to register
// RefCounted classes whose methods take and return plain values (int, float,
// bool, String, PackedFloat32Array, PackedInt32Array, PackedByteArray).
// No godot-cpp / Python / CMake needed; builds with plain g++.
#pragma once
#include <cstdint>
#include <cstring>
#include <functional>
#include <string>
#include <vector>
#include <cstdlib>
#include <cstdio>
#include "gdextension_interface.h"

namespace gdx {

// Opaque engine value storage (sizes from extension_api.json, float_64 build).
struct StrName { uint8_t d[8] = {}; };
struct Str { uint8_t d[8] = {}; };
struct Packed { uint8_t d[16] = {}; };

// V3 / COLORS keep their components flat in Val::f32 (3 / 4 floats per element)
enum Kind { NONE, BOOL, INT, FLOAT, STRING, F32, I32, BYTES, V3, COLORS, ARRAY, V2 };

inline GDExtensionVariantType vtype(Kind k) {
  switch (k) {
    case BOOL: return GDEXTENSION_VARIANT_TYPE_BOOL;
    case INT: return GDEXTENSION_VARIANT_TYPE_INT;
    case FLOAT: return GDEXTENSION_VARIANT_TYPE_FLOAT;
    case STRING: return GDEXTENSION_VARIANT_TYPE_STRING;
    case F32: return GDEXTENSION_VARIANT_TYPE_PACKED_FLOAT32_ARRAY;
    case I32: return GDEXTENSION_VARIANT_TYPE_PACKED_INT32_ARRAY;
    case BYTES: return GDEXTENSION_VARIANT_TYPE_PACKED_BYTE_ARRAY;
    case V3: return GDEXTENSION_VARIANT_TYPE_PACKED_VECTOR3_ARRAY;
    case COLORS: return GDEXTENSION_VARIANT_TYPE_PACKED_COLOR_ARRAY;
    case V2: return GDEXTENSION_VARIANT_TYPE_PACKED_VECTOR2_ARRAY;
    case ARRAY: return GDEXTENSION_VARIANT_TYPE_ARRAY;
    default: return GDEXTENSION_VARIANT_TYPE_NIL;
  }
}

// A value crossing the boundary, in C++ form.
struct Val {
  Kind kind = NONE;
  bool b = false;
  int64_t i = 0;
  double f = 0;
  std::string s;
  std::vector<float> f32;
  std::vector<int32_t> i32;
  std::vector<uint8_t> bytes;
  std::vector<Val> arr;
  Val() {}
  static Val Int(int64_t v) { Val r; r.kind = INT; r.i = v; return r; }
  static Val Float(double v) { Val r; r.kind = FLOAT; r.f = v; return r; }
  static Val Bool(bool v) { Val r; r.kind = BOOL; r.b = v; return r; }
  static Val String(std::string v) { Val r; r.kind = STRING; r.s = std::move(v); return r; }
  static Val Floats(std::vector<float> v) { Val r; r.kind = F32; r.f32 = std::move(v); return r; }
  static Val Ints(std::vector<int32_t> v) { Val r; r.kind = I32; r.i32 = std::move(v); return r; }
  static Val Bytes(std::vector<uint8_t> v) { Val r; r.kind = BYTES; r.bytes = std::move(v); return r; }
  static Val Vec3s(std::vector<float> v) { Val r; r.kind = V3; r.f32 = std::move(v); return r; }
  static Val Colors(std::vector<float> v) { Val r; r.kind = COLORS; r.f32 = std::move(v); return r; }
  static Val Vec2s(std::vector<float> v) { Val r; r.kind = V2; r.f32 = std::move(v); return r; }
  static Val List(std::vector<Val> v) { Val r; r.kind = ARRAY; r.arr = std::move(v); return r; }
};

struct Api {
  GDExtensionInterfaceGetProcAddress get = nullptr;
  GDExtensionClassLibraryPtr lib = nullptr;
  GDExtensionInterfaceClassdbConstructObject2 construct_object = nullptr;
  GDExtensionInterfaceObjectSetInstance set_instance = nullptr;
  GDExtensionInterfaceObjectSetInstanceBinding set_binding = nullptr;
  GDExtensionInterfaceClassdbRegisterExtensionClass6 register_class = nullptr;
  GDExtensionInterfaceClassdbRegisterExtensionClassMethod register_method = nullptr;
  GDExtensionInterfaceStringNameNewWithLatin1Chars string_name_new = nullptr;
  GDExtensionInterfaceStringNewWithUtf8CharsAndLen2 string_new = nullptr;
  GDExtensionInterfaceStringToUtf8Chars string_to_utf8 = nullptr;
  GDExtensionInterfaceVariantGetPtrConstructor ptr_constructor = nullptr;
  GDExtensionInterfaceVariantGetPtrDestructor ptr_destructor = nullptr;
  GDExtensionInterfaceVariantGetPtrBuiltinMethod builtin_method = nullptr;
  GDExtensionInterfaceGetVariantFromTypeConstructor from_type = nullptr;
  GDExtensionInterfaceGetVariantToTypeConstructor to_type = nullptr;
  GDExtensionInterfaceVariantGetType variant_type = nullptr;
  GDExtensionInterfaceVariantNewNil variant_nil = nullptr;
  GDExtensionInterfacePackedFloat32ArrayOperatorIndex f32_index = nullptr;
  GDExtensionInterfacePackedInt32ArrayOperatorIndex i32_index = nullptr;
  GDExtensionInterfacePackedByteArrayOperatorIndex byte_index = nullptr;
  GDExtensionInterfacePackedVector3ArrayOperatorIndex v3_index = nullptr;
  GDExtensionInterfacePackedColorArrayOperatorIndex color_index = nullptr;
  GDExtensionInterfacePackedVector2ArrayOperatorIndex v2_index = nullptr;
  GDExtensionInterfaceArrayOperatorIndex array_index = nullptr;
  GDExtensionInterfaceVariantNewCopy variant_copy = nullptr;
  GDExtensionInterfaceVariantDestroy variant_destroy = nullptr;
  GDExtensionInterfacePrintError print_error = nullptr;
  GDExtensionInterfaceClassdbGetMethodBind get_method_bind = nullptr;
  GDExtensionInterfaceObjectMethodBindPtrcall method_bind_ptrcall = nullptr;
};
inline Api api;

inline StrName name(const char *s) {
  StrName n;
  api.string_name_new(&n, s, true);
  return n;
}

// Size / resize of packed arrays go through builtin methods (hashes from extension_api.json).
constexpr GDExtensionInt HASH_SIZE = 3173160232;
constexpr GDExtensionInt HASH_RESIZE = 848867239;

inline int64_t packed_size(Kind k, const void *arr) {
  static GDExtensionPtrBuiltInMethod fn[16] = {};
  if (!fn[k]) { StrName n = name("size"); fn[k] = api.builtin_method(vtype(k), &n, HASH_SIZE); }
  int64_t r = 0;
  fn[k]((GDExtensionTypePtr)arr, nullptr, &r, 0);
  return r;
}

inline void packed_resize(Kind k, void *arr, int64_t n) {
  static GDExtensionPtrBuiltInMethod fn[16] = {};
  if (!fn[k]) { StrName nm = name("resize"); fn[k] = api.builtin_method(vtype(k), &nm, HASH_RESIZE); }
  int64_t r = 0;
  const GDExtensionConstTypePtr args[1] = {&n};
  fn[k](arr, args, &r, 1);
}

inline void write_native(const Val &v, void *p);
inline void destroy_native(Kind k, void *p);

// Engine value at p (native layout) -> Val
inline Val read_native(Kind k, const void *p) {
  Val v; v.kind = k;
  switch (k) {
    case BOOL: v.b = *(const uint8_t *)p != 0; break;
    case INT: v.i = *(const int64_t *)p; break;
    case FLOAT: v.f = *(const double *)p; break;
    case STRING: {
      int64_t n = api.string_to_utf8((GDExtensionConstStringPtr)p, nullptr, 0);
      v.s.resize(n);
      if (n) api.string_to_utf8((GDExtensionConstStringPtr)p, v.s.data(), n);
      break;
    }
    case F32: {
      int64_t n = packed_size(k, p);
      v.f32.resize(n);
      if (n) memcpy(v.f32.data(), api.f32_index((GDExtensionTypePtr)p, 0), n * 4);
      break;
    }
    case I32: {
      int64_t n = packed_size(k, p);
      v.i32.resize(n);
      if (n) memcpy(v.i32.data(), api.i32_index((GDExtensionTypePtr)p, 0), n * 4);
      break;
    }
    case BYTES: {
      int64_t n = packed_size(k, p);
      v.bytes.resize(n);
      if (n) memcpy(v.bytes.data(), api.byte_index((GDExtensionTypePtr)p, 0), n);
      break;
    }
    default: break;
  }
  return v;
}

// Construct a fresh engine value of v's kind into uninitialised storage p.
inline void write_native(const Val &v, void *p) {
  switch (v.kind) {
    case BOOL: *(uint8_t *)p = v.b; break;
    case INT: *(int64_t *)p = v.i; break;
    case FLOAT: *(double *)p = v.f; break;
    case STRING: api.string_new((GDExtensionUninitializedStringPtr)p, v.s.data(), (GDExtensionInt)v.s.size()); break;
    case V3: case COLORS: case V2: {
      api.ptr_constructor(vtype(v.kind), 0)(p, nullptr);
      int comp = v.kind == V3 ? 3 : v.kind == V2 ? 2 : 4;
      int64_t n = v.f32.size() / comp;
      packed_resize(v.kind, p, n);
      if (n) memcpy(v.kind == V3 ? (void *)api.v3_index(p, 0) : v.kind == V2 ? (void *)api.v2_index(p, 0) : (void *)api.color_index(p, 0), v.f32.data(), n * comp * 4);
      break;
    }
    case ARRAY: {
      api.ptr_constructor(vtype(v.kind), 0)(p, nullptr);
      int64_t n = v.arr.size();
      packed_resize(ARRAY, p, n);
      for (int64_t i = 0; i < n; i++) {
        alignas(8) uint8_t tmp[24];
        alignas(8) uint8_t var[24];
        write_native(v.arr[i], tmp);
        api.from_type(vtype(v.arr[i].kind))(var, tmp);
        destroy_native(v.arr[i].kind, tmp);
        GDExtensionVariantPtr slot = api.array_index(p, i);
        api.variant_destroy(slot);
        api.variant_copy(slot, var);
        api.variant_destroy(var);
      }
      break;
    }
    case F32: case I32: case BYTES: {
      api.ptr_constructor(vtype(v.kind), 0)(p, nullptr);
      int64_t n = v.kind == F32 ? v.f32.size() : v.kind == I32 ? v.i32.size() : v.bytes.size();
      packed_resize(v.kind, p, n);
      if (!n) break;
      if (v.kind == F32) memcpy(api.f32_index(p, 0), v.f32.data(), n * 4);
      else if (v.kind == I32) memcpy(api.i32_index(p, 0), v.i32.data(), n * 4);
      else memcpy(api.byte_index(p, 0), v.bytes.data(), n);
      break;
    }
    default: break;
  }
}

inline bool is_handle(Kind k) { return k == STRING || k == F32 || k == I32 || k == BYTES || k == V3 || k == COLORS || k == ARRAY || k == V2; }

inline void destroy_native(Kind k, void *p) {
  if (is_handle(k)) api.ptr_destructor(vtype(k))(p);
}

// Object.notification(NOTIFICATION_POSTINITIALIZE), as the engine expects
// when it asks for a post-initialised instance.
inline void post_initialize(GDExtensionObjectPtr obj) {
  static GDExtensionMethodBindPtr bind = nullptr;
  if (!bind) { StrName c = name("Object"), m = name("notification"); bind = api.get_method_bind(&c, &m, 4023243586); }
  int64_t what = 0;
  uint8_t reversed = 0;
  const GDExtensionConstTypePtr args[2] = {&what, &reversed};
  api.method_bind_ptrcall(bind, obj, args, nullptr);
}

// ------------------------------------------------------------------ classes
template <class T>
struct Method {
  std::string name;
  Kind ret;
  std::vector<Kind> args;
  std::vector<std::string> arg_names;
  std::function<Val(T &, std::vector<Val> &)> fn;
};

template <class T>
struct ClassReg {
  static inline StrName class_name, parent_name;
  static inline std::vector<Method<T> *> methods;

  static GDExtensionObjectPtr create(void *, GDExtensionBool notify) {
    GDExtensionObjectPtr obj = api.construct_object(&parent_name);
    T *self = new T();
    self->owner = obj;
    api.set_instance(obj, &class_name, self);
    static const GDExtensionInstanceBindingCallbacks cb = {
      [](void *, void *) -> void * { return nullptr; },
      [](void *, void *, void *) {},
      [](void *, void *, GDExtensionBool) -> GDExtensionBool { return true; },
    };
    api.set_binding(obj, api.lib, self, &cb);
    if (notify) post_initialize(obj);
    return obj;
  }
  static void free_instance(void *, GDExtensionClassInstancePtr inst) { delete (T *)inst; }

  static void call(void *ud, GDExtensionClassInstancePtr inst, const GDExtensionConstVariantPtr *args, GDExtensionInt argc, GDExtensionVariantPtr ret, GDExtensionCallError *err) {
    auto *m = (Method<T> *)ud;
    if ((size_t)argc != m->args.size()) {
      err->error = argc < (GDExtensionInt)m->args.size() ? GDEXTENSION_CALL_ERROR_TOO_FEW_ARGUMENTS : GDEXTENSION_CALL_ERROR_TOO_MANY_ARGUMENTS;
      err->expected = (int32_t)m->args.size();
      return;
    }
    std::vector<Val> in;
    for (size_t i = 0; i < m->args.size(); i++) {
      Kind k = m->args[i];
      if (api.variant_type(args[i]) != vtype(k) && !(k == FLOAT && api.variant_type(args[i]) == GDEXTENSION_VARIANT_TYPE_INT) && !(k == INT && api.variant_type(args[i]) == GDEXTENSION_VARIANT_TYPE_FLOAT)) {
        err->error = GDEXTENSION_CALL_ERROR_INVALID_ARGUMENT;
        err->argument = (int32_t)i;
        err->expected = vtype(k);
        return;
      }
      alignas(8) uint8_t buf[24];
      GDExtensionVariantType vt = api.variant_type(args[i]);
      if (k == FLOAT && vt == GDEXTENSION_VARIANT_TYPE_INT) {
        int64_t t; api.to_type(GDEXTENSION_VARIANT_TYPE_INT)(&t, (GDExtensionVariantPtr)args[i]);
        in.push_back(Val::Float((double)t));
        continue;
      }
      if (k == INT && vt == GDEXTENSION_VARIANT_TYPE_FLOAT) {
        double t; api.to_type(GDEXTENSION_VARIANT_TYPE_FLOAT)(&t, (GDExtensionVariantPtr)args[i]);
        in.push_back(Val::Int((int64_t)t));
        continue;
      }
      api.to_type(vtype(k))(buf, (GDExtensionVariantPtr)args[i]);
      in.push_back(read_native(k, buf));
      destroy_native(k, buf);
    }
    Val out = m->fn(*(T *)inst, in);
    err->error = GDEXTENSION_CALL_OK;
    if (m->ret == NONE) { api.variant_nil(ret); return; }
    out.kind = m->ret;
    alignas(8) uint8_t buf[24];
    write_native(out, buf);
    api.from_type(vtype(m->ret))(ret, buf);
    destroy_native(m->ret, buf);
  }

  static void ptrcall(void *ud, GDExtensionClassInstancePtr inst, const GDExtensionConstTypePtr *args, GDExtensionTypePtr ret) {
    auto *m = (Method<T> *)ud;
    std::vector<Val> in;
    for (size_t i = 0; i < m->args.size(); i++) in.push_back(read_native(m->args[i], args[i]));
    Val out = m->fn(*(T *)inst, in);
    if (m->ret == NONE) return;
    out.kind = m->ret;
    // ret holds a constructed value: release it, then build the new one in place
    destroy_native(m->ret, ret);
    write_native(out, ret);
  }

  static void reg(const char *cls, const char *parent) {
    class_name = name(cls);
    parent_name = name(parent);
    GDExtensionClassCreationInfo6 info = {};
    info.is_exposed = true;
    info.create_instance_func = create;
    info.free_instance_func = free_instance;
    api.register_class(api.lib, &class_name, &parent_name, &info);
  }

  static void method(const char *nm, Kind ret, std::vector<std::pair<Kind, const char *>> args, std::function<Val(T &, std::vector<Val> &)> fn) {
    auto *m = new Method<T>{nm, ret, {}, {}, fn};
    for (auto &a : args) { m->args.push_back(a.first); m->arg_names.push_back(a.second); }
    methods.push_back(m);
    static StrName empty_class = name("");
    auto *mname = new StrName(name(nm));
    auto *infos = new GDExtensionPropertyInfo[m->args.size() + 1];
    auto *metas = new GDExtensionClassMethodArgumentMetadata[m->args.size() + 1];
    auto *empty_str = new Str();
    api.string_new((GDExtensionUninitializedStringPtr)empty_str, "", 0);
    for (size_t i = 0; i < m->args.size(); i++) {
      auto *an = new StrName(name(m->arg_names[i].c_str()));
      infos[i] = {vtype(m->args[i]), an, &empty_class, 0, empty_str, 6 /* PROPERTY_USAGE_DEFAULT */};
      metas[i] = GDEXTENSION_METHOD_ARGUMENT_METADATA_NONE;
    }
    auto *rinfo = new GDExtensionPropertyInfo{vtype(ret), new StrName(name("")), &empty_class, 0, empty_str, 6};
    GDExtensionClassMethodInfo mi = {};
    mi.name = mname;
    mi.method_userdata = m;
    mi.call_func = call;
    mi.ptrcall_func = ptrcall;
    mi.method_flags = GDEXTENSION_METHOD_FLAGS_DEFAULT;
    mi.has_return_value = ret != NONE;
    mi.return_value_info = rinfo;
    mi.return_value_metadata = GDEXTENSION_METHOD_ARGUMENT_METADATA_NONE;
    mi.argument_count = (uint32_t)m->args.size();
    mi.arguments_info = infos;
    mi.arguments_metadata = metas;
    api.register_method(api.lib, &class_name, &mi);
  }
};

inline void load_api(GDExtensionInterfaceGetProcAddress get, GDExtensionClassLibraryPtr lib) {
  api.get = get;
  api.lib = lib;
#define GDX_LOAD(field, nm, type) api.field = (type)get(nm)
  GDX_LOAD(construct_object, "classdb_construct_object2", GDExtensionInterfaceClassdbConstructObject2);
  GDX_LOAD(set_instance, "object_set_instance", GDExtensionInterfaceObjectSetInstance);
  GDX_LOAD(set_binding, "object_set_instance_binding", GDExtensionInterfaceObjectSetInstanceBinding);
  GDX_LOAD(register_class, "classdb_register_extension_class6", GDExtensionInterfaceClassdbRegisterExtensionClass6);
  GDX_LOAD(register_method, "classdb_register_extension_class_method", GDExtensionInterfaceClassdbRegisterExtensionClassMethod);
  GDX_LOAD(string_name_new, "string_name_new_with_latin1_chars", GDExtensionInterfaceStringNameNewWithLatin1Chars);
  GDX_LOAD(string_new, "string_new_with_utf8_chars_and_len2", GDExtensionInterfaceStringNewWithUtf8CharsAndLen2);
  GDX_LOAD(string_to_utf8, "string_to_utf8_chars", GDExtensionInterfaceStringToUtf8Chars);
  GDX_LOAD(ptr_constructor, "variant_get_ptr_constructor", GDExtensionInterfaceVariantGetPtrConstructor);
  GDX_LOAD(ptr_destructor, "variant_get_ptr_destructor", GDExtensionInterfaceVariantGetPtrDestructor);
  GDX_LOAD(builtin_method, "variant_get_ptr_builtin_method", GDExtensionInterfaceVariantGetPtrBuiltinMethod);
  GDX_LOAD(from_type, "get_variant_from_type_constructor", GDExtensionInterfaceGetVariantFromTypeConstructor);
  GDX_LOAD(to_type, "get_variant_to_type_constructor", GDExtensionInterfaceGetVariantToTypeConstructor);
  GDX_LOAD(variant_type, "variant_get_type", GDExtensionInterfaceVariantGetType);
  GDX_LOAD(variant_nil, "variant_new_nil", GDExtensionInterfaceVariantNewNil);
  GDX_LOAD(f32_index, "packed_float32_array_operator_index", GDExtensionInterfacePackedFloat32ArrayOperatorIndex);
  GDX_LOAD(i32_index, "packed_int32_array_operator_index", GDExtensionInterfacePackedInt32ArrayOperatorIndex);
  GDX_LOAD(byte_index, "packed_byte_array_operator_index", GDExtensionInterfacePackedByteArrayOperatorIndex);
  GDX_LOAD(print_error, "print_error", GDExtensionInterfacePrintError);
  GDX_LOAD(v3_index, "packed_vector3_array_operator_index", GDExtensionInterfacePackedVector3ArrayOperatorIndex);
  GDX_LOAD(color_index, "packed_color_array_operator_index", GDExtensionInterfacePackedColorArrayOperatorIndex);
  GDX_LOAD(v2_index, "packed_vector2_array_operator_index", GDExtensionInterfacePackedVector2ArrayOperatorIndex);
  GDX_LOAD(array_index, "array_operator_index", GDExtensionInterfaceArrayOperatorIndex);
  GDX_LOAD(variant_copy, "variant_new_copy", GDExtensionInterfaceVariantNewCopy);
  GDX_LOAD(variant_destroy, "variant_destroy", GDExtensionInterfaceVariantDestroy);
  GDX_LOAD(get_method_bind, "classdb_get_method_bind", GDExtensionInterfaceClassdbGetMethodBind);
  GDX_LOAD(method_bind_ptrcall, "object_method_bind_ptrcall", GDExtensionInterfaceObjectMethodBindPtrcall);
#undef GDX_LOAD
}

}  // namespace gdx
