package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.config.Config
import com.prince.betfair.config.Credentials
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import okhttp3.OkHttpClient
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HttpClientSSOTest: StringSpec({

    val credentialsMock = mockk<Credentials>(relaxUnitFun = true)
    val objectMapperMock = mockk<ObjectMapper>(relaxUnitFun = true)
    val configMock = mockk<Config>(relaxUnitFun = true)
    val okHttpsClientMock =  mockk<OkHttpClient>(relaxUnitFun = true)
    val httpClientSSO = HttpClientSSO(credentialsMock, objectMapperMock, configMock, okHttpsClientMock)

    @AnnotationSpec.Before
    fun before(){
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    "When getToken is called returns a Token" {
        val token = Token("sessionToken", "SUCCEEDED")

// TODO
//        val mockR = mockk<CloseableHttpResponse>()
////        every { mockR.entity } returns httpEntity
//
//        every {credentials.getEmail() } returns "email"
//        every { credentials.getPassword() } returns "password"
//        every { httpPost.execute() } returns mockR
//        every { objectMapper.readValue("lool", Token::class.java) } returns token

        httpClientSSO.login() shouldBe token
    }

})
