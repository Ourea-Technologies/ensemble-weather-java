// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/** An opt-in extra on a catalog detail ({@code include=}). */
public enum CatalogInclude {
  /** Published content entries attached to the page, locale-resolved. */
  CONTENT("content"),
  /** The resolved page template. */
  TEMPLATE("template");

  private final String wire;

  CatalogInclude(String wire) {
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
