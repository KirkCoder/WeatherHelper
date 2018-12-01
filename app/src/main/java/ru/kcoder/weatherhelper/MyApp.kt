package ru.kcoder.weatherhelper

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import org.koin.android.ext.android.startKoin
import ru.kcoder.weatherhelper.di.databaseModule
import ru.kcoder.weatherhelper.di.networkModule
import ru.kcoder.weatherhelper.di.weatherListModule
import ru.kcoder.weatherhelper.di.weatherModule
import ru.kcoder.weatherhelper.ru.weatherhelper.BuildConfig

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);
        }
        startKoin(this, listOf(
            networkModule,
            weatherModule,
            databaseModule,
            weatherListModule
        ))
    }
}