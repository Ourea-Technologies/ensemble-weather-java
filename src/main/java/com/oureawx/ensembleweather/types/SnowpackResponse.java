// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The snowpack forecast of a location's spots.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackResponse}.
 */
public final class SnowpackResponse extends Model {
  private final boolean beta;
  private final LocationRef location;
  private final String units;
  private final @Nullable SnowpackRun modelRun;
  private final boolean stale;
  private final List<SnowpackSpot> spots;
  private final SnowpackResponseDefinitions definitions;
  private final ResponseMetadata metadata;

  private SnowpackResponse(Builder b) {
    this.beta = require(b.beta, "beta");
    this.location = require(b.location, "location");
    this.units = require(b.units, "units");
    this.modelRun = b.modelRun;
    this.stale = require(b.stale, "stale");
    this.spots = List.copyOf(b.spots);
    this.definitions = require(b.definitions, "definitions");
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
    b.spots = this.spots;
    b.definitions = this.definitions;
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
   * The {@code model_run} value.
   *
   * @return the {@code model_run} value, or empty when the response omits it
   */
  public Optional<SnowpackRun> modelRun() {
    return Optional.ofNullable(modelRun);
  }

  /**
   * The newest run's blend cycle is more than 24 h old.
   *
   * @return the {@code stale} value
   */
  public boolean stale() {
    return stale;
  }

  /**
   * The location's own point first, then its named elevations in their display order.
   *
   * @return the {@code spots} values; empty when the response has none
   */
  public List<SnowpackSpot> spots() {
    return spots;
  }

  /**
   * The {@code definitions} value.
   *
   * @return the {@code definitions} value
   */
  public SnowpackResponseDefinitions definitions() {
    return definitions;
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
    return new Object[] {beta, location, units, modelRun, stale, spots, definitions};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "beta", "location", "units", "modelRun", "stale", "spots", "definitions"
  };

  /** Builds a {@link SnowpackResponse}. */
  public static final class Builder {
    private @Nullable Boolean beta;
    private @Nullable LocationRef location;
    private @Nullable String units;
    private @Nullable SnowpackRun modelRun;
    private @Nullable Boolean stale;
    private List<SnowpackSpot> spots = List.of();
    private @Nullable SnowpackResponseDefinitions definitions;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link SnowpackResponse#beta()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder beta(boolean value) {
      this.beta = value;
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(LocationRef value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#units()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder units(UnitSystem value) {
      this.units = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#unitsAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder unitsAsString(String value) {
      this.units = value;
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#modelRun()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder modelRun(@Nullable SnowpackRun value) {
      this.modelRun = value;
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#stale()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stale(boolean value) {
      this.stale = value;
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#spots()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder spots(List<SnowpackSpot> values) {
      this.spots = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#definitions()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder definitions(SnowpackResponseDefinitions value) {
      this.definitions = value;
      return this;
    }

    /**
     * Sets {@link SnowpackResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackResponse}.
     *
     * @return the immutable SnowpackResponse
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackResponse build() {
      return new SnowpackResponse(this);
    }
  }
}
