// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Transport;
import com.oureawx.ensembleweather.internal.TransportRequest;
import com.oureawx.ensembleweather.internal.TransportResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/** A transport that records every request and answers from a queue. */
final class MockTransport implements Transport {
  /** One queued answer: a response, or an exception (no response). */
  static final class Answer {
    final int status;
    final Map<String, List<String>> headers;
    final byte[] body;
    final IOException failure;

    private Answer(
        int status, Map<String, List<String>> headers, byte[] body, IOException failure) {
      this.status = status;
      this.headers = headers;
      this.body = body;
      this.failure = failure;
    }
  }

  final List<TransportRequest> sent = new ArrayList<>();
  private final Deque<Answer> answers = new ArrayDeque<>();

  MockTransport respond(int status, Map<String, String> headers, String body) {
    return respond(status, headers, body.getBytes(StandardCharsets.UTF_8));
  }

  MockTransport respond(int status, Map<String, String> headers, byte[] body) {
    Map<String, List<String>> h = new LinkedHashMap<>();
    headers.forEach((k, v) -> h.put(k, List.of(v)));
    answers.add(new Answer(status, h, body, null));
    return this;
  }

  MockTransport json(int status, String body) {
    return respond(status, Map.of("Content-Type", "application/json"), body);
  }

  MockTransport fail(IOException failure) {
    answers.add(new Answer(0, Map.of(), new byte[0], failure));
    return this;
  }

  int remaining() {
    return answers.size();
  }

  @Override
  public CompletableFuture<TransportResponse> send(TransportRequest request) {
    sent.add(request);
    Answer a = answers.poll();
    if (a == null) {
      return CompletableFuture.failedFuture(
          new AssertionError("unexpected request #" + sent.size() + ": " + request.uri()));
    }
    if (a.failure != null) {
      return CompletableFuture.failedFuture(a.failure);
    }
    return CompletableFuture.completedFuture(new TransportResponse(a.status, a.headers, a.body));
  }
}
