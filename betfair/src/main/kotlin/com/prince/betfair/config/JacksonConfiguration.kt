package com.prince.betfair.config

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean

class JacksonConfiguration {
    @Bean
    fun jsonMapper() = JsonMapper.builder().addModule(KotlinModule()).build()
}