// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A PassHour from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassHour}.
 */
public final class PassHour extends Model {
  private final Instant time;
  private final double temperature;
  private final double precipitation;
  private final @Nullable Double precipitationProbability;
  private final @Nullable String precipitationType;
  private final double snowfall;
  private final @Nullable Double snowLevel;
  private final @Nullable Double snowLevelMargin;
  private final boolean freezingOnWetRoad;
  private final double windSpeed;
  private final @Nullable Double windGust;
  private final boolean gustOver40mph;
  private final boolean blowingSnow;

  private PassHour(Builder b) {
    this.time = require(b.time, "time");
    this.temperature = require(b.temperature, "temperature");
    this.precipitation = require(b.precipitation, "precipitation");
    this.precipitationProbability = b.precipitationProbability;
    this.precipitationType = b.precipitationType;
    this.snowfall = require(b.snowfall, "snowfall");
    this.snowLevel = b.snowLevel;
    this.snowLevelMargin = b.snowLevelMargin;
    this.freezingOnWetRoad = require(b.freezingOnWetRoad, "freezing_on_wet_road");
    this.windSpeed = require(b.windSpeed, "wind_speed");
    this.windGust = b.windGust;
    this.gustOver40mph = require(b.gustOver40mph, "gust_over_40mph");
    this.blowingSnow = require(b.blowingSnow, "blowing_snow");
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
    b.precipitation = this.precipitation;
    b.precipitationProbability = this.precipitationProbability;
    b.precipitationType = this.precipitationType;
    b.snowfall = this.snowfall;
    b.snowLevel = this.snowLevel;
    b.snowLevelMargin = this.snowLevelMargin;
    b.freezingOnWetRoad = this.freezingOnWetRoad;
    b.windSpeed = this.windSpeed;
    b.windGust = this.windGust;
    b.gustOver40mph = this.gustOver40mph;
    b.blowingSnow = this.blowingSnow;
    return b;
  }

  /**
   * The hour's {@code valid_time}; amounts are over the hour ending then.
   *
   * @return the {@code time} value
   */
  public Instant time() {
    return time;
  }

  /**
   * {@code ensemble.temperature.mean}.
   *
   * @return the {@code temperature} value
   */
  public double temperature() {
    return temperature;
  }

  /**
   * {@code ensemble.precipitation.mean} (mm, in).
   *
   * @return the {@code precipitation} value
   */
  public double precipitation() {
    return precipitation;
  }

  /**
   * {@code ensemble.precipitation.probability}, 0-1.
   *
   * @return the {@code precipitation_probability} value, or empty when the response omits it
   */
  public OptionalDouble precipitationProbability() {
    return precipitationProbability == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(precipitationProbability);
  }

  /**
   * The hour's served {@code precipitation_type.type}; omitted on a dry hour. One of {@code rain},
   * {@code snow}, {@code rain_snow_mix}, {@code freezing_rain}; read it as a string, since the API
   * may add values.
   *
   * @return the {@code precipitation_type} value, or empty when the response omits it
   */
  public Optional<String> precipitationType() {
    return Optional.ofNullable(precipitationType);
  }

  /**
   * {@code ensemble.snowfall.mean} (mm, in); 0 without snow.
   *
   * @return the {@code snowfall} value
   */
  public double snowfall() {
    return snowfall;
  }

  /**
   * {@code ensemble.snow_level.mean} (m, ft); omitted where not served.
   *
   * @return the {@code snow_level} value, or empty when the response omits it
   */
  public OptionalDouble snowLevel() {
    return snowLevel == null ? OptionalDouble.empty() : OptionalDouble.of(snowLevel);
  }

  /**
   * (JSON {@code snow_level_above_pass}) {@code snow_level} minus the pass's elevation (m, ft):
   * above 0 the snow level is above the pass (rain at the pass when wet), below 0 it is under it.
   *
   * @return the {@code snow_level_above_pass} value, or empty when the response omits it
   */
  public OptionalDouble snowLevelMargin() {
    return snowLevelMargin == null ? OptionalDouble.empty() : OptionalDouble.of(snowLevelMargin);
  }

  /**
   * The hour is at or below freezing (temperature mean at or below 0 C, 32 F) and wet:
   * precipitation of at least 0.254 mm (0.01 in) in this hour or one of the two before it. Weather
   * at the pass, not a measured road state.
   *
   * @return the {@code freezing_on_wet_road} value
   */
  public boolean freezingOnWetRoad() {
    return freezingOnWetRoad;
  }

  /**
   * {@code ensemble.wind_speed.mean}.
   *
   * @return the {@code wind_speed} value
   */
  public double windSpeed() {
    return windSpeed;
  }

  /**
   * {@code ensemble.wind_gust.mean}; omitted where not served.
   *
   * @return the {@code wind_gust} value, or empty when the response omits it
   */
  public OptionalDouble windGust() {
    return windGust == null ? OptionalDouble.empty() : OptionalDouble.of(windGust);
  }

  /**
   * {@code wind_gust} is over 40 mph (17.9 m/s); threshold fixed, does not change with units.
   *
   * @return the {@code gust_over_40mph} value
   */
  public boolean gustOver40mph() {
    return gustOver40mph;
  }

  /**
   * Gusts of 25 mph (11.2 m/s) or more at or below freezing with loose snow to move: snow falling
   * this hour, 1 in (25.4 mm) or more fallen in the 24 served hours before it, or snow on the
   * ground at the pass ({@code snow_on_ground}).
   *
   * @return the {@code blowing_snow} value
   */
  public boolean blowingSnow() {
    return blowingSnow;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      time,
      temperature,
      precipitation,
      precipitationProbability,
      precipitationType,
      snowfall,
      snowLevel,
      snowLevelMargin,
      freezingOnWetRoad,
      windSpeed,
      windGust,
      gustOver40mph,
      blowingSnow
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "time",
    "temperature",
    "precipitation",
    "precipitationProbability",
    "precipitationType",
    "snowfall",
    "snowLevel",
    "snowLevelMargin",
    "freezingOnWetRoad",
    "windSpeed",
    "windGust",
    "gustOver40mph",
    "blowingSnow"
  };

  /** Builds a {@link PassHour}. */
  public static final class Builder {
    private @Nullable Instant time;
    private @Nullable Double temperature;
    private @Nullable Double precipitation;
    private @Nullable Double precipitationProbability;
    private @Nullable String precipitationType;
    private @Nullable Double snowfall;
    private @Nullable Double snowLevel;
    private @Nullable Double snowLevelMargin;
    private @Nullable Boolean freezingOnWetRoad;
    private @Nullable Double windSpeed;
    private @Nullable Double windGust;
    private @Nullable Boolean gustOver40mph;
    private @Nullable Boolean blowingSnow;

    private Builder() {}

    /**
     * Sets {@link PassHour#time()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder time(Instant value) {
      this.time = value;
      return this;
    }

    /**
     * Sets {@link PassHour#temperature()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder temperature(double value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link PassHour#precipitation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipitation(double value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link PassHour#precipitationProbability()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationProbability(@Nullable Double value) {
      this.precipitationProbability = value;
      return this;
    }

    /**
     * Sets {@link PassHour#precipitationType()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationType(@Nullable String value) {
      this.precipitationType = value;
      return this;
    }

    /**
     * Sets {@link PassHour#snowfall()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snowfall(double value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link PassHour#snowLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevel(@Nullable Double value) {
      this.snowLevel = value;
      return this;
    }

    /**
     * Sets {@link PassHour#snowLevelMargin()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevelMargin(@Nullable Double value) {
      this.snowLevelMargin = value;
      return this;
    }

    /**
     * Sets {@link PassHour#freezingOnWetRoad()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder freezingOnWetRoad(boolean value) {
      this.freezingOnWetRoad = value;
      return this;
    }

    /**
     * Sets {@link PassHour#windSpeed()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windSpeed(double value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link PassHour#windGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windGust(@Nullable Double value) {
      this.windGust = value;
      return this;
    }

    /**
     * Sets {@link PassHour#gustOver40mph()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder gustOver40mph(boolean value) {
      this.gustOver40mph = value;
      return this;
    }

    /**
     * Sets {@link PassHour#blowingSnow()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder blowingSnow(boolean value) {
      this.blowingSnow = value;
      return this;
    }

    /**
     * Builds the {@link PassHour}.
     *
     * @return the immutable PassHour
     * @throws IllegalStateException if a required value was not set
     */
    public PassHour build() {
      return new PassHour(this);
    }
  }
}
