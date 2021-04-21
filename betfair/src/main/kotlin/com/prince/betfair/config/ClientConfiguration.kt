package com.prince.betfair.config

import okhttp3.OkHttpClient

interface ClientConfiguration {
    val client: OkHttpClient
}