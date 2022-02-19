package ishant.proximity.proxiityassignment.websocketservice.model

import kotlinx.serialization.Serializable

@Serializable
data class AirQualityResponse (
    val airQualityResponse: List<AirQualityData> = emptyList(),
    )
@Serializable
data class AirQualityData( val city : String,
                           val aqi : Double)