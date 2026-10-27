// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Filters and paging for the catalog location list ({@link
 * EnsembleWeatherClient#listCatalogLocations(CatalogLocationQuery)}).
 */
public final class CatalogLocationQuery {
  private static final CatalogLocationQuery DEFAULTS = builder().build();

  private final @Nullable String admin1;
  private final @Nullable String country;
  private final @Nullable String type;
  private final @Nullable String template;
  private final @Nullable Integer limit;
  private final @Nullable String cursor;

  private CatalogLocationQuery(Builder b) {
    admin1 = b.admin1;
    country = b.country;
    type = b.type;
    template = b.template;
    limit = b.limit;
    cursor = b.cursor;
  }

  /**
   * The query with nothing set: every published location, 20 a page.
   *
   * @return the default query
   */
  public static CatalogLocationQuery all() {
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
        .string("type", type)
        .string("template", template)
        .integer("limit", limit == null ? null : limit.longValue(), 20L)
        .string("cursor", cursorOverride);
  }

  /** Builds a {@link CatalogLocationQuery}. */
  public static final class Builder {
    private @Nullable String admin1;
    private @Nullable String country;
    private @Nullable String type;
    private @Nullable String template;
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
     * Only this ISO 3166-1 alpha-2 country ({@code country}), such as {@code US}.
     *
     * @param country the country code
     * @return this builder
     */
    public Builder country(String country) {
      this.country = Objects.requireNonNull(country, "country");
      return this;
    }

    /**
     * Only this catalog location type ({@code type}), in the catalog's spelling ({@code SkiArea}).
     *
     * @param type the type
     * @return this builder
     */
    public Builder type(String type) {
      this.type = Objects.requireNonNull(type, "type");
      return this;
    }

    /**
     * Only this page template variant ({@code template}), such as {@code Mountain}.
     *
     * @param template the variant
     * @return this builder
     */
    public Builder template(String template) {
      this.template = Objects.requireNonNull(template, "template");
      return this;
    }

    /**
     * The page size ({@code limit}, default 20, clamped to 1..100 by the server).
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
    public CatalogLocationQuery build() {
      return new CatalogLocationQuery(this);
    }
  }
}
