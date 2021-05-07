package com.prince.betfair.betfair.betting.entities.market

import com.fasterxml.jackson.annotation.JsonInclude
import com.prince.betfair.betfair.betting.entities.TimeRange
import com.prince.betfair.betfair.betting.enums.MarketBettingType
import com.prince.betfair.betfair.betting.enums.OrderStatus

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





