// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * One ranked point of a place.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ExploreItem}.
 */
public final class ExploreItem extends Model {
  private final int rank;
  private final LocationRef location;
  private final @Nullable String elevationName;
  private final @Nullable String elevationDisplayName;
  private final double elevation;
  private final double value;
  private final @Nullable Double p10;
  private final @Nullable Double p90;
  private final Instant asOf;
  private final @Nullable Instant initializedAt;
  private final String source;
  private final @Nullable Double distanceKm;
  private final String path;

  private ExploreItem(Builder b) {
    this.rank = require(b.rank, "rank");
    this.location = require(b.location, "location");
    this.elevationName = b.elevationName;
    this.elevationDisplayName = b.elevationDisplayName;
    this.elevation = require(b.elevation, "elevation");
    this.value = require(b.value, "value");
    this.p10 = b.p10;
    this.p90 = b.p90;
    this.asOf = require(b.asOf, "as_of");
    this.initializedAt = b.initializedAt;
    this.source = require(b.source, "source");
    this.distanceKm = b.distanceKm;
    this.path = require(b.path, "path");
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
    b.rank = this.rank;
    b.location = this.location;
    b.elevationName = this.elevationName;
    b.elevationDisplayName = this.elevationDisplayName;
    b.elevation = this.elevation;
    b.value = this.value;
    b.p10 = this.p10;
    b.p90 = this.p90;
    b.asOf = this.asOf;
    b.initializedAt = this.initializedAt;
    b.source = this.source;
    b.distanceKm = this.distanceKm;
    b.path = this.path;
    return b;
  }

  /**
   * 1-based, across pages.
   *
   * @return the {@code rank} value
   */
  public int rank() {
    return rank;
  }

  /**
   * The {@code location} value.
   *
   * @return the {@code location} value
   */
  public LocationRef location() {
    return location;
  }

  /**
   * The named elevation; {@code null} = the place's own point.
   *
   * @return the {@code elevation_name} value, or empty when the response omits it
   */
  public Optional<String> elevationName() {
    return Optional.ofNullable(elevationName);
  }

  /**
   * What the elevation {@code elevation_name} is called, as {@code ElevationSummary.display_name};
   * absent where it has no name.
   *
   * @return the {@code elevation_display_name} value, or empty when the response omits it
   */
  public Optional<String> elevationDisplayName() {
    return Optional.ofNullable(elevationDisplayName);
  }

  /**
   * The point's elevation, m or ft by {@code units}.
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * The metric's value as the place's page serves it, in {@code unit}.
   *
   * @return the {@code value} value
   */
  public double value() {
    return value;
  }

  /**
   * Present only on {@code snow_today} (the day's served {@code total_snowfall.p10}, where it has a
   * {@code snow_amount}) and {@code precip_today} ({@code total_precipitation_if_wet.p10}, where
   * the day is not {@code none}). No range on multi-day sums.
   *
   * @return the {@code p10} value, or empty when the response omits it
   */
  public OptionalDouble p10() {
    return p10 == null ? OptionalDouble.empty() : OptionalDouble.of(p10);
  }

  /**
   * As {@code p10}.
   *
   * @return the {@code p90} value, or empty when the response omits it
   */
  public OptionalDouble p90() {
    return p90 == null ? OptionalDouble.empty() : OptionalDouble.of(p90);
  }

  /**
   * When the value was taken: the forecast windows when recorded from the served run, {@code
   * base_depth} its hour, {@code new_snow_24h} the snowpack run's first hour, the season its newest
   * computed day.
   *
   * @return the {@code as_of} value
   */
  public Instant asOf() {
    return asOf;
  }

  /**
   * Forecast windows only, the served run's {@code model_run.initialized_at}.
   *
   * @return the {@code initialized_at} value, or empty when the response omits it
   */
  public Optional<Instant> initializedAt() {
    return Optional.ofNullable(initializedAt);
  }

  /**
   * The {@code source} value. One of {@code ourea}, {@code snowpack_measured}, {@code
   * snowpack_open_loop}, {@code mrms_multisensor}, {@code station_gauge}; read it as a string,
   * since the API may add values.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * With {@code near} only, from it (0.1 km).
   *
   * @return the {@code distance_km} value, or empty when the response omits it
   */
  public OptionalDouble distanceKm() {
    return distanceKm == null ? OptionalDouble.empty() : OptionalDouble.of(distanceKm);
  }

  /**
   * The place's forecast route; add {@code ?elevations=<elevation_name>} for a named point.
   *
   * @return the {@code path} value
   */
  public String path() {
    return path;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      rank,
      location,
      elevationName,
      elevationDisplayName,
      elevation,
      value,
      p10,
      p90,
      asOf,
      initializedAt,
      source,
      distanceKm,
      path
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "rank",
    "location",
    "elevationName",
    "elevationDisplayName",
    "elevation",
    "value",
    "p10",
    "p90",
    "asOf",
    "initializedAt",
    "source",
    "distanceKm",
    "path"
  };

  /** Builds a {@link ExploreItem}. */
  public static final class Builder {
    private @Nullable Integer rank;
    private @Nullable LocationRef location;
    private @Nullable String elevationName;
    private @Nullable String elevationDisplayName;
    private @Nullable Double elevation;
    private @Nullable Double value;
    private @Nullable Double p10;
    private @Nullable Double p90;
    private @Nullable Instant asOf;
    private @Nullable Instant initializedAt;
    private @Nullable String source;
    private @Nullable Double distanceKm;
    private @Nullable String path;

    private Builder() {}

    /**
     * Sets {@link ExploreItem#rank()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder rank(int value) {
      this.rank = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#elevationName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder elevationName(@Nullable String value) {
      this.elevationName = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#elevationDisplayName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder elevationDisplayName(@Nullable String value) {
      this.elevationDisplayName = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#value()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder value(double value) {
      this.value = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#p10()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p10(@Nullable Double value) {
      this.p10 = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#p90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p90(@Nullable Double value) {
      this.p90 = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#asOf()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder asOf(Instant value) {
      this.asOf = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#initializedAt()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder initializedAt(@Nullable Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#distanceKm()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder distanceKm(@Nullable Double value) {
      this.distanceKm = value;
      return this;
    }

    /**
     * Sets {@link ExploreItem#path()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder path(String value) {
      this.path = value;
      return this;
    }

    /**
     * Builds the {@link ExploreItem}.
     *
     * @return the immutable ExploreItem
     * @throws IllegalStateException if a required value was not set
     */
    public ExploreItem build() {
      return new ExploreItem(this);
    }
  }
}
