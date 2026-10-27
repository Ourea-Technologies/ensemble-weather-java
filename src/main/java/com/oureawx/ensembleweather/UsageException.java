// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/**
 * Refused by the SDK before anything was sent: an invalid probability term (such as {@code any} on
 * precipitation), a threshold unit that does not fit its variable, an unserved quantile pair, or a
 * malformed location or option.
 */
public final class UsageException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message what was wrong with the call
   */
  public UsageException(String message) {
    super(ErrorCategory.USAGE, message, null, null, null, null);
  }
}
