// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.LocalDate;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Each recent run's daily snowfall at a location's point, by source.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowTrendResponse}.
 */
public final class SnowTrendResponse extends Model {
  private final LocationRef location;
  private final String units;
  private final SnowTrendResponseElevation elevation;
  private final int retentionDays;
  private final List<LocalDate> dates;
  private final List<SnowTrendGrid> grids;
  private final ResponseMetadata metadata;

  private SnowTrendResponse(Builder b) {
    this.location = require(b.location, "location");
    this.units = require(b.units, "units");
    this.elevation = require(b.elevation, "elevation");
    this.retentionDays = require(b.retentionDays, "retention_days");
    this.dates = List.copyOf(b.dates);
    this.grids = List.copyOf(b.grids);
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
    b.units = this.units;
    b.elevation = this.elevation;
    b.retentionDays = this.retentionDays;
    b.dates = this.dates;
    b.grids = this.grids;
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
   * The point the grids are for.
   *
   * @return the {@code elevation} value
   */
  public SnowTrendResponseElevation elevation() {
    return elevation;
  }

  /**
   * How many days back runs are kept.
   *
   * @return the {@code retention_days} value
   */
  public int retentionDays() {
    return retentionDays;
  }

  /**
   * The columns, location-local dates ascending.
   *
   * @return the {@code dates} values; empty when the response has none
   */
  public List<LocalDate> dates() {
    return dates;
  }

  /**
   * {@code ourea} first; with the {@code model_data} entitlement then {@code aifs}, {@code aigefs},
   * {@code gefs}, {@code ifs_ens} and {@code rrfs}. A client must tolerate a source it does not
   * know.
   *
   * @return the {@code grids} values; empty when the response has none
   */
  public List<SnowTrendGrid> grids() {
    return grids;
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
    return new Object[] {location, units, elevation, retentionDays, dates, grids};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "location", "units", "elevation", "retentionDays", "dates", "grids"
  };

  /** Builds a {@link SnowTrendResponse}. */
  public static final class Builder {
    private @Nullable LocationRef location;
    private @Nullable String units;
    private @Nullable SnowTrendResponseElevation elevation;
    private @Nullable Integer retentionDays;
    private List<LocalDate> dates = List.of();
    private List<SnowTrendGrid> grids = List.of();
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link SnowTrendResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendResponse#units()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder units(UnitSystem value) {
      this.units = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link SnowTrendResponse#unitsAsString()}, the raw wire value, kept verbatim even when
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
     * Sets {@link SnowTrendResponse#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(SnowTrendResponseElevation value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendResponse#retentionDays()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder retentionDays(int value) {
      this.retentionDays = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendResponse#dates()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder dates(List<LocalDate> values) {
      this.dates = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowTrendResponse#grids()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder grids(List<SnowTrendGrid> values) {
      this.grids = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowTrendResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link SnowTrendResponse}.
     *
     * @return the immutable SnowTrendResponse
     * @throws IllegalStateException if a required value was not set
     */
    public SnowTrendResponse build() {
      return new SnowTrendResponse(this);
    }
  }
}
