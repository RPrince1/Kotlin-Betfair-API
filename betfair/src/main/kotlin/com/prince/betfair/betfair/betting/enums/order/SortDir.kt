package com.prince.betfair.betfair.betting.enums.order

/**
 * @property EARLIEST_TO_LATEST: Order from earliest value to latest e.g. lowest betId is first in the results.
 * @property LATEST_TO_EARLIEST: Order from the latest value to the earliest e.g. highest betId is first in the results.
 */
enum class SortDir {
    EARLIEST_TO_LATEST,
    LATEST_TO_EARLIEST
}
