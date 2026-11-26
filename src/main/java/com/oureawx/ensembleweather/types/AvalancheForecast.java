// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * The avalanche center's danger rating for the forecast zone the location's point is in, from
 * avalanche.org's public map layer. Link-first: the danger level, the zone and center names, and a
 * link to the center's full forecast, which is the thing to read -- Ourea never serves the
 * forecast's text and never rates danger itself. Absent when the feature is off on this server or
 * the point is in no avalanche center's zone. Fail closed: when the zone has not been seen in a
 * successful poll recently, or its product has expired ({@code valid_until} passed), {@code status}
 * is {@code unavailable} and {@code danger_level} is {@code null} -- an old rating is never served.
 * Show {@code attribution} with the block. Colorado (additive): where this server reads CAIC's own
 * feed, a Colorado point gets its CAIC forecast area's rating instead of avalanche.org's single
 * statewide CAIC zone. The level is the HIGHEST of CAIC's three elevation bands (above, near and
 * below treeline) for the current day, the convention of the avalanche.org map. CAIC regroups its
 * areas as conditions change, so the area's {@code zone_id} can change from day to day, and {@code
 * forecast_url} opens CAIC's map at the point. If CAIC's feed is stale or down, a Colorado point
 * gets {@code unavailable}, never the statewide rating. A point in an area where CAIC issues no
 * forecast gets {@code no_rating}. CAIC's areas have no names, so {@code zone_name} is {@code CAIC
 * forecast area}. The Cap'n Proto body carries the same as {@code ForecastResponse.avalanche} (set
 * when {@code hasAvalanche}).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AvalancheForecast}.
 */
public final class AvalancheForecast extends Model {
  private final String centerId;
  private final String centerName;
  private final long zoneId;
  private final String zoneName;
  private final @Nullable Integer dangerLevel;
  private final @Nullable String dangerLabel;
  private final String status;
  private final @Nullable Instant validFrom;
  private final @Nullable Instant validUntil;
  private final String url;
  private final String centerUrl;
  private final String attribution;

  private AvalancheForecast(Builder b) {
    this.centerId = require(b.centerId, "center_id");
    this.centerName = require(b.centerName, "center_name");
    this.zoneId = require(b.zoneId, "zone_id");
    this.zoneName = require(b.zoneName, "zone_name");
    this.dangerLevel = b.dangerLevel;
    this.dangerLabel = b.dangerLabel;
    this.status = require(b.status, "status");
    this.validFrom = b.validFrom;
    this.validUntil = b.validUntil;
    this.url = require(b.url, "forecast_url");
    this.centerUrl = require(b.centerUrl, "center_url");
    this.attribution = require(b.attribution, "attribution");
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
    b.centerId = this.centerId;
    b.centerName = this.centerName;
    b.zoneId = this.zoneId;
    b.zoneName = this.zoneName;
    b.dangerLevel = this.dangerLevel;
    b.dangerLabel = this.dangerLabel;
    b.status = this.status;
    b.validFrom = this.validFrom;
    b.validUntil = this.validUntil;
    b.url = this.url;
    b.centerUrl = this.centerUrl;
    b.attribution = this.attribution;
    return b;
  }

  /**
   * The avalanche center's short id.
   *
   * @return the {@code center_id} value
   */
  public String centerId() {
    return centerId;
  }

  /**
   * The {@code center_name} value.
   *
   * @return the {@code center_name} value
   */
  public String centerName() {
    return centerName;
  }

  /**
   * Avalanche.org's id for the forecast zone; for a CAIC forecast area, an id at or above 2^52
   * derived from CAIC's area id (stable for that grouping of CAIC's polygons).
   *
   * @return the {@code zone_id} value
   */
  public long zoneId() {
    return zoneId;
  }

  /**
   * The {@code zone_name} value.
   *
   * @return the {@code zone_name} value
   */
  public String zoneName() {
    return zoneName;
  }

  /**
   * The North American Public Avalanche Danger Scale level, 1-5; present only when {@code status}
   * is {@code rated}.
   *
   * @return the {@code danger_level} value, or empty when the response omits it
   */
  public OptionalInt dangerLevel() {
    return dangerLevel == null ? OptionalInt.empty() : OptionalInt.of(dangerLevel);
  }

  /**
   * Display text: the scale's English name for {@code danger_level} (Low, Moderate, Considerable,
   * High, Extreme), derived from the level, never the center's own wording; {@code null} exactly
   * when {@code danger_level} is. Switch on {@code danger_level} and {@code status}, show this --
   * like {@code condition_text}, it is not an enum, so it can be localised later without breaking a
   * client.
   *
   * @return the {@code danger_label} value, or empty when the response omits it
   */
  public Optional<String> dangerLabel() {
    return Optional.ofNullable(dangerLabel);
  }

  /**
   * {@code rated}: a current rating. {@code no_rating}: the center issued none for the zone. {@code
   * off_season}: the center is out of season. {@code unavailable}: no rating can be vouched for
   * (the data is stale, the product expired, or the level is not one the scale has). One of {@code
   * rated}, {@code no_rating}, {@code off_season}, {@code unavailable}; read it as a string, since
   * the API may add values.
   *
   * @return the {@code status} value
   */
  public String status() {
    return status;
  }

  /**
   * When the center's current product took effect (UTC), when stated.
   *
   * @return the {@code valid_from} value, or empty when the response omits it
   */
  public Optional<Instant> validFrom() {
    return Optional.ofNullable(validFrom);
  }

  /**
   * When the center's current product expires (UTC), when stated.
   *
   * @return the {@code valid_until} value, or empty when the response omits it
   */
  public Optional<Instant> validUntil() {
    return Optional.ofNullable(validUntil);
  }

  /**
   * (JSON {@code forecast_url}) The center's full forecast for the zone. Link it prominently. For a
   * CAIC area, CAIC's forecast map at the point ({@code
   * https://avalanche.state.co.us/?lat=..&lng=..}).
   *
   * @return the {@code forecast_url} value
   */
  public String url() {
    return url;
  }

  /**
   * The {@code center_url} value.
   *
   * @return the {@code center_url} value
   */
  public String centerUrl() {
    return centerUrl;
  }

  /**
   * The credit to show with the block: "Avalanche forecast by &lt;center&gt; via avalanche.org", or
   * "Avalanche forecast by Colorado Avalanche Information Center via avalanche.state.co.us" for a
   * CAIC area.
   *
   * @return the {@code attribution} value
   */
  public String attribution() {
    return attribution;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      centerId,
      centerName,
      zoneId,
      zoneName,
      dangerLevel,
      dangerLabel,
      status,
      validFrom,
      validUntil,
      url,
      centerUrl,
      attribution
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "centerId",
    "centerName",
    "zoneId",
    "zoneName",
    "dangerLevel",
    "dangerLabel",
    "status",
    "validFrom",
    "validUntil",
    "url",
    "centerUrl",
    "attribution"
  };

  /** Builds a {@link AvalancheForecast}. */
  public static final class Builder {
    private @Nullable String centerId;
    private @Nullable String centerName;
    private @Nullable Long zoneId;
    private @Nullable String zoneName;
    private @Nullable Integer dangerLevel;
    private @Nullable String dangerLabel;
    private @Nullable String status;
    private @Nullable Instant validFrom;
    private @Nullable Instant validUntil;
    private @Nullable String url;
    private @Nullable String centerUrl;
    private @Nullable String attribution;

    private Builder() {}

    /**
     * Sets {@link AvalancheForecast#centerId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder centerId(String value) {
      this.centerId = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#centerName()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder centerName(String value) {
      this.centerName = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#zoneId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder zoneId(long value) {
      this.zoneId = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#zoneName()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder zoneName(String value) {
      this.zoneName = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#dangerLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder dangerLevel(@Nullable Integer value) {
      this.dangerLevel = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#dangerLabel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder dangerLabel(@Nullable String value) {
      this.dangerLabel = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#status()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder status(String value) {
      this.status = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#validFrom()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder validFrom(@Nullable Instant value) {
      this.validFrom = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#validUntil()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder validUntil(@Nullable Instant value) {
      this.validUntil = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#url()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder url(String value) {
      this.url = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#centerUrl()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder centerUrl(String value) {
      this.centerUrl = value;
      return this;
    }

    /**
     * Sets {@link AvalancheForecast#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(String value) {
      this.attribution = value;
      return this;
    }

    /**
     * Builds the {@link AvalancheForecast}.
     *
     * @return the immutable AvalancheForecast
     * @throws IllegalStateException if a required value was not set
     */
    public AvalancheForecast build() {
      return new AvalancheForecast(this);
    }
  }
}
