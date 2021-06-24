package com.prince.betfair.betfair.betting.entities.market

/**
 * Information about the Runners (selections) in a market
 *
 * @property selectionId: (required) The unique id for the selection. The same selectionId and runnerName pairs are used
 * across all Betfair markets which contain them. Please note: The selectionId can be mapped to the runner name using
 * the output from listMarketCatalogue
 * @property runnerName: (required) The name of the runner.
 * @property handicap: (required) The handicap applies to market with the MarketBettingType ASIAN_HANDICAP_SINGLE_LINE &
 * ASIAN_HANDICAP_DOUBLE_LINE only otherwise '0'
 * @property sortPriority: (required) The sort priority of this runner. Indicates the order in which the runners are
 * displayed on the Betfair Exchange website.
 * @property metadata: Metadata associated with the runner.  For a description of this data for Horse Racing, please see
 * Runner Metadata Description
 */
data class RunnerCatalog(
    val selectionId: Long,
    val runnerName: String,
    val handicap: Double,
    val sortPriority: Int,
    val metadata: Map<String, String>
)
