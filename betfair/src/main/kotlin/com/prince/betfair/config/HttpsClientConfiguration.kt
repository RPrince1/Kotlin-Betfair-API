package com.prince.betfair.config

import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

@Configuration
class HttpsClientConfiguration(
    val credentials: Credentials
) {

    @Bean("https")
    fun httpClient(): OkHttpClient {
        val keystore = KeyStore.getInstance("PKCS12").apply {
            //TODO Move to config
            load(File("/usr/lib/ssl/client-2048.p12").inputStream(), credentials.getKeyStorePassword().toCharArray())
        }

        val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
        kmf.init(keystore, credentials.getKeyStorePassword().toCharArray())

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        tmf.init(null as KeyStore?)

        val context = SSLContext.getInstance("TLS")
        context.init(kmf.keyManagers, tmf.trustManagers, SecureRandom())

        val ssf = context.socketFactory
        val tm = tmf.trustManagers[0] as X509TrustManager

        return OkHttpClient().newBuilder().sslSocketFactory(ssf, tm).build()
    }
}