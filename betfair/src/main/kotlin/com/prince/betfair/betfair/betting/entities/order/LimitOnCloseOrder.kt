package com.prince.betfair.betfair.betting.entities.order

/**
 * Place a new LIMIT_ON_CLOSE bet
 *
 * @property liability: (required) The size of the bet. See Min BSP Liability
 * @property price: (required) The limit price of the bet if LOC
 */
data class LimitOnCloseOrder(
    val liability: Double,
    val price: Double
)