// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

/** A 2xx body that is not JSON, or does not match the v1 contract. Never escapes the client. */
public final class MalformedResponseException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates the exception.
   *
   * @param message what was wrong, naming the JSON path
   */
  public MalformedResponseException(String message) {
    super(message);
  }

  /**
   * Creates the exception.
   *
   * @param message what was wrong
   * @param cause the parser's exception
   */
  public MalformedResponseException(String message, Throwable cause) {
    super(message, cause);
  }
}
