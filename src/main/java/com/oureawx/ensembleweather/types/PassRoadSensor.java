// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * One state DOT road-weather station's newest reading, in the response's units. What the station
 * measured, never a forecast. A reading the station does not take, or that the observation QC
 * flagged, is omitted.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassRoadSensor}.
 */
public final class PassRoadSensor extends Model {
  private final String stationId;
  private final String name;
  private final String source;
  private final String attribution;
  private final double distanceKm;
  private final @Nullable Double elevation;
  private final @Nullable Double elevationM;
  private final Instant observedAt;
  private final boolean stale;
  private final @Nullable String pavementCondition;
  private final @Nullable Double pavementTemp;
  private final @Nullable Double subsurfaceTemp;
  private final @Nullable Double temperature;
  private final @Nullable Double windSpeed;
  private final @Nullable Double windGust;
  private final @Nullable Double windDirection;
  private final @Nullable Double visibility;

  private PassRoadSensor(Builder b) {
    this.stationId = require(b.stationId, "station_id");
    this.name = require(b.name, "name");
    this.source = require(b.source, "source");
    this.attribution = require(b.attribution, "attribution");
    this.distanceKm = require(b.distanceKm, "distance_km");
    this.elevation = b.elevation;
    this.elevationM = b.elevationM;
    this.observedAt = require(b.observedAt, "observed_at");
    this.stale = require(b.stale, "stale");
    this.pavementCondition = b.pavementCondition;
    this.pavementTemp = b.pavementTemp;
    this.subsurfaceTemp = b.subsurfaceTemp;
    this.temperature = b.temperature;
    this.windSpeed = b.windSpeed;
    this.windGust = b.windGust;
    this.windDirection = b.windDirection;
    this.visibility = b.visibility;
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
    b.attribution = this.attribution;
    b.distanceKm = this.distanceKm;
    b.elevation = this.elevation;
    b.elevationM = this.elevationM;
    b.observedAt = this.observedAt;
    b.stale = this.stale;
    b.pavementCondition = this.pavementCondition;
    b.pavementTemp = this.pavementTemp;
    b.subsurfaceTemp = this.subsurfaceTemp;
    b.temperature = this.temperature;
    b.windSpeed = this.windSpeed;
    b.windGust = this.windGust;
    b.windDirection = this.windDirection;
    b.visibility = this.visibility;
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
   * The feed: {@code rwis} (Caltrans CWWP2 RWIS) or {@code wsdot}. One of {@code rwis}, {@code
   * wsdot}; read it as a string, since the API may add values.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * Who measured it, as the client credits it.
   *
   * @return the {@code attribution} value
   */
  public String attribution() {
    return attribution;
  }

  /**
   * Straight-line distance from the pass, km.
   *
   * @return the {@code distance_km} value
   */
  public double distanceKm() {
    return distanceKm;
  }

  /**
   * The station's registry elevation in the response's length units (m, ft). Omitted when unknown.
   *
   * @return the {@code elevation} value, or empty when the response omits it
   */
  public OptionalDouble elevation() {
    return elevation == null ? OptionalDouble.empty() : OptionalDouble.of(elevation);
  }

  /**
   * The station's registry elevation in metres. Omitted when unknown.
   *
   * @return the {@code elevation_m} value, or empty when the response omits it
   */
  public OptionalDouble elevationM() {
    return elevationM == null ? OptionalDouble.empty() : OptionalDouble.of(elevationM);
  }

  /**
   * The {@code observed_at} value.
   *
   * @return the {@code observed_at} value
   */
  public Instant observedAt() {
    return observedAt;
  }

  /**
   * {@code observed_at} is more than 60 minutes before {@code as_of}.
   *
   * @return the {@code stale} value
   */
  public boolean stale() {
    return stale;
  }

  /**
   * The pavement state the sensor reports (NTCIP 1204 essSurfaceStatus as {@code /current} sensors
   * serve it: dry, trace_moisture, wet, chemically_wet, ice_warning, ice_watch, snow_warning,
   * snow_watch, absorption, dew, frost, absorption_at_dewpoint).
   *
   * @return the {@code pavement_condition} value, or empty when the response omits it
   */
  public Optional<String> pavementCondition() {
    return Optional.ofNullable(pavementCondition);
  }

  /**
   * Pavement surface temperature: °C, °F (imperial) or K (kelvin).
   *
   * @return the {@code pavement_temp} value, or empty when the response omits it
   */
  public OptionalDouble pavementTemp() {
    return pavementTemp == null ? OptionalDouble.empty() : OptionalDouble.of(pavementTemp);
  }

  /**
   * As {@code pavement_temp}.
   *
   * @return the {@code subsurface_temp} value, or empty when the response omits it
   */
  public OptionalDouble subsurfaceTemp() {
    return subsurfaceTemp == null ? OptionalDouble.empty() : OptionalDouble.of(subsurfaceTemp);
  }

  /**
   * Air temperature, as {@code pavement_temp}.
   *
   * @return the {@code temperature} value, or empty when the response omits it
   */
  public OptionalDouble temperature() {
    return temperature == null ? OptionalDouble.empty() : OptionalDouble.of(temperature);
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
   * M, or statute miles (imperial).
   *
   * @return the {@code visibility} value, or empty when the response omits it
   */
  public OptionalDouble visibility() {
    return visibility == null ? OptionalDouble.empty() : OptionalDouble.of(visibility);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      stationId,
      name,
      source,
      attribution,
      distanceKm,
      elevation,
      elevationM,
      observedAt,
      stale,
      pavementCondition,
      pavementTemp,
      subsurfaceTemp,
      temperature,
      windSpeed,
      windGust,
      windDirection,
      visibility
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "stationId",
    "name",
    "source",
    "attribution",
    "distanceKm",
    "elevation",
    "elevationM",
    "observedAt",
    "stale",
    "pavementCondition",
    "pavementTemp",
    "subsurfaceTemp",
    "temperature",
    "windSpeed",
    "windGust",
    "windDirection",
    "visibility"
  };

  /** Builds a {@link PassRoadSensor}. */
  public static final class Builder {
    private @Nullable String stationId;
    private @Nullable String name;
    private @Nullable String source;
    private @Nullable String attribution;
    private @Nullable Double distanceKm;
    private @Nullable Double elevation;
    private @Nullable Double elevationM;
    private @Nullable Instant observedAt;
    private @Nullable Boolean stale;
    private @Nullable String pavementCondition;
    private @Nullable Double pavementTemp;
    private @Nullable Double subsurfaceTemp;
    private @Nullable Double temperature;
    private @Nullable Double windSpeed;
    private @Nullable Double windGust;
    private @Nullable Double windDirection;
    private @Nullable Double visibility;

    private Builder() {}

    /**
     * Sets {@link PassRoadSensor#stationId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stationId(String value) {
      this.stationId = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(String value) {
      this.attribution = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#distanceKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder distanceKm(double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#elevation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder elevation(@Nullable Double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#elevationM()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder elevationM(@Nullable Double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#observedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder observedAt(Instant value) {
      this.observedAt = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#stale()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stale(boolean value) {
      this.stale = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#pavementCondition()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pavementCondition(@Nullable String value) {
      this.pavementCondition = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#pavementTemp()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pavementTemp(@Nullable Double value) {
      this.pavementTemp = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#subsurfaceTemp()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder subsurfaceTemp(@Nullable Double value) {
      this.subsurfaceTemp = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#temperature()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder temperature(@Nullable Double value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#windSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windSpeed(@Nullable Double value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#windGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windGust(@Nullable Double value) {
      this.windGust = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#windDirection()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windDirection(@Nullable Double value) {
      this.windDirection = value;
      return this;
    }

    /**
     * Sets {@link PassRoadSensor#visibility()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder visibility(@Nullable Double value) {
      this.visibility = value;
      return this;
    }

    /**
     * Builds the {@link PassRoadSensor}.
     *
     * @return the immutable PassRoadSensor
     * @throws IllegalStateException if a required value was not set
     */
    public PassRoadSensor build() {
      return new PassRoadSensor(this);
    }
  }
}
