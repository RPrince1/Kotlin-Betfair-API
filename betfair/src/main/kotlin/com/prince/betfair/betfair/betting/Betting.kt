package com.prince.betfair.betfair.betting

import com.fasterxml.jackson.module.kotlin.readValue

import com.prince.betfair.betfair.betting.entities.*
import com.prince.betfair.betfair.betting.enums.TimeGranularity
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.config.JacksonConfiguration
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Betting(
    private val client: OkHttpClient = OkHttpClient()
) {

    private val objectMapper = JacksonConfiguration().mapper()
    private val bettingUrl = "https://api.betfair.com/exchange/betting/rest/v1.0/"

    //Returns a list of Event Types (i.e. Sports) associated with the markets selected by the MarketFilter.
    fun listEventTypes(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<EventTypeResult> {
        val request = Request.Builder()
            .url("${bettingUrl}listEventTypes/")
            .addDefaultHeaders(sessionToken, applicationKey)
            .post(
                objectMapper.writeValueAsString(
                    mapOf(
                        Pair("filter", filter),
                        Pair("locale", locale),
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

    fun listCompetitions(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<CompetitionResult> {
        val request = Request.Builder()
            .url("${bettingUrl}listCompetitions/")
            .addDefaultHeaders(sessionToken, applicationKey)
            .post(
                objectMapper.writeValueAsString(
                    mapOf(
                        Pair("filter", filter),
                        Pair("locale", locale),
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

    fun listTimeRanges(
        filter: MarketFilter,
        granularity: TimeGranularity,
        maxResults: Int,
        sessionToken: String,
        applicationKey: String
    ): List<TimeRangeResult> {
        val request = Request.Builder()
            .url("${bettingUrl}listTimeRanges/")
            .addDefaultHeaders(sessionToken, applicationKey)
            .post(
                objectMapper.writeValueAsString(
                    mapOf(
                        Pair("filter", filter),
                        Pair("granularity", granularity),
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
    
    fun listEvents(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<EventResult> {
        val request = Request.Builder()
            .url("${bettingUrl}listEvents/")
            .addDefaultHeaders(sessionToken, applicationKey)
            .post(
                objectMapper.writeValueAsString(
                    mapOf(
                        Pair("filter", filter),
                        Pair("locale", locale),
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

private fun Request.Builder.addDefaultHeaders(sessionToken: String, applicationKey: String): Request.Builder {
    this.addHeader("X-Authentication", sessionToken)
    this.addHeader("X-Application", applicationKey)
    this.addHeader("Content-Type", "application/json")
    this.addHeader("Accept", "application/json")
    return this
}