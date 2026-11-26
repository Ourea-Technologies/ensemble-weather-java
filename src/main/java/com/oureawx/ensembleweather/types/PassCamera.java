// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One DOT camera, a still image the DOT refreshes about once a minute. Hotlinked: the client loads
 * {@code image_url} from the DOT and shows {@code attribution}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassCamera}.
 */
public final class PassCamera extends Model {
  private final String id;
  private final String title;
  private final String imageUrl;
  private final String url;
  private final @Nullable Instant capturedAt;
  private final boolean stale;
  private final String source;
  private final String attribution;

  private PassCamera(Builder b) {
    this.id = require(b.id, "id");
    this.title = require(b.title, "title");
    this.imageUrl = require(b.imageUrl, "image_url");
    this.url = require(b.url, "url");
    this.capturedAt = b.capturedAt;
    this.stale = require(b.stale, "stale");
    this.source = require(b.source, "source");
    this.attribution = require(b.attribution, "attribution");
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
    b.title = this.title;
    b.imageUrl = this.imageUrl;
    b.url = this.url;
    b.capturedAt = this.capturedAt;
    b.stale = this.stale;
    b.source = this.source;
    b.attribution = this.attribution;
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
   * The DOT's name for the camera.
   *
   * @return the {@code title} value
   */
  public String title() {
    return title;
  }

  /**
   * The DOT's current still image.
   *
   * @return the {@code image_url} value
   */
  public String imageUrl() {
    return imageUrl;
  }

  /**
   * The DOT's own page for the camera, to link out to.
   *
   * @return the {@code url} value
   */
  public String url() {
    return url;
  }

  /**
   * The image's time (its Last-Modified) when Ourea last checked it, every 15 minutes; the image at
   * {@code image_url} may be newer. Omitted until it has been checked.
   *
   * @return the {@code captured_at} value, or empty when the response omits it
   */
  public Optional<Instant> capturedAt() {
    return Optional.ofNullable(capturedAt);
  }

  /**
   * {@code captured_at} is unknown or more than 60 minutes before {@code as_of}: the camera may be
   * frozen.
   *
   * @return the {@code stale} value
   */
  public boolean stale() {
    return stale;
  }

  /**
   * The {@code source} value. One of {@code caltrans}, {@code wsdot}, {@code cdot}, {@code odot};
   * read it as a string, since the API may add values.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * The {@code attribution} value.
   *
   * @return the {@code attribution} value
   */
  public String attribution() {
    return attribution;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {id, title, imageUrl, url, capturedAt, stale, source, attribution};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id", "title", "imageUrl", "url", "capturedAt", "stale", "source", "attribution"
  };

  /** Builds a {@link PassCamera}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String title;
    private @Nullable String imageUrl;
    private @Nullable String url;
    private @Nullable Instant capturedAt;
    private @Nullable Boolean stale;
    private @Nullable String source;
    private @Nullable String attribution;

    private Builder() {}

    /**
     * Sets {@link PassCamera#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link PassCamera#title()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder title(String value) {
      this.title = value;
      return this;
    }

    /**
     * Sets {@link PassCamera#imageUrl()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder imageUrl(String value) {
      this.imageUrl = value;
      return this;
    }

    /**
     * Sets {@link PassCamera#url()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder url(String value) {
      this.url = value;
      return this;
    }

    /**
     * Sets {@link PassCamera#capturedAt()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder capturedAt(@Nullable Instant value) {
      this.capturedAt = value;
      return this;
    }

    /**
     * Sets {@link PassCamera#stale()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stale(boolean value) {
      this.stale = value;
      return this;
    }

    /**
     * Sets {@link PassCamera#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link PassCamera#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(String value) {
      this.attribution = value;
      return this;
    }

    /**
     * Builds the {@link PassCamera}.
     *
     * @return the immutable PassCamera
     * @throws IllegalStateException if a required value was not set
     */
    public PassCamera build() {
      return new PassCamera(this);
    }
  }
}
