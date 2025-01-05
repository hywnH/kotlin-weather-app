package org.example.util

import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor


object HttpClient {
    private fun createHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY // 로그 수준 설정 (BODY, HEADERS, BASIC, NONE)
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging) // Interceptor 추가
            .build()
    }
    suspend fun fetch(url: String): String? = withContext(Dispatchers.IO) {
        val client = createHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) return@withContext null
            return@withContext response.body?.string()
        }
    }
}