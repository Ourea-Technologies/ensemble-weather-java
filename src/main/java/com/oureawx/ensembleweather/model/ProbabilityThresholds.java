// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

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
  private final double dailyPrecipProbability;
  private final double dailyPrecipOccurrenceProbability;
  private final double dailySnowProbability;

  private ProbabilityThresholds(Builder b) {
    this.hourlyPrecipitation = require(b.hourlyPrecipitation, "hourly_precipitation");
    this.hourlySnowfall = require(b.hourlySnowfall, "hourly_snowfall");
    this.dailyPrecipProbability = require(b.dailyPrecipProbability, "daily_precip_probability");
    this.dailyPrecipOccurrenceProbability =
        require(b.dailyPrecipOccurrenceProbability, "daily_precip_occurrence_probability");
    this.dailySnowProbability = require(b.dailySnowProbability, "daily_snow_probability");
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
    b.dailyPrecipProbability = this.dailyPrecipProbability;
    b.dailyPrecipOccurrenceProbability = this.dailyPrecipOccurrenceProbability;
    b.dailySnowProbability = this.dailySnowProbability;
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
   * {@code daily[].precip_probability}'s day-total threshold (2.5 mm).
   *
   * @return the {@code daily_precip_probability} value
   */
  public double dailyPrecipProbability() {
    return dailyPrecipProbability;
  }

  /**
   * {@code daily[].precip_occurrence_probability}'s threshold (1.0 mm), also the "if wet"
   * condition.
   *
   * @return the {@code daily_precip_occurrence_probability} value
   */
  public double dailyPrecipOccurrenceProbability() {
    return dailyPrecipOccurrenceProbability;
  }

  /**
   * {@code daily[].snow_probability}'s day-snowfall threshold (2.5 mm).
   *
   * @return the {@code daily_snow_probability} value
   */
  public double dailySnowProbability() {
    return dailySnowProbability;
  }

  @Override
  Object[] values() {
    return new Object[] {
      hourlyPrecipitation,
      hourlySnowfall,
      dailyPrecipProbability,
      dailyPrecipOccurrenceProbability,
      dailySnowProbability
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "hourlyPrecipitation",
    "hourlySnowfall",
    "dailyPrecipProbability",
    "dailyPrecipOccurrenceProbability",
    "dailySnowProbability"
  };

  /** Builds a {@link ProbabilityThresholds}. */
  public static final class Builder {
    private @Nullable Double hourlyPrecipitation;
    private @Nullable Double hourlySnowfall;
    private @Nullable Double dailyPrecipProbability;
    private @Nullable Double dailyPrecipOccurrenceProbability;
    private @Nullable Double dailySnowProbability;

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
     * Sets {@link ProbabilityThresholds#dailyPrecipProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder dailyPrecipProbability(double value) {
      this.dailyPrecipProbability = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityThresholds#dailyPrecipOccurrenceProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder dailyPrecipOccurrenceProbability(double value) {
      this.dailyPrecipOccurrenceProbability = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityThresholds#dailySnowProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder dailySnowProbability(double value) {
      this.dailySnowProbability = value;
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
