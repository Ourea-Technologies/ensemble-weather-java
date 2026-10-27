// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Closed key set — unknown keys are a {@code 400}. The photo is referenced <b>by id</b>, never a
 * literal URL.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CmsAdCreativeContent}.
 */
public final class CmsAdCreativeContent extends Model {
  private final @Nullable String headline;
  private final @Nullable String body;
  private final @Nullable String cta;
  private final @Nullable String clickUrl;
  private final @Nullable String photoId;

  private CmsAdCreativeContent(Builder b) {
    this.headline = b.headline;
    this.body = b.body;
    this.cta = b.cta;
    this.clickUrl = b.clickUrl;
    this.photoId = b.photoId;
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
    b.headline = this.headline;
    b.body = this.body;
    b.cta = this.cta;
    b.clickUrl = this.clickUrl;
    b.photoId = this.photoId;
    return b;
  }

  /**
   * The {@code headline} value.
   *
   * @return the {@code headline} value, or empty when the response omits it
   */
  public Optional<String> headline() {
    return Optional.ofNullable(headline);
  }

  /**
   * The {@code body} value.
   *
   * @return the {@code body} value, or empty when the response omits it
   */
  public Optional<String> body() {
    return Optional.ofNullable(body);
  }

  /**
   * Call-to-action label.
   *
   * @return the {@code cta} value, or empty when the response omits it
   */
  public Optional<String> cta() {
    return Optional.ofNullable(cta);
  }

  /**
   * Must be http(s).
   *
   * @return the {@code click_url} value, or empty when the response omits it
   */
  public Optional<String> clickUrl() {
    return Optional.ofNullable(clickUrl);
  }

  /**
   * Must reference an existing photo row.
   *
   * @return the {@code photo_id} value, or empty when the response omits it
   */
  public Optional<String> photoId() {
    return Optional.ofNullable(photoId);
  }

  @Override
  Object[] values() {
    return new Object[] {headline, body, cta, clickUrl, photoId};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"headline", "body", "cta", "clickUrl", "photoId"};

  /** Builds a {@link CmsAdCreativeContent}. */
  public static final class Builder {
    private @Nullable String headline;
    private @Nullable String body;
    private @Nullable String cta;
    private @Nullable String clickUrl;
    private @Nullable String photoId;

    private Builder() {}

    /**
     * Sets {@link CmsAdCreativeContent#headline()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder headline(@Nullable String value) {
      this.headline = value;
      return this;
    }

    /**
     * Sets {@link CmsAdCreativeContent#body()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder body(@Nullable String value) {
      this.body = value;
      return this;
    }

    /**
     * Sets {@link CmsAdCreativeContent#cta()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cta(@Nullable String value) {
      this.cta = value;
      return this;
    }

    /**
     * Sets {@link CmsAdCreativeContent#clickUrl()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder clickUrl(@Nullable String value) {
      this.clickUrl = value;
      return this;
    }

    /**
     * Sets {@link CmsAdCreativeContent#photoId()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder photoId(@Nullable String value) {
      this.photoId = value;
      return this;
    }

    /**
     * Builds the {@link CmsAdCreativeContent}.
     *
     * @return the immutable CmsAdCreativeContent
     * @throws IllegalStateException if a required value was not set
     */
    public CmsAdCreativeContent build() {
      return new CmsAdCreativeContent(this);
    }
  }
}
