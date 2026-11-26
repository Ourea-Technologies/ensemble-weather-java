// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A SnowpackPlumeModel from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. An inline object of the wire schema.
 */
public final class SnowpackPlumeModel extends Model {
  private final String id;
  private final List<Integer> memberIds;
  private final List<List<Double>> depth;
  private final List<Double> depthMean;
  private final int membersSkipped;

  private SnowpackPlumeModel(Builder b) {
    this.id = require(b.id, "id");
    this.memberIds = List.copyOf(b.memberIds);
    this.depth = List.copyOf(b.depth);
    this.depthMean = List.copyOf(b.depthMean);
    this.membersSkipped = require(b.membersSkipped, "members_skipped");
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
    b.memberIds = this.memberIds;
    b.depth = this.depth;
    b.depthMean = this.depthMean;
    b.membersSkipped = this.membersSkipped;
    return b;
  }

  /**
   * {@code aifs}, {@code aigefs}, {@code gefs} or {@code ifs_ens}; a client must tolerate one it
   * does not know.
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * The model's own member numbers, in the order of {@code depth}.
   *
   * @return the {@code member_ids} values; empty when the response has none
   */
  public List<Integer> memberIds() {
    return memberIds;
  }

  /**
   * One series per member, from {@code depth.start_time} every {@code depth.interval_hours}.
   *
   * @return the {@code depth} values; empty when the response has none
   */
  public List<List<Double>> depth() {
    return depth;
  }

  /**
   * The equal-weight mean of this model's members' {@code depth} at each of the model's own steps
   * (from {@code depth.start_time} every {@code depth.interval_hours}, to the model's horizon: the
   * length of its members' series), in the response's units. Never null.
   *
   * @return the {@code depth_mean} values; empty when the response has none
   */
  public List<Double> depthMean() {
    return depthMean;
  }

  /**
   * Members not run because the ground starts bare and they bring no snow; their series is 0
   * throughout.
   *
   * @return the {@code members_skipped} value
   */
  public int membersSkipped() {
    return membersSkipped;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {id, memberIds, depth, depthMean, membersSkipped};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"id", "memberIds", "depth", "depthMean", "membersSkipped"};

  /** Builds a {@link SnowpackPlumeModel}. */
  public static final class Builder {
    private @Nullable String id;
    private List<Integer> memberIds = List.of();
    private List<List<Double>> depth = List.of();
    private List<Double> depthMean = List.of();
    private @Nullable Integer membersSkipped;

    private Builder() {}

    /**
     * Sets {@link SnowpackPlumeModel#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeModel#memberIds()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder memberIds(List<Integer> values) {
      this.memberIds = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeModel#depth()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder depth(List<List<Double>> values) {
      this.depth = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeModel#depthMean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder depthMean(List<Double> values) {
      this.depthMean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link SnowpackPlumeModel#membersSkipped()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder membersSkipped(int value) {
      this.membersSkipped = value;
      return this;
    }

    /**
     * Builds the {@link SnowpackPlumeModel}.
     *
     * @return the immutable SnowpackPlumeModel
     * @throws IllegalStateException if a required value was not set
     */
    public SnowpackPlumeModel build() {
      return new SnowpackPlumeModel(this);
    }
  }
}
