// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A EnsembleStats from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code EnsembleStats}.
 */
public final class EnsembleStats extends Model {
  private final Quantiles temperature;
  private final @Nullable Quantiles feelsLike;
  private final @Nullable Quantiles dewpoint;
  private final Quantiles precipitation;
  private final Quantiles windSpeed;
  private final @Nullable Quantiles windGust;
  private final CircularStats windDirection;
  private final @Nullable Quantiles snowLevel;
  private final @Nullable Quantiles slr;
  private final @Nullable Quantiles snowfall;
  private final @Nullable Quantiles cloudCover;
  private final @Nullable Quantiles skinTemperature;
  private final @Nullable Quantiles mslp;
  private final @Nullable Quantiles cape;
  private final @Nullable Quantiles relativeHumidity;

  private EnsembleStats(Builder b) {
    this.temperature = require(b.temperature, "temperature");
    this.feelsLike = b.feelsLike;
    this.dewpoint = b.dewpoint;
    this.precipitation = require(b.precipitation, "precipitation");
    this.windSpeed = require(b.windSpeed, "wind_speed");
    this.windGust = b.windGust;
    this.windDirection = require(b.windDirection, "wind_direction");
    this.snowLevel = b.snowLevel;
    this.slr = b.slr;
    this.snowfall = b.snowfall;
    this.cloudCover = b.cloudCover;
    this.skinTemperature = b.skinTemperature;
    this.mslp = b.mslp;
    this.cape = b.cape;
    this.relativeHumidity = b.relativeHumidity;
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
    b.temperature = this.temperature;
    b.feelsLike = this.feelsLike;
    b.dewpoint = this.dewpoint;
    b.precipitation = this.precipitation;
    b.windSpeed = this.windSpeed;
    b.windGust = this.windGust;
    b.windDirection = this.windDirection;
    b.snowLevel = this.snowLevel;
    b.slr = this.slr;
    b.snowfall = this.snowfall;
    b.cloudCover = this.cloudCover;
    b.skinTemperature = this.skinTemperature;
    b.mslp = this.mslp;
    b.cape = this.cape;
    b.relativeHumidity = this.relativeHumidity;
    return b;
  }

  /**
   * The {@code temperature} value.
   *
   * @return the {@code temperature} value
   */
  public Quantiles temperature() {
    return temperature;
  }

  /**
   * Apparent temperature, the NWS convention: the wind chill (NWS 2001, Osczevski &amp; Bluestein
   * 2005) where the air is at or below 10 C (50 F) and the 10 m wind above 4.8 km/h (3 mph); the
   * heat index (NWS / Rothfusz 1990) where the air is at or above 26.7 C (80 F); the air
   * temperature everywhere else. It is computed for EACH ensemble member from that member's own
   * temperature, wind and dewpoint (the calibrated values at the member's rank in each), and only
   * then summarised, so the statistics describe states members actually forecast: a cold, calm
   * member and a warm, windy one never combine into a cold, windy extreme. Every quantile lies
   * within the range of the members' own values. A member with no dewpoint of its own takes the
   * ensemble's mean dewpoint; with no dewpoint at all for the hour, no heat index is applied.
   * Omitted when the hour has no members to compute it from. This is the default index ({@code
   * feels_like=nws}).
   *
   * @return the {@code feels_like} value, or empty when the response omits it
   */
  public Optional<Quantiles> feelsLike() {
    return Optional.ofNullable(feelsLike);
  }

  /**
   * The {@code dewpoint} value.
   *
   * @return the {@code dewpoint} value, or empty when the response omits it
   */
  public Optional<Quantiles> dewpoint() {
    return Optional.ofNullable(dewpoint);
  }

  /**
   * The {@code precipitation} value.
   *
   * @return the {@code precipitation} value
   */
  public Quantiles precipitation() {
    return precipitation;
  }

  /**
   * The {@code wind_speed} value.
   *
   * @return the {@code wind_speed} value
   */
  public Quantiles windSpeed() {
    return windSpeed;
  }

  /**
   * Peak gust (same unit as {@code wind_speed}). Indicates lift closure risk at ski areas. Derived
   * from the served sustained wind: every rank (and the mean and spread) is {@code wind_speed}
   * times a fitted gust factor of 1.54, so a gust is never below the wind it accompanies. The
   * wind's calibration (station fit, spread) is the gust's, and so is its {@code provenance}.
   * Absent at lead 0.
   *
   * @return the {@code wind_gust} value, or empty when the response omits it
   */
  public Optional<Quantiles> windGust() {
    return Optional.ofNullable(windGust);
  }

  /**
   * The wind direction as a CIRCULAR distribution: the resultant mean bearing, an offset-frame
   * standard deviation, and the central 50 % and 90 % of the members as clockwise arcs (an arc's
   * end may be numerically smaller than its start: it crosses north). Degrees in every unit system.
   * This is the one direction shape -- the linear {@code Quantiles} ladder it replaced was
   * non-monotone across north, with a mean outside {@code [min, max]}, and.
   *
   * @return the {@code wind_direction} value
   */
  public CircularStats windDirection() {
    return windDirection;
  }

  /**
   * The {@code snow_level} value.
   *
   * @return the {@code snow_level} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowLevel() {
    return Optional.ofNullable(snowLevel);
  }

  /**
   * The {@code slr} value.
   *
   * @return the {@code slr} value, or empty when the response omits it
   */
  public Optional<Quantiles> slr() {
    return Optional.ofNullable(slr);
  }

  /**
   * Hourly snowfall depth at the location's elevation (mm, or inches for imperial callers): the
   * hour's precipitation times the effective snow-to-liquid ratio at the point, with {@code
   * probability} the chance of measurable snow. An hour whose snowfall was ASSESSED --
   * precipitation, SLR and snow level all present -- and came out dry (the point is below the snow
   * level, the surface is too warm, or the median precipitation is under the drizzle gate) serves a
   * present zero: every rank {@code 0} and {@code probability} {@code 0}. The field is absent only
   * when an input is missing and the hour could not be assessed, so absent means "unknown", never
   * "no snow" -- the convention the member plumes' {@code snowfall} already follows.
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowfall() {
    return Optional.ofNullable(snowfall);
  }

  /**
   * Cloud cover percentage (0-100%).
   *
   * @return the {@code cloud_cover} value, or empty when the response omits it
   */
  public Optional<Quantiles> cloudCover() {
    return Optional.ofNullable(cloudCover);
  }

  /**
   * Skin / ground surface temperature in the caller's temperature units. Sourced from IFS ENS
   * {@code skt} — a proxy for snow-surface temperature at ski areas and pavement-approximate
   * temperature off-road. Absent when no model with this variable contributed to this hour's blend.
   * Only with {@code include=skin_temperature}.
   *
   * @return the {@code skin_temperature} value, or empty when the response omits it
   */
  public Optional<Quantiles> skinTemperature() {
    return Optional.ofNullable(skinTemperature);
  }

  /**
   * Mean sea-level pressure: hPa for {@code metric} and {@code kelvin}, inHg for {@code imperial}.
   * A raw blend of the contributing models; no pressure observation trains a correction. Omitted
   * when no contributing model published it for this hour. Only with {@code include=mslp}.
   *
   * @return the {@code mslp} value, or empty when the response omits it
   */
  public Optional<Quantiles> mslp() {
    return Optional.ofNullable(mslp);
  }

  /**
   * Convective available potential energy, J/kg in every unit system. A raw blend of the models
   * that publish it (AIFS and AIGEFS do not). Omitted when none contributed to this hour — never
   * served as a zero, which would read as "stable".
   *
   * @return the {@code cape} value, or empty when the response omits it
   */
  public Optional<Quantiles> cape() {
    return Optional.ofNullable(cape);
  }

  /**
   * Relative humidity, percent 0–100 in every unit system, computed like {@code feels_like}: for
   * each ensemble member from its own calibrated temperature and dewpoint, then summarised, so no
   * percentile pairs one member's temperature with another's dewpoint. Omitted when {@code
   * dewpoint} is.
   *
   * @return the {@code relative_humidity} value, or empty when the response omits it
   */
  public Optional<Quantiles> relativeHumidity() {
    return Optional.ofNullable(relativeHumidity);
  }

  @Override
  Object[] values() {
    return new Object[] {
      temperature,
      feelsLike,
      dewpoint,
      precipitation,
      windSpeed,
      windGust,
      windDirection,
      snowLevel,
      slr,
      snowfall,
      cloudCover,
      skinTemperature,
      mslp,
      cape,
      relativeHumidity
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "temperature",
    "feelsLike",
    "dewpoint",
    "precipitation",
    "windSpeed",
    "windGust",
    "windDirection",
    "snowLevel",
    "slr",
    "snowfall",
    "cloudCover",
    "skinTemperature",
    "mslp",
    "cape",
    "relativeHumidity"
  };

  /** Builds a {@link EnsembleStats}. */
  public static final class Builder {
    private @Nullable Quantiles temperature;
    private @Nullable Quantiles feelsLike;
    private @Nullable Quantiles dewpoint;
    private @Nullable Quantiles precipitation;
    private @Nullable Quantiles windSpeed;
    private @Nullable Quantiles windGust;
    private @Nullable CircularStats windDirection;
    private @Nullable Quantiles snowLevel;
    private @Nullable Quantiles slr;
    private @Nullable Quantiles snowfall;
    private @Nullable Quantiles cloudCover;
    private @Nullable Quantiles skinTemperature;
    private @Nullable Quantiles mslp;
    private @Nullable Quantiles cape;
    private @Nullable Quantiles relativeHumidity;

    private Builder() {}

    /**
     * Sets {@link EnsembleStats#temperature()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder temperature(Quantiles value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#feelsLike()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder feelsLike(@Nullable Quantiles value) {
      this.feelsLike = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#dewpoint()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder dewpoint(@Nullable Quantiles value) {
      this.dewpoint = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#precipitation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipitation(Quantiles value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#windSpeed()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windSpeed(Quantiles value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#windGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windGust(@Nullable Quantiles value) {
      this.windGust = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#windDirection()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windDirection(CircularStats value) {
      this.windDirection = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#snowLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevel(@Nullable Quantiles value) {
      this.snowLevel = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#slr()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder slr(@Nullable Quantiles value) {
      this.slr = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable Quantiles value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#cloudCover()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cloudCover(@Nullable Quantiles value) {
      this.cloudCover = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#skinTemperature()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder skinTemperature(@Nullable Quantiles value) {
      this.skinTemperature = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#mslp()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder mslp(@Nullable Quantiles value) {
      this.mslp = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#cape()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cape(@Nullable Quantiles value) {
      this.cape = value;
      return this;
    }

    /**
     * Sets {@link EnsembleStats#relativeHumidity()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder relativeHumidity(@Nullable Quantiles value) {
      this.relativeHumidity = value;
      return this;
    }

    /**
     * Builds the {@link EnsembleStats}.
     *
     * @return the immutable EnsembleStats
     * @throws IllegalStateException if a required value was not set
     */
    public EnsembleStats build() {
      return new EnsembleStats(this);
    }
  }
}
