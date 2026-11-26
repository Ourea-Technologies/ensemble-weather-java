// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One served series' members in columns ({@code members=plume}). Every number is the row form's
 * ({@code members=true}) served value for the same request, at its served precision ("Precision"),
 * in the caller's units: temperature 0.1, an amount (precipitation, snowfall, a running total) 0.01
 * mm / 0.001 in, a height (snow level) 1 m / 1 ft, wind speed 0.1, a direction 1 degree, SLR 0.1;
 * {@code precipitation_hours} is a whole count. Inside a column {@code null} means "this member
 * published nothing at this step"; a variable the view withholds (Free and Plus: {@code
 * wind_speed}, {@code wind_direction}) is ABSENT. By tier ({@code members_view}). Plus ({@code
 * plume}) and Pro ({@code full}): {@code models[]}, the members by model on each model's own steps
 * with each model's median and mean lines, and every band with its {@code p50}; no {@code members}
 * key. Free and anonymous ({@code pool}): {@code models} is an EMPTY array, {@code members} carries
 * every member as one unlabelled pool on the bands' 6-hour grid ({@code PooledColumns}), and no
 * band has a {@code p50}; {@code headline} and every band's {@code mean}, {@code min}, {@code p10},
 * {@code p25}, {@code p75}, {@code p90}, {@code max} are the same numbers a paid caller gets for
 * the same request.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code MemberPlume}.
 */
public final class MemberPlume extends Model {
  private final PlumeHeadline headline;
  private final PlumeBands bands;
  private final List<ModelColumns> models;
  private final @Nullable PooledColumns pool;

  private MemberPlume(Builder b) {
    this.headline = require(b.headline, "headline");
    this.bands = require(b.bands, "bands");
    this.models = List.copyOf(b.models);
    this.pool = b.pool;
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
    b.headline = this.headline;
    b.bands = this.bands;
    b.models = this.models;
    b.pool = this.pool;
    return b;
  }

  /**
   * The {@code headline} value.
   *
   * @return the {@code headline} value
   */
  public PlumeHeadline headline() {
    return headline;
  }

  /**
   * The {@code bands} value.
   *
   * @return the {@code bands} value
   */
  public PlumeBands bands() {
    return bands;
  }

  /**
   * One entry per model with members in the served window, in the order aifs, aigefs, gefs,
   * ifs_ens, rrfs. Empty for Free and anonymous callers ({@code members_view: pool}), who are
   * served {@code members} instead.
   *
   * @return the {@code models} values; empty when the response has none
   */
  public List<ModelColumns> models() {
    return models;
  }

  /**
   * (JSON {@code members}) The {@code members} value.
   *
   * @return the {@code members} value, or empty when the response omits it
   */
  public Optional<PooledColumns> pool() {
    return Optional.ofNullable(pool);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {headline, bands, models, pool};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"headline", "bands", "models", "pool"};

  /** Builds a {@link MemberPlume}. */
  public static final class Builder {
    private @Nullable PlumeHeadline headline;
    private @Nullable PlumeBands bands;
    private List<ModelColumns> models = List.of();
    private @Nullable PooledColumns pool;

    private Builder() {}

    /**
     * Sets {@link MemberPlume#headline()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder headline(PlumeHeadline value) {
      this.headline = value;
      return this;
    }

    /**
     * Sets {@link MemberPlume#bands()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder bands(PlumeBands value) {
      this.bands = value;
      return this;
    }

    /**
     * Sets {@link MemberPlume#models()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder models(List<ModelColumns> values) {
      this.models = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link MemberPlume#pool()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder pool(@Nullable PooledColumns value) {
      this.pool = value;
      return this;
    }

    /**
     * Builds the {@link MemberPlume}.
     *
     * @return the immutable MemberPlume
     * @throws IllegalStateException if a required value was not set
     */
    public MemberPlume build() {
      return new MemberPlume(this);
    }
  }
}
