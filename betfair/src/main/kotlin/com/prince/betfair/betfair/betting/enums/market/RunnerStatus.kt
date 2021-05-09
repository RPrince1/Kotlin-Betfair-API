package com.prince.betfair.betfair.betting.enums.market

/**
 * @property ACTIVE: ACTIVE
 * @property WINNER: WINNER
 * @property LOSER: LOSER
 * @property PLACED: The runner was placed, applies to EACH_WAY marketTypes only.
 * @property REMOVED_VACANT: REMOVED_VACANT applies to Greyhounds. Greyhound markets always return a fixed number of runners
 * (traps). If a dog has been removed, the trap is shown as vacant.
 * @property REMOVED: REMOVED
 * @property HIDDEN: The selection is hidden from the market.  This occurs in Horse Racing markets were runners is hidden when it
 * is doesn’t hold an official entry following an entry stage. This could be because the horse was never entered or
 * because they have been scratched from a race at a declaration stage. All matched customer bet prices are set to 1.0
 * even if there are later supplementary stages. Should it appear likely that a specific runner may actually be
 * supplemented into the race this runner will be reinstated with all matched customer bets set back to the original
 * prices.
 */
enum class RunnerStatus {
    ACTIVE,
    WINNER,
    LOSER,
    PLACED,
    REMOVED_VACANT,
    REMOVED,
    HIDDEN
}
