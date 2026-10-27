// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * One stored response: its body, its ETag exactly as received, and when it was stored with how long
 * it stays fresh ({@code max-age}; the API sends 0, so every use revalidates).
 */
public final class CachedResponse {
  private final String etag;
  private final byte[] body;
  private final Instant storedAt;
  private final Duration maxAge;

  private CachedResponse(String etag, byte[] body, Instant storedAt, Duration maxAge) {
    this.etag = etag;
    this.body = body;
    this.storedAt = storedAt;
    this.maxAge = maxAge;
  }

  /**
   * Creates an entry.
   *
   * @param etag the {@code ETag} as received, {@code W/} included
   * @param body the body (copied)
   * @param storedAt when it was stored or last revalidated
   * @param maxAge its {@code max-age}
   * @return the entry
   */
  public static CachedResponse of(String etag, byte[] body, Instant storedAt, Duration maxAge) {
    return new CachedResponse(
        Objects.requireNonNull(etag, "etag"),
        body.clone(),
        Objects.requireNonNull(storedAt, "storedAt"),
        Objects.requireNonNull(maxAge, "maxAge"));
  }

  /**
   * The ETag, byte for byte as the server sent it.
   *
   * @return the ETag
   */
  public String etag() {
    return etag;
  }

  /**
   * The body.
   *
   * @return a copy of the body
   */
  public byte[] body() {
    return body.clone();
  }

  /**
   * The body's size.
   *
   * @return its length in bytes
   */
  public int size() {
    return body.length;
  }

  /**
   * When the entry was stored or last revalidated.
   *
   * @return the instant
   */
  public Instant storedAt() {
    return storedAt;
  }

  /**
   * How long the entry may be used without revalidating.
   *
   * @return the {@code max-age}
   */
  public Duration maxAge() {
    return maxAge;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof CachedResponse
        && ((CachedResponse) o).etag.equals(etag)
        && Arrays.equals(((CachedResponse) o).body, body)
        && ((CachedResponse) o).storedAt.equals(storedAt)
        && ((CachedResponse) o).maxAge.equals(maxAge);
  }

  @Override
  public int hashCode() {
    return Objects.hash(etag, Arrays.hashCode(body), storedAt, maxAge);
  }

  @Override
  public String toString() {
    return "CachedResponse[etag="
        + etag
        + ", "
        + body.length
        + " bytes, storedAt="
        + storedAt
        + "]";
  }
}
