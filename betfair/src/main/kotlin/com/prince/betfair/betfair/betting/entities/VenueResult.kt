package com.prince.betfair.betfair.betting.entities

/**
 * Venue Result
 *
 * @property venue: Venue
 * @property marketCount: Count of markets associated with this Venue
 */
data class VenueResult(
    val venue: String?,
    val marketCount: Int?
)
