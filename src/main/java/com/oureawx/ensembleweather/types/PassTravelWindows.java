// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The quietest and the busiest stretch of weather at the pass in the next {@code horizon_hours}, by
 * one fixed rule on the hours above: an hour has weather when it is snowing ({@code snowfall} at
 * least 0.04 in, 1 mm), {@code freezing_on_wet_road}, or {@code gust_over_40mph}. {@code
 * least_weather} is the longest run of hours without weather (earliest on a tie); {@code
 * most_weather} the run of hours with weather holding the most snowfall, then the most freezing
 * hours, then the highest gust. A description of the weather, never advice on whether to travel.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassTravelWindows}.
 */
public final class PassTravelWindows extends Model {
  private final int horizonHours;
  private final @Nullable PassTravelWindow leastWeather;
  private final @Nullable PassTravelWindow mostWeather;

  private PassTravelWindows(Builder b) {
    this.horizonHours = require(b.horizonHours, "horizon_hours");
    this.leastWeather = b.leastWeather;
    this.mostWeather = b.mostWeather;
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
    b.horizonHours = this.horizonHours;
    b.leastWeather = this.leastWeather;
    b.mostWeather = this.mostWeather;
    return b;
  }

  /**
   * The horizon's length in hours, 24.
   *
   * @return the {@code horizon_hours} value
   */
  public int horizonHours() {
    return horizonHours;
  }

  /**
   * Null when every hour has weather.
   *
   * @return the {@code least_weather} value, or empty when the response omits it
   */
  public Optional<PassTravelWindow> leastWeather() {
    return Optional.ofNullable(leastWeather);
  }

  /**
   * Null when no hour has weather.
   *
   * @return the {@code most_weather} value, or empty when the response omits it
   */
  public Optional<PassTravelWindow> mostWeather() {
    return Optional.ofNullable(mostWeather);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {horizonHours, leastWeather, mostWeather};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"horizonHours", "leastWeather", "mostWeather"};

  /** Builds a {@link PassTravelWindows}. */
  public static final class Builder {
    private @Nullable Integer horizonHours;
    private @Nullable PassTravelWindow leastWeather;
    private @Nullable PassTravelWindow mostWeather;

    private Builder() {}

    /**
     * Sets {@link PassTravelWindows#horizonHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder horizonHours(int value) {
      this.horizonHours = value;
      return this;
    }

    /**
     * Sets {@link PassTravelWindows#leastWeather()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder leastWeather(@Nullable PassTravelWindow value) {
      this.leastWeather = value;
      return this;
    }

    /**
     * Sets {@link PassTravelWindows#mostWeather()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder mostWeather(@Nullable PassTravelWindow value) {
      this.mostWeather = value;
      return this;
    }

    /**
     * Builds the {@link PassTravelWindows}.
     *
     * @return the immutable PassTravelWindows
     * @throws IllegalStateException if a required value was not set
     */
    public PassTravelWindows build() {
      return new PassTravelWindows(this);
    }
  }
}
