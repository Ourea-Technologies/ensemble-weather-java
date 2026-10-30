// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.oureawx.ensembleweather.internal.BodyTooLargeException;
import com.oureawx.ensembleweather.internal.TransportRequest;
import com.oureawx.ensembleweather.model.Aggregation;
import com.oureawx.ensembleweather.model.CatalogLocation;
import com.oureawx.ensembleweather.model.ForecastResponse;
import com.oureawx.ensembleweather.model.LocationSummary;
import com.oureawx.ensembleweather.model.Percentile;
import com.oureawx.ensembleweather.model.Quantiles;
import com.oureawx.ensembleweather.model.UnitSystem;
import com.oureawx.ensembleweather.model.Variable;
import java.io.IOException;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import javax.net.ssl.SSLHandshakeException;
import org.junit.jupiter.api.Test;

class ClientTest {
  private static final String FORECAST = body("forecast-metric.json");

  static String body(String name) {
    try {
      return new String(
          Files.readAllBytes(TestClients.specDir().resolve("vectors/bodies/" + name)),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private static String sentUrl(TestClients t, int i) {
    return t.transport.sent.get(i).uri().toString();
  }

  @Test
  void userAgentNamesTheSdkAndTheRuntimeAndTakesASuffix() {
    TestClients t = new TestClients();
    t.transport.json(200, FORECAST);
    t.builder().userAgentSuffix("myapp/1.2").build().getForecast("or/mt-hood-meadows");
    String ua = t.transport.sent.get(0).headers().get("User-Agent");
    assertTrue(ua.matches("ensemble-weather-java/0\\.1\\.0 \\(Java/[^)]+\\) myapp/1\\.2"), ua);
    assertThrows(UsageException.class, () -> t.builder().userAgentSuffix("bad\r\nheader"));
  }

  @Test
  void versionMatchesTheBuild() {
    String version = System.getProperty("ourea.project.version");
    if (version != null) {
      assertEquals(version, EnsembleWeatherClient.VERSION);
    }
    assertEquals("0.1.0", EnsembleWeatherClient.VERSION);
  }

  @Test
  void baseUrlComesFromTheEnvironmentAndIsValidated() {
    TestClients t = new TestClients();
    t.transport.json(200, FORECAST);
    t.builder(Map.of("OUREA_BASE_URL", "http://127.0.0.1:8787/"))
        .build()
        .getForecast("or/mt-hood-meadows");
    assertEquals("http://127.0.0.1:8787/api/v1/locations/or/mt-hood-meadows", sentUrl(t, 0));
    assertEquals(
        "https://api.oureawx.com", t.builder().build().baseUrl().toString(), "the default");
    assertThrows(UsageException.class, () -> t.builder().baseUrl("ftp://x.test").build());
    assertThrows(UsageException.class, () -> t.builder().baseUrl("not a url").build());
    assertThrows(UsageException.class, () -> t.builder().baseUrl("https://x.test/?a=1").build());
  }

  @Test
  void anonymousIgnoresTheEnvironmentKey() {
    TestClients t = new TestClients();
    t.transport.json(200, FORECAST);
    EnsembleWeatherClient c = t.builder(Map.of("OUREA_API_KEY", "ow_env")).anonymous().build();
    assertFalse(c.hasApiKey());
    c.getForecast("or/mt-hood-meadows");
    assertNull(t.transport.sent.get(0).headers().get("X-API-Key"));
  }

  @Test
  void theKeyNeverAppearsInToString() {
    TestClients t = new TestClients();
    EnsembleWeatherClient.Builder b = t.builder().apiKey("ow_secret_1234");
    EnsembleWeatherClient c = b.build();
    for (String s :
        List.of(c.toString(), b.toString(), c.withApiKey("ow_secret_5678").toString())) {
      assertFalse(s.contains("ow_secret"), s);
    }
    assertFalse(
        new TransportRequest(
                java.net.URI.create("https://x.test/"),
                Map.of("X-API-Key", "ow_secret_1234"),
                java.time.Duration.ofSeconds(1))
            .toString()
            .contains("ow_secret"));
  }

  @Test
  void aServerMessageThatQuotesTheKeyIsRedacted() {
    TestClients t = new TestClients();
    t.transport.json(401, "{\"error\":\"INVALID_API_KEY\",\"message\":\"bad key ow_echoed\"}");
    AuthenticationException e =
        assertThrows(
            AuthenticationException.class,
            () -> t.builder().apiKey("ow_echoed").build().getForecast("or/x"));
    assertFalse(e.getMessage().contains("ow_echoed"), e.getMessage());
    assertTrue(e.getMessage().contains("[redacted]"));
  }

  @Test
  void asyncMethodsCompleteWithTheModelOrTheException() throws Exception {
    TestClients t = new TestClients();
    t.transport.json(200, FORECAST).json(404, body("error-not-found.json"));
    EnsembleWeatherClient c = t.builder().build();
    CompletableFuture<ForecastResponse> ok =
        c.getForecastAsync(LocationId.parse("or/mt-hood-meadows"));
    assertEquals(Optional.of("mt-hood-meadows"), ok.get().location().slug());
    ExecutionException e =
        assertThrows(
            ExecutionException.class, () -> c.getAlertsAsync(LocationId.of("or", "x")).get());
    assertInstanceOf(NotFoundException.class, e.getCause());
    // A usage error is a failed future, never a synchronous throw from an async method.
    CompletableFuture<?> usage = c.getPointAlertsAsync(Coordinates.of(1, 2, 3));
    assertInstanceOf(
        UsageException.class, assertThrows(ExecutionException.class, usage::get).getCause());
  }

  @Test
  void connectionErrorsAreRetriedButNotTlsFailures() {
    TestClients t = new TestClients();
    t.transport.fail(new ConnectException("refused")).json(200, FORECAST);
    t.builder().build().getForecast("or/mt-hood-meadows");
    assertEquals(2, t.transport.sent.size());
    assertEquals(List.of(0.5), t.delays);

    TestClients tls = new TestClients();
    tls.transport.fail(new SSLHandshakeException("PKIX path building failed"));
    ConnectionException e =
        assertThrows(ConnectionException.class, () -> tls.builder().build().getForecast("or/x"));
    assertEquals(1, tls.transport.sent.size(), "a TLS verification failure is not retried");
    assertInstanceOf(SSLHandshakeException.class, e.getCause());
  }

  @Test
  void backoffDoublesUpToEightSeconds() {
    TestClients t = new TestClients();
    for (int i = 0; i < 7; i++) {
      t.transport.respond(504, Map.of(), "");
    }
    assertThrows(
        UnavailableException.class, () -> t.builder().maxRetries(6).build().getForecast("or/x"));
    assertEquals(List.of(0.5, 1.0, 2.0, 4.0, 8.0, 8.0), t.delays);
  }

  @Test
  void retryAfterAsAnHttpDateIsHonoured() {
    TestClients t = new TestClients();
    t.transport
        .respond(503, Map.of("Retry-After", "Sun, 15 Nov 2026 00:00:07 GMT"), "")
        .json(200, FORECAST);
    t.builder().build().getForecast("or/mt-hood-meadows");
    assertEquals(List.of(7.0), t.delays);
  }

  @Test
  void retryAfterOnA504IsHonoured() {
    TestClients t = new TestClients();
    t.transport.respond(504, Map.of("Retry-After", "3"), "").json(200, FORECAST);
    t.builder().build().getForecast("or/mt-hood-meadows");
    assertEquals(List.of(3.0), t.delays);
  }

  @Test
  void theErrorBodyCarriesItsRequestIdDocUrlAndParam() {
    TestClients t = new TestClients();
    t.transport.respond(
        400,
        Map.of("Content-Type", "application/json", "X-Request-Id", "from-header"),
        "{\"error\":\"BAD_REQUEST\",\"message\":\"max_hours must be 1-240\","
            + "\"request_id\":\"from-body\",\"param\":\"max_hours\","
            + "\"doc_url\":\"https://docs.oureawx.com/features/errors/#bad_request\"}");
    InvalidRequestException e =
        assertThrows(InvalidRequestException.class, () -> t.builder().build().getForecast("or/x"));
    assertEquals(Optional.of("from-body"), e.requestId());
    assertEquals(Optional.of("max_hours"), e.param());
    assertEquals(Optional.of("https://docs.oureawx.com/features/errors/#bad_request"), e.docUrl());
  }

  @Test
  void proRequiredNamesTheNearestFreeLocation() {
    TestClients t = new TestClients();
    t.transport.json(
        403,
        "{\"error\":\"PRO_REQUIRED\",\"message\":\"Pro\",\"nearest_location\":"
            + "{\"slug\":\"mt-hood-meadows\",\"url\":\"/api/v1/locations/or/mt-hood-meadows\","
            + "\"distance_km\":2.5,\"elevation_m\":1600}}");
    PermissionException e =
        assertThrows(
            PermissionException.class,
            () -> t.builder().build().getPointForecast(Coordinates.of(45.33, -121.67)));
    assertEquals("mt-hood-meadows", e.nearestLocation().orElseThrow().slug());
    assertEquals(Optional.empty(), e.param());
  }

  @Test
  void retiredCodesMapByStatus() {
    TestClients t = new TestClients();
    t.transport
        .json(403, "{\"error\":\"FORBIDDEN\",\"message\":\"x\"}")
        .json(400, "{\"error\":\"FEELS_LIKE_UNAVAILABLE\",\"message\":\"x\"}")
        .json(503, "{\"error\":\"AUTH_UNAVAILABLE\",\"message\":\"x\"}");
    EnsembleWeatherClient c = t.builder().maxRetries(0).build();
    PermissionException p = assertThrows(PermissionException.class, () -> c.getForecast("or/x"));
    assertEquals(Optional.of("FORBIDDEN"), p.code());
    assertThrows(InvalidRequestException.class, () -> c.getForecast("or/x"));
    assertThrows(UnavailableException.class, () -> c.getForecast("or/x"));
  }

  @Test
  void aBodyOverTheBoundIsAnErrorNotRetried() {
    TestClients t = new TestClients();
    t.transport.fail(new BodyTooLargeException(200));
    UnavailableException e =
        assertThrows(UnavailableException.class, () -> t.builder().build().getForecast("or/x"));
    assertTrue(e.getMessage().contains("64 MiB"));
    assertEquals(1, t.transport.sent.size());
  }

  @Test
  void aBodyThatIsNotTheContractIsUnavailable() {
    TestClients t = new TestClients();
    t.transport.json(200, "not json").json(200, "{\"location\":{}}");
    EnsembleWeatherClient c = t.builder().build();
    assertThrows(UnavailableException.class, () -> c.getForecast("or/x"));
    UnavailableException e = assertThrows(UnavailableException.class, () -> c.getForecast("or/y"));
    assertTrue(e.getMessage().contains("$.location"), e.getMessage());
  }

  @Test
  void redirectsStopAfterFive() {
    TestClients t = new TestClients();
    for (int i = 0; i < 6; i++) {
      t.transport.respond(302, Map.of("Location", "/api/v1/locations/or/hop" + i), "");
    }
    assertThrows(UnavailableException.class, () -> t.builder().build().getForecast("or/x"));
    assertEquals(6, t.transport.sent.size());
  }

  @Test
  void theKeyIsDroppedWhenARedirectChangesSchemeOrPort() {
    for (String target : List.of("http://api.oureawx.com/x", "https://api.oureawx.com:8443/x")) {
      TestClients t = new TestClients();
      t.transport.respond(307, Map.of("Location", target), "").json(200, FORECAST);
      t.builder().apiKey("ow_k").build().getForecast("or/mt-hood-meadows");
      assertEquals("ow_k", t.transport.sent.get(0).headers().get("X-API-Key"));
      assertNull(t.transport.sent.get(1).headers().get("X-API-Key"), target);
    }
  }

  @Test
  void aFreshMaxAgeIsServedWithoutARequest() {
    TestClients t = new TestClients();
    t.transport.respond(
        200,
        Map.of(
            "Content-Type",
            "application/json",
            "ETag",
            "\"a\"",
            "Cache-Control",
            "public, max-age=300"),
        body("search.json"));
    EnsembleWeatherClient c = t.builder().build();
    c.searchLocations("hood");
    assertEquals(1, c.searchLocations("hood").count());
    assertEquals(1, t.transport.sent.size());
  }

  @Test
  void theCacheIsBoundedAndSharedByWithApiKey() {
    InMemoryResponseCache cache = new InMemoryResponseCache(2, 1000);
    cache.put(
        "a", CachedResponse.of("\"1\"", new byte[400], TestClients.NOW, java.time.Duration.ZERO));
    cache.put(
        "b", CachedResponse.of("\"1\"", new byte[400], TestClients.NOW, java.time.Duration.ZERO));
    cache.get("a");
    cache.put(
        "c", CachedResponse.of("\"1\"", new byte[400], TestClients.NOW, java.time.Duration.ZERO));
    assertTrue(cache.get("a").isPresent(), "recently used stays");
    assertFalse(cache.get("b").isPresent(), "least recently used goes");
    assertEquals(2, cache.size());
    cache.put(
        "huge",
        CachedResponse.of("\"1\"", new byte[2000], TestClients.NOW, java.time.Duration.ZERO));
    assertFalse(cache.get("huge").isPresent());
  }

  @Test
  void customCacheKeysNeverHoldTheApiKey() {
    List<String> keys = new ArrayList<>();
    ResponseCache spy =
        new ResponseCache() {
          final InMemoryResponseCache inner = new InMemoryResponseCache();

          @Override
          public java.util.Optional<CachedResponse> get(String key) {
            keys.add(key);
            return inner.get(key);
          }

          @Override
          public void put(String key, CachedResponse response) {
            keys.add(key);
            inner.put(key, response);
          }

          @Override
          public void remove(String key) {
            inner.remove(key);
          }
        };
    TestClients t = new TestClients();
    t.transport.respond(200, Map.of("ETag", "\"a\""), FORECAST);
    t.builder().apiKey("ow_cachekey").cache(spy).build().getForecast("or/mt-hood-meadows");
    assertFalse(keys.isEmpty());
    for (String k : keys) {
      assertFalse(k.contains("ow_cachekey"), k);
    }
  }

  @Test
  void deprecationWarnsOncePerOperation() {
    TestClients t = new TestClients();
    Map<String, String> h =
        Map.of("Content-Type", "application/json", "Sunset", "Wed, 01 Dec 2027 00:00:00 GMT");
    t.transport
        .respond(200, h, FORECAST)
        .respond(200, h, FORECAST)
        .respond(200, h, body("search.json"));
    EnsembleWeatherClient c = t.builder().build();
    c.getForecast("or/mt-hood-meadows");
    c.getForecast("or/mt-hood-meadows");
    c.searchLocations("hood");
    assertEquals(2, t.warnings.size(), t.warnings.toString());
    assertTrue(t.warnings.get(0).contains("getLocationForecast"));
  }

  @Test
  void aPresentProbabilityListMustAnswerEveryTerm() {
    TestClients t = new TestClients();
    t.transport.json(200, body("forecast-prob-snow.json")).json(200, FORECAST);
    EnsembleWeatherClient c = t.builder().build();
    ForecastOptions o =
        ForecastOptions.builder()
            .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.millimetres(99)))
            .build();
    UnavailableException e =
        assertThrows(UnavailableException.class, () -> c.getForecast("or/x", o));
    assertTrue(e.getMessage().contains("snowfall:ge:99.00:24h"), e.getMessage());
    // An absent list is absent, not an error.
    assertTrue(c.getForecast("or/x", o).probabilities().isEmpty());
  }

  @Test
  void termsAreValidatedBeforeSending() {
    assertThrows(
        UsageException.class,
        () ->
            ProbabilityTerm.atLeast(Variable.TEMPERATURE, Threshold.celsius(0))
                .withAggregation(Aggregation.TOTAL));
    assertThrows(UsageException.class, () -> TotalTerm.of(Variable.TEMPERATURE));
    assertThrows(UsageException.class, () -> Window.between(10, 10));
    assertThrows(UsageException.class, () -> Window.hours(361));
    assertThrows(UsageException.class, () -> Threshold.of(Double.NaN));
    TestClients t = new TestClients();
    ForecastOptions wrongUnit =
        ForecastOptions.builder()
            .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.fahrenheit(3)))
            .build();
    assertThrows(UsageException.class, () -> t.builder().build().getForecast("or/x", wrongUnit));
    assertTrue(t.transport.sent.isEmpty());
  }

  @Test
  void locationsAreParsedAndCanonical() {
    assertEquals("or/mt-hood-meadows", LocationId.parse("OR/mt-hood-meadows").toString());
    assertEquals("Oregon", LocationId.of("Oregon", "x").state());
    assertThrows(UsageException.class, () -> LocationId.parse("mt-hood-meadows"));
    assertThrows(UsageException.class, () -> LocationId.parse("or/a/b"));
  }

  @Test
  void quantileRangesAreOnlyTheServedPairs() {
    Quantiles q = Quantiles.builder().p10(1.0).p25(2.0).p50(3.0).p75(4.0).p90(5.0).build();
    assertEquals(1.0, q.range(Percentile.P10, Percentile.P90).orElseThrow().low());
    assertEquals(4.0, q.range(Percentile.P25, Percentile.P75).orElseThrow().high());
    assertEquals(3.0, q.median().getAsDouble());
    assertThrows(UsageException.class, () -> q.range(Percentile.P10, Percentile.P75));
    assertFalse(
        Quantiles.builder().p10(1.0).build().range(Percentile.P10, Percentile.P90).isPresent());
    assertFalse(Quantiles.builder().build().mean().isPresent(), "absent is never 0");
  }

  @Test
  void pagingFollowsTheCursorLazilyAndStopsOnALoop() {
    TestClients t = new TestClients();
    t.transport.json(200, body("catalog-page-1.json")).json(200, body("catalog-page-2.json"));
    EnsembleWeatherClient c = t.builder().build();
    PagedIterable<CatalogLocation> all = c.iterateCatalogLocations(CatalogLocationQuery.all());
    assertTrue(t.transport.sent.isEmpty(), "nothing is fetched before iteration");
    assertEquals(
        List.of("mt-hood-meadows"),
        all.stream().limit(1).map(CatalogLocation::slug).collect(Collectors.toList()));
    assertEquals(1, t.transport.sent.size(), "the second page is fetched only when needed");

    TestClients loop = new TestClients();
    loop.transport.json(200, body("catalog-page-1.json")).json(200, body("catalog-page-1.json"));
    PagedIterable<CatalogLocation> looping =
        loop.builder()
            .build()
            .iterateCatalogLocations(CatalogLocationQuery.builder().cursor("c2").build());
    assertThrows(UnavailableException.class, () -> looping.forEach(x -> {}));
  }

  @Test
  void theLocationListIteratesUntilNextCursorIsAbsent() {
    String page1 =
        "{\"state\":\"Oregon\",\"count\":1,\"next_cursor\":\"b\",\"locations\":[" + row("a") + "]}";
    String page2 = "{\"state\":\"Oregon\",\"count\":1,\"locations\":[" + row("b") + "]}";
    TestClients t = new TestClients();
    t.transport.json(200, page1).json(200, page2);
    List<String> slugs = new ArrayList<>();
    for (LocationSummary s :
        t.builder()
            .build()
            .iterateLocations("OR", LocationListOptions.builder().limit(1).build())) {
      slugs.add(s.slug());
    }
    assertEquals(List.of("a", "b"), slugs);
    assertEquals("/api/v1/locations/or", t.transport.sent.get(0).uri().getRawPath());
    assertEquals("cursor=b&limit=1", t.transport.sent.get(1).uri().getRawQuery());
  }

  private static String row(String slug) {
    return "{\"slug\":\""
        + slug
        + "\",\"state\":\"or\",\"name\":\"N\",\"lat\":1,\"lon\":2,\"elevation_m\":3,"
        + "\"type\":\"summit\",\"timezone\":\"America/Los_Angeles\",\"county\":\"C\"}";
  }

  @Test
  void clientUnitsApplyToEveryRequestAndOptionsOverrideThem() {
    TestClients t = new TestClients();
    t.transport.json(200, body("forecast-imperial.json")).json(200, FORECAST);
    EnsembleWeatherClient c = t.builder().units(UnitSystem.IMPERIAL).build();
    c.getForecast("or/mt-hood-meadows");
    c.getForecast("or/mt-hood-meadows", ForecastOptions.builder().units(UnitSystem.METRIC).build());
    assertEquals("units=imperial", t.transport.sent.get(0).uri().getRawQuery());
    assertNull(t.transport.sent.get(1).uri().getRawQuery());
  }

  @Test
  void withApiKeySharesTheCacheButNotEntries() {
    TestClients t = new TestClients();
    t.transport
        .respond(200, Map.of("ETag", "\"a\""), FORECAST)
        .respond(304, Map.of("ETag", "\"a\""), "");
    EnsembleWeatherClient a = t.builder().apiKey("ow_a").build();
    a.getForecast("or/mt-hood-meadows");
    ForecastResponse again = a.withApiKey("ow_a").getForecast("or/mt-hood-meadows");
    assertTrue(again.metadata().revalidated());
    assertSame(UnitSystem.METRIC, again.units());
  }
}
