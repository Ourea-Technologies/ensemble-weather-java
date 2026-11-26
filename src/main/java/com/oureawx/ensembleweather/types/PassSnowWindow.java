// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A PassSnowWindow from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassSnowWindow}.
 */
public final class PassSnowWindow extends Model {
  private final Instant start;
  private final Instant end;
  private final double total;
  private final @Nullable Double p10;
  private final @Nullable Double p90;
  private final @Nullable Double pGe1in;

  private PassSnowWindow(Builder b) {
    this.start = require(b.start, "start");
    this.end = require(b.end, "end");
    this.total = require(b.total, "total");
    this.p10 = b.p10;
    this.p90 = b.p90;
    this.pGe1in = b.pGe1in;
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
    b.start = this.start;
    b.end = this.end;
    b.total = this.total;
    b.p10 = this.p10;
    b.p90 = this.p90;
    b.pGe1in = this.pGe1in;
    return b;
  }

  /**
   * When the first snowing hour begins (its {@code valid_time} minus 1 h).
   *
   * @return the {@code start} value
   */
  public Instant start() {
    return start;
  }

  /**
   * When the last snowing hour ends (its {@code valid_time}).
   *
   * @return the {@code end} value
   */
  public Instant end() {
    return end;
  }

  /**
   * The sum of the hourly snowfall means over the spell (mm, in), as {@code snow_outlook} sums its
   * windows.
   *
   * @return the {@code total} value
   */
  public double total() {
    return total;
  }

  /**
   * The p10 of the members' own totals over the spell ({@code ?totals=} machinery); omitted without
   * enough members.
   *
   * @return the {@code p10} value, or empty when the response omits it
   */
  public OptionalDouble p10() {
    return p10 == null ? OptionalDouble.empty() : OptionalDouble.of(p10);
  }

  /**
   * The p90 of the members' own totals over the spell.
   *
   * @return the {@code p90} value, or empty when the response omits it
   */
  public OptionalDouble p90() {
    return p90 == null ? OptionalDouble.empty() : OptionalDouble.of(p90);
  }

  /**
   * The share of members whose own total over the spell is 1 in (25.4 mm) or more; threshold fixed,
   * does not change with units.
   *
   * @return the {@code p_ge_1in} value, or empty when the response omits it
   */
  public OptionalDouble pGe1in() {
    return pGe1in == null ? OptionalDouble.empty() : OptionalDouble.of(pGe1in);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {start, end, total, p10, p90, pGe1in};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"start", "end", "total", "p10", "p90", "pGe1in"};

  /** Builds a {@link PassSnowWindow}. */
  public static final class Builder {
    private @Nullable Instant start;
    private @Nullable Instant end;
    private @Nullable Double total;
    private @Nullable Double p10;
    private @Nullable Double p90;
    private @Nullable Double pGe1in;

    private Builder() {}

    /**
     * Sets {@link PassSnowWindow#start()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder start(Instant value) {
      this.start = value;
      return this;
    }

    /**
     * Sets {@link PassSnowWindow#end()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder end(Instant value) {
      this.end = value;
      return this;
    }

    /**
     * Sets {@link PassSnowWindow#total()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder total(double value) {
      this.total = value;
      return this;
    }

    /**
     * Sets {@link PassSnowWindow#p10()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p10(@Nullable Double value) {
      this.p10 = value;
      return this;
    }

    /**
     * Sets {@link PassSnowWindow#p90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p90(@Nullable Double value) {
      this.p90 = value;
      return this;
    }

    /**
     * Sets {@link PassSnowWindow#pGe1in()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pGe1in(@Nullable Double value) {
      this.pGe1in = value;
      return this;
    }

    /**
     * Builds the {@link PassSnowWindow}.
     *
     * @return the immutable PassSnowWindow
     * @throws IllegalStateException if a required value was not set
     */
    public PassSnowWindow build() {
      return new PassSnowWindow(this);
    }
  }
}
