// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A CatalogElevation from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PublicCatalogElevation}.
 */
public final class CatalogElevation extends Model {
  private final String label;
  private final double heightM;
  private final @Nullable Double latOverride;
  private final @Nullable Double lonOverride;
  private final boolean isDefault;

  private final @Nullable String displayName;

  private CatalogElevation(Builder b) {
    this.label = require(b.label, "label");
    this.heightM = require(b.heightM, "elevation_m");
    this.latOverride = b.latOverride;
    this.lonOverride = b.lonOverride;
    this.isDefault = require(b.isDefault, "is_default");
    this.displayName = b.displayName;
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
    b.label = this.label;
    b.heightM = this.heightM;
    b.latOverride = this.latOverride;
    b.lonOverride = this.lonOverride;
    b.isDefault = this.isDefault;
    b.displayName = this.displayName;
    return b;
  }

  /**
   * The {@code label} value.
   *
   * @return the {@code label} value
   */
  public String label() {
    return label;
  }

  /**
   * (JSON {@code elevation_m}) The {@code elevation_m} value.
   *
   * @return the {@code elevation_m} value
   */
  public double heightM() {
    return heightM;
  }

  /**
   * The {@code lat_override} value.
   *
   * @return the {@code lat_override} value, or empty when the response omits it
   */
  public OptionalDouble latOverride() {
    return latOverride == null ? OptionalDouble.empty() : OptionalDouble.of(latOverride);
  }

  /**
   * The {@code lon_override} value.
   *
   * @return the {@code lon_override} value, or empty when the response omits it
   */
  public OptionalDouble lonOverride() {
    return lonOverride == null ? OptionalDouble.empty() : OptionalDouble.of(lonOverride);
  }

  /**
   * Whether this is the default elevation to show in the UI.
   *
   * @return the {@code is_default} value
   */
  public boolean isDefault() {
    return isDefault;
  }

  /**
   * What the place is called, for display. Absent where the catalog has no name for the point (show
   * {@code label}).
   *
   * @return the {@code display_name} value, or empty when the response omits it
   */
  public Optional<String> displayName() {
    return Optional.ofNullable(displayName);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {label, heightM, latOverride, lonOverride, isDefault, displayName};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "label", "heightM", "latOverride", "lonOverride", "isDefault", "displayName"
  };

  /** Builds a {@link CatalogElevation}. */
  public static final class Builder {
    private @Nullable String label;
    private @Nullable Double heightM;
    private @Nullable Double latOverride;
    private @Nullable Double lonOverride;
    private @Nullable Boolean isDefault;

    private @Nullable String displayName;

    private Builder() {}

    /**
     * Sets {@link CatalogElevation#label()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder label(String value) {
      this.label = value;
      return this;
    }

    /**
     * Sets {@link CatalogElevation#heightM()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder heightM(double value) {
      this.heightM = value;
      return this;
    }

    /**
     * Sets {@link CatalogElevation#latOverride()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder latOverride(@Nullable Double value) {
      this.latOverride = value;
      return this;
    }

    /**
     * Sets {@link CatalogElevation#lonOverride()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder lonOverride(@Nullable Double value) {
      this.lonOverride = value;
      return this;
    }

    /**
     * Sets {@link CatalogElevation#isDefault()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder isDefault(boolean value) {
      this.isDefault = value;
      return this;
    }

    /**
     * Sets {@link CatalogElevation#displayName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder displayName(@Nullable String value) {
      this.displayName = value;
      return this;
    }

    /**
     * Builds the {@link CatalogElevation}.
     *
     * @return the immutable CatalogElevation
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogElevation build() {
      return new CatalogElevation(this);
    }
  }
}
