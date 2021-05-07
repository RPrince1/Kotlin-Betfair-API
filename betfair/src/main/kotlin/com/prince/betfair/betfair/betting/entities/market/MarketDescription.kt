package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.enums.MarketBettingType
import java.util.*

data class MarketDescription(
    val persistenceEnabled: Boolean,
    val bspMarket: Boolean,
    val marketTime: Date,
    val suspendTime: Date,
    val settleTime: Date,
    val bettingType: MarketBettingType,
    val turnInPlayEnabled: Boolean,
    val marketType: String,
    val regulator: String,
    val marketBaseRate: Double,
    val discountAllowed: Boolean,
    val wallet: String,
    val rules: String,
    val rulesHasDate: Boolean,
    val eachWayDivisor: Double,
    val clarifications: String,
    val lineRangeInfo: MarketLineRangeInfo,
    val raceType: String,
    val priceLadderDescription: PriceLadderDescription
)
