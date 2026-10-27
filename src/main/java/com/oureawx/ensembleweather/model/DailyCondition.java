// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * A day's stable condition code.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum DailyCondition {
  /** {@code sunny}. */
  SUNNY("sunny"),
  /** {@code mostly_sunny}. */
  MOSTLY_SUNNY("mostly_sunny"),
  /** {@code partly_cloudy}. */
  PARTLY_CLOUDY("partly_cloudy"),
  /** {@code mostly_cloudy}. */
  MOSTLY_CLOUDY("mostly_cloudy"),
  /** {@code cloudy}. */
  CLOUDY("cloudy"),
  /** {@code slight_chance_showers}. */
  SLIGHT_CHANCE_SHOWERS("slight_chance_showers"),
  /** {@code chance_showers}. */
  CHANCE_SHOWERS("chance_showers"),
  /** {@code chance_rain}. */
  CHANCE_RAIN("chance_rain"),
  /** {@code showers_likely}. */
  SHOWERS_LIKELY("showers_likely"),
  /** {@code rain_likely}. */
  RAIN_LIKELY("rain_likely"),
  /** {@code showers}. */
  SHOWERS("showers"),
  /** {@code rain}. */
  RAIN("rain"),
  /** {@code slight_chance_rain_snow_mix}. */
  SLIGHT_CHANCE_RAIN_SNOW_MIX("slight_chance_rain_snow_mix"),
  /** {@code chance_rain_snow_mix}. */
  CHANCE_RAIN_SNOW_MIX("chance_rain_snow_mix"),
  /** {@code rain_snow_mix_likely}. */
  RAIN_SNOW_MIX_LIKELY("rain_snow_mix_likely"),
  /** {@code rain_snow_mix}. */
  RAIN_SNOW_MIX("rain_snow_mix"),
  /** {@code slight_chance_snow}. */
  SLIGHT_CHANCE_SNOW("slight_chance_snow"),
  /** {@code chance_snow}. */
  CHANCE_SNOW("chance_snow"),
  /** {@code snow_likely}. */
  SNOW_LIKELY("snow_likely"),
  /** {@code snow}. */
  SNOW("snow"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  DailyCondition(String value) {
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
  public static DailyCondition fromWireValue(String value) {
    for (DailyCondition c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
