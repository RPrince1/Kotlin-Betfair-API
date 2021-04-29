# Kotlin Betfair API

* Betfair API - For ref
    * https://developer.betfair.com/en/get-started/#exchange-api

  
# Getting Started

Logging in using single sign-on

    val httpsClient = HttpsClientConfiguration(File("/client-2048.p12").inputStream(), "password").client
    val ssoid = ClientSSO(httpsClient).login("larryenticer@gmail.com", "justSendIt")

Using Accounts

    val httpClient = OkHttpClient()
    val accounts = Accounts(httpClient)

Using Betting

    val httpClient = OkHttpClient()
    val betting = Betting(httpClient)