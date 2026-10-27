// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * A verified storm event summary published to the public {@code /api/v1/storm-reports} surface.
 * Pairs forecast vs observed totals with a composite skill score and headline, ready for display.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code StormReportCard}.
 */
public final class StormReportCard extends Model {
  private final long id;
  private final String locationName;
  private final String eventType;
  private final Instant onsetTime;
  private final Instant endTime;
  private final double forecastTotalMm;
  private final double observedTotalMm;
  private final @Nullable Double forecastSnowfallMm;
  private final @Nullable Double observedSnowfallMm;
  private final @Nullable String bestModel;
  private final @Nullable Integer bestLeadTimeHours;
  private final @Nullable Double compositeScore;
  private final @Nullable String headline;
  private final ResponseMetadata metadata;

  private StormReportCard(Builder b) {
    this.id = require(b.id, "id");
    this.locationName = require(b.locationName, "location_name");
    this.eventType = require(b.eventType, "event_type");
    this.onsetTime = require(b.onsetTime, "onset_time");
    this.endTime = require(b.endTime, "end_time");
    this.forecastTotalMm = require(b.forecastTotalMm, "forecast_total_mm");
    this.observedTotalMm = require(b.observedTotalMm, "observed_total_mm");
    this.forecastSnowfallMm = b.forecastSnowfallMm;
    this.observedSnowfallMm = b.observedSnowfallMm;
    this.bestModel = b.bestModel;
    this.bestLeadTimeHours = b.bestLeadTimeHours;
    this.compositeScore = b.compositeScore;
    this.headline = b.headline;
    this.metadata = b.metadata;
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
    b.id = this.id;
    b.locationName = this.locationName;
    b.eventType = this.eventType;
    b.onsetTime = this.onsetTime;
    b.endTime = this.endTime;
    b.forecastTotalMm = this.forecastTotalMm;
    b.observedTotalMm = this.observedTotalMm;
    b.forecastSnowfallMm = this.forecastSnowfallMm;
    b.observedSnowfallMm = this.observedSnowfallMm;
    b.bestModel = this.bestModel;
    b.bestLeadTimeHours = this.bestLeadTimeHours;
    b.compositeScore = this.compositeScore;
    b.headline = this.headline;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code id} value.
   *
   * @return the {@code id} value
   */
  public long id() {
    return id;
  }

  /**
   * The {@code location_name} value.
   *
   * @return the {@code location_name} value
   */
  public String locationName() {
    return locationName;
  }

  /**
   * Precipitation regime (e.g., {@code snowfall}, {@code rainfall}, {@code atmospheric_river}).
   *
   * @return the {@code event_type} value
   */
  public String eventType() {
    return eventType;
  }

  /**
   * The {@code onset_time} value.
   *
   * @return the {@code onset_time} value
   */
  public Instant onsetTime() {
    return onsetTime;
  }

  /**
   * The {@code end_time} value.
   *
   * @return the {@code end_time} value
   */
  public Instant endTime() {
    return endTime;
  }

  /**
   * The {@code forecast_total_mm} value.
   *
   * @return the {@code forecast_total_mm} value
   */
  public double forecastTotalMm() {
    return forecastTotalMm;
  }

  /**
   * The {@code observed_total_mm} value.
   *
   * @return the {@code observed_total_mm} value
   */
  public double observedTotalMm() {
    return observedTotalMm;
  }

  /**
   * The {@code forecast_snowfall_mm} value.
   *
   * @return the {@code forecast_snowfall_mm} value, or empty when the response omits it
   */
  public OptionalDouble forecastSnowfallMm() {
    return forecastSnowfallMm == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(forecastSnowfallMm);
  }

  /**
   * The {@code observed_snowfall_mm} value.
   *
   * @return the {@code observed_snowfall_mm} value, or empty when the response omits it
   */
  public OptionalDouble observedSnowfallMm() {
    return observedSnowfallMm == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(observedSnowfallMm);
  }

  /**
   * Model with the highest composite score for this event.
   *
   * @return the {@code best_model} value, or empty when the response omits it
   */
  public Optional<String> bestModel() {
    return Optional.ofNullable(bestModel);
  }

  /**
   * The {@code best_lead_time_hours} value.
   *
   * @return the {@code best_lead_time_hours} value, or empty when the response omits it
   */
  public OptionalInt bestLeadTimeHours() {
    return bestLeadTimeHours == null ? OptionalInt.empty() : OptionalInt.of(bestLeadTimeHours);
  }

  /**
   * 0-1 skill score (higher = better).
   *
   * @return the {@code composite_score} value, or empty when the response omits it
   */
  public OptionalDouble compositeScore() {
    return compositeScore == null ? OptionalDouble.empty() : OptionalDouble.of(compositeScore);
  }

  /**
   * The {@code headline} value.
   *
   * @return the {@code headline} value, or empty when the response omits it
   */
  public Optional<String> headline() {
    return Optional.ofNullable(headline);
  }

  /**
   * What the HTTP exchange said about this response: rate-limit state, request id, and whether it
   * was revalidated from the local cache. Not part of {@link #equals(Object)}.
   *
   * @return the response metadata
   */
  public ResponseMetadata metadata() {
    return metadata;
  }

  @Override
  Object[] values() {
    return new Object[] {
      id,
      locationName,
      eventType,
      onsetTime,
      endTime,
      forecastTotalMm,
      observedTotalMm,
      forecastSnowfallMm,
      observedSnowfallMm,
      bestModel,
      bestLeadTimeHours,
      compositeScore,
      headline
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id",
    "locationName",
    "eventType",
    "onsetTime",
    "endTime",
    "forecastTotalMm",
    "observedTotalMm",
    "forecastSnowfallMm",
    "observedSnowfallMm",
    "bestModel",
    "bestLeadTimeHours",
    "compositeScore",
    "headline"
  };

  /** Builds a {@link StormReportCard}. */
  public static final class Builder {
    private @Nullable Long id;
    private @Nullable String locationName;
    private @Nullable String eventType;
    private @Nullable Instant onsetTime;
    private @Nullable Instant endTime;
    private @Nullable Double forecastTotalMm;
    private @Nullable Double observedTotalMm;
    private @Nullable Double forecastSnowfallMm;
    private @Nullable Double observedSnowfallMm;
    private @Nullable String bestModel;
    private @Nullable Integer bestLeadTimeHours;
    private @Nullable Double compositeScore;
    private @Nullable String headline;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link StormReportCard#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(long value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#locationName()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder locationName(String value) {
      this.locationName = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#eventType()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder eventType(String value) {
      this.eventType = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#onsetTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder onsetTime(Instant value) {
      this.onsetTime = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#endTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder endTime(Instant value) {
      this.endTime = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#forecastTotalMm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder forecastTotalMm(double value) {
      this.forecastTotalMm = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#observedTotalMm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder observedTotalMm(double value) {
      this.observedTotalMm = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#forecastSnowfallMm()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder forecastSnowfallMm(@Nullable Double value) {
      this.forecastSnowfallMm = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#observedSnowfallMm()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder observedSnowfallMm(@Nullable Double value) {
      this.observedSnowfallMm = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#bestModel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder bestModel(@Nullable String value) {
      this.bestModel = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#bestLeadTimeHours()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder bestLeadTimeHours(@Nullable Integer value) {
      this.bestLeadTimeHours = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#compositeScore()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder compositeScore(@Nullable Double value) {
      this.compositeScore = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#headline()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder headline(@Nullable String value) {
      this.headline = value;
      return this;
    }

    /**
     * Sets {@link StormReportCard#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link StormReportCard}.
     *
     * @return the immutable StormReportCard
     * @throws IllegalStateException if a required value was not set
     */
    public StormReportCard build() {
      return new StormReportCard(this);
    }
  }
}
