package ishant.proximity.proxiityassignment.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ishant.proximity.proxiityassignment.app.custom.MethodsRepo
import ishant.proximity.proxiityassignment.di.models.CoroutineContextProvider
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityData
import ishant.proximity.proxiityassignment.websocketservice.model.AirQualityResponse
import ishant.proximity.proxiityassignment.websocketservice.model.WebSocketState
import ishant.proximity.proxiityassignment.websocketservice.services.WebSocketService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.ArrayList

class HomeViewModel(val methods: MethodsRepo,val service: WebSocketService) : AndroidViewModel(
    Application()
)
{
     var airQualityResponseFlow = MutableStateFlow<ResponseAirQualitySealed>(ResponseAirQualitySealed.Empty)
     var CitAqiList = ArrayList<Double>()

    sealed class ResponseAirQualitySealed {
        class Success(val response: AirQualityResponse) : ResponseAirQualitySealed()
        class message(val message: String?) : ResponseAirQualitySealed()
        class ErrorOnResponse(val message: String?) : ResponseAirQualitySealed()
        class loading(val isLoading: Boolean) : ResponseAirQualitySealed()
        object Empty : ResponseAirQualitySealed()
    }
    fun connectSocketNow(){
        viewModelScope.launch {
            val result = service.openSocket()
            when(result){
                is WebSocketState.Connected -> {
                    airQualityResponseFlow.value = ResponseAirQualitySealed.loading(true)
                    airQualityResponseFlow.value = ResponseAirQualitySealed.message("Loading..")
                    service.GetCurrentData()
                        .onEach { airDataList ->
                            airQualityResponseFlow.value = ResponseAirQualitySealed.message("Please Wait While fetching Data")
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
     fun cityAqiList(data: List<AirQualityData>,city:String) {
        for (points in data) {
            if (points.city.equals(city)) {
                CitAqiList.add( (methods.roundOffDecimal(points.aqi!!)!!))
            }
        }

    }
    override fun onCleared() {
        super.onCleared()
        SocketDisconnedNow()
    }

}