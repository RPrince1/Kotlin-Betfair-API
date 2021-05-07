package com.prince.betfair.betfair.betting.entities.market

data class RunnerCatalog(
    val selectionId: Long,
    val runnerName: String,
    val handicap: Double,
    val sortPriority: Int,
    val metadata: Map<String, String>
)
