package com.prince.betfair.betfair.accounts.exception

/**
 * This exception is thrown when an operation fails
 *
 * INVALID_INPUT_DATA: Invalid input data. Please check the format of your request.
 * INVALID_SESSION_INFORMATION: The session token hasn't been provided, is invalid or has expired. You must login again
 * to creata a new session token.
 * UNEXPECTED_ERROR: An unexpected internal error occurred that prevented successful request processing.
 * INVALID_APP_KEY: The application key passed is invalid or is not present.
 * SERVICE_BUSY: The service is currently too busy to service this request.
 * TIMEOUT_ERROR: The internal call to downstream service timed out
 * DUPLICATE_APP_NAME: Duplicate application name.
 * APP_KEY_CREATION_FAILED: Creating application key version has failed. Please check that your application name is
 * unique and doesn't contain your Betfair username.
 * APP_CREATION_FAILED: Application creation has been failed
 * NO_SESSION: A session token header ('X-Authentication') has not been provided in the request.
 * NO_APP_KEY: An application key header ('X-Application') has not been provided in the request
 * SUBSCRIPTION_EXPIRED: An application key is required for this operation
 * INVALID_SUBSCRIPTION_TOKEN: The subscription token provided doesn't exist
 * TOO_MANY_REQUESTS: Too many requests.
 * INVALID_CLIENT_REF: Invalid length for the client reference
 * WALLET_TRANSFER_ERROR: There was a problem transferring funds between your wallets
 * INVALID_VENDOR_CLIENT_ID: The vendor client ID is not subscribed to this Application Key.
 * USER_NOT_SUBSCRIBED: The user making the request is not subscribed to the Application Key. they are trying to perform
 * the action on (e.g. creating an Authorisation Code)
 * INVALID_SECRET: The vendor making the request has provided a vendor secret that does not match our records.
 * INVALID_AUTH_CODE: The vendor making the request has not provided a valid authorisation cod
 * INVALID_GRANT_TYPE: The vendor making the request has not provided a valid grant_type, or the grant_type they have
 * passed does not match the parameters (authCode/refreshToken)
 */
class AccountAPINGException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}
