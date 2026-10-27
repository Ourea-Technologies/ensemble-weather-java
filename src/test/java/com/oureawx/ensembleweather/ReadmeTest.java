// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

/** The README quickstart is examples/Quickstart.java, byte for byte, so it cannot rot. */
class ReadmeTest {
  private static Path root() {
    return TestClients.specDir().getParent();
  }

  private static String read(String name) throws IOException {
    return new String(Files.readAllBytes(root().resolve(name)), StandardCharsets.UTF_8);
  }

  @Test
  void theReadmeQuickstartIsTheExample() throws IOException {
    Matcher m = Pattern.compile("```java\n(// Quickstart[\\s\\S]*?)```").matcher(read("README.md"));
    assertTrue(m.find(), "README.md has no quickstart block");
    assertEquals(read("examples/Quickstart.java"), m.group(1));
  }

  @Test
  void theReadmeNeverLinksTheApiHostOrADevHost() throws IOException {
    String readme = read("README.md");
    assertFalse(readme.contains("](https://api.oureawx.com"), "the API host is not a page");
    assertFalse(readme.contains("dev.oureawx"), "never a dev host");
    assertTrue(readme.contains("https://docs.oureawx.com/java"));
  }
}
