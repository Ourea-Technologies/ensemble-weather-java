// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * One {@code /current} sensor -- its latest readings, in the request's {@code units} (the Cap'n
 * Proto {@code SensorObservation} carries the same values). A reading the sensor does not take is
 * omitted.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SensorObservation}.
 */
public final class SensorObservation extends Model {
  private final String stationId;
  private final String name;
  private final String source;
  private final Instant observationTime;
  private final @Nullable Double temperature;
  private final @Nullable Double dewpoint;
  private final @Nullable Double relativeHumidity;
  private final @Nullable Double windSpeed;
  private final @Nullable Double windGust;
  private final @Nullable Double windDirection;
  private final @Nullable Double pressure;
  private final @Nullable Double precipitation;
  private final @Nullable Double precipRate;
  private final @Nullable Double reflectivity;
  private final @Nullable Double pavementTemp;
  private final @Nullable Double subsurfaceTemp;
  private final @Nullable Double visibility;
  private final @Nullable Double cloudCover;
  private final @Nullable Double ceilingHeight;
  private final @Nullable Double snowDepth;
  private final @Nullable Double swe;
  private final @Nullable Double swePercentOfMedian;
  private final @Nullable Double snowfall;
  private final @Nullable Double solarRadiation;
  private final @Nullable String pavementCondition;

  private SensorObservation(Builder b) {
    this.stationId = require(b.stationId, "station_id");
    this.name = require(b.name, "name");
    this.source = require(b.source, "source");
    this.observationTime = require(b.observationTime, "observation_time");
    this.temperature = b.temperature;
    this.dewpoint = b.dewpoint;
    this.relativeHumidity = b.relativeHumidity;
    this.windSpeed = b.windSpeed;
    this.windGust = b.windGust;
    this.windDirection = b.windDirection;
    this.pressure = b.pressure;
    this.precipitation = b.precipitation;
    this.precipRate = b.precipRate;
    this.reflectivity = b.reflectivity;
    this.pavementTemp = b.pavementTemp;
    this.subsurfaceTemp = b.subsurfaceTemp;
    this.visibility = b.visibility;
    this.cloudCover = b.cloudCover;
    this.ceilingHeight = b.ceilingHeight;
    this.snowDepth = b.snowDepth;
    this.swe = b.swe;
    this.swePercentOfMedian = b.swePercentOfMedian;
    this.snowfall = b.snowfall;
    this.solarRadiation = b.solarRadiation;
    this.pavementCondition = b.pavementCondition;
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
    b.stationId = this.stationId;
    b.name = this.name;
    b.source = this.source;
    b.observationTime = this.observationTime;
    b.temperature = this.temperature;
    b.dewpoint = this.dewpoint;
    b.relativeHumidity = this.relativeHumidity;
    b.windSpeed = this.windSpeed;
    b.windGust = this.windGust;
    b.windDirection = this.windDirection;
    b.pressure = this.pressure;
    b.precipitation = this.precipitation;
    b.precipRate = this.precipRate;
    b.reflectivity = this.reflectivity;
    b.pavementTemp = this.pavementTemp;
    b.subsurfaceTemp = this.subsurfaceTemp;
    b.visibility = this.visibility;
    b.cloudCover = this.cloudCover;
    b.ceilingHeight = this.ceilingHeight;
    b.snowDepth = this.snowDepth;
    b.swe = this.swe;
    b.swePercentOfMedian = this.swePercentOfMedian;
    b.snowfall = this.snowfall;
    b.solarRadiation = this.solarRadiation;
    b.pavementCondition = this.pavementCondition;
    return b;
  }

  /**
   * The station's id in its network.
   *
   * @return the {@code station_id} value
   */
  public String stationId() {
    return stationId;
  }

  /**
   * The station's registry name, else its id.
   *
   * @return the {@code name} value
   */
  public String name() {
    return name;
  }

  /**
   * The {@code source} value.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * The {@code observation_time} value.
   *
   * @return the {@code observation_time} value
   */
  public Instant observationTime() {
    return observationTime;
  }

  /**
   * °C, °F (imperial) or K (kelvin).
   *
   * @return the {@code temperature} value, or empty when the response omits it
   */
  public OptionalDouble temperature() {
    return temperature == null ? OptionalDouble.empty() : OptionalDouble.of(temperature);
  }

  /**
   * As {@code temperature}.
   *
   * @return the {@code dewpoint} value, or empty when the response omits it
   */
  public OptionalDouble dewpoint() {
    return dewpoint == null ? OptionalDouble.empty() : OptionalDouble.of(dewpoint);
  }

  /**
   * Percent.
   *
   * @return the {@code relative_humidity} value, or empty when the response omits it
   */
  public OptionalDouble relativeHumidity() {
    return relativeHumidity == null ? OptionalDouble.empty() : OptionalDouble.of(relativeHumidity);
  }

  /**
   * M/s or mph (imperial).
   *
   * @return the {@code wind_speed} value, or empty when the response omits it
   */
  public OptionalDouble windSpeed() {
    return windSpeed == null ? OptionalDouble.empty() : OptionalDouble.of(windSpeed);
  }

  /**
   * As {@code wind_speed}.
   *
   * @return the {@code wind_gust} value, or empty when the response omits it
   */
  public OptionalDouble windGust() {
    return windGust == null ? OptionalDouble.empty() : OptionalDouble.of(windGust);
  }

  /**
   * Degrees clockwise from north.
   *
   * @return the {@code wind_direction} value, or empty when the response omits it
   */
  public OptionalDouble windDirection() {
    return windDirection == null ? OptionalDouble.empty() : OptionalDouble.of(windDirection);
  }

  /**
   * Station pressure: hPa, or inHg (imperial).
   *
   * @return the {@code pressure} value, or empty when the response omits it
   */
  public OptionalDouble pressure() {
    return pressure == null ? OptionalDouble.empty() : OptionalDouble.of(pressure);
  }

  /**
   * The last reporting period's precipitation: mm, or in (imperial).
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public OptionalDouble precipitation() {
    return precipitation == null ? OptionalDouble.empty() : OptionalDouble.of(precipitation);
  }

  /**
   * Radar precipitation rate: mm/h, or in/h (imperial).
   *
   * @return the {@code precip_rate} value, or empty when the response omits it
   */
  public OptionalDouble precipRate() {
    return precipRate == null ? OptionalDouble.empty() : OptionalDouble.of(precipRate);
  }

  /**
   * Radar reflectivity, dBZ.
   *
   * @return the {@code reflectivity} value, or empty when the response omits it
   */
  public OptionalDouble reflectivity() {
    return reflectivity == null ? OptionalDouble.empty() : OptionalDouble.of(reflectivity);
  }

  /**
   * As {@code temperature}.
   *
   * @return the {@code pavement_temp} value, or empty when the response omits it
   */
  public OptionalDouble pavementTemp() {
    return pavementTemp == null ? OptionalDouble.empty() : OptionalDouble.of(pavementTemp);
  }

  /**
   * As {@code temperature}.
   *
   * @return the {@code subsurface_temp} value, or empty when the response omits it
   */
  public OptionalDouble subsurfaceTemp() {
    return subsurfaceTemp == null ? OptionalDouble.empty() : OptionalDouble.of(subsurfaceTemp);
  }

  /**
   * M, or ft (imperial).
   *
   * @return the {@code visibility} value, or empty when the response omits it
   */
  public OptionalDouble visibility() {
    return visibility == null ? OptionalDouble.empty() : OptionalDouble.of(visibility);
  }

  /**
   * Percent.
   *
   * @return the {@code cloud_cover} value, or empty when the response omits it
   */
  public OptionalDouble cloudCover() {
    return cloudCover == null ? OptionalDouble.empty() : OptionalDouble.of(cloudCover);
  }

  /**
   * M, or ft (imperial).
   *
   * @return the {@code ceiling_height} value, or empty when the response omits it
   */
  public OptionalDouble ceilingHeight() {
    return ceilingHeight == null ? OptionalDouble.empty() : OptionalDouble.of(ceilingHeight);
  }

  /**
   * Cm, or in (imperial).
   *
   * @return the {@code snow_depth} value, or empty when the response omits it
   */
  public OptionalDouble snowDepth() {
    return snowDepth == null ? OptionalDouble.empty() : OptionalDouble.of(snowDepth);
  }

  /**
   * Snow water equivalent: mm, or in (imperial).
   *
   * @return the {@code swe} value, or empty when the response omits it
   */
  public OptionalDouble swe() {
    return swe == null ? OptionalDouble.empty() : OptionalDouble.of(swe);
  }

  /**
   * SWE as a percent of the station's median for the date.
   *
   * @return the {@code swe_percent_of_median} value, or empty when the response omits it
   */
  public OptionalDouble swePercentOfMedian() {
    return swePercentOfMedian == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(swePercentOfMedian);
  }

  /**
   * Mm, or in (imperial).
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public OptionalDouble snowfall() {
    return snowfall == null ? OptionalDouble.empty() : OptionalDouble.of(snowfall);
  }

  /**
   * W/m².
   *
   * @return the {@code solar_radiation} value, or empty when the response omits it
   */
  public OptionalDouble solarRadiation() {
    return solarRadiation == null ? OptionalDouble.empty() : OptionalDouble.of(solarRadiation);
  }

  /**
   * Road-weather pavement state as the network reports it.
   *
   * @return the {@code pavement_condition} value, or empty when the response omits it
   */
  public Optional<String> pavementCondition() {
    return Optional.ofNullable(pavementCondition);
  }

  @Override
  Object[] values() {
    return new Object[] {
      stationId,
      name,
      source,
      observationTime,
      temperature,
      dewpoint,
      relativeHumidity,
      windSpeed,
      windGust,
      windDirection,
      pressure,
      precipitation,
      precipRate,
      reflectivity,
      pavementTemp,
      subsurfaceTemp,
      visibility,
      cloudCover,
      ceilingHeight,
      snowDepth,
      swe,
      swePercentOfMedian,
      snowfall,
      solarRadiation,
      pavementCondition
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "stationId",
    "name",
    "source",
    "observationTime",
    "temperature",
    "dewpoint",
    "relativeHumidity",
    "windSpeed",
    "windGust",
    "windDirection",
    "pressure",
    "precipitation",
    "precipRate",
    "reflectivity",
    "pavementTemp",
    "subsurfaceTemp",
    "visibility",
    "cloudCover",
    "ceilingHeight",
    "snowDepth",
    "swe",
    "swePercentOfMedian",
    "snowfall",
    "solarRadiation",
    "pavementCondition"
  };

  /** Builds a {@link SensorObservation}. */
  public static final class Builder {
    private @Nullable String stationId;
    private @Nullable String name;
    private @Nullable String source;
    private @Nullable Instant observationTime;
    private @Nullable Double temperature;
    private @Nullable Double dewpoint;
    private @Nullable Double relativeHumidity;
    private @Nullable Double windSpeed;
    private @Nullable Double windGust;
    private @Nullable Double windDirection;
    private @Nullable Double pressure;
    private @Nullable Double precipitation;
    private @Nullable Double precipRate;
    private @Nullable Double reflectivity;
    private @Nullable Double pavementTemp;
    private @Nullable Double subsurfaceTemp;
    private @Nullable Double visibility;
    private @Nullable Double cloudCover;
    private @Nullable Double ceilingHeight;
    private @Nullable Double snowDepth;
    private @Nullable Double swe;
    private @Nullable Double swePercentOfMedian;
    private @Nullable Double snowfall;
    private @Nullable Double solarRadiation;
    private @Nullable String pavementCondition;

    private Builder() {}

    /**
     * Sets {@link SensorObservation#stationId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stationId(String value) {
      this.stationId = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#observationTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder observationTime(Instant value) {
      this.observationTime = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#temperature()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder temperature(@Nullable Double value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#dewpoint()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder dewpoint(@Nullable Double value) {
      this.dewpoint = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#relativeHumidity()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder relativeHumidity(@Nullable Double value) {
      this.relativeHumidity = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#windSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windSpeed(@Nullable Double value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#windGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windGust(@Nullable Double value) {
      this.windGust = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#windDirection()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windDirection(@Nullable Double value) {
      this.windDirection = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#pressure()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pressure(@Nullable Double value) {
      this.pressure = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable Double value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#precipRate()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipRate(@Nullable Double value) {
      this.precipRate = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#reflectivity()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder reflectivity(@Nullable Double value) {
      this.reflectivity = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#pavementTemp()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pavementTemp(@Nullable Double value) {
      this.pavementTemp = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#subsurfaceTemp()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder subsurfaceTemp(@Nullable Double value) {
      this.subsurfaceTemp = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#visibility()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder visibility(@Nullable Double value) {
      this.visibility = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#cloudCover()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cloudCover(@Nullable Double value) {
      this.cloudCover = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#ceilingHeight()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder ceilingHeight(@Nullable Double value) {
      this.ceilingHeight = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#snowDepth()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowDepth(@Nullable Double value) {
      this.snowDepth = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#swe()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder swe(@Nullable Double value) {
      this.swe = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#swePercentOfMedian()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder swePercentOfMedian(@Nullable Double value) {
      this.swePercentOfMedian = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable Double value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#solarRadiation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder solarRadiation(@Nullable Double value) {
      this.solarRadiation = value;
      return this;
    }

    /**
     * Sets {@link SensorObservation#pavementCondition()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pavementCondition(@Nullable String value) {
      this.pavementCondition = value;
      return this;
    }

    /**
     * Builds the {@link SensorObservation}.
     *
     * @return the immutable SensorObservation
     * @throws IllegalStateException if a required value was not set
     */
    public SensorObservation build() {
      return new SensorObservation(this);
    }
  }
}
