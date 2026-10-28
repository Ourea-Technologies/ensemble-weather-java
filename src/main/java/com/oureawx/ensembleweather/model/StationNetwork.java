// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Optional;

/**
 * The observation network of a station.
 *
 * <p>The API adds values within v1, so a value this SDK version does not know parses to {@link
 * #UNKNOWN}, and the model keeps the raw string (its {@code ...AsString()} accessor).
 */
public enum StationNetwork {
  /** {@code snotel}. */
  SNOTEL("snotel"),
  /** {@code snolite}. */
  SNOLITE("snolite"),
  /** {@code raws}. */
  RAWS("raws"),
  /** {@code asos}. */
  ASOS("asos"),
  /** {@code awos}. */
  AWOS("awos"),
  /** {@code metar}. */
  METAR("metar"),
  /** {@code rwis_wsdot}. */
  RWIS_WSDOT("rwis_wsdot"),
  /** {@code rwis_odot}. */
  RWIS_ODOT("rwis_odot"),
  /** {@code rwis_caltrans}. */
  RWIS_CALTRANS("rwis_caltrans"),
  /** {@code rwis_idot}. */
  RWIS_IDOT("rwis_idot"),
  /** {@code rwis_ndot}. */
  RWIS_NDOT("rwis_ndot"),
  /** {@code rwis_udot}. */
  RWIS_UDOT("rwis_udot"),
  /** {@code rwis_cdot}. */
  RWIS_CDOT("rwis_cdot"),
  /** {@code rwis_mdt}. */
  RWIS_MDT("rwis_mdt"),
  /** {@code rwis_wydot}. */
  RWIS_WYDOT("rwis_wydot"),
  /** {@code rwis_other}. */
  RWIS_OTHER("rwis_other"),
  /** {@code agrimet}. */
  AGRIMET("agrimet"),
  /** {@code agweathernet}. */
  AGWEATHERNET("agweathernet"),
  /** {@code cocorahs}. */
  COCORAHS("cocorahs"),
  /** {@code coop}. */
  COOP("coop"),
  /** {@code usgs}. */
  USGS("usgs"),
  /** {@code tempest}. */
  TEMPEST("tempest"),
  /** {@code davis}. */
  DAVIS("davis"),
  /** {@code wxflow}. */
  WXFLOW("wxflow"),
  /** {@code cdec}. */
  CDEC("cdec"),
  /** {@code cimis}. */
  CIMIS("cimis"),
  /** {@code mesonet_oregon}. */
  MESONET_OREGON("mesonet_oregon"),
  /** {@code mesonet_synoptic}. */
  MESONET_SYNOPTIC("mesonet_synoptic"),
  /** {@code mesonet_other}. */
  MESONET_OTHER("mesonet_other"),
  /** {@code ndbc_buoy}. */
  NDBC_BUOY("ndbc_buoy"),
  /** {@code other}. */
  OTHER("other"),
  /** {@code hads}. */
  HADS("hads"),
  /** {@code madis}. */
  MADIS("madis"),
  /** {@code nwac}. */
  NWAC("nwac"),
  /** {@code mt_mesonet}. */
  MT_MESONET("mt_mesonet"),
  /** A value this SDK version does not know; read the model's raw string. */
  UNKNOWN("");

  private final String value;

  StationNetwork(String value) {
    this.value = value;
  }

  /**
   * The API's spelling of this value.
   *
   * @return the wire value, or empty for {@link #UNKNOWN}
   */
  public Optional<String> wireValue() {
    return value.isEmpty() ? Optional.empty() : Optional.of(value);
  }

  /**
   * Parses the API's spelling.
   *
   * @param value the wire value
   * @return the matching constant, or {@link #UNKNOWN}
   */
  public static StationNetwork fromWireValue(String value) {
    for (StationNetwork c : values()) {
      if (!c.value.isEmpty() && c.value.equals(value)) {
        return c;
      }
    }
    return UNKNOWN;
  }
}
