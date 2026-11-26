// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * A GeoJSON {@code MultiPolygon}: an array of polygons, each an array of rings (the outer one
 * first, then any holes), each ring closed. A position is {@code [longitude, latitude]} in degrees
 * (WGS84) with at most four decimals.
 *
 * <p>GeoJSON, passed through untouched (BEHAVIOR.md 3.0): {@code properties} and {@code
 * coordinates} are the API's JSON exactly, wire keys and all, ready for a map library.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AvalancheZoneGeometry}.
 */
public final class AvalancheZoneGeometry extends Model {
  private final String type;
  private final JsonValue coordinates;

  private AvalancheZoneGeometry(Builder b) {
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
   * The {@code type} value. Always {@code MultiPolygon} today.
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

  /** Builds a {@link AvalancheZoneGeometry}. */
  public static final class Builder {
    private @Nullable String type;
    private @Nullable JsonValue coordinates;

    private Builder() {}

    /**
     * Sets {@link AvalancheZoneGeometry#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link AvalancheZoneGeometry#coordinates()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder coordinates(JsonValue value) {
      this.coordinates = value;
      return this;
    }

    /**
     * Builds the {@link AvalancheZoneGeometry}.
     *
     * @return the immutable AvalancheZoneGeometry
     * @throws IllegalStateException if a required value was not set
     */
    public AvalancheZoneGeometry build() {
      return new AvalancheZoneGeometry(this);
    }
  }
}
