package ishant.proximity.proxiityassignment.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ishant.proximity.proxiityassignment.di.models.CoroutineContextProvider
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityResponse
import ishant.proximity.proxiityassignment.websocketservice.model.WebSocketState
import ishant.proximity.proxiityassignment.websocketservice.services.WebSocketService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeViewModel(val coroutineProvider: CoroutineContextProvider,val service: WebSocketService) : AndroidViewModel(
    Application()
)
{
    private var airQualityResponseFlow = MutableStateFlow<ResponseAirQualitySealed>(ResponseAirQualitySealed.Empty)

    sealed class ResponseAirQualitySealed {
        class Success(val response: AirQualityResponse) : ResponseAirQualitySealed()
        class ErrorOnResponse(val message: String?) : ResponseAirQualitySealed()
        class loading(val isLoading: Boolean?) : ResponseAirQualitySealed()
        object Empty : ResponseAirQualitySealed()
    }
    fun connectSocketNow(){
        viewModelScope.launch {
            val result = service.openSocket()
            when(result){
                is WebSocketState.Connected -> {
                    airQualityResponseFlow.value = ResponseAirQualitySealed.loading(true)
                    airQualityResponseFlow.value = ResponseAirQualitySealed.ErrorOnResponse("Please Wait Connecting...")
                    service.GetCurrentData()
                        .onEach { airDataList ->
                            airQualityResponseFlow.value = ResponseAirQualitySealed.ErrorOnResponse("Connected")
                            airQualityResponseFlow.value=ResponseAirQualitySealed.loading(false)
                            airQualityResponseFlow.value =ResponseAirQualitySealed.Success(airDataList)
                            delay(30000)
                        }.launchIn(viewModelScope)
                }
                is WebSocketState.Disconnected -> {
                    airQualityResponseFlow.value = ResponseAirQualitySealed.ErrorOnResponse("Disconnected")
                    airQualityResponseFlow.value=ResponseAirQualitySealed.loading(false)
                }

                else -> {
                    airQualityResponseFlow.value = ResponseAirQualitySealed.ErrorOnResponse((result.message.toString()))
                    airQualityResponseFlow.value=ResponseAirQualitySealed.loading(false)
                }
            }
        }
    }




    fun SocketDisconnedNow(){
        viewModelScope.launch {
            service.closeSocket()
        }
    }

    override fun onCleared() {
        super.onCleared()
        SocketDisconnedNow()
    }

}