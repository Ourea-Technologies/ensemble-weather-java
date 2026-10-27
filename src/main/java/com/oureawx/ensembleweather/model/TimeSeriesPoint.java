// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A TimeSeriesPoint from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code TimeSeriesPoint}.
 */
public final class TimeSeriesPoint extends Model {
  private final Instant validTime;
  private final int leadTimeHours;
  private final boolean isNight;
  private final String condition;
  private final String conditionText;
  private final String conditionIcon;
  private final @Nullable Boolean gustNotable;
  private final @Nullable PrecipType precipType;
  private final @Nullable InversionInfo inversion;
  private final @Nullable FreezingLevel freezingLevel;
  private final EnsembleStats ensemble;
  private final @Nullable HourProvenance provenance;
  private final List<ModelStats> modelMeans;
  private final List<ModelMembers> members;
  private final @Nullable MembersCumulative membersCumulative;
  private final @Nullable Double cumulativePrecipitation;
  private final @Nullable Double cumulativeSnowfall;

  private TimeSeriesPoint(Builder b) {
    this.validTime = require(b.validTime, "valid_time");
    this.leadTimeHours = require(b.leadTimeHours, "lead_time_hours");
    this.isNight = require(b.isNight, "is_night");
    this.condition = require(b.condition, "condition");
    this.conditionText = require(b.conditionText, "condition_text");
    this.conditionIcon = require(b.conditionIcon, "condition_icon");
    this.gustNotable = b.gustNotable;
    this.precipType = b.precipType;
    this.inversion = b.inversion;
    this.freezingLevel = b.freezingLevel;
    this.ensemble = require(b.ensemble, "ensemble");
    this.provenance = b.provenance;
    this.modelMeans = List.copyOf(b.modelMeans);
    this.members = List.copyOf(b.members);
    this.membersCumulative = b.membersCumulative;
    this.cumulativePrecipitation = b.cumulativePrecipitation;
    this.cumulativeSnowfall = b.cumulativeSnowfall;
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
    b.validTime = this.validTime;
    b.leadTimeHours = this.leadTimeHours;
    b.isNight = this.isNight;
    b.condition = this.condition;
    b.conditionText = this.conditionText;
    b.conditionIcon = this.conditionIcon;
    b.gustNotable = this.gustNotable;
    b.precipType = this.precipType;
    b.inversion = this.inversion;
    b.freezingLevel = this.freezingLevel;
    b.ensemble = this.ensemble;
    b.provenance = this.provenance;
    b.modelMeans = this.modelMeans;
    b.members = this.members;
    b.membersCumulative = this.membersCumulative;
    b.cumulativePrecipitation = this.cumulativePrecipitation;
    b.cumulativeSnowfall = this.cumulativeSnowfall;
    return b;
  }

  /**
   * The {@code valid_time} value.
   *
   * @return the {@code valid_time} value
   */
  public Instant validTime() {
    return validTime;
  }

  /**
   * The {@code lead_time_hours} value.
   *
   * @return the {@code lead_time_hours} value
   */
  public int leadTimeHours() {
    return leadTimeHours;
  }

  /**
   * Whether this hour is nighttime at the forecast location.
   *
   * @return the {@code is_night} value
   */
  public boolean isNight() {
    return isNight;
  }

  /**
   * A stable machine code to switch on -- the display text ({@code condition_text}) lowercased,
   * each run of other characters one {@code _} ({@code partly_cloudy}, {@code
   * rain_snow_mix_likely}). Treat unknown codes as additive.
   *
   * @return the {@code condition} value ({@link HourlyCondition#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public HourlyCondition condition() {
    return HourlyCondition.fromWireValue(condition);
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
   * Human-readable weather condition for the hour, day/night aware. One rule shared with the daily
   * {@code condition}: a SKY class crossed with a PRECIPITATION tier.
   *
   * @return the {@code condition_text} value
   */
  public String conditionText() {
    return conditionText;
  }

  /**
   * Icon hint (prefixed with night_ for nighttime). Sky-only hints name the five sky classes.
   * Precipitating hints carry the SIDE of the sky: {@code sun_*} ({@code night_moon_*}) when the
   * sky leans clear ({@code P(< 40%) > P(>= 80%)}) — a sun or moon beside the rain cloud — and
   * plain otherwise. Additive: {@code sun_chance_showers}, {@code sun_showers_likely}, {@code
   * night_moon_chance_showers}, {@code night_moon_showers_likely}, {@code mostly_sunny}, {@code
   * mostly_cloudy}, {@code night_mostly_clear}, {@code night_mostly_cloudy}, {@code
   * showers_likely}, {@code night_showers_likely}. Retired: {@code sun_rain}, {@code
   * scattered_showers}, {@code night_scattered_showers}, {@code rain_likely}, {@code
   * night_rain_likely} (the hourly never says "likely rain"; an hour below the definite tier is
   * showers). Snow, mix and freezing-rain conditions take their phase's icon at every tier ({@code
   * snow}, {@code rain_snow_mix}, {@code freezing_rain}, and the {@code night_} forms). Additive:
   * {@code freezing_rain}, {@code night_freezing_rain}.
   *
   * @return the {@code condition_icon} value ({@link HourlyIcon#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public HourlyIcon conditionIcon() {
    return HourlyIcon.fromWireValue(conditionIcon);
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
   * Whether the hour's gust is worth showing beside the wind -- its median at least 1.3 times the
   * wind's median and at least 5 mph (2.2 m/s) above it. Omitted where the hour serves no gust.
   *
   * @return the {@code gust_notable} value, or empty when the response omits it
   */
  public Optional<Boolean> gustNotable() {
    return Optional.ofNullable(gustNotable);
  }

  /**
   * Present only when precipitation &gt; 0.
   *
   * @return the {@code precip_type} value, or empty when the response omits it
   */
  public Optional<PrecipType> precipType() {
    return Optional.ofNullable(precipType);
  }

  /**
   * Present only when a temperature inversion or cold pool is detected for this hour.
   *
   * @return the {@code inversion} value, or empty when the response omits it
   */
  public Optional<InversionInfo> inversion() {
    return Optional.ofNullable(inversion);
  }

  /**
   * The hour's freezing level. Omitted when the profile has no level above the surface to read it
   * from.
   *
   * @return the {@code freezing_level} value, or empty when the response omits it
   */
  public Optional<FreezingLevel> freezingLevel() {
    return Optional.ofNullable(freezingLevel);
  }

  /**
   * The {@code ensemble} value.
   *
   * @return the {@code ensemble} value
   */
  public EnsembleStats ensemble() {
    return ensemble;
  }

  /**
   * Present only with {@code ?diagnostics=provenance} -- which post-processing rule shaped each
   * variable this hour. A diagnostic for research and verification, not for display.
   *
   * @return the {@code provenance} value, or empty when the response omits it
   */
  public Optional<HourProvenance> provenance() {
    return Optional.ofNullable(provenance);
  }

  /**
   * The {@code model_means} value.
   *
   * @return the {@code model_means} values; empty when the response has none
   */
  public List<ModelStats> modelMeans() {
    return modelMeans;
  }

  /**
   * Per-member ensemble values, one {@code (id, members)} per model with members at this hour, in
   * {@code model_run.models} order. Only present with {@code ?members=true} -- for spaghetti plots
   * and plumes. A model with no members at this hour is absent. The 6-hourly models have members at
   * every 6th hour, rrfs at every hour to 84 h.
   *
   * @return the {@code members} values; empty when the response has none
   */
  public List<ModelMembers> members() {
    return members;
  }

  /**
   * The {@code members_cumulative} value.
   *
   * @return the {@code members_cumulative} value, or empty when the response omits it
   */
  public Optional<MembersCumulative> membersCumulative() {
    return Optional.ofNullable(membersCumulative);
  }

  /**
   * The HEADLINE's running precipitation total -- the sum of {@code ensemble.precipitation.mean}
   * from the first served hour ("now") through this one, in the request's units. The running line
   * {@code snow_outlook} is the 24 h / 48 h / 7 d point of; draw it on the plume beside the
   * members' band. A mean is additive, so this is exact; percentiles are not, so there is no
   * running headline band (the members' is {@code members_cumulative}). Never compute it on the
   * client.
   *
   * @return the {@code cumulative_precipitation} value, or empty when the response omits it
   */
  public OptionalDouble cumulativePrecipitation() {
    return cumulativePrecipitation == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(cumulativePrecipitation);
  }

  /**
   * The headline's running snowfall total, as {@code cumulative_precipitation} over {@code
   * ensemble.snowfall.mean}; present exactly when the hour serves {@code ensemble.snowfall}.
   *
   * @return the {@code cumulative_snowfall} value, or empty when the response omits it
   */
  public OptionalDouble cumulativeSnowfall() {
    return cumulativeSnowfall == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(cumulativeSnowfall);
  }

  @Override
  Object[] values() {
    return new Object[] {
      validTime,
      leadTimeHours,
      isNight,
      condition,
      conditionText,
      conditionIcon,
      gustNotable,
      precipType,
      inversion,
      freezingLevel,
      ensemble,
      provenance,
      modelMeans,
      members,
      membersCumulative,
      cumulativePrecipitation,
      cumulativeSnowfall
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "validTime",
    "leadTimeHours",
    "isNight",
    "condition",
    "conditionText",
    "conditionIcon",
    "gustNotable",
    "precipType",
    "inversion",
    "freezingLevel",
    "ensemble",
    "provenance",
    "modelMeans",
    "members",
    "membersCumulative",
    "cumulativePrecipitation",
    "cumulativeSnowfall"
  };

  /** Builds a {@link TimeSeriesPoint}. */
  public static final class Builder {
    private @Nullable Instant validTime;
    private @Nullable Integer leadTimeHours;
    private @Nullable Boolean isNight;
    private @Nullable String condition;
    private @Nullable String conditionText;
    private @Nullable String conditionIcon;
    private @Nullable Boolean gustNotable;
    private @Nullable PrecipType precipType;
    private @Nullable InversionInfo inversion;
    private @Nullable FreezingLevel freezingLevel;
    private @Nullable EnsembleStats ensemble;
    private @Nullable HourProvenance provenance;
    private List<ModelStats> modelMeans = List.of();
    private List<ModelMembers> members = List.of();
    private @Nullable MembersCumulative membersCumulative;
    private @Nullable Double cumulativePrecipitation;
    private @Nullable Double cumulativeSnowfall;

    private Builder() {}

    /**
     * Sets {@link TimeSeriesPoint#validTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder validTime(Instant value) {
      this.validTime = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#leadTimeHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder leadTimeHours(int value) {
      this.leadTimeHours = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#isNight()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder isNight(boolean value) {
      this.isNight = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#condition()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder condition(HourlyCondition value) {
      this.condition = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#conditionAsString()}, the raw wire value, kept verbatim even when
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
     * Sets {@link TimeSeriesPoint#conditionText()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionText(String value) {
      this.conditionText = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#conditionIcon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder conditionIcon(HourlyIcon value) {
      this.conditionIcon = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#conditionIconAsString()}, the raw wire value, kept verbatim even
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
     * Sets {@link TimeSeriesPoint#gustNotable()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder gustNotable(@Nullable Boolean value) {
      this.gustNotable = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#precipType()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipType(@Nullable PrecipType value) {
      this.precipType = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#inversion()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder inversion(@Nullable InversionInfo value) {
      this.inversion = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#freezingLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder freezingLevel(@Nullable FreezingLevel value) {
      this.freezingLevel = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#ensemble()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder ensemble(EnsembleStats value) {
      this.ensemble = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#provenance()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder provenance(@Nullable HourProvenance value) {
      this.provenance = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#modelMeans()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder modelMeans(List<ModelStats> values) {
      this.modelMeans = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#members()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder members(List<ModelMembers> values) {
      this.members = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#membersCumulative()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder membersCumulative(@Nullable MembersCumulative value) {
      this.membersCumulative = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#cumulativePrecipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cumulativePrecipitation(@Nullable Double value) {
      this.cumulativePrecipitation = value;
      return this;
    }

    /**
     * Sets {@link TimeSeriesPoint#cumulativeSnowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cumulativeSnowfall(@Nullable Double value) {
      this.cumulativeSnowfall = value;
      return this;
    }

    /**
     * Builds the {@link TimeSeriesPoint}.
     *
     * @return the immutable TimeSeriesPoint
     * @throws IllegalStateException if a required value was not set
     */
    public TimeSeriesPoint build() {
      return new TimeSeriesPoint(this);
    }
  }
}
