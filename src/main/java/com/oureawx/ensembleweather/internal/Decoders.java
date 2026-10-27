// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import com.oureawx.ensembleweather.model.*;

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
    b.severity(o.string("severity"));
    b.urgency(o.string("urgency"));
    b.headline(o.string("headline"));
    b.onset(o.optInstant("onset"));
    b.expires(o.instant("expires"));
    b.ends(o.optInstant("ends"));
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
    b.alertCount(o.integer("alert_count"));
    b.asOf(o.instant("as_of"));
    b.alerts(o.list("alerts", Decoders::activeAlert));
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
    b.elevationM(o.number("elevation_m"));
    b.latOverride(o.optNumber("lat_override"));
    b.lonOverride(o.optNumber("lon_override"));
    b.isDefault(o.bool("is_default"));
    return b.build();
  }

  /**
   * Decodes a {@code CatalogHousePlacement}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogHousePlacement catalogHousePlacement(JsonFields o) {
    CatalogHousePlacement.Builder b = CatalogHousePlacement.builder();
    b.campaignId(o.string("campaign_id"));
    b.creativeId(o.string("creative_id"));
    b.content(o.object("content", Decoders::cmsAdCreativeContent));
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
    b.type(o.string("type"));
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
    b.ads(o.list("ads", Decoders::catalogResolvedAdSlot));
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
    b.network(o.string("network"));
    b.stationStatus(o.string("station_status"));
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
   * Decodes a {@code CatalogResolvedAdSlot}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogResolvedAdSlot catalogResolvedAdSlot(JsonFields o) {
    CatalogResolvedAdSlot.Builder b = CatalogResolvedAdSlot.builder();
    b.slotKey(o.string("slot_key"));
    b.kindAsString(o.string("kind"));
    b.house(o.optObject("house", Decoders::catalogHousePlacement));
    b.thirdParty(o.optObject("third_party", Decoders::catalogThirdPartyPlacement));
    b.sizes(o.intLists("sizes"));
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
    b.resolvedLocale(o.string("resolved_locale"));
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
    b.network(o.optString("network"));
    b.sourceStationId(o.optString("source_station_id"));
    b.elevationMslM(o.optNumber("elevation_msl_m"));
    b.sensorHeightAglM(o.optNumber("sensor_height_agl_m"));
    b.sensorCapabilities(o.stringList("sensor_capabilities"));
    b.stationStatus(o.optString("station_status"));
    b.lastReportAt(o.optInstant("last_report_at"));
    b.lastReportSummary(o.optJson("last_report_summary"));
    b.installDate(o.optDate("install_date"));
    b.decommissionDate(o.optDate("decommission_date"));
    b.updateFrequencyMinutes(o.optInteger("update_frequency_minutes"));
    b.stationUrl(o.optString("station_url"));
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
    b.type(o.string("type"));
    b.timezone(o.zone("timezone"));
    b.county(o.optString("county"));
    b.country(o.optString("country"));
    b.aliases(o.stringList("aliases"));
    b.network(o.optString("network"));
    b.sourceStationId(o.optString("source_station_id"));
    b.elevationMslM(o.optNumber("elevation_msl_m"));
    b.sensorHeightAglM(o.optNumber("sensor_height_agl_m"));
    b.sensorCapabilities(o.stringList("sensor_capabilities"));
    b.stationStatus(o.optString("station_status"));
    b.lastReportAt(o.optInstant("last_report_at"));
    b.lastReportSummary(o.optJson("last_report_summary"));
    b.installDate(o.optDate("install_date"));
    b.decommissionDate(o.optDate("decommission_date"));
    b.updateFrequencyMinutes(o.optInteger("update_frequency_minutes"));
    b.stationUrl(o.optString("station_url"));
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
    b.ads(o.list("ads", Decoders::catalogResolvedAdSlot));
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
    b.locationType(o.optString("location_type"));
    b.templateVariant(o.optString("template_variant"));
    b.spec(o.list("spec", Decoders::cmsTemplateSection));
    return b.build();
  }

  /**
   * Decodes a {@code CatalogThirdPartyPlacement}.
   *
   * @param o the object
   * @return the model
   */
  public static CatalogThirdPartyPlacement catalogThirdPartyPlacement(JsonFields o) {
    CatalogThirdPartyPlacement.Builder b = CatalogThirdPartyPlacement.builder();
    b.config(o.json("config"));
    b.unitId(o.string("unit_id"));
    b.providerAsString(o.string("provider"));
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
   * Decodes a {@code CmsAdCreativeContent}.
   *
   * @param o the object
   * @return the model
   */
  public static CmsAdCreativeContent cmsAdCreativeContent(JsonFields o) {
    CmsAdCreativeContent.Builder b = CmsAdCreativeContent.builder();
    b.headline(o.optString("headline"));
    b.body(o.optString("body"));
    b.cta(o.optString("cta"));
    b.clickUrl(o.optString("click_url"));
    b.photoId(o.optString("photo_id"));
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
    b.conditionAsString(o.string("condition"));
    b.conditionText(o.string("condition_text"));
    b.conditionIconAsString(o.string("condition_icon"));
    b.windSpeed(o.number("wind_speed"));
    b.windGust(o.optNumber("wind_gust"));
    b.windDirection(o.number("wind_direction"));
    b.relativeHumidity(o.optNumber("relative_humidity"));
    b.dewpoint(o.optNumber("dewpoint"));
    b.precipProbability(o.optNumber("precip_probability"));
    b.sourceAsString(o.string("source"));
    b.station(o.optObject("station", Decoders::currentStation));
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
    b.current(o.optObject("current", Decoders::currentConditions));
    b.asOf(o.instant("as_of"));
    b.modelRunInitializedAt(o.optInstant("model_run_initialized_at"));
    b.nowcastHours(o.list("nowcast_hours", Decoders::timeSeriesPoint));
    b.unitsAsString(o.string("units"));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
    b.nowcastUnavailableAsStrings(o.stringList("nowcast_unavailable"));
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
    b.name(o.string("name"));
    b.observationTime(o.instant("observation_time"));
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
    b.precipTypeAsString(o.optString("precip_type"));
    b.high(o.object("high", Decoders::quantiles));
    b.low(o.object("low", Decoders::quantiles));
    b.feelsLikeHigh(o.optObject("feels_like_high", Decoders::quantiles));
    b.feelsLikeLow(o.optObject("feels_like_low", Decoders::quantiles));
    b.feelsLikeUtciHigh(o.optObject("feels_like_utci_high", Decoders::quantiles));
    b.feelsLikeUtciLow(o.optObject("feels_like_utci_low", Decoders::quantiles));
    b.totalPrecip(o.object("total_precip", Decoders::quantiles));
    b.totalSnowfall(o.optObject("total_snowfall", Decoders::quantiles));
    b.precipProbability(o.number("precip_probability"));
    b.precipOccurrenceProbability(o.number("precip_occurrence_probability"));
    b.snowProbability(o.number("snow_probability"));
    b.snowLevelMean(o.optNumber("snow_level_mean"));
    b.snowLevel(o.optObject("snow_level", Decoders::quantiles));
    b.totalPrecipIfWet(o.optObject("total_precip_if_wet", Decoders::quantiles));
    b.totalSnowfallIfWet(o.optObject("total_snowfall_if_wet", Decoders::quantiles));
    b.precipDisplay(o.object("precip_display", Decoders::precipDisplay));
    b.cloudCoverMean(o.optNumber("cloud_cover_mean"));
    b.wind(o.optObject("wind", Decoders::dailyWind));
    b.stormOrientationAsString(o.optString("storm_orientation"));
    b.stormOrientationDescription(o.optString("storm_orientation_description"));
    b.astro(o.object("astro", Decoders::dailyAstro));
    b.temperatureClimatology(o.optObject("temperature_climatology", Decoders::dayClimatology));
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
    b.statusAsString(o.optString("status"));
    b.peakSpeed(o.optNumber("peak_speed"));
    b.peakSpeedP90(o.optNumber("peak_speed_p90"));
    b.isGust(o.optBool("is_gust"));
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
    b.elevation(o.number("elevation"));
    b.isDefault(o.optBool("default"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.timeseries(o.list("timeseries", Decoders::elevationTimeSeriesPoint));
    b.hourly(o.list("hourly", Decoders::timeSeriesPoint));
    b.daily(o.list("daily", Decoders::dailyForecast));
    b.snowOutlook(o.optObject("snow_outlook", Decoders::snowOutlook));
    return b.build();
  }

  /**
   * Decodes a {@code ElevationTimeSeriesPoint}.
   *
   * @param o the object
   * @return the model
   */
  public static ElevationTimeSeriesPoint elevationTimeSeriesPoint(JsonFields o) {
    ElevationTimeSeriesPoint.Builder b = ElevationTimeSeriesPoint.builder();
    b.validTime(o.instant("valid_time"));
    b.leadTimeHours(o.integer("lead_time_hours"));
    b.temperature(o.object("temperature", Decoders::quantiles));
    b.snowLevelDelta(o.optObject("snow_level_delta", Decoders::quantiles));
    b.windSpeed(o.optObject("wind_speed", Decoders::quantiles));
    b.windGust(o.optObject("wind_gust", Decoders::quantiles));
    b.precipitation(o.object("precipitation", Decoders::quantiles));
    b.conditionAsString(o.string("condition"));
    b.conditionText(o.string("condition_text"));
    b.conditionIconAsString(o.string("condition_icon"));
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
    b.feelsLikeUtci(o.optObject("feels_like_utci", Decoders::quantiles));
    b.dewpoint(o.optObject("dewpoint", Decoders::quantiles));
    b.precipitation(o.object("precipitation", Decoders::quantiles));
    b.windSpeed(o.object("wind_speed", Decoders::quantiles));
    b.windGust(o.optObject("wind_gust", Decoders::quantiles));
    b.windDirection(o.object("wind_direction", Decoders::quantiles));
    b.windDirectionCircular(o.optObject("wind_direction_circular", Decoders::circularStats));
    b.snowLevel(o.optObject("snow_level", Decoders::quantiles));
    b.slr(o.optObject("slr", Decoders::quantiles));
    b.snowfall(o.optObject("snowfall", Decoders::quantiles));
    b.cloudCover(o.optObject("cloud_cover", Decoders::quantiles));
    b.skinTemperature(o.optObject("skin_temperature", Decoders::quantiles));
    b.ptype(o.optObject("ptype", Decoders::quantiles));
    b.mslp(o.optObject("mslp", Decoders::quantiles));
    b.cape(o.optObject("cape", Decoders::quantiles));
    b.relativeHumidity(o.optObject("relative_humidity", Decoders::quantiles));
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
    b.location(o.object("location", Decoders::locationRef));
    b.modelRun(o.object("model_run", Decoders::modelRunInfo));
    b.unitsAsString(o.string("units"));
    b.forecast(o.object("forecast", Decoders::forecastData));
    b.elevationForecasts(o.list("elevation_forecasts", Decoders::elevationForecast));
    b.nowcastApplied(o.bool("nowcast_applied"));
    b.nowcastUnavailableAsStrings(o.stringList("nowcast_unavailable"));
    b.membersViewAsString(o.optString("members_view"));
    b.gapFlow(o.optObject("gap_flow", Decoders::gapFlow));
    b.nearestSensors(o.list("nearest_sensors", Decoders::nearestSensor));
    b.point(o.optObject("point", Decoders::pointInfo));
    b.current(o.optObject("current", Decoders::currentConditions));
    b.attribution(o.object("attribution", Decoders::responseAttribution));
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
    b.statusAsString(o.optString("status"));
    b.pressureDiffHpa(o.optNumber("pressure_diff_hpa"));
    b.windMultiplier(o.optNumber("wind_multiplier"));
    b.direction(o.optString("direction"));
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
   * Decodes a {@code LocationElevation}.
   *
   * @param o the object
   * @return the model
   */
  public static LocationElevation locationElevation(JsonFields o) {
    LocationElevation.Builder b = LocationElevation.builder();
    b.name(o.string("name"));
    b.elevation(o.number("elevation"));
    b.isDefault(o.optBool("default"));
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
    b.slug(o.string("slug"));
    b.state(o.string("state"));
    b.name(o.string("name"));
    b.lat(o.number("lat"));
    b.lon(o.number("lon"));
    b.elevationM(o.number("elevation_m"));
    b.typeAsString(o.string("type"));
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
    b.elevations(o.list("elevations", Decoders::locationElevation));
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
    b.locations(o.list("locations", Decoders::locationSummary));
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
    b.memberId(o.integer("member_id"));
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
    b.precipitation(o.optObject("precipitation", Decoders::quantiles));
    b.snowfall(o.optObject("snowfall", Decoders::quantiles));
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
    b.members(o.list("members", Decoders::memberForecast));
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
    b.windDirection(o.optObject("wind_direction", Decoders::quantiles));
    b.snowLevel(o.optObject("snow_level", Decoders::quantiles));
    b.slr(o.optObject("slr", Decoders::quantiles));
    b.snowfall(o.optObject("snowfall", Decoders::quantiles));
    b.interpolated(o.optBool("interpolated"));
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
    b.elevations(o.list("elevations", Decoders::locationElevation));
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
    b.location(o.object("location", Decoders::locationSummary));
    b.radiusKm(o.number("radius_km"));
    b.count(o.integer("count"));
    b.locations(o.list("locations", Decoders::nearbyLocation));
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
    b.locations(o.list("locations", Decoders::nearbyLocation));
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
    b.variableAsString(o.string("variable"));
    b.opAsString(o.string("op"));
    b.threshold(o.number("threshold"));
    b.windowStartHours(o.integer("window_start_hours"));
    b.windowEndHours(o.integer("window_end_hours"));
    b.aggregationAsString(o.string("aggregation"));
    b.from(o.optInstant("from"));
    b.to(o.optInstant("to"));
    b.probability(o.optNumber("probability"));
    b.members(o.integer("members"));
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
    b.dailyPrecipProbability(o.number("daily_precip_probability"));
    b.dailyPrecipOccurrenceProbability(o.number("daily_precip_occurrence_probability"));
    b.dailySnowProbability(o.number("daily_snow_probability"));
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
    b.runs(o.list("runs", Decoders::runInfo));
    b.count(o.integer("count"));
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
    b.observationTime(o.instant("observation_time"));
    b.temperature(o.optNumber("temperature"));
    b.dewpoint(o.optNumber("dewpoint"));
    b.relativeHumidity(o.optNumber("relative_humidity"));
    b.windSpeed(o.optNumber("wind_speed"));
    b.windGust(o.optNumber("wind_gust"));
    b.windDirection(o.optNumber("wind_direction"));
    b.pressure(o.optNumber("pressure"));
    b.precipitation(o.optNumber("precipitation"));
    b.precipRate(o.optNumber("precip_rate"));
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
    return b.build();
  }

  /**
   * Decodes a {@code StormReportCard}.
   *
   * @param o the object
   * @return the model
   */
  public static StormReportCard stormReportCard(JsonFields o) {
    StormReportCard.Builder b = StormReportCard.builder();
    b.id(o.longInteger("id"));
    b.locationName(o.string("location_name"));
    b.eventType(o.string("event_type"));
    b.onsetTime(o.instant("onset_time"));
    b.endTime(o.instant("end_time"));
    b.forecastTotalMm(o.number("forecast_total_mm"));
    b.observedTotalMm(o.number("observed_total_mm"));
    b.forecastSnowfallMm(o.optNumber("forecast_snowfall_mm"));
    b.observedSnowfallMm(o.optNumber("observed_snowfall_mm"));
    b.bestModel(o.optString("best_model"));
    b.bestLeadTimeHours(o.optInteger("best_lead_time_hours"));
    b.compositeScore(o.optNumber("composite_score"));
    b.headline(o.optString("headline"));
    return b.build();
  }

  /**
   * Decodes a {@code StormReportsResponse}.
   *
   * @param o the object
   * @return the model
   */
  public static StormReportsResponse stormReportsResponse(JsonFields o) {
    StormReportsResponse.Builder b = StormReportsResponse.builder();
    b.reports(o.list("reports", Decoders::stormReportCard));
    b.count(o.integer("count"));
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
    b.precipType(o.optObject("precip_type", Decoders::precipType));
    b.inversion(o.optObject("inversion", Decoders::inversionInfo));
    b.freezingLevel(o.optObject("freezing_level", Decoders::freezingLevel));
    b.ensemble(o.object("ensemble", Decoders::ensembleStats));
    b.provenance(o.optObject("provenance", Decoders::hourProvenance));
    b.modelMeans(o.list("model_means", Decoders::modelStats));
    b.members(o.list("members", Decoders::modelMembers));
    b.membersCumulative(o.optObject("members_cumulative", Decoders::membersCumulative));
    b.cumulativePrecipitation(o.optNumber("cumulative_precipitation"));
    b.cumulativeSnowfall(o.optNumber("cumulative_snowfall"));
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
    b.variableAsString(o.string("variable"));
    b.windowStartHours(o.integer("window_start_hours"));
    b.windowEndHours(o.integer("window_end_hours"));
    b.from(o.optInstant("from"));
    b.to(o.optInstant("to"));
    b.quantiles(o.optObject("quantiles", Decoders::quantiles));
    b.members(o.integer("members"));
    return b.build();
  }
}
