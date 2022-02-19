package ishant.proximity.proxiityassignment.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ishant.proximity.proxiityassignment.di.models.CoroutineContextProvider
import ishant.proximity.proxiityassignment.websocketservice.services.WebSocketService

class HomeViewModel(val coroutineProvider: CoroutineContextProvider,val service: WebSocketService) : AndroidViewModel(
    Application()
)
{

}