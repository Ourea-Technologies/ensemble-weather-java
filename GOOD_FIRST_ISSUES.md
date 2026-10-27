# Good first issues (draft)

Three starter issues to file with the `good first issue` label when the
repository is published. Each is small, self-contained and has a clear test.

## 1. Example: daily highs and lows for a week

Add `examples/DailyOutlook.java`: an anonymous forecast with
`ForecastOptions.builder().hourly(false).maxDays(7)` that prints each
`DailyForecast`'s date, `conditionText()`, and the p10-p90 range of `high()`
and `low()` (via `range(Percentile.P10, Percentile.P90)`). Add a row to the
README's examples table; `./gradlew runExamples` must pass against the mock
(teach `MockApiServer` to serve a `daily` array if needed).

## 2. `toString()` for the option classes

`ForecastOptions`, `CurrentOptions`, `NearbyOptions` and friends have no
`toString()`, so they print as `ForecastOptions@1b6d3586` in logs. Give each a
`toString()` that lists the options that are set (and nothing else), with a
unit test per class.

## 3. A `Stream` of the hours in a window

Add `ForecastData.hoursBetween(Instant from, Instant to)` (or a helper in an
`Hours` utility) that returns the `TimeSeriesPoint`s whose `validTime` falls in
`[from, to)`. It must key on `validTime`, never on list position: the series is
not guaranteed contiguous (see the `timeseries` docs). Unit-test a gap.
