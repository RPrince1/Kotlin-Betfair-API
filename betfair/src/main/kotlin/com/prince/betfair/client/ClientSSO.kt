package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.client.exception.ClientException
import com.prince.betfair.config.ClientConfiguration
import com.prince.betfair.config.JacksonConfiguration
import okhttp3.FormBody
import okhttp3.Request

class ClientSSO(
    private val httpsClientConfiguration: ClientConfiguration,
    private val objectMapper: ObjectMapper = JacksonConfiguration().mapper()
) {

    fun login(email: String, password: String): Token {
        val request = Request.Builder()
            .url("https://identitysso-cert.betfair.com/api/certlogin")
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .method(
                "POST",
                FormBody.Builder()
                    .add("username", email)
                    .add("password", password)
                    .build()
            )
            .addHeader("X-Application", "appkey")
            .build()

        val response = httpsClientConfiguration.client.newCall(request).execute()

        val body = when {
            response.isSuccessful -> response.body?.string() ?: throw ClientException("Response body is null")
            else -> throw ClientException("Response code: ${response.code}, reason: ${response.body}")
        }

        return objectMapper.readValue(body, Token::class.java)
    }
}
