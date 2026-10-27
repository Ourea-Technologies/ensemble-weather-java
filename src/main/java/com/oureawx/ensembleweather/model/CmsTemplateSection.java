// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One element of a page template's {@code spec}. Duplicate {@code (kind, key)} pairs within a spec
 * are a {@code 400} (the same key under different kinds is fine).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CmsTemplateSection}.
 */
public final class CmsTemplateSection extends Model {
  private final String kind;
  private final String key;
  private final @Nullable JsonValue config;

  private CmsTemplateSection(Builder b) {
    this.kind = require(b.kind, "kind");
    this.key = require(b.key, "key");
    this.config = b.config;
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
    b.kind = this.kind;
    b.key = this.key;
    b.config = this.config;
    return b;
  }

  /**
   * Closed vocabulary — unknown kinds fail deserialization ({@code 400}). {@code weather_panel} = a
   * forecast/nowcast panel the client already knows how to draw; {@code content_slot} = a named
   * hole filled by published content entries whose attachment {@code slot} matches {@code key};
   * {@code ad_slot} = a named hole resolved by the ad engine against {@code ad_slots.key} (the key
   * must exist); {@code media} = photo/webcam strip.
   *
   * @return the {@code kind} value ({@link TemplateSectionKind#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public TemplateSectionKind kind() {
    return TemplateSectionKind.fromWireValue(kind);
  }

  /**
   * The {@code kind} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code kind} string
   */
  public String kindAsString() {
    return kind;
  }

  /**
   * Kebab-case section key.
   *
   * @return the {@code key} value
   */
  public String key() {
    return key;
  }

  /**
   * Client-defined render hints ({@code hours}, {@code by_elevation_band}, media {@code
   * source}/{@code limit}, …) — not backend contract.
   *
   * @return the {@code config} value, or empty when the response omits it
   */
  public Optional<JsonValue> config() {
    return Optional.ofNullable(config);
  }

  @Override
  Object[] values() {
    return new Object[] {kind, key, config};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"kind", "key", "config"};

  /** Builds a {@link CmsTemplateSection}. */
  public static final class Builder {
    private @Nullable String kind;
    private @Nullable String key;
    private @Nullable JsonValue config;

    private Builder() {}

    /**
     * Sets {@link CmsTemplateSection#kind()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder kind(TemplateSectionKind value) {
      this.kind = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CmsTemplateSection#kindAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder kindAsString(String value) {
      this.kind = value;
      return this;
    }

    /**
     * Sets {@link CmsTemplateSection#key()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder key(String value) {
      this.key = value;
      return this;
    }

    /**
     * Sets {@link CmsTemplateSection#config()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder config(@Nullable JsonValue value) {
      this.config = value;
      return this;
    }

    /**
     * Builds the {@link CmsTemplateSection}.
     *
     * @return the immutable CmsTemplateSection
     * @throws IllegalStateException if a required value was not set
     */
    public CmsTemplateSection build() {
      return new CmsTemplateSection(this);
    }
  }
}
