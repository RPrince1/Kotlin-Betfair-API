package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.enums.order.PersistenceType

/**
 * Instruction to update LIMIT bet's persistence of an order that do not affect exposure
 *
 * betId: (required) Unique identifier for the bet
 * newPersistenceType: (required) The new persistence type to update this bet to
 */
data class UpdateInstruction(
    val betId: String,
    val newPersistenceType: PersistenceType
)
