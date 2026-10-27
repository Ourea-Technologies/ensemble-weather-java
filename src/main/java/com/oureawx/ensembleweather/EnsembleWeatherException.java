// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.util.Optional;
import java.util.OptionalInt;
import org.jspecify.annotations.Nullable;

/**
 * The base of every exception the SDK throws. It is unchecked, and each {@link ErrorCategory} has
 * its own subclass, so catch the one you can handle:
 *
 * <pre>{@code
 * try {
 *   client.getPointForecast(Coordinates.of(51.5, -0.1));
 * } catch (OutsideCoverageException e) {
 *   System.out.println(e.code().orElse("") + ": " + e.getMessage());
 * }
 * }</pre>
 *
 * <p>An HTTP error carries {@link #status()}, the API's {@link #code()} (verbatim, even one this
 * SDK version does not know) and the server's {@link #requestId()}. The API key never appears in a
 * message or in {@link #toString()}.
 */
public abstract class EnsembleWeatherException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /** The category. */
  private final ErrorCategory category;

  /** The HTTP status, or null. */
  private final @Nullable Integer status;

  /** The API's error code, or null. */
  private final @Nullable String code;

  /** The request id, or null. */
  private final @Nullable String requestId;

  /**
   * Creates the exception.
   *
   * @param category the category, fixed by the subclass
   * @param message the human-readable detail
   * @param status the HTTP status, or null when there was no response
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   * @param cause the underlying exception, or null
   */
  protected EnsembleWeatherException(
      ErrorCategory category,
      String message,
      @Nullable Integer status,
      @Nullable String code,
      @Nullable String requestId,
      @Nullable Throwable cause) {
    super(message, cause);
    this.category = category;
    this.status = status;
    this.code = code;
    this.requestId = requestId;
  }

  /**
   * The category, one per subclass.
   *
   * @return the category
   */
  public final ErrorCategory category() {
    return category;
  }

  /**
   * The HTTP status of the error response.
   *
   * @return the status, or empty when no response was received
   */
  public final OptionalInt status() {
    return status == null ? OptionalInt.empty() : OptionalInt.of(status);
  }

  /**
   * The API's machine-readable error code ({@code OUTSIDE_COVERAGE}), kept verbatim.
   *
   * @return the code, or empty when there was no JSON error body
   */
  public final Optional<String> code() {
    return Optional.ofNullable(code);
  }

  /**
   * The server's {@code X-Request-Id}, for support requests.
   *
   * @return the request id, or empty
   */
  public final Optional<String> requestId() {
    return Optional.ofNullable(requestId);
  }
}
