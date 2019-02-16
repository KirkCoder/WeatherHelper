package ru.kcoder.weatherhelper.di

import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSource
import ru.kcoder.weatherhelper.data.database.weather.WeatherDbSourceImpl
import ru.kcoder.weatherhelper.data.network.weather.WeatherNetworkSource
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepositoryImpl
import ru.kcoder.weatherhelper.features.weather.detail.item.ContractWeatherDetailItem
import ru.kcoder.weatherhelper.features.weather.detail.item.InteractorWeatherDetailItem
import ru.kcoder.weatherhelper.features.weather.list.InteractorWeatherList
import ru.kcoder.weatherhelper.features.weather.detail.item.FragmentWeatherDetailItem
import ru.kcoder.weatherhelper.features.weather.detail.item.ViewModelWeatherDetailItem
import ru.kcoder.weatherhelper.features.weather.detail.host.ContractWeatherDetailHost
import ru.kcoder.weatherhelper.features.weather.detail.host.FragmentWeatherDetailHost
import ru.kcoder.weatherhelper.features.weather.detail.host.InteractorWeatherDetailHost
import ru.kcoder.weatherhelper.features.weather.detail.host.ViewModelWeatherDetailHost
import ru.kcoder.weatherhelper.features.weather.list.ContractWeatherList
import ru.kcoder.weatherhelper.features.weather.list.ViewModelWeatherList
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisorImpl

const val WEATHER_LIST_SCOPE = "weather_list_scope"
const val WEATHER_DETAIL_SCOPE = "weather_detail_scope"
const val WEATHER_DETAIL_HOST_SCOPE = "weather_detail_host_scope"

val weatherModule = module {
    factory<WeatherNetworkSource> { get<Retrofit>().create(WeatherNetworkSource::class.java) }
    factory<WeatherDbSource> { WeatherDbSourceImpl(get()) }
    factory<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get(), get()) }
}

val weatherListModule = module("weather_list") {
    viewModel<ContractWeatherList.ViewModel> {
        ViewModelWeatherList(
            get(),
            get(name = "weather_list.ErrorSupervisor")
        )
    }
    scope<ErrorSupervisor>(WEATHER_LIST_SCOPE) { ErrorSupervisorImpl() }
    scope<ContractWeatherList.Interactor>(WEATHER_LIST_SCOPE) {
        InteractorWeatherList(
            get(), get(), get(),
            get(name = "weather_list.ErrorSupervisor")
        )
    }
}

val weatherDetailModule = module("weather_detail_item") {
    scope<ErrorSupervisor>(WEATHER_DETAIL_SCOPE) { ErrorSupervisorImpl() }
    viewModel<ContractWeatherDetailItem.ViewModel> {
        ViewModelWeatherDetailItem(
            get(),
            getProperty(FragmentWeatherDetailItem.ID_KEY),
            getProperty(FragmentWeatherDetailItem.NEED_UPDATE),
            get(name = "weather_detail_item.ErrorSupervisor")
        )
    }
    factory<ContractWeatherDetailItem.Interactor> {
        InteractorWeatherDetailItem(
            get(), get(), get(),
            get(name = "weather_detail_item.ErrorSupervisor")
        )
    }
}

val weatherDetailHostModule = module("weather_host") {
    viewModel<ContractWeatherDetailHost.ViewModel> {
        ViewModelWeatherDetailHost(
            get(),
            getProperty(FragmentWeatherDetailHost.ID_KEY),
            getProperty(FragmentWeatherDetailHost.UPDATE_KEY),
            get(name = "weather_host.ErrorSupervisor")
        )
    }
    scope<ErrorSupervisor>(WEATHER_DETAIL_HOST_SCOPE) { ErrorSupervisorImpl() }
    scope<ContractWeatherDetailHost.Interactor>(WEATHER_DETAIL_HOST_SCOPE) {
        InteractorWeatherDetailHost(
            get(), get(), get(),
            get(name = "weather_host.ErrorSupervisor")
        )
    }
}