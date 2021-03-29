package com.prince.betfair.services

import com.prince.betfair.betfair.accounts.Accounts
import com.prince.betfair.client.HttpClientSSO
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class BettingBot() {

   @Autowired
   private lateinit var httpClientSSO: HttpClientSSO

   @Autowired
   private lateinit var accounts: Accounts

    init {
        logger.info { "Program is running" }
        val token = httpClientSSO.login()

        if (token != null) {

            val funds = accounts.getAccountFunds(token)

            logger.info{ funds.toString() }
        }
    }
}