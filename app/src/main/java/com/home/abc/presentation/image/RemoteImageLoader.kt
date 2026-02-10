package com.home.abc.presentation.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object RemoteImageLoader {
    private const val CACHE_MEMORY_DIVIDER = 8L
    private const val NETWORK_TIMEOUT_MS = 5_000

    private val cache = LruCache<String, Bitmap>(
        (Runtime.getRuntime().maxMemory() / CACHE_MEMORY_DIVIDER).toInt()
    )

    suspend fun load(url: String): Bitmap? {
        cache.get(url)?.let { return it }
        return withContext(Dispatchers.IO) {
            runCatching {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.connectTimeout = NETWORK_TIMEOUT_MS
                connection.readTimeout = NETWORK_TIMEOUT_MS
                connection.instanceFollowRedirects = true
                connection.inputStream.use { stream ->
                    val bitmap = BitmapFactory.decodeStream(stream)
                    if (bitmap != null) {
                        cache.put(url, bitmap)
                    }
                    bitmap
                }
            }.getOrNull()
        }
    }
}
