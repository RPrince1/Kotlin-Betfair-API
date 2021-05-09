package com.prince.betfair.betfair.betting.entities.market

data class ExchangePrices(
    val availableToBack: List<PriceSize>?,
    val availableToLay: List<PriceSize>?,
    val tradedVolume: List<PriceSize>?
)
