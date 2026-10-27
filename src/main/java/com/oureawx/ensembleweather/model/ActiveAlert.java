// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Compact active NWS alert (headline carries the user text).
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code ActiveAlert}.
 */
public final class ActiveAlert extends Model {
  private final String id;
  private final String event;
  private final String severity;
  private final String urgency;
  private final String headline;
  private final @Nullable Instant onset;
  private final Instant expires;
  private final @Nullable Instant ends;

  private ActiveAlert(Builder b) {
    this.id = require(b.id, "id");
    this.event = require(b.event, "event");
    this.severity = require(b.severity, "severity");
    this.urgency = require(b.urgency, "urgency");
    this.headline = require(b.headline, "headline");
    this.onset = b.onset;
    this.expires = require(b.expires, "expires");
    this.ends = b.ends;
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
    b.event = this.event;
    b.severity = this.severity;
    b.urgency = this.urgency;
    b.headline = this.headline;
    b.onset = this.onset;
    b.expires = this.expires;
    b.ends = this.ends;
    return b;
  }

  /**
   * NWS alert identifier (urn:oid:…).
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * The {@code event} value.
   *
   * @return the {@code event} value
   */
  public String event() {
    return event;
  }

  /**
   * NWS severity (Extreme/Severe/Moderate/Minor/Unknown).
   *
   * @return the {@code severity} value
   */
  public String severity() {
    return severity;
  }

  /**
   * The {@code urgency} value.
   *
   * @return the {@code urgency} value
   */
  public String urgency() {
    return urgency;
  }

  /**
   * The {@code headline} value.
   *
   * @return the {@code headline} value
   */
  public String headline() {
    return headline;
  }

  /**
   * Omitted when NWS supplies none.
   *
   * @return the {@code onset} value, or empty when the response omits it
   */
  public Optional<Instant> onset() {
    return Optional.ofNullable(onset);
  }

  /**
   * The {@code expires} value.
   *
   * @return the {@code expires} value
   */
  public Instant expires() {
    return expires;
  }

  /**
   * Omitted when NWS supplies none.
   *
   * @return the {@code ends} value, or empty when the response omits it
   */
  public Optional<Instant> ends() {
    return Optional.ofNullable(ends);
  }

  @Override
  Object[] values() {
    return new Object[] {id, event, severity, urgency, headline, onset, expires, ends};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id", "event", "severity", "urgency", "headline", "onset", "expires", "ends"
  };

  /** Builds a {@link ActiveAlert}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String event;
    private @Nullable String severity;
    private @Nullable String urgency;
    private @Nullable String headline;
    private @Nullable Instant onset;
    private @Nullable Instant expires;
    private @Nullable Instant ends;

    private Builder() {}

    /**
     * Sets {@link ActiveAlert#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#event()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder event(String value) {
      this.event = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#severity()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder severity(String value) {
      this.severity = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#urgency()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder urgency(String value) {
      this.urgency = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#headline()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder headline(String value) {
      this.headline = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#onset()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder onset(@Nullable Instant value) {
      this.onset = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#expires()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder expires(Instant value) {
      this.expires = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#ends()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder ends(@Nullable Instant value) {
      this.ends = value;
      return this;
    }

    /**
     * Builds the {@link ActiveAlert}.
     *
     * @return the immutable ActiveAlert
     * @throws IllegalStateException if a required value was not set
     */
    public ActiveAlert build() {
      return new ActiveAlert(this);
    }
  }
}
