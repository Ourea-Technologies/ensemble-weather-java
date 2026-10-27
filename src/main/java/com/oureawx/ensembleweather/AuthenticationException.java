// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import org.jspecify.annotations.Nullable;

/**
 * The API key is not valid ({@code INVALID_API_KEY}, {@code UNAUTHORIZED}, HTTP 401). Not retried:
 * check the key, or send none for anonymous Free access.
 */
public final class AuthenticationException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   */
  public AuthenticationException(
      String message, int status, @Nullable String code, @Nullable String requestId) {
    super(ErrorCategory.AUTHENTICATION, message, status, code, requestId, null);
  }
}
