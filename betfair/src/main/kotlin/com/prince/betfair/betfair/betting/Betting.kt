package com.prince.betfair.betfair.betting

import com.fasterxml.jackson.module.kotlin.readValue

import com.prince.betfair.betfair.betting.entities.*
import com.prince.betfair.betfair.betting.entities.competition.CompetitionResult
import com.prince.betfair.betfair.betting.entities.competition.CountryCodeResult
import com.prince.betfair.betfair.betting.entities.event.EventResult
import com.prince.betfair.betfair.betting.entities.event.EventTypeResult
import com.prince.betfair.betfair.betting.entities.market.*
import com.prince.betfair.betfair.betting.entities.order.*
import com.prince.betfair.betfair.betting.enums.*
import com.prince.betfair.betfair.betting.enums.market.*
import com.prince.betfair.betfair.betting.enums.order.BetStatus
import com.prince.betfair.betfair.betting.enums.order.GroupBy
import com.prince.betfair.betfair.betting.enums.order.OrderBy
import com.prince.betfair.betfair.betting.enums.order.SortDir
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

    /**
     * Returns a list of Event Types (i.e. Sports) associated with the markets selected by the MarketFilter.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun listEventTypes(
        filter: MarketFilter,
        locale: String? = null,
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
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of Competitions (i.e., World Cup 2013) associated with the markets selected by the MarketFilter.
     * Currently only Football markets have an associated competition.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun listCompetitions(
        filter: MarketFilter,
        locale: String? = null,
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
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of time ranges in the granularity specified in the request (i.e. 3PM to 4PM, Aug 14th to Aug 15th) associated with the markets selected by the MarketFilter.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param granularity: (required) The granularity of time periods that correspond to markets selected by the market
     * filter.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun listTimeRanges(
        filter: MarketFilter,
        granularity: TimeGranularity,
        sessionToken: String,
        applicationKey: String
    ): List<TimeRangeResult> {
        val request = createRequest(
            "listTimeRanges",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("granularity", granularity)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of Events (i.e, Reading vs. Man United) associated with the markets selected by the MarketFilter.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun listEvents(
        filter: MarketFilter,
        locale: String? = null,
        sessionToken: String,
        applicationKey: String
    ): List<EventResult> {
        val request = createRequest(
            "listEvents",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of market types (i.e. MATCH_ODDS, NEXT_GOAL) associated with the markets selected by the
     * MarketFilter. The market types are always the same, regardless of locale.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun listMarketTypes(
        filter: MarketFilter,
        locale: String? = null,
        sessionToken: String,
        applicationKey: String
    ): List<MarketTypeResult> {
        val request = createRequest(
            "listMarketTypes",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of Countries associated with the markets selected by the MarketFilter.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun listCountries(
        filter: MarketFilter,
        locale: String? = null,
        sessionToken: String,
        applicationKey: String
    ): List<CountryCodeResult> {
        val request = createRequest(
            "listCountries",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of Venues (i.e. Cheltenham, Ascot) associated with the markets selected by the MarketFilter.
     * Currently, only Horse Racing markets are associated with a Venue.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun listVenues(
        filter: MarketFilter,
        locale: String? = null,
        sessionToken: String,
        applicationKey: String
    ): List<VenueResult> {
        val request = createRequest(
            "listVenues",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("filter", filter),
                Pair("locale", locale)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Returns a list of information about published (ACTIVE/SUSPENDED) markets that does not change (or changes very
     * rarely). You use listMarketCatalogue to retrieve the name of the market, the names of selections and other
     * information about markets.  Market Data Request Limits apply to requests made to listMarketCatalogue.
     *
     * Please note: listMarketCatalogue does not return markets that are CLOSED.
     *
     * @param filter: (required) The filter to select desired markets. All markets that match the criteria in the filter
     * are selected.
     * @param marketProjection: The type and amount of data returned about the market.
     * @param sort: The order of the results. Will default to RANK if not passed. RANK is an assigned priority that is
     * determined by our Market Operations team in our back-end system. A result's overall rank is derived from the
     * ranking given to the flowing attributes for the result. EventType, Competition, StartTime, MarketType, MarketId.
     * For example, EventType is ranked by the most popular sports types and marketTypes are ranked in the following
     * order: ODDS ASIAN LINE RANGE If all other dimensions of the result are equal, then the results are ranked in
     * MarketId order.
     * @param maxResults: (required) limit on the total number of results returned, must be greater than 0 and less than
     * or equal to 1000
     * @param locale: The language used for the response. If not specified, the default is returned.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
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
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
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
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
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
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
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
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
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
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
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

    /**
     * Place new orders into market. Please note that additional bet sizing rules apply to bets placed into the Italian
     * Exchange.
     *
     * In normal circumstances the placeOrders is an atomic operation.  PLEASE NOTE: if the 'Best Execution' features is
     * switched off, placeOrders can return ‘PROCESSED_WITH_ERRORS’ meaning that some bets can be rejected and other
     * placed when submitted in the same PlaceInstruction
     *
     * @param marketId: (required) The market id these orders are to be placed on
     * @param instructions: (required) The number of place instructions.  The limit of place instructions per request is
     * 200 for the Global Exchange and 50 for the Italian Exchange.
     * @param customerRef: Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used
     * to de-dupe mistaken re-submissions.   CustomerRef can contain: upper/lower chars, digits, chars : - . _ + * : ; ~
     * only. Please note: There is a time window associated with the de-duplication of duplicate submissions which is 60
     * seconds.
     * @param marketVersion: Optional parameter allowing the client to specify which version of the market the orders
     * should be placed on. If the current market version is higher than that sent on an order, the bet will be lapsed.
     * @param customerStrategyRef: An optional reference customers can use to specify which strategy has sent the order.
     * The reference will be returned on order change messages through the stream API. The string is limited to 15
     * characters. If an empty string is provided it will be treated as null.
     * @param async: An optional flag (not setting equates to false) which specifies if the orders should be placed
     * asynchronously. Orders can be tracked via the Exchange Stream API or or the API-NG by providing a
     * customerOrderRef for each place order. An order's status will be PENDING and no bet ID will be returned. This
     * functionality is available for all bet types - including Market on Close and Limit on Close
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun placeOrders(
        marketId: String,
        instructions: List<PlaceInstruction>,
        customerRef: String?,
        marketVersion: MarketVersion?,
        customerStrategyRef: String?,
        async: Boolean?,
        sessionToken: String,
        applicationKey: String
    ): PlaceExecutionReport
    {
        val request = createRequest(
            "placeOrders",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("marketId", marketId),
                Pair("instructions", instructions),
                Pair("customerRef", customerRef),
                Pair("marketVersion", marketVersion),
                Pair("customerStrategyRef", customerStrategyRef),
                Pair("async", async)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Cancel all bets OR cancel all bets on a market OR fully or partially cancel particular orders on a market. Only
     * LIMIT orders can be cancelled or partially cancelled once placed.
     *
     * @param marketId: If marketId and betId aren't supplied all bets are cancelled. Please note: Concurrent requests
     * to cancel all bets will be rejected until the initial request to cancel all bets is complete.
     * @param instructions: All instructions need to be on the same market. If not supplied all unmatched bets on the
     * market (if market id is passed) are fully cancelled.  The limit of cancel instructions per request is 60
     * @param customerRef: Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used
     * to de-dupe mistaken re-submissions.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun cancelOrders(
        marketId: String?,
        instructions: List<CancelInstruction>?,
        customerRef: String?,
        sessionToken: String,
        applicationKey: String
    ): CancelExecutionReport {
        val request = createRequest(
            "cancelOrders",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("marketId", marketId),
                Pair("instructions", instructions),
                Pair("customerRef", customerRef),
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * This operation is logically a bulk cancel followed by a bulk place. The cancel is completed first then the new
     * orders are placed. The new orders will be placed atomically in that they will all be placed or none will be
     * placed. In the case where the new orders cannot be placed the cancellations will not be rolled back. See
     * ReplaceInstruction.
     *
     * @param marketId: (required) The market id these orders are to be placed on
     * @param instructions: (required) The number of replace instructions.  The limit of replace instructions per
     * request is 60.
     * @param customerRef: Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used
     * to de-dupe mistaken re-submissions.
     * @param marketVersion: Optional parameter allowing the client to specify which version of the market the orders
     * should be placed on. If the current market version is higher than that sent on an order, the bet will be lapsed.
     * @param async: An optional flag (not setting equates to false) which specifies if the orders should be replaced
     * asynchronously. Orders can be tracked via the Exchange Stream API or the API-NG by providing a customerOrderRef
     * for each replace order. Not available for MOC or LOC bets.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun replaceOrders(
        marketId: String,
        instructions: List<ReplaceInstruction>,
        customerRef: String?,
        marketVersion: MarketVersion?,
        async: Boolean?,
        sessionToken: String,
        applicationKey: String
    ): ReplaceExecutionReport {
        val request = createRequest(
            "replaceOrders",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("marketId", marketId),
                Pair("instructions", instructions),
                Pair("customerRef", customerRef),
                Pair("marketVersion", marketVersion),
                Pair("async", async)
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    /**
     * Update non-exposure changing fields
     *
     * @param marketId: (required) The market id these orders are to be placed on
     * @param instructions: (required) The number of update instructions.  The limit of update instructions per request
     * is 60
     * @param customerRef: Optional parameter allowing the client to pass a unique string (up to 32 chars) that is used
     * to de-dupe mistaken re-submissions.
     * @param sessionToken: (required) Session token provided by ClientSSO
     * @param applicationKey: (required) Application key identifying your api client
     * @throws APINGException
     */
    fun updateOrders(
        marketId: String,
        instructions: List<UpdateInstruction>,
        customerRef: String?,
        sessionToken: String,
        applicationKey: String
    ): UpdateExecutionReport {
        val request = createRequest(
            "updateOrders",
            sessionToken,
            applicationKey,
            mapOf(
                Pair("marketId", marketId),
                Pair("instructions", instructions),
                Pair("customerRef", customerRef),
            )
        )

        val response = client.newCall(request).execute()
        val body = handleResponse(response)

        return objectMapper.readValue(body)
    }

    private fun handleResponse(response: Response): String {
        val responseBody = response.body?.string()

        println(responseBody)

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
