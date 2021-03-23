package com.prince.betfair.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.prince.betfair.objects.Credentials
import com.prince.betfair.objects.Token
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPost
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.ssl.StrictHostnameVerifier
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.util.*
import javax.net.ssl.KeyManager
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext

@Component
class HttpClientSSO(
    private val objectMapper: ObjectMapper,
    private val credentials: Credentials
) {
    private val port = 443
    private val httpClient = DefaultHttpClient()

    fun getToken(): Token? {

        val ctx: SSLContext = SSLContext.getInstance("TLS")
        val keyManagers = getKeyManagers("pkcs12", FileInputStream(File("/usr/lib/ssl/client-2048.p12")), credentials.getKeyStorePassword())
        ctx.init(keyManagers, null, SecureRandom())
        val factory = SSLSocketFactory(ctx, StrictHostnameVerifier())

        // TODO wrap http library in class
        val manager: ClientConnectionManager = httpClient.getConnectionManager()
        manager.schemeRegistry.register(Scheme("https", port, factory))
        val httpPost = HttpPost("https://identitysso-cert.betfair.com/api/certlogin")
        val nvps: MutableList<NameValuePair> = ArrayList()

        nvps.add(BasicNameValuePair("username", credentials.getEmail()))
        nvps.add(BasicNameValuePair("password", credentials.getPassword()))
        httpPost.entity = UrlEncodedFormEntity(nvps)

        httpPost.setHeader("X-Application", "appkey")

        println("executing request" + httpPost.requestLine)

        val response: HttpResponse = httpClient.execute(httpPost)
        val entity: HttpEntity = response.getEntity()

        println("----------------------------------------")
        System.out.println(response.getStatusLine())
        if (entity != null) {
            val responseString = EntityUtils.toString(entity)
            //extract the session token from responsestring
            println("responseString$responseString")
        }

        return objectMapper.readValue(EntityUtils.toString(entity), Token::class.java)
    }

    @Throws(Exception::class)
    private fun getKeyManagers(
        keyStoreType: String,
        keyStoreFile: InputStream,
        keyStorePassword: String
    ): Array<KeyManager?>? {
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(keyStoreFile, keyStorePassword.toCharArray())
        val kmf: KeyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        kmf.init(keyStore, keyStorePassword.toCharArray())
        return kmf.getKeyManagers()
    }
}

fun main(args: Array<String>) {
    HttpClientSSO(ObjectMapper(), Credentials()).getToken()
}
