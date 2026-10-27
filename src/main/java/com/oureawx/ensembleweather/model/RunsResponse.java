// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A RunsResponse from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code RunsResponse}.
 */
public final class RunsResponse extends Model {
  private final @Nullable ServedRun served;
  private final List<RunInfo> runs;
  private final int count;
  private final ResponseMetadata metadata;

  private RunsResponse(Builder b) {
    this.served = b.served;
    this.runs = List.copyOf(b.runs);
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
    b.served = this.served;
    b.runs = this.runs;
    b.count = this.count;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * What a forecast request serves now; {@code null} when no cycle is servable.
   *
   * @return the {@code served} value, or empty when the response omits it
   */
  public Optional<ServedRun> served() {
    return Optional.ofNullable(served);
  }

  /**
   * Init times at least two models completed, newest first.
   *
   * @return the {@code runs} values; empty when the response has none
   */
  public List<RunInfo> runs() {
    return runs;
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
    return new Object[] {served, runs, count};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"served", "runs", "count"};

  /** Builds a {@link RunsResponse}. */
  public static final class Builder {
    private @Nullable ServedRun served;
    private List<RunInfo> runs = List.of();
    private @Nullable Integer count;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link RunsResponse#served()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder served(@Nullable ServedRun value) {
      this.served = value;
      return this;
    }

    /**
     * Sets {@link RunsResponse#runs()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder runs(List<RunInfo> values) {
      this.runs = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link RunsResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link RunsResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link RunsResponse}.
     *
     * @return the immutable RunsResponse
     * @throws IllegalStateException if a required value was not set
     */
    public RunsResponse build() {
      return new RunsResponse(this);
    }
  }
}
