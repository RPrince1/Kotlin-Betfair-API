package com.prince.betfair.utils

import com.prince.betfair.objects.Credentials
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.conn.ClientConnectionManager
import org.apache.http.conn.scheme.Scheme
import org.apache.http.conn.ssl.SSLSocketFactory
import org.apache.http.conn.ssl.StrictHostnameVerifier
import org.apache.http.impl.client.DefaultHttpClient
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.KeyManager
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext

@Component
class Https(
    private val credentials: Credentials
) {

    //TODO move to config file
    private val port = 443
    private val httpClient = DefaultHttpClient()

    init {
        val ctx: SSLContext = SSLContext.getInstance("TLS")
        val keyManagers = getKeyManagers("pkcs12", FileInputStream(File("/usr/lib/ssl/client-2048.p12")), credentials.getKeyStorePassword())
        ctx.init(keyManagers, null, SecureRandom())
        val factory = SSLSocketFactory(ctx, StrictHostnameVerifier())
        val manager: ClientConnectionManager = httpClient.getConnectionManager()
        manager.schemeRegistry.register(Scheme("https", port, factory))
    }

    fun execute(request: HttpUriRequest): CloseableHttpResponse? {
        return httpClient.execute(request)
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