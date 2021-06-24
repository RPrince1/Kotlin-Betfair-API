package com.prince.betfair.betfair.accounts.entities

/**
 * Describes developer/vendor specific application
 *
 * @property appName: (required) The unique name of the application
 * @property appId: (required) A unique id of this application
 * @property appVersions: (required) The application versions (including application keys)
 */
data class DeveloperApp(
    val appName: String,
    val appId: Long,
    val appVersions: List<DeveloperAppVersion>
)
