// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.util.Optional;

/**
 * Where the client keeps responses it can revalidate with their ETag. The default is an {@link
 * InMemoryResponseCache} (256 entries, 32 MiB); implement this to share a cache across processes,
 * or turn caching off with {@link EnsembleWeatherClient.Builder#disableCache()}.
 *
 * <p>Keys are opaque strings made of the full URL and a SHA-256 hash of the API key (never the key
 * itself), so keyless and keyed entries never mix. Implementations must be thread-safe.
 */
public interface ResponseCache {
  /**
   * Looks up an entry.
   *
   * @param key the cache key
   * @return the entry, or empty
   */
  Optional<CachedResponse> get(String key);

  /**
   * Stores or replaces an entry.
   *
   * @param key the cache key
   * @param response the entry
   */
  void put(String key, CachedResponse response);

  /**
   * Removes an entry, if present.
   *
   * @param key the cache key
   */
  void remove(String key);
}
