// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.types.UnitSystem;
import org.jspecify.annotations.Nullable;

/**
 * Options for a location's observed season to date ({@link Client#getSeason(LocationId,
 * SeasonOptions)}).
 */
public final class SeasonOptions {
  private static final SeasonOptions DEFAULTS = builder().build();

  private final @Nullable Integer waterYear;
  private final boolean daily;
  private final @Nullable UnitSystem units;

  private SeasonOptions(Builder b) {
    waterYear = b.waterYear;
    daily = b.daily;
    units = b.units;
  }

  /**
   * The options with nothing set: the current water year, totals only.
   *
   * @return the default options
   */
  public static SeasonOptions defaults() {
    return DEFAULTS;
  }

  /**
   * Returns a new builder.
   *
   * @return an empty builder
   */
  public static Builder builder() {
    return new Builder();
  }

  void addTo(Query q, UnitSystem clientUnits) {
    UnitSystem u = units == null ? clientUnits : units;
    q.integer("water_year", waterYear == null ? null : waterYear.longValue(), null)
        .string("units", u.wireValue().orElseThrow(), "metric");
    if (daily) {
      q.csv("include", java.util.List.of("daily"));
    }
  }

  /** Builds {@link SeasonOptions}. */
  public static final class Builder {
    private @Nullable Integer waterYear;
    private boolean daily;
    private @Nullable UnitSystem units;

    private Builder() {}

    /**
     * The water year ({@code water_year}: October 1 of the year before to September 30); the
     * current one otherwise.
     *
     * @param waterYear the year it ends in, 2027 or later
     * @return this builder
     */
    public Builder waterYear(int waterYear) {
      this.waterYear = waterYear;
      return this;
    }

    /**
     * Include the day-by-day record ({@code include=daily}).
     *
     * @param daily whether to include it
     * @return this builder
     */
    public Builder daily(boolean daily) {
      this.daily = daily;
      return this;
    }

    /**
     * The unit system of the totals ({@code units}); the client's default otherwise.
     *
     * @param units metric, imperial or kelvin
     * @return this builder
     */
    public Builder units(UnitSystem units) {
      this.units = Checks.units(units);
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the immutable options
     */
    public SeasonOptions build() {
      return new SeasonOptions(this);
    }
  }
}
