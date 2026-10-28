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
 * SDK version does not know), the server's {@link #requestId()}, the code's {@link #docUrl()} and,
 * on a 400 about one parameter, its {@link #param()}. The API key never appears in a message or in
 * {@link #toString()}.
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

  /** The code's documentation URL, or null. */
  private final @Nullable String docUrl;

  /** The query parameter a 400 is about, or null. */
  private final @Nullable String param;

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
    this(category, message, status, code, requestId, null, null, cause);
  }

  /**
   * Creates the exception with the error body's documentation link and parameter.
   *
   * @param category the category, fixed by the subclass
   * @param message the human-readable detail
   * @param status the HTTP status, or null when there was no response
   * @param code the API's error code, or null
   * @param requestId the request id (the body's {@code request_id}, else {@code X-Request-Id}), or
   *     null
   * @param docUrl the body's {@code doc_url}, or null
   * @param param the body's {@code param}, or null
   * @param cause the underlying exception, or null
   */
  protected EnsembleWeatherException(
      ErrorCategory category,
      String message,
      @Nullable Integer status,
      @Nullable String code,
      @Nullable String requestId,
      @Nullable String docUrl,
      @Nullable String param,
      @Nullable Throwable cause) {
    super(message, cause);
    this.category = category;
    this.status = status;
    this.code = code;
    this.requestId = requestId;
    this.docUrl = docUrl;
    this.param = param;
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
   * The server's request id (the error body's {@code request_id}, else the {@code X-Request-Id}
   * header), for support requests.
   *
   * @return the request id, or empty
   */
  public final Optional<String> requestId() {
    return Optional.ofNullable(requestId);
  }

  /**
   * The error code's explanation on the docs errors page (cause, fix, whether to retry): the error
   * body's {@code doc_url}.
   *
   * @return the URL, or empty when the response did not carry one
   */
  public final Optional<String> docUrl() {
    return Optional.ofNullable(docUrl);
  }

  /**
   * On a 400 about one query parameter, its name ({@code max_hours}, {@code prob}, {@code lat}):
   * the error body's {@code param}.
   *
   * @return the parameter name, or empty when the error is not about one parameter
   */
  public final Optional<String> param() {
    return Optional.ofNullable(param);
  }
}
