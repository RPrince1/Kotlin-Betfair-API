package com.prince.betfair.client

sealed class SSOID {
    data class Success(val sessionToken: String) : SSOID()
    data class Failure(val loginStatus: String, val cause: String?) : SSOID()
}