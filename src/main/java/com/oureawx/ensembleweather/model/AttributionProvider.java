// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A AttributionProvider from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AttributionProvider}.
 */
public final class AttributionProvider extends Model {
  private final String id;
  private final String name;
  private final String credit;
  private final String licence;
  private final String licenceUrl;
  private final String termsUrl;
  private final boolean modified;
  private final List<String> notices;
  private final List<AttributionSource> sources;

  private AttributionProvider(Builder b) {
    this.id = require(b.id, "id");
    this.name = require(b.name, "name");
    this.credit = require(b.credit, "credit");
    this.licence = require(b.licence, "licence");
    this.licenceUrl = require(b.licenceUrl, "licence_url");
    this.termsUrl = require(b.termsUrl, "terms_url");
    this.modified = require(b.modified, "modified");
    this.notices = List.copyOf(b.notices);
    this.sources = List.copyOf(b.sources);
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
    b.credit = this.credit;
    b.licence = this.licence;
    b.licenceUrl = this.licenceUrl;
    b.termsUrl = this.termsUrl;
    b.modified = this.modified;
    b.notices = this.notices;
    b.sources = this.sources;
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
   * The credit line a response carries for this provider.
   *
   * @return the {@code credit} value
   */
  public String credit() {
    return credit;
  }

  /**
   * SPDX id where one exists ({@code CC-BY-4.0}, {@code CC-BY-3.0}, {@code ODbL-1.0}); otherwise
   * {@code public-domain-us-gov}, {@code public-domain}, {@code none-stated}, or the name of the
   * provider's own terms.
   *
   * @return the {@code licence} value
   */
  public String licence() {
    return licence;
  }

  /**
   * The {@code licence_url} value.
   *
   * @return the {@code licence_url} value
   */
  public String licenceUrl() {
    return licenceUrl;
  }

  /**
   * The page the wording was taken from.
   *
   * @return the {@code terms_url} value
   */
  public String termsUrl() {
    return termsUrl;
  }

  /**
   * Ourea serves this provider's data modified and its terms ask that this be said.
   *
   * @return the {@code modified} value
   */
  public boolean modified() {
    return modified;
  }

  /**
   * Statements the terms require shown in full, beyond {@code credit}.
   *
   * @return the {@code notices} values; empty when the response has none
   */
  public List<String> notices() {
    return notices;
  }

  /**
   * The {@code sources} value.
   *
   * @return the {@code sources} values; empty when the response has none
   */
  public List<AttributionSource> sources() {
    return sources;
  }

  @Override
  Object[] values() {
    return new Object[] {
      id, name, credit, licence, licenceUrl, termsUrl, modified, notices, sources
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id", "name", "credit", "licence", "licenceUrl", "termsUrl", "modified", "notices", "sources"
  };

  /** Builds a {@link AttributionProvider}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String name;
    private @Nullable String credit;
    private @Nullable String licence;
    private @Nullable String licenceUrl;
    private @Nullable String termsUrl;
    private @Nullable Boolean modified;
    private List<String> notices = List.of();
    private List<AttributionSource> sources = List.of();

    private Builder() {}

    /**
     * Sets {@link AttributionProvider#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link AttributionProvider#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link AttributionProvider#credit()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder credit(String value) {
      this.credit = value;
      return this;
    }

    /**
     * Sets {@link AttributionProvider#licence()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder licence(String value) {
      this.licence = value;
      return this;
    }

    /**
     * Sets {@link AttributionProvider#licenceUrl()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder licenceUrl(String value) {
      this.licenceUrl = value;
      return this;
    }

    /**
     * Sets {@link AttributionProvider#termsUrl()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder termsUrl(String value) {
      this.termsUrl = value;
      return this;
    }

    /**
     * Sets {@link AttributionProvider#modified()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder modified(boolean value) {
      this.modified = value;
      return this;
    }

    /**
     * Sets {@link AttributionProvider#notices()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder notices(List<String> values) {
      this.notices = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link AttributionProvider#sources()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder sources(List<AttributionSource> values) {
      this.sources = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link AttributionProvider}.
     *
     * @return the immutable AttributionProvider
     * @throws IllegalStateException if a required value was not set
     */
    public AttributionProvider build() {
      return new AttributionProvider(this);
    }
  }
}
