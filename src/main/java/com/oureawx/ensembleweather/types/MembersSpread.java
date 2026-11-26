// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The members' spread at this hour for the plume's instantaneous variables -- the Quantiles of
 * every member's own {@code temperature} (temperature unit) and {@code snow_level} (length unit)
 * here, over the members as served. Draw it as the plume's band on the temperature and snow-level
 * panels: the hour's {@code ensemble.temperature} / {@code ensemble.snow_level} percentiles are the
 * calibrated blend's distribution, a different quantity. Present with {@code members=true} at an
 * hour where at least two members carry a value -- in practice the 6-hourly synoptic hours, as
 * {@code members_cumulative}; a quantity fewer than two members carry is omitted. Equal-weight
 * member spread.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code MembersSpread}.
 */
public final class MembersSpread extends Model {
  private final @Nullable MemberQuantiles temperature;
  private final @Nullable MemberQuantiles snowLevel;
  private final @Nullable MemberQuantiles snowLevelWetBulb;
  private final @Nullable MemberQuantiles slr;
  private final @Nullable MemberQuantiles windSpeed;

  private MembersSpread(Builder b) {
    this.temperature = b.temperature;
    this.snowLevel = b.snowLevel;
    this.snowLevelWetBulb = b.snowLevelWetBulb;
    this.slr = b.slr;
    this.windSpeed = b.windSpeed;
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
    b.snowLevel = this.snowLevel;
    b.snowLevelWetBulb = this.snowLevelWetBulb;
    b.slr = this.slr;
    b.windSpeed = this.windSpeed;
    return b;
  }

  /**
   * The {@code temperature} value.
   *
   * @return the {@code temperature} value, or empty when the response omits it
   */
  public Optional<MemberQuantiles> temperature() {
    return Optional.ofNullable(temperature);
  }

  /**
   * The {@code snow_level} value.
   *
   * @return the {@code snow_level} value, or empty when the response omits it
   */
  public Optional<MemberQuantiles> snowLevel() {
    return Optional.ofNullable(snowLevel);
  }

  /**
   * The Quantiles of the members' own {@code snow_level_wet_bulb} (the snow level on the NWS
   * scale), over the members {@code snow_level} is over. Draw it as the band of a snow-level panel
   * labelled on the NWS scale. Omitted when {@code snow_level} is.
   *
   * @return the {@code snow_level_wet_bulb} value, or empty when the response omits it
   */
  public Optional<MemberQuantiles> snowLevelWetBulb() {
    return Optional.ofNullable(snowLevelWetBulb);
  }

  /**
   * The snow-to-liquid ratio over the members SNOWING at this hour (their own {@code snowfall}
   * above 0), at 00/06/12/18Z only, in both member views. Omitted at other hours and where fewer
   * than five members snow, never a zero, so a dry step has no violin. It is the same diagnostic
   * ratio as {@code ensemble.slr} and can differ from it: {@code ensemble.slr} is the weighted
   * blend of every member's ratio, dry members included, at an hour whose served snowfall is above
   * 0. As few as five snowing members can sit behind a step, not the whole ensemble. RRFS {@code
   * member_id} 1..5 have no ratio.
   *
   * @return the {@code slr} value, or empty when the response omits it
   */
  public Optional<MemberQuantiles> slr() {
    return Optional.ofNullable(slr);
  }

  /**
   * The members' own wind speed at this hour, in the wind unit. Draw it as the band on the wind
   * panel: {@code ensemble.wind_speed} is the calibrated blend's distribution, a different
   * quantity. Omitted from the Free plume view, which withholds the members' wind.
   *
   * @return the {@code wind_speed} value, or empty when the response omits it
   */
  public Optional<MemberQuantiles> windSpeed() {
    return Optional.ofNullable(windSpeed);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {temperature, snowLevel, snowLevelWetBulb, slr, windSpeed};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "temperature", "snowLevel", "snowLevelWetBulb", "slr", "windSpeed"
  };

  /** Builds a {@link MembersSpread}. */
  public static final class Builder {
    private @Nullable MemberQuantiles temperature;
    private @Nullable MemberQuantiles snowLevel;
    private @Nullable MemberQuantiles snowLevelWetBulb;
    private @Nullable MemberQuantiles slr;
    private @Nullable MemberQuantiles windSpeed;

    private Builder() {}

    /**
     * Sets {@link MembersSpread#temperature()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder temperature(@Nullable MemberQuantiles value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link MembersSpread#snowLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevel(@Nullable MemberQuantiles value) {
      this.snowLevel = value;
      return this;
    }

    /**
     * Sets {@link MembersSpread#snowLevelWetBulb()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevelWetBulb(@Nullable MemberQuantiles value) {
      this.snowLevelWetBulb = value;
      return this;
    }

    /**
     * Sets {@link MembersSpread#slr()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder slr(@Nullable MemberQuantiles value) {
      this.slr = value;
      return this;
    }

    /**
     * Sets {@link MembersSpread#windSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windSpeed(@Nullable MemberQuantiles value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Builds the {@link MembersSpread}.
     *
     * @return the immutable MembersSpread
     * @throws IllegalStateException if a required value was not set
     */
    public MembersSpread build() {
      return new MembersSpread(this);
    }
  }
}
