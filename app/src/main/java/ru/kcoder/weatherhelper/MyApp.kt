package ru.kcoder.weatherhelper

import android.app.Application
import org.koin.android.ext.android.startKoin
import ru.kcoder.weatherhelper.di.*
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
        }
        startKoin(this, listOf(
            networkModule,
            weatherModule,
            databaseModule,
            supervisorsModule,
            weatherListModule,
            weatherDetailModule,
            weatherDetailHostModule,
            settingsModule,
            resourceModule,
            placeModule
        ))
    }
}