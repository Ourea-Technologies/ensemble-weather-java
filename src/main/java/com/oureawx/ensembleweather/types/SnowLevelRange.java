// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * A day's snow level over its wet hours (m, or ft with {@code units=imperial}): the lowest and
 * highest hourly ensemble-mean snow level among them, and their precipitation-weighted mean. The
 * level is the rain line (the bottom of the melting layer), as {@code EnsembleStats.snow_level} is;
 * the day on the NWS scale is {@code DailyForecast.snow_level_wet_bulb}. {@code
 * DailyForecast.snow_level_wet_bulb_range} is this same object over the same hours on the NWS scale
 * (the height of the 0.5 C wet-bulb).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowLevelRange}.
 */
public final class SnowLevelRange extends Model {
  private final double low;
  private final double high;
  private final double mean;

  private SnowLevelRange(Builder b) {
    this.low = require(b.low, "low");
    this.high = require(b.high, "high");
    this.mean = require(b.mean, "mean");
  }

  /**
   * Returns a new builder.
   *
   * @return an empty builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns a builder holding this object's values.
   *
   * @return a builder to make a modified copy
   */
  public Builder toBuilder() {
    Builder b = new Builder();
    b.low = this.low;
    b.high = this.high;
    b.mean = this.mean;
    return b;
  }

  /**
   * The lowest hourly snow level among the wet hours.
   *
   * @return the {@code low} value
   */
  public double low() {
    return low;
  }

  /**
   * The highest hourly snow level among the wet hours.
   *
   * @return the {@code high} value
   */
  public double high() {
    return high;
  }

  /**
   * The wet hours' snow level, weighted by their precipitation.
   *
   * @return the {@code mean} value
   */
  public double mean() {
    return mean;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {low, high, mean};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"low", "high", "mean"};

  /** Builds a {@link SnowLevelRange}. */
  public static final class Builder {
    private @Nullable Double low;
    private @Nullable Double high;
    private @Nullable Double mean;

    private Builder() {}

    /**
     * Sets {@link SnowLevelRange#low()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder low(double value) {
      this.low = value;
      return this;
    }

    /**
     * Sets {@link SnowLevelRange#high()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder high(double value) {
      this.high = value;
      return this;
    }

    /**
     * Sets {@link SnowLevelRange#mean()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder mean(double value) {
      this.mean = value;
      return this;
    }

    /**
     * Builds the {@link SnowLevelRange}.
     *
     * @return the immutable SnowLevelRange
     * @throws IllegalStateException if a required value was not set
     */
    public SnowLevelRange build() {
      return new SnowLevelRange(this);
    }
  }
}
