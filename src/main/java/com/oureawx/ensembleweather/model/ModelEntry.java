// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * One contributing model.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ModelEntry}.
 */
public final class ModelEntry extends Model {
  private final String id;
  private final @Nullable Instant cycle;
  private final @Nullable Integer members;

  private ModelEntry(Builder b) {
    this.id = require(b.id, "id");
    this.cycle = b.cycle;
    this.members = b.members;
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
    b.id = this.id;
    b.cycle = this.cycle;
    b.members = this.members;
    return b;
  }

  /**
   * The model id ({@code aifs}, {@code aigefs}, {@code gefs}, {@code ifs_ens}, {@code rrfs}; treat
   * others as additive).
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * The model's own cycle init (per-model-latest). Omitted when it was not read.
   *
   * @return the {@code cycle} value, or empty when the response omits it
   */
  public Optional<Instant> cycle() {
    return Optional.ofNullable(cycle);
  }

  /**
   * How many of its members the forecast carries (in {@code model_run}); omitted in {@code /runs}
   * {@code served}.
   *
   * @return the {@code members} value, or empty when the response omits it
   */
  public OptionalInt members() {
    return members == null ? OptionalInt.empty() : OptionalInt.of(members);
  }

  @Override
  Object[] values() {
    return new Object[] {id, cycle, members};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"id", "cycle", "members"};

  /** Builds a {@link ModelEntry}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable Instant cycle;
    private @Nullable Integer members;

    private Builder() {}

    /**
     * Sets {@link ModelEntry#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link ModelEntry#cycle()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cycle(@Nullable Instant value) {
      this.cycle = value;
      return this;
    }

    /**
     * Sets {@link ModelEntry#members()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder members(@Nullable Integer value) {
      this.members = value;
      return this;
    }

    /**
     * Builds the {@link ModelEntry}.
     *
     * @return the immutable ModelEntry
     * @throws IllegalStateException if a required value was not set
     */
    public ModelEntry build() {
      return new ModelEntry(this);
    }
  }
}
