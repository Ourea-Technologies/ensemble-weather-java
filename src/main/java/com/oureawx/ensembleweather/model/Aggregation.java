// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * How a probability term reads its window.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum Aggregation {
  /** {@code total}. */
  TOTAL("total"),
  /** {@code any}. */
  ANY("any"),
  /** {@code all}. */
  ALL("all"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  Aggregation(String value) {
    this.value = value;
  }

  /**
   * The API's spelling of this value.
   *
   * @return the wire value, or empty for {@link #UNKNOWN}
   */
  public Optional<String> wireValue() {
    return value.isEmpty() ? Optional.empty() : Optional.of(value);
  }

  /**
   * Parses the API's spelling.
   *
   * @param value the wire value
   * @return the matching constant, or {@link #UNKNOWN}
   */
  public static Aggregation fromWireValue(String value) {
    for (Aggregation c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
