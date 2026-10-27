// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * The primary type of a location, snake_case on the wire.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum LocationType {
  /** {@code airport}. */
  AIRPORT("airport"),
  /** {@code backcountry}. */
  BACKCOUNTRY("backcountry"),
  /** {@code beach_coast}. */
  BEACH_COAST("beach_coast"),
  /** {@code campground}. */
  CAMPGROUND("campground"),
  /** {@code city_town}. */
  CITY_TOWN("city_town"),
  /** {@code climbing_area}. */
  CLIMBING_AREA("climbing_area"),
  /** {@code coastal_point}. */
  COASTAL_POINT("coastal_point"),
  /** {@code event_venue}. */
  EVENT_VENUE("event_venue"),
  /** {@code fire_lookout}. */
  FIRE_LOOKOUT("fire_lookout"),
  /** {@code fishing_river_access}. */
  FISHING_RIVER_ACCESS("fishing_river_access"),
  /** {@code golf_course}. */
  GOLF_COURSE("golf_course"),
  /** {@code hot_spring}. */
  HOT_SPRING("hot_spring"),
  /** {@code lake}. */
  LAKE("lake"),
  /** {@code mountain_pass}. */
  MOUNTAIN_PASS("mountain_pass"),
  /** {@code national_park}. */
  NATIONAL_PARK("national_park"),
  /** {@code observation_station}. */
  OBSERVATION_STATION("observation_station"),
  /** {@code other}. */
  OTHER("other"),
  /** {@code park}. */
  PARK("park"),
  /** {@code recreation_area}. */
  RECREATION_AREA("recreation_area"),
  /** {@code reservoir_lake}. */
  RESERVOIR_LAKE("reservoir_lake"),
  /** {@code scenic_tourist}. */
  SCENIC_TOURIST("scenic_tourist"),
  /** {@code ski_area}. */
  SKI_AREA("ski_area"),
  /** {@code state_park}. */
  STATE_PARK("state_park"),
  /** {@code summit}. */
  SUMMIT("summit"),
  /** {@code trailhead}. */
  TRAILHEAD("trailhead"),
  /** {@code volcano}. */
  VOLCANO("volcano"),
  /** {@code waterfall}. */
  WATERFALL("waterfall"),
  /** {@code wine_agritourism}. */
  WINE_AGRITOURISM("wine_agritourism"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  LocationType(String value) {
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
  public static LocationType fromWireValue(String value) {
    for (LocationType c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
