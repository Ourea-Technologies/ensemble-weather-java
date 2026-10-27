// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A CurrentResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CurrentResponse}.
 */
public final class CurrentResponse extends Model {
  private final LocationRef location;
  private final List<ActiveAlert> alerts;
  private final Instant alertsAsOf;
  private final int sensorCount;
  private final List<SensorObservation> sensors;
  private final @Nullable CurrentConditions current;
  private final Instant asOf;
  private final @Nullable Instant modelRunInitializedAt;
  private final List<TimeSeriesPoint> nowcastHours;
  private final String units;
  private final ResponseAttribution attribution;
  private final List<String> nowcastUnavailable;
  private final ResponseMetadata metadata;

  private CurrentResponse(Builder b) {
    this.location = require(b.location, "location");
    this.alerts = List.copyOf(b.alerts);
    this.alertsAsOf = require(b.alertsAsOf, "alerts_as_of");
    this.sensorCount = require(b.sensorCount, "sensor_count");
    this.sensors = List.copyOf(b.sensors);
    this.current = b.current;
    this.asOf = require(b.asOf, "as_of");
    this.modelRunInitializedAt = b.modelRunInitializedAt;
    this.nowcastHours = List.copyOf(b.nowcastHours);
    this.units = require(b.units, "units");
    this.attribution = require(b.attribution, "attribution");
    this.nowcastUnavailable = List.copyOf(b.nowcastUnavailable);
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
    b.location = this.location;
    b.alerts = this.alerts;
    b.alertsAsOf = this.alertsAsOf;
    b.sensorCount = this.sensorCount;
    b.sensors = this.sensors;
    b.current = this.current;
    b.asOf = this.asOf;
    b.modelRunInitializedAt = this.modelRunInitializedAt;
    b.nowcastHours = this.nowcastHours;
    b.units = this.units;
    b.attribution = this.attribution;
    b.nowcastUnavailable = this.nowcastUnavailable;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The one location reference.
   *
   * @return the {@code location} value
   */
  public LocationRef location() {
    return location;
  }

  /**
   * Active NWS watches/warnings/advisories for this location (zone- and polygon-matched). Rides the
   * fresh /current lane deliberately — the cacheable forecast body never carries alerts.
   *
   * @return the {@code alerts} values; empty when the response has none
   */
  public List<ActiveAlert> alerts() {
    return alerts;
  }

  /**
   * Timestamp of the alerts snapshot backing this response (refreshed ~60s; staleness beyond a few
   * minutes means the alerts pipeline is degraded).
   *
   * @return the {@code alerts_as_of} value
   */
  public Instant alertsAsOf() {
    return alertsAsOf;
  }

  /**
   * The {@code sensor_count} value.
   *
   * @return the {@code sensor_count} value
   */
  public int sensorCount() {
    return sensorCount;
  }

  /**
   * Each sensor's latest reading set (in the request's {@code units}, under plain names). A reading
   * is omitted where the sensor does not take it, and where verification does not take it as truth
   * at that station (the one rule that also governs scoring and training): HADS river-gauge
   * temperature and dewpoint and CWOP backyard-station dewpoint and wind. A sensor left with no
   * reading at all is not listed, and is neither counted in {@code sensor_count} nor credited in
   * {@code attribution}.
   *
   * @return the {@code sensors} values; empty when the response has none
   */
  public List<SensorObservation> sensors() {
    return sensors;
  }

  /**
   * "Right now" flattened: the {@code nowcast_hours} hour nearest the request time, read field by
   * field (see the schema). Null when no cycle is servable or no band hour lies within an hour of
   * now.
   *
   * @return the {@code current} value, or empty when the response omits it
   */
  public Optional<CurrentConditions> current() {
    return Optional.ofNullable(current);
  }

  /**
   * When this overlay was assembled (the request time); the {@code nowcast_hours} window is
   * relative to it.
   *
   * @return the {@code as_of} value
   */
  public Instant asOf() {
    return asOf;
  }

  /**
   * The reference init of the cycle the band was read from (null when no cycle is servable). A
   * change means the cached base forecast is from an older cycle.
   *
   * @return the {@code model_run_initialized_at} value, or empty when the response omits it
   */
  public Optional<Instant> modelRunInitializedAt() {
    return Optional.ofNullable(modelRunInitializedAt);
  }

  /**
   * The obs-blended forecast hours with valid times in [as_of − 1 h, as_of + 10 h], shaped like the
   * forecast's {@code timeseries} points; null when no cycle is servable. Present only with {@code
   * ?include=nowcast_hours}; {@code current} is the flat "right now".
   *
   * @return the {@code nowcast_hours} values; empty when the response has none
   */
  public List<TimeSeriesPoint> nowcastHours() {
    return nowcastHours;
  }

  /**
   * The {@code units} value.
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
   * The {@code attribution} value.
   *
   * @return the {@code attribution} value
   */
  public ResponseAttribution attribution() {
    return attribution;
  }

  /**
   * The live nowcast sources {@code nowcast_hours} and {@code current} were built without because
   * their read did not answer in time (or failed). A request never waits on a slow observation read
   * past a short deadline; it serves without that source and names it here. Omitted when every
   * source was read.
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
      location,
      alerts,
      alertsAsOf,
      sensorCount,
      sensors,
      current,
      asOf,
      modelRunInitializedAt,
      nowcastHours,
      units,
      attribution,
      nowcastUnavailable
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "location",
    "alerts",
    "alertsAsOf",
    "sensorCount",
    "sensors",
    "current",
    "asOf",
    "modelRunInitializedAt",
    "nowcastHours",
    "units",
    "attribution",
    "nowcastUnavailable"
  };

  /** Builds a {@link CurrentResponse}. */
  public static final class Builder {
    private @Nullable LocationRef location;
    private List<ActiveAlert> alerts = List.of();
    private @Nullable Instant alertsAsOf;
    private @Nullable Integer sensorCount;
    private List<SensorObservation> sensors = List.of();
    private @Nullable CurrentConditions current;
    private @Nullable Instant asOf;
    private @Nullable Instant modelRunInitializedAt;
    private List<TimeSeriesPoint> nowcastHours = List.of();
    private @Nullable String units;
    private @Nullable ResponseAttribution attribution;
    private List<String> nowcastUnavailable = List.of();
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link CurrentResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link CurrentResponse#alerts()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder alerts(List<ActiveAlert> values) {
      this.alerts = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CurrentResponse#alertsAsOf()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder alertsAsOf(Instant value) {
      this.alertsAsOf = value;
      return this;
    }

    /**
     * Sets {@link CurrentResponse#sensorCount()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder sensorCount(int value) {
      this.sensorCount = value;
      return this;
    }

    /**
     * Sets {@link CurrentResponse#sensors()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder sensors(List<SensorObservation> values) {
      this.sensors = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CurrentResponse#current()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder current(@Nullable CurrentConditions value) {
      this.current = value;
      return this;
    }

    /**
     * Sets {@link CurrentResponse#asOf()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder asOf(Instant value) {
      this.asOf = value;
      return this;
    }

    /**
     * Sets {@link CurrentResponse#modelRunInitializedAt()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder modelRunInitializedAt(@Nullable Instant value) {
      this.modelRunInitializedAt = value;
      return this;
    }

    /**
     * Sets {@link CurrentResponse#nowcastHours()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder nowcastHours(List<TimeSeriesPoint> values) {
      this.nowcastHours = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CurrentResponse#units()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder units(UnitSystem value) {
      this.units = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CurrentResponse#unitsAsString()}, the raw wire value, kept verbatim even when
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
     * Sets {@link CurrentResponse#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(ResponseAttribution value) {
      this.attribution = value;
      return this;
    }

    /**
     * Sets {@link CurrentResponse#nowcastUnavailable()}.
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
     * Sets {@link CurrentResponse#nowcastUnavailableAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder nowcastUnavailableAsStrings(List<String> values) {
      this.nowcastUnavailable = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CurrentResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link CurrentResponse}.
     *
     * @return the immutable CurrentResponse
     * @throws IllegalStateException if a required value was not set
     */
    public CurrentResponse build() {
      return new CurrentResponse(this);
    }
  }
}
