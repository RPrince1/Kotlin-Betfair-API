package com.prince.betfair.betfair.betting.entities.event

/**
 * Event Result
 *
 * @property event
 * @property marketCount: Count of markets associated with this event
 */
data class EventResult(
    val event: Event?,
    val marketCount: Int?
)