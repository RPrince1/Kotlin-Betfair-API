package com.prince.betfair.betfair.betting

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prince.betfair.betfair.betting.entities.EventTypeResult
import com.prince.betfair.betfair.betting.entities.MarketFilter
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.client.Token
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Betting(
    private val client: OkHttpClient,
    private val objectMapper: ObjectMapper
) {

    private val bettingUrl = "https://api.betfair.com/exchange/betting/rest/v1.0/"

    //Returns a list of Event Types (i.e. Sports) associated with the markets selected by the MarketFilter.
    fun listEventTypes(
        filter: MarketFilter,
        //TODO add locale
        locale: String? = null,
        maxResults: Int,
        token: Token,
        applicationKey: String
    ): List<EventTypeResult> {
        val request = Request.Builder()
            .url("${bettingUrl}listEventTypes/")
            .addHeader("X-Authentication", token.sessionToken)
            .addHeader("X-Application", applicationKey)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .post(
                objectMapper.writeValueAsString(
                    mapOf(
                        Pair("filter", filter),
                        Pair("maxResults", maxResults.toString())
                    )
                ).toRequestBody()
            )
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        val body = when {
            response.isSuccessful -> responseBody ?: throw APINGException("Response body is null")
            else -> throw APINGException("Response code: ${response.code}, reason: $responseBody")
        }

        return objectMapper.readValue(body)
    }
}