// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The rate-limit state the API reported on a response ({@code RateLimit-Limit}, {@code -Remaining},
 * {@code -Reset} and {@code -Policy}). Read it rather than hard-coding limits: they differ by tier
 * and change over time. An edge-cacheable anonymous response carries none.
 */
public final class RateLimit {
  private final long limit;
  private final long remaining;
  private final Duration reset;
  private final @Nullable String policy;

  private RateLimit(long limit, long remaining, Duration reset, @Nullable String policy) {
    this.limit = limit;
    this.remaining = remaining;
    this.reset = reset;
    this.policy = policy;
  }

  /**
   * Creates a rate-limit state.
   *
   * @param limit the size of the binding budget
   * @param remaining the requests left in it
   * @param reset the time until it is whole again
   * @param policy the {@code RateLimit-Policy} header, or null when absent
   * @return the state
   */
  public static RateLimit of(long limit, long remaining, Duration reset, @Nullable String policy) {
    return new RateLimit(limit, remaining, Objects.requireNonNull(reset, "reset"), policy);
  }

  /**
   * The size of the budget the response was counted against ({@code RateLimit-Limit}).
   *
   * @return the limit
   */
  public long limit() {
    return limit;
  }

  /**
   * Requests left before the binding limit refuses ({@code RateLimit-Remaining}; 0 on a 429).
   *
   * @return the remaining requests
   */
  public long remaining() {
    return remaining;
  }

  /**
   * Time until the binding limit is whole again ({@code RateLimit-Reset}).
   *
   * @return the reset delay
   */
  public Duration reset() {
    return reset;
  }

  /**
   * The caller's budgets, such as {@code 60;w=60, 10000;w=86400} ({@code RateLimit-Policy}).
   *
   * @return the policy, or empty when the response carried none
   */
  public Optional<String> policy() {
    return Optional.ofNullable(policy);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof RateLimit
        && ((RateLimit) o).limit == limit
        && ((RateLimit) o).remaining == remaining
        && ((RateLimit) o).reset.equals(reset)
        && Objects.equals(((RateLimit) o).policy, policy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(limit, remaining, reset, policy);
  }

  @Override
  public String toString() {
    return "RateLimit[limit="
        + limit
        + ", remaining="
        + remaining
        + ", reset="
        + reset
        + (policy == null ? "" : ", policy=" + policy)
        + "]";
  }
}
