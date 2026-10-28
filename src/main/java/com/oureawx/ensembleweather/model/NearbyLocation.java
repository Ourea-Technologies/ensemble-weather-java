// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A NearbyLocation from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code NearbyLocation}.
 */
public final class NearbyLocation extends Model {
  private final String slug;
  private final String state;
  private final String name;
  private final double lat;
  private final double lon;
  private final double elevationM;
  private final String type;
  private final ZoneId timezone;
  private final String county;
  private final @Nullable String description;
  private final List<LocationElevation> elevations;
  private final double distanceKm;

  private NearbyLocation(Builder b) {
    this.slug = require(b.slug, "slug");
    this.state = require(b.state, "state");
    this.name = require(b.name, "name");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.type = require(b.type, "type");
    this.timezone = require(b.timezone, "timezone");
    this.county = require(b.county, "county");
    this.description = b.description;
    this.elevations = List.copyOf(b.elevations);
    this.distanceKm = require(b.distanceKm, "distance_km");
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
    b.state = this.state;
    b.name = this.name;
    b.lat = this.lat;
    b.lon = this.lon;
    b.elevationM = this.elevationM;
    b.type = this.type;
    b.timezone = this.timezone;
    b.county = this.county;
    b.description = this.description;
    b.elevations = this.elevations;
    b.distanceKm = this.distanceKm;
    return b;
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
   * The primary location type, snake_case.
   *
   * @return the {@code type} value
   */
  public String type() {
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
   * Administrative county.
   *
   * @return the {@code county} value
   */
  public String county() {
    return county;
  }

  /**
   * Free-text blurb from the catalog (when present).
   *
   * @return the {@code description} value, or empty when the response omits it
   */
  public Optional<String> description() {
    return Optional.ofNullable(description);
  }

  /**
   * The location's elevation points (e.g., ski area base / mid / summit), the primary first.
   * Exactly one entry is {@code default: true} -- the primary, the elevation the forecast is served
   * at -- and it is listed once: when a named point sits at the primary's elevation the primary
   * carries that name ({@code main_lodge}) instead of {@code default}. Every other entry omits
   * {@code default}. Omitted when empty.
   *
   * @return the {@code elevations} values; empty when the response has none
   */
  public List<LocationElevation> elevations() {
    return elevations;
  }

  /**
   * Great-circle distance from the location, km.
   *
   * @return the {@code distance_km} value
   */
  public double distanceKm() {
    return distanceKm;
  }

  @Override
  Object[] values() {
    return new Object[] {
      slug,
      state,
      name,
      lat,
      lon,
      elevationM,
      type,
      timezone,
      county,
      description,
      elevations,
      distanceKm
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "slug",
    "state",
    "name",
    "lat",
    "lon",
    "elevationM",
    "type",
    "timezone",
    "county",
    "description",
    "elevations",
    "distanceKm"
  };

  /** Builds a {@link NearbyLocation}. */
  public static final class Builder {
    private @Nullable String slug;
    private @Nullable String state;
    private @Nullable String name;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private @Nullable Double elevationM;
    private @Nullable String type;
    private @Nullable ZoneId timezone;
    private @Nullable String county;
    private @Nullable String description;
    private List<LocationElevation> elevations = List.of();
    private @Nullable Double distanceKm;

    private Builder() {}

    /**
     * Sets {@link NearbyLocation#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#timezone()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder timezone(ZoneId value) {
      this.timezone = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#county()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder county(String value) {
      this.county = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#description()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder description(@Nullable String value) {
      this.description = value;
      return this;
    }

    /**
     * Sets {@link NearbyLocation#elevations()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder elevations(List<LocationElevation> values) {
      this.elevations = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link NearbyLocation#distanceKm()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder distanceKm(double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Builds the {@link NearbyLocation}.
     *
     * @return the immutable NearbyLocation
     * @throws IllegalStateException if a required value was not set
     */
    public NearbyLocation build() {
      return new NearbyLocation(this);
    }
  }
}
