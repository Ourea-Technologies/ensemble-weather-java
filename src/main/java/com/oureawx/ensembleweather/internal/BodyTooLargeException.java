// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather.internal;

import java.io.IOException;

/** A response body over the size bound: an error, never an out-of-memory. */
public final class BodyTooLargeException extends IOException {
  private static final long serialVersionUID = 1L;

  /** The bound: 64 MiB. */
  public static final long MAX_BODY_BYTES = 64L * 1024 * 1024;

  /** The response's status. */
  private final int status;

  /**
   * Creates the exception.
   *
   * @param status the response's HTTP status
   */
  public BodyTooLargeException(int status) {
    super("the response body is over the SDK's 64 MiB bound");
    this.status = status;
  }

  /**
   * The response's status.
   *
   * @return the HTTP status
   */
  public int status() {
    return status;
  }
}
