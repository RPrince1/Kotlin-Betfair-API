package com.prince.betfair.betfair.betting.entities.market

/**
 * Profit and loss in a market
 *
 * @param marketId: The unique identifier for the market
 * @param commissionApplied: The commission rate applied to P&L values. Only returned if netOfCommision option is requested
 * @param profitAndLosses: Calculated profit and loss data.
 */
data class MarketProfitAndLoss(
    val marketId: String?,
    val commissionApplied: Double?,
    val profitAndLosses: List<RunnerProfitAndLoss>?
)
