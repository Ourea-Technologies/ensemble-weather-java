// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A distribution: the one shape of every ensemble statistic the API serves (each hour's {@code
 * ensemble.*} but {@code wind_direction}, the daily highs, lows and totals, window totals,
 * snow-outlook windows), in the request's units. Percentiles are over the ensemble members.
 *
 * <p>The API sends every field but {@code probability}; the SDK still treats each as optional, and
 * a missing one is absent, never 0. {@link #range(Percentile, Percentile)} offers the served pairs
 * (p10 to p90, p25 to p75) and {@link #median()} is p50; the SDK never interpolates or sums
 * quantiles. Wind direction is circular and is a {@link CircularStats}, not a {@code Quantiles}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks). Wire schema: {@code
 * Quantiles}.
 */
public final class Quantiles extends Model {
  private final @Nullable Double mean;
  private final @Nullable Double p10;
  private final @Nullable Double p25;
  private final @Nullable Double p50;
  private final @Nullable Double p75;
  private final @Nullable Double p90;
  private final @Nullable Double min;
  private final @Nullable Double max;
  private final @Nullable Double probability;

  private Quantiles(Builder b) {
    this.mean = b.mean;
    this.p10 = b.p10;
    this.p25 = b.p25;
    this.p50 = b.p50;
    this.p75 = b.p75;
    this.p90 = b.p90;
    this.min = b.min;
    this.max = b.max;
    this.probability = b.probability;
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
    b.mean = this.mean;
    b.p10 = this.p10;
    b.p25 = this.p25;
    b.p50 = this.p50;
    b.p75 = this.p75;
    b.p90 = this.p90;
    b.min = this.min;
    b.max = this.max;
    b.probability = this.probability;
    return b;
  }

  /**
   * Ensemble mean. For right-skewed, zero-inflated variables (precipitation, snowfall) the mean is
   * inflated by upper-tail members and over-predicts at a point -- it is the verification/scorecard
   * number, NOT the headline. Prefer {@code p50} for the displayed amount.
   *
   * @return the {@code mean} value, or empty when the response omits it
   */
  public OptionalDouble mean() {
    return mean == null ? OptionalDouble.empty() : OptionalDouble.of(mean);
  }

  /**
   * 10th percentile (realistic lower bound for visualization).
   *
   * @return the {@code p10} value, or empty when the response omits it
   */
  public OptionalDouble p10() {
    return p10 == null ? OptionalDouble.empty() : OptionalDouble.of(p10);
  }

  /**
   * The {@code p25} value.
   *
   * @return the {@code p25} value, or empty when the response omits it
   */
  public OptionalDouble p25() {
    return p25 == null ? OptionalDouble.empty() : OptionalDouble.of(p25);
  }

  /**
   * Ensemble median -- the headline-appropriate amount for skewed precipitation (half the members
   * above, half below). Pair with {@code probability} for occurrence, since the median can be 0 on
   * scattered-shower days while precip is still likely.
   *
   * @return the {@code p50} value, or empty when the response omits it
   */
  public OptionalDouble p50() {
    return p50 == null ? OptionalDouble.empty() : OptionalDouble.of(p50);
  }

  /**
   * The {@code p75} value.
   *
   * @return the {@code p75} value, or empty when the response omits it
   */
  public OptionalDouble p75() {
    return p75 == null ? OptionalDouble.empty() : OptionalDouble.of(p75);
  }

  /**
   * 90th percentile (realistic upper bound for visualization).
   *
   * @return the {@code p90} value, or empty when the response omits it
   */
  public OptionalDouble p90() {
    return p90 == null ? OptionalDouble.empty() : OptionalDouble.of(p90);
  }

  /**
   * P0 (ensemble minimum -- absolute extreme, often an outlier).
   *
   * @return the {@code min} value, or empty when the response omits it
   */
  public OptionalDouble min() {
    return min == null ? OptionalDouble.empty() : OptionalDouble.of(min);
  }

  /**
   * P100 (ensemble maximum -- absolute extreme, often an outlier).
   *
   * @return the {@code max} value, or empty when the response omits it
   */
  public OptionalDouble max() {
    return max == null ? OptionalDouble.empty() : OptionalDouble.of(max);
  }

  /**
   * Only on the hourly {@code precipitation} and {@code snowfall}: the probability of occurrence,
   * P(amount &gt;= 0.254 mm) -- the NWS measurable event, 0.01 in ({@code thresholds} names it) --
   * with the rain-icon false-alarm gate applied so it agrees with the hour's {@code
   * condition}/icon. Omitted everywhere else.
   *
   * @return the {@code probability} value, or empty when the response omits it
   */
  public OptionalDouble probability() {
    return probability == null ? OptionalDouble.empty() : OptionalDouble.of(probability);
  }

  /**
   * The median (p50). It is the median of the members, not "the forecast": read the whole
   * distribution where it matters.
   *
   * @return p50, or empty when the response omits it
   */
  public OptionalDouble median() {
    return p50();
  }

  /**
   * A range between two served percentiles: p10 to p90, or p25 to p75. There is no interpolation,
   * so any other pair is refused.
   *
   * @param low {@link Percentile#P10} or {@link Percentile#P25}
   * @param high {@link Percentile#P90} or {@link Percentile#P75}
   * @return the range, or empty when the response omits either end
   * @throws com.oureawx.ensembleweather.UsageException for a pair the server does not serve
   */
  public Optional<QuantileRange> range(Percentile low, Percentile high) {
    if (!((low == Percentile.P10 && high == Percentile.P90)
        || (low == Percentile.P25 && high == Percentile.P75))) {
      throw new com.oureawx.ensembleweather.UsageException(
          "the served ranges are P10..P90 and P25..P75, not " + low + ".." + high);
    }
    Double lo = low == Percentile.P10 ? p10 : p25;
    Double hi = high == Percentile.P90 ? p90 : p75;
    return lo == null || hi == null
        ? Optional.empty()
        : Optional.of(QuantileRange.of(low, high, lo, hi));
  }

  @Override
  Object[] values() {
    return new Object[] {mean, p10, p25, p50, p75, p90, min, max, probability};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "mean", "p10", "p25", "p50", "p75", "p90", "min", "max", "probability"
  };

  /** Builds a {@link Quantiles}. */
  public static final class Builder {
    private @Nullable Double mean;
    private @Nullable Double p10;
    private @Nullable Double p25;
    private @Nullable Double p50;
    private @Nullable Double p75;
    private @Nullable Double p90;
    private @Nullable Double min;
    private @Nullable Double max;
    private @Nullable Double probability;

    private Builder() {}

    /**
     * Sets {@link Quantiles#mean()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder mean(@Nullable Double value) {
      this.mean = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#p10()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p10(@Nullable Double value) {
      this.p10 = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#p25()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p25(@Nullable Double value) {
      this.p25 = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#p50()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p50(@Nullable Double value) {
      this.p50 = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#p75()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p75(@Nullable Double value) {
      this.p75 = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#p90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p90(@Nullable Double value) {
      this.p90 = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#min()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder min(@Nullable Double value) {
      this.min = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#max()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder max(@Nullable Double value) {
      this.max = value;
      return this;
    }

    /**
     * Sets {@link Quantiles#probability()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder probability(@Nullable Double value) {
      this.probability = value;
      return this;
    }

    /**
     * Builds the {@link Quantiles}.
     *
     * @return the immutable Quantiles
     * @throws IllegalStateException if a required value was not set
     */
    public Quantiles build() {
      return new Quantiles(this);
    }
  }
}
