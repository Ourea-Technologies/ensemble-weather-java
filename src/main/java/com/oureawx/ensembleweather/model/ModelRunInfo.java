// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The composition the forecast was blended from. The blend is per-model-latest: every model
 * contributes its freshest complete cycle within its staleness cap (aifs/aigefs/gefs 30 h, ifs_ens
 * 36 h, rrfs 14 h), aligned by VALID time, so the models are generally at DIFFERENT inits — {@code
 * models[].cycle} names each one's.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ModelRunInfo}.
 */
public final class ModelRunInfo extends Model {
  private final Instant initializedAt;
  private final int totalMembers;
  private final List<ModelEntry> models;

  private ModelRunInfo(Builder b) {
    this.initializedAt = require(b.initializedAt, "initialized_at");
    this.totalMembers = require(b.totalMembers, "total_members");
    this.models = List.copyOf(b.models);
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
    b.initializedAt = this.initializedAt;
    b.totalMembers = this.totalMembers;
    b.models = this.models;
    return b;
  }

  /**
   * The reference init: the NEWEST contributing model's cycle. Every {@code lead_time_hours} in the
   * response counts from it; a model whose cycle is older contributes its own lead {@code
   * lead_time_hours + (initialized_at − models[].cycle)}. For {@code ?run=} (a pinned historical
   * cycle) every model is at this init.
   *
   * @return the {@code initialized_at} value
   */
  public Instant initializedAt() {
    return initializedAt;
  }

  /**
   * The {@code total_members} value.
   *
   * @return the {@code total_members} value
   */
  public int totalMembers() {
    return totalMembers;
  }

  /**
   * One entry per contributing model, in the order {@code aifs}, {@code aigefs}, {@code gefs},
   * {@code ifs_ens}, {@code rrfs} (a model added later follows, by id). It replaces {@code
   * aifs_members}..{@code rrfs_members}, the {@code source_models} CSV and the {@code model_cycles}
   * map: a new model is a new element, never a new key. A model that contributed nothing is absent.
   *
   * @return the {@code models} values; empty when the response has none
   */
  public List<ModelEntry> models() {
    return models;
  }

  @Override
  Object[] values() {
    return new Object[] {initializedAt, totalMembers, models};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"initializedAt", "totalMembers", "models"};

  /** Builds a {@link ModelRunInfo}. */
  public static final class Builder {
    private @Nullable Instant initializedAt;
    private @Nullable Integer totalMembers;
    private List<ModelEntry> models = List.of();

    private Builder() {}

    /**
     * Sets {@link ModelRunInfo#initializedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder initializedAt(Instant value) {
      this.initializedAt = value;
      return this;
    }

    /**
     * Sets {@link ModelRunInfo#totalMembers()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder totalMembers(int value) {
      this.totalMembers = value;
      return this;
    }

    /**
     * Sets {@link ModelRunInfo#models()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder models(List<ModelEntry> values) {
      this.models = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link ModelRunInfo}.
     *
     * @return the immutable ModelRunInfo
     * @throws IllegalStateException if a required value was not set
     */
    public ModelRunInfo build() {
      return new ModelRunInfo(this);
    }
  }
}
