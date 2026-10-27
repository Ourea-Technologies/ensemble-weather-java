// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * What a forecast request serves now; {@code null} when no cycle is servable.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ServedRun}.
 */
public final class ServedRun extends Model {
  private final Instant initializedAt;
  private final List<ModelEntry> models;

  private ServedRun(Builder b) {
    this.initializedAt = require(b.initializedAt, "initialized_at");
    this.models = List.copyOf(b.models);
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
   * Each contributing model and its own cycle ({@code (id, cycle)}), as {@code model_run.models}.
   *
   * @return the {@code models} values; empty when the response has none
   */
  public List<ModelEntry> models() {
    return models;
  }

  @Override
  Object[] values() {
    return new Object[] {initializedAt, models};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"initializedAt", "models"};

  /** Builds a {@link ServedRun}. */
  public static final class Builder {
    private @Nullable Instant initializedAt;
    private List<ModelEntry> models = List.of();

    private Builder() {}

    /**
     * Sets {@link ServedRun#initializedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder initializedAt(Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link ServedRun#models()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder models(List<ModelEntry> values) {
      this.models = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link ServedRun}.
     *
     * @return the immutable ServedRun
     * @throws IllegalStateException if a required value was not set
     */
    public ServedRun build() {
      return new ServedRun(this);
    }
  }
}
