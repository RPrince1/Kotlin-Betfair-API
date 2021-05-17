package com.prince.betfair.betfair.betting.entities.order

/**
 * A container representing search results.
 *
 * @property clearedOrders: (required) The list of cleared orders returned by your query. This will be a valid list
 * (i.e. empty or non-empty but never 'null').
 * @property moreAvailable: (required) Indicates whether there are further result items beyond this page. Note that
 * underlying data is highly time-dependent and the subsequent search orders query might return an empty result.
 */
data class ClearedOrderSummaryReport(
    val clearedOrders: List<ClearedOrderSummary>,
    val moreAvailable: Boolean
)
