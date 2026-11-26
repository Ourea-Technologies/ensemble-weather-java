// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * A GeoJSON {@code Polygon} or {@code MultiPolygon}. A position is {@code [longitude, latitude]} in
 * degrees (WGS84) with at most three decimals; every ring is closed (its first position repeated
 * last). A {@code Polygon}'s {@code coordinates} is an array of rings (the outer one first, then
 * any holes); a {@code MultiPolygon}'s is an array of such polygons.
 *
 * <p>GeoJSON, passed through untouched (BEHAVIOR.md 3.0): {@code properties} and {@code
 * coordinates} are the API's JSON exactly, wire keys and all, ready for a map library.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AlertOutlineGeometry}.
 */
public final class AlertOutlineGeometry extends Model {
  private final String type;
  private final JsonValue coordinates;

  private AlertOutlineGeometry(Builder b) {
    this.type = require(b.type, "type");
    this.coordinates = require(b.coordinates, "coordinates");
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
    b.coordinates = this.coordinates;
    return b;
  }

  /**
   * The {@code type} value. One of {@code Polygon}, {@code MultiPolygon}; read it as a string,
   * since the API may add values.
   *
   * @return the {@code type} value
   */
  public String type() {
    return type;
  }

  /**
   * The {@code coordinates} value.
   *
   * @return the {@code coordinates} value
   */
  public JsonValue coordinates() {
    return coordinates;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {type, coordinates};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"type", "coordinates"};

  /** Builds a {@link AlertOutlineGeometry}. */
  public static final class Builder {
    private @Nullable String type;
    private @Nullable JsonValue coordinates;

    private Builder() {}

    /**
     * Sets {@link AlertOutlineGeometry#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link AlertOutlineGeometry#coordinates()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder coordinates(JsonValue value) {
      this.coordinates = value;
      return this;
    }

    /**
     * Builds the {@link AlertOutlineGeometry}.
     *
     * @return the immutable AlertOutlineGeometry
     * @throws IllegalStateException if a required value was not set
     */
    public AlertOutlineGeometry build() {
      return new AlertOutlineGeometry(this);
    }
  }
}
