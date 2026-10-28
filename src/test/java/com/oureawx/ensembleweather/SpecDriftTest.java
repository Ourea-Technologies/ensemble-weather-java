// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.oureawx.ensembleweather.internal.Decoders;
import com.oureawx.ensembleweather.internal.JsonFields;
import com.oureawx.ensembleweather.internal.MalformedResponseException;
import com.oureawx.ensembleweather.internal.TransportRequest;
import com.oureawx.ensembleweather.model.JsonValue;
import com.oureawx.ensembleweather.model.LocationType;
import com.oureawx.ensembleweather.model.UnitSystem;
import com.oureawx.ensembleweather.model.Variable;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;

/**
 * Spec drift (SDK-STANDARD.md "Tests"): every operation in the pinned spec is exposed, each sends
 * only parameters its route defines, every schema property has its accessor, and the models decode
 * every response example in the spec, reading every field.
 */
class SpecDriftTest {
  private static final Map<String, Object> SPEC = loadSpec();

  @SuppressWarnings("unchecked")
  private static Map<String, Object> loadSpec() {
    try (InputStream in =
        Files.newInputStream(TestClients.specDir().resolve("openapi/v1/openapi.yaml"))) {
      LoadSettings settings = LoadSettings.builder().setCodePointLimit(16 * 1024 * 1024).build();
      return (Map<String, Object>) new Load(settings).loadFromInputStream(in);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  @SuppressWarnings("unchecked")
  private static Map<String, Object> map(Object o, String... keys) {
    Object cur = o;
    for (String k : keys) {
      cur = cur == null ? null : ((Map<String, Object>) cur).get(k);
    }
    return cur == null ? Map.of() : (Map<String, Object>) cur;
  }

  @SuppressWarnings("unchecked")
  private static List<Object> list(Object o) {
    return o == null ? List.of() : (List<Object>) o;
  }

  private static String refName(Object ref) {
    String s = String.valueOf(ref);
    return s.substring(s.lastIndexOf('/') + 1);
  }

  /** operationId -> the operation object. */
  private static Map<String, Map<String, Object>> operations() {
    Map<String, Map<String, Object>> ops = new TreeMap<>();
    for (Object item : map(SPEC, "paths").values()) {
      for (Object op : map(item).values()) {
        Map<String, Object> o = map(op);
        if (o.containsKey("operationId")) {
          ops.put(String.valueOf(o.get("operationId")), o);
        }
      }
    }
    return ops;
  }

  private static final Set<String> OUT_OF_SCOPE = Set.of("getMapsToken");

  /** How the SDK calls each operation, with every option it has set. */
  private static final Map<String, Consumer<EnsembleWeatherClient>> CALLS = new TreeMap<>();

  static {
    ForecastOptions all =
        ForecastOptions.builder()
            .units(UnitSystem.IMPERIAL)
            .maxHours(48)
            .includePast(true)
            .models(true)
            .members(true)
            .daily(false)
            .maxDays(3)
            .hourly(false)
            .run(Instant.parse("2026-11-15T00:00:00Z"))
            .sources("snotel")
            .excludeSources("raws")
            .nowcast(false)
            .probability(ProbabilityTerm.atLeast(Variable.SNOWFALL, Threshold.of(1)))
            .total(TotalTerm.of(Variable.SNOWFALL))
            .diagnostics(Diagnostic.PROVENANCE)
            .include(ForecastInclude.SKIN_TEMPERATURE, ForecastInclude.MSLP)
            .build();
    ForecastOptions withElevations = ForecastOptions.builder().allElevations().build();
    CurrentOptions current =
        CurrentOptions.builder()
            .units(UnitSystem.KELVIN)
            .sources("a")
            .excludeSources("b")
            .includeNowcastHours(true)
            .build();
    NearbyOptions nearby =
        NearbyOptions.builder().units(UnitSystem.IMPERIAL).limit(5).radiusKm(10).build();
    LocationListOptions list =
        LocationListOptions.builder()
            .type(LocationType.SKI_AREA)
            .units(UnitSystem.IMPERIAL)
            .limit(5)
            .cursor("c")
            .build();
    CatalogDetailOptions detail =
        CatalogDetailOptions.builder()
            .include(CatalogInclude.CONTENT, CatalogInclude.TEMPLATE)
            .locale("es")
            .build();
    LocationId x = LocationId.of("or", "x");
    Coordinates point = Coordinates.of(45.3, -121.7, 1600);
    CALLS.put(
        "getLocationForecast",
        c -> {
          c.getForecast(x, all);
          c.getForecast(x, withElevations);
        });
    CALLS.put("getPointForecast", c -> c.getPointForecast(point, all));
    CALLS.put("getCurrentConditions", c -> c.getCurrent(x, current));
    CALLS.put("getCurrentByCoordinates", c -> c.getCurrent(point, current));
    CALLS.put("getLocationAlerts", c -> c.getAlerts(x));
    CALLS.put("getAlertsByCoordinates", c -> c.getAlerts(Coordinates.of(45.3, -121.7)));
    CALLS.put("getNearbyLocations", c -> c.getNearby(x, nearby));
    CALLS.put("getNearbyByCoordinates", c -> c.getNearby(Coordinates.of(45.3, -121.7), nearby));
    CALLS.put(
        "searchLocations",
        c ->
            c.searchLocations(
                "hood",
                SearchOptions.builder()
                    .state("or")
                    .county("Hood River")
                    .type(LocationType.SKI_AREA)
                    .limit(5)
                    .build()));
    CALLS.put("getAllLocations", c -> c.listLocations(list));
    CALLS.put("getLocations", c -> c.listLocations("or", list));
    CALLS.put(
        "listCatalogLocations",
        c ->
            c.listCatalogLocations(
                CatalogLocationQuery.builder()
                    .admin1("US-OR")
                    .country("US")
                    .type("ski_area")
                    .template("mountain")
                    .limit(5)
                    .cursor("c")
                    .build()));
    CALLS.put("getCatalogLocation", c -> c.getCatalogLocation("x", detail));
    CALLS.put(
        "listCatalogStations",
        c ->
            c.listCatalogStations(
                CatalogStationQuery.builder()
                    .admin1("US-OR")
                    .country("US")
                    .networks("snotel", "raws")
                    .capabilities("swe", "air_temp")
                    .status("active")
                    .limit(5)
                    .cursor("c")
                    .build()));
    CALLS.put("getCatalogStation", c -> c.getCatalogStation("x", detail));
    CALLS.put("getAvailableRuns", c -> c.getRuns(5, "c"));
    CALLS.put("getAttribution", EnsembleWeatherClient::getAttribution);
  }

  @Test
  void exposesEveryOperation() {
    Set<String> spec = new TreeSet<>(operations().keySet());
    spec.removeAll(OUT_OF_SCOPE);
    assertEquals(spec, CALLS.keySet(), "the operations the SDK exposes vs the spec");
  }

  private static Set<String> queryParameters(Map<String, Object> op) {
    Set<String> names = new TreeSet<>();
    for (Object p : list(op.get("parameters"))) {
      Map<String, Object> param = map(p);
      if (param.containsKey("$ref")) {
        param = map(SPEC, "components", "parameters", refName(param.get("$ref")));
      }
      if ("query".equals(param.get("in"))) {
        names.add(String.valueOf(param.get("name")));
      }
    }
    return names;
  }

  @TestFactory
  Stream<DynamicTest> eachOperationSendsOnlyItsParameters() {
    List<DynamicTest> tests = new ArrayList<>();
    for (Map.Entry<String, Consumer<EnsembleWeatherClient>> e : CALLS.entrySet()) {
      tests.add(
          DynamicTest.dynamicTest(
              e.getKey(),
              () -> {
                Set<String> allowed = queryParameters(operations().get(e.getKey()));
                TestClients t = new TestClients();
                for (int i = 0; i < 4; i++) {
                  t.transport.json(200, "{}");
                }
                try {
                  e.getValue().accept(t.builder().apiKey("ow_k").disableCache().build());
                } catch (UnavailableException expected) {
                  // "{}" is not a valid body; only the request matters here.
                }
                assertTrue(!t.transport.sent.isEmpty(), "nothing was sent");
                for (TransportRequest r : t.transport.sent) {
                  String q = r.uri().getRawQuery();
                  if (q == null) {
                    continue;
                  }
                  for (String pair : q.split("&", -1)) {
                    String name = pair.substring(0, pair.indexOf('='));
                    assertTrue(allowed.contains(name), e.getKey() + " sent " + name);
                  }
                }
              }));
    }
    return tests.stream();
  }

  // ------------------------------------------------------------------ schemas

  private static final Map<String, String> INLINE =
      Map.of(
          "LocationSummary.elevations", "LocationElevation",
          "CurrentConditions.station", "CurrentStation",
          "TimeSeriesPoint.members", "ModelMembers",
          "RunsResponse.served", "ServedRun",
          "RunsResponse.runs", "RunInfo");

  private static final Set<String> NOT_MODELS =
      Set.of("ErrorResponse", "UnitSystem", "FreezingLevel", "ModelMeans", "NextCursor");

  private static String className(String schema) {
    if (schema.startsWith("FreezingLevel")) {
      return "FreezingLevel";
    }
    if (schema.equals("PublicCatalogLocationPage")) {
      return "CatalogLocationPage";
    }
    if (schema.equals("PublicCatalogStationPage")) {
      return "CatalogStationPage";
    }
    return schema.startsWith("Public") ? schema.substring("Public".length()) : schema;
  }

  static String camel(String snake) {
    if (snake.equals("default") || snake.equals("is_default")) {
      return "isDefault";
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

  private static Map<String, Object> properties(Map<String, Object> schema) {
    Map<String, Object> props = new LinkedHashMap<>();
    for (Object part : list(schema.get("allOf"))) {
      Map<String, Object> p = map(part);
      if (p.containsKey("$ref")) {
        p = map(SPEC, "components", "schemas", refName(p.get("$ref")));
      }
      props.putAll(properties(p));
    }
    props.putAll(map(schema, "properties"));
    return props;
  }

  private static void checkProperties(String className, Map<String, Object> props) {
    Class<?> type;
    try {
      type = Class.forName("com.oureawx.ensembleweather.model." + className);
    } catch (ClassNotFoundException e) {
      fail("no model class " + className);
      return;
    }
    for (String prop : props.keySet()) {
      String getter = camel(prop);
      try {
        type.getMethod(getter);
      } catch (NoSuchMethodException e) {
        fail(className + " has no " + getter + "() for the spec's " + prop);
      }
    }
  }

  @TestFactory
  Stream<DynamicTest> everySchemaPropertyHasItsAccessor() {
    List<DynamicTest> tests = new ArrayList<>();
    for (Map.Entry<String, Object> e : map(SPEC, "components", "schemas").entrySet()) {
      String schema = e.getKey();
      if (NOT_MODELS.contains(schema)) {
        continue;
      }
      tests.add(
          DynamicTest.dynamicTest(
              schema,
              () -> {
                Map<String, Object> props = properties(map(e.getValue()));
                checkProperties(className(schema), props);
                for (Map.Entry<String, Object> p : props.entrySet()) {
                  String inline = INLINE.get(schema + "." + p.getKey());
                  if (inline != null) {
                    Map<String, Object> s = map(p.getValue());
                    if (s.containsKey("items")) {
                      s = map(s, "items");
                    }
                    if (s.containsKey("anyOf")) {
                      s = map(list(s.get("anyOf")).get(0));
                    }
                    checkProperties(inline, properties(s));
                  }
                }
              }));
    }
    tests.add(
        DynamicTest.dynamicTest(
            "ModelMeans items",
            () ->
                checkProperties(
                    "ModelStats",
                    properties(map(SPEC, "components", "schemas", "ModelMeans", "items")))));
    return tests.stream();
  }

  // ------------------------------------------------------------------ examples

  private static final Map<String, Function<JsonFields, ?>> DECODERS = new TreeMap<>();

  static {
    DECODERS.put("getLocationForecast", Decoders::forecastResponse);
    DECODERS.put("getPointForecast", Decoders::forecastResponse);
    DECODERS.put("getCurrentConditions", Decoders::currentResponse);
    DECODERS.put("getCurrentByCoordinates", Decoders::currentResponse);
    DECODERS.put("getLocationAlerts", Decoders::alertsResponse);
    DECODERS.put("getAlertsByCoordinates", Decoders::alertsResponse);
    DECODERS.put("getNearbyLocations", Decoders::nearbyLocationsResponse);
    DECODERS.put("getNearbyByCoordinates", Decoders::nearbyPointResponse);
    DECODERS.put("searchLocations", Decoders::searchLocationsResponse);
    DECODERS.put("getAllLocations", Decoders::locationsResponse);
    DECODERS.put("getLocations", Decoders::locationsResponse);
    DECODERS.put("listCatalogLocations", Decoders::catalogLocationPage);
    DECODERS.put("getCatalogLocation", Decoders::catalogLocationDetail);
    DECODERS.put("listCatalogStations", Decoders::catalogStationPage);
    DECODERS.put("getCatalogStation", Decoders::catalogStationDetail);
    DECODERS.put("getAvailableRuns", Decoders::runsResponse);
    DECODERS.put("getAttribution", Decoders::attributionRegistry);
  }

  /**
   * Spec examples that do not match the spec's own schemas, with the reason this SDK reports. Each
   * is a finding against ensemble-weather-spec; the test fails when one starts to decode (so the
   * entry can go) or fails for another reason.
   */
  private static final Map<String, String> STALE_EXAMPLES = Map.of();

  @SuppressWarnings("unchecked")
  static JsonValue toJson(Object yaml) {
    if (yaml == null) {
      return JsonValue.nullValue();
    }
    if (yaml instanceof Map) {
      Map<String, JsonValue> m = new LinkedHashMap<>();
      ((Map<Object, Object>) yaml).forEach((k, v) -> m.put(String.valueOf(k), toJson(v)));
      return JsonValue.ofObject(m);
    }
    if (yaml instanceof List) {
      List<JsonValue> l = new ArrayList<>();
      for (Object v : (List<Object>) yaml) {
        l.add(toJson(v));
      }
      return JsonValue.ofArray(l);
    }
    if (yaml instanceof Boolean) {
      return JsonValue.of((Boolean) yaml);
    }
    if (yaml instanceof Number) {
      return JsonValue.of(new BigDecimal(yaml.toString()));
    }
    return JsonValue.of(String.valueOf(yaml));
  }

  @TestFactory
  Stream<DynamicTest> theModelsDecodeEveryResponseExample() {
    List<DynamicTest> tests = new ArrayList<>();
    int found = 0;
    for (Map.Entry<String, Map<String, Object>> op : operations().entrySet()) {
      if (OUT_OF_SCOPE.contains(op.getKey())) {
        continue;
      }
      for (Map.Entry<String, Object> r : map(op.getValue(), "responses").entrySet()) {
        Map<String, Object> response = map(r.getValue());
        if (response.containsKey("$ref")) {
          response = map(SPEC, "components", "responses", refName(response.get("$ref")));
        }
        Map<String, Object> content = map(response, "content", "application/json");
        Map<String, Object> examples = new LinkedHashMap<>();
        if (content.containsKey("example")) {
          examples.put("example", content.get("example"));
        }
        for (Map.Entry<String, Object> ex : map(content, "examples").entrySet()) {
          examples.put(ex.getKey(), map(ex.getValue()).get("value"));
        }
        boolean ok = r.getKey().startsWith("2");
        for (Map.Entry<String, Object> ex : examples.entrySet()) {
          found++;
          String label = op.getKey() + " " + r.getKey() + " " + ex.getKey();
          JsonValue json = toJson(ex.getValue());
          tests.add(
              DynamicTest.dynamicTest(
                  label,
                  () -> {
                    if (!ok) {
                      assertTrue(json.get("error").flatMap(JsonValue::asString).isPresent(), label);
                      assertTrue(
                          json.get("message").flatMap(JsonValue::asString).isPresent(), label);
                      return;
                    }
                    JsonFields.Tracker tracker = new JsonFields.Tracker();
                    String stale = STALE_EXAMPLES.get(label);
                    try {
                      DECODERS.get(op.getKey()).apply(JsonFields.root(json, tracker));
                    } catch (MalformedResponseException e) {
                      if (stale != null && String.valueOf(e.getMessage()).contains(stale)) {
                        return;
                      }
                      throw new AssertionError(label + ": " + e.getMessage(), e);
                    }
                    if (stale != null) {
                      fail(label + " decodes now: remove it from STALE_EXAMPLES");
                    }
                    assertEquals(Set.of(), tracker.unread(), label + ": fields no model reads");
                  }));
        }
      }
    }
    int examplesFound = found;
    tests.add(
        DynamicTest.dynamicTest(
            "finds the spec's examples", () -> assertTrue(examplesFound > 20, "" + examplesFound)));
    return tests.stream();
  }
}
