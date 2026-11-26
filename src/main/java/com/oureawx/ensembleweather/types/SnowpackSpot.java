// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A SnowpackSpot from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackSpot}.
 */
public final class SnowpackSpot extends Model {
  private final @Nullable String name;
  private final @Nullable String displayName;
  private final double elevation;
  private final double elevationM;
  private final Instant initializedAt;
  private final @Nullable SnowpackSpotAnchor anchor;
  private final SnowpackDepth depth;
  private final @Nullable Double depthChange24h;
  private final List<Double> swe;
  private final List<SnowpackStep> steps;
  private final List<SnowpackLayer> layers;
  private final List<SnowpackAspect> aspects;

  private SnowpackSpot(Builder b) {
    this.name = b.name;
    this.displayName = b.displayName;
    this.elevation = require(b.elevation, "elevation");
    this.elevationM = require(b.elevationM, "elevation_m");
    this.initializedAt = require(b.initializedAt, "initialized_at");
    this.anchor = b.anchor;
    this.depth = require(b.depth, "depth");
    this.depthChange24h = b.depthChange24h;
    this.swe = List.copyOf(b.swe);
    this.steps = List.copyOf(b.steps);
    this.layers = List.copyOf(b.layers);
    this.aspects = List.copyOf(b.aspects);
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
    b.name = this.name;
    b.displayName = this.displayName;
    b.elevation = this.elevation;
    b.elevationM = this.elevationM;
    b.initializedAt = this.initializedAt;
    b.anchor = this.anchor;
    b.depth = this.depth;
    b.depthChange24h = this.depthChange24h;
    b.swe = this.swe;
    b.steps = this.steps;
    b.layers = this.layers;
    b.aspects = this.aspects;
    return b;
  }

  /**
   * The named elevation ({@code location.elevations[].name}); {@code null} is the location's own
   * point.
   *
   * @return the {@code name} value, or empty when the response omits it
   */
  public Optional<String> name() {
    return Optional.ofNullable(name);
  }

  /**
   * What the named elevation is called, as {@code ElevationSummary.display_name}; absent for the
   * location's own point and where the catalog has no name for the elevation.
   *
   * @return the {@code display_name} value, or empty when the response omits it
   */
  public Optional<String> displayName() {
    return Optional.ofNullable(displayName);
  }

  /**
   * In the response's units (m or ft).
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * The {@code elevation_m} value.
   *
   * @return the {@code elevation_m} value
   */
  public double elevationM() {
    return elevationM;
  }

  /**
   * The blend cycle this spot's forecast was driven with; older than {@code
   * model_run.initialized_at} when the newest run did not reach the spot.
   *
   * @return the {@code initialized_at} value
   */
  public Instant initializedAt() {
    return initializedAt;
  }

  /**
   * The snow-depth sensor at the spot. Absent when there is none.
   *
   * @return the {@code anchor} value, or empty when the response omits it
   */
  public Optional<SnowpackSpotAnchor> anchor() {
    return Optional.ofNullable(anchor);
  }

  /**
   * The {@code depth} value.
   *
   * @return the {@code depth} value
   */
  public SnowpackDepth depth() {
    return depth;
  }

  /**
   * The depth at {@code depth.start_time} less this spot's modelled depth 24 h before, in the
   * depth's units and rounding (cm or in; a loss is negative): the model's own change, snowfall,
   * settlement and melt together. Both sides are taken as {@code depth.p50} is for its {@code
   * basis}. The earlier depth is read from the newest earlier run whose start was at most 12 h
   * before that hour and whose depth had the same {@code basis}; it is never a longer-lead value.
   * Absent where there is no such run or either value is missing; never 0 in place of a value, and
   * a real zero change is {@code 0}.
   *
   * @return the {@code depth_change_24h} value, or empty when the response omits it
   */
  public OptionalDouble depthChange24h() {
    return depthChange24h == null ? OptionalDouble.empty() : OptionalDouble.of(depthChange24h);
  }

  /**
   * Hourly snow water equivalent (mm or in), parallel to {@code depth.p50}.
   *
   * @return the {@code swe} values; empty when the response has none
   */
  public List<Double> swe() {
    return swe;
  }

  /**
   * One entry every six hours.
   *
   * @return the {@code steps} values; empty when the response has none
   */
  public List<SnowpackStep> steps() {
    return steps;
  }

  /**
   * Buried layers at {@code depth.start_time}, at most three. Descriptive only.
   *
   * @return the {@code layers} values; empty when the response has none
   */
  public List<SnowpackLayer> layers() {
    return layers;
  }

  /**
   * The snow on a 38° slope at the spot facing each way, in the order north, east, south, west.
   * Present only where the spot's slopes were run: every named elevation of a ski area, and the
   * area's own unnamed spot at the 150 beta resorts. The fields above are always flat ground.
   * Descriptive only.
   *
   * @return the {@code aspects} values; empty when the response has none
   */
  public List<SnowpackAspect> aspects() {
    return aspects;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      name,
      displayName,
      elevation,
      elevationM,
      initializedAt,
      anchor,
      depth,
      depthChange24h,
      swe,
      steps,
      layers,
      aspects
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "name",
    "displayName",
    "elevation",
    "elevationM",
    "initializedAt",
    "anchor",
    "depth",
    "depthChange24h",
    "swe",
    "steps",
    "layers",
    "aspects"
  };

  /** Builds a {@link SnowpackSpot}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable String displayName;
    private @Nullable Double elevation;
    private @Nullable Double elevationM;
    private @Nullable Instant initializedAt;
    private @Nullable SnowpackSpotAnchor anchor;
    private @Nullable SnowpackDepth depth;
    private @Nullable Double depthChange24h;
    private List<Double> swe = List.of();
    private List<SnowpackStep> steps = List.of();
    private List<SnowpackLayer> layers = List.of();
    private List<SnowpackAspect> aspects = List.of();

    private Builder() {}

    /**
     * Sets {@link SnowpackSpot#name()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder name(@Nullable String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#displayName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder displayName(@Nullable String value) {
      this.displayName = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#elevationM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevationM(double value) {
      this.elevationM = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#initializedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder initializedAt(Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#anchor()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder anchor(@Nullable SnowpackSpotAnchor value) {
      this.anchor = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#depth()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder depth(SnowpackDepth value) {
      this.depth = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#depthChange24h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder depthChange24h(@Nullable Double value) {
      this.depthChange24h = value;
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#swe()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder swe(List<Double> values) {
      this.swe = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#steps()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder steps(List<SnowpackStep> values) {
      this.steps = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#layers()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder layers(List<SnowpackLayer> values) {
      this.layers = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackSpot#aspects()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder aspects(List<SnowpackAspect> values) {
      this.aspects = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link SnowpackSpot}.
     *
     * @return the immutable SnowpackSpot
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackSpot build() {
      return new SnowpackSpot(this);
    }
  }
}
