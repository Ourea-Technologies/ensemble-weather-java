// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
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
  private final @Nullable String precipitationType;
  private final Quantiles high;
  private final Quantiles low;
  private final @Nullable Quantiles feelsLikeHigh;
  private final @Nullable Quantiles feelsLikeLow;
  private final Quantiles totalPrecipitation;
  private final @Nullable Quantiles totalSnowfall;
  private final double precipitationProbability;
  private final double precipitationOccurrenceProbability;
  private final double snowProbability;
  private final @Nullable Double snowLevelMean;
  private final @Nullable Quantiles snowLevel;
  private final @Nullable Quantiles totalPrecipitationIfWet;
  private final @Nullable Quantiles totalSnowfallIfWet;
  private final PrecipDisplay precipitationDisplay;
  private final @Nullable Double cloudCoverMean;
  private final @Nullable DailyWind wind;
  private final @Nullable String stormOrientation;
  private final @Nullable String stormOrientationDescription;
  private final DailyAstro astro;
  private final @Nullable DayClimatology temperatureClimatology;

  private final @Nullable Instant coveredFrom;
  private final @Nullable MeasuredPrecipitation measuredPrecipitation;
  private final @Nullable String snowCharacter;
  private final @Nullable Double slr;
  private final @Nullable SnowLevelRange snowLevelRange;
  private final @Nullable Quantiles snowLevelWetBulb;
  private final @Nullable SnowLevelRange snowLevelWetBulbRange;
  private final @Nullable DayNormal normal;
  private final @Nullable DayDeparture departure;
  private final @Nullable DayPercentile percentile;

  private DailyForecast(Builder b) {
    this.date = require(b.date, "date");
    this.condition = require(b.condition, "condition");
    this.conditionText = require(b.conditionText, "condition_text");
    this.conditionIcon = require(b.conditionIcon, "condition_icon");
    this.precipitationType = b.precipitationType;
    this.high = require(b.high, "high");
    this.low = require(b.low, "low");
    this.feelsLikeHigh = b.feelsLikeHigh;
    this.feelsLikeLow = b.feelsLikeLow;
    this.totalPrecipitation = require(b.totalPrecipitation, "total_precipitation");
    this.totalSnowfall = b.totalSnowfall;
    this.precipitationProbability =
        require(b.precipitationProbability, "precipitation_probability");
    this.precipitationOccurrenceProbability =
        require(b.precipitationOccurrenceProbability, "precipitation_occurrence_probability");
    this.snowProbability = require(b.snowProbability, "snow_probability");
    this.snowLevelMean = b.snowLevelMean;
    this.snowLevel = b.snowLevel;
    this.totalPrecipitationIfWet = b.totalPrecipitationIfWet;
    this.totalSnowfallIfWet = b.totalSnowfallIfWet;
    this.precipitationDisplay = require(b.precipitationDisplay, "precipitation_display");
    this.cloudCoverMean = b.cloudCoverMean;
    this.wind = b.wind;
    this.stormOrientation = b.stormOrientation;
    this.stormOrientationDescription = b.stormOrientationDescription;
    this.astro = require(b.astro, "astro");
    this.temperatureClimatology = b.temperatureClimatology;
    this.coveredFrom = b.coveredFrom;
    this.measuredPrecipitation = b.measuredPrecipitation;
    this.snowCharacter = b.snowCharacter;
    this.slr = b.slr;
    this.snowLevelRange = b.snowLevelRange;
    this.snowLevelWetBulb = b.snowLevelWetBulb;
    this.snowLevelWetBulbRange = b.snowLevelWetBulbRange;
    this.normal = b.normal;
    this.departure = b.departure;
    this.percentile = b.percentile;
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
    b.precipitationType = this.precipitationType;
    b.high = this.high;
    b.low = this.low;
    b.feelsLikeHigh = this.feelsLikeHigh;
    b.feelsLikeLow = this.feelsLikeLow;
    b.totalPrecipitation = this.totalPrecipitation;
    b.totalSnowfall = this.totalSnowfall;
    b.precipitationProbability = this.precipitationProbability;
    b.precipitationOccurrenceProbability = this.precipitationOccurrenceProbability;
    b.snowProbability = this.snowProbability;
    b.snowLevelMean = this.snowLevelMean;
    b.snowLevel = this.snowLevel;
    b.totalPrecipitationIfWet = this.totalPrecipitationIfWet;
    b.totalSnowfallIfWet = this.totalSnowfallIfWet;
    b.precipitationDisplay = this.precipitationDisplay;
    b.cloudCoverMean = this.cloudCoverMean;
    b.wind = this.wind;
    b.stormOrientation = this.stormOrientation;
    b.stormOrientationDescription = this.stormOrientationDescription;
    b.astro = this.astro;
    b.temperatureClimatology = this.temperatureClimatology;
    b.coveredFrom = this.coveredFrom;
    b.measuredPrecipitation = this.measuredPrecipitation;
    b.snowCharacter = this.snowCharacter;
    b.slr = this.slr;
    b.snowLevelRange = this.snowLevelRange;
    b.snowLevelWetBulb = this.snowLevelWetBulb;
    b.snowLevelWetBulbRange = this.snowLevelWetBulbRange;
    b.normal = this.normal;
    b.departure = this.departure;
    b.percentile = this.percentile;
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
   * A stable machine code to switch on ({@code partly_cloudy}, {@code rain_snow_mix_likely}). Treat
   * unknown codes as additive. {@code condition_text} is its display text, in English today; it is
   * for showing, never for parsing or deriving the code (a {@code lang=} query parameter is
   * reserved for localised text).
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
   * Machine-readable icon hint for frontends: one of {@link ConditionIcon} (the {@code night_*}
   * forms are hourly only). Treat unknown values as additive.
   *
   * @return the {@code condition_icon} value ({@link ConditionIcon#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public ConditionIcon conditionIcon() {
    return ConditionIcon.fromWireValue(conditionIcon);
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
   * The day's precipitation type, absent when {@code precipitation_occurrence_probability} is under
   * 0.15. Read off the day's HOURLY phases: the day's snow share is the precipitation-weighted mean
   * of the {@code timeseries[].precipitation_type.snow_probability} of its wet hours (the share of
   * each hour's water that falls as snow), read at that field's 0.01 resolution — {@code snow} when
   * at least 99.5% of the day's water is snow, {@code rain} when under 0.5% is (or no hour is
   * typed), {@code rain_snow_mix} between. So a day whose wet hours are all {@code rain_snow_mix}
   * is {@code rain_snow_mix}, and a rain day with one heavy snow hour is {@code rain_snow_mix}, not
   * {@code rain}. {@code condition} states this type's phase at its tier: a Snow or Rain/Snow Mix
   * label, or a liquid label. {@code freezing_rain} is an hourly type only; such hours count as
   * liquid.
   *
   * @return the {@code precipitation_type} value, or empty when the response omits it ({@link
   *     PrecipitationType#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<PrecipitationType> precipitationType() {
    return Optional.ofNullable(precipitationType).map(PrecipitationType::fromWireValue);
  }

  /**
   * The {@code precipitation_type} value exactly as the API sent it, even when this SDK version
   * does not know it.
   *
   * @return the raw {@code precipitation_type} string, or empty when absent
   */
  public Optional<String> precipitationTypeAsString() {
    return Optional.ofNullable(precipitationType);
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
   * The {@code total_precipitation} value.
   *
   * @return the {@code total_precipitation} value
   */
  public Quantiles totalPrecipitation() {
    return totalPrecipitation;
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
   * read {@code precipitation_occurrence_probability} instead; the two answer different questions
   * and {@code precipitation_probability} is always the smaller of the two.
   *
   * @return the {@code precipitation_probability} value
   */
  public double precipitationProbability() {
    return precipitationProbability;
  }

  /**
   * <b>OCCURRENCE: {@code P(day total >= 1.0 mm)}</b> (0-1) — the chance the day precipitates at
   * all, as opposed to {@code precipitation_probability}'s chance that it amounts to anything. This
   * is the number {@code condition}'s tier and {@code precipitation_type}'s dry gate read; their
   * PHASE comes from the day's hourly phases (see {@code precipitation_type}).
   *
   * @return the {@code precipitation_occurrence_probability} value
   */
  public double precipitationOccurrenceProbability() {
    return precipitationOccurrenceProbability;
  }

  /**
   * {@code P(day snowfall total >= 2.5 mm)} (0-1), inclusive. Same estimator, threshold and caveats
   * as {@code precipitation_probability} — see its description. There is no snowfall counterpart to
   * {@code precipitation_occurrence_probability}: snow occurrence is not separately calibrated.
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
   * The day total GIVEN a wet day (total &gt;= the {@code
   * daily_precipitation_occurrence_probability} threshold, 1.0 mm): the upper part of the day-total
   * distribution, renormalized. Not the mean divided by the probability. Omitted under a 0.1 %
   * chance.
   *
   * @return the {@code total_precipitation_if_wet} value, or empty when the response omits it
   */
  public Optional<Quantiles> totalPrecipitationIfWet() {
    return Optional.ofNullable(totalPrecipitationIfWet);
  }

  /**
   * The day's snowfall given a snowy day (&gt;= the {@code daily_snow_probability} threshold, 2.5
   * mm), as {@code total_precipitation_if_wet}.
   *
   * @return the {@code total_snowfall_if_wet} value, or empty when the response omits it
   */
  public Optional<Quantiles> totalSnowfallIfWet() {
    return Optional.ofNullable(totalSnowfallIfWet);
  }

  /**
   * The {@code precipitation_display} value.
   *
   * @return the {@code precipitation_display} value
   */
  public PrecipDisplay precipitationDisplay() {
    return precipitationDisplay;
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

  /**
   * Present only on a part day -- the leading date of a run whose reference init falls after its
   * local midnight (today, for most cycles) -- and absent on a whole day. The instant (UTC) from
   * which the row's values describe the date: its {@code high}, {@code low}, totals and
   * probabilities are over {@code covered_from} to the end of the local day, not the whole calendar
   * day. The hours between local midnight and {@code covered_from} are not in the forecast series,
   * so a part day's total can be well under the day's; compare a whole day with the climate ({@code
   * departure} and {@code percentile} are absent on a part day for the same reason). One exception.
   * With the nowcast applied, the precipitation total, its probabilities and its if-wet amounts
   * also include the radar-measured precipitation of the hours between local midnight and {@code
   * covered_from} (MRMS radar-only, where the radar observed the point well), so today's total
   * keeps the rain that already fell when the reference run advances. The high and low still start
   * at {@code covered_from}. The snowfall total includes the snow of that measured precipitation,
   * each measured hour's water at the snow-to-water ratio of the older run's forecast for that
   * hour, and the day's precipitation type and label count it as that forecast's type (snow on a
   * cold day, not rain); until that older run's forecast has been read for the point (in the
   * background, after each new run), the measured water counts without snow, as before. Not on a
   * pinned {@code run}. Its row starts at the run's init in every value: no hour before the init is
   * counted, measured or forecast.
   *
   * @return the {@code covered_from} value, or empty when the response omits it
   */
  public Optional<Instant> coveredFrom() {
    return Optional.ofNullable(coveredFrom);
  }

  /**
   * Present only on a day part of which is a measurement -- the leading date, when the radar (MRMS
   * radar-only) measured hours between its local midnight and the reference init -- and absent
   * otherwise. Always absent on a pinned {@code run}: a pinned row counts nothing from before the
   * run's init, so its {@code total_precipitation} and {@code total_snowfall} are its served
   * hours'. {@code amount} is that measured precipitation, in the response's precipitation unit (as
   * {@code precipitation_display.amount}); {@code through} is the end (UTC) of its last measured
   * hour. The day's {@code total_precipitation} and {@code total_snowfall} include it with its
   * measurement error: its band is no longer only the forecast hours' spread, because a radar
   * amount is itself uncertain (against MRMS MultiSensor QPE, the scoring truth, a radar-only day
   * sum runs from about 0.7x to 1.2x at 15 mm or more, wider when lighter). The day's {@code mean}
   * does not count {@code amount} in full, so a first day's {@code total_precipitation.mean} can be
   * below {@code measured_precipitation.amount}. {@code amount} is the radar's own sum; the total
   * counts it at the measured ratio of truth to radar for an amount of its size, about 0.87 to
   * 0.93: 0.914 of it up to 1.9 mm, 0.868 at 9.0 mm, 0.925 from 22.1 mm (the radar amount in mm,
   * whatever the response unit). Between those amounts the water counted rises in a straight line
   * with the amount measured, so the ratio has no step and more measured water never counts for
   * less. Example: 11.33 mm measured counts as 10.06 mm (0.888), and with 0.02 mm in the hours
   * still forecast the day's {@code mean} is 10.08 mm. Each percentile takes the ratio's own
   * quantile the same way.
   *
   * @return the {@code measured_precipitation} value, or empty when the response omits it
   */
  public Optional<MeasuredPrecipitation> measuredPrecipitation() {
    return Optional.ofNullable(measuredPrecipitation);
  }

  /**
   * The day's snow character. {@code wet} when half or more of the water that fell as the day's
   * snow fell in hours whose snow was wet (each hour weighted by its {@code ensemble.snowfall.mean}
   * over its {@code ensemble.slr.p50}, the water it brought); otherwise the density class of the
   * day's snow-to-liquid ratio, {@code slr}: its snow over the water that fell as that snow, the
   * way a day's ratio is observed. A heavy dense hour outweighs a dusting of powder. Present
   * exactly when the day serves snowfall ({@code total_snowfall.mean} above 0) and {@code
   * precipitation_display.kind} is not {@code none}; absent otherwise. There is no daily
   * Light/Heavy Snow condition: intensity is a rate, and the NWS heavy-snow amount criteria differ
   * by region.
   *
   * @return the {@code snow_character} value, or empty when the response omits it ({@link
   *     SnowCharacter#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<SnowCharacter> snowCharacter() {
    return Optional.ofNullable(snowCharacter).map(SnowCharacter::fromWireValue);
  }

  /**
   * The {@code snow_character} value exactly as the API sent it, even when this SDK version does
   * not know it.
   *
   * @return the raw {@code snow_character} string, or empty when absent
   */
  public Optional<String> snowCharacterAsString() {
    return Optional.ofNullable(snowCharacter);
  }

  /**
   * The day's snow-to-liquid ratio, to one decimal: the ratio {@code snow_character} classifies on
   * a day that is not {@code wet}. Present exactly when {@code snow_character} is.
   *
   * @return the {@code slr} value, or empty when the response omits it
   */
  public OptionalDouble slr() {
    return slr == null ? OptionalDouble.empty() : OptionalDouble.of(slr);
  }

  /**
   * The snow level over the day's wet hours -- the hours the day's {@code precipitation_type} reads
   * (the hours that serve a precipitation type): the lowest and highest hourly {@code
   * ensemble.snow_level.mean} and their precipitation-weighted mean. {@code Snow_level} and {@code
   * snow_level_mean} read the same hours with the same weights on such a day, so {@code
   * snow_level.mean} is this {@code mean}; the p10-p90 of {@code snow_level} is the members'
   * spread, and this is the day's own range in time while it precipitates. Omitted on a day with no
   * wet hour. The RAIN LINE, as {@code snow_level} is.
   *
   * @return the {@code snow_level_range} value, or empty when the response omits it
   */
  public Optional<SnowLevelRange> snowLevelRange() {
    return Optional.ofNullable(snowLevelRange);
  }

  /**
   * The day's snow level on the NWS scale -- the height of the 0.5 C wet-bulb. It is the day's
   * {@code snow_level} on that scale, the same statistic over the same hours -- each rank the mean
   * of the hourly {@code ensemble.snow_level_wet_bulb} ranks over the hours {@code snow_level}
   * reads (the wet hours, weighted by their precipitation, on a day that has any; every hour on a
   * dry day) -- so each rank is the {@code snow_level} rank raised by 275 m (901 ft), as on the
   * hour, and {@code mean} is {@code snow_level_wet_bulb_range.mean}. (An hourly rank at {@code 0},
   * snow to sea level, is {@code 0} on both scales, so on a day with such hours a rank is less than
   * 275 m above {@code snow_level}'s, and is {@code 0} only where every hour it reads is.) Omitted
   * when no hour serves a snow level.
   *
   * @return the {@code snow_level_wet_bulb} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowLevelWetBulb() {
    return Optional.ofNullable(snowLevelWetBulb);
  }

  /**
   * {@code Snow_level_range} on the NWS scale -- over the same wet hours, the lowest and highest
   * hourly {@code ensemble.snow_level_wet_bulb.mean} and their precipitation-weighted mean (which
   * is {@code snow_level_wet_bulb.mean}). Each is the {@code snow_level_range} value raised by 275
   * m (901 ft), an hour at {@code 0} (snow to sea level) counted at {@code 0}. Show this range as
   * the day's "snow level". Omitted exactly when {@code snow_level_range} is.
   *
   * @return the {@code snow_level_wet_bulb_range} value, or empty when the response omits it
   */
  public Optional<SnowLevelRange> snowLevelWetBulbRange() {
    return Optional.ofNullable(snowLevelWetBulbRange);
  }

  /**
   * The {@code normal} value.
   *
   * @return the {@code normal} value, or empty when the response omits it
   */
  public Optional<DayNormal> normal() {
    return Optional.ofNullable(normal);
  }

  /**
   * The {@code departure} value.
   *
   * @return the {@code departure} value, or empty when the response omits it
   */
  public Optional<DayDeparture> departure() {
    return Optional.ofNullable(departure);
  }

  /**
   * The {@code percentile} value.
   *
   * @return the {@code percentile} value, or empty when the response omits it
   */
  public Optional<DayPercentile> percentile() {
    return Optional.ofNullable(percentile);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      date,
      condition,
      conditionText,
      conditionIcon,
      precipitationType,
      high,
      low,
      feelsLikeHigh,
      feelsLikeLow,
      totalPrecipitation,
      totalSnowfall,
      precipitationProbability,
      precipitationOccurrenceProbability,
      snowProbability,
      snowLevelMean,
      snowLevel,
      totalPrecipitationIfWet,
      totalSnowfallIfWet,
      precipitationDisplay,
      cloudCoverMean,
      wind,
      stormOrientation,
      stormOrientationDescription,
      astro,
      temperatureClimatology,
      coveredFrom,
      measuredPrecipitation,
      snowCharacter,
      slr,
      snowLevelRange,
      snowLevelWetBulb,
      snowLevelWetBulbRange,
      normal,
      departure,
      percentile
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "date",
    "condition",
    "conditionText",
    "conditionIcon",
    "precipitationType",
    "high",
    "low",
    "feelsLikeHigh",
    "feelsLikeLow",
    "totalPrecipitation",
    "totalSnowfall",
    "precipitationProbability",
    "precipitationOccurrenceProbability",
    "snowProbability",
    "snowLevelMean",
    "snowLevel",
    "totalPrecipitationIfWet",
    "totalSnowfallIfWet",
    "precipitationDisplay",
    "cloudCoverMean",
    "wind",
    "stormOrientation",
    "stormOrientationDescription",
    "astro",
    "temperatureClimatology",
    "coveredFrom",
    "measuredPrecipitation",
    "snowCharacter",
    "slr",
    "snowLevelRange",
    "snowLevelWetBulb",
    "snowLevelWetBulbRange",
    "normal",
    "departure",
    "percentile"
  };

  /** Builds a {@link DailyForecast}. */
  public static final class Builder {
    private @Nullable LocalDate date;
    private @Nullable String condition;
    private @Nullable String conditionText;
    private @Nullable String conditionIcon;
    private @Nullable String precipitationType;
    private @Nullable Quantiles high;
    private @Nullable Quantiles low;
    private @Nullable Quantiles feelsLikeHigh;
    private @Nullable Quantiles feelsLikeLow;
    private @Nullable Quantiles totalPrecipitation;
    private @Nullable Quantiles totalSnowfall;
    private @Nullable Double precipitationProbability;
    private @Nullable Double precipitationOccurrenceProbability;
    private @Nullable Double snowProbability;
    private @Nullable Double snowLevelMean;
    private @Nullable Quantiles snowLevel;
    private @Nullable Quantiles totalPrecipitationIfWet;
    private @Nullable Quantiles totalSnowfallIfWet;
    private @Nullable PrecipDisplay precipitationDisplay;
    private @Nullable Double cloudCoverMean;
    private @Nullable DailyWind wind;
    private @Nullable String stormOrientation;
    private @Nullable String stormOrientationDescription;
    private @Nullable DailyAstro astro;
    private @Nullable DayClimatology temperatureClimatology;

    private @Nullable Instant coveredFrom;
    private @Nullable MeasuredPrecipitation measuredPrecipitation;
    private @Nullable String snowCharacter;
    private @Nullable Double slr;
    private @Nullable SnowLevelRange snowLevelRange;
    private @Nullable Quantiles snowLevelWetBulb;
    private @Nullable SnowLevelRange snowLevelWetBulbRange;
    private @Nullable DayNormal normal;
    private @Nullable DayDeparture departure;
    private @Nullable DayPercentile percentile;

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
    public Builder conditionIcon(ConditionIcon value) {
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
     * Sets {@link DailyForecast#precipitationType()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationType(@Nullable PrecipitationType value) {
      this.precipitationType = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyForecast#precipitationTypeAsString()}, the raw wire value, kept verbatim
     * even when this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder precipitationTypeAsString(@Nullable String value) {
      this.precipitationType = value;
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
     * Sets {@link DailyForecast#totalPrecipitation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder totalPrecipitation(Quantiles value) {
      this.totalPrecipitation = value;
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
     * Sets {@link DailyForecast#precipitationProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipitationProbability(double value) {
      this.precipitationProbability = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#precipitationOccurrenceProbability()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipitationOccurrenceProbability(double value) {
      this.precipitationOccurrenceProbability = value;
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
     * Sets {@link DailyForecast#totalPrecipitationIfWet()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder totalPrecipitationIfWet(@Nullable Quantiles value) {
      this.totalPrecipitationIfWet = value;
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
     * Sets {@link DailyForecast#precipitationDisplay()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder precipitationDisplay(PrecipDisplay value) {
      this.precipitationDisplay = value;
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
     * Sets {@link DailyForecast#coveredFrom()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder coveredFrom(@Nullable Instant value) {
      this.coveredFrom = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#measuredPrecipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder measuredPrecipitation(@Nullable MeasuredPrecipitation value) {
      this.measuredPrecipitation = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowCharacter()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowCharacter(@Nullable SnowCharacter value) {
      this.snowCharacter = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowCharacterAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder snowCharacterAsString(@Nullable String value) {
      this.snowCharacter = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#slr()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder slr(@Nullable Double value) {
      this.slr = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowLevelRange()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevelRange(@Nullable SnowLevelRange value) {
      this.snowLevelRange = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowLevelWetBulb()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevelWetBulb(@Nullable Quantiles value) {
      this.snowLevelWetBulb = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#snowLevelWetBulbRange()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevelWetBulbRange(@Nullable SnowLevelRange value) {
      this.snowLevelWetBulbRange = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#normal()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder normal(@Nullable DayNormal value) {
      this.normal = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#departure()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder departure(@Nullable DayDeparture value) {
      this.departure = value;
      return this;
    }

    /**
     * Sets {@link DailyForecast#percentile()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder percentile(@Nullable DayPercentile value) {
      this.percentile = value;
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
