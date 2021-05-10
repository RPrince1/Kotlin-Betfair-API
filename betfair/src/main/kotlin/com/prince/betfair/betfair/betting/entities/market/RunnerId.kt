package com.prince.betfair.betfair.betting.entities.market

/**
 * This object contains the unique identifier for a runner
 *
 * @property marketId: (required) The id of the market bet on
 * @property selectionId: (required) The id of the selection bet on
 * @property handicap: The handicap associated with the runner in case of asian handicap markets, otherwise returns '0.0'.
 */
data class RunnerId(
    val marketId: String,
    val selectionId: Long,
    val handicap: Double
)