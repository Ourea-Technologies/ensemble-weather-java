// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The point the grids are for.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class SnowTrendResponseElevation extends Model {
  private final @Nullable String name;
  private final @Nullable String displayName;
  private final double height;

  private SnowTrendResponseElevation(Builder b) {
    this.name = b.name;
    this.displayName = b.displayName;
    this.height = require(b.height, "elevation");
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
    b.name = this.name;
    b.displayName = this.displayName;
    b.height = this.height;
    return b;
  }

  /**
   * The named elevation; {@code null} for a location with one point.
   *
   * @return the {@code name} value, or empty when the response omits it
   */
  public Optional<String> name() {
    return Optional.ofNullable(name);
  }

  /**
   * What that elevation is called, as {@code ElevationSummary.display_name}; absent where it has no
   * name.
   *
   * @return the {@code display_name} value, or empty when the response omits it
   */
  public Optional<String> displayName() {
    return Optional.ofNullable(displayName);
  }

  /**
   * (JSON {@code elevation}) Its elevation, in m (metric) or ft (imperial).
   *
   * @return the {@code elevation} value
   */
  public double height() {
    return height;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {name, displayName, height};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"name", "displayName", "height"};

  /** Builds a {@link SnowTrendResponseElevation}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable String displayName;
    private @Nullable Double height;

    private Builder() {}

    /**
     * Sets {@link SnowTrendResponseElevation#name()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder name(@Nullable String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendResponseElevation#displayName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder displayName(@Nullable String value) {
      this.displayName = value;
      return this;
    }

    /**
     * Sets {@link SnowTrendResponseElevation#height()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder height(double value) {
      this.height = value;
      return this;
    }

    /**
     * Builds the {@link SnowTrendResponseElevation}.
     *
     * @return the immutable SnowTrendResponseElevation
     * @throws IllegalStateException if a required value was not set
     */
    public SnowTrendResponseElevation build() {
      return new SnowTrendResponseElevation(this);
    }
  }
}
