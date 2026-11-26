// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One page of a ranking.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ExploreResponse}.
 */
public final class ExploreResponse extends Model {
  private final String metric;
  private final String order;
  private final String units;
  private final String unit;
  private final List<ExploreItem> items;
  private final int count;
  private final @Nullable String nextCursor;
  private final ResponseMetadata metadata;

  private ExploreResponse(Builder b) {
    this.metric = require(b.metric, "metric");
    this.order = require(b.order, "order");
    this.units = require(b.units, "units");
    this.unit = require(b.unit, "unit");
    this.items = List.copyOf(b.items);
    this.count = require(b.count, "count");
    this.nextCursor = b.nextCursor;
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
    b.metric = this.metric;
    b.order = this.order;
    b.units = this.units;
    b.unit = this.unit;
    b.items = this.items;
    b.count = this.count;
    b.nextCursor = this.nextCursor;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code metric} value.
   *
   * @return the {@code metric} value ({@link ExploreMetric#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public ExploreMetric metric() {
    return ExploreMetric.fromWireValue(metric);
  }

  /**
   * The {@code metric} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code metric} string
   */
  public String metricAsString() {
    return metric;
  }

  /**
   * The {@code order} value. One of {@code desc}, {@code asc}; read it as a string, since the API
   * may add values.
   *
   * @return the {@code order} value
   */
  public String order() {
    return order;
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
   * The unit of every {@code value}, {@code p10} and {@code p90}. One of {@code mm}, {@code cm},
   * {@code in}, {@code percent}; read it as a string, since the API may add values.
   *
   * @return the {@code unit} value
   */
  public String unit() {
    return unit;
  }

  /**
   * The {@code items} value.
   *
   * @return the {@code items} values; empty when the response has none
   */
  public List<ExploreItem> items() {
    return items;
  }

  /**
   * Items on this page.
   *
   * @return the {@code count} value
   */
  public int count() {
    return count;
  }

  /**
   * Pass as {@code ?cursor=} (same other parameters) for the next page; null at the end.
   *
   * @return the {@code next_cursor} value, or empty when the response omits it
   */
  public Optional<String> nextCursor() {
    return Optional.ofNullable(nextCursor);
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
    return new Object[] {metric, order, units, unit, items, count, nextCursor};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "metric", "order", "units", "unit", "items", "count", "nextCursor"
  };

  /** Builds a {@link ExploreResponse}. */
  public static final class Builder {
    private @Nullable String metric;
    private @Nullable String order;
    private @Nullable String units;
    private @Nullable String unit;
    private List<ExploreItem> items = List.of();
    private @Nullable Integer count;
    private @Nullable String nextCursor;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link ExploreResponse#metric()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder metric(ExploreMetric value) {
      this.metric = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ExploreResponse#metricAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder metricAsString(String value) {
      this.metric = value;
      return this;
    }

    /**
     * Sets {@link ExploreResponse#order()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder order(String value) {
      this.order = value;
      return this;
    }

    /**
     * Sets {@link ExploreResponse#units()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder units(UnitSystem value) {
      this.units = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ExploreResponse#unitsAsString()}, the raw wire value, kept verbatim even when
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
     * Sets {@link ExploreResponse#unit()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder unit(String value) {
      this.unit = value;
      return this;
    }

    /**
     * Sets {@link ExploreResponse#items()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder items(List<ExploreItem> values) {
      this.items = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ExploreResponse#count()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder count(int value) {
      this.count = value;
      return this;
    }

    /**
     * Sets {@link ExploreResponse#nextCursor()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder nextCursor(@Nullable String value) {
      this.nextCursor = value;
      return this;
    }

    /**
     * Sets {@link ExploreResponse#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link ExploreResponse}.
     *
     * @return the immutable ExploreResponse
     * @throws IllegalStateException if a required value was not set
     */
    public ExploreResponse build() {
      return new ExploreResponse(this);
    }
  }
}
