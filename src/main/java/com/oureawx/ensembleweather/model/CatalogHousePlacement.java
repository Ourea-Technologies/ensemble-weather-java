// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * A resolved house placement (campaign + creative content).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CatalogHousePlacement}.
 */
public final class CatalogHousePlacement extends Model {
  private final String campaignId;
  private final String creativeId;
  private final CmsAdCreativeContent content;

  private CatalogHousePlacement(Builder b) {
    this.campaignId = require(b.campaignId, "campaign_id");
    this.creativeId = require(b.creativeId, "creative_id");
    this.content = require(b.content, "content");
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
    b.campaignId = this.campaignId;
    b.creativeId = this.creativeId;
    b.content = this.content;
    return b;
  }

  /**
   * The {@code campaign_id} value.
   *
   * @return the {@code campaign_id} value
   */
  public String campaignId() {
    return campaignId;
  }

  /**
   * The {@code creative_id} value.
   *
   * @return the {@code creative_id} value
   */
  public String creativeId() {
    return creativeId;
  }

  /**
   * The {@code content} value.
   *
   * @return the {@code content} value
   */
  public CmsAdCreativeContent content() {
    return content;
  }

  @Override
  Object[] values() {
    return new Object[] {campaignId, creativeId, content};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"campaignId", "creativeId", "content"};

  /** Builds a {@link CatalogHousePlacement}. */
  public static final class Builder {
    private @Nullable String campaignId;
    private @Nullable String creativeId;
    private @Nullable CmsAdCreativeContent content;

    private Builder() {}

    /**
     * Sets {@link CatalogHousePlacement#campaignId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder campaignId(String value) {
      this.campaignId = value;
      return this;
    }

    /**
     * Sets {@link CatalogHousePlacement#creativeId()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder creativeId(String value) {
      this.creativeId = value;
      return this;
    }

    /**
     * Sets {@link CatalogHousePlacement#content()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder content(CmsAdCreativeContent value) {
      this.content = value;
      return this;
    }

    /**
     * Builds the {@link CatalogHousePlacement}.
     *
     * @return the immutable CatalogHousePlacement
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogHousePlacement build() {
      return new CatalogHousePlacement(this);
    }
  }
}
