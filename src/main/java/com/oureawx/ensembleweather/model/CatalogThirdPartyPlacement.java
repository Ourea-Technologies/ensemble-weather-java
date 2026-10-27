// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * A resolved third-party placement (network config served verbatim).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CatalogThirdPartyPlacement}.
 */
public final class CatalogThirdPartyPlacement extends Model {
  private final String unitId;
  private final String provider;
  private final JsonValue config;

  private CatalogThirdPartyPlacement(Builder b) {
    this.unitId = require(b.unitId, "unit_id");
    this.provider = require(b.provider, "provider");
    this.config = require(b.config, "config");
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
    b.unitId = this.unitId;
    b.provider = this.provider;
    b.config = this.config;
    return b;
  }

  /**
   * The {@code unit_id} value.
   *
   * @return the {@code unit_id} value
   */
  public String unitId() {
    return unitId;
  }

  /**
   * The {@code provider} value.
   *
   * @return the {@code provider} value ({@link AdProvider#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public AdProvider provider() {
    return AdProvider.fromWireValue(provider);
  }

  /**
   * The {@code provider} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code provider} string
   */
  public String providerAsString() {
    return provider;
  }

  /**
   * The {@code config} value.
   *
   * @return the {@code config} value
   */
  public JsonValue config() {
    return config;
  }

  @Override
  Object[] values() {
    return new Object[] {unitId, provider, config};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"unitId", "provider", "config"};

  /** Builds a {@link CatalogThirdPartyPlacement}. */
  public static final class Builder {
    private @Nullable String unitId;
    private @Nullable String provider;
    private @Nullable JsonValue config;

    private Builder() {}

    /**
     * Sets {@link CatalogThirdPartyPlacement#unitId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder unitId(String value) {
      this.unitId = value;
      return this;
    }

    /**
     * Sets {@link CatalogThirdPartyPlacement#provider()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder provider(AdProvider value) {
      this.provider = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogThirdPartyPlacement#providerAsString()}, the raw wire value, kept verbatim
     * even when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder providerAsString(String value) {
      this.provider = value;
      return this;
    }

    /**
     * Sets {@link CatalogThirdPartyPlacement#config()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder config(JsonValue value) {
      this.config = value;
      return this;
    }

    /**
     * Builds the {@link CatalogThirdPartyPlacement}.
     *
     * @return the immutable CatalogThirdPartyPlacement
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogThirdPartyPlacement build() {
      return new CatalogThirdPartyPlacement(this);
    }
  }
}
