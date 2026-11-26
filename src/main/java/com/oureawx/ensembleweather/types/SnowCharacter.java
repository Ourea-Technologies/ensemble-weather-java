// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;

/**
 * The character of the falling snow, from its snow-to-liquid ratio and temperature.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum SnowCharacter {
  /** {@code wet}. */
  WET("wet"),
  /** {@code average}. */
  AVERAGE("average"),
  /** {@code dry}. */
  DRY("dry"),
  /** {@code dense}. */
  DENSE("dense"),
  /** {@code very_dry}. */
  VERY_DRY("very_dry"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  SnowCharacter(String value) {
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
  public static SnowCharacter fromWireValue(String value) {
    for (SnowCharacter c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
