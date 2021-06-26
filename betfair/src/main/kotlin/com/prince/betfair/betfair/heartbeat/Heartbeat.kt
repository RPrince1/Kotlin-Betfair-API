package com.prince.betfair.betfair.heartbeat

import com.fasterxml.jackson.module.kotlin.readValue
import com.prince.betfair.betfair.betting.exception.APINGException
import com.prince.betfair.config.JacksonConfiguration
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class Heartbeat(private val client: OkHttpClient = OkHttpClient()) {

    private val objectMapper = JacksonConfiguration().mapper()
    private val heartbeatUrl = "https://api.betfair.com/exchange/heartbeat/rest/v1.0/"

    /**
     * This heartbeat operation is provided to help customers have their positions managed automatically in the event of
     * their API clients losing connectivity with the Betfair API. If a heartbeat request is not received within a
     * prescribed time period, then Betfair will attempt to cancel all 'LIMIT' type bets for the given customer on the
     * given exchange. There is no guarantee that this service will result in all bets being cancelled as there are a
     * number of circumstances where bets are unable to be cancelled. Manual intervention is strongly advised in the
     * event of a loss of connectivity to ensure that positions are correctly managed. If this service becomes
     * unavailable for any reason, then your heartbeat will be unregistered automatically to avoid bets being
     * inadvertently cancelled upon resumption of service. you should manage your position manually until the service is
     * resumed. Heartbeat data may also be lost in the unlikely event of nodes failing within the cluster, which may
     * result in your position not being managed until a subsequent heartbeat request is received.
     *
     * @param preferredTimeoutSeconds: (required) Maximum period in seconds that may elapse (without a subsequent
     * heartbeat request), before a cancellation request is automatically submitted on your behalf. The minimum value is
     * 10, the maximum value permitted is 300. Passing 0 will result in your heartbeat being unregistered (or ignored if
     * you have no current heartbeat registered). You will still get an actionPerformed value returned when passing 0,
     * so this may be used to determine if any action was performed since your last heartbeat, without actually
     * registering a new heartbeat. Passing a negative value will result in an error being returned, INVALID_INPUT_DATA.
     * Any errors while registering your heartbeat will result in a error being returned, UNEXPECTED_ERROR. Passing a
     * value that is less than the minimum timeout will result in your heartbeat adopting the minimum timeout. Passing a
     * value that is greater than the maximum timeout will result in your heartbeat adopting the maximum timeout. The
     * minimum and maximum timeouts are subject to change, so your client should utilise the returned
     * actualTimeoutSeconds to set an appropriate frequency for your subsequent heartbeat requests.
     * @throws APINGException
     */
    fun heartbeat(
        preferredTimeoutSeconds: Int,
        sessionToken: String,
        applicationKey: String
    ): HeartbeatReport {
        val response = client.newCall(
            Request.Builder()
                .url("${heartbeatUrl}heartbeat/")
                .addHeader("X-Authentication", sessionToken)
                .addHeader("X-Application", applicationKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .post(
                    objectMapper.writeValueAsString(
                        mapOf(
                            Pair("preferredTimeoutSeconds", preferredTimeoutSeconds)
                        )
                    ).toRequestBody()
                )
                .build()
        ).execute()

        val responseBody = response.body?.string()

        println(responseBody)

        val body = when {
            response.isSuccessful -> responseBody ?: throw APINGException("Response body is null")
            else -> throw APINGException("Response code: ${response.code}, reason: $responseBody")
        }

        return objectMapper.readValue(body)
    }
}