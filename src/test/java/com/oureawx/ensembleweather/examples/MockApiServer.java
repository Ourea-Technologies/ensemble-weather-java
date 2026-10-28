// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.examples;

import com.oureawx.ensembleweather.internal.Json;
import com.oureawx.ensembleweather.model.JsonValue;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A tiny stand-in for the Ourea API, serving the shared vector bodies, so the examples run in CI
 * without a key and without calling the real API. It is a test stub: its probabilities and totals
 * are canned numbers, not forecasts.
 *
 * <pre>
 * ./gradlew mockApi     # serves on http://127.0.0.1:8787 until stopped
 * OUREA_BASE_URL=http://127.0.0.1:8787 java -cp ... Quickstart
 * </pre>
 */
public final class MockApiServer implements AutoCloseable {
  private final HttpServer server;
  private final Path bodies;

  private MockApiServer(Path specDir, int port) throws IOException {
    this.bodies = specDir.resolve("vectors").resolve("bodies");
    this.server =
        HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), port), 0);
    server.createContext("/", this::handle);
    server.start();
  }

  /**
   * Starts the mock.
   *
   * @param specDir the pinned spec directory
   * @param port the port, or 0 for any free one
   * @return the running mock
   * @throws IOException if it cannot listen
   */
  public static MockApiServer start(Path specDir, int port) throws IOException {
    return new MockApiServer(specDir, port);
  }

  /**
   * The base URL to pass as {@code OUREA_BASE_URL}.
   *
   * @return the URL
   */
  public String url() {
    return "http://127.0.0.1:" + server.getAddress().getPort();
  }

  @Override
  public void close() {
    server.stop(0);
  }

  /**
   * Serves until stopped.
   *
   * @param args {@code [specDir] [port]}
   * @throws Exception if it cannot start
   */
  public static void main(String[] args) throws Exception {
    Path spec = Paths.get(args.length > 0 ? args[0] : "spec");
    int port = args.length > 1 ? Integer.parseInt(args[1]) : 8787;
    MockApiServer mock = start(spec, port);
    System.out.println("mock Ourea API on " + mock.url() + " (OUREA_BASE_URL=" + mock.url() + ")");
    Thread.currentThread().join();
  }

  private JsonValue load(String name) throws IOException {
    return Json.parse(Files.readAllBytes(bodies.resolve(name + ".json")));
  }

  private static JsonValue num(double d) {
    return JsonValue.of(BigDecimal.valueOf(d));
  }

  private static JsonValue str(String s) {
    return JsonValue.of(s);
  }

  private static Map<String, String> params(HttpExchange ex) {
    Map<String, String> out = new LinkedHashMap<>();
    String raw = ex.getRequestURI().getRawQuery();
    if (raw == null) {
      return out;
    }
    for (String pair : raw.split("&", -1)) {
      int eq = pair.indexOf('=');
      out.put(
          pair.substring(0, eq),
          URLDecoder.decode(pair.substring(eq + 1).replace("+", "%2B"), StandardCharsets.UTF_8));
    }
    return out;
  }

  private static int[] window(String text) {
    String w = text == null ? "24h" : text.substring(0, text.length() - 1);
    int dash = w.indexOf('-');
    return dash < 0
        ? new int[] {0, Integer.parseInt(w)}
        : new int[] {
          Integer.parseInt(w.substring(0, dash)), Integer.parseInt(w.substring(dash + 1))
        };
  }

  private JsonValue forecast(Map<String, String> p, boolean point) throws IOException {
    String units = p.getOrDefault("units", "metric");
    Map<String, JsonValue> body =
        new LinkedHashMap<>(
            load(units.equals("imperial") ? "forecast-imperial" : "forecast-metric").asObject());
    body.put("units", str(units));
    double[] canned = {0.42, 0.18, 0.73, 0.05};
    if (p.containsKey("prob")) {
      List<JsonValue> answers = new ArrayList<>();
      String[] terms = p.get("prob").split(",", -1);
      for (int i = 0; i < terms.length; i++) {
        String[] parts = terms[i].split(":", -1);
        int[] w = window(parts.length > 3 ? parts[3] : null);
        boolean amount = parts[0].equals("precipitation") || parts[0].equals("snowfall");
        Map<String, JsonValue> a = new LinkedHashMap<>();
        a.put("term", str(terms[i]));
        a.put("variable", str(parts[0]));
        a.put("op", str(parts[1]));
        a.put("threshold", JsonValue.of(new BigDecimal(parts[2])));
        a.put("window_start_hours", num(w[0]));
        a.put("window_end_hours", num(w[1]));
        a.put("aggregation", str(parts.length > 4 ? parts[4] : amount ? "total" : "any"));
        a.put("from", str("2026-11-15T00:00:00Z"));
        a.put("to", str("2026-11-16T00:00:00Z"));
        a.put("probability", num(canned[i % canned.length]));
        a.put("members", num(131));
        a.put("null_reason", JsonValue.nullValue());
        answers.add(JsonValue.ofObject(a));
      }
      body.put("probabilities", JsonValue.ofArray(answers));
    }
    if (p.containsKey("totals")) {
      JsonValue quantiles =
          load("forecast-totals")
              .get("window_totals")
              .orElseThrow()
              .asArray()
              .get(0)
              .get("quantiles")
              .orElseThrow();
      List<JsonValue> answers = new ArrayList<>();
      for (String term : p.get("totals").split(",", -1)) {
        String[] parts = term.split(":", -1);
        int[] w = window(parts[1]);
        Map<String, JsonValue> a = new LinkedHashMap<>();
        a.put("term", str(term));
        a.put("variable", str(parts[0]));
        a.put("window_start_hours", num(w[0]));
        a.put("window_end_hours", num(w[1]));
        a.put("aggregation", str("total"));
        a.put("from", str("2026-11-15T00:00:00Z"));
        a.put("to", str("2026-11-17T00:00:00Z"));
        a.put("quantiles", quantiles);
        a.put("members", num(131));
        a.put("null_reason", JsonValue.nullValue());
        answers.add(JsonValue.ofObject(a));
      }
      body.put("window_totals", JsonValue.ofArray(answers));
    }
    if (point) {
      double lat = Double.parseDouble(p.get("lat"));
      double lon = Double.parseDouble(p.get("lon"));
      Map<String, JsonValue> pt = new LinkedHashMap<>();
      pt.put("requested_lat", num(lat));
      pt.put("requested_lon", num(lon));
      pt.put("lat", num(Math.round(lat * 100) / 100.0));
      pt.put("lon", num(Math.round(lon * 100) / 100.0));
      pt.put("resolution_deg", num(0.01));
      pt.put(
          "elevation",
          num(p.containsKey("elevation_m") ? Double.parseDouble(p.get("elevation_m")) : 1661));
      pt.put("elevation_source", str(p.containsKey("elevation_m") ? "request" : "dem_3dep_10m"));
      pt.put("state", str("OR"));
      body.put("point", JsonValue.ofObject(pt));
    }
    return JsonValue.ofObject(body);
  }

  private JsonValue attribution() {
    Map<String, JsonValue> a = new LinkedHashMap<>();
    a.put("sources", JsonValue.ofArray(List.of(str("ecmwf-ifs-ens"), str("noaa-gefs"))));
    a.put("credit", str("Forecast by Ourea from ECMWF and NOAA ensemble data."));
    a.put("url", str("https://oureawx.com/attribution"));
    return JsonValue.ofObject(a);
  }

  private JsonValue current(String units) throws IOException {
    Map<String, JsonValue> c = new LinkedHashMap<>();
    c.put("time", str("2026-11-15T00:00:00Z"));
    c.put("temperature", num(units.equals("imperial") ? 28.4 : -2.0));
    c.put("condition", str("snow"));
    c.put("condition_text", str("Snow"));
    c.put("condition_icon", str("night_snow"));
    c.put("wind_speed", num(units.equals("imperial") ? 13.4 : 6.0));
    c.put("wind_direction", num(5));
    c.put("source", str("nowcast"));
    Map<String, JsonValue> body = new LinkedHashMap<>();
    body.put("location", load("forecast-metric").get("location").orElseThrow());
    body.put("alerts", JsonValue.ofArray(List.of()));
    body.put("alerts_as_of", str("2026-11-15T00:00:00Z"));
    body.put("sensor_count", num(0));
    body.put("sensors", JsonValue.ofArray(List.of()));
    body.put("current", JsonValue.ofObject(c));
    body.put("as_of", str("2026-11-15T00:00:00Z"));
    body.put("model_run_initialized_at", str("2026-11-15T00:00:00Z"));
    body.put("units", str(units));
    body.put("attribution", attribution());
    return JsonValue.ofObject(body);
  }

  private JsonValue alerts() throws IOException {
    Map<String, JsonValue> alert = new LinkedHashMap<>();
    alert.put("id", str("urn:oid:2.49.0.1.840.0.example"));
    alert.put("event", str("Winter Storm Warning"));
    alert.put("severity", str("severe"));
    alert.put("urgency", str("expected"));
    alert.put("certainty", str("likely"));
    alert.put("headline", str("Winter Storm Warning until 4 PM PST Sunday"));
    alert.put("expires", str("2026-11-16T00:00:00Z"));
    Map<String, JsonValue> body = new LinkedHashMap<>();
    body.put("location", load("forecast-metric").get("location").orElseThrow());
    body.put("alert_count", num(1));
    body.put("as_of", str("2026-11-15T00:00:00Z"));
    body.put("alerts", JsonValue.ofArray(List.of(JsonValue.ofObject(alert))));
    body.put("attribution", attribution());
    return JsonValue.ofObject(body);
  }

  private static JsonValue error(String code, String message) {
    Map<String, JsonValue> e = new LinkedHashMap<>();
    e.put("error", str(code));
    e.put("message", str(message));
    e.put("request_id", str("mock-request"));
    e.put(
        "doc_url",
        str("https://docs.oureawx.com/features/errors/#" + code.toLowerCase(Locale.ROOT)));
    return JsonValue.ofObject(e);
  }

  private void handle(HttpExchange ex) throws IOException {
    String path = ex.getRequestURI().getRawPath();
    Map<String, String> p = params(ex);
    int status = 200;
    JsonValue body;
    String[] seg = path.split("/", -1);
    if (path.matches("/api/v1/locations/[^/]+/[^/]+")) {
      body = forecast(p, false);
    } else if (path.matches("/api/v1/locations/[^/]+/[^/]+/current")) {
      body = current(p.getOrDefault("units", "metric"));
    } else if (path.matches("/api/v1/locations/[^/]+/[^/]+/alerts")) {
      body = alerts();
    } else if (path.equals("/api/v1/forecast")
        || path.equals("/api/v1/current")
        || path.equals("/api/v1/alerts")) {
      double lat = Double.parseDouble(p.getOrDefault("lat", "0"));
      double lon = Double.parseDouble(p.getOrDefault("lon", "0"));
      if (!(lat >= 24 && lat <= 50 && lon >= -125 && lon <= -66)) {
        status = 404;
        body =
            error("OUTSIDE_COVERAGE", lat + "," + lon + " is outside the forecast domain (CONUS).");
      } else if (path.equals("/api/v1/current")) {
        body = current(p.getOrDefault("units", "metric"));
      } else if (path.equals("/api/v1/alerts")) {
        body = alerts();
      } else {
        body = forecast(p, true);
      }
    } else if (path.equals("/api/v1/search/locations")) {
      body = load("search");
    } else if (path.equals("/api/v1/catalog/locations")) {
      body = load("c2".equals(p.get("cursor")) ? "catalog-page-2" : "catalog-page-1");
    } else if (seg.length == 5 && path.startsWith("/api/v1/catalog/locations/")) {
      body = load("catalog-detail");
    } else if (path.equals("/api/v1/attribution")) {
      body = load("attribution");
    } else {
      status = 404;
      body = error("NOT_FOUND", "No route for " + path);
    }
    byte[] bytes = body.toString().getBytes(StandardCharsets.UTF_8);
    ex.getResponseHeaders().add("Content-Type", "application/json");
    ex.getResponseHeaders().add("Cache-Control", "private, max-age=0");
    if (ex.getRequestHeaders().getFirst("X-API-Key") != null) {
      ex.getResponseHeaders().add("RateLimit-Limit", "600");
      ex.getResponseHeaders().add("RateLimit-Remaining", "599");
      ex.getResponseHeaders().add("RateLimit-Reset", "60");
      ex.getResponseHeaders().add("RateLimit-Policy", "600;w=60");
    }
    ex.sendResponseHeaders(status, bytes.length);
    try (OutputStream out = ex.getResponseBody()) {
      out.write(bytes);
    }
  }
}
