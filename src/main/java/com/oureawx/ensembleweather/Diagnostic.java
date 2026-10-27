// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/** An opt-in serve-path diagnostic for a forecast ({@code diagnostics=}); neither by default. */
public enum Diagnostic {
  /** Each hour's {@code provenance} tags: which post-processing rule shaped each variable. */
  PROVENANCE("provenance"),
  /** The model-native precipitation-type code quantiles ({@code ensemble.ptype}). */
  PTYPE("ptype");

  private final String wire;

  Diagnostic(String wire) {
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
