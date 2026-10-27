// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * What the HTTP exchange said about a response, beside its body: the rate-limit state, the server's
 * request id, and whether the body came from the local ETag cache.
 */
public final class ResponseMetadata {
  private static final ResponseMetadata NONE = new ResponseMetadata(null, null, false);

  private final @Nullable RateLimit rateLimit;
  private final @Nullable String requestId;
  private final boolean revalidated;

  private ResponseMetadata(
      @Nullable RateLimit rateLimit, @Nullable String requestId, boolean revalidated) {
    this.rateLimit = rateLimit;
    this.requestId = requestId;
    this.revalidated = revalidated;
  }

  /**
   * Metadata with nothing in it, as on a model built by hand.
   *
   * @return the empty metadata
   */
  public static ResponseMetadata none() {
    return NONE;
  }

  /**
   * Creates response metadata.
   *
   * @param rateLimit the rate-limit state, or null when the response carried none
   * @param requestId the {@code X-Request-Id}, or null
   * @param revalidated whether the body is the cached one, confirmed current by a {@code 304}
   * @return the metadata
   */
  public static ResponseMetadata of(
      @Nullable RateLimit rateLimit, @Nullable String requestId, boolean revalidated) {
    return new ResponseMetadata(rateLimit, requestId, revalidated);
  }

  /**
   * The rate-limit state of this response.
   *
   * @return the state, or empty when the response carried no {@code RateLimit-*} headers (an
   *     edge-cacheable anonymous response carries none)
   */
  public Optional<RateLimit> rateLimit() {
    return Optional.ofNullable(rateLimit);
  }

  /**
   * The server's {@code X-Request-Id}, for support requests.
   *
   * @return the request id, or empty
   */
  public Optional<String> requestId() {
    return Optional.ofNullable(requestId);
  }

  /**
   * Whether this body came from the local cache after the server answered {@code 304 Not Modified}
   * to its ETag.
   *
   * @return true when revalidated
   */
  public boolean revalidated() {
    return revalidated;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof ResponseMetadata
        && Objects.equals(((ResponseMetadata) o).rateLimit, rateLimit)
        && Objects.equals(((ResponseMetadata) o).requestId, requestId)
        && ((ResponseMetadata) o).revalidated == revalidated;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rateLimit, requestId, revalidated);
  }

  @Override
  public String toString() {
    return "ResponseMetadata[rateLimit="
        + rateLimit
        + ", requestId="
        + requestId
        + ", revalidated="
        + revalidated
        + "]";
  }
}
