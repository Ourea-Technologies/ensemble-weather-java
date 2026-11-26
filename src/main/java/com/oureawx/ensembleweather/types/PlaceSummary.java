// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * A short plain-language summary of what is coming at a place, written by the server from templates
 * filled with served values (never free text from a model). Every number in {@code short} and
 * {@code long} is one of the served values in {@code events[].values}, rounded for reading (snow in
 * whole inches at 1 inch and above, one decimal below; whole centimetres in metric). Snow is always
 * a window's headline total (the forecast's {@code snow_outlook.next_*}, the sum of the hourly
 * means) with its served p10-p90 range, written "up to N" when p10 is under a trace. Likelihood
 * words: "likely" at a served chance of 60% or more, "possible" at 30-59%. Times are to the hour in
 * the place's own zone ({@code location.timezone}). The wording of one place is fixed for the whole
 * run: a stable hash of (place, elevation, run, event kind) picks among phrasings that state the
 * same facts, so the favorites row, the page, the web and the apps show the same text, and it
 * changes when a new run arrives.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PlaceSummary}.
 */
public final class PlaceSummary extends Model {
  private final String shortText;
  private final List<String> longText;
  private final List<SummaryEvent> events;
  private final @Nullable String elevationName;
  private final double elevation;
  private final Instant run;
  private final Instant generatedAt;

  private PlaceSummary(Builder b) {
    this.shortText = require(b.shortText, "short");
    this.longText = List.copyOf(b.longText);
    this.events = List.copyOf(b.events);
    this.elevationName = b.elevationName;
    this.elevation = require(b.elevation, "elevation");
    this.run = require(b.run, "run");
    this.generatedAt = require(b.generatedAt, "generated_at");
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
    b.shortText = this.shortText;
    b.longText = this.longText;
    b.events = this.events;
    b.elevationName = this.elevationName;
    b.elevation = this.elevation;
    b.run = this.run;
    b.generatedAt = this.generatedAt;
    return b;
  }

  /**
   * One sentence: the most important thing at the place, by priority (an NWS alert, precipitation
   * starting or stopping soon, snow coming, wind or a large temperature change, then the day's
   * headline). The favorites row, widgets and notification previews show it.
   *
   * @return the {@code short} value
   */
  public String shortText() {
    return shortText;
  }

  /**
   * One to three sentences for the location page, each from a different kind of event. {@code
   * long[0]} is always {@code short}.
   *
   * @return the {@code long} values; empty when the response has none
   */
  public List<String> longText() {
    return longText;
  }

  /**
   * The event behind each sentence of {@code long}, in the same order ({@code events[i]} wrote
   * {@code long[i]}), so a client can style a sentence (an alert chip, a snow icon) without parsing
   * text.
   *
   * @return the {@code events} values; empty when the response has none
   */
  public List<SummaryEvent> events() {
    return events;
  }

  /**
   * The served elevation the sentences describe: its display name where the catalog has one, else
   * its name. Omitted for a place with no named elevations.
   *
   * @return the {@code elevation_name} value, or empty when the response omits it
   */
  public Optional<String> elevationName() {
    return Optional.ofNullable(elevationName);
  }

  /**
   * The height the sentences describe, in the response's length units (m, ft).
   *
   * @return the {@code elevation} value
   */
  public double elevation() {
    return elevation;
  }

  /**
   * The init of the forecast run the sentences were written from.
   *
   * @return the {@code run} value
   */
  public Instant run() {
    return run;
  }

  /**
   * When the sentences were written (the request's now); the "updated" stamp.
   *
   * @return the {@code generated_at} value
   */
  public Instant generatedAt() {
    return generatedAt;
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {shortText, longText, events, elevationName, elevation, run, generatedAt};
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "shortText", "longText", "events", "elevationName", "elevation", "run", "generatedAt"
  };

  /** Builds a {@link PlaceSummary}. */
  public static final class Builder {
    private @Nullable String shortText;
    private List<String> longText = List.of();
    private List<SummaryEvent> events = List.of();
    private @Nullable String elevationName;
    private @Nullable Double elevation;
    private @Nullable Instant run;
    private @Nullable Instant generatedAt;

    private Builder() {}

    /**
     * Sets {@link PlaceSummary#shortText()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder shortText(String value) {
      this.shortText = value;
      return this;
    }

    /**
     * Sets {@link PlaceSummary#longText()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder longText(List<String> values) {
      this.longText = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlaceSummary#events()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder events(List<SummaryEvent> values) {
      this.events = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link PlaceSummary#elevationName()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder elevationName(@Nullable String value) {
      this.elevationName = value;
      return this;
    }

    /**
     * Sets {@link PlaceSummary#elevation()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder elevation(double value) {
      this.elevation = value;
      return this;
    }

    /**
     * Sets {@link PlaceSummary#run()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder run(Instant value) {
      this.run = value;
      return this;
    }

    /**
     * Sets {@link PlaceSummary#generatedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder generatedAt(Instant value) {
      this.generatedAt = value;
      return this;
    }

    /**
     * Builds the {@link PlaceSummary}.
     *
     * @return the immutable PlaceSummary
     * @throws IllegalStateException if a required value was not set
     */
    public PlaceSummary build() {
      return new PlaceSummary(this);
    }
  }
}
