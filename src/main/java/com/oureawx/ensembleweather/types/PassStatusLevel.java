// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;

/**
 * The official restriction level on a mountain pass, least to most severe.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum PassStatusLevel {
  /** {@code none}. */
  NONE("none"),
  /** {@code advisory}. */
  ADVISORY("advisory"),
  /** {@code traction}. */
  TRACTION("traction"),
  /** {@code r1}. */
  R1("r1"),
  /** {@code r2}. */
  R2("r2"),
  /** {@code r3}. */
  R3("r3"),
  /** {@code chains}. */
  CHAINS("chains"),
  /** {@code closed}. */
  CLOSED("closed"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  PassStatusLevel(String value) {
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
  public static PassStatusLevel fromWireValue(String value) {
    for (PassStatusLevel c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
