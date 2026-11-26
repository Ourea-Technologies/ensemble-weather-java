// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A PassChance from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassChance}.
 */
public final class PassChance extends Model {
  private final @Nullable Double probability;
  private final double threshold;
  private final @Nullable Double precipitationThreshold;
  private final int members;
  private final String method;
  private final @Nullable String nullReason;

  private PassChance(Builder b) {
    this.probability = b.probability;
    this.threshold = require(b.threshold, "threshold");
    this.precipitationThreshold = b.precipitationThreshold;
    this.members = require(b.members, "members");
    this.method = require(b.method, "method");
    this.nullReason = b.nullReason;
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
    b.probability = this.probability;
    b.threshold = this.threshold;
    b.precipitationThreshold = this.precipitationThreshold;
    b.members = this.members;
    b.method = this.method;
    b.nullReason = this.nullReason;
    return b;
  }

  /**
   * 0-1 (0.001); null when it cannot be answered ({@code null_reason}).
   *
   * @return the {@code probability} value, or empty when the response omits it
   */
  public OptionalDouble probability() {
    return probability == null ? OptionalDouble.empty() : OptionalDouble.of(probability);
  }

  /**
   * The event's threshold, in the response's units.
   *
   * @return the {@code threshold} value
   */
  public double threshold() {
    return threshold;
  }

  /**
   * For {@code p_wet_below_freezing}, the precipitation that makes an hour wet, in the response's
   * units.
   *
   * @return the {@code precipitation_threshold} value, or empty when the response omits it
   */
  public OptionalDouble precipitationThreshold() {
    return precipitationThreshold == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(precipitationThreshold);
  }

  /**
   * The members that voted (0 for {@code hourly_gust_distribution}).
   *
   * @return the {@code members} value
   */
  public int members() {
    return members;
  }

  /**
   * The {@code method} value. One of {@code members}, {@code hourly_gust_distribution}; read it as
   * a string, since the API may add values.
   *
   * @return the {@code method} value
   */
  public String method() {
    return method;
  }

  /**
   * The {@code null_reason} value. One of {@code window_not_covered}, {@code too_few_members},
   * {@code variable_unavailable}; read it as a string, since the API may add values.
   *
   * @return the {@code null_reason} value, or empty when the response omits it
   */
  public Optional<String> nullReason() {
    return Optional.ofNullable(nullReason);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      probability, threshold, precipitationThreshold, members, method, nullReason
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "probability", "threshold", "precipitationThreshold", "members", "method", "nullReason"
  };

  /** Builds a {@link PassChance}. */
  public static final class Builder {
    private @Nullable Double probability;
    private @Nullable Double threshold;
    private @Nullable Double precipitationThreshold;
    private @Nullable Integer members;
    private @Nullable String method;
    private @Nullable String nullReason;

    private Builder() {}

    /**
     * Sets {@link PassChance#probability()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder probability(@Nullable Double value) {
      this.probability = value;
      return this;
    }

    /**
     * Sets {@link PassChance#threshold()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder threshold(double value) {
      this.threshold = value;
      return this;
    }

    /**
     * Sets {@link PassChance#precipitationThreshold()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationThreshold(@Nullable Double value) {
      this.precipitationThreshold = value;
      return this;
    }

    /**
     * Sets {@link PassChance#members()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder members(int value) {
      this.members = value;
      return this;
    }

    /**
     * Sets {@link PassChance#method()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder method(String value) {
      this.method = value;
      return this;
    }

    /**
     * Sets {@link PassChance#nullReason()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder nullReason(@Nullable String value) {
      this.nullReason = value;
      return this;
    }

    /**
     * Builds the {@link PassChance}.
     *
     * @return the immutable PassChance
     * @throws IllegalStateException if a required value was not set
     */
    public PassChance build() {
      return new PassChance(this);
    }
  }
}
