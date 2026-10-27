// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Which serve-path branch produced this hour. Each list names the {@code ensemble} field it applies
 * to, so a tag joins to the value it describes with no lookup table.
 *
 * <p>Immutable. Build one with {@link #builder()} (for tests and mocks); the client decodes
 * responses into it. Wire schema: {@code HourProvenance}.
 */
public final class HourProvenance extends Model {
  private final @Nullable Boolean densified;
  private final List<String> ngr;
  private final List<String> fallback;
  private final List<String> borrowed;
  private final List<String> spreadFloored;
  private final List<String> floorClamped;
  private final List<String> bucketSplit;
  private final List<String> distributionParameters;
  private final List<String> spreadInterpolated;
  private final List<String> interpolatedContributor;
  private final List<String> momentMatchedFamily;
  private final List<String> lapseProfile;
  private final List<String> lapseClimatology;
  private final List<String> lapsePrism;
  private final List<String> spreadClampedLow;
  private final List<String> spreadClampedHigh;
  private final List<String> gustDressed;

  private HourProvenance(Builder b) {
    this.densified = b.densified;
    this.ngr = List.copyOf(b.ngr);
    this.fallback = List.copyOf(b.fallback);
    this.borrowed = List.copyOf(b.borrowed);
    this.spreadFloored = List.copyOf(b.spreadFloored);
    this.floorClamped = List.copyOf(b.floorClamped);
    this.bucketSplit = List.copyOf(b.bucketSplit);
    this.distributionParameters = List.copyOf(b.distributionParameters);
    this.spreadInterpolated = List.copyOf(b.spreadInterpolated);
    this.interpolatedContributor = List.copyOf(b.interpolatedContributor);
    this.momentMatchedFamily = List.copyOf(b.momentMatchedFamily);
    this.lapseProfile = List.copyOf(b.lapseProfile);
    this.lapseClimatology = List.copyOf(b.lapseClimatology);
    this.lapsePrism = List.copyOf(b.lapsePrism);
    this.spreadClampedLow = List.copyOf(b.spreadClampedLow);
    this.spreadClampedHigh = List.copyOf(b.spreadClampedHigh);
    this.gustDressed = List.copyOf(b.gustDressed);
  }

  /**
   * Returns a new builder.
   *
   * @return an empty builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Returns a builder holding this object's values.
   *
   * @return a builder to make a modified copy
   */
  public Builder toBuilder() {
    Builder b = new Builder();
    b.densified = this.densified;
    b.ngr = this.ngr;
    b.fallback = this.fallback;
    b.borrowed = this.borrowed;
    b.spreadFloored = this.spreadFloored;
    b.floorClamped = this.floorClamped;
    b.bucketSplit = this.bucketSplit;
    b.distributionParameters = this.distributionParameters;
    b.spreadInterpolated = this.spreadInterpolated;
    b.interpolatedContributor = this.interpolatedContributor;
    b.momentMatchedFamily = this.momentMatchedFamily;
    b.lapseProfile = this.lapseProfile;
    b.lapseClimatology = this.lapseClimatology;
    b.lapsePrism = this.lapsePrism;
    b.spreadClampedLow = this.spreadClampedLow;
    b.spreadClampedHigh = this.spreadClampedHigh;
    b.gustDressed = this.gustDressed;
    return b;
  }

  /**
   * This hour sits strictly between two native model steps: its instantaneous values were linearly
   * interpolated (plus a diurnal bulge), not read from a model step. Omitted when false.
   *
   * @return the {@code densified} value, or empty when the response omits it
   */
  public Optional<Boolean> densified() {
    return Optional.ofNullable(densified);
  }

  /**
   * Variables corrected by a fitted NGR / truncated-NGR / censored-gamma model for this location,
   * variable and lead-time bin.
   *
   * @return the {@code ngr} values; empty when the response has none
   */
  public List<ProvenanceVariable> ngr() {
    List<ProvenanceVariable> out = new ArrayList<>(ngr.size());
    for (String v : ngr) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code ngr} values exactly as the API sent them, including any this SDK version does not
   * know.
   *
   * @return the raw {@code ngr} strings
   */
  public List<String> ngrAsStrings() {
    return ngr;
  }

  /**
   * Variables served through the {@code inflate_around_mean} fallback because no fitted model was
   * available for that lead-time bin. The widening is a configured constant, not a calibration.
   *
   * @return the {@code fallback} values; empty when the response has none
   */
  public List<ProvenanceVariable> fallback() {
    List<ProvenanceVariable> out = new ArrayList<>(fallback.size());
    for (String v : fallback) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code fallback} values exactly as the API sent them, including any this SDK version does
   * not know.
   *
   * @return the raw {@code fallback} strings
   */
  public List<String> fallbackAsStrings() {
    return fallback;
  }

  /**
   * Variables at a point no fitted station reaches (none within 75 km), served from the fits of
   * climatologically similar supplemental stations (same terrain class, ranked by distance and
   * elevation difference, kept apart from one another) and shrunk toward the fallback. Borrowed
   * statistical strength, not a calibration trained for this point. Added 2026-10-09; only
   * variables whose borrowed fit beat the fallback on held-out stations borrow.
   *
   * @return the {@code borrowed} values; empty when the response has none
   */
  public List<ProvenanceVariable> borrowed() {
    List<ProvenanceVariable> out = new ArrayList<>(borrowed.size());
    for (String v : borrowed) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code borrowed} values exactly as the API sent them, including any this SDK version does
   * not know.
   *
   * @return the raw {@code borrowed} strings
   */
  public List<String> borrowedAsStrings() {
    return borrowed;
  }

  /**
   * Variables whose percentile ladder was rebuilt as {@code N(mean, min_serve_sigma)} by the
   * serve-time spread floor. Such a ladder carries no ensemble information: every probability read
   * off it is fabricated at the floor width. Treat the mean as usable and the uncertainty around it
   * as not.
   *
   * @return the {@code spread_floored} values; empty when the response has none
   */
  public List<ProvenanceVariable> spreadFloored() {
    List<ProvenanceVariable> out = new ArrayList<>(spreadFloored.size());
    for (String v : spreadFloored) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code spread_floored} values exactly as the API sent them, including any this SDK version
   * does not know.
   *
   * @return the raw {@code spread_floored} strings
   */
  public List<String> spreadFlooredAsStrings() {
    return spreadFloored;
  }

  /**
   * Variables whose low tail was truncated by the zero floor. The truncation biases the conditional
   * mean upward; it is a backstop, not a neutral operation.
   *
   * @return the {@code floor_clamped} values; empty when the response has none
   */
  public List<ProvenanceVariable> floorClamped() {
    List<ProvenanceVariable> out = new ArrayList<>(floorClamped.size());
    for (String v : floorClamped) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code floor_clamped} values exactly as the API sent them, including any this SDK version
   * does not know.
   *
   * @return the raw {@code floor_clamped} strings
   */
  public List<String> floorClampedAsStrings() {
    return floorClamped;
  }

  /**
   * Accumulation variables whose value at this hour is an even share of a wider accumulation, not
   * an independently modelled hourly rate: either a native multi-hour bucket split across the hours
   * between two model steps, or a contributor's accumulation spread evenly across the hours that
   * failed to download before it — the missing hours are served with their share, and no hour
   * carries a multi-step total. Every hour of the same split carries the identical share by
   * construction. (This replaces the short-lived {@code accumulation_interval_hours}, which tagged
   * the widened total instead of splitting it.).
   *
   * @return the {@code bucket_split} values; empty when the response has none
   */
  public List<ProvenanceVariable> bucketSplit() {
    List<ProvenanceVariable> out = new ArrayList<>(bucketSplit.size());
    for (String v : bucketSplit) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code bucket_split} values exactly as the API sent them, including any this SDK version
   * does not know.
   *
   * @return the raw {@code bucket_split} strings
   */
  public List<String> bucketSplitAsStrings() {
    return bucketSplit;
  }

  /**
   * Variables whose blend included a contributor that supplied a two-parameter predictive
   * distribution (a location and a scale) instead of ensemble members. NOAA's REFS publishes {@code
   * mean} and {@code sprd} for RRFS and no member files at all, so RRFS contributes a measured
   * Gaussian rather than a draw of members.
   *
   * @return the {@code distribution_parameters} values; empty when the response has none
   */
  public List<ProvenanceVariable> distributionParameters() {
    List<ProvenanceVariable> out = new ArrayList<>(distributionParameters.size());
    for (String v : distributionParameters) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code distribution_parameters} values exactly as the API sent them, including any this SDK
   * version does not know.
   *
   * @return the raw {@code distribution_parameters} strings
   */
  public List<String> distributionParametersAsStrings() {
    return distributionParameters;
  }

  /**
   * Variables whose spread was not measured by ANY contributor at this hour and was interpolated
   * from the bracketing hours that did measure it. The global ensembles publish on a 6-hourly
   * native step while RRFS is hourly, so at the five leads between those steps RRFS is the only
   * contributor and supplies a location with no distribution; the served ladder there is the two
   * bracketing hours' quantile shape, re-centred on this hour's own mean.
   *
   * @return the {@code spread_interpolated} values; empty when the response has none
   */
  public List<ProvenanceVariable> spreadInterpolated() {
    List<ProvenanceVariable> out = new ArrayList<>(spreadInterpolated.size());
    for (String v : spreadInterpolated) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code spread_interpolated} values exactly as the API sent them, including any this SDK
   * version does not know.
   *
   * @return the raw {@code spread_interpolated} strings
   */
  public List<String> spreadInterpolatedAsStrings() {
    return spreadInterpolated;
  }

  /**
   * Variables whose pool at this hour contains at least one contributor that did NOT publish at
   * this lead. Its distribution was interpolated from its two bracketing native steps (linearly in
   * the mean, in every quantile and in the scale, with the same diurnal shaping the hourly
   * densifier applies to any chord) so the pool — and therefore its spread — is the same set of
   * contributors at every lead.
   *
   * @return the {@code interpolated_contributor} values; empty when the response has none
   */
  public List<ProvenanceVariable> interpolatedContributor() {
    List<ProvenanceVariable> out = new ArrayList<>(interpolatedContributor.size());
    for (String v : interpolatedContributor) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code interpolated_contributor} values exactly as the API sent them, including any this
   * SDK version does not know.
   *
   * @return the raw {@code interpolated_contributor} strings
   */
  public List<String> interpolatedContributorAsStrings() {
    return interpolatedContributor;
  }

  /**
   * Variables whose served {@code probability} was computed from a distribution recovered by METHOD
   * OF MOMENTS from the hour's mean and standard deviation, rather than from the exact parameters
   * the calibration fit produced.
   *
   * @return the {@code moment_matched_family} values; empty when the response has none
   */
  public List<ProvenanceVariable> momentMatchedFamily() {
    List<ProvenanceVariable> out = new ArrayList<>(momentMatchedFamily.size());
    for (String v : momentMatchedFamily) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code moment_matched_family} values exactly as the API sent them, including any this SDK
   * version does not know.
   *
   * @return the raw {@code moment_matched_family} strings
   */
  public List<String> momentMatchedFamilyAsStrings() {
    return momentMatchedFamily;
  }

  /**
   * Variables carried from the location's primary elevation to this point's elevation by the hour's
   * OWN upper-air lapse rate (the profile bracketed the layer). Only an {@code
   * elevation_forecasts[].hourly} hour can carry it.
   *
   * @return the {@code lapse_profile} values; empty when the response has none
   */
  public List<ProvenanceVariable> lapseProfile() {
    List<ProvenanceVariable> out = new ArrayList<>(lapseProfile.size());
    for (String v : lapseProfile) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code lapse_profile} values exactly as the API sent them, including any this SDK version
   * does not know.
   *
   * @return the raw {@code lapse_profile} strings
   */
  public List<String> lapseProfileAsStrings() {
    return lapseProfile;
  }

  /**
   * Variables carried from the location's primary elevation to this point's elevation by a
   * CLIMATOLOGICAL lapse rate — for {@code temperature} because the hour's profile could not
   * bracket the layer (always the case below the primary, where the profile starts), the diurnal
   * table; for {@code dewpoint} always, a mean 2 °C/km (no upper-air humidity is ingested). Only an
   * {@code elevation_forecasts[].hourly} hour can carry it.
   *
   * @return the {@code lapse_climatology} values; empty when the response has none
   */
  public List<ProvenanceVariable> lapseClimatology() {
    List<ProvenanceVariable> out = new ArrayList<>(lapseClimatology.size());
    for (String v : lapseClimatology) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code lapse_climatology} values exactly as the API sent them, including any this SDK
   * version does not know.
   *
   * @return the raw {@code lapse_climatology} strings
   */
  public List<String> lapseClimatologyAsStrings() {
    return lapseClimatology;
  }

  /**
   * Variables carried from the location's primary elevation to this point's elevation by the local
   * PRISM precipitation–elevation regression: the PRISM 800 m climatological normals at the
   * observing stations within 40 km, regressed on the stations' elevations (distance-weighted, Daly
   * et al. 2008), give the ratio of the normal at this elevation to the normal at the primary's for
   * the valid time's month, and the hour's corrected {@code precipitation} and {@code snowfall}
   * distributions are scaled by it (every rank, mean and spread; the probability of any
   * precipitation is unchanged). Absent when the neighbourhood cannot determine a slope (fewer than
   * five stations, or under 300 m of elevation span) or PRISM is not loaded — the amounts are then
   * the primary's. Only an {@code elevation_forecasts[].hourly} hour can carry it.
   *
   * @return the {@code lapse_prism} values; empty when the response has none
   */
  public List<ProvenanceVariable> lapsePrism() {
    List<ProvenanceVariable> out = new ArrayList<>(lapsePrism.size());
    for (String v : lapsePrism) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code lapse_prism} values exactly as the API sent them, including any this SDK version
   * does not know.
   *
   * @return the raw {@code lapse_prism} strings
   */
  public List<String> lapsePrismAsStrings() {
    return lapsePrism;
  }

  /**
   * Variables whose calibration fit was accepted and served, but whose fitted spread fell BELOW the
   * serve-time spread band and was re-inflated to the band's low edge. The mean is the fit's; the
   * uncertainty around it is the band's, not the fit's. A coverage or PIT statistic computed over
   * such hours describes the band.
   *
   * @return the {@code spread_clamped_low} values; empty when the response has none
   */
  public List<ProvenanceVariable> spreadClampedLow() {
    List<ProvenanceVariable> out = new ArrayList<>(spreadClampedLow.size());
    for (String v : spreadClampedLow) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code spread_clamped_low} values exactly as the API sent them, including any this SDK
   * version does not know.
   *
   * @return the raw {@code spread_clamped_low} strings
   */
  public List<String> spreadClampedLowAsStrings() {
    return spreadClampedLow;
  }

  /**
   * Variables whose calibration fit was accepted and served, but whose fitted spread rose ABOVE the
   * serve-time spread band and was cut back to the band's high edge. The mean is the fit's; the
   * uncertainty around it is the band's, not the fit's. A coverage or PIT statistic computed over
   * such hours describes the band.
   *
   * @return the {@code spread_clamped_high} values; empty when the response has none
   */
  public List<ProvenanceVariable> spreadClampedHigh() {
    List<ProvenanceVariable> out = new ArrayList<>(spreadClampedHigh.size());
    for (String v : spreadClampedHigh) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code spread_clamped_high} values exactly as the API sent them, including any this SDK
   * version does not know.
   *
   * @return the raw {@code spread_clamped_high} strings
   */
  public List<String> spreadClampedHighAsStrings() {
    return spreadClampedHigh;
  }

  /**
   * Variables whose pool at this hour included a contributor that publishes a 10 m wind but no gust
   * (the AI ensembles) and was dressed from its mean wind by the WMO in-land gust factor of 1.75
   * (Harper, Kepert &amp; Ginger 2010, WMO/TD-No. 1555, Table 1.1). The dressed contributor carries
   * its wind's spread, not a gust-specific one. Only {@code wind_gust} can appear here. Since the
   * served gust is derived from the served wind, it carries the wind's provenance and this list is
   * empty on every hour that serves a wind.
   *
   * @return the {@code gust_dressed} values; empty when the response has none
   */
  public List<ProvenanceVariable> gustDressed() {
    List<ProvenanceVariable> out = new ArrayList<>(gustDressed.size());
    for (String v : gustDressed) {
      out.add(ProvenanceVariable.fromWireValue(v));
    }
    return List.copyOf(out);
  }

  /**
   * The {@code gust_dressed} values exactly as the API sent them, including any this SDK version
   * does not know.
   *
   * @return the raw {@code gust_dressed} strings
   */
  public List<String> gustDressedAsStrings() {
    return gustDressed;
  }

  @Override
  Object[] values() {
    return new Object[] {
      densified,
      ngr,
      fallback,
      borrowed,
      spreadFloored,
      floorClamped,
      bucketSplit,
      distributionParameters,
      spreadInterpolated,
      interpolatedContributor,
      momentMatchedFamily,
      lapseProfile,
      lapseClimatology,
      lapsePrism,
      spreadClampedLow,
      spreadClampedHigh,
      gustDressed
    };
  }

  @Override
  String[] names() {
    return NAMES;
  }

  private static final String[] NAMES = {
    "densified",
    "ngr",
    "fallback",
    "borrowed",
    "spreadFloored",
    "floorClamped",
    "bucketSplit",
    "distributionParameters",
    "spreadInterpolated",
    "interpolatedContributor",
    "momentMatchedFamily",
    "lapseProfile",
    "lapseClimatology",
    "lapsePrism",
    "spreadClampedLow",
    "spreadClampedHigh",
    "gustDressed"
  };

  /** Builds a {@link HourProvenance}. */
  public static final class Builder {
    private @Nullable Boolean densified;
    private List<String> ngr = List.of();
    private List<String> fallback = List.of();
    private List<String> borrowed = List.of();
    private List<String> spreadFloored = List.of();
    private List<String> floorClamped = List.of();
    private List<String> bucketSplit = List.of();
    private List<String> distributionParameters = List.of();
    private List<String> spreadInterpolated = List.of();
    private List<String> interpolatedContributor = List.of();
    private List<String> momentMatchedFamily = List.of();
    private List<String> lapseProfile = List.of();
    private List<String> lapseClimatology = List.of();
    private List<String> lapsePrism = List.of();
    private List<String> spreadClampedLow = List.of();
    private List<String> spreadClampedHigh = List.of();
    private List<String> gustDressed = List.of();

    private Builder() {}

    /**
     * Sets {@link HourProvenance#densified()}.
     *
     * @param value the value, or null for absent
     * @return this builder
     */
    public Builder densified(@Nullable Boolean value) {
      this.densified = value;
      return this;
    }

    /**
     * Sets {@link HourProvenance#ngr()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder ngr(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.ngr = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#ngrAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder ngrAsStrings(List<String> values) {
      this.ngr = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#fallback()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder fallback(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.fallback = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#fallbackAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder fallbackAsStrings(List<String> values) {
      this.fallback = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#borrowed()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder borrowed(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.borrowed = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#borrowedAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder borrowedAsStrings(List<String> values) {
      this.borrowed = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadFloored()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder spreadFloored(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.spreadFloored = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadFlooredAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder spreadFlooredAsStrings(List<String> values) {
      this.spreadFloored = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#floorClamped()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder floorClamped(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.floorClamped = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#floorClampedAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder floorClampedAsStrings(List<String> values) {
      this.floorClamped = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#bucketSplit()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder bucketSplit(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.bucketSplit = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#bucketSplitAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder bucketSplitAsStrings(List<String> values) {
      this.bucketSplit = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#distributionParameters()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder distributionParameters(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.distributionParameters = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#distributionParametersAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder distributionParametersAsStrings(List<String> values) {
      this.distributionParameters = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadInterpolated()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder spreadInterpolated(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.spreadInterpolated = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadInterpolatedAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder spreadInterpolatedAsStrings(List<String> values) {
      this.spreadInterpolated = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#interpolatedContributor()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder interpolatedContributor(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.interpolatedContributor = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#interpolatedContributorAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder interpolatedContributorAsStrings(List<String> values) {
      this.interpolatedContributor = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#momentMatchedFamily()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder momentMatchedFamily(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.momentMatchedFamily = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#momentMatchedFamilyAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder momentMatchedFamilyAsStrings(List<String> values) {
      this.momentMatchedFamily = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#lapseProfile()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder lapseProfile(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.lapseProfile = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#lapseProfileAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder lapseProfileAsStrings(List<String> values) {
      this.lapseProfile = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#lapseClimatology()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder lapseClimatology(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.lapseClimatology = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#lapseClimatologyAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder lapseClimatologyAsStrings(List<String> values) {
      this.lapseClimatology = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#lapsePrism()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder lapsePrism(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.lapsePrism = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#lapsePrismAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder lapsePrismAsStrings(List<String> values) {
      this.lapsePrism = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadClampedLow()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder spreadClampedLow(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.spreadClampedLow = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadClampedLowAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder spreadClampedLowAsStrings(List<String> values) {
      this.spreadClampedLow = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadClampedHigh()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder spreadClampedHigh(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.spreadClampedHigh = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#spreadClampedHighAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder spreadClampedHighAsStrings(List<String> values) {
      this.spreadClampedHigh = List.copyOf(values);
      return this;
    }

    /**
     * Sets {@link HourProvenance#gustDressed()}.
     *
     * @param values the values
     * @return this builder
     */
    public Builder gustDressed(List<ProvenanceVariable> values) {
      List<String> raw = new ArrayList<>(values.size());
      for (ProvenanceVariable v : values) {
        raw.add(wire(v.wireValue()));
      }
      this.gustDressed = raw;
      return this;
    }

    /**
     * Sets {@link HourProvenance#gustDressedAsStrings()}, the raw wire values.
     *
     * @param values the raw values
     * @return this builder
     */
    public Builder gustDressedAsStrings(List<String> values) {
      this.gustDressed = List.copyOf(values);
      return this;
    }

    /**
     * Builds the {@link HourProvenance}.
     *
     * @return the immutable HourProvenance
     * @throws IllegalStateException if a required value was not set
     */
    public HourProvenance build() {
      return new HourProvenance(this);
    }
  }
}
