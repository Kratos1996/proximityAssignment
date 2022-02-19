package ishant.proximity.proxiityassignment.websocketservice.model

import kotlinx.serialization.Serializable

@Serializable
data class AirQualityResponse (
    val city : String,
    val aqi : Double
    )
