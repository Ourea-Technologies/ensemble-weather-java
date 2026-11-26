// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * One model's members over its NATIVE steps inside the served window ({@code valid_time}: the hours
 * at which a member of the model carries a value -- every hour for RRFS, every sixth for the global
 * ensembles; never padded to the hourly series). Each variable is one array per member: {@code
 * temperature[i][j]} is member {@code member_ids[i]} at {@code valid_time[j]}. The values, units
 * and meanings are the row form's {@code MemberForecast} fields: {@code precipitation} and {@code
 * snowfall} are totals over the {@code precipitation_hours} ending at the step, and the {@code
 * cumulative_*} running totals start where {@code snow_outlook} does.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ModelColumns}.
 */
public final class ModelColumns extends Model {
  private final String id;
  private final List<Instant> validTime;
  private final List<Integer> memberIds;
  private final List<List<OptionalDouble>> temperature;
  private final List<List<OptionalDouble>> precipitation;
  private final List<List<OptionalInt>> precipitationHours;
  private final List<List<OptionalDouble>> cumulativePrecipitation;
  private final List<List<OptionalDouble>> snowLevel;
  private final List<List<OptionalDouble>> snowLevelWetBulb;
  private final List<List<OptionalDouble>> snowfall;
  private final List<List<OptionalDouble>> cumulativeSnowfall;
  private final List<OptionalDouble> cumulativeSnowfallP50;
  private final List<OptionalDouble> cumulativeSnowfall10to1P50;
  private final List<OptionalDouble> temperatureMean;
  private final List<OptionalDouble> precipitationMean;
  private final List<OptionalDouble> cumulativePrecipitationMean;
  private final List<OptionalDouble> snowLevelMean;
  private final List<OptionalDouble> snowLevelWetBulbMean;
  private final List<OptionalDouble> snowfallMean;
  private final List<OptionalDouble> cumulativeSnowfallMean;
  private final List<OptionalDouble> cumulativeSnowfall10to1Mean;
  private final List<OptionalDouble> windSpeedMean;
  private final List<Integer> cumulativePrecipitationBandMemberIds;
  private final List<Integer> cumulativeSnowfallBandMemberIds;
  private final List<Integer> cumulativeSnowfall10to1BandMemberIds;
  private final List<List<OptionalDouble>> windSpeed;
  private final List<List<OptionalDouble>> windDirection;
  private final List<List<OptionalDouble>> slr;

  private ModelColumns(Builder b) {
    this.id = require(b.id, "id");
    this.validTime = List.copyOf(b.validTime);
    this.memberIds = List.copyOf(b.memberIds);
    this.temperature = List.copyOf(b.temperature);
    this.precipitation = List.copyOf(b.precipitation);
    this.precipitationHours = List.copyOf(b.precipitationHours);
    this.cumulativePrecipitation = List.copyOf(b.cumulativePrecipitation);
    this.snowLevel = List.copyOf(b.snowLevel);
    this.snowLevelWetBulb = List.copyOf(b.snowLevelWetBulb);
    this.snowfall = List.copyOf(b.snowfall);
    this.cumulativeSnowfall = List.copyOf(b.cumulativeSnowfall);
    this.cumulativeSnowfallP50 = List.copyOf(b.cumulativeSnowfallP50);
    this.cumulativeSnowfall10to1P50 = List.copyOf(b.cumulativeSnowfall10to1P50);
    this.temperatureMean = List.copyOf(b.temperatureMean);
    this.precipitationMean = List.copyOf(b.precipitationMean);
    this.cumulativePrecipitationMean = List.copyOf(b.cumulativePrecipitationMean);
    this.snowLevelMean = List.copyOf(b.snowLevelMean);
    this.snowLevelWetBulbMean = List.copyOf(b.snowLevelWetBulbMean);
    this.snowfallMean = List.copyOf(b.snowfallMean);
    this.cumulativeSnowfallMean = List.copyOf(b.cumulativeSnowfallMean);
    this.cumulativeSnowfall10to1Mean = List.copyOf(b.cumulativeSnowfall10to1Mean);
    this.windSpeedMean = List.copyOf(b.windSpeedMean);
    this.cumulativePrecipitationBandMemberIds = List.copyOf(b.cumulativePrecipitationBandMemberIds);
    this.cumulativeSnowfallBandMemberIds = List.copyOf(b.cumulativeSnowfallBandMemberIds);
    this.cumulativeSnowfall10to1BandMemberIds = List.copyOf(b.cumulativeSnowfall10to1BandMemberIds);
    this.windSpeed = List.copyOf(b.windSpeed);
    this.windDirection = List.copyOf(b.windDirection);
    this.slr = List.copyOf(b.slr);
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
    b.validTime = this.validTime;
    b.memberIds = this.memberIds;
    b.temperature = this.temperature;
    b.precipitation = this.precipitation;
    b.precipitationHours = this.precipitationHours;
    b.cumulativePrecipitation = this.cumulativePrecipitation;
    b.snowLevel = this.snowLevel;
    b.snowLevelWetBulb = this.snowLevelWetBulb;
    b.snowfall = this.snowfall;
    b.cumulativeSnowfall = this.cumulativeSnowfall;
    b.cumulativeSnowfallP50 = this.cumulativeSnowfallP50;
    b.cumulativeSnowfall10to1P50 = this.cumulativeSnowfall10to1P50;
    b.temperatureMean = this.temperatureMean;
    b.precipitationMean = this.precipitationMean;
    b.cumulativePrecipitationMean = this.cumulativePrecipitationMean;
    b.snowLevelMean = this.snowLevelMean;
    b.snowLevelWetBulbMean = this.snowLevelWetBulbMean;
    b.snowfallMean = this.snowfallMean;
    b.cumulativeSnowfallMean = this.cumulativeSnowfallMean;
    b.cumulativeSnowfall10to1Mean = this.cumulativeSnowfall10to1Mean;
    b.windSpeedMean = this.windSpeedMean;
    b.cumulativePrecipitationBandMemberIds = this.cumulativePrecipitationBandMemberIds;
    b.cumulativeSnowfallBandMemberIds = this.cumulativeSnowfallBandMemberIds;
    b.cumulativeSnowfall10to1BandMemberIds = this.cumulativeSnowfall10to1BandMemberIds;
    b.windSpeed = this.windSpeed;
    b.windDirection = this.windDirection;
    b.slr = this.slr;
    return b;
  }

  /**
   * The {@code id} value.
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
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
   * The members, ascending ({@code MemberForecast.member_id}). Rrfs includes its display-only
   * members ({@code model_run.models[].display_members}), whose rows are {@code null} past lead 60
   * and in the columns they do not carry ({@code snow_level}, {@code snowfall}, {@code
   * cumulative_snowfall}, {@code slr}). {@code bands} and {@code headline} do not include them.
   *
   * @return the {@code member_ids} values; empty when the response has none
   */
  public List<Integer> memberIds() {
    return memberIds;
  }

  /**
   * The {@code temperature} value.
   *
   * @return the {@code temperature} values; empty when the response has none
   */
  public List<List<OptionalDouble>> temperature() {
    return temperature;
  }

  /**
   * The {@code precipitation} value.
   *
   * @return the {@code precipitation} values; empty when the response has none
   */
  public List<List<OptionalDouble>> precipitation() {
    return precipitation;
  }

  /**
   * {@code [member][step]}: the hours each {@code precipitation} value covers; {@code null} where
   * it is absent or its span unknown.
   *
   * @return the {@code precipitation_hours} values; empty when the response has none
   */
  public List<List<OptionalInt>> precipitationHours() {
    return precipitationHours;
  }

  /**
   * The {@code cumulative_precipitation} value.
   *
   * @return the {@code cumulative_precipitation} values; empty when the response has none
   */
  public List<List<OptionalDouble>> cumulativePrecipitation() {
    return cumulativePrecipitation;
  }

  /**
   * The {@code snow_level} value.
   *
   * @return the {@code snow_level} values; empty when the response has none
   */
  public List<List<OptionalDouble>> snowLevel() {
    return snowLevel;
  }

  /**
   * {@code [Member][step]}, each member's {@code MemberForecast.snow_level_wet_bulb} (the snow
   * level on the NWS scale); {@code null} exactly where its {@code snow_level} is.
   *
   * @return the {@code snow_level_wet_bulb} values; empty when the response has none
   */
  public List<List<OptionalDouble>> snowLevelWetBulb() {
    return snowLevelWetBulb;
  }

  /**
   * The {@code snowfall} value.
   *
   * @return the {@code snowfall} values; empty when the response has none
   */
  public List<List<OptionalDouble>> snowfall() {
    return snowfall;
  }

  /**
   * The {@code cumulative_snowfall} value.
   *
   * @return the {@code cumulative_snowfall} values; empty when the response has none
   */
  public List<List<OptionalDouble>> cumulativeSnowfall() {
    return cumulativeSnowfall;
  }

  /**
   * One value per {@code valid_time} step (not per member): the median of the model's members'
   * {@code cumulative_snowfall} there (Hyndman-Fan type 7, as {@code bands}: the middle value, or
   * the midpoint of the two middle ones; a single-member model's own total), at the amount
   * precision. The model's line on a snow meteogram. RRFS display members are not in it, so RRFS's
   * is its deterministic run's. {@code null} where no member carries one.
   *
   * @return the {@code cumulative_snowfall_p50} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativeSnowfallP50() {
    return cumulativeSnowfallP50;
  }

  /**
   * As {@code cumulative_snowfall_p50}, over the members' running snowfall at 10:1 (see {@code
   * PlumeBands.cumulative_snowfall_10to1}).
   *
   * @return the {@code cumulative_snowfall_10to1_p50} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativeSnowfall10to1P50() {
    return cumulativeSnowfall10to1P50;
  }

  /**
   * The model's mean lines. Each {@code <column>_mean} is one value per {@code valid_time} step
   * (not per member): the equal-weight arithmetic mean of the model's members' {@code <column>}
   * values at that step, over the members carrying one (a single-member model's own value), in the
   * column's units at the column's precision; {@code null} where no member carries one. Beside
   * {@code cumulative_snowfall_p50}, the model's line on a chart. RRFS display members are in none,
   * so RRFS's are its deterministic run's values. Here: of {@code temperature}.
   *
   * @return the {@code temperature_mean} values; empty when the response has none
   */
  public List<OptionalDouble> temperatureMean() {
    return temperatureMean;
  }

  /**
   * The mean of {@code precipitation} (see {@code temperature_mean}). Like the column, a total over
   * the {@code precipitation_hours} ending at the step: six hours for the global ensembles, one for
   * RRFS.
   *
   * @return the {@code precipitation_mean} values; empty when the response has none
   */
  public List<OptionalDouble> precipitationMean() {
    return precipitationMean;
  }

  /**
   * The mean of {@code cumulative_precipitation} (see {@code temperature_mean}).
   *
   * @return the {@code cumulative_precipitation_mean} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativePrecipitationMean() {
    return cumulativePrecipitationMean;
  }

  /**
   * The mean of {@code snow_level} (see {@code temperature_mean}).
   *
   * @return the {@code snow_level_mean} values; empty when the response has none
   */
  public List<OptionalDouble> snowLevelMean() {
    return snowLevelMean;
  }

  /**
   * The mean of {@code snow_level_wet_bulb} (see {@code temperature_mean}).
   *
   * @return the {@code snow_level_wet_bulb_mean} values; empty when the response has none
   */
  public List<OptionalDouble> snowLevelWetBulbMean() {
    return snowLevelWetBulbMean;
  }

  /**
   * The mean of {@code snowfall} (see {@code temperature_mean}). Like the column, a total over the
   * {@code precipitation_hours} ending at the step: the 6-hour snowfall for the global ensembles,
   * the hourly one for RRFS.
   *
   * @return the {@code snowfall_mean} values; empty when the response has none
   */
  public List<OptionalDouble> snowfallMean() {
    return snowfallMean;
  }

  /**
   * The mean of {@code cumulative_snowfall} (see {@code temperature_mean}), beside its median
   * {@code cumulative_snowfall_p50}.
   *
   * @return the {@code cumulative_snowfall_mean} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativeSnowfallMean() {
    return cumulativeSnowfallMean;
  }

  /**
   * The mean of the members' running snowfall at 10:1, beside {@code cumulative_snowfall_10to1_p50}
   * (the members' 10:1 column itself is not served).
   *
   * @return the {@code cumulative_snowfall_10to1_mean} values; empty when the response has none
   */
  public List<OptionalDouble> cumulativeSnowfall10to1Mean() {
    return cumulativeSnowfall10to1Mean;
  }

  /**
   * The mean of {@code wind_speed} (see {@code temperature_mean}). Pro ({@code members_view: full})
   * only, as {@code wind_speed} is; absent in the plume view. There is no mean of {@code
   * wind_direction} (a direction has no arithmetic mean) or of {@code slr} (a member's ratio means
   * something only where it snows; see {@code bands.slr}).
   *
   * @return the {@code wind_speed_mean} values; empty when the response has none
   */
  public List<OptionalDouble> windSpeedMean() {
    return windSpeedMean;
  }

  /**
   * Which of this model's {@code member_ids} are in {@code bands.cumulative_precipitation},
   * ascending. The running-total bands are over ONE fixed set of members at every hour they are
   * served (so no rung ever falls when a model's horizon ends): the members still carrying a
   * running total at the band's last hour. A model that ends before the band does (RRFS at ~84 h;
   * GEFS at 240 h in a 15-day plume) is in the band at NO hour, though its members' own running
   * totals are still served and drawn, and its list here is empty. At every hour the band is served
   * its {@code mean} is the equal-weight mean of the listed members' running totals there, over all
   * models; it is not the mean of every member drawn. RRFS display members are never listed. Every
   * other band ({@code temperature}, {@code snow_level}, {@code snow_level_wet_bulb}, {@code
   * wind_speed}, {@code precipitation_6h}, {@code snowfall_6h}, {@code slr}) is over the members
   * carrying a value at that hour, whatever their model.
   *
   * @return the {@code cumulative_precipitation_band_member_ids} values; empty when the response
   *     has none
   */
  public List<Integer> cumulativePrecipitationBandMemberIds() {
    return cumulativePrecipitationBandMemberIds;
  }

  /**
   * As {@code cumulative_precipitation_band_member_ids}, for {@code bands.cumulative_snowfall}.
   *
   * @return the {@code cumulative_snowfall_band_member_ids} values; empty when the response has
   *     none
   */
  public List<Integer> cumulativeSnowfallBandMemberIds() {
    return cumulativeSnowfallBandMemberIds;
  }

  /**
   * As {@code cumulative_precipitation_band_member_ids}, for {@code
   * bands.cumulative_snowfall_10to1}.
   *
   * @return the {@code cumulative_snowfall_10to1_band_member_ids} values; empty when the response
   *     has none
   */
  public List<Integer> cumulativeSnowfall10to1BandMemberIds() {
    return cumulativeSnowfall10to1BandMemberIds;
  }

  /**
   * Pro ({@code members_view: full}) only; absent in the plume view.
   *
   * @return the {@code wind_speed} values; empty when the response has none
   */
  public List<List<OptionalDouble>> windSpeed() {
    return windSpeed;
  }

  /**
   * Pro only; absent in the plume view.
   *
   * @return the {@code wind_direction} values; empty when the response has none
   */
  public List<List<OptionalDouble>> windDirection() {
    return windDirection;
  }

  /**
   * In both views. As {@code MemberForecast.slr}: the ratio from the member's column at every step,
   * whether or not it snows there.
   *
   * @return the {@code slr} values; empty when the response has none
   */
  public List<List<OptionalDouble>> slr() {
    return slr;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      id,
      validTime,
      memberIds,
      temperature,
      precipitation,
      precipitationHours,
      cumulativePrecipitation,
      snowLevel,
      snowLevelWetBulb,
      snowfall,
      cumulativeSnowfall,
      cumulativeSnowfallP50,
      cumulativeSnowfall10to1P50,
      temperatureMean,
      precipitationMean,
      cumulativePrecipitationMean,
      snowLevelMean,
      snowLevelWetBulbMean,
      snowfallMean,
      cumulativeSnowfallMean,
      cumulativeSnowfall10to1Mean,
      windSpeedMean,
      cumulativePrecipitationBandMemberIds,
      cumulativeSnowfallBandMemberIds,
      cumulativeSnowfall10to1BandMemberIds,
      windSpeed,
      windDirection,
      slr
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id",
    "validTime",
    "memberIds",
    "temperature",
    "precipitation",
    "precipitationHours",
    "cumulativePrecipitation",
    "snowLevel",
    "snowLevelWetBulb",
    "snowfall",
    "cumulativeSnowfall",
    "cumulativeSnowfallP50",
    "cumulativeSnowfall10to1P50",
    "temperatureMean",
    "precipitationMean",
    "cumulativePrecipitationMean",
    "snowLevelMean",
    "snowLevelWetBulbMean",
    "snowfallMean",
    "cumulativeSnowfallMean",
    "cumulativeSnowfall10to1Mean",
    "windSpeedMean",
    "cumulativePrecipitationBandMemberIds",
    "cumulativeSnowfallBandMemberIds",
    "cumulativeSnowfall10to1BandMemberIds",
    "windSpeed",
    "windDirection",
    "slr"
  };

  /** Builds a {@link ModelColumns}. */
  public static final class Builder {
    private @Nullable String id;
    private List<Instant> validTime = List.of();
    private List<Integer> memberIds = List.of();
    private List<List<OptionalDouble>> temperature = List.of();
    private List<List<OptionalDouble>> precipitation = List.of();
    private List<List<OptionalInt>> precipitationHours = List.of();
    private List<List<OptionalDouble>> cumulativePrecipitation = List.of();
    private List<List<OptionalDouble>> snowLevel = List.of();
    private List<List<OptionalDouble>> snowLevelWetBulb = List.of();
    private List<List<OptionalDouble>> snowfall = List.of();
    private List<List<OptionalDouble>> cumulativeSnowfall = List.of();
    private List<OptionalDouble> cumulativeSnowfallP50 = List.of();
    private List<OptionalDouble> cumulativeSnowfall10to1P50 = List.of();
    private List<OptionalDouble> temperatureMean = List.of();
    private List<OptionalDouble> precipitationMean = List.of();
    private List<OptionalDouble> cumulativePrecipitationMean = List.of();
    private List<OptionalDouble> snowLevelMean = List.of();
    private List<OptionalDouble> snowLevelWetBulbMean = List.of();
    private List<OptionalDouble> snowfallMean = List.of();
    private List<OptionalDouble> cumulativeSnowfallMean = List.of();
    private List<OptionalDouble> cumulativeSnowfall10to1Mean = List.of();
    private List<OptionalDouble> windSpeedMean = List.of();
    private List<Integer> cumulativePrecipitationBandMemberIds = List.of();
    private List<Integer> cumulativeSnowfallBandMemberIds = List.of();
    private List<Integer> cumulativeSnowfall10to1BandMemberIds = List.of();
    private List<List<OptionalDouble>> windSpeed = List.of();
    private List<List<OptionalDouble>> windDirection = List.of();
    private List<List<OptionalDouble>> slr = List.of();

    private Builder() {}

    /**
     * Sets {@link ModelColumns#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link ModelColumns#validTime()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder validTime(List<Instant> values) {
      this.validTime = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#memberIds()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder memberIds(List<Integer> values) {
      this.memberIds = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#temperature()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder temperature(List<List<OptionalDouble>> values) {
      this.temperature = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#precipitation()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder precipitation(List<List<OptionalDouble>> values) {
      this.precipitation = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#precipitationHours()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder precipitationHours(List<List<OptionalInt>> values) {
      this.precipitationHours = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativePrecipitation()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativePrecipitation(List<List<OptionalDouble>> values) {
      this.cumulativePrecipitation = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#snowLevel()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevel(List<List<OptionalDouble>> values) {
      this.snowLevel = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#snowLevelWetBulb()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevelWetBulb(List<List<OptionalDouble>> values) {
      this.snowLevelWetBulb = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#snowfall()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowfall(List<List<OptionalDouble>> values) {
      this.snowfall = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativeSnowfall()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall(List<List<OptionalDouble>> values) {
      this.cumulativeSnowfall = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativeSnowfallP50()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfallP50(List<OptionalDouble> values) {
      this.cumulativeSnowfallP50 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativeSnowfall10to1P50()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall10to1P50(List<OptionalDouble> values) {
      this.cumulativeSnowfall10to1P50 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#temperatureMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder temperatureMean(List<OptionalDouble> values) {
      this.temperatureMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#precipitationMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder precipitationMean(List<OptionalDouble> values) {
      this.precipitationMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativePrecipitationMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativePrecipitationMean(List<OptionalDouble> values) {
      this.cumulativePrecipitationMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#snowLevelMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevelMean(List<OptionalDouble> values) {
      this.snowLevelMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#snowLevelWetBulbMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevelWetBulbMean(List<OptionalDouble> values) {
      this.snowLevelWetBulbMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#snowfallMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowfallMean(List<OptionalDouble> values) {
      this.snowfallMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativeSnowfallMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfallMean(List<OptionalDouble> values) {
      this.cumulativeSnowfallMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativeSnowfall10to1Mean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall10to1Mean(List<OptionalDouble> values) {
      this.cumulativeSnowfall10to1Mean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#windSpeedMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder windSpeedMean(List<OptionalDouble> values) {
      this.windSpeedMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativePrecipitationBandMemberIds()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativePrecipitationBandMemberIds(List<Integer> values) {
      this.cumulativePrecipitationBandMemberIds = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativeSnowfallBandMemberIds()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfallBandMemberIds(List<Integer> values) {
      this.cumulativeSnowfallBandMemberIds = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#cumulativeSnowfall10to1BandMemberIds()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall10to1BandMemberIds(List<Integer> values) {
      this.cumulativeSnowfall10to1BandMemberIds = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#windSpeed()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder windSpeed(List<List<OptionalDouble>> values) {
      this.windSpeed = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#windDirection()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder windDirection(List<List<OptionalDouble>> values) {
      this.windDirection = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelColumns#slr()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder slr(List<List<OptionalDouble>> values) {
      this.slr = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link ModelColumns}.
     *
     * @return the immutable ModelColumns
     * @throws IllegalStateException if a required value was not set
     */
    public ModelColumns build() {
      return new ModelColumns(this);
    }
  }
}
