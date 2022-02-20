package ishant.proximity.proxiityassignment.di


import android.content.Context
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.websocket.*
import ishant.proximity.proxiityassignment.app.custom.MethodsRepo
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ishant.proximity.proxiityassignment.di.models.CoroutineContextProvider
import ishant.proximity.proxiityassignment.ui.home.viewmodel.HomeViewModel
import ishant.proximity.proxiityassignment.websocketservice.services.WebSocketRepository
import ishant.proximity.proxiityassignment.websocketservice.services.WebSocketService
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext

private val appKoinModule = module {


    single { CoroutineContextProvider() }
    single{
        MethodsRepo(context = androidContext())
    }
    single<HttpClient> {
        HttpClient(CIO) {
            install(Logging)
            install(WebSockets) {
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }
    single<WebSocketService> {
        WebSocketRepository(context = androidApplication(),client = get())
    }

    viewModel {
        HomeViewModel(methods = get(), service = get())
    }

}


val appModule = listOf(appKoinModule)
