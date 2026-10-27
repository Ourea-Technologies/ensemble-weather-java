// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.model.Aggregation;
import com.oureawx.ensembleweather.model.Operator;
import com.oureawx.ensembleweather.model.Variable;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One probability to ask the server for, sent as a {@code prob=} term: the chance that a variable
 * is at least (or at most) a threshold over a window. The server computes it from the full
 * ensemble; the SDK never derives a probability itself.
 *
 * <pre>{@code
 * ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(6))              // next 24 h
 * ProbabilityTerm.atMost(Variable.TEMPERATURE, Threshold.fahrenheit(32))
 *     .over(Window.between(6, 18))
 *     .withAggregation(Aggregation.ALL)                                        // every hour
 * }</pre>
 *
 * <p>Precipitation and snowfall are read as the window's {@link Aggregation#TOTAL total} (the only
 * aggregation they allow); the other variables at {@link Aggregation#ANY any} hour by default, or
 * {@link Aggregation#ALL all} hours.
 */
public final class ProbabilityTerm {
  private final Variable variable;
  private final Operator op;
  private final Threshold threshold;
  private final Window window;
  private final @Nullable Aggregation aggregation;

  private ProbabilityTerm(
      Variable variable,
      Operator op,
      Threshold threshold,
      Window window,
      @Nullable Aggregation aggregation) {
    if (variable == Variable.UNKNOWN) {
      throw new UsageException("a probability term needs a known variable, not UNKNOWN");
    }
    this.variable = variable;
    this.op = op;
    this.threshold = Objects.requireNonNull(threshold, "threshold");
    this.window = Objects.requireNonNull(window, "window");
    if (aggregation != null) {
      checkAggregation(variable, aggregation);
    }
    this.aggregation = aggregation;
  }

  private static void checkAggregation(Variable variable, Aggregation aggregation) {
    boolean amount = isAmount(variable);
    if (aggregation == Aggregation.UNKNOWN
        || (amount && aggregation != Aggregation.TOTAL)
        || (!amount && aggregation == Aggregation.TOTAL)) {
      throw new UsageException(
          "aggregation "
              + aggregation
              + " is not allowed for "
              + variable
              + ": precipitation and snowfall take TOTAL only; the other variables ANY or ALL");
    }
  }

  static boolean isAmount(Variable variable) {
    return variable == Variable.PRECIPITATION || variable == Variable.SNOWFALL;
  }

  /**
   * The chance that {@code variable} is at least {@code threshold} over the next 24 hours.
   *
   * @param variable the variable
   * @param threshold the threshold
   * @return the term
   */
  public static ProbabilityTerm atLeast(Variable variable, Threshold threshold) {
    return new ProbabilityTerm(
        Objects.requireNonNull(variable, "variable"),
        Operator.GE,
        threshold,
        Window.hours(24),
        null);
  }

  /**
   * The chance that {@code variable} is at most {@code threshold} over the next 24 hours.
   *
   * @param variable the variable
   * @param threshold the threshold
   * @return the term
   */
  public static ProbabilityTerm atMost(Variable variable, Threshold threshold) {
    return new ProbabilityTerm(
        Objects.requireNonNull(variable, "variable"),
        Operator.LE,
        threshold,
        Window.hours(24),
        null);
  }

  /**
   * This term over another window.
   *
   * @param window the window
   * @return a new term
   */
  public ProbabilityTerm over(Window window) {
    return new ProbabilityTerm(variable, op, threshold, window, aggregation);
  }

  /**
   * This term with an explicit aggregation.
   *
   * @param aggregation {@code TOTAL} for precipitation and snowfall; {@code ANY} or {@code ALL}
   *     otherwise
   * @return a new term
   * @throws UsageException if the aggregation does not fit the variable
   */
  public ProbabilityTerm withAggregation(Aggregation aggregation) {
    return new ProbabilityTerm(
        variable, op, threshold, window, Objects.requireNonNull(aggregation, "aggregation"));
  }

  /**
   * The variable.
   *
   * @return the variable
   */
  public Variable variable() {
    return variable;
  }

  /**
   * At least ({@link Operator#GE}) or at most ({@link Operator#LE}).
   *
   * @return the operator
   */
  public Operator op() {
    return op;
  }

  /**
   * The threshold, as given.
   *
   * @return the threshold
   */
  public Threshold threshold() {
    return threshold;
  }

  /**
   * The window.
   *
   * @return the window
   */
  public Window window() {
    return window;
  }

  /**
   * The aggregation as given.
   *
   * @return the aggregation, or empty for the variable's default
   */
  public Optional<Aggregation> aggregation() {
    return Optional.ofNullable(aggregation);
  }

  /**
   * The aggregation the server applies: the explicit one, or the variable's default.
   *
   * @return {@code TOTAL} for precipitation and snowfall, else the given one or {@code ANY}
   */
  public Aggregation effectiveAggregation() {
    if (aggregation != null) {
      return aggregation;
    }
    return isAmount(variable) ? Aggregation.TOTAL : Aggregation.ANY;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (!(o instanceof ProbabilityTerm)) {
      return false;
    }
    ProbabilityTerm t = (ProbabilityTerm) o;
    return t.variable == variable
        && t.op == op
        && t.threshold.equals(threshold)
        && t.window.equals(window)
        && t.effectiveAggregation() == effectiveAggregation();
  }

  @Override
  public int hashCode() {
    return Objects.hash(variable, op, threshold, window, effectiveAggregation());
  }

  @Override
  public String toString() {
    return "ProbabilityTerm["
        + variable
        + " "
        + op
        + " "
        + threshold
        + " over "
        + window
        + ", "
        + effectiveAggregation()
        + "]";
  }
}
