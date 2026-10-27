// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The data sources THIS response drew on, and what their licences ask us to say. {@code sources}
 * names exactly the contributors: the blend's models that moved a served number (a model read but
 * pooled at zero weight is absent), the climatology behind the daily normal ({@code era5}, {@code
 * snotel}), PRISM where a precipitation-elevation carry was applied, the DEM for a coordinate
 * forecast whose elevation came from it ({@code usgs_3dep}), and the observation networks of the
 * nowcast and the nearest sensors. {@code credit} is one line per provider (ECMWF-derived output
 * says it was modified, as its CC BY 4.0 terms require); carry it with any redistributed output.
 * The full notices are at {@code url}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ResponseAttribution}.
 */
public final class ResponseAttribution extends Model {
  private final List<String> sources;
  private final String credit;
  private final String url;

  private ResponseAttribution(Builder b) {
    this.sources = List.copyOf(b.sources);
    this.credit = require(b.credit, "credit");
    this.url = require(b.url, "url");
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
    b.sources = this.sources;
    b.credit = this.credit;
    b.url = this.url;
    return b;
  }

  /**
   * Source ids from {@code GET /api/v1/attribution}, in registry order.
   *
   * @return the {@code sources} values; empty when the response has none
   */
  public List<String> sources() {
    return sources;
  }

  /**
   * The {@code credit} value.
   *
   * @return the {@code credit} value
   */
  public String credit() {
    return credit;
  }

  /**
   * Path of the full registry.
   *
   * @return the {@code url} value
   */
  public String url() {
    return url;
  }

  @Override
  Object[] values() {
    return new Object[] {sources, credit, url};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"sources", "credit", "url"};

  /** Builds a {@link ResponseAttribution}. */
  public static final class Builder {
    private List<String> sources = List.of();
    private @Nullable String credit;
    private @Nullable String url;

    private Builder() {}

    /**
     * Sets {@link ResponseAttribution#sources()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder sources(List<String> values) {
      this.sources = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ResponseAttribution#credit()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder credit(String value) {
      this.credit = value;
      return this;
    }

    /**
     * Sets {@link ResponseAttribution#url()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder url(String value) {
      this.url = value;
      return this;
    }

    /**
     * Builds the {@link ResponseAttribution}.
     *
     * @return the immutable ResponseAttribution
     * @throws IllegalStateException if a required value was not set
     */
    public ResponseAttribution build() {
      return new ResponseAttribution(this);
    }
  }
}
