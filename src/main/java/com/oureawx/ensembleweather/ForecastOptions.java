// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.model.UnitSystem;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.jspecify.annotations.Nullable;

/**
 * Options for a forecast ({@link EnsembleWeatherClient#getForecast(LocationId, ForecastOptions)}
 * and {@link EnsembleWeatherClient#getPointForecast(Coordinates, ForecastOptions)}). Every option
 * left unset, or set to the API's default, is left out of the request, so equal requests share one
 * edge-cache entry.
 *
 * <pre>{@code
 * ForecastOptions options = ForecastOptions.builder()
 *     .units(UnitSystem.IMPERIAL)
 *     .maxHours(72)
 *     .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(6)))
 *     .total(TotalTerm.of(Variable.SNOWFALL, Window.hours(72)))
 *     .build();
 * }</pre>
 */
public final class ForecastOptions {
  private static final ForecastOptions DEFAULTS = builder().build();

  private final @Nullable UnitSystem units;
  private final @Nullable Integer maxHours;
  private final @Nullable Boolean includePast;
  private final @Nullable Boolean models;
  private final @Nullable Boolean members;
  private final List<String> elevations;
  private final @Nullable Boolean daily;
  private final @Nullable Integer maxDays;
  private final @Nullable Boolean hourly;
  private final @Nullable Instant run;
  private final List<String> sources;
  private final List<String> excludeSources;
  private final @Nullable Boolean nowcast;
  private final List<ProbabilityTerm> probabilities;
  private final List<TotalTerm> totals;
  private final Set<Diagnostic> diagnostics;
  private final Set<FeelsLikeIndex> feelsLike;

  private ForecastOptions(Builder b) {
    units = b.units;
    maxHours = b.maxHours;
    includePast = b.includePast;
    models = b.models;
    members = b.members;
    elevations = List.copyOf(b.elevations);
    daily = b.daily;
    maxDays = b.maxDays;
    hourly = b.hourly;
    run = b.run;
    sources = List.copyOf(b.sources);
    excludeSources = List.copyOf(b.excludeSources);
    nowcast = b.nowcast;
    probabilities = List.copyOf(b.probabilities);
    totals = List.copyOf(b.totals);
    diagnostics = b.diagnostics.isEmpty() ? Set.of() : Set.copyOf(EnumSet.copyOf(b.diagnostics));
    feelsLike = b.feelsLike.isEmpty() ? Set.of() : Set.copyOf(EnumSet.copyOf(b.feelsLike));
  }

  /**
   * The options with nothing set: the API's defaults.
   *
   * @return the default options
   */
  public static ForecastOptions defaults() {
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

  UnitSystem unitsOr(UnitSystem fallback) {
    return units == null ? fallback : units;
  }

  List<ProbabilityTerm> probabilities() {
    return probabilities;
  }

  List<TotalTerm> totals() {
    return totals;
  }

  void addTo(Query q, UnitSystem effectiveUnits, boolean point) {
    if (point && !elevations.isEmpty()) {
      throw new UsageException("elevations apply to named locations, not to a point forecast");
    }
    q.string("units", effectiveUnits.wireValue().orElseThrow(), "metric")
        .integer("max_hours", maxHours == null ? null : maxHours.longValue(), 24L)
        .bool("include_past", includePast, false)
        .bool("models", models, false)
        .bool("members", members, false)
        .csv("elevations", elevations)
        .bool("daily", daily, true)
        .integer("max_days", maxDays == null ? null : maxDays.longValue(), null)
        .bool("hourly", hourly, true)
        .string("run", run == null ? null : run.toString())
        .csv("sources", sources)
        .csv("exclude_sources", excludeSources)
        .bool("nowcast", nowcast, true);
    if (!diagnostics.isEmpty()) {
      List<String> names = new ArrayList<>();
      for (Diagnostic d : EnumSet.copyOf(diagnostics)) {
        names.add(d.wireValue());
      }
      q.csv("diagnostics", names);
    }
    if (!feelsLike.isEmpty() && !feelsLike.equals(Set.of(FeelsLikeIndex.NWS))) {
      List<String> names = new ArrayList<>();
      for (FeelsLikeIndex f : EnumSet.copyOf(feelsLike)) {
        names.add(f.wireValue());
      }
      q.csv("feels_like", names);
    }
  }

  /** Builds {@link ForecastOptions}. */
  public static final class Builder {
    private @Nullable UnitSystem units;
    private @Nullable Integer maxHours;
    private @Nullable Boolean includePast;
    private @Nullable Boolean models;
    private @Nullable Boolean members;
    private List<String> elevations = List.of();
    private @Nullable Boolean daily;
    private @Nullable Integer maxDays;
    private @Nullable Boolean hourly;
    private @Nullable Instant run;
    private List<String> sources = List.of();
    private List<String> excludeSources = List.of();
    private @Nullable Boolean nowcast;
    private final List<ProbabilityTerm> probabilities = new ArrayList<>();
    private final List<TotalTerm> totals = new ArrayList<>();
    private Set<Diagnostic> diagnostics = Set.of();
    private Set<FeelsLikeIndex> feelsLike = Set.of();

    private Builder() {}

    /**
     * The unit system of every value in the response ({@code units}); the client's default
     * otherwise.
     *
     * @param units metric (the API's default), imperial or kelvin
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
     * Hours of the hourly series past its first hour ({@code max_hours}, default 24), clamped by
     * the server to the tier (Free 240, Pro 360).
     *
     * @param hours the hours
     * @return this builder
     */
    public Builder maxHours(int hours) {
      this.maxHours = hours;
      return this;
    }

    /**
     * Serve the hourly series from the model run's init, the past hours included ({@code
     * include_past}, default false).
     *
     * @param includePast whether to include the past hours
     * @return this builder
     */
    public Builder includePast(boolean includePast) {
      this.includePast = includePast;
      return this;
    }

    /**
     * Include per-model breakdowns ({@code models}, Pro; default false).
     *
     * @param models whether to include them
     * @return this builder
     */
    public Builder models(boolean models) {
      this.models = models;
      return this;
    }

    /**
     * Include each ensemble member's values ({@code members}, default false): the plume view for
     * Free callers, every member variable for Pro.
     *
     * @param members whether to include them
     * @return this builder
     */
    public Builder members(boolean members) {
      this.members = members;
      return this;
    }

    /**
     * Include forecasts at these named elevations of the location ({@code elevations}). Named
     * locations only.
     *
     * @param names elevation names ({@code location.elevations[].name})
     * @return this builder
     */
    public Builder elevations(String... names) {
      this.elevations = List.copyOf(Arrays.asList(names));
      return this;
    }

    /**
     * Include forecasts at every extra elevation of the location ({@code elevations=all}).
     *
     * @return this builder
     */
    public Builder allElevations() {
      this.elevations = List.of("all");
      return this;
    }

    /**
     * Include the daily summaries ({@code daily}, default true).
     *
     * @param daily whether to include them
     * @return this builder
     */
    public Builder daily(boolean daily) {
      this.daily = daily;
      return this;
    }

    /**
     * The number of daily rows ({@code max_days}); the tier's full horizon by default.
     *
     * @param days the days, 1 to 15
     * @return this builder
     */
    public Builder maxDays(int days) {
      this.maxDays = days;
      return this;
    }

    /**
     * Include the hourly series ({@code hourly}, default true); false serves the dailies alone.
     *
     * @param hourly whether to include it
     * @return this builder
     */
    public Builder hourly(boolean hourly) {
      this.hourly = hourly;
      return this;
    }

    /**
     * Pin a historical model run by its init time ({@code run}, Pro).
     *
     * @param initializedAt the run's init
     * @return this builder
     */
    public Builder run(Instant initializedAt) {
      this.run = Objects.requireNonNull(initializedAt, "initializedAt");
      return this;
    }

    /**
     * Allow only these observation sources in the nowcast ({@code sources}).
     *
     * @param sources network names, such as {@code snotel}
     * @return this builder
     */
    public Builder sources(String... sources) {
      this.sources = List.copyOf(Arrays.asList(sources));
      return this;
    }

    /**
     * Leave these observation sources out of the nowcast ({@code exclude_sources}).
     *
     * @param sources network names, such as {@code raws}
     * @return this builder
     */
    public Builder excludeSources(String... sources) {
      this.excludeSources = List.copyOf(Arrays.asList(sources));
      return this;
    }

    /**
     * Blend live observations into the first hours ({@code nowcast}, default true); false serves
     * the pure, longer-cacheable base forecast.
     *
     * @param nowcast whether to apply the nowcast
     * @return this builder
     */
    public Builder nowcast(boolean nowcast) {
      this.nowcast = nowcast;
      return this;
    }

    /**
     * Ask for a probability ({@code prob}); call it once per term. The answers come back in {@code
     * probabilities()} in the order the terms were added.
     *
     * @param term the term
     * @return this builder
     */
    public Builder probability(ProbabilityTerm term) {
      probabilities.add(Objects.requireNonNull(term, "term"));
      return this;
    }

    /**
     * Ask for several probabilities ({@code prob}), in this order.
     *
     * @param terms the terms
     * @return this builder
     */
    public Builder probabilities(List<ProbabilityTerm> terms) {
      for (ProbabilityTerm t : terms) {
        probability(t);
      }
      return this;
    }

    /**
     * Ask for a window total ({@code totals}); call it once per term. The answers come back in
     * {@code windowTotals()} in the order the terms were added.
     *
     * @param term the term
     * @return this builder
     */
    public Builder total(TotalTerm term) {
      totals.add(Objects.requireNonNull(term, "term"));
      return this;
    }

    /**
     * Ask for several window totals ({@code totals}), in this order.
     *
     * @param terms the terms
     * @return this builder
     */
    public Builder totals(List<TotalTerm> terms) {
      for (TotalTerm t : terms) {
        total(t);
      }
      return this;
    }

    /**
     * Opt-in serve-path diagnostics ({@code diagnostics}).
     *
     * @param diagnostics the diagnostics
     * @return this builder
     */
    public Builder diagnostics(Diagnostic... diagnostics) {
      this.diagnostics = Set.copyOf(Arrays.asList(diagnostics));
      return this;
    }

    /**
     * Which "feels like" indices to serve ({@code feels_like}, default NWS).
     *
     * @param indices the indices
     * @return this builder
     */
    public Builder feelsLike(FeelsLikeIndex... indices) {
      this.feelsLike = Set.copyOf(Arrays.asList(indices));
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the immutable options
     */
    public ForecastOptions build() {
      return new ForecastOptions(this);
    }
  }
}
