package com.prince.betfair.betfair.betting.enums.market

/**
 * @property LIMIT: A normal exchange limit order for immediate execution
 * @property LIMIT_ON_CLOSE: Limit order for the auction (SP)
 * @property MARKET_ON_CLOSE: Market order for the auction (SP)
 */
enum class OrderType {
    LIMIT,
    LIMIT_ON_CLOSE,
    MARKET_ON_CLOSE
}
