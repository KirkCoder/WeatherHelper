package ru.kcoder.weatherhelper.di

import android.arch.persistence.room.Room
import android.content.Context
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kcoder.weatherhelper.data.database.room.WeatherHelperRoomDb
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSourceImpl
import ru.kcoder.weatherhelper.data.network.common.OkHttpBuilder
import ru.kcoder.weatherhelper.data.network.common.RetrofitBuilder
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepositoryImpl

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

private fun getWeatherHelperDb(context: Context): WeatherHelperRoomDb {
    return Room.databaseBuilder(
            context, WeatherHelperRoomDb::class.java, WeatherHelperRoomDb.DATABASE
    ).build()
}

val weatherModule = module {
    factory<WeatherNetworkSource> { get<Retrofit>().create(WeatherNetworkSource::class.java) }
    factory<WeatherDbSource> { WeatherDbSourceImpl(get()) }
    factory<WeatherRepository> { WeatherRepositoryImpl(get(), get()) }
}