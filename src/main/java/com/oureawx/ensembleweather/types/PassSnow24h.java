// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * The next 24 h of snow at the pass: exactly the default forecast's own {@code
 * snow_outlook.next_24h} and {@code snow_outlook.quantiles.next_24h} (unpinned, the nowcast on, the
 * same cycle), so the pass page, the passes board and search state one number.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassSnow24h}.
 */
public final class PassSnow24h extends Model {
  private final @Nullable Double total;
  private final @Nullable Double p10;
  private final @Nullable Double p90;
  private final @Nullable Instant startsAt;

  private PassSnow24h(Builder b) {
    this.total = b.total;
    this.p10 = b.p10;
    this.p90 = b.p90;
    this.startsAt = b.startsAt;
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
    b.total = this.total;
    b.p10 = this.p10;
    b.p90 = this.p90;
    b.startsAt = this.startsAt;
    return b;
  }

  /**
   * {@code snow_outlook.next_24h} (mm, in); null where the forecast does not reach 24 h.
   *
   * @return the {@code total} value, or empty when the response omits it
   */
  public OptionalDouble total() {
    return total == null ? OptionalDouble.empty() : OptionalDouble.of(total);
  }

  /**
   * {@code snow_outlook.quantiles.next_24h.p10}; null without members covering the window.
   *
   * @return the {@code p10} value, or empty when the response omits it
   */
  public OptionalDouble p10() {
    return p10 == null ? OptionalDouble.empty() : OptionalDouble.of(p10);
  }

  /**
   * {@code snow_outlook.quantiles.next_24h.p90}.
   *
   * @return the {@code p90} value, or empty when the response omits it
   */
  public OptionalDouble p90() {
    return p90 == null ? OptionalDouble.empty() : OptionalDouble.of(p90);
  }

  /**
   * The start of the first snow spell ({@code snow_windows[0].start}) when it begins inside the 24
   * h; null otherwise.
   *
   * @return the {@code starts_at} value, or empty when the response omits it
   */
  public Optional<Instant> startsAt() {
    return Optional.ofNullable(startsAt);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {total, p10, p90, startsAt};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"total", "p10", "p90", "startsAt"};

  /** Builds a {@link PassSnow24h}. */
  public static final class Builder {
    private @Nullable Double total;
    private @Nullable Double p10;
    private @Nullable Double p90;
    private @Nullable Instant startsAt;

    private Builder() {}

    /**
     * Sets {@link PassSnow24h#total()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder total(@Nullable Double value) {
      this.total = value;
      return this;
    }

    /**
     * Sets {@link PassSnow24h#p10()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p10(@Nullable Double value) {
      this.p10 = value;
      return this;
    }

    /**
     * Sets {@link PassSnow24h#p90()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder p90(@Nullable Double value) {
      this.p90 = value;
      return this;
    }

    /**
     * Sets {@link PassSnow24h#startsAt()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder startsAt(@Nullable Instant value) {
      this.startsAt = value;
      return this;
    }

    /**
     * Builds the {@link PassSnow24h}.
     *
     * @return the immutable PassSnow24h
     * @throws IllegalStateException if a required value was not set
     */
    public PassSnow24h build() {
      return new PassSnow24h(this);
    }
  }
}
