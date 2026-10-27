// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * One published content entry as the public catalog detail serves it: entry fields + the
 * attachment's slot/order + which locale the read path actually resolved. Draft/audit internals are
 * deliberately absent.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CatalogResolvedEntry}.
 */
public final class CatalogResolvedEntry extends Model {
  private final String id;
  private final String contentTypeKey;
  private final String title;
  private final JsonValue body;
  private final String slot;
  private final int displayOrder;
  private final String resolvedLocale;
  private final Instant updatedAt;

  private CatalogResolvedEntry(Builder b) {
    this.id = require(b.id, "id");
    this.contentTypeKey = require(b.contentTypeKey, "content_type_key");
    this.title = require(b.title, "title");
    this.body = require(b.body, "body");
    this.slot = require(b.slot, "slot");
    this.displayOrder = require(b.displayOrder, "display_order");
    this.resolvedLocale = require(b.resolvedLocale, "resolved_locale");
    this.updatedAt = require(b.updatedAt, "updated_at");
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
    b.contentTypeKey = this.contentTypeKey;
    b.title = this.title;
    b.body = this.body;
    b.slot = this.slot;
    b.displayOrder = this.displayOrder;
    b.resolvedLocale = this.resolvedLocale;
    b.updatedAt = this.updatedAt;
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
   * The {@code content_type_key} value.
   *
   * @return the {@code content_type_key} value
   */
  public String contentTypeKey() {
    return contentTypeKey;
  }

  /**
   * The {@code title} value.
   *
   * @return the {@code title} value
   */
  public String title() {
    return title;
  }

  /**
   * Shaped by the content type's {@code field_schema}.
   *
   * @return the {@code body} value
   */
  public JsonValue body() {
    return body;
  }

  /**
   * The template {@code content_slot} key this entry fills.
   *
   * @return the {@code slot} value
   */
  public String slot() {
    return slot;
  }

  /**
   * The {@code display_order} value.
   *
   * @return the {@code display_order} value
   */
  public int displayOrder() {
    return displayOrder;
  }

  /**
   * The requested locale if ANY field of this entry carried a translation, else the entry's base
   * locale. Partial coverage overlays per field — that IS the fallback model.
   *
   * @return the {@code resolved_locale} value
   */
  public String resolvedLocale() {
    return resolvedLocale;
  }

  /**
   * The {@code updated_at} value.
   *
   * @return the {@code updated_at} value
   */
  public Instant updatedAt() {
    return updatedAt;
  }

  @Override
  Object[] values() {
    return new Object[] {
      id, contentTypeKey, title, body, slot, displayOrder, resolvedLocale, updatedAt
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id", "contentTypeKey", "title", "body", "slot", "displayOrder", "resolvedLocale", "updatedAt"
  };

  /** Builds a {@link CatalogResolvedEntry}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String contentTypeKey;
    private @Nullable String title;
    private @Nullable JsonValue body;
    private @Nullable String slot;
    private @Nullable Integer displayOrder;
    private @Nullable String resolvedLocale;
    private @Nullable Instant updatedAt;

    private Builder() {}

    /**
     * Sets {@link CatalogResolvedEntry#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedEntry#contentTypeKey()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder contentTypeKey(String value) {
      this.contentTypeKey = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedEntry#title()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder title(String value) {
      this.title = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedEntry#body()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder body(JsonValue value) {
      this.body = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedEntry#slot()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slot(String value) {
      this.slot = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedEntry#displayOrder()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder displayOrder(int value) {
      this.displayOrder = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedEntry#resolvedLocale()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder resolvedLocale(String value) {
      this.resolvedLocale = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedEntry#updatedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder updatedAt(Instant value) {
      this.updatedAt = value;
      return this;
    }

    /**
     * Builds the {@link CatalogResolvedEntry}.
     *
     * @return the immutable CatalogResolvedEntry
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogResolvedEntry build() {
      return new CatalogResolvedEntry(this);
    }
  }
}
