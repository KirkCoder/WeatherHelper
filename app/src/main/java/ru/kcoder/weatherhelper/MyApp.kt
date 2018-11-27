package ru.kcoder.weatherhelper

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.kcoder.weatherhelper.di.networkModule

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            networkModule
        ))
    }
}