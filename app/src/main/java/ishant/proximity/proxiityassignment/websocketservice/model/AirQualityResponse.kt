package ishant.proximity.proxiityassignment.websocketservice.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class AirQualityResponse(

	val airQualityResponseNew: List<AirQualityData>
)
@Serializable
data class AirQualityData(

	val city: String?=null,

	val aqi: Double?=null,

	var time:Long?=null
)
