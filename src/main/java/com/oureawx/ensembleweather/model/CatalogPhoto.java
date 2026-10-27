// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Photo with the storage backend already resolved to a single public {@code url} (GCS object or
 * external link).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogPhoto}.
 */
public final class CatalogPhoto extends Model {
  private final String url;
  private final @Nullable String caption;
  private final @Nullable String credit;
  private final @Nullable String altText;
  private final @Nullable String license;
  private final @Nullable String licenseUrl;

  private CatalogPhoto(Builder b) {
    this.url = require(b.url, "url");
    this.caption = b.caption;
    this.credit = b.credit;
    this.altText = b.altText;
    this.license = b.license;
    this.licenseUrl = b.licenseUrl;
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
    b.url = this.url;
    b.caption = this.caption;
    b.credit = this.credit;
    b.altText = this.altText;
    b.license = this.license;
    b.licenseUrl = this.licenseUrl;
    return b;
  }

  /**
   * The {@code url} value.
   *
   * @return the {@code url} value
   */
  public String url() {
    return url;
  }

  /**
   * The {@code caption} value.
   *
   * @return the {@code caption} value, or empty when the response omits it
   */
  public Optional<String> caption() {
    return Optional.ofNullable(caption);
  }

  /**
   * The {@code credit} value.
   *
   * @return the {@code credit} value, or empty when the response omits it
   */
  public Optional<String> credit() {
    return Optional.ofNullable(credit);
  }

  /**
   * The {@code alt_text} value.
   *
   * @return the {@code alt_text} value, or empty when the response omits it
   */
  public Optional<String> altText() {
    return Optional.ofNullable(altText);
  }

  /**
   * The {@code license} value.
   *
   * @return the {@code license} value, or empty when the response omits it
   */
  public Optional<String> license() {
    return Optional.ofNullable(license);
  }

  /**
   * The {@code license_url} value.
   *
   * @return the {@code license_url} value, or empty when the response omits it
   */
  public Optional<String> licenseUrl() {
    return Optional.ofNullable(licenseUrl);
  }

  @Override
  Object[] values() {
    return new Object[] {url, caption, credit, altText, license, licenseUrl};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "url", "caption", "credit", "altText", "license", "licenseUrl"
  };

  /** Builds a {@link CatalogPhoto}. */
  public static final class Builder {
    private @Nullable String url;
    private @Nullable String caption;
    private @Nullable String credit;
    private @Nullable String altText;
    private @Nullable String license;
    private @Nullable String licenseUrl;

    private Builder() {}

    /**
     * Sets {@link CatalogPhoto#url()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder url(String value) {
      this.url = value;
      return this;
    }

    /**
     * Sets {@link CatalogPhoto#caption()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder caption(@Nullable String value) {
      this.caption = value;
      return this;
    }

    /**
     * Sets {@link CatalogPhoto#credit()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder credit(@Nullable String value) {
      this.credit = value;
      return this;
    }

    /**
     * Sets {@link CatalogPhoto#altText()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder altText(@Nullable String value) {
      this.altText = value;
      return this;
    }

    /**
     * Sets {@link CatalogPhoto#license()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder license(@Nullable String value) {
      this.license = value;
      return this;
    }

    /**
     * Sets {@link CatalogPhoto#licenseUrl()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder licenseUrl(@Nullable String value) {
      this.licenseUrl = value;
      return this;
    }

    /**
     * Builds the {@link CatalogPhoto}.
     *
     * @return the immutable CatalogPhoto
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogPhoto build() {
      return new CatalogPhoto(this);
    }
  }
}
