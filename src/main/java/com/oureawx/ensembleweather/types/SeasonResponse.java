// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Observed season-to-date precipitation at a location.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SeasonResponse}.
 */
public final class SeasonResponse extends Model {
  private final LocationRef location;
  private final int waterYear;
  private final LocalDate starts;
  private final LocalDate through;
  private final @Nullable Instant asOf;
  private final String units;
  private final @Nullable SeasonTotal precipitation;
  private final @Nullable SeasonTotal snowfall;
  private final @Nullable SeasonStation station;
  private final List<SeasonDay> daily;
  private final ResponseAttribution attribution;
  private final ResponseMetadata metadata;

  private SeasonResponse(Builder b) {
    this.location = require(b.location, "location");
    this.waterYear = require(b.waterYear, "water_year");
    this.starts = require(b.starts, "starts");
    this.through = require(b.through, "through");
    this.asOf = b.asOf;
    this.units = require(b.units, "units");
    this.precipitation = b.precipitation;
    this.snowfall = b.snowfall;
    this.station = b.station;
    this.daily = List.copyOf(b.daily);
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
    b.location = this.location;
    b.waterYear = this.waterYear;
    b.starts = this.starts;
    b.through = this.through;
    b.asOf = this.asOf;
    b.units = this.units;
    b.precipitation = this.precipitation;
    b.snowfall = this.snowfall;
    b.station = this.station;
    b.daily = this.daily;
    b.attribution = this.attribution;
    b.metadata = this.metadata;
    return b;
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
   * The water year by its ending year.
   *
   * @return the {@code water_year} value
   */
  public int waterYear() {
    return waterYear;
  }

  /**
   * The first local day of the water year.
   *
   * @return the {@code starts} value
   */
  public LocalDate starts() {
    return starts;
  }

  /**
   * The last local day with an observed row (the day before {@code starts} when there is none).
   *
   * @return the {@code through} value
   */
  public LocalDate through() {
    return through;
  }

  /**
   * When the newest day row was computed; null with no rows.
   *
   * @return the {@code as_of} value, or empty when the response omits it
   */
  public Optional<Instant> asOf() {
    return Optional.ofNullable(asOf);
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
   * Null until the location has an observed day.
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public Optional<SeasonTotal> precipitation() {
    return Optional.ofNullable(precipitation);
  }

  /**
   * Always null for now; season snowfall is not served yet.
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public Optional<SeasonTotal> snowfall() {
    return Optional.ofNullable(snowfall);
  }

  /**
   * The nearest SNOTEL or SCAN gauge within 25 km and 300 m with rows this water year, or null.
   *
   * @return the {@code station} value, or empty when the response omits it
   */
  public Optional<SeasonStation> station() {
    return Optional.ofNullable(station);
  }

  /**
   * Only with {@code include=daily}. One row per observed local day, oldest first.
   *
   * @return the {@code daily} values; empty when the response has none
   */
  public List<SeasonDay> daily() {
    return daily;
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
  Object[] fieldValues() {
    return new Object[] {
      location,
      waterYear,
      starts,
      through,
      asOf,
      units,
      precipitation,
      snowfall,
      station,
      daily,
      attribution
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "location",
    "waterYear",
    "starts",
    "through",
    "asOf",
    "units",
    "precipitation",
    "snowfall",
    "station",
    "daily",
    "attribution"
  };

  /** Builds a {@link SeasonResponse}. */
  public static final class Builder {
    private @Nullable LocationRef location;
    private @Nullable Integer waterYear;
    private @Nullable LocalDate starts;
    private @Nullable LocalDate through;
    private @Nullable Instant asOf;
    private @Nullable String units;
    private @Nullable SeasonTotal precipitation;
    private @Nullable SeasonTotal snowfall;
    private @Nullable SeasonStation station;
    private List<SeasonDay> daily = List.of();
    private @Nullable ResponseAttribution attribution;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link SeasonResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#waterYear()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder waterYear(int value) {
      this.waterYear = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#starts()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder starts(LocalDate value) {
      this.starts = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#through()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder through(LocalDate value) {
      this.through = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#asOf()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder asOf(@Nullable Instant value) {
      this.asOf = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#units()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder units(UnitSystem value) {
      this.units = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link SeasonResponse#unitsAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder unitsAsString(String value) {
      this.units = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable SeasonTotal value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable SeasonTotal value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#station()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder station(@Nullable SeasonStation value) {
      this.station = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#daily()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder daily(List<SeasonDay> values) {
      this.daily = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SeasonResponse#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(ResponseAttribution value) {
      this.attribution = value;
      return this;
    }

    /**
     * Sets {@link SeasonResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link SeasonResponse}.
     *
     * @return the immutable SeasonResponse
     * @throws IllegalStateException if a required value was not set
     */
    public SeasonResponse build() {
      return new SeasonResponse(this);
    }
  }
}
