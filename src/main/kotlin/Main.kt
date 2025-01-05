package org.example

import kotlinx.coroutines.*
import org.example.service.WeatherService

suspend fun readInput(): String? = withContext(Dispatchers.IO) {
    readlnOrNull()
}

fun main() = runBlocking {
    val cities = mutableListOf<String>()
    var isInputFinished = false

    val inputJob = launch {
        println("Enter a city name (type 'done' to finish):")
        while (true) {
            delay(100)
            val input = readInput()?.trim()
            if (input.equals("done", ignoreCase = true)) {
                isInputFinished = true
                break
            }
            if (!input.isNullOrEmpty()) {
                cities.add(input)
                println("Added city: $input")
            }
        }
    }

    val apiJob = launch {
        while (true) {
            delay(1000)
            if (cities.isNotEmpty()) {
                val city = cities.removeAt(0)
                try {
                    println("Fetching weather for $city...")
                    val weather = WeatherService.getWeather(city)
                    println("Weather in ${weather.name}: ${weather.weather[0].description}")
                } catch (e: Exception) {
                    println("Failed to fetch weather for $city: ${e.message}")
                }
            } else {
                if (!isActive && isInputFinished) break
            }
        }
    }

    inputJob.join() // 입력 종료를 기다림
    println("Finished input. Waiting for remaining API requests...")
    apiJob.cancelAndJoin() // 남은 API 작업 종료

    println("All tasks completed!")
}
