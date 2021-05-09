package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.client.SSOID.Failure
import com.prince.betfair.client.SSOID.Success
import com.prince.betfair.client.exception.ClientException
import com.prince.betfair.client.exception.ServerException
import com.prince.betfair.config.JacksonConfiguration
import okhttp3.*

class ClientSSO(private val client: OkHttpClient) {

    private val objectMapper: ObjectMapper = JacksonConfiguration().mapper()

    fun login(email: String, password: String): SSOID {
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

        val response = client.newCall(request).execute()
        val bodyResult = kotlin.runCatching {
            getBody(response)
        }
        return when (val body = bodyResult.getOrNull()) {
            null -> Failure(bodyResult.exceptionOrNull()!!.javaClass.name, bodyResult.exceptionOrNull()?.message)
            else -> handleLoginResponse(body)
        }
    }

    private fun handleLoginResponse(b: String): SSOID {
        val loginResponse = objectMapper.readValue(b, LoginResponse::class.java)

        return if (loginResponse.loginStatus == "SUCCESS" && !loginResponse.sessionToken.isNullOrEmpty()) {
            Success(loginResponse.sessionToken)
        } else {
            Failure(loginResponse.loginStatus, null)
        }
    }

    private fun getBody(response: Response) =
        when {
            response.isSuccessful -> response.body?.string() ?: throw ServerException("Response body is null")
            else -> throw ClientException("Response code: ${response.code}, reason: ${response.body}")
        }
}