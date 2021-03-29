package com.prince.betfair.betfair.accounts

data class AccountFundsResponse(
    val availableToBetBalance: Double,
    val exposure: Double,
    val retainedCommission: Double,
    val exposureLimit: Double,
    val discountRate: Double,
    val pointsBalance: Int
)


