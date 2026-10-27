// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.jspecify.annotations.Nullable;

/** One HTTP response: status, headers (case-insensitive) and the whole body. */
public final class TransportResponse {
  private final int status;
  private final Map<String, List<String>> headers;
  private final byte[] body;

  /**
   * Creates a response.
   *
   * @param status the HTTP status
   * @param headers the headers
   * @param body the body (not copied)
   */
  public TransportResponse(int status, Map<String, List<String>> headers, byte[] body) {
    this.status = status;
    TreeMap<String, List<String>> h = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    for (Map.Entry<String, List<String>> e : headers.entrySet()) {
      h.merge(e.getKey(), List.copyOf(e.getValue()), (a, b) -> a);
    }
    this.headers = h;
    this.body = body;
  }

  /**
   * The status.
   *
   * @return the HTTP status
   */
  public int status() {
    return status;
  }

  /**
   * The first value of a header.
   *
   * @param name the header name, any case
   * @return the value, or null when absent
   */
  public @Nullable String header(String name) {
    List<String> values = headers.get(name);
    return values == null || values.isEmpty() ? null : values.get(0);
  }

  /**
   * Every value of a header, joined with {@code ", "}.
   *
   * @param name the header name, any case
   * @return the joined values, or null when absent
   */
  public @Nullable String joinedHeader(String name) {
    List<String> values = headers.get(name);
    return values == null || values.isEmpty() ? null : String.join(", ", values);
  }

  /**
   * The body.
   *
   * @return the body bytes (not copied)
   */
  public byte[] body() {
    return body;
  }
}
