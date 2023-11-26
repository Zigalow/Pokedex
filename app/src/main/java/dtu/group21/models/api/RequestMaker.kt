package dtu.group21.models.api

abstract class RequestMaker<T>(
    val baseUrl: String,
) {
    abstract suspend fun makeRequest(location: String) : T
}