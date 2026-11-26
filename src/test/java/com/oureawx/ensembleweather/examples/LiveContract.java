// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.examples;

import com.oureawx.ensembleweather.CatalogLocationQuery;
import com.oureawx.ensembleweather.Client;
import com.oureawx.ensembleweather.ExploreOptions;
import com.oureawx.ensembleweather.ForecastOptions;
import com.oureawx.ensembleweather.LocationId;
import com.oureawx.ensembleweather.PermissionException;
import com.oureawx.ensembleweather.ProbabilityTerm;
import com.oureawx.ensembleweather.SearchOptions;
import com.oureawx.ensembleweather.Threshold;
import com.oureawx.ensembleweather.TotalTerm;
import com.oureawx.ensembleweather.Window;
import com.oureawx.ensembleweather.types.ExploreMetric;
import com.oureawx.ensembleweather.types.ExploreResponse;
import com.oureawx.ensembleweather.types.ForecastResponse;
import com.oureawx.ensembleweather.types.Variable;

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
    Client client = Client.builder().build();
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
    check(!f.series().timeseries().isEmpty(), "hourly series served");
    check(f.location().elevation() > 0, "forecast location carries its elevation");

    ForecastResponse plume =
        client.getForecast(meadows, ForecastOptions.builder().membersPlume().maxHours(12).build());
    check(plume.series().plume().isPresent(), "members=plume serves the plume view");

    check(client.searchLocations("hood meadows").count() >= 1, "search");
    check(
        !client.listCatalogLocations(CatalogLocationQuery.all()).items().isEmpty(), "catalog page");
    check(!client.getAttribution().providers().isEmpty(), "attribution");
    check(
        client.getCurrent(meadows).location().slug().orElse("").equals("mt-hood-meadows"),
        "current");
    check(client.getAlerts(meadows).count() >= 0, "alerts");
    check(client.getNearby(meadows).count() >= 0, "nearby");
    check(client.getRuns().count() >= 0, "runs");

    ExploreResponse top =
        client.explore(
            ExploreMetric.SNOW_5D, ExploreOptions.builder().state("or").limit(3).build());
    check(top.metricAsString().equals("snow_5d") && top.items().size() <= 3, "explore");
    check(
        client
                .searchLocations(
                    "hood",
                    SearchOptions.builder()
                        .sort(ExploreMetric.SNOW_5D)
                        .near(45.33, -121.66)
                        .build())
                .count()
            >= 0,
        "search sorted near a point");
    check(client.getComposition().modelCount() > 0, "composition (keyless)");
    check(client.getSeason(meadows).waterYear() >= 2027, "season");
    check(client.getSnowpack(meadows).beta(), "snowpack (beta)");
    check(client.getSnowpackPlume(meadows).beta(), "snowpack plume (beta)");
    check(client.getAlertOutlines().type().equals("FeatureCollection"), "alert outlines");
    check(client.getAvalancheZones().type().equals("FeatureCollection"), "avalanche zones");
    if (client.hasApiKey()) {
      try {
        check(!client.getSnowTrend(meadows).grids().isEmpty(), "snow trend (Plus or Pro)");
      } catch (PermissionException e) {
        check("PLUS_REQUIRED".equals(e.code().orElse("")), "snow trend refused below Plus");
      }
    } else {
      try {
        client.getSnowTrend(meadows);
        check(false, "snow trend is refused anonymously");
      } catch (PermissionException e) {
        check("PLUS_REQUIRED".equals(e.code().orElse("")), "snow trend refused anonymously");
      }
    }

    ForecastResponse again = client.getForecast(meadows);
    System.out.println(
        "info revalidated=" + again.metadata().revalidated() + ", " + client.lastRateLimit());
    System.out.println("live contract passed");
  }
}
