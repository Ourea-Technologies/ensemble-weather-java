// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;

/**
 * What {@code explore} ranks places by, and the metric a search can sort by.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum ExploreMetric {
  /** {@code base_depth}. */
  BASE_DEPTH("base_depth"),
  /** {@code new_snow_24h}. */
  NEW_SNOW_24H("new_snow_24h"),
  /** {@code snow_today}. */
  SNOW_TODAY("snow_today"),
  /** {@code snow_5d}. */
  SNOW_5D("snow_5d"),
  /** {@code snow_10d}. */
  SNOW_10D("snow_10d"),
  /** {@code snow_14d}. */
  SNOW_14D("snow_14d"),
  /** {@code precip_today}. */
  PRECIP_TODAY("precip_today"),
  /** {@code precip_5d}. */
  PRECIP_5D("precip_5d"),
  /** {@code precip_10d}. */
  PRECIP_10D("precip_10d"),
  /** {@code precip_14d}. */
  PRECIP_14D("precip_14d"),
  /** {@code season_precip}. */
  SEASON_PRECIP("season_precip"),
  /** {@code season_precip_pct_normal}. */
  SEASON_PRECIP_PCT_NORMAL("season_precip_pct_normal"),
  /** {@code season_snow}. */
  SEASON_SNOW("season_snow"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  ExploreMetric(String value) {
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
  public static ExploreMetric fromWireValue(String value) {
    for (ExploreMetric c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
