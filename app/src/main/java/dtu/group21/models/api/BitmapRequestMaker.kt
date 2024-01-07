package dtu.group21.models.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.URL

class BitmapRequestMaker(
    baseUrl: String
) : RequestMaker<Bitmap>(baseUrl) {
    override suspend fun makeRequest(location: String): Bitmap {
        val imageBytes = URL("$baseUrl/$location").readBytes()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}