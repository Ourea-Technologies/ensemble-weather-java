// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One of a location's elevation points, as {@code /nearby}, the list rows and the forecast's {@code
 * location.elevations} serve it.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ElevationSummary}.
 */
public final class ElevationSummary extends Model {
  private final String name;
  private final @Nullable String displayName;
  private final double height;
  private final @Nullable Boolean isDefault;

  private ElevationSummary(Builder b) {
    this.name = require(b.name, "name");
    this.displayName = b.displayName;
    this.height = require(b.height, "elevation");
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
    b.displayName = this.displayName;
    b.height = this.height;
    b.isDefault = this.isDefault;
    return b;
  }

  /**
   * The elevation's id: the name to pass in {@code ?elevations=} (case, {@code -}, {@code _} and
   * spaces ignored). Stable; not a display name ({@code base}, {@code low_cascade_village_lift}).
   *
   * @return the {@code name} value
   */
  public String name() {
    return name;
  }

  /**
   * What the place is called, for display ("K-1 Lodge", "FourRunner Quad top"). Absent where the
   * catalog has no name for the point: show {@code name} as before. Never accepted by {@code
   * ?elevations=}.
   *
   * @return the {@code display_name} value, or empty when the response omits it
   */
  public Optional<String> displayName() {
    return Optional.ofNullable(displayName);
  }

  /**
   * (JSON {@code elevation}) In the caller's length units (m, ft), to 0.1.
   *
   * @return the {@code elevation} value
   */
  public double height() {
    return height;
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
  Object[] fieldValues() {
    return new Object[] {name, displayName, height, isDefault};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"name", "displayName", "height", "isDefault"};

  /** Builds a {@link ElevationSummary}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable String displayName;
    private @Nullable Double height;
    private @Nullable Boolean isDefault;

    private Builder() {}

    /**
     * Sets {@link ElevationSummary#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link ElevationSummary#displayName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder displayName(@Nullable String value) {
      this.displayName = value;
      return this;
    }

    /**
     * Sets {@link ElevationSummary#height()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder height(double value) {
      this.height = value;
      return this;
    }

    /**
     * Sets {@link ElevationSummary#isDefault()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder isDefault(@Nullable Boolean value) {
      this.isDefault = value;
      return this;
    }

    /**
     * Builds the {@link ElevationSummary}.
     *
     * @return the immutable ElevationSummary
     * @throws IllegalStateException if a required value was not set
     */
    public ElevationSummary build() {
      return new ElevationSummary(this);
    }
  }
}
