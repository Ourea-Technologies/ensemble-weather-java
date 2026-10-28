// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Forecast at one of a location's additional elevations (ski-area base / mid / summit). Each is a
 * full point forecast built by the SAME pipeline as the primary {@code forecast} — grid assembly,
 * snowfall derivation, NGR/CSGD correction, nowcast blend, conditions, feels-like, provenance, the
 * daily windows and day totals, the snow outlook — at this elevation's coordinates ({@code
 * lat}/{@code lon}) and elevation. Every model's 2 m temperature and dewpoint are first carried
 * from the model's own terrain height to the primary's elevation (a diurnal climatological lapse at
 * the local solar hour; dewpoint 2 °C/km), as for every served point; they are then carried from
 * the primary's elevation by a lapse rate (the hour's own profile when it brackets the layer, else
 * climatology; see {@code provenance.lapse_profile} / {@code lapse_climatology}), and the learned
 * corrections are the primary's. Precipitation and snowfall are carried from the primary's
 * elevation by the local PRISM precipitation–elevation regression (see {@code
 * provenance.lapse_prism}). Wind and snow level are the forecast at those coordinates — no
 * elevation multiplier is applied to them. The location's own point is never repeated here. The
 * legacy compact {@code timeseries} points and the {@code default} flag are gone -- read {@code
 * hourly}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ElevationForecast}.
 */
public final class ElevationForecast extends Model {
  private final String name;
  private final double elevation;
  private final double lat;
  private final double lon;
  private final List<TimeSeriesPoint> hourly;
  private final List<DailyForecast> daily;
  private final @Nullable SnowOutlook snowOutlook;

  private ElevationForecast(Builder b) {
    this.name = require(b.name, "name");
    this.elevation = require(b.elevation, "elevation");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.hourly = List.copyOf(b.hourly);
    this.daily = List.copyOf(b.daily);
    this.snowOutlook = b.snowOutlook;
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
    b.name = this.name;
    b.elevation = this.elevation;
    b.lat = this.lat;
    b.lon = this.lon;
    b.hourly = this.hourly;
    b.daily = this.daily;
    b.snowOutlook = this.snowOutlook;
    return b;
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
   * The {@code elevation} value.
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * Where this elevation was forecast — its own coordinates when the catalog has them, else the
   * location's.
   *
   * @return the {@code lat} value
   */
  public double lat() {
    return lat;
  }

  /**
   * The {@code lon} value.
   *
   * @return the {@code lon} value
   */
  public double lon() {
    return lon;
  }

  /**
   * The full hourly series at this elevation — the same shape and the same code as {@code
   * forecast.timeseries} (never with {@code model_means}). With {@code members=true} each hour
   * carries THIS elevation's {@code members}: every member lapsed by the same per-hour offset as
   * the headline temperature and dewpoint, its precipitation carried by the same PRISM ratio (over
   * the hours its {@code precipitation_hours} span), and its snowfall derived by the member
   * snowfall rule at this elevation; in the response's {@code members_view} (Free {@code plume} /
   * Pro {@code full}). Same gap rule: key on {@code valid_time}. Omitted with {@code hourly=false}.
   *
   * @return the {@code hourly} values; empty when the response has none
   */
  public List<TimeSeriesPoint> hourly() {
    return hourly;
  }

  /**
   * Daily summaries at this elevation — the same daily build as {@code forecast.daily} (omitted
   * when empty).
   *
   * @return the {@code daily} values; empty when the response has none
   */
  public List<DailyForecast> daily() {
    return daily;
  }

  /**
   * Rolling snow totals at this elevation, as {@code forecast.snow_outlook} (omitted when there are
   * no dailies).
   *
   * @return the {@code snow_outlook} value, or empty when the response omits it
   */
  public Optional<SnowOutlook> snowOutlook() {
    return Optional.ofNullable(snowOutlook);
  }

  @Override
  Object[] values() {
    return new Object[] {name, elevation, lat, lon, hourly, daily, snowOutlook};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "name", "elevation", "lat", "lon", "hourly", "daily", "snowOutlook"
  };

  /** Builds a {@link ElevationForecast}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable Double elevation;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private List<TimeSeriesPoint> hourly = List.of();
    private List<DailyForecast> daily = List.of();
    private @Nullable SnowOutlook snowOutlook;

    private Builder() {}

    /**
     * Sets {@link ElevationForecast#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link ElevationForecast#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link ElevationForecast#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link ElevationForecast#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link ElevationForecast#hourly()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder hourly(List<TimeSeriesPoint> values) {
      this.hourly = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ElevationForecast#daily()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder daily(List<DailyForecast> values) {
      this.daily = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ElevationForecast#snowOutlook()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowOutlook(@Nullable SnowOutlook value) {
      this.snowOutlook = value;
      return this;
    }

    /**
     * Builds the {@link ElevationForecast}.
     *
     * @return the immutable ElevationForecast
     * @throws IllegalStateException if a required value was not set
     */
    public ElevationForecast build() {
      return new ElevationForecast(this);
    }
  }
}
