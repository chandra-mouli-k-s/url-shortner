# url-shortner
## url-shortner Application

The local code would run without any VM agrs. All you need to do is run git clone -> mvn clean install & run

Shortening input end point on local would be:

http://localhost:8080/shorten?url=<URL>

Example : http://localhost:8080/shorten?url=https://www.youtube.com/watch?v=3ltcxsBiHZ8

The shortened URL appears as part of the JSON response. You can link or copy it and save it.

Ideally the URL had to look shorter, but here it isn't because im using a simple hashing for the URL.