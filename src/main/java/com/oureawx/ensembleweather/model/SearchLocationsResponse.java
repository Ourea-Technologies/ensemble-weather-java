// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A SearchLocationsResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SearchLocationsResponse}.
 */
public final class SearchLocationsResponse extends Model {
  private final List<SearchHit> items;
  private final int count;
  private final ResponseMetadata metadata;

  private SearchLocationsResponse(Builder b) {
    this.items = List.copyOf(b.items);
    this.count = require(b.count, "count");
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
    b.items = this.items;
    b.count = this.count;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code items} value.
   *
   * @return the {@code items} values; empty when the response has none
   */
  public List<SearchHit> items() {
    return items;
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
    return new Object[] {items, count};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"items", "count"};

  /** Builds a {@link SearchLocationsResponse}. */
  public static final class Builder {
    private List<SearchHit> items = List.of();
    private @Nullable Integer count;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link SearchLocationsResponse#items()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder items(List<SearchHit> values) {
      this.items = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SearchLocationsResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link SearchLocationsResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link SearchLocationsResponse}.
     *
     * @return the immutable SearchLocationsResponse
     * @throws IllegalStateException if a required value was not set
     */
    public SearchLocationsResponse build() {
      return new SearchLocationsResponse(this);
    }
  }
}
