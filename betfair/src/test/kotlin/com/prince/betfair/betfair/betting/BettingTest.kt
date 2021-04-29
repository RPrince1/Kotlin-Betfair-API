package com.prince.betfair.betfair.betting

import com.prince.betfair.betfair.betting.entities.EventType
import com.prince.betfair.betfair.betting.entities.EventTypeResult
import com.prince.betfair.betfair.betting.entities.MarketFilter
import com.prince.betfair.betfair.betting.exception.APINGException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

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

    "Given a null response, when getAccountFunds is called then it throws an AccountAPINGException" {
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
})