package com.prince.betfair.betfair.betting.entities.market

/**
 * Description of a markets key line selection, comprising the selectionId and handicap of the team it is applied to.
 *
 * @param selectionId: (required) Selection ID of the runner in the key line handicap.
 * @param handicap: (required) Handicap value of the key line.
 */
data class KeyLineSelection(
    val selectionId: Long,
    val handicap: Double
)
