# ensemble-weather

Official Java SDK for the [Ourea](https://oureawx.com) probabilistic weather API.

[![CI](https://github.com/Ourea-Technologies/ensemble-weather-java/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/Ourea-Technologies/ensemble-weather-java/actions/workflows/ci.yml?query=branch%3Amain)
[![Security](https://github.com/Ourea-Technologies/ensemble-weather-java/actions/workflows/security.yml/badge.svg?branch=main)](https://github.com/Ourea-Technologies/ensemble-weather-java/actions/workflows/security.yml?query=branch%3Amain)
[![Maven Central](https://img.shields.io/maven-central/v/com.oureawx/ensemble-weather)](https://central.sonatype.com/artifact/com.oureawx/ensemble-weather)
[![Java 11+](https://img.shields.io/badge/java-11%20%7C%2017%20%7C%2021%20%7C%2025-blue)](https://github.com/Ourea-Technologies/ensemble-weather-java/actions/workflows/ci.yml?query=branch%3Amain)
[![License: BSD-2-Clause](https://img.shields.io/badge/license-BSD--2--Clause-blue)](LICENSE)
[![Docs](https://img.shields.io/badge/docs-docs.oureawx.com-informational)](https://docs.oureawx.com/java)
[![OpenSSF Scorecard](https://api.scorecard.dev/projects/github.com/Ourea-Technologies/ensemble-weather-java/badge)](https://scorecard.dev/viewer/?uri=github.com/Ourea-Technologies/ensemble-weather-java)

Forecasts for the contiguous United States from a blend of up to five ensemble
weather models, with every hour's full distribution. Java 11 or newer, built
on `java.net.http`; one small runtime dependency (jackson-core's streaming
parser); a JPMS module, `com.oureawx.ensembleweather`.

## Install

Gradle:

```kotlin
implementation("com.oureawx:ensemble-weather:0.1.0")
```

Maven:

```xml
<dependency>
  <groupId>com.oureawx</groupId>
  <artifactId>ensemble-weather</artifactId>
  <version>0.1.0</version>
</dependency>
```

Or try it with no project at all: `jbang --deps com.oureawx:ensemble-weather:0.1.0 Quickstart.java`.

## Quickstart

No signup and no API key. This is [`examples/Quickstart.java`](examples/Quickstart.java),
which CI runs on every change:

```java
// Quickstart: an anonymous forecast for Mt. Hood Meadows, Oregon (no API key).
// Prints the next 24 hours' temperature as a p10-p90 range, and the chance of
// at least 6 in (15 cm) of snow in the next 24 hours.
import com.oureawx.ensembleweather.Client;
import com.oureawx.ensembleweather.ForecastOptions;
import com.oureawx.ensembleweather.ProbabilityTerm;
import com.oureawx.ensembleweather.Threshold;
import com.oureawx.ensembleweather.types.ForecastResponse;
import com.oureawx.ensembleweather.types.Percentile;
import com.oureawx.ensembleweather.types.ProbabilityResult;
import com.oureawx.ensembleweather.types.TimeSeriesPoint;
import com.oureawx.ensembleweather.types.UnitSystem;
import com.oureawx.ensembleweather.types.Variable;
import java.util.List;

public class Quickstart {
  public static void main(String[] args) {
    Client client = Client.builder().build();
    ForecastOptions options =
        ForecastOptions.builder()
            .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(6)))
            .build();
    ForecastResponse forecast = client.getForecast("or/mt-hood-meadows", options);

    String degrees = forecast.units() == UnitSystem.IMPERIAL ? "°F" : "°C";
    System.out.println(forecast.location().name() + ": temperature, next 24 h (p10 to p90)");
    List<TimeSeriesPoint> hours = forecast.series().timeseries();
    for (TimeSeriesPoint hour : hours.subList(0, Math.min(25, hours.size()))) {
      hour.ensemble()
          .temperature()
          .range(Percentile.P10, Percentile.P90)
          .ifPresent(
              t ->
                  System.out.printf(
                      "  %s  %s to %s %s%n", hour.validTime(), t.low(), t.high(), degrees));
    }

    for (ProbabilityResult snow : forecast.probabilities()) {
      if (snow.value().isPresent()) {
        System.out.printf(
            "Chance of 6 in (15 cm) or more of snow in the next 24 h: %.0f%% (%d members)%n",
            snow.value().getAsDouble() * 100, snow.members());
      } else {
        System.out.println("The chance of 6 in of snow is not available for this window yet.");
      }
    }
  }
}
```

## Authentication

Pass a key to the builder, or set the `OUREA_API_KEY` environment variable:

```java
Client client = Client.builder().apiKey("ow_...").build();
// or: OUREA_API_KEY=ow_... java -jar app.jar, then Client.builder().build()
```

|                                                      | Anonymous                  | Free key                   | Pro key                                                               |
| ---------------------------------------------------- | -------------------------- | -------------------------- | --------------------------------------------------------------------- |
| Named-location forecasts, current conditions, alerts | 10-day horizon, latest run | 10-day horizon, latest run | 15-day horizon, past runs, per-model breakdowns, all member variables |
| Forecasts at any coordinates (`getPointForecast`)    | no                         | no                         | yes                                                                   |
| Search and catalog                                   | yes                        | yes                        | yes, larger list budgets                                              |

See [pricing](https://oureawx.com/pricing). `getSnowTrend` needs Plus or Pro
(`PLUS_REQUIRED` otherwise). Search, catalog details, attribution and
`getComposition` are always sent **without** the key, even when you have one, so
every caller shares one edge-cache entry; catalog list pages and forecasts are
sent with it. Call `.sendKeyOnOpenRoutes(true)` to attribute every request to
your key. The key is never logged, never in an exception message or
`toString()`, and never sent to another host on a redirect.

## Probabilities, not single numbers

Every forecast value is a distribution (`Quantiles`: mean, p10, p25, p50, p75,
p90, min, max). Ask the server for the probabilities and totals you need:

```java
// The chance of a threshold over a window, with its reference class.
ForecastResponse f =
    client.getForecast(
        "or/mt-hood-meadows",
        ForecastOptions.builder()
            .probability(
                ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(12))
                    .over(Window.hours(72)))
            .probability(
                ProbabilityTerm.atMost(Variable.TEMPERATURE, Threshold.fahrenheit(32))
                    .over(Window.between(6, 18))
                    .withAggregation(Aggregation.ALL))
            .total(TotalTerm.of(Variable.SNOWFALL, Window.hours(72)))
            .build());
for (ProbabilityResult p : f.probabilities()) {
  System.out.println(p.variable() + " " + p.op() + " " + p.threshold() + " over "
      + p.windowStartHours() + "-" + p.windowEndHours() + " h: "
      + p.value() + " from " + p.members() + " members");
}

// A storm total: the distribution of each member's own total over the window
// (empty when the model run does not cover the window yet).
Optional<Quantiles> storm = f.windowTotals().get(0).quantiles();

// Quantiles: the served ranges, by name.
Optional<QuantileRange> spread =
    f.series().timeseries().get(0).ensemble().temperature().range(Percentile.P10, Percentile.P90);
```

The SDK never sums, averages or interpolates quantiles, and never derives a
probability from them: the server computes every probability and window total
from the full ensemble (summing hourly quantiles gives a wrong storm total).
Thresholds you give in another unit (`Threshold.inches(6)` on a metric
request) are converted exactly before sending; response values are always in
the request's units. Answers are matched to your terms by the `term()` the
server echoes and come back in the order you asked; an empty `value()`
is never 0, and `nullReason()` says why (`WINDOW_NOT_COVERED`,
`TOO_FEW_MEMBERS`, `VARIABLE_UNAVAILABLE`). Wind direction is circular: each
hour's `ensemble().windDirection()` is a `CircularStats` (mean, spread and the
clockwise 50 % and 90 % sectors, whose `to` may be numerically less than
`from`), not quantiles.
More at [docs.oureawx.com/concepts/probabilities](https://docs.oureawx.com/concepts/probabilities).

## Names that differ from the API's JSON

A JSON field that repeats a word of the type holding it would read twice in
Java (`forecast.forecast()`, `current.current()`, `probability.probability()`),
so the SDK names it differently. There is one table for every Ourea SDK
(ensemble-weather-spec BEHAVIOR.md 3.0), and each accessor's Javadoc starts
with its JSON name. Every other field keeps its JSON name in camelCase. The
GeoJSON routes (`getAlertOutlines`, `getAvalancheZones`) are passed through
untouched: a feature's `properties()` and its geometry's `coordinates()` are
the API's JSON (`JsonValue`) with its wire keys, so an avalanche zone's
`properties` keeps `forecast_url` and `danger_level`.

| Type | JSON field | Java accessor |
|---|---|---|
| `ForecastResponse` | `forecast` | `series()` |
| `ForecastResponse` | `elevation_forecasts` | `elevations()` |
| `CurrentResponse` | `current` | `now()` |
| `CurrentConditions` | `condition` | `code()` |
| `CurrentConditions` | `condition_text` | `text()` |
| `CurrentConditions` | `condition_icon` | `icon()` |
| `CurrentStation` | `station_id` | `id()` |
| `ProbabilityResult` | `probability` | `value()` |
| `ProbabilityThresholds` | `daily_precipitation_probability` | `dailyPrecipitation()` |
| `ProbabilityThresholds` | `daily_precipitation_occurrence_probability` | `dailyPrecipitationOccurrence()` |
| `ProbabilityThresholds` | `daily_snow_probability` | `dailySnow()` |
| `WindowTotal` | `window_start_hours` | `startHours()` |
| `WindowTotal` | `window_end_hours` | `endHours()` |
| `ModelMembers` | `members` | `items()` |
| `MemberForecast` | `member_id` | `id()` |
| `MemberPlume` | `members` | `pool()` |
| `AlertsResponse` | `alerts` | `items()` |
| `AlertsResponse` | `alert_count` | `count()` |
| `LocationsResponse` | `locations` | `items()` |
| `NearbyLocationsResponse` | `location` | `origin()` |
| `NearbyLocationsResponse` | `locations` | `items()` |
| `NearbyPointResponse` | `locations` | `items()` |
| `RunsResponse` | `runs` | `items()` |
| `ElevationForecast` | `elevation` | `height()` |
| `ElevationSummary` | `elevation` | `height()` |
| `SnowTrendResponseElevation` | `elevation` | `height()` |
| `CatalogElevation` | `elevation_m` | `heightM()` |
| `CatalogStation, CatalogStationDetail` | `source_station_id` | `sourceId()` |
| `CatalogStation, CatalogStationDetail` | `station_status` | `status()` |
| `CatalogStation, CatalogStationDetail` | `station_url` | `url()` |
| `CatalogNearbyStation` | `station_status` | `status()` |
| `CatalogTemplateSummary` | `template_variant` | `variant()` |
| `CatalogResolvedEntry` | `resolved_locale` | `locale()` |
| `SensorObservation` | `observation_time` | `observedAt()` |
| `PassHour` | `snow_level_above_pass` | `snowLevelMargin()` |
| `PassStatusSegment` | `status` | `state()` |
| `PassStatusSegment` | `status_text` | `text()` |
| `AvalancheForecast` | `forecast_url` | `url()` |
| `SeasonTotal` | `total` | `amount()` |
| `SnowTrendDay` | `snow` | `amount()` |
| `PlaceSummary` | `short` | `shortText()` (a Java keyword) |
| `PlaceSummary` | `long` | `longText()` (a Java keyword) |
| `ElevationSummary, CatalogElevation` | `default / is_default` | `isDefault()` (a Java keyword) |

## Every route

| Method (each with an `...Async` twin) | Route | Access |
|---|---|---|
| `getForecast`, `getPointForecast` | a named location's forecast; one at coordinates (`membersPlume()` for the plume view) | anonymous; Pro |
| `getCurrent`, `getPointCurrent` | current conditions (a mountain pass also gets `pass()`) | anonymous; Pro |
| `getAlerts`, `getPointAlerts` | active NWS alerts | anonymous; Pro |
| `getNearby`, `getPointNearby` | nearby locations | anonymous |
| `searchLocations` | search by name, optionally `sort` by a metric and `near` a point | anonymous, keyless |
| `explore`, `iterateExplore` | places ranked by a metric (`SNOW_5D`, `BASE_DEPTH`, `SEASON_PRECIP`, ...) | anonymous |
| `getSeason` | observed season-to-date precipitation and snowfall | anonymous |
| `getSnowTrend` | each recent run's daily snowfall, run by run | Plus or Pro |
| `getSnowpack`, `getSnowpackPlume` | the modelled snowpack and its snow-depth plume (beta) | anonymous |
| `getAlertOutlines`, `getAvalancheZones` | GeoJSON for maps: alert outlines, avalanche zones and ratings | anonymous |
| `listLocations`, `iterateLocations` | every location, paged | anonymous |
| `listCatalogLocations`, `getCatalogLocation`, `listCatalogStations`, `getCatalogStation` | the catalog | anonymous |
| `getRuns`, `getComposition`, `getAttribution` | served runs, the blend's models and members, data credits | anonymous |

## More examples

| Example                                                  | Shows                                                                   |
| -------------------------------------------------------- | ----------------------------------------------------------------------- |
| [`Quickstart.java`](examples/Quickstart.java)             | the program above, anonymous                                            |
| [`PointForecast.java`](examples/PointForecast.java)       | a forecast at coordinates with elevation (Pro key from `OUREA_API_KEY`) |
| [`ChanceOfSnow.java`](examples/ChanceOfSnow.java)         | several `prob` terms in one call, with their windows and member counts  |
| [`StormTotal.java`](examples/StormTotal.java)             | `totals` window distributions for precipitation and snowfall            |
| [`SearchAndCatalog.java`](examples/SearchAndCatalog.java) | search, then page through the catalog with the cursor                   |
| [`CurrentAndAlerts.java`](examples/CurrentAndAlerts.java) | current conditions and active alerts for a location                     |
| [`ErrorHandling.java`](examples/ErrorHandling.java)       | catching an out-of-coverage error by its type; reading rate-limit state |
| [`Units.java`](examples/Units.java)                       | the same forecast in metric and imperial                                |
| [`SnowRankings.java`](examples/SnowRankings.java)         | `explore` by the next 5 days' snow, then the season so far              |

Run them all against a local mock of the API with `./gradlew runExamples`.
Every operation also has an `...Async` twin returning a `CompletableFuture`
(`getForecastAsync`, `searchLocationsAsync`, ...), and list operations have an
`iterate...` form that follows the cursor lazily (`Iterable` and `stream()`).

## Errors, retries and rate limits

Every exception is an unchecked `OureaException` with a stable
`category()`. HTTP errors carry `status()`, `code()` (verbatim, even when new),
`getMessage()`, `requestId()`, `docUrl()` (the code's entry on the docs errors
page) and, on a 400 about one parameter, `param()`; `RateLimitException` adds
`retryAfter()`, and a `PRO_REQUIRED` from a coordinate route may name a
`nearestLocation()` that answers without a key:

| Class                      | Category           | When                                                               |
| -------------------------- | ------------------ | ------------------------------------------------------------------ |
| `AuthenticationException`  | `AUTHENTICATION`   | invalid or missing key (401)                                       |
| `PermissionException`      | `PERMISSION`       | your plan does not allow it: `PRO_REQUIRED`, `PLUS_REQUIRED` (403) |
| `NotFoundException`        | `NOT_FOUND`        | no such location or route (404, 410)                               |
| `InvalidRequestException`  | `INVALID_REQUEST`  | the server rejected a parameter (400)                              |
| `OutsideCoverageException` | `OUTSIDE_COVERAGE` | `OUTSIDE_COVERAGE` (404): the point is outside the domain          |
| `RateLimitException`       | `RATE_LIMITED`     | over a budget (429)                                                |
| `UnavailableException`     | `UNAVAILABLE`      | the server could not answer (5xx), or sent a body off-contract     |
| `ConnectionException`      | `CONNECTION`       | no response: DNS, TLS, reset, or the 30 s per-attempt timeout      |
| `UsageException`           | `USAGE`            | refused before sending: an invalid term, an unserved quantile pair |

```java
try {
  client.getPointForecast(Coordinates.of(51.5, -0.1));
} catch (OutsideCoverageException e) {
  System.out.println(e.code().orElse("") + ": " + e.getMessage());
}
```

Connection errors (except TLS verification failures), 429, 502, 503 and 504
are retried up to `maxRetries` (default 2) times, waiting exactly `Retry-After`
(sent on 429, 503 and 504) when the server sends one of 60 s or less (more than that is returned at once
as the exception), else full-jitter backoff from 0.5 s up to 8 s. Nothing else
is retried.

Each top-level response has `metadata()`: `rateLimit()` (`limit`, `remaining`,
`reset`, `policy` from the `RateLimit-*` headers), `requestId()` and
`revalidated()`; `client.lastRateLimit()` holds the last state seen. Read them
rather than hard-coding limits. Repeated calls revalidate a local ETag cache
(on by default, 256 entries / 32 MiB; `.disableCache()` or your own
`ResponseCache`), so an unchanged forecast costs a `304`.

## Documentation

- Guides and concepts: [docs.oureawx.com](https://docs.oureawx.com)
- This SDK's guide: [docs.oureawx.com/java](https://docs.oureawx.com/java)
- API reference (Javadoc): [javadoc.io](https://javadoc.io/doc/com.oureawx/ensemble-weather), or `./gradlew javadoc` locally
- The OpenAPI spec this SDK targets (API v1, contract 1.0.0): [ensemble-weather-spec](https://github.com/Ourea-Technologies/ensemble-weather-spec/blob/main/openapi/v1/openapi.yaml), pinned in [`spec/`](spec/README.md)

## Supported versions

Java 11, 17, 21 and 25 (tested in CI on each). The jar is compiled with
`--release 11` and is a named JPMS module (`com.oureawx.ensembleweather`) that
also works on the classpath. This is version 0.x; the API may change before
1.0.

## Contributing and community

This SDK is open source and we want it to be the best weather SDK in Java.
Bug reports, feature requests, docs fixes and pull requests are all welcome,
including small ones.

- **Report a bug:** [open a bug report](https://github.com/Ourea-Technologies/ensemble-weather-java/issues/new?template=bug_report.yml)
- **Request a feature** or an API capability: [open a feature request](https://github.com/Ourea-Technologies/ensemble-weather-java/issues/new?template=feature_request.yml)
- **Ask a question or share what you built:** [GitHub Discussions](https://github.com/Ourea-Technologies/ensemble-weather-java/discussions)
- **Contribute code or docs:** read [CONTRIBUTING.md](CONTRIBUTING.md), and look for [`good first issue`](https://github.com/Ourea-Technologies/ensemble-weather-java/labels/good%20first%20issue)
- **Security issues:** never in public issues; see [SECURITY.md](SECURITY.md)

Licensed under [BSD-2-Clause](LICENSE), Copyright (c) 2026 Ourea Technologies.
