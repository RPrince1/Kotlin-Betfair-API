package com.prince.betfair.betfair.heartbeat

/**
 * @property NONE: No action was performed since last heartbeat, or this is the first heartbeat
 * @property CANCELLATION_REQUEST_SUBMITTED: A request to cancel all unmatched bets was submitted since last heartbeat
 * @property ALL_BETS_CANCELLED: All unmatched bets were cancelled since last heartbeat
 * @property SOME_BETS_NOT_CANCELLED: Not all unmatched bets were cancelled since last heartbeat
 * @property CANCELLATION_REQUEST_ERROR: There was an error requesting cancellation, no bets have been cancelled
 * @property CANCELLATION_STATUS_UNKNOWN: There was no response from requesting cancellation, cancellation status
 * unknown
 */
enum class ActionPerformed {
    NONE,
    CANCELLATION_REQUEST_SUBMITTED,
    ALL_BETS_CANCELLED,
    SOME_BETS_NOT_CANCELLED,
    CANCELLATION_REQUEST_ERROR,
    CANCELLATION_STATUS_UNKNOWN
}
