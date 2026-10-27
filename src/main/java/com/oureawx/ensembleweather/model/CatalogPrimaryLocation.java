// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * A station's parent location, by reference.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogPrimaryLocation}.
 */
public final class CatalogPrimaryLocation extends Model {
  private final String slug;
  private final String name;
  private final double lat;
  private final double lon;
  private final double elevationM;
  private final String type;

  private CatalogPrimaryLocation(Builder b) {
    this.slug = require(b.slug, "slug");
    this.name = require(b.name, "name");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.type = require(b.type, "type");
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
    b.slug = this.slug;
    b.name = this.name;
    b.lat = this.lat;
    b.lon = this.lon;
    b.elevationM = this.elevationM;
    b.type = this.type;
    return b;
  }

  /**
   * The {@code slug} value.
   *
   * @return the {@code slug} value
   */
  public String slug() {
    return slug;
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
   * The {@code lat} value.
   *
   * @return the {@code lat} value
   */
  public double lat() {
    return lat;
  }

  /**
   * The {@code lon} value.
   *
   * @return the {@code lon} value
   */
  public double lon() {
    return lon;
  }

  /**
   * The {@code elevation_m} value.
   *
   * @return the {@code elevation_m} value
   */
  public double elevationM() {
    return elevationM;
  }

  /**
   * The {@code type} value.
   *
   * @return the {@code type} value
   */
  public String type() {
    return type;
  }

  @Override
  Object[] values() {
    return new Object[] {slug, name, lat, lon, elevationM, type};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"slug", "name", "lat", "lon", "elevationM", "type"};

  /** Builds a {@link CatalogPrimaryLocation}. */
  public static final class Builder {
    private @Nullable String slug;
    private @Nullable String name;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private @Nullable Double elevationM;
    private @Nullable String type;

    private Builder() {}

    /**
     * Sets {@link CatalogPrimaryLocation#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link CatalogPrimaryLocation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link CatalogPrimaryLocation#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link CatalogPrimaryLocation#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link CatalogPrimaryLocation#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link CatalogPrimaryLocation#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Builds the {@link CatalogPrimaryLocation}.
     *
     * @return the immutable CatalogPrimaryLocation
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogPrimaryLocation build() {
      return new CatalogPrimaryLocation(this);
    }
  }
}
