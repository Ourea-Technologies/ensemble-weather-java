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
  private final String certainty;
  private final String headline;
  private final @Nullable Instant onset;
  private final Instant expires;
  private final @Nullable Instant ends;

  private ActiveAlert(Builder b) {
    this.id = require(b.id, "id");
    this.event = require(b.event, "event");
    this.severity = require(b.severity, "severity");
    this.urgency = require(b.urgency, "urgency");
    this.certainty = require(b.certainty, "certainty");
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
    b.certainty = this.certainty;
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
   * The NWS severity, snake_case like every enum (it passed NWS's {@code Severe} casing through).
   * Additive: treat an unknown value as {@code unknown}.
   *
   * @return the {@code severity} value ({@link AlertSeverity#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public AlertSeverity severity() {
    return AlertSeverity.fromWireValue(severity);
  }

  /**
   * The {@code severity} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code severity} string
   */
  public String severityAsString() {
    return severity;
  }

  /**
   * The NWS urgency, snake_case.
   *
   * @return the {@code urgency} value ({@link AlertUrgency#UNKNOWN} for a value this SDK version
   *     does not know)
   */
  public AlertUrgency urgency() {
    return AlertUrgency.fromWireValue(urgency);
  }

  /**
   * The {@code urgency} value exactly as the API sent it, even when this SDK version does not know
   * it.
   *
   * @return the raw {@code urgency} string
   */
  public String urgencyAsString() {
    return urgency;
  }

  /**
   * The NWS certainty, snake_case (new).
   *
   * @return the {@code certainty} value ({@link AlertCertainty#UNKNOWN} for a value this SDK
   *     version does not know)
   */
  public AlertCertainty certainty() {
    return AlertCertainty.fromWireValue(certainty);
  }

  /**
   * The {@code certainty} value exactly as the API sent it, even when this SDK version does not
   * know it.
   *
   * @return the raw {@code certainty} string
   */
  public String certaintyAsString() {
    return certainty;
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
    return new Object[] {id, event, severity, urgency, certainty, headline, onset, expires, ends};
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id", "event", "severity", "urgency", "certainty", "headline", "onset", "expires", "ends"
  };

  /** Builds a {@link ActiveAlert}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String event;
    private @Nullable String severity;
    private @Nullable String urgency;
    private @Nullable String certainty;
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
    public Builder severity(AlertSeverity value) {
      this.severity = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ActiveAlert#severityAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder severityAsString(String value) {
      this.severity = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#urgency()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder urgency(AlertUrgency value) {
      this.urgency = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ActiveAlert#urgencyAsString()}, the raw wire value, kept verbatim even when this
     * SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder urgencyAsString(String value) {
      this.urgency = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#certainty()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder certainty(AlertCertainty value) {
      this.certainty = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link ActiveAlert#certaintyAsString()}, the raw wire value, kept verbatim even when
     * this SDK version does not know it.
     *
     * @param value the raw value
     * @return this builder
     */
    public Builder certaintyAsString(String value) {
      this.certainty = value;
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
