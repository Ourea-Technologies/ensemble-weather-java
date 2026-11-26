// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * The event behind each served probability, in the request's units, so no client hard-codes them:
 * P(amount &gt;= the threshold).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ProbabilityThresholds}.
 */
public final class ProbabilityThresholds extends Model {
  private final double hourlyPrecipitation;
  private final double hourlySnowfall;
  private final double dailyPrecipitation;
  private final double dailyPrecipitationOccurrence;
  private final double dailySnow;

  private ProbabilityThresholds(Builder b) {
    this.hourlyPrecipitation = require(b.hourlyPrecipitation, "hourly_precipitation");
    this.hourlySnowfall = require(b.hourlySnowfall, "hourly_snowfall");
    this.dailyPrecipitation = require(b.dailyPrecipitation, "daily_precipitation_probability");
    this.dailyPrecipitationOccurrence =
        require(b.dailyPrecipitationOccurrence, "daily_precipitation_occurrence_probability");
    this.dailySnow = require(b.dailySnow, "daily_snow_probability");
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
    b.hourlyPrecipitation = this.hourlyPrecipitation;
    b.hourlySnowfall = this.hourlySnowfall;
    b.dailyPrecipitation = this.dailyPrecipitation;
    b.dailyPrecipitationOccurrence = this.dailyPrecipitationOccurrence;
    b.dailySnow = this.dailySnow;
    return b;
  }

  /**
   * {@code ensemble.precipitation.probability}'s threshold (0.254 mm).
   *
   * @return the {@code hourly_precipitation} value
   */
  public double hourlyPrecipitation() {
    return hourlyPrecipitation;
  }

  /**
   * {@code ensemble.snowfall.probability}'s threshold (0.254 mm).
   *
   * @return the {@code hourly_snowfall} value
   */
  public double hourlySnowfall() {
    return hourlySnowfall;
  }

  /**
   * (JSON {@code daily_precipitation_probability}) {@code daily[].precipitation_probability}'s
   * day-total threshold (2.5 mm).
   *
   * @return the {@code daily_precipitation_probability} value
   */
  public double dailyPrecipitation() {
    return dailyPrecipitation;
  }

  /**
   * (JSON {@code daily_precipitation_occurrence_probability}) {@code
   * daily[].precipitation_occurrence_probability}'s threshold (1.0 mm), also the "if wet"
   * condition.
   *
   * @return the {@code daily_precipitation_occurrence_probability} value
   */
  public double dailyPrecipitationOccurrence() {
    return dailyPrecipitationOccurrence;
  }

  /**
   * (JSON {@code daily_snow_probability}) {@code daily[].snow_probability}'s day-snowfall threshold
   * (2.5 mm).
   *
   * @return the {@code daily_snow_probability} value
   */
  public double dailySnow() {
    return dailySnow;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      hourlyPrecipitation,
      hourlySnowfall,
      dailyPrecipitation,
      dailyPrecipitationOccurrence,
      dailySnow
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "hourlyPrecipitation",
    "hourlySnowfall",
    "dailyPrecipitation",
    "dailyPrecipitationOccurrence",
    "dailySnow"
  };

  /** Builds a {@link ProbabilityThresholds}. */
  public static final class Builder {
    private @Nullable Double hourlyPrecipitation;
    private @Nullable Double hourlySnowfall;
    private @Nullable Double dailyPrecipitation;
    private @Nullable Double dailyPrecipitationOccurrence;
    private @Nullable Double dailySnow;

    private Builder() {}

    /**
     * Sets {@link ProbabilityThresholds#hourlyPrecipitation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder hourlyPrecipitation(double value) {
      this.hourlyPrecipitation = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityThresholds#hourlySnowfall()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder hourlySnowfall(double value) {
      this.hourlySnowfall = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityThresholds#dailyPrecipitation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder dailyPrecipitation(double value) {
      this.dailyPrecipitation = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityThresholds#dailyPrecipitationOccurrence()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder dailyPrecipitationOccurrence(double value) {
      this.dailyPrecipitationOccurrence = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityThresholds#dailySnow()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder dailySnow(double value) {
      this.dailySnow = value;
      return this;
    }

    /**
     * Builds the {@link ProbabilityThresholds}.
     *
     * @return the immutable ProbabilityThresholds
     * @throws IllegalStateException if a required value was not set
     */
    public ProbabilityThresholds build() {
      return new ProbabilityThresholds(this);
    }
  }
}
