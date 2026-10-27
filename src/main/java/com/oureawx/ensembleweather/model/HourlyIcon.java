// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * An hour's icon hint.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum HourlyIcon {
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
  /** {@code sun_chance_showers}. */
  SUN_CHANCE_SHOWERS("sun_chance_showers"),
  /** {@code sun_showers_likely}. */
  SUN_SHOWERS_LIKELY("sun_showers_likely"),
  /** {@code chance_showers}. */
  CHANCE_SHOWERS("chance_showers"),
  /** {@code showers_likely}. */
  SHOWERS_LIKELY("showers_likely"),
  /** {@code rain}. */
  RAIN("rain"),
  /** {@code light_rain}. */
  LIGHT_RAIN("light_rain"),
  /** {@code heavy_rain}. */
  HEAVY_RAIN("heavy_rain"),
  /** {@code rain_snow_mix}. */
  RAIN_SNOW_MIX("rain_snow_mix"),
  /** {@code snow}. */
  SNOW("snow"),
  /** {@code freezing_rain}. */
  FREEZING_RAIN("freezing_rain"),
  /** {@code night_clear}. */
  NIGHT_CLEAR("night_clear"),
  /** {@code night_mostly_clear}. */
  NIGHT_MOSTLY_CLEAR("night_mostly_clear"),
  /** {@code night_partly_cloudy}. */
  NIGHT_PARTLY_CLOUDY("night_partly_cloudy"),
  /** {@code night_mostly_cloudy}. */
  NIGHT_MOSTLY_CLOUDY("night_mostly_cloudy"),
  /** {@code night_cloudy}. */
  NIGHT_CLOUDY("night_cloudy"),
  /** {@code night_moon_chance_showers}. */
  NIGHT_MOON_CHANCE_SHOWERS("night_moon_chance_showers"),
  /** {@code night_moon_showers_likely}. */
  NIGHT_MOON_SHOWERS_LIKELY("night_moon_showers_likely"),
  /** {@code night_chance_showers}. */
  NIGHT_CHANCE_SHOWERS("night_chance_showers"),
  /** {@code night_showers_likely}. */
  NIGHT_SHOWERS_LIKELY("night_showers_likely"),
  /** {@code night_rain}. */
  NIGHT_RAIN("night_rain"),
  /** {@code night_light_rain}. */
  NIGHT_LIGHT_RAIN("night_light_rain"),
  /** {@code night_heavy_rain}. */
  NIGHT_HEAVY_RAIN("night_heavy_rain"),
  /** {@code night_rain_snow_mix}. */
  NIGHT_RAIN_SNOW_MIX("night_rain_snow_mix"),
  /** {@code night_snow}. */
  NIGHT_SNOW("night_snow"),
  /** {@code night_freezing_rain}. */
  NIGHT_FREEZING_RAIN("night_freezing_rain"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  HourlyIcon(String value) {
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
  public static HourlyIcon fromWireValue(String value) {
    for (HourlyIcon c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
