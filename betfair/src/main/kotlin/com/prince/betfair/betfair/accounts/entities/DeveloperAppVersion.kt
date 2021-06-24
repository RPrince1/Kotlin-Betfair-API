package com.prince.betfair.betfair.accounts.entities

/**
 * Describes a version of an external application
 *
 * @property owner: (required) The user who owns the specific version of the application
 * @property versionId: (required) The unique Id of the application version
 * @property version: (required) The version identifier string such as 1.0, 2.0. Unique for a given application.
 * @property applicationKey: (required) The unqiue application key associated with this application version
 * @property delayData: (required) Indicates whether the data exposed by platform services as seen by this application
 * key is delayed or realtime.
 * @property subscriptionRequired: (required) Indicates whether the application version needs explicit subscription
 * @property ownerManaged: (required) Indicates whether the application version needs explicit management by the
 * software owner. A value of false indicates, this is a version meant for personal developer use.
 * @property active: (required) Indicates whether the application version is currently active
 * @property vendorId: Public unique string provided to the Vendor that they can use to pass to the Betfair API in order
 * to identify themselves.
 * @property vendorSecret: Private unique string provided to the Vendor that they pass with certain calls to confirm
 * their identity. Linked to a particular App Key.
 */
data class DeveloperAppVersion(
    val owner: String,
    val versionId: Long,
    val version: String,
    val applicationKey: String,
    val delayData: Boolean,
    val subscriptionRequired: Boolean,
    val ownerManaged: Boolean,
    val active: Boolean,
    val vendorId: String?,
    val vendorSecret: String?
)
