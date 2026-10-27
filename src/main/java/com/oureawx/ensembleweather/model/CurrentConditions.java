// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * "Right now" as one flat object: a view of ONE served hourly point, never a second forecast.
 * {@code time} names the hour (the served hour nearest the request time, within one hour of it —
 * the window in which the nowcast blend's time taper is at full weight); every value is copied from
 * that hour, in the request's {@code units}:.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CurrentConditions}.
 */
public final class CurrentConditions extends Model {
  private final Instant time;
  private final double temperature;
  private final @Nullable Double feelsLike;
  private final String condition;
  private final String conditionText;
  private final String conditionIcon;
  private final double windSpeed;
  private final @Nullable Double windGust;
  private final double windDirection;
  private final @Nullable Double relativeHumidity;
  private final @Nullable Double dewpoint;
  private final @Nullable Double precipProbability;
  private final String source;
  private final @Nullable CurrentStation station;

  private CurrentConditions(Builder b) {
    this.time = require(b.time, "time");
    this.temperature = require(b.temperature, "temperature");
    this.feelsLike = b.feelsLike;
    this.condition = require(b.condition, "condition");
    this.conditionText = require(b.conditionText, "condition_text");
    this.conditionIcon = require(b.conditionIcon, "condition_icon");
    this.windSpeed = require(b.windSpeed, "wind_speed");
    this.windGust = b.windGust;
    this.windDirection = require(b.windDirection, "wind_direction");
    this.relativeHumidity = b.relativeHumidity;
    this.dewpoint = b.dewpoint;
    this.precipProbability = b.precipProbability;
    this.source = require(b.source, "source");
    this.station = b.station;
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
    b.time = this.time;
    b.temperature = this.temperature;
    b.feelsLike = this.feelsLike;
    b.condition = this.condition;
    b.conditionText = this.conditionText;
    b.conditionIcon = this.conditionIcon;
    b.windSpeed = this.windSpeed;
    b.windGust = this.windGust;
    b.windDirection = this.windDirection;
    b.relativeHumidity = this.relativeHumidity;
    b.dewpoint = this.dewpoint;
    b.precipProbability = this.precipProbability;
    b.source = this.source;
    b.station = this.station;
    return b;
  }

  /**
   * {@code valid_time} of the hour the block is read from.
   *
   * @return the {@code time} value
   */
  public Instant time() {
    return time;
  }

  /**
   * °C (metric), °F (imperial), K (kelvin).
   *
   * @return the {@code temperature} value
   */
  public double temperature() {
    return temperature;
  }

  /**
   * Same unit as {@code temperature}.
   *
   * @return the {@code feels_like} value, or empty when the response omits it
   */
  public OptionalDouble feelsLike() {
    return feelsLike == null ? OptionalDouble.empty() : OptionalDouble.of(feelsLike);
  }

  /**
   * The hour's stable condition code, as {@code TimeSeriesPoint.condition} ({@code partly_cloudy}).
   * Treat unknown codes as additive.
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
   * The display text for {@code condition}, as {@code TimeSeriesPoint.condition_text}.
   *
   * @return the {@code condition_text} value
   */
  public String conditionText() {
    return conditionText;
  }

  /**
   * The {@code condition_icon} value.
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

  /**
   * M/s (metric, kelvin) or mph (imperial).
   *
   * @return the {@code wind_speed} value
   */
  public double windSpeed() {
    return windSpeed;
  }

  /**
   * Same unit as {@code wind_speed}. Omitted where the hour serves no gust.
   *
   * @return the {@code wind_gust} value, or empty when the response omits it
   */
  public OptionalDouble windGust() {
    return windGust == null ? OptionalDouble.empty() : OptionalDouble.of(windGust);
  }

  /**
   * Degrees clockwise from north, direction the wind blows FROM.
   *
   * @return the {@code wind_direction} value
   */
  public double windDirection() {
    return windDirection;
  }

  /**
   * Relative humidity, percent (0–100).
   *
   * @return the {@code relative_humidity} value, or empty when the response omits it
   */
  public OptionalDouble relativeHumidity() {
    return relativeHumidity == null ? OptionalDouble.empty() : OptionalDouble.of(relativeHumidity);
  }

  /**
   * Same unit as {@code temperature}.
   *
   * @return the {@code dewpoint} value, or empty when the response omits it
   */
  public OptionalDouble dewpoint() {
    return dewpoint == null ? OptionalDouble.empty() : OptionalDouble.of(dewpoint);
  }

  /**
   * Probability (0–1) of at least a 0.1 mm trace in the hour.
   *
   * @return the {@code precip_probability} value, or empty when the response omits it
   */
  public OptionalDouble precipProbability() {
    return precipProbability == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(precipProbability);
  }

  /**
   * {@code nowcast} — at least one live observation (surface station, MRMS radar, GOES cloud mask)
   * carried weight at this hour. {@code model} — none did; the hour is the calibrated forecast
   * alone.
   *
   * @return the {@code source} value ({@link ConditionsSource#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public ConditionsSource source() {
    return ConditionsSource.fromWireValue(source);
  }

  /**
   * The {@code source} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code source} string
   */
  public String sourceAsString() {
    return source;
  }

  /**
   * The surface station whose reading anchored temperature, dewpoint and wind at this hour. Omitted
   * when none did.
   *
   * @return the {@code station} value, or empty when the response omits it
   */
  public Optional<CurrentStation> station() {
    return Optional.ofNullable(station);
  }

  @Override
  Object[] values() {
    return new Object[] {
      time,
      temperature,
      feelsLike,
      condition,
      conditionText,
      conditionIcon,
      windSpeed,
      windGust,
      windDirection,
      relativeHumidity,
      dewpoint,
      precipProbability,
      source,
      station
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "time",
    "temperature",
    "feelsLike",
    "condition",
    "conditionText",
    "conditionIcon",
    "windSpeed",
    "windGust",
    "windDirection",
    "relativeHumidity",
    "dewpoint",
    "precipProbability",
    "source",
    "station"
  };

  /** Builds a {@link CurrentConditions}. */
  public static final class Builder {
    private @Nullable Instant time;
    private @Nullable Double temperature;
    private @Nullable Double feelsLike;
    private @Nullable String condition;
    private @Nullable String conditionText;
    private @Nullable String conditionIcon;
    private @Nullable Double windSpeed;
    private @Nullable Double windGust;
    private @Nullable Double windDirection;
    private @Nullable Double relativeHumidity;
    private @Nullable Double dewpoint;
    private @Nullable Double precipProbability;
    private @Nullable String source;
    private @Nullable CurrentStation station;

    private Builder() {}

    /**
     * Sets {@link CurrentConditions#time()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder time(Instant value) {
      this.time = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#temperature()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder temperature(double value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#feelsLike()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder feelsLike(@Nullable Double value) {
      this.feelsLike = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#condition()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder condition(HourlyCondition value) {
      this.condition = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CurrentConditions#conditionAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder conditionAsString(String value) {
      this.condition = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#conditionText()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionText(String value) {
      this.conditionText = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#conditionIcon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionIcon(HourlyIcon value) {
      this.conditionIcon = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CurrentConditions#conditionIconAsString()}, the raw wire value, kept verbatim
     * even when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder conditionIconAsString(String value) {
      this.conditionIcon = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#windSpeed()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windSpeed(double value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#windGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windGust(@Nullable Double value) {
      this.windGust = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#windDirection()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windDirection(double value) {
      this.windDirection = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#relativeHumidity()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder relativeHumidity(@Nullable Double value) {
      this.relativeHumidity = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#dewpoint()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder dewpoint(@Nullable Double value) {
      this.dewpoint = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#precipProbability()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipProbability(@Nullable Double value) {
      this.precipProbability = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(ConditionsSource value) {
      this.source = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CurrentConditions#sourceAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder sourceAsString(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link CurrentConditions#station()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder station(@Nullable CurrentStation value) {
      this.station = value;
      return this;
    }

    /**
     * Builds the {@link CurrentConditions}.
     *
     * @return the immutable CurrentConditions
     * @throws IllegalStateException if a required value was not set
     */
    public CurrentConditions build() {
      return new CurrentConditions(this);
    }
  }
}
