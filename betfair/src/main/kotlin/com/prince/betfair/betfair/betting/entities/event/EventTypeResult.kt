package com.prince.betfair.betfair.betting.entities.event

/**
 * EventType Result
 *
 * @property eventType: The ID identifying the Event Type
 * @property marketCount: Count of markets associated with this eventType
 */
data class EventTypeResult(
    val eventType: EventType?,
    val marketCount: Int?
)