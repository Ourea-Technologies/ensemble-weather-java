// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * Inlined summary of a station near a catalog location — enough to render a list; full detail
 * requires a follow-up GET against {@code /api/v1/catalog/stations/(slug)}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogNearbyStation}.
 */
public final class CatalogNearbyStation extends Model {
  private final String slug;
  private final String name;
  private final String network;
  private final String status;
  private final @Nullable Double distanceKm;
  private final @Nullable Double elevationDiffM;
  private final boolean isPrimary;

  private CatalogNearbyStation(Builder b) {
    this.slug = require(b.slug, "slug");
    this.name = require(b.name, "name");
    this.network = require(b.network, "network");
    this.status = require(b.status, "station_status");
    this.distanceKm = b.distanceKm;
    this.elevationDiffM = b.elevationDiffM;
    this.isPrimary = require(b.isPrimary, "is_primary");
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
    b.network = this.network;
    b.status = this.status;
    b.distanceKm = this.distanceKm;
    b.elevationDiffM = this.elevationDiffM;
    b.isPrimary = this.isPrimary;
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
   * Observation network, snake_case (was the CMS enum's {@code Snotel}).
   *
   * @return the {@code network} value ({@link StationNetwork#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public StationNetwork network() {
    return StationNetwork.fromWireValue(network);
  }

  /**
   * The {@code network} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code network} string
   */
  public String networkAsString() {
    return network;
  }

  /**
   * (JSON {@code station_status}) Live-feed status, snake_case (was {@code Active}).
   *
   * @return the {@code station_status} value ({@link StationStatus#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public StationStatus status() {
    return StationStatus.fromWireValue(status);
  }

  /**
   * The {@code station_status} value exactly as the API sent it, even when this SDK version does
   * not know it.
   *
   * @return the raw {@code station_status} string
   */
  public String statusAsString() {
    return status;
  }

  /**
   * The {@code distance_km} value.
   *
   * @return the {@code distance_km} value, or empty when the response omits it
   */
  public OptionalDouble distanceKm() {
    return distanceKm == null ? OptionalDouble.empty() : OptionalDouble.of(distanceKm);
  }

  /**
   * The {@code elevation_diff_m} value.
   *
   * @return the {@code elevation_diff_m} value, or empty when the response omits it
   */
  public OptionalDouble elevationDiffM() {
    return elevationDiffM == null ? OptionalDouble.empty() : OptionalDouble.of(elevationDiffM);
  }

  /**
   * The {@code is_primary} value.
   *
   * @return the {@code is_primary} value
   */
  public boolean isPrimary() {
    return isPrimary;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {slug, name, network, status, distanceKm, elevationDiffM, isPrimary};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "slug", "name", "network", "status", "distanceKm", "elevationDiffM", "isPrimary"
  };

  /** Builds a {@link CatalogNearbyStation}. */
  public static final class Builder {
    private @Nullable String slug;
    private @Nullable String name;
    private @Nullable String network;
    private @Nullable String status;
    private @Nullable Double distanceKm;
    private @Nullable Double elevationDiffM;
    private @Nullable Boolean isPrimary;

    private Builder() {}

    /**
     * Sets {@link CatalogNearbyStation#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#network()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder network(StationNetwork value) {
      this.network = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#networkAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder networkAsString(String value) {
      this.network = value;
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#status()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder status(StationStatus value) {
      this.status = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#statusAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder statusAsString(String value) {
      this.status = value;
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#distanceKm()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder distanceKm(@Nullable Double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#elevationDiffM()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder elevationDiffM(@Nullable Double value) {
      this.elevationDiffM = value;
      return this;
    }

    /**
     * Sets {@link CatalogNearbyStation#isPrimary()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder isPrimary(boolean value) {
      this.isPrimary = value;
      return this;
    }

    /**
     * Builds the {@link CatalogNearbyStation}.
     *
     * @return the immutable CatalogNearbyStation
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogNearbyStation build() {
      return new CatalogNearbyStation(this);
    }
  }
}
