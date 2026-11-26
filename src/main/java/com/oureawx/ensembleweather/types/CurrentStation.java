// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

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
  private final String id;
  private final String name;
  private final Instant observationTime;

  private final boolean calm;

  private CurrentStation(Builder b) {
    this.id = require(b.id, "station_id");
    this.name = require(b.name, "name");
    this.observationTime = require(b.observationTime, "observation_time");
    this.calm = require(b.calm, "calm");
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
    b.name = this.name;
    b.observationTime = this.observationTime;
    b.calm = this.calm;
    return b;
  }

  /**
   * (JSON {@code station_id}) The station's id in its network, as {@code
   * nearest_sensors[].station_id} ({@code name} held it before).
   *
   * @return the {@code station_id} value
   */
  public String id() {
    return id;
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

  /**
   * {@code True} when the station's reading was calm (0 wind speed; a METAR's {@code 00000KT}). A
   * calm reading has no bearing, so it does not turn the served direction: {@code wind_direction}
   * is then the forecast's, not a reported direction, and should not be shown as the station's.
   * {@code false} for a moving wind and for a station that reads no wind. Additive; Cap'n Proto
   * {@code CurrentStation.calm}.
   *
   * @return the {@code calm} value
   */
  public boolean calm() {
    return calm;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {id, name, observationTime, calm};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"id", "name", "observationTime", "calm"};

  /** Builds a {@link CurrentStation}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String name;
    private @Nullable Instant observationTime;

    private @Nullable Boolean calm;

    private Builder() {}

    /**
     * Sets {@link CurrentStation#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
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
     * Sets {@link CurrentStation#calm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder calm(boolean value) {
      this.calm = value;
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
