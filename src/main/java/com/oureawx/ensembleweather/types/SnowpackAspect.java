// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * The snow on one slope at a spot, described as the spot's flat ground is: the model run on a slope
 * of {@code slope_angle_deg} facing {@code aspect}, with the flat ground's snowfall and the slope's
 * own sun. Modelled and never measured. Descriptive only.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackAspect}.
 */
public final class SnowpackAspect extends Model {
  private final String aspect;
  private final double slopeAngleDeg;
  private final SnowpackDepth depth;
  private final @Nullable Double depthChange24h;
  private final List<SnowpackStep> steps;
  private final List<SnowpackLayer> layers;

  private SnowpackAspect(Builder b) {
    this.aspect = require(b.aspect, "aspect");
    this.slopeAngleDeg = require(b.slopeAngleDeg, "slope_angle_deg");
    this.depth = require(b.depth, "depth");
    this.depthChange24h = b.depthChange24h;
    this.steps = List.copyOf(b.steps);
    this.layers = List.copyOf(b.layers);
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
    b.aspect = this.aspect;
    b.slopeAngleDeg = this.slopeAngleDeg;
    b.depth = this.depth;
    b.depthChange24h = this.depthChange24h;
    b.steps = this.steps;
    b.layers = this.layers;
    return b;
  }

  /**
   * The way the slope faces: today {@code north}, {@code east}, {@code south} or {@code west}.
   * Tolerate values you do not know.
   *
   * @return the {@code aspect} value
   */
  public String aspect() {
    return aspect;
  }

  /**
   * How steep the slope is, degrees from horizontal.
   *
   * @return the {@code slope_angle_deg} value
   */
  public double slopeAngleDeg() {
    return slopeAngleDeg;
  }

  /**
   * Hourly snow depth on the slope, measured vertically as the flat depth is. {@code basis} is
   * always {@code open_loop}.
   *
   * @return the {@code depth} value
   */
  public SnowpackDepth depth() {
    return depth;
  }

  /**
   * As the spot's {@code depth_change_24h}, this slope's own (open loop on both sides), read from
   * the same earlier run as the spot's and from no other. Absent where the spot's has no earlier
   * run, that run kept no such slope, or a value is missing.
   *
   * @return the {@code depth_change_24h} value, or empty when the response omits it
   */
  public OptionalDouble depthChange24h() {
    return depthChange24h == null ? OptionalDouble.empty() : OptionalDouble.of(depthChange24h);
  }

  /**
   * One entry every six hours, at the spot's own {@code steps[].valid_time}.
   *
   * @return the {@code steps} values; empty when the response has none
   */
  public List<SnowpackStep> steps() {
    return steps;
  }

  /**
   * Buried layers on the slope at {@code depth.start_time}, at most three. Descriptive only.
   *
   * @return the {@code layers} values; empty when the response has none
   */
  public List<SnowpackLayer> layers() {
    return layers;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {aspect, slopeAngleDeg, depth, depthChange24h, steps, layers};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "aspect", "slopeAngleDeg", "depth", "depthChange24h", "steps", "layers"
  };

  /** Builds a {@link SnowpackAspect}. */
  public static final class Builder {
    private @Nullable String aspect;
    private @Nullable Double slopeAngleDeg;
    private @Nullable SnowpackDepth depth;
    private @Nullable Double depthChange24h;
    private List<SnowpackStep> steps = List.of();
    private List<SnowpackLayer> layers = List.of();

    private Builder() {}

    /**
     * Sets {@link SnowpackAspect#aspect()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder aspect(String value) {
      this.aspect = value;
      return this;
    }

    /**
     * Sets {@link SnowpackAspect#slopeAngleDeg()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slopeAngleDeg(double value) {
      this.slopeAngleDeg = value;
      return this;
    }

    /**
     * Sets {@link SnowpackAspect#depth()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder depth(SnowpackDepth value) {
      this.depth = value;
      return this;
    }

    /**
     * Sets {@link SnowpackAspect#depthChange24h()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder depthChange24h(@Nullable Double value) {
      this.depthChange24h = value;
      return this;
    }

    /**
     * Sets {@link SnowpackAspect#steps()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder steps(List<SnowpackStep> values) {
      this.steps = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackAspect#layers()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder layers(List<SnowpackLayer> values) {
      this.layers = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link SnowpackAspect}.
     *
     * @return the immutable SnowpackAspect
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackAspect build() {
      return new SnowpackAspect(this);
    }
  }
}
