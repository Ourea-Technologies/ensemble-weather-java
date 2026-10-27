// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The threshold of a {@link ProbabilityTerm}: a number, with or without an explicit unit. Without
 * one it is in the request's units; with one, the SDK converts it to the request's units exactly
 * and then rounds it as the server does (round half away from zero at the variable's served
 * precision).
 *
 * <pre>{@code
 * Threshold.inches(6)        // 6 in: 152.40 on a metric request, 6.000 on an imperial one
 * Threshold.fahrenheit(32)   // 0.0 on a metric request
 * Threshold.of(10)           // 10 in whatever units the request uses
 * }</pre>
 */
public final class Threshold {
  private final double value;
  private final @Nullable ThresholdUnit unit;

  private Threshold(double value, @Nullable ThresholdUnit unit) {
    if (!Double.isFinite(value)) {
      throw new UsageException("a threshold must be a finite number, not " + value);
    }
    this.value = value;
    this.unit = unit;
  }

  /**
   * A threshold in the request's own units.
   *
   * @param value the number
   * @return the threshold
   */
  public static Threshold of(double value) {
    return new Threshold(value, null);
  }

  /**
   * A threshold in an explicit unit.
   *
   * @param value the number
   * @param unit its unit
   * @return the threshold
   */
  public static Threshold of(double value, ThresholdUnit unit) {
    return new Threshold(value, Objects.requireNonNull(unit, "unit"));
  }

  /**
   * Millimetres.
   *
   * @param value the amount
   * @return the threshold
   */
  public static Threshold millimetres(double value) {
    return of(value, ThresholdUnit.MILLIMETRES);
  }

  /**
   * Inches.
   *
   * @param value the amount
   * @return the threshold
   */
  public static Threshold inches(double value) {
    return of(value, ThresholdUnit.INCHES);
  }

  /**
   * Degrees Celsius.
   *
   * @param value the temperature
   * @return the threshold
   */
  public static Threshold celsius(double value) {
    return of(value, ThresholdUnit.CELSIUS);
  }

  /**
   * Degrees Fahrenheit.
   *
   * @param value the temperature
   * @return the threshold
   */
  public static Threshold fahrenheit(double value) {
    return of(value, ThresholdUnit.FAHRENHEIT);
  }

  /**
   * Kelvin.
   *
   * @param value the temperature
   * @return the threshold
   */
  public static Threshold kelvin(double value) {
    return of(value, ThresholdUnit.KELVIN);
  }

  /**
   * Metres.
   *
   * @param value the height
   * @return the threshold
   */
  public static Threshold metres(double value) {
    return of(value, ThresholdUnit.METRES);
  }

  /**
   * Feet.
   *
   * @param value the height
   * @return the threshold
   */
  public static Threshold feet(double value) {
    return of(value, ThresholdUnit.FEET);
  }

  /**
   * Metres per second.
   *
   * @param value the speed
   * @return the threshold
   */
  public static Threshold metresPerSecond(double value) {
    return of(value, ThresholdUnit.METRES_PER_SECOND);
  }

  /**
   * Miles per hour.
   *
   * @param value the speed
   * @return the threshold
   */
  public static Threshold milesPerHour(double value) {
    return of(value, ThresholdUnit.MILES_PER_HOUR);
  }

  /**
   * The number as given.
   *
   * @return the value
   */
  public double value() {
    return value;
  }

  /**
   * The explicit unit.
   *
   * @return the unit, or empty for "in the request's units"
   */
  public Optional<ThresholdUnit> unit() {
    return Optional.ofNullable(unit);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof Threshold
        && Double.compare(((Threshold) o).value, value) == 0
        && ((Threshold) o).unit == unit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, unit);
  }

  @Override
  public String toString() {
    return unit == null ? Double.toString(value) : value + " " + unit;
  }
}
