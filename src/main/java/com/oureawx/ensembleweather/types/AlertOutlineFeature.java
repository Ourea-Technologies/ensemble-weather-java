// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * One alert's outline, a GeoJSON {@code Feature}.
 *
 * <p>GeoJSON, passed through untouched (BEHAVIOR.md 3.0): {@code properties} and {@code
 * coordinates} are the API's JSON exactly, wire keys and all, ready for a map library.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AlertOutlineFeature}.
 */
public final class AlertOutlineFeature extends Model {
  private final String type;
  private final AlertOutlineGeometry geometry;
  private final JsonValue properties;

  private AlertOutlineFeature(Builder b) {
    this.type = require(b.type, "type");
    this.geometry = require(b.geometry, "geometry");
    this.properties = require(b.properties, "properties");
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
    b.type = this.type;
    b.geometry = this.geometry;
    b.properties = this.properties;
    return b;
  }

  /**
   * The {@code type} value. Always {@code Feature} today.
   *
   * @return the {@code type} value
   */
  public String type() {
    return type;
  }

  /**
   * The {@code geometry} value.
   *
   * @return the {@code geometry} value
   */
  public AlertOutlineGeometry geometry() {
    return geometry;
  }

  /**
   * The {@code properties} value.
   *
   * @return the {@code properties} value
   */
  public JsonValue properties() {
    return properties;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {type, geometry, properties};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"type", "geometry", "properties"};

  /** Builds a {@link AlertOutlineFeature}. */
  public static final class Builder {
    private @Nullable String type;
    private @Nullable AlertOutlineGeometry geometry;
    private @Nullable JsonValue properties;

    private Builder() {}

    /**
     * Sets {@link AlertOutlineFeature#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link AlertOutlineFeature#geometry()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder geometry(AlertOutlineGeometry value) {
      this.geometry = value;
      return this;
    }

    /**
     * Sets {@link AlertOutlineFeature#properties()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder properties(JsonValue value) {
      this.properties = value;
      return this;
    }

    /**
     * Builds the {@link AlertOutlineFeature}.
     *
     * @return the immutable AlertOutlineFeature
     * @throws IllegalStateException if a required value was not set
     */
    public AlertOutlineFeature build() {
      return new AlertOutlineFeature(this);
    }
  }
}
