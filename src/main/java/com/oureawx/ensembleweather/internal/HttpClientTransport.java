// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.net.ProxySelector;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;
import org.jspecify.annotations.Nullable;

/**
 * The real transport: {@link java.net.http.HttpClient} with redirects off (the client follows them
 * itself, to drop the key across hosts), TLS verification always on (there is no option to turn it
 * off), and bodies bounded at 64 MiB.
 */
public final class HttpClientTransport implements Transport {
  private final HttpClient client;

  /**
   * Creates the transport.
   *
   * @param connectTimeout the connect timeout
   * @param proxy the proxy selector, or null for the system default
   * @param executor the executor for async work, or null for the client's default
   */
  public HttpClientTransport(
      Duration connectTimeout, @Nullable ProxySelector proxy, @Nullable Executor executor) {
    HttpClient.Builder b =
        HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NEVER)
            .connectTimeout(connectTimeout);
    if (proxy != null) {
      b.proxy(proxy);
    }
    if (executor != null) {
      b.executor(executor);
    }
    this.client = b.build();
  }

  /**
   * Whether the JDK's own HTTP client logging is set to print request headers (the {@code
   * jdk.httpclient.HttpClient.log} system property), which would include {@code X-API-Key}.
   *
   * @param property the property's value, or null
   * @return true when headers would be logged
   */
  public static boolean jdkLogsHeaders(@Nullable String property) {
    if (property == null) {
      return false;
    }
    String p = property.toLowerCase(Locale.ROOT);
    return p.contains("all") || p.contains("headers") || p.contains("requests");
  }

  @Override
  public CompletableFuture<TransportResponse> send(TransportRequest request) {
    HttpRequest.Builder b = HttpRequest.newBuilder(request.uri()).GET().timeout(request.timeout());
    for (Map.Entry<String, String> h : request.headers().entrySet()) {
      b.header(h.getKey(), h.getValue());
    }
    return client
        .sendAsync(b.build(), info -> new BoundedBody(info))
        .thenApply(r -> new TransportResponse(r.statusCode(), r.headers().map(), r.body()));
  }

  /** Collects a body, failing once it passes the bound. */
  private static final class BoundedBody implements HttpResponse.BodySubscriber<byte[]> {
    private final CompletableFuture<byte[]> result = new CompletableFuture<>();
    private final List<ByteBuffer> parts = new ArrayList<>();
    private final int status;
    private long size;
    private Flow.@Nullable Subscription subscription;

    BoundedBody(HttpResponse.ResponseInfo info) {
      this.status = info.statusCode();
      long declared = info.headers().firstValueAsLong("Content-Length").orElse(-1L);
      if (declared > BodyTooLargeException.MAX_BODY_BYTES) {
        result.completeExceptionally(new BodyTooLargeException(status));
      }
    }

    @Override
    public CompletionStage<byte[]> getBody() {
      return result;
    }

    @Override
    public void onSubscribe(Flow.Subscription s) {
      this.subscription = s;
      if (result.isDone()) {
        s.cancel();
      } else {
        s.request(Long.MAX_VALUE);
      }
    }

    @Override
    public void onNext(List<ByteBuffer> items) {
      if (result.isDone()) {
        return;
      }
      for (ByteBuffer item : items) {
        size += item.remaining();
        parts.add(item);
      }
      if (size > BodyTooLargeException.MAX_BODY_BYTES) {
        parts.clear();
        Flow.Subscription s = subscription;
        if (s != null) {
          s.cancel();
        }
        result.completeExceptionally(new BodyTooLargeException(status));
      }
    }

    @Override
    public void onError(Throwable error) {
      result.completeExceptionally(error);
    }

    @Override
    public void onComplete() {
      byte[] out = new byte[(int) size];
      int at = 0;
      for (ByteBuffer part : parts) {
        int n = part.remaining();
        part.get(out, at, n);
        at += n;
      }
      result.complete(out);
    }
  }
}
