// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A CatalogLocationDetail from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogLocationDetail}.
 */
public final class CatalogLocationDetail extends Model {
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
  private final List<CatalogElevation> elevations;
  private final List<CatalogPhoto> photos;
  private final List<CatalogWebcam> webcams;
  private final List<CatalogNearbyStation> nearbyStations;
  private final @Nullable CatalogTemplateSummary template;
  private final List<CatalogResolvedEntry> content;
  private final List<CatalogResolvedAdSlot> ads;
  private final ResponseMetadata metadata;

  private CatalogLocationDetail(Builder b) {
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
    this.elevations = List.copyOf(b.elevations);
    this.photos = List.copyOf(b.photos);
    this.webcams = List.copyOf(b.webcams);
    this.nearbyStations = List.copyOf(b.nearbyStations);
    this.template = b.template;
    this.content = List.copyOf(b.content);
    this.ads = List.copyOf(b.ads);
    this.metadata = b.metadata;
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
    b.elevations = this.elevations;
    b.photos = this.photos;
    b.webcams = this.webcams;
    b.nearbyStations = this.nearbyStations;
    b.template = this.template;
    b.content = this.content;
    b.ads = this.ads;
    b.metadata = this.metadata;
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

  /**
   * The {@code elevations} value.
   *
   * @return the {@code elevations} values; empty when the response has none
   */
  public List<CatalogElevation> elevations() {
    return elevations;
  }

  /**
   * The {@code photos} value.
   *
   * @return the {@code photos} values; empty when the response has none
   */
  public List<CatalogPhoto> photos() {
    return photos;
  }

  /**
   * The {@code webcams} value.
   *
   * @return the {@code webcams} values; empty when the response has none
   */
  public List<CatalogWebcam> webcams() {
    return webcams;
  }

  /**
   * The {@code nearby_stations} value.
   *
   * @return the {@code nearby_stations} values; empty when the response has none
   */
  public List<CatalogNearbyStation> nearbyStations() {
    return nearbyStations;
  }

  /**
   * The {@code template} value.
   *
   * @return the {@code template} value, or empty when the response omits it
   */
  public Optional<CatalogTemplateSummary> template() {
    return Optional.ofNullable(template);
  }

  /**
   * The {@code content} value.
   *
   * @return the {@code content} values; empty when the response has none
   */
  public List<CatalogResolvedEntry> content() {
    return content;
  }

  /**
   * The {@code ads} value.
   *
   * @return the {@code ads} values; empty when the response has none
   */
  public List<CatalogResolvedAdSlot> ads() {
    return ads;
  }

  /**
   * What the HTTP exchange said about this response: rate-limit state, request id, and whether it
   * was revalidated from the local cache. Not part of {@link #equals(Object)}.
   *
   * @return the response metadata
   */
  public ResponseMetadata metadata() {
    return metadata;
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
      isFeatured,
      elevations,
      photos,
      webcams,
      nearbyStations,
      template,
      content,
      ads
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
    "isFeatured",
    "elevations",
    "photos",
    "webcams",
    "nearbyStations",
    "template",
    "content",
    "ads"
  };

  /** Builds a {@link CatalogLocationDetail}. */
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
    private List<CatalogElevation> elevations = List.of();
    private List<CatalogPhoto> photos = List.of();
    private List<CatalogWebcam> webcams = List.of();
    private List<CatalogNearbyStation> nearbyStations = List.of();
    private @Nullable CatalogTemplateSummary template;
    private List<CatalogResolvedEntry> content = List.of();
    private List<CatalogResolvedAdSlot> ads = List.of();
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link CatalogLocationDetail#slug()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slug(String value) {
      this.slug = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#timezone()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder timezone(ZoneId value) {
      this.timezone = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#county()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder county(@Nullable String value) {
      this.county = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#country()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder country(@Nullable String value) {
      this.country = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#aliases()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder aliases(List<String> values) {
      this.aliases = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#descriptionShort()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder descriptionShort(@Nullable String value) {
      this.descriptionShort = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#descriptionLong()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder descriptionLong(@Nullable String value) {
      this.descriptionLong = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#microclimateNotes()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder microclimateNotes(@Nullable String value) {
      this.microclimateNotes = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#metaTitle()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder metaTitle(@Nullable String value) {
      this.metaTitle = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#metaDescription()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder metaDescription(@Nullable String value) {
      this.metaDescription = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#bestSeasons()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder bestSeasons(List<String> values) {
      this.bestSeasons = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#keyFactors()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder keyFactors(List<String> values) {
      this.keyFactors = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#tags()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder tags(List<String> values) {
      this.tags = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#externalLinks()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder externalLinks(@Nullable JsonValue value) {
      this.externalLinks = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#isFeatured()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder isFeatured(@Nullable Boolean value) {
      this.isFeatured = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#elevations()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder elevations(List<CatalogElevation> values) {
      this.elevations = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#photos()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder photos(List<CatalogPhoto> values) {
      this.photos = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#webcams()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder webcams(List<CatalogWebcam> values) {
      this.webcams = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#nearbyStations()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder nearbyStations(List<CatalogNearbyStation> values) {
      this.nearbyStations = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#template()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder template(@Nullable CatalogTemplateSummary value) {
      this.template = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#content()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder content(List<CatalogResolvedEntry> values) {
      this.content = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#ads()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder ads(List<CatalogResolvedAdSlot> values) {
      this.ads = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationDetail#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link CatalogLocationDetail}.
     *
     * @return the immutable CatalogLocationDetail
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogLocationDetail build() {
      return new CatalogLocationDetail(this);
    }
  }
}
