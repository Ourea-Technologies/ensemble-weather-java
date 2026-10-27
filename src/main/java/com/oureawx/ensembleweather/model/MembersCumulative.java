// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The distribution of the members' running totals at this hour -- for each quantity, percentiles
 * over every member's {@code cumulative_precipitation} / {@code cumulative_snowfall} here. Computed
 * from the cumulative distribution, NEVER by summing hourly percentiles (quantiles are not
 * additive: the sum of hourly p90s is far above the p90 of the total). Present at an hour where at
 * least two members carry a running total -- in practice the 6-hourly synoptic hours
 * (00/06/12/18Z), since between them only RRFS's single member has a value; absent at the other
 * hours. A quantity fewer than two members carry is omitted. The members are equally weighted: this
 * is the member spread, not the Ourea forecast -- the headline total is the served {@code
 * snow_outlook} (the calibrated, skill-weighted blend's mean; a served running headline total is to
 * come). Clients draw these served values and never compute percentiles or totals themselves.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code MembersCumulative}.
 */
public final class MembersCumulative extends Model {
  private final @Nullable Quantiles precipitation;
  private final @Nullable Quantiles snowfall;

  private MembersCumulative(Builder b) {
    this.precipitation = b.precipitation;
    this.snowfall = b.snowfall;
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
    b.precipitation = this.precipitation;
    b.snowfall = this.snowfall;
    return b;
  }

  /**
   * The {@code precipitation} value.
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public Optional<Quantiles> precipitation() {
    return Optional.ofNullable(precipitation);
  }

  /**
   * The {@code snowfall} value.
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowfall() {
    return Optional.ofNullable(snowfall);
  }

  @Override
  Object[] values() {
    return new Object[] {precipitation, snowfall};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"precipitation", "snowfall"};

  /** Builds a {@link MembersCumulative}. */
  public static final class Builder {
    private @Nullable Quantiles precipitation;
    private @Nullable Quantiles snowfall;

    private Builder() {}

    /**
     * Sets {@link MembersCumulative#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable Quantiles value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link MembersCumulative#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable Quantiles value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Builds the {@link MembersCumulative}.
     *
     * @return the immutable MembersCumulative
     * @throws IllegalStateException if a required value was not set
     */
    public MembersCumulative build() {
      return new MembersCumulative(this);
    }
  }
}
