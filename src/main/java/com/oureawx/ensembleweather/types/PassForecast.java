// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * "Is the pass getting weather?" at the pass's own point and elevation: forecast quantities only,
 * in the response's units. {@code snow_24h} is the default pass page's own (<code>
 * /locations/&#123;state&#125;/&#123;slug&#125;</code>, unpinned, the nowcast on); the hours, snow
 * spells, windows and chances are the pass's forecast from the current hour with its members
 * ({@code nowcast=false}, rebuilt once an hour), and every chance but the gust's is a share of
 * those members. It states weather, never a road verdict: no closure, chain or hazard calls of our
 * own. The DOT's own status ({@code official_status}), its road sensors ({@code road_sensors}) and
 * its cameras ({@code cameras}) ride beside the forecast, each attributed to the DOT and never
 * mixed into a forecast number.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassForecast}.
 */
public final class PassForecast extends Model {
  private final double elevation;
  private final double elevationM;
  private final Instant from;
  private final PassSnow24h snow24h;
  private final PassChances chances;
  private final List<PassSnowWindow> snowWindows;
  private final List<PassHour> hours;
  private final PassTravelWindows windows;
  private final @Nullable PassOfficialStatus officialStatus;
  private final List<PassRoadSensor> roadSensors;
  private final List<PassCamera> cameras;

  private PassForecast(Builder b) {
    this.elevation = require(b.elevation, "elevation");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.from = require(b.from, "from");
    this.snow24h = require(b.snow24h, "snow_24h");
    this.chances = require(b.chances, "chances");
    this.snowWindows = List.copyOf(b.snowWindows);
    this.hours = List.copyOf(b.hours);
    this.windows = require(b.windows, "windows");
    this.officialStatus = b.officialStatus;
    this.roadSensors = List.copyOf(b.roadSensors);
    this.cameras = List.copyOf(b.cameras);
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
    b.elevation = this.elevation;
    b.elevationM = this.elevationM;
    b.from = this.from;
    b.snow24h = this.snow24h;
    b.chances = this.chances;
    b.snowWindows = this.snowWindows;
    b.hours = this.hours;
    b.windows = this.windows;
    b.officialStatus = this.officialStatus;
    b.roadSensors = this.roadSensors;
    b.cameras = this.cameras;
    return b;
  }

  /**
   * The pass's elevation, in the response's length units (m, ft).
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * The pass's elevation in metres, whatever {@code units} is.
   *
   * @return the {@code elevation_m} value
   */
  public double elevationM() {
    return elevationM;
  }

  /**
   * The first hour of {@code hours} (the hour containing now); the 24 h and 48 h windows open here.
   *
   * @return the {@code from} value
   */
  public Instant from() {
    return from;
  }

  /**
   * The {@code snow_24h} value.
   *
   * @return the {@code snow_24h} value
   */
  public PassSnow24h snow24h() {
    return snow24h;
  }

  /**
   * The {@code chances} value.
   *
   * @return the {@code chances} value
   */
  public PassChances chances() {
    return chances;
  }

  /**
   * Each spell of snow at the pass in the next 48 h, in time order; empty when none. A spell is a
   * run of hours whose snowfall mean is at least 0.04 in (1 mm) an hour, with breaks of up to 2 h
   * joined, and a total of at least a trace (0.1 in, 2.54 mm). It is a timing window, not a split
   * of a storm into days (the storm-band ruling, is open).
   *
   * @return the {@code snow_windows} values; empty when the response has none
   */
  public List<PassSnowWindow> snowWindows() {
    return snowWindows;
  }

  /**
   * The next 48 hours at the pass, from {@code from}, one per hour.
   *
   * @return the {@code hours} values; empty when the response has none
   */
  public List<PassHour> hours() {
    return hours;
  }

  /**
   * The {@code windows} value.
   *
   * @return the {@code windows} value
   */
  public PassTravelWindows windows() {
    return windows;
  }

  /**
   * The {@code official_status} value.
   *
   * @return the {@code official_status} value, or empty when the response omits it
   */
  public Optional<PassOfficialStatus> officialStatus() {
    return Optional.ofNullable(officialStatus);
  }

  /**
   * The state DOT road-weather stations nearest the pass, nearest first: at most 3, within 25 km,
   * each with its newest reading in the last 3 h. What the sensors measured, never our forecast and
   * never the DOT's official status. A reading the observation QC flagged is omitted. Omitted when
   * no road station qualifies.
   *
   * @return the {@code road_sensors} values; empty when the response has none
   */
  public List<PassRoadSensor> roadSensors() {
    return roadSensors;
  }

  /**
   * The DOT's own cameras at the pass, in the order the pass's curation lists them. The client
   * loads {@code image_url} from the DOT itself (Ourea neither copies nor stores the image) and
   * credits {@code attribution}. Omitted where the pass maps to no camera.
   *
   * @return the {@code cameras} values; empty when the response has none
   */
  public List<PassCamera> cameras() {
    return cameras;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      elevation,
      elevationM,
      from,
      snow24h,
      chances,
      snowWindows,
      hours,
      windows,
      officialStatus,
      roadSensors,
      cameras
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "elevation",
    "elevationM",
    "from",
    "snow24h",
    "chances",
    "snowWindows",
    "hours",
    "windows",
    "officialStatus",
    "roadSensors",
    "cameras"
  };

  /** Builds a {@link PassForecast}. */
  public static final class Builder {
    private @Nullable Double elevation;
    private @Nullable Double elevationM;
    private @Nullable Instant from;
    private @Nullable PassSnow24h snow24h;
    private @Nullable PassChances chances;
    private List<PassSnowWindow> snowWindows = List.of();
    private List<PassHour> hours = List.of();
    private @Nullable PassTravelWindows windows;
    private @Nullable PassOfficialStatus officialStatus;
    private List<PassRoadSensor> roadSensors = List.of();
    private List<PassCamera> cameras = List.of();

    private Builder() {}

    /**
     * Sets {@link PassForecast#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link PassForecast#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link PassForecast#from()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder from(Instant value) {
      this.from = value;
      return this;
    }

    /**
     * Sets {@link PassForecast#snow24h()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snow24h(PassSnow24h value) {
      this.snow24h = value;
      return this;
    }

    /**
     * Sets {@link PassForecast#chances()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder chances(PassChances value) {
      this.chances = value;
      return this;
    }

    /**
     * Sets {@link PassForecast#snowWindows()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowWindows(List<PassSnowWindow> values) {
      this.snowWindows = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PassForecast#hours()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder hours(List<PassHour> values) {
      this.hours = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PassForecast#windows()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windows(PassTravelWindows value) {
      this.windows = value;
      return this;
    }

    /**
     * Sets {@link PassForecast#officialStatus()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder officialStatus(@Nullable PassOfficialStatus value) {
      this.officialStatus = value;
      return this;
    }

    /**
     * Sets {@link PassForecast#roadSensors()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder roadSensors(List<PassRoadSensor> values) {
      this.roadSensors = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PassForecast#cameras()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cameras(List<PassCamera> values) {
      this.cameras = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link PassForecast}.
     *
     * @return the immutable PassForecast
     * @throws IllegalStateException if a required value was not set
     */
    public PassForecast build() {
      return new PassForecast(this);
    }
  }
}
