// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import org.jspecify.annotations.Nullable;

/**
 * The caller's plan does not allow the request ({@code PRO_REQUIRED}, {@code FORBIDDEN}, HTTP 403),
 * such as a forecast at coordinates without a Pro key.
 */
public final class PermissionException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   */
  public PermissionException(
      String message, int status, @Nullable String code, @Nullable String requestId) {
    super(ErrorCategory.PERMISSION, message, status, code, requestId, null);
  }
}
