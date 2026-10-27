// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One named elevation point of a location.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code LocationElevation}.
 */
public final class LocationElevation extends Model {
  private final String name;
  private final double elevation;
  private final @Nullable Boolean isDefault;

  private LocationElevation(Builder b) {
    this.name = require(b.name, "name");
    this.elevation = require(b.elevation, "elevation");
    this.isDefault = b.isDefault;
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
    b.elevation = this.elevation;
    b.isDefault = this.isDefault;
    return b;
  }

  /**
   * The {@code name} value.
   *
   * @return the {@code name} value
   */
  public String name() {
    return name;
  }

  /**
   * In the caller's length units (m, ft), to 0.1.
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * Present (true) on the primary only.
   *
   * @return the {@code default} value, or empty when the response omits it
   */
  public Optional<Boolean> isDefault() {
    return Optional.ofNullable(isDefault);
  }

  @Override
  Object[] values() {
    return new Object[] {name, elevation, isDefault};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"name", "elevation", "isDefault"};

  /** Builds a {@link LocationElevation}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable Double elevation;
    private @Nullable Boolean isDefault;

    private Builder() {}

    /**
     * Sets {@link LocationElevation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link LocationElevation#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link LocationElevation#isDefault()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder isDefault(@Nullable Boolean value) {
      this.isDefault = value;
      return this;
    }

    /**
     * Builds the {@link LocationElevation}.
     *
     * @return the immutable LocationElevation
     * @throws IllegalStateException if a required value was not set
     */
    public LocationElevation build() {
      return new LocationElevation(this);
    }
  }
}
