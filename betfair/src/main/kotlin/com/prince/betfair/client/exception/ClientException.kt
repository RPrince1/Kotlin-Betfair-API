package com.prince.betfair.client.exception

class ClientException : Exception {
    constructor(message: String) : super(message)
    constructor(message: String, throwable: Throwable) : super(message, throwable)
}
