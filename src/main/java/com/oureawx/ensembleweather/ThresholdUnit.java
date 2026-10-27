// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/**
 * The unit a {@link Threshold} is given in. A threshold in another unit than the request's is
 * converted exactly before sending (1 in = 25.4 mm, 1 ft = 0.3048 m, 1 mph = 0.44704 m/s, °F = °C ×
 * 9/5 + 32, K = °C + 273.15). This converts the caller's input; response values are never
 * converted.
 */
public enum ThresholdUnit {
  /** Millimetres (precipitation, snowfall). */
  MILLIMETRES,
  /** Inches (precipitation, snowfall). */
  INCHES,
  /** Degrees Celsius (temperature). */
  CELSIUS,
  /** Degrees Fahrenheit (temperature). */
  FAHRENHEIT,
  /** Kelvin (temperature). */
  KELVIN,
  /** Metres (snow level). */
  METRES,
  /** Feet (snow level). */
  FEET,
  /** Metres per second (wind speed). */
  METRES_PER_SECOND,
  /** Miles per hour (wind speed). */
  MILES_PER_HOUR
}
