package com.prince.betfair.utils

import com.prince.betfair.client.Credentials
import io.kotest.core.spec.style.StringSpec
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HttpsTest: StringSpec({

    val credentialsMock = mockk<Credentials>(relaxUnitFun = true)
    val https = Https(credentialsMock)

})