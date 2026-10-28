// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * Where a coordinate forecast ({@code GET /api/v1/forecast}) was computed. Present only on that
 * endpoint.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PointInfo}.
 */
public final class PointInfo extends Model {
  private final double requestedLat;
  private final double requestedLon;
  private final double lat;
  private final double lon;
  private final double resolutionDeg;
  private final double elevation;
  private final String elevationSource;
  private final String state;

  private PointInfo(Builder b) {
    this.requestedLat = require(b.requestedLat, "requested_lat");
    this.requestedLon = require(b.requestedLon, "requested_lon");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.resolutionDeg = require(b.resolutionDeg, "resolution_deg");
    this.elevation = require(b.elevation, "elevation");
    this.elevationSource = require(b.elevationSource, "elevation_source");
    this.state = require(b.state, "state");
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
    b.requestedLat = this.requestedLat;
    b.requestedLon = this.requestedLon;
    b.lat = this.lat;
    b.lon = this.lon;
    b.resolutionDeg = this.resolutionDeg;
    b.elevation = this.elevation;
    b.elevationSource = this.elevationSource;
    b.state = this.state;
    return b;
  }

  /**
   * Latitude as sent.
   *
   * @return the {@code requested_lat} value
   */
  public double requestedLat() {
    return requestedLat;
  }

  /**
   * Longitude as sent.
   *
   * @return the {@code requested_lon} value
   */
  public double requestedLon() {
    return requestedLon;
  }

  /**
   * The quantized latitude the forecast was computed at.
   *
   * @return the {@code lat} value
   */
  public double lat() {
    return lat;
  }

  /**
   * The quantized longitude the forecast was computed at.
   *
   * @return the {@code lon} value
   */
  public double lon() {
    return lon;
  }

  /**
   * Quantization step in degrees (0.01).
   *
   * @return the {@code resolution_deg} value
   */
  public double resolutionDeg() {
    return resolutionDeg;
  }

  /**
   * Elevation the forecast was computed at, in the response's length unit (m; ft for {@code
   * units=imperial}); {@code location.elevation_m} is the same height in metres.
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * {@code request} = the caller's {@code elevation_m} (it always wins); {@code dem_3dep_10m} = no
   * {@code elevation_m} was sent, and this is the USGS 3DEP 1/3 arc-second (~10 m) DEM, bilinear at
   * the quantized point.
   *
   * @return the {@code elevation_source} value ({@link ElevationSource#UNKNOWN} for a value this
   *     SDK version does not know)
   */
  public ElevationSource elevationSource() {
    return ElevationSource.fromWireValue(elevationSource);
  }

  /**
   * The {@code elevation_source} value exactly as the API sent it, even when this SDK version does
   * not know it.
   *
   * @return the raw {@code elevation_source} string
   */
  public String elevationSourceAsString() {
    return elevationSource;
  }

  /**
   * The lowercase USPS code of the state the point is in (sets the daily summaries' timezone), as
   * {@code location.state}.
   *
   * @return the {@code state} value
   */
  public String state() {
    return state;
  }

  @Override
  Object[] values() {
    return new Object[] {
      requestedLat, requestedLon, lat, lon, resolutionDeg, elevation, elevationSource, state
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "requestedLat",
    "requestedLon",
    "lat",
    "lon",
    "resolutionDeg",
    "elevation",
    "elevationSource",
    "state"
  };

  /** Builds a {@link PointInfo}. */
  public static final class Builder {
    private @Nullable Double requestedLat;
    private @Nullable Double requestedLon;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private @Nullable Double resolutionDeg;
    private @Nullable Double elevation;
    private @Nullable String elevationSource;
    private @Nullable String state;

    private Builder() {}

    /**
     * Sets {@link PointInfo#requestedLat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder requestedLat(double value) {
      this.requestedLat = value;
      return this;
    }

    /**
     * Sets {@link PointInfo#requestedLon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder requestedLon(double value) {
      this.requestedLon = value;
      return this;
    }

    /**
     * Sets {@link PointInfo#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link PointInfo#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link PointInfo#resolutionDeg()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder resolutionDeg(double value) {
      this.resolutionDeg = value;
      return this;
    }

    /**
     * Sets {@link PointInfo#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link PointInfo#elevationSource()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationSource(ElevationSource value) {
      this.elevationSource = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link PointInfo#elevationSourceAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder elevationSourceAsString(String value) {
      this.elevationSource = value;
      return this;
    }

    /**
     * Sets {@link PointInfo#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Builds the {@link PointInfo}.
     *
     * @return the immutable PointInfo
     * @throws IllegalStateException if a required value was not set
     */
    public PointInfo build() {
      return new PointInfo(this);
    }
  }
}
