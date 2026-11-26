// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A PassTravelWindow from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassTravelWindow}.
 */
public final class PassTravelWindow extends Model {
  private final Instant start;
  private final Instant end;
  private final double snowfall;
  private final int freezingOnWetRoadHours;
  private final @Nullable Double maxWindGust;

  private PassTravelWindow(Builder b) {
    this.start = require(b.start, "start");
    this.end = require(b.end, "end");
    this.snowfall = require(b.snowfall, "snowfall");
    this.freezingOnWetRoadHours = require(b.freezingOnWetRoadHours, "freezing_on_wet_road_hours");
    this.maxWindGust = b.maxWindGust;
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
    b.start = this.start;
    b.end = this.end;
    b.snowfall = this.snowfall;
    b.freezingOnWetRoadHours = this.freezingOnWetRoadHours;
    b.maxWindGust = this.maxWindGust;
    return b;
  }

  /**
   * When the run's first hour begins.
   *
   * @return the {@code start} value
   */
  public Instant start() {
    return start;
  }

  /**
   * When its last hour ends.
   *
   * @return the {@code end} value
   */
  public Instant end() {
    return end;
  }

  /**
   * The sum of the hourly snowfall means over the run (mm, in).
   *
   * @return the {@code snowfall} value
   */
  public double snowfall() {
    return snowfall;
  }

  /**
   * The {@code freezing_on_wet_road_hours} value.
   *
   * @return the {@code freezing_on_wet_road_hours} value
   */
  public int freezingOnWetRoadHours() {
    return freezingOnWetRoadHours;
  }

  /**
   * The highest hourly gust mean in the run; omitted where no gust is served.
   *
   * @return the {@code max_wind_gust} value, or empty when the response omits it
   */
  public OptionalDouble maxWindGust() {
    return maxWindGust == null ? OptionalDouble.empty() : OptionalDouble.of(maxWindGust);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {start, end, snowfall, freezingOnWetRoadHours, maxWindGust};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "start", "end", "snowfall", "freezingOnWetRoadHours", "maxWindGust"
  };

  /** Builds a {@link PassTravelWindow}. */
  public static final class Builder {
    private @Nullable Instant start;
    private @Nullable Instant end;
    private @Nullable Double snowfall;
    private @Nullable Integer freezingOnWetRoadHours;
    private @Nullable Double maxWindGust;

    private Builder() {}

    /**
     * Sets {@link PassTravelWindow#start()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder start(Instant value) {
      this.start = value;
      return this;
    }

    /**
     * Sets {@link PassTravelWindow#end()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder end(Instant value) {
      this.end = value;
      return this;
    }

    /**
     * Sets {@link PassTravelWindow#snowfall()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snowfall(double value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link PassTravelWindow#freezingOnWetRoadHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder freezingOnWetRoadHours(int value) {
      this.freezingOnWetRoadHours = value;
      return this;
    }

    /**
     * Sets {@link PassTravelWindow#maxWindGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder maxWindGust(@Nullable Double value) {
      this.maxWindGust = value;
      return this;
    }

    /**
     * Builds the {@link PassTravelWindow}.
     *
     * @return the immutable PassTravelWindow
     * @throws IllegalStateException if a required value was not set
     */
    public PassTravelWindow build() {
      return new PassTravelWindow(this);
    }
  }
}
