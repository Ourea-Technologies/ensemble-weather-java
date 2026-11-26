// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.types.LocationType;
import com.oureawx.ensembleweather.types.UnitSystem;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Filters, order and paging for {@link
 * Client#explore(com.oureawx.ensembleweather.types.ExploreMetric, ExploreOptions)}, which ranks
 * places by a metric. Every option left unset, or set to the API's default ({@code order=desc},
 * {@code limit=25}, {@code points=primary}, {@code radius_km=100}), is left out of the request.
 *
 * <pre>{@code
 * ExploreOptions options = ExploreOptions.builder()
 *     .state("or")
 *     .type(LocationType.SKI_AREA)
 *     .units(UnitSystem.IMPERIAL)
 *     .build();
 * ExploreResponse top = client.explore(ExploreMetric.SNOW_5D, options);
 * }</pre>
 */
public final class ExploreOptions {
  private static final ExploreOptions DEFAULTS = builder().build();

  private final @Nullable SortOrder order;
  private final @Nullable String type;
  private final @Nullable String state;
  private final @Nullable Double nearLat;
  private final @Nullable Double nearLon;
  private final @Nullable Double radiusKm;
  private final @Nullable Double elevationMin;
  private final @Nullable Double elevationMax;
  private final boolean allPoints;
  private final @Nullable Integer limit;
  private final @Nullable String cursor;
  private final @Nullable UnitSystem units;

  private ExploreOptions(Builder b) {
    order = b.order;
    type = b.type;
    state = b.state;
    nearLat = b.nearLat;
    nearLon = b.nearLon;
    radiusKm = b.radiusKm;
    elevationMin = b.elevationMin;
    elevationMax = b.elevationMax;
    allPoints = b.allPoints;
    limit = b.limit;
    cursor = b.cursor;
    units = b.units;
  }

  /**
   * The options with nothing set: the API's defaults.
   *
   * @return the default options
   */
  public static ExploreOptions defaults() {
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

  @Nullable String cursor() {
    return cursor;
  }

  void addTo(Query q, UnitSystem clientUnits, @Nullable String pageCursor) {
    UnitSystem u = units == null ? clientUnits : units;
    q.string("order", order == null ? null : order.wireValue(), "desc")
        .string("type", type)
        .string("state", state)
        .number("radius_km", radiusKm, 100.0)
        .number("elevation_min", elevationMin, null)
        .number("elevation_max", elevationMax, null)
        .string("points", allPoints ? "all" : null)
        .integer("limit", limit == null ? null : limit.longValue(), 25L)
        .string("cursor", pageCursor)
        .string("units", u.wireValue().orElseThrow(), "metric");
    if (nearLat != null && nearLon != null) {
      q.numbers("near", nearLat, nearLon);
    }
  }

  /** Builds {@link ExploreOptions}. */
  public static final class Builder {
    private @Nullable SortOrder order;
    private @Nullable String type;
    private @Nullable String state;
    private @Nullable Double nearLat;
    private @Nullable Double nearLon;
    private @Nullable Double radiusKm;
    private @Nullable Double elevationMin;
    private @Nullable Double elevationMax;
    private boolean allPoints;
    private @Nullable Integer limit;
    private @Nullable String cursor;
    private @Nullable UnitSystem units;

    private Builder() {}

    /**
     * The ranking order ({@code order}, default {@link SortOrder#DESC}: the most first).
     *
     * @param order the order
     * @return this builder
     */
    public Builder order(SortOrder order) {
      this.order = Objects.requireNonNull(order, "order");
      return this;
    }

    /**
     * Only places of this type ({@code type}).
     *
     * @param type the type
     * @return this builder
     */
    public Builder type(LocationType type) {
      this.type =
          type.wireValue().orElseThrow(() -> new UsageException("type UNKNOWN cannot be sent"));
      return this;
    }

    /**
     * Only places in this state ({@code state}): a two-letter code, sent lowercase.
     *
     * @param state the state
     * @return this builder
     */
    public Builder state(String state) {
      this.state = Checks.state(Objects.requireNonNull(state, "state"));
      return this;
    }

    /**
     * Only places within {@link #radiusKm(double)} of this point ({@code near}, sent as {@code
     * lat,lon}).
     *
     * @param lat the latitude, -90..90
     * @param lon the longitude, -180..180
     * @return this builder
     */
    public Builder near(double lat, double lon) {
      Checks.latLon("near", lat, lon);
      this.nearLat = lat;
      this.nearLon = lon;
      return this;
    }

    /**
     * The radius around {@code near} ({@code radius_km}, default 100, at most 1000).
     *
     * @param radiusKm the radius in km
     * @return this builder
     */
    public Builder radiusKm(double radiusKm) {
      this.radiusKm = Checks.positive("radius_km", radiusKm, 1000);
      return this;
    }

    /**
     * Only points at or above this elevation ({@code elevation_min}), in the request's units.
     *
     * @param elevation the lowest elevation
     * @return this builder
     */
    public Builder elevationMin(double elevation) {
      this.elevationMin = Checks.finite("elevation_min", elevation);
      return this;
    }

    /**
     * Only points at or below this elevation ({@code elevation_max}), in the request's units.
     *
     * @param elevation the highest elevation
     * @return this builder
     */
    public Builder elevationMax(double elevation) {
      this.elevationMax = Checks.finite("elevation_max", elevation);
      return this;
    }

    /**
     * Rank every named elevation of a place ({@code points=all}), not only its primary point.
     *
     * @param all whether to rank every point
     * @return this builder
     */
    public Builder allPoints(boolean all) {
      this.allPoints = all;
      return this;
    }

    /**
     * Items per page ({@code limit}, default 25, 1..100).
     *
     * @param limit the count
     * @return this builder
     */
    public Builder limit(int limit) {
      this.limit = limit;
      return this;
    }

    /**
     * Start at this page ({@code cursor}): a previous page's {@code nextCursor()}.
     *
     * @param cursor the cursor
     * @return this builder
     */
    public Builder cursor(String cursor) {
      this.cursor = Objects.requireNonNull(cursor, "cursor");
      return this;
    }

    /**
     * The unit system of the values ({@code units}); the client's default otherwise.
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
    public ExploreOptions build() {
      return new ExploreOptions(this);
    }
  }
}
