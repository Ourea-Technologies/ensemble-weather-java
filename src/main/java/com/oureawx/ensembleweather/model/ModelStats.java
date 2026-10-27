// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A ModelStats from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ModelStats}.
 */
public final class ModelStats extends Model {
  private final String id;
  private final @Nullable Quantiles temperature;
  private final @Nullable Quantiles dewpoint;
  private final @Nullable Quantiles precipitation;
  private final @Nullable Quantiles windSpeed;
  private final @Nullable Quantiles windDirection;
  private final @Nullable Quantiles snowLevel;
  private final @Nullable Quantiles slr;
  private final @Nullable Quantiles snowfall;
  private final @Nullable Boolean interpolated;

  private ModelStats(Builder b) {
    this.id = require(b.id, "id");
    this.temperature = b.temperature;
    this.dewpoint = b.dewpoint;
    this.precipitation = b.precipitation;
    this.windSpeed = b.windSpeed;
    this.windDirection = b.windDirection;
    this.snowLevel = b.snowLevel;
    this.slr = b.slr;
    this.snowfall = b.snowfall;
    this.interpolated = b.interpolated;
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
    b.id = this.id;
    b.temperature = this.temperature;
    b.dewpoint = this.dewpoint;
    b.precipitation = this.precipitation;
    b.windSpeed = this.windSpeed;
    b.windDirection = this.windDirection;
    b.snowLevel = this.snowLevel;
    b.slr = this.slr;
    b.snowfall = this.snowfall;
    b.interpolated = this.interpolated;
    return b;
  }

  /**
   * The model id ({@code aifs}, {@code aigefs}, {@code gefs}, {@code ifs_ens}, {@code rrfs}; treat
   * others as additive).
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * Absent when this model published nothing for the hour; never 0. The global ensembles publish on
   * a 6-hourly step, so at five lead hours out of six a contributing model has nothing to report.
   *
   * @return the {@code temperature} value, or empty when the response omits it
   */
  public Optional<Quantiles> temperature() {
    return Optional.ofNullable(temperature);
  }

  /**
   * Always absent for {@code aigefs}, which publishes no 2 m humidity and no surface pressure: the
   * blended dewpoint, relative humidity and feels-like are pooled from the models that publish one,
   * their weights renormalised over them.
   *
   * @return the {@code dewpoint} value, or empty when the response omits it
   */
  public Optional<Quantiles> dewpoint() {
    return Optional.ofNullable(dewpoint);
  }

  /**
   * Absent when this model published nothing for the hour; never 0.
   *
   * @return the {@code precipitation} value, or empty when the response omits it
   */
  public Optional<Quantiles> precipitation() {
    return Optional.ofNullable(precipitation);
  }

  /**
   * Absent when this model published nothing for the hour; never 0.
   *
   * @return the {@code wind_speed} value, or empty when the response omits it
   */
  public Optional<Quantiles> windSpeed() {
    return Optional.ofNullable(windSpeed);
  }

  /**
   * Absent when this model published nothing for the hour; never 0.
   *
   * @return the {@code wind_direction} value, or empty when the response omits it
   */
  public Optional<Quantiles> windDirection() {
    return Optional.ofNullable(windDirection);
  }

  /**
   * The {@code snow_level} value.
   *
   * @return the {@code snow_level} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowLevel() {
    return Optional.ofNullable(snowLevel);
  }

  /**
   * The {@code slr} value.
   *
   * @return the {@code slr} value, or empty when the response omits it
   */
  public Optional<Quantiles> slr() {
    return Optional.ofNullable(slr);
  }

  /**
   * The {@code snowfall} value.
   *
   * @return the {@code snowfall} value, or empty when the response omits it
   */
  public Optional<Quantiles> snowfall() {
    return Optional.ofNullable(snowfall);
  }

  /**
   * {@code True} when this model did not publish at this lead and the stats above were interpolated
   * from its two bracketing native steps, so the blend pools the same set of contributors at every
   * lead. Omitted (absent) when the model published at this hour, which is the ordinary case —
   * treat absence as {@code false}. The global ensembles publish 6-hourly and the
   * convection-allowing model is hourly, so at five lead hours out of six the 6-hourly models' rows
   * carry this flag. NOTE — {@code models=true} and {@code members=true} deliberately disagree
   * here, and the disagreement is the honest answer to two different questions. A model's per-hour
   * STATS can be interpolated to a lead it did not publish at, because a distribution between two
   * distributions is a distribution. Its per-hour MEMBERS cannot: member 7 at lead 3 is a specific
   * draw from a specific run, and interpolating member 7's two neighbouring values would invent a
   * trajectory that ensemble never produced and would break the member-to-member correlation every
   * spaghetti plot and every ECC reordering depends on. So the same hour serves {@code
   * model_stats.aifs.temperature} with {@code interpolated: true} and {@code
   * members.aifs[*].temperature = null}. The stats are the blend's input; the members are the raw
   * draws.
   *
   * @return the {@code interpolated} value, or empty when the response omits it
   */
  public Optional<Boolean> interpolated() {
    return Optional.ofNullable(interpolated);
  }

  @Override
  Object[] values() {
    return new Object[] {
      id,
      temperature,
      dewpoint,
      precipitation,
      windSpeed,
      windDirection,
      snowLevel,
      slr,
      snowfall,
      interpolated
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id",
    "temperature",
    "dewpoint",
    "precipitation",
    "windSpeed",
    "windDirection",
    "snowLevel",
    "slr",
    "snowfall",
    "interpolated"
  };

  /** Builds a {@link ModelStats}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable Quantiles temperature;
    private @Nullable Quantiles dewpoint;
    private @Nullable Quantiles precipitation;
    private @Nullable Quantiles windSpeed;
    private @Nullable Quantiles windDirection;
    private @Nullable Quantiles snowLevel;
    private @Nullable Quantiles slr;
    private @Nullable Quantiles snowfall;
    private @Nullable Boolean interpolated;

    private Builder() {}

    /**
     * Sets {@link ModelStats#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#temperature()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder temperature(@Nullable Quantiles value) {
      this.temperature = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#dewpoint()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder dewpoint(@Nullable Quantiles value) {
      this.dewpoint = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#precipitation()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder precipitation(@Nullable Quantiles value) {
      this.precipitation = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#windSpeed()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windSpeed(@Nullable Quantiles value) {
      this.windSpeed = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#windDirection()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder windDirection(@Nullable Quantiles value) {
      this.windDirection = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#snowLevel()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowLevel(@Nullable Quantiles value) {
      this.snowLevel = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#slr()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder slr(@Nullable Quantiles value) {
      this.slr = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#snowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder snowfall(@Nullable Quantiles value) {
      this.snowfall = value;
      return this;
    }

    /**
     * Sets {@link ModelStats#interpolated()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder interpolated(@Nullable Boolean value) {
      this.interpolated = value;
      return this;
    }

    /**
     * Builds the {@link ModelStats}.
     *
     * @return the immutable ModelStats
     * @throws IllegalStateException if a required value was not set
     */
    public ModelStats build() {
      return new ModelStats(this);
    }
  }
}
