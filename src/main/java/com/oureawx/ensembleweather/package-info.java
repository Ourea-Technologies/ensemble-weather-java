// SPDX-License-Identifier: BSD-2-Clause
/**
 * The official Java SDK for the <a href="https://oureawx.com">Ourea</a> probabilistic weather API.
 *
 * <p>Start with {@link com.oureawx.ensembleweather.Client}. Every forecast value is a distribution
 * ({@link com.oureawx.ensembleweather.types.Quantiles}); ask the server for the probabilities
 * ({@link com.oureawx.ensembleweather.ProbabilityTerm}) and window totals ({@link
 * com.oureawx.ensembleweather.TotalTerm}) you need: the SDK never computes statistics itself.
 * Errors are unchecked {@link com.oureawx.ensembleweather.OureaException}s, one subclass per
 * category.
 */
@NullMarked
package com.oureawx.ensembleweather;

import org.jspecify.annotations.NullMarked;
