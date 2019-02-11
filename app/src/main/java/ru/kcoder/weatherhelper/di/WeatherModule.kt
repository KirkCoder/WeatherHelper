package ru.kcoder.weatherhelper.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSourceImpl
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepositoryImpl
import ru.kcoder.weatherhelper.features.weather.detail.ContractWeatherDetail
import ru.kcoder.weatherhelper.features.weather.detail.WeatherDetailInteractor
import ru.kcoder.weatherhelper.features.weather.list.InteractorWeatherList
import ru.kcoder.weatherhelper.features.weather.detail.FragmentWeatherDetail
import ru.kcoder.weatherhelper.features.weather.detail.ViewModelWeatherDetail
import ru.kcoder.weatherhelper.features.weather.list.ContractWeatherList
import ru.kcoder.weatherhelper.features.weather.list.ViewModelWeatherList
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisorImpl
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandlerImpl

const val WEATHER_LIST_SCOPE = "weather_list_scope"
const val WEATHER_DETAIL_SCOPE = "weather_detail_scope"

val weatherModule = module {
    factory<WeatherNetworkSource> { get<Retrofit>().create(WeatherNetworkSource::class.java) }
    factory<WeatherDbSource> { WeatherDbSourceImpl(get()) }
    factory<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get(), get()) }
}

val weatherListModule = module("weather_list") {
    viewModel<ContractWeatherList.ViewModel> {
        ViewModelWeatherList(
            get(),
            get(name = "weather_list.ScopeHandler"),
            get(name = "weather_list.ErrorSupervisor")
        )
    }
    scope<ErrorSupervisor>(WEATHER_LIST_SCOPE) { ErrorSupervisorImpl() }
    scope<ScopeHandler>(WEATHER_LIST_SCOPE) { ScopeHandlerImpl() }
    scope<ContractWeatherList.Interactor>(WEATHER_LIST_SCOPE) {
        InteractorWeatherList(
            get(), get(),
            get(name = "weather_list.ScopeHandler"),
            get(name = "weather_list.ErrorSupervisor")
        )
    }
}

val weatherDetailModule = module("weather_detail") {
    viewModel<ContractWeatherDetail.ViewModel> {
        ViewModelWeatherDetail(
            get(),
            getProperty(FragmentWeatherDetail.ID_KEY),
            getProperty(FragmentWeatherDetail.NEED_UPDATE),
            get(name = "weather_detail.ScopeHandler"),
            get(name = "weather_detail.ErrorSupervisor")
        )
    }
    scope<ErrorSupervisor>(WEATHER_DETAIL_SCOPE) { ErrorSupervisorImpl() }
    scope<ScopeHandler>(WEATHER_DETAIL_SCOPE) { ScopeHandlerImpl() }
    scope<ContractWeatherDetail.Interactor>(WEATHER_DETAIL_SCOPE) {
        WeatherDetailInteractor(
            get(), get(),
            get(name = "weather_detail.ScopeHandler"),
            get(name = "weather_detail.ErrorSupervisor")
        )
    }
}