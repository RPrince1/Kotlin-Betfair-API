package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.enums.market.RollUpModel

/**
 * Options to alter the default representation of best offer prices
 *
 * @property bestPricesDepth: The maximum number of prices to return on each side for each runner. If unspecified
 * defaults to 3. Maximum returned price depth returned is 10.
 * @property rollupModel: The model to use when rolling up available sizes. If unspecified defaults to STAKE rollup
 * model with rollupLimit of minimum stake in the specified currency.
 * @property rollupLimit: The volume limit to use when rolling up returned sizes. The exact definition of the limit
 * depends on the rollupModel. If no limit is provided it will use minimum stake as default the value. Ignored if no
 * rollup model is specified.
 * @property rollupLiabilityThreshold: Only applicable when rollupModel is MANAGED_LIABILITY. The rollup model switches
 * from being stake based to liability based at the smallest lay price which is >= rollupLiabilityThreshold.service
 * level default (TBD). Not supported as yet.
 * @property rollupLiabilityFactor: Only applicable when rollupModel is MANAGED_LIABILITY.
 * (rollupLiabilityFactor * rollupLimit) is the minimum liabilty the user is deemed to be comfortable with. After the
 * rollupLiabilityThreshold price subsequent volumes will be rolled up to minimum value such that the liability >= the
 * minimum liability.service level default (5). Not supported as yet.
 */
data class ExBestOffersOverrides(
    val rollupLiabilityFactor: Int,
    val rollupLiabilityThreshold: Double,
    val bestPricesDepth: Int,
    val rollupModel: RollUpModel,
    val rollupLimit: Int
)
