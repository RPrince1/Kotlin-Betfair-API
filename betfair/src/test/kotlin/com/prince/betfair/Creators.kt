package com.prince.betfair

import com.prince.betfair.betfair.betting.entities.TimeRange
import com.prince.betfair.betfair.betting.entities.competition.Competition
import com.prince.betfair.betfair.betting.entities.event.Event
import com.prince.betfair.betfair.betting.entities.event.EventType
import com.prince.betfair.betfair.betting.entities.market.*
import com.prince.betfair.betfair.betting.enums.market.*
import java.time.Instant
import java.util.*

fun createMarketCatalogue(): MarketCatalogue {
    val marketId = "1245678"
    val marketName = "Exit dates"
    val marketStartTime = Date.from(Instant.now())
    val persistenceEnabled = true
    val bspMarket = true
    val marketTime = Date.from(Instant.now())
    val suspendTime = Date.from(Instant.now())
    val settleTime = Date.from(Instant.now())
    val bettingType = MarketBettingType.ODDS
    val turnInPlayEnabled = true
    val marketType = "marketType"
    val regulator = "Warren G"
    val marketBaseRate = 5.0
    val discountAllowed = true
    val wallet = "skint"
    val rules = "1st rule of fight club"
    val rulesHasDate = true
    val eachWayDivisor = 2.0
    val clarifications = "none"
    val maxUnitValue = 2.0
    val minUnitValue = 1.0
    val interval = 0.5
    val marketUnit = "marketUnit"
    val lineRangeInfo = MarketLineRangeInfo(maxUnitValue, minUnitValue, interval, marketUnit)
    val raceType = "Backwards"
    val priceLadderType = PriceLadderType.CLASSIC
    val priceLadderDescription = PriceLadderDescription(priceLadderType)
    val description = MarketDescription(
        persistenceEnabled, bspMarket, marketTime, suspendTime, settleTime, bettingType,
        turnInPlayEnabled, marketType, regulator, marketBaseRate, discountAllowed, wallet, rules, rulesHasDate,
        eachWayDivisor, clarifications, lineRangeInfo, raceType, priceLadderDescription
    )
    val totalMatched = 2.0
    val selectionId = 1L
    val runnerName = "Mo"
    val handicap = 2.0
    val sortPriority = 1
    val metaData1 = "meta"
    val metaData2 = "data"
    val metadata = mapOf(Pair(metaData1, metaData2))
    val runnerCatalog = RunnerCatalog(selectionId, runnerName, handicap, sortPriority, metadata)
    val runners = listOf(runnerCatalog)
    val eventId = "1234567"
    val eventName = "Disco"
    val eventType = EventType(eventId, eventName)
    val competitionId = "876543"
    val competitionName = "Dancing Cup"
    val competition = Competition(competitionId, competitionName)
    val countryCode = "GB"
    val timezone = "GMT"
    val venue = "Bowlarama Venue"
    val openDate = Date.from(Instant.now())
    val event = Event(eventId, eventName, countryCode, timezone, venue, openDate)
    return MarketCatalogue(
        marketId,
        marketName,
        marketStartTime,
        description,
        totalMatched,
        runners,
        eventType,
        competition,
        event
    )
}

fun createMatchProjection() = MatchProjection.NO_ROLLUP

fun createOrderProjection() = OrderProjection.ALL

fun createPriceProjection(
    priceData: Set<PriceData> = setOf(createPriceData()),
    exBestOffersOverrides: ExBestOffersOverrides = createExBestOffersOverrides(),
    virtualise: Boolean = true,
    rolloverStakes: Boolean = true
) =
    PriceProjection(
        priceData,
        exBestOffersOverrides,
        virtualise,
        rolloverStakes
    )

fun createExBestOffersOverrides(
    rollupLiabilityFactor: Int = 1,
    rollupLiabilityThreshold: Double = 2.0,
    bestPricesDepth: Int = 1,
    rollupModel: RollUpModel = createRollUpModel(),
    rollupLimit: Int = 1
) =
    ExBestOffersOverrides(
        rollupLiabilityFactor,
        rollupLiabilityThreshold,
        bestPricesDepth,
        rollupModel,
        rollupLimit
    )

fun createRollUpModel() = RollUpModel.MANAGED_LIABILITY

fun createPriceData() = PriceData.EX_ALL_OFFERS

fun createRunnerId(
    marketId: String = "marketId",
    selectionId: Long = 12346,
    handicap: Double = 2.0
) = RunnerId(marketId, selectionId, handicap)

fun createTimeRange(
    from: Date = Date.from(Instant.now().minusMillis(999999999)),
    to: Date = Date.from(Instant.now())
) = TimeRange(
    from,
    to
)