package com.prince.betfair.utils

import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpUriRequest
import java.net.URI

class HttpPost(
    private val https: Https
) {

    private val httpPost = HttpPost()

    fun setUri(uri: String) {
        httpPost.uri = URI.create(uri)
    }

    fun setHeader(first: String, second: String) {
        httpPost.addHeader(first, second)
    }

    fun setEntity(list: List<NameValuePair>) {
        httpPost.entity = UrlEncodedFormEntity(list)
    }

    fun execute(): CloseableHttpResponse? {
        return https.execute(httpPost)
    }
}