// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * An immutable JSON value, for the few fields the API documents as free-form JSON (a catalog
 * location's {@code external_links}, a content entry's {@code body}, an ad network's {@code
 * config}). It is plain data: strings, numbers, booleans, null, arrays and objects. Nothing in it
 * is ever turned into an instance of a class named by the JSON.
 */
public final class JsonValue {
  /** The kind of a JSON value. */
  public enum Kind {
    /** A JSON object. */
    OBJECT,
    /** A JSON array. */
    ARRAY,
    /** A JSON string. */
    STRING,
    /** A JSON number. */
    NUMBER,
    /** {@code true} or {@code false}. */
    BOOLEAN,
    /** {@code null}. */
    NULL
  }

  private static final JsonValue NULL = new JsonValue(Kind.NULL, null);
  private static final JsonValue TRUE = new JsonValue(Kind.BOOLEAN, true);
  private static final JsonValue FALSE = new JsonValue(Kind.BOOLEAN, false);

  private final Kind kind;
  private final @Nullable Object value;

  private JsonValue(Kind kind, @Nullable Object value) {
    this.kind = kind;
    this.value = value;
  }

  /**
   * The JSON {@code null}.
   *
   * @return null as a value
   */
  public static JsonValue nullValue() {
    return NULL;
  }

  /**
   * A JSON string.
   *
   * @param value the string
   * @return the value
   */
  public static JsonValue of(String value) {
    return new JsonValue(Kind.STRING, Objects.requireNonNull(value, "value"));
  }

  /**
   * A JSON number.
   *
   * @param value the number, exactly as written
   * @return the value
   */
  public static JsonValue of(BigDecimal value) {
    return new JsonValue(Kind.NUMBER, Objects.requireNonNull(value, "value"));
  }

  /**
   * A JSON boolean.
   *
   * @param value the boolean
   * @return the value
   */
  public static JsonValue of(boolean value) {
    return value ? TRUE : FALSE;
  }

  /**
   * A JSON array.
   *
   * @param items the elements (copied)
   * @return the value
   */
  public static JsonValue ofArray(List<JsonValue> items) {
    return new JsonValue(Kind.ARRAY, List.copyOf(items));
  }

  /**
   * A JSON object, keeping the members' order.
   *
   * @param members the members (copied)
   * @return the value
   */
  public static JsonValue ofObject(Map<String, JsonValue> members) {
    Map<String, JsonValue> copy = new LinkedHashMap<>();
    for (Map.Entry<String, JsonValue> e : members.entrySet()) {
      copy.put(
          Objects.requireNonNull(e.getKey(), "key"), Objects.requireNonNull(e.getValue(), "value"));
    }
    return new JsonValue(Kind.OBJECT, Collections.unmodifiableMap(copy));
  }

  /**
   * What kind of value this is.
   *
   * @return the kind
   */
  public Kind kind() {
    return kind;
  }

  /**
   * Whether this is the JSON {@code null}.
   *
   * @return true for null
   */
  public boolean isNull() {
    return kind == Kind.NULL;
  }

  /**
   * This value as a string.
   *
   * @return the string, or empty when this is not a string
   */
  public Optional<String> asString() {
    return kind == Kind.STRING
        ? Optional.of((String) Objects.requireNonNull(value))
        : Optional.empty();
  }

  /**
   * This value as a number, exactly as written.
   *
   * @return the number, or empty when this is not a number
   */
  public Optional<BigDecimal> asNumber() {
    return kind == Kind.NUMBER
        ? Optional.of((BigDecimal) Objects.requireNonNull(value))
        : Optional.empty();
  }

  /**
   * This value as a boolean.
   *
   * @return the boolean, or empty when this is not a boolean
   */
  public Optional<Boolean> asBoolean() {
    return kind == Kind.BOOLEAN
        ? Optional.of((Boolean) Objects.requireNonNull(value))
        : Optional.empty();
  }

  /**
   * This value's elements.
   *
   * @return the elements, or an empty list when this is not an array
   */
  @SuppressWarnings("unchecked")
  public List<JsonValue> asArray() {
    return kind == Kind.ARRAY ? (List<JsonValue>) Objects.requireNonNull(value) : List.of();
  }

  /**
   * This value's members, in the order they were written.
   *
   * @return the members, or an empty map when this is not an object
   */
  @SuppressWarnings("unchecked")
  public Map<String, JsonValue> asObject() {
    return kind == Kind.OBJECT ? (Map<String, JsonValue>) Objects.requireNonNull(value) : Map.of();
  }

  /**
   * One member of this object.
   *
   * @param name the member name
   * @return the member, or empty when this is not an object or has no such member
   */
  public Optional<JsonValue> get(String name) {
    return Optional.ofNullable(asObject().get(name));
  }

  @Override
  public boolean equals(@Nullable Object other) {
    if (!(other instanceof JsonValue)) {
      return false;
    }
    JsonValue o = (JsonValue) other;
    if (kind != o.kind) {
      return false;
    }
    if (kind == Kind.NUMBER) {
      return ((BigDecimal) Objects.requireNonNull(value))
              .compareTo((BigDecimal) Objects.requireNonNull(o.value))
          == 0;
    }
    return Objects.equals(value, o.value);
  }

  @Override
  public int hashCode() {
    if (kind == Kind.NUMBER) {
      return ((BigDecimal) Objects.requireNonNull(value)).stripTrailingZeros().hashCode();
    }
    return Objects.hash(kind, value);
  }

  /** Returns this value as compact JSON text. */
  @Override
  public String toString() {
    StringBuilder out = new StringBuilder();
    write(out);
    return out.toString();
  }

  private void write(StringBuilder out) {
    switch (kind) {
      case NULL:
        out.append("null");
        break;
      case BOOLEAN:
      case NUMBER:
        out.append(
            value instanceof BigDecimal ? ((BigDecimal) value).toString() : String.valueOf(value));
        break;
      case STRING:
        quote(out, (String) Objects.requireNonNull(value));
        break;
      case ARRAY:
        out.append('[');
        List<String> parts = new ArrayList<>();
        for (JsonValue item : asArray()) {
          parts.add(item.toString());
        }
        out.append(String.join(",", parts)).append(']');
        break;
      case OBJECT:
        out.append('{');
        boolean first = true;
        for (Map.Entry<String, JsonValue> e : asObject().entrySet()) {
          if (!first) {
            out.append(',');
          }
          first = false;
          quote(out, e.getKey());
          out.append(':');
          e.getValue().write(out);
        }
        out.append('}');
        break;
    }
  }

  private static void quote(StringBuilder out, String s) {
    out.append('"');
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '"':
          out.append("\\\"");
          break;
        case '\\':
          out.append("\\\\");
          break;
        case '\n':
          out.append("\\n");
          break;
        case '\r':
          out.append("\\r");
          break;
        case '\t':
          out.append("\\t");
          break;
        default:
          if (c < 0x20) {
            out.append(String.format("\\u%04x", (int) c));
          } else {
            out.append(c);
          }
      }
    }
    out.append('"');
  }
}
