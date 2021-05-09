package com.prince.betfair.betfair.betting.enums.market

/**
 * @property SP_AVAILABLE: Amount available for the BSP auction.
 * @property SP_TRADED: Amount traded in the BSP auction.
 * @property EX_BEST_OFFERS: Only the best prices available for each runner, to requested price depth.
 * @property EX_ALL_OFFERS: EX_ALL_OFFERS trumps EX_BEST_OFFERS if both settings are present
 * @property EX_TRADED: Amount traded on the exchange.
 */
enum class PriceData {
    SP_AVAILABLE,
    SP_TRADED,
    EX_BEST_OFFERS,
    EX_ALL_OFFERS,
    EX_TRADED
}