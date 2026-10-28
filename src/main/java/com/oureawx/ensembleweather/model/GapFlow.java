// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * The Columbia Gorge gap-flow status ({@code ForecastResponse.gap_flow}).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code GapFlow}.
 */
public final class GapFlow extends Model {
  private final String status;
  private final double pressureDiffHpa;
  private final double windMultiplier;
  private final String direction;

  private GapFlow(Builder b) {
    this.status = require(b.status, "status");
    this.pressureDiffHpa = require(b.pressureDiffHpa, "pressure_diff_hpa");
    this.windMultiplier = require(b.windMultiplier, "wind_multiplier");
    this.direction = require(b.direction, "direction");
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
    b.pressureDiffHpa = this.pressureDiffHpa;
    b.windMultiplier = this.windMultiplier;
    b.direction = this.direction;
    return b;
  }

  /**
   * The {@code status} value.
   *
   * @return the {@code status} value ({@link GapFlowStatus#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public GapFlowStatus status() {
    return GapFlowStatus.fromWireValue(status);
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
   * The {@code pressure_diff_hpa} value.
   *
   * @return the {@code pressure_diff_hpa} value
   */
  public double pressureDiffHpa() {
    return pressureDiffHpa;
  }

  /**
   * The {@code wind_multiplier} value.
   *
   * @return the {@code wind_multiplier} value
   */
  public double windMultiplier() {
    return windMultiplier;
  }

  /**
   * The {@code direction} value.
   *
   * @return the {@code direction} value
   */
  public String direction() {
    return direction;
  }

  @Override
  Object[] values() {
    return new Object[] {status, pressureDiffHpa, windMultiplier, direction};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "status", "pressureDiffHpa", "windMultiplier", "direction"
  };

  /** Builds a {@link GapFlow}. */
  public static final class Builder {
    private @Nullable String status;
    private @Nullable Double pressureDiffHpa;
    private @Nullable Double windMultiplier;
    private @Nullable String direction;

    private Builder() {}

    /**
     * Sets {@link GapFlow#status()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder status(GapFlowStatus value) {
      this.status = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link GapFlow#statusAsString()}, the raw wire value, kept verbatim even when this SDK
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
     * Sets {@link GapFlow#pressureDiffHpa()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder pressureDiffHpa(double value) {
      this.pressureDiffHpa = value;
      return this;
    }

    /**
     * Sets {@link GapFlow#windMultiplier()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windMultiplier(double value) {
      this.windMultiplier = value;
      return this;
    }

    /**
     * Sets {@link GapFlow#direction()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder direction(String value) {
      this.direction = value;
      return this;
    }

    /**
     * Builds the {@link GapFlow}.
     *
     * @return the immutable GapFlow
     * @throws IllegalStateException if a required value was not set
     */
    public GapFlow build() {
      return new GapFlow(this);
    }
  }
}
