// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A bearing's distribution stated on the circle. Weather APIs serve a direction as one number; the
 * statistical convention for a direction DISTRIBUTION is a mean direction with a dispersion beside
 * it and intervals as clockwise arcs. Degrees in both unit systems.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CircularStats}.
 */
public final class CircularStats extends Model {
  private final double mean;
  private final @Nullable Double stddev;
  private final Sector sector50;
  private final Sector sector90;

  private CircularStats(Builder b) {
    this.mean = require(b.mean, "mean");
    this.stddev = b.stddev;
    this.sector50 = require(b.sector50, "sector_50");
    this.sector90 = require(b.sector90, "sector_90");
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
    b.stddev = this.stddev;
    b.sector50 = this.sector50;
    b.sector90 = this.sector90;
    return b;
  }

  /**
   * The resultant-vector mean direction, degrees clockwise from north, in [0, 360). Equals {@code
   * wind_direction.mean}.
   *
   * @return the {@code mean} value
   */
  public double mean() {
    return mean;
  }

  /**
   * Standard deviation of the members' signed angular offsets from {@code mean}, degrees. Exact for
   * a concentrated distribution and never a difference of two bearings. Absent on a single-member
   * hour.
   *
   * @return the {@code stddev} value, or empty when the response omits it
   */
  public OptionalDouble stddev() {
    return stddev == null ? OptionalDouble.empty() : OptionalDouble.of(stddev);
  }

  /**
   * The central 50 % of the members — the clockwise arc from the 25th to the 75th circular
   * percentile.
   *
   * @return the {@code sector_50} value
   */
  public Sector sector50() {
    return sector50;
  }

  /**
   * The central 90 % of the members — the clockwise arc from the 5th to the 95th circular
   * percentile.
   *
   * @return the {@code sector_90} value
   */
  public Sector sector90() {
    return sector90;
  }

  @Override
  Object[] values() {
    return new Object[] {mean, stddev, sector50, sector90};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"mean", "stddev", "sector50", "sector90"};

  /** Builds a {@link CircularStats}. */
  public static final class Builder {
    private @Nullable Double mean;
    private @Nullable Double stddev;
    private @Nullable Sector sector50;
    private @Nullable Sector sector90;

    private Builder() {}

    /**
     * Sets {@link CircularStats#mean()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder mean(double value) {
      this.mean = value;
      return this;
    }

    /**
     * Sets {@link CircularStats#stddev()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder stddev(@Nullable Double value) {
      this.stddev = value;
      return this;
    }

    /**
     * Sets {@link CircularStats#sector50()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder sector50(Sector value) {
      this.sector50 = value;
      return this;
    }

    /**
     * Sets {@link CircularStats#sector90()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder sector90(Sector value) {
      this.sector90 = value;
      return this;
    }

    /**
     * Builds the {@link CircularStats}.
     *
     * @return the immutable CircularStats
     * @throws IllegalStateException if a required value was not set
     */
    public CircularStats build() {
      return new CircularStats(this);
    }
  }
}
