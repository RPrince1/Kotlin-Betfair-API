package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.betfair.accounts.exception.AccountAPINGException
import com.prince.betfair.config.Config
import com.prince.betfair.config.Credentials
import mu.KotlinLogging
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class HttpClientSSO(
    val credentials: Credentials,
    val objectMapper: ObjectMapper,
    val config: Config,
    @Qualifier("https")
    val client: OkHttpClient
) {

    fun login(): Token? {
        val request = Request.Builder()
            .url(config.exchange.identityUrl)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .method(
                "POST",
                FormBody.Builder()
                    .add("username", credentials.getEmail())
                    .add("password", credentials.getPassword())
                    .build()
            )
            .addHeader("X-Application", "appkey")
            .build()

        val response = client.newCall(request).execute()
        val body: String = response.body?.string() ?: throw AccountAPINGException("Response Body was null")

        logger.info { body }
        return objectMapper.readValue(body, Token::class.java)
    }
}