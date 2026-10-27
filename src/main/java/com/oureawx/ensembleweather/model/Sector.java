// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * A clockwise arc on the compass, degrees. {@code to} may be numerically smaller than {@code from}
 * because the arc crosses north; the width is {@code (to - from) mod 360}, never {@code to - from},
 * and a bearing {@code b} lies inside it when {@code (b - from) mod 360 <= (to - from) mod 360}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code Sector}.
 */
public final class Sector extends Model {
  private final double from;
  private final double to;

  private Sector(Builder b) {
    this.from = require(b.from, "from");
    this.to = require(b.to, "to");
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
    b.from = this.from;
    b.to = this.to;
    return b;
  }

  /**
   * The {@code from} value.
   *
   * @return the {@code from} value
   */
  public double from() {
    return from;
  }

  /**
   * The {@code to} value.
   *
   * @return the {@code to} value
   */
  public double to() {
    return to;
  }

  @Override
  Object[] values() {
    return new Object[] {from, to};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"from", "to"};

  /** Builds a {@link Sector}. */
  public static final class Builder {
    private @Nullable Double from;
    private @Nullable Double to;

    private Builder() {}

    /**
     * Sets {@link Sector#from()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder from(double value) {
      this.from = value;
      return this;
    }

    /**
     * Sets {@link Sector#to()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder to(double value) {
      this.to = value;
      return this;
    }

    /**
     * Builds the {@link Sector}.
     *
     * @return the immutable Sector
     * @throws IllegalStateException if a required value was not set
     */
    public Sector build() {
      return new Sector(this);
    }
  }
}
