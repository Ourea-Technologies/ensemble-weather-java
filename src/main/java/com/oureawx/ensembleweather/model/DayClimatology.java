// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * Served as a daily row's {@code temperature_climatology}. Seasonal temperature normal for this day
 * at the forecast point (all-hours mean and standard deviation over a ±15-day window around the
 * date), resolved the way verification resolves a station's reference: the nearest climatology
 * station's own normal when one lies within 60 km, else the ERA5 0.25° cell the point lies in.
 * Units follow the request's {@code units} parameter (°C metric, °F imperial). Omitted only outside
 * the climatology grid or before the normals are loaded. {@code std} includes the diurnal range, so
 * it is a wide, climate-scale spread — useful for bounding a forecast against the local climate,
 * not for tight day-to-day comparison.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code DayClimatology}.
 */
public final class DayClimatology extends Model {
  private final double mean;
  private final double std;

  private DayClimatology(Builder b) {
    this.mean = require(b.mean, "mean");
    this.std = require(b.std, "std");
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
    b.mean = this.mean;
    b.std = this.std;
    return b;
  }

  /**
   * All-hours seasonal mean temperature.
   *
   * @return the {@code mean} value
   */
  public double mean() {
    return mean;
  }

  /**
   * Seasonal standard deviation (includes the diurnal range).
   *
   * @return the {@code std} value
   */
  public double std() {
    return std;
  }

  @Override
  Object[] values() {
    return new Object[] {mean, std};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"mean", "std"};

  /** Builds a {@link DayClimatology}. */
  public static final class Builder {
    private @Nullable Double mean;
    private @Nullable Double std;

    private Builder() {}

    /**
     * Sets {@link DayClimatology#mean()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder mean(double value) {
      this.mean = value;
      return this;
    }

    /**
     * Sets {@link DayClimatology#std()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder std(double value) {
      this.std = value;
      return this;
    }

    /**
     * Builds the {@link DayClimatology}.
     *
     * @return the immutable DayClimatology
     * @throws IllegalStateException if a required value was not set
     */
    public DayClimatology build() {
      return new DayClimatology(this);
    }
  }
}
