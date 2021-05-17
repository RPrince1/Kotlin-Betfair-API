package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.entities.market.PriceSize
import com.prince.betfair.betfair.betting.enums.market.OrderStatus
import com.prince.betfair.betfair.betting.enums.market.OrderType
import com.prince.betfair.betfair.betting.enums.order.PersistenceType
import com.prince.betfair.betfair.betting.enums.market.Side
import java.util.*

/**
 * Summary of a current order.
 *
 * @property betId: (required) The bet ID of the original place order.
 * @property marketId: (required) The market id the order is for.
 * @property selectionId: (required) The selection id the order is for.
 * @property handicap: The handicap associated with the runner in case of Asian handicap markets, null
 * otherwise.
 * @property priceSize: (required) The price and size of the bet.
 * @property bspLiability: (required) Not to be confused with size. This is the liability of a given BSP bet.
 * @property side: (required) BACK/LAY
 * @property status: (required) Either EXECUTABLE (an unmatched amount remains) or EXECUTION_COMPLETE (no unmatched
 * amount remains).
 * @property persistenceType: (required) What to do with the order at turn-in-play.
 * @property orderType: (required) BSP Order type.
 * @property placedDate: (required) The date, to the second, the bet was placed.
 * @property matchedDate: The date, to the second, of the last matched bet fragment (where applicable)
 * @property averagePriceMatched: The average price matched at. Voided match fragments are removed from this average
 * calculation. The price is automatically adjusted in the event of non runners being declared with applicable reduction
 * factors. Please note: This value is not meaningful for activity on LINE markets and is not guaranteed to be returned
 * or maintained for these markets.
 * @property sizeMatched: The current amount of this bet that was matched.
 * @property sizeRemaining: The current amount of this bet that is unmatched.
 * @property sizeLapsed: The current amount of this bet that was lapsed.
 * @property sizeCancelled: The current amount of this bet that was cancelled.
 * @property sizeVoided: The current amount of this bet that was voided.
 * @property regulatorAuthCode: The regulator authorisation code.
 * @property regulatorCode: The regulator Code.
 * @property customerOrderRef: The order reference defined by the customer for this bet
 * @property customerStrategyRef: The strategy reference defined by the customer for this bet
 */
data class CurrentOrderSummary(
    val betId: String,
    val marketId: String,
    val selectionId: Long,
    val handicap: Double?,
    val priceSize: PriceSize,
    val bspLiability: Double,
    val side: Side,
    val status: OrderStatus,
    val persistenceType: PersistenceType,
    val orderType: OrderType,
    val placedDate: Date,
    val matchedDate: Date?,
    val averagePriceMatched: Double?,
    val sizeMatched: Double?,
    val sizeRemaining: Double?,
    val sizeLapsed: Double?,
    val sizeCancelled: Double?,
    val sizeVoided: Double?,
    val regulatorAuthCode: String?,
    val regulatorCode: String?,
    val customerOrderRef: String?,
    val customerStrategyRef: String?
)
