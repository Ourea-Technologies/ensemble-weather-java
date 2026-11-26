// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.types.UnitSystem;
import java.util.Locale;

/** Argument checks shared by the options builders: refused before anything is sent. */
final class Checks {
  private Checks() {}

  static void latLon(String what, double lat, double lon) {
    if (!(lat >= -90 && lat <= 90)) {
      throw new UsageException(what + " latitude must be within -90..90, not " + lat);
    }
    if (!(lon >= -180 && lon <= 180)) {
      throw new UsageException(what + " longitude must be within -180..180, not " + lon);
    }
  }

  static double positive(String what, double value, double max) {
    if (!(value > 0 && value <= max)) {
      throw new UsageException(what + " must be more than 0 and at most " + max + ", not " + value);
    }
    return value;
  }

  static double finite(String what, double value) {
    if (!Double.isFinite(value)) {
      throw new UsageException(what + " must be a finite number, not " + value);
    }
    return value;
  }

  static UnitSystem units(UnitSystem units) {
    if (units == UnitSystem.UNKNOWN) {
      throw new UsageException("units must be METRIC, IMPERIAL or KELVIN");
    }
    return units;
  }

  /** BEHAVIOR.md 3.1 rule 1: a two-letter state is sent lowercase; a name passes through. */
  static String state(String state) {
    return state.length() == 2 ? state.toLowerCase(Locale.ROOT) : state;
  }
}
