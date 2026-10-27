// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A CatalogLocationPage from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogLocationPage}.
 */
public final class CatalogLocationPage extends Model {
  private final List<CatalogLocation> items;
  private final @Nullable String nextCursor;
  private final ResponseMetadata metadata;

  private CatalogLocationPage(Builder b) {
    this.items = List.copyOf(b.items);
    this.nextCursor = b.nextCursor;
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
    b.items = this.items;
    b.nextCursor = this.nextCursor;
    b.metadata = this.metadata;
    return b;
  }

  /**
   * The {@code items} value.
   *
   * @return the {@code items} values; empty when the response has none
   */
  public List<CatalogLocation> items() {
    return items;
  }

  /**
   * Pass as {@code ?cursor=} for the next page; null at the end.
   *
   * @return the {@code next_cursor} value, or empty when the response omits it
   */
  public Optional<String> nextCursor() {
    return Optional.ofNullable(nextCursor);
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
    return new Object[] {items, nextCursor};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"items", "nextCursor"};

  /** Builds a {@link CatalogLocationPage}. */
  public static final class Builder {
    private List<CatalogLocation> items = List.of();
    private @Nullable String nextCursor;
    private ResponseMetadata metadata = ResponseMetadata.none();

    private Builder() {}

    /**
     * Sets {@link CatalogLocationPage#items()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder items(List<CatalogLocation> values) {
      this.items = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link CatalogLocationPage#nextCursor()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder nextCursor(@Nullable String value) {
      this.nextCursor = value;
      return this;
    }

    /**
     * Sets {@link CatalogLocationPage#metadata()}.
     *
     * @param value the response metadata
     * @return this builder
     */
    public Builder metadata(ResponseMetadata value) {
      this.metadata = value;
      return this;
    }

    /**
     * Builds the {@link CatalogLocationPage}.
     *
     * @return the immutable CatalogLocationPage
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogLocationPage build() {
      return new CatalogLocationPage(this);
    }
  }
}
