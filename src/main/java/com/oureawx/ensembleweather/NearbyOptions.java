// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.model.UnitSystem;
import org.jspecify.annotations.Nullable;

/**
 * Options for nearby locations ({@link EnsembleWeatherClient#getNearby(LocationId,
 * NearbyOptions)}).
 */
public final class NearbyOptions {
  private static final NearbyOptions DEFAULTS = builder().build();

  private final @Nullable UnitSystem units;
  private final @Nullable Integer limit;
  private final @Nullable Double radiusKm;

  private NearbyOptions(Builder b) {
    units = b.units;
    limit = b.limit;
    radiusKm = b.radiusKm;
  }

  /**
   * The options with nothing set.
   *
   * @return the default options
   */
  public static NearbyOptions defaults() {
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
    q.string("units", u.wireValue().orElseThrow(), "metric")
        .integer("limit", limit == null ? null : limit.longValue(), 3L)
        .number("radius_km", radiusKm, 50.0);
  }

  /** Builds {@link NearbyOptions}. */
  public static final class Builder {
    private @Nullable UnitSystem units;
    private @Nullable Integer limit;
    private @Nullable Double radiusKm;

    private Builder() {}

    /**
     * The unit system of the rows' elevations ({@code units}); the client's default otherwise.
     *
     * @param units metric, imperial or kelvin
     * @return this builder
     */
    public Builder units(UnitSystem units) {
      if (units == UnitSystem.UNKNOWN) {
        throw new UsageException("units must be METRIC, IMPERIAL or KELVIN");
      }
      this.units = units;
      return this;
    }

    /**
     * Neighbours wanted ({@code limit}, default 3, clamped to 1..20 by the server).
     *
     * @param limit the count
     * @return this builder
     */
    public Builder limit(int limit) {
      this.limit = limit;
      return this;
    }

    /**
     * Search radius in km ({@code radius_km}, default 50, capped at 100 by the server).
     *
     * @param radiusKm the radius
     * @return this builder
     */
    public Builder radiusKm(double radiusKm) {
      if (!Double.isFinite(radiusKm)) {
        throw new UsageException("radius_km must be a finite number");
      }
      this.radiusKm = radiusKm;
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the immutable options
     */
    public NearbyOptions build() {
      return new NearbyOptions(this);
    }
  }
}
