// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

/**
 * The category of an {@link EnsembleWeatherException}: one per subclass. HTTP errors are mapped by
 * the API's error code first, and by HTTP status when the code is unknown or the body is not JSON.
 */
public enum ErrorCategory {
  /**
   * {@code INVALID_API_KEY}, {@code UNAUTHORIZED}, or HTTP 401: see {@link
   * AuthenticationException}.
   */
  AUTHENTICATION,
  /** {@code PRO_REQUIRED}, {@code FORBIDDEN}, or HTTP 403: see {@link PermissionException}. */
  PERMISSION,
  /** {@code LOCATION_NOT_FOUND} and friends, or HTTP 404 or 410: see {@link NotFoundException}. */
  NOT_FOUND,
  /** {@code BAD_REQUEST} and friends, or HTTP 400: see {@link InvalidRequestException}. */
  INVALID_REQUEST,
  /** {@code OUTSIDE_COVERAGE} (by code only): see {@link OutsideCoverageException}. */
  OUTSIDE_COVERAGE,
  /** {@code TOO_MANY_REQUESTS}, or HTTP 429: see {@link RateLimitException}. */
  RATE_LIMITED,
  /** {@code FORECAST_UNAVAILABLE} and friends, or HTTP 5xx: see {@link UnavailableException}. */
  UNAVAILABLE,
  /** No response at all (DNS, TLS, reset, timeout): see {@link ConnectionException}. */
  CONNECTION,
  /** Refused by the SDK before anything was sent: see {@link UsageException}. */
  USAGE
}
