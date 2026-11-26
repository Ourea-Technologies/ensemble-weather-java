// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A distribution over the ensemble MEMBERS ({@code members_cumulative}, {@code members_spread},
 * {@code members_6h}): the {@code Quantiles} shape. {@code P50}, the members' median, is the
 * plume's paid detail (the {@code plume_detail} entitlement: Plus and Pro) and is ABSENT for Free
 * and anonymous callers ({@code members_view: pool}); the mean, {@code p10}, {@code p25}, {@code
 * p75}, {@code p90}, {@code min} and {@code max} are served to every caller. This does not touch
 * the forecast's own calibrated quantiles ({@code ensemble.<variable>.p50}, the daily statistics).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code MemberQuantiles}.
 */
public final class MemberQuantiles extends Model {
  private final double mean;
  private final double p10;
  private final double p25;
  private final @Nullable Double p50;
  private final double p75;
  private final double p90;
  private final double min;
  private final double max;
  private final @Nullable Double probability;

  private MemberQuantiles(Builder b) {
    this.mean = require(b.mean, "mean");
    this.p10 = require(b.p10, "p10");
    this.p25 = require(b.p25, "p25");
    this.p50 = b.p50;
    this.p75 = require(b.p75, "p75");
    this.p90 = require(b.p90, "p90");
    this.min = require(b.min, "min");
    this.max = require(b.max, "max");
    this.probability = b.probability;
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
    b.mean = this.mean;
    b.p10 = this.p10;
    b.p25 = this.p25;
    b.p50 = this.p50;
    b.p75 = this.p75;
    b.p90 = this.p90;
    b.min = this.min;
    b.max = this.max;
    b.probability = this.probability;
    return b;
  }

  /**
   * The {@code mean} value.
   *
   * @return the {@code mean} value
   */
  public double mean() {
    return mean;
  }

  /**
   * The {@code p10} value.
   *
   * @return the {@code p10} value
   */
  public double p10() {
    return p10;
  }

  /**
   * The {@code p25} value.
   *
   * @return the {@code p25} value
   */
  public double p25() {
    return p25;
  }

  /**
   * The members' median. Plus and Pro; absent for Free and anonymous.
   *
   * @return the {@code p50} value, or empty when the response omits it
   */
  public OptionalDouble p50() {
    return p50 == null ? OptionalDouble.empty() : OptionalDouble.of(p50);
  }

  /**
   * The {@code p75} value.
   *
   * @return the {@code p75} value
   */
  public double p75() {
    return p75;
  }

  /**
   * The {@code p90} value.
   *
   * @return the {@code p90} value
   */
  public double p90() {
    return p90;
  }

  /**
   * The {@code min} value.
   *
   * @return the {@code min} value
   */
  public double min() {
    return min;
  }

  /**
   * The {@code max} value.
   *
   * @return the {@code max} value
   */
  public double max() {
    return max;
  }

  /**
   * As {@code Quantiles.probability}, where the use says.
   *
   * @return the {@code probability} value, or empty when the response omits it
   */
  public OptionalDouble probability() {
    return probability == null ? OptionalDouble.empty() : OptionalDouble.of(probability);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {mean, p10, p25, p50, p75, p90, min, max, probability};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "mean", "p10", "p25", "p50", "p75", "p90", "min", "max", "probability"
  };

  /** Builds a {@link MemberQuantiles}. */
  public static final class Builder {
    private @Nullable Double mean;
    private @Nullable Double p10;
    private @Nullable Double p25;
    private @Nullable Double p50;
    private @Nullable Double p75;
    private @Nullable Double p90;
    private @Nullable Double min;
    private @Nullable Double max;
    private @Nullable Double probability;

    private Builder() {}

    /**
     * Sets {@link MemberQuantiles#mean()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder mean(double value) {
      this.mean = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#p10()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder p10(double value) {
      this.p10 = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#p25()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder p25(double value) {
      this.p25 = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#p50()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p50(@Nullable Double value) {
      this.p50 = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#p75()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder p75(double value) {
      this.p75 = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#p90()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder p90(double value) {
      this.p90 = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#min()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder min(double value) {
      this.min = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#max()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder max(double value) {
      this.max = value;
      return this;
    }

    /**
     * Sets {@link MemberQuantiles#probability()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder probability(@Nullable Double value) {
      this.probability = value;
      return this;
    }

    /**
     * Builds the {@link MemberQuantiles}.
     *
     * @return the immutable MemberQuantiles
     * @throws IllegalStateException if a required value was not set
     */
    public MemberQuantiles build() {
      return new MemberQuantiles(this);
    }
  }
}
