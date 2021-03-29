package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.utils.HttpPost
import mu.KotlinLogging
import org.apache.http.NameValuePair
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class HttpClientSSO() {

    @Autowired
    private lateinit var credentials: Credentials

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var httpPost: HttpPost

    fun login(): Token? {
        httpPost.setUri("https://identitysso-cert.betfair.com/api/certlogin")
        val nvps: MutableList<NameValuePair> = ArrayList()
        nvps.add(BasicNameValuePair("username", credentials.getEmail()))
        nvps.add(BasicNameValuePair("password", credentials.getPassword()))
        httpPost.setEntity(nvps)
        httpPost.addHeader("X-Application", "appkey")

        val response: CloseableHttpResponse? = httpPost.execute()
        val entity = EntityUtils.toString(response?.entity)

        logger.info { entity }

        //TODO Handle errors
        return objectMapper.readValue(entity, Token::class.java)
    }
}