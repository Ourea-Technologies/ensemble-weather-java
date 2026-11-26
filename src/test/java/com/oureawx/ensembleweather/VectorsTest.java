// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.oureawx.ensembleweather.internal.Json;
import com.oureawx.ensembleweather.internal.TransportRequest;
import com.oureawx.ensembleweather.types.Aggregation;
import com.oureawx.ensembleweather.types.AlertClass;
import com.oureawx.ensembleweather.types.ExploreMetric;
import com.oureawx.ensembleweather.types.JsonValue;
import com.oureawx.ensembleweather.types.LocationType;
import com.oureawx.ensembleweather.types.Operator;
import com.oureawx.ensembleweather.types.UnitSystem;
import com.oureawx.ensembleweather.types.Variable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

/**
 * Runs every shared vector in spec/vectors (BEHAVIOR.md section 13) through the public API and a
 * mock transport. The argument mapping in {@link #invoke} is the only per-language code. A vector
 * this runner cannot run fails; nothing is skipped.
 */
class VectorsTest {
  private static final Path VECTORS = TestClients.specDir().resolve("vectors");

  static List<Path> vectorFiles() throws IOException {
    try (Stream<Path> files = Files.walk(VECTORS)) {
      return files
          .filter(p -> p.toString().endsWith(".json"))
          .filter(p -> !VECTORS.relativize(p).startsWith("bodies"))
          .sorted()
          .collect(Collectors.toList());
    }
  }

  static JsonValue read(Path p) throws IOException {
    return Json.parse(Files.readAllBytes(p));
  }

  @Test
  void findsAll58Vectors() throws IOException {
    assertEquals(58, vectorFiles().size());
  }

  @TestFactory
  Stream<DynamicTest> vectors() throws IOException {
    return vectorFiles().stream()
        .map(
            p ->
                DynamicTest.dynamicTest(
                    VECTORS.relativize(p).toString().replace('\\', '/'), () -> run(read(p))));
  }

  // ------------------------------------------------------------------ helpers over JsonValue

  private static String str(JsonValue v, String name) {
    return v.get(name).flatMap(JsonValue::asString).orElseThrow();
  }

  private static Optional<JsonValue> opt(JsonValue v, String name) {
    return v.get(name).filter(x -> !x.isNull());
  }

  private static int integer(JsonValue v) {
    return v.asNumber().orElseThrow().intValueExact();
  }

  private static double number(JsonValue v) {
    return v.asNumber().orElseThrow().doubleValue();
  }

  // ------------------------------------------------------------------ the run

  private static void run(JsonValue vector) throws Exception {
    JsonValue clientConfig = vector.get("client").orElse(JsonValue.ofObject(Map.of()));
    Map<String, String> env = new LinkedHashMap<>();
    opt(clientConfig, "env")
        .ifPresent(e -> e.asObject().forEach((k, v) -> env.put(k, v.asString().orElseThrow())));
    TestClients t = new TestClients();
    for (JsonValue r : vector.get("responses").orElseThrow().asArray()) {
      Map<String, String> headers = new LinkedHashMap<>();
      opt(r, "headers")
          .ifPresent(
              h -> h.asObject().forEach((k, v) -> headers.put(k, v.asString().orElseThrow())));
      byte[] body;
      if (opt(r, "body_file").isPresent()) {
        body = Files.readAllBytes(VECTORS.resolve(str(r, "body_file")));
      } else {
        body =
            opt(r, "body").flatMap(JsonValue::asString).orElse("").getBytes(StandardCharsets.UTF_8);
      }
      t.transport.respond(integer(r.get("status").orElseThrow()), headers, body);
    }
    Client.Builder b = t.builder(env);
    opt(clientConfig, "api_key").flatMap(JsonValue::asString).ifPresent(b::apiKey);
    opt(clientConfig, "base_url").flatMap(JsonValue::asString).ifPresent(b::baseUrl);
    opt(clientConfig, "max_retries").ifPresent(v -> b.maxRetries(integer(v)));
    opt(clientConfig, "cache")
        .flatMap(JsonValue::asBoolean)
        .ifPresent(
            on -> {
              if (!on) {
                b.disableCache();
              }
            });
    opt(clientConfig, "send_key_on_open_routes")
        .flatMap(JsonValue::asBoolean)
        .ifPresent(b::sendKeyOnOpenRoutes);
    Client client = b.build();

    List<JsonValue> calls = new ArrayList<>();
    opt(vector, "call").ifPresent(calls::add);
    opt(vector, "calls").ifPresent(c -> calls.addAll(c.asArray()));
    List<Object> results = new ArrayList<>();
    Throwable error = null;
    for (JsonValue call : calls) {
      Client target =
          opt(call, "with_api_key")
              .flatMap(JsonValue::asString)
              .map(client::withApiKey)
              .orElse(client);
      try {
        results.add(invoke(target, call));
      } catch (OureaException e) {
        error = e;
        break;
      }
    }

    JsonValue expect = vector.get("expect").orElseThrow();
    Optional<JsonValue> wantError = opt(expect, "error");
    if (wantError.isPresent()) {
      assertNotNull(error, "an error was expected");
      checkError((OureaException) error, wantError.get());
    } else if (error != null) {
      throw new AssertionError("no error was expected", error);
    }

    List<TransportRequest> sent = t.transport.sent;
    Optional<JsonValue> one = vector.get("expect_request");
    if (one.isPresent()) {
      if (one.get().isNull()) {
        assertEquals(0, sent.size(), "nothing may be sent");
      } else {
        assertFalse(sent.isEmpty(), "the request was not sent");
        checkRequest(sent.get(0), one.get(), "request");
      }
    }
    List<JsonValue> many = opt(vector, "expect_requests").map(JsonValue::asArray).orElse(List.of());
    for (int i = 0; i < many.size(); i++) {
      assertTrue(i < sent.size(), "request " + i + " was not sent");
      checkRequest(sent.get(i), many.get(i), "request " + i);
    }
    Optional<JsonValue> count = opt(expect, "requests");
    if (count.isPresent()) {
      assertEquals(integer(count.get()), sent.size(), "requests sent");
    }
    Optional<JsonValue> delays = opt(expect, "delays_s");
    if (delays.isPresent()) {
      List<Double> want = new ArrayList<>();
      for (JsonValue d : delays.get().asArray()) {
        want.add(number(d));
      }
      assertEquals(want, t.delays, "retry delays (s)");
    }
    Optional<JsonValue> result = opt(expect, "result");
    if (result.isPresent()) {
      assertFalse(results.isEmpty(), "no result");
      checkResult(results.get(0), result.get());
    }
    List<JsonValue> resultsWanted =
        opt(expect, "results").map(JsonValue::asArray).orElse(List.of());
    for (int i = 0; i < resultsWanted.size(); i++) {
      checkResult(results.get(i), resultsWanted.get(i));
    }
    Optional<JsonValue> items = opt(expect, "items");
    if (items.isPresent()) {
      List<String> want = new ArrayList<>();
      for (JsonValue v : items.get().asArray()) {
        want.add(v.asString().orElseThrow());
      }
      assertEquals(want, results.get(0), "iterated items");
    }
    Optional<JsonValue> warnings = opt(expect, "warnings");
    if (warnings.isPresent()) {
      List<JsonValue> want = warnings.get().asArray();
      assertEquals(want.size(), t.warnings.size(), "warnings: " + t.warnings);
      for (int i = 0; i < want.size(); i++) {
        String contains = str(want.get(i), "contains");
        assertTrue(t.warnings.get(i).contains(contains), t.warnings.get(i) + " lacks " + contains);
      }
    }
  }

  // ------------------------------------------------------------------ the argument mapping

  private static LocationId location(JsonValue args) {
    return opt(args, "location").isPresent()
        ? LocationId.parse(str(args, "location"))
        : LocationId.of(str(args, "state"), str(args, "slug"));
  }

  private static String[] strings(JsonValue v) {
    List<String> out = new ArrayList<>();
    for (JsonValue s : v.asArray()) {
      out.add(s.asString().orElseThrow());
    }
    return out.toArray(new String[0]);
  }

  private static UnitSystem units(JsonValue v) {
    return UnitSystem.fromWireValue(v.asString().orElseThrow());
  }

  private static void onlyArgs(JsonValue args, String... allowed) {
    for (String k : args.asObject().keySet()) {
      if (!List.of(allowed).contains(k)) {
        throw new AssertionError("the vectors runner has no mapping for argument " + k);
      }
    }
  }

  private static Object invoke(Client client, JsonValue call) {
    String operation = str(call, "operation");
    JsonValue args = call.get("args").orElseThrow();
    switch (operation) {
      case "getLocationForecast":
        return client.getForecast(location(args), forecastOptions(args));
      case "getPointForecast":
        {
          double lat = number(args.get("lat").orElseThrow());
          double lon = number(args.get("lon").orElseThrow());
          Coordinates at =
              opt(args, "elevation_m").isPresent()
                  ? Coordinates.of(lat, lon, number(args.get("elevation_m").orElseThrow()))
                  : Coordinates.of(lat, lon);
          return client.getPointForecast(at, forecastOptions(args));
        }
      case "getCurrentConditions":
        onlyArgs(args, "location", "state", "slug");
        return client.getCurrent(location(args));
      case "getLocationAlerts":
        onlyArgs(args, "location", "state", "slug");
        return client.getAlerts(location(args));
      case "getLocations":
        {
          onlyArgs(args, "state", "limit");
          LocationListOptions.Builder o = LocationListOptions.builder();
          opt(args, "limit").ifPresent(v -> o.limit(integer(v)));
          return client.listLocations(str(args, "state"), o.build());
        }
      case "getNearbyLocations":
        onlyArgs(args, "location", "state", "slug");
        return client.getNearby(location(args));
      case "getNearbyByCoordinates":
        onlyArgs(args, "lat", "lon");
        return client.getPointNearby(
            Coordinates.of(
                number(args.get("lat").orElseThrow()), number(args.get("lon").orElseThrow())));
      case "getAvailableRuns":
        onlyArgs(args, "limit");
        return client.getRuns(integer(args.get("limit").orElseThrow()));
      case "searchLocations":
        return client.searchLocations(str(args, "q"), searchOptions(args));
      case "getCatalogLocation":
        {
          onlyArgs(args, "slug", "include");
          CatalogDetailOptions.Builder o = CatalogDetailOptions.builder();
          opt(args, "include")
              .ifPresent(
                  v -> {
                    List<CatalogInclude> inc = new ArrayList<>();
                    for (String s : strings(v)) {
                      inc.add(CatalogInclude.valueOf(s.toUpperCase(Locale.ROOT)));
                    }
                    o.include(inc.toArray(new CatalogInclude[0]));
                  });
          return client.getCatalogLocation(str(args, "slug"), o.build());
        }
      case "getCatalogStation":
        onlyArgs(args, "slug");
        return client.getCatalogStation(str(args, "slug"));
      case "getAttribution":
        return client.getAttribution();
      case "listCatalogLocations":
        if (opt(args, "iterate").flatMap(JsonValue::asBoolean).orElse(false)) {
          List<String> slugs = new ArrayList<>();
          client
              .iterateCatalogLocations(CatalogLocationQuery.all())
              .forEach(l -> slugs.add(l.slug()));
          return slugs;
        }
        return client.listCatalogLocations(CatalogLocationQuery.all());
      case "explore":
        return client.explore(
            ExploreMetric.fromWireValue(str(args, "metric")), exploreOptions(args));
      case "getComposition":
        onlyArgs(args);
        return client.getComposition();
      case "getLocationSeason":
        {
          onlyArgs(args, "location", "state", "slug", "water_year", "include", "units");
          SeasonOptions.Builder o = SeasonOptions.builder();
          opt(args, "water_year").ifPresent(v -> o.waterYear(integer(v)));
          opt(args, "include").ifPresent(v -> o.daily(List.of(strings(v)).contains("daily")));
          opt(args, "units").ifPresent(v -> o.units(units(v)));
          return client.getSeason(location(args), o.build());
        }
      case "getLocationSnowTrend":
        {
          onlyArgs(args, "location", "state", "slug", "elevation", "units");
          SnowTrendOptions.Builder o = SnowTrendOptions.builder();
          opt(args, "elevation").ifPresent(v -> o.elevation(v.asString().orElseThrow()));
          opt(args, "units").ifPresent(v -> o.units(units(v)));
          return client.getSnowTrend(location(args), o.build());
        }
      case "getLocationSnowpack":
        onlyArgs(args, "location", "state", "slug", "units");
        return opt(args, "units").isPresent()
            ? client.getSnowpack(location(args), units(args.get("units").orElseThrow()))
            : client.getSnowpack(location(args));
      case "getLocationSnowpackPlume":
        onlyArgs(args, "location", "state", "slug", "units");
        return opt(args, "units").isPresent()
            ? client.getSnowpackPlume(location(args), units(args.get("units").orElseThrow()))
            : client.getSnowpackPlume(location(args));
      case "getAlertOutlines":
        {
          onlyArgs(args, "classes", "bbox");
          AlertOutlineOptions.Builder o = AlertOutlineOptions.builder();
          opt(args, "classes")
              .ifPresent(
                  v -> {
                    List<AlertClass> cs = new ArrayList<>();
                    for (String s : strings(v)) {
                      cs.add(AlertClass.fromWireValue(s));
                    }
                    o.classes(cs.toArray(new AlertClass[0]));
                  });
          opt(args, "bbox")
              .ifPresent(
                  b ->
                      o.bbox(
                          number(b.get("west").orElseThrow()),
                          number(b.get("south").orElseThrow()),
                          number(b.get("east").orElseThrow()),
                          number(b.get("north").orElseThrow())));
          return client.getAlertOutlines(o.build());
        }
      case "getAvalancheZones":
        onlyArgs(args);
        return client.getAvalancheZones();
      default:
        throw new AssertionError("the vectors runner has no mapping for " + operation);
    }
  }

  private static SearchOptions searchOptions(JsonValue args) {
    SearchOptions.Builder o = SearchOptions.builder();
    for (Map.Entry<String, JsonValue> e : args.asObject().entrySet()) {
      JsonValue v = e.getValue();
      switch (e.getKey()) {
        case "q":
          break;
        case "sort":
          if (!"relevance".equals(v.asString().orElseThrow())) {
            o.sort(ExploreMetric.fromWireValue(v.asString().orElseThrow()));
          }
          break;
        case "order":
          o.order(SortOrder.valueOf(v.asString().orElseThrow().toUpperCase(Locale.ROOT)));
          break;
        case "near":
          o.near(number(v.get("lat").orElseThrow()), number(v.get("lon").orElseThrow()));
          break;
        case "radius_km":
          o.radiusKm(number(v));
          break;
        case "units":
          o.units(units(v));
          break;
        case "state":
          o.state(v.asString().orElseThrow());
          break;
        case "limit":
          o.limit(integer(v));
          break;
        default:
          throw new AssertionError("the vectors runner has no mapping for argument " + e.getKey());
      }
    }
    return o.build();
  }

  private static ExploreOptions exploreOptions(JsonValue args) {
    ExploreOptions.Builder o = ExploreOptions.builder();
    for (Map.Entry<String, JsonValue> e : args.asObject().entrySet()) {
      JsonValue v = e.getValue();
      switch (e.getKey()) {
        case "metric":
          break;
        case "order":
          o.order(SortOrder.valueOf(v.asString().orElseThrow().toUpperCase(Locale.ROOT)));
          break;
        case "type":
          o.type(LocationType.fromWireValue(v.asString().orElseThrow()));
          break;
        case "state":
          o.state(v.asString().orElseThrow());
          break;
        case "near":
          o.near(number(v.get("lat").orElseThrow()), number(v.get("lon").orElseThrow()));
          break;
        case "radius_km":
          o.radiusKm(number(v));
          break;
        case "elevation_min":
          o.elevationMin(number(v));
          break;
        case "elevation_max":
          o.elevationMax(number(v));
          break;
        case "points":
          o.allPoints("all".equals(v.asString().orElseThrow()));
          break;
        case "limit":
          o.limit(integer(v));
          break;
        case "cursor":
          o.cursor(v.asString().orElseThrow());
          break;
        case "units":
          o.units(units(v));
          break;
        default:
          throw new AssertionError("the vectors runner has no mapping for argument " + e.getKey());
      }
    }
    return o.build();
  }

  private static ForecastOptions forecastOptions(JsonValue args) {
    ForecastOptions.Builder o = ForecastOptions.builder();
    for (Map.Entry<String, JsonValue> e : args.asObject().entrySet()) {
      JsonValue v = e.getValue();
      switch (e.getKey()) {
        case "state":
        case "slug":
        case "location":
        case "lat":
        case "lon":
        case "elevation_m":
          break;
        case "units":
          o.units(UnitSystem.fromWireValue(v.asString().orElseThrow()));
          break;
        case "max_hours":
          o.maxHours(integer(v));
          break;
        case "members":
          if (v.kind() == JsonValue.Kind.STRING) {
            o.members(v.asString().orElseThrow());
          } else {
            o.members(v.asBoolean().orElseThrow());
          }
          break;
        case "elevations":
          if (v.kind() == JsonValue.Kind.STRING && "all".equals(v.asString().orElseThrow())) {
            o.allElevations();
          } else {
            o.elevations(strings(v));
          }
          break;
        case "sources":
          o.sources(strings(v));
          break;
        case "exclude_sources":
          o.excludeSources(strings(v));
          break;
        case "include":
          {
            List<ForecastInclude> inc = new ArrayList<>();
            for (String s : strings(v)) {
              inc.add(ForecastInclude.valueOf(s.toUpperCase(Locale.ROOT)));
            }
            o.include(inc.toArray(new ForecastInclude[0]));
            break;
          }
        case "models":
          o.models(v.asBoolean().orElseThrow());
          break;
        case "nowcast":
          o.nowcast(v.asBoolean().orElseThrow());
          break;
        case "include_past":
          o.includePast(v.asBoolean().orElseThrow());
          break;
        case "daily":
          o.daily(v.asBoolean().orElseThrow());
          break;
        case "hourly":
          o.hourly(v.asBoolean().orElseThrow());
          break;
        case "prob":
          for (JsonValue term : v.asArray()) {
            o.probability(term(term));
          }
          break;
        case "totals":
          for (JsonValue term : v.asArray()) {
            o.total(
                TotalTerm.of(
                    Variable.fromWireValue(str(term, "variable")),
                    window(term.get("window").orElseThrow())));
          }
          break;
        default:
          throw new AssertionError("the vectors runner has no mapping for argument " + e.getKey());
      }
    }
    return o.build();
  }

  private static ProbabilityTerm term(JsonValue term) {
    Variable variable = Variable.fromWireValue(str(term, "variable"));
    JsonValue t = term.get("threshold").orElseThrow();
    Threshold threshold = threshold(number(t.get("value").orElseThrow()), str(t, "unit"));
    Operator op = Operator.fromWireValue(str(term, "op"));
    ProbabilityTerm p =
        op == Operator.GE
            ? ProbabilityTerm.atLeast(variable, threshold)
            : ProbabilityTerm.atMost(variable, threshold);
    if (opt(term, "window").isPresent()) {
      p = p.over(window(term.get("window").orElseThrow()));
    }
    if (opt(term, "aggregation").isPresent()) {
      p = p.withAggregation(Aggregation.fromWireValue(str(term, "aggregation")));
    }
    return p;
  }

  private static Threshold threshold(double value, String unit) {
    switch (unit) {
      case "mm":
        return Threshold.millimetres(value);
      case "in":
        return Threshold.inches(value);
      case "degC":
        return Threshold.celsius(value);
      case "degF":
        return Threshold.fahrenheit(value);
      case "K":
        return Threshold.kelvin(value);
      case "m":
        return Threshold.metres(value);
      case "ft":
        return Threshold.feet(value);
      case "m/s":
        return Threshold.metresPerSecond(value);
      case "mph":
        return Threshold.milesPerHour(value);
      default:
        throw new AssertionError("unknown threshold unit " + unit);
    }
  }

  private static Window window(JsonValue w) {
    if (opt(w, "hours").isPresent()) {
      return Window.hours(integer(w.get("hours").orElseThrow()));
    }
    return Window.between(
        integer(w.get("start_hours").orElseThrow()), integer(w.get("end_hours").orElseThrow()));
  }

  // ------------------------------------------------------------------ checks

  private static void checkError(OureaException e, JsonValue want) {
    opt(want, "category")
        .ifPresent(
            c ->
                assertEquals(
                    c.asString().orElseThrow(), e.category().name().toLowerCase(Locale.ROOT)));
    Optional<JsonValue> code = want.get("code");
    if (code.isPresent()) {
      assertEquals(code.get().asString(), e.code(), "code");
    }
    opt(want, "status").ifPresent(s -> assertEquals(OptionalInt.of(integer(s)), e.status()));
    opt(want, "retry_after")
        .ifPresent(
            r -> {
              RateLimitException rl = assertInstanceOf(RateLimitException.class, e);
              assertEquals((long) integer(r), rl.retryAfter().orElseThrow().toSeconds());
            });
    opt(want, "string_excludes")
        .flatMap(JsonValue::asString)
        .ifPresent(
            secret -> {
              StringWriter trace = new StringWriter();
              e.printStackTrace(new PrintWriter(trace));
              for (String text :
                  List.of(String.valueOf(e.getMessage()), e.toString(), trace.toString())) {
                assertFalse(text.contains(secret), "leaked in: " + text);
              }
            });
  }

  private static Map<String, String> decodedQuery(URI uri) {
    Map<String, String> out = new LinkedHashMap<>();
    String raw = uri.getRawQuery();
    if (raw == null || raw.isEmpty()) {
      return out;
    }
    for (String pair : raw.split("&", -1)) {
      int eq = pair.indexOf('=');
      String k = eq < 0 ? pair : pair.substring(0, eq);
      String v = eq < 0 ? "" : pair.substring(eq + 1);
      out.put(
          URLDecoder.decode(k, StandardCharsets.UTF_8),
          URLDecoder.decode(v.replace("+", "%2B"), StandardCharsets.UTF_8));
    }
    return out;
  }

  private static String header(TransportRequest r, String name) {
    for (Map.Entry<String, String> h : r.headers().entrySet()) {
      if (h.getKey().equalsIgnoreCase(name)) {
        return h.getValue();
      }
    }
    return null;
  }

  private static void checkRequest(TransportRequest sent, JsonValue want, String label) {
    URI uri = sent.uri();
    opt(want, "method").ifPresent(m -> assertEquals(m.asString().orElseThrow(), "GET", label));
    opt(want, "path")
        .ifPresent(
            p -> assertEquals(p.asString().orElseThrow(), uri.getRawPath(), label + " path"));
    opt(want, "url_prefix")
        .ifPresent(
            p ->
                assertTrue(
                    uri.toString().startsWith(p.asString().orElseThrow()), label + " url " + uri));
    opt(want, "query_string")
        .ifPresent(
            q ->
                assertEquals(
                    q.asString().orElseThrow(),
                    uri.getRawQuery() == null ? "" : uri.getRawQuery(),
                    label + " query string"));
    Map<String, String> query = decodedQuery(uri);
    opt(want, "query")
        .ifPresent(
            q ->
                q.asObject()
                    .forEach(
                        (k, v) ->
                            assertEquals(
                                v.asString().orElseThrow(), query.get(k), label + " query " + k)));
    opt(want, "query_absent")
        .ifPresent(
            q -> {
              for (JsonValue k : q.asArray()) {
                assertFalse(query.containsKey(k.asString().orElseThrow()), label + " has " + k);
              }
            });
    opt(want, "headers")
        .ifPresent(
            h ->
                h.asObject()
                    .forEach(
                        (k, v) ->
                            assertEquals(
                                v.asString().orElseThrow(),
                                header(sent, k),
                                label + " header " + k)));
    opt(want, "headers_absent")
        .ifPresent(
            h -> {
              for (JsonValue k : h.asArray()) {
                assertNull(header(sent, k.asString().orElseThrow()), label + " header " + k);
              }
            });
    opt(want, "headers_match")
        .ifPresent(
            h ->
                h.asObject()
                    .forEach(
                        (k, v) -> {
                          String got = header(sent, k);
                          assertNotNull(got, label + " header " + k);
                          assertTrue(
                              Pattern.compile(v.asString().orElseThrow()).matcher(got).find(),
                              label + " header " + k + ": " + got);
                        }));
  }

  private static final Pattern SEGMENT = Pattern.compile("([a-z0-9_]+)((?:\\[\\d+\\])*)");
  private static final Pattern INDEX = Pattern.compile("\\[(\\d+)\\]");

  /** Java's names for wire words it reserves (BEHAVIOR.md 3.0: the language's idiom wins). */
  private static final Map<String, String> RESERVED =
      Map.of("default", "isDefault", "short", "shortText", "long", "longText");

  private static String camel(String snake) {
    if (RESERVED.containsKey(snake)) {
      return RESERVED.get(snake);
    }
    StringBuilder out = new StringBuilder();
    boolean up = false;
    for (int i = 0; i < snake.length(); i++) {
      char c = snake.charAt(i);
      if (c == '_') {
        up = true;
      } else {
        out.append(up ? Character.toUpperCase(c) : c);
        up = false;
      }
    }
    return out.toString();
  }

  /** Unwraps Optional and its primitive cousins; empty becomes null. */
  private static Object unwrap(Object v) {
    if (v instanceof Optional) {
      return ((Optional<?>) v).orElse(null);
    }
    if (v instanceof OptionalDouble) {
      OptionalDouble d = (OptionalDouble) v;
      return d.isPresent() ? d.getAsDouble() : null;
    }
    if (v instanceof OptionalInt) {
      OptionalInt d = (OptionalInt) v;
      return d.isPresent() ? d.getAsInt() : null;
    }
    if (v instanceof OptionalLong) {
      OptionalLong d = (OptionalLong) v;
      return d.isPresent() ? d.getAsLong() : null;
    }
    return v;
  }

  private static Object call(Object target, String method) {
    try {
      Method m = target.getClass().getMethod(method);
      return unwrap(m.invoke(target));
    } catch (NoSuchMethodException e) {
      throw new AssertionError(target.getClass().getSimpleName() + " has no " + method + "()", e);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new LinkageError(e.getMessage(), e);
    }
  }

  /** One step into a GeoJSON body, which is passed through untouched: wire keys, raw JSON. */
  private static @org.jspecify.annotations.Nullable Object member(JsonValue json, String key) {
    return json.get(key).filter(v -> !v.isNull()).orElse(null);
  }

  /** Reads a vector path ({@code probabilities[0].window_start_hours}) over the model. */
  private static Object path(Object result, String path, boolean raw) {
    Object current = result;
    String[] segments = path.split("\\.", -1);
    int from = 0;
    if (segments[0].equals("revalidated")) {
      return ((com.oureawx.ensembleweather.types.ResponseMetadata) call(current, "metadata"))
          .revalidated();
    }
    if (segments[0].equals("rate_limit")) {
      current =
          ((com.oureawx.ensembleweather.types.ResponseMetadata) call(current, "metadata"))
              .rateLimit()
              .orElse(null);
      from = 1;
    }
    for (int i = from; i < segments.length && current != null; i++) {
      Matcher m = SEGMENT.matcher(segments[i]);
      if (!m.matches()) {
        throw new AssertionError("bad path " + path);
      }
      boolean last = i == segments.length - 1 && m.group(2).isEmpty();
      if (current instanceof JsonValue) {
        current = member((JsonValue) current, m.group(1));
      } else {
        current = call(current, camel(m.group(1)) + (raw && last ? "AsString" : ""));
      }
      Matcher idx = INDEX.matcher(m.group(2));
      while (idx.find() && current != null) {
        int n = Integer.parseInt(idx.group(1));
        List<?> list =
            current instanceof JsonValue ? ((JsonValue) current).asArray() : (List<?>) current;
        current = n < list.size() ? unwrap(list.get(n)) : null;
        if (current instanceof JsonValue && ((JsonValue) current).isNull()) {
          current = null;
        }
      }
    }
    return plain(current);
  }

  /** A raw JSON leaf as the Java value a model would hold. */
  private static Object plain(Object v) {
    if (!(v instanceof JsonValue)) {
      return v;
    }
    JsonValue j = (JsonValue) v;
    switch (j.kind()) {
      case NULL:
        return null;
      case STRING:
        return j.asString().orElseThrow();
      case NUMBER:
        return j.asNumber().orElseThrow();
      case BOOLEAN:
        return j.asBoolean().orElseThrow();
      default:
        return j;
    }
  }

  private static void checkResult(Object result, JsonValue want) {
    for (Map.Entry<String, JsonValue> e : want.asObject().entrySet()) {
      String p = e.getKey();
      JsonValue v = e.getValue();
      if (v.isNull()) {
        assertNull(path(result, p, false), p + " should be absent");
      } else if (v.kind() == JsonValue.Kind.OBJECT && v.get("unknown").isPresent()) {
        Object known = path(result, p, false);
        assertTrue(
            known instanceof Enum && ((Enum<?>) known).name().equals("UNKNOWN"), p + ": " + known);
        assertEquals(str(v, "unknown"), path(result, p, true), p + " raw");
      } else if (v.kind() == JsonValue.Kind.NUMBER) {
        Object got = path(result, p, false);
        assertNotNull(got, p);
        assertEquals(
            0,
            v.asNumber().orElseThrow().compareTo(new BigDecimal(got.toString())),
            p + ": want " + v + ", got " + got);
      } else if (v.kind() == JsonValue.Kind.BOOLEAN) {
        assertEquals(v.asBoolean().orElseThrow(), path(result, p, false), p);
      } else if (v.kind() == JsonValue.Kind.STRING) {
        Object got = path(result, p, false);
        if (got instanceof Enum) {
          got = path(result, p, true);
        }
        if (got instanceof Instant) {
          // A timestamp expected as a string matches as an instant.
          assertEquals(
              OffsetDateTime.parse(v.asString().orElseThrow()).toInstant(), got, p + " (instant)");
        } else {
          assertEquals(v.asString().orElseThrow(), String.valueOf(got), p);
        }
      } else {
        fail("unsupported expectation at " + p + ": " + v);
      }
    }
  }
}
