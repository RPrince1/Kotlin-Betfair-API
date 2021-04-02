package com.prince.betfair.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class HttpClientConfiguration {

    @Primary
    @Bean("http")
    fun httpClient(): OkHttpClient {
        return OkHttpClient()
    }
}