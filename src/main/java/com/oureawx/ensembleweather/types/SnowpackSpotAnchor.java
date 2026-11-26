// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import org.jspecify.annotations.Nullable;

/**
 * The snow-depth sensor at the spot. Absent when there is none.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class SnowpackSpotAnchor extends Model {
  private final String station;
  private final String name;
  private final String network;

  private SnowpackSpotAnchor(Builder b) {
    this.station = require(b.station, "station");
    this.name = require(b.name, "name");
    this.network = require(b.network, "network");
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
    b.station = this.station;
    b.name = this.name;
    b.network = this.network;
    return b;
  }

  /**
   * The station's catalog slug.
   *
   * @return the {@code station} value
   */
  public String station() {
    return station;
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
   * The {@code network} value.
   *
   * @return the {@code network} value
   */
  public String network() {
    return network;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {station, name, network};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"station", "name", "network"};

  /** Builds a {@link SnowpackSpotAnchor}. */
  public static final class Builder {
    private @Nullable String station;
    private @Nullable String name;
    private @Nullable String network;

    private Builder() {}

    /**
     * Sets {@link SnowpackSpotAnchor#station()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder station(String value) {
      this.station = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpotAnchor#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpotAnchor#network()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder network(String value) {
      this.network = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackSpotAnchor}.
     *
     * @return the immutable SnowpackSpotAnchor
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackSpotAnchor build() {
      return new SnowpackSpotAnchor(this);
    }
  }
}
