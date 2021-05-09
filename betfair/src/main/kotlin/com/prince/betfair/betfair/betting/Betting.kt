package com.prince.betfair.betfair.betting

import com.fasterxml.jackson.module.kotlin.readValue

import com.prince.betfair.betfair.betting.entities.*
import com.prince.betfair.betfair.betting.entities.competition.CompetitionResult
import com.prince.betfair.betfair.betting.entities.competition.CountryCodeResult
import com.prince.betfair.betfair.betting.entities.event.EventResult
import com.prince.betfair.betfair.betting.entities.event.EventTypeResult
import com.prince.betfair.betfair.betting.entities.market.MarketCatalogue
import com.prince.betfair.betfair.betting.entities.market.MarketFilter
import com.prince.betfair.betfair.betting.enums.market.MarketProjection
import com.prince.betfair.betfair.betting.entities.market.MarketTypeResult
import com.prince.betfair.betfair.betting.entities.market.PriceProjection
import com.prince.betfair.betfair.betting.enums.market.MarketSort
import com.prince.betfair.betfair.betting.enums.TimeGranularity
import com.prince.betfair.betfair.betting.enums.market.MatchProjection
import com.prince.betfair.betfair.betting.enums.market.OrderProjection
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.config.JacksonConfiguration
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.util.*

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
        val request = createRequest(
            "listEventTypes",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    fun listCompetitions(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<CompetitionResult> {
        val request = createRequest(
            "listCompetitions",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    fun listTimeRanges(
        filter: MarketFilter,
        granularity: TimeGranularity,
        maxResults: Int,
        sessionToken: String,
        applicationKey: String
    ): List<TimeRangeResult> {
        val request = createRequest(
            "listTimeRanges",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("granularity", granularity),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    fun listEvents(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<EventResult> {
        val request = createRequest(
            "listEvents",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    fun listMarketTypes(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<MarketTypeResult> {
        val request = createRequest(
            "listMarketTypes",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    fun listCountries(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<CountryCodeResult> {
        val request = createRequest(
            "listCountries",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    fun listVenues(
        filter: MarketFilter,
        locale: String? = null,
        maxResults: Int? = null,
        sessionToken: String,
        applicationKey: String
    ): List<VenueResult> {
        val request = createRequest(
            "listVenues",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    fun listMarketCatalogue(
        filter: MarketFilter,
        marketProjection: Set<MarketProjection>? = null,
        sort: MarketSort? = null,
        locale: String? = null,
        maxResults: Int,
        sessionToken: String,
        applicationKey: String
    ): List<MarketCatalogue> {
        val request = createRequest(
            "listMarketCatalogue",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("marketProjection", marketProjection),
                Pair("sort", sort),
                Pair("locale", locale),
                Pair("maxResults", maxResults.toString())
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of dynamic data about markets. Dynamic data includes prices, the status of the market,
     * the status of selections, the traded volume, and the status of any orders you have placed in the market.
     *
     * @param priceProjection: The projection of price data you want to receive in the response.
     * @param orderProjection: The orders you want to receive in the response.
     * @param matchProjection: If you ask for orders, specifies the representation of matches.
     * @param includeOverallPosition: If you ask for orders, returns matches for each selection. Defaults to true if
     * unspecified.
     * @param partitionMatchedByStrategyRef: If you ask for orders, returns the breakdown of matches by strategy for
     * each selection. Defaults to false if unspecified.
     * @param customerStrategyRefs: If you ask for orders, restricts the results to orders matching any of the specified
     * set of customer defined strategies.
     * Also filters which matches by strategy for selections are returned, if partitionMatchedByStrategyRef is true.
     * An empty set will be treated as if the parameter has been omitted (or null passed).
     * @param currencyCode: A Betfair standard currency code. If not specified, the default currency code is used.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param matchedSince: If you ask for orders, restricts the results to orders that have at least one fragment
     * matched since
     * the specified date (all matched fragments of such an order will be returned even if some were matched before the
     * specified date).
     * All EXECUTABLE orders will be returned regardless of matched date.
     * @param betIds: If you ask for orders, restricts the results to orders with the specified bet IDs. Omitting this
     * parameter means that all bets will be included in the response. Please note: A maximum of 250 betId's can be
     * provided at a time.
     */
    fun listMarketBook(
        marketIds : List<String>,
        priceProjection: PriceProjection?,
        orderProjection: OrderProjection?,
        matchProjection: MatchProjection?,
        includeOverallPosition: Boolean?,
        partitionMatchedByStrategyRef: Boolean?,
        customerStrategy: Set<String>?,
        currencyCode: String?,
        locale: String?,
        matchedSince: Date?,
        betIds: Set<String>?,
        sessionToken: String,
        applicationKey: String
    ): List<MarketBook> {
        val request = createRequest(
            "listMarketBook",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("marketIds", marketIds),
                Pair("priceProjection", priceProjection),
                Pair("orderProjection", orderProjection),
                Pair("matchProjection", matchProjection),
                Pair("includeOverallPosition", includeOverallPosition),
                Pair("partitionMatchedByStrategyRef", partitionMatchedByStrategyRef),
                Pair("customerStrategyRefs", customerStrategy),
                Pair("currencyCode", currencyCode),
                Pair("locale", locale),
                Pair("matchedSince", matchedSince),
                Pair("betIds", betIds)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    private fun handleResponse(response: Response): String {
        val responseBody = response.body?.string()

        return when {
            response.isSuccessful -> responseBody ?: throw APINGException("Response body is null")
            else -> throw APINGException("Response code: ${response.code}, reason: $responseBody")
        }
    }

    private fun createRequest(
        path: String,
        sessionToken: String,
        applicationKey: String,
        body: Map<String, Any?>
    ): Request {
        return Request.Builder()
            .url("${bettingUrl}$path/")
            .addHeader("X-Authentication", sessionToken)
            .addHeader("X-Application", applicationKey)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .post(objectMapper.writeValueAsString(body).toRequestBody())
            .build()
    }
}
