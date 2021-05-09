package com.prince.betfair

import com.prince.betfair.betfair.betting.entities.market.ExBestOffersOverrides
import com.prince.betfair.betfair.betting.entities.market.PriceProjection
import com.prince.betfair.betfair.betting.enums.market.MatchProjection
import com.prince.betfair.betfair.betting.enums.market.OrderProjection
import com.prince.betfair.betfair.betting.enums.market.PriceData
import com.prince.betfair.betfair.betting.enums.market.RollUpModel

fun createMatchProjection() = MatchProjection.NO_ROLLUP

fun createOrderProjection() = OrderProjection.ALL

fun createPriceProjection(
    priceData: Set<PriceData> = setOf(createPriceData()),
    exBestOffersOverrides: ExBestOffersOverrides = createExBestOffersOverrides(),
    virtualise: Boolean = true,
    rolloverStakes: Boolean = true
) =
    PriceProjection(
        priceData,
        exBestOffersOverrides,
        virtualise,
        rolloverStakes
    )

fun createExBestOffersOverrides(
    rollupLiabilityFactor: Int = 1,
    rollupLiabilityThreshold: Double = 2.0,
    bestPricesDepth: Int = 1,
    rollupModel: RollUpModel = createRollUpModel(),
    rollupLimit: Int = 1
) =
    ExBestOffersOverrides(
        rollupLiabilityFactor,
        rollupLiabilityThreshold,
        bestPricesDepth,
        rollupModel,
        rollupLimit
    )

fun createRollUpModel() = RollUpModel.MANAGED_LIABILITY

fun createPriceData() = PriceData.EX_ALL_OFFERS