package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val name: String,
    val weather: List<Weather>
)

@Serializable
data class Weather(
    val main: String,
    val description: String
)