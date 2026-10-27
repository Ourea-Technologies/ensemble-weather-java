// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import org.jspecify.annotations.Nullable;

/**
 * No such location, model run, report or route ({@code LOCATION_NOT_FOUND}, {@code
 * MODEL_RUN_NOT_FOUND}, {@code NOT_FOUND}, {@code GONE}, HTTP 404 or 410).
 */
public final class NotFoundException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   */
  public NotFoundException(
      String message, int status, @Nullable String code, @Nullable String requestId) {
    super(ErrorCategory.NOT_FOUND, message, status, code, requestId, null);
  }
}
