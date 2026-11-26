// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.types.AlertClass;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Filters for {@link Client#getAlertOutlines(AlertOutlineOptions)}: the alert classes and a
 * bounding box.
 */
public final class AlertOutlineOptions {
  private static final AlertOutlineOptions DEFAULTS = builder().build();

  private final List<String> classes;
  private final double @Nullable [] bbox;

  private AlertOutlineOptions(Builder b) {
    classes = List.copyOf(b.classes);
    bbox = b.bbox == null ? null : b.bbox.clone();
  }

  /**
   * The options with nothing set: every class, everywhere.
   *
   * @return the default options
   */
  public static AlertOutlineOptions defaults() {
    return DEFAULTS;
  }

  /**
   * Returns a new builder.
   *
   * @return an empty builder
   */
  public static Builder builder() {
    return new Builder();
  }

  void addTo(Query q) {
    q.csv("class", classes);
    double[] box = bbox;
    if (box != null) {
      q.numbers("bbox", box[0], box[1], box[2], box[3]);
    }
  }

  /** Builds {@link AlertOutlineOptions}. */
  public static final class Builder {
    private List<String> classes = List.of();
    private double @Nullable [] bbox;

    private Builder() {}

    /**
     * Only these alert classes ({@code class}, one comma list, sent sorted and de-duplicated).
     *
     * @param classes the classes
     * @return this builder
     */
    public Builder classes(AlertClass... classes) {
      List<String> out = new ArrayList<>();
      for (AlertClass c : Arrays.asList(classes)) {
        out.add(
            c.wireValue().orElseThrow(() -> new UsageException("class UNKNOWN cannot be sent")));
      }
      this.classes = List.copyOf(out);
      return this;
    }

    /**
     * Only outlines that touch this box ({@code bbox}, sent as {@code west,south,east,north}).
     *
     * @param west the west edge, -180..180
     * @param south the south edge, -90..90
     * @param east the east edge, -180..180, not west of {@code west}
     * @param north the north edge, -90..90, not south of {@code south}
     * @return this builder
     */
    public Builder bbox(double west, double south, double east, double north) {
      Checks.latLon("bbox south-west", south, west);
      Checks.latLon("bbox north-east", north, east);
      if (west > east) {
        throw new UsageException("bbox west (" + west + ") is east of east (" + east + ")");
      }
      if (south > north) {
        throw new UsageException("bbox south (" + south + ") is north of north (" + north + ")");
      }
      this.bbox = new double[] {west, south, east, north};
      return this;
    }

    /**
     * Builds the options.
     *
     * @return the immutable options
     */
    public AlertOutlineOptions build() {
      return new AlertOutlineOptions(this);
    }
  }
}
