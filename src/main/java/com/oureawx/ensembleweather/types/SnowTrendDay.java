// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A SnowTrendDay from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowTrendDay}.
 */
public final class SnowTrendDay extends Model {
  private final LocalDate date;
  private final @Nullable Double amount;
  private final boolean partial;
  private final @Nullable Double p10;
  private final @Nullable Double p90;
  private final @Nullable String kind;

  private SnowTrendDay(Builder b) {
    this.date = require(b.date, "date");
    this.amount = b.amount;
    this.partial = require(b.partial, "partial");
    this.p10 = b.p10;
    this.p90 = b.p90;
    this.kind = b.kind;
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
    b.date = this.date;
    b.amount = this.amount;
    b.partial = this.partial;
    b.p10 = this.p10;
    b.p90 = this.p90;
    b.kind = this.kind;
    return b;
  }

  /**
   * The {@code dates} entry this day is for.
   *
   * @return the {@code date} value
   */
  public LocalDate date() {
    return date;
  }

  /**
   * (JSON {@code snow}) The day's snowfall in mm (metric) or inches (imperial), at the served
   * amount precision. {@code ourea}: the day card's {@code precipitation_display.snow_amount}, the
   * day's mean snowfall, {@code null} where the card has none. A model: the median of its members'
   * day totals; {@code null} only where no member has a value.
   *
   * @return the {@code snow} value, or empty when the response omits it
   */
  public OptionalDouble amount() {
    return amount == null ? OptionalDouble.empty() : OptionalDouble.of(amount);
  }

  /**
   * The run covers only part of this local day.
   *
   * @return the {@code partial} value
   */
  public boolean partial() {
    return partial;
  }

  /**
   * The range's low end. {@code ourea}: {@code total_snowfall.p10}, the day's own 10th percentile
   * (not given a wet day), present exactly when {@code snow} is. A model: its members' 10th
   * percentile, absent with fewer than two members.
   *
   * @return the {@code p10} value, or empty when the response omits it
   */
  public OptionalDouble p10() {
    return p10 == null ? OptionalDouble.empty() : OptionalDouble.of(p10);
  }

  /**
   * The range's high end, as {@code p10}.
   *
   * @return the {@code p90} value, or empty when the response omits it
   */
  public OptionalDouble p90() {
    return p90 == null ? OptionalDouble.empty() : OptionalDouble.of(p90);
  }

  /**
   * {@code ourea} only: the day's {@code precipitation_display.kind}. One of {@code none}, {@code
   * rain}, {@code snow}, {@code rain_snow_mix}, {@code freezing_rain}; read it as a string, since
   * the API may add values.
   *
   * @return the {@code kind} value, or empty when the response omits it
   */
  public Optional<String> kind() {
    return Optional.ofNullable(kind);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {date, amount, partial, p10, p90, kind};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"date", "amount", "partial", "p10", "p90", "kind"};

  /** Builds a {@link SnowTrendDay}. */
  public static final class Builder {
    private @Nullable LocalDate date;
    private @Nullable Double amount;
    private @Nullable Boolean partial;
    private @Nullable Double p10;
    private @Nullable Double p90;
    private @Nullable String kind;

    private Builder() {}

    /**
     * Sets {@link SnowTrendDay#date()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder date(LocalDate value) {
      this.date = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendDay#amount()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder amount(@Nullable Double value) {
      this.amount = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendDay#partial()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder partial(boolean value) {
      this.partial = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendDay#p10()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p10(@Nullable Double value) {
      this.p10 = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendDay#p90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p90(@Nullable Double value) {
      this.p90 = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendDay#kind()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder kind(@Nullable String value) {
      this.kind = value;
      return this;
    }

    /**
     * Builds the {@link SnowTrendDay}.
     *
     * @return the immutable SnowTrendDay
     * @throws IllegalStateException if a required value was not set
     */
    public SnowTrendDay build() {
      return new SnowTrendDay(this);
    }
  }
}
