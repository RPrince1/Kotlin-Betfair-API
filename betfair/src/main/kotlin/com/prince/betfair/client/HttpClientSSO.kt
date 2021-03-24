package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.prince.betfair.objects.Credentials
import com.prince.betfair.objects.Token
import com.prince.betfair.utils.HttpPost
import com.prince.betfair.utils.Https
import org.apache.http.NameValuePair
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Component
import java.util.*

@Component
class HttpClientSSO(
    private val objectMapper: ObjectMapper,
    private val credentials: Credentials,
    private val httpPost: HttpPost
) {
    fun getToken(): Token? {
        httpPost.setUri("https://identitysso-cert.betfair.com/api/certlogin")
        val nvps: MutableList<NameValuePair> = ArrayList()
        nvps.add(BasicNameValuePair("username", credentials.getEmail()))
        nvps.add(BasicNameValuePair("password", credentials.getPassword()))
        httpPost.setEntity(nvps)
        httpPost.setHeader("X-Application", "appkey")

        val response: CloseableHttpResponse? = httpPost.execute()

        return objectMapper.readValue(EntityUtils.toString(response?.entity), Token::class.java)
    }
}

fun main(args: Array<String>) {
    val mapper = JsonMapper.builder().addModule(KotlinModule()).build()
    println(HttpClientSSO(mapper, Credentials(), HttpPost(Https(Credentials()))).getToken())
}
