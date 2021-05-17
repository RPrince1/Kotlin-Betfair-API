package com.prince.betfair.betfair.betting.enums.order

/**
 * @property INVALID_BET_SIZE: bet size is invalid for your currency or your regulator
 * @property INVALID_RUNNER: Runner does not exist, includes vacant traps in greyhound racing
 * @property BET_TAKEN_OR_LAPSED: Bet cannot be cancelled or modified as it has already been taken or has been cancelled/lapsed Includes attempts to cancel/modify market on close BSP bets and cancelling limit on close BSP bets. The error may be returned on placeOrders request if for example a bet is placed at the point when a market admin event takes place (i.e. market is turned in-play)
 * @property BET_IN_PROGRESS: No result was received from the matcher in a timeout configured for the system
 * @property RUNNER_REMOVED: Runner has been removed from the event
 * @property MARKET_NOT_OPEN_FOR_BETTING: Attempt to edit a bet on a market that has closed.
 * @property LOSS_LIMIT_EXCEEDED: The action has caused the account to exceed the self imposed loss limit
 * @property MARKET_NOT_OPEN_FOR_BSP_BETTING: Market now closed to bsp betting. Turned in-play or has been reconciled
 * @property INVALID_PRICE_EDIT: Attempt to edit down the price of a bsp limit on close lay bet, or edit up the price of a limit on close back bet
 * @property INVALID_ODDS: Odds not on price ladder - either edit or placement
 * @property INSUFFICIENT_FUNDS: Insufficient funds available to cover the bet action. Either the exposure limit or available to bet limit would be exceeded
 * @property INVALID_PERSISTENCE_TYPE: Invalid persistence type for this market, e.g. KEEP for a non in-play market.
 * @property ERROR_IN_MATCHER: A problem with the matcher prevented this action completing successfully
 * @property INVALID_BACK_LAY_COMBINATION: The order contains a back and a lay for the same runner at overlapping prices. This would guarantee a self match. This also applies to BSP limit on close bets
 * @property ERROR_IN_ORDER: The action failed because the parent order failed
 * @property INVALID_BID_TYPE: Bid type is mandatory
 * @property INVALID_BET_ID: Bet for id supplied has not been found
 * @property CANCELLED_NOT_PLACED: Bet cancelled but replacement bet was not placed
 * @property RELATED_ACTION_FAILED: Action failed due to the failure of a action on which this action is dependent
 * @property NO_ACTION_REQUIRED: the action does not result in any state change. eg changing a persistence to it's current value
 * @property TIME_IN_FORCE_CONFLICT: You may only specify a time in force on either the place request OR on individual limit order instructions (not both), since the implied behaviors are incompatible.
 * @property UNEXPECTED_PERSISTENCE_TYPE: You have specified a persistence type for a FILL_OR_KILL order, which is nonsensical because no umatched portion can remain after the order has been placed.
 * @property INVALID_ORDER_TYPE: You have specified a time in force of FILL_OR_KILL, but have included a non-LIMIT order type.
 * @property UNEXPECTED_MIN_FILL_SIZE: You have specified a minFillSize on a limit order, where the limit order's time in force is not FILL_OR_KILL. Using minFillSize is not supported where the time in force of the request (as opposed to an order) is FILL_OR_KILL.
 * @property INVALID_CUSTOMER_ORDER_REF: The supplied customer order reference is too long.
 * @property INVALID_MIN_FILL_SIZE: The minFillSize must be greater than zero and less than or equal to the order's size. The minFillSize cannot be less than the minimum bet size for your currency
 * @property BET_LAPSED_PRICE_IMPROVEMENT_TOO_LARGE: Your bet is lapsed. There is better odds than requested available in the market, but your preferences don't allow the system to match your bet against better odds. Change your betting preferences to accept better odds if you don't want to receive this error. Please see https://support.betfair.com/app/answers/detail/a_id/404/ for more details regarding Best Execution and how to update your settings.
 */
enum class InstructionReportErrorCode {
    INVALID_BET_SIZE,
    INVALID_RUNNER,
    BET_TAKEN_OR_LAPSED,
    BET_IN_PROGRESS,
    RUNNER_REMOVED,
    MARKET_NOT_OPEN_FOR_BETTING,
    LOSS_LIMIT_EXCEEDED,
    MARKET_NOT_OPEN_FOR_BSP_BETTING,
    INVALID_PRICE_EDIT,
    INVALID_ODDS,
    INSUFFICIENT_FUNDS,
    INVALID_PERSISTENCE_TYPE,
    ERROR_IN_MATCHER,
    INVALID_BACK_LAY_COMBINATION,
    ERROR_IN_ORDER,
    INVALID_BID_TYPE,
    INVALID_BET_ID,
    CANCELLED_NOT_PLACED,
    RELATED_ACTION_FAILED,
    NO_ACTION_REQUIRED,
    TIME_IN_FORCE_CONFLICT,
    UNEXPECTED_PERSISTENCE_TYPE,
    INVALID_ORDER_TYPE,
    UNEXPECTED_MIN_FILL_SIZE,
    INVALID_CUSTOMER_ORDER_REF,
    INVALID_MIN_FILL_SIZE,
    BET_LAPSED_PRICE_IMPROVEMENT_TOO_LARGE
}
