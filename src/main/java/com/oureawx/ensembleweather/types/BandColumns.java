// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import java.util.OptionalDouble;

/**
 * One band's statistics (Hyndman-Fan type 7 over the served member values), each an array aligned
 * to {@code PlumeBands.valid_time}, {@code null} where the band is absent at that hour. {@code
 * P50}, the members' median, is served to Plus and Pro ({@code plume_detail}) and is ABSENT, the
 * key itself, for Free and anonymous callers ({@code members_view: pool}); every other column is
 * served to every caller.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code BandColumns}.
 */
public final class BandColumns extends Model {
  private final List<OptionalDouble> mean;
  private final List<OptionalDouble> min;
  private final List<OptionalDouble> p10;
  private final List<OptionalDouble> p25;
  private final List<OptionalDouble> p50;
  private final List<OptionalDouble> p75;
  private final List<OptionalDouble> p90;
  private final List<OptionalDouble> max;

  private BandColumns(Builder b) {
    this.mean = List.copyOf(b.mean);
    this.min = List.copyOf(b.min);
    this.p10 = List.copyOf(b.p10);
    this.p25 = List.copyOf(b.p25);
    this.p50 = List.copyOf(b.p50);
    this.p75 = List.copyOf(b.p75);
    this.p90 = List.copyOf(b.p90);
    this.max = List.copyOf(b.max);
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
    b.mean = this.mean;
    b.min = this.min;
    b.p10 = this.p10;
    b.p25 = this.p25;
    b.p50 = this.p50;
    b.p75 = this.p75;
    b.p90 = this.p90;
    b.max = this.max;
    return b;
  }

  /**
   * The {@code mean} value.
   *
   * @return the {@code mean} values; empty when the response has none
   */
  public List<OptionalDouble> mean() {
    return mean;
  }

  /**
   * The {@code min} value.
   *
   * @return the {@code min} values; empty when the response has none
   */
  public List<OptionalDouble> min() {
    return min;
  }

  /**
   * The {@code p10} value.
   *
   * @return the {@code p10} values; empty when the response has none
   */
  public List<OptionalDouble> p10() {
    return p10;
  }

  /**
   * The {@code p25} value.
   *
   * @return the {@code p25} values; empty when the response has none
   */
  public List<OptionalDouble> p25() {
    return p25;
  }

  /**
   * The {@code p50} value.
   *
   * @return the {@code p50} values; empty when the response has none
   */
  public List<OptionalDouble> p50() {
    return p50;
  }

  /**
   * The {@code p75} value.
   *
   * @return the {@code p75} values; empty when the response has none
   */
  public List<OptionalDouble> p75() {
    return p75;
  }

  /**
   * The {@code p90} value.
   *
   * @return the {@code p90} values; empty when the response has none
   */
  public List<OptionalDouble> p90() {
    return p90;
  }

  /**
   * The {@code max} value.
   *
   * @return the {@code max} values; empty when the response has none
   */
  public List<OptionalDouble> max() {
    return max;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {mean, min, p10, p25, p50, p75, p90, max};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"mean", "min", "p10", "p25", "p50", "p75", "p90", "max"};

  /** Builds a {@link BandColumns}. */
  public static final class Builder {
    private List<OptionalDouble> mean = List.of();
    private List<OptionalDouble> min = List.of();
    private List<OptionalDouble> p10 = List.of();
    private List<OptionalDouble> p25 = List.of();
    private List<OptionalDouble> p50 = List.of();
    private List<OptionalDouble> p75 = List.of();
    private List<OptionalDouble> p90 = List.of();
    private List<OptionalDouble> max = List.of();

    private Builder() {}

    /**
     * Sets {@link BandColumns#mean()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder mean(List<OptionalDouble> values) {
      this.mean = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link BandColumns#min()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder min(List<OptionalDouble> values) {
      this.min = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link BandColumns#p10()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p10(List<OptionalDouble> values) {
      this.p10 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link BandColumns#p25()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p25(List<OptionalDouble> values) {
      this.p25 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link BandColumns#p50()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p50(List<OptionalDouble> values) {
      this.p50 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link BandColumns#p75()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p75(List<OptionalDouble> values) {
      this.p75 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link BandColumns#p90()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder p90(List<OptionalDouble> values) {
      this.p90 = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link BandColumns#max()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder max(List<OptionalDouble> values) {
      this.max = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link BandColumns}.
     *
     * @return the immutable BandColumns
     * @throws IllegalStateException if a required value was not set
     */
    public BandColumns build() {
      return new BandColumns(this);
    }
  }
}
