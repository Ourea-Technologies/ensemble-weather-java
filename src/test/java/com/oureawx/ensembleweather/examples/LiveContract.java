// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.examples;

import com.oureawx.ensembleweather.CatalogLocationQuery;
import com.oureawx.ensembleweather.EnsembleWeatherClient;
import com.oureawx.ensembleweather.ForecastOptions;
import com.oureawx.ensembleweather.LocationId;
import com.oureawx.ensembleweather.ProbabilityTerm;
import com.oureawx.ensembleweather.Threshold;
import com.oureawx.ensembleweather.TotalTerm;
import com.oureawx.ensembleweather.Window;
import com.oureawx.ensembleweather.model.ForecastResponse;
import com.oureawx.ensembleweather.model.Variable;

/**
 * The nightly contract run against the live API ({@code live.yml}), with the read-only CI key from
 * {@code OUREA_API_KEY}. Read-only calls only; it never runs on pull requests. Run it by hand with
 * {@code ./gradlew liveContract}.
 */
public final class LiveContract {
  private LiveContract() {}

  private static void check(boolean ok, String what) {
    if (!ok) {
      throw new AssertionError("contract: " + what);
    }
    System.out.println("ok   " + what);
  }

  /**
   * Runs the contract.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    EnsembleWeatherClient client = EnsembleWeatherClient.builder().build();
    LocationId meadows = LocationId.of("or", "mt-hood-meadows");

    ForecastResponse f =
        client.getForecast(
            meadows,
            ForecastOptions.builder()
                .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(6)))
                .probability(
                    ProbabilityTerm.atMost(Variable.TEMPERATURE, Threshold.fahrenheit(32))
                        .over(Window.hours(48)))
                .total(TotalTerm.of(Variable.SNOWFALL, Window.hours(72)))
                .build());
    check(
        f.location().slug().orElse("").equals("mt-hood-meadows"), "forecast for a named location");
    check(f.probabilities().size() == 2, "prob terms answered in the caller's order");
    check(
        f.probabilities().get(0).variable() == Variable.SNOWFALL
            && f.probabilities().get(1).variable() == Variable.TEMPERATURE,
        "prob answers mapped back to their terms");
    check(f.windowTotals().size() == 1, "window total answered");
    check(!f.forecast().timeseries().isEmpty(), "hourly series served");

    check(client.searchLocations("hood meadows").count() >= 1, "search");
    check(
        !client.listCatalogLocations(CatalogLocationQuery.all()).items().isEmpty(), "catalog page");
    check(!client.getAttribution().providers().isEmpty(), "attribution");
    check(
        client.getCurrent(meadows).location().slug().orElse("").equals("mt-hood-meadows"),
        "current");
    check(client.getAlerts(meadows).alertCount() >= 0, "alerts");
    check(client.getNearby(meadows).count() >= 0, "nearby");
    check(client.getRuns().count() >= 0, "runs");

    ForecastResponse again = client.getForecast(meadows);
    System.out.println(
        "info revalidated=" + again.metadata().revalidated() + ", " + client.lastRateLimit());
    System.out.println("live contract passed");
  }
}
