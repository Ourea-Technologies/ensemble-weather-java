// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.types.ExploreMetric;
import com.oureawx.ensembleweather.types.LocationType;
import com.oureawx.ensembleweather.types.UnitSystem;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Hard filters, a sort and a limit for {@link Client#searchLocations(String, SearchOptions)}. A
 * trailing state token in the query text is only a soft hint; these are hard filters. Every option
 * left unset, or set to the API's default, is left out of the request.
 *
 * <pre>{@code
 * SearchOptions options = SearchOptions.builder()
 *     .sort(ExploreMetric.SNOW_5D)
 *     .near(45.33, -121.66)
 *     .build();
 * }</pre>
 */
public final class SearchOptions {
  private static final SearchOptions DEFAULTS = builder().build();

  private final @Nullable String state;
  private final @Nullable String county;
  private final @Nullable String type;
  private final @Nullable Integer limit;
  private final @Nullable ExploreMetric sort;
  private final @Nullable SortOrder order;
  private final @Nullable Double nearLat;
  private final @Nullable Double nearLon;
  private final @Nullable Double radiusKm;
  private final @Nullable UnitSystem units;

  private SearchOptions(Builder b) {
    state = b.state;
    county = b.county;
    type = b.type;
    limit = b.limit;
    sort = b.sort;
    order = b.order;
    nearLat = b.nearLat;
    nearLon = b.nearLon;
    radiusKm = b.radiusKm;
    units = b.units;
  }

  /**
   * The options with nothing set.
   *
   * @return the default options
   */
  public static SearchOptions defaults() {
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
    q.string("state", state)
        .string("county", county)
        .string("type", type)
        .integer("limit", limit == null ? null : limit.longValue(), 20L)
        .string("sort", sort == null ? null : sort.wireValue().orElseThrow(), "relevance")
        .string("order", order == null ? null : order.wireValue(), "desc")
        .number("radius_km", radiusKm, 100.0)
        .string("units", u.wireValue().orElseThrow(), "metric");
    if (nearLat != null && nearLon != null) {
      q.numbers("near", nearLat, nearLon);
    }
  }

  /** Builds {@link SearchOptions}. */
  public static final class Builder {
    private @Nullable String state;
    private @Nullable String county;
    private @Nullable String type;
    private @Nullable Integer limit;
    private @Nullable ExploreMetric sort;
    private @Nullable SortOrder order;
    private @Nullable Double nearLat;
    private @Nullable Double nearLon;
    private @Nullable Double radiusKm;
    private @Nullable UnitSystem units;

    private Builder() {}

    /**
     * Only this state ({@code state}): a two-letter code (sent lowercase) or the full name.
     *
     * @param state the state
     * @return this builder
     */
    public Builder state(String state) {
      this.state = Checks.state(Objects.requireNonNull(state, "state"));
      return this;
    }

    /**
     * Only this county ({@code county}).
     *
     * @param county the county name
     * @return this builder
     */
    public Builder county(String county) {
      this.county = Objects.requireNonNull(county, "county");
      return this;
    }

    /**
     * Only this location type ({@code type}); needed to see observation stations.
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
     * Maximum hits ({@code limit}, default 20, clamped to 1..100 by the server).
     *
     * @param limit the count
     * @return this builder
     */
    public Builder limit(int limit) {
      this.limit = limit;
      return this;
    }

    /**
     * Ranks the hits by an explore metric ({@code sort}; the default is by relevance). Each hit
     * then carries the metric's value in {@code explore()}.
     *
     * @param metric the metric
     * @return this builder
     */
    public Builder sort(ExploreMetric metric) {
      this.sort = requireKnown(metric);
      return this;
    }

    /**
     * The order of a metric sort ({@code order}, default {@link SortOrder#DESC}).
     *
     * @param order the order
     * @return this builder
     */
    public Builder order(SortOrder order) {
      this.order = Objects.requireNonNull(order, "order");
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
     * The unit system of the hits' metric values ({@code units}); the client's default otherwise.
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
    public SearchOptions build() {
      return new SearchOptions(this);
    }
  }

  static ExploreMetric requireKnown(ExploreMetric metric) {
    if (Objects.requireNonNull(metric, "metric") == ExploreMetric.UNKNOWN) {
      throw new UsageException("metric UNKNOWN cannot be sent");
    }
    return metric;
  }
}
