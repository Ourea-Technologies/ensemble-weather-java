// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

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
  private final int alertCount;
  private final Instant asOf;
  private final List<ActiveAlert> alerts;
  private final ResponseAttribution attribution;
  private final ResponseMetadata metadata;

  private AlertsResponse(Builder b) {
    this.location = require(b.location, "location");
    this.alertCount = require(b.alertCount, "alert_count");
    this.asOf = require(b.asOf, "as_of");
    this.alerts = List.copyOf(b.alerts);
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
    b.alertCount = this.alertCount;
    b.asOf = this.asOf;
    b.alerts = this.alerts;
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
   * The {@code alert_count} value.
   *
   * @return the {@code alert_count} value
   */
  public int alertCount() {
    return alertCount;
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
   * The {@code alerts} value.
   *
   * @return the {@code alerts} values; empty when the response has none
   */
  public List<ActiveAlert> alerts() {
    return alerts;
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
  Object[] values() {
    return new Object[] {location, alertCount, asOf, alerts, attribution};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"location", "alertCount", "asOf", "alerts", "attribution"};

  /** Builds a {@link AlertsResponse}. */
  public static final class Builder {
    private @Nullable LocationRef location;
    private @Nullable Integer alertCount;
    private @Nullable Instant asOf;
    private List<ActiveAlert> alerts = List.of();
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
     * Sets {@link AlertsResponse#alertCount()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder alertCount(int value) {
      this.alertCount = value;
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
     * Sets {@link AlertsResponse#alerts()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder alerts(List<ActiveAlert> values) {
      this.alerts = List.copyOf(values);
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
