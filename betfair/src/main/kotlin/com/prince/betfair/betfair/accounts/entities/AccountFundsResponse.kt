package com.prince.betfair.betfair.accounts.entities

/**
 * Response for retrieving available to bet.
 *
 * @property availableToBetBalance: Amount available to bet.
 * @property exposure: Current exposure.
 * @property retainedCommission: Sum of retained commission.
 * @property exposureLimit: Exposure limit.
 * @property discountRate: User Discount Rate.
 * @property pointsBalance: The Betfair points balance
 */
data class AccountFundsResponse(
    val availableToBetBalance: Double?,
    val exposure: Double?,
    val retainedCommission: Double?,
    val exposureLimit: Double?,
    val discountRate: Double?,
    val pointsBalance: Int?
)


