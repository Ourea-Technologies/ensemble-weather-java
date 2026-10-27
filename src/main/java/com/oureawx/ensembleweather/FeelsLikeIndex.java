// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/** A "feels like" index a forecast can serve ({@code feels_like=}). */
public enum FeelsLikeIndex {
  /** The NWS apparent temperature (the default). */
  NWS("nws"),
  /** The Universal Thermal Climate Index: named, but not served yet (a 400 until it is). */
  UTCI("utci");

  private final String wire;

  FeelsLikeIndex(String wire) {
    this.wire = wire;
  }

  /**
   * The API's spelling.
   *
   * @return the wire value
   */
  public String wireValue() {
    return wire;
  }
}
