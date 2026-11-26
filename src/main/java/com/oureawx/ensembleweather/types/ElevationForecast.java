// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

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
  private final double height;
  private final double lat;
  private final double lon;
  private final List<TimeSeriesPoint> hourly;
  private final List<DailyForecast> daily;
  private final @Nullable SnowOutlook snowOutlook;

  private final @Nullable String displayName;
  private final List<NearestSensor> nearestSensors;
  private final @Nullable PrecipOutlook precipitationOutlook;
  private final @Nullable MemberPlume plume;
  private final @Nullable SnowOnGround snowOnGround;

  private ElevationForecast(Builder b) {
    this.name = require(b.name, "name");
    this.height = require(b.height, "elevation");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.hourly = List.copyOf(b.hourly);
    this.daily = List.copyOf(b.daily);
    this.snowOutlook = b.snowOutlook;
    this.displayName = b.displayName;
    this.nearestSensors = List.copyOf(b.nearestSensors);
    this.precipitationOutlook = b.precipitationOutlook;
    this.plume = b.plume;
    this.snowOnGround = b.snowOnGround;
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
    b.height = this.height;
    b.lat = this.lat;
    b.lon = this.lon;
    b.hourly = this.hourly;
    b.daily = this.daily;
    b.snowOutlook = this.snowOutlook;
    b.displayName = this.displayName;
    b.nearestSensors = this.nearestSensors;
    b.precipitationOutlook = this.precipitationOutlook;
    b.plume = this.plume;
    b.snowOnGround = this.snowOnGround;
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
   * (JSON {@code elevation}) The {@code elevation} value.
   *
   * @return the {@code elevation} value
   */
  public double height() {
    return height;
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

  /**
   * What the place is called, as {@code location.elevations[].display_name}; absent where the
   * catalog has no name for it.
   *
   * @return the {@code display_name} value, or empty when the response omits it
   */
  public Optional<String> displayName() {
    return Optional.ofNullable(displayName);
  }

  /**
   * A named spot's own nearby observation sensors, ranked around ITS point and elevation as the
   * location's {@code nearest_sensors} are around the location's. Omitted for an elevation band
   * without its own coordinates, and wherever the location's own list is omitted ({@code
   * nowcast=false}, {@code members=plume}).
   *
   * @return the {@code nearest_sensors} values; empty when the response has none
   */
  public List<NearestSensor> nearestSensors() {
    return nearestSensors;
  }

  /**
   * Rolling liquid totals at this elevation, as {@code forecast.precipitation_outlook}.
   *
   * @return the {@code precipitation_outlook} value, or empty when the response omits it
   */
  public Optional<PrecipOutlook> precipitationOutlook() {
    return Optional.ofNullable(precipitationOutlook);
  }

  /**
   * This elevation's members in columns, as {@code forecast.plume} -- present only with {@code
   * members=plume}, instead of {@code hourly}, {@code daily}, {@code snow_outlook} and {@code
   * precipitation_outlook}.
   *
   * @return the {@code plume} value, or empty when the response omits it
   */
  public Optional<MemberPlume> plume() {
    return Optional.ofNullable(plume);
  }

  /**
   * The snow on the ground at this elevation, as the response's own {@code snow_on_ground}; absent
   * where no snowpack column runs for it.
   *
   * @return the {@code snow_on_ground} value, or empty when the response omits it
   */
  public Optional<SnowOnGround> snowOnGround() {
    return Optional.ofNullable(snowOnGround);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      name,
      height,
      lat,
      lon,
      hourly,
      daily,
      snowOutlook,
      displayName,
      nearestSensors,
      precipitationOutlook,
      plume,
      snowOnGround
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "name",
    "height",
    "lat",
    "lon",
    "hourly",
    "daily",
    "snowOutlook",
    "displayName",
    "nearestSensors",
    "precipitationOutlook",
    "plume",
    "snowOnGround"
  };

  /** Builds a {@link ElevationForecast}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable Double height;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private List<TimeSeriesPoint> hourly = List.of();
    private List<DailyForecast> daily = List.of();
    private @Nullable SnowOutlook snowOutlook;

    private @Nullable String displayName;
    private List<NearestSensor> nearestSensors = List.of();
    private @Nullable PrecipOutlook precipitationOutlook;
    private @Nullable MemberPlume plume;
    private @Nullable SnowOnGround snowOnGround;

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
     * Sets {@link ElevationForecast#height()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder height(double value) {
      this.height = value;
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
     * Sets {@link ElevationForecast#displayName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder displayName(@Nullable String value) {
      this.displayName = value;
      return this;
    }

    /**
     * Sets {@link ElevationForecast#nearestSensors()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder nearestSensors(List<NearestSensor> values) {
      this.nearestSensors = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ElevationForecast#precipitationOutlook()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationOutlook(@Nullable PrecipOutlook value) {
      this.precipitationOutlook = value;
      return this;
    }

    /**
     * Sets {@link ElevationForecast#plume()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder plume(@Nullable MemberPlume value) {
      this.plume = value;
      return this;
    }

    /**
     * Sets {@link ElevationForecast#snowOnGround()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowOnGround(@Nullable SnowOnGround value) {
      this.snowOnGround = value;
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
