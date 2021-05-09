package com.prince.betfair.betfair.betting.enums.market

/**
 * @property ALL: EXECUTABLE and EXECUTION_COMPLETE orders
 * @property EXECUTABLE: An order that has a remaining unmatched portion. This is either a fully unmatched or partially matched bet (order)
 * @property EXECUTION_COMPLETE: An order that does not have any remaining unmatched portion.  This is a fully matched bet (order).
 */
enum class OrderProjection {
    ALL,
    EXECUTABLE,
    EXECUTION_COMPLETE
}