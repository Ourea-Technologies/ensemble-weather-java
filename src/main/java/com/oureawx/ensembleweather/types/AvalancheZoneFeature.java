// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * One forecast zone, a GeoJSON {@code Feature}.
 *
 * <p>GeoJSON, passed through untouched (BEHAVIOR.md 3.0): {@code properties} and {@code
 * coordinates} are the API's JSON exactly, wire keys and all, ready for a map library.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AvalancheZoneFeature}.
 */
public final class AvalancheZoneFeature extends Model {
  private final String type;
  private final long id;
  private final AvalancheZoneGeometry geometry;
  private final JsonValue properties;

  private AvalancheZoneFeature(Builder b) {
    this.type = require(b.type, "type");
    this.id = require(b.id, "id");
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
    b.id = this.id;
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
   * The zone's id, as {@code properties.zone_id}.
   *
   * @return the {@code id} value
   */
  public long id() {
    return id;
  }

  /**
   * The {@code geometry} value.
   *
   * @return the {@code geometry} value
   */
  public AvalancheZoneGeometry geometry() {
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
    return new Object[] {type, id, geometry, properties};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"type", "id", "geometry", "properties"};

  /** Builds a {@link AvalancheZoneFeature}. */
  public static final class Builder {
    private @Nullable String type;
    private @Nullable Long id;
    private @Nullable AvalancheZoneGeometry geometry;
    private @Nullable JsonValue properties;

    private Builder() {}

    /**
     * Sets {@link AvalancheZoneFeature#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link AvalancheZoneFeature#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(long value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link AvalancheZoneFeature#geometry()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder geometry(AvalancheZoneGeometry value) {
      this.geometry = value;
      return this;
    }

    /**
     * Sets {@link AvalancheZoneFeature#properties()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder properties(JsonValue value) {
      this.properties = value;
      return this;
    }

    /**
     * Builds the {@link AvalancheZoneFeature}.
     *
     * @return the immutable AvalancheZoneFeature
     * @throws IllegalStateException if a required value was not set
     */
    public AvalancheZoneFeature build() {
      return new AvalancheZoneFeature(this);
    }
  }
}
