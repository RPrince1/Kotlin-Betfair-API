package com.prince.betfair.betfair.betting

import com.prince.betfair.betfair.betting.entities.EventType
import com.prince.betfair.betfair.betting.entities.EventTypeResult
import com.prince.betfair.betfair.betting.entities.MarketFilter
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.client.Token
import com.prince.betfair.config.Config
import com.prince.betfair.config.Credentials
import com.prince.betfair.config.JacksonConfiguration
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class BettingTest : StringSpec({

    val objectMapper = JacksonConfiguration().mapper()
    val configMock = mockk<Config>(relaxUnitFun = true)
    val clientMock = mockk<OkHttpClient>(relaxUnitFun = true)
    val credentialsMock = mockk<Credentials>(relaxUnitFun = true)
    val response = mockk<Response>(relaxUnitFun = true)

    val token = Token("sessionToken", "SUCCESS")
    val url = "https://www.betfair.com"

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

        every { configMock.exchange.betting.url } returns url
        every { credentialsMock.getApplicationKey() } returns "appKey"
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()

        val betting = Betting(configMock, clientMock, objectMapper, credentialsMock)
        val result = betting.listEventTypes(marketFilter, null, 10, token)

        verify { configMock.exchange.betting.url }

        result shouldBe listOf(expectedEventTypeResult)
    }

    "Given a non-200 response, when listEventTypes is called then it throws an APINGException" {
        every { configMock.exchange.betting.url } returns url
        every { credentialsMock.getApplicationKey() } returns "appKey"
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns "Error".toResponseBody()
        every { response.isSuccessful } returns false
        every { response.code } returns 409

        val marketFilter = MarketFilter()
        val betting = Betting(configMock, clientMock, objectMapper, credentialsMock)

        val exception = shouldThrow<APINGException> {
            betting.listEventTypes(marketFilter, null, 10, token)
        }

        exception.message shouldBe "Response code: 409, reason: Error"
    }

    "Given a null response, when getAccountFunds is called then it throws an AccountAPINGException" {
        every { configMock.exchange.betting.url } returns url
        every { credentialsMock.getApplicationKey() } returns "appKey"
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.isSuccessful } returns true

        val marketFilter = MarketFilter()
        val betting = Betting(configMock, clientMock, objectMapper, credentialsMock)

        val exception = shouldThrow<APINGException> {
            betting.listEventTypes(marketFilter, null, 10, token)
        }

        exception.message shouldBe "Response body is null"
    }
})