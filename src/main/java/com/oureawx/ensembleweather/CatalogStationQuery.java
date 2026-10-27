// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import org.jspecify.annotations.Nullable;

/**
 * Filters and paging for the catalog station list ({@link
 * EnsembleWeatherClient#listCatalogStations(CatalogStationQuery)}).
 */
public final class CatalogStationQuery {
  private static final CatalogStationQuery DEFAULTS = builder().build();

  private final @Nullable String admin1;
  private final @Nullable String country;
  private final List<String> networks;
  private final List<String> capabilities;
  private final @Nullable String status;
  private final @Nullable Integer limit;
  private final @Nullable String cursor;

  private CatalogStationQuery(Builder b) {
    admin1 = b.admin1;
    country = b.country;
    networks = b.networks;
    capabilities = b.capabilities;
    status = b.status;
    limit = b.limit;
    cursor = b.cursor;
  }

  /**
   * The query with nothing set: every published station, 20 a page.
   *
   * @return the default query
   */
  public static CatalogStationQuery all() {
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

  void addTo(Query q, @Nullable String cursorOverride) {
    q.string("admin1", admin1)
        .string("country", country)
        .csv("network", networks)
        .repeated("capability", capabilities)
        .string("status", status)
        .integer("limit", limit == null ? null : limit.longValue(), 20L)
        .string("cursor", cursorOverride);
  }

  /** Builds a {@link CatalogStationQuery}. */
  public static final class Builder {
    private @Nullable String admin1;
    private @Nullable String country;
    private List<String> networks = List.of();
    private List<String> capabilities = List.of();
    private @Nullable String status;
    private @Nullable Integer limit;
    private @Nullable String cursor;

    private Builder() {}

    /**
     * Only this ISO 3166-2 region ({@code admin1}), such as {@code US-OR}.
     *
     * @param admin1 the region code
     * @return this builder
     */
    public Builder admin1(String admin1) {
      this.admin1 = Objects.requireNonNull(admin1, "admin1");
      return this;
    }

    /**
     * Only this ISO 3166-1 alpha-2 country ({@code country}).
     *
     * @param country the country code
     * @return this builder
     */
    public Builder country(String country) {
      this.country = Objects.requireNonNull(country, "country");
      return this;
    }

    /**
     * Only these networks, sent as {@code network=}, such as {@code Snotel}.
     *
     * @param networks the networks
     * @return this builder
     */
    public Builder networks(String... networks) {
      this.networks = List.copyOf(Arrays.asList(networks));
      return this;
    }

    /**
     * Only stations with every one of these capabilities ({@code capability}, repeated). Sent
     * sorted and de-duplicated, so equal queries share one URL.
     *
     * @param capabilities the capabilities, such as {@code swe}
     * @return this builder
     */
    public Builder capabilities(String... capabilities) {
      this.capabilities = List.copyOf(new TreeSet<>(Arrays.asList(capabilities)));
      return this;
    }

    /**
     * Only stations in this status ({@code status}), such as {@code Active}.
     *
     * @param status the status
     * @return this builder
     */
    public Builder status(String status) {
      this.status = Objects.requireNonNull(status, "status");
      return this;
    }

    /**
     * The page size ({@code limit}, default 20, at most 100).
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
     * Builds the query.
     *
     * @return the immutable query
     */
    public CatalogStationQuery build() {
      return new CatalogStationQuery(this);
    }
  }
}
