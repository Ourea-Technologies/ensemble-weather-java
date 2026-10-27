// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.jspecify.annotations.Nullable;

/**
 * Builds the canonical query string (BEHAVIOR.md section 3.1): no parameter at its default, each
 * parameter once under its own name, alphabetical order, RFC 3986 percent-encoding (space as {@code
 * %20}, uppercase hex), except the literal {@code :} and {@code ,} of {@code prob} and {@code
 * totals}.
 */
public final class Query {
  private final Map<String, List<String>> params = new TreeMap<>();

  /** Creates an empty query. */
  public Query() {}

  private Query put(String name, String encodedValue) {
    if (params.containsKey(name)) {
      throw new IllegalStateException("parameter " + name + " set twice");
    }
    params.put(name, List.of(encodedValue));
    return this;
  }

  /**
   * A string parameter; null is omitted.
   *
   * @param name the parameter
   * @param value the value
   * @return this query
   */
  public Query string(String name, @Nullable String value) {
    return value == null ? this : put(name, encode(value));
  }

  /**
   * A string parameter, omitted when null or at its default.
   *
   * @param name the parameter
   * @param value the value
   * @param defaultValue the route's default
   * @return this query
   */
  public Query string(String name, @Nullable String value, String defaultValue) {
    return value == null || value.equals(defaultValue) ? this : string(name, value);
  }

  /**
   * A boolean parameter, omitted when null or at its default.
   *
   * @param name the parameter
   * @param value the value
   * @param defaultValue the route's default
   * @return this query
   */
  public Query bool(String name, @Nullable Boolean value, boolean defaultValue) {
    return value == null || value == defaultValue ? this : put(name, value ? "true" : "false");
  }

  /**
   * An integer parameter, omitted when null or at its default.
   *
   * @param name the parameter
   * @param value the value
   * @param defaultValue the route's default, or null when it has none
   * @return this query
   */
  public Query integer(String name, @Nullable Long value, @Nullable Long defaultValue) {
    return value == null || value.equals(defaultValue) ? this : put(name, Long.toString(value));
  }

  /**
   * A number parameter, omitted when null or at its default; sent as the shortest decimal that
   * round-trips, never with an exponent, never rounded.
   *
   * @param name the parameter
   * @param value the value
   * @param defaultValue the route's default, or null when it has none
   * @return this query
   */
  public Query number(String name, @Nullable Double value, @Nullable Double defaultValue) {
    if (value == null || (defaultValue != null && value.doubleValue() == defaultValue)) {
      return this;
    }
    return put(name, plain(value));
  }

  /**
   * A comma-joined list, percent-encoded as one value; omitted when empty.
   *
   * @param name the parameter
   * @param values the values
   * @return this query
   */
  public Query csv(String name, List<String> values) {
    return values.isEmpty() ? this : string(name, String.join(",", values));
  }

  /**
   * A value built by the SDK whose {@code :} and {@code ,} are sent literally ({@code prob}, {@code
   * totals}); every other character must be unreserved.
   *
   * @param name the parameter
   * @param value the value
   * @return this query
   */
  public Query literal(String name, String value) {
    for (int i = 0; i < value.length(); i++) {
      char c = value.charAt(i);
      if (!(unreserved(c) || c == ':' || c == ',')) {
        throw new IllegalArgumentException("unsafe character in " + name + ": " + c);
      }
    }
    return put(name, value);
  }

  /**
   * A repeated parameter ({@code capability=a&capability=b}); omitted when empty.
   *
   * @param name the parameter
   * @param values the values, sent in this order
   * @return this query
   */
  public Query repeated(String name, List<String> values) {
    if (values.isEmpty()) {
      return this;
    }
    List<String> encoded = new ArrayList<>();
    for (String v : values) {
      encoded.add(encode(v));
    }
    params.put(name, List.copyOf(encoded));
    return this;
  }

  /**
   * The query string, without the {@code ?}; empty when no parameter is set.
   *
   * @return the canonical query string
   */
  public String build() {
    StringBuilder out = new StringBuilder();
    for (Map.Entry<String, List<String>> e : params.entrySet()) {
      for (String v : e.getValue()) {
        if (out.length() > 0) {
          out.append('&');
        }
        out.append(e.getKey()).append('=').append(v);
      }
    }
    return out.toString();
  }

  private static boolean unreserved(char c) {
    return (c >= 'A' && c <= 'Z')
        || (c >= 'a' && c <= 'z')
        || (c >= '0' && c <= '9')
        || c == '-'
        || c == '.'
        || c == '_'
        || c == '~';
  }

  /**
   * RFC 3986 percent-encoding of UTF-8: unreserved characters as they are, every other byte as
   * {@code %XX} in uppercase hex.
   *
   * @param value the text
   * @return the encoded text
   */
  public static String encode(String value) {
    StringBuilder out = new StringBuilder();
    for (byte b : value.getBytes(StandardCharsets.UTF_8)) {
      char c = (char) (b & 0xFF);
      if (c < 0x80 && unreserved(c)) {
        out.append(c);
      } else {
        out.append('%').append(HEX[(b >> 4) & 0xF]).append(HEX[b & 0xF]);
      }
    }
    return out.toString();
  }

  private static final char[] HEX = "0123456789ABCDEF".toCharArray();

  /**
   * The shortest decimal that round-trips, with no exponent and no trailing {@code .0}.
   *
   * @param value a finite number
   * @return the decimal text
   */
  public static String plain(double value) {
    if (!Double.isFinite(value)) {
      throw new IllegalArgumentException("not a finite number: " + value);
    }
    String s = new BigDecimal(Double.toString(value)).stripTrailingZeros().toPlainString();
    return Objects.equals(s, "-0") ? "0" : s;
  }
}
