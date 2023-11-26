package dtu.group21.models.api

import org.json.JSONObject
import java.net.URL

class JsonRequestMaker(
    baseUrl: String
) : RequestMaker<JSONObject>(baseUrl) {
    override suspend fun makeRequest(location: String) = JSONObject(URL("$baseUrl/$location").readText())
}