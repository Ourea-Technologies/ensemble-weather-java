// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import org.jspecify.annotations.Nullable;

/**
 * The server could not answer ({@code FORECAST_UNAVAILABLE}, {@code TIMEOUT}, {@code
 * INTERNAL_ERROR}, {@code DATABASE_ERROR}, HTTP 5xx), or answered with a body that does not match
 * the v1 contract. 502, 503 and 504 are retried first.
 */
public final class UnavailableException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   */
  public UnavailableException(
      String message, int status, @Nullable String code, @Nullable String requestId) {
    super(ErrorCategory.UNAVAILABLE, message, status, code, requestId, null);
  }
}
