// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * This hour's headline precipitation ({@code ensemble.precipitation.mean}) split by phase, in the
 * precipitation units -- {@code rain + snow + rain_snow_mix + freezing_rain} is the hour's mean (to
 * the served precision), so a stacked bar is the headline bar. The shares come from the members
 * ({@code members=true}): each member value votes its precipitation, spread evenly over the hours
 * its {@code precipitation_hours} span, as rain (none of it falls as snow), snow (all of it does)
 * or rain/snow mix (part does) -- its share being its served snowfall over precipitation x SLR, the
 * snowfall rule's melting-layer and surface shares, so the split agrees with the members' snowfall.
 * A liquid member whose drops freeze on contact -- fully melted aloft over a sub-freezing surface,
 * the rule {@code precipitation_type} calls freezing rain, applied to the member's own column --
 * votes {@code freezing_rain}, not {@code rain} (before it was counted in {@code rain}), so an hour
 * whose {@code precipitation_type} is {@code freezing_rain} draws its icing members as freezing
 * rain. Ice pellets are not split out: {@code precipitation_type} has no ice-pellet type (a partly
 * melted drop over a frozen surface is {@code rain_snow_mix} in both). Served with {@code
 * members=true} at every hour some member's precipitation covers; absent where no member's does,
 * and where every covering member is dry under a wet headline (no phase to apportion). A dry hour
 * serves zeros.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PrecipitationByType}.
 */
public final class PrecipitationByType extends Model {
  private final double rain;
  private final double snow;
  private final double rainSnowMix;
  private final double freezingRain;

  private PrecipitationByType(Builder b) {
    this.rain = require(b.rain, "rain");
    this.snow = require(b.snow, "snow");
    this.rainSnowMix = require(b.rainSnowMix, "rain_snow_mix");
    this.freezingRain = require(b.freezingRain, "freezing_rain");
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
    b.rain = this.rain;
    b.snow = this.snow;
    b.rainSnowMix = this.rainSnowMix;
    b.freezingRain = this.freezingRain;
    return b;
  }

  /**
   * The {@code rain} value.
   *
   * @return the {@code rain} value
   */
  public double rain() {
    return rain;
  }

  /**
   * The liquid that falls as snow (not snow depth; that is {@code ensemble.snowfall}).
   *
   * @return the {@code snow} value
   */
  public double snow() {
    return snow;
  }

  /**
   * The {@code rain_snow_mix} value.
   *
   * @return the {@code rain_snow_mix} value
   */
  public double rainSnowMix() {
    return rainSnowMix;
  }

  /**
   * Liquid that freezes on contact (counted in {@code rain} before).
   *
   * @return the {@code freezing_rain} value
   */
  public double freezingRain() {
    return freezingRain;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {rain, snow, rainSnowMix, freezingRain};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"rain", "snow", "rainSnowMix", "freezingRain"};

  /** Builds a {@link PrecipitationByType}. */
  public static final class Builder {
    private @Nullable Double rain;
    private @Nullable Double snow;
    private @Nullable Double rainSnowMix;
    private @Nullable Double freezingRain;

    private Builder() {}

    /**
     * Sets {@link PrecipitationByType#rain()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder rain(double value) {
      this.rain = value;
      return this;
    }

    /**
     * Sets {@link PrecipitationByType#snow()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snow(double value) {
      this.snow = value;
      return this;
    }

    /**
     * Sets {@link PrecipitationByType#rainSnowMix()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder rainSnowMix(double value) {
      this.rainSnowMix = value;
      return this;
    }

    /**
     * Sets {@link PrecipitationByType#freezingRain()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder freezingRain(double value) {
      this.freezingRain = value;
      return this;
    }

    /**
     * Builds the {@link PrecipitationByType}.
     *
     * @return the immutable PrecipitationByType
     * @throws IllegalStateException if a required value was not set
     */
    public PrecipitationByType build() {
      return new PrecipitationByType(this);
    }
  }
}
