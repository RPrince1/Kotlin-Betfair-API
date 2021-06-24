package com.prince.betfair.betfair.betting.enums.market

/**
 * @property MINIMUM_TRADED: Minimum traded volume
 * @property MAXIMUM_TRADED: Maximum traded volume
 * @property MINIMUM_AVAILABLE: Minimum available to match
 * @property MAXIMUM_AVAILABLE: Maximum available to match
 * @property FIRST_TO_START: The closest markets based on their expected start time
 * @property LAST_TO_START: The most distant markets based on their expected start time
 */
enum class MarketSort {
    MINIMUM_TRADED,
    MAXIMUM_TRADED,
    MINIMUM_AVAILABLE,
    MAXIMUM_AVAILABLE,
    FIRST_TO_START,
    LAST_TO_START
}
