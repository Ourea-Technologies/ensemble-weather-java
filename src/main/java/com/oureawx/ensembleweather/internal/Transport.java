// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.util.concurrent.CompletableFuture;

/**
 * The HTTP seam: one GET, no redirects followed, the body bounded. {@link HttpClientTransport} is
 * the real one; tests pass a mock.
 */
public interface Transport {
  /**
   * Sends one request.
   *
   * @param request the request
   * @return the response; fails with an {@link java.io.IOException} when none was received
   */
  CompletableFuture<TransportResponse> send(TransportRequest request);
}
