package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.enums.market.RunnerStatus
import java.util.*

/**
 * The dynamic data about runners in a market
 *
 * @property selectionId: (required) The unique id of the runner (selection). Please note - the same selectionId and
 * runnerName pairs are used accross all Betfair markets which contain them.
 * @property handicap: (required) The handicap.  Enter the specific handicap value (returned by RUNNER in listMaketBook)
 * if the market is an Asian handicap market.
 * @property status: (required) The status of the selection (i.e., ACTIVE, REMOVED, WINNER, PLACED, LOSER, HIDDEN)
 * Runner status information is available for 90 days following market settlement.
 * @property adjustmentFactor: The adjustment factor applied if the selection is removed
 * @property lastPriceTraded: The price of the most recent bet matched on this selection
 * @property totalMatched: The total amount matched on this runner
 * @property removalDate: If date and time the runner was removed
 * @property sp: The BSP related prices for this runner
 * @property ex: The Exchange prices available for this runner
 * @property orders: List of orders in the market
 * @property matches: List of matches (i.e, orders that have been fully or partially executed)
 * @property matchesByStrategy: List of matches for each strategy, ordered by matched data
 */
data class Runner(
    val selectionId: Long,
    val handicap: Double,
    val status: RunnerStatus,
    val adjustmentFactor: Double?,
    val lastPriceTraded: Double?,
    val totalMatched: Double?,
    val removalDate: Date?,
    val sp: StartingPrices?,
    val ex: ExchangePrices?,
    val orders: List<Order>?,
    val matches: List<Match>?,
    val matchesByStrategy: Map<String, Match>?
)
