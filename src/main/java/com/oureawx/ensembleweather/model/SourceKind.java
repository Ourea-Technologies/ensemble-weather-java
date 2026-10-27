// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * The kind of a data source.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum SourceKind {
  /** {@code model}. */
  MODEL("model"),
  /** {@code peer}. */
  PEER("peer"),
  /** {@code observation}. */
  OBSERVATION("observation"),
  /** {@code alerts}. */
  ALERTS("alerts"),
  /** {@code climatology}. */
  CLIMATOLOGY("climatology"),
  /** {@code terrain}. */
  TERRAIN("terrain"),
  /** {@code places}. */
  PLACES("places"),
  /** {@code basemap}. */
  BASEMAP("basemap"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  SourceKind(String value) {
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
  public static SourceKind fromWireValue(String value) {
    for (SourceKind c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
