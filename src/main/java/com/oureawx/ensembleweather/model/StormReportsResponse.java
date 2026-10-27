// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A StormReportsResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code StormReportsResponse}.
 */
public final class StormReportsResponse extends Model {
  private final List<StormReportCard> reports;
  private final int count;
  private final ResponseMetadata metadata;

  private StormReportsResponse(Builder b) {
    this.reports = List.copyOf(b.reports);
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
    b.reports = this.reports;
    b.count = this.count;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code reports} value.
   *
   * @return the {@code reports} values; empty when the response has none
   */
  public List<StormReportCard> reports() {
    return reports;
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
    return new Object[] {reports, count};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"reports", "count"};

  /** Builds a {@link StormReportsResponse}. */
  public static final class Builder {
    private List<StormReportCard> reports = List.of();
    private @Nullable Integer count;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link StormReportsResponse#reports()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder reports(List<StormReportCard> values) {
      this.reports = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link StormReportsResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link StormReportsResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link StormReportsResponse}.
     *
     * @return the immutable StormReportsResponse
     * @throws IllegalStateException if a required value was not set
     */
    public StormReportsResponse build() {
      return new StormReportsResponse(this);
    }
  }
}
