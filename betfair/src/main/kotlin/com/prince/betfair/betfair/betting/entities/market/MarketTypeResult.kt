package com.prince.betfair.betfair.betting.entities.market

/**
 * MarketType Result
 *
 * @property marketType: Market Type
 * @property marketCount: Count of markets associated with this marketType
 */
data class MarketTypeResult(
    val marketType: String?,
    val marketCount: Int?
)
