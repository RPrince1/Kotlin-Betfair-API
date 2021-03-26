package com.prince.betfair.client

import org.springframework.context.annotation.Configuration
import java.io.File
import java.lang.IndexOutOfBoundsException

@Configuration
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
        } catch (e: IndexOutOfBoundsException) {
            throw IndexOutOfBoundsException("Not all credentials provided in credentials file")
        }
    }

    fun getEmail(): String = email
    fun getPassword(): String = password
    fun getKeyStorePassword(): String = keystorePassword
}