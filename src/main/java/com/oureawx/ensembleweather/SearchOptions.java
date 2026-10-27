// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.model.LocationType;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Hard filters and a limit for {@link EnsembleWeatherClient#searchLocations(String,
 * SearchOptions)}. A trailing state token in the query text is only a soft hint; these are hard
 * filters.
 */
public final class SearchOptions {
  private static final SearchOptions DEFAULTS = builder().build();

  private final @Nullable String state;
  private final @Nullable String county;
  private final @Nullable String type;
  private final @Nullable Integer limit;

  private SearchOptions(Builder b) {
    state = b.state;
    county = b.county;
    type = b.type;
    limit = b.limit;
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

  void addTo(Query q) {
    q.string("state", state)
        .string("county", county)
        .string("type", type)
        .integer("limit", limit == null ? null : limit.longValue(), 20L);
  }

  /** Builds {@link SearchOptions}. */
  public static final class Builder {
    private @Nullable String state;
    private @Nullable String county;
    private @Nullable String type;
    private @Nullable Integer limit;

    private Builder() {}

    /**
     * Only this state ({@code state}): a two-letter code or the full name.
     *
     * @param state the state
     * @return this builder
     */
    public Builder state(String state) {
      this.state = Objects.requireNonNull(state, "state");
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
     * Builds the options.
     *
     * @return the immutable options
     */
    public SearchOptions build() {
      return new SearchOptions(this);
    }
  }
}
