// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import org.jspecify.annotations.Nullable;

/**
 * A window of forecast hours, counted from the first served hour: {@code [start, end)} with {@code
 * 0 <= start < end <= 360}. The default window of a term is the next 24 hours.
 */
public final class Window {
  /** The maximum window end, in hours. */
  public static final int MAX_HOURS = 360;

  private final int startHours;
  private final int endHours;

  private Window(int startHours, int endHours) {
    if (startHours < 0 || endHours <= startHours || endHours > MAX_HOURS) {
      throw new UsageException(
          "a window needs 0 <= start < end <= "
              + MAX_HOURS
              + " hours, not "
              + startHours
              + ".."
              + endHours);
    }
    this.startHours = startHours;
    this.endHours = endHours;
  }

  /**
   * The next {@code hours} hours, from the first served hour.
   *
   * @param hours the window length, 1 to 360
   * @return the window
   */
  public static Window hours(int hours) {
    return new Window(0, hours);
  }

  /**
   * Hours {@code startHours} to {@code endHours} from the first served hour.
   *
   * @param startHours the first hour, 0 or more
   * @param endHours the end (exclusive), at most 360
   * @return the window
   */
  public static Window between(int startHours, int endHours) {
    return new Window(startHours, endHours);
  }

  /**
   * The first hour.
   *
   * @return the start, in hours from the first served hour
   */
  public int startHours() {
    return startHours;
  }

  /**
   * The end hour (exclusive).
   *
   * @return the end, in hours from the first served hour
   */
  public int endHours() {
    return endHours;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof Window
        && ((Window) o).startHours == startHours
        && ((Window) o).endHours == endHours;
  }

  @Override
  public int hashCode() {
    return 31 * startHours + endHours;
  }

  /** Returns the wire form: {@code 24h}, or {@code 6-18h} when the window does not start at 0. */
  @Override
  public String toString() {
    return startHours == 0 ? endHours + "h" : startHours + "-" + endHours + "h";
  }
}
