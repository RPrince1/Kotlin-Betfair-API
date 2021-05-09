package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.enums.market.Side
import java.util.*

/**
 * An individual bet Match, or rollup by price or avg price. Rollup depends on the requested MatchProjection
 *
 * @property betId: Only present if no rollup
 * @property matchId: Only present if no rollup
 * @property side: (required) Indicates if the bet is a Back or a LAY
 * @property price: (required) Either actual match price or avg match price depending on rollup. This value is not
 * meaningful for activity on LINE markets and is not guaranteed to be returned or maintained for these markets.
 * @property size: (required) Size matched at in this fragment, or at this price or avg price depending on rollup
 * @property matchDate: Only present if no rollup
 */
data class Match(
    val betId: String?,
    val matchId: String?,
    val side: Side,
    val price: Double,
    val size: Double,
    val matchDate: Date?
)
