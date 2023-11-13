package dtu.group21.models.api

import java.net.URL

class StringRequestMaker(
    baseUrl: String
) : RequestMaker<String>(baseUrl) {
    override fun makeRequest(location: String) = URL("$baseUrl/$location").readText()
}