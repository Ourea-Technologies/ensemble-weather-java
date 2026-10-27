// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * Snow accumulation totals for common UI windows. Values follow the request's {@code units} param
 * (mm metric, inches imperial). Every total is the sum of the hourly snowfall MEANS over its window
 * (changed 2026-09-24 from summed medians): the mean is the only statistic that adds across hours,
 * and it is what every client sums, so {@code next_24h} equals the sum of {@code timeseries[0..24]}
 * snowfall means. Pair with {@code daily[].snow_probability} for occurrence. The windows open at
 * the first served hour and are read from the forecast whatever {@code max_hours} or {@code
 * max_days} is. A window the forecast does not reach (the model run ends before it closes) is
 * {@code null} -- never the shorter sum of the hours there are, which was served as the whole
 * window.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowOutlook}.
 */
public final class SnowOutlook extends Model {
  private final @Nullable Double next24h;
  private final @Nullable Double next48h;
  private final @Nullable Double next7d;
  private final @Nullable Double overnight4pm9am;
  private final @Nullable SnowOutlookQuantiles quantiles;

  private SnowOutlook(Builder b) {
    this.next24h = b.next24h;
    this.next48h = b.next48h;
    this.next7d = b.next7d;
    this.overnight4pm9am = b.overnight4pm9am;
    this.quantiles = b.quantiles;
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
    b.next24h = this.next24h;
    b.next48h = this.next48h;
    b.next7d = this.next7d;
    b.overnight4pm9am = this.overnight4pm9am;
    b.quantiles = this.quantiles;
    return b;
  }

  /**
   * Sum of the hourly snowfall means over the 24 h from the first served hour; {@code null} when
   * the forecast does not reach its end.
   *
   * @return the {@code next_24h} value, or empty when the response omits it
   */
  public OptionalDouble next24h() {
    return next24h == null ? OptionalDouble.empty() : OptionalDouble.of(next24h);
  }

  /**
   * Sum of the hourly snowfall means over the first 48 h; {@code null} when not covered.
   *
   * @return the {@code next_48h} value, or empty when the response omits it
   */
  public OptionalDouble next48h() {
    return next48h == null ? OptionalDouble.empty() : OptionalDouble.of(next48h);
  }

  /**
   * Sum of the hourly snowfall means over the first 168 h; {@code null} when not covered.
   *
   * @return the {@code next_7d} value, or empty when the response omits it
   */
  public OptionalDouble next7d() {
    return next7d == null ? OptionalDouble.empty() : OptionalDouble.of(next7d);
  }

  /**
   * Snow expected overnight, local 16:00 today → 09:00 tomorrow: the sum of the hourly snowfall
   * means in the window, read from the forecast whatever {@code max_hours} is. Null when the window
   * isn't covered by the model run, with {@code nowcast=false} (it is wall-clock-anchored), and
   * null when any hour inside the window was refused — a fixed window summed across a gap
   * under-counts by exactly the missing hours while still looking complete, so the headline is
   * withheld instead.
   *
   * @return the {@code overnight_4pm_9am} value, or empty when the response omits it
   */
  public OptionalDouble overnight4pm9am() {
    return overnight4pm9am == null ? OptionalDouble.empty() : OptionalDouble.of(overnight4pm9am);
  }

  /**
   * The {@code quantiles} value.
   *
   * @return the {@code quantiles} value, or empty when the response omits it
   */
  public Optional<SnowOutlookQuantiles> quantiles() {
    return Optional.ofNullable(quantiles);
  }

  @Override
  Object[] values() {
    return new Object[] {next24h, next48h, next7d, overnight4pm9am, quantiles};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "next24h", "next48h", "next7d", "overnight4pm9am", "quantiles"
  };

  /** Builds a {@link SnowOutlook}. */
  public static final class Builder {
    private @Nullable Double next24h;
    private @Nullable Double next48h;
    private @Nullable Double next7d;
    private @Nullable Double overnight4pm9am;
    private @Nullable SnowOutlookQuantiles quantiles;

    private Builder() {}

    /**
     * Sets {@link SnowOutlook#next24h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next24h(@Nullable Double value) {
      this.next24h = value;
      return this;
    }

    /**
     * Sets {@link SnowOutlook#next48h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next48h(@Nullable Double value) {
      this.next48h = value;
      return this;
    }

    /**
     * Sets {@link SnowOutlook#next7d()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next7d(@Nullable Double value) {
      this.next7d = value;
      return this;
    }

    /**
     * Sets {@link SnowOutlook#overnight4pm9am()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder overnight4pm9am(@Nullable Double value) {
      this.overnight4pm9am = value;
      return this;
    }

    /**
     * Sets {@link SnowOutlook#quantiles()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder quantiles(@Nullable SnowOutlookQuantiles value) {
      this.quantiles = value;
      return this;
    }

    /**
     * Builds the {@link SnowOutlook}.
     *
     * @return the immutable SnowOutlook
     * @throws IllegalStateException if a required value was not set
     */
    public SnowOutlook build() {
      return new SnowOutlook(this);
    }
  }
}
