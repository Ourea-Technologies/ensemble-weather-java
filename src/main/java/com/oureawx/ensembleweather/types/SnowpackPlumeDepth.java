// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The members' depth at each step. Absent when there is no plume.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class SnowpackPlumeDepth extends Model {
  private final Instant startTime;
  private final int intervalHours;
  private final List<Integer> members;
  private final List<Double> p50;
  private final List<Double> mean;
  private final List<Double> p10;
  private final List<Double> p25;
  private final List<Double> p75;
  private final List<Double> p90;

  private SnowpackPlumeDepth(Builder b) {
    this.startTime = require(b.startTime, "start_time");
    this.intervalHours = require(b.intervalHours, "interval_hours");
    this.members = List.copyOf(b.members);
    this.p50 = List.copyOf(b.p50);
    this.mean = List.copyOf(b.mean);
    this.p10 = List.copyOf(b.p10);
    this.p25 = List.copyOf(b.p25);
    this.p75 = List.copyOf(b.p75);
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
    b.members = this.members;
    b.p50 = this.p50;
    b.mean = this.mean;
    b.p10 = this.p10;
    b.p25 = this.p25;
    b.p75 = this.p75;
    b.p90 = this.p90;
    return b;
  }

  /**
   * The time of the first value of every series in the body.
   *
   * @return the {@code start_time} value
   */
  public Instant startTime() {
    return startTime;
  }

  /**
   * Hours between values (3).
   *
   * @return the {@code interval_hours} value
   */
  public int intervalHours() {
    return intervalHours;
  }

  /**
   * Members that reach each step; it falls where a model's horizon ends.
   *
   * @return the {@code members} values; empty when the response has none
   */
  public List<Integer> members() {
    return members;
  }

  /**
   * The members' median depth, in the response's units. Plus and Pro ({@code plume_detail}); ABSENT
   * for Free and anonymous callers.
   *
   * @return the {@code p50} values; empty when the response has none
   */
  public List<Double> p50() {
    return p50;
  }

  /**
   * Served to every caller, as the bands are. The equal-weight mean depth of the members that reach
   * each step ({@code members}), in the response's units (cm, or inches with {@code
   * units=imperial}) at the depths' precision; one value per step from {@code start_time} every
   * {@code interval_hours}, the length of {@code p50}. Never null. It steps where a model's horizon
   * ends and its members leave the mean, as the bands do.
   *
   * @return the {@code mean} values; empty when the response has none
   */
  public List<Double> mean() {
    return mean;
  }

  /**
   * Every caller (Plus and Pro only before). A tenth of the members are at or below it.
   *
   * @return the {@code p10} values; empty when the response has none
   */
  public List<Double> p10() {
    return p10;
  }

  /**
   * Every caller (Plus and Pro only before).
   *
   * @return the {@code p25} values; empty when the response has none
   */
  public List<Double> p25() {
    return p25;
  }

  /**
   * Every caller (Plus and Pro only before).
   *
   * @return the {@code p75} values; empty when the response has none
   */
  public List<Double> p75() {
    return p75;
  }

  /**
   * Every caller (Plus and Pro only before). A tenth of the members are at or above it.
   *
   * @return the {@code p90} values; empty when the response has none
   */
  public List<Double> p90() {
    return p90;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {startTime, intervalHours, members, p50, mean, p10, p25, p75, p90};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "startTime", "intervalHours", "members", "p50", "mean", "p10", "p25", "p75", "p90"
  };

  /** Builds a {@link SnowpackPlumeDepth}. */
  public static final class Builder {
    private @Nullable Instant startTime;
    private @Nullable Integer intervalHours;
    private List<Integer> members = List.of();
    private List<Double> p50 = List.of();
    private List<Double> mean = List.of();
    private List<Double> p10 = List.of();
    private List<Double> p25 = List.of();
    private List<Double> p75 = List.of();
    private List<Double> p90 = List.of();

    private Builder() {}

    /**
     * Sets {@link SnowpackPlumeDepth#startTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder startTime(Instant value) {
      this.startTime = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#intervalHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder intervalHours(int value) {
      this.intervalHours = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#members()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder members(List<Integer> values) {
      this.members = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#p50()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p50(List<Double> values) {
      this.p50 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#mean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder mean(List<Double> values) {
      this.mean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#p10()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p10(List<Double> values) {
      this.p10 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#p25()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p25(List<Double> values) {
      this.p25 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#p75()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p75(List<Double> values) {
      this.p75 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeDepth#p90()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p90(List<Double> values) {
      this.p90 = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link SnowpackPlumeDepth}.
     *
     * @return the immutable SnowpackPlumeDepth
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackPlumeDepth build() {
      return new SnowpackPlumeDepth(this);
    }
  }
}
