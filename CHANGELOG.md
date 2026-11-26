# Changelog

All notable changes to this SDK are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the SDK uses
[Semantic Versioning](https://semver.org/) (0.x until its pre-1.0 review).
Outside contributors are credited by GitHub handle. Thank you!

## [Unreleased]

## [0.1.0] - unreleased

The first release on Maven Central (`com.oureawx:ensemble-weather`). Targets
the v1 contract at ensemble-weather-spec `7954268` (the server's public spec
at 256cd770, byte for byte), and runs all 58 shared vectors on Java 11, 17,
21 and 25. The never-published drafts below are folded into it.

### Added

- `Client` with a builder: API key (or `OUREA_API_KEY`), base URL (or
  `OUREA_BASE_URL`), default units, timeout, retries, cache, proxy and
  executor. Blocking methods and `...Async` twins returning
  `CompletableFuture` for every v1 operation except `getMapsToken`.
- `explore(ExploreMetric, ExploreOptions)` and `iterateExplore(...)`: places
  ranked by a metric (`ExploreMetric.SNOW_5D`, `BASE_DEPTH`,
  `SEASON_PRECIP`, ...), with `order` (`SortOrder`), `type`, `state`,
  `near`, `radiusKm`, elevation bounds, `allPoints` and cursor paging.
- `getSeason` (observed season-to-date precipitation and snowfall,
  `SeasonOptions`), `getSnowTrend` (each recent run's daily snowfall; Plus or
  Pro, `SnowTrendOptions`), `getSnowpack` and `getSnowpackPlume` (beta),
  `getComposition` (an open route, sent without the key), and the GeoJSON
  routes `getAlertOutlines(AlertOutlineOptions)` and `getAvalancheZones`.
- `SearchOptions` takes `sort`, `order`, `near`, `radiusKm` and `units` (the
  client's units are sent by default); a hit carries `explore()` with a
  metric sort.
- `ForecastOptions.membersPlume()` and `members(String)` (`"true"`,
  `"false"` or `"plume"`; anything else is a `UsageException`): the plume
  view in `ForecastData.plume()` (`MemberPlume`); `MembersView.POOL`.
- New fields: `ForecastResponse.location()` is a `ForecastLocation` (adds
  `elevation()` and `elevations()`), `snowOnGround()` and `avalanche()`
  (`AvalancheForecast`); `snowLevelWetBulb()` on `EnsembleStats`,
  `ModelStats`, `MemberForecast` and `DailyForecast`;
  `TimeSeriesPoint.measured()`, `snowCharacter()`, `membersSpread()`,
  `members6h()`, `precipitationByType()`; `DailyForecast.coveredFrom()`,
  `measuredPrecipitation()`, `snowCharacter()`, `slr()`, `snowLevelRange()`,
  `snowLevelWetBulbRange()`, `normal()`, `departure()`, `percentile()`;
  `precipitationOutlook()` and `plume()` on `ForecastData` and
  `ElevationForecast`; `ElevationForecast.displayName()`, `snowOnGround()`,
  `nearestSensors()`; `SnowOutlook.next72h()`; `WindowTotal.method()`;
  `CurrentConditions.measured()` and `cloudCover()`; `CurrentStation.calm()`;
  `CurrentResponse.summary()` (`PlaceSummary`) and `pass()`
  (`PassForecast`); `SensorObservation.distanceKm()` and `elevationDiff()`;
  ten NWS fields on `ActiveAlert` (`sent()`, `messageType()`,
  `description()`, `instruction()`, ...); `PrecipDisplay.snowAmount()`;
  `ModelEntry.displayMembers()`; `SearchHit.explore()`;
  `CatalogElevation.displayName()`; `CatalogStationDetail.indexable()`.
  `MembersCumulative` holds `MemberQuantiles`; `LocationSummary` and
  `NearbyLocation` elevations are `ElevationSummary` (adds `displayName()`).
- Enum values: `light_snow` and `heavy_snow` conditions and icons,
  `SourceKind.AVALANCHE_FORECAST`, `StationNetwork.RAIN_GAUGE` and
  `SURFRAD`; new enums `SnowCharacter`, `AlertClass`, `PassStatusLevel`,
  `ExploreMetric`. `PLUS_REQUIRED` is a `PermissionException`.
- Probabilities (`ProbabilityTerm`, `Threshold`, `Window`) sent as canonical
  `prob=` terms with exact unit conversion and server-identical rounding;
  answers are matched to the caller's terms by the `term` the server echoes.
  Window totals (`TotalTerm`).
- Immutable models with builders for every schema the operations reach, with
  `Optional` for absent values, `java.time` types, and `UNKNOWN` plus the raw
  string for enum values added later. GeoJSON features keep their
  `properties` and `coordinates` as raw `JsonValue`, wire keys and all.
- Unchecked exceptions, one per error category, under `OureaException`, with
  `code()`, `docUrl()`, `param()` and `requestId()`;
  `PermissionException.nearestLocation()`.
- Lazy paging (`PagedIterable`: `Iterable` and `stream()`).
- Retries with `Retry-After` and full-jitter backoff, same-host redirects that
  drop the key across hosts, a local ETag cache (`ResponseCache`,
  `InMemoryResponseCache`), rate-limit state and deprecation warnings.
- Nine examples (`examples/SnowRankings.java` is new), a local mock API that
  serves every route, and a nightly live contract that covers them.

### Changed

- No stutter (BEHAVIOR.md 3.0): the client is `Client` (was
  `EnsembleWeatherClient`), the base exception is `OureaException` (was
  `EnsembleWeatherException`), and the models are in
  `com.oureawx.ensembleweather.types` (was `.model`), also the JPMS export.
- JSON fields that repeat a word of their own type have SDK names, one table
  for every Ourea SDK (BEHAVIOR.md 3.0); each accessor's Javadoc starts with
  the JSON name. `ForecastResponse.series()` (JSON `forecast`) and
  `elevations()` (`elevation_forecasts`); `CurrentResponse.now()`
  (`current`); `CurrentConditions.code()`, `text()`, `icon()`
  (`condition*`); `CurrentStation.id()`; `ProbabilityResult.value()`
  (`probability`); `ProbabilityThresholds.dailyPrecipitation()`,
  `dailyPrecipitationOccurrence()`, `dailySnow()`; `WindowTotal.startHours()`
  and `endHours()`; `ModelMembers.items()`; `MemberForecast.id()`;
  `MemberPlume.pool()`; `items()` on `AlertsResponse` (with `count()`),
  `LocationsResponse`, `NearbyLocationsResponse` (with `origin()`),
  `NearbyPointResponse` and `RunsResponse`; `height()` on
  `ElevationForecast`, `ElevationSummary` and the snow trend's elevation;
  `CatalogElevation.heightM()`; `CatalogStation` `sourceId()`, `status()`,
  `url()`; `CatalogNearbyStation.status()`; `CatalogTemplateSummary.variant()`;
  `CatalogResolvedEntry.locale()`; `SensorObservation.observedAt()`;
  `PassHour.snowLevelMargin()`; `PassStatusSegment.state()` and `text()`;
  `AvalancheForecast.url()`; `SeasonTotal.amount()`; `SnowTrendDay.amount()`.
  Java's reserved words take an idiom name: `PlaceSummary.shortText()` and
  `longText()` (JSON `short`, `long`), and `isDefault()` as before.
- Comma lists (`sources`, `exclude_sources`, `elevations`, `diagnostics`,
  `include`, `network`, `capability`, `class`) are sorted, de-duplicated, each
  item percent-encoded, and the commas sent literally (BEHAVIOR.md 3.1
  rule 5). `capability` is one comma list, not a repeated parameter.
- `precip_*` is `precipitation_*` everywhere; `windDirection()` on
  `EnsembleStats` and `ModelStats` is `CircularStats`; the coordinate
  overloads are `getPointForecast`, `getPointCurrent`, `getPointAlerts` and
  `getPointNearby`; errors map the public codes as BEHAVIOR.md section 8
  says, a retired code by status.

### Removed

- `getStormReports`/`getStormReport` and catalog ads (not in the public
  API); `ForecastOptions.feelsLike`, `Diagnostic.PTYPE` and the fields the
  v1 contract dropped. `LocationElevation` is `ElevationSummary`.

[Unreleased]: https://github.com/Ourea-Technologies/ensemble-weather-java/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/Ourea-Technologies/ensemble-weather-java/releases/tag/v0.1.0
