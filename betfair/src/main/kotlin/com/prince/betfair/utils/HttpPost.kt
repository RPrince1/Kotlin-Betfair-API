package com.prince.betfair.utils

import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.net.URI

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class HttpPost(
    private val https: Https
) {
    private val httpPost = HttpPost()

    fun setUri(uri: String) {
        httpPost.uri = URI.create(uri)
    }

    fun addHeader(first: String, second: String) {
        httpPost.addHeader(first, second)
    }

    fun setEntity(list: List<NameValuePair>) {
        httpPost.entity = UrlEncodedFormEntity(list)
    }

    fun execute(): CloseableHttpResponse? {
        return https.execute(httpPost)
    }
}