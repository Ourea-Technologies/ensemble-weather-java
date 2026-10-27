// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * The kind of a page-template section.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum TemplateSectionKind {
  /** {@code weather_panel}. */
  WEATHER_PANEL("weather_panel"),
  /** {@code content_slot}. */
  CONTENT_SLOT("content_slot"),
  /** {@code ad_slot}. */
  AD_SLOT("ad_slot"),
  /** {@code media}. */
  MEDIA("media"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  TemplateSectionKind(String value) {
    this.value = value;
  }

  /**
   * The API's spelling of this value.
   *
   * @return the wire value, or empty for {@link #UNKNOWN}
   */
  public Optional<String> wireValue() {
    return value.isEmpty() ? Optional.empty() : Optional.of(value);
  }

  /**
   * Parses the API's spelling.
   *
   * @param value the wire value
   * @return the matching constant, or {@link #UNKNOWN}
   */
  public static TemplateSectionKind fromWireValue(String value) {
    for (TemplateSectionKind c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
