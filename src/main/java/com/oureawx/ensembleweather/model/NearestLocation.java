// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * On {@code 403 PRO_REQUIRED} from a coordinate route ({@code /api/v1/forecast}, {@code
 * /api/v1/current}, {@code /api/v1/alerts} by {@code lat}/{@code lon}), the nearest named location
 * whose forecast answers without a key -- within 10 km of the point and within 300 m of its
 * elevation (the caller's {@code elevation_m}, else the terrain), so a mountain point is never sent
 * to a valley town. Absent when none qualifies. Call {@code url} (a path on this API) for that
 * location's forecast, current conditions or alerts.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code NearestLocation}.
 */
public final class NearestLocation extends Model {
  private final String slug;
  private final String url;
  private final double distanceKm;
  private final double elevationM;

  private NearestLocation(Builder b) {
    this.slug = require(b.slug, "slug");
    this.url = require(b.url, "url");
    this.distanceKm = require(b.distanceKm, "distance_km");
    this.elevationM = require(b.elevationM, "elevation_m");
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
    b.url = this.url;
    b.distanceKm = this.distanceKm;
    b.elevationM = this.elevationM;
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
   * The location route's path, with the refused route's suffix ({@code /current}, {@code /alerts}).
   *
   * @return the {@code url} value
   */
  public String url() {
    return url;
  }

  /**
   * The {@code distance_km} value.
   *
   * @return the {@code distance_km} value
   */
  public double distanceKm() {
    return distanceKm;
  }

  /**
   * The location's elevation, metres.
   *
   * @return the {@code elevation_m} value
   */
  public double elevationM() {
    return elevationM;
  }

  @Override
  Object[] values() {
    return new Object[] {slug, url, distanceKm, elevationM};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"slug", "url", "distanceKm", "elevationM"};

  /** Builds a {@link NearestLocation}. */
  public static final class Builder {
    private @Nullable String slug;
    private @Nullable String url;
    private @Nullable Double distanceKm;
    private @Nullable Double elevationM;

    private Builder() {}

    /**
     * Sets {@link NearestLocation#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link NearestLocation#url()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder url(String value) {
      this.url = value;
      return this;
    }

    /**
     * Sets {@link NearestLocation#distanceKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder distanceKm(double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Sets {@link NearestLocation#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Builds the {@link NearestLocation}.
     *
     * @return the immutable NearestLocation
     * @throws IllegalStateException if a required value was not set
     */
    public NearestLocation build() {
      return new NearestLocation(this);
    }
  }
}
