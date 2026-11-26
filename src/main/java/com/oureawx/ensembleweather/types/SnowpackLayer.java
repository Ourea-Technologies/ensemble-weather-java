// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.LocalDate;
import org.jspecify.annotations.Nullable;

/**
 * A SnowpackLayer from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SnowpackLayer}.
 */
public final class SnowpackLayer extends Model {
  private final String form;
  private final double thickness;
  private final double depth;
  private final LocalDate formed;

  private SnowpackLayer(Builder b) {
    this.form = require(b.form, "form");
    this.thickness = require(b.thickness, "thickness");
    this.depth = require(b.depth, "depth");
    this.formed = require(b.formed, "formed");
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
    b.form = this.form;
    b.thickness = this.thickness;
    b.depth = this.depth;
    b.formed = this.formed;
    return b;
  }

  /**
   * A key of {@code definitions.layer_form}: today {@code faceted_grains}, {@code depth_hoar},
   * {@code buried_surface_hoar}, {@code melt_freeze_crust}, {@code ice_layer} or {@code
   * rounding_facets}. Tolerate values you do not know.
   *
   * @return the {@code form} value
   */
  public String form() {
    return form;
  }

  /**
   * Cm or in.
   *
   * @return the {@code thickness} value
   */
  public double thickness() {
    return thickness;
  }

  /**
   * From the surface down to the layer's top (cm or in).
   *
   * @return the {@code depth} value
   */
  public double depth() {
    return depth;
  }

  /**
   * The {@code formed} value.
   *
   * @return the {@code formed} value
   */
  public LocalDate formed() {
    return formed;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {form, thickness, depth, formed};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"form", "thickness", "depth", "formed"};

  /** Builds a {@link SnowpackLayer}. */
  public static final class Builder {
    private @Nullable String form;
    private @Nullable Double thickness;
    private @Nullable Double depth;
    private @Nullable LocalDate formed;

    private Builder() {}

    /**
     * Sets {@link SnowpackLayer#form()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder form(String value) {
      this.form = value;
      return this;
    }

    /**
     * Sets {@link SnowpackLayer#thickness()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder thickness(double value) {
      this.thickness = value;
      return this;
    }

    /**
     * Sets {@link SnowpackLayer#depth()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder depth(double value) {
      this.depth = value;
      return this;
    }

    /**
     * Sets {@link SnowpackLayer#formed()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder formed(LocalDate value) {
      this.formed = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackLayer}.
     *
     * @return the immutable SnowpackLayer
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackLayer build() {
      return new SnowpackLayer(this);
    }
  }
}
