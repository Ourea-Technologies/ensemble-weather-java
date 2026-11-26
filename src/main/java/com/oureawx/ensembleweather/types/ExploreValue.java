// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A search hit's Explore value; the fields mean what an {@code ExploreItem}'s do.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ExploreValue}.
 */
public final class ExploreValue extends Model {
  private final String metric;
  private final double value;
  private final String unit;
  private final @Nullable Double p10;
  private final @Nullable Double p90;
  private final Instant asOf;
  private final @Nullable Instant initializedAt;
  private final String source;

  private ExploreValue(Builder b) {
    this.metric = require(b.metric, "metric");
    this.value = require(b.value, "value");
    this.unit = require(b.unit, "unit");
    this.p10 = b.p10;
    this.p90 = b.p90;
    this.asOf = require(b.asOf, "as_of");
    this.initializedAt = b.initializedAt;
    this.source = require(b.source, "source");
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
    b.metric = this.metric;
    b.value = this.value;
    b.unit = this.unit;
    b.p10 = this.p10;
    b.p90 = this.p90;
    b.asOf = this.asOf;
    b.initializedAt = this.initializedAt;
    b.source = this.source;
    return b;
  }

  /**
   * The {@code metric} value.
   *
   * @return the {@code metric} value ({@link ExploreMetric#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public ExploreMetric metric() {
    return ExploreMetric.fromWireValue(metric);
  }

  /**
   * The {@code metric} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code metric} string
   */
  public String metricAsString() {
    return metric;
  }

  /**
   * The {@code value} value.
   *
   * @return the {@code value} value
   */
  public double value() {
    return value;
  }

  /**
   * The {@code unit} value. One of {@code mm}, {@code cm}, {@code in}, {@code percent}; read it as
   * a string, since the API may add values.
   *
   * @return the {@code unit} value
   */
  public String unit() {
    return unit;
  }

  /**
   * The {@code p10} value.
   *
   * @return the {@code p10} value, or empty when the response omits it
   */
  public OptionalDouble p10() {
    return p10 == null ? OptionalDouble.empty() : OptionalDouble.of(p10);
  }

  /**
   * The {@code p90} value.
   *
   * @return the {@code p90} value, or empty when the response omits it
   */
  public OptionalDouble p90() {
    return p90 == null ? OptionalDouble.empty() : OptionalDouble.of(p90);
  }

  /**
   * The {@code as_of} value.
   *
   * @return the {@code as_of} value
   */
  public Instant asOf() {
    return asOf;
  }

  /**
   * The {@code initialized_at} value.
   *
   * @return the {@code initialized_at} value, or empty when the response omits it
   */
  public Optional<Instant> initializedAt() {
    return Optional.ofNullable(initializedAt);
  }

  /**
   * The {@code source} value.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {metric, value, unit, p10, p90, asOf, initializedAt, source};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "metric", "value", "unit", "p10", "p90", "asOf", "initializedAt", "source"
  };

  /** Builds a {@link ExploreValue}. */
  public static final class Builder {
    private @Nullable String metric;
    private @Nullable Double value;
    private @Nullable String unit;
    private @Nullable Double p10;
    private @Nullable Double p90;
    private @Nullable Instant asOf;
    private @Nullable Instant initializedAt;
    private @Nullable String source;

    private Builder() {}

    /**
     * Sets {@link ExploreValue#metric()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder metric(ExploreMetric value) {
      this.metric = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ExploreValue#metricAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder metricAsString(String value) {
      this.metric = value;
      return this;
    }

    /**
     * Sets {@link ExploreValue#value()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder value(double value) {
      this.value = value;
      return this;
    }

    /**
     * Sets {@link ExploreValue#unit()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder unit(String value) {
      this.unit = value;
      return this;
    }

    /**
     * Sets {@link ExploreValue#p10()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p10(@Nullable Double value) {
      this.p10 = value;
      return this;
    }

    /**
     * Sets {@link ExploreValue#p90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p90(@Nullable Double value) {
      this.p90 = value;
      return this;
    }

    /**
     * Sets {@link ExploreValue#asOf()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder asOf(Instant value) {
      this.asOf = value;
      return this;
    }

    /**
     * Sets {@link ExploreValue#initializedAt()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder initializedAt(@Nullable Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link ExploreValue#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Builds the {@link ExploreValue}.
     *
     * @return the immutable ExploreValue
     * @throws IllegalStateException if a required value was not set
     */
    public ExploreValue build() {
      return new ExploreValue(this);
    }
  }
}
