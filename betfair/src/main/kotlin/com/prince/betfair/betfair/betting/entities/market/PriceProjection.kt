package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.enums.market.PriceData

/**
 *  Selection criteria of the returning price data
 *
 *  @property priceData The basic price data you want to receive in the response.
 *  @property exBestOffersOverrides: Options to alter the default representation of best offer prices Applicable to
 *  EX_BEST_OFFERS priceData selection
 *  @property virtualise: Indicates if the returned prices should include virtual prices. Applicable to EX_BEST_OFFERS
 *  and EX_ALL_OFFERS priceData selections, default value is false. Please note: This must be set to 'true' replicate
 *  the display of prices on the Betfair Exchange website.
 *  @property rolloverStakes: Indicates if the volume returned at each price point should be the absolute value or a
 *  cumulative sum of volumes available at the price and all better prices. If unspecified defaults to false. Applicable
 *  to EX_BEST_OFFERS and EX_ALL_OFFERS price projections. Not supported as yet.
 */
data class PriceProjection(
    val priceData: Set<PriceData>,
    val exBestOffersOverrides: ExBestOffersOverrides,
    val virtualise: Boolean,
    val rolloverStakes: Boolean
)
