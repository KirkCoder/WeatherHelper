package ru.kcoder.weatherhelper.di

import androidx.room.Room
import android.content.Context
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.converter.gson.GsonConverterFactory
import ru.kcoder.weatherhelper.data.database.room.WeatherHelperRoomDb
import ru.kcoder.weatherhelper.data.database.settings.SettingsSource
import ru.kcoder.weatherhelper.data.database.settings.SettingsSourceImpl
import ru.kcoder.weatherhelper.data.network.common.OkHttpBuilder
import ru.kcoder.weatherhelper.data.network.common.RetrofitBuilder
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepositoryImpl
import ru.kcoder.weatherhelper.data.resourses.imageres.ImageResSource
import ru.kcoder.weatherhelper.data.resourses.imageres.ImageResSourceImpl
import ru.kcoder.weatherhelper.data.resourses.string.WeatherStringSource
import ru.kcoder.weatherhelper.data.resourses.string.WeatherStringSourceImpl
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandlerImpl

val networkModule = module {
    single {
        val builder = OkHttpBuilder(get(), OkHttpClient.Builder())
        builder.build()
    }
    single { GsonConverterFactory.create() }
    single { RetrofitBuilder(get()).build() }
}

val databaseModule = module {
    single { getWeatherHelperDb(get()) }
}

val settingsModule = module {
    factory<SettingsSource> { SettingsSourceImpl(get()) }
    factory <SettingsRepository>{ SettingsRepositoryImpl(get()) }
}

val resourceModule = module {
    factory<WeatherStringSource> { WeatherStringSourceImpl(get())}
    factory<ImageResSource> { ImageResSourceImpl() }
}

val supervisorsModule = module {
    factory<ScopeHandler>{ ScopeHandlerImpl() }
}

private fun getWeatherHelperDb(context: Context): WeatherHelperRoomDb {
    return Room.databaseBuilder(
            context, WeatherHelperRoomDb::class.java, WeatherHelperRoomDb.DATABASE
    ).build()
}