package com.prince.betfair.betfair.betting.entities

import java.util.*

/**
 * A list of ISO-2 codes available for countryCode via http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
 */
data class Event(
    val id: String,
    val name: String,
    val countryCode: String?,
    val timezone: String,
    val venue: String?,
    val openDate: Date
)
