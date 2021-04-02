package com.prince.betfair.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean

class JacksonConfiguration {

    @Bean
    fun mapper() = ObjectMapper().registerKotlinModule()
}