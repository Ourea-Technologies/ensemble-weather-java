// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import com.oureawx.ensembleweather.ProbabilityTerm;
import com.oureawx.ensembleweather.ThresholdUnit;
import com.oureawx.ensembleweather.TotalTerm;
import com.oureawx.ensembleweather.UsageException;
import com.oureawx.ensembleweather.Window;
import com.oureawx.ensembleweather.model.Aggregation;
import com.oureawx.ensembleweather.model.ProbabilityResult;
import com.oureawx.ensembleweather.model.UnitSystem;
import com.oureawx.ensembleweather.model.Variable;
import com.oureawx.ensembleweather.model.WindowTotal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The wire form of {@code prob=} and {@code totals=} (BEHAVIOR.md sections 4 and 5): threshold
 * conversion and server-identical rounding, canonical order, de-duplication, and mapping the
 * server's answers back to the caller's terms. No statistics happen here.
 */
public final class Terms {
  private Terms() {}

  /** One term in wire form, with the key its answer is matched by. */
  public static final class WireTerm {
    final String text;
    final String key;
    final Variable variable;
    final String op;
    final Window window;
    final int aggregationRank;
    final double threshold;

    WireTerm(
        String text,
        String key,
        Variable variable,
        String op,
        Window window,
        int aggregationRank,
        double threshold) {
      this.text = text;
      this.key = key;
      this.variable = variable;
      this.op = op;
      this.window = window;
      this.aggregationRank = aggregationRank;
      this.threshold = threshold;
    }
  }

  private static final Comparator<WireTerm> ORDER =
      Comparator.<WireTerm, String>comparing(t -> wire(t.variable))
          .thenComparing(t -> t.op)
          .thenComparingInt(t -> t.window.startHours())
          .thenComparingInt(t -> t.window.endHours())
          .thenComparingInt(t -> t.aggregationRank)
          .thenComparingDouble(t -> t.threshold);

  private static String wire(Variable v) {
    return v.wireValue().orElseThrow(() -> new UsageException("UNKNOWN is not a variable"));
  }

  /**
   * The decimals a variable is served at (BEHAVIOR.md section 4).
   *
   * @param variable the variable
   * @param units the request's units
   * @return the number of decimals
   */
  public static int decimals(Variable variable, UnitSystem units) {
    switch (variable) {
      case PRECIPITATION:
      case SNOWFALL:
        return units == UnitSystem.IMPERIAL ? 3 : 2;
      case TEMPERATURE:
      case WIND_SPEED:
        return 1;
      case SNOW_LEVEL:
        return 0;
      default:
        throw new UsageException("UNKNOWN is not a variable");
    }
  }

  /**
   * Converts a caller's threshold into the request's units, with the exact factors of BEHAVIOR.md
   * section 7.
   *
   * @param term the term
   * @param units the request's units
   * @return the threshold in the request's units
   */
  static double convert(ProbabilityTerm term, UnitSystem units) {
    double v = term.threshold().value();
    ThresholdUnit unit = term.threshold().unit().orElse(null);
    if (unit == null) {
      return v;
    }
    boolean imperial = units == UnitSystem.IMPERIAL;
    switch (term.variable()) {
      case PRECIPITATION:
      case SNOWFALL:
        if (unit == ThresholdUnit.MILLIMETRES) {
          return imperial ? v / 25.4 : v;
        }
        if (unit == ThresholdUnit.INCHES) {
          return imperial ? v : v * 25.4;
        }
        break;
      case SNOW_LEVEL:
        if (unit == ThresholdUnit.METRES) {
          return imperial ? v / 0.3048 : v;
        }
        if (unit == ThresholdUnit.FEET) {
          return imperial ? v : v * 0.3048;
        }
        break;
      case WIND_SPEED:
        if (unit == ThresholdUnit.METRES_PER_SECOND) {
          return imperial ? v / 0.44704 : v;
        }
        if (unit == ThresholdUnit.MILES_PER_HOUR) {
          return imperial ? v : v * 0.44704;
        }
        break;
      case TEMPERATURE:
        double celsius;
        if (unit == ThresholdUnit.CELSIUS) {
          celsius = v;
        } else if (unit == ThresholdUnit.FAHRENHEIT) {
          celsius = (v - 32) * 5 / 9;
        } else if (unit == ThresholdUnit.KELVIN) {
          celsius = v - 273.15;
        } else {
          break;
        }
        if (units == UnitSystem.IMPERIAL) {
          return unit == ThresholdUnit.FAHRENHEIT ? v : celsius * 9 / 5 + 32;
        }
        if (units == UnitSystem.KELVIN) {
          return unit == ThresholdUnit.KELVIN ? v : celsius + 273.15;
        }
        return celsius;
      default:
        break;
    }
    throw new UsageException("a " + unit + " threshold does not fit " + term.variable());
  }

  /**
   * Rounds as the server does and prints with exactly {@code d} decimals: {@code
   * round_half_away_from_zero(v * 10^d)} in doubles, {@code -0} as {@code 0}.
   *
   * @param value the threshold
   * @param decimals {@code d}
   * @return the threshold text
   */
  public static String round(double value, int decimals) {
    double scaled = value * Math.pow(10, decimals);
    double units = Math.signum(scaled) * Math.floor(Math.abs(scaled) + 0.5);
    if (Math.abs(units) >= 1e15) {
      throw new UsageException("threshold out of range: " + value);
    }
    long n = (long) units;
    String digits = Long.toString(Math.abs(n));
    if (decimals > 0) {
      while (digits.length() <= decimals) {
        digits = "0" + digits;
      }
      int point = digits.length() - decimals;
      digits = digits.substring(0, point) + "." + digits.substring(point);
    }
    return n < 0 ? "-" + digits : digits;
  }

  private static int rank(Aggregation a) {
    switch (a) {
      case TOTAL:
        return 0;
      case ANY:
        return 1;
      default:
        return 2;
    }
  }

  private static String key(
      String variable, String op, String threshold, int start, int end, String aggregation) {
    return variable + ":" + op + ":" + threshold + ":" + start + "-" + end + ":" + aggregation;
  }

  /**
   * One probability term in wire form.
   *
   * @param term the caller's term
   * @param units the request's units
   * @return the wire term
   */
  public static WireTerm probability(ProbabilityTerm term, UnitSystem units) {
    String variable = wire(term.variable());
    String op = term.op().wireValue().orElseThrow();
    String threshold = round(convert(term, units), decimals(term.variable(), units));
    Aggregation agg = term.effectiveAggregation();
    String aggWire = agg.wireValue().orElseThrow();
    boolean amount =
        term.variable() == Variable.PRECIPITATION || term.variable() == Variable.SNOWFALL;
    boolean isDefault = agg == (amount ? Aggregation.TOTAL : Aggregation.ANY);
    String text =
        variable
            + ":"
            + op
            + ":"
            + threshold
            + ":"
            + term.window()
            + (isDefault ? "" : ":" + aggWire);
    return new WireTerm(
        text,
        key(variable, op, threshold, term.window().startHours(), term.window().endHours(), aggWire),
        term.variable(),
        op,
        term.window(),
        rank(agg),
        Double.parseDouble(threshold));
  }

  /**
   * One window-total term in wire form.
   *
   * @param term the caller's term
   * @return the wire term
   */
  public static WireTerm total(TotalTerm term) {
    String variable = wire(term.variable());
    return new WireTerm(
        variable + ":" + term.window(),
        variable + ":" + term.window().startHours() + "-" + term.window().endHours(),
        term.variable(),
        "",
        term.window(),
        0,
        0);
  }

  /**
   * The canonical {@code prob=} or {@code totals=} value: de-duplicated, sorted, comma-joined.
   *
   * @param terms the wire terms, in the caller's order
   * @return the value, or empty when there are no terms
   */
  public static String join(List<WireTerm> terms) {
    Map<String, WireTerm> unique = new LinkedHashMap<>();
    for (WireTerm t : terms) {
      unique.putIfAbsent(t.text, t);
    }
    List<WireTerm> sorted = new ArrayList<>(unique.values());
    sorted.sort(ORDER);
    List<String> parts = new ArrayList<>();
    for (WireTerm t : sorted) {
      parts.add(t.text);
    }
    return String.join(",", parts);
  }

  /**
   * Maps the server's probability answers back to the caller's terms, in the caller's order
   * (duplicates share one answer).
   *
   * @param terms the wire terms, in the caller's order
   * @param results the server's answers, in canonical order
   * @param units the request's units
   * @return the answers, one per caller term
   * @throws MalformedResponseException if a term has no answer
   */
  public static List<ProbabilityResult> mapProbabilities(
      List<WireTerm> terms, List<ProbabilityResult> results, UnitSystem units) {
    Map<String, ProbabilityResult> byKey = new HashMap<>();
    for (ProbabilityResult r : results) {
      Variable v = r.variable();
      if (v == Variable.UNKNOWN) {
        continue;
      }
      byKey.put(
          key(
              r.variableAsString(),
              r.opAsString(),
              round(r.threshold(), decimals(v, units)),
              r.windowStartHours(),
              r.windowEndHours(),
              r.aggregationAsString()),
          r);
    }
    List<ProbabilityResult> out = new ArrayList<>();
    for (WireTerm t : terms) {
      ProbabilityResult r = byKey.get(t.key);
      if (r == null) {
        throw new MalformedResponseException(
            "the response's probabilities have no answer for prob term " + t.text);
      }
      out.add(r);
    }
    return out;
  }

  /**
   * Maps the server's window totals back to the caller's terms, in the caller's order.
   *
   * @param terms the wire terms, in the caller's order
   * @param results the server's answers
   * @return the answers, one per caller term
   * @throws MalformedResponseException if a term has no answer
   */
  public static List<WindowTotal> mapTotals(List<WireTerm> terms, List<WindowTotal> results) {
    Map<String, WindowTotal> byKey = new HashMap<>();
    for (WindowTotal r : results) {
      byKey.put(r.variableAsString() + ":" + r.windowStartHours() + "-" + r.windowEndHours(), r);
    }
    List<WindowTotal> out = new ArrayList<>();
    for (WireTerm t : terms) {
      WindowTotal r = byKey.get(t.key);
      if (r == null) {
        throw new MalformedResponseException(
            "the response's window_totals have no answer for totals term " + t.text);
      }
      out.add(Objects.requireNonNull(r));
    }
    return out;
  }
}
