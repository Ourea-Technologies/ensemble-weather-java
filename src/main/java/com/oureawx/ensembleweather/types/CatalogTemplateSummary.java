// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The resolved page template as the public API serves it — identity + scope + spec; editorial audit
 * fields stay internal.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CatalogTemplateSummary}.
 */
public final class CatalogTemplateSummary extends Model {
  private final String id;
  private final @Nullable String locationType;
  private final @Nullable String variant;
  private final List<CmsTemplateSection> spec;

  private CatalogTemplateSummary(Builder b) {
    this.id = require(b.id, "id");
    this.locationType = b.locationType;
    this.variant = b.variant;
    this.spec = List.copyOf(b.spec);
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
    b.locationType = this.locationType;
    b.variant = this.variant;
    b.spec = this.spec;
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
   * The location type the template is scoped to, snake_case as {@code LocationRef.type}. Null =
   * wildcard scope.
   *
   * @return the {@code location_type} value, or empty when the response omits it ({@link
   *     LocationType#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<LocationType> locationType() {
    return Optional.ofNullable(locationType).map(LocationType::fromWireValue);
  }

  /**
   * The {@code location_type} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code location_type} string, or empty when absent
   */
  public Optional<String> locationTypeAsString() {
    return Optional.ofNullable(locationType);
  }

  /**
   * (JSON {@code template_variant}) The page-template variant the template is scoped to,
   * snake_case. Null = wildcard scope.
   *
   * @return the {@code template_variant} value, or empty when the response omits it ({@link
   *     TemplateVariant#UNKNOWN} for a value this SDK version does not know)
   */
  public Optional<TemplateVariant> variant() {
    return Optional.ofNullable(variant).map(TemplateVariant::fromWireValue);
  }

  /**
   * The {@code template_variant} value exactly as the API sent it, even when this SDK version does
   * not know it.
   *
   * @return the raw {@code template_variant} string, or empty when absent
   */
  public Optional<String> variantAsString() {
    return Optional.ofNullable(variant);
  }

  /**
   * The {@code spec} value.
   *
   * @return the {@code spec} values; empty when the response has none
   */
  public List<CmsTemplateSection> spec() {
    return spec;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {id, locationType, variant, spec};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"id", "locationType", "variant", "spec"};

  /** Builds a {@link CatalogTemplateSummary}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String locationType;
    private @Nullable String variant;
    private List<CmsTemplateSection> spec = List.of();

    private Builder() {}

    /**
     * Sets {@link CatalogTemplateSummary#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link CatalogTemplateSummary#locationType()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder locationType(@Nullable LocationType value) {
      this.locationType = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogTemplateSummary#locationTypeAsString()}, the raw wire value, kept verbatim
     * even when this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder locationTypeAsString(@Nullable String value) {
      this.locationType = value;
      return this;
    }

    /**
     * Sets {@link CatalogTemplateSummary#variant()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder variant(@Nullable TemplateVariant value) {
      this.variant = value == null ? null : wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogTemplateSummary#variantAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value, or null for absent
     * @return this builder
     */
    public Builder variantAsString(@Nullable String value) {
      this.variant = value;
      return this;
    }

    /**
     * Sets {@link CatalogTemplateSummary#spec()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder spec(List<CmsTemplateSection> values) {
      this.spec = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link CatalogTemplateSummary}.
     *
     * @return the immutable CatalogTemplateSummary
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogTemplateSummary build() {
      return new CatalogTemplateSummary(this);
    }
  }
}
