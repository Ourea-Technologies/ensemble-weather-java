// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A SnowpackModel from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class SnowpackModel extends Model {
  private final String name;
  private final String by;
  private final String license;
  private final @Nullable String version;

  private SnowpackModel(Builder b) {
    this.name = require(b.name, "name");
    this.by = require(b.by, "by");
    this.license = require(b.license, "license");
    this.version = b.version;
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
    b.name = this.name;
    b.by = this.by;
    b.license = this.license;
    b.version = this.version;
    return b;
  }

  /**
   * The {@code name} value.
   *
   * @return the {@code name} value
   */
  public String name() {
    return name;
  }

  /**
   * The {@code by} value.
   *
   * @return the {@code by} value
   */
  public String by() {
    return by;
  }

  /**
   * The {@code license} value.
   *
   * @return the {@code license} value
   */
  public String license() {
    return license;
  }

  /**
   * The build that ran: the commits of Ourea's SNOWPACK and MeteoIO source it was built from, as
   * {@code snowpack <commit> meteoio <commit>}. Absent on a run whose build carried no such record.
   *
   * @return the {@code version} value, or empty when the response omits it
   */
  public Optional<String> version() {
    return Optional.ofNullable(version);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {name, by, license, version};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"name", "by", "license", "version"};

  /** Builds a {@link SnowpackModel}. */
  public static final class Builder {
    private @Nullable String name;
    private @Nullable String by;
    private @Nullable String license;
    private @Nullable String version;

    private Builder() {}

    /**
     * Sets {@link SnowpackModel#name()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder name(String value) {
      this.name = value;
      return this;
    }

    /**
     * Sets {@link SnowpackModel#by()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder by(String value) {
      this.by = value;
      return this;
    }

    /**
     * Sets {@link SnowpackModel#license()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder license(String value) {
      this.license = value;
      return this;
    }

    /**
     * Sets {@link SnowpackModel#version()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder version(@Nullable String value) {
      this.version = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackModel}.
     *
     * @return the immutable SnowpackModel
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackModel build() {
      return new SnowpackModel(this);
    }
  }
}
