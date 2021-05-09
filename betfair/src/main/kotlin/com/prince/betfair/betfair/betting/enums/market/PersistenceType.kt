package com.prince.betfair.betfair.betting.enums.market

/**
 * @property LAPSE: Lapse the order when the market is turned in-play
 * @property PERSIST: Persist the order to in-play. The bet will be place automatically into the in-play market at the
 * start of the event.
 * @property MARKET_ON_CLOSE: Put the order into the auction (SP) at turn-in-play
 */
enum class PersistenceType {
    LAPSE,
    PERSIST,
    MARKET_ON_CLOSE
}
