package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.entities.competition.Competition
import com.prince.betfair.betfair.betting.entities.event.Event
import com.prince.betfair.betfair.betting.entities.event.EventType
import java.util.*

/**
 * Information about a market
 *
 * @property marketId: (required) The unique identifier for the market. MarketId's are prefixed with '1.'
 * @property marketName: (required) The name of the market
 * @property marketStartTime: The time this market starts at, only returned when the MARKET_START_TIME enum is passed in
 * the marketProjections
 * @property description: Details about the market
 * @property totalMatched: The total amount of money matched on the market
 * @property runners: The runners (selections) contained in the market
 * @property eventType: The Event Type the market is contained within
 * @property competition: The competition the market is contained within. Usually only applies to Football competitions
 * @property event: The event the market is contained within
 */
data class MarketCatalogue(
    val marketId: String,
    val marketName: String,
    val marketStartTime: Date?,
    val description: MarketDescription?,
    val totalMatched: Double?,
    val runners: List<RunnerCatalog>?,
    val eventType: EventType?,
    val competition: Competition?,
    val event: Event?
)
