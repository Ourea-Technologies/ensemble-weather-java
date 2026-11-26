// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * Rolling liquid-equivalent totals over the same windows as {@code snow_outlook}, for places where
 * rain is the story (the favorites cards' "0.6 in rain" with its range). Values follow {@code
 * units} (mm metric, inches imperial). Each total is the sum of the hourly precipitation means over
 * the window (means add across hours), read from the forecast whatever {@code max_hours} is; a
 * window the forecast does not reach is {@code null}. {@code quantiles} holds each window's range:
 * the percentiles of every member's own precipitation total over the same hours, and those members'
 * own mean -- one distribution, never a sum of hourly percentiles. The members carry the calibrated
 * water (ECC through the headline's fit), so that mean is close to the total but not equal to it;
 * show the range with the total.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PrecipOutlook}.
 */
public final class PrecipOutlook extends Model {
  private final @Nullable Double next24h;
  private final @Nullable Double next48h;
  private final @Nullable Double next72h;
  private final @Nullable Double next7d;
  private final @Nullable SnowOutlookQuantiles quantiles;

  private PrecipOutlook(Builder b) {
    this.next24h = b.next24h;
    this.next48h = b.next48h;
    this.next72h = b.next72h;
    this.next7d = b.next7d;
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
    b.next72h = this.next72h;
    b.next7d = this.next7d;
    b.quantiles = this.quantiles;
    return b;
  }

  /**
   * Sum of the hourly precipitation means over the 24 h from the first served hour; {@code null}
   * when not covered.
   *
   * @return the {@code next_24h} value, or empty when the response omits it
   */
  public OptionalDouble next24h() {
    return next24h == null ? OptionalDouble.empty() : OptionalDouble.of(next24h);
  }

  /**
   * The same over 48 h.
   *
   * @return the {@code next_48h} value, or empty when the response omits it
   */
  public OptionalDouble next48h() {
    return next48h == null ? OptionalDouble.empty() : OptionalDouble.of(next48h);
  }

  /**
   * The same over 72 h.
   *
   * @return the {@code next_72h} value, or empty when the response omits it
   */
  public OptionalDouble next72h() {
    return next72h == null ? OptionalDouble.empty() : OptionalDouble.of(next72h);
  }

  /**
   * The same over 168 h.
   *
   * @return the {@code next_7d} value, or empty when the response omits it
   */
  public OptionalDouble next7d() {
    return next7d == null ? OptionalDouble.empty() : OptionalDouble.of(next7d);
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
  Object[] fieldValues() {
    return new Object[] {next24h, next48h, next72h, next7d, quantiles};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"next24h", "next48h", "next72h", "next7d", "quantiles"};

  /** Builds a {@link PrecipOutlook}. */
  public static final class Builder {
    private @Nullable Double next24h;
    private @Nullable Double next48h;
    private @Nullable Double next72h;
    private @Nullable Double next7d;
    private @Nullable SnowOutlookQuantiles quantiles;

    private Builder() {}

    /**
     * Sets {@link PrecipOutlook#next24h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next24h(@Nullable Double value) {
      this.next24h = value;
      return this;
    }

    /**
     * Sets {@link PrecipOutlook#next48h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next48h(@Nullable Double value) {
      this.next48h = value;
      return this;
    }

    /**
     * Sets {@link PrecipOutlook#next72h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next72h(@Nullable Double value) {
      this.next72h = value;
      return this;
    }

    /**
     * Sets {@link PrecipOutlook#next7d()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next7d(@Nullable Double value) {
      this.next7d = value;
      return this;
    }

    /**
     * Sets {@link PrecipOutlook#quantiles()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder quantiles(@Nullable SnowOutlookQuantiles value) {
      this.quantiles = value;
      return this;
    }

    /**
     * Builds the {@link PrecipOutlook}.
     *
     * @return the immutable PrecipOutlook
     * @throws IllegalStateException if a required value was not set
     */
    public PrecipOutlook build() {
      return new PrecipOutlook(this);
    }
  }
}
