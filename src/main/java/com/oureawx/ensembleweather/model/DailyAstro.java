// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The day's sun and moon, grouped.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code DailyAstro}.
 */
public final class DailyAstro extends Model {
  private final @Nullable Instant sunrise;
  private final @Nullable Instant sunset;
  private final String moonPhase;
  private final double moonIllumination;
  private final String astronomicalSeason;

  private DailyAstro(Builder b) {
    this.sunrise = b.sunrise;
    this.sunset = b.sunset;
    this.moonPhase = require(b.moonPhase, "moon_phase");
    this.moonIllumination = require(b.moonIllumination, "moon_illumination");
    this.astronomicalSeason = require(b.astronomicalSeason, "astronomical_season");
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
    b.sunrise = this.sunrise;
    b.sunset = this.sunset;
    b.moonPhase = this.moonPhase;
    b.moonIllumination = this.moonIllumination;
    b.astronomicalSeason = this.astronomicalSeason;
    return b;
  }

  /**
   * Sunrise time at the forecast location, ISO-8601 UTC. Computed with the NOAA Solar Calculator
   * algorithm including atmospheric refraction (-34′), solar semi-diameter (-16′), and
   * elevation-induced horizon dip. Accurate to within ~1 minute of USNO reference values.
   *
   * @return the {@code sunrise} value, or empty when the response omits it
   */
  public Optional<Instant> sunrise() {
    return Optional.ofNullable(sunrise);
  }

  /**
   * Sunset time at the forecast location, ISO-8601 UTC. For western- hemisphere locations in
   * late-afternoon UTC, this often falls on the UTC calendar day *after* the local forecast date.
   *
   * @return the {@code sunset} value, or empty when the response omits it
   */
  public Optional<Instant> sunset() {
    return Optional.ofNullable(sunset);
  }

  /**
   * Eight-way moon phase for the date.
   *
   * @return the {@code moon_phase} value ({@link MoonPhase#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public MoonPhase moonPhase() {
    return MoonPhase.fromWireValue(moonPhase);
  }

  /**
   * The {@code moon_phase} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code moon_phase} string
   */
  public String moonPhaseAsString() {
    return moonPhase;
  }

  /**
   * Fraction of the moon's disc that's illuminated (0.0 = new, 1.0 = full). More precise than the
   * named phase — useful if the UI wants to render a crescent icon scaled to the exact
   * illumination.
   *
   * @return the {@code moon_illumination} value
   */
  public double moonIllumination() {
    return moonIllumination;
  }

  /**
   * Astronomical season by equinox/solstice (not meteorological). Hemisphere-aware — a
   * Southern-hemisphere location returns {@code summer} in January.
   *
   * @return the {@code astronomical_season} value ({@link AstronomicalSeason#UNKNOWN} for a value
   *     this SDK version does not know)
   */
  public AstronomicalSeason astronomicalSeason() {
    return AstronomicalSeason.fromWireValue(astronomicalSeason);
  }

  /**
   * The {@code astronomical_season} value exactly as the API sent it, even when this SDK version
   * does not know it.
   *
   * @return the raw {@code astronomical_season} string
   */
  public String astronomicalSeasonAsString() {
    return astronomicalSeason;
  }

  @Override
  Object[] values() {
    return new Object[] {sunrise, sunset, moonPhase, moonIllumination, astronomicalSeason};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "sunrise", "sunset", "moonPhase", "moonIllumination", "astronomicalSeason"
  };

  /** Builds a {@link DailyAstro}. */
  public static final class Builder {
    private @Nullable Instant sunrise;
    private @Nullable Instant sunset;
    private @Nullable String moonPhase;
    private @Nullable Double moonIllumination;
    private @Nullable String astronomicalSeason;

    private Builder() {}

    /**
     * Sets {@link DailyAstro#sunrise()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder sunrise(@Nullable Instant value) {
      this.sunrise = value;
      return this;
    }

    /**
     * Sets {@link DailyAstro#sunset()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder sunset(@Nullable Instant value) {
      this.sunset = value;
      return this;
    }

    /**
     * Sets {@link DailyAstro#moonPhase()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder moonPhase(MoonPhase value) {
      this.moonPhase = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyAstro#moonPhaseAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder moonPhaseAsString(String value) {
      this.moonPhase = value;
      return this;
    }

    /**
     * Sets {@link DailyAstro#moonIllumination()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder moonIllumination(double value) {
      this.moonIllumination = value;
      return this;
    }

    /**
     * Sets {@link DailyAstro#astronomicalSeason()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder astronomicalSeason(AstronomicalSeason value) {
      this.astronomicalSeason = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyAstro#astronomicalSeasonAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder astronomicalSeasonAsString(String value) {
      this.astronomicalSeason = value;
      return this;
    }

    /**
     * Builds the {@link DailyAstro}.
     *
     * @return the immutable DailyAstro
     * @throws IllegalStateException if a required value was not set
     */
    public DailyAstro build() {
      return new DailyAstro(this);
    }
  }
}
