package ru.kcoder.weatherhelper.di

import android.location.Geocoder
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepositoryImpl
import ru.kcoder.weatherhelper.data.resourses.timezone.TimeZoneSource
import ru.kcoder.weatherhelper.data.resourses.timezone.TimeZoneSourceImpl
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractor
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractorImpl
import ru.kcoder.weatherhelper.presentation.place.ViewModelAddPlace
import ru.kcoder.weatherhelper.presentation.place.ViewModelAddPlaceImpl
import java.util.*


val placeModule = module {
    factory<PlaceRepository> { PlaceRepositoryImpl(get(), get(), get()) }
    factory<PlaceAddInteractor> { PlaceAddInteractorImpl(get(), get()) }
    factory { Geocoder(get(), Locale.getDefault()) }
    factory<TimeZoneSource> { TimeZoneSourceImpl() }
    viewModel<ViewModelAddPlace> { ViewModelAddPlaceImpl(get()) }
}