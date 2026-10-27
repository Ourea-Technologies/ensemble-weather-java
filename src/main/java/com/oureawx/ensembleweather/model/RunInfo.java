// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A model-run init at least two models completed.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code RunInfo}.
 */
public final class RunInfo extends Model {
  private final Instant initializedAt;

  private RunInfo(Builder b) {
    this.initializedAt = require(b.initializedAt, "initialized_at");
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
    return b;
  }

  /**
   * The {@code initialized_at} value.
   *
   * @return the {@code initialized_at} value
   */
  public Instant initializedAt() {
    return initializedAt;
  }

  @Override
  Object[] values() {
    return new Object[] {initializedAt};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"initializedAt"};

  /** Builds a {@link RunInfo}. */
  public static final class Builder {
    private @Nullable Instant initializedAt;

    private Builder() {}

    /**
     * Sets {@link RunInfo#initializedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder initializedAt(Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Builds the {@link RunInfo}.
     *
     * @return the immutable RunInfo
     * @throws IllegalStateException if a required value was not set
     */
    public RunInfo build() {
      return new RunInfo(this);
    }
  }
}
