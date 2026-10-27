// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * The forecast options the server applied, after the tier clamps and bounds. {@code max_hours} and
 * {@code max_days} are clamped, never refused (Free 240 h / 10 days, Pro 360 h / 15 days; {@code
 * max_hours} has a 6 h floor), so the body says what it served. Present on every REST forecast
 * body.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code AppliedOptions}.
 */
public final class AppliedOptions extends Model {
  private final @Nullable Integer maxHours;
  private final @Nullable Integer maxDays;
  private final List<String> clamped;

  private AppliedOptions(Builder b) {
    this.maxHours = b.maxHours;
    this.maxDays = b.maxDays;
    this.clamped = List.copyOf(b.clamped);
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
    b.maxHours = this.maxHours;
    b.maxDays = this.maxDays;
    b.clamped = this.clamped;
    return b;
  }

  /**
   * Hours past the first served hour; {@code null} with {@code hourly=false}.
   *
   * @return the {@code max_hours} value, or empty when the response omits it
   */
  public OptionalInt maxHours() {
    return maxHours == null ? OptionalInt.empty() : OptionalInt.of(maxHours);
  }

  /**
   * Daily rows served for; {@code null} with {@code daily=false}.
   *
   * @return the {@code max_days} value, or empty when the response omits it
   */
  public OptionalInt maxDays() {
    return maxDays == null ? OptionalInt.empty() : OptionalInt.of(maxDays);
  }

  /**
   * The requested options the server changed, in this order. Empty when it changed none.
   *
   * @return the {@code clamped} values; empty when the response has none
   */
  public List<ClampedOption> clamped() {
    List<ClampedOption> out = new ArrayList<>(clamped.size());
    for (String v : clamped) {
      out.add(ClampedOption.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code clamped} values exactly as the API sent them, including any this SDK version does
   * not know.
   *
   * @return the raw {@code clamped} strings
   */
  public List<String> clampedAsStrings() {
    return clamped;
  }

  @Override
  Object[] values() {
    return new Object[] {maxHours, maxDays, clamped};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"maxHours", "maxDays", "clamped"};

  /** Builds a {@link AppliedOptions}. */
  public static final class Builder {
    private @Nullable Integer maxHours;
    private @Nullable Integer maxDays;
    private List<String> clamped = List.of();

    private Builder() {}

    /**
     * Sets {@link AppliedOptions#maxHours()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder maxHours(@Nullable Integer value) {
      this.maxHours = value;
      return this;
    }

    /**
     * Sets {@link AppliedOptions#maxDays()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder maxDays(@Nullable Integer value) {
      this.maxDays = value;
      return this;
    }

    /**
     * Sets {@link AppliedOptions#clamped()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder clamped(List<ClampedOption> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ClampedOption v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.clamped = raw;
      return this;
    }

    /**
     * Sets {@link AppliedOptions#clampedAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder clampedAsStrings(List<String> values) {
      this.clamped = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link AppliedOptions}.
     *
     * @return the immutable AppliedOptions
     * @throws IllegalStateException if a required value was not set
     */
    public AppliedOptions build() {
      return new AppliedOptions(this);
    }
  }
}
