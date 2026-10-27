// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A ForecastData from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ForecastData}.
 */
public final class ForecastData extends Model {
  private final int maxHours;
  private final int intervalHours;
  private final List<TimeSeriesPoint> timeseries;
  private final List<DailyForecast> daily;
  private final @Nullable SnowOutlook snowOutlook;

  private ForecastData(Builder b) {
    this.maxHours = require(b.maxHours, "max_hours");
    this.intervalHours = require(b.intervalHours, "interval_hours");
    this.timeseries = List.copyOf(b.timeseries);
    this.daily = List.copyOf(b.daily);
    this.snowOutlook = b.snowOutlook;
  }

  /**
   * Returns a new builder.
   *
   * @return an empty builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns a builder holding this object's values.
   *
   * @return a builder to make a modified copy
   */
  public Builder toBuilder() {
    Builder b = new Builder();
    b.maxHours = this.maxHours;
    b.intervalHours = this.intervalHours;
    b.timeseries = this.timeseries;
    b.daily = this.daily;
    b.snowOutlook = this.snowOutlook;
    return b;
  }

  /**
   * The last served hour, in hours from the FIRST served one (the current hour by default, the
   * run's init with {@code include_past=true} or a pinned {@code run}) -- the request's {@code
   * max_hours} clamped to the tier, less when the run ends first. {@code 0} with {@code
   * hourly=false}, which serves no hour.
   *
   * @return the {@code max_hours} value
   */
  public int maxHours() {
    return maxHours;
  }

  /**
   * The {@code interval_hours} value.
   *
   * @return the {@code interval_hours} value
   */
  public int intervalHours() {
    return intervalHours;
  }

  /**
   * Omitted with {@code hourly=false}. Starts at the hour containing the request time ({@code
   * floor(now)}; 23:00Z for a 23:23Z request against the 18Z run) and runs {@code max_hours} past
   * it; with {@code include_past=true} or a pinned {@code run}, at the run's init. {@code
   * lead_time_hours} counts from {@code model_run.initialized_at} either way. Hourly points,
   * ascending by {@code valid_time}. <b>Not guaranteed contiguous.</b> {@code temperature}, {@code
   * precipitation}, {@code wind_speed} and {@code wind_direction} are non-nullable on a {@code
   * TimeSeriesPoint} because the blend always produces them; an hour where it did not is OMITTED
   * from this array rather than served with fabricated zeros. That is a system failure on our side,
   * it is alarmed on, and it is expected to be vanishingly rare — but a client must key on {@code
   * valid_time} / {@code lead_time_hours} and must not assume {@code timeseries[i]} is {@code i}
   * hours after {@code timeseries[0]}. The same hours are absent from {@code daily} (their day
   * totals sum only the hours present, exactly as the first and last day of any forecast already
   * do) and from {@code elevation_forecasts[].timeseries}.
   *
   * @return the {@code timeseries} values; empty when the response has none
   */
  public List<TimeSeriesPoint> timeseries() {
    return timeseries;
  }

  /**
   * The {@code daily} value.
   *
   * @return the {@code daily} values; empty when the response has none
   */
  public List<DailyForecast> daily() {
    return daily;
  }

  /**
   * Rolling snow-accumulation summary for common UI windows. Always forward-looking (next N hours /
   * days), summed from the same corrected hourly series as {@code daily} and {@code timeseries} —
   * exposed here so clients don't each re-implement the same rolling sums. Like {@code daily}, it
   * does not depend on {@code max_hours} or {@code hourly}: the windows are read from the forecast,
   * not from the served hours. Its windows open at the first served hour -- the current hour by
   * default, the run's init with {@code include_past=true} -- with {@code hourly=false} too.
   * Present exactly when {@code daily} is.
   *
   * @return the {@code snow_outlook} value, or empty when the response omits it
   */
  public Optional<SnowOutlook> snowOutlook() {
    return Optional.ofNullable(snowOutlook);
  }

  @Override
  Object[] values() {
    return new Object[] {maxHours, intervalHours, timeseries, daily, snowOutlook};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "maxHours", "intervalHours", "timeseries", "daily", "snowOutlook"
  };

  /** Builds a {@link ForecastData}. */
  public static final class Builder {
    private @Nullable Integer maxHours;
    private @Nullable Integer intervalHours;
    private List<TimeSeriesPoint> timeseries = List.of();
    private List<DailyForecast> daily = List.of();
    private @Nullable SnowOutlook snowOutlook;

    private Builder() {}

    /**
     * Sets {@link ForecastData#maxHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder maxHours(int value) {
      this.maxHours = value;
      return this;
    }

    /**
     * Sets {@link ForecastData#intervalHours()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder intervalHours(int value) {
      this.intervalHours = value;
      return this;
    }

    /**
     * Sets {@link ForecastData#timeseries()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder timeseries(List<TimeSeriesPoint> values) {
      this.timeseries = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ForecastData#daily()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder daily(List<DailyForecast> values) {
      this.daily = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ForecastData#snowOutlook()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowOutlook(@Nullable SnowOutlook value) {
      this.snowOutlook = value;
      return this;
    }

    /**
     * Builds the {@link ForecastData}.
     *
     * @return the immutable ForecastData
     * @throws IllegalStateException if a required value was not set
     */
    public ForecastData build() {
      return new ForecastData(this);
    }
  }
}
