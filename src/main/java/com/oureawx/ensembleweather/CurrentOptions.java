// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.model.UnitSystem;
import java.util.Arrays;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Options for current conditions ({@link EnsembleWeatherClient#getCurrent(LocationId,
 * CurrentOptions)}).
 */
public final class CurrentOptions {
  private static final CurrentOptions DEFAULTS = builder().build();

  private final @Nullable UnitSystem units;
  private final List<String> sources;
  private final List<String> excludeSources;
  private final boolean nowcastHours;

  private CurrentOptions(Builder b) {
    units = b.units;
    sources = b.sources;
    excludeSources = b.excludeSources;
    nowcastHours = b.nowcastHours;
  }

  /**
   * The options with nothing set.
   *
   * @return the default options
   */
  public static CurrentOptions defaults() {
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
        .csv("sources", sources)
        .csv("exclude_sources", excludeSources)
        .string("include", nowcastHours ? "nowcast_hours" : null);
  }

  /** Builds {@link CurrentOptions}. */
  public static final class Builder {
    private @Nullable UnitSystem units;
    private List<String> sources = List.of();
    private List<String> excludeSources = List.of();
    private boolean nowcastHours;

    private Builder() {}

    /**
     * The unit system of the response ({@code units}); the client's default otherwise.
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
     * Allow only these observation sources ({@code sources}).
     *
     * @param sources network names, such as {@code snotel}
     * @return this builder
     */
    public Builder sources(String... sources) {
      this.sources = List.copyOf(Arrays.asList(sources));
      return this;
    }

    /**
     * Leave these observation sources out ({@code exclude_sources}).
     *
     * @param sources network names
     * @return this builder
     */
    public Builder excludeSources(String... sources) {
      this.excludeSources = List.copyOf(Arrays.asList(sources));
      return this;
    }

    /**
     * Include the obs-blended forecast hours around now ({@code include=nowcast_hours}).
     *
     * @param include whether to include them
     * @return this builder
     */
    public Builder includeNowcastHours(boolean include) {
      this.nowcastHours = include;
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the immutable options
     */
    public CurrentOptions build() {
      return new CurrentOptions(this);
    }
  }
}
