package com.prince.betfair.betfair.betting.entities.competition

/**
 * Competition Result
 *
 * @property competition
 * @property marketCount: Count of markets associated with this competition
 * @property competitionRegion: Region in which this competition is happening
 */
data class CompetitionResult(
    val competition: Competition?,
    val marketCount: Int?,
    val competitionRegion: String?
)
