// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A SnowTrendRun from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class SnowTrendRun extends Model {
  private final boolean backfilled;
  private final Instant initializedAt;
  private final List<Optional<SnowTrendDay>> days;

  private SnowTrendRun(Builder b) {
    this.backfilled = require(b.backfilled, "backfilled");
    this.initializedAt = require(b.initializedAt, "initialized_at");
    this.days = List.copyOf(b.days);
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
    b.backfilled = this.backfilled;
    b.initializedAt = this.initializedAt;
    b.days = this.days;
    return b;
  }

  /**
   * {@code true}: re-rendered after the fact from the pinned run ({@code ?run=<initialized_at>}) by
   * the post-deploy backfill -- today's calibration and no live nowcast, so it can differ slightly
   * from what was served then. {@code false}: recorded while it was served, or the live newest run.
   * A served recording always replaces a backfilled one.
   *
   * @return the {@code backfilled} value
   */
  public boolean backfilled() {
    return backfilled;
  }

  /**
   * {@code ourea}: the served body's {@code model_run.initialized_at}. A model: its own cycle.
   *
   * @return the {@code initialized_at} value
   */
  public Instant initializedAt() {
    return initializedAt;
  }

  /**
   * One entry per {@code dates} entry, in order; {@code null} where the run has no value.
   *
   * @return the {@code days} values; empty when the response has none
   */
  public List<Optional<SnowTrendDay>> days() {
    return days;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {backfilled, initializedAt, days};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"backfilled", "initializedAt", "days"};

  /** Builds a {@link SnowTrendRun}. */
  public static final class Builder {
    private @Nullable Boolean backfilled;
    private @Nullable Instant initializedAt;
    private List<Optional<SnowTrendDay>> days = List.of();

    private Builder() {}

    /**
     * Sets {@link SnowTrendRun#backfilled()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder backfilled(boolean value) {
      this.backfilled = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendRun#initializedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder initializedAt(Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendRun#days()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder days(List<Optional<SnowTrendDay>> values) {
      this.days = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link SnowTrendRun}.
     *
     * @return the immutable SnowTrendRun
     * @throws IllegalStateException if a required value was not set
     */
    public SnowTrendRun build() {
      return new SnowTrendRun(this);
    }
  }
}
