package com.prince.betfair.betfair.accounts

import com.prince.betfair.betfair.accounts.enum.Location

data class Wallet(
    val location: Location = Location.UK
)

