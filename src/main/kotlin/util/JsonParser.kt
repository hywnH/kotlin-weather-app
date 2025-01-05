package org.example.util

import kotlinx.serialization.json.Json

object JsonParser {
    val json = Json { ignoreUnknownKeys = true }

    inline fun <reified T> parse(jsonString: String): T? {
        return try {
            json.decodeFromString<T>(jsonString)
        } catch (e: Exception) {
            println("JSON parsing error: ${e.message}")
            null
        }
    }
}