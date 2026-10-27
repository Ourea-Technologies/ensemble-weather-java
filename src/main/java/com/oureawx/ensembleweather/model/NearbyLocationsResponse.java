// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A NearbyLocationsResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code NearbyLocationsResponse}.
 */
public final class NearbyLocationsResponse extends Model {
  private final LocationSummary location;
  private final double radiusKm;
  private final int count;
  private final List<NearbyLocation> locations;
  private final ResponseMetadata metadata;

  private NearbyLocationsResponse(Builder b) {
    this.location = require(b.location, "location");
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
    b.location = this.location;
    b.radiusKm = this.radiusKm;
    b.count = this.count;
    b.locations = this.locations;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code location} value.
   *
   * @return the {@code location} value
   */
  public LocationSummary location() {
    return location;
  }

  /**
   * The radius applied, after the cap.
   *
   * @return the {@code radius_km} value
   */
  public double radiusKm() {
    return radiusKm;
  }

  /**
   * Neighbours in this response (the location itself is not counted).
   *
   * @return the {@code count} value
   */
  public int count() {
    return count;
  }

  /**
   * Nearest first.
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
    return new Object[] {location, radiusKm, count, locations};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"location", "radiusKm", "count", "locations"};

  /** Builds a {@link NearbyLocationsResponse}. */
  public static final class Builder {
    private @Nullable LocationSummary location;
    private @Nullable Double radiusKm;
    private @Nullable Integer count;
    private List<NearbyLocation> locations = List.of();
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link NearbyLocationsResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationSummary value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocationsResponse#radiusKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder radiusKm(double value) {
      this.radiusKm = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocationsResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocationsResponse#locations()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder locations(List<NearbyLocation> values) {
      this.locations = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link NearbyLocationsResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link NearbyLocationsResponse}.
     *
     * @return the immutable NearbyLocationsResponse
     * @throws IllegalStateException if a required value was not set
     */
    public NearbyLocationsResponse build() {
      return new NearbyLocationsResponse(this);
    }
  }
}
