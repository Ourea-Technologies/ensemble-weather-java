// SPDX-License-Identifier: BSD-2-Clause
/**
 * The official Java SDK for the <a href="https://oureawx.com">Ourea</a> probabilistic weather API.
 * Its one runtime dependency is jackson-core (the streaming parser, no data binding).
 */
module com.oureawx.ensembleweather {
  requires transitive java.net.http;
  requires com.fasterxml.jackson.core;
  requires static transitive org.jspecify;

  exports com.oureawx.ensembleweather;
  exports com.oureawx.ensembleweather.model;
}
