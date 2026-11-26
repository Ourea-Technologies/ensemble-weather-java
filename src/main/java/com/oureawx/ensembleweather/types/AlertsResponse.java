// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A AlertsResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AlertsResponse}.
 */
public final class AlertsResponse extends Model {
  private final LocationRef location;
  private final int count;
  private final Instant asOf;
  private final List<ActiveAlert> items;
  private final ResponseAttribution attribution;
  private final ResponseMetadata metadata;

  private AlertsResponse(Builder b) {
    this.location = require(b.location, "location");
    this.count = require(b.count, "alert_count");
    this.asOf = require(b.asOf, "as_of");
    this.items = List.copyOf(b.items);
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
    b.count = this.count;
    b.asOf = this.asOf;
    b.items = this.items;
    b.attribution = this.attribution;
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
   * (JSON {@code alert_count}) The {@code alert_count} value.
   *
   * @return the {@code alert_count} value
   */
  public int count() {
    return count;
  }

  /**
   * Snapshot timestamp (see /current alerts_as_of).
   *
   * @return the {@code as_of} value
   */
  public Instant asOf() {
    return asOf;
  }

  /**
   * (JSON {@code alerts}) The {@code alerts} value.
   *
   * @return the {@code alerts} values; empty when the response has none
   */
  public List<ActiveAlert> items() {
    return items;
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
    return new Object[] {location, count, asOf, items, attribution};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"location", "count", "asOf", "items", "attribution"};

  /** Builds a {@link AlertsResponse}. */
  public static final class Builder {
    private @Nullable LocationRef location;
    private @Nullable Integer count;
    private @Nullable Instant asOf;
    private List<ActiveAlert> items = List.of();
    private @Nullable ResponseAttribution attribution;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link AlertsResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link AlertsResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link AlertsResponse#asOf()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder asOf(Instant value) {
      this.asOf = value;
      return this;
    }

    /**
     * Sets {@link AlertsResponse#items()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder items(List<ActiveAlert> values) {
      this.items = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link AlertsResponse#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(ResponseAttribution value) {
      this.attribution = value;
      return this;
    }

    /**
     * Sets {@link AlertsResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link AlertsResponse}.
     *
     * @return the immutable AlertsResponse
     * @throws IllegalStateException if a required value was not set
     */
    public AlertsResponse build() {
      return new AlertsResponse(this);
    }
  }
}
