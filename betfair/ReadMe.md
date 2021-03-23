# Useful Sites
* Betfair API - Getting Started
    * https://developer.betfair.com/en/get-started/#exchange-api 
    
# Steps to creating a betfair bot
* Sign up for a Betfair account
    * https://register.betfair.com
* Create a development app key using `createDeveloperAppKeys`
    * https://docs.developer.betfair.com/visualisers/api-ng-account-operations/
* Make first API call. Session token is automatically populates if logged into Betfair. Add app key to interface. Execute any operation.
    * https://docs.developer.betfair.com/visualisers/api-ng-sports-operations/
* Non-Interactive login - autonomous application.
  * https://docs.developer.betfair.com/display/1smk3cen4v3lu3yomq5qye0ni/Non-Interactive+%28bot%29+login
* Add generated CA to Java Keystore
  * In the java bin directory run (or use keytool if installed) </br> `./keytool -import -trustcacerts -keystore ../lib/security/cacerts -storepass changeit -noprompt -alias betfair -file /usr/lib/ssl/client-2048.crt`
  * Check it's been added to the cacerts </br> `./keytool -list -keystore ../lib/security/cacerts`
