package com.prince.betfair.betfair.betting.entities.market

import com.fasterxml.jackson.annotation.JsonInclude
import com.prince.betfair.betfair.betting.entities.TimeRange
import com.prince.betfair.betfair.betting.enums.market.MarketBettingType
import com.prince.betfair.betfair.betting.enums.market.OrderStatus

/**
 * @property textQuery: Restrict markets by any text associated with the Event name. You can include a wildcard (*)
 * character as long as it is not the first character. Please note - the textQuery field doesn't evaluate market or
 * selection names.
 * @property exchangeIds: DEPRECATED
 * @property eventTypeIds: Restrict markets by event type associated with the market. (i.e., Football, Hockey, etc)
 * @property eventIds: Restrict markets by the event id associated with the market.
 * @property competitionIds: Restrict markets by the competitions associated with the market.
 * @property marketIds: Restrict markets by the market id associated with the market.
 * @property venues: Restrict markets by the venue associated with the market. Currently only Horse Racing markets have
 * venues.
 * @property bspOnly: Restrict to bsp markets only, if True or non-bsp markets if False. If not specified then returns
 * both BSP and non-BSP markets
 * @property turnInPlayEnabled: Restrict to markets that will turn in play if True or will not turn in play if false. If
 * not specified, returns both.
 * @property inPlayOnly: Restrict to markets that are currently in play if True or are not currently in play if false.
 * If not specified, returns both.
 * @property marketBettingTypes: Restrict to markets that match the betting type of the market (i.e. Odds, Asian
 * Handicap Singles, Asian Handicap Doubles or Line)
 * @property marketCountries: Restrict to markets that are in the specified country or countries
 * @property marketTypeCodes: Restrict to markets that match the type of the market (i.e., MATCH_ODDS, HALF_TIME_SCORE).
 * You should use this instead of relying on the market name as the market type codes are the same in all locales.
 * Please note: All marketTypes are available via the listMarketTypes operations.
 * @property marketStartTime: Restrict to markets with a market start time before or after the specified date
 * @property withOrders: Restrict to markets that I have one or more orders in these status.
 * @property raceTypes: Restrict by race type (i.e. Hurdle, Flat, Bumper, Harness, Chase)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MarketFilter(
    val textQuery: String? = null,
    val eventTypeIds: Set<String>? = null,
    val eventIds: Set<String>? = null,
    val competitionIds: Set<String>? = null,
    val marketIds: Set<String>? = null,
    val venues: Set<String>? = null,
    val bspOnly: Boolean? = null,
    val turnInPlayEnabled: Boolean? = null,
    val inPlayOnly: Boolean? = null,
    val marketBettingTypes: Set<MarketBettingType>? = null,
    val marketCountries: Set<String>? = null,
    val marketTypeCodes: Set<String>? = null,
    val marketStartTime: TimeRange? = null,
    val withOrders: Set<OrderStatus>? = null,
    val raceTypes: Set<String>? = null
)





