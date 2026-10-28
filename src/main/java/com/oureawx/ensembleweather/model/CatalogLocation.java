// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A catalog location's PUBLIC projection -- the one {@code LocationRef} plus the catalog's public
 * content, by an allowlist: the CMS internals (row ids, {@code created_by}, {@code updated_by},
 * {@code created_at}, {@code updated_at}, {@code deleted_at}, {@code is_published}, {@code
 * enrichment_tier}, {@code view_count_*}, {@code template_variant}) are not served.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogLocation}.
 */
public final class CatalogLocation extends Model {
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
  private final @Nullable String descriptionShort;
  private final @Nullable String descriptionLong;
  private final @Nullable String microclimateNotes;
  private final @Nullable String metaTitle;
  private final @Nullable String metaDescription;
  private final List<String> bestSeasons;
  private final List<String> keyFactors;
  private final List<String> tags;
  private final @Nullable JsonValue externalLinks;
  private final @Nullable Boolean isFeatured;

  private CatalogLocation(Builder b) {
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
    this.descriptionShort = b.descriptionShort;
    this.descriptionLong = b.descriptionLong;
    this.microclimateNotes = b.microclimateNotes;
    this.metaTitle = b.metaTitle;
    this.metaDescription = b.metaDescription;
    this.bestSeasons = List.copyOf(b.bestSeasons);
    this.keyFactors = List.copyOf(b.keyFactors);
    this.tags = List.copyOf(b.tags);
    this.externalLinks = b.externalLinks;
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
    b.descriptionShort = this.descriptionShort;
    b.descriptionLong = this.descriptionLong;
    b.microclimateNotes = this.microclimateNotes;
    b.metaTitle = this.metaTitle;
    b.metaDescription = this.metaDescription;
    b.bestSeasons = this.bestSeasons;
    b.keyFactors = this.keyFactors;
    b.tags = this.tags;
    b.externalLinks = this.externalLinks;
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
   * The primary location type, snake_case.
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
   * The {@code microclimate_notes} value.
   *
   * @return the {@code microclimate_notes} value, or empty when the response omits it
   */
  public Optional<String> microclimateNotes() {
    return Optional.ofNullable(microclimateNotes);
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
   * The {@code best_seasons} value.
   *
   * @return the {@code best_seasons} values; empty when the response has none
   */
  public List<String> bestSeasons() {
    return bestSeasons;
  }

  /**
   * The {@code key_factors} value.
   *
   * @return the {@code key_factors} values; empty when the response has none
   */
  public List<String> keyFactors() {
    return keyFactors;
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
   * Free-form JSON of labelled links.
   *
   * @return the {@code external_links} value, or empty when the response omits it
   */
  public Optional<JsonValue> externalLinks() {
    return Optional.ofNullable(externalLinks);
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
      descriptionShort,
      descriptionLong,
      microclimateNotes,
      metaTitle,
      metaDescription,
      bestSeasons,
      keyFactors,
      tags,
      externalLinks,
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
    "descriptionShort",
    "descriptionLong",
    "microclimateNotes",
    "metaTitle",
    "metaDescription",
    "bestSeasons",
    "keyFactors",
    "tags",
    "externalLinks",
    "isFeatured"
  };

  /** Builds a {@link CatalogLocation}. */
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
    private @Nullable String descriptionShort;
    private @Nullable String descriptionLong;
    private @Nullable String microclimateNotes;
    private @Nullable String metaTitle;
    private @Nullable String metaDescription;
    private List<String> bestSeasons = List.of();
    private List<String> keyFactors = List.of();
    private List<String> tags = List.of();
    private @Nullable JsonValue externalLinks;
    private @Nullable Boolean isFeatured;

    private Builder() {}

    /**
     * Sets {@link CatalogLocation#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(LocationType value) {
      this.type = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogLocation#typeAsString()}, the raw wire value, kept verbatim even when this
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
     * Sets {@link CatalogLocation#timezone()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder timezone(ZoneId value) {
      this.timezone = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#county()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder county(@Nullable String value) {
      this.county = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#country()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder country(@Nullable String value) {
      this.country = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#aliases()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder aliases(List<String> values) {
      this.aliases = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocation#descriptionShort()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder descriptionShort(@Nullable String value) {
      this.descriptionShort = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#descriptionLong()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder descriptionLong(@Nullable String value) {
      this.descriptionLong = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#microclimateNotes()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder microclimateNotes(@Nullable String value) {
      this.microclimateNotes = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#metaTitle()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder metaTitle(@Nullable String value) {
      this.metaTitle = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#metaDescription()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder metaDescription(@Nullable String value) {
      this.metaDescription = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#bestSeasons()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder bestSeasons(List<String> values) {
      this.bestSeasons = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocation#keyFactors()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder keyFactors(List<String> values) {
      this.keyFactors = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocation#tags()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder tags(List<String> values) {
      this.tags = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocation#externalLinks()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder externalLinks(@Nullable JsonValue value) {
      this.externalLinks = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocation#isFeatured()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder isFeatured(@Nullable Boolean value) {
      this.isFeatured = value;
      return this;
    }

    /**
     * Builds the {@link CatalogLocation}.
     *
     * @return the immutable CatalogLocation
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogLocation build() {
      return new CatalogLocation(this);
    }
  }
}
