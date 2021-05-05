package com.prince.betfair.betfair.betting

import com.prince.betfair.betfair.betting.entities.*
import com.prince.betfair.betfair.betting.enums.TimeGranularity
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
        val expectedRequest = expectedRequest(method, sessionToken, appKey, mapOf(
            Pair("filter", marketFilter),
            Pair("locale", locale),
            Pair("maxResults", maxResults.toString())
        ))

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
        val expectedRequest = expectedRequest(method, sessionToken, appKey, mapOf(
            Pair("filter", marketFilter),
            Pair("locale", locale),
            Pair("maxResults", maxResults.toString())
        ))

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
        val expectedRequest = expectedRequest(method, sessionToken, appKey, mapOf(
            Pair("filter", marketFilter),
            Pair("granularity", granularity),
            Pair("maxResults", maxResults.toString())
        ))

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
        val expectedRequest = expectedRequest(method, sessionToken, appKey, mapOf(
            Pair("filter", marketFilter),
            Pair("locale", locale),
            Pair("maxResults", maxResults.toString())
        ))

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