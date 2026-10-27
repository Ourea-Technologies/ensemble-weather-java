// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.util.Objects;
import java.util.OptionalDouble;
import org.jspecify.annotations.Nullable;

/**
 * A point (WGS84 latitude and longitude, west negative), optionally with its elevation in metres.
 * Coordinates are sent exactly as given; the server quantizes them to 0.01°. Without an elevation
 * the server reads the ground's from its 10 m DEM.
 */
public final class Coordinates {
  private final double lat;
  private final double lon;
  private final @Nullable Double elevationM;

  private Coordinates(double lat, double lon, @Nullable Double elevationM) {
    if (!Double.isFinite(lat) || !Double.isFinite(lon)) {
      throw new UsageException("coordinates must be finite numbers, not " + lat + ", " + lon);
    }
    if (elevationM != null && !Double.isFinite(elevationM)) {
      throw new UsageException("an elevation must be a finite number, not " + elevationM);
    }
    this.lat = lat;
    this.lon = lon;
    this.elevationM = elevationM;
  }

  /**
   * A point without an elevation.
   *
   * @param lat latitude, decimal degrees
   * @param lon longitude, decimal degrees (west negative)
   * @return the point
   */
  public static Coordinates of(double lat, double lon) {
    return new Coordinates(lat, lon, null);
  }

  /**
   * A point with its elevation.
   *
   * @param lat latitude, decimal degrees
   * @param lon longitude, decimal degrees (west negative)
   * @param elevationM elevation in metres above sea level, whatever the request's units
   * @return the point
   */
  public static Coordinates of(double lat, double lon, double elevationM) {
    return new Coordinates(lat, lon, elevationM);
  }

  /**
   * This point at another elevation.
   *
   * @param elevationM elevation in metres above sea level
   * @return a new point
   */
  public Coordinates withElevationM(double elevationM) {
    return new Coordinates(lat, lon, elevationM);
  }

  /**
   * The latitude.
   *
   * @return decimal degrees
   */
  public double lat() {
    return lat;
  }

  /**
   * The longitude.
   *
   * @return decimal degrees, west negative
   */
  public double lon() {
    return lon;
  }

  /**
   * The elevation.
   *
   * @return metres above sea level, or empty to use the server's DEM
   */
  public OptionalDouble elevationM() {
    return elevationM == null ? OptionalDouble.empty() : OptionalDouble.of(elevationM);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof Coordinates
        && Double.compare(((Coordinates) o).lat, lat) == 0
        && Double.compare(((Coordinates) o).lon, lon) == 0
        && Objects.equals(((Coordinates) o).elevationM, elevationM);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lat, lon, elevationM);
  }

  @Override
  public String toString() {
    return lat + "," + lon + (elevationM == null ? "" : " @" + elevationM + " m");
  }
}
