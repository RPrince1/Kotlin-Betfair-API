package com.prince.betfair.services

import com.prince.betfair.betfair.accounts.Accounts
import com.prince.betfair.betfair.betting.Betting
import com.prince.betfair.betfair.betting.entities.MarketFilter
import com.prince.betfair.client.HttpClientSSO
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class BettingBot(
    private val httpClientSSO: HttpClientSSO,
    private val accounts: Accounts,
    private val betting: Betting
) {

    init {
        logger.info { "Program is running" }

        val token = httpClientSSO.login()

        if (token != null) {

            betting.listEventTypes(MarketFilter(), null, token)

//            val funds = accounts.getAccountFunds(token)
//            val devKey = accounts.getDeveloperAppKeys(token)
//            logger.info{ devKey.toString() }
        }
    }
}