// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * A live nowcast source.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum NowcastSource {
  /** {@code observation}. */
  OBSERVATION("observation"),
  /** {@code radar}. */
  RADAR("radar"),
  /** {@code cloud}. */
  CLOUD("cloud"),
  /** {@code nearest_sensors}. */
  NEAREST_SENSORS("nearest_sensors"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  NowcastSource(String value) {
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
  public static NowcastSource fromWireValue(String value) {
    for (NowcastSource c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
