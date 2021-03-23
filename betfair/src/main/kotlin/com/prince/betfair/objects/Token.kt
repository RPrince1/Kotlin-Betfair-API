package com.prince.betfair.objects

import org.springframework.boot.jackson.JsonComponent

@JsonComponent
data class Token(val sessionToken: String, val loginStatus: String)
