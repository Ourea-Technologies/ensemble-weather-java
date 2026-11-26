// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
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

  private final @Nullable Instant sent;
  private final @Nullable String messageType;
  private final List<String> references;
  private final @Nullable String senderName;
  private final @Nullable String areaDesc;
  private final @Nullable String description;
  private final @Nullable String instruction;
  private final @Nullable String sourceUrl;
  private final @Nullable String eventKey;
  private final @Nullable String vtecAction;

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
    this.sent = b.sent;
    this.messageType = b.messageType;
    this.references = List.copyOf(b.references);
    this.senderName = b.senderName;
    this.areaDesc = b.areaDesc;
    this.description = b.description;
    this.instruction = b.instruction;
    this.sourceUrl = b.sourceUrl;
    this.eventKey = b.eventKey;
    this.vtecAction = b.vtecAction;
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
    b.sent = this.sent;
    b.messageType = this.messageType;
    b.references = this.references;
    b.senderName = this.senderName;
    b.areaDesc = this.areaDesc;
    b.description = this.description;
    b.instruction = this.instruction;
    b.sourceUrl = this.sourceUrl;
    b.eventKey = this.eventKey;
    b.vtecAction = this.vtecAction;
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

  /**
   * When NWS issued this message.
   *
   * @return the {@code sent} value, or empty when the response omits it
   */
  public Optional<Instant> sent() {
    return Optional.ofNullable(sent);
  }

  /**
   * The CAP message type, snake_case. Not a sign of a new event: NWS sends messages typed {@code
   * alert} that continue or end an existing one. Use {@code event_key}. Additive: treat an unknown
   * value as absent. One of {@code alert}, {@code update}, {@code cancel}; read it as a string,
   * since the API may add values.
   *
   * @return the {@code message_type} value, or empty when the response omits it
   */
  public Optional<String> messageType() {
    return Optional.ofNullable(messageType);
  }

  /**
   * The ids of the messages this one replaces. Omitted when there are none.
   *
   * @return the {@code references} values; empty when the response has none
   */
  public List<String> references() {
    return references;
  }

  /**
   * The {@code sender_name} value.
   *
   * @return the {@code sender_name} value, or empty when the response omits it
   */
  public Optional<String> senderName() {
    return Optional.ofNullable(senderName);
  }

  /**
   * The places this message covers, as NWS words them.
   *
   * @return the {@code area_desc} value, or empty when the response omits it
   */
  public Optional<String> areaDesc() {
    return Optional.ofNullable(areaDesc);
  }

  /**
   * The full NWS text, unedited.
   *
   * @return the {@code description} value, or empty when the response omits it
   */
  public Optional<String> description() {
    return Optional.ofNullable(description);
  }

  /**
   * What NWS says to do, unedited. Omitted when NWS gives none.
   *
   * @return the {@code instruction} value, or empty when the response omits it
   */
  public Optional<String> instruction() {
    return Optional.ofNullable(instruction);
  }

  /**
   * The NWS forecast page for this location.
   *
   * @return the {@code source_url} value, or empty when the response omits it
   */
  public Optional<String> sourceUrl() {
    return Optional.ofNullable(sourceUrl);
  }

  /**
   * The stable identity of this event. Every message in one event has the same key, across NWS
   * Updates and new {@code id}s, so to notify once per event, dedupe on this and nothing else.
   * Treat it as opaque. Omitted only on a message stored before this field existed.
   *
   * @return the {@code event_key} value, or empty when the response omits it
   */
  public Optional<String> eventKey() {
    return Optional.ofNullable(eventKey);
  }

  /**
   * The VTEC action of this message, lower case. {@code can}, {@code exp} and {@code upg} mean this
   * message ends the event. Omitted when NWS sent no VTEC. Additive: treat an unknown value as
   * absent. One of {@code new}, {@code con}, {@code ext}, {@code exa}, {@code exb}, {@code upg},
   * {@code can}, {@code exp}, {@code cor}, {@code rou}; read it as a string, since the API may add
   * values.
   *
   * @return the {@code vtec_action} value, or empty when the response omits it
   */
  public Optional<String> vtecAction() {
    return Optional.ofNullable(vtecAction);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      id,
      event,
      severity,
      urgency,
      certainty,
      headline,
      onset,
      expires,
      ends,
      sent,
      messageType,
      references,
      senderName,
      areaDesc,
      description,
      instruction,
      sourceUrl,
      eventKey,
      vtecAction
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id",
    "event",
    "severity",
    "urgency",
    "certainty",
    "headline",
    "onset",
    "expires",
    "ends",
    "sent",
    "messageType",
    "references",
    "senderName",
    "areaDesc",
    "description",
    "instruction",
    "sourceUrl",
    "eventKey",
    "vtecAction"
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

    private @Nullable Instant sent;
    private @Nullable String messageType;
    private List<String> references = List.of();
    private @Nullable String senderName;
    private @Nullable String areaDesc;
    private @Nullable String description;
    private @Nullable String instruction;
    private @Nullable String sourceUrl;
    private @Nullable String eventKey;
    private @Nullable String vtecAction;

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
     * Sets {@link ActiveAlert#sent()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder sent(@Nullable Instant value) {
      this.sent = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#messageType()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder messageType(@Nullable String value) {
      this.messageType = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#references()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder references(List<String> values) {
      this.references = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link ActiveAlert#senderName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder senderName(@Nullable String value) {
      this.senderName = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#areaDesc()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder areaDesc(@Nullable String value) {
      this.areaDesc = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#description()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder description(@Nullable String value) {
      this.description = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#instruction()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder instruction(@Nullable String value) {
      this.instruction = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#sourceUrl()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder sourceUrl(@Nullable String value) {
      this.sourceUrl = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#eventKey()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder eventKey(@Nullable String value) {
      this.eventKey = value;
      return this;
    }

    /**
     * Sets {@link ActiveAlert#vtecAction()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder vtecAction(@Nullable String value) {
      this.vtecAction = value;
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
