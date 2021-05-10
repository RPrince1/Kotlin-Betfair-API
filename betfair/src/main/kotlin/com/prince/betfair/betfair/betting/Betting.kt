package com.prince.betfair.betfair.betting

import com.fasterxml.jackson.module.kotlin.readValue

import com.prince.betfair.betfair.betting.entities.*
import com.prince.betfair.betfair.betting.entities.competition.CompetitionResult
import com.prince.betfair.betfair.betting.entities.competition.CountryCodeResult
import com.prince.betfair.betfair.betting.entities.event.EventResult
import com.prince.betfair.betfair.betting.entities.event.EventTypeResult
import com.prince.betfair.betfair.betting.entities.market.*
import com.prince.betfair.betfair.betting.enums.*
import com.prince.betfair.betfair.betting.enums.market.*
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
        marketIds: List<String>,
        priceProjection: PriceProjection?,
        orderProjection: OrderProjection?,
        matchProjection: MatchProjection?,
        includeOverallPosition: Boolean?,
        partitionMatchedByStrategyRef: Boolean?,
        customerStrategyRefs: Set<String>?,
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
                Pair("customerStrategyRefs", customerStrategyRefs),
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

    /**
     * Returns a list of dynamic data about a market and a specified runner. Dynamic data includes prices, the status of
     * the market, the status of selections, the traded volume, and the status of any orders you have placed in the
     * market
     *
     * You can only pass in one marketId and one selectionId in that market per request. If the selectionId being passed
     * in is not a valid one / doesn’t belong in that market then the call will still work but only the market data is
     * returned
     *
     * @param marketId: (required) The unique id for the market.
     * @param selectionId: (required) The unique id for the selection in the market.
     * @param handicap: The handicap associated with the runner in case of Asian handicap market
     * @param priceProjection: The projection of price data you want to receive in the response.
     * @param orderProjection: The orders you want to receive in the response.
     * @param matchProjection: If you ask for orders, specifies the representation of matches.
     * @param includeOverallPosition: If you ask for orders, returns matches for each selection. Defaults to true if
     * unspecified.
     * @param partitionMatchedByStrategyRef: If you ask for orders, returns the breakdown of matches by strategy for
     * each selection. Defaults to false if unspecified.
     * @param customerStrategyRefs: If you ask for orders, restricts the results to orders matching any of the specified
     * set of customer defined strategies.Also filters which matches by strategy for selections are returned, if
     * partitionMatchedByStrategyRef is true. An empty set will be treated as if the parameter has been omitted (or null
     * passed).
     * @param currencyCode: A Betfair standard currency code. If not specified, the default currency code is used.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param matchedSince: If you ask for orders, restricts the results to orders that have at least one fragment
     * matched since the specified date (all matched fragments of such an order will be returned even if some were
     * matched before the specified date). All EXECUTABLE orders will be returned regardless of matched date.
     * @param betIds: If you ask for orders, restricts the results to orders with the specified bet IDs. Omitting this
     * parameter means that all bets will be included in the response. Please note: A maximum of 250 betId's can be
     * provided at a time.
     * @throws APINGException
     */
    fun listRunnerBook(
        marketId: String,
        selectionId: Long,
        handicap: Double?,
        priceProjection: PriceProjection?,
        orderProjection: OrderProjection?,
        matchProjection: MatchProjection?,
        includeOverallPosition: Boolean?,
        partitionMatchedByStrategyRef: Boolean?,
        customerStrategyRefs: Set<String>?,
        currencyCode: String?,
        locale: String?,
        matchedSince: Date?,
        betIds: Set<String>?,
        sessionToken: String,
        applicationKey: String
    ): List<MarketBook> {
        val request = createRequest(
            "listRunnerBook",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("marketId", marketId),
                Pair("selectionId", selectionId),
                Pair("handicap", handicap),
                Pair("priceProjection", priceProjection),
                Pair("orderProjection", orderProjection),
                Pair("matchProjection", matchProjection),
                Pair("includeOverallPosition", includeOverallPosition),
                Pair("partitionMatchedByStrategyRef", partitionMatchedByStrategyRef),
                Pair("customerStrategyRefs", customerStrategyRefs),
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

    /**
     * Retrieve profit and loss for a given list of OPEN markets. The values are calculated using matched bets and
     * optionally settled bets. Only odds (MarketBettingType = ODDS) markets  are implemented, markets of other types
     * are silently ignored.
     * To retrieve your profit and loss for CLOSED markets, please use the listClearedOrders request.
     * Please note:  Market Data Request Limits apply to requests made to listMarketProfitAndLoss
     *
     * @param marketIds: (required) List of markets to calculate profit and loss
     * @param includeSettledBets: Option to include settled bets (partially settled markets only). Defaults to false if
     * not specified.
     * @param includeBspBets: Option to include BSP bets. Defaults to false if not specified.
     * @param netOfCommission: Option to return profit and loss net of users current commission rate for this market
     * including any special tariffs. Defaults to false if not specified.
     * @throws APINGException
     */
    fun listMarketProfitAndLoss(
        marketIds: Set<String>,
        includeSettledBets: Boolean,
        includeBspBets: Boolean,
        netOfCommission: Boolean,
        sessionToken: String,
        applicationKey: String
    ): List<MarketProfitAndLoss> {
        val request = createRequest(
            "listMarketProfitAndLoss",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("marketIds", marketIds),
                Pair("includeSettledBets", includeSettledBets),
                Pair("includeBspBets", includeBspBets),
                Pair("netOfCommission", netOfCommission),
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of your current orders. Optionally you can filter and sort your current orders using the various
     * parameters, setting none of the parameters will return all of your current orders up to a maximum of 1000 bets,
     * ordered BY_BET and sorted EARLIEST_TO_LATEST. To retrieve more than 1000 orders, you need to make use of the
     * fromRecord and recordCount parameters.
     *
     * To efficiently track new bet matches from a specific time, customers should use a combination of the dateRange,
     * orderBy "BY_MATCH_TIME" and orderProjection “ALL” to filter fully/partially matched orders from the list of
     * returned bets. The response will then filter out any bet records that have no matched date and provide a list of
     * betIds in the order which they are fully/partially matched from the date and time specified in the dateRange
     * field.
     *
     * @param betIds: Optionally restricts the results to the specified bet IDs. A maximum of 250 betId's, or a
     * combination of 250 betId's & marketId's are permitted.
     * @param marketIds: Optionally restricts the results to the specified market IDs. A maximum of 250 marketId's, or a
     * combination of 250 marketId's & betId's are permitted.
     * @param orderProjection: Optionally restricts the results to the specified order status.
     * @param customerOrderRefs: Optionally restricts the results to the specified customer order references.
     * @param customerStrategyRefs: Optionally restricts the results to the specified customer strategy references.
     * @param dateRange: Optionally restricts the results to be from/to the specified date, these dates are contextual
     * to the orders being returned and therefore the dates used to filter on will change to placed, matched, voided or
     * settled dates depending on the orderBy. This date is inclusive, i.e. if an order was placed on exactly this date
     * (to the millisecond) then it will be included in the results. If the from is later than the to, no results will
     * be returned.
     * @param orderBy: Specifies how the results will be ordered. If no value is passed in, it defaults to BY_BET.  Also
     * acts as a filter such that only orders with a valid value in the field being ordered by will be returned (i.e.
     * BY_VOID_TIME returns only voided orders, BY_SETTLED_TIME (applies to partially settled markets) returns only
     * settled orders and BY_MATCH_TIME returns only orders with a matched date (voided, settled, matched orders)). Note
     * that specifying an orderBy parameter defines the context of the date filter applied by the dateRange parameter
     * (placed, matched, voided or settled date) - see the dateRange parameter description (above) for more information.
     * See also the OrderBy type definition.
     * @param sortDir: Specifies the direction the results will be sorted in. If no value is passed in, it defaults to
     * EARLIEST_TO_LATEST.
     * @param fromRecord: Specifies the first record that will be returned. Records start at index zero, not at index
     * one.
     * @param recordCount: Specifies how many records will be returned from the index position 'fromRecord'. Note that
     * there is a page size limit of 1000. A value of zero indicates that you would like all records (including and from
     * 'fromRecord') up to the limit.
     * @throws APINGException
     */
    fun listCurrentOrders(
        betIds: Set<String>?,
        marketIds: Set<String>?,
        orderProjection: OrderProjection?,
        customerOrderRefs: Set<String>?,
        customerStrategyRefs: Set<String>?,
        dateRange: TimeRange?,
        orderBy: OrderBy?,
        sortDir: SortDir?,
        fromRecord: Int?,
        recordCount: Int?,
        sessionToken: String,
        applicationKey: String
    ): CurrentOrderSummaryReport {
        val request = createRequest(
            "listCurrentOrders",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("betIds", betIds),
                Pair("marketIds", marketIds),
                Pair("orderProjection", orderProjection),
                Pair("customerOrderRefs", customerOrderRefs),
                Pair("customerStrategyRefs", customerStrategyRefs),
                Pair("dateRange", dateRange),
                Pair("orderBy", orderBy),
                Pair("sortDir", sortDir),
                Pair("fromRecord", fromRecord),
                Pair("recordCount", recordCount),
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of settled bets based on the bet status, ordered by settled date. To retrieve more than 1000
     * records, you need to make use of the fromRecord and recordCount parameters.
     * By default the service will return all available data for the last 90 days (see Best Practice note below).
     *
     * You should specify a settledDateRange "from" date when making requests for data. This reduces the amount of data
     * that requires downloading & improves the speed of the response. Specifying a "from" date of the last call will
     * ensure that only new data is returned.
     *
     * @param betStatus: (required) Restricts the results to the specified status.
     * @param eventTypeIds: Optionally restricts the results to the specified Event Type IDs.
     * @param eventIds: Optionally restricts the results to the specified Event IDs.
     * @param marketIds: Optionally restricts the results to the specified market IDs.
     * @param runnerIds: Optionally restricts the results to the specified Runners.
     * @param betIds: Optionally restricts the results to the specified bet IDs. A maximum of 1000 betId's are allowed
     * in a single request.
     * @param customerOrderRefs: Optionally restricts the results to the specified customer order references.
     * @param customerStrategyRefs: Optionally restricts the results to the specified customer strategy references.
     * @param side: Optionally restricts the results to the specified side.
     * @param settledDateRange: Optionally restricts the results to be from/to the specified settled date. This date is
     * inclusive, i.e. if an order was cleared on exactly this date (to the millisecond) then it will be included in the
     * results. If the from is later than the to, no results will be returned.
     * @param Please Note: if you have a longer running market that is settled at multiple different times then there is
     * no way to get the returned market rollup to only include bets settled in a certain date range, it will always
     * return the overall position from the market including all settlements.
     * @param groupBy: How to aggregate the lines, if not supplied then the lowest level is returned, i.e. bet by bet
     * This is only applicable to SETTLED BetStatus.
     * @param includeItemDescription: If true then an ItemDescription object is included in the response.
     * @param locale: The language used for the itemDescription. If not specified, the customer account default is
     * returned.
     * @param fromRecord: Specifies the first record that will be returned. Records start at index zero.
     * @param recordCount: Specifies how many records will be returned, from the index position 'fromRecord'. Note that
     * there is a page size limit of 1000. A value of zero indicates that you would like all records (including and from
     * 'fromRecord') up to the limit.
     * @throws APINGException
     */
    fun listClearedOrders(
        betStatus: BetStatus,
        eventTypeIds: Set<String>?,
        eventIds: Set<String>?,
        marketIds: Set<String>?,
        runnerIds: Set<RunnerId>?,
        betIds: Set<String>?,
        customerOrderRefs: Set<String>?,
        customerStrategyRefs: Set<String>?,
        side: Side?,
        settledDateRange: TimeRange?,
        groupBy: GroupBy?,
        includeItemDescription: Boolean?,
        locale: String?,
        fromRecord: Int?,
        recordCount: Int?,
        sessionToken: String,
        applicationKey: String
        ): ClearedOrderSummaryReport {
        val request = createRequest(
            "listClearedOrders",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("betStatus", betStatus),
                Pair("eventTypeIds", eventTypeIds),
                Pair("eventIds", eventIds),
                Pair("marketIds", marketIds),
                Pair("runnerIds", runnerIds),
                Pair("betIds", betIds),
                Pair("customerOrderRefs", customerOrderRefs),
                Pair("customerStrategyRefs", customerStrategyRefs),
                Pair("side", side),
                Pair("settledDateRange", settledDateRange),
                Pair("groupBy", groupBy),
                Pair("includeItemDescription", includeItemDescription),
                Pair("locale", locale),
                Pair("fromRecord", fromRecord),
                Pair("recordCount", recordCount),
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
