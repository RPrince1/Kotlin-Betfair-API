package com.prince.betfair.objects

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean

@Bean
fun mapper() = JsonMapper.builder().addModule(KotlinModule()).build()
