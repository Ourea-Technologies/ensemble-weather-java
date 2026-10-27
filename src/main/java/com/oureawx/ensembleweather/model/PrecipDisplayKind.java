// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * The headline kind of a day's precipitation.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum PrecipDisplayKind {
  /** {@code none}. */
  NONE("none"),
  /** {@code rain}. */
  RAIN("rain"),
  /** {@code snow}. */
  SNOW("snow"),
  /** {@code rain_snow_mix}. */
  RAIN_SNOW_MIX("rain_snow_mix"),
  /** {@code freezing_rain}. */
  FREEZING_RAIN("freezing_rain"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  PrecipDisplayKind(String value) {
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
  public static PrecipDisplayKind fromWireValue(String value) {
    for (PrecipDisplayKind c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
