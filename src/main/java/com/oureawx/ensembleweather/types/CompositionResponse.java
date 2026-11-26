// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * {@code GET /api/v1/composition}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CompositionResponse}.
 */
public final class CompositionResponse extends Model {
  private final Instant initializedAt;
  private final List<ModelEntry> models;
  private final int modelCount;
  private final int totalMembers;
  private final ResponseMetadata metadata;

  private CompositionResponse(Builder b) {
    this.initializedAt = require(b.initializedAt, "initialized_at");
    this.models = List.copyOf(b.models);
    this.modelCount = require(b.modelCount, "model_count");
    this.totalMembers = require(b.totalMembers, "total_members");
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
    b.initializedAt = this.initializedAt;
    b.models = this.models;
    b.modelCount = this.modelCount;
    b.totalMembers = this.totalMembers;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The reference init: the newest contributing cycle, a forecast's {@code
   * model_run.initialized_at}.
   *
   * @return the {@code initialized_at} value
   */
  public Instant initializedAt() {
    return initializedAt;
  }

  /**
   * Each contributing model, its own cycle and its members ({@code members} absent only when the
   * cycle's member count is not known).
   *
   * @return the {@code models} values; empty when the response has none
   */
  public List<ModelEntry> models() {
    return models;
  }

  /**
   * Entries in {@code models}.
   *
   * @return the {@code model_count} value
   */
  public int modelCount() {
    return modelCount;
  }

  /**
   * The sum of {@code models[].members}: the members the blend draws on. Not counting the RRFS
   * display-only members.
   *
   * @return the {@code total_members} value
   */
  public int totalMembers() {
    return totalMembers;
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
    return new Object[] {initializedAt, models, modelCount, totalMembers};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"initializedAt", "models", "modelCount", "totalMembers"};

  /** Builds a {@link CompositionResponse}. */
  public static final class Builder {
    private @Nullable Instant initializedAt;
    private List<ModelEntry> models = List.of();
    private @Nullable Integer modelCount;
    private @Nullable Integer totalMembers;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link CompositionResponse#initializedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder initializedAt(Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link CompositionResponse#models()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder models(List<ModelEntry> values) {
      this.models = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CompositionResponse#modelCount()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder modelCount(int value) {
      this.modelCount = value;
      return this;
    }

    /**
     * Sets {@link CompositionResponse#totalMembers()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder totalMembers(int value) {
      this.totalMembers = value;
      return this;
    }

    /**
     * Sets {@link CompositionResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link CompositionResponse}.
     *
     * @return the immutable CompositionResponse
     * @throws IllegalStateException if a required value was not set
     */
    public CompositionResponse build() {
      return new CompositionResponse(this);
    }
  }
}
