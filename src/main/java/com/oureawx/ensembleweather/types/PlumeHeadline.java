// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.OptionalDouble;

/**
 * The lines a plume draws over its members, at every served hour ({@code valid_time}): the blend's
 * {@code temperature} and {@code snow_level} means ({@code ensemble.*.mean}) and its running totals
 * ({@code cumulative_precipitation}, {@code cumulative_snowfall}: the "Ourea forecast" line), and
 * that snowfall line at a 10:1 snow-to-liquid ratio ({@code cumulative_snowfall_10to1}), and its
 * {@code wind_speed} mean and its snowfall in each 6 hours ({@code snowfall_6h}). {@code null}
 * where the hour serves none.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PlumeHeadline}.
 */
public final class PlumeHeadline extends Model {
  private final List<Instant> validTime;
  private final List<OptionalDouble> temperature;
  private final List<OptionalDouble> snowLevel;
  private final List<OptionalDouble> snowLevelWetBulb;
  private final List<OptionalDouble> cumulativePrecipitation;
  private final List<OptionalDouble> cumulativeSnowfall;
  private final List<OptionalDouble> cumulativeSnowfall10to1;
  private final List<OptionalDouble> windSpeed;
  private final List<OptionalDouble> snowfall6h;

  private PlumeHeadline(Builder b) {
    this.validTime = List.copyOf(b.validTime);
    this.temperature = List.copyOf(b.temperature);
    this.snowLevel = List.copyOf(b.snowLevel);
    this.snowLevelWetBulb = List.copyOf(b.snowLevelWetBulb);
    this.cumulativePrecipitation = List.copyOf(b.cumulativePrecipitation);
    this.cumulativeSnowfall = List.copyOf(b.cumulativeSnowfall);
    this.cumulativeSnowfall10to1 = List.copyOf(b.cumulativeSnowfall10to1);
    this.windSpeed = List.copyOf(b.windSpeed);
    this.snowfall6h = List.copyOf(b.snowfall6h);
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
    b.temperature = this.temperature;
    b.snowLevel = this.snowLevel;
    b.snowLevelWetBulb = this.snowLevelWetBulb;
    b.cumulativePrecipitation = this.cumulativePrecipitation;
    b.cumulativeSnowfall = this.cumulativeSnowfall;
    b.cumulativeSnowfall10to1 = this.cumulativeSnowfall10to1;
    b.windSpeed = this.windSpeed;
    b.snowfall6h = this.snowfall6h;
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
   * The {@code temperature} value.
   *
   * @return the {@code temperature} values; empty when the response has none
   */
  public List<OptionalDouble> temperature() {
    return temperature;
  }

  /**
   * The {@code snow_level} value.
   *
   * @return the {@code snow_level} values; empty when the response has none
   */
  public List<OptionalDouble> snowLevel() {
    return snowLevel;
  }

  /**
   * The blend's snow level on the NWS scale ({@code ensemble.snow_level_wet_bulb.mean}, the height
   * of the 0.5 C wet-bulb); {@code snow_level} is the rain line. {@code null} exactly where {@code
   * snow_level} is.
   *
   * @return the {@code snow_level_wet_bulb} values; empty when the response has none
   */
  public List<OptionalDouble> snowLevelWetBulb() {
    return snowLevelWetBulb;
  }

  /**
   * The {@code cumulative_precipitation} value.
   *
   * @return the {@code cumulative_precipitation} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativePrecipitation() {
    return cumulativePrecipitation;
  }

  /**
   * The hourly series' {@code cumulative_snowfall}: the running sum of the day cards' snowfall
   * ({@code daily[].precipitation_display.snow_amount}), spread inside each local date by the
   * blend's hourly mean snowfall, so at the end of each date served whole it equals the running sum
   * of the cards. The card's snow is the day's mean, so this is the running mean total. The "Ourea
   * forecast" line and its "Total by" readout.
   *
   * @return the {@code cumulative_snowfall} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativeSnowfall() {
    return cumulativeSnowfall;
  }

  /**
   * The headline's running snowfall at a fixed 10:1 snow-to-liquid ratio, in the snowfall units (in
   * / mm), at the amount precision: each hour's served snowfall with its SLR ({@code
   * ensemble.slr.mean}) replaced by 10 -- the liquid that falls as snow, times 10 (0.1 in of it is
   * 1 in; 1 mm is 1 cm) -- summed from the first served hour over the blend's hourly means. It is
   * not yet on the day cards' statistic, as {@code cumulative_snowfall} is (the cards have no 10:1
   * amount). Rain stays rain: the phase gates of {@code snowfall} all apply, as on the {@code
   * snowfall_total_10to1} map. Beside {@code cumulative_snowfall} it is the "Ourea ratio vs 10:1"
   * comparison. {@code null} where the hour serves no snowfall.
   *
   * @return the {@code cumulative_snowfall_10to1} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativeSnowfall10to1() {
    return cumulativeSnowfall10to1;
  }

  /**
   * The forecast's hourly wind speed, the hourly series' {@code ensemble.wind_speed.mean}, in the
   * wind unit (mph / km/h / m/s as {@code units} says) at the wind precision (0.1), at every served
   * hour. It is the forecast's own wind, not a member's, so it is served in every view, the plume
   * view included (where the members' wind and {@code bands.wind_speed} are withheld).
   *
   * @return the {@code wind_speed} values; empty when the response has none
   */
  public List<OptionalDouble> windSpeed() {
    return windSpeed;
  }

  /**
   * The forecast's snowfall in the 6 hours ending at each 00/06/12/18Z hour, in the snowfall units
   * (in / mm) at the amount precision: the rise of {@code cumulative_snowfall} over {@code
   * (valid_time - 6 h, valid_time]}, computed here so no client differences the line. The 6-hour
   * amounts therefore add up to the running line, and state the day cards' statistic as it does;
   * they are not the sum of six hourly {@code ensemble.snowfall.mean}. Aligned to {@code
   * valid_time} like every headline line and {@code null} at every hour that is not 00, 06, 12 or
   * 18Z; also {@code null} where the window would open before the served window does (the first
   * served hour's window opens an hour before it, as {@code bands.snowfall_6h}'s) and where any
   * hour of the window serves no {@code cumulative_snowfall} (the hours of a last local date that
   * has no day card, which is why the running line can end before {@code bands} does: nothing is
   * extrapolated past it). The same 00/06/12/18Z steps {@code bands.snowfall_6h} is served on.
   *
   * @return the {@code snowfall_6h} values; empty when the response has none
   */
  public List<OptionalDouble> snowfall6h() {
    return snowfall6h;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      validTime,
      temperature,
      snowLevel,
      snowLevelWetBulb,
      cumulativePrecipitation,
      cumulativeSnowfall,
      cumulativeSnowfall10to1,
      windSpeed,
      snowfall6h
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "validTime",
    "temperature",
    "snowLevel",
    "snowLevelWetBulb",
    "cumulativePrecipitation",
    "cumulativeSnowfall",
    "cumulativeSnowfall10to1",
    "windSpeed",
    "snowfall6h"
  };

  /** Builds a {@link PlumeHeadline}. */
  public static final class Builder {
    private List<Instant> validTime = List.of();
    private List<OptionalDouble> temperature = List.of();
    private List<OptionalDouble> snowLevel = List.of();
    private List<OptionalDouble> snowLevelWetBulb = List.of();
    private List<OptionalDouble> cumulativePrecipitation = List.of();
    private List<OptionalDouble> cumulativeSnowfall = List.of();
    private List<OptionalDouble> cumulativeSnowfall10to1 = List.of();
    private List<OptionalDouble> windSpeed = List.of();
    private List<OptionalDouble> snowfall6h = List.of();

    private Builder() {}

    /**
     * Sets {@link PlumeHeadline#validTime()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder validTime(List<Instant> values) {
      this.validTime = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#temperature()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder temperature(List<OptionalDouble> values) {
      this.temperature = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#snowLevel()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevel(List<OptionalDouble> values) {
      this.snowLevel = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#snowLevelWetBulb()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevelWetBulb(List<OptionalDouble> values) {
      this.snowLevelWetBulb = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#cumulativePrecipitation()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativePrecipitation(List<OptionalDouble> values) {
      this.cumulativePrecipitation = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#cumulativeSnowfall()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall(List<OptionalDouble> values) {
      this.cumulativeSnowfall = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#cumulativeSnowfall10to1()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall10to1(List<OptionalDouble> values) {
      this.cumulativeSnowfall10to1 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#windSpeed()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder windSpeed(List<OptionalDouble> values) {
      this.windSpeed = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlumeHeadline#snowfall6h()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowfall6h(List<OptionalDouble> values) {
      this.snowfall6h = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link PlumeHeadline}.
     *
     * @return the immutable PlumeHeadline
     * @throws IllegalStateException if a required value was not set
     */
    public PlumeHeadline build() {
      return new PlumeHeadline(this);
    }
  }
}
