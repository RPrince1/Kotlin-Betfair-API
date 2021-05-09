package com.prince.betfair.betfair.betting.entities.market

/**
 * Information about the Betfair Starting Price. Only available in BSP markets
 *
 * @property nearPrice: What the starting price would be if the market was reconciled now taking into account the SP
 * bets as well as unmatched exchange bets on the same selection in the exchange. This data is cached and update every
 * 60 seconds. Please note: Type Double may contain numbers, INF, -INF, and NaN.
 * @property farPrice: What the starting price would be if the market was reconciled now taking into account only the
 * currently place SP bets. The Far Price is not as complicated but not as accurate and only accounts for money on the
 * exchange at SP. This data is cached and updated every 60 seconds. Please note: Type Double may contain numbers, INF,
 * -INF, and NaN.
 * @property backStakeTaken: The total amount of back bets matched at the actual Betfair Starting Price.
 * Pre-reconciliation, this field is zero for all prices except 1.01 (for Market on Close bets) and at the limit price
 * for any Limit on Close bets.
 * @property layLiabilityTaken: The lay amount matched at the actual Betfair Starting Price. Pre-reconciliation, this
 * field is zero for all prices except 1000 (for Market on Close bets) and at the limit price for any Limit on Close
 * bets.
 * @property actualSP: The final BSP price for this runner. Only available for a BSP market that has been reconciled.
 * Please note: for REMOVED runners the actualSP will be returned as 'NaN. Value may be returned as 'Infinity' if no BSP
 * can be calculated.
 */
data class StartingPrices(
    val nearPrice: Double?,
    val farPrice: Double?,
    val backStakeTaken: List<PriceSize>?,
    val layLiabilityTaken: List<PriceSize>?,
    val actualSP: Double?
)
