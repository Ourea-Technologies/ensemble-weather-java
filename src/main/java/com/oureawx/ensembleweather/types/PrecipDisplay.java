// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A day's precipitation headline, chosen by the server so every client says the same thing: {@code
 * kind} {@code none} when the chance of a wet day ({@code precipitation_occurrence_probability}) is
 * under 15 %, else the day's {@code precipitation_type}; {@code chance} that chance; {@code amount}
 * the median amount if wet ({@code total_snowfall_if_wet.p50} for snow, {@code
 * total_precipitation_if_wet.p50} otherwise).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PrecipDisplay}.
 */
public final class PrecipDisplay extends Model {
  private final String kind;
  private final double chance;
  private final @Nullable Double amount;

  private final @Nullable Double snowAmount;

  private PrecipDisplay(Builder b) {
    this.kind = require(b.kind, "kind");
    this.chance = require(b.chance, "chance");
    this.amount = b.amount;
    this.snowAmount = b.snowAmount;
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
    b.kind = this.kind;
    b.chance = this.chance;
    b.amount = this.amount;
    b.snowAmount = this.snowAmount;
    return b;
  }

  /**
   * The {@code kind} value.
   *
   * @return the {@code kind} value ({@link PrecipDisplayKind#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public PrecipDisplayKind kind() {
    return PrecipDisplayKind.fromWireValue(kind);
  }

  /**
   * The {@code kind} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code kind} string
   */
  public String kindAsString() {
    return kind;
  }

  /**
   * The {@code chance} value.
   *
   * @return the {@code chance} value
   */
  public double chance() {
    return chance;
  }

  /**
   * The {@code amount} value.
   *
   * @return the {@code amount} value, or empty when the response omits it
   */
  public OptionalDouble amount() {
    return amount == null ? OptionalDouble.empty() : OptionalDouble.of(amount);
  }

  /**
   * The day's mean snowfall, the served {@code total_snowfall.mean}, in the request's snow units.
   * Present on every day that serves snowfall (that mean above 0), whatever its {@code kind} and
   * whatever its chance: a day with {@code kind} {@code none} (under a 10 % chance of a wet day)
   * still carries the small mean its few wet members leave, which may round to 0 at the served
   * precision. On a {@code rain_snow_mix} day it is the snow beside the liquid {@code amount}, and
   * on a {@code snow} day it equals {@code amount}. Absent on a day with no snowfall, never null.
   * Its range is {@code total_snowfall.p10} to {@code total_snowfall.p90}. That field is still
   * served; no headline reads it.
   *
   * @return the {@code snow_amount} value, or empty when the response omits it
   */
  public OptionalDouble snowAmount() {
    return snowAmount == null ? OptionalDouble.empty() : OptionalDouble.of(snowAmount);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {kind, chance, amount, snowAmount};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"kind", "chance", "amount", "snowAmount"};

  /** Builds a {@link PrecipDisplay}. */
  public static final class Builder {
    private @Nullable String kind;
    private @Nullable Double chance;
    private @Nullable Double amount;

    private @Nullable Double snowAmount;

    private Builder() {}

    /**
     * Sets {@link PrecipDisplay#kind()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder kind(PrecipDisplayKind value) {
      this.kind = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link PrecipDisplay#kindAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder kindAsString(String value) {
      this.kind = value;
      return this;
    }

    /**
     * Sets {@link PrecipDisplay#chance()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder chance(double value) {
      this.chance = value;
      return this;
    }

    /**
     * Sets {@link PrecipDisplay#amount()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder amount(@Nullable Double value) {
      this.amount = value;
      return this;
    }

    /**
     * Sets {@link PrecipDisplay#snowAmount()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowAmount(@Nullable Double value) {
      this.snowAmount = value;
      return this;
    }

    /**
     * Builds the {@link PrecipDisplay}.
     *
     * @return the immutable PrecipDisplay
     * @throws IllegalStateException if a required value was not set
     */
    public PrecipDisplay build() {
      return new PrecipDisplay(this);
    }
  }
}
