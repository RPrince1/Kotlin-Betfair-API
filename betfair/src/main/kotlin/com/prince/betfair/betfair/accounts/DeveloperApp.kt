package com.prince.betfair.betfair.accounts

data class DeveloperApp(
    val appName: String,
    val appId: Long,
    val appVersions: List<DeveloperAppVersion>
)
