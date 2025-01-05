package org.example.service

import org.example.model.WeatherResponse
import org.example.model.GeocodeResponse
import org.example.util.HttpClient
import org.example.util.JsonParser

object WeatherService {
    private const val API_KEY = "cfa352de6f843d53c33d42f32f75c4b3"
    private const val GEOCODE_BASE_URL = "http://api.openweathermap.org/geo/1.0/direct"
    private const val WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/weather"
    suspend fun getWeather(city: String): WeatherResponse {

        val geocodeUrl = "${GEOCODE_BASE_URL}?q=${city}&limit=1&appid=${API_KEY}"
        println(geocodeUrl)
        val geocodeResponse = HttpClient.fetch(geocodeUrl)
        val geocodeData = geocodeResponse?.let {
            JsonParser.parse<List<GeocodeResponse>>(it)?.firstOrNull()
        } ?: throw IllegalArgumentException("Invalid city name or no data found.")

        val weatherUrl = "${WEATHER_BASE_URL}?lat=${geocodeData.lat}&lon=${geocodeData.lon}&appid=${API_KEY}"
        val weatherResponse = HttpClient.fetch(weatherUrl)
        return weatherResponse?.let{
            JsonParser.parse<WeatherResponse>(it)
        } ?: throw IllegalArgumentException("Failed to fetch weather data.")
    }
}