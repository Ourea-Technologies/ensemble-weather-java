// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.OptionalDouble;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * One ensemble member's values at a single forecast hour. All physical fields are in the caller's
 * unit system (metric or imperial) — temperature in °C/°F, precipitation &amp; snowfall in mm/in,
 * wind speed in m/s or mph, snow level in m/ft. {@code slr} is unitless.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code MemberForecast}.
 */
public final class MemberForecast extends Model {
  private final int memberId;
  private final @Nullable Double temperature;
  private final @Nullable Double precipitation;
  private final @Nullable Integer precipitationHours;
  private final @Nullable Double windSpeed;
  private final @Nullable Double windDirection;
  private final @Nullable Double snowLevel;
  private final @Nullable Double slr;
  private final @Nullable Double snowfall;
  private final @Nullable Double cumulativePrecipitation;
  private final @Nullable Double cumulativeSnowfall;

  private MemberForecast(Builder b) {
    this.memberId = require(b.memberId, "member_id");
    this.temperature = b.temperature;
    this.precipitation = b.precipitation;
    this.precipitationHours = b.precipitationHours;
    this.windSpeed = b.windSpeed;
    this.windDirection = b.windDirection;
    this.snowLevel = b.snowLevel;
    this.slr = b.slr;
    this.snowfall = b.snowfall;
    this.cumulativePrecipitation = b.cumulativePrecipitation;
    this.cumulativeSnowfall = b.cumulativeSnowfall;
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
    b.memberId = this.memberId;
    b.temperature = this.temperature;
    b.precipitation = this.precipitation;
    b.precipitationHours = this.precipitationHours;
    b.windSpeed = this.windSpeed;
    b.windDirection = this.windDirection;
    b.snowLevel = this.snowLevel;
    b.slr = this.slr;
    b.snowfall = this.snowfall;
    b.cumulativePrecipitation = this.cumulativePrecipitation;
    b.cumulativeSnowfall = this.cumulativeSnowfall;
    return b;
  }

  /**
   * The member's position on its model's ensemble axis, from 0: stable across the hours of a
   * response and across cycles, and numbered per model (a member is {@code (model, member_id)}). 0
   * is the control for {@code aifs} (cf), {@code aigefs} (mem000) and {@code gefs} (gec00); {@code
   * ifs_ens} has no control in ECMWF open data, so its 0..49 are perturbed members 1..50; {@code
   * rrfs} is the one deterministic run, 0.
   *
   * @return the {@code member_id} value
   */
  public int memberId() {
    return memberId;
  }

  /**
   * {@code null} when this member published nothing at this hour.
   *
   * @return the {@code temperature} value, or empty when the response omits it
   */
  public OptionalDouble temperature() {
    return temperature == null ? OptionalDouble.empty() : OptionalDouble.of(temperature);
  }

  /**
   * {@code null} when this member published nothing at this hour. A measured {@code 0.0} (a
   * genuinely dry member) is still served as {@code 0.0} — the two are different claims. The value
   * is the TOTAL over the {@code precipitation_hours} hours ending at this hour — a 6-hourly
   * model's whole 6-hour amount at its native step, and {@code null} between — so summing a
   * member's {@code precipitation} over time gives its accumulated total.
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public OptionalDouble precipitation() {
    return precipitation == null ? OptionalDouble.empty() : OptionalDouble.of(precipitation);
  }

  /**
   * How many hours {@code precipitation} (and {@code snowfall}) covers, ending at this hour: back
   * to this model's previous precipitation step (1 for an hourly model, 6 for a 6-hourly one). A
   * model's FIRST served value states its span too -- a run that stores no lead 0 (aigefs) carries
   * the total since its own init (e.g. 6), and a model whose cycle is older than {@code
   * model_run.initialized_at} has its first bucket cut to its one-hour share at the read, so it
   * says 1. Omitted when {@code precipitation} is absent, and for a run's analysis value at its own
   * init (lead 0), which covers no forecast hour.
   *
   * @return the {@code precipitation_hours} value, or empty when the response omits it
   */
  public OptionalInt precipitationHours() {
    return precipitationHours == null ? OptionalInt.empty() : OptionalInt.of(precipitationHours);
  }

  /**
   * {@code null} when this member published nothing at this hour.
   *
   * @return the {@code wind_speed} value, or empty when the response omits it
   */
  public OptionalDouble windSpeed() {
    return windSpeed == null ? OptionalDouble.empty() : OptionalDouble.of(windSpeed);
  }

  /**
   * The {@code wind_direction} value.
   *
   * @return the {@code wind_direction} value, or empty when the response omits it
   */
  public OptionalDouble windDirection() {
    return windDirection == null ? OptionalDouble.empty() : OptionalDouble.of(windDirection);
  }

  /**
   * The {@code snow_level} value.
   *
   * @return the {@code snow_level} value, or empty when the response omits it
   */
  public OptionalDouble snowLevel() {
    return snowLevel == null ? OptionalDouble.empty() : OptionalDouble.of(snowLevel);
  }

  /**
   * Snow-to-liquid ratio for this member (unitless). Higher = fluffier snow. Omitted for models
   * that don't diagnose SLR (RRFS may not always provide it).
   *
   * @return the {@code slr} value, or empty when the response omits it
   */
  public OptionalDouble slr() {
    return slr == null ? OptionalDouble.empty() : OptionalDouble.of(slr);
  }

  /**
   * Per-member snowfall depth at the location's elevation, computed as {@code precipitation ×
   * effective_slr(slr, elevation, snow_level)}. Omitted when the derivation is impossible: no SLR,
   * no snow level, or no precipitation MEASUREMENT for this member. A member whose precipitation
   * was measured at {@code 0.0} serves a present {@code 0.0} instead, so "this member gets no snow"
   * stays distinct from "we cannot say". Its surface wet-bulb gate reads the member's own
   * temperature and dewpoint; a member without a 2 m dewpoint ({@code aigefs}) is gated on its own
   * temperature with the served dewpoint. It also takes the headline's drizzle gate over the hours
   * its {@code precipitation_hours} span -- a light hour (median precipitation under 0.5 mm) at or
   * above 0 °C states 0 mm of snow in the headline, and the member's snowfall is scaled by the
   * share of its hours the gate admits -- so a plume cannot snow where the hourly headline states
   * none for that reason.
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public OptionalDouble snowfall() {
    return snowfall == null ? OptionalDouble.empty() : OptionalDouble.of(snowfall);
  }

  /**
   * This member's running precipitation total (mm / in) from the start of the served window through
   * this hour -- the sum of its {@code precipitation} over every served hour up to here. The window
   * opens ONE HOUR BEFORE the first served {@code valid_time} (the span of the first hour's own
   * value): the current hour by default, the run's init with {@code include_past=true} or a pinned
   * {@code run} -- the window the served {@code snow_outlook} totals over too. A value whose span
   * ({@code precipitation_hours}) began before the window counts only its share inside it, {@code
   * precipitation × (hours of the span inside the window) / precipitation_hours} (the headline
   * spreads a bucket evenly over its hours the same way); a value with no span (a run's analysis at
   * its own init) counts in full. Present exactly when {@code precipitation} is. Clients should
   * draw cumulative QPF from this instead of summing.
   *
   * @return the {@code cumulative_precipitation} value, or empty when the response omits it
   */
  public OptionalDouble cumulativePrecipitation() {
    return cumulativePrecipitation == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(cumulativePrecipitation);
  }

  /**
   * This member's running snowfall total (mm / in), by the same window and share rule as {@code
   * cumulative_precipitation} over its {@code snowfall}. Present exactly when {@code snowfall} is.
   *
   * @return the {@code cumulative_snowfall} value, or empty when the response omits it
   */
  public OptionalDouble cumulativeSnowfall() {
    return cumulativeSnowfall == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(cumulativeSnowfall);
  }

  @Override
  Object[] values() {
    return new Object[] {
      memberId,
      temperature,
      precipitation,
      precipitationHours,
      windSpeed,
      windDirection,
      snowLevel,
      slr,
      snowfall,
      cumulativePrecipitation,
      cumulativeSnowfall
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "memberId",
    "temperature",
    "precipitation",
    "precipitationHours",
    "windSpeed",
    "windDirection",
    "snowLevel",
    "slr",
    "snowfall",
    "cumulativePrecipitation",
    "cumulativeSnowfall"
  };

  /** Builds a {@link MemberForecast}. */
  public static final class Builder {
    private @Nullable Integer memberId;
    private @Nullable Double temperature;
    private @Nullable Double precipitation;
    private @Nullable Integer precipitationHours;
    private @Nullable Double windSpeed;
    private @Nullable Double windDirection;
    private @Nullable Double snowLevel;
    private @Nullable Double slr;
    private @Nullable Double snowfall;
    private @Nullable Double cumulativePrecipitation;
    private @Nullable Double cumulativeSnowfall;

    private Builder() {}

    /**
     * Sets {@link MemberForecast#memberId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder memberId(int value) {
      this.memberId = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#temperature()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder temperature(@Nullable Double value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable Double value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#precipitationHours()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationHours(@Nullable Integer value) {
      this.precipitationHours = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#windSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windSpeed(@Nullable Double value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#windDirection()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windDirection(@Nullable Double value) {
      this.windDirection = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#snowLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevel(@Nullable Double value) {
      this.snowLevel = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#slr()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder slr(@Nullable Double value) {
      this.slr = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable Double value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#cumulativePrecipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cumulativePrecipitation(@Nullable Double value) {
      this.cumulativePrecipitation = value;
      return this;
    }

    /**
     * Sets {@link MemberForecast#cumulativeSnowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cumulativeSnowfall(@Nullable Double value) {
      this.cumulativeSnowfall = value;
      return this;
    }

    /**
     * Builds the {@link MemberForecast}.
     *
     * @return the immutable MemberForecast
     * @throws IllegalStateException if a required value was not set
     */
    public MemberForecast build() {
      return new MemberForecast(this);
    }
  }
}
