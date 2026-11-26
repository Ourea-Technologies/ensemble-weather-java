// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.LocalDate;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A SeasonDay from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SeasonDay}.
 */
public final class SeasonDay extends Model {
  private final LocalDate date;
  private final @Nullable Double precipitation;
  private final @Nullable Double snowfall;
  private final boolean provisional;

  private SeasonDay(Builder b) {
    this.date = require(b.date, "date");
    this.precipitation = b.precipitation;
    this.snowfall = b.snowfall;
    this.provisional = require(b.provisional, "provisional");
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
    b.precipitation = this.precipitation;
    b.snowfall = this.snowfall;
    b.provisional = this.provisional;
    return b;
  }

  /**
   * The {@code date} value.
   *
   * @return the {@code date} value
   */
  public LocalDate date() {
    return date;
  }

  /**
   * The day's total; null when an hour was not observed.
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public OptionalDouble precipitation() {
    return precipitation == null ? OptionalDouble.empty() : OptionalDouble.of(precipitation);
  }

  /**
   * Always null for now.
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public OptionalDouble snowfall() {
    return snowfall == null ? OptionalDouble.empty() : OptionalDouble.of(snowfall);
  }

  /**
   * The {@code provisional} value.
   *
   * @return the {@code provisional} value
   */
  public boolean provisional() {
    return provisional;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {date, precipitation, snowfall, provisional};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"date", "precipitation", "snowfall", "provisional"};

  /** Builds a {@link SeasonDay}. */
  public static final class Builder {
    private @Nullable LocalDate date;
    private @Nullable Double precipitation;
    private @Nullable Double snowfall;
    private @Nullable Boolean provisional;

    private Builder() {}

    /**
     * Sets {@link SeasonDay#date()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder date(LocalDate value) {
      this.date = value;
      return this;
    }

    /**
     * Sets {@link SeasonDay#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable Double value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link SeasonDay#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable Double value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link SeasonDay#provisional()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder provisional(boolean value) {
      this.provisional = value;
      return this;
    }

    /**
     * Builds the {@link SeasonDay}.
     *
     * @return the immutable SeasonDay
     * @throws IllegalStateException if a required value was not set
     */
    public SeasonDay build() {
      return new SeasonDay(this);
    }
  }
}
