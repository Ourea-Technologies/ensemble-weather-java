// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * The severity of an alert.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum AlertSeverity {
  /** {@code extreme}. */
  EXTREME("extreme"),
  /** {@code severe}. */
  SEVERE("severe"),
  /** {@code moderate}. */
  MODERATE("moderate"),
  /** {@code minor}. */
  MINOR("minor"),
  /**
   * {@code unknown} on the wire, or a value this SDK version does not know; read the model's raw
   * string to tell them apart.
   */
  UNKNOWN("");

  private final String value;

  AlertSeverity(String value) {
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
  public static AlertSeverity fromWireValue(String value) {
    for (AlertSeverity c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
