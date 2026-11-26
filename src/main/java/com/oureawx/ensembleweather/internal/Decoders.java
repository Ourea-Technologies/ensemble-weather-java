// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import com.oureawx.ensembleweather.types.*;

/**
 * Maps decoded JSON onto the model builders, field by field (no reflection, no type names from
 * JSON).
 */
public final class Decoders {
  private Decoders() {}

  /**
   * Decodes a {@code ActiveAlert}.
   *
   * @param o the object
   * @return the model
   */
  public static ActiveAlert activeAlert(JsonFields o) {
    ActiveAlert.Builder b = ActiveAlert.builder();
    b.id(o.string("id"));
    b.event(o.string("event"));
    b.severityAsString(o.string("severity"));
    b.urgencyAsString(o.string("urgency"));
    b.certaintyAsString(o.string("certainty"));
    b.headline(o.string("headline"));
    b.onset(o.optInstant("onset"));
    b.expires(o.instant("expires"));
    b.ends(o.optInstant("ends"));
    b.sent(o.optInstant("sent"));
    b.messageType(o.optString("message_type"));
    b.references(o.stringList("references"));
    b.senderName(o.optString("sender_name"));
    b.areaDesc(o.optString("area_desc"));
    b.description(o.optString("description"));
    b.instruction(o.optString("instruction"));
    b.sourceUrl(o.optString("source_url"));
    b.eventKey(o.optString("event_key"));
    b.vtecAction(o.optString("vtec_action"));
    return b.build();
  }

  /**
   * Decodes a {@code AlertsResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static AlertsResponse alertsResponse(JsonFields o) {
    AlertsResponse.Builder b = AlertsResponse.builder();
    b.location(o.object("location", Decoders::locationRef));
    b.count(o.integer("alert_count"));
    b.asOf(o.instant("as_of"));
    b.items(o.list("alerts", Decoders::activeAlert));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
    return b.build();
  }

  /**
   * Decodes a {@code AppliedOptions}.
   *
   * @param o the object
   * @return the model
   */
  public static AppliedOptions appliedOptions(JsonFields o) {
    AppliedOptions.Builder b = AppliedOptions.builder();
    b.maxHours(o.optInteger("max_hours"));
    b.maxDays(o.optInteger("max_days"));
    b.clampedAsStrings(o.stringList("clamped"));
    return b.build();
  }

  /**
   * Decodes a {@code AttributionProvider}.
   *
   * @param o the object
   * @return the model
   */
  public static AttributionProvider attributionProvider(JsonFields o) {
    AttributionProvider.Builder b = AttributionProvider.builder();
    b.id(o.string("id"));
    b.name(o.string("name"));
    b.credit(o.string("credit"));
    b.licence(o.string("licence"));
    b.licenceUrl(o.string("licence_url"));
    b.termsUrl(o.string("terms_url"));
    b.modified(o.bool("modified"));
    b.notices(o.stringList("notices"));
    b.sources(o.list("sources", Decoders::attributionSource));
    return b.build();
  }

  /**
   * Decodes a {@code AttributionRegistry}.
   *
   * @param o the object
   * @return the model
   */
  public static AttributionRegistry attributionRegistry(JsonFields o) {
    AttributionRegistry.Builder b = AttributionRegistry.builder();
    b.providers(o.list("providers", Decoders::attributionProvider));
    return b.build();
  }

  /**
   * Decodes a {@code AttributionSource}.
   *
   * @param o the object
   * @return the model
   */
  public static AttributionSource attributionSource(JsonFields o) {
    AttributionSource.Builder b = AttributionSource.builder();
    b.id(o.string("id"));
    b.name(o.string("name"));
    b.kindAsString(o.string("kind"));
    b.provider(o.string("provider"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogElevation}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogElevation catalogElevation(JsonFields o) {
    CatalogElevation.Builder b = CatalogElevation.builder();
    b.label(o.string("label"));
    b.heightM(o.number("elevation_m"));
    b.latOverride(o.optNumber("lat_override"));
    b.lonOverride(o.optNumber("lon_override"));
    b.isDefault(o.bool("is_default"));
    b.displayName(o.optString("display_name"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogLocation}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogLocation catalogLocation(JsonFields o) {
    CatalogLocation.Builder b = CatalogLocation.builder();
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.optString("county"));
    b.country(o.optString("country"));
    b.aliases(o.stringList("aliases"));
    b.descriptionShort(o.optString("description_short"));
    b.descriptionLong(o.optString("description_long"));
    b.microclimateNotes(o.optString("microclimate_notes"));
    b.metaTitle(o.optString("meta_title"));
    b.metaDescription(o.optString("meta_description"));
    b.bestSeasons(o.stringList("best_seasons"));
    b.keyFactors(o.stringList("key_factors"));
    b.tags(o.stringList("tags"));
    b.externalLinks(o.optJson("external_links"));
    b.isFeatured(o.optBool("is_featured"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogLocationDetail}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogLocationDetail catalogLocationDetail(JsonFields o) {
    CatalogLocationDetail.Builder b = CatalogLocationDetail.builder();
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.optString("county"));
    b.country(o.optString("country"));
    b.aliases(o.stringList("aliases"));
    b.descriptionShort(o.optString("description_short"));
    b.descriptionLong(o.optString("description_long"));
    b.microclimateNotes(o.optString("microclimate_notes"));
    b.metaTitle(o.optString("meta_title"));
    b.metaDescription(o.optString("meta_description"));
    b.bestSeasons(o.stringList("best_seasons"));
    b.keyFactors(o.stringList("key_factors"));
    b.tags(o.stringList("tags"));
    b.externalLinks(o.optJson("external_links"));
    b.isFeatured(o.optBool("is_featured"));
    b.elevations(o.list("elevations", Decoders::catalogElevation));
    b.photos(o.list("photos", Decoders::catalogPhoto));
    b.webcams(o.list("webcams", Decoders::catalogWebcam));
    b.nearbyStations(o.list("nearby_stations", Decoders::catalogNearbyStation));
    b.template(o.optObject("template", Decoders::catalogTemplateSummary));
    b.content(o.list("content", Decoders::catalogResolvedEntry));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogLocationPage}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogLocationPage catalogLocationPage(JsonFields o) {
    CatalogLocationPage.Builder b = CatalogLocationPage.builder();
    b.items(o.list("items", Decoders::catalogLocation));
    b.nextCursor(o.optString("next_cursor"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogNearbyStation}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogNearbyStation catalogNearbyStation(JsonFields o) {
    CatalogNearbyStation.Builder b = CatalogNearbyStation.builder();
    b.slug(o.string("slug"));
    b.name(o.string("name"));
    b.networkAsString(o.string("network"));
    b.statusAsString(o.string("station_status"));
    b.distanceKm(o.optNumber("distance_km"));
    b.elevationDiffM(o.optNumber("elevation_diff_m"));
    b.isPrimary(o.bool("is_primary"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogPhoto}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogPhoto catalogPhoto(JsonFields o) {
    CatalogPhoto.Builder b = CatalogPhoto.builder();
    b.url(o.string("url"));
    b.caption(o.optString("caption"));
    b.credit(o.optString("credit"));
    b.altText(o.optString("alt_text"));
    b.license(o.optString("license"));
    b.licenseUrl(o.optString("license_url"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogPrimaryLocation}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogPrimaryLocation catalogPrimaryLocation(JsonFields o) {
    CatalogPrimaryLocation.Builder b = CatalogPrimaryLocation.builder();
    b.slug(o.string("slug"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.type(o.string("type"));
    return b.build();
  }

  /**
   * Decodes a {@code CatalogResolvedEntry}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogResolvedEntry catalogResolvedEntry(JsonFields o) {
    CatalogResolvedEntry.Builder b = CatalogResolvedEntry.builder();
    b.id(o.string("id"));
    b.contentTypeKey(o.string("content_type_key"));
    b.title(o.string("title"));
    b.body(o.json("body"));
    b.slot(o.string("slot"));
    b.displayOrder(o.integer("display_order"));
    b.locale(o.string("resolved_locale"));
    b.updatedAt(o.instant("updated_at"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogStation}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogStation catalogStation(JsonFields o) {
    CatalogStation.Builder b = CatalogStation.builder();
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.optString("county"));
    b.country(o.optString("country"));
    b.aliases(o.stringList("aliases"));
    b.networkAsString(o.optString("network"));
    b.sourceId(o.optString("source_station_id"));
    b.elevationMslM(o.optNumber("elevation_msl_m"));
    b.sensorHeightAglM(o.optNumber("sensor_height_agl_m"));
    b.sensorCapabilities(o.stringList("sensor_capabilities"));
    b.statusAsString(o.optString("station_status"));
    b.lastReportAt(o.optInstant("last_report_at"));
    b.lastReportSummary(o.optJson("last_report_summary"));
    b.installDate(o.optDate("install_date"));
    b.decommissionDate(o.optDate("decommission_date"));
    b.updateFrequencyMinutes(o.optInteger("update_frequency_minutes"));
    b.url(o.optString("station_url"));
    b.dataUrl(o.optString("data_url"));
    b.descriptionShort(o.optString("description_short"));
    b.descriptionLong(o.optString("description_long"));
    b.metaTitle(o.optString("meta_title"));
    b.metaDescription(o.optString("meta_description"));
    b.tags(o.stringList("tags"));
    b.isFeatured(o.optBool("is_featured"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogStationDetail}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogStationDetail catalogStationDetail(JsonFields o) {
    CatalogStationDetail.Builder b = CatalogStationDetail.builder();
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.optString("county"));
    b.country(o.optString("country"));
    b.aliases(o.stringList("aliases"));
    b.networkAsString(o.optString("network"));
    b.sourceId(o.optString("source_station_id"));
    b.elevationMslM(o.optNumber("elevation_msl_m"));
    b.sensorHeightAglM(o.optNumber("sensor_height_agl_m"));
    b.sensorCapabilities(o.stringList("sensor_capabilities"));
    b.statusAsString(o.optString("station_status"));
    b.lastReportAt(o.optInstant("last_report_at"));
    b.lastReportSummary(o.optJson("last_report_summary"));
    b.installDate(o.optDate("install_date"));
    b.decommissionDate(o.optDate("decommission_date"));
    b.updateFrequencyMinutes(o.optInteger("update_frequency_minutes"));
    b.url(o.optString("station_url"));
    b.dataUrl(o.optString("data_url"));
    b.descriptionShort(o.optString("description_short"));
    b.descriptionLong(o.optString("description_long"));
    b.metaTitle(o.optString("meta_title"));
    b.metaDescription(o.optString("meta_description"));
    b.tags(o.stringList("tags"));
    b.isFeatured(o.optBool("is_featured"));
    b.photos(o.list("photos", Decoders::catalogPhoto));
    b.webcams(o.list("webcams", Decoders::catalogWebcam));
    b.primaryLocation(o.optObject("primary_location", Decoders::catalogPrimaryLocation));
    b.template(o.optObject("template", Decoders::catalogTemplateSummary));
    b.content(o.list("content", Decoders::catalogResolvedEntry));
    b.indexable(o.bool("indexable"));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogStationPage}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogStationPage catalogStationPage(JsonFields o) {
    CatalogStationPage.Builder b = CatalogStationPage.builder();
    b.items(o.list("items", Decoders::catalogStation));
    b.nextCursor(o.optString("next_cursor"));
    return b.build();
  }

  /**
   * Decodes a {@code CatalogTemplateSummary}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogTemplateSummary catalogTemplateSummary(JsonFields o) {
    CatalogTemplateSummary.Builder b = CatalogTemplateSummary.builder();
    b.id(o.string("id"));
    b.locationTypeAsString(o.optString("location_type"));
    b.variantAsString(o.optString("template_variant"));
    b.spec(o.list("spec", Decoders::cmsTemplateSection));
    return b.build();
  }

  /**
   * Decodes a {@code PublicCatalogWebcam}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogWebcam catalogWebcam(JsonFields o) {
    CatalogWebcam.Builder b = CatalogWebcam.builder();
    b.name(o.string("name"));
    b.url(o.string("url"));
    b.embedType(o.optString("embed_type"));
    b.provider(o.optString("provider"));
    b.refreshIntervalSeconds(o.optInteger("refresh_interval_seconds"));
    return b.build();
  }

  /**
   * Decodes a {@code CircularStats}.
   *
   * @param o the object
   * @return the model
   */
  public static CircularStats circularStats(JsonFields o) {
    CircularStats.Builder b = CircularStats.builder();
    b.mean(o.number("mean"));
    b.stddev(o.optNumber("stddev"));
    b.sector50(o.object("sector_50", Decoders::sector));
    b.sector90(o.object("sector_90", Decoders::sector));
    return b.build();
  }

  /**
   * Decodes a {@code CmsTemplateSection}.
   *
   * @param o the object
   * @return the model
   */
  public static CmsTemplateSection cmsTemplateSection(JsonFields o) {
    CmsTemplateSection.Builder b = CmsTemplateSection.builder();
    b.kindAsString(o.string("kind"));
    b.key(o.string("key"));
    b.config(o.optJson("config"));
    return b.build();
  }

  /**
   * Decodes a {@code CurrentConditions}.
   *
   * @param o the object
   * @return the model
   */
  public static CurrentConditions currentConditions(JsonFields o) {
    CurrentConditions.Builder b = CurrentConditions.builder();
    b.time(o.instant("time"));
    b.temperature(o.number("temperature"));
    b.feelsLike(o.optNumber("feels_like"));
    b.codeAsString(o.string("condition"));
    b.text(o.string("condition_text"));
    b.iconAsString(o.string("condition_icon"));
    b.windSpeed(o.number("wind_speed"));
    b.windGust(o.optNumber("wind_gust"));
    b.windDirection(o.number("wind_direction"));
    b.relativeHumidity(o.optNumber("relative_humidity"));
    b.dewpoint(o.optNumber("dewpoint"));
    b.precipitationProbability(o.optNumber("precipitation_probability"));
    b.sourceAsString(o.string("source"));
    b.station(o.optObject("station", Decoders::currentStation));
    b.measured(o.bool("measured"));
    b.cloudCover(o.optNumber("cloud_cover"));
    return b.build();
  }

  /**
   * Decodes a {@code CurrentResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static CurrentResponse currentResponse(JsonFields o) {
    CurrentResponse.Builder b = CurrentResponse.builder();
    b.location(o.object("location", Decoders::locationRef));
    b.alerts(o.list("alerts", Decoders::activeAlert));
    b.alertsAsOf(o.instant("alerts_as_of"));
    b.sensorCount(o.integer("sensor_count"));
    b.sensors(o.list("sensors", Decoders::sensorObservation));
    b.now(o.optObject("current", Decoders::currentConditions));
    b.asOf(o.instant("as_of"));
    b.modelRunInitializedAt(o.optInstant("model_run_initialized_at"));
    b.nowcastHours(o.list("nowcast_hours", Decoders::timeSeriesPoint));
    b.unitsAsString(o.string("units"));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
    b.nowcastUnavailableAsStrings(o.stringList("nowcast_unavailable"));
    b.summary(o.optObject("summary", Decoders::placeSummary));
    b.pass(o.optObject("pass", Decoders::passForecast));
    return b.build();
  }

  /**
   * Decodes a {@code CurrentStation}.
   *
   * @param o the object
   * @return the model
   */
  public static CurrentStation currentStation(JsonFields o) {
    CurrentStation.Builder b = CurrentStation.builder();
    b.id(o.string("station_id"));
    b.name(o.string("name"));
    b.observationTime(o.instant("observation_time"));
    b.calm(o.bool("calm"));
    return b.build();
  }

  /**
   * Decodes a {@code DailyAstro}.
   *
   * @param o the object
   * @return the model
   */
  public static DailyAstro dailyAstro(JsonFields o) {
    DailyAstro.Builder b = DailyAstro.builder();
    b.sunrise(o.optInstant("sunrise"));
    b.sunset(o.optInstant("sunset"));
    b.moonPhaseAsString(o.string("moon_phase"));
    b.moonIllumination(o.number("moon_illumination"));
    b.astronomicalSeasonAsString(o.string("astronomical_season"));
    return b.build();
  }

  /**
   * Decodes a {@code DailyForecast}.
   *
   * @param o the object
   * @return the model
   */
  public static DailyForecast dailyForecast(JsonFields o) {
    DailyForecast.Builder b = DailyForecast.builder();
    b.date(o.date("date"));
    b.conditionAsString(o.string("condition"));
    b.conditionText(o.string("condition_text"));
    b.conditionIconAsString(o.string("condition_icon"));
    b.precipitationTypeAsString(o.optString("precipitation_type"));
    b.high(o.object("high", Decoders::quantiles));
    b.low(o.object("low", Decoders::quantiles));
    b.feelsLikeHigh(o.optObject("feels_like_high", Decoders::quantiles));
    b.feelsLikeLow(o.optObject("feels_like_low", Decoders::quantiles));
    b.totalPrecipitation(o.object("total_precipitation", Decoders::quantiles));
    b.totalSnowfall(o.optObject("total_snowfall", Decoders::quantiles));
    b.precipitationProbability(o.number("precipitation_probability"));
    b.precipitationOccurrenceProbability(o.number("precipitation_occurrence_probability"));
    b.snowProbability(o.number("snow_probability"));
    b.snowLevelMean(o.optNumber("snow_level_mean"));
    b.snowLevel(o.optObject("snow_level", Decoders::quantiles));
    b.totalPrecipitationIfWet(o.optObject("total_precipitation_if_wet", Decoders::quantiles));
    b.totalSnowfallIfWet(o.optObject("total_snowfall_if_wet", Decoders::quantiles));
    b.precipitationDisplay(o.object("precipitation_display", Decoders::precipDisplay));
    b.cloudCoverMean(o.optNumber("cloud_cover_mean"));
    b.wind(o.optObject("wind", Decoders::dailyWind));
    b.stormOrientationAsString(o.optString("storm_orientation"));
    b.stormOrientationDescription(o.optString("storm_orientation_description"));
    b.astro(o.object("astro", Decoders::dailyAstro));
    b.temperatureClimatology(o.optObject("temperature_climatology", Decoders::dayClimatology));
    b.coveredFrom(o.optInstant("covered_from"));
    b.measuredPrecipitation(o.optObject("measured_precipitation", Decoders::measuredPrecipitation));
    b.snowCharacterAsString(o.optString("snow_character"));
    b.slr(o.optNumber("slr"));
    b.snowLevelRange(o.optObject("snow_level_range", Decoders::snowLevelRange));
    b.snowLevelWetBulb(o.optObject("snow_level_wet_bulb", Decoders::quantiles));
    b.snowLevelWetBulbRange(o.optObject("snow_level_wet_bulb_range", Decoders::snowLevelRange));
    b.normal(o.optObject("normal", Decoders::dayNormal));
    b.departure(o.optObject("departure", Decoders::dayDeparture));
    b.percentile(o.optObject("percentile", Decoders::dayPercentile));
    return b.build();
  }

  /**
   * Decodes a {@code DailyWind}.
   *
   * @param o the object
   * @return the model
   */
  public static DailyWind dailyWind(JsonFields o) {
    DailyWind.Builder b = DailyWind.builder();
    b.statusAsString(o.string("status"));
    b.peakSpeed(o.number("peak_speed"));
    b.peakSpeedP90(o.optNumber("peak_speed_p90"));
    b.isGust(o.bool("is_gust"));
    b.sustainedSpeed(o.optNumber("sustained_speed"));
    return b.build();
  }

  /**
   * Decodes a {@code DayClimatology}.
   *
   * @param o the object
   * @return the model
   */
  public static DayClimatology dayClimatology(JsonFields o) {
    DayClimatology.Builder b = DayClimatology.builder();
    b.mean(o.number("mean"));
    b.std(o.number("std"));
    return b.build();
  }

  /**
   * Decodes a {@code ElevationForecast}.
   *
   * @param o the object
   * @return the model
   */
  public static ElevationForecast elevationForecast(JsonFields o) {
    ElevationForecast.Builder b = ElevationForecast.builder();
    b.name(o.string("name"));
    b.height(o.number("elevation"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.hourly(o.list("hourly", Decoders::timeSeriesPoint));
    b.daily(o.list("daily", Decoders::dailyForecast));
    b.snowOutlook(o.optObject("snow_outlook", Decoders::snowOutlook));
    b.displayName(o.optString("display_name"));
    b.nearestSensors(o.list("nearest_sensors", Decoders::nearestSensor));
    b.precipitationOutlook(o.optObject("precipitation_outlook", Decoders::precipOutlook));
    b.plume(o.optObject("plume", Decoders::memberPlume));
    b.snowOnGround(o.optObject("snow_on_ground", Decoders::snowOnGround));
    return b.build();
  }

  /**
   * Decodes a {@code EnsembleStats}.
   *
   * @param o the object
   * @return the model
   */
  public static EnsembleStats ensembleStats(JsonFields o) {
    EnsembleStats.Builder b = EnsembleStats.builder();
    b.temperature(o.object("temperature", Decoders::quantiles));
    b.feelsLike(o.optObject("feels_like", Decoders::quantiles));
    b.dewpoint(o.optObject("dewpoint", Decoders::quantiles));
    b.precipitation(o.object("precipitation", Decoders::quantiles));
    b.windSpeed(o.object("wind_speed", Decoders::quantiles));
    b.windGust(o.optObject("wind_gust", Decoders::quantiles));
    b.windDirection(o.object("wind_direction", Decoders::circularStats));
    b.snowLevel(o.optObject("snow_level", Decoders::quantiles));
    b.slr(o.optObject("slr", Decoders::quantiles));
    b.snowfall(o.optObject("snowfall", Decoders::quantiles));
    b.cloudCover(o.optObject("cloud_cover", Decoders::quantiles));
    b.skinTemperature(o.optObject("skin_temperature", Decoders::quantiles));
    b.mslp(o.optObject("mslp", Decoders::quantiles));
    b.cape(o.optObject("cape", Decoders::quantiles));
    b.relativeHumidity(o.optObject("relative_humidity", Decoders::quantiles));
    b.snowLevelWetBulb(o.optObject("snow_level_wet_bulb", Decoders::quantiles));
    return b.build();
  }

  /**
   * Decodes a {@code ForecastData}.
   *
   * @param o the object
   * @return the model
   */
  public static ForecastData forecastData(JsonFields o) {
    ForecastData.Builder b = ForecastData.builder();
    b.maxHours(o.integer("max_hours"));
    b.intervalHours(o.integer("interval_hours"));
    b.timeseries(o.list("timeseries", Decoders::timeSeriesPoint));
    b.daily(o.list("daily", Decoders::dailyForecast));
    b.snowOutlook(o.optObject("snow_outlook", Decoders::snowOutlook));
    b.precipitationOutlook(o.optObject("precipitation_outlook", Decoders::precipOutlook));
    b.plume(o.optObject("plume", Decoders::memberPlume));
    return b.build();
  }

  /**
   * Decodes a {@code ForecastResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static ForecastResponse forecastResponse(JsonFields o) {
    ForecastResponse.Builder b = ForecastResponse.builder();
    b.thresholds(o.optObject("thresholds", Decoders::probabilityThresholds));
    b.probabilities(o.list("probabilities", Decoders::probabilityResult));
    b.windowTotals(o.list("window_totals", Decoders::windowTotal));
    b.applied(o.optObject("applied", Decoders::appliedOptions));
    b.location(o.object("location", Decoders::forecastLocation));
    b.modelRun(o.object("model_run", Decoders::modelRunInfo));
    b.unitsAsString(o.string("units"));
    b.series(o.object("forecast", Decoders::forecastData));
    b.elevations(o.list("elevation_forecasts", Decoders::elevationForecast));
    b.nowcastApplied(o.bool("nowcast_applied"));
    b.nowcastUnavailableAsStrings(o.stringList("nowcast_unavailable"));
    b.membersViewAsString(o.optString("members_view"));
    b.gapFlow(o.optObject("gap_flow", Decoders::gapFlow));
    b.nearestSensors(o.list("nearest_sensors", Decoders::nearestSensor));
    b.point(o.optObject("point", Decoders::pointInfo));
    b.current(o.optObject("current", Decoders::currentConditions));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
    b.avalanche(o.optObject("avalanche", Decoders::avalancheForecast));
    b.snowOnGround(o.optObject("snow_on_ground", Decoders::snowOnGround));
    return b.build();
  }

  /**
   * Decodes a {@code FreezingLevel}.
   *
   * @param o the object
   * @return the model
   */
  public static FreezingLevel freezingLevel(JsonFields o) {
    FreezingLevel.Builder b = FreezingLevel.builder();
    b.kindAsString(o.string("kind"));
    b.height(o.optNumber("height"));
    b.aboveFreezingLayerAloft(o.optBool("above_freezing_layer_aloft"));
    b.top(o.optNumber("top"));
    return b.build();
  }

  /**
   * Decodes a {@code GapFlow}.
   *
   * @param o the object
   * @return the model
   */
  public static GapFlow gapFlow(JsonFields o) {
    GapFlow.Builder b = GapFlow.builder();
    b.statusAsString(o.string("status"));
    b.pressureDiffHpa(o.number("pressure_diff_hpa"));
    b.windMultiplier(o.number("wind_multiplier"));
    b.direction(o.string("direction"));
    return b.build();
  }

  /**
   * Decodes a {@code HourProvenance}.
   *
   * @param o the object
   * @return the model
   */
  public static HourProvenance hourProvenance(JsonFields o) {
    HourProvenance.Builder b = HourProvenance.builder();
    b.densified(o.optBool("densified"));
    b.ngrAsStrings(o.stringList("ngr"));
    b.fallbackAsStrings(o.stringList("fallback"));
    b.borrowedAsStrings(o.stringList("borrowed"));
    b.spreadFlooredAsStrings(o.stringList("spread_floored"));
    b.floorClampedAsStrings(o.stringList("floor_clamped"));
    b.bucketSplitAsStrings(o.stringList("bucket_split"));
    b.distributionParametersAsStrings(o.stringList("distribution_parameters"));
    b.spreadInterpolatedAsStrings(o.stringList("spread_interpolated"));
    b.interpolatedContributorAsStrings(o.stringList("interpolated_contributor"));
    b.momentMatchedFamilyAsStrings(o.stringList("moment_matched_family"));
    b.lapseProfileAsStrings(o.stringList("lapse_profile"));
    b.lapseClimatologyAsStrings(o.stringList("lapse_climatology"));
    b.lapsePrismAsStrings(o.stringList("lapse_prism"));
    b.spreadClampedLowAsStrings(o.stringList("spread_clamped_low"));
    b.spreadClampedHighAsStrings(o.stringList("spread_clamped_high"));
    b.gustDressedAsStrings(o.stringList("gust_dressed"));
    return b.build();
  }

  /**
   * Decodes a {@code InversionInfo}.
   *
   * @param o the object
   * @return the model
   */
  public static InversionInfo inversionInfo(JsonFields o) {
    InversionInfo.Builder b = InversionInfo.builder();
    b.kindAsString(o.string("kind"));
    b.depth(o.number("depth"));
    b.strength(o.number("strength"));
    return b.build();
  }

  /**
   * Decodes a {@code LocationRef}.
   *
   * @param o the object
   * @return the model
   */
  public static LocationRef locationRef(JsonFields o) {
    LocationRef.Builder b = LocationRef.builder();
    b.slug(o.optString("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.optString("type"));
    b.timezone(o.zone("timezone"));
    return b.build();
  }

  /**
   * Decodes a {@code LocationSummary}.
   *
   * @param o the object
   * @return the model
   */
  public static LocationSummary locationSummary(JsonFields o) {
    LocationSummary.Builder b = LocationSummary.builder();
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.string("county"));
    b.description(o.optString("description"));
    b.elevations(o.list("elevations", Decoders::elevationSummary));
    return b.build();
  }

  /**
   * Decodes a {@code LocationsResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static LocationsResponse locationsResponse(JsonFields o) {
    LocationsResponse.Builder b = LocationsResponse.builder();
    b.state(o.string("state"));
    b.count(o.integer("count"));
    b.items(o.list("locations", Decoders::locationSummary));
    b.nextCursor(o.optString("next_cursor"));
    return b.build();
  }

  /**
   * Decodes a {@code MemberForecast}.
   *
   * @param o the object
   * @return the model
   */
  public static MemberForecast memberForecast(JsonFields o) {
    MemberForecast.Builder b = MemberForecast.builder();
    b.id(o.integer("member_id"));
    b.temperature(o.optNumber("temperature"));
    b.precipitation(o.optNumber("precipitation"));
    b.precipitationHours(o.optInteger("precipitation_hours"));
    b.windSpeed(o.optNumber("wind_speed"));
    b.windDirection(o.optNumber("wind_direction"));
    b.snowLevel(o.optNumber("snow_level"));
    b.slr(o.optNumber("slr"));
    b.snowfall(o.optNumber("snowfall"));
    b.cumulativePrecipitation(o.optNumber("cumulative_precipitation"));
    b.cumulativeSnowfall(o.optNumber("cumulative_snowfall"));
    b.snowLevelWetBulb(o.optNumber("snow_level_wet_bulb"));
    return b.build();
  }

  /**
   * Decodes a {@code MembersCumulative}.
   *
   * @param o the object
   * @return the model
   */
  public static MembersCumulative membersCumulative(JsonFields o) {
    MembersCumulative.Builder b = MembersCumulative.builder();
    b.precipitation(o.optObject("precipitation", Decoders::memberQuantiles));
    b.snowfall(o.optObject("snowfall", Decoders::memberQuantiles));
    return b.build();
  }

  /**
   * Decodes a {@code ModelEntry}.
   *
   * @param o the object
   * @return the model
   */
  public static ModelEntry modelEntry(JsonFields o) {
    ModelEntry.Builder b = ModelEntry.builder();
    b.id(o.string("id"));
    b.cycle(o.optInstant("cycle"));
    b.members(o.optInteger("members"));
    b.displayMembers(o.integerList("display_members"));
    b.displayMembersSnowfall(o.optString("display_members_snowfall"));
    return b.build();
  }

  /**
   * Decodes a {@code ModelMembers}.
   *
   * @param o the object
   * @return the model
   */
  public static ModelMembers modelMembers(JsonFields o) {
    ModelMembers.Builder b = ModelMembers.builder();
    b.id(o.string("id"));
    b.items(o.list("members", Decoders::memberForecast));
    return b.build();
  }

  /**
   * Decodes a {@code ModelRunInfo}.
   *
   * @param o the object
   * @return the model
   */
  public static ModelRunInfo modelRunInfo(JsonFields o) {
    ModelRunInfo.Builder b = ModelRunInfo.builder();
    b.initializedAt(o.instant("initialized_at"));
    b.totalMembers(o.integer("total_members"));
    b.models(o.list("models", Decoders::modelEntry));
    return b.build();
  }

  /**
   * Decodes a {@code ModelStats}.
   *
   * @param o the object
   * @return the model
   */
  public static ModelStats modelStats(JsonFields o) {
    ModelStats.Builder b = ModelStats.builder();
    b.id(o.string("id"));
    b.temperature(o.optObject("temperature", Decoders::quantiles));
    b.dewpoint(o.optObject("dewpoint", Decoders::quantiles));
    b.precipitation(o.optObject("precipitation", Decoders::quantiles));
    b.windSpeed(o.optObject("wind_speed", Decoders::quantiles));
    b.windDirection(o.optObject("wind_direction", Decoders::circularStats));
    b.snowLevel(o.optObject("snow_level", Decoders::quantiles));
    b.slr(o.optObject("slr", Decoders::quantiles));
    b.snowfall(o.optObject("snowfall", Decoders::quantiles));
    b.interpolated(o.optBool("interpolated"));
    b.snowLevelWetBulb(o.optObject("snow_level_wet_bulb", Decoders::quantiles));
    return b.build();
  }

  /**
   * Decodes a {@code NearbyLocation}.
   *
   * @param o the object
   * @return the model
   */
  public static NearbyLocation nearbyLocation(JsonFields o) {
    NearbyLocation.Builder b = NearbyLocation.builder();
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.type(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.string("county"));
    b.description(o.optString("description"));
    b.elevations(o.list("elevations", Decoders::elevationSummary));
    b.distanceKm(o.number("distance_km"));
    return b.build();
  }

  /**
   * Decodes a {@code NearbyLocationsResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static NearbyLocationsResponse nearbyLocationsResponse(JsonFields o) {
    NearbyLocationsResponse.Builder b = NearbyLocationsResponse.builder();
    b.origin(o.object("location", Decoders::locationSummary));
    b.radiusKm(o.number("radius_km"));
    b.count(o.integer("count"));
    b.items(o.list("locations", Decoders::nearbyLocation));
    return b.build();
  }

  /**
   * Decodes a {@code NearbyPointResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static NearbyPointResponse nearbyPointResponse(JsonFields o) {
    NearbyPointResponse.Builder b = NearbyPointResponse.builder();
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.radiusKm(o.number("radius_km"));
    b.count(o.integer("count"));
    b.items(o.list("locations", Decoders::nearbyLocation));
    return b.build();
  }

  /**
   * Decodes a {@code NearestLocation}.
   *
   * @param o the object
   * @return the model
   */
  public static NearestLocation nearestLocation(JsonFields o) {
    NearestLocation.Builder b = NearestLocation.builder();
    b.slug(o.string("slug"));
    b.url(o.string("url"));
    b.distanceKm(o.number("distance_km"));
    b.elevationM(o.number("elevation_m"));
    return b.build();
  }

  /**
   * Decodes a {@code NearestSensor}.
   *
   * @param o the object
   * @return the model
   */
  public static NearestSensor nearestSensor(JsonFields o) {
    NearestSensor.Builder b = NearestSensor.builder();
    b.stationId(o.string("station_id"));
    b.name(o.string("name"));
    b.source(o.string("source"));
    b.distanceKm(o.number("distance_km"));
    b.elevationDiff(o.number("elevation_diff"));
    b.effectiveDistanceKm(o.number("effective_distance_km"));
    b.observationTime(o.instant("observation_time"));
    return b.build();
  }

  /**
   * Decodes a {@code PointInfo}.
   *
   * @param o the object
   * @return the model
   */
  public static PointInfo pointInfo(JsonFields o) {
    PointInfo.Builder b = PointInfo.builder();
    b.requestedLat(o.number("requested_lat"));
    b.requestedLon(o.number("requested_lon"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.resolutionDeg(o.number("resolution_deg"));
    b.elevation(o.number("elevation"));
    b.elevationSourceAsString(o.string("elevation_source"));
    b.state(o.string("state"));
    return b.build();
  }

  /**
   * Decodes a {@code PrecipDisplay}.
   *
   * @param o the object
   * @return the model
   */
  public static PrecipDisplay precipDisplay(JsonFields o) {
    PrecipDisplay.Builder b = PrecipDisplay.builder();
    b.kindAsString(o.string("kind"));
    b.chance(o.number("chance"));
    b.amount(o.optNumber("amount"));
    b.snowAmount(o.optNumber("snow_amount"));
    return b.build();
  }

  /**
   * Decodes a {@code PrecipType}.
   *
   * @param o the object
   * @return the model
   */
  public static PrecipType precipType(JsonFields o) {
    PrecipType.Builder b = PrecipType.builder();
    b.typeAsString(o.string("type"));
    b.snowProbability(o.number("snow_probability"));
    b.rainProbability(o.number("rain_probability"));
    return b.build();
  }

  /**
   * Decodes a {@code ProbabilityResult}.
   *
   * @param o the object
   * @return the model
   */
  public static ProbabilityResult probabilityResult(JsonFields o) {
    ProbabilityResult.Builder b = ProbabilityResult.builder();
    b.term(o.string("term"));
    b.variableAsString(o.string("variable"));
    b.opAsString(o.string("op"));
    b.threshold(o.number("threshold"));
    b.windowStartHours(o.integer("window_start_hours"));
    b.windowEndHours(o.integer("window_end_hours"));
    b.aggregationAsString(o.string("aggregation"));
    b.from(o.optInstant("from"));
    b.to(o.optInstant("to"));
    b.value(o.optNumber("probability"));
    b.members(o.integer("members"));
    b.nullReasonAsString(o.optString("null_reason"));
    return b.build();
  }

  /**
   * Decodes a {@code ProbabilityThresholds}.
   *
   * @param o the object
   * @return the model
   */
  public static ProbabilityThresholds probabilityThresholds(JsonFields o) {
    ProbabilityThresholds.Builder b = ProbabilityThresholds.builder();
    b.hourlyPrecipitation(o.number("hourly_precipitation"));
    b.hourlySnowfall(o.number("hourly_snowfall"));
    b.dailyPrecipitation(o.number("daily_precipitation_probability"));
    b.dailyPrecipitationOccurrence(o.number("daily_precipitation_occurrence_probability"));
    b.dailySnow(o.number("daily_snow_probability"));
    return b.build();
  }

  /**
   * Decodes a {@code Quantiles}.
   *
   * @param o the object
   * @return the model
   */
  public static Quantiles quantiles(JsonFields o) {
    Quantiles.Builder b = Quantiles.builder();
    b.mean(o.optNumber("mean"));
    b.p10(o.optNumber("p10"));
    b.p25(o.optNumber("p25"));
    b.p50(o.optNumber("p50"));
    b.p75(o.optNumber("p75"));
    b.p90(o.optNumber("p90"));
    b.min(o.optNumber("min"));
    b.max(o.optNumber("max"));
    b.probability(o.optNumber("probability"));
    return b.build();
  }

  /**
   * Decodes a {@code ResponseAttribution}.
   *
   * @param o the object
   * @return the model
   */
  public static ResponseAttribution responseAttribution(JsonFields o) {
    ResponseAttribution.Builder b = ResponseAttribution.builder();
    b.sources(o.stringList("sources"));
    b.credit(o.string("credit"));
    b.url(o.string("url"));
    return b.build();
  }

  /**
   * Decodes a {@code RunInfo}.
   *
   * @param o the object
   * @return the model
   */
  public static RunInfo runInfo(JsonFields o) {
    RunInfo.Builder b = RunInfo.builder();
    b.initializedAt(o.instant("initialized_at"));
    return b.build();
  }

  /**
   * Decodes a {@code RunsResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static RunsResponse runsResponse(JsonFields o) {
    RunsResponse.Builder b = RunsResponse.builder();
    b.served(o.optObject("served", Decoders::servedRun));
    b.items(o.list("runs", Decoders::runInfo));
    b.count(o.integer("count"));
    b.nextCursor(o.optString("next_cursor"));
    return b.build();
  }

  /**
   * Decodes a {@code SearchHit}.
   *
   * @param o the object
   * @return the model
   */
  public static SearchHit searchHit(JsonFields o) {
    SearchHit.Builder b = SearchHit.builder();
    b.key(o.string("key"));
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.string("county"));
    b.explore(o.optObject("explore", Decoders::exploreValue));
    return b.build();
  }

  /**
   * Decodes a {@code SearchLocationsResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static SearchLocationsResponse searchLocationsResponse(JsonFields o) {
    SearchLocationsResponse.Builder b = SearchLocationsResponse.builder();
    b.items(o.list("items", Decoders::searchHit));
    b.count(o.integer("count"));
    return b.build();
  }

  /**
   * Decodes a {@code Sector}.
   *
   * @param o the object
   * @return the model
   */
  public static Sector sector(JsonFields o) {
    Sector.Builder b = Sector.builder();
    b.from(o.number("from"));
    b.to(o.number("to"));
    return b.build();
  }

  /**
   * Decodes a {@code SensorObservation}.
   *
   * @param o the object
   * @return the model
   */
  public static SensorObservation sensorObservation(JsonFields o) {
    SensorObservation.Builder b = SensorObservation.builder();
    b.stationId(o.string("station_id"));
    b.name(o.string("name"));
    b.source(o.string("source"));
    b.observedAt(o.instant("observation_time"));
    b.temperature(o.optNumber("temperature"));
    b.dewpoint(o.optNumber("dewpoint"));
    b.relativeHumidity(o.optNumber("relative_humidity"));
    b.windSpeed(o.optNumber("wind_speed"));
    b.windGust(o.optNumber("wind_gust"));
    b.windDirection(o.optNumber("wind_direction"));
    b.pressure(o.optNumber("pressure"));
    b.precipitation(o.optNumber("precipitation"));
    b.precipitationRate(o.optNumber("precipitation_rate"));
    b.reflectivity(o.optNumber("reflectivity"));
    b.pavementTemp(o.optNumber("pavement_temp"));
    b.subsurfaceTemp(o.optNumber("subsurface_temp"));
    b.visibility(o.optNumber("visibility"));
    b.cloudCover(o.optNumber("cloud_cover"));
    b.ceilingHeight(o.optNumber("ceiling_height"));
    b.snowDepth(o.optNumber("snow_depth"));
    b.swe(o.optNumber("swe"));
    b.swePercentOfMedian(o.optNumber("swe_percent_of_median"));
    b.snowfall(o.optNumber("snowfall"));
    b.solarRadiation(o.optNumber("solar_radiation"));
    b.pavementCondition(o.optString("pavement_condition"));
    b.distanceKm(o.number("distance_km"));
    b.elevationDiff(o.optNumber("elevation_diff"));
    return b.build();
  }

  /**
   * Decodes a {@code ServedRun}.
   *
   * @param o the object
   * @return the model
   */
  public static ServedRun servedRun(JsonFields o) {
    ServedRun.Builder b = ServedRun.builder();
    b.initializedAt(o.instant("initialized_at"));
    b.models(o.list("models", Decoders::modelEntry));
    return b.build();
  }

  /**
   * Decodes a {@code SnowOutlook}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowOutlook snowOutlook(JsonFields o) {
    SnowOutlook.Builder b = SnowOutlook.builder();
    b.next24h(o.optNumber("next_24h"));
    b.next48h(o.optNumber("next_48h"));
    b.next7d(o.optNumber("next_7d"));
    b.overnight4pm9am(o.optNumber("overnight_4pm_9am"));
    b.quantiles(o.optObject("quantiles", Decoders::snowOutlookQuantiles));
    b.next72h(o.optNumber("next_72h"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowOutlookQuantiles}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowOutlookQuantiles snowOutlookQuantiles(JsonFields o) {
    SnowOutlookQuantiles.Builder b = SnowOutlookQuantiles.builder();
    b.next24h(o.optObject("next_24h", Decoders::quantiles));
    b.next48h(o.optObject("next_48h", Decoders::quantiles));
    b.next7d(o.optObject("next_7d", Decoders::quantiles));
    b.next72h(o.optObject("next_72h", Decoders::quantiles));
    return b.build();
  }

  /**
   * Decodes a {@code TimeSeriesPoint}.
   *
   * @param o the object
   * @return the model
   */
  public static TimeSeriesPoint timeSeriesPoint(JsonFields o) {
    TimeSeriesPoint.Builder b = TimeSeriesPoint.builder();
    b.validTime(o.instant("valid_time"));
    b.leadTimeHours(o.integer("lead_time_hours"));
    b.isNight(o.bool("is_night"));
    b.conditionAsString(o.string("condition"));
    b.conditionText(o.string("condition_text"));
    b.conditionIconAsString(o.string("condition_icon"));
    b.gustNotable(o.optBool("gust_notable"));
    b.precipitationType(o.optObject("precipitation_type", Decoders::precipType));
    b.inversion(o.optObject("inversion", Decoders::inversionInfo));
    b.freezingLevel(o.optObject("freezing_level", Decoders::freezingLevel));
    b.ensemble(o.object("ensemble", Decoders::ensembleStats));
    b.provenance(o.optObject("provenance", Decoders::hourProvenance));
    b.modelMeans(o.list("model_means", Decoders::modelStats));
    b.members(o.list("members", Decoders::modelMembers));
    b.membersCumulative(o.optObject("members_cumulative", Decoders::membersCumulative));
    b.cumulativePrecipitation(o.optNumber("cumulative_precipitation"));
    b.cumulativeSnowfall(o.optNumber("cumulative_snowfall"));
    b.measured(o.bool("measured"));
    b.snowCharacterAsString(o.optString("snow_character"));
    b.membersSpread(o.optObject("members_spread", Decoders::membersSpread));
    b.members6h(o.optObject("members_6h", Decoders::membersCumulative));
    b.precipitationByType(o.optObject("precipitation_by_type", Decoders::precipitationByType));
    return b.build();
  }

  /**
   * Decodes a {@code WindowTotal}.
   *
   * @param o the object
   * @return the model
   */
  public static WindowTotal windowTotal(JsonFields o) {
    WindowTotal.Builder b = WindowTotal.builder();
    b.term(o.string("term"));
    b.variableAsString(o.string("variable"));
    b.startHours(o.integer("window_start_hours"));
    b.endHours(o.integer("window_end_hours"));
    b.aggregationAsString(o.string("aggregation"));
    b.from(o.optInstant("from"));
    b.to(o.optInstant("to"));
    b.quantiles(o.optObject("quantiles", Decoders::quantiles));
    b.members(o.integer("members"));
    b.nullReasonAsString(o.optString("null_reason"));
    b.method(o.optString("method"));
    return b.build();
  }

  /**
   * Decodes a {@code ForecastLocation}.
   *
   * @param o the object
   * @return the model
   */
  public static ForecastLocation forecastLocation(JsonFields o) {
    ForecastLocation.Builder b = ForecastLocation.builder();
    b.slug(o.optString("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.optString("type"));
    b.timezone(o.zone("timezone"));
    b.elevation(o.number("elevation"));
    b.elevations(o.list("elevations", Decoders::elevationSummary));
    return b.build();
  }

  /**
   * Decodes a {@code ElevationSummary}.
   *
   * @param o the object
   * @return the model
   */
  public static ElevationSummary elevationSummary(JsonFields o) {
    ElevationSummary.Builder b = ElevationSummary.builder();
    b.name(o.string("name"));
    b.displayName(o.optString("display_name"));
    b.height(o.number("elevation"));
    b.isDefault(o.optBool("default"));
    return b.build();
  }

  /**
   * Decodes a {@code MemberQuantiles}.
   *
   * @param o the object
   * @return the model
   */
  public static MemberQuantiles memberQuantiles(JsonFields o) {
    MemberQuantiles.Builder b = MemberQuantiles.builder();
    b.mean(o.number("mean"));
    b.p10(o.number("p10"));
    b.p25(o.number("p25"));
    b.p50(o.optNumber("p50"));
    b.p75(o.number("p75"));
    b.p90(o.number("p90"));
    b.min(o.number("min"));
    b.max(o.number("max"));
    b.probability(o.optNumber("probability"));
    return b.build();
  }

  /**
   * Decodes a {@code MembersSpread}.
   *
   * @param o the object
   * @return the model
   */
  public static MembersSpread membersSpread(JsonFields o) {
    MembersSpread.Builder b = MembersSpread.builder();
    b.temperature(o.optObject("temperature", Decoders::memberQuantiles));
    b.snowLevel(o.optObject("snow_level", Decoders::memberQuantiles));
    b.snowLevelWetBulb(o.optObject("snow_level_wet_bulb", Decoders::memberQuantiles));
    b.slr(o.optObject("slr", Decoders::memberQuantiles));
    b.windSpeed(o.optObject("wind_speed", Decoders::memberQuantiles));
    return b.build();
  }

  /**
   * Decodes a {@code PrecipOutlook}.
   *
   * @param o the object
   * @return the model
   */
  public static PrecipOutlook precipOutlook(JsonFields o) {
    PrecipOutlook.Builder b = PrecipOutlook.builder();
    b.next24h(o.optNumber("next_24h"));
    b.next48h(o.optNumber("next_48h"));
    b.next72h(o.optNumber("next_72h"));
    b.next7d(o.optNumber("next_7d"));
    b.quantiles(o.optObject("quantiles", Decoders::snowOutlookQuantiles));
    return b.build();
  }

  /**
   * Decodes a {@code PrecipitationByType}.
   *
   * @param o the object
   * @return the model
   */
  public static PrecipitationByType precipitationByType(JsonFields o) {
    PrecipitationByType.Builder b = PrecipitationByType.builder();
    b.rain(o.number("rain"));
    b.snow(o.number("snow"));
    b.rainSnowMix(o.number("rain_snow_mix"));
    b.freezingRain(o.number("freezing_rain"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowLevelRange}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowLevelRange snowLevelRange(JsonFields o) {
    SnowLevelRange.Builder b = SnowLevelRange.builder();
    b.low(o.number("low"));
    b.high(o.number("high"));
    b.mean(o.number("mean"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowOnGround}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowOnGround snowOnGround(JsonFields o) {
    SnowOnGround.Builder b = SnowOnGround.builder();
    b.beta(o.bool("beta"));
    b.depth(o.number("depth"));
    b.basis(o.string("basis"));
    b.surface(o.optString("surface"));
    b.validTime(o.instant("valid_time"));
    b.generatedAt(o.instant("generated_at"));
    return b.build();
  }

  /**
   * Decodes a {@code DayNormal}.
   *
   * @param o the object
   * @return the model
   */
  public static DayNormal dayNormal(JsonFields o) {
    DayNormal.Builder b = DayNormal.builder();
    b.high(o.optNumber("high"));
    b.low(o.optNumber("low"));
    b.precipitation(o.optNumber("precipitation"));
    b.source(o.string("source"));
    b.distanceKm(o.number("distance_km"));
    return b.build();
  }

  /**
   * Decodes a {@code DayDeparture}.
   *
   * @param o the object
   * @return the model
   */
  public static DayDeparture dayDeparture(JsonFields o) {
    DayDeparture.Builder b = DayDeparture.builder();
    b.high(o.optNumber("high"));
    b.low(o.optNumber("low"));
    return b.build();
  }

  /**
   * Decodes a {@code DayPercentile}.
   *
   * @param o the object
   * @return the model
   */
  public static DayPercentile dayPercentile(JsonFields o) {
    DayPercentile.Builder b = DayPercentile.builder();
    b.high(o.optNumber("high"));
    b.low(o.optNumber("low"));
    b.precipitation(o.optNumber("precipitation"));
    return b.build();
  }

  /**
   * Decodes a {@code AvalancheForecast}.
   *
   * @param o the object
   * @return the model
   */
  public static AvalancheForecast avalancheForecast(JsonFields o) {
    AvalancheForecast.Builder b = AvalancheForecast.builder();
    b.centerId(o.string("center_id"));
    b.centerName(o.string("center_name"));
    b.zoneId(o.longInteger("zone_id"));
    b.zoneName(o.string("zone_name"));
    b.dangerLevel(o.optInteger("danger_level"));
    b.dangerLabel(o.optString("danger_label"));
    b.status(o.string("status"));
    b.validFrom(o.optInstant("valid_from"));
    b.validUntil(o.optInstant("valid_until"));
    b.url(o.string("forecast_url"));
    b.centerUrl(o.string("center_url"));
    b.attribution(o.string("attribution"));
    return b.build();
  }

  /**
   * Decodes a {@code MemberPlume}.
   *
   * @param o the object
   * @return the model
   */
  public static MemberPlume memberPlume(JsonFields o) {
    MemberPlume.Builder b = MemberPlume.builder();
    b.headline(o.object("headline", Decoders::plumeHeadline));
    b.bands(o.object("bands", Decoders::plumeBands));
    b.models(o.list("models", Decoders::modelColumns));
    b.pool(o.optObject("members", Decoders::pooledColumns));
    return b.build();
  }

  /**
   * Decodes a {@code PlumeHeadline}.
   *
   * @param o the object
   * @return the model
   */
  public static PlumeHeadline plumeHeadline(JsonFields o) {
    PlumeHeadline.Builder b = PlumeHeadline.builder();
    b.validTime(o.instantList("valid_time"));
    b.temperature(o.nullableNumberList("temperature"));
    b.snowLevel(o.nullableNumberList("snow_level"));
    b.snowLevelWetBulb(o.nullableNumberList("snow_level_wet_bulb"));
    b.cumulativePrecipitation(o.nullableNumberList("cumulative_precipitation"));
    b.cumulativeSnowfall(o.nullableNumberList("cumulative_snowfall"));
    b.cumulativeSnowfall10to1(o.nullableNumberList("cumulative_snowfall_10to1"));
    b.windSpeed(o.nullableNumberList("wind_speed"));
    b.snowfall6h(o.nullableNumberList("snowfall_6h"));
    return b.build();
  }

  /**
   * Decodes a {@code PlumeBands}.
   *
   * @param o the object
   * @return the model
   */
  public static PlumeBands plumeBands(JsonFields o) {
    PlumeBands.Builder b = PlumeBands.builder();
    b.validTime(o.instantList("valid_time"));
    b.cumulativePrecipitation(o.object("cumulative_precipitation", Decoders::bandColumns));
    b.cumulativeSnowfall(o.object("cumulative_snowfall", Decoders::bandColumns));
    b.cumulativeSnowfall10to1(o.object("cumulative_snowfall_10to1", Decoders::bandColumns));
    b.temperature(o.object("temperature", Decoders::bandColumns));
    b.snowLevel(o.object("snow_level", Decoders::bandColumns));
    b.snowLevelWetBulb(o.object("snow_level_wet_bulb", Decoders::bandColumns));
    b.precipitation6h(o.object("precipitation_6h", Decoders::bandColumns));
    b.snowfall6h(o.object("snowfall_6h", Decoders::bandColumns));
    b.slr(o.optObject("slr", Decoders::bandColumns));
    b.windSpeed(o.optObject("wind_speed", Decoders::bandColumns));
    return b.build();
  }

  /**
   * Decodes a {@code BandColumns}.
   *
   * @param o the object
   * @return the model
   */
  public static BandColumns bandColumns(JsonFields o) {
    BandColumns.Builder b = BandColumns.builder();
    b.mean(o.nullableNumberList("mean"));
    b.min(o.nullableNumberList("min"));
    b.p10(o.nullableNumberList("p10"));
    b.p25(o.nullableNumberList("p25"));
    b.p50(o.nullableNumberList("p50"));
    b.p75(o.nullableNumberList("p75"));
    b.p90(o.nullableNumberList("p90"));
    b.max(o.nullableNumberList("max"));
    return b.build();
  }

  /**
   * Decodes a {@code PooledColumns}.
   *
   * @param o the object
   * @return the model
   */
  public static PooledColumns pooledColumns(JsonFields o) {
    PooledColumns.Builder b = PooledColumns.builder();
    b.validTime(o.instantList("valid_time"));
    b.temperature(o.nullableNumberLists("temperature"));
    b.precipitation(o.nullableNumberLists("precipitation"));
    b.precipitationHours(o.nullableIntegerLists("precipitation_hours"));
    b.cumulativePrecipitation(o.nullableNumberLists("cumulative_precipitation"));
    b.snowLevel(o.nullableNumberLists("snow_level"));
    b.snowLevelWetBulb(o.nullableNumberLists("snow_level_wet_bulb"));
    b.snowfall(o.nullableNumberLists("snowfall"));
    b.cumulativeSnowfall(o.nullableNumberLists("cumulative_snowfall"));
    b.slr(o.nullableNumberLists("slr"));
    b.cumulativePrecipitationBandMembers(o.integerList("cumulative_precipitation_band_members"));
    b.cumulativeSnowfallBandMembers(o.integerList("cumulative_snowfall_band_members"));
    b.cumulativeSnowfall10to1BandMembers(o.integerList("cumulative_snowfall_10to1_band_members"));
    return b.build();
  }

  /**
   * Decodes a {@code ModelColumns}.
   *
   * @param o the object
   * @return the model
   */
  public static ModelColumns modelColumns(JsonFields o) {
    ModelColumns.Builder b = ModelColumns.builder();
    b.id(o.string("id"));
    b.validTime(o.instantList("valid_time"));
    b.memberIds(o.integerList("member_ids"));
    b.temperature(o.nullableNumberLists("temperature"));
    b.precipitation(o.nullableNumberLists("precipitation"));
    b.precipitationHours(o.nullableIntegerLists("precipitation_hours"));
    b.cumulativePrecipitation(o.nullableNumberLists("cumulative_precipitation"));
    b.snowLevel(o.nullableNumberLists("snow_level"));
    b.snowLevelWetBulb(o.nullableNumberLists("snow_level_wet_bulb"));
    b.snowfall(o.nullableNumberLists("snowfall"));
    b.cumulativeSnowfall(o.nullableNumberLists("cumulative_snowfall"));
    b.cumulativeSnowfallP50(o.nullableNumberList("cumulative_snowfall_p50"));
    b.cumulativeSnowfall10to1P50(o.nullableNumberList("cumulative_snowfall_10to1_p50"));
    b.temperatureMean(o.nullableNumberList("temperature_mean"));
    b.precipitationMean(o.nullableNumberList("precipitation_mean"));
    b.cumulativePrecipitationMean(o.nullableNumberList("cumulative_precipitation_mean"));
    b.snowLevelMean(o.nullableNumberList("snow_level_mean"));
    b.snowLevelWetBulbMean(o.nullableNumberList("snow_level_wet_bulb_mean"));
    b.snowfallMean(o.nullableNumberList("snowfall_mean"));
    b.cumulativeSnowfallMean(o.nullableNumberList("cumulative_snowfall_mean"));
    b.cumulativeSnowfall10to1Mean(o.nullableNumberList("cumulative_snowfall_10to1_mean"));
    b.windSpeedMean(o.nullableNumberList("wind_speed_mean"));
    b.cumulativePrecipitationBandMemberIds(
        o.integerList("cumulative_precipitation_band_member_ids"));
    b.cumulativeSnowfallBandMemberIds(o.integerList("cumulative_snowfall_band_member_ids"));
    b.cumulativeSnowfall10to1BandMemberIds(
        o.integerList("cumulative_snowfall_10to1_band_member_ids"));
    b.windSpeed(o.nullableNumberLists("wind_speed"));
    b.windDirection(o.nullableNumberLists("wind_direction"));
    b.slr(o.nullableNumberLists("slr"));
    return b.build();
  }

  /**
   * Decodes a {@code PlaceSummary}.
   *
   * @param o the object
   * @return the model
   */
  public static PlaceSummary placeSummary(JsonFields o) {
    PlaceSummary.Builder b = PlaceSummary.builder();
    b.shortText(o.string("short"));
    b.longText(o.stringList("long"));
    b.events(o.list("events", Decoders::summaryEvent));
    b.elevationName(o.optString("elevation_name"));
    b.elevation(o.number("elevation"));
    b.run(o.instant("run"));
    b.generatedAt(o.instant("generated_at"));
    return b.build();
  }

  /**
   * Decodes a {@code SummaryEvent}.
   *
   * @param o the object
   * @return the model
   */
  public static SummaryEvent summaryEvent(JsonFields o) {
    SummaryEvent.Builder b = SummaryEvent.builder();
    b.kind(o.string("kind"));
    b.template(o.string("template"));
    b.at(o.optInstant("at"));
    b.until(o.optInstant("until"));
    b.alertId(o.optString("alert_id"));
    b.values(o.optObject("values", Decoders::summaryValues));
    return b.build();
  }

  /**
   * Decodes a {@code SummaryValues}.
   *
   * @param o the object
   * @return the model
   */
  public static SummaryValues summaryValues(JsonFields o) {
    SummaryValues.Builder b = SummaryValues.builder();
    b.snowfall(o.optNumber("snowfall"));
    b.snowfallP10(o.optNumber("snowfall_p10"));
    b.snowfallP90(o.optNumber("snowfall_p90"));
    b.snowfallWindow(o.optString("snowfall_window"));
    b.temperature(o.optNumber("temperature"));
    b.windGust(o.optNumber("wind_gust"));
    b.precipitationProbability(o.optNumber("precipitation_probability"));
    return b.build();
  }

  /**
   * Decodes a {@code PassForecast}.
   *
   * @param o the object
   * @return the model
   */
  public static PassForecast passForecast(JsonFields o) {
    PassForecast.Builder b = PassForecast.builder();
    b.elevation(o.number("elevation"));
    b.elevationM(o.number("elevation_m"));
    b.from(o.instant("from"));
    b.snow24h(o.object("snow_24h", Decoders::passSnow24h));
    b.chances(o.object("chances", Decoders::passChances));
    b.snowWindows(o.list("snow_windows", Decoders::passSnowWindow));
    b.hours(o.list("hours", Decoders::passHour));
    b.windows(o.object("windows", Decoders::passTravelWindows));
    b.officialStatus(o.optObject("official_status", Decoders::passOfficialStatus));
    b.roadSensors(o.list("road_sensors", Decoders::passRoadSensor));
    b.cameras(o.list("cameras", Decoders::passCamera));
    return b.build();
  }

  /**
   * Decodes a {@code PassSnow24h}.
   *
   * @param o the object
   * @return the model
   */
  public static PassSnow24h passSnow24h(JsonFields o) {
    PassSnow24h.Builder b = PassSnow24h.builder();
    b.total(o.optNumber("total"));
    b.p10(o.optNumber("p10"));
    b.p90(o.optNumber("p90"));
    b.startsAt(o.optInstant("starts_at"));
    return b.build();
  }

  /**
   * Decodes a {@code PassChances}.
   *
   * @param o the object
   * @return the model
   */
  public static PassChances passChances(JsonFields o) {
    PassChances.Builder b = PassChances.builder();
    b.windowHours(o.integer("window_hours"));
    b.from(o.instant("from"));
    b.to(o.instant("to"));
    b.pSnowGe1in(o.object("p_snow_ge_1in", Decoders::passChance));
    b.pSnowGe6in(o.object("p_snow_ge_6in", Decoders::passChance));
    b.pWetBelowFreezing(o.object("p_wet_below_freezing", Decoders::passChance));
    b.pGustGt40mph(o.object("p_gust_gt_40mph", Decoders::passChance));
    return b.build();
  }

  /**
   * Decodes a {@code PassChance}.
   *
   * @param o the object
   * @return the model
   */
  public static PassChance passChance(JsonFields o) {
    PassChance.Builder b = PassChance.builder();
    b.probability(o.optNumber("probability"));
    b.threshold(o.number("threshold"));
    b.precipitationThreshold(o.optNumber("precipitation_threshold"));
    b.members(o.integer("members"));
    b.method(o.string("method"));
    b.nullReason(o.optString("null_reason"));
    return b.build();
  }

  /**
   * Decodes a {@code PassSnowWindow}.
   *
   * @param o the object
   * @return the model
   */
  public static PassSnowWindow passSnowWindow(JsonFields o) {
    PassSnowWindow.Builder b = PassSnowWindow.builder();
    b.start(o.instant("start"));
    b.end(o.instant("end"));
    b.total(o.number("total"));
    b.p10(o.optNumber("p10"));
    b.p90(o.optNumber("p90"));
    b.pGe1in(o.optNumber("p_ge_1in"));
    return b.build();
  }

  /**
   * Decodes a {@code PassHour}.
   *
   * @param o the object
   * @return the model
   */
  public static PassHour passHour(JsonFields o) {
    PassHour.Builder b = PassHour.builder();
    b.time(o.instant("time"));
    b.temperature(o.number("temperature"));
    b.precipitation(o.number("precipitation"));
    b.precipitationProbability(o.optNumber("precipitation_probability"));
    b.precipitationType(o.optString("precipitation_type"));
    b.snowfall(o.number("snowfall"));
    b.snowLevel(o.optNumber("snow_level"));
    b.snowLevelMargin(o.optNumber("snow_level_above_pass"));
    b.freezingOnWetRoad(o.bool("freezing_on_wet_road"));
    b.windSpeed(o.number("wind_speed"));
    b.windGust(o.optNumber("wind_gust"));
    b.gustOver40mph(o.bool("gust_over_40mph"));
    b.blowingSnow(o.bool("blowing_snow"));
    return b.build();
  }

  /**
   * Decodes a {@code PassTravelWindows}.
   *
   * @param o the object
   * @return the model
   */
  public static PassTravelWindows passTravelWindows(JsonFields o) {
    PassTravelWindows.Builder b = PassTravelWindows.builder();
    b.horizonHours(o.integer("horizon_hours"));
    b.leastWeather(o.optObject("least_weather", Decoders::passTravelWindow));
    b.mostWeather(o.optObject("most_weather", Decoders::passTravelWindow));
    return b.build();
  }

  /**
   * Decodes a {@code PassTravelWindow}.
   *
   * @param o the object
   * @return the model
   */
  public static PassTravelWindow passTravelWindow(JsonFields o) {
    PassTravelWindow.Builder b = PassTravelWindow.builder();
    b.start(o.instant("start"));
    b.end(o.instant("end"));
    b.snowfall(o.number("snowfall"));
    b.freezingOnWetRoadHours(o.integer("freezing_on_wet_road_hours"));
    b.maxWindGust(o.optNumber("max_wind_gust"));
    return b.build();
  }

  /**
   * Decodes a {@code PassOfficialStatus}.
   *
   * @param o the object
   * @return the model
   */
  public static PassOfficialStatus passOfficialStatus(JsonFields o) {
    PassOfficialStatus.Builder b = PassOfficialStatus.builder();
    b.source(o.string("source"));
    b.attribution(o.string("attribution"));
    b.url(o.string("url"));
    b.checkedAt(o.instant("checked_at"));
    b.stale(o.bool("stale"));
    b.levelAsString(o.string("level"));
    b.segments(o.list("segments", Decoders::passStatusSegment));
    return b.build();
  }

  /**
   * Decodes a {@code PassStatusSegment}.
   *
   * @param o the object
   * @return the model
   */
  public static PassStatusSegment passStatusSegment(JsonFields o) {
    PassStatusSegment.Builder b = PassStatusSegment.builder();
    b.id(o.string("id"));
    b.route(o.string("route"));
    b.direction(o.string("direction"));
    b.location(o.string("location"));
    b.state(o.string("status"));
    b.levelAsString(o.string("level"));
    b.text(o.string("status_text"));
    b.updatedAt(o.instant("updated_at"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.roadCondition(o.optString("road_condition"));
    b.weatherCondition(o.optString("weather_condition"));
    return b.build();
  }

  /**
   * Decodes a {@code PassRoadSensor}.
   *
   * @param o the object
   * @return the model
   */
  public static PassRoadSensor passRoadSensor(JsonFields o) {
    PassRoadSensor.Builder b = PassRoadSensor.builder();
    b.stationId(o.string("station_id"));
    b.name(o.string("name"));
    b.source(o.string("source"));
    b.attribution(o.string("attribution"));
    b.distanceKm(o.number("distance_km"));
    b.elevation(o.optNumber("elevation"));
    b.elevationM(o.optNumber("elevation_m"));
    b.observedAt(o.instant("observed_at"));
    b.stale(o.bool("stale"));
    b.pavementCondition(o.optString("pavement_condition"));
    b.pavementTemp(o.optNumber("pavement_temp"));
    b.subsurfaceTemp(o.optNumber("subsurface_temp"));
    b.temperature(o.optNumber("temperature"));
    b.windSpeed(o.optNumber("wind_speed"));
    b.windGust(o.optNumber("wind_gust"));
    b.windDirection(o.optNumber("wind_direction"));
    b.visibility(o.optNumber("visibility"));
    return b.build();
  }

  /**
   * Decodes a {@code PassCamera}.
   *
   * @param o the object
   * @return the model
   */
  public static PassCamera passCamera(JsonFields o) {
    PassCamera.Builder b = PassCamera.builder();
    b.id(o.string("id"));
    b.title(o.string("title"));
    b.imageUrl(o.string("image_url"));
    b.url(o.string("url"));
    b.capturedAt(o.optInstant("captured_at"));
    b.stale(o.bool("stale"));
    b.source(o.string("source"));
    b.attribution(o.string("attribution"));
    return b.build();
  }

  /**
   * Decodes a {@code ExploreResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static ExploreResponse exploreResponse(JsonFields o) {
    ExploreResponse.Builder b = ExploreResponse.builder();
    b.metricAsString(o.string("metric"));
    b.order(o.string("order"));
    b.unitsAsString(o.string("units"));
    b.unit(o.string("unit"));
    b.items(o.list("items", Decoders::exploreItem));
    b.count(o.integer("count"));
    b.nextCursor(o.optString("next_cursor"));
    return b.build();
  }

  /**
   * Decodes a {@code ExploreItem}.
   *
   * @param o the object
   * @return the model
   */
  public static ExploreItem exploreItem(JsonFields o) {
    ExploreItem.Builder b = ExploreItem.builder();
    b.rank(o.integer("rank"));
    b.location(o.object("location", Decoders::locationRef));
    b.elevationName(o.optString("elevation_name"));
    b.elevationDisplayName(o.optString("elevation_display_name"));
    b.elevation(o.number("elevation"));
    b.value(o.number("value"));
    b.p10(o.optNumber("p10"));
    b.p90(o.optNumber("p90"));
    b.asOf(o.instant("as_of"));
    b.initializedAt(o.optInstant("initialized_at"));
    b.source(o.string("source"));
    b.distanceKm(o.optNumber("distance_km"));
    b.path(o.string("path"));
    return b.build();
  }

  /**
   * Decodes a {@code ExploreValue}.
   *
   * @param o the object
   * @return the model
   */
  public static ExploreValue exploreValue(JsonFields o) {
    ExploreValue.Builder b = ExploreValue.builder();
    b.metricAsString(o.string("metric"));
    b.value(o.number("value"));
    b.unit(o.string("unit"));
    b.p10(o.optNumber("p10"));
    b.p90(o.optNumber("p90"));
    b.asOf(o.instant("as_of"));
    b.initializedAt(o.optInstant("initialized_at"));
    b.source(o.string("source"));
    return b.build();
  }

  /**
   * Decodes a {@code CompositionResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static CompositionResponse compositionResponse(JsonFields o) {
    CompositionResponse.Builder b = CompositionResponse.builder();
    b.initializedAt(o.instant("initialized_at"));
    b.models(o.list("models", Decoders::modelEntry));
    b.modelCount(o.integer("model_count"));
    b.totalMembers(o.integer("total_members"));
    return b.build();
  }

  /**
   * Decodes a {@code SeasonResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static SeasonResponse seasonResponse(JsonFields o) {
    SeasonResponse.Builder b = SeasonResponse.builder();
    b.location(o.object("location", Decoders::locationRef));
    b.waterYear(o.integer("water_year"));
    b.starts(o.date("starts"));
    b.through(o.date("through"));
    b.asOf(o.optInstant("as_of"));
    b.unitsAsString(o.string("units"));
    b.precipitation(o.optObject("precipitation", Decoders::seasonTotal));
    b.snowfall(o.optObject("snowfall", Decoders::seasonTotal));
    b.station(o.optObject("station", Decoders::seasonStation));
    b.daily(o.list("daily", Decoders::seasonDay));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
    return b.build();
  }

  /**
   * Decodes a {@code SeasonTotal}.
   *
   * @param o the object
   * @return the model
   */
  public static SeasonTotal seasonTotal(JsonFields o) {
    SeasonTotal.Builder b = SeasonTotal.builder();
    b.amount(o.number("total"));
    b.unit(o.string("unit"));
    b.source(o.string("source"));
    b.daysCovered(o.integer("days_covered"));
    b.daysMissing(o.integer("days_missing"));
    b.provisionalDays(o.integer("provisional_days"));
    b.normalToDate(o.optNumber("normal_to_date"));
    b.percentOfNormal(o.optInteger("percent_of_normal"));
    b.normalSource(o.optString("normal_source"));
    return b.build();
  }

  /**
   * Decodes a {@code SeasonStation}.
   *
   * @param o the object
   * @return the model
   */
  public static SeasonStation seasonStation(JsonFields o) {
    SeasonStation.Builder b = SeasonStation.builder();
    b.id(o.string("id"));
    b.network(o.string("network"));
    b.name(o.string("name"));
    b.distanceKm(o.number("distance_km"));
    b.elevationM(o.number("elevation_m"));
    b.precipitationTotal(o.optNumber("precipitation_total"));
    b.precipitationPercentOfMedian(o.optInteger("precipitation_percent_of_median"));
    b.swe(o.optNumber("swe"));
    b.swePercentOfMedian(o.optInteger("swe_percent_of_median"));
    b.snowDepth(o.optNumber("snow_depth"));
    b.through(o.date("through"));
    return b.build();
  }

  /**
   * Decodes a {@code SeasonDay}.
   *
   * @param o the object
   * @return the model
   */
  public static SeasonDay seasonDay(JsonFields o) {
    SeasonDay.Builder b = SeasonDay.builder();
    b.date(o.date("date"));
    b.precipitation(o.optNumber("precipitation"));
    b.snowfall(o.optNumber("snowfall"));
    b.provisional(o.bool("provisional"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowTrendResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowTrendResponse snowTrendResponse(JsonFields o) {
    SnowTrendResponse.Builder b = SnowTrendResponse.builder();
    b.location(o.object("location", Decoders::locationRef));
    b.unitsAsString(o.string("units"));
    b.elevation(o.object("elevation", Decoders::snowTrendResponseElevation));
    b.retentionDays(o.integer("retention_days"));
    b.dates(o.dateList("dates"));
    b.grids(o.list("grids", Decoders::snowTrendGrid));
    return b.build();
  }

  /**
   * Decodes a {@code SnowTrendResponseElevation}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowTrendResponseElevation snowTrendResponseElevation(JsonFields o) {
    SnowTrendResponseElevation.Builder b = SnowTrendResponseElevation.builder();
    b.name(o.optString("name"));
    b.displayName(o.optString("display_name"));
    b.height(o.number("elevation"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowTrendGrid}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowTrendGrid snowTrendGrid(JsonFields o) {
    SnowTrendGrid.Builder b = SnowTrendGrid.builder();
    b.source(o.string("source"));
    b.runs(o.list("runs", Decoders::snowTrendRun));
    return b.build();
  }

  /**
   * Decodes a {@code SnowTrendRun}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowTrendRun snowTrendRun(JsonFields o) {
    SnowTrendRun.Builder b = SnowTrendRun.builder();
    b.backfilled(o.bool("backfilled"));
    b.initializedAt(o.instant("initialized_at"));
    b.days(o.nullableList("days", Decoders::snowTrendDay));
    return b.build();
  }

  /**
   * Decodes a {@code SnowTrendDay}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowTrendDay snowTrendDay(JsonFields o) {
    SnowTrendDay.Builder b = SnowTrendDay.builder();
    b.date(o.date("date"));
    b.amount(o.optNumber("snow"));
    b.partial(o.bool("partial"));
    b.p10(o.optNumber("p10"));
    b.p90(o.optNumber("p90"));
    b.kind(o.optString("kind"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackResponse snowpackResponse(JsonFields o) {
    SnowpackResponse.Builder b = SnowpackResponse.builder();
    b.beta(o.bool("beta"));
    b.location(o.object("location", Decoders::locationRef));
    b.unitsAsString(o.string("units"));
    b.modelRun(o.optObject("model_run", Decoders::snowpackRun));
    b.stale(o.bool("stale"));
    b.spots(o.list("spots", Decoders::snowpackSpot));
    b.definitions(o.object("definitions", Decoders::snowpackResponseDefinitions));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackResponseDefinitions}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackResponseDefinitions snowpackResponseDefinitions(JsonFields o) {
    SnowpackResponseDefinitions.Builder b = SnowpackResponseDefinitions.builder();
    b.surface(o.stringMap("surface"));
    b.layerForm(o.stringMap("layer_form"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackRun}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackRun snowpackRun(JsonFields o) {
    SnowpackRun.Builder b = SnowpackRun.builder();
    b.initializedAt(o.instant("initialized_at"));
    b.generatedAt(o.instant("generated_at"));
    b.model(o.object("model", Decoders::snowpackModel));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackModel}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackModel snowpackModel(JsonFields o) {
    SnowpackModel.Builder b = SnowpackModel.builder();
    b.name(o.string("name"));
    b.by(o.string("by"));
    b.license(o.string("license"));
    b.version(o.optString("version"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackSpot}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackSpot snowpackSpot(JsonFields o) {
    SnowpackSpot.Builder b = SnowpackSpot.builder();
    b.name(o.optString("name"));
    b.displayName(o.optString("display_name"));
    b.elevation(o.number("elevation"));
    b.elevationM(o.number("elevation_m"));
    b.initializedAt(o.instant("initialized_at"));
    b.anchor(o.optObject("anchor", Decoders::snowpackSpotAnchor));
    b.depth(o.object("depth", Decoders::snowpackDepth));
    b.depthChange24h(o.optNumber("depth_change_24h"));
    b.swe(o.numberList("swe"));
    b.steps(o.list("steps", Decoders::snowpackStep));
    b.layers(o.list("layers", Decoders::snowpackLayer));
    b.aspects(o.list("aspects", Decoders::snowpackAspect));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackSpotAnchor}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackSpotAnchor snowpackSpotAnchor(JsonFields o) {
    SnowpackSpotAnchor.Builder b = SnowpackSpotAnchor.builder();
    b.station(o.string("station"));
    b.name(o.string("name"));
    b.network(o.string("network"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackStep}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackStep snowpackStep(JsonFields o) {
    SnowpackStep.Builder b = SnowpackStep.builder();
    b.validTime(o.instant("valid_time"));
    b.surface(o.string("surface"));
    b.newSnow24h(o.number("new_snow_24h"));
    b.newSnowDensityKgM3(o.optNumber("new_snow_density_kg_m3"));
    b.settlement24h(o.optNumber("settlement_24h"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackLayer}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackLayer snowpackLayer(JsonFields o) {
    SnowpackLayer.Builder b = SnowpackLayer.builder();
    b.form(o.string("form"));
    b.thickness(o.number("thickness"));
    b.depth(o.number("depth"));
    b.formed(o.date("formed"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackAspect}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackAspect snowpackAspect(JsonFields o) {
    SnowpackAspect.Builder b = SnowpackAspect.builder();
    b.aspect(o.string("aspect"));
    b.slopeAngleDeg(o.number("slope_angle_deg"));
    b.depth(o.object("depth", Decoders::snowpackDepth));
    b.depthChange24h(o.optNumber("depth_change_24h"));
    b.steps(o.list("steps", Decoders::snowpackStep));
    b.layers(o.list("layers", Decoders::snowpackLayer));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackDepth}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackDepth snowpackDepth(JsonFields o) {
    SnowpackDepth.Builder b = SnowpackDepth.builder();
    b.startTime(o.instant("start_time"));
    b.intervalHours(o.integer("interval_hours"));
    b.basis(o.string("basis"));
    b.p10(o.numberList("p10"));
    b.p50(o.numberList("p50"));
    b.p90(o.numberList("p90"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackPlumeResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackPlumeResponse snowpackPlumeResponse(JsonFields o) {
    SnowpackPlumeResponse.Builder b = SnowpackPlumeResponse.builder();
    b.beta(o.bool("beta"));
    b.location(o.object("location", Decoders::locationRef));
    b.unitsAsString(o.string("units"));
    b.modelRun(o.optObject("model_run", Decoders::snowpackRun));
    b.stale(o.bool("stale"));
    b.depth(o.optObject("depth", Decoders::snowpackPlumeDepth));
    b.models(o.list("models", Decoders::snowpackPlumeModel));
    b.memberDepths(o.numberLists("member_depths"));
    b.spread(o.string("spread"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackPlumeDepth}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackPlumeDepth snowpackPlumeDepth(JsonFields o) {
    SnowpackPlumeDepth.Builder b = SnowpackPlumeDepth.builder();
    b.startTime(o.instant("start_time"));
    b.intervalHours(o.integer("interval_hours"));
    b.members(o.integerList("members"));
    b.p50(o.numberList("p50"));
    b.mean(o.numberList("mean"));
    b.p10(o.numberList("p10"));
    b.p25(o.numberList("p25"));
    b.p75(o.numberList("p75"));
    b.p90(o.numberList("p90"));
    return b.build();
  }

  /**
   * Decodes a {@code SnowpackPlumeModel}.
   *
   * @param o the object
   * @return the model
   */
  public static SnowpackPlumeModel snowpackPlumeModel(JsonFields o) {
    SnowpackPlumeModel.Builder b = SnowpackPlumeModel.builder();
    b.id(o.string("id"));
    b.memberIds(o.integerList("member_ids"));
    b.depth(o.numberLists("depth"));
    b.depthMean(o.numberList("depth_mean"));
    b.membersSkipped(o.integer("members_skipped"));
    return b.build();
  }

  /**
   * Decodes a {@code AlertOutlineCollection}.
   *
   * @param o the object
   * @return the model
   */
  public static AlertOutlineCollection alertOutlineCollection(JsonFields o) {
    AlertOutlineCollection.Builder b = AlertOutlineCollection.builder();
    b.type(o.string("type"));
    b.features(o.list("features", Decoders::alertOutlineFeature));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
    return b.build();
  }

  /**
   * Decodes a {@code AlertOutlineFeature}.
   *
   * @param o the object
   * @return the model
   */
  public static AlertOutlineFeature alertOutlineFeature(JsonFields o) {
    AlertOutlineFeature.Builder b = AlertOutlineFeature.builder();
    b.type(o.string("type"));
    b.geometry(o.object("geometry", Decoders::alertOutlineGeometry));
    b.properties(o.json("properties"));
    return b.build();
  }

  /**
   * Decodes a {@code AlertOutlineGeometry}.
   *
   * @param o the object
   * @return the model
   */
  public static AlertOutlineGeometry alertOutlineGeometry(JsonFields o) {
    AlertOutlineGeometry.Builder b = AlertOutlineGeometry.builder();
    b.type(o.string("type"));
    b.coordinates(o.json("coordinates"));
    return b.build();
  }

  /**
   * Decodes a {@code AvalancheZoneCollection}.
   *
   * @param o the object
   * @return the model
   */
  public static AvalancheZoneCollection avalancheZoneCollection(JsonFields o) {
    AvalancheZoneCollection.Builder b = AvalancheZoneCollection.builder();
    b.type(o.string("type"));
    b.features(o.list("features", Decoders::avalancheZoneFeature));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
    return b.build();
  }

  /**
   * Decodes a {@code AvalancheZoneFeature}.
   *
   * @param o the object
   * @return the model
   */
  public static AvalancheZoneFeature avalancheZoneFeature(JsonFields o) {
    AvalancheZoneFeature.Builder b = AvalancheZoneFeature.builder();
    b.type(o.string("type"));
    b.id(o.longInteger("id"));
    b.geometry(o.object("geometry", Decoders::avalancheZoneGeometry));
    b.properties(o.json("properties"));
    return b.build();
  }

  /**
   * Decodes a {@code AvalancheZoneGeometry}.
   *
   * @param o the object
   * @return the model
   */
  public static AvalancheZoneGeometry avalancheZoneGeometry(JsonFields o) {
    AvalancheZoneGeometry.Builder b = AvalancheZoneGeometry.builder();
    b.type(o.string("type"));
    b.coordinates(o.json("coordinates"));
    return b.build();
  }

  /**
   * Decodes a {@code MeasuredPrecipitation}.
   *
   * @param o the object
   * @return the model
   */
  public static MeasuredPrecipitation measuredPrecipitation(JsonFields o) {
    MeasuredPrecipitation.Builder b = MeasuredPrecipitation.builder();
    b.amount(o.number("amount"));
    b.through(o.instant("through"));
    return b.build();
  }
}
