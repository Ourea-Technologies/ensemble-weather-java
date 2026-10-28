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
import com.oureawx.ensembleweather.EnsembleWeatherClient;
import com.oureawx.ensembleweather.ForecastOptions;
import com.oureawx.ensembleweather.ProbabilityTerm;
import com.oureawx.ensembleweather.Threshold;
import com.oureawx.ensembleweather.model.ForecastResponse;
import com.oureawx.ensembleweather.model.Percentile;
import com.oureawx.ensembleweather.model.ProbabilityResult;
import com.oureawx.ensembleweather.model.TimeSeriesPoint;
import com.oureawx.ensembleweather.model.UnitSystem;
import com.oureawx.ensembleweather.model.Variable;
import java.util.List;

public class Quickstart {
  public static void main(String[] args) {
    EnsembleWeatherClient client = EnsembleWeatherClient.builder().build();
    ForecastOptions options =
        ForecastOptions.builder()
            .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.inches(6)))
            .build();
    ForecastResponse forecast = client.getForecast("or/mt-hood-meadows", options);

    String degrees = forecast.units() == UnitSystem.IMPERIAL ? "°F" : "°C";
    System.out.println(forecast.location().name() + ": temperature, next 24 h (p10 to p90)");
    List<TimeSeriesPoint> hours = forecast.forecast().timeseries();
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
      if (snow.probability().isPresent()) {
        System.out.printf(
            "Chance of 6 in (15 cm) or more of snow in the next 24 h: %.0f%% (%d members)%n",
            snow.probability().getAsDouble() * 100, snow.members());
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
EnsembleWeatherClient client = EnsembleWeatherClient.builder().apiKey("ow_...").build();
// or: OUREA_API_KEY=ow_... java -jar app.jar, then EnsembleWeatherClient.builder().build()
```

|                                                      | Anonymous                  | Free key                   | Pro key                                                               |
| ---------------------------------------------------- | -------------------------- | -------------------------- | --------------------------------------------------------------------- |
| Named-location forecasts, current conditions, alerts | 10-day horizon, latest run | 10-day horizon, latest run | 15-day horizon, past runs, per-model breakdowns, all member variables |
| Forecasts at any coordinates (`getPointForecast`)    | no                         | no                         | yes                                                                   |
| Search and catalog                                   | yes                        | yes                        | yes, larger list budgets                                              |

See [pricing](https://oureawx.com/pricing). Search, catalog details and
attribution are always sent **without** the key, even when you have one, so
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
      + p.probability() + " from " + p.members() + " members");
}

// A storm total: the distribution of each member's own total over the window
// (empty when the model run does not cover the window yet).
Optional<Quantiles> storm = f.windowTotals().get(0).quantiles();

// Quantiles: the served ranges, by name.
Optional<QuantileRange> spread =
    f.forecast().timeseries().get(0).ensemble().temperature().range(Percentile.P10, Percentile.P90);
```

The SDK never sums, averages or interpolates quantiles, and never derives a
probability from them: the server computes every probability and window total
from the full ensemble (summing hourly quantiles gives a wrong storm total).
Thresholds you give in another unit (`Threshold.inches(6)` on a metric
request) are converted exactly before sending; response values are always in
the request's units. Answers are matched to your terms by the `term()` the
server echoes and come back in the order you asked; an empty `probability()`
is never 0, and `nullReason()` says why (`WINDOW_NOT_COVERED`,
`TOO_FEW_MEMBERS`, `VARIABLE_UNAVAILABLE`). Wind direction is circular: each
hour's `ensemble().windDirection()` is a `CircularStats` (mean, spread and the
clockwise 50 % and 90 % sectors, whose `to` may be numerically less than
`from`), not quantiles.
More at [docs.oureawx.com/concepts/probabilities](https://docs.oureawx.com/concepts/probabilities).

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

Run them all against a local mock of the API with `./gradlew runExamples`.
Every operation also has an `...Async` twin returning a `CompletableFuture`
(`getForecastAsync`, `searchLocationsAsync`, ...), and list operations have an
`iterate...` form that follows the cursor lazily (`Iterable` and `stream()`).

## Errors, retries and rate limits

Every exception is an unchecked `EnsembleWeatherException` with a stable
`category()`. HTTP errors carry `status()`, `code()` (verbatim, even when new),
`getMessage()`, `requestId()`, `docUrl()` (the code's entry on the docs errors
page) and, on a 400 about one parameter, `param()`; `RateLimitException` adds
`retryAfter()`, and a `PRO_REQUIRED` from a coordinate route may name a
`nearestLocation()` that answers without a key:

| Class                      | Category           | When                                                               |
| -------------------------- | ------------------ | ------------------------------------------------------------------ |
| `AuthenticationException`  | `AUTHENTICATION`   | invalid or missing key (401)                                       |
| `PermissionException`      | `PERMISSION`       | your plan does not allow it, such as `PRO_REQUIRED` (403)          |
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
