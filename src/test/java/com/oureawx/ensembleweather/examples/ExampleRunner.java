// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.examples;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Runs every program in {@code examples/} against the local mock API (never the real one), each as
 * its own JVM with {@code OUREA_BASE_URL} pointing at the mock, as a user would run it. The
 * Pro-only example runs twice: without a key (it must skip and exit 0) and with a mock key.
 */
public final class ExampleRunner {
  private ExampleRunner() {}

  /**
   * Runs the examples.
   *
   * @param args {@code specDir classpath}
   * @throws Exception if one cannot be started
   */
  public static void main(String[] args) throws Exception {
    Path spec = Paths.get(args[0]);
    String classpath = args[1];
    Path examples = spec.getParent().resolve("examples");
    List<String> names;
    try (Stream<Path> files = Files.list(examples)) {
      names =
          files
              .map(p -> p.getFileName().toString())
              .filter(n -> n.endsWith(".java"))
              .map(n -> n.substring(0, n.length() - ".java".length()))
              .sorted()
              .collect(Collectors.toList());
    }
    if (names.size() < 8) {
      throw new IllegalStateException("expected the 8 standard examples, found " + names);
    }
    String java = ProcessHandle.current().info().command().orElse("java");
    int failed = 0;
    int runs = 0;
    try (MockApiServer mock = MockApiServer.start(spec, 0)) {
      List<String[]> plan = new ArrayList<>();
      for (String n : names) {
        plan.add(new String[] {n, null});
      }
      plan.add(new String[] {"PointForecast", "ow_mock_pro_key"});
      for (String[] run : plan) {
        runs++;
        ProcessBuilder pb =
            new ProcessBuilder(java, "-cp", classpath, run[0]).redirectErrorStream(true);
        Map<String, String> env = pb.environment();
        env.remove("OUREA_API_KEY");
        env.put("OUREA_BASE_URL", mock.url());
        if (run[1] != null) {
          env.put("OUREA_API_KEY", run[1]);
        }
        Process process = pb.start();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (InputStream in = process.getInputStream()) {
          in.transferTo(out);
        }
        boolean done = process.waitFor(60, TimeUnit.SECONDS);
        String text = out.toString(StandardCharsets.UTF_8);
        boolean ok = done && process.exitValue() == 0 && !text.isBlank();
        if (!done) {
          process.destroyForcibly();
        }
        if (!ok) {
          failed++;
        }
        System.out.println(
            (ok ? "PASS " : "FAIL ") + run[0] + (run[1] == null ? "" : " (mock key)"));
        System.out.println(text.replaceAll("(?m)^", "    ").stripTrailing());
      }
    }
    System.out.println((runs - failed) + "/" + runs + " example runs passed");
    if (failed > 0) {
      System.exit(1);
    }
  }
}
