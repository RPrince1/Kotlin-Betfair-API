package com.prince.betfair.betfair.accounts

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prince.betfair.betfair.accounts.exception.AccountAPINGException
import com.prince.betfair.betfair.accounts.entities.AccountFundsResponse
import com.prince.betfair.betfair.accounts.entities.DeveloperApp
import com.prince.betfair.client.Token
import com.prince.betfair.config.Config
import com.prince.betfair.config.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

@Component
class Accounts(
    private val objectMapper: ObjectMapper,
    private val credentials: Credentials,
    private val wallet: Wallet,
    private val config: Config,
    private val client: OkHttpClient
) {

    fun getDeveloperAppKeys(token: Token): List<DeveloperApp> {
        val request = Request.Builder()
            .url("${config.exchange.account.url}getDeveloperAppKeys/")
            .addHeader("X-Authentication", token.sessionToken)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .method("POST", FormBody.Builder().build())
            .build()

        val response = client.newCall(request).execute()

        val body = when {
            response.isSuccessful -> response.body?.string() ?: throw AccountAPINGException("Response body is null")
            else -> throw AccountAPINGException("Response code: ${response.code}, reason: ${response.body}")
        }

        return objectMapper.readValue(body)
    }

    fun getAccountFunds(token: Token): AccountFundsResponse {
        val request = Request.Builder()
            .url("${config.exchange.account.url}getAccountFunds/")
            .addHeader("X-Authentication", token.sessionToken)
            .addHeader("X-Application", credentials.getApplicationKey())
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Wallet", wallet.location.toString())
            .method("POST", FormBody.Builder().build())
            .build()

        val response = client.newCall(request).execute()

        val body = when {
            response.isSuccessful -> response.body?.string() ?: throw AccountAPINGException("Response body is null")
            else -> throw AccountAPINGException("Response code: ${response.code}, reason: ${response.body}")
        }

        return objectMapper.readValue(body, AccountFundsResponse::class.java)
    }
}