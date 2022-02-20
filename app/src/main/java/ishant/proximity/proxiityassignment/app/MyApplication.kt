package ishant.proximity.proxiityassignment.app

import android.app.Application
import android.content.Context
import ishant.proximity.proxiityassignment.BuildConfig
import ishant.proximity.proxiityassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {


    companion object { lateinit var application: MyApplication }
    lateinit var context: Context
    override fun onCreate() {
        super.onCreate()
        application = this
        context = this
        startKoin {
            // use Koin logger
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            // declare used Android context
            androidContext(context)
            // declare modules
            modules(appModule)
        }

    }

    fun getInstance(): MyApplication {
        return application
    }

    fun getAppContext(): Context {
        return context
    }


}