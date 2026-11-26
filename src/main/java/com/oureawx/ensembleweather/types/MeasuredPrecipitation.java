// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Present only on a day part of which is a measurement -- the leading date, when the radar (MRMS
 * radar-only) measured hours between its local midnight and the reference init -- and absent
 * otherwise. Always absent on a pinned {@code run}: a pinned row counts nothing from before the
 * run's init, so its {@code total_precipitation} and {@code total_snowfall} are its served hours'.
 * {@code amount} is that measured precipitation, in the response's precipitation unit (as {@code
 * precipitation_display.amount}); {@code through} is the end (UTC) of its last measured hour. The
 * day's {@code total_precipitation} and {@code total_snowfall} include it with its measurement
 * error: its band is no longer only the forecast hours' spread, because a radar amount is itself
 * uncertain (against MRMS MultiSensor QPE, the scoring truth, a radar-only day sum runs from about
 * 0.7x to 1.2x at 15 mm or more, wider when lighter). The day's {@code mean} does not count {@code
 * amount} in full, so a first day's {@code total_precipitation.mean} can be below {@code
 * measured_precipitation.amount}. {@code amount} is the radar's own sum; the total counts it at the
 * measured ratio of truth to radar for an amount of its size, about 0.87 to 0.93: 0.914 of it up to
 * 1.9 mm, 0.868 at 9.0 mm, 0.925 from 22.1 mm (the radar amount in mm, whatever the response unit).
 * Between those amounts the water counted rises in a straight line with the amount measured, so the
 * ratio has no step and more measured water never counts for less. Example: 11.33 mm measured
 * counts as 10.06 mm (0.888), and with 0.02 mm in the hours still forecast the day's {@code mean}
 * is 10.08 mm. Each percentile takes the ratio's own quantile the same way.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class MeasuredPrecipitation extends Model {
  private final double amount;
  private final Instant through;

  private MeasuredPrecipitation(Builder b) {
    this.amount = require(b.amount, "amount");
    this.through = require(b.through, "through");
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
    b.amount = this.amount;
    b.through = this.through;
    return b;
  }

  /**
   * The {@code amount} value.
   *
   * @return the {@code amount} value
   */
  public double amount() {
    return amount;
  }

  /**
   * The {@code through} value.
   *
   * @return the {@code through} value
   */
  public Instant through() {
    return through;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {amount, through};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"amount", "through"};

  /** Builds a {@link MeasuredPrecipitation}. */
  public static final class Builder {
    private @Nullable Double amount;
    private @Nullable Instant through;

    private Builder() {}

    /**
     * Sets {@link MeasuredPrecipitation#amount()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder amount(double value) {
      this.amount = value;
      return this;
    }

    /**
     * Sets {@link MeasuredPrecipitation#through()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder through(Instant value) {
      this.through = value;
      return this;
    }

    /**
     * Builds the {@link MeasuredPrecipitation}.
     *
     * @return the immutable MeasuredPrecipitation
     * @throws IllegalStateException if a required value was not set
     */
    public MeasuredPrecipitation build() {
      return new MeasuredPrecipitation(this);
    }
  }
}
