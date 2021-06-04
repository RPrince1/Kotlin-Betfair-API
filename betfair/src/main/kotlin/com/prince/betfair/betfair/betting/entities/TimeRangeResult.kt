package com.prince.betfair.betfair.betting.entities

/**
 * TimeRange Result
 *
 * @property timeRange
 * @property marketCount: Count of markets associated with this TimeRange
 */
data class TimeRangeResult(
    val timeRange: TimeRange?,
    val marketCount: Int?
)
