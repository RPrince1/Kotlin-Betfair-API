package com.prince.betfair.betfair.accounts

import com.prince.betfair.betfair.accounts.exception.AccountAPINGException
import com.prince.betfair.betfair.accounts.entities.AccountFundsResponse
import com.prince.betfair.betfair.accounts.entities.DeveloperApp
import com.prince.betfair.betfair.accounts.entities.DeveloperAppVersion
import com.prince.betfair.client.Token
import com.prince.betfair.config.JacksonConfiguration
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class AccountsTest: StringSpec({

    val objectMapper = JacksonConfiguration().mapper()
    val walletMock =  mockk<Wallet>(relaxUnitFun = true)
    val clientMock = mockk<OkHttpClient>(relaxUnitFun = true)
    val response = mockk<Response>(relaxUnitFun = true)

    val token = Token("sessionToken", "SUCCESS")
    val appKey = "appKey"

    @AnnotationSpec.AfterEach
    fun after() {
        clearAllMocks()
    }

    //getDeveloperKeys
    "Given a 200 response, when getDeveloperAppKeys is called then List<DeveloperApp> is returned" {
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

        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns jsonResult.toResponseBody()
        every { response.isSuccessful  } returns true

        val account = Accounts(objectMapper, walletMock, clientMock)
        val result = account.getDeveloperAppKeys(token)

        result shouldBe listOf(expectedDeveloperApp)
    }

    "Given a non-200 response when getDeveloperAppKeys is called then throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body?.toString() } returns "Bad request"
        every { response.code } returns 401
        every { response.isSuccessful  } returns false

        val account = Accounts(objectMapper, walletMock, clientMock)

        val exception = shouldThrow<AccountAPINGException> {
            account.getDeveloperAppKeys(token)
        }

        exception.message shouldBe "Response code: 401, reason: Bad request"
    }

    "Given a null response body, when getDeveloperAppKeys is called then throws an AccountAPINGException" {
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.code } returns 402
        every { response.isSuccessful } returns true

        val account = Accounts(objectMapper, walletMock, clientMock)

        val exception = shouldThrow<AccountAPINGException> {
            account.getDeveloperAppKeys(token)
        }

        exception.message shouldBe "Response body is null"
    }

    "Given a 200 response, when getAccountFunds is called then returns AccountFundsResponse" {
        val expectedAccountFundsResponse = AccountFundsResponse(
            102.toDouble(),
            1.toDouble(),
            2.toDouble(),
            3.toDouble(),
            4.toDouble(),
            5
        )
        val jsonResult = """
            {"availableToBetBalance":102,"exposure":1.0,"retainedCommission":2.0,"exposureLimit":3.0,
            "discountRate":4.0,"pointsBalance":5}
        """.trimIndent()

        every { walletMock.location.toString() } returns "UK"
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body?.string()  } returns jsonResult
        every { response.isSuccessful  } returns true

        val account = Accounts(objectMapper, walletMock, clientMock)
        val result = account.getAccountFunds(token, appKey)

        verify { walletMock.location.toString() }

        result shouldBe expectedAccountFundsResponse
    }

    "Given a non-200 response, when getAccountFunds is called then it throws an AccountAPINGException" {
        every { walletMock.location.toString() } returns "UK"
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body.toString() } returns "Bad request"
        every { response.code } returns 403
        every { response.isSuccessful  } returns false

        val account = Accounts(objectMapper, walletMock, clientMock)
        val exception = shouldThrow<AccountAPINGException> {
            account.getAccountFunds(token, appKey)
        }

        verify { walletMock.location.toString() }

        exception.message shouldBe "Response code: 403, reason: Bad request"
    }

    "Given a null response, when getAccountFunds is called then it throws an AccountAPINGException" {
        every { walletMock.location.toString() } returns "UK"
        every { clientMock.newCall(any()).execute() } returns response
        every { response.body } returns null
        every { response.code } returns 404
        every { response.isSuccessful } returns true

        val account = Accounts(objectMapper, walletMock, clientMock)
        val exception = shouldThrow<AccountAPINGException> {
            account.getAccountFunds(token, appKey)
        }

        verify { walletMock.location.toString() }

        exception.message shouldBe "Response body is null"
    }

})