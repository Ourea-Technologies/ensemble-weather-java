// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.jspecify.annotations.Nullable;

/**
 * Every item of a cursor-paged list, fetched lazily: the first page is requested when iteration
 * starts, and each next page only when the previous one is used up, until {@code next_cursor} is
 * absent. Iterate it, or {@link #stream()} it and stop early with {@code limit}; each iteration
 * starts again from the first page. A failed page throws its {@link EnsembleWeatherException} from
 * {@code hasNext()}.
 *
 * <pre>{@code
 * for (CatalogLocation location : client.iterateCatalogLocations(CatalogLocationQuery.all())) {
 *   System.out.println(location.slug());
 * }
 * }</pre>
 *
 * @param <T> the item type
 */
public final class PagedIterable<T> implements Iterable<T> {
  /** One page: its items and the cursor of the next one. */
  static final class Page<T> {
    final List<T> items;
    final @Nullable String nextCursor;

    Page(List<T> items, @Nullable String nextCursor) {
      this.items = items;
      this.nextCursor = nextCursor;
    }
  }

  /** Fetches the page at a cursor (null for the first). */
  interface Fetcher<T> {
    Page<T> fetch(@Nullable String cursor);
  }

  private final @Nullable String firstCursor;
  private final Fetcher<T> fetcher;

  PagedIterable(@Nullable String firstCursor, Fetcher<T> fetcher) {
    this.firstCursor = firstCursor;
    this.fetcher = fetcher;
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private @Nullable Iterator<T> page;
      private @Nullable String cursor = firstCursor;
      private boolean last;

      @Override
      public boolean hasNext() {
        while (page == null || !page.hasNext()) {
          if (last) {
            return false;
          }
          String at = cursor;
          Page<T> next = fetcher.fetch(at);
          page = next.items.iterator();
          if (next.nextCursor == null || next.nextCursor.isEmpty()) {
            last = true;
          } else if (next.nextCursor.equals(at)) {
            throw new UnavailableException(
                "the list's next_cursor did not advance (" + at + "); stopping", 200, null, null);
          } else {
            cursor = next.nextCursor;
          }
        }
        return true;
      }

      @Override
      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        Iterator<T> p = page;
        if (p == null) {
          throw new NoSuchElementException();
        }
        return p.next();
      }
    };
  }

  /**
   * The items as a sequential, lazy stream.
   *
   * @return a stream that fetches pages as it is consumed
   */
  public Stream<T> stream() {
    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(iterator(), Spliterator.ORDERED | Spliterator.NONNULL),
        false);
  }
}
