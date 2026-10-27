# Changelog

All notable changes to this SDK are documented here. The format follows
[Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and the SDK uses
[Semantic Versioning](https://semver.org/) (0.x until its pre-1.0 review).
Outside contributors are credited by GitHub handle. Thank you!

## [Unreleased]

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
