// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A day's precipitation headline, chosen by the server so every client says the same thing: {@code
 * kind} {@code none} when the chance of a wet day ({@code precip_occurrence_probability}) is under
 * 15 %, else the day's {@code precip_type}; {@code chance} that chance; {@code amount} the median
 * amount if wet ({@code total_snowfall_if_wet.p50} for snow, {@code total_precip_if_wet.p50}
 * otherwise).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PrecipDisplay}.
 */
public final class PrecipDisplay extends Model {
  private final String kind;
  private final double chance;
  private final @Nullable Double amount;

  private PrecipDisplay(Builder b) {
    this.kind = require(b.kind, "kind");
    this.chance = require(b.chance, "chance");
    this.amount = b.amount;
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

  @Override
  Object[] values() {
    return new Object[] {kind, chance, amount};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"kind", "chance", "amount"};

  /** Builds a {@link PrecipDisplay}. */
  public static final class Builder {
    private @Nullable String kind;
    private @Nullable Double chance;
    private @Nullable Double amount;

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
