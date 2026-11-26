// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.types;

import java.time.Instant;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * One DOT chain-control or closure point, as the DOT publishes it.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code PassStatusSegment}.
 */
public final class PassStatusSegment extends Model {
  private final String id;
  private final String route;
  private final String direction;
  private final String location;
  private final String state;
  private final String level;
  private final String text;
  private final Instant updatedAt;
  private final double lat;
  private final double lon;
  private final @Nullable String roadCondition;
  private final @Nullable String weatherCondition;

  private PassStatusSegment(Builder b) {
    this.id = require(b.id, "id");
    this.route = require(b.route, "route");
    this.direction = require(b.direction, "direction");
    this.location = require(b.location, "location");
    this.state = require(b.state, "status");
    this.level = require(b.level, "level");
    this.text = require(b.text, "status_text");
    this.updatedAt = require(b.updatedAt, "updated_at");
    this.lat = require(b.lat, "lat");
    this.lon = require(b.lon, "lon");
    this.roadCondition = b.roadCondition;
    this.weatherCondition = b.weatherCondition;
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
    b.route = this.route;
    b.direction = this.direction;
    b.location = this.location;
    b.state = this.state;
    b.level = this.level;
    b.text = this.text;
    b.updatedAt = this.updatedAt;
    b.lat = this.lat;
    b.lon = this.lon;
    b.roadCondition = this.roadCondition;
    b.weatherCondition = this.weatherCondition;
    return b;
  }

  /**
   * The DOT's own id for the point.
   *
   * @return the {@code id} value
   */
  public String id() {
    return id;
  }

  /**
   * The {@code route} value.
   *
   * @return the {@code route} value
   */
  public String route() {
    return route;
  }

  /**
   * The direction the point controls, as the DOT names it.
   *
   * @return the {@code direction} value
   */
  public String direction() {
    return direction;
  }

  /**
   * The DOT's name for the point (its trailing field padding removed).
   *
   * @return the {@code location} value
   */
  public String location() {
    return location;
  }

  /**
   * (JSON {@code status}) The DOT's status code, verbatim.
   *
   * @return the {@code status} value
   */
  public String state() {
    return state;
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
   * (JSON {@code status_text}) The DOT's own description of the status, verbatim.
   *
   * @return the {@code status_text} value
   */
  public String text() {
    return text;
  }

  /**
   * When the DOT set this status.
   *
   * @return the {@code updated_at} value
   */
  public Instant updatedAt() {
    return updatedAt;
  }

  /**
   * The {@code lat} value.
   *
   * @return the {@code lat} value
   */
  public double lat() {
    return lat;
  }

  /**
   * The {@code lon} value.
   *
   * @return the {@code lon} value
   */
  public double lon() {
    return lon;
  }

  /**
   * The DOT's own report of the road surface there, verbatim, where it gives one beside the
   * restriction (WSDOT, ODOT). Omitted otherwise.
   *
   * @return the {@code road_condition} value, or empty when the response omits it
   */
  public Optional<String> roadCondition() {
    return Optional.ofNullable(roadCondition);
  }

  /**
   * The DOT's own report of the weather there, verbatim (WSDOT, ODOT). Omitted otherwise. Not a
   * forecast of Ourea's.
   *
   * @return the {@code weather_condition} value, or empty when the response omits it
   */
  public Optional<String> weatherCondition() {
    return Optional.ofNullable(weatherCondition);
  }

  @Override
  Object[] fieldValues() {
    return new Object[] {
      id,
      route,
      direction,
      location,
      state,
      level,
      text,
      updatedAt,
      lat,
      lon,
      roadCondition,
      weatherCondition
    };
  }

  @Override
  String[] fieldNames() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "id",
    "route",
    "direction",
    "location",
    "state",
    "level",
    "text",
    "updatedAt",
    "lat",
    "lon",
    "roadCondition",
    "weatherCondition"
  };

  /** Builds a {@link PassStatusSegment}. */
  public static final class Builder {
    private @Nullable String id;
    private @Nullable String route;
    private @Nullable String direction;
    private @Nullable String location;
    private @Nullable String state;
    private @Nullable String level;
    private @Nullable String text;
    private @Nullable Instant updatedAt;
    private @Nullable Double lat;
    private @Nullable Double lon;
    private @Nullable String roadCondition;
    private @Nullable String weatherCondition;

    private Builder() {}

    /**
     * Sets {@link PassStatusSegment#id()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder id(String value) {
      this.id = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#route()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder route(String value) {
      this.route = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#direction()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder direction(String value) {
      this.direction = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#location()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder location(String value) {
      this.location = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#state()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder state(String value) {
      this.state = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#level()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder level(PassStatusLevel value) {
      this.level = wire(value.wireValue());
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#levelAsString()}, the raw wire value, kept verbatim even when
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
     * Sets {@link PassStatusSegment#text()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder text(String value) {
      this.text = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#updatedAt()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder updatedAt(Instant value) {
      this.updatedAt = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#lat()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lat(double value) {
      this.lat = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#lon()}. Required.
     *
     * @param value the value
     * @return this builder
     */
    public Builder lon(double value) {
      this.lon = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#roadCondition()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder roadCondition(@Nullable String value) {
      this.roadCondition = value;
      return this;
    }

    /**
     * Sets {@link PassStatusSegment#weatherCondition()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder weatherCondition(@Nullable String value) {
      this.weatherCondition = value;
      return this;
    }

    /**
     * Builds the {@link PassStatusSegment}.
     *
     * @return the immutable PassStatusSegment
     * @throws IllegalStateException if a required value was not set
     */
    public PassStatusSegment build() {
      return new PassStatusSegment(this);
    }
  }
}
