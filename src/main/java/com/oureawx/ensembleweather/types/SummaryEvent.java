// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A SummaryEvent from the v1 API.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code SummaryEvent}.
 */
public final class SummaryEvent extends Model {
  private final String kind;
  private final String template;
  private final @Nullable Instant at;
  private final @Nullable Instant until;
  private final @Nullable String alertId;
  private final @Nullable SummaryValues values;

  private SummaryEvent(Builder b) {
    this.kind = require(b.kind, "kind");
    this.template = require(b.template, "template");
    this.at = b.at;
    this.until = b.until;
    this.alertId = b.alertId;
    this.values = b.values;
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
    b.template = this.template;
    b.at = this.at;
    b.until = this.until;
    b.alertId = this.alertId;
    b.values = this.values;
    return b;
  }

  /**
   * What the sentence is about. Clients must ignore a kind they do not know: {@code
   * phase_at_elevation} and {@code change_since_last_run} are planned (v1.1). One of {@code alert},
   * {@code precip_starting}, {@code precip_stopping}, {@code snow_coming}, {@code wind}, {@code
   * temperature_change}, {@code day_headline}, {@code pass_snow_starts}, {@code pass_rain_to_snow},
   * {@code pass_freezing_on_wet_road}; read it as a string, since the API may add values.
   *
   * @return the {@code kind} value
   */
  public String kind() {
    return kind;
  }

  /**
   * The phrasing used, {@code <template>.<variant>} ({@code snow_coming.v1.3}), stable across
   * releases so sentences can be counted, reviewed and later translated.
   *
   * @return the {@code template} value
   */
  public String template() {
    return template;
  }

  /**
   * The hour the event begins (precipitation starting or stopping, an alert's onset, a pass event).
   *
   * @return the {@code at} value, or empty when the response omits it
   */
  public Optional<Instant> at() {
    return Optional.ofNullable(at);
  }

  /**
   * When the event ends, where the sentence says so (an alert's end, a snow window's end).
   *
   * @return the {@code until} value, or empty when the response omits it
   */
  public Optional<Instant> until() {
    return Optional.ofNullable(until);
  }

  /**
   * The {@code alerts[].id} an {@code alert} event states.
   *
   * @return the {@code alert_id} value, or empty when the response omits it
   */
  public Optional<String> alertId() {
    return Optional.ofNullable(alertId);
  }

  /**
   * The served numbers the sentence states, unrounded, in the response's units.
   *
   * @return the {@code values} value, or empty when the response omits it
   */
  public Optional<SummaryValues> values() {
    return Optional.ofNullable(values);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {kind, template, at, until, alertId, values};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {"kind", "template", "at", "until", "alertId", "values"};

  /** Builds a {@link SummaryEvent}. */
  public static final class Builder {
    private @Nullable String kind;
    private @Nullable String template;
    private @Nullable Instant at;
    private @Nullable Instant until;
    private @Nullable String alertId;
    private @Nullable SummaryValues values;

    private Builder() {}

    /**
     * Sets {@link SummaryEvent#kind()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder kind(String value) {
      this.kind = value;
      return this;
    }

    /**
     * Sets {@link SummaryEvent#template()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder template(String value) {
      this.template = value;
      return this;
    }

    /**
     * Sets {@link SummaryEvent#at()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder at(@Nullable Instant value) {
      this.at = value;
      return this;
    }

    /**
     * Sets {@link SummaryEvent#until()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder until(@Nullable Instant value) {
      this.until = value;
      return this;
    }

    /**
     * Sets {@link SummaryEvent#alertId()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder alertId(@Nullable String value) {
      this.alertId = value;
      return this;
    }

    /**
     * Sets {@link SummaryEvent#values()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder values(@Nullable SummaryValues value) {
      this.values = value;
      return this;
    }

    /**
     * Builds the {@link SummaryEvent}.
     *
     * @return the immutable SummaryEvent
     * @throws IllegalStateException if a required value was not set
     */
    public SummaryEvent build() {
      return new SummaryEvent(this);
    }
  }
}
