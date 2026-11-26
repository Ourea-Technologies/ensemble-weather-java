// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A SnowTrendGrid from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowTrendGrid}.
 */
public final class SnowTrendGrid extends Model {
  private final String source;
  private final List<SnowTrendRun> runs;

  private SnowTrendGrid(Builder b) {
    this.source = require(b.source, "source");
    this.runs = List.copyOf(b.runs);
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
    b.source = this.source;
    b.runs = this.runs;
    return b;
  }

  /**
   * {@code ourea}, or a model id.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * Oldest first; the last is the newest. Empty when none is recorded yet.
   *
   * @return the {@code runs} values; empty when the response has none
   */
  public List<SnowTrendRun> runs() {
    return runs;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {source, runs};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"source", "runs"};

  /** Builds a {@link SnowTrendGrid}. */
  public static final class Builder {
    private @Nullable String source;
    private List<SnowTrendRun> runs = List.of();

    private Builder() {}

    /**
     * Sets {@link SnowTrendGrid#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendGrid#runs()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder runs(List<SnowTrendRun> values) {
      this.runs = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link SnowTrendGrid}.
     *
     * @return the immutable SnowTrendGrid
     * @throws IllegalStateException if a required value was not set
     */
    public SnowTrendGrid build() {
      return new SnowTrendGrid(this);
    }
  }
}
