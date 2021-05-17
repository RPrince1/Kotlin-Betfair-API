package com.prince.betfair.betfair.betting.enums.order

/**
 * @property ERROR_IN_MATCHER: The matcher is not healthy. Please note: The error will also be returned is you attempt
 * concurrent 'cancel all' bets requests using cancelOrders which isn't permitted.
 * @property PROCESSED_WITH_ERRORS: The order itself has been accepted, but at least one (possibly all) actions have
 * generated errors
 * @property BET_ACTION_ERROR: There is an error with an action that has caused the entire order to be rejected. Check
 * the instructionReports errorCode for the reason for the rejection of the order.
 * @property INVALID_ACCOUNT_STATE: Order rejected due to the account's status (suspended, inactive, dup cards)
 * @property INVALID_WALLET_STATUS: Order rejected due to the account's wallet's status
 * @property INSUFFICIENT_FUNDS: Account has exceeded its exposure limit or available to bet limit
 * @property LOSS_LIMIT_EXCEEDED: The account has exceed the self imposed loss limit
 * @property MARKET_SUSPENDED: Market is suspended
 * @property MARKET_NOT_OPEN_FOR_BETTING: Market is not open for betting. It is either not yet active, suspended or
 * closed awaiting settlement.
 * @property DUPLICATE_TRANSACTION: Duplicate customer reference data submitted - Please note: There is a time window
 * associated with the de-duplication of duplicate submissions which is 60 second
 * @property INVALID_ORDER: Order cannot be accepted by the matcher due to the combination of actions. For example, bets
 * being edited are not on the same market, or order includes both edits and placement
 * @property INVALID_MARKET_ID: Market doesn't exist
 * @property PERMISSION_DENIED: Business rules do not allow order to be placed. You are either attempting to place the
 * order using a Delayed Application Key or from a restricted jurisdiction (i.e. USA)
 * @property DUPLICATE_BETIDS: Duplicate bet ids found. For example, you've included the same betId more than once in a
 * single cancelOrders request.
 * @property NO_ACTION_REQUIRED: Order hasn't been passed to matcher as system detected there will be no state change
 * @property SERVICE_UNAVAILABLE: The requested service is unavailable
 * @property REJECTED_BY_REGULATOR: The regulator rejected the order. On the Italian Exchange this error will occur if
 * more than 50 bets are sent in a single placeOrders request.
 * @property NO_CHASING: A specific error code that relates to Spanish Exchange markets only which indicates that the
 * bet placed contravenes the Spanish regulatory rules relating to loss chasing.
 * @property REGULATOR_IS_NOT_AVAILABLE: The underlying regulator service is not available.
 * @property TOO_MANY_INSTRUCTIONS: The amount of orders exceeded the maximum amount allowed to be executed
 * @property INVALID_MARKET_VERSION: The supplied market version is invalid. Max length allowed for market version is 12.
 * @property INVALID_PROFIT_RATIO: The order falls outside the permitted price and size combination.
 */
enum class ExecutionReportErrorCode {
    ERROR_IN_MATCHER,
    PROCESSED_WITH_ERRORS,
    BET_ACTION_ERROR,
    INVALID_ACCOUNT_STATE,
    INVALID_WALLET_STATUS,
    INSUFFICIENT_FUNDS,
    LOSS_LIMIT_EXCEEDED,
    MARKET_SUSPENDED,
    MARKET_NOT_OPEN_FOR_BETTING,
    DUPLICATE_TRANSACTION,
    INVALID_ORDER,
    INVALID_MARKET_ID,
    PERMISSION_DENIED,
    DUPLICATE_BETIDS,
    NO_ACTION_REQUIRED,
    SERVICE_UNAVAILABLE,
    REJECTED_BY_REGULATOR,
    NO_CHASING,
    REGULATOR_IS_NOT_AVAILABLE,
    TOO_MANY_INSTRUCTIONS,
    INVALID_MARKET_VERSION,
    INVALID_PROFIT_RATIO
}
