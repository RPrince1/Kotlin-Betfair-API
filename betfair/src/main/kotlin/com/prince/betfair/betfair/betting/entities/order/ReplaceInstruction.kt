package com.prince.betfair.betfair.betting.entities.order

/**
 * Instruction to replace a LIMIT or LIMIT_ON_CLOSE order at a new price. Original order will be cancelled and a new
 * order placed at the new price for the remaining stake.
 *
 * @property betId: (required) Unique identifier for the bet
 * @property newPrice: (required) The price to replace the bet at
 */
data class ReplaceInstruction(
    val betId: String,
    val newPrice: Double
)
