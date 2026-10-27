// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * One model's members at an hour.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ModelMembers}.
 */
public final class ModelMembers extends Model {
  private final String id;
  private final List<MemberForecast> members;

  private ModelMembers(Builder b) {
    this.id = require(b.id, "id");
    this.members = List.copyOf(b.members);
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
    b.members = this.members;
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
   * The {@code members} value.
   *
   * @return the {@code members} values; empty when the response has none
   */
  public List<MemberForecast> members() {
    return members;
  }

  @Override
  Object[] values() {
    return new Object[] {id, members};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"id", "members"};

  /** Builds a {@link ModelMembers}. */
  public static final class Builder {
    private @Nullable String id;
    private List<MemberForecast> members = List.of();

    private Builder() {}

    /**
     * Sets {@link ModelMembers#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link ModelMembers#members()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder members(List<MemberForecast> values) {
      this.members = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link ModelMembers}.
     *
     * @return the immutable ModelMembers
     * @throws IllegalStateException if a required value was not set
     */
    public ModelMembers build() {
      return new ModelMembers(this);
    }
  }
}
