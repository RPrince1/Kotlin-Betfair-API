package com.prince.betfair.betfair.accounts

class AccountAPINGException : Exception {

    constructor(message: String) : super(message)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}
