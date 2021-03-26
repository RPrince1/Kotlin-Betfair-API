package com.prince.betfair.utils

import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.apache.http.client.methods.CloseableHttpResponse
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HttpPostTest: StringSpec({

    val httpsMock = mockk<Https>(relaxUnitFun = true)
    val httpPost = HttpPost(httpsMock)

    "When httpPost.execute is called https.execute is called" {
        every { httpsMock.execute(any()) } returns mockk<CloseableHttpResponse>()

        httpPost.execute()

        verify { httpsMock.execute(any()) }
    }

})
