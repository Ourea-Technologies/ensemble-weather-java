// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import org.jspecify.annotations.Nullable;

/**
 * The server rejected the request ({@code BAD_REQUEST}, {@code INVALID_COORDINATES}, {@code
 * ELEVATION_REQUIRED} and the other 400 codes). Not retried.
 */
public final class InvalidRequestException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   */
  public InvalidRequestException(
      String message, int status, @Nullable String code, @Nullable String requestId) {
    super(ErrorCategory.INVALID_REQUEST, message, status, code, requestId, null);
  }
}
