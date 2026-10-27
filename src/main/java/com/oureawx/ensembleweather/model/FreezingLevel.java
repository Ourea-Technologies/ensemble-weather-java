// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * The hour's freezing level: the lowest altitude at which the air is at 0 °C (AMS Glossary), from
 * the same surface + pressure-level profile {@code inversion} reads. Tagged by {@code kind} (a
 * discriminator); heights are MSL in the caller's length units (m, or ft for {@code imperial}).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code FreezingLevel}.
 */
public final class FreezingLevel extends Model {
  private final String kind;
  private final @Nullable Double height;
  private final @Nullable Boolean aboveFreezingLayerAloft;
  private final @Nullable Double top;

  private FreezingLevel(Builder b) {
    this.kind = require(b.kind, "kind");
    this.height = b.height;
    this.aboveFreezingLayerAloft = b.aboveFreezingLayerAloft;
    this.top = b.top;
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
    b.kind = this.kind;
    b.height = this.height;
    b.aboveFreezingLayerAloft = this.aboveFreezingLayerAloft;
    b.top = this.top;
    return b;
  }

  /**
   * The {@code kind} value.
   *
   * @return the {@code kind} value ({@link FreezingLevelKind#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public FreezingLevelKind kind() {
    return FreezingLevelKind.fromWireValue(kind);
  }

  /**
   * The {@code kind} value exactly as the API sent it, even when this SDK version does not know it.
   *
   * @return the raw {@code kind} string
   */
  public String kindAsString() {
    return kind;
  }

  /**
   * The lowest 0 °C height, MSL.
   *
   * @return the {@code height} value, or empty when the response omits it
   */
  public OptionalDouble height() {
    return height == null ? OptionalDouble.empty() : OptionalDouble.of(height);
  }

  /**
   * An above-freezing layer sits higher up — an inversion's warm nose, the freezing-rain / sleet
   * profile.
   *
   * @return the {@code above_freezing_layer_aloft} value, or empty when the response omits it
   */
  public Optional<Boolean> aboveFreezingLayerAloft() {
    return Optional.ofNullable(aboveFreezingLayerAloft);
  }

  /**
   * Above 0 °C at every level read, up to this height (MSL). The freezing level is above it; not
   * extrapolated.
   *
   * @return the {@code top} value, or empty when the response omits it
   */
  public OptionalDouble top() {
    return top == null ? OptionalDouble.empty() : OptionalDouble.of(top);
  }

  @Override
  Object[] values() {
    return new Object[] {kind, height, aboveFreezingLayerAloft, top};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"kind", "height", "aboveFreezingLayerAloft", "top"};

  /** Builds a {@link FreezingLevel}. */
  public static final class Builder {
    private @Nullable String kind;
    private @Nullable Double height;
    private @Nullable Boolean aboveFreezingLayerAloft;
    private @Nullable Double top;

    private Builder() {}

    /**
     * Sets {@link FreezingLevel#kind()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder kind(FreezingLevelKind value) {
      this.kind = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link FreezingLevel#kindAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder kindAsString(String value) {
      this.kind = value;
      return this;
    }

    /**
     * Sets {@link FreezingLevel#height()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder height(@Nullable Double value) {
      this.height = value;
      return this;
    }

    /**
     * Sets {@link FreezingLevel#aboveFreezingLayerAloft()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder aboveFreezingLayerAloft(@Nullable Boolean value) {
      this.aboveFreezingLayerAloft = value;
      return this;
    }

    /**
     * Sets {@link FreezingLevel#top()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder top(@Nullable Double value) {
      this.top = value;
      return this;
    }

    /**
     * Builds the {@link FreezingLevel}.
     *
     * @return the immutable FreezingLevel
     * @throws IllegalStateException if a required value was not set
     */
    public FreezingLevel build() {
      return new FreezingLevel(this);
    }
  }
}
