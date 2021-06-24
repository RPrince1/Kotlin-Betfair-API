package com.prince.betfair.betfair.betting.entities.competition

/**
 * CountryCode Result
 *
 * @property countryCode: The ISO-2 code for the event.  A list of ISO-2 codes is available via
 * http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
 * @property marketCount: Count of markets associated with this Country Code
 */
data class CountryCodeResult(
    val countryCode: String?,
    val marketCount: Int?
)