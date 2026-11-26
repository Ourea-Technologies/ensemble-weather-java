// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.LocalDate;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * A SeasonStation from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SeasonStation}.
 */
public final class SeasonStation extends Model {
  private final String id;
  private final String network;
  private final String name;
  private final double distanceKm;
  private final double elevationM;
  private final @Nullable Double precipitationTotal;
  private final @Nullable Integer precipitationPercentOfMedian;
  private final @Nullable Double swe;
  private final @Nullable Integer swePercentOfMedian;
  private final @Nullable Double snowDepth;
  private final LocalDate through;

  private SeasonStation(Builder b) {
    this.id = require(b.id, "id");
    this.network = require(b.network, "network");
    this.name = require(b.name, "name");
    this.distanceKm = require(b.distanceKm, "distance_km");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.precipitationTotal = b.precipitationTotal;
    this.precipitationPercentOfMedian = b.precipitationPercentOfMedian;
    this.swe = b.swe;
    this.swePercentOfMedian = b.swePercentOfMedian;
    this.snowDepth = b.snowDepth;
    this.through = require(b.through, "through");
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
    b.network = this.network;
    b.name = this.name;
    b.distanceKm = this.distanceKm;
    b.elevationM = this.elevationM;
    b.precipitationTotal = this.precipitationTotal;
    b.precipitationPercentOfMedian = this.precipitationPercentOfMedian;
    b.swe = this.swe;
    b.swePercentOfMedian = this.swePercentOfMedian;
    b.snowDepth = this.snowDepth;
    b.through = this.through;
    return b;
  }

  /**
   * The NRCS station triplet.
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * The {@code network} value. One of {@code snotel}, {@code scan}; read it as a string, since the
   * API may add values.
   *
   * @return the {@code network} value
   */
  public String network() {
    return network;
  }

  /**
   * The {@code name} value.
   *
   * @return the {@code name} value
   */
  public String name() {
    return name;
  }

  /**
   * The {@code distance_km} value.
   *
   * @return the {@code distance_km} value
   */
  public double distanceKm() {
    return distanceKm;
  }

  /**
   * The {@code elevation_m} value.
   *
   * @return the {@code elevation_m} value
   */
  public double elevationM() {
    return elevationM;
  }

  /**
   * The gauge's water-year accumulator on {@code through} (mm or in).
   *
   * @return the {@code precipitation_total} value, or empty when the response omits it
   */
  public OptionalDouble precipitationTotal() {
    return precipitationTotal == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(precipitationTotal);
  }

  /**
   * The {@code precipitation_percent_of_median} value.
   *
   * @return the {@code precipitation_percent_of_median} value, or empty when the response omits it
   */
  public OptionalInt precipitationPercentOfMedian() {
    return precipitationPercentOfMedian == null
        ? OptionalInt.empty()
        : OptionalInt.of(precipitationPercentOfMedian);
  }

  /**
   * Snow water equivalent on {@code through} (mm or in).
   *
   * @return the {@code swe} value, or empty when the response omits it
   */
  public OptionalDouble swe() {
    return swe == null ? OptionalDouble.empty() : OptionalDouble.of(swe);
  }

  /**
   * The {@code swe_percent_of_median} value.
   *
   * @return the {@code swe_percent_of_median} value, or empty when the response omits it
   */
  public OptionalInt swePercentOfMedian() {
    return swePercentOfMedian == null ? OptionalInt.empty() : OptionalInt.of(swePercentOfMedian);
  }

  /**
   * Snow depth on {@code through} (cm or in).
   *
   * @return the {@code snow_depth} value, or empty when the response omits it
   */
  public OptionalDouble snowDepth() {
    return snowDepth == null ? OptionalDouble.empty() : OptionalDouble.of(snowDepth);
  }

  /**
   * The gauge's newest day.
   *
   * @return the {@code through} value
   */
  public LocalDate through() {
    return through;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      id,
      network,
      name,
      distanceKm,
      elevationM,
      precipitationTotal,
      precipitationPercentOfMedian,
      swe,
      swePercentOfMedian,
      snowDepth,
      through
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id",
    "network",
    "name",
    "distanceKm",
    "elevationM",
    "precipitationTotal",
    "precipitationPercentOfMedian",
    "swe",
    "swePercentOfMedian",
    "snowDepth",
    "through"
  };

  /** Builds a {@link SeasonStation}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String network;
    private @Nullable String name;
    private @Nullable Double distanceKm;
    private @Nullable Double elevationM;
    private @Nullable Double precipitationTotal;
    private @Nullable Integer precipitationPercentOfMedian;
    private @Nullable Double swe;
    private @Nullable Integer swePercentOfMedian;
    private @Nullable Double snowDepth;
    private @Nullable LocalDate through;

    private Builder() {}

    /**
     * Sets {@link SeasonStation#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#network()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder network(String value) {
      this.network = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#distanceKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder distanceKm(double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#precipitationTotal()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationTotal(@Nullable Double value) {
      this.precipitationTotal = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#precipitationPercentOfMedian()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationPercentOfMedian(@Nullable Integer value) {
      this.precipitationPercentOfMedian = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#swe()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder swe(@Nullable Double value) {
      this.swe = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#swePercentOfMedian()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder swePercentOfMedian(@Nullable Integer value) {
      this.swePercentOfMedian = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#snowDepth()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowDepth(@Nullable Double value) {
      this.snowDepth = value;
      return this;
    }

    /**
     * Sets {@link SeasonStation#through()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder through(LocalDate value) {
      this.through = value;
      return this;
    }

    /**
     * Builds the {@link SeasonStation}.
     *
     * @return the immutable SeasonStation
     * @throws IllegalStateException if a required value was not set
     */
    public SeasonStation build() {
      return new SeasonStation(this);
    }
  }
}
