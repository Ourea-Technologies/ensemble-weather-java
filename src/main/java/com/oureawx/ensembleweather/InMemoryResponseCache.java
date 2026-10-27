// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The default {@link ResponseCache}: an in-memory LRU bounded by entry count and total body size
 * (256 entries and 32 MiB unless given). Thread-safe.
 */
public final class InMemoryResponseCache implements ResponseCache {
  /** The default maximum number of entries. */
  public static final int DEFAULT_MAX_ENTRIES = 256;

  /** The default maximum total body size, in bytes (32 MiB). */
  public static final long DEFAULT_MAX_BYTES = 32L * 1024 * 1024;

  private final int maxEntries;
  private final long maxBytes;
  private final LinkedHashMap<String, CachedResponse> entries =
      new LinkedHashMap<>(16, 0.75f, true);
  private long bytes;

  /** A cache with the default bounds. */
  public InMemoryResponseCache() {
    this(DEFAULT_MAX_ENTRIES, DEFAULT_MAX_BYTES);
  }

  /**
   * A cache with explicit bounds.
   *
   * @param maxEntries the maximum number of entries, at least 1
   * @param maxBytes the maximum total body size in bytes, at least 1
   */
  public InMemoryResponseCache(int maxEntries, long maxBytes) {
    if (maxEntries < 1 || maxBytes < 1) {
      throw new IllegalArgumentException("cache bounds must be positive");
    }
    this.maxEntries = maxEntries;
    this.maxBytes = maxBytes;
  }

  @Override
  public synchronized Optional<CachedResponse> get(String key) {
    return Optional.ofNullable(entries.get(key));
  }

  @Override
  public synchronized void put(String key, CachedResponse response) {
    remove(key);
    if (response.size() > maxBytes) {
      return;
    }
    entries.put(key, response);
    bytes += response.size();
    Iterator<Map.Entry<String, CachedResponse>> eldest = entries.entrySet().iterator();
    while ((entries.size() > maxEntries || bytes > maxBytes) && eldest.hasNext()) {
      bytes -= eldest.next().getValue().size();
      eldest.remove();
    }
  }

  @Override
  public synchronized void remove(String key) {
    CachedResponse old = entries.remove(key);
    if (old != null) {
      bytes -= old.size();
    }
  }

  /**
   * The number of entries.
   *
   * @return the count
   */
  public synchronized int size() {
    return entries.size();
  }

  @Override
  public synchronized String toString() {
    return "InMemoryResponseCache[" + entries.size() + " entries, " + bytes + " bytes]";
  }
}
