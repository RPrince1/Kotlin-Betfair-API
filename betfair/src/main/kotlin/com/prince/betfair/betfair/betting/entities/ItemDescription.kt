package com.prince.betfair.betfair.betting.entities

import java.util.*

/**
 * This object contains some text which may be useful to render a betting history view. It offers no long-term warranty as to the correctness of the text.
 *
 * @property eventTypeDesc: The event type name, translated into the requested locale. Available at EVENT_TYPE groupBy
 * or lower.
 * @property eventDesc: The eventName, or openDate + venue, translated into the requested locale. Available at EVENT
 * groupBy or lower.
 * @property marketDesc: The market name or racing market type ("Win", "To Be Placed (2 places)", "To Be Placed
 * (5 places)" etc) translated into the requested locale. Available at MARKET groupBy or lower.
 * @property marketType: The market type e.g. MATCH_ODDS, PLACE, WIN etc.
 * @property marketStartTime: The start time of the market (in ISO-8601 format, not translated). Available at MARKET
 * groupBy or lower.
 * @property runnerDesc: The runner name, maybe including the handicap, translated into the requested locale. Available
 * at BET groupBy.
 * @property numberOfWinners: The number of winners on a market. Available at BET groupBy.
 * @property eachWayDivisor: The divisor is returned for the marketType EACH_WAY only and refers to the fraction of the
 * win odds at which the place portion of an each way bet is settled
 */
data class ItemDescription(
    val eventTypeDesc: String?,
    val eventDesc: String?,
    val marketDesc: String?,
    val marketType: String?,
    val marketStartTime: Date?,
    val runnerDesc: String?,
    val numberOfWinners: Int?,
    val eachWayDivisor: Double?
)
