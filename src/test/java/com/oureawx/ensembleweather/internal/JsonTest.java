// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.oureawx.ensembleweather.model.JsonValue;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

class JsonTest {
  private static JsonValue parse(String s) {
    return Json.parse(s.getBytes(StandardCharsets.UTF_8));
  }

  @Test
  void parsesPlainDataOnly() {
    JsonValue v = parse("{\"@class\":\"java.lang.Runtime\",\"a\":[1,2.5,true,null,\"x\"]}");
    // A type hint is just a string: nothing is ever instantiated from JSON.
    assertEquals("java.lang.Runtime", v.get("@class").flatMap(JsonValue::asString).orElseThrow());
    assertEquals(5, v.get("a").orElseThrow().asArray().size());
    assertEquals("{\"@class\":\"java.lang.Runtime\",\"a\":[1,2.5,true,null,\"x\"]}", v.toString());
  }

  @Test
  void refusesMalformedOrHostileInput() {
    assertThrows(MalformedResponseException.class, () -> parse(""));
    assertThrows(MalformedResponseException.class, () -> parse("{\"a\":1} trailing"));
    assertThrows(MalformedResponseException.class, () -> parse("{\"a\":1,\"a\":2}"));
    assertThrows(MalformedResponseException.class, () -> parse("{\"a\":"));
    assertThrows(MalformedResponseException.class, () -> parse("[".repeat(1000)));
    assertThrows(MalformedResponseException.class, () -> parse("1" + "0".repeat(2000)));
  }

  @Test
  void fieldsAreTypeChecked() {
    JsonFields f = JsonFields.root(parse("{\"n\":\"1\",\"i\":1.5,\"t\":\"yesterday\"}"), null);
    assertThrows(MalformedResponseException.class, () -> f.number("n"));
    assertThrows(MalformedResponseException.class, () -> f.integer("i"));
    assertThrows(MalformedResponseException.class, () -> f.instant("t"));
    assertThrows(MalformedResponseException.class, () -> f.string("missing"));
    assertEquals(null, f.optString("missing"));
  }

  @Test
  void instantsAcceptOffsets() {
    JsonFields f = JsonFields.root(parse("{\"t\":\"2026-11-15T01:00:00+01:00\"}"), null);
    assertEquals("2026-11-15T00:00:00Z", f.instant("t").toString());
  }
}
