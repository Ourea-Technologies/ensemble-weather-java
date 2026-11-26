// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/** The order of a ranked list ({@code order}): highest value first, the default, or lowest. */
public enum SortOrder {
  /** {@code desc}: the highest value first (the API's default). */
  DESC("desc"),
  /** {@code asc}: the lowest value first. */
  ASC("asc");

  private final String value;

  SortOrder(String value) {
    this.value = value;
  }

  /**
   * The API's spelling.
   *
   * @return the wire value
   */
  public String wireValue() {
    return value;
  }
}
