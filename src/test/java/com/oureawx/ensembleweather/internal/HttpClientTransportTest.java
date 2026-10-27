// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** The real java.net.http transport, against a local server. */
class HttpClientTransportTest {
  private HttpServer server;
  private URI base;

  @BeforeEach
  void start() throws Exception {
    server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
    server.createContext(
        "/redirect",
        ex -> {
          ex.getResponseHeaders().add("Location", "/elsewhere");
          ex.sendResponseHeaders(302, -1);
          ex.close();
        });
    server.createContext(
        "/echo",
        ex -> {
          byte[] b =
              String.valueOf(ex.getRequestHeaders().getFirst("X-API-Key"))
                  .getBytes(StandardCharsets.UTF_8);
          ex.sendResponseHeaders(200, b.length);
          try (OutputStream out = ex.getResponseBody()) {
            out.write(b);
          }
        });
    server.createContext(
        "/huge",
        ex -> {
          ex.sendResponseHeaders(200, 0);
          byte[] chunk = new byte[1 << 20];
          try (OutputStream out = ex.getResponseBody()) {
            for (int i = 0; i < 65; i++) {
              out.write(chunk);
            }
          } catch (java.io.IOException closed) {
            // The client stops reading at the bound.
          }
        });
    server.start();
    base = URI.create("http://127.0.0.1:" + server.getAddress().getPort());
  }

  @AfterEach
  void stop() {
    server.stop(0);
  }

  private TransportResponse get(String path) throws Exception {
    return new HttpClientTransport(Duration.ofSeconds(5), null, null)
        .send(
            new TransportRequest(
                base.resolve(path), Map.of("X-API-Key", "ow_k"), Duration.ofSeconds(10)))
        .get();
  }

  @Test
  void redirectsAreNotFollowedByTheStack() throws Exception {
    TransportResponse r = get("/redirect");
    assertEquals(302, r.status());
    assertEquals("/elsewhere", r.header("location"));
  }

  @Test
  void headersAreSent() throws Exception {
    assertEquals("ow_k", new String(get("/echo").body(), StandardCharsets.UTF_8));
  }

  @Test
  void bodiesAreBoundedAt64MiB() {
    ExecutionException e = assertThrows(ExecutionException.class, () -> get("/huge"));
    Throwable c = e.getCause();
    while (!(c instanceof BodyTooLargeException) && c.getCause() != null) {
      c = c.getCause();
    }
    assertInstanceOf(BodyTooLargeException.class, c);
  }

  @Test
  void jdkHeaderLoggingIsDetected() {
    assertTrue(HttpClientTransport.jdkLogsHeaders("headers,errors"));
    assertTrue(HttpClientTransport.jdkLogsHeaders("all"));
    assertFalse(HttpClientTransport.jdkLogsHeaders("errors"));
    assertFalse(HttpClientTransport.jdkLogsHeaders(null));
  }
}
