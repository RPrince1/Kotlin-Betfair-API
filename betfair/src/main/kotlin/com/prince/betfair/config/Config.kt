package com.prince.betfair.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "betfair", ignoreUnknownFields = true)
data class Config(
    val exchange: Exchange
)

data class Exchange(
    val betting: Betting,
    val account: Account,
    val identityUrl: String
)

data class Betting(
    val url: String
)

data class Account(
    val url: String
)
