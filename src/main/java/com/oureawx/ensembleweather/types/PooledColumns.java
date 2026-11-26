// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * The members as ONE unlabelled pool, for a caller without the plume's paid detail (Free and
 * anonymous; {@code members_view: pool}). Present only in that view. Every column is {@code
 * [member][step]}: row {@code i} is one member, the same member in every column and on every
 * elevation of the response; there is no member id and no model. The rows are in an order fixed for
 * the run and the location that does not follow the models (a new run reshuffles; the same request
 * draws the same lines). RRFS's display-only members are not in the pool. {@code valid_time} is the
 * pool's one grid: the served hours that carry a member distribution ({@code bands.valid_time}'s
 * 6-hourly synoptic hours), up to the last hour the running-total bands are served (where a global
 * ensemble's run ends a step before the served window does, the pool ends with it, so the global
 * ensembles' members all run to the same last step); a member with no value at a step is {@code
 * null} there (a short-range member is {@code null} from its last step on). Values are the members'
 * own served values, in the columns' units and precision ({@code MemberPlume}), except an hourly
 * member's {@code precipitation} and {@code snowfall}, restated over the grid step as the rise of
 * its own running total since the previous step. What is not hidden: {@code model_run.models[]}
 * states each model's member count; a member that ends early is a shorter-range model's; a row
 * missing from a {@code *_band_members} list ended before that band did.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PooledColumns}.
 */
public final class PooledColumns extends Model {
  private final List<Instant> validTime;
  private final List<List<OptionalDouble>> temperature;
  private final List<List<OptionalDouble>> precipitation;
  private final List<List<OptionalInt>> precipitationHours;
  private final List<List<OptionalDouble>> cumulativePrecipitation;
  private final List<List<OptionalDouble>> snowLevel;
  private final List<List<OptionalDouble>> snowLevelWetBulb;
  private final List<List<OptionalDouble>> snowfall;
  private final List<List<OptionalDouble>> cumulativeSnowfall;
  private final List<List<OptionalDouble>> slr;
  private final List<Integer> cumulativePrecipitationBandMembers;
  private final List<Integer> cumulativeSnowfallBandMembers;
  private final List<Integer> cumulativeSnowfall10to1BandMembers;

  private PooledColumns(Builder b) {
    this.validTime = List.copyOf(b.validTime);
    this.temperature = List.copyOf(b.temperature);
    this.precipitation = List.copyOf(b.precipitation);
    this.precipitationHours = List.copyOf(b.precipitationHours);
    this.cumulativePrecipitation = List.copyOf(b.cumulativePrecipitation);
    this.snowLevel = List.copyOf(b.snowLevel);
    this.snowLevelWetBulb = List.copyOf(b.snowLevelWetBulb);
    this.snowfall = List.copyOf(b.snowfall);
    this.cumulativeSnowfall = List.copyOf(b.cumulativeSnowfall);
    this.slr = List.copyOf(b.slr);
    this.cumulativePrecipitationBandMembers = List.copyOf(b.cumulativePrecipitationBandMembers);
    this.cumulativeSnowfallBandMembers = List.copyOf(b.cumulativeSnowfallBandMembers);
    this.cumulativeSnowfall10to1BandMembers = List.copyOf(b.cumulativeSnowfall10to1BandMembers);
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
    b.precipitation = this.precipitation;
    b.precipitationHours = this.precipitationHours;
    b.cumulativePrecipitation = this.cumulativePrecipitation;
    b.snowLevel = this.snowLevel;
    b.snowLevelWetBulb = this.snowLevelWetBulb;
    b.snowfall = this.snowfall;
    b.cumulativeSnowfall = this.cumulativeSnowfall;
    b.slr = this.slr;
    b.cumulativePrecipitationBandMembers = this.cumulativePrecipitationBandMembers;
    b.cumulativeSnowfallBandMembers = this.cumulativeSnowfallBandMembers;
    b.cumulativeSnowfall10to1BandMembers = this.cumulativeSnowfall10to1BandMembers;
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
   * {@code [member][step]}: the hours each {@code precipitation} and {@code snowfall} value covers:
   * the grid step (6), less where a member's first value covers less; {@code null} where the value
   * is absent.
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
   * The {@code snow_level_wet_bulb} value.
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
   * The {@code slr} value.
   *
   * @return the {@code slr} values; empty when the response has none
   */
  public List<List<OptionalDouble>> slr() {
    return slr;
  }

  /**
   * The rows (indices into every column) in {@code bands.cumulative_precipitation}'s fixed
   * population, ascending: that band's {@code mean} is the mean of these rows' running totals.
   *
   * @return the {@code cumulative_precipitation_band_members} values; empty when the response has
   *     none
   */
  public List<Integer> cumulativePrecipitationBandMembers() {
    return cumulativePrecipitationBandMembers;
  }

  /**
   * Likewise for {@code bands.cumulative_snowfall}.
   *
   * @return the {@code cumulative_snowfall_band_members} values; empty when the response has none
   */
  public List<Integer> cumulativeSnowfallBandMembers() {
    return cumulativeSnowfallBandMembers;
  }

  /**
   * Likewise for {@code bands.cumulative_snowfall_10to1}.
   *
   * @return the {@code cumulative_snowfall_10to1_band_members} values; empty when the response has
   *     none
   */
  public List<Integer> cumulativeSnowfall10to1BandMembers() {
    return cumulativeSnowfall10to1BandMembers;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      validTime,
      temperature,
      precipitation,
      precipitationHours,
      cumulativePrecipitation,
      snowLevel,
      snowLevelWetBulb,
      snowfall,
      cumulativeSnowfall,
      slr,
      cumulativePrecipitationBandMembers,
      cumulativeSnowfallBandMembers,
      cumulativeSnowfall10to1BandMembers
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "validTime",
    "temperature",
    "precipitation",
    "precipitationHours",
    "cumulativePrecipitation",
    "snowLevel",
    "snowLevelWetBulb",
    "snowfall",
    "cumulativeSnowfall",
    "slr",
    "cumulativePrecipitationBandMembers",
    "cumulativeSnowfallBandMembers",
    "cumulativeSnowfall10to1BandMembers"
  };

  /** Builds a {@link PooledColumns}. */
  public static final class Builder {
    private List<Instant> validTime = List.of();
    private List<List<OptionalDouble>> temperature = List.of();
    private List<List<OptionalDouble>> precipitation = List.of();
    private List<List<OptionalInt>> precipitationHours = List.of();
    private List<List<OptionalDouble>> cumulativePrecipitation = List.of();
    private List<List<OptionalDouble>> snowLevel = List.of();
    private List<List<OptionalDouble>> snowLevelWetBulb = List.of();
    private List<List<OptionalDouble>> snowfall = List.of();
    private List<List<OptionalDouble>> cumulativeSnowfall = List.of();
    private List<List<OptionalDouble>> slr = List.of();
    private List<Integer> cumulativePrecipitationBandMembers = List.of();
    private List<Integer> cumulativeSnowfallBandMembers = List.of();
    private List<Integer> cumulativeSnowfall10to1BandMembers = List.of();

    private Builder() {}

    /**
     * Sets {@link PooledColumns#validTime()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder validTime(List<Instant> values) {
      this.validTime = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#temperature()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder temperature(List<List<OptionalDouble>> values) {
      this.temperature = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#precipitation()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder precipitation(List<List<OptionalDouble>> values) {
      this.precipitation = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#precipitationHours()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder precipitationHours(List<List<OptionalInt>> values) {
      this.precipitationHours = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#cumulativePrecipitation()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativePrecipitation(List<List<OptionalDouble>> values) {
      this.cumulativePrecipitation = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#snowLevel()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevel(List<List<OptionalDouble>> values) {
      this.snowLevel = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#snowLevelWetBulb()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowLevelWetBulb(List<List<OptionalDouble>> values) {
      this.snowLevelWetBulb = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#snowfall()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder snowfall(List<List<OptionalDouble>> values) {
      this.snowfall = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#cumulativeSnowfall()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall(List<List<OptionalDouble>> values) {
      this.cumulativeSnowfall = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#slr()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder slr(List<List<OptionalDouble>> values) {
      this.slr = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#cumulativePrecipitationBandMembers()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativePrecipitationBandMembers(List<Integer> values) {
      this.cumulativePrecipitationBandMembers = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#cumulativeSnowfallBandMembers()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfallBandMembers(List<Integer> values) {
      this.cumulativeSnowfallBandMembers = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PooledColumns#cumulativeSnowfall10to1BandMembers()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder cumulativeSnowfall10to1BandMembers(List<Integer> values) {
      this.cumulativeSnowfall10to1BandMembers = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link PooledColumns}.
     *
     * @return the immutable PooledColumns
     * @throws IllegalStateException if a required value was not set
     */
    public PooledColumns build() {
      return new PooledColumns(this);
    }
  }
}
