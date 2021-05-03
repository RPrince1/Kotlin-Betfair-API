package com.prince.betfair.betfair.accounts

import com.fasterxml.jackson.module.kotlin.readValue
import com.prince.betfair.betfair.accounts.entities.AccountFundsResponse
import com.prince.betfair.betfair.accounts.entities.DeveloperApp
import com.prince.betfair.betfair.accounts.exception.AccountAPINGException
import com.prince.betfair.config.JacksonConfiguration
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class Accounts(
    private val client: OkHttpClient = OkHttpClient()
) {

    private val objectMapper = JacksonConfiguration().mapper()
    private val accountUrl = "https://api.betfair.com/exchange/account/rest/v1.0/"

    fun getDeveloperAppKeys(sessionToken: String): List<DeveloperApp> {
        val request = Request.Builder()
            .url("${accountUrl}getDeveloperAppKeys/")
            .addHeader("X-Authentication", sessionToken)
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

    fun getAccountFunds(sessionToken: String, applicationKey: String, wallet: Wallet): AccountFundsResponse {
        val request = Request.Builder()
            .url("${accountUrl}getAccountFunds/")
            .addHeader("X-Authentication", sessionToken)
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