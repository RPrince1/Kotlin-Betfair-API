package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.utils.HttpPost
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.apache.http.client.methods.CloseableHttpResponse
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HttpClientSSOTest: StringSpec({

    val credentials = mockk<Credentials>(relaxUnitFun = true)
    val httpPost =  mockk<HttpPost>(relaxUnitFun = true)
    val objectMapper = mockk<ObjectMapper>(relaxUnitFun = true)
    val httpClientSSO = HttpClientSSO(objectMapper, credentials, httpPost)

    @AnnotationSpec.Before
    fun before(){
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    "When getToken is called returns a Token" {
        val token = Token("sessionToken", "SUCCEEDED")


        val mockR = mockk<CloseableHttpResponse>()
//        every { mockR.entity } returns httpEntity

        every {credentials.getEmail() } returns "email"
        every { credentials.getPassword() } returns "password"
        every { httpPost.execute() } returns mockR
        every { objectMapper.readValue("lool", Token::class.java) } returns token

        httpClientSSO.login() shouldBe token
    }

})
