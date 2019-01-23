package ru.kcoder.weatherhelper.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSourceImpl
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepositoryImpl
import ru.kcoder.weatherhelper.domain.weather.detail.WeatherDetailInteractor
import ru.kcoder.weatherhelper.domain.weather.detail.WeatherDetailInteractorImpl
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractor
import ru.kcoder.weatherhelper.domain.weather.list.WeatherListInteractorImpl
import ru.kcoder.weatherhelper.presentation.weather.detail.FragmentWeatherDetail
import ru.kcoder.weatherhelper.presentation.weather.detail.ViewModelWeatherDetail
import ru.kcoder.weatherhelper.presentation.weather.detail.ViewModelWeatherDetailImpl
import ru.kcoder.weatherhelper.presentation.weather.list.ViewModelWeatherList
import ru.kcoder.weatherhelper.presentation.weather.list.ViewModelWeatherListImpl

val weatherModule = module {
    factory<WeatherNetworkSource> { get<Retrofit>().create(WeatherNetworkSource::class.java) }
    factory<WeatherDbSource> { WeatherDbSourceImpl(get()) }
    factory<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get(), get()) }
}

val weatherListModule = module {
    factory<WeatherListInteractor> { WeatherListInteractorImpl(get(), get()) }
    viewModel<ViewModelWeatherList> { ViewModelWeatherListImpl(get()) }
}

val weatherDetailModule = module {
    factory<WeatherDetailInteractor> { WeatherDetailInteractorImpl(get(), get()) }
    viewModel<ViewModelWeatherDetail> {
        ViewModelWeatherDetailImpl(
            get(),
            getProperty(FragmentWeatherDetail.ID_KEY),
            getProperty(FragmentWeatherDetail.NEED_UPDATE)
        )
    }
}