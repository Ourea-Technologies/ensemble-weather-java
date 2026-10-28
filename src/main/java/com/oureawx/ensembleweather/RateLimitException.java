// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.time.Duration;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * Over a rate budget ({@code TOO_MANY_REQUESTS}, HTTP 429). The SDK retries a 429 after its {@code
 * Retry-After} when that is 60 s or less; a longer wait is returned at once as this exception, with
 * {@link #retryAfter()} set.
 */
public final class RateLimitException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /** The server's Retry-After, or null. */
  private final @Nullable Duration retryAfter;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   * @param retryAfter the server's {@code Retry-After}, or null
   */
  public RateLimitException(
      String message,
      int status,
      @Nullable String code,
      @Nullable String requestId,
      @Nullable Duration retryAfter) {
    this(message, status, code, requestId, null, null, retryAfter);
  }

  /**
   * Creates the exception with the error body's documentation link and parameter.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the request id, or null
   * @param docUrl the body's {@code doc_url}, or null
   * @param param the body's {@code param}, or null
   * @param retryAfter the server's {@code Retry-After}, or null
   */
  public RateLimitException(
      String message,
      int status,
      @Nullable String code,
      @Nullable String requestId,
      @Nullable String docUrl,
      @Nullable String param,
      @Nullable Duration retryAfter) {
    super(ErrorCategory.RATE_LIMITED, message, status, code, requestId, docUrl, param, null);
    this.retryAfter = retryAfter;
  }

  /**
   * How long the server asked the caller to wait ({@code Retry-After}).
   *
   * @return the wait, or empty when the response did not say
   */
  public Optional<Duration> retryAfter() {
    return Optional.ofNullable(retryAfter);
  }
}
