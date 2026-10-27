// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import org.jspecify.annotations.Nullable;

/**
 * The point lies outside the forecast domain ({@code OUTSIDE_COVERAGE}, mapped by code whatever the
 * status).
 */
public final class OutsideCoverageException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   */
  public OutsideCoverageException(
      String message, int status, @Nullable String code, @Nullable String requestId) {
    super(ErrorCategory.OUTSIDE_COVERAGE, message, status, code, requestId, null);
  }
}
