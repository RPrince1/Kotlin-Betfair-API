package com.prince.betfair.betfair.betting.entities.order

import com.prince.betfair.betfair.betting.enums.order.BetTargetType
import com.prince.betfair.betfair.betting.enums.order.PersistenceType
import com.prince.betfair.betfair.betting.enums.order.TimeInForce

/**
 * Place a new LIMIT order (simple exchange bet for immediate execution)
 *
 * @property size: (required) The size of the bet. Please note: For market type EACH_WAY. The total stake = size x 2
 * @property price: (required) The limit price. For LINE markets, the price at which the bet is settled and struck will always be 2.0 (Evens). On these bets, the Price field is used to indicate the line value which is being bought or sold
 * @property persistenceType: (required) What to do with the order at turn-in-play
 * @property timeInForce: The type of TimeInForce value to use. This value takes precedence over any PersistenceType value chosen. If this attribute is populated along with the PersistenceType field, then the PersistenceType will be ignored. When using FILL_OR_KILL for a Line market the Volume Weighted Average Price (VWAP) functionality is disabled
 * @property minFillSize: An optional field used if the TimeInForce attribute is populated. If specified without TimeInForce then this field is ignored. If no minFillSize is specified, the order is killed unless the entire size can be matched. If minFillSize is specified, the order is killed unless at least the minFillSize can be matched. The minFillSize cannot be greater than the order's size. If specified for a BetTargetType and FILL_OR_KILL order, then this value will be ignored
 * @property betTargetType: An optional field to allow betting to a targeted PAYOUT or BACKERS_PROFIT. It's invalid to specify both a Size and BetTargetType. Matching provides best execution at the requested price or better up to the payout or profit. If the bet is not matched completely and immediately, the remaining portion enters the unmatched pool of bets on the exchange. BetTargetType bets are invalid for LINE markets
 * @property betTargetSize: An optional field which must be specified if BetTargetType is specified for this order. The requested outcome size of either the payout or profit. This is named from the backer's perspective. For Lay bets the profit represents the bet's liability
 */
data class LimitOrder(
    val size: Double,
    val price: Double,
    val persistenceType: PersistenceType,
    val timeInForce: TimeInForce?,
    val minFillSize: Double?,
    val betTargetType: BetTargetType?,
    val betTargetSize: Double?
)
