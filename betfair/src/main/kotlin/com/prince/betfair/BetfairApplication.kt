package com.prince.betfair

import com.prince.betfair.config.Config
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(Config::class)
@SpringBootApplication
class BetfairApplication

fun main(args: Array<String>) {
	runApplication<BetfairApplication>(*args)
}
