package com.prince.betfair

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BetfairApplication

fun main(args: Array<String>) {
	runApplication<BetfairApplication>(*args)
}
