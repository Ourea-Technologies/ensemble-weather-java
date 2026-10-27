// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One filled ad slot as the public API serves it. Exactly one of {@code house} / {@code
 * third_party} is present, discriminated by {@code kind}. Report impressions/clicks for it via
 * {@code POST /api/v1/events/ad}.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code CatalogResolvedAdSlot}.
 */
public final class CatalogResolvedAdSlot extends Model {
  private final String slotKey;
  private final String kind;
  private final @Nullable CatalogHousePlacement house;
  private final @Nullable CatalogThirdPartyPlacement thirdParty;
  private final List<List<Integer>> sizes;

  private CatalogResolvedAdSlot(Builder b) {
    this.slotKey = require(b.slotKey, "slot_key");
    this.kind = require(b.kind, "kind");
    this.house = b.house;
    this.thirdParty = b.thirdParty;
    this.sizes = List.copyOf(b.sizes);
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
    b.slotKey = this.slotKey;
    b.kind = this.kind;
    b.house = this.house;
    b.thirdParty = this.thirdParty;
    b.sizes = this.sizes;
    return b;
  }

  /**
   * The {@code slot_key} value.
   *
   * @return the {@code slot_key} value
   */
  public String slotKey() {
    return slotKey;
  }

  /**
   * The {@code kind} value.
   *
   * @return the {@code kind} value ({@link AdSlotKind#UNKNOWN} for a value this SDK version does
   *     not know)
   */
  public AdSlotKind kind() {
    return AdSlotKind.fromWireValue(kind);
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
   * Present iff {@code kind = house}.
   *
   * @return the {@code house} value, or empty when the response omits it
   */
  public Optional<CatalogHousePlacement> house() {
    return Optional.ofNullable(house);
  }

  /**
   * Present iff {@code kind = third_party}.
   *
   * @return the {@code third_party} value, or empty when the response omits it
   */
  public Optional<CatalogThirdPartyPlacement> thirdParty() {
    return Optional.ofNullable(thirdParty);
  }

  /**
   * The slot's client rendering hints ({@code default_sizes}).
   *
   * @return the {@code sizes} values; empty when the response has none
   */
  public List<List<Integer>> sizes() {
    return sizes;
  }

  @Override
  Object[] values() {
    return new Object[] {slotKey, kind, house, thirdParty, sizes};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {"slotKey", "kind", "house", "thirdParty", "sizes"};

  /** Builds a {@link CatalogResolvedAdSlot}. */
  public static final class Builder {
    private @Nullable String slotKey;
    private @Nullable String kind;
    private @Nullable CatalogHousePlacement house;
    private @Nullable CatalogThirdPartyPlacement thirdParty;
    private List<List<Integer>> sizes = List.of();

    private Builder() {}

    /**
     * Sets {@link CatalogResolvedAdSlot#slotKey()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder slotKey(String value) {
      this.slotKey = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedAdSlot#kind()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder kind(AdSlotKind value) {
      this.kind = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link CatalogResolvedAdSlot#kindAsString()}, the raw wire value, kept verbatim even
     * when this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder kindAsString(String value) {
      this.kind = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedAdSlot#house()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder house(@Nullable CatalogHousePlacement value) {
      this.house = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedAdSlot#thirdParty()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder thirdParty(@Nullable CatalogThirdPartyPlacement value) {
      this.thirdParty = value;
      return this;
    }

    /**
     * Sets {@link CatalogResolvedAdSlot#sizes()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder sizes(List<List<Integer>> values) {
      this.sizes = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link CatalogResolvedAdSlot}.
     *
     * @return the immutable CatalogResolvedAdSlot
     * @throws IllegalStateException if a required value was not set
     */
    public CatalogResolvedAdSlot build() {
      return new CatalogResolvedAdSlot(this);
    }
  }
}
