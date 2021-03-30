package com.prince.betfair.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(ignoreUnknownFields = true)
data class Config(
    val url: String
)
