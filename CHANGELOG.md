# Changelog

All notable changes to this SDK are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the SDK uses
[Semantic Versioning](https://semver.org/) (0.x until its pre-1.0 review).
Outside contributors are credited by GitHub handle. Thank you!

## [Unreleased]

Moves to the v1 contract live on api.oureawx.com (ensemble-weather-spec
`8b85fea`, the server's public spec byte for byte). Breaking (0.x).

### Changed

- `precip_*` is `precipitation_*` everywhere: `DailyForecast`
  `totalPrecipitation()`, `totalPrecipitationIfWet()`,
  `precipitationProbability()`, `precipitationOccurrenceProbability()`,
  `precipitationType()`, `precipitationDisplay()`; `TimeSeriesPoint`
  `precipitationType()`; `CurrentConditions.precipitationProbability()`;
  `SensorObservation.precipitationRate()`; `ProbabilityThresholds`
  `dailyPrecipitation*()`.
- `EnsembleStats.windDirection()` and `ModelStats.windDirection()` are
  `CircularStats` (mean, stddev, `sector50`/`sector90`), not `Quantiles`.
- Probability and window-total answers are matched to the caller's terms by
  the `term` the server echoes, not by position or a rebuilt key.
- Errors: the 16 public codes map as BEHAVIOR.md section 8 says
  (`AUTH_UNAVAILABLE` is unavailable; `OUTSIDE_COVERAGE` is a 404); a retired
  code maps by status. `requestId()` prefers the body's `request_id`.
  `Retry-After` is honoured on 504 as on 429 and 503.
- `ActiveAlert.severity()`/`urgency()` are enums (`AlertSeverity`,
  `AlertUrgency`, snake_case on the wire); catalog `network`,
  `station_status`, `template_variant` and a template's `location_type` are
  enums (`StationNetwork`, `StationStatus`, `TemplateVariant`,
  `LocationType`). `HourlyIcon` and `DailyIcon` are one `ConditionIcon`.
- `LocationRef.slug()` and `type()` are optional (a coordinate forecast's
  location may have neither); `DailyWind` and `GapFlow` fields the spec
  requires are no longer optional.
- The vectors' location argument is `slug` (was `name`).

### Added

- `ProbabilityResult.term()`/`nullReason()`; `WindowTotal.term()`,
  `aggregation()` (`TotalAggregation`) and `nullReason()` (`NullReason`).
- `ActiveAlert.certainty()` (`AlertCertainty`); `CurrentStation.stationId()`.
- `EnsembleWeatherException.docUrl()` and `param()`;
  `PermissionException.nearestLocation()` (`NearestLocation`).
- `ForecastOptions.include(ForecastInclude...)` for the opt-in
  `ensemble.mslp` and `ensemble.skin_temperature`.
- `getRuns(limit, cursor)` and `RunsResponse.nextCursor()`.

### Removed

- `getStormReports`/`getStormReport`, `StormReportCard` and
  `StormReportsResponse` (not in the public API).
- Catalog ads: `CatalogInclude.ADS`, `CatalogLocationDetail.ads()`,
  `CatalogStationDetail.ads()` and their models.
- `ForecastOptions.feelsLike` and `FeelsLikeIndex` (NWS is the only index and
  the default), `Diagnostic.PTYPE`, `EnsembleStats.feelsLikeUtci()`,
  `ptype()` and `windDirectionCircular()`, `DailyForecast.feelsLikeUtci*()`,
  and `ElevationForecast.isDefault()`/`timeseries()` with
  `ElevationTimeSeriesPoint`.

## [0.1.0] - 2026-10-27

The first release, targeting API v1 (contract 1.0.0, ensemble-weather-spec
`0fbb334`).

### Added

- `EnsembleWeatherClient` with a builder: API key (or `OUREA_API_KEY`), base
  URL (or `OUREA_BASE_URL`), default units, timeout, retries, cache, proxy and
  executor. Blocking methods and `...Async` twins returning `CompletableFuture`
  for every v1 operation except `getMapsToken`.
- Probabilities (`ProbabilityTerm`, `Threshold`, `Window`) sent as canonical
  `prob=` terms, with exact unit conversion and server-identical rounding, and
  answers mapped back to the caller's order; window totals (`TotalTerm`).
- Immutable models with builders for every schema the operations reach, with
  `Optional` for absent values, `java.time` types, and `UNKNOWN` plus the raw
  string for enum values added later.
- Unchecked exceptions, one per error category, under
  `EnsembleWeatherException`.
- Lazy paging (`PagedIterable`: `Iterable` and `stream()`).
- Retries with `Retry-After` and full-jitter backoff, same-host redirects that
  drop the key across hosts, a local ETag cache (`ResponseCache`,
  `InMemoryResponseCache`), rate-limit state and deprecation warnings.
- The eight standard examples, a local mock API, and CI that runs all 43
  shared vectors on Java 11, 17, 21 and 25.

[Unreleased]: https://github.com/Ourea-Technologies/ensemble-weather-java/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/Ourea-Technologies/ensemble-weather-java/releases/tag/v0.1.0
