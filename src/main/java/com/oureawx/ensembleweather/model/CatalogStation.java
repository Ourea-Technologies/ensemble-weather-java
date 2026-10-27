// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * A catalog station's PUBLIC projection -- the {@code LocationRef} fields ({@code type} is {@code
 * observation_station}) plus the station's public facts, by an allowlist; the CMS internals are not
 * served.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogStation}.
 */
public final class CatalogStation extends Model {
  private final String slug;
  private final String state;
  private final String name;
  private final double lat;
  private final double lon;
  private final double elevationM;
  private final String type;
  private final ZoneId timezone;
  private final @Nullable String county;
  private final @Nullable String country;
  private final List<String> aliases;
  private final @Nullable String network;
  private final @Nullable String sourceStationId;
  private final @Nullable Double elevationMslM;
  private final @Nullable Double sensorHeightAglM;
  private final List<String> sensorCapabilities;
  private final @Nullable String stationStatus;
  private final @Nullable Instant lastReportAt;
  private final @Nullable JsonValue lastReportSummary;
  private final @Nullable LocalDate installDate;
  private final @Nullable LocalDate decommissionDate;
  private final @Nullable Integer updateFrequencyMinutes;
  private final @Nullable String stationUrl;
  private final @Nullable String dataUrl;
  private final @Nullable String descriptionShort;
  private final @Nullable String descriptionLong;
  private final @Nullable String metaTitle;
  private final @Nullable String metaDescription;
  private final List<String> tags;
  private final @Nullable Boolean isFeatured;

  private CatalogStation(Builder b) {
    this.slug = require(b.slug, "slug");
    this.state = require(b.state, "state");
    this.name = require(b.name, "name");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.type = require(b.type, "type");
    this.timezone = require(b.timezone, "timezone");
    this.county = b.county;
    this.country = b.country;
    this.aliases = List.copyOf(b.aliases);
    this.network = b.network;
    this.sourceStationId = b.sourceStationId;
    this.elevationMslM = b.elevationMslM;
    this.sensorHeightAglM = b.sensorHeightAglM;
    this.sensorCapabilities = List.copyOf(b.sensorCapabilities);
    this.stationStatus = b.stationStatus;
    this.lastReportAt = b.lastReportAt;
    this.lastReportSummary = b.lastReportSummary;
    this.installDate = b.installDate;
    this.decommissionDate = b.decommissionDate;
    this.updateFrequencyMinutes = b.updateFrequencyMinutes;
    this.stationUrl = b.stationUrl;
    this.dataUrl = b.dataUrl;
    this.descriptionShort = b.descriptionShort;
    this.descriptionLong = b.descriptionLong;
    this.metaTitle = b.metaTitle;
    this.metaDescription = b.metaDescription;
    this.tags = List.copyOf(b.tags);
    this.isFeatured = b.isFeatured;
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
    b.country = this.country;
    b.aliases = this.aliases;
    b.network = this.network;
    b.sourceStationId = this.sourceStationId;
    b.elevationMslM = this.elevationMslM;
    b.sensorHeightAglM = this.sensorHeightAglM;
    b.sensorCapabilities = this.sensorCapabilities;
    b.stationStatus = this.stationStatus;
    b.lastReportAt = this.lastReportAt;
    b.lastReportSummary = this.lastReportSummary;
    b.installDate = this.installDate;
    b.decommissionDate = this.decommissionDate;
    b.updateFrequencyMinutes = this.updateFrequencyMinutes;
    b.stationUrl = this.stationUrl;
    b.dataUrl = this.dataUrl;
    b.descriptionShort = this.descriptionShort;
    b.descriptionLong = this.descriptionLong;
    b.metaTitle = this.metaTitle;
    b.metaDescription = this.metaDescription;
    b.tags = this.tags;
    b.isFeatured = this.isFeatured;
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
   * @return the {@code county} value, or empty when the response omits it
   */
  public Optional<String> county() {
    return Optional.ofNullable(county);
  }

  /**
   * The {@code country} value.
   *
   * @return the {@code country} value, or empty when the response omits it
   */
  public Optional<String> country() {
    return Optional.ofNullable(country);
  }

  /**
   * The {@code aliases} value.
   *
   * @return the {@code aliases} values; empty when the response has none
   */
  public List<String> aliases() {
    return aliases;
  }

  /**
   * The {@code network} value.
   *
   * @return the {@code network} value, or empty when the response omits it
   */
  public Optional<String> network() {
    return Optional.ofNullable(network);
  }

  /**
   * The {@code source_station_id} value.
   *
   * @return the {@code source_station_id} value, or empty when the response omits it
   */
  public Optional<String> sourceStationId() {
    return Optional.ofNullable(sourceStationId);
  }

  /**
   * The {@code elevation_msl_m} value.
   *
   * @return the {@code elevation_msl_m} value, or empty when the response omits it
   */
  public OptionalDouble elevationMslM() {
    return elevationMslM == null ? OptionalDouble.empty() : OptionalDouble.of(elevationMslM);
  }

  /**
   * The {@code sensor_height_agl_m} value.
   *
   * @return the {@code sensor_height_agl_m} value, or empty when the response omits it
   */
  public OptionalDouble sensorHeightAglM() {
    return sensorHeightAglM == null ? OptionalDouble.empty() : OptionalDouble.of(sensorHeightAglM);
  }

  /**
   * The {@code sensor_capabilities} value.
   *
   * @return the {@code sensor_capabilities} values; empty when the response has none
   */
  public List<String> sensorCapabilities() {
    return sensorCapabilities;
  }

  /**
   * The {@code station_status} value.
   *
   * @return the {@code station_status} value, or empty when the response omits it
   */
  public Optional<String> stationStatus() {
    return Optional.ofNullable(stationStatus);
  }

  /**
   * The {@code last_report_at} value.
   *
   * @return the {@code last_report_at} value, or empty when the response omits it
   */
  public Optional<Instant> lastReportAt() {
    return Optional.ofNullable(lastReportAt);
  }

  /**
   * Free-form JSON of the last report.
   *
   * @return the {@code last_report_summary} value, or empty when the response omits it
   */
  public Optional<JsonValue> lastReportSummary() {
    return Optional.ofNullable(lastReportSummary);
  }

  /**
   * The {@code install_date} value.
   *
   * @return the {@code install_date} value, or empty when the response omits it
   */
  public Optional<LocalDate> installDate() {
    return Optional.ofNullable(installDate);
  }

  /**
   * The {@code decommission_date} value.
   *
   * @return the {@code decommission_date} value, or empty when the response omits it
   */
  public Optional<LocalDate> decommissionDate() {
    return Optional.ofNullable(decommissionDate);
  }

  /**
   * The {@code update_frequency_minutes} value.
   *
   * @return the {@code update_frequency_minutes} value, or empty when the response omits it
   */
  public OptionalInt updateFrequencyMinutes() {
    return updateFrequencyMinutes == null
        ? OptionalInt.empty()
        : OptionalInt.of(updateFrequencyMinutes);
  }

  /**
   * The {@code station_url} value.
   *
   * @return the {@code station_url} value, or empty when the response omits it
   */
  public Optional<String> stationUrl() {
    return Optional.ofNullable(stationUrl);
  }

  /**
   * The {@code data_url} value.
   *
   * @return the {@code data_url} value, or empty when the response omits it
   */
  public Optional<String> dataUrl() {
    return Optional.ofNullable(dataUrl);
  }

  /**
   * The {@code description_short} value.
   *
   * @return the {@code description_short} value, or empty when the response omits it
   */
  public Optional<String> descriptionShort() {
    return Optional.ofNullable(descriptionShort);
  }

  /**
   * The {@code description_long} value.
   *
   * @return the {@code description_long} value, or empty when the response omits it
   */
  public Optional<String> descriptionLong() {
    return Optional.ofNullable(descriptionLong);
  }

  /**
   * The {@code meta_title} value.
   *
   * @return the {@code meta_title} value, or empty when the response omits it
   */
  public Optional<String> metaTitle() {
    return Optional.ofNullable(metaTitle);
  }

  /**
   * The {@code meta_description} value.
   *
   * @return the {@code meta_description} value, or empty when the response omits it
   */
  public Optional<String> metaDescription() {
    return Optional.ofNullable(metaDescription);
  }

  /**
   * The {@code tags} value.
   *
   * @return the {@code tags} values; empty when the response has none
   */
  public List<String> tags() {
    return tags;
  }

  /**
   * The {@code is_featured} value.
   *
   * @return the {@code is_featured} value, or empty when the response omits it
   */
  public Optional<Boolean> isFeatured() {
    return Optional.ofNullable(isFeatured);
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
      country,
      aliases,
      network,
      sourceStationId,
      elevationMslM,
      sensorHeightAglM,
      sensorCapabilities,
      stationStatus,
      lastReportAt,
      lastReportSummary,
      installDate,
      decommissionDate,
      updateFrequencyMinutes,
      stationUrl,
      dataUrl,
      descriptionShort,
      descriptionLong,
      metaTitle,
      metaDescription,
      tags,
      isFeatured
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
    "country",
    "aliases",
    "network",
    "sourceStationId",
    "elevationMslM",
    "sensorHeightAglM",
    "sensorCapabilities",
    "stationStatus",
    "lastReportAt",
    "lastReportSummary",
    "installDate",
    "decommissionDate",
    "updateFrequencyMinutes",
    "stationUrl",
    "dataUrl",
    "descriptionShort",
    "descriptionLong",
    "metaTitle",
    "metaDescription",
    "tags",
    "isFeatured"
  };

  /** Builds a {@link CatalogStation}. */
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
    private @Nullable String country;
    private List<String> aliases = List.of();
    private @Nullable String network;
    private @Nullable String sourceStationId;
    private @Nullable Double elevationMslM;
    private @Nullable Double sensorHeightAglM;
    private List<String> sensorCapabilities = List.of();
    private @Nullable String stationStatus;
    private @Nullable Instant lastReportAt;
    private @Nullable JsonValue lastReportSummary;
    private @Nullable LocalDate installDate;
    private @Nullable LocalDate decommissionDate;
    private @Nullable Integer updateFrequencyMinutes;
    private @Nullable String stationUrl;
    private @Nullable String dataUrl;
    private @Nullable String descriptionShort;
    private @Nullable String descriptionLong;
    private @Nullable String metaTitle;
    private @Nullable String metaDescription;
    private List<String> tags = List.of();
    private @Nullable Boolean isFeatured;

    private Builder() {}

    /**
     * Sets {@link CatalogStation#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(LocationType value) {
      this.type = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogStation#typeAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder typeAsString(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#timezone()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder timezone(ZoneId value) {
      this.timezone = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#county()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder county(@Nullable String value) {
      this.county = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#country()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder country(@Nullable String value) {
      this.country = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#aliases()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder aliases(List<String> values) {
      this.aliases = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogStation#network()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder network(@Nullable String value) {
      this.network = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#sourceStationId()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder sourceStationId(@Nullable String value) {
      this.sourceStationId = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#elevationMslM()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder elevationMslM(@Nullable Double value) {
      this.elevationMslM = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#sensorHeightAglM()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder sensorHeightAglM(@Nullable Double value) {
      this.sensorHeightAglM = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#sensorCapabilities()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder sensorCapabilities(List<String> values) {
      this.sensorCapabilities = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogStation#stationStatus()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder stationStatus(@Nullable String value) {
      this.stationStatus = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#lastReportAt()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder lastReportAt(@Nullable Instant value) {
      this.lastReportAt = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#lastReportSummary()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder lastReportSummary(@Nullable JsonValue value) {
      this.lastReportSummary = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#installDate()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder installDate(@Nullable LocalDate value) {
      this.installDate = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#decommissionDate()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder decommissionDate(@Nullable LocalDate value) {
      this.decommissionDate = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#updateFrequencyMinutes()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder updateFrequencyMinutes(@Nullable Integer value) {
      this.updateFrequencyMinutes = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#stationUrl()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder stationUrl(@Nullable String value) {
      this.stationUrl = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#dataUrl()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder dataUrl(@Nullable String value) {
      this.dataUrl = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#descriptionShort()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder descriptionShort(@Nullable String value) {
      this.descriptionShort = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#descriptionLong()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder descriptionLong(@Nullable String value) {
      this.descriptionLong = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#metaTitle()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder metaTitle(@Nullable String value) {
      this.metaTitle = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#metaDescription()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder metaDescription(@Nullable String value) {
      this.metaDescription = value;
      return this;
    }

    /**
     * Sets {@link CatalogStation#tags()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder tags(List<String> values) {
      this.tags = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogStation#isFeatured()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder isFeatured(@Nullable Boolean value) {
      this.isFeatured = value;
      return this;
    }

    /**
     * Builds the {@link CatalogStation}.
     *
     * @return the immutable CatalogStation
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogStation build() {
      return new CatalogStation(this);
    }
  }
}
