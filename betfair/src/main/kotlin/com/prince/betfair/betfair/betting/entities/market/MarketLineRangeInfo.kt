package com.prince.betfair.betfair.betting.entities.market

data class MarketLineRangeInfo(
    val maxUnitValue: Double,
    val minUnitValue: Double,
    val interval: Double,
    val marketUnit: String
)
