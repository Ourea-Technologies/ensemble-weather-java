// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * What the snowpack model states is on the ground at a spot at one hour. {@code depth} is the same
 * number as {@code depth.p50} of that hour on <code>GET
 * /locations/&#123;state&#125;/&#123;slug&#125;/snowpack</code>, which also serves the depth's
 * range, its hourly series, the six-hourly steps and the layer notes. This is a description of the
 * snow, not an avalanche forecast: nothing here is a stability or hazard statement.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowOnGround}.
 */
public final class SnowOnGround extends Model {
  private final boolean beta;
  private final double depth;
  private final String basis;
  private final @Nullable String surface;
  private final Instant validTime;
  private final Instant generatedAt;

  private SnowOnGround(Builder b) {
    this.beta = require(b.beta, "beta");
    this.depth = require(b.depth, "depth");
    this.basis = require(b.basis, "basis");
    this.surface = b.surface;
    this.validTime = require(b.validTime, "valid_time");
    this.generatedAt = require(b.generatedAt, "generated_at");
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
    b.beta = this.beta;
    b.depth = this.depth;
    b.basis = this.basis;
    b.surface = this.surface;
    b.validTime = this.validTime;
    b.generatedAt = this.generatedAt;
    return b;
  }

  /**
   * Always {@code true} while the product is a beta; fields may be added.
   *
   * @return the {@code beta} value
   */
  public boolean beta() {
    return beta;
  }

  /**
   * Snow depth: cm, or in (imperial).
   *
   * @return the {@code depth} value
   */
  public double depth() {
    return depth;
  }

  /**
   * {@code measured}: the run started from a snow-depth sensor at the spot. {@code open_loop}: the
   * model's own state, with the measured median correction of such depths. An open set: treat an
   * unknown value as {@code open_loop}.
   *
   * @return the {@code basis} value
   */
  public String basis() {
    return basis;
  }

  /**
   * The surface class at the hour; {@code /snowpack}'s {@code definitions.surface} carries the text
   * of each value (an open set). Omitted before the run's first six-hourly step.
   *
   * @return the {@code surface} value, or empty when the response omits it
   */
  public Optional<String> surface() {
    return Optional.ofNullable(surface);
  }

  /**
   * The hour the depth is for.
   *
   * @return the {@code valid_time} value
   */
  public Instant validTime() {
    return validTime;
  }

  /**
   * When the snowpack run that produced it finished.
   *
   * @return the {@code generated_at} value
   */
  public Instant generatedAt() {
    return generatedAt;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {beta, depth, basis, surface, validTime, generatedAt};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "beta", "depth", "basis", "surface", "validTime", "generatedAt"
  };

  /** Builds a {@link SnowOnGround}. */
  public static final class Builder {
    private @Nullable Boolean beta;
    private @Nullable Double depth;
    private @Nullable String basis;
    private @Nullable String surface;
    private @Nullable Instant validTime;
    private @Nullable Instant generatedAt;

    private Builder() {}

    /**
     * Sets {@link SnowOnGround#beta()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder beta(boolean value) {
      this.beta = value;
      return this;
    }

    /**
     * Sets {@link SnowOnGround#depth()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder depth(double value) {
      this.depth = value;
      return this;
    }

    /**
     * Sets {@link SnowOnGround#basis()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder basis(String value) {
      this.basis = value;
      return this;
    }

    /**
     * Sets {@link SnowOnGround#surface()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder surface(@Nullable String value) {
      this.surface = value;
      return this;
    }

    /**
     * Sets {@link SnowOnGround#validTime()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder validTime(Instant value) {
      this.validTime = value;
      return this;
    }

    /**
     * Sets {@link SnowOnGround#generatedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder generatedAt(Instant value) {
      this.generatedAt = value;
      return this;
    }

    /**
     * Builds the {@link SnowOnGround}.
     *
     * @return the immutable SnowOnGround
     * @throws IllegalStateException if a required value was not set
     */
    public SnowOnGround build() {
      return new SnowOnGround(this);
    }
  }
}
