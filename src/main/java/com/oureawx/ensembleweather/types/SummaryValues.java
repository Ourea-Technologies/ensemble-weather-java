// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * Each number a summary sentence states, as served. A property is present only when the sentence
 * states it.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SummaryValues}.
 */
public final class SummaryValues extends Model {
  private final @Nullable Double snowfall;
  private final @Nullable Double snowfallP10;
  private final @Nullable Double snowfallP90;
  private final @Nullable String snowfallWindow;
  private final @Nullable Double temperature;
  private final @Nullable Double windGust;
  private final @Nullable Double precipitationProbability;

  private SummaryValues(Builder b) {
    this.snowfall = b.snowfall;
    this.snowfallP10 = b.snowfallP10;
    this.snowfallP90 = b.snowfallP90;
    this.snowfallWindow = b.snowfallWindow;
    this.temperature = b.temperature;
    this.windGust = b.windGust;
    this.precipitationProbability = b.precipitationProbability;
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
    b.snowfall = this.snowfall;
    b.snowfallP10 = this.snowfallP10;
    b.snowfallP90 = this.snowfallP90;
    b.snowfallWindow = this.snowfallWindow;
    b.temperature = this.temperature;
    b.windGust = this.windGust;
    b.precipitationProbability = this.precipitationProbability;
    return b;
  }

  /**
   * The window's headline snowfall total (mm, in): for {@code snow_coming} the forecast's {@code
   * snow_outlook.<snowfall_window>}; for {@code pass_snow_starts} the window's {@code
   * pass.snow_windows[].total}.
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public OptionalDouble snowfall() {
    return snowfall == null ? OptionalDouble.empty() : OptionalDouble.of(snowfall);
  }

  /**
   * The window's served p10 ({@code snow_outlook.quantiles.<window>.p10}, or the pass window's
   * {@code p10}).
   *
   * @return the {@code snowfall_p10} value, or empty when the response omits it
   */
  public OptionalDouble snowfallP10() {
    return snowfallP10 == null ? OptionalDouble.empty() : OptionalDouble.of(snowfallP10);
  }

  /**
   * The window's served p90.
   *
   * @return the {@code snowfall_p90} value, or empty when the response omits it
   */
  public OptionalDouble snowfallP90() {
    return snowfallP90 == null ? OptionalDouble.empty() : OptionalDouble.of(snowfallP90);
  }

  /**
   * Which window {@code snowfall} totals. One of {@code next_24h}, {@code next_48h}, {@code
   * next_7d}, {@code pass_snow_window}; read it as a string, since the API may add values.
   *
   * @return the {@code snowfall_window} value, or empty when the response omits it
   */
  public Optional<String> snowfallWindow() {
    return Optional.ofNullable(snowfallWindow);
  }

  /**
   * A day's served high ({@code daily[].high.mean}) a {@code day_headline} or {@code
   * temperature_change} states.
   *
   * @return the {@code temperature} value, or empty when the response omits it
   */
  public OptionalDouble temperature() {
    return temperature == null ? OptionalDouble.empty() : OptionalDouble.of(temperature);
  }

  /**
   * The served hourly gust mean ({@code ensemble.wind_gust.mean}) a {@code wind} sentence states.
   *
   * @return the {@code wind_gust} value, or empty when the response omits it
   */
  public OptionalDouble windGust() {
    return windGust == null ? OptionalDouble.empty() : OptionalDouble.of(windGust);
  }

  /**
   * The served chance (0-1) behind the sentence's likelihood word.
   *
   * @return the {@code precipitation_probability} value, or empty when the response omits it
   */
  public OptionalDouble precipitationProbability() {
    return precipitationProbability == null
        ? OptionalDouble.empty()
        : OptionalDouble.of(precipitationProbability);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      snowfall,
      snowfallP10,
      snowfallP90,
      snowfallWindow,
      temperature,
      windGust,
      precipitationProbability
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "snowfall",
    "snowfallP10",
    "snowfallP90",
    "snowfallWindow",
    "temperature",
    "windGust",
    "precipitationProbability"
  };

  /** Builds a {@link SummaryValues}. */
  public static final class Builder {
    private @Nullable Double snowfall;
    private @Nullable Double snowfallP10;
    private @Nullable Double snowfallP90;
    private @Nullable String snowfallWindow;
    private @Nullable Double temperature;
    private @Nullable Double windGust;
    private @Nullable Double precipitationProbability;

    private Builder() {}

    /**
     * Sets {@link SummaryValues#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable Double value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link SummaryValues#snowfallP10()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfallP10(@Nullable Double value) {
      this.snowfallP10 = value;
      return this;
    }

    /**
     * Sets {@link SummaryValues#snowfallP90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfallP90(@Nullable Double value) {
      this.snowfallP90 = value;
      return this;
    }

    /**
     * Sets {@link SummaryValues#snowfallWindow()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfallWindow(@Nullable String value) {
      this.snowfallWindow = value;
      return this;
    }

    /**
     * Sets {@link SummaryValues#temperature()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder temperature(@Nullable Double value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link SummaryValues#windGust()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windGust(@Nullable Double value) {
      this.windGust = value;
      return this;
    }

    /**
     * Sets {@link SummaryValues#precipitationProbability()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitationProbability(@Nullable Double value) {
      this.precipitationProbability = value;
      return this;
    }

    /**
     * Builds the {@link SummaryValues}.
     *
     * @return the immutable SummaryValues
     * @throws IllegalStateException if a required value was not set
     */
    public SummaryValues build() {
      return new SummaryValues(this);
    }
  }
}
