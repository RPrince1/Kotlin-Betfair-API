package com.prince.betfair.betfair.betting.enums.market

/**
 * @property NO_ROLLUP: No rollup, return raw fragments
 * @property ROLLED_UP_BY_PRICE: Rollup matched amounts by distinct matched prices per side.
 * @property ROLLED_UP_BY_AVG_PRICE: Rollup matched amounts by average matched price per side
*/
enum class MatchProjection {
    NO_ROLLUP,
    ROLLED_UP_BY_PRICE,
    ROLLED_UP_BY_AVG_PRICE
}
