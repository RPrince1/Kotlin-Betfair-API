package com.prince.betfair.betfair.betting.enums.market

/**
 * @property STAKE: The volumes will be rolled up to the minimum value which is >= rollupLimit.
 * @property PAYOUT: The volumes will be rolled up to the minimum value where the payout( price * volume )
 * is >= rollupLimit. On a LINE market, volumes will be rolled up where payout( 2.0 * volume ) is >= rollupLimit
 * @property MANAGED_LIABILITY: The volumes will be rolled up to the minimum value which is >= rollupLimit, until a lay
 * price threshold. There after, the volumes will be rolled up to the minimum value such that the liability >= a minimum
 * liability. Not supported as yet.
 * @property NONE: No rollup will be applied. However the volumes will be filtered by currency specific minimum stake
 * unless overridden specifically for the channel.
 */
enum class RollUpModel {
    STAKE,
    PAYOUT,
    MANAGED_LIABILITY,
    NONE
}
