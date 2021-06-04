package com.prince.betfair.betfair.betting.entities.event

import java.util.*

/**
 * Event
 *
 * @property id: The unique id for the event
 * @property name: The name of the event
 * @property countryCode: The ISO-2 code for the event.  A list of ISO-2 codes is available via
 * http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
 * @property timezone: This is timezone in which the event is taking place.
 * @property venue: venue
 * @property openDate: The scheduled start date and time of the event. This is Europe/London (GMT) by default
 */
data class Event(
    val id: String?,
    val name: String?,
    val countryCode: String?,
    val timezone: String?,
    val venue: String?,
    val openDate: Date?
)
