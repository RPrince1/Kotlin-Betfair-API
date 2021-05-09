package com.prince.betfair.betfair.betting

import com.prince.betfair.betfair.betting.entities.*
import com.prince.betfair.betfair.betting.entities.competition.Competition
import com.prince.betfair.betfair.betting.entities.competition.CompetitionResult
import com.prince.betfair.betfair.betting.entities.competition.CountryCodeResult
import com.prince.betfair.betfair.betting.entities.event.Event
import com.prince.betfair.betfair.betting.entities.event.EventResult
import com.prince.betfair.betfair.betting.entities.event.EventType
import com.prince.betfair.betfair.betting.entities.event.EventTypeResult
import com.prince.betfair.betfair.betting.entities.market.*
import com.prince.betfair.betfair.betting.enums.*
import com.prince.betfair.betfair.betting.enums.market.MarketBettingType
import com.prince.betfair.betfair.betting.enums.market.MarketProjection
import com.prince.betfair.betfair.betting.enums.market.MarketSort
import com.prince.betfair.betfair.betting.enums.market.PriceLadderType
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.config.JacksonConfiguration
import com.prince.betfair.createMatchProjection
import com.prince.betfair.createOrderProjection
import com.prince.betfair.createPriceProjection
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.CapturingSlot
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.time.Instant
import java.util.*

class BettingTest : StringSpec({

    val clientMock = mockk<OkHttpClient>(relaxUnitFun = true)
    val response = mockk<Response>(relaxUnitFun = true)

    val sessionToken = "sessionToken"
    val appKey = "appKey"

    @AnnotationSpec.AfterEach
    fun after() {
        clearAllMocks()
    }

    //listEventTypes
    "Given a 200 response, when listEventTypes is called then List<EventTypeResult> is returned" {
        val eventType = EventType("1", "Horse Racing")
        val expectedEventTypeResult = EventTypeResult(eventType, 20701)

        val method = "listEventTypes"
        val marketFilter = MarketFilter()
        val locale = null
        val maxResults = 10
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"eventType":{"id":"1","name":"Horse Racing"},"marketCount":20701}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listEventTypes(marketFilter, locale, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedEventTypeResult)
    }

    "Given a non-200 response, when listEventTypes is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listEventTypes(marketFilter, null, 10, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listEventTypes is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listEventTypes(marketFilter, null, 10, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listCompetitions
    "Given a 200 response, when listCompetitions is called then List<CompetitionResult> is returned" {
        val competition = Competition("1", "Bread winners cup")
        val expectedCompetitionResult = CompetitionResult(competition, 19, "BRA")

        val method = "listCompetitions"
        val marketFilter = MarketFilter()
        val locale = null
        val maxResults = 10
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"competition":{"id":"1","name":"Bread winners cup"},"marketCount":19,"competitionRegion":"BRA"}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listCompetitions(marketFilter, locale, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedCompetitionResult)
    }

    "Given a non-200 response, when listCompetitions is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listCompetitions(marketFilter, null, 10, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listCompetitions is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listCompetitions(marketFilter, null, 10, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listTimeRanges
    "Given a 200 response, when listTimeRanges is called then List<TimeRangeResult> is returned" {
        val from = Date.from(Instant.now().minusMillis(999999999))
        val to = Date.from(Instant.now())
        val timeRange = TimeRange(from, to)
        val expectedTimeRangeResult = TimeRangeResult(timeRange, 2)

        val method = "listTimeRanges"
        val marketFilter = MarketFilter()
        val granularity = TimeGranularity.DAYS
        val maxResults = 1
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("granularity", granularity),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"timeRange":{"from":"${from.toInstant()}","to":"${to.toInstant()}"},"marketCount":2}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listTimeRanges(marketFilter, granularity, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedTimeRangeResult)
    }

    "Given a non-200 response, when listTimeRanges is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listTimeRanges(marketFilter, TimeGranularity.DAYS, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listTimeRanges is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listCompetitions(marketFilter, null, 10, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listEvents
    "Given a 200 response, when listEvents is called then List<EventResult> is returned" {
        val id = "123"
        val name = "Set 02"
        val countryCode = "CG"
        val timezone = "GMT"
        val venue = "Oxenberg"
        val openDate = Date.from(Instant.now())
        val event = Event(id, name, countryCode, timezone, venue, openDate)
        val marketCount = 2
        val expectedEventResult = EventResult(event, marketCount)

        val method = "listEvents"
        val marketFilter = MarketFilter()
        val locale = null
        val maxResults = 1
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"event":{"id":"$id","name":"$name","countryCode":"$countryCode","timezone":"$timezone","venue":"$venue",
            "openDate":"${openDate.toInstant()}"},"marketCount":$marketCount}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listEvents(marketFilter, locale, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedEventResult)
    }

    "Given a non-200 response, when listEvents is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listEvents(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listEvents is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listEvents(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listMarketTypes
    "Given a 200 response, when listMarketTypes is called then List<MarketTypeResult> is returned" {
        val marketType = "NONSPORT"
        val marketCount = 85
        val expectedMarketTypeResult = MarketTypeResult(marketType, marketCount)

        val method = "listMarketTypes"
        val marketFilter = MarketFilter()
        val locale = null
        val maxResults = 1
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"marketType":"$marketType","marketCount":$marketCount}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listMarketTypes(marketFilter, locale, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedMarketTypeResult)
    }

    "Given a non-200 response, when listMarketTypes is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listMarketTypes(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listMarketTypes is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listEvents(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listCountries
    "Given a 200 response, when listCountries is called then List<CountryCodeResult> is returned" {
        val countryCode = "GB"
        val marketCount = 3796
        val expectedCountryCodeResult = CountryCodeResult(countryCode, marketCount)

        val method = "listCountries"
        val marketFilter = MarketFilter()
        val locale = null
        val maxResults = 1
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"countryCode":"$countryCode","marketCount":$marketCount}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listCountries(marketFilter, locale, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedCountryCodeResult)
    }

    "Given a non-200 response, when listCountries is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listCountries(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listCountries is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listCountries(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listVenues
    "Given a 200 response, when listVenues is called then List<CountryCodeResult> is returned" {
        val venue = "Vue Cinema"
        val marketCount = 3796
        val expectedVenueResult = VenueResult(venue, marketCount)

        val method = "listVenues"
        val marketFilter = MarketFilter()
        val locale = null
        val maxResults = 1
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"venue":"$venue","marketCount":$marketCount}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listVenues(marketFilter, locale, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedVenueResult)
    }

    "Given a non-200 response, when listVenues is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listVenues(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listVenues is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listVenues(marketFilter, null, 1, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listMarketCatalogue
    //TODO reduce size of this test
    "Given a 200 response, when listMarketCatalogue is called then List<MarketCatalogue> is returned" {
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
        val expectedMarketCatalogue = MarketCatalogue(
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

        val method = "listMarketCatalogue"
        val marketFilter = MarketFilter()
        val marketProjection = setOf(MarketProjection.MARKET_DESCRIPTION)
        val sort = MarketSort.FIRST_TO_START
        val locale = null
        val maxResults = 1
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("marketProjection", marketProjection),
                Pair("sort", sort),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val jsonResult = """
            [{"marketId":"$marketId","marketName":"$marketName","marketStartTime":"${marketStartTime.toInstant()}",
            "description":{"persistenceEnabled":$persistenceEnabled,"bspMarket":$bspMarket,"marketTime":"${marketTime.toInstant()}",
            "suspendTime":"${suspendTime.toInstant()}","settleTime":"${settleTime.toInstant()}","bettingType":"$bettingType",
            "turnInPlayEnabled":$turnInPlayEnabled,
            "marketType":"$marketType","regulator":"$regulator","marketBaseRate":$marketBaseRate,"discountAllowed":$discountAllowed,
            "wallet":"$wallet","rules":"$rules","rulesHasDate":$rulesHasDate,"eachWayDivisor":$eachWayDivisor,
            "clarifications":"$clarifications","lineRangeInfo":{"maxUnitValue":$maxUnitValue,"minUnitValue":$minUnitValue,
            "interval":$interval,"marketUnit":"$marketUnit"},"raceType":"$raceType",
            "priceLadderDescription":{"priceLadderType":"$priceLadderType"}},"totalMatched":$totalMatched,
            "runners":[{"selectionId":$selectionId,"runnerName":"$runnerName","handicap":$handicap,"sortPriority":$sortPriority,
            "metadata":{"$metaData1":"$metaData2"}}],"eventType":{"id":"$eventId","name":"$eventName"},
            "competition":{"id":"$competitionId","name":"$competitionName"},"event":{"id":"$eventId","name":"$eventName",
            "countryCode":"$countryCode","timezone":"$timezone","venue":"$venue","openDate":"${openDate.toInstant()}"}}]
        """.trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result =
            betting.listMarketCatalogue(marketFilter, marketProjection, sort, locale, maxResults, sessionToken, appKey)

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedMarketCatalogue)
    }

    "Given a non-200 response, when listMarketCatalogue is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val marketProjection = setOf(MarketProjection.MARKET_DESCRIPTION)
        val sort = MarketSort.FIRST_TO_START
        val locale = null
        val maxResults = 1
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listMarketCatalogue(marketFilter, marketProjection, sort, locale, maxResults, sessionToken, appKey)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listMarketCatalogue is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val marketProjection = setOf(MarketProjection.MARKET_DESCRIPTION)
        val sort = MarketSort.FIRST_TO_START
        val locale = null
        val maxResults = 1
        val betting = Betting(clientMock)

        val exception = shouldThrow<APINGException> {
            betting.listMarketCatalogue(marketFilter, marketProjection, sort, locale, maxResults, sessionToken, appKey)
        }

        exception.message shouldBe "Response body is null"
    }

    //listMarketBook
    "Given a 200 response, when listMarketBook is called then List<MarketBook> is returned" {
        val expectedMarketBook = createMarketBook()

        val marketIds = listOf("1.163016936")
        val priceProjection = createPriceProjection()
        val orderProjection = createOrderProjection()
        val matchProjection = createMatchProjection()
        val includeOverallPosition = true
        val partitionMatchedByStrategyRef = true
        val customerStrategy = setOf("STRAT")
        val currencyCode = "GBP"
        val locale = null
        val matchedSince = Date.from(Instant.now())
        val betIds = setOf("1.163016936")

        val method = "listMarketBook"
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("marketIds", marketIds),
                Pair("priceProjection", priceProjection),
                Pair("orderProjection", orderProjection),
                Pair("matchProjection", matchProjection),
                Pair("includeOverallPosition", includeOverallPosition),
                Pair("partitionMatchedByStrategyRef", partitionMatchedByStrategyRef),
                Pair("customerStrategyRefs", customerStrategy),
                Pair("currencyCode", currencyCode),
                Pair("locale", locale),
                Pair("matchedSince", matchedSince),
                Pair("betIds", betIds)
            )
        )

        val jsonResult = createMarketBookJson()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listMarketBook(
                marketIds,
                priceProjection,
                orderProjection,
                matchProjection,
                includeOverallPosition,
                partitionMatchedByStrategyRef,
                customerStrategy,
                currencyCode,
                locale,
                matchedSince,
                betIds,
                sessionToken,
                appKey
            )

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url
        result shouldBe listOf(expectedMarketBook)
    }
})

private fun expectedRequest(method: String, sessionToken: String, applicationKey: String, body: Map<String, Any?>) =
    Request.Builder()
        .url("https://api.betfair.com/exchange/betting/rest/v1.0/$method/")
        .addHeader("X-Authentication", sessionToken)
        .addHeader("X-Application", applicationKey)
        .addHeader("Content-Type", "application/json")
        .addHeader("Accept", "application/json")
        .post(
            JacksonConfiguration().mapper().writeValueAsString(
                body
            ).toRequestBody()
        )
        .build()