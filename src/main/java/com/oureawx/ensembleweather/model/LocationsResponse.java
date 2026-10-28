// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A LocationsResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code LocationsResponse}.
 */
public final class LocationsResponse extends Model {
  private final String state;
  private final int count;
  private final List<LocationSummary> locations;
  private final @Nullable String nextCursor;
  private final ResponseMetadata metadata;

  private LocationsResponse(Builder b) {
    this.state = require(b.state, "state");
    this.count = require(b.count, "count");
    this.locations = List.copyOf(b.locations);
    this.nextCursor = b.nextCursor;
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
    b.state = this.state;
    b.count = this.count;
    b.locations = this.locations;
    b.nextCursor = this.nextCursor;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * Display label for the scope: {@code Oregon}, {@code Washington}, {@code California} for a
   * single-state query, or {@code all} when hitting {@code /api/v1/locations}.
   *
   * @return the {@code state} value
   */
  public String state() {
    return state;
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
  public List<LocationSummary> locations() {
    return locations;
  }

  /**
   * The {@code next_cursor} value.
   *
   * @return the {@code next_cursor} value, or empty when the response omits it
   */
  public Optional<String> nextCursor() {
    return Optional.ofNullable(nextCursor);
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
    return new Object[] {state, count, locations, nextCursor};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"state", "count", "locations", "nextCursor"};

  /** Builds a {@link LocationsResponse}. */
  public static final class Builder {
    private @Nullable String state;
    private @Nullable Integer count;
    private List<LocationSummary> locations = List.of();
    private @Nullable String nextCursor;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link LocationsResponse#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link LocationsResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link LocationsResponse#locations()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder locations(List<LocationSummary> values) {
      this.locations = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link LocationsResponse#nextCursor()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder nextCursor(@Nullable String value) {
      this.nextCursor = value;
      return this;
    }

    /**
     * Sets {@link LocationsResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link LocationsResponse}.
     *
     * @return the immutable LocationsResponse
     * @throws IllegalStateException if a required value was not set
     */
    public LocationsResponse build() {
      return new LocationsResponse(this);
    }
  }
}
