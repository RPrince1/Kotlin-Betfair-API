package com.prince.betfair.betfair.accounts

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.client.Credentials
import com.prince.betfair.client.Token
import mu.KotlinLogging
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Autowired

private val logger = KotlinLogging.logger {}

class Accounts() {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var credentials: Credentials

    @Autowired
    private lateinit var wallet: Wallet

    val client = OkHttpClient()

    fun getAccountFunds(token: Token): AccountFundsResponse {
        //TODO change to base url
        val request = Request.Builder()
            .url("https://api.betfair.com/exchange/account/rest/v1.0/getAccountFunds/")
            .addHeader("X-Authentication", token.sessionToken)
            .addHeader("X-Application", credentials.getApplicationKey())
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Wallet", wallet.location.toString())
            .method("POST", FormBody.Builder().build())
            .build()

        val response = client.newCall(request).execute()
        val body: String = response.body?.string() ?: throw AccountAPINGException("Response Body was null")

        return objectMapper.readValue(body, AccountFundsResponse::class.java)
    }

}