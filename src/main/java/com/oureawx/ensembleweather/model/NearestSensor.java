// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A NearestSensor from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code NearestSensor}.
 */
public final class NearestSensor extends Model {
  private final String stationId;
  private final String name;
  private final String source;
  private final double distanceKm;
  private final double elevationDiff;
  private final double effectiveDistanceKm;
  private final Instant observationTime;

  private NearestSensor(Builder b) {
    this.stationId = require(b.stationId, "station_id");
    this.name = require(b.name, "name");
    this.source = require(b.source, "source");
    this.distanceKm = require(b.distanceKm, "distance_km");
    this.elevationDiff = require(b.elevationDiff, "elevation_diff");
    this.effectiveDistanceKm = require(b.effectiveDistanceKm, "effective_distance_km");
    this.observationTime = require(b.observationTime, "observation_time");
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
    b.distanceKm = this.distanceKm;
    b.elevationDiff = this.elevationDiff;
    b.effectiveDistanceKm = this.effectiveDistanceKm;
    b.observationTime = this.observationTime;
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
   * The station's name from the stations registry, else its id.
   *
   * @return the {@code name} value
   */
  public String name() {
    return name;
  }

  /**
   * Observation network (snotel, nws, raws, rwis, cdec, tempest, etc.).
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * Straight-line surface distance in km.
   *
   * @return the {@code distance_km} value
   */
  public double distanceKm() {
    return distanceKm;
  }

  /**
   * Sensor elevation minus forecast-location elevation, in the caller's length units (m metric, ft
   * imperial). Positive if the sensor sits above the forecast point.
   *
   * @return the {@code elevation_diff} value
   */
  public double elevationDiff() {
    return elevationDiff;
  }

  /**
   * {@code distance_km + |elevation_diff_m| / 100}. Lower ranks closer.
   *
   * @return the {@code effective_distance_km} value
   */
  public double effectiveDistanceKm() {
    return effectiveDistanceKm;
  }

  /**
   * The {@code observation_time} value.
   *
   * @return the {@code observation_time} value
   */
  public Instant observationTime() {
    return observationTime;
  }

  @Override
  Object[] values() {
    return new Object[] {
      stationId, name, source, distanceKm, elevationDiff, effectiveDistanceKm, observationTime
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
    "distanceKm",
    "elevationDiff",
    "effectiveDistanceKm",
    "observationTime"
  };

  /** Builds a {@link NearestSensor}. */
  public static final class Builder {
    private @Nullable String stationId;
    private @Nullable String name;
    private @Nullable String source;
    private @Nullable Double distanceKm;
    private @Nullable Double elevationDiff;
    private @Nullable Double effectiveDistanceKm;
    private @Nullable Instant observationTime;

    private Builder() {}

    /**
     * Sets {@link NearestSensor#stationId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stationId(String value) {
      this.stationId = value;
      return this;
    }

    /**
     * Sets {@link NearestSensor#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link NearestSensor#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link NearestSensor#distanceKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder distanceKm(double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Sets {@link NearestSensor#elevationDiff()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationDiff(double value) {
      this.elevationDiff = value;
      return this;
    }

    /**
     * Sets {@link NearestSensor#effectiveDistanceKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder effectiveDistanceKm(double value) {
      this.effectiveDistanceKm = value;
      return this;
    }

    /**
     * Sets {@link NearestSensor#observationTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder observationTime(Instant value) {
      this.observationTime = value;
      return this;
    }

    /**
     * Builds the {@link NearestSensor}.
     *
     * @return the immutable NearestSensor
     * @throws IllegalStateException if a required value was not set
     */
    public NearestSensor build() {
      return new NearestSensor(this);
    }
  }
}
