package com.prince.betfair.betfair.accounts

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prince.betfair.betfair.accounts.entities.AccountFundsResponse
import com.prince.betfair.betfair.accounts.entities.DeveloperApp
import com.prince.betfair.betfair.accounts.exception.AccountAPINGException
import com.prince.betfair.client.Token
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class Accounts(
    private val objectMapper: ObjectMapper,
    private val wallet: Wallet,
    private val client: OkHttpClient
) {

    private val accountUrl = "https://api.betfair.com/exchange/account/rest/v1.0/"

    fun getDeveloperAppKeys(token: Token): List<DeveloperApp> {
        val request = Request.Builder()
            .url("${accountUrl}getDeveloperAppKeys/")
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

    fun getAccountFunds(token: Token, applicationKey: String): AccountFundsResponse {
        val request = Request.Builder()
            .url("${accountUrl}getAccountFunds/")
            .addHeader("X-Authentication", token.sessionToken)
            .addHeader("X-Application", applicationKey)
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