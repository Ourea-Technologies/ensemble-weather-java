// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A NearbyPointResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code NearbyPointResponse}.
 */
public final class NearbyPointResponse extends Model {
  private final double lat;
  private final double lon;
  private final double radiusKm;
  private final int count;
  private final List<NearbyLocation> locations;
  private final ResponseMetadata metadata;

  private NearbyPointResponse(Builder b) {
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.radiusKm = require(b.radiusKm, "radius_km");
    this.count = require(b.count, "count");
    this.locations = List.copyOf(b.locations);
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
    b.lat = this.lat;
    b.lon = this.lon;
    b.radiusKm = this.radiusKm;
    b.count = this.count;
    b.locations = this.locations;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code lat} value.
   *
   * @return the {@code lat} value
   */
  public double lat() {
    return lat;
  }

  /**
   * The {@code lon} value.
   *
   * @return the {@code lon} value
   */
  public double lon() {
    return lon;
  }

  /**
   * The {@code radius_km} value.
   *
   * @return the {@code radius_km} value
   */
  public double radiusKm() {
    return radiusKm;
  }

  /**
   * The {@code count} value.
   *
   * @return the {@code count} value
   */
  public int count() {
    return count;
  }

  /**
   * The {@code locations} value.
   *
   * @return the {@code locations} values; empty when the response has none
   */
  public List<NearbyLocation> locations() {
    return locations;
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
    return new Object[] {lat, lon, radiusKm, count, locations};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"lat", "lon", "radiusKm", "count", "locations"};

  /** Builds a {@link NearbyPointResponse}. */
  public static final class Builder {
    private @Nullable Double lat;
    private @Nullable Double lon;
    private @Nullable Double radiusKm;
    private @Nullable Integer count;
    private List<NearbyLocation> locations = List.of();
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link NearbyPointResponse#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link NearbyPointResponse#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link NearbyPointResponse#radiusKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder radiusKm(double value) {
      this.radiusKm = value;
      return this;
    }

    /**
     * Sets {@link NearbyPointResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link NearbyPointResponse#locations()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder locations(List<NearbyLocation> values) {
      this.locations = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link NearbyPointResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link NearbyPointResponse}.
     *
     * @return the immutable NearbyPointResponse
     * @throws IllegalStateException if a required value was not set
     */
    public NearbyPointResponse build() {
      return new NearbyPointResponse(this);
    }
  }
}
