// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.oureawx.ensembleweather.ProbabilityTerm;
import com.oureawx.ensembleweather.Threshold;
import com.oureawx.ensembleweather.model.UnitSystem;
import com.oureawx.ensembleweather.model.Variable;
import org.junit.jupiter.api.Test;

class QueryAndTermsTest {
  @Test
  void percentEncodingIsRfc3986() {
    assertEquals("a%20b%2B%26%3D%3A%2C~-._", Query.encode("a b+&=:,~-._"));
    assertEquals("%C3%B1%E2%82%AC", Query.encode("ñ€"));
  }

  @Test
  void numbersAreShortestPlainDecimals() {
    assertEquals("1661", Query.plain(1661.0));
    assertEquals("45.3311", Query.plain(45.3311));
    assertEquals("-121.6649", Query.plain(-121.6649));
    assertEquals("0", Query.plain(-0.0));
    assertEquals("0.0000001", Query.plain(1e-7));
    assertEquals("100000000000000000000", Query.plain(1e20));
    assertEquals("0.1", Query.plain(0.1));
  }

  @Test
  void parametersAreSortedAndDefaultsOmitted() {
    String q =
        new Query()
            .bool("nowcast", true, true)
            .integer("max_hours", 24L, 24L)
            .string("units", "imperial", "metric")
            .bool("include_past", true, false)
            .literal("prob", "snowfall:ge:6.000:24h,temperature:le:32.0:12h")
            .build();
    assertEquals(
        "include_past=true&prob=snowfall:ge:6.000:24h,temperature:le:32.0:12h&units=imperial", q);
  }

  @Test
  void roundingIsHalfAwayFromZeroInDoubles() {
    assertEquals("0.063", Terms.round(0.0625, 3));
    assertEquals("1.00", Terms.round(1.005, 2));
    assertEquals("-0.1", Terms.round(-0.05, 1));
    assertEquals("0.0", Terms.round(-0.04, 1));
    assertEquals("-3", Terms.round(-2.5, 0));
    assertEquals("3", Terms.round(2.5, 0));
    assertEquals("152.40", Terms.round(6 * 25.4, 2));
  }

  @Test
  void thresholdsConvertExactly() {
    assertEquals(
        "temperature:ge:273.1:24h",
        Terms.probability(
                ProbabilityTerm.atLeast(Variable.TEMPERATURE, Threshold.celsius(-0.05)),
                UnitSystem.KELVIN)
            .text);
    assertEquals(
        "temperature:le:32.0:24h",
        Terms.probability(
                ProbabilityTerm.atMost(Variable.TEMPERATURE, Threshold.celsius(0)),
                UnitSystem.IMPERIAL)
            .text);
    assertEquals(
        "snow_level:le:5000:24h",
        Terms.probability(
                ProbabilityTerm.atMost(Variable.SNOW_LEVEL, Threshold.metres(1524)),
                UnitSystem.IMPERIAL)
            .text);
    assertEquals(
        "wind_speed:ge:6.7:24h",
        Terms.probability(
                ProbabilityTerm.atLeast(Variable.WIND_SPEED, Threshold.milesPerHour(15)),
                UnitSystem.METRIC)
            .text);
    assertEquals(
        "precipitation:ge:1.000:24h",
        Terms.probability(
                ProbabilityTerm.atLeast(Variable.PRECIPITATION, Threshold.millimetres(25.4)),
                UnitSystem.IMPERIAL)
            .text);
  }
}
