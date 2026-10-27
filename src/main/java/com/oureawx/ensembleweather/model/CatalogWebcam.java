// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * A CatalogWebcam from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogWebcam}.
 */
public final class CatalogWebcam extends Model {
  private final String name;
  private final String url;
  private final @Nullable String embedType;
  private final @Nullable String provider;
  private final @Nullable Integer refreshIntervalSeconds;

  private CatalogWebcam(Builder b) {
    this.name = require(b.name, "name");
    this.url = require(b.url, "url");
    this.embedType = b.embedType;
    this.provider = b.provider;
    this.refreshIntervalSeconds = b.refreshIntervalSeconds;
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
    b.name = this.name;
    b.url = this.url;
    b.embedType = this.embedType;
    b.provider = this.provider;
    b.refreshIntervalSeconds = this.refreshIntervalSeconds;
    return b;
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
   * The {@code url} value.
   *
   * @return the {@code url} value
   */
  public String url() {
    return url;
  }

  /**
   * The {@code embed_type} value.
   *
   * @return the {@code embed_type} value, or empty when the response omits it
   */
  public Optional<String> embedType() {
    return Optional.ofNullable(embedType);
  }

  /**
   * The {@code provider} value.
   *
   * @return the {@code provider} value, or empty when the response omits it
   */
  public Optional<String> provider() {
    return Optional.ofNullable(provider);
  }

  /**
   * The {@code refresh_interval_seconds} value.
   *
   * @return the {@code refresh_interval_seconds} value, or empty when the response omits it
   */
  public OptionalInt refreshIntervalSeconds() {
    return refreshIntervalSeconds == null
        ? OptionalInt.empty()
        : OptionalInt.of(refreshIntervalSeconds);
  }

  @Override
  Object[] values() {
    return new Object[] {name, url, embedType, provider, refreshIntervalSeconds};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "name", "url", "embedType", "provider", "refreshIntervalSeconds"
  };

  /** Builds a {@link CatalogWebcam}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable String url;
    private @Nullable String embedType;
    private @Nullable String provider;
    private @Nullable Integer refreshIntervalSeconds;

    private Builder() {}

    /**
     * Sets {@link CatalogWebcam#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link CatalogWebcam#url()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder url(String value) {
      this.url = value;
      return this;
    }

    /**
     * Sets {@link CatalogWebcam#embedType()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder embedType(@Nullable String value) {
      this.embedType = value;
      return this;
    }

    /**
     * Sets {@link CatalogWebcam#provider()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder provider(@Nullable String value) {
      this.provider = value;
      return this;
    }

    /**
     * Sets {@link CatalogWebcam#refreshIntervalSeconds()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder refreshIntervalSeconds(@Nullable Integer value) {
      this.refreshIntervalSeconds = value;
      return this;
    }

    /**
     * Builds the {@link CatalogWebcam}.
     *
     * @return the immutable CatalogWebcam
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogWebcam build() {
      return new CatalogWebcam(this);
    }
  }
}
