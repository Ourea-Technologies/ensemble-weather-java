// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The forecast's {@code location} -- the one {@code LocationRef} (every field where it was) plus
 * what a forecast client labels and picks with, so the forecast body alone is enough to draw a
 * plume or a meteogram at a named elevation (no {@code /nearby} call, no client-side unit
 * conversion). Additive.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ForecastLocation}.
 */
public final class ForecastLocation extends Model {
  private final @Nullable String slug;
  private final String state;
  private final String name;
  private final double lat;
  private final double lon;
  private final double elevationM;
  private final @Nullable String type;
  private final ZoneId timezone;
  private final double elevation;
  private final List<ElevationSummary> elevations;

  private ForecastLocation(Builder b) {
    this.slug = b.slug;
    this.state = require(b.state, "state");
    this.name = require(b.name, "name");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.type = b.type;
    this.timezone = require(b.timezone, "timezone");
    this.elevation = require(b.elevation, "elevation");
    this.elevations = List.copyOf(b.elevations);
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
    b.elevation = this.elevation;
    b.elevations = this.elevations;
    return b;
  }

  /**
   * The location's key: its catalog slug. With {@code state}, the URL <code>
   * /api/v1/locations/&#123;state&#125;/&#123;slug&#125;</code>; what a favorite stores. {@code
   * Null} on a coordinate forecast ({@code GET /api/v1/forecast}, {@code /current}, {@code /alerts}
   * by {@code lat}/{@code lon}), which names no catalog place.
   *
   * @return the {@code slug} value, or empty when the response omits it
   */
  public Optional<String> slug() {
    return Optional.ofNullable(slug);
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
   * The primary location type, snake_case. {@code Null} on a coordinate forecast.
   *
   * @return the {@code type} value, or empty when the response omits it ({@link
   *     LocationType#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<LocationType> type() {
    return Optional.ofNullable(type).map(LocationType::fromWireValue);
  }

  /**
   * The {@code type} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code type} string, or empty when absent
   */
  public Optional<String> typeAsString() {
    return Optional.ofNullable(type);
  }

  /**
   * The IANA zone the location's local days and hours are kept in -- the zone {@code daily} dates,
   * sunrise and sunset are computed in. Label the hourly series in it (/1080).
   *
   * @return the {@code timezone} value
   */
  public ZoneId timezone() {
    return timezone;
  }

  /**
   * {@code elevation_m} in the request's length units (m for {@code metric}/{@code kelvin}, ft for
   * {@code imperial}), to 0.1 -- the elevation {@code forecast.timeseries} (and its members) is
   * served at. Label the primary's series and draw the snow-level reference line with it. {@code
   * elevation_m} stays metres. (Cap'n Proto: {@code LocationInfo.elevation}.)
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * The location's named elevations -- exactly the list its {@code /nearby} and list rows serve
   * ({@code LocationSummary.elevations}): the primary first and its ONE {@code default: true}, then
   * each other elevation (ski-area base / mid / summit), in the request's length units. Name one in
   * {@code ?elevations=} to get its forecast (and, with {@code members=true}, its plume) in {@code
   * elevation_forecasts[]}; label the primary's series ({@code forecast}) with the {@code default:
   * true} entry's name. Listed whether or not {@code elevations} was asked for. Omitted for a
   * location with one elevation and for a coordinate forecast. (Cap'n Proto: {@code
   * LocationInfo.elevations}, empty there.)
   *
   * @return the {@code elevations} values; empty when the response has none
   */
  public List<ElevationSummary> elevations() {
    return elevations;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      slug, state, name, lat, lon, elevationM, type, timezone, elevation, elevations
    };
  }

  @Override
  String[] fieldNames() {
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
    "elevation",
    "elevations"
  };

  /** Builds a {@link ForecastLocation}. */
  public static final class Builder {
    private @Nullable String slug;
    private @Nullable String state;
    private @Nullable String name;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private @Nullable Double elevationM;
    private @Nullable String type;
    private @Nullable ZoneId timezone;
    private @Nullable Double elevation;
    private List<ElevationSummary> elevations = List.of();

    private Builder() {}

    /**
     * Sets {@link ForecastLocation#slug()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder slug(@Nullable String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#type()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder type(@Nullable LocationType value) {
      this.type = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ForecastLocation#typeAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder typeAsString(@Nullable String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#timezone()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder timezone(ZoneId value) {
      this.timezone = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link ForecastLocation#elevations()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder elevations(List<ElevationSummary> values) {
      this.elevations = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link ForecastLocation}.
     *
     * @return the immutable ForecastLocation
     * @throws IllegalStateException if a required value was not set
     */
    public ForecastLocation build() {
      return new ForecastLocation(this);
    }
  }
}
