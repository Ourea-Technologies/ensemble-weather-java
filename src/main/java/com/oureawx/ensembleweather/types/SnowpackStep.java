// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A SnowpackStep from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackStep}.
 */
public final class SnowpackStep extends Model {
  private final Instant validTime;
  private final String surface;
  private final double newSnow24h;
  private final @Nullable Double newSnowDensityKgM3;
  private final @Nullable Double settlement24h;

  private SnowpackStep(Builder b) {
    this.validTime = require(b.validTime, "valid_time");
    this.surface = require(b.surface, "surface");
    this.newSnow24h = require(b.newSnow24h, "new_snow_24h");
    this.newSnowDensityKgM3 = b.newSnowDensityKgM3;
    this.settlement24h = b.settlement24h;
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
    b.validTime = this.validTime;
    b.surface = this.surface;
    b.newSnow24h = this.newSnow24h;
    b.newSnowDensityKgM3 = this.newSnowDensityKgM3;
    b.settlement24h = this.settlement24h;
    return b;
  }

  /**
   * The {@code valid_time} value.
   *
   * @return the {@code valid_time} value
   */
  public Instant validTime() {
    return validTime;
  }

  /**
   * A key of {@code definitions.surface}: today {@code none}, {@code powder}, {@code
   * wind_affected}, {@code settled}, {@code wet}, {@code crust} or {@code ice_crust}. Tolerate
   * values you do not know.
   *
   * @return the {@code surface} value
   */
  public String surface() {
    return surface;
  }

  /**
   * New snow of the 24 h to {@code valid_time}, as it lies (cm or in).
   *
   * @return the {@code new_snow_24h} value
   */
  public double newSnow24h() {
    return newSnow24h;
  }

  /**
   * Its density. Absent under a trace of new snow.
   *
   * @return the {@code new_snow_density_kg_m3} value, or empty when the response omits it
   */
  public OptionalDouble newSnowDensityKgM3() {
    return newSnowDensityKgM3 == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(newSnowDensityKgM3);
  }

  /**
   * How far the snow on the ground 24 h earlier settled (cm or in). Absent when not stated.
   *
   * @return the {@code settlement_24h} value, or empty when the response omits it
   */
  public OptionalDouble settlement24h() {
    return settlement24h == null ? OptionalDouble.empty() : OptionalDouble.of(settlement24h);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {validTime, surface, newSnow24h, newSnowDensityKgM3, settlement24h};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "validTime", "surface", "newSnow24h", "newSnowDensityKgM3", "settlement24h"
  };

  /** Builds a {@link SnowpackStep}. */
  public static final class Builder {
    private @Nullable Instant validTime;
    private @Nullable String surface;
    private @Nullable Double newSnow24h;
    private @Nullable Double newSnowDensityKgM3;
    private @Nullable Double settlement24h;

    private Builder() {}

    /**
     * Sets {@link SnowpackStep#validTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder validTime(Instant value) {
      this.validTime = value;
      return this;
    }

    /**
     * Sets {@link SnowpackStep#surface()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder surface(String value) {
      this.surface = value;
      return this;
    }

    /**
     * Sets {@link SnowpackStep#newSnow24h()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder newSnow24h(double value) {
      this.newSnow24h = value;
      return this;
    }

    /**
     * Sets {@link SnowpackStep#newSnowDensityKgM3()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder newSnowDensityKgM3(@Nullable Double value) {
      this.newSnowDensityKgM3 = value;
      return this;
    }

    /**
     * Sets {@link SnowpackStep#settlement24h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder settlement24h(@Nullable Double value) {
      this.settlement24h = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackStep}.
     *
     * @return the immutable SnowpackStep
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackStep build() {
      return new SnowpackStep(this);
    }
  }
}
