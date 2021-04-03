package com.prince.betfair.betfair.accounts

import com.prince.betfair.betfair.accounts.enum.Location
import org.springframework.stereotype.Component

@Component
data class Wallet(
    val location: Location = Location.UK
)

