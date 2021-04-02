package com.prince.betfair.betfair

import com.prince.betfair.betfair.accounts.Accounts
import com.prince.betfair.betfair.accounts.Wallet
import com.prince.betfair.betfair.accounts.exception.AccountAPINGException
import com.prince.betfair.betfair.accounts.response.DeveloperApp
import com.prince.betfair.betfair.accounts.response.DeveloperAppVersion
import com.prince.betfair.client.Token
import com.prince.betfair.config.Config
import com.prince.betfair.config.Credentials
import com.prince.betfair.config.JacksonConfiguration
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import okhttp3.OkHttpClient
import okhttp3.Response
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AccountsTest: StringSpec({

    val objectMapper = JacksonConfiguration().mapper()
    val credentialsMock = mockk<Credentials>(relaxUnitFun = true)
    val walletMock =  mockk<Wallet>(relaxUnitFun = true)
    val configMock = mockk<Config>(relaxUnitFun = true)
    val clientMock = mockk<OkHttpClient>(relaxUnitFun = true)
    val response = mockk<Response>(relaxUnitFun = true)

    val token = Token("sessionToken", "SUCCESS")

    "Given a 200 response, when getDeveloperAppKeys is called List<DeveloperApp> is returned" {

        val developerAppVersion = DeveloperAppVersion(
            "owner",
            123L,
            "version",
            "appKey",
            true,
            true,
            true,
            true
        )
        val expectedDeveloperApp = DeveloperApp(
            "appName",
            123L,
            listOf(developerAppVersion)
        )

        val jsonResult = """
            [{"appName":"appName","appId":123,"appVersions":[{"owner":"owner","versionId":123,"version":"version",
            "applicationKey":"appKey","delayData":true,"subscriptionRequired":true,"ownerManaged":true,
            "active":true}]}]
        """.trimIndent()

        every { response.body?.string()  } returns jsonResult
        every { response.isSuccessful  } returns true
        every { clientMock.newCall(any()).execute() } returns response
        every { configMock.exchange.account.url } returns "https://www.betfair.com"

        val account = Accounts(objectMapper, credentialsMock, walletMock, configMock, clientMock)
        val developerApp = account.getDeveloperAppKeys(token)

        developerApp shouldBe listOf(expectedDeveloperApp)
    }

    "Given a non-200 response when getDeveloperAppKeys is called throws an AccountAPINGException" {
        every { configMock.exchange.account.url } returns "https://www.betfair.com"
        every { clientMock.newCall(any()).execute() } returns response
        every { response.code } returns 400
        every { response.body.toString() } returns "Bad request"
        every { response.isSuccessful  } returns false
        val account = Accounts(objectMapper, credentialsMock, walletMock, configMock, clientMock)

        val exception = shouldThrow<AccountAPINGException> {
            account.getDeveloperAppKeys(token)
        }

        exception.message shouldBe "Response code: 400, reason: Bad request"
    }

    "Given an empty response body, getDeveloperAppKeys throws an AccountAPINGException" {

    }
})