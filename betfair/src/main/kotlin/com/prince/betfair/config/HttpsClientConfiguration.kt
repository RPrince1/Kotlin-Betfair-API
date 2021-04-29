package com.prince.betfair.config

import okhttp3.OkHttpClient
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class HttpsClientConfiguration(
    private val p12: InputStream,
    private val keystorePassword: String,
) {

    lateinit var client: OkHttpClient

    init {
        val keystore = KeyStore.getInstance("PKCS12").apply {
            load(p12, keystorePassword.toCharArray())
        }
        val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        kmf.init(keystore, keystorePassword.toCharArray())
        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(null as KeyStore?)
        val context = SSLContext.getInstance("TLS")
        context.init(kmf.keyManagers, tmf.trustManagers, SecureRandom())
        val ssf = context.socketFactory
        val tm = tmf.trustManagers[0] as X509TrustManager
        client =  OkHttpClient().newBuilder().sslSocketFactory(ssf, tm).build()
    }

}