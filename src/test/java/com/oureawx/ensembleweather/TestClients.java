// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.internal.Warnings;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/** Builds clients wired to a mock transport, a recording sleeper and a fixed clock. */
final class TestClients {
  static final Instant NOW = Instant.parse("2026-11-15T00:00:00Z");

  final MockTransport transport = new MockTransport();
  final List<Double> delays = new ArrayList<>();
  final List<String> warnings = new ArrayList<>();

  static Path specDir() {
    String dir = System.getProperty("ourea.spec.dir");
    return dir == null ? Paths.get("spec") : Paths.get(dir);
  }

  EnsembleWeatherClient.Builder builder(Map<String, String> env) {
    return EnsembleWeatherClient.builder()
        .transport(transport)
        .sleeper(
            d -> {
              delays.add(d.toNanos() / 1e9);
              return CompletableFuture.completedFuture(null);
            })
        .random(() -> 1.0)
        .clock(Clock.fixed(NOW, ZoneOffset.UTC))
        .warnings(new Warnings(warnings::add))
        .environment(env::get)
        .timeout(Duration.ofSeconds(30));
  }

  EnsembleWeatherClient.Builder builder() {
    return builder(Map.of());
  }
}
