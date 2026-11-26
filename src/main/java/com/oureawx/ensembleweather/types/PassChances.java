// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Chances at the pass over the next {@code window_hours} hours, each the share of the forecast's
 * members meeting the event (the {@code ?prob=} machinery), except the gust chance (see its {@code
 * method}). Thresholds are fixed product constants named in imperial (1 in, 6 in, 40 mph): they do
 * not change with {@code units}; {@code threshold} states each in the response's units (25.4 mm,
 * 152.4 mm, 17.9 m/s).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassChances}.
 */
public final class PassChances extends Model {
  private final int windowHours;
  private final Instant from;
  private final Instant to;
  private final PassChance pSnowGe1in;
  private final PassChance pSnowGe6in;
  private final PassChance pWetBelowFreezing;
  private final PassChance pGustGt40mph;

  private PassChances(Builder b) {
    this.windowHours = require(b.windowHours, "window_hours");
    this.from = require(b.from, "from");
    this.to = require(b.to, "to");
    this.pSnowGe1in = require(b.pSnowGe1in, "p_snow_ge_1in");
    this.pSnowGe6in = require(b.pSnowGe6in, "p_snow_ge_6in");
    this.pWetBelowFreezing = require(b.pWetBelowFreezing, "p_wet_below_freezing");
    this.pGustGt40mph = require(b.pGustGt40mph, "p_gust_gt_40mph");
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
    b.windowHours = this.windowHours;
    b.from = this.from;
    b.to = this.to;
    b.pSnowGe1in = this.pSnowGe1in;
    b.pSnowGe6in = this.pSnowGe6in;
    b.pWetBelowFreezing = this.pWetBelowFreezing;
    b.pGustGt40mph = this.pGustGt40mph;
    return b;
  }

  /**
   * The window's length, 24.
   *
   * @return the {@code window_hours} value
   */
  public int windowHours() {
    return windowHours;
  }

  /**
   * The {@code from} value.
   *
   * @return the {@code from} value
   */
  public Instant from() {
    return from;
  }

  /**
   * The {@code to} value.
   *
   * @return the {@code to} value
   */
  public Instant to() {
    return to;
  }

  /**
   * Snowfall total over the window of 1 in (25.4 mm) or more: {@code prob=snowfall:ge:<1 in>:24h}.
   * Threshold fixed, does not change with units.
   *
   * @return the {@code p_snow_ge_1in} value
   */
  public PassChance pSnowGe1in() {
    return pSnowGe1in;
  }

  /**
   * Snowfall total over the window of 6 in (152.4 mm) or more. Threshold fixed, does not change
   * with units.
   *
   * @return the {@code p_snow_ge_6in} value
   */
  public PassChance pSnowGe6in() {
    return pSnowGe6in;
  }

  /**
   * Some hour of the window in which the member is at or below freezing ({@code threshold}: 0 C, 32
   * F) with precipitation of at least {@code precipitation_threshold} (0.254 mm, 0.01 in) in the
   * member's value ending at that hour: a wet road at or below freezing. Weather, not a measured
   * pavement state.
   *
   * @return the {@code p_wet_below_freezing} value
   */
  public PassChance pWetBelowFreezing() {
    return pWetBelowFreezing;
  }

  /**
   * A gust over 40 mph (17.9 m/s; threshold fixed, does not change with units) in the window. The
   * members carry no gust, so this is the highest hourly chance in the window, read from each
   * hour's served calibrated gust distribution ({@code method} {@code hourly_gust_distribution}):
   * the chance of the gustiest hour, not of any hour.
   *
   * @return the {@code p_gust_gt_40mph} value
   */
  public PassChance pGustGt40mph() {
    return pGustGt40mph;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      windowHours, from, to, pSnowGe1in, pSnowGe6in, pWetBelowFreezing, pGustGt40mph
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "windowHours", "from", "to", "pSnowGe1in", "pSnowGe6in", "pWetBelowFreezing", "pGustGt40mph"
  };

  /** Builds a {@link PassChances}. */
  public static final class Builder {
    private @Nullable Integer windowHours;
    private @Nullable Instant from;
    private @Nullable Instant to;
    private @Nullable PassChance pSnowGe1in;
    private @Nullable PassChance pSnowGe6in;
    private @Nullable PassChance pWetBelowFreezing;
    private @Nullable PassChance pGustGt40mph;

    private Builder() {}

    /**
     * Sets {@link PassChances#windowHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windowHours(int value) {
      this.windowHours = value;
      return this;
    }

    /**
     * Sets {@link PassChances#from()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder from(Instant value) {
      this.from = value;
      return this;
    }

    /**
     * Sets {@link PassChances#to()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder to(Instant value) {
      this.to = value;
      return this;
    }

    /**
     * Sets {@link PassChances#pSnowGe1in()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder pSnowGe1in(PassChance value) {
      this.pSnowGe1in = value;
      return this;
    }

    /**
     * Sets {@link PassChances#pSnowGe6in()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder pSnowGe6in(PassChance value) {
      this.pSnowGe6in = value;
      return this;
    }

    /**
     * Sets {@link PassChances#pWetBelowFreezing()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder pWetBelowFreezing(PassChance value) {
      this.pWetBelowFreezing = value;
      return this;
    }

    /**
     * Sets {@link PassChances#pGustGt40mph()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder pGustGt40mph(PassChance value) {
      this.pGustGt40mph = value;
      return this;
    }

    /**
     * Builds the {@link PassChances}.
     *
     * @return the immutable PassChances
     * @throws IllegalStateException if a required value was not set
     */
    public PassChances build() {
      return new PassChances(this);
    }
  }
}
