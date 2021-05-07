package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.entities.competition.Competition
import com.prince.betfair.betfair.betting.entities.event.Event
import com.prince.betfair.betfair.betting.entities.event.EventType
import java.util.*

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
