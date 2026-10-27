// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * Two served percentiles of one distribution, such as p10 to p90: "at least {@code low}, could
 * reach {@code high}". Both values are the server's; nothing is computed.
 */
public final class QuantileRange {
  private final Percentile lowPercentile;
  private final Percentile highPercentile;
  private final double low;
  private final double high;

  private QuantileRange(
      Percentile lowPercentile, Percentile highPercentile, double low, double high) {
    this.lowPercentile = lowPercentile;
    this.highPercentile = highPercentile;
    this.low = low;
    this.high = high;
  }

  static QuantileRange of(
      Percentile lowPercentile, Percentile highPercentile, double low, double high) {
    return new QuantileRange(lowPercentile, highPercentile, low, high);
  }

  /**
   * The lower percentile's name.
   *
   * @return {@code P10} or {@code P25}
   */
  public Percentile lowPercentile() {
    return lowPercentile;
  }

  /**
   * The upper percentile's name.
   *
   * @return {@code P90} or {@code P75}
   */
  public Percentile highPercentile() {
    return highPercentile;
  }

  /**
   * The lower value.
   *
   * @return the lower percentile's value
   */
  public double low() {
    return low;
  }

  /**
   * The upper value.
   *
   * @return the upper percentile's value
   */
  public double high() {
    return high;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof QuantileRange
        && ((QuantileRange) o).lowPercentile == lowPercentile
        && ((QuantileRange) o).highPercentile == highPercentile
        && Double.compare(((QuantileRange) o).low, low) == 0
        && Double.compare(((QuantileRange) o).high, high) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(lowPercentile, highPercentile, low, high);
  }

  @Override
  public String toString() {
    return lowPercentile + ".." + highPercentile + " = " + low + ".." + high;
  }
}
