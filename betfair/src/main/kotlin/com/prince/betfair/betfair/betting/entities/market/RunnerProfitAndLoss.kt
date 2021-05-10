package com.prince.betfair.betfair.betting.entities.market

/**
 * Profit and loss if selection is wins or loses
 *
 * @param selectionId: The unique identifier for the selection
 * @param ifWin: Profit or loss for the market if this selection is the winner.
 * @param ifLose: Profit or loss for the market if this selection is the loser. Only returned for multi-winner odds markets.
 * @param ifPlace: Profit or loss for the market if this selection is placed. Applies to marketType EACH_WAY only.
 */
data class RunnerProfitAndLoss(
    val selectionId: Long?,
    val ifWin: Double?,
    val ifLose: Double?,
    val ifPlace: Double?
)