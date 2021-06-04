package com.prince.betfair.betfair.betting.entities.order

/**
 * Instruction to fully or partially cancel an order (only applies to LIMIT orders).
 * Please note: the CancelInstruction report won't be returned for marketId level cancel instructions.
 *
 * @property: betId: The betId
 * @property: sizeReduction: If supplied then this is a partial cancel.  Should be set to 'null' if no size reduction is
 * required.
 */
data class CancelInstruction(
    val betId: String?,
    val sizeReduction: Double?
)
