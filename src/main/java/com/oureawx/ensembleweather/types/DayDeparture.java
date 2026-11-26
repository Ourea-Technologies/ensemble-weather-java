// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * The served headline minus the normal, in the request's units: {@code high.p50 - normal.high} and
 * {@code low.p50 - normal.low}. Positive is warmer than normal. Present only with {@code normal},
 * and absent on a date the forecast covers only from partway through (the first date of a run that
 * starts after local midnight), whose high and low are over part of the day while the normal is
 * over all of it.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code DayDeparture}.
 */
public final class DayDeparture extends Model {
  private final @Nullable Double high;
  private final @Nullable Double low;

  private DayDeparture(Builder b) {
    this.high = b.high;
    this.low = b.low;
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

  @Override
  Object[] fieldValues() {
    return new Object[] {high, low};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"high", "low"};

  /** Builds a {@link DayDeparture}. */
  public static final class Builder {
    private @Nullable Double high;
    private @Nullable Double low;

    private Builder() {}

    /**
     * Sets {@link DayDeparture#high()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder high(@Nullable Double value) {
      this.high = value;
      return this;
    }

    /**
     * Sets {@link DayDeparture#low()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder low(@Nullable Double value) {
      this.low = value;
      return this;
    }

    /**
     * Builds the {@link DayDeparture}.
     *
     * @return the immutable DayDeparture
     * @throws IllegalStateException if a required value was not set
     */
    public DayDeparture build() {
      return new DayDeparture(this);
    }
  }
}
