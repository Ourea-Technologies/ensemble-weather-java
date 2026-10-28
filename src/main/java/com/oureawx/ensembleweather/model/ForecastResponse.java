// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A ForecastResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ForecastResponse}.
 */
public final class ForecastResponse extends Model {
  private final @Nullable ProbabilityThresholds thresholds;
  private final List<ProbabilityResult> probabilities;
  private final List<WindowTotal> windowTotals;
  private final @Nullable AppliedOptions applied;
  private final LocationRef location;
  private final ModelRunInfo modelRun;
  private final String units;
  private final ForecastData forecast;
  private final List<ElevationForecast> elevationForecasts;
  private final boolean nowcastApplied;
  private final List<String> nowcastUnavailable;
  private final @Nullable String membersView;
  private final @Nullable GapFlow gapFlow;
  private final List<NearestSensor> nearestSensors;
  private final @Nullable PointInfo point;
  private final @Nullable CurrentConditions current;
  private final ResponseAttribution attribution;
  private final ResponseMetadata metadata;

  private ForecastResponse(Builder b) {
    this.thresholds = b.thresholds;
    this.probabilities = List.copyOf(b.probabilities);
    this.windowTotals = List.copyOf(b.windowTotals);
    this.applied = b.applied;
    this.location = require(b.location, "location");
    this.modelRun = require(b.modelRun, "model_run");
    this.units = require(b.units, "units");
    this.forecast = require(b.forecast, "forecast");
    this.elevationForecasts = List.copyOf(b.elevationForecasts);
    this.nowcastApplied = require(b.nowcastApplied, "nowcast_applied");
    this.nowcastUnavailable = List.copyOf(b.nowcastUnavailable);
    this.membersView = b.membersView;
    this.gapFlow = b.gapFlow;
    this.nearestSensors = List.copyOf(b.nearestSensors);
    this.point = b.point;
    this.current = b.current;
    this.attribution = require(b.attribution, "attribution");
    this.metadata = b.metadata;
  }

  /**
   * Returns a new builder.
   *
   * @return an empty builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns a builder holding this object's values.
   *
   * @return a builder to make a modified copy
   */
  public Builder toBuilder() {
    Builder b = new Builder();
    b.thresholds = this.thresholds;
    b.probabilities = this.probabilities;
    b.windowTotals = this.windowTotals;
    b.applied = this.applied;
    b.location = this.location;
    b.modelRun = this.modelRun;
    b.units = this.units;
    b.forecast = this.forecast;
    b.elevationForecasts = this.elevationForecasts;
    b.nowcastApplied = this.nowcastApplied;
    b.nowcastUnavailable = this.nowcastUnavailable;
    b.membersView = this.membersView;
    b.gapFlow = this.gapFlow;
    b.nearestSensors = this.nearestSensors;
    b.point = this.point;
    b.current = this.current;
    b.attribution = this.attribution;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code thresholds} value.
   *
   * @return the {@code thresholds} value, or empty when the response omits it
   */
  public Optional<ProbabilityThresholds> thresholds() {
    return Optional.ofNullable(thresholds);
  }

  /**
   * The {@code ?prob=} terms, answered (omitted when none were asked).
   *
   * @return the {@code probabilities} values; empty when the response has none
   */
  public List<ProbabilityResult> probabilities() {
    return probabilities;
  }

  /**
   * The {@code ?totals=} windows (omitted when none were asked).
   *
   * @return the {@code window_totals} values; empty when the response has none
   */
  public List<WindowTotal> windowTotals() {
    return windowTotals;
  }

  /**
   * The {@code applied} value.
   *
   * @return the {@code applied} value, or empty when the response omits it
   */
  public Optional<AppliedOptions> applied() {
    return Optional.ofNullable(applied);
  }

  /**
   * The {@code location} value.
   *
   * @return the {@code location} value
   */
  public LocationRef location() {
    return location;
  }

  /**
   * The {@code model_run} value.
   *
   * @return the {@code model_run} value
   */
  public ModelRunInfo modelRun() {
    return modelRun;
  }

  /**
   * The unit system every value in this body is in -- the request's {@code units=}, {@code metric}
   * when it is not given. Always present, so a cached or shared body says whether {@code 32.0} is
   * °F or °C. The Cap'n Proto body carries the same as {@code ForecastResponse.units} (set when
   * {@code hasUnits}).
   *
   * @return the {@code units} value ({@link UnitSystem#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public UnitSystem units() {
    return UnitSystem.fromWireValue(units);
  }

  /**
   * The {@code units} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code units} string
   */
  public String unitsAsString() {
    return units;
  }

  /**
   * The {@code forecast} value.
   *
   * @return the {@code forecast} value
   */
  public ForecastData forecast() {
    return forecast;
  }

  /**
   * The {@code elevation_forecasts} value.
   *
   * @return the {@code elevation_forecasts} values; empty when the response has none
   */
  public List<ElevationForecast> elevationForecasts() {
    return elevationForecasts;
  }

  /**
   * Whether the live nowcast layer is baked in. {@code true} = merged legacy shape (uncacheable).
   * {@code false} = pure Layer-A base forecast (edge-cacheable; overlay {@code …/current}'s {@code
   * nowcast_hours} on top).
   *
   * @return the {@code nowcast_applied} value
   */
  public boolean nowcastApplied() {
    return nowcastApplied;
  }

  /**
   * The live nowcast sources this body was built WITHOUT because their read did not answer in time
   * (or failed): the nearest station's reading ({@code observation}), MRMS {@code radar}, GOES
   * {@code cloud}, or the {@code nearest_sensors} list. A forecast request never waits on a slow
   * observation read past a short deadline (250 ms for the radar and cloud grids, 1 s for a
   * location's first station read); it serves the forecast without that source and names it here.
   * Omitted when every source was read, and on {@code nowcast=false}.
   *
   * @return the {@code nowcast_unavailable} values; empty when the response has none
   */
  public List<NowcastSource> nowcastUnavailable() {
    List<NowcastSource> out = new ArrayList<>(nowcastUnavailable.size());
    for (String v : nowcastUnavailable) {
      out.add(NowcastSource.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code nowcast_unavailable} values exactly as the API sent them, including any this SDK
   * version does not know.
   *
   * @return the raw {@code nowcast_unavailable} strings
   */
  public List<String> nowcastUnavailableAsStrings() {
    return nowcastUnavailable;
  }

  /**
   * Which member view {@code forecast.timeseries[*].members} (and {@code
   * elevation_forecasts[*].hourly[*].members}) carries; present exactly when members are. {@code
   * plume} (Free) withholds {@code wind_speed}, {@code wind_direction} and {@code slr} — a {@code
   * null} there is withheld by the view, not a member that published nothing. {@code full} (Pro)
   * carries every member variable.
   *
   * @return the {@code members_view} value, or empty when the response omits it ({@link
   *     MembersView#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<MembersView> membersView() {
    return Optional.ofNullable(membersView).map(MembersView::fromWireValue);
  }

  /**
   * The {@code members_view} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code members_view} string, or empty when absent
   */
  public Optional<String> membersViewAsString() {
    return Optional.ofNullable(membersView);
  }

  /**
   * Columbia Gorge gap flow status at the first served hour, from the forecast
   * mean-sea-level-pressure difference across the Gorge (The Dalles minus Portland, each model
   * differenced on its own, then averaged). Only present for locations within the Gorge corridor
   * and when the forecast carries MSLP at both ends. Omitted for nowcast=false.
   *
   * @return the {@code gap_flow} value, or empty when the response omits it
   */
  public Optional<GapFlow> gapFlow() {
    return Optional.ofNullable(gapFlow);
  }

  /**
   * Nearby observation sensors ranked by an elevation-weighted distance. {@code
   * effective_distance_km} combines horizontal distance with a 100 m / 1 km elevation penalty — a
   * sensor 200 m below the forecast point effectively moves 2 km further away in the ranking.
   * Typically top 5 returned. Empty when no sensor reported within 50 km / 3 hours.
   *
   * @return the {@code nearest_sensors} values; empty when the response has none
   */
  public List<NearestSensor> nearestSensors() {
    return nearestSensors;
  }

  /**
   * The {@code point} value.
   *
   * @return the {@code point} value, or empty when the response omits it
   */
  public Optional<PointInfo> point() {
    return Optional.ofNullable(point);
  }

  /**
   * The {@code current} value.
   *
   * @return the {@code current} value, or empty when the response omits it
   */
  public Optional<CurrentConditions> current() {
    return Optional.ofNullable(current);
  }

  /**
   * The {@code attribution} value.
   *
   * @return the {@code attribution} value
   */
  public ResponseAttribution attribution() {
    return attribution;
  }

  /**
   * What the HTTP exchange said about this response: rate-limit state, request id, and whether it
   * was revalidated from the local cache. Not part of {@link #equals(Object)}.
   *
   * @return the response metadata
   */
  public ResponseMetadata metadata() {
    return metadata;
  }

  @Override
  Object[] values() {
    return new Object[] {
      thresholds,
      probabilities,
      windowTotals,
      applied,
      location,
      modelRun,
      units,
      forecast,
      elevationForecasts,
      nowcastApplied,
      nowcastUnavailable,
      membersView,
      gapFlow,
      nearestSensors,
      point,
      current,
      attribution
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "thresholds",
    "probabilities",
    "windowTotals",
    "applied",
    "location",
    "modelRun",
    "units",
    "forecast",
    "elevationForecasts",
    "nowcastApplied",
    "nowcastUnavailable",
    "membersView",
    "gapFlow",
    "nearestSensors",
    "point",
    "current",
    "attribution"
  };

  /** Builds a {@link ForecastResponse}. */
  public static final class Builder {
    private @Nullable ProbabilityThresholds thresholds;
    private List<ProbabilityResult> probabilities = List.of();
    private List<WindowTotal> windowTotals = List.of();
    private @Nullable AppliedOptions applied;
    private @Nullable LocationRef location;
    private @Nullable ModelRunInfo modelRun;
    private @Nullable String units;
    private @Nullable ForecastData forecast;
    private List<ElevationForecast> elevationForecasts = List.of();
    private @Nullable Boolean nowcastApplied;
    private List<String> nowcastUnavailable = List.of();
    private @Nullable String membersView;
    private @Nullable GapFlow gapFlow;
    private List<NearestSensor> nearestSensors = List.of();
    private @Nullable PointInfo point;
    private @Nullable CurrentConditions current;
    private @Nullable ResponseAttribution attribution;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link ForecastResponse#thresholds()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder thresholds(@Nullable ProbabilityThresholds value) {
      this.thresholds = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#probabilities()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder probabilities(List<ProbabilityResult> values) {
      this.probabilities = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ForecastResponse#windowTotals()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder windowTotals(List<WindowTotal> values) {
      this.windowTotals = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ForecastResponse#applied()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder applied(@Nullable AppliedOptions value) {
      this.applied = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#modelRun()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder modelRun(ModelRunInfo value) {
      this.modelRun = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#units()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder units(UnitSystem value) {
      this.units = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ForecastResponse#unitsAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder unitsAsString(String value) {
      this.units = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#forecast()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder forecast(ForecastData value) {
      this.forecast = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#elevationForecasts()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder elevationForecasts(List<ElevationForecast> values) {
      this.elevationForecasts = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ForecastResponse#nowcastApplied()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder nowcastApplied(boolean value) {
      this.nowcastApplied = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#nowcastUnavailable()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder nowcastUnavailable(List<NowcastSource> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (NowcastSource v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.nowcastUnavailable = raw;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#nowcastUnavailableAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder nowcastUnavailableAsStrings(List<String> values) {
      this.nowcastUnavailable = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ForecastResponse#membersView()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder membersView(@Nullable MembersView value) {
      this.membersView = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ForecastResponse#membersViewAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder membersViewAsString(@Nullable String value) {
      this.membersView = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#gapFlow()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder gapFlow(@Nullable GapFlow value) {
      this.gapFlow = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#nearestSensors()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder nearestSensors(List<NearestSensor> values) {
      this.nearestSensors = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ForecastResponse#point()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder point(@Nullable PointInfo value) {
      this.point = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#current()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder current(@Nullable CurrentConditions value) {
      this.current = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(ResponseAttribution value) {
      this.attribution = value;
      return this;
    }

    /**
     * Sets {@link ForecastResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link ForecastResponse}.
     *
     * @return the immutable ForecastResponse
     * @throws IllegalStateException if a required value was not set
     */
    public ForecastResponse build() {
      return new ForecastResponse(this);
    }
  }
}
