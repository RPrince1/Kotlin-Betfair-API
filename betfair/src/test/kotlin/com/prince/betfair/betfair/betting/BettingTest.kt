package com.prince.betfair.betfair.betting

import com.prince.betfair.betfair.betting.entities.*
import com.prince.betfair.betfair.betting.enums.TimeGranularity
import com.prince.betfair.betfair.betting.exception.APINGException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.time.withConstantNow
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
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

        val jsonResult = """
            [{"eventType":{"id":"1","name":"Horse Racing"},"marketCount":20701}]
        """.trimIndent()

        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()

        val betting = Betting(clientMock)
        val result = betting.listEventTypes(marketFilter, null, 10, sessionToken, appKey)

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

        val jsonResult = """
            [{"competition":{"id":"1","name":"Bread winners cup"},"marketCount":19,"competitionRegion":"BRA"}]
        """.trimIndent()

        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)
        val result = betting.listCompetitions(marketFilter, null, 10, sessionToken, appKey)

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

        val jsonResult = """
            [{"timeRange":{"from":"${from.toInstant()}","to":"${to.toInstant()}"},"marketCount":2}]
        """.trimIndent()

        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(clientMock)
        val result = betting.listTimeRanges(marketFilter, TimeGranularity.DAYS, 1, sessionToken, appKey)

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
})