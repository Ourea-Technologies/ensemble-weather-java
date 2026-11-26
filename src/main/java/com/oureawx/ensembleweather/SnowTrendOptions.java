// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.types.UnitSystem;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Options for a location's multi-run snow trend ({@link Client#getSnowTrend(LocationId,
 * SnowTrendOptions)}).
 */
public final class SnowTrendOptions {
  private static final SnowTrendOptions DEFAULTS = builder().build();

  private final @Nullable String elevation;
  private final @Nullable UnitSystem units;

  private SnowTrendOptions(Builder b) {
    elevation = b.elevation;
    units = b.units;
  }

  /**
   * The options with nothing set: the location's default elevation.
   *
   * @return the default options
   */
  public static SnowTrendOptions defaults() {
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
    q.string("elevation", elevation).string("units", u.wireValue().orElseThrow(), "metric");
  }

  /** Builds {@link SnowTrendOptions}. */
  public static final class Builder {
    private @Nullable String elevation;
    private @Nullable UnitSystem units;

    private Builder() {}

    /**
     * The named elevation of the location ({@code elevation}, a {@code location.elevations[].name}
     * such as {@code main_lodge}); its default elevation otherwise.
     *
     * @param name the elevation's name
     * @return this builder
     */
    public Builder elevation(String name) {
      this.elevation = Objects.requireNonNull(name, "elevation");
      return this;
    }

    /**
     * The unit system of the amounts ({@code units}); the client's default otherwise.
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
    public SnowTrendOptions build() {
      return new SnowTrendOptions(this);
    }
  }
}
