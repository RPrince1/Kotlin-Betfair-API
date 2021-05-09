package com.prince.betfair.client

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HttpClientSSOTest: StringSpec({

    val client =  mockk<OkHttpClient>(relaxUnitFun = true)
    val response = mockk<Response>(relaxUnitFun = true)

    val httpClientSSO = ClientSSO(client)

    @AnnotationSpec.Before
    fun before(){
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    "Given a successful response, when the loginStatus is SUCCESS then returns a SSOID.Success" {
        val sessionToken = "sessionToken"
        val loginStatus = "SUCCESS"
        val expectedSSOID = SSOID.Success(sessionToken)
        val jsonResult = """{"sessionToken":"$sessionToken","loginStatus":"$loginStatus"}"""

        every { client.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns true
        every { response.body?.string() } returns jsonResult

        val result = httpClientSSO.login("email", "password")

        result shouldBe expectedSSOID
    }

    "Given a successful response, when the loginStatus is not SUCCESS then returns SSOID.Failure" {
        val loginStatus = "FAILED"
        val expectedSSOID = SSOID.Failure(loginStatus, null)
        val jsonResult = """{"loginStatus":"$loginStatus"}"""

        every { client.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns true
        every { response.body?.string() } returns jsonResult

        val result = httpClientSSO.login("email", "password")

        result shouldBe expectedSSOID
    }

    "Given an unsuccessful response, when login is called then throws a ClientException" {
        val body = "error"
        val responseCode = 400
        val expectedSSOID = SSOID.Failure(
            "com.prince.betfair.client.exception.ClientException",
            "Response code: $responseCode, reason: $body"
        )

        every { client.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns false
        every { response.body.toString() } returns body
        every { response.code } returns responseCode

        val result = httpClientSSO.login("email", "password")

        result shouldBe expectedSSOID
    }

    "Given successful response with an empty body, when login is called then throws a ServerException" {
        val expectedSSOID = SSOID.Failure(
            "com.prince.betfair.client.exception.ServerException",
            "Response body is null"
        )

        every { client.newCall(any()).execute() } returns response
        every { response.isSuccessful } returns true
        every { response.body } returns null

        val result = httpClientSSO.login("email", "password")

        result shouldBe expectedSSOID
    }
})
