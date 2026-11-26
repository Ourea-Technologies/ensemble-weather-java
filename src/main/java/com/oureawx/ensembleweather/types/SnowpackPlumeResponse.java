// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A location's snow-depth plume.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackPlumeResponse}.
 */
public final class SnowpackPlumeResponse extends Model {
  private final boolean beta;
  private final LocationRef location;
  private final String units;
  private final @Nullable SnowpackRun modelRun;
  private final boolean stale;
  private final @Nullable SnowpackPlumeDepth depth;
  private final List<SnowpackPlumeModel> models;
  private final List<List<Double>> memberDepths;
  private final String spread;
  private final ResponseMetadata metadata;

  private SnowpackPlumeResponse(Builder b) {
    this.beta = require(b.beta, "beta");
    this.location = require(b.location, "location");
    this.units = require(b.units, "units");
    this.modelRun = b.modelRun;
    this.stale = require(b.stale, "stale");
    this.depth = b.depth;
    this.models = List.copyOf(b.models);
    this.memberDepths = List.copyOf(b.memberDepths);
    this.spread = require(b.spread, "spread");
    this.metadata = b.metadata;
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
    b.location = this.location;
    b.units = this.units;
    b.modelRun = this.modelRun;
    b.stale = this.stale;
    b.depth = this.depth;
    b.models = this.models;
    b.memberDepths = this.memberDepths;
    b.spread = this.spread;
    b.metadata = this.metadata;
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
   * The {@code location} value.
   *
   * @return the {@code location} value
   */
  public LocationRef location() {
    return location;
  }

  /**
   * The {@code units} value.
   *
   * @return the {@code units} value ({@link UnitSystem#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public UnitSystem units() {
    return UnitSystem.fromWireValue(units);
  }

  /**
   * The {@code units} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code units} string
   */
  public String unitsAsString() {
    return units;
  }

  /**
   * The run the plume is from: the 12Z blend cycle the members were read from. Absent when no
   * member stage has reached the location.
   *
   * @return the {@code model_run} value, or empty when the response omits it
   */
  public Optional<SnowpackRun> modelRun() {
    return Optional.ofNullable(modelRun);
  }

  /**
   * The plume's blend cycle is more than 24 h old.
   *
   * @return the {@code stale} value
   */
  public boolean stale() {
    return stale;
  }

  /**
   * The members' depth at each step. Absent when there is no plume.
   *
   * @return the {@code depth} value, or empty when the response omits it
   */
  public Optional<SnowpackPlumeDepth> depth() {
    return Optional.ofNullable(depth);
  }

  /**
   * Every member's own series, by model, each model to its own horizon. Plus and Pro ({@code
   * plume_detail}); absent for Free and anonymous callers, who are served {@code member_depths}.
   *
   * @return the {@code models} values; empty when the response has none
   */
  public List<SnowpackPlumeModel> models() {
    return models;
  }

  /**
   * Every member's depth as ONE unlabelled pool, for Free and anonymous callers (absent for Plus
   * and Pro, who are served {@code models}). One series per member, from {@code depth.start_time}
   * every {@code depth.interval_hours}, each exactly the length of {@code depth.mean} (the body
   * ends at the last step every member reaches, so no series is shorter than another). There is no
   * member id and no model; the order is fixed for the run and the location and does not follow the
   * models. Units as {@code depth}. {@code depth.members} is the number of series.
   *
   * @return the {@code member_depths} values; empty when the response has none
   */
  public List<List<Double>> memberDepths() {
    return memberDepths;
  }

  /**
   * What the bands are, in a sentence a client may show beside them.
   *
   * @return the {@code spread} value
   */
  public String spread() {
    return spread;
  }

  /**
   * What the HTTP exchange said about this response: rate-limit state, request id, and whether it
   * was revalidated from the local cache. Not part of {@link #equals(Object)}.
   *
   * @return the response metadata
   */
  public ResponseMetadata metadata() {
    return metadata;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      beta, location, units, modelRun, stale, depth, models, memberDepths, spread
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "beta", "location", "units", "modelRun", "stale", "depth", "models", "memberDepths", "spread"
  };

  /** Builds a {@link SnowpackPlumeResponse}. */
  public static final class Builder {
    private @Nullable Boolean beta;
    private @Nullable LocationRef location;
    private @Nullable String units;
    private @Nullable SnowpackRun modelRun;
    private @Nullable Boolean stale;
    private @Nullable SnowpackPlumeDepth depth;
    private List<SnowpackPlumeModel> models = List.of();
    private List<List<Double>> memberDepths = List.of();
    private @Nullable String spread;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link SnowpackPlumeResponse#beta()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder beta(boolean value) {
      this.beta = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#units()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder units(UnitSystem value) {
      this.units = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#unitsAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder unitsAsString(String value) {
      this.units = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#modelRun()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder modelRun(@Nullable SnowpackRun value) {
      this.modelRun = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#stale()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stale(boolean value) {
      this.stale = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#depth()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder depth(@Nullable SnowpackPlumeDepth value) {
      this.depth = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#models()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder models(List<SnowpackPlumeModel> values) {
      this.models = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#memberDepths()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder memberDepths(List<List<Double>> values) {
      this.memberDepths = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#spread()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder spread(String value) {
      this.spread = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackPlumeResponse}.
     *
     * @return the immutable SnowpackPlumeResponse
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackPlumeResponse build() {
      return new SnowpackPlumeResponse(this);
    }
  }
}
