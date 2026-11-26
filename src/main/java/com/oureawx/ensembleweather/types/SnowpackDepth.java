// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Hourly snow depth from {@code start_time} (cm or in).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackDepth}.
 */
public final class SnowpackDepth extends Model {
  private final Instant startTime;
  private final int intervalHours;
  private final String basis;
  private final List<Double> p10;
  private final List<Double> p50;
  private final List<Double> p90;

  private SnowpackDepth(Builder b) {
    this.startTime = require(b.startTime, "start_time");
    this.intervalHours = require(b.intervalHours, "interval_hours");
    this.basis = require(b.basis, "basis");
    this.p10 = List.copyOf(b.p10);
    this.p50 = List.copyOf(b.p50);
    this.p90 = List.copyOf(b.p90);
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
    b.startTime = this.startTime;
    b.intervalHours = this.intervalHours;
    b.basis = this.basis;
    b.p10 = this.p10;
    b.p50 = this.p50;
    b.p90 = this.p90;
    return b;
  }

  /**
   * The {@code start_time} value.
   *
   * @return the {@code start_time} value
   */
  public Instant startTime() {
    return startTime;
  }

  /**
   * The {@code interval_hours} value.
   *
   * @return the {@code interval_hours} value
   */
  public int intervalHours() {
    return intervalHours;
  }

  /**
   * {@code measured}: started from a sensor reading at the spot. {@code open_loop}: the model's own
   * state, served with {@code p10} and {@code p90} where the caller's plan includes the range.
   * Tolerate values you do not know.
   *
   * @return the {@code basis} value
   */
  public String basis() {
    return basis;
  }

  /**
   * The {@code p10} value.
   *
   * @return the {@code p10} values; empty when the response has none
   */
  public List<Double> p10() {
    return p10;
  }

  /**
   * The {@code p50} value.
   *
   * @return the {@code p50} values; empty when the response has none
   */
  public List<Double> p50() {
    return p50;
  }

  /**
   * The {@code p90} value.
   *
   * @return the {@code p90} values; empty when the response has none
   */
  public List<Double> p90() {
    return p90;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {startTime, intervalHours, basis, p10, p50, p90};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "startTime", "intervalHours", "basis", "p10", "p50", "p90"
  };

  /** Builds a {@link SnowpackDepth}. */
  public static final class Builder {
    private @Nullable Instant startTime;
    private @Nullable Integer intervalHours;
    private @Nullable String basis;
    private List<Double> p10 = List.of();
    private List<Double> p50 = List.of();
    private List<Double> p90 = List.of();

    private Builder() {}

    /**
     * Sets {@link SnowpackDepth#startTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder startTime(Instant value) {
      this.startTime = value;
      return this;
    }

    /**
     * Sets {@link SnowpackDepth#intervalHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder intervalHours(int value) {
      this.intervalHours = value;
      return this;
    }

    /**
     * Sets {@link SnowpackDepth#basis()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder basis(String value) {
      this.basis = value;
      return this;
    }

    /**
     * Sets {@link SnowpackDepth#p10()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p10(List<Double> values) {
      this.p10 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackDepth#p50()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p50(List<Double> values) {
      this.p50 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackDepth#p90()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p90(List<Double> values) {
      this.p90 = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link SnowpackDepth}.
     *
     * @return the immutable SnowpackDepth
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackDepth build() {
      return new SnowpackDepth(this);
    }
  }
}
