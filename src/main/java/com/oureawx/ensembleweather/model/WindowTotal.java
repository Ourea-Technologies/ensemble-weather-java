// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One {@code ?totals=} window, across the members.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code WindowTotal}.
 */
public final class WindowTotal extends Model {
  private final String variable;
  private final int windowStartHours;
  private final int windowEndHours;
  private final @Nullable Instant from;
  private final @Nullable Instant to;
  private final @Nullable Quantiles quantiles;
  private final int members;

  private WindowTotal(Builder b) {
    this.variable = require(b.variable, "variable");
    this.windowStartHours = require(b.windowStartHours, "window_start_hours");
    this.windowEndHours = require(b.windowEndHours, "window_end_hours");
    this.from = b.from;
    this.to = b.to;
    this.quantiles = b.quantiles;
    this.members = require(b.members, "members");
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
    b.variable = this.variable;
    b.windowStartHours = this.windowStartHours;
    b.windowEndHours = this.windowEndHours;
    b.from = this.from;
    b.to = this.to;
    b.quantiles = this.quantiles;
    b.members = this.members;
    return b;
  }

  /**
   * The {@code variable} value.
   *
   * @return the {@code variable} value ({@link Variable#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public Variable variable() {
    return Variable.fromWireValue(variable);
  }

  /**
   * The {@code variable} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code variable} string
   */
  public String variableAsString() {
    return variable;
  }

  /**
   * The {@code window_start_hours} value.
   *
   * @return the {@code window_start_hours} value
   */
  public int windowStartHours() {
    return windowStartHours;
  }

  /**
   * The {@code window_end_hours} value.
   *
   * @return the {@code window_end_hours} value
   */
  public int windowEndHours() {
    return windowEndHours;
  }

  /**
   * The {@code from} value.
   *
   * @return the {@code from} value, or empty when the response omits it
   */
  public Optional<Instant> from() {
    return Optional.ofNullable(from);
  }

  /**
   * The {@code to} value.
   *
   * @return the {@code to} value, or empty when the response omits it
   */
  public Optional<Instant> to() {
    return Optional.ofNullable(to);
  }

  /**
   * The members' window totals, counted as the headline counts its windows (a straddling bucket by
   * its share inside; only members covering the whole window); {@code null} when the window is not
   * covered or fewer than two members cover it.
   *
   * @return the {@code quantiles} value, or empty when the response omits it
   */
  public Optional<Quantiles> quantiles() {
    return Optional.ofNullable(quantiles);
  }

  /**
   * The {@code members} value.
   *
   * @return the {@code members} value
   */
  public int members() {
    return members;
  }

  @Override
  Object[] values() {
    return new Object[] {variable, windowStartHours, windowEndHours, from, to, quantiles, members};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "variable", "windowStartHours", "windowEndHours", "from", "to", "quantiles", "members"
  };

  /** Builds a {@link WindowTotal}. */
  public static final class Builder {
    private @Nullable String variable;
    private @Nullable Integer windowStartHours;
    private @Nullable Integer windowEndHours;
    private @Nullable Instant from;
    private @Nullable Instant to;
    private @Nullable Quantiles quantiles;
    private @Nullable Integer members;

    private Builder() {}

    /**
     * Sets {@link WindowTotal#variable()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder variable(Variable value) {
      this.variable = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link WindowTotal#variableAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder variableAsString(String value) {
      this.variable = value;
      return this;
    }

    /**
     * Sets {@link WindowTotal#windowStartHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windowStartHours(int value) {
      this.windowStartHours = value;
      return this;
    }

    /**
     * Sets {@link WindowTotal#windowEndHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windowEndHours(int value) {
      this.windowEndHours = value;
      return this;
    }

    /**
     * Sets {@link WindowTotal#from()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder from(@Nullable Instant value) {
      this.from = value;
      return this;
    }

    /**
     * Sets {@link WindowTotal#to()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder to(@Nullable Instant value) {
      this.to = value;
      return this;
    }

    /**
     * Sets {@link WindowTotal#quantiles()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder quantiles(@Nullable Quantiles value) {
      this.quantiles = value;
      return this;
    }

    /**
     * Sets {@link WindowTotal#members()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder members(int value) {
      this.members = value;
      return this;
    }

    /**
     * Builds the {@link WindowTotal}.
     *
     * @return the immutable WindowTotal
     * @throws IllegalStateException if a required value was not set
     */
    public WindowTotal build() {
      return new WindowTotal(this);
    }
  }
}
