// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A GeoJSON {@code FeatureCollection} (RFC 7946) of the active winter and wind alerts' outlines,
 * with this response's {@code attribution} as a foreign member.
 *
 * <p>GeoJSON, passed through untouched (BEHAVIOR.md 3.0): {@code properties} and {@code
 * coordinates} are the API's JSON exactly, wire keys and all, ready for a map library.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AlertOutlineCollection}.
 */
public final class AlertOutlineCollection extends Model {
  private final String type;
  private final List<AlertOutlineFeature> features;
  private final ResponseAttribution attribution;
  private final ResponseMetadata metadata;

  private AlertOutlineCollection(Builder b) {
    this.type = require(b.type, "type");
    this.features = List.copyOf(b.features);
    this.attribution = require(b.attribution, "attribution");
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
    b.type = this.type;
    b.features = this.features;
    b.attribution = this.attribution;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code type} value. Always {@code FeatureCollection} today.
   *
   * @return the {@code type} value
   */
  public String type() {
    return type;
  }

  /**
   * One Feature per alert, ordered by {@code properties.id}.
   *
   * @return the {@code features} values; empty when the response has none
   */
  public List<AlertOutlineFeature> features() {
    return features;
  }

  /**
   * The {@code attribution} value.
   *
   * @return the {@code attribution} value
   */
  public ResponseAttribution attribution() {
    return attribution;
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
    return new Object[] {type, features, attribution};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"type", "features", "attribution"};

  /** Builds a {@link AlertOutlineCollection}. */
  public static final class Builder {
    private @Nullable String type;
    private List<AlertOutlineFeature> features = List.of();
    private @Nullable ResponseAttribution attribution;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link AlertOutlineCollection#type()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder type(String value) {
      this.type = value;
      return this;
    }

    /**
     * Sets {@link AlertOutlineCollection#features()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder features(List<AlertOutlineFeature> values) {
      this.features = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link AlertOutlineCollection#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(ResponseAttribution value) {
      this.attribution = value;
      return this;
    }

    /**
     * Sets {@link AlertOutlineCollection#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link AlertOutlineCollection}.
     *
     * @return the immutable AlertOutlineCollection
     * @throws IllegalStateException if a required value was not set
     */
    public AlertOutlineCollection build() {
      return new AlertOutlineCollection(this);
    }
  }
}
