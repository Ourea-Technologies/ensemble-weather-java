// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.model.LocationType;
import com.oureawx.ensembleweather.model.UnitSystem;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Filters and paging for {@link EnsembleWeatherClient#listLocations(LocationListOptions)}: the
 * slug-ordered location list, one page at a time.
 */
public final class LocationListOptions {
  private static final LocationListOptions DEFAULTS = builder().build();

  private final @Nullable String type;
  private final @Nullable UnitSystem units;
  private final @Nullable Integer limit;
  private final @Nullable String cursor;

  private LocationListOptions(Builder b) {
    type = b.type;
    units = b.units;
    limit = b.limit;
    cursor = b.cursor;
  }

  /**
   * The options with nothing set.
   *
   * @return the default options
   */
  public static LocationListOptions defaults() {
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

  void addTo(Query q, UnitSystem clientUnits, @Nullable String cursorOverride) {
    UnitSystem u = units == null ? clientUnits : units;
    q.string("type", type)
        .string("units", u.wireValue().orElseThrow(), "metric")
        .integer("limit", limit == null ? null : limit.longValue(), null)
        .string("cursor", cursorOverride);
  }

  /** Builds {@link LocationListOptions}. */
  public static final class Builder {
    private @Nullable String type;
    private @Nullable UnitSystem units;
    private @Nullable Integer limit;
    private @Nullable String cursor;

    private Builder() {}

    /**
     * Only this location type ({@code type}).
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
     * The unit system of the rows' elevations ({@code units}).
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
     * The page size ({@code limit}, 1..5000; capped lower for anonymous and Free callers).
     *
     * @param limit the page size
     * @return this builder
     */
    public Builder limit(int limit) {
      this.limit = limit;
      return this;
    }

    /**
     * Start at this page ({@code cursor}: a previous page's {@code next_cursor}).
     *
     * @param cursor the cursor
     * @return this builder
     */
    public Builder cursor(String cursor) {
      this.cursor = Objects.requireNonNull(cursor, "cursor");
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the immutable options
     */
    public LocationListOptions build() {
      return new LocationListOptions(this);
    }
  }
}
