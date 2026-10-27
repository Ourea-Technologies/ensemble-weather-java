// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * An eight-way moon phase.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum MoonPhase {
  /** {@code new_moon}. */
  NEW_MOON("new_moon"),
  /** {@code waxing_crescent}. */
  WAXING_CRESCENT("waxing_crescent"),
  /** {@code first_quarter}. */
  FIRST_QUARTER("first_quarter"),
  /** {@code waxing_gibbous}. */
  WAXING_GIBBOUS("waxing_gibbous"),
  /** {@code full_moon}. */
  FULL_MOON("full_moon"),
  /** {@code waning_gibbous}. */
  WANING_GIBBOUS("waning_gibbous"),
  /** {@code last_quarter}. */
  LAST_QUARTER("last_quarter"),
  /** {@code waning_crescent}. */
  WANING_CRESCENT("waning_crescent"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  MoonPhase(String value) {
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
  public static MoonPhase fromWireValue(String value) {
    for (MoonPhase c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
