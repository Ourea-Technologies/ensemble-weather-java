// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

/** A percentile the API serves in a {@link Quantiles}. */
public enum Percentile {
  /** The 10th percentile. */
  P10,
  /** The 25th percentile. */
  P25,
  /** The median. */
  P50,
  /** The 75th percentile. */
  P75,
  /** The 90th percentile. */
  P90
}
