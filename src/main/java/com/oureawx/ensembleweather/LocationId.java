// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.util.Locale;
import java.util.Objects;
import org.jspecify.annotations.Nullable;

/**
 * A named location: its state and its catalog slug, as in {@code or/mt-hood-meadows}. A two-letter
 * state code is lowercased (the canonical form); a state name is passed through, and the server
 * redirects it. Both are sent as percent-encoded path segments.
 */
public final class LocationId {
  private final String state;
  private final String slug;

  private LocationId(String state, String slug) {
    if (state.isEmpty() || slug.isEmpty()) {
      throw new UsageException("a location needs a state and a slug, such as or/mt-hood-meadows");
    }
    this.state = canonicalState(state);
    this.slug = slug;
  }

  private static String canonicalState(String state) {
    if (state.length() == 2 && isAsciiLetter(state.charAt(0)) && isAsciiLetter(state.charAt(1))) {
      return state.toLowerCase(Locale.ROOT);
    }
    return state;
  }

  private static boolean isAsciiLetter(char c) {
    return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
  }

  /**
   * A location from its state and slug.
   *
   * @param state the USPS code ({@code or}) or the state's name
   * @param slug the catalog slug ({@code mt-hood-meadows})
   * @return the location
   */
  public static LocationId of(String state, String slug) {
    return new LocationId(
        Objects.requireNonNull(state, "state"), Objects.requireNonNull(slug, "slug"));
  }

  /**
   * Parses {@code "state/slug"}, split on its one slash.
   *
   * @param location such as {@code or/mt-hood-meadows}
   * @return the location
   * @throws UsageException if the string does not hold exactly one slash
   */
  public static LocationId parse(String location) {
    Objects.requireNonNull(location, "location");
    int slash = location.indexOf('/');
    if (slash < 0 || location.indexOf('/', slash + 1) >= 0) {
      throw new UsageException(
          "a location is \"state/slug\" with one slash, such as or/mt-hood-meadows, not \""
              + location
              + "\"");
    }
    return new LocationId(location.substring(0, slash), location.substring(slash + 1));
  }

  /**
   * The state as it is sent.
   *
   * @return the lowercase USPS code, or the state name as given
   */
  public String state() {
    return state;
  }

  /**
   * The slug.
   *
   * @return the slug
   */
  public String slug() {
    return slug;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    return o instanceof LocationId
        && ((LocationId) o).state.equals(state)
        && ((LocationId) o).slug.equals(slug);
  }

  @Override
  public int hashCode() {
    return Objects.hash(state, slug);
  }

  /** Returns {@code state/slug}. */
  @Override
  public String toString() {
    return state + "/" + slug;
  }
}
