// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * Predicted precipitation type derived from the mean snow level, station elevation, and the hour's
 * served (corrected) surface temperature and dewpoint, on the same rule, and the same temperature,
 * the served {@code snowfall} accumulates at. The snow level is the bottom of the melting layer,
 * where falling snow has fully melted; the rain/snow mix lies ABOVE it, from the snow level up to
 * the all-snow height about 127 m higher. Absent when the hour has no snow level: the phase cannot
 * be placed.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PrecipType}.
 */
public final class PrecipType extends Model {
  private final String type;
  private final double snowProbability;
  private final double rainProbability;

  private PrecipType(Builder b) {
    this.type = require(b.type, "type");
    this.snowProbability = require(b.snowProbability, "snow_probability");
    this.rainProbability = require(b.rainProbability, "rain_probability");
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
    b.type = this.type;
    b.snowProbability = this.snowProbability;
    b.rainProbability = this.rainProbability;
    return b;
  }

  /**
   * The categorical reading of {@code snow_probability}: {@code snow} only at 1 (and at most 2 °C),
   * {@code rain_snow_mix} strictly between 0 and 1, {@code rain} or {@code freezing_rain} at 0.
   * {@code freezing_rain} is fully melted precipitation (the location at or below the snow level)
   * falling onto a surface below 0 °C. The hourly {@code condition} states this type's phase: a
   * Snow, Rain/Snow Mix or Freezing Rain label, or a liquid label for {@code rain}.
   *
   * @return the {@code type} value ({@link PrecipitationType#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public PrecipitationType type() {
    return PrecipitationType.fromWireValue(type);
  }

  /**
   * The {@code type} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code type} string
   */
  public String typeAsString() {
    return type;
  }

  /**
   * Share of the hour's precipitation that falls as snow (0-1): the melting layer's snow fraction
   * at the location (0 at and below the snow level, 1 from about 127 m above it, linear in melting
   * energy between) times the surface snow share — all snow at or below 2 °C air temperature, none
   * at or above 4 °C, and the surface wet-bulb fade (none at or above 2 °C wet-bulb). It is the
   * share the served {@code snowfall} accumulates at, so a warm rain hour has 0 and an hour inside
   * the mixed band carries its fraction. Conditional on precipitation; for the chance that snow
   * accumulates at all, use {@code ensemble.snowfall.probability}.
   *
   * @return the {@code snow_probability} value
   */
  public double snowProbability() {
    return snowProbability;
  }

  /**
   * {@code 1 - snow_probability} (0-1).
   *
   * @return the {@code rain_probability} value
   */
  public double rainProbability() {
    return rainProbability;
  }

  @Override
  Object[] values() {
    return new Object[] {type, snowProbability, rainProbability};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"type", "snowProbability", "rainProbability"};

  /** Builds a {@link PrecipType}. */
  public static final class Builder {
    private @Nullable String type;
    private @Nullable Double snowProbability;
    private @Nullable Double rainProbability;

    private Builder() {}

    /**
     * Sets {@link PrecipType#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(PrecipitationType value) {
      this.type = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link PrecipType#typeAsString()}, the raw wire value, kept verbatim even when this SDK
     * version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder typeAsString(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link PrecipType#snowProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snowProbability(double value) {
      this.snowProbability = value;
      return this;
    }

    /**
     * Sets {@link PrecipType#rainProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder rainProbability(double value) {
      this.rainProbability = value;
      return this;
    }

    /**
     * Builds the {@link PrecipType}.
     *
     * @return the immutable PrecipType
     * @throws IllegalStateException if a required value was not set
     */
    public PrecipType build() {
      return new PrecipType(this);
    }
  }
}
