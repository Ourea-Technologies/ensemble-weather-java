// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The RANGE of each {@code snow_outlook} window's snowfall total, on the default response (no
 * {@code members} needed) and on every {@code elevation_forecasts[].snow_outlook}. The percentiles
 * are those of every member's OWN total over the window -- the same hours the headline sums (valid
 * times in [first served hour, + N h), each covering the hour ending at it; a 6-hourly bucket
 * straddling an end counts its share inside) -- never a sum of hourly percentiles (quantiles are
 * not additive). Members are pooled across models, one vote each, and a member counts only when its
 * values cover the WHOLE window (a model whose run ends inside it is left out). {@code mean} is the
 * window's headline total ({@code next_24h}...). Show it as "most likely {@code p50}, at least
 * {@code p10}, could reach {@code p90}". A window is null when fewer than two members cover it; the
 * object is omitted when every window is null.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowOutlookQuantiles}.
 */
public final class SnowOutlookQuantiles extends Model {
  private final @Nullable Quantiles next24h;
  private final @Nullable Quantiles next48h;
  private final @Nullable Quantiles next7d;

  private SnowOutlookQuantiles(Builder b) {
    this.next24h = b.next24h;
    this.next48h = b.next48h;
    this.next7d = b.next7d;
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
    return b;
  }

  /**
   * The {@code next_24h} value.
   *
   * @return the {@code next_24h} value, or empty when the response omits it
   */
  public Optional<Quantiles> next24h() {
    return Optional.ofNullable(next24h);
  }

  /**
   * The {@code next_48h} value.
   *
   * @return the {@code next_48h} value, or empty when the response omits it
   */
  public Optional<Quantiles> next48h() {
    return Optional.ofNullable(next48h);
  }

  /**
   * The {@code next_7d} value.
   *
   * @return the {@code next_7d} value, or empty when the response omits it
   */
  public Optional<Quantiles> next7d() {
    return Optional.ofNullable(next7d);
  }

  @Override
  Object[] values() {
    return new Object[] {next24h, next48h, next7d};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"next24h", "next48h", "next7d"};

  /** Builds a {@link SnowOutlookQuantiles}. */
  public static final class Builder {
    private @Nullable Quantiles next24h;
    private @Nullable Quantiles next48h;
    private @Nullable Quantiles next7d;

    private Builder() {}

    /**
     * Sets {@link SnowOutlookQuantiles#next24h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next24h(@Nullable Quantiles value) {
      this.next24h = value;
      return this;
    }

    /**
     * Sets {@link SnowOutlookQuantiles#next48h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next48h(@Nullable Quantiles value) {
      this.next48h = value;
      return this;
    }

    /**
     * Sets {@link SnowOutlookQuantiles#next7d()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder next7d(@Nullable Quantiles value) {
      this.next7d = value;
      return this;
    }

    /**
     * Builds the {@link SnowOutlookQuantiles}.
     *
     * @return the immutable SnowOutlookQuantiles
     * @throws IllegalStateException if a required value was not set
     */
    public SnowOutlookQuantiles build() {
      return new SnowOutlookQuantiles(this);
    }
  }
}
