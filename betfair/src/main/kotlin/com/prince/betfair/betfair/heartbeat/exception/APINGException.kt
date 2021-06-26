package com.prince.betfair.betfair.heartbeat.exception

/**
 * This exception is thrown when an operation fails
 *
 * INVALID_INPUT_DATA: Invalid input data
 * INVALID_SESSION_INFORMATION: The session token passed is invalid
 * NO_APP_KEY: An application key is required for this operation
 * NO_SESSION: A session token is required for this operation
 * INVALID_APP_KEY: The application key passed is invalid
 * UNEXPECTED_ERROR: An unexpected internal error occurred that prevented successful request processing.
 */
class APINGException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}