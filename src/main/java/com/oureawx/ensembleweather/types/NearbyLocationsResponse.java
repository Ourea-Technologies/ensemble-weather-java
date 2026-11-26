// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A NearbyLocationsResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code NearbyLocationsResponse}.
 */
public final class NearbyLocationsResponse extends Model {
  private final LocationSummary origin;
  private final double radiusKm;
  private final int count;
  private final List<NearbyLocation> items;
  private final ResponseMetadata metadata;

  private NearbyLocationsResponse(Builder b) {
    this.origin = require(b.origin, "location");
    this.radiusKm = require(b.radiusKm, "radius_km");
    this.count = require(b.count, "count");
    this.items = List.copyOf(b.items);
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
    b.origin = this.origin;
    b.radiusKm = this.radiusKm;
    b.count = this.count;
    b.items = this.items;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * (JSON {@code location}) The {@code location} value.
   *
   * @return the {@code location} value
   */
  public LocationSummary origin() {
    return origin;
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
   * (JSON {@code locations}) Nearest first.
   *
   * @return the {@code locations} values; empty when the response has none
   */
  public List<NearbyLocation> items() {
    return items;
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
    return new Object[] {origin, radiusKm, count, items};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"origin", "radiusKm", "count", "items"};

  /** Builds a {@link NearbyLocationsResponse}. */
  public static final class Builder {
    private @Nullable LocationSummary origin;
    private @Nullable Double radiusKm;
    private @Nullable Integer count;
    private List<NearbyLocation> items = List.of();
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link NearbyLocationsResponse#origin()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder origin(LocationSummary value) {
      this.origin = value;
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
     * Sets {@link NearbyLocationsResponse#items()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder items(List<NearbyLocation> values) {
      this.items = List.copyOf(values);
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
