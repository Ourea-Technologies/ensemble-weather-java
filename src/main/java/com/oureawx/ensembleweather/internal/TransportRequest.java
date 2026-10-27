// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.net.URI;
import java.time.Duration;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** One GET request. Its {@code toString} never shows header values (the API key is one). */
public final class TransportRequest {
  private final URI uri;
  private final Map<String, String> headers;
  private final Duration timeout;

  /**
   * Creates a request.
   *
   * @param uri the URL
   * @param headers the headers, in order
   * @param timeout the per-attempt timeout
   */
  public TransportRequest(URI uri, Map<String, String> headers, Duration timeout) {
    this.uri = uri;
    this.headers = Collections.unmodifiableMap(new LinkedHashMap<>(headers));
    this.timeout = timeout;
  }

  /**
   * The URL.
   *
   * @return the URL
   */
  public URI uri() {
    return uri;
  }

  /**
   * The headers.
   *
   * @return the headers, in order
   */
  public Map<String, String> headers() {
    return headers;
  }

  /**
   * The per-attempt timeout.
   *
   * @return the timeout
   */
  public Duration timeout() {
    return timeout;
  }

  @Override
  public String toString() {
    return "GET " + uri + " (headers: " + headers.keySet() + ")";
  }
}
