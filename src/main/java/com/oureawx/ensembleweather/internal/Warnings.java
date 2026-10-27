// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Deprecation warnings, once per operation (BEHAVIOR.md section 11). The process-wide instance logs
 * through {@link System.Logger} {@code com.oureawx.ensembleweather} at {@code WARNING}.
 */
public final class Warnings {
  private static final System.Logger LOGGER = System.getLogger("com.oureawx.ensembleweather");
  private static final Warnings PROCESS =
      new Warnings(m -> LOGGER.log(System.Logger.Level.WARNING, m));

  private final Set<String> seen = ConcurrentHashMap.newKeySet();
  private final Consumer<String> sink;

  /**
   * Creates a sink with its own once-per-operation memory.
   *
   * @param sink receives each warning
   */
  public Warnings(Consumer<String> sink) {
    this.sink = sink;
  }

  /**
   * The process-wide sink.
   *
   * @return the shared instance
   */
  public static Warnings process() {
    return PROCESS;
  }

  /**
   * Emits {@code message} unless {@code key} was already warned about.
   *
   * @param key the operation id
   * @param message the warning
   */
  public void warnOnce(String key, String message) {
    if (seen.add(key)) {
      sink.accept(message);
    }
  }

  /**
   * Emits {@code message} unconditionally.
   *
   * @param message the warning
   */
  public void warn(String message) {
    sink.accept(message);
  }
}
