package com.prince.betfair.betfair.betting.enums.market

/**
 * @property PENDING: An asynchronous order is yet to be processed. Once the bet has been processed by the exchange
 * (including waiting for any in-play delay), the result will be reported and available on the
 * Exchange Stream API and API NG.
 * Not a valid search criteria on MarketFilter
 * @property EXECUTION_COMPLETE: An order that does not have any remaining unmatched portion.
 * @property EXECUTABLE: An order that has a remaining unmatched portion.
 * @property EXPIRED: The order is no longer available for execution due to its time in force constraint.
 * In the case of FILL_OR_KILL orders, this means the order has been killed because it could not be filled to your
 * specifications.
 * Not a valid search criteria on MarketFilter
 */
enum class OrderStatus {
    PENDING,
    EXECUTION_COMPLETE,
    EXECUTABLE,
    EXPIRED
}