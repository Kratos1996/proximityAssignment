package ishant.proximity.proxiityassignment.websocketservice.services


import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import ishant.proximity.proxiityassignment.R
import ishant.proximity.proxiityassignment.appconstance.AppConstance
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityData
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityResponse
import ishant.proximity.proxiityassignment.websocketservice.model.WebSocketState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.reflect.Type
import java.util.ArrayList

class WebSocketRepository(private val client: HttpClient,val context:Context): WebSocketService {

    private var socket : WebSocketSession? = null

    override suspend fun openSocket(): WebSocketState {
        return try {
            socket = client.webSocketSession {
                url(AppConstance.WEBSOCKET_URL)
            }
            if (socket?.isActive == true){
                WebSocketState.Connected(context.getString(R.string.socketConnectionEstablished))
            }else{
                WebSocketState.Disconnected(context.getString(R.string.socket_Disconnected))
            }
        }catch (e: Exception){
            WebSocketState.ErrorOnSocket(context.getString(R.string.error))
        }
    }

    override fun GetCurrentData(): Flow<AirQualityResponse> {
        return try{
            socket?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map {
                    val json  = (it as? Frame.Text)?.readText() ?: ""
                    val type: Type = object : TypeToken<List<AirQualityData>>() {}.type
                    val listAirQualityData= Gson().fromJson<List<AirQualityData>>(json, type)
                    listAirQualityData.forEachIndexed { index, airQualityData ->
                        airQualityData.time=System.currentTimeMillis()
                    }
                    AirQualityResponse(listAirQualityData)

                } ?: flow{}
        }catch(e : Exception){
            flow{}
        }
    }

    override suspend fun closeSocket() {
        socket?.close()
    }
}

