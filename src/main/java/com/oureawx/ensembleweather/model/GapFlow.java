// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * Columbia Gorge gap flow status at the first served hour, from the forecast
 * mean-sea-level-pressure difference across the Gorge (The Dalles minus Portland, each model
 * differenced on its own, then averaged). Only present for locations within the Gorge corridor and
 * when the forecast carries MSLP at both ends. Omitted for nowcast=false.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code GapFlow}.
 */
public final class GapFlow extends Model {
  private final @Nullable String status;
  private final @Nullable Double pressureDiffHpa;
  private final @Nullable Double windMultiplier;
  private final @Nullable String direction;

  private GapFlow(Builder b) {
    this.status = b.status;
    this.pressureDiffHpa = b.pressureDiffHpa;
    this.windMultiplier = b.windMultiplier;
    this.direction = b.direction;
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
   * @return the {@code status} value, or empty when the response omits it ({@link
   *     GapFlowStatus#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<GapFlowStatus> status() {
    return Optional.ofNullable(status).map(GapFlowStatus::fromWireValue);
  }

  /**
   * The {@code status} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code status} string, or empty when absent
   */
  public Optional<String> statusAsString() {
    return Optional.ofNullable(status);
  }

  /**
   * The {@code pressure_diff_hpa} value.
   *
   * @return the {@code pressure_diff_hpa} value, or empty when the response omits it
   */
  public OptionalDouble pressureDiffHpa() {
    return pressureDiffHpa == null ? OptionalDouble.empty() : OptionalDouble.of(pressureDiffHpa);
  }

  /**
   * The {@code wind_multiplier} value.
   *
   * @return the {@code wind_multiplier} value, or empty when the response omits it
   */
  public OptionalDouble windMultiplier() {
    return windMultiplier == null ? OptionalDouble.empty() : OptionalDouble.of(windMultiplier);
  }

  /**
   * The {@code direction} value.
   *
   * @return the {@code direction} value, or empty when the response omits it
   */
  public Optional<String> direction() {
    return Optional.ofNullable(direction);
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
     * Sets {@link GapFlow#status()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder status(@Nullable GapFlowStatus value) {
      this.status = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link GapFlow#statusAsString()}, the raw wire value, kept verbatim even when this SDK
     * version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder statusAsString(@Nullable String value) {
      this.status = value;
      return this;
    }

    /**
     * Sets {@link GapFlow#pressureDiffHpa()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pressureDiffHpa(@Nullable Double value) {
      this.pressureDiffHpa = value;
      return this;
    }

    /**
     * Sets {@link GapFlow#windMultiplier()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windMultiplier(@Nullable Double value) {
      this.windMultiplier = value;
      return this;
    }

    /**
     * Sets {@link GapFlow#direction()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder direction(@Nullable String value) {
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
