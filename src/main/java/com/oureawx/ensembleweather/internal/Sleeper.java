// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** Waits before a retry, without blocking a thread. Tests record the delays instead. */
public interface Sleeper {
  /**
   * Completes after {@code delay}.
   *
   * @param delay the wait
   * @return a future that completes when the wait is over
   */
  CompletableFuture<Void> sleep(Duration delay);

  /**
   * The real sleeper.
   *
   * @return a sleeper on {@link CompletableFuture#delayedExecutor}
   */
  static Sleeper system() {
    return delay ->
        CompletableFuture.runAsync(
            () -> {}, CompletableFuture.delayedExecutor(delay.toMillis(), TimeUnit.MILLISECONDS));
  }
}
