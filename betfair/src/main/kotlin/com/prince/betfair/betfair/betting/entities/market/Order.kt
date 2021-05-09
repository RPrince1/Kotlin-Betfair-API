package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.enums.market.OrderStatus
import com.prince.betfair.betfair.betting.enums.market.OrderType
import com.prince.betfair.betfair.betting.enums.market.PersistenceType
import com.prince.betfair.betfair.betting.enums.market.Side
import java.util.*

/**
 * @property betId: (required)
 * @property orderType: (required) BSP Order type.
 * @property status: (required) Either EXECUTABLE (an unmatched amount remains) or EXECUTION_COMPLETE (no unmatched
 * amount remains).
 * @property persistenceType: (required) What to do with the order at turn-in-play
 * @property side: (required) Indicates if the bet is a Back or a LAY. For LINE markets customers either Buy a line (LAY
 * bet, winning if outcome is greater than the taken line (price)) or Sell a line (BACK bet, winning if outcome is less
 * than the taken line (price))
 * @property price: (required) The price of the bet. Please note: LINE markets operate at even-money odds of 2.0.
 * However, price for these markets refers to the line positions available as defined by the markets min-max range and
 * interval steps
 * @property size: (required) The size of the bet.
 * @property bspLiability: (required) Not to be confused with size. This is the liability of a given BSP bet.
 * @property placedDate: (required) The date, to the second, the bet was placed.
 * @property avgPriceMatched: The average price matched at. Voided match fragments are removed from this average
 * calculation. For MARKET_ON_CLOSE BSP bets this reports the matched SP price following the SP reconciliation process.
 * This value is not meaningful for activity on LINE markets and is not guaranteed to be returned or maintained for
 * these markets.
 * @property sizeMatched: The current amount of this bet that was matched.
 * @property sizeRemaining: The current amount of this bet that is unmatched.
 * @property sizeLapsed: The current amount of this bet that was lapsed.
 * @property sizeCancelled: The current amount of this bet that was cancelled.
 * @property sizeVoided: The current amount of this bet that was voided.
 * @property customerOrderRef: The customer order reference sent for this bet
 * @property customerStrategyRef: The customer strategy reference sent for this bet
 */
data class Order(
    val betId: String,
    val orderType: OrderType,
    val status: OrderStatus,
    val persistenceType: PersistenceType,
    val side: Side,
    val price: Double,
    val size: Double,
    val bspLiability: Double,
    val placedDate: Date,
    val avgPriceMatched: Double?,
    val sizeMatched: Double?,
    val sizeRemaining: Double?,
    val sizeLapsed: Double?,
    val sizeCancelled: Double?,
    val sizeVoided: Double?,
    val customerOrderRef: String?,
    val customerStrategyRef: String?
)
