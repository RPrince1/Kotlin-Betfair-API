package com.prince.betfair.betfair.betting.entities.market

/**
 * Market Line and Range Info
 *
 * @property maxUnitValue: (required) maxPrice - Maximum value for the outcome, in market units for this market (eg 100
 * runs)
 * @property minUnitValue: (required) minPrice - Minimum value for the outcome, in market units for this market (eg 0
 * runs)
 * @property interval: (required) interval - The odds ladder on this market will be between the range of minUnitValue
 * and maxUnitValue, in increments of the inverval value.e.g. If minUnitValue=10 runs, maxUnitValue=20 runs,
 * interval=0.5 runs, then valid odds include 10, 10.5, 11, 11.5 up to 20 runs.
 * @property marketUnit: unit - The type of unit the lines are incremented in by the interval (e.g: runs, goals or
 * seconds).
 */
data class MarketLineRangeInfo(
    val maxUnitValue: Double,
    val minUnitValue: Double,
    val interval: Double,
    val marketUnit: String
)
