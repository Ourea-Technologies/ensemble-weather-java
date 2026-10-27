// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import com.oureawx.ensembleweather.AuthenticationException;
import com.oureawx.ensembleweather.CachedResponse;
import com.oureawx.ensembleweather.ConnectionException;
import com.oureawx.ensembleweather.EnsembleWeatherException;
import com.oureawx.ensembleweather.InvalidRequestException;
import com.oureawx.ensembleweather.NotFoundException;
import com.oureawx.ensembleweather.OutsideCoverageException;
import com.oureawx.ensembleweather.PermissionException;
import com.oureawx.ensembleweather.RateLimitException;
import com.oureawx.ensembleweather.ResponseCache;
import com.oureawx.ensembleweather.UnavailableException;
import com.oureawx.ensembleweather.model.JsonValue;
import com.oureawx.ensembleweather.model.RateLimit;
import com.oureawx.ensembleweather.model.ResponseMetadata;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import org.jspecify.annotations.Nullable;

/**
 * Sends one operation: credentials (section 3.2), redirects (3.3), errors (8), retries (9), the
 * ETag cache (10) and deprecation warnings (11) of BEHAVIOR.md. Every request is a GET.
 */
public final class Engine {
  /** The longest {@code Retry-After} the SDK waits out; a longer one is returned as the error. */
  public static final Duration MAX_RETRY_AFTER = Duration.ofSeconds(60);

  private static final int MAX_REDIRECTS = 5;
  private static final Set<Integer> RETRYABLE = Set.of(429, 502, 503, 504);
  private static final Pattern MAX_AGE = Pattern.compile("(?:^|[,\\s])max-age\\s*=\\s*\"?(\\d+)");
  private static final Pattern DEPRECATION_LINK =
      Pattern.compile("<([^>]*)>\\s*;[^,]*rel\\s*=\\s*\"?deprecation\"?", Pattern.CASE_INSENSITIVE);

  private final URI base;
  private final String baseText;
  private final @Nullable String apiKey;
  private final boolean sendKeyOnOpenRoutes;
  private final int maxRetries;
  private final Duration timeout;
  private final @Nullable ResponseCache cache;
  private final Transport transport;
  private final Sleeper sleeper;
  private final DoubleSupplier random;
  private final Clock clock;
  private final Warnings warnings;
  private final String userAgent;
  private final AtomicReference<@Nullable RateLimit> lastRateLimit;

  /**
   * Creates an engine. Every argument is fixed for its life; a client with another key makes
   * another engine that shares the cache and the transport.
   *
   * @param base the base URL, without a trailing slash
   * @param apiKey the API key, or null for anonymous calls
   * @param sendKeyOnOpenRoutes send the key on routes that need none, too
   * @param maxRetries the retries after the first attempt
   * @param timeout the per-attempt timeout
   * @param cache the ETag cache, or null when disabled
   * @param transport the HTTP seam
   * @param sleeper waits between attempts
   * @param random jitter in [0, 1]
   * @param clock the time, for cache freshness and HTTP-date {@code Retry-After}
   * @param warnings the deprecation sink
   * @param userAgent the {@code User-Agent}
   * @param lastRateLimit receives each response's rate-limit state
   */
  public Engine(
      URI base,
      @Nullable String apiKey,
      boolean sendKeyOnOpenRoutes,
      int maxRetries,
      Duration timeout,
      @Nullable ResponseCache cache,
      Transport transport,
      Sleeper sleeper,
      DoubleSupplier random,
      Clock clock,
      Warnings warnings,
      String userAgent,
      AtomicReference<@Nullable RateLimit> lastRateLimit) {
    this.base = base;
    this.baseText = base.toString();
    this.apiKey = apiKey;
    this.sendKeyOnOpenRoutes = sendKeyOnOpenRoutes;
    this.maxRetries = maxRetries;
    this.timeout = timeout;
    this.cache = cache;
    this.transport = transport;
    this.sleeper = sleeper;
    this.random = random;
    this.clock = clock;
    this.warnings = warnings;
    this.userAgent = userAgent;
    this.lastRateLimit = lastRateLimit;
  }

  /** A successful response: its body (the cached one after a 304) and its metadata. */
  public static final class Result {
    private final byte[] body;
    private final ResponseMetadata metadata;
    private final @Nullable TransportResponse headers;

    Result(byte[] body, ResponseMetadata metadata) {
      this(body, metadata, null);
    }

    private Result(byte[] body, ResponseMetadata metadata, @Nullable TransportResponse headers) {
      this.body = body;
      this.metadata = metadata;
      this.headers = headers;
    }

    Result withHeaders(TransportResponse response) {
      return new Result(body, metadata, response);
    }

    /**
     * The body.
     *
     * @return the body bytes
     */
    public byte[] body() {
      return body;
    }

    /**
     * The metadata.
     *
     * @return the response metadata
     */
    public ResponseMetadata metadata() {
      return metadata;
    }
  }

  /** What one attempt produced: a result, an error response, or no response. */
  private static final class Outcome {
    final @Nullable Result result;
    final @Nullable TransportResponse error;
    final @Nullable Throwable failure;

    private Outcome(
        @Nullable Result result, @Nullable TransportResponse error, @Nullable Throwable failure) {
      this.result = result;
      this.error = error;
      this.failure = failure;
    }

    static Outcome ok(Result r) {
      return new Outcome(r, null, null);
    }

    static Outcome error(TransportResponse r) {
      return new Outcome(null, r, null);
    }

    static Outcome failure(Throwable t) {
      return new Outcome(null, null, t);
    }
  }

  /**
   * Sends one operation.
   *
   * @param operationId the OpenAPI operation id (for deprecation warnings)
   * @param path the percent-encoded path, from {@code /api/v1}
   * @param query the canonical query string, possibly empty
   * @param openRoute whether the route needs no credentials (sent keyless unless configured)
   * @return the result; fails with an {@link EnsembleWeatherException}
   */
  public CompletableFuture<Result> get(
      String operationId, String path, String query, boolean openRoute) {
    URI uri = URI.create(baseText + path + (query.isEmpty() ? "" : "?" + query));
    boolean sendKey = apiKey != null && (!openRoute || sendKeyOnOpenRoutes);
    return attempt(operationId, uri, sendKey, 0);
  }

  private CompletableFuture<Result> attempt(
      String operationId, URI uri, boolean sendKey, int retriesDone) {
    return hop(uri, sendKey, 0)
        .thenCompose(
            outcome -> {
              Result result = outcome.result;
              if (result != null) {
                warnIfDeprecated(operationId, result);
                return CompletableFuture.completedFuture(result);
              }
              Duration delay = null;
              EnsembleWeatherException error;
              TransportResponse response = outcome.error;
              if (response != null) {
                error = errorFor(response);
                if (RETRYABLE.contains(response.status())) {
                  Duration retryAfter =
                      response.status() == 429 || response.status() == 503
                          ? retryAfter(response)
                          : null;
                  if (retryAfter == null) {
                    delay = backoff(retriesDone + 1);
                  } else if (retryAfter.compareTo(MAX_RETRY_AFTER) <= 0) {
                    delay = retryAfter;
                  }
                }
              } else {
                Throwable failure = Objects.requireNonNull(outcome.failure);
                error = connectionError(failure);
                if (error instanceof ConnectionException && !isTlsFailure(failure)) {
                  delay = backoff(retriesDone + 1);
                }
              }
              if (delay == null || retriesDone >= maxRetries) {
                return CompletableFuture.failedFuture(error);
              }
              return sleeper
                  .sleep(delay)
                  .thenCompose(v -> attempt(operationId, uri, sendKey, retriesDone + 1));
            });
  }

  /** Full jitter: a random wait in {@code [0, min(8 s, 0.5 s * 2^(n-1))]}. */
  private Duration backoff(int retry) {
    double cap = Math.min(8.0, 0.5 * Math.pow(2, retry - 1));
    double seconds = cap * Math.max(0.0, Math.min(1.0, random.getAsDouble()));
    return Duration.ofNanos(Math.round(seconds * 1e9));
  }

  private @Nullable Duration retryAfter(TransportResponse response) {
    String value = response.header("Retry-After");
    if (value == null) {
      return null;
    }
    String v = value.trim();
    if (v.matches("\\d{1,10}")) {
      return Duration.ofSeconds(Long.parseLong(v));
    }
    try {
      Instant at = ZonedDateTime.parse(v, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
      Duration d = Duration.between(clock.instant(), at);
      return d.isNegative() ? Duration.ZERO : d;
    } catch (DateTimeException e) {
      return null;
    }
  }

  private String cacheKey(URI uri, boolean sendKey) {
    String who = sendKey && apiKey != null ? "key:" + sha256(apiKey) : "anonymous";
    return uri.toASCIIString() + " " + who;
  }

  private static String sha256(String text) {
    try {
      byte[] digest =
          MessageDigest.getInstance("SHA-256").digest(text.getBytes(StandardCharsets.UTF_8));
      StringBuilder out = new StringBuilder();
      for (byte b : digest) {
        out.append(String.format(Locale.ROOT, "%02x", b));
      }
      return out.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("SHA-256 is required on every Java platform", e);
    }
  }

  private static boolean sameOrigin(URI a, URI b) {
    return a.getScheme().equalsIgnoreCase(b.getScheme())
        && Objects.equals(
            a.getHost() == null ? null : a.getHost().toLowerCase(Locale.ROOT),
            b.getHost() == null ? null : b.getHost().toLowerCase(Locale.ROOT))
        && port(a) == port(b);
  }

  private static int port(URI u) {
    if (u.getPort() >= 0) {
      return u.getPort();
    }
    return "https".equalsIgnoreCase(u.getScheme()) ? 443 : 80;
  }

  private CompletableFuture<Outcome> hop(URI uri, boolean sendKey, int redirects) {
    String key = cache == null ? null : cacheKey(uri, sendKey);
    CachedResponse entry = cache == null || key == null ? null : cache.get(key).orElse(null);
    if (entry != null
        && !entry.maxAge().isZero()
        && clock.instant().isBefore(entry.storedAt().plus(entry.maxAge()))) {
      return CompletableFuture.completedFuture(
          Outcome.ok(new Result(entry.body(), ResponseMetadata.of(null, null, false))));
    }
    Map<String, String> headers = new LinkedHashMap<>();
    headers.put("User-Agent", userAgent);
    headers.put("Accept", "application/json");
    if (sendKey && apiKey != null) {
      headers.put("X-API-Key", apiKey);
    }
    if (entry != null) {
      headers.put("If-None-Match", entry.etag());
    }
    TransportRequest request = new TransportRequest(uri, headers, timeout);
    CompletableFuture<TransportResponse> sent;
    try {
      sent = transport.send(request);
    } catch (RuntimeException e) {
      sent = CompletableFuture.failedFuture(e);
    }
    CachedResponse cached = entry;
    return sent.thenCompose(response -> onResponse(uri, sendKey, redirects, key, cached, response))
        .exceptionally(failure -> Outcome.failure(unwrap(failure)));
  }

  private CompletableFuture<Outcome> onResponse(
      URI uri,
      boolean sendKey,
      int redirects,
      @Nullable String key,
      @Nullable CachedResponse entry,
      TransportResponse response) {
    int status = response.status();
    RateLimit rateLimit = rateLimit(response);
    if (rateLimit != null) {
      lastRateLimit.set(rateLimit);
    }
    String location = response.header("Location");
    if ((status == 301 || status == 302 || status == 307 || status == 308) && location != null) {
      if (redirects >= MAX_REDIRECTS) {
        return CompletableFuture.completedFuture(Outcome.error(response));
      }
      URI next;
      try {
        next = uri.resolve(location.trim());
      } catch (IllegalArgumentException e) {
        return CompletableFuture.completedFuture(Outcome.error(response));
      }
      // The key only ever goes to the configured base URL's origin.
      return hop(next, sendKey && sameOrigin(next, base), redirects + 1);
    }
    String requestId = response.header("X-Request-Id");
    String cacheControl = response.joinedHeader("Cache-Control");
    boolean noStore =
        cacheControl != null && cacheControl.toLowerCase(Locale.ROOT).contains("no-store");
    if (status == 304 && entry != null) {
      String etag = response.header("ETag");
      if (cache != null && key != null) {
        if (noStore) {
          cache.remove(key);
        } else {
          cache.put(
              key,
              CachedResponse.of(
                  etag == null ? entry.etag() : etag,
                  entry.body(),
                  clock.instant(),
                  maxAge(cacheControl)));
        }
      }
      return CompletableFuture.completedFuture(
          Outcome.ok(
              new Result(entry.body(), ResponseMetadata.of(rateLimit, requestId, true))
                  .withHeaders(response)));
    }
    if (status >= 200 && status < 300) {
      if (cache != null && key != null) {
        String etag = response.header("ETag");
        if (noStore || status != 200 || etag == null) {
          cache.remove(key);
        } else {
          cache.put(
              key, CachedResponse.of(etag, response.body(), clock.instant(), maxAge(cacheControl)));
        }
      }
      return CompletableFuture.completedFuture(
          Outcome.ok(
              new Result(response.body(), ResponseMetadata.of(rateLimit, requestId, false))
                  .withHeaders(response)));
    }
    return CompletableFuture.completedFuture(Outcome.error(response));
  }

  private static Duration maxAge(@Nullable String cacheControl) {
    if (cacheControl == null) {
      return Duration.ZERO;
    }
    String cc = cacheControl.toLowerCase(Locale.ROOT);
    if (cc.contains("no-cache")) {
      return Duration.ZERO;
    }
    Matcher m = MAX_AGE.matcher(cc);
    if (!m.find()) {
      return Duration.ZERO;
    }
    try {
      return Duration.ofSeconds(Long.parseLong(m.group(1)));
    } catch (NumberFormatException e) {
      return Duration.ZERO;
    }
  }

  private static @Nullable RateLimit rateLimit(TransportResponse r) {
    Long limit = parseLong(r.header("RateLimit-Limit"));
    Long remaining = parseLong(r.header("RateLimit-Remaining"));
    Long reset = parseLong(r.header("RateLimit-Reset"));
    if (limit == null || remaining == null || reset == null) {
      return null;
    }
    return RateLimit.of(limit, remaining, Duration.ofSeconds(reset), r.header("RateLimit-Policy"));
  }

  private static @Nullable Long parseLong(@Nullable String s) {
    if (s == null) {
      return null;
    }
    try {
      return Long.parseLong(s.trim());
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private void warnIfDeprecated(String operationId, Result result) {
    TransportResponse headers = result.headers;
    if (headers == null) {
      return;
    }
    String deprecation = headers.header("Deprecation");
    String sunset = headers.header("Sunset");
    if (deprecation == null && sunset == null) {
      return;
    }
    StringBuilder m = new StringBuilder("Ourea API: ").append(operationId).append(" is deprecated");
    if (deprecation != null) {
      m.append(" (Deprecation: ").append(deprecation).append(')');
    }
    if (sunset != null) {
      m.append("; it is removed after ").append(sunset);
    }
    String link = headers.joinedHeader("Link");
    if (link != null) {
      Matcher lm = DEPRECATION_LINK.matcher(link);
      if (lm.find()) {
        m.append("; see ").append(lm.group(1));
      }
    }
    warnings.warnOnce(operationId, m.toString());
  }

  private static Throwable unwrap(Throwable t) {
    Throwable c = t;
    while ((c instanceof CompletionException || c instanceof ExecutionException)
        && c.getCause() != null) {
      c = c.getCause();
    }
    return c;
  }

  private static boolean isTlsFailure(Throwable t) {
    for (Throwable c = t; c != null; c = c.getCause()) {
      if (c instanceof SSLHandshakeException || c instanceof SSLPeerUnverifiedException) {
        return true;
      }
    }
    return false;
  }

  /** Removes the API key from any text that might quote it. */
  private String redact(String text) {
    String key = apiKey;
    return key == null || key.isEmpty() ? text : text.replace(key, "[redacted]");
  }

  private EnsembleWeatherException connectionError(Throwable failure) {
    if (failure instanceof EnsembleWeatherException) {
      return (EnsembleWeatherException) failure;
    }
    if (failure instanceof BodyTooLargeException) {
      return new UnavailableException(
          redact(failure.getMessage() == null ? "body too large" : failure.getMessage()),
          ((BodyTooLargeException) failure).status(),
          null,
          null);
    }
    String detail = failure.getMessage() == null ? "" : ": " + failure.getMessage();
    return new ConnectionException(
        redact(
            "no response from "
                + base.getHost()
                + " ("
                + failure.getClass().getSimpleName()
                + detail
                + ")"),
        failure);
  }

  /**
   * Maps an error response to its exception: by the API's code first, else by status.
   *
   * @param response the error response
   * @return the exception
   */
  EnsembleWeatherException errorFor(TransportResponse response) {
    int status = response.status();
    String code = null;
    String message = null;
    try {
      JsonValue body = Json.parse(response.body());
      code = body.get("error").flatMap(JsonValue::asString).orElse(null);
      message = body.get("message").flatMap(JsonValue::asString).orElse(null);
    } catch (MalformedResponseException e) {
      // Not JSON (a proxy's page, an empty body): classified by status alone.
    }
    String requestId = response.header("X-Request-Id");
    String text =
        redact(
            (message == null || message.isEmpty() ? "HTTP " + status : message)
                + " (HTTP "
                + status
                + (code == null ? "" : ", " + code)
                + (requestId == null ? "" : ", request " + requestId)
                + ")");
    if (code != null) {
      switch (code) {
        case "INVALID_API_KEY":
        case "UNAUTHORIZED":
          return new AuthenticationException(text, status, code, requestId);
        case "PRO_REQUIRED":
        case "FORBIDDEN":
          return new PermissionException(text, status, code, requestId);
        case "LOCATION_NOT_FOUND":
        case "MODEL_RUN_NOT_FOUND":
        case "NOT_FOUND":
        case "GONE":
          return new NotFoundException(text, status, code, requestId);
        case "BAD_REQUEST":
        case "INVALID_COORDINATES":
        case "INVALID_ELEVATION":
        case "INVALID_MAX_HOURS":
        case "ELEVATION_REQUIRED":
        case "AMBIGUOUS":
        case "FEELS_LIKE_UNAVAILABLE":
          return new InvalidRequestException(text, status, code, requestId);
        case "OUTSIDE_COVERAGE":
          return new OutsideCoverageException(text, status, code, requestId);
        case "TOO_MANY_REQUESTS":
          return new RateLimitException(text, status, code, requestId, retryAfter(response));
        case "FORECAST_UNAVAILABLE":
        case "TIMEOUT":
        case "INTERNAL_ERROR":
        case "DATABASE_ERROR":
          return new UnavailableException(text, status, code, requestId);
        default:
          break;
      }
    }
    if (status == 401) {
      return new AuthenticationException(text, status, code, requestId);
    }
    if (status == 403) {
      return new PermissionException(text, status, code, requestId);
    }
    if (status == 404 || status == 410) {
      return new NotFoundException(text, status, code, requestId);
    }
    if (status == 429) {
      return new RateLimitException(text, status, code, requestId, retryAfter(response));
    }
    if (status >= 400 && status < 500) {
      return new InvalidRequestException(text, status, code, requestId);
    }
    return new UnavailableException(text, status, code, requestId);
  }
}
