// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * A SeasonTotal from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SeasonTotal}.
 */
public final class SeasonTotal extends Model {
  private final double amount;
  private final String unit;
  private final String source;
  private final int daysCovered;
  private final int daysMissing;
  private final int provisionalDays;
  private final @Nullable Double normalToDate;
  private final @Nullable Integer percentOfNormal;
  private final @Nullable String normalSource;

  private SeasonTotal(Builder b) {
    this.amount = require(b.amount, "total");
    this.unit = require(b.unit, "unit");
    this.source = require(b.source, "source");
    this.daysCovered = require(b.daysCovered, "days_covered");
    this.daysMissing = require(b.daysMissing, "days_missing");
    this.provisionalDays = require(b.provisionalDays, "provisional_days");
    this.normalToDate = b.normalToDate;
    this.percentOfNormal = b.percentOfNormal;
    this.normalSource = b.normalSource;
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
    b.amount = this.amount;
    b.unit = this.unit;
    b.source = this.source;
    b.daysCovered = this.daysCovered;
    b.daysMissing = this.daysMissing;
    b.provisionalDays = this.provisionalDays;
    b.normalToDate = this.normalToDate;
    b.percentOfNormal = this.percentOfNormal;
    b.normalSource = this.normalSource;
    return b;
  }

  /**
   * (JSON {@code total}) Sum over the covered days, in {@code unit}.
   *
   * @return the {@code total} value
   */
  public double amount() {
    return amount;
  }

  /**
   * The {@code unit} value. One of {@code mm}, {@code cm}, {@code in}; read it as a string, since
   * the API may add values.
   *
   * @return the {@code unit} value
   */
  public String unit() {
    return unit;
  }

  /**
   * {@code mrms_multisensor}: the gridded analysis at the location; {@code station_gauge}: the
   * location is a gauge site and this is the gauge's accumulator. One of {@code mrms_multisensor},
   * {@code nohrsc_nsa}, {@code station_gauge}; read it as a string, since the API may add values.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * The {@code days_covered} value.
   *
   * @return the {@code days_covered} value
   */
  public int daysCovered() {
    return daysCovered;
  }

  /**
   * Days since {@code starts} with no complete observation; not counted as dry.
   *
   * @return the {@code days_missing} value
   */
  public int daysMissing() {
    return daysMissing;
  }

  /**
   * Covered days still inside the 7-day restate window.
   *
   * @return the {@code provisional_days} value
   */
  public int provisionalDays() {
    return provisionalDays;
  }

  /**
   * PRISM 1991-2020 over the covered days, in {@code unit}; null where no normal is loaded.
   *
   * @return the {@code normal_to_date} value, or empty when the response omits it
   */
  public OptionalDouble normalToDate() {
    return normalToDate == null ? OptionalDouble.empty() : OptionalDouble.of(normalToDate);
  }

  /**
   * The {@code percent_of_normal} value.
   *
   * @return the {@code percent_of_normal} value, or empty when the response omits it
   */
  public OptionalInt percentOfNormal() {
    return percentOfNormal == null ? OptionalInt.empty() : OptionalInt.of(percentOfNormal);
  }

  /**
   * The {@code normal_source} value. Always {@code prism_1991_2020} today.
   *
   * @return the {@code normal_source} value, or empty when the response omits it
   */
  public Optional<String> normalSource() {
    return Optional.ofNullable(normalSource);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      amount,
      unit,
      source,
      daysCovered,
      daysMissing,
      provisionalDays,
      normalToDate,
      percentOfNormal,
      normalSource
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "amount",
    "unit",
    "source",
    "daysCovered",
    "daysMissing",
    "provisionalDays",
    "normalToDate",
    "percentOfNormal",
    "normalSource"
  };

  /** Builds a {@link SeasonTotal}. */
  public static final class Builder {
    private @Nullable Double amount;
    private @Nullable String unit;
    private @Nullable String source;
    private @Nullable Integer daysCovered;
    private @Nullable Integer daysMissing;
    private @Nullable Integer provisionalDays;
    private @Nullable Double normalToDate;
    private @Nullable Integer percentOfNormal;
    private @Nullable String normalSource;

    private Builder() {}

    /**
     * Sets {@link SeasonTotal#amount()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder amount(double value) {
      this.amount = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#unit()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder unit(String value) {
      this.unit = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#daysCovered()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder daysCovered(int value) {
      this.daysCovered = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#daysMissing()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder daysMissing(int value) {
      this.daysMissing = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#provisionalDays()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder provisionalDays(int value) {
      this.provisionalDays = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#normalToDate()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder normalToDate(@Nullable Double value) {
      this.normalToDate = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#percentOfNormal()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder percentOfNormal(@Nullable Integer value) {
      this.percentOfNormal = value;
      return this;
    }

    /**
     * Sets {@link SeasonTotal#normalSource()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder normalSource(@Nullable String value) {
      this.normalSource = value;
      return this;
    }

    /**
     * Builds the {@link SeasonTotal}.
     *
     * @return the immutable SeasonTotal
     * @throws IllegalStateException if a required value was not set
     */
    public SeasonTotal build() {
      return new SeasonTotal(this);
    }
  }
}
