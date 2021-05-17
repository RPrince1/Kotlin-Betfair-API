package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.entities.ItemDescription
import com.prince.betfair.betfair.betting.enums.market.OrderType
import com.prince.betfair.betfair.betting.enums.order.PersistenceType
import com.prince.betfair.betfair.betting.enums.market.Side
import java.util.*

/**
 * Summary of a cleared order.
 *
 * @property eventTypeId: The id of the event type bet on. Available at EVENT_TYPE groupBy level or lower.
 * @property eventId: The id of the event bet on. Available at EVENT groupBy level or lower.
 * @property marketId: The id of the market bet on. Available at MARKET groupBy level or lower.
 * @property selectionId: The id of the selection bet on. Available at RUNNER groupBy level or lower.
 * @property handicap: The handicap.  Enter the specific handicap value (returned by RUNNER in listMaketBook) if the
 * market is an Asian handicap market. Available at MARKET groupBy level or lower.
 * @property betId: The id of the bet. Available at BET groupBy level.
 * @property placedDate: The date the bet order was placed by the customer. Only available at BET groupBy level.
 * @property persistenceType: The turn in play persistence state of the order at bet placement time. This field will be
 * empty or omitted on true SP bets. Only available at BET groupBy level.
 * @property orderType: The type of bet (e.g standard limited-liability Exchange bet (LIMIT), a standard BSP bet
 * (MARKET_ON_CLOSE), or a minimum-accepted-price BSP bet (LIMIT_ON_CLOSE)). If the bet has a OrderType of
 * MARKET_ON_CLOSE and a persistenceType of MARKET_ON_CLOSE then it is a bet which has transitioned from LIMIT to
 * MARKET_ON_CLOSE. Only available at BET groupBy level.
 * @property side: Whether the bet was a back or lay bet. Available at SIDE groupBy level or lower.
 * @property itemDescription: A container for all the ancillary data and localised text valid for this Item
 * @property betOutcome: The settlement outcome of the bet. Tri-state (WIN/LOSE/PLACE) to account for Each Way bets
 * where the place portion of the bet won but the win portion lost. The profit/loss amount in this case could be
 * positive or negative depending on the price matched at. Only available at BET groupBy level.
 * @property priceRequested: The average requested price across all settled bet orders under this Item. Available at
 * SIDE groupBy level or lower. For LINE markets this is the line position requested. For LINE markets this is the
 * line position requested.
 * @property settledDate: The date and time the bet order was settled by Betfair. Available at SIDE groupBy level or
 * lower.
 * @property lastMatchedDate: The date and time the last bet order was matched by Betfair. Available on Settled orders
 * only.
 * @property betCount: The number of actual bets within this grouping (will be 1 for BET groupBy)
 * @property commission: The cumulative amount of commission paid by the customer across all bets under this Item, in
 * the account currency. Available at EXCHANGE, EVENT_TYPE, EVENT and MARKET level groupings only.
 * @property priceMatched: The average matched price across all settled bets or bet fragments under this Item. Available
 * at SIDE groupBy level or lower. For LINE markets this is the line position matched at.
 * @property priceReduced: If true, then the matched price was affected by a reduction factor due to of a runner removal
 * from this Horse Racing market.
 * @property sizeSettled: The cumulative bet size that was settled as matched or voided under this Item, in the account
 * currency. Available at SIDE groupBy level or lower.
 * @property profit: The profit or loss (negative profit) gained on this line, in the account currency
 * @property sizeCancelled: The amount of the bet that was available to be matched, before cancellation or lapsing, in
 * the account currency
 * @property customerOrderRef: The order reference defined by the customer for the bet order
 * @property customerStrategyRef: The strategy reference defined by the customer for the bet order
 */
data class ClearedOrderSummary(
    val eventTypeId: String?,
    val eventId: String?,
    val marketId: String?,
    val selectionId: Long?,
    val handicap: Double?,
    val betId: String?,
    val placedDate: Date?,
    val persistenceType: PersistenceType?,
    val orderType: OrderType?,
    val side: Side?,
    val itemDescription: ItemDescription?,
    val betOutcome: String?,
    val priceRequested: Double?,
    val settledDate: Date?,
    val lastMatchedDate: Date?,
    val betCount: Int?,
    val commission: Double?,
    val priceMatched: Double?,
    val priceReduced: Boolean?,
    val sizeSettled: Double?,
    val profit: Double?,
    val sizeCancelled: Double?,
    val customerOrderRef: String?,
    val customerStrategyRef: String?
)
