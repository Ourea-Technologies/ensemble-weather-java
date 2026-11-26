// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * The newest snowpack run among the spots served. Absent when {@code spots} is empty.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackRun}.
 */
public final class SnowpackRun extends Model {
  private final Instant initializedAt;
  private final Instant generatedAt;
  private final SnowpackModel model;

  private SnowpackRun(Builder b) {
    this.initializedAt = require(b.initializedAt, "initialized_at");
    this.generatedAt = require(b.generatedAt, "generated_at");
    this.model = require(b.model, "model");
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
    b.generatedAt = this.generatedAt;
    b.model = this.model;
    return b;
  }

  /**
   * The blend cycle the snowpack model was driven with.
   *
   * @return the {@code initialized_at} value
   */
  public Instant initializedAt() {
    return initializedAt;
  }

  /**
   * The {@code generated_at} value.
   *
   * @return the {@code generated_at} value
   */
  public Instant generatedAt() {
    return generatedAt;
  }

  /**
   * The {@code model} value.
   *
   * @return the {@code model} value
   */
  public SnowpackModel model() {
    return model;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {initializedAt, generatedAt, model};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"initializedAt", "generatedAt", "model"};

  /** Builds a {@link SnowpackRun}. */
  public static final class Builder {
    private @Nullable Instant initializedAt;
    private @Nullable Instant generatedAt;
    private @Nullable SnowpackModel model;

    private Builder() {}

    /**
     * Sets {@link SnowpackRun#initializedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder initializedAt(Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link SnowpackRun#generatedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder generatedAt(Instant value) {
      this.generatedAt = value;
      return this;
    }

    /**
     * Sets {@link SnowpackRun#model()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder model(SnowpackModel value) {
      this.model = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackRun}.
     *
     * @return the immutable SnowpackRun
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackRun build() {
      return new SnowpackRun(this);
    }
  }
}
