package com.prince.betfair.objects

import org.springframework.stereotype.Component
import java.io.File

@Component
class Credentials {

    private lateinit var email: String
    private lateinit var password: String
    private lateinit var keystorePassword: String
    private val credentials = File("Credentials").readLines()

    init {
        try {
            email = credentials[0]
            password = credentials[1]
            keystorePassword = credentials[2]
        } catch (e: ArrayIndexOutOfBoundsException) {
            //TODO add logger
            //logger.info("Not all credentials provided")
        }
    }

    fun getEmail(): String = email
    fun getPassword(): String = password
    fun getKeyStorePassword(): String = keystorePassword
}