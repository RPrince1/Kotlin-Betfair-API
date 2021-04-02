package com.prince.betfair.betfair.accounts.exception

class AccountAPINGException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}
