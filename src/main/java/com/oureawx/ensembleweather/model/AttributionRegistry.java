// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;

/**
 * A AttributionRegistry from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AttributionRegistry}.
 */
public final class AttributionRegistry extends Model {
  private final List<AttributionProvider> providers;
  private final ResponseMetadata metadata;

  private AttributionRegistry(Builder b) {
    this.providers = List.copyOf(b.providers);
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
    b.providers = this.providers;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code providers} value.
   *
   * @return the {@code providers} values; empty when the response has none
   */
  public List<AttributionProvider> providers() {
    return providers;
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
  Object[] values() {
    return new Object[] {providers};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"providers"};

  /** Builds a {@link AttributionRegistry}. */
  public static final class Builder {
    private List<AttributionProvider> providers = List.of();
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link AttributionRegistry#providers()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder providers(List<AttributionProvider> values) {
      this.providers = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link AttributionRegistry#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link AttributionRegistry}.
     *
     * @return the immutable AttributionRegistry
     * @throws IllegalStateException if a required value was not set
     */
    public AttributionRegistry build() {
      return new AttributionRegistry(this);
    }
  }
}
