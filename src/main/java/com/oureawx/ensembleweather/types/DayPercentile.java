// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * How unusual the day is: where the served headline ({@code p50}) falls in the day's climatological
 * distribution, as a percentile 0-100, <code>Pr&#123;climate &lt;= forecast&#125;</code> -- 95 for
 * {@code precipitation} reads "wetter than 95% of this date's days". {@code high} and {@code low}
 * read the normal's Gaussian (mean and standard deviation of the calendar-day maximum / minimum);
 * {@code precipitation} reads its zero-inflated gamma (a dry atom plus a gamma fitted to the wet
 * days), so a dry forecast sits AT the atom and reads the climatological share of dry days. A
 * member is absent where its normal is, or where that normal has no distribution to place a value
 * in (no fitted mixture, zero spread). Absent as {@code departure} is.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code DayPercentile}.
 */
public final class DayPercentile extends Model {
  private final @Nullable Double high;
  private final @Nullable Double low;
  private final @Nullable Double precipitation;

  private DayPercentile(Builder b) {
    this.high = b.high;
    this.low = b.low;
    this.precipitation = b.precipitation;
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
    b.high = this.high;
    b.low = this.low;
    b.precipitation = this.precipitation;
    return b;
  }

  /**
   * The {@code high} value.
   *
   * @return the {@code high} value, or empty when the response omits it
   */
  public OptionalDouble high() {
    return high == null ? OptionalDouble.empty() : OptionalDouble.of(high);
  }

  /**
   * The {@code low} value.
   *
   * @return the {@code low} value, or empty when the response omits it
   */
  public OptionalDouble low() {
    return low == null ? OptionalDouble.empty() : OptionalDouble.of(low);
  }

  /**
   * The {@code precipitation} value.
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public OptionalDouble precipitation() {
    return precipitation == null ? OptionalDouble.empty() : OptionalDouble.of(precipitation);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {high, low, precipitation};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"high", "low", "precipitation"};

  /** Builds a {@link DayPercentile}. */
  public static final class Builder {
    private @Nullable Double high;
    private @Nullable Double low;
    private @Nullable Double precipitation;

    private Builder() {}

    /**
     * Sets {@link DayPercentile#high()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder high(@Nullable Double value) {
      this.high = value;
      return this;
    }

    /**
     * Sets {@link DayPercentile#low()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder low(@Nullable Double value) {
      this.low = value;
      return this;
    }

    /**
     * Sets {@link DayPercentile#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable Double value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Builds the {@link DayPercentile}.
     *
     * @return the immutable DayPercentile
     * @throws IllegalStateException if a required value was not set
     */
    public DayPercentile build() {
      return new DayPercentile(this);
    }
  }
}
