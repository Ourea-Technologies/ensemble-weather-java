// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.ZoneId;
import org.jspecify.annotations.Nullable;

/**
 * One location search result: the {@code LocationRef} fields , the county and {@code key}. {@code
 * Types[]} is gone ({@code type} is the primary type).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SearchHit}.
 */
public final class SearchHit extends Model {
  private final String key;
  private final String slug;
  private final String state;
  private final String name;
  private final double lat;
  private final double lon;
  private final double elevationM;
  private final String type;
  private final ZoneId timezone;
  private final String county;

  private SearchHit(Builder b) {
    this.key = require(b.key, "key");
    this.slug = require(b.slug, "slug");
    this.state = require(b.state, "state");
    this.name = require(b.name, "name");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.type = require(b.type, "type");
    this.timezone = require(b.timezone, "timezone");
    this.county = require(b.county, "county");
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
    b.key = this.key;
    b.slug = this.slug;
    b.state = this.state;
    b.name = this.name;
    b.lat = this.lat;
    b.lon = this.lon;
    b.elevationM = this.elevationM;
    b.type = this.type;
    b.timezone = this.timezone;
    b.county = this.county;
    return b;
  }

  /**
   * State-qualified identity {@code "<state>-<slug>"} (lowercase state). Bare slugs are only unique
   * per state.
   *
   * @return the {@code key} value
   */
  public String key() {
    return key;
  }

  /**
   * The location's key: its catalog slug. With {@code state}, the URL {@code
   * /api/v1/locations/(state)/(slug)}; what a favorite stores.
   *
   * @return the {@code slug} value
   */
  public String slug() {
    return slug;
  }

  /**
   * The lowercase USPS state code.
   *
   * @return the {@code state} value
   */
  public String state() {
    return state;
  }

  /**
   * The display name.
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
   * Elevation in metres, whatever {@code units} is (0.1 m).
   *
   * @return the {@code elevation_m} value
   */
  public double elevationM() {
    return elevationM;
  }

  /**
   * The primary location type, snake_case .
   *
   * @return the {@code type} value ({@link LocationType#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public LocationType type() {
    return LocationType.fromWireValue(type);
  }

  /**
   * The {@code type} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code type} string
   */
  public String typeAsString() {
    return type;
  }

  /**
   * The IANA zone the location's local days and hours are kept in -- the zone {@code daily} dates,
   * sunrise and sunset are computed in. Label the hourly series in it.
   *
   * @return the {@code timezone} value
   */
  public ZoneId timezone() {
    return timezone;
  }

  /**
   * The {@code county} value.
   *
   * @return the {@code county} value
   */
  public String county() {
    return county;
  }

  @Override
  Object[] values() {
    return new Object[] {key, slug, state, name, lat, lon, elevationM, type, timezone, county};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "key", "slug", "state", "name", "lat", "lon", "elevationM", "type", "timezone", "county"
  };

  /** Builds a {@link SearchHit}. */
  public static final class Builder {
    private @Nullable String key;
    private @Nullable String slug;
    private @Nullable String state;
    private @Nullable String name;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private @Nullable Double elevationM;
    private @Nullable String type;
    private @Nullable ZoneId timezone;
    private @Nullable String county;

    private Builder() {}

    /**
     * Sets {@link SearchHit#key()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder key(String value) {
      this.key = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(LocationType value) {
      this.type = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link SearchHit#typeAsString()}, the raw wire value, kept verbatim even when this SDK
     * version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder typeAsString(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#timezone()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder timezone(ZoneId value) {
      this.timezone = value;
      return this;
    }

    /**
     * Sets {@link SearchHit#county()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder county(String value) {
      this.county = value;
      return this;
    }

    /**
     * Builds the {@link SearchHit}.
     *
     * @return the immutable SearchHit
     * @throws IllegalStateException if a required value was not set
     */
    public SearchHit build() {
      return new SearchHit(this);
    }
  }
}
