// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A legacy compact hourly point of an elevation forecast.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ElevationTimeSeriesPoint}.
 */
public final class ElevationTimeSeriesPoint extends Model {
  private final Instant validTime;
  private final int leadTimeHours;
  private final Quantiles temperature;
  private final @Nullable Quantiles snowLevelDelta;
  private final @Nullable Quantiles windSpeed;
  private final @Nullable Quantiles windGust;
  private final Quantiles precipitation;
  private final String condition;
  private final String conditionText;
  private final String conditionIcon;

  private ElevationTimeSeriesPoint(Builder b) {
    this.validTime = require(b.validTime, "valid_time");
    this.leadTimeHours = require(b.leadTimeHours, "lead_time_hours");
    this.temperature = require(b.temperature, "temperature");
    this.snowLevelDelta = b.snowLevelDelta;
    this.windSpeed = b.windSpeed;
    this.windGust = b.windGust;
    this.precipitation = require(b.precipitation, "precipitation");
    this.condition = require(b.condition, "condition");
    this.conditionText = require(b.conditionText, "condition_text");
    this.conditionIcon = require(b.conditionIcon, "condition_icon");
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
    b.leadTimeHours = this.leadTimeHours;
    b.temperature = this.temperature;
    b.snowLevelDelta = this.snowLevelDelta;
    b.windSpeed = this.windSpeed;
    b.windGust = this.windGust;
    b.precipitation = this.precipitation;
    b.condition = this.condition;
    b.conditionText = this.conditionText;
    b.conditionIcon = this.conditionIcon;
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
   * The {@code lead_time_hours} value.
   *
   * @return the {@code lead_time_hours} value
   */
  public int leadTimeHours() {
    return leadTimeHours;
  }

  /**
   * The {@code temperature} value.
   *
   * @return the {@code temperature} value
   */
  public Quantiles temperature() {
    return temperature;
  }

  /**
   * Elevation minus snow level (positive = above the snow level = snow expected; negative = below
   * it = rain).
   *
   * @return the {@code snow_level_delta} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowLevelDelta() {
    return Optional.ofNullable(snowLevelDelta);
  }

  /**
   * The variant's served wind speed ({@code hourly[].ensemble.wind_speed}).
   *
   * @return the {@code wind_speed} value, or empty when the response omits it
   */
  public Optional<Quantiles> windSpeed() {
    return Optional.ofNullable(windSpeed);
  }

  /**
   * The variant's served gust ({@code hourly[].ensemble.wind_gust}); absent at lead 0, as on the
   * primary.
   *
   * @return the {@code wind_gust} value, or empty when the response omits it
   */
  public Optional<Quantiles> windGust() {
    return Optional.ofNullable(windGust);
  }

  /**
   * The variant's served hourly precipitation ({@code hourly[].ensemble.precipitation}), the same
   * shape as the primary's, including the occurrence {@code probability}. Prefer {@code p50} (or
   * {@code probability}) over {@code mean} for display.
   *
   * @return the {@code precipitation} value
   */
  public Quantiles precipitation() {
    return precipitation;
  }

  /**
   * The machine code, as {@code TimeSeriesPoint.condition}.
   *
   * @return the {@code condition} value ({@link HourlyCondition#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public HourlyCondition condition() {
    return HourlyCondition.fromWireValue(condition);
  }

  /**
   * The {@code condition} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code condition} string
   */
  public String conditionAsString() {
    return condition;
  }

  /**
   * The variant's served condition ({@code hourly[].condition}), derived as for the primary's hour
   * (see {@code TimeSeriesPoint.condition}): a dry hour above the snow level is a sky condition,
   * not snow.
   *
   * @return the {@code condition_text} value
   */
  public String conditionText() {
    return conditionText;
  }

  /**
   * The variant's served icon hint ({@code hourly[].condition_icon}; see {@code
   * TimeSeriesPoint.condition_icon}).
   *
   * @return the {@code condition_icon} value ({@link HourlyIcon#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public HourlyIcon conditionIcon() {
    return HourlyIcon.fromWireValue(conditionIcon);
  }

  /**
   * The {@code condition_icon} value exactly as the API sent it, even when this SDK version does
   * not know it.
   *
   * @return the raw {@code condition_icon} string
   */
  public String conditionIconAsString() {
    return conditionIcon;
  }

  @Override
  Object[] values() {
    return new Object[] {
      validTime,
      leadTimeHours,
      temperature,
      snowLevelDelta,
      windSpeed,
      windGust,
      precipitation,
      condition,
      conditionText,
      conditionIcon
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "validTime",
    "leadTimeHours",
    "temperature",
    "snowLevelDelta",
    "windSpeed",
    "windGust",
    "precipitation",
    "condition",
    "conditionText",
    "conditionIcon"
  };

  /** Builds a {@link ElevationTimeSeriesPoint}. */
  public static final class Builder {
    private @Nullable Instant validTime;
    private @Nullable Integer leadTimeHours;
    private @Nullable Quantiles temperature;
    private @Nullable Quantiles snowLevelDelta;
    private @Nullable Quantiles windSpeed;
    private @Nullable Quantiles windGust;
    private @Nullable Quantiles precipitation;
    private @Nullable String condition;
    private @Nullable String conditionText;
    private @Nullable String conditionIcon;

    private Builder() {}

    /**
     * Sets {@link ElevationTimeSeriesPoint#validTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder validTime(Instant value) {
      this.validTime = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#leadTimeHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder leadTimeHours(int value) {
      this.leadTimeHours = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#temperature()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder temperature(Quantiles value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#snowLevelDelta()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevelDelta(@Nullable Quantiles value) {
      this.snowLevelDelta = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#windSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windSpeed(@Nullable Quantiles value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#windGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windGust(@Nullable Quantiles value) {
      this.windGust = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#precipitation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipitation(Quantiles value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#condition()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder condition(HourlyCondition value) {
      this.condition = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#conditionAsString()}, the raw wire value, kept verbatim
     * even when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder conditionAsString(String value) {
      this.condition = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#conditionText()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionText(String value) {
      this.conditionText = value;
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#conditionIcon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionIcon(HourlyIcon value) {
      this.conditionIcon = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ElevationTimeSeriesPoint#conditionIconAsString()}, the raw wire value, kept
     * verbatim even when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder conditionIconAsString(String value) {
      this.conditionIcon = value;
      return this;
    }

    /**
     * Builds the {@link ElevationTimeSeriesPoint}.
     *
     * @return the immutable ElevationTimeSeriesPoint
     * @throws IllegalStateException if a required value was not set
     */
    public ElevationTimeSeriesPoint build() {
      return new ElevationTimeSeriesPoint(this);
    }
  }
}
