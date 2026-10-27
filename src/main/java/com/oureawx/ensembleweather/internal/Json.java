// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.StreamReadConstraints;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.oureawx.ensembleweather.model.JsonValue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Parses JSON into {@link JsonValue} trees with jackson-core's streaming parser. There is no data
 * binding: the parser yields tokens and this class builds plain data from them, so no JSON can name
 * a class to instantiate. Nesting depth, number and string lengths are bounded, and duplicate keys
 * are refused.
 */
public final class Json {
  private static final JsonFactory FACTORY =
      JsonFactory.builder()
          .streamReadConstraints(
              StreamReadConstraints.builder()
                  .maxNestingDepth(256)
                  .maxNumberLength(1000)
                  .maxStringLength(16 * 1024 * 1024)
                  .maxNameLength(50_000)
                  .build())
          .enable(StreamReadFeature.STRICT_DUPLICATE_DETECTION)
          .build();

  private Json() {}

  /**
   * Parses one JSON document.
   *
   * @param bytes UTF-8 JSON
   * @return the value
   * @throws MalformedResponseException if the bytes are not one JSON value
   */
  public static JsonValue parse(byte[] bytes) {
    try (JsonParser p = FACTORY.createParser(bytes)) {
      JsonToken first = p.nextToken();
      if (first == null) {
        throw new MalformedResponseException("the body is empty, not JSON");
      }
      JsonValue value = read(p, first);
      if (p.nextToken() != null) {
        throw new MalformedResponseException("the body has data after its JSON value");
      }
      return value;
    } catch (JsonProcessingException e) {
      throw new MalformedResponseException(
          "the body is not valid JSON: " + e.getOriginalMessage(), e);
    } catch (IOException e) {
      throw new MalformedResponseException("the body could not be read: " + e.getMessage(), e);
    }
  }

  private static JsonToken next(JsonParser p) throws IOException {
    JsonToken t = p.nextToken();
    if (t == null) {
      throw new MalformedResponseException("the body ends inside a JSON value");
    }
    return t;
  }

  private static JsonValue read(JsonParser p, JsonToken token) throws IOException {
    switch (token) {
      case START_OBJECT:
        {
          Map<String, JsonValue> members = new LinkedHashMap<>();
          for (JsonToken t = next(p); t != JsonToken.END_OBJECT; t = next(p)) {
            if (t != JsonToken.FIELD_NAME) {
              throw new MalformedResponseException("the body ends inside an object");
            }
            String name = p.currentName();
            members.put(name, read(p, next(p)));
          }
          return JsonValue.ofObject(members);
        }
      case START_ARRAY:
        {
          List<JsonValue> items = new ArrayList<>();
          for (JsonToken t = next(p); t != JsonToken.END_ARRAY; t = next(p)) {
            items.add(read(p, t));
          }
          return JsonValue.ofArray(items);
        }
      case VALUE_STRING:
        return JsonValue.of(p.getText());
      case VALUE_NUMBER_INT:
      case VALUE_NUMBER_FLOAT:
        return JsonValue.of(p.getDecimalValue());
      case VALUE_TRUE:
        return JsonValue.of(true);
      case VALUE_FALSE:
        return JsonValue.of(false);
      case VALUE_NULL:
        return JsonValue.nullValue();
      default:
        throw new MalformedResponseException("unexpected JSON token " + token);
    }
  }
}
