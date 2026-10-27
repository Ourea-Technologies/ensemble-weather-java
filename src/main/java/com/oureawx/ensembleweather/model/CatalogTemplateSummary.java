// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

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
  private final @Nullable String templateVariant;
  private final List<CmsTemplateSection> spec;

  private CatalogTemplateSummary(Builder b) {
    this.id = require(b.id, "id");
    this.locationType = b.locationType;
    this.templateVariant = b.templateVariant;
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
    b.templateVariant = this.templateVariant;
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
   * Null = wildcard scope.
   *
   * @return the {@code location_type} value, or empty when the response omits it
   */
  public Optional<String> locationType() {
    return Optional.ofNullable(locationType);
  }

  /**
   * Null = wildcard scope.
   *
   * @return the {@code template_variant} value, or empty when the response omits it
   */
  public Optional<String> templateVariant() {
    return Optional.ofNullable(templateVariant);
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
  Object[] values() {
    return new Object[] {id, locationType, templateVariant, spec};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"id", "locationType", "templateVariant", "spec"};

  /** Builds a {@link CatalogTemplateSummary}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String locationType;
    private @Nullable String templateVariant;
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
    public Builder locationType(@Nullable String value) {
      this.locationType = value;
      return this;
    }

    /**
     * Sets {@link CatalogTemplateSummary#templateVariant()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder templateVariant(@Nullable String value) {
      this.templateVariant = value;
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
