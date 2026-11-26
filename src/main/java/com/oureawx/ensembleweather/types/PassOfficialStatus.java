// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The DOT's own status for the road over the pass -- chain controls and closures as the DOT
 * publishes them, its wording verbatim. Never blended with the forecast and never a verdict of
 * Ourea's: a client labels it as the DOT's ("Caltrans says", "WSDOT says") and links to {@code
 * url}. Caltrans (CWWP2 chain controls, public domain) and WSDOT (Mountain Pass Conditions); CDOT
 * and ODOT are built and switched on once their keys and terms are signed for. A pass whose road
 * maps to no DOT feed has no {@code official_status}. Read every 5 minutes; {@code stale} marks a
 * {@code checked_at} more than 30 minutes old, and the block is still served so the client can say
 * how old it is.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassOfficialStatus}.
 */
public final class PassOfficialStatus extends Model {
  private final String source;
  private final String attribution;
  private final String url;
  private final Instant checkedAt;
  private final boolean stale;
  private final String level;
  private final List<PassStatusSegment> segments;

  private PassOfficialStatus(Builder b) {
    this.source = require(b.source, "source");
    this.attribution = require(b.attribution, "attribution");
    this.url = require(b.url, "url");
    this.checkedAt = require(b.checkedAt, "checked_at");
    this.stale = require(b.stale, "stale");
    this.level = require(b.level, "level");
    this.segments = List.copyOf(b.segments);
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
    b.source = this.source;
    b.attribution = this.attribution;
    b.url = this.url;
    b.checkedAt = this.checkedAt;
    b.stale = this.stale;
    b.level = this.level;
    b.segments = this.segments;
    return b;
  }

  /**
   * The DOT feed. added {@code wsdot}, {@code cdot} and {@code odot}; a client shows {@code
   * attribution} and should accept a source it does not know. One of {@code caltrans}, {@code
   * wsdot}, {@code cdot}, {@code odot}; read it as a string, since the API may add values.
   *
   * @return the {@code source} value
   */
  public String source() {
    return source;
  }

  /**
   * Who to credit, as the client shows it.
   *
   * @return the {@code attribution} value
   */
  public String attribution() {
    return attribution;
  }

  /**
   * The DOT's own page for this road, to link out to.
   *
   * @return the {@code url} value
   */
  public String url() {
    return url;
  }

  /**
   * How fresh the DOT's feed behind these segments was at Ourea's last read: the time the DOT wrote
   * the file Ourea last read (Caltrans rewrites it every minute; Ourea reads it every 5), and the
   * older of two when the segments span two DOT districts. A feed that does not say when it was
   * written (WSDOT, CDOT, ODOT) is as fresh as Ourea's last successful read of it; each segment's
   * {@code updated_at} is the DOT's own time for its report.
   *
   * @return the {@code checked_at} value
   */
  public Instant checkedAt() {
    return checkedAt;
  }

  /**
   * {@code checked_at} is more than 30 minutes before {@code as_of}.
   *
   * @return the {@code stale} value
   */
  public boolean stale() {
    return stale;
  }

  /**
   * The {@code level} value.
   *
   * @return the {@code level} value ({@link PassStatusLevel#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public PassStatusLevel level() {
    return PassStatusLevel.fromWireValue(level);
  }

  /**
   * The {@code level} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code level} string
   */
  public String levelAsString() {
    return level;
  }

  /**
   * The DOT's chain-control and closure points on the road over the pass, each direction, in the
   * pass's curated order. {@code level} is the most restrictive of theirs.
   *
   * @return the {@code segments} values; empty when the response has none
   */
  public List<PassStatusSegment> segments() {
    return segments;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {source, attribution, url, checkedAt, stale, level, segments};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "source", "attribution", "url", "checkedAt", "stale", "level", "segments"
  };

  /** Builds a {@link PassOfficialStatus}. */
  public static final class Builder {
    private @Nullable String source;
    private @Nullable String attribution;
    private @Nullable String url;
    private @Nullable Instant checkedAt;
    private @Nullable Boolean stale;
    private @Nullable String level;
    private List<PassStatusSegment> segments = List.of();

    private Builder() {}

    /**
     * Sets {@link PassOfficialStatus#source()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder source(String value) {
      this.source = value;
      return this;
    }

    /**
     * Sets {@link PassOfficialStatus#attribution()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder attribution(String value) {
      this.attribution = value;
      return this;
    }

    /**
     * Sets {@link PassOfficialStatus#url()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder url(String value) {
      this.url = value;
      return this;
    }

    /**
     * Sets {@link PassOfficialStatus#checkedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder checkedAt(Instant value) {
      this.checkedAt = value;
      return this;
    }

    /**
     * Sets {@link PassOfficialStatus#stale()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder stale(boolean value) {
      this.stale = value;
      return this;
    }

    /**
     * Sets {@link PassOfficialStatus#level()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder level(PassStatusLevel value) {
      this.level = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link PassOfficialStatus#levelAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder levelAsString(String value) {
      this.level = value;
      return this;
    }

    /**
     * Sets {@link PassOfficialStatus#segments()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder segments(List<PassStatusSegment> values) {
      this.segments = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link PassOfficialStatus}.
     *
     * @return the immutable PassOfficialStatus
     * @throws IllegalStateException if a required value was not set
     */
    public PassOfficialStatus build() {
      return new PassOfficialStatus(this);
    }
  }
}
