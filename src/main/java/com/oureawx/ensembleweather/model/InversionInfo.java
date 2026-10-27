// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import org.jspecify.annotations.Nullable;

/**
 * Surface-based cold pool or elevated inversion detected from the model's vertical temperature
 * profile. Clients can use this to badge hours where "warmer aloft than at the surface" conditions
 * are in effect.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code InversionInfo}.
 */
public final class InversionInfo extends Model {
  private final String kind;
  private final double depth;
  private final double strength;

  private InversionInfo(Builder b) {
    this.kind = require(b.kind, "kind");
    this.depth = require(b.depth, "depth");
    this.strength = require(b.strength, "strength");
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
    b.depth = this.depth;
    b.strength = this.strength;
    return b;
  }

  /**
   * {@code surface_shallow}: shallow (&lt;300 m) surface-based inversion, common overnight in
   * calm/clear conditions. {@code cold_pool}: deep (≥300 m) surface-based cold pool, can persist
   * for days under a ridge. {@code elevated}: warm layer aloft above a normal surface layer
   * (subsidence, capped boundary layer).
   *
   * @return the {@code kind} value ({@link InversionKind#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public InversionKind kind() {
    return InversionKind.fromWireValue(kind);
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
   * Depth of the inversion layer in the caller's length units (meters for metric, feet for
   * imperial). For {@code elevated}, this is the thickness of the warm layer (top − base).
   *
   * @return the {@code depth} value
   */
  public double depth() {
    return depth;
  }

  /**
   * Temperature difference driving the inversion in the caller's temperature units. Always
   * positive: the warm part minus the cold part.
   *
   * @return the {@code strength} value
   */
  public double strength() {
    return strength;
  }

  @Override
  Object[] values() {
    return new Object[] {kind, depth, strength};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"kind", "depth", "strength"};

  /** Builds a {@link InversionInfo}. */
  public static final class Builder {
    private @Nullable String kind;
    private @Nullable Double depth;
    private @Nullable Double strength;

    private Builder() {}

    /**
     * Sets {@link InversionInfo#kind()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder kind(InversionKind value) {
      this.kind = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link InversionInfo#kindAsString()}, the raw wire value, kept verbatim even when this
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
     * Sets {@link InversionInfo#depth()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder depth(double value) {
      this.depth = value;
      return this;
    }

    /**
     * Sets {@link InversionInfo#strength()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder strength(double value) {
      this.strength = value;
      return this;
    }

    /**
     * Builds the {@link InversionInfo}.
     *
     * @return the immutable InversionInfo
     * @throws IllegalStateException if a required value was not set
     */
    public InversionInfo build() {
      return new InversionInfo(this);
    }
  }
}
