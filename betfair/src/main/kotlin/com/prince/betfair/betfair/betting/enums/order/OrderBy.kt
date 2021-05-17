package com.prince.betfair.betfair.betting.enums.order

/**
 * @property BY_BET: @Deprecated Use BY_PLACE_TIME instead. Order by placed time, then bet id.
 * @property BY_MARKET: Order by market id, then placed time, then bet id.
 * @property BY_MATCH_TIME: Order by time of last matched fragment (if any), then placed time, then bet id. Filters out orders which have no matched date. The dateRange filter (if specified) is applied to the matched date.
 * @property BY_PLACE_TIME: Order by placed time, then bet id. This is an alias of to be deprecated BY_BET. The dateRange filter (if specified) is applied to the placed date.
 * @property BY_SETTLED_TIME: Order by time of last settled fragment (if any due to partial market settlement), then by last match time, then placed time, then bet id. Filters out orders which have not been settled. The dateRange filter (if specified) is applied to the settled date.
 * @property BY_VOID_TIME: Order by time of last voided fragment (if any), then by last match time, then placed time, then bet id. Filters out orders which have not been voided. The dateRange filter (if specified) is applied to the voided date.
 */
enum class OrderBy {
    BY_BET,
    BY_MARKET,
    BY_MATCH_TIME,
    BY_PLACE_TIME,
    BY_SETTLED_TIME,
    BY_VOID_TIME
}
