// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import com.oureawx.ensembleweather.model.JsonValue;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import org.jspecify.annotations.Nullable;

/**
 * A typed reader over one JSON object. Unknown members are ignored (forward compatibility); a
 * member of the wrong type, or a required member that is missing, is a {@link
 * MalformedResponseException} naming its path. With a {@link Tracker} it also records which members
 * were never read, which the spec-drift tests use to prove the models know every field.
 */
public final class JsonFields {
  private final Map<String, JsonValue> members;
  private final String path;
  private final @Nullable Tracker tracker;
  private final Set<String> read = new LinkedHashSet<>();

  private JsonFields(Map<String, JsonValue> members, String path, @Nullable Tracker tracker) {
    this.members = members;
    this.path = path;
    this.tracker = tracker;
    if (tracker != null) {
      tracker.objects.add(this);
    }
  }

  /**
   * Reads a root value, which must be an object.
   *
   * @param value the parsed body
   * @param tracker records unread members, or null
   * @return the reader
   */
  public static JsonFields root(JsonValue value, @Nullable Tracker tracker) {
    if (value.kind() != JsonValue.Kind.OBJECT) {
      throw new MalformedResponseException("the body is JSON " + value.kind() + ", not an object");
    }
    return new JsonFields(value.asObject(), "$", tracker);
  }

  /** Records every object a decode read, to list the members it never read. */
  public static final class Tracker {
    private final List<JsonFields> objects = new ArrayList<>();

    /** Creates an empty tracker. */
    public Tracker() {}

    /**
     * The members no decoder read, as JSON paths.
     *
     * @return the unread paths, sorted
     */
    public Set<String> unread() {
      Set<String> out = new TreeSet<>();
      for (JsonFields f : objects) {
        for (String name : f.members.keySet()) {
          if (!f.read.contains(name)) {
            out.add(f.path + "." + name);
          }
        }
      }
      return out;
    }
  }

  private @Nullable JsonValue raw(String name) {
    read.add(name);
    JsonValue v = members.get(name);
    return v == null || v.isNull() ? null : v;
  }

  private MalformedResponseException wrong(String name, String expected, JsonValue got) {
    return new MalformedResponseException(
        path + "." + name + " should be " + expected + ", not JSON " + got.kind());
  }

  private MalformedResponseException missing(String name) {
    return new MalformedResponseException(path + "." + name + " is required but missing");
  }

  /**
   * A required string.
   *
   * @param name the member
   * @return the string
   */
  public String string(String name) {
    String v = optString(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional string.
   *
   * @param name the member
   * @return the string, or null when absent or null
   */
  public @Nullable String optString(String name) {
    JsonValue v = raw(name);
    if (v == null) {
      return null;
    }
    return v.asString().orElseThrow(() -> wrong(name, "a string", v));
  }

  private @Nullable BigDecimal decimal(String name) {
    JsonValue v = raw(name);
    if (v == null) {
      return null;
    }
    return v.asNumber().orElseThrow(() -> wrong(name, "a number", v));
  }

  /**
   * A required number.
   *
   * @param name the member
   * @return the number
   */
  public double number(String name) {
    Double v = optNumber(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional number.
   *
   * @param name the member
   * @return the number, or null
   */
  public @Nullable Double optNumber(String name) {
    BigDecimal d = decimal(name);
    return d == null ? null : d.doubleValue();
  }

  /**
   * A required integer.
   *
   * @param name the member
   * @return the integer
   */
  public int integer(String name) {
    Integer v = optInteger(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional integer.
   *
   * @param name the member
   * @return the integer, or null
   */
  public @Nullable Integer optInteger(String name) {
    BigDecimal d = decimal(name);
    if (d == null) {
      return null;
    }
    try {
      return d.intValueExact();
    } catch (ArithmeticException e) {
      throw new MalformedResponseException(path + "." + name + " should be an integer, not " + d);
    }
  }

  /**
   * A required 64-bit integer.
   *
   * @param name the member
   * @return the integer
   */
  public long longInteger(String name) {
    Long v = optLongInteger(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional 64-bit integer.
   *
   * @param name the member
   * @return the integer, or null
   */
  public @Nullable Long optLongInteger(String name) {
    BigDecimal d = decimal(name);
    if (d == null) {
      return null;
    }
    try {
      return d.longValueExact();
    } catch (ArithmeticException e) {
      throw new MalformedResponseException(path + "." + name + " should be an integer, not " + d);
    }
  }

  /**
   * A required boolean.
   *
   * @param name the member
   * @return the boolean
   */
  public boolean bool(String name) {
    Boolean v = optBool(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional boolean.
   *
   * @param name the member
   * @return the boolean, or null
   */
  public @Nullable Boolean optBool(String name) {
    JsonValue v = raw(name);
    if (v == null) {
      return null;
    }
    return v.asBoolean().orElseThrow(() -> wrong(name, "a boolean", v));
  }

  /**
   * A required RFC 3339 date-time.
   *
   * @param name the member
   * @return the instant
   */
  public Instant instant(String name) {
    Instant v = optInstant(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional RFC 3339 date-time.
   *
   * @param name the member
   * @return the instant, or null
   */
  public @Nullable Instant optInstant(String name) {
    String s = optString(name);
    if (s == null) {
      return null;
    }
    try {
      return DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(s, OffsetDateTime::from).toInstant();
    } catch (DateTimeException e) {
      throw new MalformedResponseException(path + "." + name + " is not a date-time: " + s, e);
    }
  }

  /**
   * A required ISO date.
   *
   * @param name the member
   * @return the date
   */
  public LocalDate date(String name) {
    LocalDate v = optDate(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional ISO date.
   *
   * @param name the member
   * @return the date, or null
   */
  public @Nullable LocalDate optDate(String name) {
    String s = optString(name);
    if (s == null) {
      return null;
    }
    try {
      return LocalDate.parse(s);
    } catch (DateTimeException e) {
      throw new MalformedResponseException(path + "." + name + " is not a date: " + s, e);
    }
  }

  /**
   * A required IANA time-zone id.
   *
   * @param name the member
   * @return the zone
   */
  public ZoneId zone(String name) {
    ZoneId v = optZone(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional IANA time-zone id.
   *
   * @param name the member
   * @return the zone, or null
   */
  public @Nullable ZoneId optZone(String name) {
    String s = optString(name);
    if (s == null) {
      return null;
    }
    try {
      return ZoneId.of(s);
    } catch (DateTimeException e) {
      throw new MalformedResponseException(path + "." + name + " is not a time zone: " + s, e);
    }
  }

  /**
   * A required free-form JSON member.
   *
   * @param name the member
   * @return the value
   */
  public JsonValue json(String name) {
    JsonValue v = optJson(name);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional free-form JSON member.
   *
   * @param name the member
   * @return the value, or null when absent or null
   */
  public @Nullable JsonValue optJson(String name) {
    return raw(name);
  }

  private JsonFields child(String name, JsonValue v, String childPath) {
    if (v.kind() != JsonValue.Kind.OBJECT) {
      throw wrong(name, "an object", v);
    }
    return new JsonFields(v.asObject(), childPath, tracker);
  }

  /**
   * A required object.
   *
   * @param name the member
   * @param decoder decodes the object
   * @param <T> the model type
   * @return the model
   */
  public <T> T object(String name, Function<JsonFields, T> decoder) {
    T v = optObject(name, decoder);
    if (v == null) {
      throw missing(name);
    }
    return v;
  }

  /**
   * An optional object.
   *
   * @param name the member
   * @param decoder decodes the object
   * @param <T> the model type
   * @return the model, or null when absent or null
   */
  public <T> @Nullable T optObject(String name, Function<JsonFields, T> decoder) {
    JsonValue v = raw(name);
    if (v == null) {
      return null;
    }
    return decoder.apply(child(name, v, path + "." + name));
  }

  private List<JsonValue> array(String name) {
    JsonValue v = raw(name);
    if (v == null) {
      return List.of();
    }
    if (v.kind() != JsonValue.Kind.ARRAY) {
      throw wrong(name, "an array", v);
    }
    return v.asArray();
  }

  /**
   * An array of objects; absent or null is an empty list.
   *
   * @param name the member
   * @param decoder decodes each element
   * @param <T> the model type
   * @return the models
   */
  public <T> List<T> list(String name, Function<JsonFields, T> decoder) {
    List<JsonValue> items = array(name);
    List<T> out = new ArrayList<>(items.size());
    for (int i = 0; i < items.size(); i++) {
      out.add(decoder.apply(child(name, items.get(i), path + "." + name + "[" + i + "]")));
    }
    return out;
  }

  /**
   * An array of strings; absent or null is an empty list.
   *
   * @param name the member
   * @return the strings
   */
  public List<String> stringList(String name) {
    List<String> out = new ArrayList<>();
    for (JsonValue item : array(name)) {
      out.add(item.asString().orElseThrow(() -> wrong(name, "an array of strings", item)));
    }
    return out;
  }

  /**
   * An array of arrays of integers; absent or null is an empty list.
   *
   * @param name the member
   * @return the integer lists
   */
  public List<List<Integer>> intLists(String name) {
    List<List<Integer>> out = new ArrayList<>();
    for (JsonValue row : array(name)) {
      if (row.kind() != JsonValue.Kind.ARRAY) {
        throw wrong(name, "an array of arrays", row);
      }
      List<Integer> ints = new ArrayList<>();
      for (JsonValue cell : row.asArray()) {
        BigDecimal d = cell.asNumber().orElseThrow(() -> wrong(name, "integers", cell));
        try {
          ints.add(d.intValueExact());
        } catch (ArithmeticException e) {
          throw new MalformedResponseException(path + "." + name + " should hold integers");
        }
      }
      out.add(List.copyOf(ints));
    }
    return out;
  }
}
