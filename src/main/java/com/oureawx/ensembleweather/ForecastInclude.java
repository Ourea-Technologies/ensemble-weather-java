// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/** An opt-in forecast variable ({@code include=}); neither is served by default. */
public enum ForecastInclude {
  /** Mean-sea-level pressure quantiles ({@code ensemble.mslp}). */
  MSLP("mslp"),
  /** Skin (surface) temperature quantiles ({@code ensemble.skin_temperature}). */
  SKIN_TEMPERATURE("skin_temperature");

  private final String wire;

  ForecastInclude(String wire) {
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
