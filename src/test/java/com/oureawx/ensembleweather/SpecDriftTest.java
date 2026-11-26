// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.oureawx.ensembleweather.internal.Decoders;
import com.oureawx.ensembleweather.internal.JsonFields;
import com.oureawx.ensembleweather.internal.MalformedResponseException;
import com.oureawx.ensembleweather.internal.TransportRequest;
import com.oureawx.ensembleweather.types.AlertClass;
import com.oureawx.ensembleweather.types.ExploreMetric;
import com.oureawx.ensembleweather.types.JsonValue;
import com.oureawx.ensembleweather.types.LocationType;
import com.oureawx.ensembleweather.types.UnitSystem;
import com.oureawx.ensembleweather.types.Variable;
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
  private static final Map<String, Consumer<Client>> CALLS = new TreeMap<>();

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
    CALLS.put("getCurrentByCoordinates", c -> c.getPointCurrent(point, current));
    CALLS.put("getLocationAlerts", c -> c.getAlerts(x));
    CALLS.put("getAlertsByCoordinates", c -> c.getPointAlerts(Coordinates.of(45.3, -121.7)));
    CALLS.put("getNearbyLocations", c -> c.getNearby(x, nearby));
    CALLS.put(
        "getNearbyByCoordinates", c -> c.getPointNearby(Coordinates.of(45.3, -121.7), nearby));
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
                    .sort(ExploreMetric.SNOW_5D)
                    .order(SortOrder.ASC)
                    .near(45.3, -121.7)
                    .radiusKm(20)
                    .units(UnitSystem.IMPERIAL)
                    .build()));
    CALLS.put(
        "explore",
        c ->
            c.explore(
                ExploreMetric.SNOW_5D,
                ExploreOptions.builder()
                    .order(SortOrder.ASC)
                    .type(LocationType.SKI_AREA)
                    .state("OR")
                    .near(45.3, -121.7)
                    .radiusKm(20)
                    .elevationMin(1000)
                    .elevationMax(2000)
                    .allPoints(true)
                    .limit(5)
                    .cursor("c")
                    .units(UnitSystem.IMPERIAL)
                    .build()));
    CALLS.put("getComposition", Client::getComposition);
    CALLS.put(
        "getLocationSeason",
        c ->
            c.getSeason(
                x,
                SeasonOptions.builder()
                    .waterYear(2027)
                    .daily(true)
                    .units(UnitSystem.IMPERIAL)
                    .build()));
    CALLS.put(
        "getLocationSnowTrend",
        c ->
            c.getSnowTrend(
                x,
                SnowTrendOptions.builder()
                    .elevation("main_lodge")
                    .units(UnitSystem.IMPERIAL)
                    .build()));
    CALLS.put("getLocationSnowpack", c -> c.getSnowpack(x, UnitSystem.IMPERIAL));
    CALLS.put("getLocationSnowpackPlume", c -> c.getSnowpackPlume(x, UnitSystem.IMPERIAL));
    CALLS.put(
        "getAlertOutlines",
        c ->
            c.getAlertOutlines(
                AlertOutlineOptions.builder()
                    .classes(AlertClass.WIND, AlertClass.WINTER)
                    .bbox(-125, 41, -116, 49)
                    .build()));
    CALLS.put("getAvalancheZones", Client::getAvalancheZones);
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
    CALLS.put("getAttribution", Client::getAttribution);
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
    for (Map.Entry<String, Consumer<Client>> e : CALLS.entrySet()) {
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
      Map.ofEntries(
          Map.entry("CurrentConditions.station", "CurrentStation"),
          Map.entry("TimeSeriesPoint.members", "ModelMembers"),
          Map.entry("RunsResponse.served", "ServedRun"),
          Map.entry("RunsResponse.runs", "RunInfo"),
          Map.entry("DailyForecast.measured_precipitation", "MeasuredPrecipitation"),
          Map.entry("SnowTrendResponse.elevation", "SnowTrendResponseElevation"),
          Map.entry("SnowTrendGrid.runs", "SnowTrendRun"),
          Map.entry("SnowpackResponse.definitions", "SnowpackResponseDefinitions"),
          Map.entry("SnowpackRun.model", "SnowpackModel"),
          Map.entry("SnowpackSpot.anchor", "SnowpackSpotAnchor"),
          Map.entry("SnowpackPlumeResponse.depth", "SnowpackPlumeDepth"),
          Map.entry("SnowpackPlumeResponse.models", "SnowpackPlumeModel"));

  /**
   * Schemas with no model class of their own: enums and arrays the models hold as Java types, and
   * {@code AlertOutlineProperties}, which a feature passes through as raw JSON (BEHAVIOR.md 3.0:
   * GeoJSON keeps its wire keys).
   */
  private static final Set<String> NOT_MODELS =
      Set.of(
          "ErrorResponse",
          "UnitSystem",
          "FreezingLevel",
          "ModelMeans",
          "NextCursor",
          "MemberColumn",
          "NullableNumbers",
          "AlertOutlineProperties");

  /** Java's names for wire words it reserves (BEHAVIOR.md 3.0: the language's idiom wins). */
  private static final Map<String, String> RESERVED =
      Map.of(
          "default",
          "isDefault",
          "is_default",
          "isDefault",
          "short",
          "shortText",
          "long",
          "longText");

  /**
   * The BEHAVIOR.md 3.0 rename table, read from the pinned spec: where the field sits (a schema, or
   * {@code Schema.field} for an inline object) to its wire names and their SDK names.
   */
  static final Map<String, Map<String, String>> RENAMES = renames();

  private static Map<String, Map<String, String>> renames() {
    String behaviour;
    try {
      behaviour = Files.readString(TestClients.specDir().resolve("BEHAVIOR.md"));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
    String table =
        behaviour
            .split("\\| Schema \\(where the field sits\\) \\| Wire name \\| SDK name \\|", -1)[1];
    Map<String, Map<String, String>> out = new TreeMap<>();
    String[] lines = table.split("\n", -1);
    for (int i = 2; i < lines.length && lines[i].startsWith("|"); i++) {
      String[] cells = lines[i].split("\\|", -1);
      List<String> wires = names(cells[2]);
      List<String> sdks = names(cells[3].split("\\(", -1)[0]);
      assertEquals(wires.size(), sdks.size(), lines[i]);
      for (String holder : names(cells[1].split("\\(", -1)[0])) {
        String key = holder.replace("[]", "");
        for (int j = 0; j < wires.size(); j++) {
          out.computeIfAbsent(key, k -> new TreeMap<>()).put(wires.get(j), sdks.get(j));
        }
      }
    }
    assertTrue(out.size() > 25, "the rename table: " + out.keySet());
    return out;
  }

  private static List<String> names(String cell) {
    List<String> out = new ArrayList<>();
    for (String s : cell.split(",", -1)) {
      String n = s.trim().replace("`", "");
      if (!n.isEmpty()) {
        out.add(n);
      }
    }
    return out;
  }

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

  private static void checkProperties(String className, String holder, Map<String, Object> props) {
    Class<?> type;
    try {
      type = Class.forName("com.oureawx.ensembleweather.types." + className);
    } catch (ClassNotFoundException e) {
      fail("no model class " + className);
      return;
    }
    Map<String, String> renamed = RENAMES.getOrDefault(holder, Map.of());
    for (String prop : props.keySet()) {
      String sdk = renamed.get(prop);
      String getter = camel(sdk == null ? prop : sdk);
      try {
        type.getMethod(getter);
      } catch (NoSuchMethodException e) {
        fail(className + " has no " + getter + "() for the spec's " + prop);
      }
      if (sdk != null && !camel(prop).equals(getter)) {
        String wireGetter = camel(prop);
        boolean alias = true;
        try {
          type.getMethod(wireGetter);
        } catch (NoSuchMethodException e) {
          alias = false;
        }
        assertTrue(
            !alias,
            className + "." + wireGetter + "() is the wire name of renamed " + getter + "()");
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
                checkProperties(className(schema), schema, props);
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
                    checkProperties(inline, schema + "." + p.getKey(), properties(s));
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
    DECODERS.put("explore", Decoders::exploreResponse);
    DECODERS.put("getComposition", Decoders::compositionResponse);
    DECODERS.put("getLocationSeason", Decoders::seasonResponse);
    DECODERS.put("getLocationSnowTrend", Decoders::snowTrendResponse);
    DECODERS.put("getLocationSnowpack", Decoders::snowpackResponse);
    DECODERS.put("getLocationSnowpackPlume", Decoders::snowpackPlumeResponse);
    DECODERS.put("getAlertOutlines", Decoders::alertOutlineCollection);
    DECODERS.put("getAvalancheZones", Decoders::avalancheZoneCollection);
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
        if (content.isEmpty()) {
          content = map(response, "content", "application/geo+json");
        }
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
