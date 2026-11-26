// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The members' distributions, at the served hours that carry at least one ({@code valid_time}; the
 * synoptic hours in practice): {@code cumulative_precipitation} / {@code cumulative_snowfall} are
 * the row form's {@code members_cumulative} (the spread of the members' running totals, never a sum
 * of hourly percentiles), {@code temperature} / {@code snow_level} its {@code members_spread},
 * {@code precipitation_6h} / {@code snowfall_6h} its {@code members_6h} (6-hour totals ending at a
 * 00/06/12/18Z hour), {@code slr} its {@code members_spread.slr} (the key is absent when no hour
 * carries it), and {@code cumulative_snowfall_10to1} the spread of the members' running snowfall at
 * 10:1. A band is {@code null} at an hour without it.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PlumeBands}.
 */
public final class PlumeBands extends Model {
  private final List<Instant> validTime;
  private final BandColumns cumulativePrecipitation;
  private final BandColumns cumulativeSnowfall;
  private final BandColumns cumulativeSnowfall10to1;
  private final BandColumns temperature;
  private final BandColumns snowLevel;
  private final BandColumns snowLevelWetBulb;
  private final BandColumns precipitation6h;
  private final BandColumns snowfall6h;
  private final @Nullable BandColumns slr;
  private final @Nullable BandColumns windSpeed;

  private PlumeBands(Builder b) {
    this.validTime = List.copyOf(b.validTime);
    this.cumulativePrecipitation = require(b.cumulativePrecipitation, "cumulative_precipitation");
    this.cumulativeSnowfall = require(b.cumulativeSnowfall, "cumulative_snowfall");
    this.cumulativeSnowfall10to1 = require(b.cumulativeSnowfall10to1, "cumulative_snowfall_10to1");
    this.temperature = require(b.temperature, "temperature");
    this.snowLevel = require(b.snowLevel, "snow_level");
    this.snowLevelWetBulb = require(b.snowLevelWetBulb, "snow_level_wet_bulb");
    this.precipitation6h = require(b.precipitation6h, "precipitation_6h");
    this.snowfall6h = require(b.snowfall6h, "snowfall_6h");
    this.slr = b.slr;
    this.windSpeed = b.windSpeed;
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
    b.cumulativePrecipitation = this.cumulativePrecipitation;
    b.cumulativeSnowfall = this.cumulativeSnowfall;
    b.cumulativeSnowfall10to1 = this.cumulativeSnowfall10to1;
    b.temperature = this.temperature;
    b.snowLevel = this.snowLevel;
    b.snowLevelWetBulb = this.snowLevelWetBulb;
    b.precipitation6h = this.precipitation6h;
    b.snowfall6h = this.snowfall6h;
    b.slr = this.slr;
    b.windSpeed = this.windSpeed;
    return b;
  }

  /**
   * The {@code valid_time} value.
   *
   * @return the {@code valid_time} values; empty when the response has none
   */
  public List<Instant> validTime() {
    return validTime;
  }

  /**
   * The {@code cumulative_precipitation} value.
   *
   * @return the {@code cumulative_precipitation} value
   */
  public BandColumns cumulativePrecipitation() {
    return cumulativePrecipitation;
  }

  /**
   * The {@code cumulative_snowfall} value.
   *
   * @return the {@code cumulative_snowfall} value
   */
  public BandColumns cumulativeSnowfall() {
    return cumulativeSnowfall;
  }

  /**
   * The members' running snowfall at 10:1 -- each member's snowfall with its SLR replaced by 10
   * (its snowing liquid x 10, every phase gate of its {@code snowfall} kept, the {@code
   * snowfall_total_10to1} map's member rule), totalled as {@code cumulative_snowfall} is -- by
   * {@code cumulative_snowfall}'s rule: one fixed set of members, so every rung never decreases.
   * Not in the row form; RRFS display members are not in it.
   *
   * @return the {@code cumulative_snowfall_10to1} value
   */
  public BandColumns cumulativeSnowfall10to1() {
    return cumulativeSnowfall10to1;
  }

  /**
   * The {@code temperature} value.
   *
   * @return the {@code temperature} value
   */
  public BandColumns temperature() {
    return temperature;
  }

  /**
   * The {@code snow_level} value.
   *
   * @return the {@code snow_level} value
   */
  public BandColumns snowLevel() {
    return snowLevel;
  }

  /**
   * The row form's {@code members_spread.snow_level_wet_bulb} -- the members' snow level on the NWS
   * scale, at the hours {@code snow_level} carries a band.
   *
   * @return the {@code snow_level_wet_bulb} value
   */
  public BandColumns snowLevelWetBulb() {
    return snowLevelWetBulb;
  }

  /**
   * The {@code precipitation_6h} value.
   *
   * @return the {@code precipitation_6h} value
   */
  public BandColumns precipitation6h() {
    return precipitation6h;
  }

  /**
   * The {@code snowfall_6h} value.
   *
   * @return the {@code snowfall_6h} value
   */
  public BandColumns snowfall6h() {
    return snowfall6h;
  }

  /**
   * The {@code slr} value.
   *
   * @return the {@code slr} value, or empty when the response omits it
   */
  public Optional<BandColumns> slr() {
    return Optional.ofNullable(slr);
  }

  /**
   * {@code members_spread.wind_speed}. Absent when no hour carries it, which is every hour of the
   * Free view.
   *
   * @return the {@code wind_speed} value, or empty when the response omits it
   */
  public Optional<BandColumns> windSpeed() {
    return Optional.ofNullable(windSpeed);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      validTime,
      cumulativePrecipitation,
      cumulativeSnowfall,
      cumulativeSnowfall10to1,
      temperature,
      snowLevel,
      snowLevelWetBulb,
      precipitation6h,
      snowfall6h,
      slr,
      windSpeed
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "validTime",
    "cumulativePrecipitation",
    "cumulativeSnowfall",
    "cumulativeSnowfall10to1",
    "temperature",
    "snowLevel",
    "snowLevelWetBulb",
    "precipitation6h",
    "snowfall6h",
    "slr",
    "windSpeed"
  };

  /** Builds a {@link PlumeBands}. */
  public static final class Builder {
    private List<Instant> validTime = List.of();
    private @Nullable BandColumns cumulativePrecipitation;
    private @Nullable BandColumns cumulativeSnowfall;
    private @Nullable BandColumns cumulativeSnowfall10to1;
    private @Nullable BandColumns temperature;
    private @Nullable BandColumns snowLevel;
    private @Nullable BandColumns snowLevelWetBulb;
    private @Nullable BandColumns precipitation6h;
    private @Nullable BandColumns snowfall6h;
    private @Nullable BandColumns slr;
    private @Nullable BandColumns windSpeed;

    private Builder() {}

    /**
     * Sets {@link PlumeBands#validTime()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder validTime(List<Instant> values) {
      this.validTime = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeBands#cumulativePrecipitation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder cumulativePrecipitation(BandColumns value) {
      this.cumulativePrecipitation = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#cumulativeSnowfall()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder cumulativeSnowfall(BandColumns value) {
      this.cumulativeSnowfall = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#cumulativeSnowfall10to1()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder cumulativeSnowfall10to1(BandColumns value) {
      this.cumulativeSnowfall10to1 = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#temperature()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder temperature(BandColumns value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#snowLevel()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snowLevel(BandColumns value) {
      this.snowLevel = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#snowLevelWetBulb()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snowLevelWetBulb(BandColumns value) {
      this.snowLevelWetBulb = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#precipitation6h()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipitation6h(BandColumns value) {
      this.precipitation6h = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#snowfall6h()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snowfall6h(BandColumns value) {
      this.snowfall6h = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#slr()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder slr(@Nullable BandColumns value) {
      this.slr = value;
      return this;
    }

    /**
     * Sets {@link PlumeBands#windSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windSpeed(@Nullable BandColumns value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Builds the {@link PlumeBands}.
     *
     * @return the immutable PlumeBands
     * @throws IllegalStateException if a required value was not set
     */
    public PlumeBands build() {
      return new PlumeBands(this);
    }
  }
}
