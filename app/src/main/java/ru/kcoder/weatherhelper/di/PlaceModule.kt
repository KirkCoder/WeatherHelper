package ru.kcoder.weatherhelper.di

import android.location.Geocoder
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepositoryImpl
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractor
import ru.kcoder.weatherhelper.domain.place.PlaceAddInteractorImpl
import ru.kcoder.weatherhelper.presentation.place.ViewModelAddPlaceImpl
import java.util.*


val placeModule = module {
    factory<PlaceRepository> { PlaceRepositoryImpl(get()) }
    factory<PlaceAddInteractor> { PlaceAddInteractorImpl(get()) }
    factory { Geocoder(get(), Locale.getDefault()) }
    viewModel { ViewModelAddPlaceImpl(get()) }
}