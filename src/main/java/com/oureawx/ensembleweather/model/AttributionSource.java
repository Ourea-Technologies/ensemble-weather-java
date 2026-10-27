// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * A AttributionSource from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AttributionSource}.
 */
public final class AttributionSource extends Model {
  private final String id;
  private final String name;
  private final String kind;
  private final String provider;

  private AttributionSource(Builder b) {
    this.id = require(b.id, "id");
    this.name = require(b.name, "name");
    this.kind = require(b.kind, "kind");
    this.provider = require(b.provider, "provider");
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
    b.name = this.name;
    b.kind = this.kind;
    b.provider = this.provider;
    return b;
  }

  /**
   * The {@code id} value.
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * The {@code name} value.
   *
   * @return the {@code name} value
   */
  public String name() {
    return name;
  }

  /**
   * The {@code kind} value.
   *
   * @return the {@code kind} value ({@link SourceKind#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public SourceKind kind() {
    return SourceKind.fromWireValue(kind);
  }

  /**
   * The {@code kind} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code kind} string
   */
  public String kindAsString() {
    return kind;
  }

  /**
   * The {@code provider} value.
   *
   * @return the {@code provider} value
   */
  public String provider() {
    return provider;
  }

  @Override
  Object[] values() {
    return new Object[] {id, name, kind, provider};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"id", "name", "kind", "provider"};

  /** Builds a {@link AttributionSource}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String kind;
    private @Nullable String provider;

    private Builder() {}

    /**
     * Sets {@link AttributionSource#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link AttributionSource#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link AttributionSource#kind()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder kind(SourceKind value) {
      this.kind = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link AttributionSource#kindAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder kindAsString(String value) {
      this.kind = value;
      return this;
    }

    /**
     * Sets {@link AttributionSource#provider()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder provider(String value) {
      this.provider = value;
      return this;
    }

    /**
     * Builds the {@link AttributionSource}.
     *
     * @return the immutable AttributionSource
     * @throws IllegalStateException if a required value was not set
     */
    public AttributionSource build() {
      return new AttributionSource(this);
    }
  }
}
