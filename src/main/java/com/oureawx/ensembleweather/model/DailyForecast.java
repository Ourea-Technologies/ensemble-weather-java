// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * Daily summary keyed by one local calendar date (the location's own US timezone, not always
 * Pacific).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code DailyForecast}.
 */
public final class DailyForecast extends Model {
  private final LocalDate date;
  private final String condition;
  private final String conditionText;
  private final String conditionIcon;
  private final @Nullable String precipType;
  private final Quantiles high;
  private final Quantiles low;
  private final @Nullable Quantiles feelsLikeHigh;
  private final @Nullable Quantiles feelsLikeLow;
  private final @Nullable Quantiles feelsLikeUtciHigh;
  private final @Nullable Quantiles feelsLikeUtciLow;
  private final Quantiles totalPrecip;
  private final @Nullable Quantiles totalSnowfall;
  private final double precipProbability;
  private final double precipOccurrenceProbability;
  private final double snowProbability;
  private final @Nullable Double snowLevelMean;
  private final @Nullable Quantiles snowLevel;
  private final @Nullable Quantiles totalPrecipIfWet;
  private final @Nullable Quantiles totalSnowfallIfWet;
  private final PrecipDisplay precipDisplay;
  private final @Nullable Double cloudCoverMean;
  private final @Nullable DailyWind wind;
  private final @Nullable String stormOrientation;
  private final @Nullable String stormOrientationDescription;
  private final DailyAstro astro;
  private final @Nullable DayClimatology temperatureClimatology;

  private DailyForecast(Builder b) {
    this.date = require(b.date, "date");
    this.condition = require(b.condition, "condition");
    this.conditionText = require(b.conditionText, "condition_text");
    this.conditionIcon = require(b.conditionIcon, "condition_icon");
    this.precipType = b.precipType;
    this.high = require(b.high, "high");
    this.low = require(b.low, "low");
    this.feelsLikeHigh = b.feelsLikeHigh;
    this.feelsLikeLow = b.feelsLikeLow;
    this.feelsLikeUtciHigh = b.feelsLikeUtciHigh;
    this.feelsLikeUtciLow = b.feelsLikeUtciLow;
    this.totalPrecip = require(b.totalPrecip, "total_precip");
    this.totalSnowfall = b.totalSnowfall;
    this.precipProbability = require(b.precipProbability, "precip_probability");
    this.precipOccurrenceProbability =
        require(b.precipOccurrenceProbability, "precip_occurrence_probability");
    this.snowProbability = require(b.snowProbability, "snow_probability");
    this.snowLevelMean = b.snowLevelMean;
    this.snowLevel = b.snowLevel;
    this.totalPrecipIfWet = b.totalPrecipIfWet;
    this.totalSnowfallIfWet = b.totalSnowfallIfWet;
    this.precipDisplay = require(b.precipDisplay, "precip_display");
    this.cloudCoverMean = b.cloudCoverMean;
    this.wind = b.wind;
    this.stormOrientation = b.stormOrientation;
    this.stormOrientationDescription = b.stormOrientationDescription;
    this.astro = require(b.astro, "astro");
    this.temperatureClimatology = b.temperatureClimatology;
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
    b.date = this.date;
    b.condition = this.condition;
    b.conditionText = this.conditionText;
    b.conditionIcon = this.conditionIcon;
    b.precipType = this.precipType;
    b.high = this.high;
    b.low = this.low;
    b.feelsLikeHigh = this.feelsLikeHigh;
    b.feelsLikeLow = this.feelsLikeLow;
    b.feelsLikeUtciHigh = this.feelsLikeUtciHigh;
    b.feelsLikeUtciLow = this.feelsLikeUtciLow;
    b.totalPrecip = this.totalPrecip;
    b.totalSnowfall = this.totalSnowfall;
    b.precipProbability = this.precipProbability;
    b.precipOccurrenceProbability = this.precipOccurrenceProbability;
    b.snowProbability = this.snowProbability;
    b.snowLevelMean = this.snowLevelMean;
    b.snowLevel = this.snowLevel;
    b.totalPrecipIfWet = this.totalPrecipIfWet;
    b.totalSnowfallIfWet = this.totalSnowfallIfWet;
    b.precipDisplay = this.precipDisplay;
    b.cloudCoverMean = this.cloudCoverMean;
    b.wind = this.wind;
    b.stormOrientation = this.stormOrientation;
    b.stormOrientationDescription = this.stormOrientationDescription;
    b.astro = this.astro;
    b.temperatureClimatology = this.temperatureClimatology;
    return b;
  }

  /**
   * The {@code date} value.
   *
   * @return the {@code date} value
   */
  public LocalDate date() {
    return date;
  }

  /**
   * A stable machine code to switch on -- the display text ({@code condition_text}) lowercased,
   * each run of other characters one {@code _} ({@code partly_cloudy}, {@code
   * rain_snow_mix_likely}). Treat unknown codes as additive.
   *
   * @return the {@code condition} value ({@link DailyCondition#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public DailyCondition condition() {
    return DailyCondition.fromWireValue(condition);
  }

  /**
   * The {@code condition} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code condition} string
   */
  public String conditionAsString() {
    return condition;
  }

  /**
   * Human-readable weather condition for the day — a daytime label, on the same rule as the hourly
   * {@code condition}.
   *
   * @return the {@code condition_text} value
   */
  public String conditionText() {
    return conditionText;
  }

  /**
   * Machine-readable icon hint for frontends. Sky-only hints name the five sky classes.
   * Precipitating hints carry the SIDE of the sky: {@code sun_*} when the daytime sky leans clear
   * ({@code P(< 40%) > P(>= 80%)}) — a sun beside the rain cloud — and plain otherwise. Additive:
   * {@code mostly_sunny}, {@code mostly_cloudy}, {@code sun_chance_showers}, {@code
   * sun_showers_likely}, {@code sun_showers}, {@code chance_rain}, {@code showers_likely}, {@code
   * showers}. Retired: {@code sun_rain}, {@code scattered_showers}, {@code
   * night_scattered_showers}.
   *
   * @return the {@code condition_icon} value ({@link DailyIcon#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public DailyIcon conditionIcon() {
    return DailyIcon.fromWireValue(conditionIcon);
  }

  /**
   * The {@code condition_icon} value exactly as the API sent it, even when this SDK version does
   * not know it.
   *
   * @return the raw {@code condition_icon} string
   */
  public String conditionIconAsString() {
    return conditionIcon;
  }

  /**
   * The day's precipitation type, {@code null} when {@code precip_occurrence_probability} is under
   * 0.15. Read off the day's HOURLY phases: the day's snow share is the precipitation-weighted mean
   * of the {@code timeseries[].precip_type.snow_probability} of its wet hours (the share of each
   * hour's water that falls as snow), read at that field's 0.01 resolution — {@code snow} when at
   * least 99.5% of the day's water is snow, {@code rain} when under 0.5% is (or no hour is typed),
   * {@code rain_snow_mix} between. So a day whose wet hours are all {@code rain_snow_mix} is {@code
   * rain_snow_mix}, and a rain day with one heavy snow hour is {@code rain_snow_mix}, not {@code
   * rain}. {@code condition} states this type's phase at its tier: a Snow or Rain/Snow Mix label,
   * or a liquid label. {@code freezing_rain} is an hourly type only; such hours count as liquid.
   *
   * @return the {@code precip_type} value, or empty when the response omits it ({@link
   *     PrecipitationType#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<PrecipitationType> precipType() {
    return Optional.ofNullable(precipType).map(PrecipitationType::fromWireValue);
  }

  /**
   * The {@code precip_type} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code precip_type} string, or empty when absent
   */
  public Optional<String> precipTypeAsString() {
    return Optional.ofNullable(precipType);
  }

  /**
   * Calendar-day maximum: the max over local midnight to midnight on {@code date} (earlier
   * releases: 06:00-18:00 only). On day 0, the part of the day the model run covers.
   *
   * @return the {@code high} value
   */
  public Quantiles high() {
    return high;
  }

  /**
   * Calendar-day minimum: the min over local midnight to midnight on {@code date}, the same hours
   * as {@code high} (earlier releases: the night that BEGINS on {@code date}, 18:00 through 06:00
   * the next morning). On day 0, the part of the day the model run covers. Label it "Low", not
   * "Tonight".
   *
   * @return the {@code low} value
   */
  public Quantiles low() {
    return low;
  }

  /**
   * Calendar-day maximum of the hourly {@code feels_like} (the NWS apparent temperature), per
   * member: each ensemble member's own hourly feels-like, that member's maximum over the same local
   * calendar day as {@code high}, and the statistics across those members' maxima ({@code mean}
   * their mean; {@code min}/{@code max} the lowest and highest member's). Every value is a day some
   * member forecast. In wind it sits at or below {@code high}, in hot humid air at or above; on a
   * mild day (above 10 C, below 26.7 C) it is the members' own daily high. Omitted when no member
   * carries a feels-like through the window.
   *
   * @return the {@code feels_like_high} value, or empty when the response omits it
   */
  public Optional<Quantiles> feelsLikeHigh() {
    return Optional.ofNullable(feelsLikeHigh);
  }

  /**
   * Calendar-day minimum of the hourly {@code feels_like} (the NWS apparent temperature), per
   * member: each member's own minimum over the same local calendar day as {@code low}, and the
   * statistics across those minima. It sits below {@code low} only by the wind chill of members
   * that are both cold and windy. Omitted when no member carries a feels-like through the window.
   *
   * @return the {@code feels_like_low} value, or empty when the response omits it
   */
  public Optional<Quantiles> feelsLikeLow() {
    return Optional.ofNullable(feelsLikeLow);
  }

  /**
   * The daily UTCI maximum, built like {@code feels_like_high} from the hourly {@code
   * feels_like_utci}. Present only with {@code feels_like=utci}, not served yet: absent today.
   *
   * @return the {@code feels_like_utci_high} value, or empty when the response omits it
   */
  public Optional<Quantiles> feelsLikeUtciHigh() {
    return Optional.ofNullable(feelsLikeUtciHigh);
  }

  /**
   * The daily UTCI minimum, built like {@code feels_like_low} from the hourly {@code
   * feels_like_utci}. Present only with {@code feels_like=utci}, not served yet: absent today.
   *
   * @return the {@code feels_like_utci_low} value, or empty when the response omits it
   */
  public Optional<Quantiles> feelsLikeUtciLow() {
    return Optional.ofNullable(feelsLikeUtciLow);
  }

  /**
   * The {@code total_precip} value.
   *
   * @return the {@code total_precip} value
   */
  public Quantiles totalPrecip() {
    return totalPrecip;
  }

  /**
   * The {@code total_snowfall} value.
   *
   * @return the {@code total_snowfall} value, or empty when the response omits it
   */
  public Optional<Quantiles> totalSnowfall() {
    return Optional.ofNullable(totalSnowfall);
  }

  /**
   * <b>MEANINGFUL precipitation: {@code P(day total >= 2.5 mm)}</b> (0-1) — the headline number,
   * the chance the day accumulates a tenth of an inch or more. For "will it precipitate at all",
   * read {@code precip_occurrence_probability} instead; the two answer different questions and
   * {@code precip_probability} is always the smaller of the two.
   *
   * @return the {@code precip_probability} value
   */
  public double precipProbability() {
    return precipProbability;
  }

  /**
   * <b>OCCURRENCE: {@code P(day total >= 1.0 mm)}</b> (0-1) — the chance the day precipitates at
   * all, as opposed to {@code precip_probability}'s chance that it amounts to anything. This is the
   * number {@code condition}'s tier and {@code precip_type}'s dry gate read; their PHASE comes from
   * the day's hourly phases (see {@code precip_type}).
   *
   * @return the {@code precip_occurrence_probability} value
   */
  public double precipOccurrenceProbability() {
    return precipOccurrenceProbability;
  }

  /**
   * {@code P(day snowfall total >= 2.5 mm)} (0-1), inclusive. Same estimator, threshold and caveats
   * as {@code precip_probability} — see its description. There is no snowfall counterpart to {@code
   * precip_occurrence_probability}: snow occurrence is not separately calibrated.
   *
   * @return the {@code snow_probability} value
   */
  public double snowProbability() {
    return snowProbability;
  }

  /**
   * The {@code snow_level_mean} value.
   *
   * @return the {@code snow_level_mean} value, or empty when the response omits it
   */
  public OptionalDouble snowLevelMean() {
    return snowLevelMean == null ? OptionalDouble.empty() : OptionalDouble.of(snowLevelMean);
  }

  /**
   * The day's snow level -- each rank the mean of the day's hourly ranks (the day-mean's
   * distribution under the rank coherence the day totals assume). Omitted when no hour serves a
   * snow level.
   *
   * @return the {@code snow_level} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowLevel() {
    return Optional.ofNullable(snowLevel);
  }

  /**
   * The day total GIVEN a wet day (total &gt;= the {@code daily_precip_occurrence_probability}
   * threshold, 1.0 mm): the upper part of the day-total distribution, renormalized. Not the mean
   * divided by the probability. Omitted under a 0.1 % chance.
   *
   * @return the {@code total_precip_if_wet} value, or empty when the response omits it
   */
  public Optional<Quantiles> totalPrecipIfWet() {
    return Optional.ofNullable(totalPrecipIfWet);
  }

  /**
   * The day's snowfall given a snowy day (&gt;= the {@code daily_snow_probability} threshold, 2.5
   * mm), as {@code total_precip_if_wet}.
   *
   * @return the {@code total_snowfall_if_wet} value, or empty when the response omits it
   */
  public Optional<Quantiles> totalSnowfallIfWet() {
    return Optional.ofNullable(totalSnowfallIfWet);
  }

  /**
   * The {@code precip_display} value.
   *
   * @return the {@code precip_display} value
   */
  public PrecipDisplay precipDisplay() {
    return precipDisplay;
  }

  /**
   * Average cloud cover for the day (0-100%). This is the mean of the day's hourly cloud-cover
   * means and its meaning is unchanged — but it is NOT what {@code condition} is derived from: the
   * sky label is the day's most likely cloud-cover CLASS, so a bimodal day can read {@code
   * cloud_cover_mean} near 50 and still be labelled Cloudy.
   *
   * @return the {@code cloud_cover_mean} value, or empty when the response omits it
   */
  public OptionalDouble cloudCoverMean() {
    return cloudCoverMean == null ? OptionalDouble.empty() : OptionalDouble.of(cloudCoverMean);
  }

  /**
   * Daily wind summary. Uses gust data when available, otherwise sustained wind.
   *
   * @return the {@code wind} value, or empty when the response omits it
   */
  public Optional<DailyWind> wind() {
    return Optional.ofNullable(wind);
  }

  /**
   * How SLR changes over a snow storm. Only present on days with 3+ hours of snowfall.
   * right_side_up: dense→fluffy (stable, powder on top). upside_down: fluffy→dense (weak base,
   * avalanche risk). mixed: no clear trend.
   *
   * @return the {@code storm_orientation} value, or empty when the response omits it ({@link
   *     StormOrientation#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<StormOrientation> stormOrientation() {
    return Optional.ofNullable(stormOrientation).map(StormOrientation::fromWireValue);
  }

  /**
   * The {@code storm_orientation} value exactly as the API sent it, even when this SDK version does
   * not know it.
   *
   * @return the raw {@code storm_orientation} string, or empty when absent
   */
  public Optional<String> stormOrientationAsString() {
    return Optional.ofNullable(stormOrientation);
  }

  /**
   * Plain-language companion to {@code storm_orientation}, suitable for direct display. Populated
   * whenever {@code storm_orientation} is set — the pair is either both present or both absent.
   * Example for upside_down: "Upside-down storm: fluffy snow fell first, denser snow on top. Weak
   * base layer — elevated avalanche risk, heavier ski surface.".
   *
   * @return the {@code storm_orientation_description} value, or empty when the response omits it
   */
  public Optional<String> stormOrientationDescription() {
    return Optional.ofNullable(stormOrientationDescription);
  }

  /**
   * The {@code astro} value.
   *
   * @return the {@code astro} value
   */
  public DailyAstro astro() {
    return astro;
  }

  /**
   * The {@code temperature_climatology} value.
   *
   * @return the {@code temperature_climatology} value, or empty when the response omits it
   */
  public Optional<DayClimatology> temperatureClimatology() {
    return Optional.ofNullable(temperatureClimatology);
  }

  @Override
  Object[] values() {
    return new Object[] {
      date,
      condition,
      conditionText,
      conditionIcon,
      precipType,
      high,
      low,
      feelsLikeHigh,
      feelsLikeLow,
      feelsLikeUtciHigh,
      feelsLikeUtciLow,
      totalPrecip,
      totalSnowfall,
      precipProbability,
      precipOccurrenceProbability,
      snowProbability,
      snowLevelMean,
      snowLevel,
      totalPrecipIfWet,
      totalSnowfallIfWet,
      precipDisplay,
      cloudCoverMean,
      wind,
      stormOrientation,
      stormOrientationDescription,
      astro,
      temperatureClimatology
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "date",
    "condition",
    "conditionText",
    "conditionIcon",
    "precipType",
    "high",
    "low",
    "feelsLikeHigh",
    "feelsLikeLow",
    "feelsLikeUtciHigh",
    "feelsLikeUtciLow",
    "totalPrecip",
    "totalSnowfall",
    "precipProbability",
    "precipOccurrenceProbability",
    "snowProbability",
    "snowLevelMean",
    "snowLevel",
    "totalPrecipIfWet",
    "totalSnowfallIfWet",
    "precipDisplay",
    "cloudCoverMean",
    "wind",
    "stormOrientation",
    "stormOrientationDescription",
    "astro",
    "temperatureClimatology"
  };

  /** Builds a {@link DailyForecast}. */
  public static final class Builder {
    private @Nullable LocalDate date;
    private @Nullable String condition;
    private @Nullable String conditionText;
    private @Nullable String conditionIcon;
    private @Nullable String precipType;
    private @Nullable Quantiles high;
    private @Nullable Quantiles low;
    private @Nullable Quantiles feelsLikeHigh;
    private @Nullable Quantiles feelsLikeLow;
    private @Nullable Quantiles feelsLikeUtciHigh;
    private @Nullable Quantiles feelsLikeUtciLow;
    private @Nullable Quantiles totalPrecip;
    private @Nullable Quantiles totalSnowfall;
    private @Nullable Double precipProbability;
    private @Nullable Double precipOccurrenceProbability;
    private @Nullable Double snowProbability;
    private @Nullable Double snowLevelMean;
    private @Nullable Quantiles snowLevel;
    private @Nullable Quantiles totalPrecipIfWet;
    private @Nullable Quantiles totalSnowfallIfWet;
    private @Nullable PrecipDisplay precipDisplay;
    private @Nullable Double cloudCoverMean;
    private @Nullable DailyWind wind;
    private @Nullable String stormOrientation;
    private @Nullable String stormOrientationDescription;
    private @Nullable DailyAstro astro;
    private @Nullable DayClimatology temperatureClimatology;

    private Builder() {}

    /**
     * Sets {@link DailyForecast#date()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder date(LocalDate value) {
      this.date = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#condition()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder condition(DailyCondition value) {
      this.condition = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyForecast#conditionAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder conditionAsString(String value) {
      this.condition = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#conditionText()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionText(String value) {
      this.conditionText = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#conditionIcon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionIcon(DailyIcon value) {
      this.conditionIcon = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyForecast#conditionIconAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder conditionIconAsString(String value) {
      this.conditionIcon = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#precipType()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipType(@Nullable PrecipitationType value) {
      this.precipType = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyForecast#precipTypeAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder precipTypeAsString(@Nullable String value) {
      this.precipType = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#high()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder high(Quantiles value) {
      this.high = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#low()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder low(Quantiles value) {
      this.low = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#feelsLikeHigh()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder feelsLikeHigh(@Nullable Quantiles value) {
      this.feelsLikeHigh = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#feelsLikeLow()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder feelsLikeLow(@Nullable Quantiles value) {
      this.feelsLikeLow = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#feelsLikeUtciHigh()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder feelsLikeUtciHigh(@Nullable Quantiles value) {
      this.feelsLikeUtciHigh = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#feelsLikeUtciLow()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder feelsLikeUtciLow(@Nullable Quantiles value) {
      this.feelsLikeUtciLow = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#totalPrecip()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder totalPrecip(Quantiles value) {
      this.totalPrecip = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#totalSnowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder totalSnowfall(@Nullable Quantiles value) {
      this.totalSnowfall = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#precipProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipProbability(double value) {
      this.precipProbability = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#precipOccurrenceProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipOccurrenceProbability(double value) {
      this.precipOccurrenceProbability = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder snowProbability(double value) {
      this.snowProbability = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowLevelMean()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevelMean(@Nullable Double value) {
      this.snowLevelMean = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevel(@Nullable Quantiles value) {
      this.snowLevel = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#totalPrecipIfWet()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder totalPrecipIfWet(@Nullable Quantiles value) {
      this.totalPrecipIfWet = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#totalSnowfallIfWet()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder totalSnowfallIfWet(@Nullable Quantiles value) {
      this.totalSnowfallIfWet = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#precipDisplay()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipDisplay(PrecipDisplay value) {
      this.precipDisplay = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#cloudCoverMean()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cloudCoverMean(@Nullable Double value) {
      this.cloudCoverMean = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#wind()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder wind(@Nullable DailyWind value) {
      this.wind = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#stormOrientation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder stormOrientation(@Nullable StormOrientation value) {
      this.stormOrientation = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyForecast#stormOrientationAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder stormOrientationAsString(@Nullable String value) {
      this.stormOrientation = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#stormOrientationDescription()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder stormOrientationDescription(@Nullable String value) {
      this.stormOrientationDescription = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#astro()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder astro(DailyAstro value) {
      this.astro = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#temperatureClimatology()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder temperatureClimatology(@Nullable DayClimatology value) {
      this.temperatureClimatology = value;
      return this;
    }

    /**
     * Builds the {@link DailyForecast}.
     *
     * @return the immutable DailyForecast
     * @throws IllegalStateException if a required value was not set
     */
    public DailyForecast build() {
      return new DailyForecast(this);
    }
  }
}
