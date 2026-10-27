// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.model.Variable;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * One window total to ask the server for, sent as a {@code totals=} term: the distribution of each
 * ensemble member's own precipitation or snowfall total over a window. This is the only storm total
 * the SDK offers: summing hourly quantiles would give a wrong total, so the server computes it from
 * the members.
 *
 * <pre>{@code
 * TotalTerm.of(Variable.SNOWFALL, Window.hours(72))
 * }</pre>
 */
public final class TotalTerm {
  private final Variable variable;
  private final Window window;

  private TotalTerm(Variable variable, Window window) {
    if (!ProbabilityTerm.isAmount(variable)) {
      throw new UsageException(
          "window totals are served for PRECIPITATION and SNOWFALL only, not " + variable);
    }
    this.variable = variable;
    this.window = Objects.requireNonNull(window, "window");
  }

  /**
   * The total of {@code variable} over {@code window}.
   *
   * @param variable {@code PRECIPITATION} or {@code SNOWFALL}
   * @param window the window
   * @return the term
   */
  public static TotalTerm of(Variable variable, Window window) {
    return new TotalTerm(Objects.requireNonNull(variable, "variable"), window);
  }

  /**
   * The total of {@code variable} over the next 24 hours.
   *
   * @param variable {@code PRECIPITATION} or {@code SNOWFALL}
   * @return the term
   */
  public static TotalTerm of(Variable variable) {
    return of(variable, Window.hours(24));
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
   * The window.
   *
   * @return the window
   */
  public Window window() {
    return window;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof TotalTerm
        && ((TotalTerm) o).variable == variable
        && ((TotalTerm) o).window.equals(window);
  }

  @Override
  public int hashCode() {
    return Objects.hash(variable, window);
  }

  @Override
  public String toString() {
    return "TotalTerm[" + variable + " over " + window + "]";
  }
}
