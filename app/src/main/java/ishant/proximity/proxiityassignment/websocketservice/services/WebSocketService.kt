package ishant.proximity.proxiityassignment.websocketservice.services

import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityResponse
import ishant.proximity.proxiityassignment.websocketservice.model.WebSocketState
import kotlinx.coroutines.flow.Flow

interface WebSocketService {

    suspend fun openSocket() : WebSocketState

    fun GetCurrentData(): Flow<List<AirQualityResponse>>

    suspend fun  closeSocket()


}