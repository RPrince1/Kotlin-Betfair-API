package com.prince.betfair.betfair.betting.enums.market

/**
 * @property COMPETITION: If not selected then the competition will not be returned with marketCatalogue
 * @property EVENT: If not selected then the event will not be returned with marketCatalogue
 * @property EVENT_TYPE: If not selected then the eventType will not be returned with marketCatalogue
 * @property MARKET_START_TIME: If not selected then the start time will not be returned with marketCatalogue
 * @property MARKET_DESCRIPTION: If not selected then the description will not be returned with marketCatalogue
 * @property RUNNER_DESCRIPTION: If not selected then the runners will not be returned with marketCatalogue
 * @property RUNNER_METADATA: If not selected then the runner metadata will not be returned with marketCatalogue. If
 * selected then RUNNER_DESCRIPTION will also be returned regardless of whether it is included as a market projection.
 */
enum class MarketProjection {
    COMPETITION,
    EVENT,
    EVENT_TYPE,
    MARKET_START_TIME,
    MARKET_DESCRIPTION,
    RUNNER_DESCRIPTION,
    RUNNER_METADATA
}
