// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * The day's climate normal at the forecast point: the mean, over the day of year +/-15 days of
 * every year of the station's record, of the very quantities this row states -- the local CALENDAR
 * date's maximum hourly temperature ({@code high}, the quantity {@code high} is), its minimum
 * ({@code low}) and its precipitation TOTAL ({@code precipitation}, dry days included; a daily
 * total, never an hourly amount). Units follow the request's {@code units} (°C / mm metric, °F / in
 * imperial), converted as the row's own values are; {@code distance_km} is always km. Where it
 * comes from. The nearest climatology station within 60 km of the point and within 300 m of its
 * elevation (a station far above or below the point describes another climate, and no lapse rate
 * carries a normal across a valley inversion) that holds calendar-day normals ({@code source:
 * station}, today a SNOTEL station's own hourly record, at least ten years of complete local days);
 * else the ERA5 0.25° cell the point lies in ({@code source: era5_cell}). The ERA5 normals are
 * sampled at four hours a day and state no calendar-day maximum, minimum or total, so no cell
 * answers today. No approximation: where neither has a normal the whole object is absent, and an
 * absent member ({@code precipitation} in a window too dry to fit) is absent rather than estimated.
 * The source is credited in {@code attribution}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code DayNormal}.
 */
public final class DayNormal extends Model {
  private final @Nullable Double high;
  private final @Nullable Double low;
  private final @Nullable Double precipitation;
  private final String source;
  private final double distanceKm;

  private DayNormal(Builder b) {
    this.high = b.high;
    this.low = b.low;
    this.precipitation = b.precipitation;
    this.source = require(b.source, "source");
    this.distanceKm = require(b.distanceKm, "distance_km");
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
    b.high = this.high;
    b.low = this.low;
    b.precipitation = this.precipitation;
    b.source = this.source;
    b.distanceKm = this.distanceKm;
    return b;
  }

  /**
   * Mean local-calendar-day maximum temperature.
   *
   * @return the {@code high} value, or empty when the response omits it
   */
  public OptionalDouble high() {
    return high == null ? OptionalDouble.empty() : OptionalDouble.of(high);
  }

  /**
   * Mean local-calendar-day minimum temperature.
   *
   * @return the {@code low} value, or empty when the response omits it
   */
  public OptionalDouble low() {
    return low == null ? OptionalDouble.empty() : OptionalDouble.of(low);
  }

  /**
   * Mean local-calendar-day precipitation total, dry days included.
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public OptionalDouble precipitation() {
    return precipitation == null ? OptionalDouble.empty() : OptionalDouble.of(precipitation);
  }

  /**
   * A climatology station's own record, or the ERA5 cell of the point. One of {@code station},
   * {@code era5_cell}; read it as a string, since the API may add values.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * Great-circle distance from the point to the station (or the cell centre), km.
   *
   * @return the {@code distance_km} value
   */
  public double distanceKm() {
    return distanceKm;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {high, low, precipitation, source, distanceKm};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"high", "low", "precipitation", "source", "distanceKm"};

  /** Builds a {@link DayNormal}. */
  public static final class Builder {
    private @Nullable Double high;
    private @Nullable Double low;
    private @Nullable Double precipitation;
    private @Nullable String source;
    private @Nullable Double distanceKm;

    private Builder() {}

    /**
     * Sets {@link DayNormal#high()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder high(@Nullable Double value) {
      this.high = value;
      return this;
    }

    /**
     * Sets {@link DayNormal#low()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder low(@Nullable Double value) {
      this.low = value;
      return this;
    }

    /**
     * Sets {@link DayNormal#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable Double value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link DayNormal#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link DayNormal#distanceKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder distanceKm(double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Builds the {@link DayNormal}.
     *
     * @return the immutable DayNormal
     * @throws IllegalStateException if a required value was not set
     */
    public DayNormal build() {
      return new DayNormal(this);
    }
  }
}
