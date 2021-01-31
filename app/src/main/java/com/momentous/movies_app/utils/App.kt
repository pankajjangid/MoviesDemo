package com.momentous.movies_app.utils

import android.content.Context
import android.os.StrictMode
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.momentous.movies_app.networking.MyApi
import com.momentous.movies_app.networking.NetworkConnectionInterceptor
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


class App : MultiDexApplication(), KodeinAware {
    companion object {
        var application: App? = null
        val TAG = "MyApp"
        lateinit var networkStatus: ConnectivityLiveData

        fun getInstance(): App? {
            return application
        }


    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        // FirebaseApp.initializeApp(this)

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        networkStatus = ConnectivityLiveData(this)


    }


    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }

    }


}