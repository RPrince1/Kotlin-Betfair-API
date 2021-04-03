package com.prince.betfair.betfair.betting.exception

class APINGException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}