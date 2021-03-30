package com.prince.betfair.betfair.accounts

import org.springframework.stereotype.Component

@Component
data class Wallet(
    val location: Location = Location.UK
)

enum class Location {
    UK
}