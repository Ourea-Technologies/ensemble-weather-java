// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A day's wind summary ({@code DailyForecast.wind}). Uses gust data when available, otherwise
 * sustained wind.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code DailyWind}.
 */
public final class DailyWind extends Model {
  private final String status;
  private final double peakSpeed;
  private final @Nullable Double peakSpeedP90;
  private final boolean isGust;
  private final @Nullable Double sustainedSpeed;

  private DailyWind(Builder b) {
    this.status = require(b.status, "status");
    this.peakSpeed = require(b.peakSpeed, "peak_speed");
    this.peakSpeedP90 = b.peakSpeedP90;
    this.isGust = require(b.isGust, "is_gust");
    this.sustainedSpeed = b.sustainedSpeed;
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
    b.status = this.status;
    b.peakSpeed = this.peakSpeed;
    b.peakSpeedP90 = this.peakSpeedP90;
    b.isGust = this.isGust;
    b.sustainedSpeed = this.sustainedSpeed;
    return b;
  }

  /**
   * Wind severity classification based on peak gust/speed. calm: &lt;11 mph, light: 11-22,
   * moderate: 22-34, strong: 34-49 (lifts affected), very_strong: 49-67 (most lifts closed),
   * extreme: 67+ (all lifts closed).
   *
   * @return the {@code status} value ({@link WindStatus#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public WindStatus status() {
    return WindStatus.fromWireValue(status);
  }

  /**
   * The {@code status} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code status} string
   */
  public String statusAsString() {
    return status;
  }

  /**
   * Peak wind for the day (mph imperial, m/s metric): the expected peak — the maximum over the
   * day's hours of the hourly gust ensemble mean when gust data is available, otherwise of the
   * sustained wind mean. Comparable to a deterministic provider's daily gust (NWS).
   *
   * @return the {@code peak_speed} value
   */
  public double peakSpeed() {
    return peakSpeed;
  }

  /**
   * The 90th-percentile counterpart of peak_speed (the maximum over the day's hours of the hourly
   * gust p90), a "gusts could reach" upper band. Omitted when is_gust is false.
   *
   * @return the {@code peak_speed_p90} value, or empty when the response omits it
   */
  public OptionalDouble peakSpeedP90() {
    return peakSpeedP90 == null ? OptionalDouble.empty() : OptionalDouble.of(peakSpeedP90);
  }

  /**
   * Whether peak_speed is from gust data (true) or sustained wind (false).
   *
   * @return the {@code is_gust} value
   */
  public boolean isGust() {
    return isGust;
  }

  /**
   * Peak sustained wind for the day (mph imperial, m/s metric), independent of gust. Equals
   * peak_speed when is_gust is false. Use this for sustained-vs-sustained comparison against
   * providers whose daily wind is sustained (e.g. NWS). Omitted when no sustained-wind ensemble was
   * available.
   *
   * @return the {@code sustained_speed} value, or empty when the response omits it
   */
  public OptionalDouble sustainedSpeed() {
    return sustainedSpeed == null ? OptionalDouble.empty() : OptionalDouble.of(sustainedSpeed);
  }

  @Override
  Object[] values() {
    return new Object[] {status, peakSpeed, peakSpeedP90, isGust, sustainedSpeed};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "status", "peakSpeed", "peakSpeedP90", "isGust", "sustainedSpeed"
  };

  /** Builds a {@link DailyWind}. */
  public static final class Builder {
    private @Nullable String status;
    private @Nullable Double peakSpeed;
    private @Nullable Double peakSpeedP90;
    private @Nullable Boolean isGust;
    private @Nullable Double sustainedSpeed;

    private Builder() {}

    /**
     * Sets {@link DailyWind#status()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder status(WindStatus value) {
      this.status = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyWind#statusAsString()}, the raw wire value, kept verbatim even when this SDK
     * version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder statusAsString(String value) {
      this.status = value;
      return this;
    }

    /**
     * Sets {@link DailyWind#peakSpeed()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder peakSpeed(double value) {
      this.peakSpeed = value;
      return this;
    }

    /**
     * Sets {@link DailyWind#peakSpeedP90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder peakSpeedP90(@Nullable Double value) {
      this.peakSpeedP90 = value;
      return this;
    }

    /**
     * Sets {@link DailyWind#isGust()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder isGust(boolean value) {
      this.isGust = value;
      return this;
    }

    /**
     * Sets {@link DailyWind#sustainedSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder sustainedSpeed(@Nullable Double value) {
      this.sustainedSpeed = value;
      return this;
    }

    /**
     * Builds the {@link DailyWind}.
     *
     * @return the immutable DailyWind
     * @throws IllegalStateException if a required value was not set
     */
    public DailyWind build() {
      return new DailyWind(this);
    }
  }
}
