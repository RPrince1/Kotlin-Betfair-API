package com.prince.betfair.betfair.betting

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.prince.betfair.betfair.betting.entities.EventTypeResult
import com.prince.betfair.betfair.betting.entities.MarketFilter
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.client.Token
import com.prince.betfair.config.Config
import mu.KotlinLogging
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class Betting(
    private val config: Config,
    private val client: OkHttpClient,
    private val objectMapper: ObjectMapper
) {

    //Returns a list of Event Types (i.e. Sports) associated with the markets selected by the MarketFilter.
    fun listEventTypes(filter: MarketFilter, locale: String? = null, token: Token): List<EventTypeResult> {
        val request = Request.Builder()
            .url("${config.exchange.betting.url}listEventTypes/")
            .addHeader("X-Authentication", token.sessionToken)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .method("POST", FormBody.Builder().add("MarketFilter", filter.toString()).build())
            .build()

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        val body = when {
            response.isSuccessful -> responseBody ?: throw APINGException("Response body is null")
            else -> throw APINGException("Response code: ${response.code}, reason: $responseBody}")
        }

        logger.info { body }

        return objectMapper.readValue(body)
    }
}