// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One {@code ?totals=} term, across the members: a window total of an amount, or a window low /
 * high / peak.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code WindowTotal}.
 */
public final class WindowTotal extends Model {
  private final String term;
  private final String variable;
  private final int windowStartHours;
  private final int windowEndHours;
  private final String aggregation;
  private final @Nullable Instant from;
  private final @Nullable Instant to;
  private final @Nullable Quantiles quantiles;
  private final int members;
  private final @Nullable String nullReason;

  private WindowTotal(Builder b) {
    this.term = require(b.term, "term");
    this.variable = require(b.variable, "variable");
    this.windowStartHours = require(b.windowStartHours, "window_start_hours");
    this.windowEndHours = require(b.windowEndHours, "window_end_hours");
    this.aggregation = require(b.aggregation, "aggregation");
    this.from = b.from;
    this.to = b.to;
    this.quantiles = b.quantiles;
    this.members = require(b.members, "members");
    this.nullReason = b.nullReason;
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
    b.term = this.term;
    b.variable = this.variable;
    b.windowStartHours = this.windowStartHours;
    b.windowEndHours = this.windowEndHours;
    b.aggregation = this.aggregation;
    b.from = this.from;
    b.to = this.to;
    b.quantiles = this.quantiles;
    b.members = this.members;
    b.nullReason = this.nullReason;
    return b;
  }

  /**
   * The term's canonical spelling ({@code <N>h} for a window from 0, an amount's {@code total}
   * omitted, {@code min}/{@code max} spelled) -- the key to match the request term by.
   *
   * @return the {@code term} value
   */
  public String term() {
    return term;
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
   * What each member contributes -- {@code total} (its window total of {@code precipitation} /
   * {@code snowfall}), {@code min} or {@code max} (its lowest or highest {@code temperature}, or
   * its highest {@code wind_speed}, over the window's hours).
   *
   * @return the {@code aggregation} value ({@link TotalAggregation#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public TotalAggregation aggregation() {
    return TotalAggregation.fromWireValue(aggregation);
  }

  /**
   * The {@code aggregation} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code aggregation} string
   */
  public String aggregationAsString() {
    return aggregation;
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
   * The members' window totals (counted as the headline counts its windows: a straddling bucket by
   * its share inside) or window extremes, in the request's units at the variable's served
   * precision; only members covering the whole window count. {@code null} when the window is not
   * covered or fewer than two members cover it.
   *
   * @return the {@code quantiles} value, or empty when the response omits it
   */
  public Optional<Quantiles> quantiles() {
    return Optional.ofNullable(quantiles);
  }

  /**
   * The members counted: those covering the whole window (for a {@code wind_speed} {@code max},
   * those with a value at every hour of it).
   *
   * @return the {@code members} value
   */
  public int members() {
    return members;
  }

  /**
   * Why {@code quantiles} is {@code null}; {@code null} when it is not.
   *
   * @return the {@code null_reason} value, or empty when the response omits it ({@link
   *     NullReason#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<NullReason> nullReason() {
    return Optional.ofNullable(nullReason).map(NullReason::fromWireValue);
  }

  /**
   * The {@code null_reason} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code null_reason} string, or empty when absent
   */
  public Optional<String> nullReasonAsString() {
    return Optional.ofNullable(nullReason);
  }

  @Override
  Object[] values() {
    return new Object[] {
      term,
      variable,
      windowStartHours,
      windowEndHours,
      aggregation,
      from,
      to,
      quantiles,
      members,
      nullReason
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "term",
    "variable",
    "windowStartHours",
    "windowEndHours",
    "aggregation",
    "from",
    "to",
    "quantiles",
    "members",
    "nullReason"
  };

  /** Builds a {@link WindowTotal}. */
  public static final class Builder {
    private @Nullable String term;
    private @Nullable String variable;
    private @Nullable Integer windowStartHours;
    private @Nullable Integer windowEndHours;
    private @Nullable String aggregation;
    private @Nullable Instant from;
    private @Nullable Instant to;
    private @Nullable Quantiles quantiles;
    private @Nullable Integer members;
    private @Nullable String nullReason;

    private Builder() {}

    /**
     * Sets {@link WindowTotal#term()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder term(String value) {
      this.term = value;
      return this;
    }

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
     * Sets {@link WindowTotal#aggregation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder aggregation(TotalAggregation value) {
      this.aggregation = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link WindowTotal#aggregationAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder aggregationAsString(String value) {
      this.aggregation = value;
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
     * Sets {@link WindowTotal#nullReason()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder nullReason(@Nullable NullReason value) {
      this.nullReason = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link WindowTotal#nullReasonAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder nullReasonAsString(@Nullable String value) {
      this.nullReason = value;
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
