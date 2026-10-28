// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * The surface station whose reading anchored temperature, dewpoint and wind at this hour. Omitted
 * when none did.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CurrentStation}.
 */
public final class CurrentStation extends Model {
  private final String stationId;
  private final String name;
  private final Instant observationTime;

  private CurrentStation(Builder b) {
    this.stationId = require(b.stationId, "station_id");
    this.name = require(b.name, "name");
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
    b.observationTime = this.observationTime;
    return b;
  }

  /**
   * The station's id in its network, as {@code nearest_sensors[].station_id} ({@code name} held it
   * before).
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
   * The {@code observation_time} value.
   *
   * @return the {@code observation_time} value
   */
  public Instant observationTime() {
    return observationTime;
  }

  @Override
  Object[] values() {
    return new Object[] {stationId, name, observationTime};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"stationId", "name", "observationTime"};

  /** Builds a {@link CurrentStation}. */
  public static final class Builder {
    private @Nullable String stationId;
    private @Nullable String name;
    private @Nullable Instant observationTime;

    private Builder() {}

    /**
     * Sets {@link CurrentStation#stationId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stationId(String value) {
      this.stationId = value;
      return this;
    }

    /**
     * Sets {@link CurrentStation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link CurrentStation#observationTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder observationTime(Instant value) {
      this.observationTime = value;
      return this;
    }

    /**
     * Builds the {@link CurrentStation}.
     *
     * @return the immutable CurrentStation
     * @throws IllegalStateException if a required value was not set
     */
    public CurrentStation build() {
      return new CurrentStation(this);
    }
  }
}
