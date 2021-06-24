package com.prince.betfair.betfair.betting

import com.prince.betfair.*
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
import com.prince.betfair.betfair.betting.enums.market.*
import com.prince.betfair.betfair.betting.enums.order.*
import com.prince.betfair.betfair.betting.enums.order.InstructionReportStatus.*
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.config.JacksonConfiguration
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
import java.io.File
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
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale)
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
        val result = betting.listEventTypes(marketFilter, locale, sessionToken, appKey)

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

        shouldThrow<APINGException> {
            Betting(clientMock).listEventTypes(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listEventTypes is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listEventTypes(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listCompetitions
    "Given a 200 response, when listCompetitions is called then List<CompetitionResult> is returned" {
        val competition = Competition("1", "Bread winners cup")
        val expectedCompetitionResult = CompetitionResult(competition, 19, "BRA")

        val method = "listCompetitions"
        val marketFilter = MarketFilter()
        val locale = "locale"
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale),
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
        val result = betting.listCompetitions(marketFilter, locale, sessionToken, appKey)

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

        shouldThrow<APINGException> {
            Betting(clientMock).listCompetitions(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listCompetitions is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listCompetitions(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
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
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("granularity", granularity)
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
        val result = betting.listTimeRanges(marketFilter, granularity, sessionToken, appKey)

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

        shouldThrow<APINGException> {
            Betting(clientMock)
                .listTimeRanges(
                    filter = MarketFilter(),
                    granularity = TimeGranularity.DAYS,
                    sessionToken = sessionToken,
                    applicationKey = appKey
                )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listTimeRanges is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock)
                .listTimeRanges(
                    filter = MarketFilter(),
                    granularity = TimeGranularity.DAYS,
                    sessionToken = sessionToken,
                    applicationKey = appKey
                )
        }.message shouldBe "Response body is null"
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
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale)
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
        val result = betting.listEvents(marketFilter, locale, sessionToken, appKey)

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

        shouldThrow<APINGException> {
            Betting(clientMock).listEvents(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listEvents is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listEvents(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listMarketTypes
    "Given a 200 response, when listMarketTypes is called then List<MarketTypeResult> is returned" {
        val marketType = "NONSPORT"
        val marketCount = 85
        val expectedMarketTypeResult = MarketTypeResult(marketType, marketCount)

        val method = "listMarketTypes"
        val marketFilter = MarketFilter()
        val locale = null
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale)
            )
        )

        val jsonResult = """[{"marketType":"$marketType","marketCount":$marketCount}]""".trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listMarketTypes(marketFilter, locale, sessionToken, appKey)

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

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketTypes(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listMarketTypes is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketTypes(
                filter = MarketFilter(),
                locale = "locale",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listCountries
    "Given a 200 response, when listCountries is called then List<CountryCodeResult> is returned" {
        val countryCode = "GB"
        val marketCount = 3796
        val expectedCountryCodeResult = CountryCodeResult(countryCode, marketCount)

        val method = "listCountries"
        val marketFilter = MarketFilter()
        val locale = null
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale)
            )
        )

        val jsonResult = """[{"countryCode":"$countryCode","marketCount":$marketCount}]""".trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listCountries(marketFilter, locale, sessionToken, appKey)

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

        shouldThrow<APINGException> {
            Betting(clientMock).listCountries(
                filter = MarketFilter(),
                locale = "GB",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listCountries is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listCountries(
                filter = MarketFilter(),
                locale = "GB",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listVenues
    "Given a 200 response, when listVenues is called then List<CountryCodeResult> is returned" {
        val venue = "Vue Cinema"
        val marketCount = 3796
        val expectedVenueResult = VenueResult(venue, marketCount)

        val method = "listVenues"
        val marketFilter = MarketFilter()
        val locale = null
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("filter", marketFilter),
                Pair("locale", locale)
            )
        )

        val jsonResult = """[{"venue":"$venue","marketCount":$marketCount}]""".trimIndent()

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listVenues(marketFilter, locale, sessionToken, appKey)

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

        shouldThrow<APINGException> {
            Betting(clientMock).listVenues(
                filter = MarketFilter(),
                locale = "GB",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listVenues is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listVenues(
                filter = MarketFilter(),
                locale = "GB",
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listMarketCatalogue
    "Given a 200 response, when listMarketCatalogue is called then List<MarketCatalogue> is returned" {
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

        val jsonResult =
            File("${System.getProperty("user.dir")}/src/test/resources/listMarketCatalogueResponse.json").readText(
                Charsets.UTF_8
            )

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

        result.size shouldBe 10
        result[0].marketId shouldBe "1.160663234"
        result[0].marketName shouldBe "Next Conservative Leader."
        result[0].totalMatched shouldBe 59108.56
        result[0].competition?.id shouldBe "10538818"
        result[0].competition?.name shouldBe "UK - Party Leaders"
    }

    "Given a non-200 response, when listMarketCatalogue is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketCatalogue(
                filter = MarketFilter(),
                marketProjection = setOf(MarketProjection.MARKET_DESCRIPTION),
                sort = MarketSort.FIRST_TO_START,
                locale = null,
                maxResults = 1,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listMarketCatalogue is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketCatalogue(
                filter = MarketFilter(),
                marketProjection = setOf(MarketProjection.MARKET_DESCRIPTION),
                sort = MarketSort.FIRST_TO_START,
                locale = null,
                maxResults = 1,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listMarketBook
    "Given a 200 response, when listMarketBook is called then List<MarketBook> is returned" {
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

        val jsonResult =
            File("${System.getProperty("user.dir")}/src/test/resources/listMarketBookResponse.json").readText(Charsets.UTF_8)

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

        result[0].marketId shouldBe "1.163016936"
        result[0].isMarketDataDelayed shouldBe true
        result[0].status shouldBe MarketStatus.OPEN
        result[0].betDelay shouldBe 0
        result[0].bspReconciled shouldBe false
        result[0].complete shouldBe true
        result[0].inplay shouldBe false
        result[0].numberOfWinners shouldBe 1
        result[0].numberOfRunners shouldBe 10
        result[0].numberOfActiveRunners shouldBe 10
        result[0].lastMatchTime shouldBe Date.from(Instant.parse("2021-05-01T12:45:40.038Z"))
        result[0].totalMatched shouldBe 778.03
        result[0].totalAvailable shouldBe 6354.01
        result[0].crossMatching shouldBe true
        result[0].runnersVoidable shouldBe false
        result[0].version shouldBe 3802804447
        result[0].runners?.size shouldBe 10
        result[0].runners?.get(0)?.selectionId shouldBe 55212
        result[0].runners?.get(0)?.handicap shouldBe 0.0
        result[0].runners?.get(0)?.status shouldBe RunnerStatus.ACTIVE
        result[0].runners?.get(0)?.lastPriceTraded shouldBe 2.7
        result[0].runners?.get(0)?.totalMatched shouldBe 0.0
    }

    "Given a non-200 response, when listMarketBook is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketBook(
                marketIds = listOf("1.163016936"),
                priceProjection = createPriceProjection(),
                orderProjection = createOrderProjection(),
                matchProjection = createMatchProjection(),
                includeOverallPosition = true,
                partitionMatchedByStrategyRef = true,
                customerStrategyRefs = setOf("STRAT"),
                currencyCode = "GBP",
                locale = "ENG",
                matchedSince = Date.from(Instant.now()),
                betIds = setOf("1.163016936"),
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listMarketBook is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketBook(
                marketIds = listOf("1.163016936"),
                priceProjection = createPriceProjection(),
                orderProjection = createOrderProjection(),
                matchProjection = createMatchProjection(),
                includeOverallPosition = true,
                partitionMatchedByStrategyRef = true,
                customerStrategyRefs = setOf("STRAT"),
                currencyCode = "GBP",
                locale = "ENG",
                matchedSince = Date.from(Instant.now()),
                betIds = setOf("1.163016936"),
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listRunnerBook
    "Given a 200 response, when listRunnerBook is called then List<MarketBook> is returned" {
        val marketId = "1.163016936"
        val selectionId = 1234567L
        val handicap = 0.0
        val priceProjection = createPriceProjection()
        val orderProjection = createOrderProjection()
        val matchProjection = createMatchProjection()
        val includeOverallPosition = true
        val partitionMatchedByStrategyRef = true
        val customerStrategyRefs = setOf("STRAT")
        val currencyCode = "GBP"
        val locale = "Other"
        val matchedSince = Date.from(Instant.now())
        val betIds = setOf("1.163016936")

        val method = "listRunnerBook"
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("marketId", marketId),
                Pair("selectionId", selectionId),
                Pair("handicap", handicap),
                Pair("priceProjection", priceProjection),
                Pair("orderProjection", orderProjection),
                Pair("matchProjection", matchProjection),
                Pair("includeOverallPosition", includeOverallPosition),
                Pair("partitionMatchedByStrategyRef", partitionMatchedByStrategyRef),
                Pair("customerStrategyRefs", customerStrategyRefs),
                Pair("currencyCode", currencyCode),
                Pair("locale", locale),
                Pair("matchedSince", matchedSince),
                Pair("betIds", betIds)
            )
        )

        val jsonResult =
            File("${System.getProperty("user.dir")}/src/test/resources/listRunnerBookResponse.json").readText(Charsets.UTF_8)

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listRunnerBook(
            marketId,
            selectionId,
            handicap,
            priceProjection,
            orderProjection,
            matchProjection,
            includeOverallPosition,
            partitionMatchedByStrategyRef,
            customerStrategyRefs,
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

        result[0].marketId shouldBe "1.163016936"
        result[0].isMarketDataDelayed shouldBe true
        result[0].status shouldBe MarketStatus.OPEN
        result[0].betDelay shouldBe 0
        result[0].bspReconciled shouldBe false
        result[0].complete shouldBe true
        result[0].inplay shouldBe false
        result[0].numberOfWinners shouldBe 1
        result[0].numberOfRunners shouldBe 10
        result[0].numberOfActiveRunners shouldBe 10
        result[0].lastMatchTime shouldBe Date.from(Instant.parse("2021-05-01T12:45:40.038Z"))
        result[0].totalMatched shouldBe 778.03
        result[0].totalAvailable shouldBe 6354.01
        result[0].crossMatching shouldBe true
        result[0].runnersVoidable shouldBe false
        result[0].version shouldBe 3802804447
        result[0].runners?.size shouldBe 1
        result[0].runners?.get(0)?.selectionId shouldBe 55212
        result[0].runners?.get(0)?.handicap shouldBe 0.0
        result[0].runners?.get(0)?.status shouldBe RunnerStatus.ACTIVE
        result[0].runners?.get(0)?.lastPriceTraded shouldBe 2.7
        result[0].runners?.get(0)?.totalMatched shouldBe 0.0
    }

    "Given a non-200 response, when listRunnerBook is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        shouldThrow<APINGException> {
            Betting(clientMock).listRunnerBook(
                marketId = "1.163016936",
                selectionId = 1234567L,
                handicap = 0.0,
                priceProjection = createPriceProjection(),
                orderProjection = createOrderProjection(),
                matchProjection = createMatchProjection(),
                includeOverallPosition = true,
                partitionMatchedByStrategyRef = true,
                customerStrategyRefs = setOf("STRAT"),
                currencyCode = "GBP",
                locale = "Other",
                matchedSince = Date.from(Instant.now()),
                betIds = setOf("1.163016936"),
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listRunnerBook is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listRunnerBook(
                marketId = "1.163016936",
                selectionId = 1234567L,
                handicap = 0.0,
                priceProjection = createPriceProjection(),
                orderProjection = createOrderProjection(),
                matchProjection = createMatchProjection(),
                includeOverallPosition = true,
                partitionMatchedByStrategyRef = true,
                customerStrategyRefs = setOf("STRAT"),
                currencyCode = "GBP",
                locale = "Other",
                matchedSince = Date.from(Instant.now()),
                betIds = setOf("1.163016936"),
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listMarketProfitAndLoss
    "Given a 200 response, when listMarketProfitAndLoss is called then List<MarketProfitAndLoss> is returned" {
        val marketIds = setOf("1.163016936")
        val includeSettledBets = true
        val includeBspBets = true
        val netOfCommission = true

        val method = "listMarketProfitAndLoss"
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("marketIds", marketIds),
                Pair("includeSettledBets", includeSettledBets),
                Pair("includeBspBets", includeBspBets),
                Pair("netOfCommission", netOfCommission),
            )
        )

        val jsonResult =
            File("${System.getProperty("user.dir")}/src/test/resources/listMarketProfitAndLossResponse.json").readText(
                Charsets.UTF_8
            )

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listMarketProfitAndLoss(
            marketIds,
            includeSettledBets,
            includeBspBets,
            netOfCommission,
            sessionToken,
            appKey
        )

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url

        result[0].marketId shouldBe "1.163016936"
        result[0].commissionApplied shouldBe 5.0
        result[0].profitAndLosses?.size shouldBe 10
        result[0].profitAndLosses?.get(0)?.selectionId shouldBe 1408
        result[0].profitAndLosses?.get(0)?.ifWin shouldBe 0.0
    }

    "Given a non-200 response, when listMarketProfitAndLoss is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketProfitAndLoss(
                marketIds = setOf("1.163016936"),
                includeSettledBets = true,
                includeBspBets = true,
                netOfCommission = true,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listMarketProfitAndLoss is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listMarketProfitAndLoss(
                marketIds = setOf("1.163016936"),
                includeSettledBets = true,
                includeBspBets = true,
                netOfCommission = true,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listCurrentOrders
    "Given a 200 response, when listCurrentOrders is called then CurrentOrderSummaryReport is returned" {
        val betIds = setOf("123456")
        val marketIds = setOf("45678")
        val orderProjection = createOrderProjection()
        val customerOrderRefs = setOf("ref123")
        val customerStrategyRefs = setOf("stratRef")
        val dateRange = TimeRange(Date.from(Instant.now()), Date.from(Instant.now()))
        val orderBy = OrderBy.BY_BET
        val sortDir = SortDir.EARLIEST_TO_LATEST
        val fromRecord = 1
        val recordCount = 2

        val method = "listCurrentOrders"
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("betIds", betIds),
                Pair("marketIds", marketIds),
                Pair("orderProjection", orderProjection),
                Pair("customerOrderRefs", customerOrderRefs),
                Pair("customerStrategyRefs", customerStrategyRefs),
                Pair("dateRange", dateRange),
                Pair("orderBy", orderBy),
                Pair("sortDir", sortDir),
                Pair("fromRecord", fromRecord),
                Pair("recordCount", recordCount),
            )
        )

        val jsonResult =
            File("${System.getProperty("user.dir")}/src/test/resources/listCurrentOrdersResponse.json").readText(
                Charsets.UTF_8
            )

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listCurrentOrders(
            betIds,
            marketIds,
            orderProjection,
            customerOrderRefs,
            customerStrategyRefs,
            dateRange,
            orderBy,
            sortDir,
            fromRecord,
            recordCount,
            sessionToken,
            appKey
        )

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url

        result.currentOrders[0].betId shouldBe "232845276850"
        result.currentOrders[0].marketId shouldBe "1.183147417"
        result.currentOrders[0].selectionId shouldBe 22023487
        result.currentOrders[0].handicap shouldBe 0.0
        result.currentOrders[0].priceSize.price shouldBe 3.0
        result.currentOrders[0].priceSize.size shouldBe 2.0
        result.currentOrders[0].bspLiability shouldBe 0.0
        result.currentOrders[0].side shouldBe Side.BACK
        result.currentOrders[0].status shouldBe OrderStatus.EXECUTABLE
        result.currentOrders[0].persistenceType shouldBe PersistenceType.LAPSE
        result.currentOrders[0].orderType shouldBe OrderType.LIMIT
        result.currentOrders[0].placedDate shouldBe Date.from(Instant.parse("2021-05-09T22:48:25.000Z"))
        result.currentOrders[0].averagePriceMatched shouldBe 0.0
        result.currentOrders[0].sizeMatched shouldBe 0.0
        result.currentOrders[0].sizeRemaining shouldBe 2.0
        result.currentOrders[0].sizeLapsed shouldBe 0.0
        result.currentOrders[0].sizeCancelled shouldBe 0.0
        result.currentOrders[0].sizeVoided shouldBe 0.0
        result.currentOrders[0].regulatorCode shouldBe "GIBRALTAR REGULATOR"
        result.moreAvailable shouldBe false
    }

    "Given a non-200 response, when listCurrentOrders is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        shouldThrow<APINGException> {
            Betting(clientMock).listCurrentOrders(
                betIds = setOf("123456"),
                marketIds = setOf("45678"),
                orderProjection = createOrderProjection(),
                customerOrderRefs = setOf("ref123"),
                customerStrategyRefs = setOf("stratRef"),
                dateRange = TimeRange(Date.from(Instant.now()), Date.from(Instant.now())),
                orderBy = OrderBy.BY_BET,
                sortDir = SortDir.EARLIEST_TO_LATEST,
                fromRecord = 1,
                recordCount = 2,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listCurrentOrders is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listCurrentOrders(
                betIds = setOf("123456"),
                marketIds = setOf("45678"),
                orderProjection = createOrderProjection(),
                customerOrderRefs = setOf("ref123"),
                customerStrategyRefs = setOf("stratRef"),
                dateRange = TimeRange(Date.from(Instant.now()), Date.from(Instant.now())),
                orderBy = OrderBy.BY_BET,
                sortDir = SortDir.EARLIEST_TO_LATEST,
                fromRecord = 1,
                recordCount = 2,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //listClearedOrders
    "Given a 200 response, when listClearedOrders is called then ClearedOrderSummaryReport is returned" {
        val betStatus = BetStatus.CANCELLED
        val eventTypeIds = setOf("7")
        val eventIds = setOf("30504615")
        val betIds = setOf("12")
        val marketIds = setOf("1.183147417")
        val runnerIds = setOf(createRunnerId())
        val customerOrderRefs = setOf("customerOrderRef")
        val customerStrategyRefs = setOf("customerStrategyRef")
        val side = Side.BACK
        val settledDateRange = createTimeRange()
        val groupBy = GroupBy.BET
        val includeItemDescription = true
        val locale = "locale"
        val fromRecord = 1
        val recordCount = 2

        val method = "listClearedOrders"
        val expectedRequest = expectedRequest(
            method, sessionToken, appKey, mapOf(
                Pair("betStatus", betStatus),
                Pair("eventTypeIds", eventTypeIds),
                Pair("eventIds", eventIds),
                Pair("marketIds", marketIds),
                Pair("runnerIds", runnerIds),
                Pair("betIds", betIds),
                Pair("customerOrderRefs", customerOrderRefs),
                Pair("customerStrategyRefs", customerStrategyRefs),
                Pair("side", side),
                Pair("settledDateRange", settledDateRange),
                Pair("groupBy", groupBy),
                Pair("includeItemDescription", includeItemDescription),
                Pair("locale", locale),
                Pair("fromRecord", fromRecord),
                Pair("recordCount", recordCount),
            )
        )

        val jsonResult =
            File("${System.getProperty("user.dir")}/src/test/resources/listClearedOrdersResponse.json").readText(
                Charsets.UTF_8
            )

        val slot = CapturingSlot<Request>()

        every { clientMock.newCall(capture(slot)).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val betting = Betting(clientMock)
        val result = betting.listClearedOrders(
            betStatus,
            eventTypeIds,
            eventIds,
            marketIds,
            runnerIds,
            betIds,
            customerOrderRefs,
            customerStrategyRefs,
            side,
            settledDateRange,
            groupBy,
            includeItemDescription,
            locale,
            fromRecord,
            recordCount,
            sessionToken,
            appKey
        )

        slot.captured.body?.contentLength() shouldBe expectedRequest.body?.contentLength()
        slot.captured.headers shouldBe expectedRequest.headers
        slot.captured.method shouldBe expectedRequest.method
        slot.captured.url shouldBe expectedRequest.url

        result.clearedOrders.size shouldBe 2
        result.clearedOrders[0].eventTypeId shouldBe "7"
        result.clearedOrders[0].eventId shouldBe "30504615"
        result.clearedOrders[0].marketId shouldBe "1.183147417"
        result.clearedOrders[0].selectionId shouldBe 22023487
        result.clearedOrders[0].handicap shouldBe 0.0
        result.clearedOrders[0].betId shouldBe "232845276850"
        result.clearedOrders[0].placedDate shouldBe Date.from(Instant.parse("2021-05-09T22:48:25.000Z"))
        result.clearedOrders[0].persistenceType shouldBe PersistenceType.LAPSE
        result.clearedOrders[0].orderType shouldBe OrderType.LIMIT
        result.clearedOrders[0].side shouldBe Side.BACK
        result.clearedOrders[0].priceRequested shouldBe 3.0
        result.clearedOrders[0].settledDate shouldBe Date.from(Instant.parse("2021-05-10T12:05:55.000Z"))
        result.clearedOrders[0].sizeCancelled shouldBe 2.0
        result.moreAvailable shouldBe false
    }

    "Given a non-200 response, when listClearedOrders is called then it throws an APINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        shouldThrow<APINGException> {
            Betting(clientMock).listClearedOrders(
                betStatus = BetStatus.CANCELLED,
                eventTypeIds = setOf("7"),
                eventIds = setOf("30504615"),
                betIds = setOf("12"),
                marketIds = setOf("1.183147417"),
                runnerIds = setOf(createRunnerId()),
                customerOrderRefs = setOf("customerOrderRef"),
                customerStrategyRefs = setOf("customerStrategyRef"),
                side = Side.BACK,
                settledDateRange = createTimeRange(),
                groupBy = GroupBy.BET,
                includeItemDescription = true,
                locale = "locale",
                fromRecord = 1,
                recordCount = 2,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when listClearedOrders is called then it throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        shouldThrow<APINGException> {
            Betting(clientMock).listClearedOrders(
                betStatus = BetStatus.CANCELLED,
                eventTypeIds = setOf("7"),
                eventIds = setOf("30504615"),
                betIds = setOf("12"),
                marketIds = setOf("1.183147417"),
                runnerIds = setOf(createRunnerId()),
                customerOrderRefs = setOf("customerOrderRef"),
                customerStrategyRefs = setOf("customerStrategyRef"),
                side = Side.BACK,
                settledDateRange = createTimeRange(),
                groupBy = GroupBy.BET,
                includeItemDescription = true,
                locale = "locale",
                fromRecord = 1,
                recordCount = 2,
                sessionToken = sessionToken,
                applicationKey = appKey
            )
        }.message shouldBe "Response body is null"
    }

    //PlaceOrders
    //TODO Faster laptop required to finish tests, manual tests conducted instead


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