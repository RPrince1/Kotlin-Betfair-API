package com.prince.betfair.betfair.betting

import com.prince.betfair.betfair.betting.entities.market.KeyLineDescription
import com.prince.betfair.betfair.betting.entities.market.Runner
import com.prince.betfair.betfair.betting.enums.market.MarketStatus
import java.util.*

/**
 * The dynamic data in a market
 *
 * @property marketId: (required) The unique identifier for the market. MarketId's are prefixed with '1.'
 * @property isMarketDataDelayed: (required) True if the data returned by listMarketBook will be delayed. The data may be delayed
 * because you are not logged in with a funded account or you are using an Application Key that does not allow up to
 * date data.
 * @property status: The status of the market, for example OPEN, SUSPENDED, CLOSED (settled), etc.
 * @property betDelay: The number of seconds an order is held until it is submitted into the market. Orders are usually delayed when the market is in-play
 * @property bspReconciled: True if the market starting price has been reconciled
 * @property complete: If false, runners may be added to the market
 * @property inplay: True if the market is currently in play
 * @property numberOfWinners: The number of selections that could be settled as winners
 * @property numberOfRunners: The number of runners in the market
 * @property numberOfActiveRunners: The number of runners that are currently active. An active runner is a selection
 * available for betting
 * @property lastMatchTime: The most recent time an order was executed
 * @property totalMatched: The total amount matched
 * @property totalAvailable: The total amount of orders that remain unmatched
 * @property crossMatching: True if cross matching is enabled for this market.
 * @property runnersVoidable: True if runners in the market can be voided. Please note - this doesn't include horse
 * racing markets under which bets are voided on non-runners with any applicable reduction factor applied
 * @property version: The version of the market. The version increments whenever the market status changes, for example,
 * turning in-play, or suspended when a goal is scored.
 * @property runners: Information about the runners (selections) in the market.
 * @property keyLineDescription: Description of a markets key line for valid market types
 */
data class MarketBook(
    val marketId: String,
    val isMarketDataDelayed: Boolean,
    val status: MarketStatus?,
    val betDelay: Int?,
    val bspReconciled: Boolean?,
    val complete: Boolean?,
    val inplay: Boolean?,
    val numberOfWinners: Int?,
    val numberOfRunners: Int?,
    val numberOfActiveRunners: Int?,
    val lastMatchTime: Date?,
    val totalMatched: Double?,
    val totalAvailable: Double?,
    val crossMatching: Boolean?,
    val runnersVoidable: Boolean?,
    val version: Long?,
    val runners: List<Runner>?,
    val keyLineDescription: KeyLineDescription?
)
