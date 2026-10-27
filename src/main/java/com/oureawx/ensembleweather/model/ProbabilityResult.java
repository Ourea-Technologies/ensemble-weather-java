// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * One {@code ?prob=} term, answered.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ProbabilityResult}.
 */
public final class ProbabilityResult extends Model {
  private final String variable;
  private final String op;
  private final double threshold;
  private final int windowStartHours;
  private final int windowEndHours;
  private final String aggregation;
  private final @Nullable Instant from;
  private final @Nullable Instant to;
  private final @Nullable Double probability;
  private final int members;

  private ProbabilityResult(Builder b) {
    this.variable = require(b.variable, "variable");
    this.op = require(b.op, "op");
    this.threshold = require(b.threshold, "threshold");
    this.windowStartHours = require(b.windowStartHours, "window_start_hours");
    this.windowEndHours = require(b.windowEndHours, "window_end_hours");
    this.aggregation = require(b.aggregation, "aggregation");
    this.from = b.from;
    this.to = b.to;
    this.probability = b.probability;
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
    b.op = this.op;
    b.threshold = this.threshold;
    b.windowStartHours = this.windowStartHours;
    b.windowEndHours = this.windowEndHours;
    b.aggregation = this.aggregation;
    b.from = this.from;
    b.to = this.to;
    b.probability = this.probability;
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
   * The {@code op} value.
   *
   * @return the {@code op} value ({@link Operator#UNKNOWN} for a value this SDK version does not
   *     know)
   */
  public Operator op() {
    return Operator.fromWireValue(op);
  }

  /**
   * The {@code op} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code op} string
   */
  public String opAsString() {
    return op;
  }

  /**
   * In the request's units, at the variable's served precision.
   *
   * @return the {@code threshold} value
   */
  public double threshold() {
    return threshold;
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
   * The {@code aggregation} value.
   *
   * @return the {@code aggregation} value ({@link Aggregation#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public Aggregation aggregation() {
    return Aggregation.fromWireValue(aggregation);
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
   * The window's first instant.
   *
   * @return the {@code from} value, or empty when the response omits it
   */
  public Optional<Instant> from() {
    return Optional.ofNullable(from);
  }

  /**
   * The window's end (exclusive).
   *
   * @return the {@code to} value, or empty when the response omits it
   */
  public Optional<Instant> to() {
    return Optional.ofNullable(to);
  }

  /**
   * The share of members meeting the term, 0..1 (0.001); {@code null} when the served series does
   * not reach the window's end or carries no members ({@code hourly=false}).
   *
   * @return the {@code probability} value, or empty when the response omits it
   */
  public OptionalDouble probability() {
    return probability == null ? OptionalDouble.empty() : OptionalDouble.of(probability);
  }

  /**
   * Members that voted: for {@code total}, the members whose values cover the whole window; for
   * {@code any}/{@code all}, those with a value in it.
   *
   * @return the {@code members} value
   */
  public int members() {
    return members;
  }

  @Override
  Object[] values() {
    return new Object[] {
      variable,
      op,
      threshold,
      windowStartHours,
      windowEndHours,
      aggregation,
      from,
      to,
      probability,
      members
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "variable",
    "op",
    "threshold",
    "windowStartHours",
    "windowEndHours",
    "aggregation",
    "from",
    "to",
    "probability",
    "members"
  };

  /** Builds a {@link ProbabilityResult}. */
  public static final class Builder {
    private @Nullable String variable;
    private @Nullable String op;
    private @Nullable Double threshold;
    private @Nullable Integer windowStartHours;
    private @Nullable Integer windowEndHours;
    private @Nullable String aggregation;
    private @Nullable Instant from;
    private @Nullable Instant to;
    private @Nullable Double probability;
    private @Nullable Integer members;

    private Builder() {}

    /**
     * Sets {@link ProbabilityResult#variable()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder variable(Variable value) {
      this.variable = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#variableAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder variableAsString(String value) {
      this.variable = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#op()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder op(Operator value) {
      this.op = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#opAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder opAsString(String value) {
      this.op = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#threshold()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder threshold(double value) {
      this.threshold = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#windowStartHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windowStartHours(int value) {
      this.windowStartHours = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#windowEndHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder windowEndHours(int value) {
      this.windowEndHours = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#aggregation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder aggregation(Aggregation value) {
      this.aggregation = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#aggregationAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder aggregationAsString(String value) {
      this.aggregation = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#from()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder from(@Nullable Instant value) {
      this.from = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#to()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder to(@Nullable Instant value) {
      this.to = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#probability()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder probability(@Nullable Double value) {
      this.probability = value;
      return this;
    }

    /**
     * Sets {@link ProbabilityResult#members()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder members(int value) {
      this.members = value;
      return this;
    }

    /**
     * Builds the {@link ProbabilityResult}.
     *
     * @return the immutable ProbabilityResult
     * @throws IllegalStateException if a required value was not set
     */
    public ProbabilityResult build() {
      return new ProbabilityResult(this);
    }
  }
}
