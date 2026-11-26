// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Map;

/**
 * A SnowpackResponseDefinitions from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class SnowpackResponseDefinitions extends Model {
  private final Map<String, String> surface;
  private final Map<String, String> layerForm;

  private SnowpackResponseDefinitions(Builder b) {
    this.surface = Map.copyOf(b.surface);
    this.layerForm = Map.copyOf(b.layerForm);
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
    b.surface = this.surface;
    b.layerForm = this.layerForm;
    return b;
  }

  /**
   * Each surface class and what it means.
   *
   * @return the {@code surface} values; empty when the response has none
   */
  public Map<String, String> surface() {
    return surface;
  }

  /**
   * Each layer form and what it is.
   *
   * @return the {@code layer_form} values; empty when the response has none
   */
  public Map<String, String> layerForm() {
    return layerForm;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {surface, layerForm};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"surface", "layerForm"};

  /** Builds a {@link SnowpackResponseDefinitions}. */
  public static final class Builder {
    private Map<String, String> surface = Map.of();
    private Map<String, String> layerForm = Map.of();

    private Builder() {}

    /**
     * Sets {@link SnowpackResponseDefinitions#surface()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder surface(Map<String, String> values) {
      this.surface = Map.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackResponseDefinitions#layerForm()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder layerForm(Map<String, String> values) {
      this.layerForm = Map.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link SnowpackResponseDefinitions}.
     *
     * @return the immutable SnowpackResponseDefinitions
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackResponseDefinitions build() {
      return new SnowpackResponseDefinitions(this);
    }
  }
}
