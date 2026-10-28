// SPDX-License-Identifier: BSD-2-Clause
package com.oureawx.ensembleweather;

import com.oureawx.ensembleweather.model.NearestLocation;
import java.util.Optional;
import org.jspecify.annotations.Nullable;

/**
 * The caller's plan does not allow the request ({@code PRO_REQUIRED}, HTTP 403), such as a forecast
 * at coordinates without a Pro key. From a coordinate route it may name the {@link
 * #nearestLocation()} whose forecast answers without a key.
 */
public final class PermissionException extends EnsembleWeatherException {
  private static final long serialVersionUID = 1L;

  /** The body's nearest named location, or null (not serialized). */
  private final transient @Nullable NearestLocation nearestLocation;

  /**
   * Creates the exception.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the {@code X-Request-Id}, or null
   */
  public PermissionException(
      String message, int status, @Nullable String code, @Nullable String requestId) {
    this(message, status, code, requestId, null, null, null);
  }

  /**
   * Creates the exception with the error body's details.
   *
   * @param message the human-readable detail
   * @param status the HTTP status
   * @param code the API's error code, or null
   * @param requestId the request id, or null
   * @param docUrl the body's {@code doc_url}, or null
   * @param param the body's {@code param}, or null
   * @param nearestLocation the body's {@code nearest_location}, or null
   */
  public PermissionException(
      String message,
      int status,
      @Nullable String code,
      @Nullable String requestId,
      @Nullable String docUrl,
      @Nullable String param,
      @Nullable NearestLocation nearestLocation) {
    super(ErrorCategory.PERMISSION, message, status, code, requestId, docUrl, param, null);
    this.nearestLocation = nearestLocation;
  }

  /**
   * On {@code PRO_REQUIRED} from a coordinate route, the nearest named location whose forecast
   * answers without a key (within 10 km and 300 m of elevation); call its {@code url}, or {@link
   * EnsembleWeatherClient#getForecast(String)} with its state and slug.
   *
   * @return the location, or empty when none qualifies or the error is not from a coordinate route
   */
  public Optional<NearestLocation> nearestLocation() {
    return Optional.ofNullable(nearestLocation);
  }
}
