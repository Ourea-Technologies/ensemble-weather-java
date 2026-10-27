// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/**
 * No response was received: DNS, TLS, a reset connection, or the per-attempt timeout. Retried up to
 * {@code maxRetries} times, except a TLS verification failure. {@link #getCause()} is the
 * underlying exception.
 */
public final class ConnectionException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param cause the underlying exception
   */
  public ConnectionException(String message, Throwable cause) {
    super(ErrorCategory.CONNECTION, message, null, null, null, cause);
  }
}
