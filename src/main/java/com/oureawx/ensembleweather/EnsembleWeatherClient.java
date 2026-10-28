// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Decoders;
import com.oureawx.ensembleweather.internal.Engine;
import com.oureawx.ensembleweather.internal.HttpClientTransport;
import com.oureawx.ensembleweather.internal.Json;
import com.oureawx.ensembleweather.internal.JsonFields;
import com.oureawx.ensembleweather.internal.MalformedResponseException;
import com.oureawx.ensembleweather.internal.Query;
import com.oureawx.ensembleweather.internal.Sleeper;
import com.oureawx.ensembleweather.internal.Terms;
import com.oureawx.ensembleweather.internal.Transport;
import com.oureawx.ensembleweather.internal.Warnings;
import com.oureawx.ensembleweather.model.AlertsResponse;
import com.oureawx.ensembleweather.model.AttributionRegistry;
import com.oureawx.ensembleweather.model.CatalogLocation;
import com.oureawx.ensembleweather.model.CatalogLocationDetail;
import com.oureawx.ensembleweather.model.CatalogLocationPage;
import com.oureawx.ensembleweather.model.CatalogStation;
import com.oureawx.ensembleweather.model.CatalogStationDetail;
import com.oureawx.ensembleweather.model.CatalogStationPage;
import com.oureawx.ensembleweather.model.CurrentResponse;
import com.oureawx.ensembleweather.model.ForecastResponse;
import com.oureawx.ensembleweather.model.JsonValue;
import com.oureawx.ensembleweather.model.LocationSummary;
import com.oureawx.ensembleweather.model.LocationsResponse;
import com.oureawx.ensembleweather.model.NearbyLocationsResponse;
import com.oureawx.ensembleweather.model.NearbyPointResponse;
import com.oureawx.ensembleweather.model.RateLimit;
import com.oureawx.ensembleweather.model.ResponseMetadata;
import com.oureawx.ensembleweather.model.RunsResponse;
import com.oureawx.ensembleweather.model.SearchLocationsResponse;
import com.oureawx.ensembleweather.model.UnitSystem;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Clock;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.jspecify.annotations.Nullable;

/**
 * The client for the Ourea v1 API. Thread-safe and immutable: build one and share it.
 *
 * <pre>{@code
 * EnsembleWeatherClient client = EnsembleWeatherClient.builder().build(); // anonymous, Free tier
 * ForecastResponse f = client.getForecast("or/mt-hood-meadows");
 * }</pre>
 *
 * <p>Every operation has a blocking method and an {@code ...Async} twin returning a {@link
 * CompletableFuture}; both throw (or complete with) an unchecked {@link EnsembleWeatherException}.
 *
 * <p>Requests are always sent in their canonical form (parameters at their default left out,
 * alphabetical order, RFC 3986 encoding), so equal requests share one edge-cache entry. Search,
 * catalog details and attribution need no credentials and are sent <b>without</b> the API key even
 * when the client has one, so every caller shares their edge entry; set {@link
 * Builder#sendKeyOnOpenRoutes(boolean)} to attribute them to the key. Repeated calls revalidate a
 * local ETag cache, so an unchanged response costs a {@code 304}.
 */
public final class EnsembleWeatherClient {
  /** This SDK's version. */
  public static final String VERSION = "0.1.0";

  /** The production API. */
  public static final String DEFAULT_BASE_URL = "https://api.oureawx.com";

  private final Builder config;
  private final URI baseUrl;
  private final @Nullable String apiKey;
  private final UnitSystem units;
  private final Engine engine;
  private final AtomicReference<@Nullable RateLimit> lastRateLimit;

  private EnsembleWeatherClient(
      Builder config,
      URI baseUrl,
      @Nullable String apiKey,
      @Nullable ResponseCache cache,
      Transport transport,
      Warnings warnings) {
    this.config = config;
    this.baseUrl = baseUrl;
    this.apiKey = apiKey;
    this.units = config.units;
    this.lastRateLimit = new AtomicReference<>();
    String text = baseUrl.toString();
    while (text.endsWith("/")) {
      text = text.substring(0, text.length() - 1);
    }
    this.engine =
        new Engine(
            URI.create(text),
            apiKey,
            config.sendKeyOnOpenRoutes,
            config.maxRetries,
            config.timeout,
            cache,
            transport,
            config.sleeper,
            config.random,
            config.clock,
            warnings,
            userAgent(config.userAgentSuffix),
            lastRateLimit);
  }

  /**
   * Returns a new builder. With nothing set, the client is anonymous unless {@code OUREA_API_KEY}
   * is set, and calls {@code OUREA_BASE_URL} if set, else {@value #DEFAULT_BASE_URL}.
   *
   * @return a builder
   */
  public static Builder builder() {
    return new Builder();
  }

  static String userAgent(@Nullable String suffix) {
    String ua =
        "ensemble-weather-java/" + VERSION + " (Java/" + System.getProperty("java.version") + ")";
    return suffix == null ? ua : ua + " " + suffix;
  }

  /**
   * A client like this one but calling with another API key. It shares this client's cache (entries
   * never mix between keys) and HTTP connections.
   *
   * @param apiKey the key
   * @return the new client
   */
  public EnsembleWeatherClient withApiKey(String apiKey) {
    if (Objects.requireNonNull(apiKey, "apiKey").isEmpty()) {
      throw new UsageException("an API key cannot be empty");
    }
    return new EnsembleWeatherClient(
        config, baseUrl, apiKey, config.cacheInstance, config.transportInstance(), config.warnings);
  }

  /**
   * The rate-limit state of the last response that carried one (on this client).
   *
   * @return the state, or empty before any response carried {@code RateLimit-*} headers
   */
  public Optional<RateLimit> lastRateLimit() {
    return Optional.ofNullable(lastRateLimit.get());
  }

  /**
   * The base URL this client calls.
   *
   * @return the base URL
   */
  public URI baseUrl() {
    return baseUrl;
  }

  /**
   * Whether this client sends an API key.
   *
   * @return true when it has a key
   */
  public boolean hasApiKey() {
    return apiKey != null;
  }

  /** Describes the client; never shows the API key. */
  @Override
  public String toString() {
    return "EnsembleWeatherClient[baseUrl="
        + baseUrl
        + ", apiKey="
        + (apiKey == null ? "none" : "set")
        + ", units="
        + units
        + "]";
  }

  // ---------------------------------------------------------------- plumbing

  private static <T> T join(CompletableFuture<T> future) {
    try {
      return future.get();
    } catch (ExecutionException e) {
      Throwable cause = e.getCause();
      if (cause instanceof RuntimeException) {
        throw (RuntimeException) cause;
      }
      if (cause instanceof Error) {
        throw (Error) cause;
      }
      throw new IllegalStateException(cause);
    } catch (InterruptedException e) {
      future.cancel(true);
      Thread.currentThread().interrupt();
      throw new ConnectionException("interrupted while waiting for the response", e);
    }
  }

  private static String segment(String value) {
    return Query.encode(value);
  }

  private static String locationPath(LocationId location) {
    return "/api/v1/locations/" + segment(location.state()) + "/" + segment(location.slug());
  }

  private <T> CompletableFuture<T> call(
      String operationId,
      String path,
      Query query,
      boolean openRoute,
      Function<JsonFields, T> decoder,
      BiFunction<T, ResponseMetadata, T> withMetadata,
      BiFunction<T, JsonValue, T> post) {
    return engine
        .get(operationId, path, query.build(), openRoute)
        .thenApply(
            result -> {
              try {
                JsonValue body = Json.parse(result.body());
                T model = decoder.apply(JsonFields.root(body, null));
                return withMetadata.apply(post.apply(model, body), result.metadata());
              } catch (MalformedResponseException e) {
                throw new UnavailableException(
                    "the response does not match the v1 contract: " + e.getMessage(),
                    200,
                    null,
                    result.metadata().requestId().orElse(null));
              }
            });
  }

  private <T> CompletableFuture<T> call(
      String operationId,
      String path,
      Query query,
      boolean openRoute,
      Function<JsonFields, T> decoder,
      BiFunction<T, ResponseMetadata, T> withMetadata) {
    return call(operationId, path, query, openRoute, decoder, withMetadata, (m, b) -> m);
  }

  private static <T> CompletableFuture<T> guard(
      java.util.function.Supplier<CompletableFuture<T>> s) {
    try {
      return s.get();
    } catch (EnsembleWeatherException e) {
      return CompletableFuture.failedFuture(e);
    }
  }

  private static Query point(Coordinates at, boolean withElevation) {
    if (!withElevation && at.elevationM().isPresent()) {
      throw new UsageException("this route takes no elevation; pass Coordinates.of(lat, lon)");
    }
    Query q = new Query().number("lat", at.lat(), null).number("lon", at.lon(), null);
    if (at.elevationM().isPresent()) {
      q.number("elevation_m", at.elevationM().getAsDouble(), null);
    }
    return q;
  }

  // ---------------------------------------------------------------- forecasts

  private CompletableFuture<ForecastResponse> forecast(
      String operationId, String path, Query q, ForecastOptions options, boolean pointRoute) {
    UnitSystem effective = options.unitsOr(units);
    options.addTo(q, effective, pointRoute);
    List<Terms.WireTerm> prob = new ArrayList<>();
    for (ProbabilityTerm t : options.probabilities()) {
      prob.add(Terms.probability(t, effective));
    }
    List<Terms.WireTerm> totals = new ArrayList<>();
    for (TotalTerm t : options.totals()) {
      totals.add(Terms.total(t));
    }
    if (!prob.isEmpty()) {
      q.literal("prob", Terms.join(prob));
    }
    if (!totals.isEmpty()) {
      q.literal("totals", Terms.join(totals));
    }
    return call(
        operationId,
        path,
        q,
        false,
        Decoders::forecastResponse,
        (r, m) -> r.toBuilder().metadata(m).build(),
        (r, body) -> {
          ForecastResponse.Builder b = r.toBuilder();
          // An answer list the server omitted stays empty; a present one must answer every term.
          if (!prob.isEmpty() && body.get("probabilities").filter(v -> !v.isNull()).isPresent()) {
            b.probabilities(Terms.mapProbabilities(prob, r.probabilities()));
          }
          if (!totals.isEmpty() && body.get("window_totals").filter(v -> !v.isNull()).isPresent()) {
            b.windowTotals(Terms.mapTotals(totals, r.windowTotals()));
          }
          return b.build();
        });
  }

  /**
   * The forecast for a named location ({@code getLocationForecast}). Works without an API key (Free
   * tier: 10-day horizon, latest run).
   *
   * @param location the location
   * @param options the options, such as probabilities and window totals
   * @return the forecast; {@code probabilities()} and {@code windowTotals()} are in the order the
   *     terms were added
   */
  public ForecastResponse getForecast(LocationId location, ForecastOptions options) {
    return join(getForecastAsync(location, options));
  }

  /**
   * The forecast for a named location, with the API's default options.
   *
   * @param location the location
   * @return the forecast
   */
  public ForecastResponse getForecast(LocationId location) {
    return getForecast(location, ForecastOptions.defaults());
  }

  /**
   * The forecast for a named location given as {@code "state/slug"}.
   *
   * @param location such as {@code or/mt-hood-meadows}
   * @param options the options
   * @return the forecast
   */
  public ForecastResponse getForecast(String location, ForecastOptions options) {
    return getForecast(LocationId.parse(location), options);
  }

  /**
   * The forecast for a named location given as {@code "state/slug"}, with default options.
   *
   * @param location such as {@code or/mt-hood-meadows}
   * @return the forecast
   */
  public ForecastResponse getForecast(String location) {
    return getForecast(LocationId.parse(location), ForecastOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getForecast(LocationId, ForecastOptions)}.
   *
   * @param location the location
   * @param options the options
   * @return the forecast, eventually
   */
  public CompletableFuture<ForecastResponse> getForecastAsync(
      LocationId location, ForecastOptions options) {
    return guard(
        () -> forecast("getLocationForecast", locationPath(location), new Query(), options, false));
  }

  /**
   * Asynchronously, {@link #getForecast(LocationId)}.
   *
   * @param location the location
   * @return the forecast, eventually
   */
  public CompletableFuture<ForecastResponse> getForecastAsync(LocationId location) {
    return getForecastAsync(location, ForecastOptions.defaults());
  }

  /**
   * The forecast at coordinates ({@code getPointForecast}). Pro only: Free and anonymous callers
   * get a {@link PermissionException}; a point outside the domain an {@link
   * OutsideCoverageException}.
   *
   * @param at the point, with its elevation when you need one (a summit, a lift top)
   * @param options the options (all but {@code elevations})
   * @return the forecast at the quantized point; {@code point()} says where
   */
  public ForecastResponse getPointForecast(Coordinates at, ForecastOptions options) {
    return join(getPointForecastAsync(at, options));
  }

  /**
   * The forecast at coordinates, with default options.
   *
   * @param at the point
   * @return the forecast
   */
  public ForecastResponse getPointForecast(Coordinates at) {
    return getPointForecast(at, ForecastOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getPointForecast(Coordinates, ForecastOptions)}.
   *
   * @param at the point
   * @param options the options
   * @return the forecast, eventually
   */
  public CompletableFuture<ForecastResponse> getPointForecastAsync(
      Coordinates at, ForecastOptions options) {
    return guard(
        () -> forecast("getPointForecast", "/api/v1/forecast", point(at, true), options, true));
  }

  /**
   * Asynchronously, {@link #getPointForecast(Coordinates)}.
   *
   * @param at the point
   * @return the forecast, eventually
   */
  public CompletableFuture<ForecastResponse> getPointForecastAsync(Coordinates at) {
    return getPointForecastAsync(at, ForecastOptions.defaults());
  }

  // ---------------------------------------------------------------- current, alerts, nearby

  /**
   * Current conditions and nearby sensors for a named location ({@code getCurrentConditions}).
   *
   * @param location the location
   * @param options the options
   * @return the current conditions
   */
  public CurrentResponse getCurrent(LocationId location, CurrentOptions options) {
    return join(getCurrentAsync(location, options));
  }

  /**
   * Current conditions for a named location, with default options.
   *
   * @param location the location
   * @return the current conditions
   */
  public CurrentResponse getCurrent(LocationId location) {
    return getCurrent(location, CurrentOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getCurrent(LocationId, CurrentOptions)}.
   *
   * @param location the location
   * @param options the options
   * @return the current conditions, eventually
   */
  public CompletableFuture<CurrentResponse> getCurrentAsync(
      LocationId location, CurrentOptions options) {
    return guard(
        () -> {
          Query q = new Query();
          options.addTo(q, units);
          return call(
              "getCurrentConditions",
              locationPath(location) + "/current",
              q,
              false,
              Decoders::currentResponse,
              (r, m) -> r.toBuilder().metadata(m).build());
        });
  }

  /**
   * Asynchronously, {@link #getCurrent(LocationId)}.
   *
   * @param location the location
   * @return the current conditions, eventually
   */
  public CompletableFuture<CurrentResponse> getCurrentAsync(LocationId location) {
    return getCurrentAsync(location, CurrentOptions.defaults());
  }

  /**
   * Current conditions at coordinates ({@code getCurrentByCoordinates}, Pro).
   *
   * @param at the point
   * @param options the options
   * @return the current conditions
   */
  public CurrentResponse getCurrent(Coordinates at, CurrentOptions options) {
    return join(getCurrentAsync(at, options));
  }

  /**
   * Current conditions at coordinates, with default options.
   *
   * @param at the point
   * @return the current conditions
   */
  public CurrentResponse getCurrent(Coordinates at) {
    return getCurrent(at, CurrentOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getCurrent(Coordinates, CurrentOptions)}.
   *
   * @param at the point
   * @param options the options
   * @return the current conditions, eventually
   */
  public CompletableFuture<CurrentResponse> getCurrentAsync(
      Coordinates at, CurrentOptions options) {
    return guard(
        () -> {
          Query q = point(at, true);
          options.addTo(q, units);
          return call(
              "getCurrentByCoordinates",
              "/api/v1/current",
              q,
              false,
              Decoders::currentResponse,
              (r, m) -> r.toBuilder().metadata(m).build());
        });
  }

  /**
   * Asynchronously, {@link #getCurrent(Coordinates)}.
   *
   * @param at the point
   * @return the current conditions, eventually
   */
  public CompletableFuture<CurrentResponse> getCurrentAsync(Coordinates at) {
    return getCurrentAsync(at, CurrentOptions.defaults());
  }

  /**
   * Active NWS alerts for a named location ({@code getLocationAlerts}).
   *
   * @param location the location
   * @return the alerts, possibly none
   */
  public AlertsResponse getAlerts(LocationId location) {
    return join(getAlertsAsync(location));
  }

  /**
   * Asynchronously, {@link #getAlerts(LocationId)}.
   *
   * @param location the location
   * @return the alerts, eventually
   */
  public CompletableFuture<AlertsResponse> getAlertsAsync(LocationId location) {
    return guard(
        () ->
            call(
                "getLocationAlerts",
                locationPath(location) + "/alerts",
                new Query(),
                false,
                Decoders::alertsResponse,
                (r, m) -> r.toBuilder().metadata(m).build()));
  }

  /**
   * Active NWS alerts at coordinates ({@code getAlertsByCoordinates}, Pro).
   *
   * @param at the point, without an elevation
   * @return the alerts, possibly none
   */
  public AlertsResponse getAlerts(Coordinates at) {
    return join(getAlertsAsync(at));
  }

  /**
   * Asynchronously, {@link #getAlerts(Coordinates)}.
   *
   * @param at the point
   * @return the alerts, eventually
   */
  public CompletableFuture<AlertsResponse> getAlertsAsync(Coordinates at) {
    return guard(
        () ->
            call(
                "getAlertsByCoordinates",
                "/api/v1/alerts",
                point(at, false),
                false,
                Decoders::alertsResponse,
                (r, m) -> r.toBuilder().metadata(m).build()));
  }

  /**
   * A location and its nearest public neighbours ({@code getNearbyLocations}).
   *
   * @param location the location
   * @param options the options
   * @return the location and its neighbours, nearest first
   */
  public NearbyLocationsResponse getNearby(LocationId location, NearbyOptions options) {
    return join(getNearbyAsync(location, options));
  }

  /**
   * A location's three nearest neighbours within 50 km.
   *
   * @param location the location
   * @return the location and its neighbours
   */
  public NearbyLocationsResponse getNearby(LocationId location) {
    return getNearby(location, NearbyOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getNearby(LocationId, NearbyOptions)}.
   *
   * @param location the location
   * @param options the options
   * @return the neighbours, eventually
   */
  public CompletableFuture<NearbyLocationsResponse> getNearbyAsync(
      LocationId location, NearbyOptions options) {
    return guard(
        () -> {
          Query q = new Query();
          options.addTo(q, units);
          return call(
              "getNearbyLocations",
              locationPath(location) + "/nearby",
              q,
              false,
              Decoders::nearbyLocationsResponse,
              (r, m) -> r.toBuilder().metadata(m).build());
        });
  }

  /**
   * Asynchronously, {@link #getNearby(LocationId)}.
   *
   * @param location the location
   * @return the neighbours, eventually
   */
  public CompletableFuture<NearbyLocationsResponse> getNearbyAsync(LocationId location) {
    return getNearbyAsync(location, NearbyOptions.defaults());
  }

  /**
   * The nearest public locations to coordinates ({@code getNearbyByCoordinates}).
   *
   * @param at the point, without an elevation
   * @param options the options
   * @return the locations, nearest first
   */
  public NearbyPointResponse getNearby(Coordinates at, NearbyOptions options) {
    return join(getNearbyAsync(at, options));
  }

  /**
   * The three nearest public locations to coordinates within 50 km.
   *
   * @param at the point
   * @return the locations
   */
  public NearbyPointResponse getNearby(Coordinates at) {
    return getNearby(at, NearbyOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getNearby(Coordinates, NearbyOptions)}.
   *
   * @param at the point
   * @param options the options
   * @return the locations, eventually
   */
  public CompletableFuture<NearbyPointResponse> getNearbyAsync(
      Coordinates at, NearbyOptions options) {
    return guard(
        () -> {
          Query q = point(at, false);
          options.addTo(q, units);
          return call(
              "getNearbyByCoordinates",
              "/api/v1/nearby",
              q,
              false,
              Decoders::nearbyPointResponse,
              (r, m) -> r.toBuilder().metadata(m).build());
        });
  }

  /**
   * Asynchronously, {@link #getNearby(Coordinates)}.
   *
   * @param at the point
   * @return the locations, eventually
   */
  public CompletableFuture<NearbyPointResponse> getNearbyAsync(Coordinates at) {
    return getNearbyAsync(at, NearbyOptions.defaults());
  }

  // ---------------------------------------------------------------- search and lists

  /**
   * Searches locations by name ({@code searchLocations}). Sent without the API key (see the class
   * documentation). Key results by {@code key()}: bare slugs are unique only per state.
   *
   * @param text the query, at least 2 characters
   * @param options hard filters and a limit
   * @return the hits, best first
   */
  public SearchLocationsResponse searchLocations(String text, SearchOptions options) {
    return join(searchLocationsAsync(text, options));
  }

  /**
   * Searches locations by name, with no filters.
   *
   * @param text the query
   * @return the hits
   */
  public SearchLocationsResponse searchLocations(String text) {
    return searchLocations(text, SearchOptions.defaults());
  }

  /**
   * Asynchronously, {@link #searchLocations(String, SearchOptions)}.
   *
   * @param text the query
   * @param options the options
   * @return the hits, eventually
   */
  public CompletableFuture<SearchLocationsResponse> searchLocationsAsync(
      String text, SearchOptions options) {
    return guard(
        () -> {
          Query q = new Query().string("q", Objects.requireNonNull(text, "text"));
          options.addTo(q);
          return call(
              "searchLocations",
              "/api/v1/search/locations",
              q,
              true,
              Decoders::searchLocationsResponse,
              (r, m) -> r.toBuilder().metadata(m).build());
        });
  }

  /**
   * Asynchronously, {@link #searchLocations(String)}.
   *
   * @param text the query
   * @return the hits, eventually
   */
  public CompletableFuture<SearchLocationsResponse> searchLocationsAsync(String text) {
    return searchLocationsAsync(text, SearchOptions.defaults());
  }

  private CompletableFuture<LocationsResponse> locations(
      @Nullable String state, LocationListOptions options, @Nullable String cursor) {
    Query q = new Query();
    options.addTo(q, units, cursor);
    String path =
        state == null ? "/api/v1/locations" : "/api/v1/locations/" + segment(stateSegment(state));
    return call(
        state == null ? "getAllLocations" : "getLocations",
        path,
        q,
        false,
        Decoders::locationsResponse,
        (r, m) -> r.toBuilder().metadata(m).build());
  }

  private static String stateSegment(String state) {
    return state.length() == 2 ? state.toLowerCase(Locale.ROOT) : state;
  }

  /**
   * One page of every location ({@code getAllLocations}), slug-ordered.
   *
   * @param options filters and paging
   * @return the page; {@code nextCursor()} leads to the next one
   */
  public LocationsResponse listLocations(LocationListOptions options) {
    return join(listLocationsAsync(options));
  }

  /**
   * Asynchronously, {@link #listLocations(LocationListOptions)}.
   *
   * @param options filters and paging
   * @return the page, eventually
   */
  public CompletableFuture<LocationsResponse> listLocationsAsync(LocationListOptions options) {
    return guard(() -> locations(null, options, options.cursor()));
  }

  /**
   * One page of a state's locations ({@code getLocations}).
   *
   * @param state the USPS code or the state's name
   * @param options filters and paging
   * @return the page
   */
  public LocationsResponse listLocations(String state, LocationListOptions options) {
    return join(listLocationsAsync(state, options));
  }

  /**
   * Asynchronously, {@link #listLocations(String, LocationListOptions)}.
   *
   * @param state the state
   * @param options filters and paging
   * @return the page, eventually
   */
  public CompletableFuture<LocationsResponse> listLocationsAsync(
      String state, LocationListOptions options) {
    return guard(
        () -> locations(Objects.requireNonNull(state, "state"), options, options.cursor()));
  }

  /**
   * Every location, lazily following the cursor ({@code getAllLocations}).
   *
   * @param options filters, page size and a starting cursor
   * @return the locations
   */
  public PagedIterable<LocationSummary> iterateLocations(LocationListOptions options) {
    return new PagedIterable<>(
        options.cursor(),
        c -> {
          LocationsResponse p = join(locations(null, options, c));
          return new PagedIterable.Page<>(p.locations(), p.nextCursor().orElse(null));
        });
  }

  /**
   * Every location in a state, lazily following the cursor ({@code getLocations}).
   *
   * @param state the state
   * @param options filters, page size and a starting cursor
   * @return the locations
   */
  public PagedIterable<LocationSummary> iterateLocations(
      String state, LocationListOptions options) {
    Objects.requireNonNull(state, "state");
    return new PagedIterable<>(
        options.cursor(),
        c -> {
          LocationsResponse p = join(locations(state, options, c));
          return new PagedIterable.Page<>(p.locations(), p.nextCursor().orElse(null));
        });
  }

  // ---------------------------------------------------------------- catalog

  private CompletableFuture<CatalogLocationPage> catalogLocations(
      CatalogLocationQuery query, @Nullable String cursor) {
    Query q = new Query();
    query.addTo(q, cursor);
    return call(
        "listCatalogLocations",
        "/api/v1/catalog/locations",
        q,
        false,
        Decoders::catalogLocationPage,
        (r, m) -> r.toBuilder().metadata(m).build());
  }

  /**
   * One page of the catalog's locations ({@code listCatalogLocations}). Sent with the API key: a
   * keyed client keeps its own list budget.
   *
   * @param query filters and paging
   * @return the page; {@code nextCursor()} leads to the next one
   */
  public CatalogLocationPage listCatalogLocations(CatalogLocationQuery query) {
    return join(listCatalogLocationsAsync(query));
  }

  /**
   * Asynchronously, {@link #listCatalogLocations(CatalogLocationQuery)}.
   *
   * @param query filters and paging
   * @return the page, eventually
   */
  public CompletableFuture<CatalogLocationPage> listCatalogLocationsAsync(
      CatalogLocationQuery query) {
    return guard(() -> catalogLocations(query, query.cursor()));
  }

  /**
   * Every catalog location, lazily following the cursor ({@code listCatalogLocations}).
   *
   * @param query filters, page size and a starting cursor
   * @return the locations
   */
  public PagedIterable<CatalogLocation> iterateCatalogLocations(CatalogLocationQuery query) {
    return new PagedIterable<>(
        query.cursor(),
        c -> {
          CatalogLocationPage p = join(catalogLocations(query, c));
          return new PagedIterable.Page<>(p.items(), p.nextCursor().orElse(null));
        });
  }

  /**
   * A catalog location's full detail ({@code getCatalogLocation}). Sent without the API key. A
   * retired or renamed slug answers with the location it resolves to now.
   *
   * @param slug the slug
   * @param options opt-in extras
   * @return the detail
   */
  public CatalogLocationDetail getCatalogLocation(String slug, CatalogDetailOptions options) {
    return join(getCatalogLocationAsync(slug, options));
  }

  /**
   * A catalog location's detail, without extras.
   *
   * @param slug the slug
   * @return the detail
   */
  public CatalogLocationDetail getCatalogLocation(String slug) {
    return getCatalogLocation(slug, CatalogDetailOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getCatalogLocation(String, CatalogDetailOptions)}.
   *
   * @param slug the slug
   * @param options opt-in extras
   * @return the detail, eventually
   */
  public CompletableFuture<CatalogLocationDetail> getCatalogLocationAsync(
      String slug, CatalogDetailOptions options) {
    return guard(
        () -> {
          Query q = new Query();
          options.addTo(q);
          return call(
              "getCatalogLocation",
              "/api/v1/catalog/locations/" + segment(Objects.requireNonNull(slug, "slug")),
              q,
              true,
              Decoders::catalogLocationDetail,
              (r, m) -> r.toBuilder().metadata(m).build());
        });
  }

  /**
   * Asynchronously, {@link #getCatalogLocation(String)}.
   *
   * @param slug the slug
   * @return the detail, eventually
   */
  public CompletableFuture<CatalogLocationDetail> getCatalogLocationAsync(String slug) {
    return getCatalogLocationAsync(slug, CatalogDetailOptions.defaults());
  }

  private CompletableFuture<CatalogStationPage> catalogStations(
      CatalogStationQuery query, @Nullable String cursor) {
    Query q = new Query();
    query.addTo(q, cursor);
    return call(
        "listCatalogStations",
        "/api/v1/catalog/stations",
        q,
        false,
        Decoders::catalogStationPage,
        (r, m) -> r.toBuilder().metadata(m).build());
  }

  /**
   * One page of the catalog's observation stations ({@code listCatalogStations}). Sent with the API
   * key.
   *
   * @param query filters and paging
   * @return the page
   */
  public CatalogStationPage listCatalogStations(CatalogStationQuery query) {
    return join(listCatalogStationsAsync(query));
  }

  /**
   * Asynchronously, {@link #listCatalogStations(CatalogStationQuery)}.
   *
   * @param query filters and paging
   * @return the page, eventually
   */
  public CompletableFuture<CatalogStationPage> listCatalogStationsAsync(CatalogStationQuery query) {
    return guard(() -> catalogStations(query, query.cursor()));
  }

  /**
   * Every catalog station, lazily following the cursor ({@code listCatalogStations}).
   *
   * @param query filters, page size and a starting cursor
   * @return the stations
   */
  public PagedIterable<CatalogStation> iterateCatalogStations(CatalogStationQuery query) {
    return new PagedIterable<>(
        query.cursor(),
        c -> {
          CatalogStationPage p = join(catalogStations(query, c));
          return new PagedIterable.Page<>(p.items(), p.nextCursor().orElse(null));
        });
  }

  /**
   * A catalog station's full detail ({@code getCatalogStation}). Sent without the API key.
   *
   * @param slug the slug
   * @param options opt-in extras
   * @return the detail
   */
  public CatalogStationDetail getCatalogStation(String slug, CatalogDetailOptions options) {
    return join(getCatalogStationAsync(slug, options));
  }

  /**
   * A catalog station's detail, without extras.
   *
   * @param slug the slug
   * @return the detail
   */
  public CatalogStationDetail getCatalogStation(String slug) {
    return getCatalogStation(slug, CatalogDetailOptions.defaults());
  }

  /**
   * Asynchronously, {@link #getCatalogStation(String, CatalogDetailOptions)}.
   *
   * @param slug the slug
   * @param options opt-in extras
   * @return the detail, eventually
   */
  public CompletableFuture<CatalogStationDetail> getCatalogStationAsync(
      String slug, CatalogDetailOptions options) {
    return guard(
        () -> {
          Query q = new Query();
          options.addTo(q);
          return call(
              "getCatalogStation",
              "/api/v1/catalog/stations/" + segment(Objects.requireNonNull(slug, "slug")),
              q,
              true,
              Decoders::catalogStationDetail,
              (r, m) -> r.toBuilder().metadata(m).build());
        });
  }

  /**
   * Asynchronously, {@link #getCatalogStation(String)}.
   *
   * @param slug the slug
   * @return the detail, eventually
   */
  public CompletableFuture<CatalogStationDetail> getCatalogStationAsync(String slug) {
    return getCatalogStationAsync(slug, CatalogDetailOptions.defaults());
  }

  // ---------------------------------------------------------------- runs, attribution

  /**
   * The model runs a {@code run} option can pin, and what a forecast serves now ({@code
   * getAvailableRuns}).
   *
   * @param limit the number of runs (default 10, at most 100)
   * @return the runs, newest first; {@code nextCursor()} leads to older ones
   */
  public RunsResponse getRuns(int limit) {
    return join(getRunsAsync(limit, null));
  }

  /**
   * A page of model runs after a cursor ({@code getAvailableRuns}).
   *
   * @param limit the number of runs (default 10, at most 100)
   * @param cursor a previous page's {@code nextCursor()}, or null for the newest runs
   * @return the runs, newest first
   */
  public RunsResponse getRuns(int limit, @Nullable String cursor) {
    return join(getRunsAsync(limit, cursor));
  }

  /**
   * The ten newest model runs, and what a forecast serves now.
   *
   * @return the runs
   */
  public RunsResponse getRuns() {
    return getRuns(10);
  }

  /**
   * Asynchronously, {@link #getRuns(int, String)}.
   *
   * @param limit the number of runs
   * @param cursor a previous page's {@code nextCursor()}, or null
   * @return the runs, eventually
   */
  public CompletableFuture<RunsResponse> getRunsAsync(int limit, @Nullable String cursor) {
    return guard(
        () ->
            call(
                "getAvailableRuns",
                "/api/v1/runs",
                new Query().integer("limit", (long) limit, 10L).string("cursor", cursor),
                false,
                Decoders::runsResponse,
                (r, m) -> r.toBuilder().metadata(m).build()));
  }

  /**
   * Asynchronously, {@link #getRuns(int)}.
   *
   * @param limit the number of runs
   * @return the runs, eventually
   */
  public CompletableFuture<RunsResponse> getRunsAsync(int limit) {
    return getRunsAsync(limit, null);
  }

  /**
   * Asynchronously, {@link #getRuns()}.
   *
   * @return the runs, eventually
   */
  public CompletableFuture<RunsResponse> getRunsAsync() {
    return getRunsAsync(10);
  }

  /**
   * Every data source with its licence and required notices ({@code getAttribution}). Sent without
   * the API key. If you redistribute Ourea output, carry its {@code attribution.credit}.
   *
   * @return the attribution registry
   */
  public AttributionRegistry getAttribution() {
    return join(getAttributionAsync());
  }

  /**
   * Asynchronously, {@link #getAttribution()}.
   *
   * @return the registry, eventually
   */
  public CompletableFuture<AttributionRegistry> getAttributionAsync() {
    return guard(
        () ->
            call(
                "getAttribution",
                "/api/v1/attribution",
                new Query(),
                true,
                Decoders::attributionRegistry,
                (r, m) -> r.toBuilder().metadata(m).build()));
  }

  // ---------------------------------------------------------------- builder

  /** Configures an {@link EnsembleWeatherClient}. */
  public static final class Builder {
    private @Nullable String apiKey;
    private boolean anonymous;
    private @Nullable String baseUrl;
    private UnitSystem units = UnitSystem.METRIC;
    private Duration timeout = Duration.ofSeconds(30);
    private int maxRetries = 2;
    private @Nullable ResponseCache cacheInstance = new InMemoryResponseCache();
    private boolean cacheSetExplicitly;
    private boolean sendKeyOnOpenRoutes;
    private @Nullable String userAgentSuffix;
    private @Nullable ProxySelector proxy;
    private @Nullable Executor executor;
    // Test seams (package-private): the vectors runner replaces these.
    private @Nullable Transport transport;
    private Sleeper sleeper = Sleeper.system();
    private DoubleSupplier random = () -> ThreadLocalRandom.current().nextDouble();
    private Clock clock = Clock.systemUTC();
    private Warnings warnings = Warnings.process();
    private UnaryOperator<@Nullable String> environment = System::getenv;

    private Builder() {}

    /**
     * The API key, sent as {@code X-API-Key}. Without one the client uses {@code OUREA_API_KEY}
     * from the environment, else calls anonymously (Free tier), which is valid.
     *
     * @param apiKey the key, such as {@code ow_...}
     * @return this builder
     */
    public Builder apiKey(String apiKey) {
      if (Objects.requireNonNull(apiKey, "apiKey").isEmpty()) {
        throw new UsageException("an API key cannot be empty; use anonymous() for no key");
      }
      this.apiKey = apiKey;
      this.anonymous = false;
      return this;
    }

    /**
     * Calls anonymously, even when {@code OUREA_API_KEY} is set.
     *
     * @return this builder
     */
    public Builder anonymous() {
      this.apiKey = null;
      this.anonymous = true;
      return this;
    }

    /**
     * The base URL, for a proxy or a local mock. Default: {@code OUREA_BASE_URL} from the
     * environment, else {@value EnsembleWeatherClient#DEFAULT_BASE_URL}. A trailing slash is
     * ignored, and a path prefix is kept.
     *
     * @param baseUrl an {@code http} or {@code https} URL
     * @return this builder
     */
    public Builder baseUrl(String baseUrl) {
      this.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl");
      return this;
    }

    /**
     * The default unit system of every request (metric unless set). Each call's options can
     * override it.
     *
     * @param units metric, imperial or kelvin
     * @return this builder
     */
    public Builder units(UnitSystem units) {
      if (units == UnitSystem.UNKNOWN) {
        throw new UsageException("units must be METRIC, IMPERIAL or KELVIN");
      }
      this.units = units;
      return this;
    }

    /**
     * The timeout of each attempt (30 s unless set); retries are bounded by {@link
     * #maxRetries(int)}.
     *
     * @param timeout a positive duration
     * @return this builder
     */
    public Builder timeout(Duration timeout) {
      if (timeout.isNegative() || timeout.isZero()) {
        throw new UsageException("the timeout must be positive");
      }
      this.timeout = timeout;
      return this;
    }

    /**
     * How many times to retry a connection error, 429, 502, 503 or 504 (2 unless set, so three
     * attempts in all; 0 disables retrying).
     *
     * @param maxRetries 0 or more
     * @return this builder
     */
    public Builder maxRetries(int maxRetries) {
      if (maxRetries < 0) {
        throw new UsageException("maxRetries cannot be negative");
      }
      this.maxRetries = maxRetries;
      return this;
    }

    /**
     * Use this cache instead of the default in-memory one, for example to share across clients or
     * processes.
     *
     * @param cache the cache
     * @return this builder
     */
    public Builder cache(ResponseCache cache) {
      this.cacheInstance = Objects.requireNonNull(cache, "cache");
      this.cacheSetExplicitly = true;
      return this;
    }

    /**
     * Turn the local ETag cache off: no request then carries {@code If-None-Match}.
     *
     * @return this builder
     */
    public Builder disableCache() {
      this.cacheInstance = null;
      this.cacheSetExplicitly = true;
      return this;
    }

    /**
     * Also send the key on routes that need none (search, catalog details, attribution), so every
     * request is attributed to the key. Off by default, so those requests share the edge cache.
     *
     * @param send whether to send it
     * @return this builder
     */
    public Builder sendKeyOnOpenRoutes(boolean send) {
      this.sendKeyOnOpenRoutes = send;
      return this;
    }

    /**
     * A product token appended to the {@code User-Agent}, such as {@code myapp/1.2}. It must not
     * carry user data.
     *
     * @param productToken the token
     * @return this builder
     */
    public Builder userAgentSuffix(String productToken) {
      for (int i = 0; i < productToken.length(); i++) {
        char c = productToken.charAt(i);
        if (c < 0x20 || c > 0x7e) {
          throw new UsageException("the User-Agent suffix must be printable ASCII");
        }
      }
      this.userAgentSuffix = productToken;
      return this;
    }

    /**
     * The proxy selector for HTTP connections (the system default unless set).
     *
     * @param proxy the selector
     * @return this builder
     */
    public Builder proxy(ProxySelector proxy) {
      this.proxy = Objects.requireNonNull(proxy, "proxy");
      return this;
    }

    /**
     * The executor for the HTTP client's asynchronous work (the client's own unless set).
     *
     * @param executor the executor
     * @return this builder
     */
    public Builder executor(Executor executor) {
      this.executor = Objects.requireNonNull(executor, "executor");
      return this;
    }

    Builder transport(Transport transport) {
      this.transport = transport;
      return this;
    }

    Builder sleeper(Sleeper sleeper) {
      this.sleeper = sleeper;
      return this;
    }

    Builder random(DoubleSupplier random) {
      this.random = random;
      return this;
    }

    Builder clock(Clock clock) {
      this.clock = clock;
      return this;
    }

    Builder warnings(Warnings warnings) {
      this.warnings = warnings;
      return this;
    }

    Builder environment(UnaryOperator<@Nullable String> environment) {
      this.environment = environment;
      return this;
    }

    private Transport transportInstance() {
      Transport t = transport;
      if (t == null) {
        t = new HttpClientTransport(timeout, proxy, executor);
        transport = t;
      }
      return t;
    }

    /**
     * Builds the client.
     *
     * @return the client
     * @throws UsageException if the base URL is not an http(s) URL
     */
    public EnsembleWeatherClient build() {
      String key = apiKey;
      if (key == null && !anonymous) {
        String env = environment.apply("OUREA_API_KEY");
        key = env == null || env.trim().isEmpty() ? null : env.trim();
      }
      String url = baseUrl;
      if (url == null) {
        String env = environment.apply("OUREA_BASE_URL");
        url = env == null || env.trim().isEmpty() ? DEFAULT_BASE_URL : env.trim();
      }
      URI uri;
      try {
        uri = new URI(url);
      } catch (URISyntaxException e) {
        throw new UsageException("the base URL is not a URL: " + url);
      }
      String scheme = uri.getScheme();
      if (scheme == null
          || !(scheme.equalsIgnoreCase("https") || scheme.equalsIgnoreCase("http"))
          || uri.getHost() == null
          || uri.getRawQuery() != null
          || uri.getRawFragment() != null) {
        throw new UsageException("the base URL must be an http(s) URL with a host: " + url);
      }
      if (!cacheSetExplicitly) {
        cacheInstance = new InMemoryResponseCache();
      }
      Builder snapshot = copy();
      if (key != null
          && HttpClientTransport.jdkLogsHeaders(
              System.getProperty("jdk.httpclient.HttpClient.log"))) {
        warnings.warnOnce(
            "jdk.httpclient.HttpClient.log",
            "jdk.httpclient.HttpClient.log makes java.net.http print request headers, which"
                + " include the X-API-Key; do not enable it where logs are kept");
      }
      return new EnsembleWeatherClient(
          snapshot, uri, key, snapshot.cacheInstance, snapshot.transportInstance(), warnings);
    }

    private Builder copy() {
      Builder b = new Builder();
      b.apiKey = apiKey;
      b.anonymous = anonymous;
      b.baseUrl = baseUrl;
      b.units = units;
      b.timeout = timeout;
      b.maxRetries = maxRetries;
      b.cacheInstance = cacheInstance;
      b.cacheSetExplicitly = true;
      b.sendKeyOnOpenRoutes = sendKeyOnOpenRoutes;
      b.userAgentSuffix = userAgentSuffix;
      b.proxy = proxy;
      b.executor = executor;
      b.transport = transport;
      b.sleeper = sleeper;
      b.random = random;
      b.clock = clock;
      b.warnings = warnings;
      b.environment = environment;
      return b;
    }

    /** Describes the builder; never shows the API key. */
    @Override
    public String toString() {
      return "EnsembleWeatherClient.Builder[apiKey=" + (apiKey == null ? "unset" : "set") + "]";
    }
  }
}
