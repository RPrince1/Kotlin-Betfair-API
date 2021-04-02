package com.prince.betfair.services

import com.prince.betfair.betfair.accounts.Accounts
import com.prince.betfair.client.HttpClientSSO
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class BettingBot(
    private final val httpClientSSO: HttpClientSSO,
    val accounts: Accounts
) {

    init {
        logger.info { "Program is running" }

        val token = httpClientSSO.login()

        if (token != null) {

//            val funds = accounts.getAccountFunds(token)
            val devKey = accounts.getDeveloperAppKeys(token)
            logger.info{ devKey.toString() }
        }
    }
}