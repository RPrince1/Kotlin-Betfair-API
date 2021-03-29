package com.prince.betfair.utils

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.net.URI

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class HttpGet(

) {
    private val httpClient = DefaultHttpClient()
    private val httpGet = HttpGet()

    fun setUri(uri: String) {
        httpGet.uri = URI.create(uri)
    }

    fun addHeader(first: String, second: String) {
        httpGet.addHeader(first, second)
    }

    fun execute(): CloseableHttpResponse? {
        return httpClient.execute(httpGet)
    }
}