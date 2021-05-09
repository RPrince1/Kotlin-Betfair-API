package com.prince.betfair.betfair.betting.enums.market

/**
 * @property INACTIVE: The market has been created but isn't yet available.
 * @property CLOSED: The market has been settled and is no longer available for betting.
 * @property OPEN: The market is open for betting.
 * @property SUSPENDED: The market is suspended and not available for betting.
 */
enum class MarketStatus {
    INACTIVE,
    CLOSED,
    OPEN,
    SUSPENDED
}
