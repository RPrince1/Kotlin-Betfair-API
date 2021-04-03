package com.prince.betfair.client

import com.prince.betfair.client.exception.ClientException
import com.prince.betfair.config.Config
import com.prince.betfair.config.Credentials
import com.prince.betfair.config.JacksonConfiguration
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HttpClientSSOTest: StringSpec({

    val credentialsMock = mockk<Credentials>(relaxUnitFun = true)
    val configMock = mockk<Config>(relaxUnitFun = true)
    val okHttpsClientMock =  mockk<OkHttpClient>(relaxUnitFun = true)
    val objectMapper = JacksonConfiguration().mapper()
    val response = mockk<Response>(relaxUnitFun = true)

    val httpClientSSO = HttpClientSSO(credentialsMock, objectMapper, configMock, okHttpsClientMock)
    val url = "https://www.betfair.com"

    @AnnotationSpec.Before
    fun before(){
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    "Given a successful response, when login is called then returns a Token" {
        val sessionToken = "sessionToken"
        val loginStatus = "SUCCEEDED"
        val expectedToken = Token(sessionToken, loginStatus)
        val jsonResult = """{"sessionToken":"$sessionToken","loginStatus":"$loginStatus"}"""

        every { configMock.exchange.identityUrl } returns url
        every { credentialsMock.getEmail() } returns "email"
        every { credentialsMock.getPassword() } returns "password"
        every { okHttpsClientMock.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns true
        every { response.body?.string() } returns jsonResult

        val result = httpClientSSO.login()

        verify { configMock.exchange.identityUrl }
        verify { credentialsMock.getEmail() }
        verify { credentialsMock.getPassword() }

        result shouldBe expectedToken
    }

    "Given an unsuccessful response, when login is called then throws a ClientException" {
        val sessionToken = "sessionToken"
        val loginStatus = "FAILED"
        val jsonResult = """{"sessionToken":"$sessionToken","loginStatus":"$loginStatus"}"""

        every { configMock.exchange.identityUrl } returns url
        every { credentialsMock.getEmail() } returns "email"
        every { credentialsMock.getPassword() } returns "password"
        every { okHttpsClientMock.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns false
        every { response.body.toString() } returns jsonResult
        every { response.code } returns 400

        val exception = shouldThrow<ClientException> {
            httpClientSSO.login()
        }

        verify { configMock.exchange.identityUrl }
        verify { credentialsMock.getEmail() }
        verify { credentialsMock.getPassword() }

        exception.message shouldBe "Response code: 400, reason: $jsonResult"
    }

    "Given successful response with an empty body, when login is called then throws a ClientException" {
        every { configMock.exchange.identityUrl } returns url
        every { credentialsMock.getEmail() } returns "email"
        every { credentialsMock.getPassword() } returns "password"
        every { okHttpsClientMock.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns true
        every { response.body } returns null

        val exception = shouldThrow<ClientException> {
            httpClientSSO.login()
        }

        verify { configMock.exchange.identityUrl }
        verify { credentialsMock.getEmail() }
        verify { credentialsMock.getPassword() }

        exception.message shouldBe "Response body is null"
    }
})
