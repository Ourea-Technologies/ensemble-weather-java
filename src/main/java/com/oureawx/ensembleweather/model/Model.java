// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.model;

import java.util.Arrays;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The value semantics every model shares: {@code equals}, {@code hashCode} and {@code toString}
 * over the model's fields, as a record would have them.
 */
abstract class Model {
  Model() {}

  /** The field values, in declaration order. */
  abstract @Nullable Object[] values();

  /** The field names, in the same order. */
  abstract String[] names();

  static <T> T require(@Nullable T value, String wireName) {
    if (value == null) {
      throw new IllegalStateException(wireName + " is required");
    }
    return value;
  }

  static String wire(Optional<String> value) {
    return value.orElseThrow(
        () -> new IllegalArgumentException("UNKNOWN has no wire value; pass the raw string"));
  }

  // Every model is a final class, so the exact class is the right test of equality.
  @SuppressWarnings("EqualsGetClass")
  @Override
  public final boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || other.getClass() != getClass()) {
      return false;
    }
    return Arrays.equals(values(), ((Model) other).values());
  }

  @Override
  public final int hashCode() {
    return 31 * getClass().hashCode() + Arrays.hashCode(values());
  }

  @Override
  public final String toString() {
    StringBuilder out = new StringBuilder(getClass().getSimpleName()).append('[');
    @Nullable Object[] values = values();
    String[] names = names();
    boolean first = true;
    for (int i = 0; i < names.length; i++) {
      Object value = values[i];
      if (value == null) {
        continue;
      }
      if (!first) {
        out.append(", ");
      }
      first = false;
      out.append(names[i]).append('=').append(value);
    }
    return out.append(']').toString();
  }
}
