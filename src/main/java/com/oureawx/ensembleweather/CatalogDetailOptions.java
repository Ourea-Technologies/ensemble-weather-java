// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.jspecify.annotations.Nullable;

/**
 * Opt-in extras for a catalog detail ({@link EnsembleWeatherClient#getCatalogLocation(String,
 * CatalogDetailOptions)} and {@link EnsembleWeatherClient#getCatalogStation(String,
 * CatalogDetailOptions)}).
 */
public final class CatalogDetailOptions {
  private static final CatalogDetailOptions DEFAULTS = builder().build();

  private final Set<CatalogInclude> include;
  private final @Nullable String locale;

  private CatalogDetailOptions(Builder b) {
    include = b.include;
    locale = b.locale;
  }

  /**
   * The options with nothing set.
   *
   * @return the default options
   */
  public static CatalogDetailOptions defaults() {
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
    if (!include.isEmpty()) {
      List<String> names = new ArrayList<>();
      for (CatalogInclude i : EnumSet.copyOf(include)) {
        names.add(i.wireValue());
      }
      q.csv("include", names);
    }
    q.string("locale", locale, "en");
  }

  /** Builds {@link CatalogDetailOptions}. */
  public static final class Builder {
    private Set<CatalogInclude> include = Set.of();
    private @Nullable String locale;

    private Builder() {}

    /**
     * Opt-in extras ({@code include}).
     *
     * @param include the extras
     * @return this builder
     */
    public Builder include(CatalogInclude... include) {
      this.include = Set.copyOf(Arrays.asList(include));
      return this;
    }

    /**
     * The BCP 47 locale for {@code content} ({@code locale}, default {@code en}).
     *
     * @param locale the locale tag, such as {@code es}
     * @return this builder
     */
    public Builder locale(String locale) {
      this.locale = Objects.requireNonNull(locale, "locale");
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the immutable options
     */
    public CatalogDetailOptions build() {
      return new CatalogDetailOptions(this);
    }
  }
}
