package org.example.model

import kotlinx.serialization.Serializable

@Serializable
data class GeocodeResponse(
    val lat: Double,
    val lon: Double
)

