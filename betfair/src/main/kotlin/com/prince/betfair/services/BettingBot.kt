package com.prince.betfair.services

import com.prince.betfair.client.HttpClientSSO
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class BettingBot(
    private final val httpClientSSO: HttpClientSSO
) {

    init {
        logger.info { "Program is running" }
        val token = httpClientSSO.getToken()
        logger.info { token }
    }
}