// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * One contributing model.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ModelEntry}.
 */
public final class ModelEntry extends Model {
  private final String id;
  private final @Nullable Instant cycle;
  private final @Nullable Integer members;

  private final List<Integer> displayMembers;
  private final @Nullable String displayMembersSnowfall;

  private ModelEntry(Builder b) {
    this.id = require(b.id, "id");
    this.cycle = b.cycle;
    this.members = b.members;
    this.displayMembers = List.copyOf(b.displayMembers);
    this.displayMembersSnowfall = b.displayMembersSnowfall;
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
    b.cycle = this.cycle;
    b.members = this.members;
    b.displayMembers = this.displayMembers;
    b.displayMembersSnowfall = this.displayMembersSnowfall;
    return b;
  }

  /**
   * The model id ({@code aifs}, {@code aigefs}, {@code gefs}, {@code ifs_ens}, {@code rrfs}; treat
   * others as additive).
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * The model's own cycle init (per-model-latest). Omitted when it was not read.
   *
   * @return the {@code cycle} value, or empty when the response omits it
   */
  public Optional<Instant> cycle() {
    return Optional.ofNullable(cycle);
  }

  /**
   * How many of its members the forecast carries (in {@code model_run}); omitted in {@code /runs}
   * {@code served}.
   *
   * @return the {@code members} value, or empty when the response omits it
   */
  public OptionalInt members() {
    return members == null ? OptionalInt.empty() : OptionalInt.of(members);
  }

  /**
   * The {@code member_id}s of this model's members served for display only, on a response that asks
   * for members: {@code rrfs} 1..5 are the RRFS ensemble's m001..m005, beside the deterministic run
   * (0). They ride in {@code timeseries[].members} and {@code forecast.plume} like any member, and
   * are in nothing else: not in {@code members} or {@code total_members}, the blend, any statistic,
   * band or {@code precipitation_by_type}, any {@code prob=} or {@code totals=} answer, or
   * verification. Absent when there are none (a cycle stored without them, or a point with no value
   * of them). A member that a cycle did not finish is left out, so this can be fewer than five.
   *
   * @return the {@code display_members} values; empty when the response has none
   */
  public List<Integer> displayMembers() {
    return displayMembers;
  }

  /**
   * How the {@code display_members}' {@code snowfall} and {@code cumulative_snowfall} are made,
   * present exactly when {@code display_members} is. {@code borrowed_from_deterministic}: the
   * members store no pressure column, so each member's snowfall is its own precipitation x the
   * deterministic run's snow-to-liquid ratio at that hour, gated by the deterministic run's snow
   * level, through the same snowfall rule as every member. Their snowfall spread is the
   * precipitation spread alone, not each member's own temperature profile; label it as borrowed
   * where it is drawn. Always {@code borrowed_from_deterministic} today.
   *
   * @return the {@code display_members_snowfall} value, or empty when the response omits it
   */
  public Optional<String> displayMembersSnowfall() {
    return Optional.ofNullable(displayMembersSnowfall);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {id, cycle, members, displayMembers, displayMembersSnowfall};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id", "cycle", "members", "displayMembers", "displayMembersSnowfall"
  };

  /** Builds a {@link ModelEntry}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable Instant cycle;
    private @Nullable Integer members;

    private List<Integer> displayMembers = List.of();
    private @Nullable String displayMembersSnowfall;

    private Builder() {}

    /**
     * Sets {@link ModelEntry#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link ModelEntry#cycle()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder cycle(@Nullable Instant value) {
      this.cycle = value;
      return this;
    }

    /**
     * Sets {@link ModelEntry#members()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder members(@Nullable Integer value) {
      this.members = value;
      return this;
    }

    /**
     * Sets {@link ModelEntry#displayMembers()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder displayMembers(List<Integer> values) {
      this.displayMembers = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ModelEntry#displayMembersSnowfall()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder displayMembersSnowfall(@Nullable String value) {
      this.displayMembersSnowfall = value;
      return this;
    }

    /**
     * Builds the {@link ModelEntry}.
     *
     * @return the immutable ModelEntry
     * @throws IllegalStateException if a required value was not set
     */
    public ModelEntry build() {
      return new ModelEntry(this);
    }
  }
}
