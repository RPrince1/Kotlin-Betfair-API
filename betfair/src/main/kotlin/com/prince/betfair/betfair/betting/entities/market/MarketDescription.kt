package com.prince.betfair.betfair.betting.entities.market

import com.prince.betfair.betfair.betting.enums.market.MarketBettingType
import java.util.*

/**
 * Market definition
 *
 * @property persistenceEnabled: (required) If 'true' the market supports 'Keep' bets if the market is to be turned
 * in-play
 * @property bspMarket: (required) If 'true' the market supports Betfair SP betting
 * @property marketTime: (required) The market start time. This is the scheduled start time of the market e.g. horse
 * race or football match etc.
 * @property suspendTime: (required) The market suspend time. This is the next time the market will be suspended for
 * betting and is normally the same as the marketTime.
 * @property settleTime: settled time
 * @property bettingType: (required) See MarketBettingType
 * @property turnInPlayEnabled: (required) If 'true' the market is set to turn in-play
 * @property marketType: (required) Market base type
 * @property regulator: (required) The market regulator. Value include “GIBRALTAR REGULATOR" (.com), MR_ESP (Betfair.es
 * markets), MR_IT (Betfair.it). GIBRALTAR REGULATOR = MR_INT in the Stream API
 * @property marketBaseRate: (required) The commission rate applicable to the market
 * @property discountAllowed: (required) Indicates whether or not the user's discount rate is taken into account on this
 * market. If ‘false’ all users will be charged the same commission rate, regardless of discount rate.
 * @property wallet: The wallet to which the market belongs.
 * @property rules: The market rules.
 * @property rulesHasDate: Indicates whether rules have a date included.
 * @property eachWayDivisor: The divisor is returned for the marketType EACH_WAY only and refers to the fraction of the
 * win odds at which the place portion of an each way bet is settled
 * @property clarifications: Any additional information regarding the market
 * @property lineRangeInfo: Line range info for line markets
 * @property raceType: An external identifier of a race type
 * @property priceLadderDescription: Details about the price ladder in use for this market.
 */
data class MarketDescription(
    val persistenceEnabled: Boolean,
    val bspMarket: Boolean,
    val marketTime: Date,
    val suspendTime: Date,
    val settleTime: Date,
    val bettingType: MarketBettingType,
    val turnInPlayEnabled: Boolean,
    val marketType: String,
    val regulator: String,
    val marketBaseRate: Double,
    val discountAllowed: Boolean,
    val wallet: String,
    val rules: String,
    val rulesHasDate: Boolean,
    val eachWayDivisor: Double,
    val clarifications: String,
    val lineRangeInfo: MarketLineRangeInfo,
    val raceType: String,
    val priceLadderDescription: PriceLadderDescription
)
