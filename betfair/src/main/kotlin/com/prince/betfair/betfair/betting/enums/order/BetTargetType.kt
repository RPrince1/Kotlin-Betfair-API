package com.prince.betfair.betfair.betting.enums.order

/**
 * @property BACKERS_PROFIT: The payout requested minus the calculated size at which this LimitOrder is to be placed.
 * BetTargetType bets are invalid for LINE markets
 * @property PAYOUT: The total payout requested on a LimitOrder
 */
enum class BetTargetType {
    BACKERS_PROFIT,
    PAYOUT
}
