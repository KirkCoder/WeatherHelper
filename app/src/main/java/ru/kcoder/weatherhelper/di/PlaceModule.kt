package ru.kcoder.weatherhelper.di

import android.location.Geocoder
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepository
import ru.kcoder.weatherhelper.data.reposiries.place.PlaceRepositoryImpl
import ru.kcoder.weatherhelper.data.resourses.timezone.TimeZoneSource
import ru.kcoder.weatherhelper.data.resourses.timezone.TimeZoneSourceImpl
import ru.kcoder.weatherhelper.features.place.ContaractPlace
import ru.kcoder.weatherhelper.features.place.PlaceInteractor
import ru.kcoder.weatherhelper.features.place.ViewModelPlace
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisorImpl
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandlerImpl
import java.util.*

const val PLACE_SCOPE = "place_scope"

val placeModule = module("place") {
    viewModel<ContaractPlace.ViewModel> {
        ViewModelPlace(
            get(),
            get(name = "place.ScopeHandler"),
            get(name = "place.ErrorSupervisor")
        )
    }
    scope<ErrorSupervisor>(PLACE_SCOPE) { ErrorSupervisorImpl() }
    scope<ScopeHandler>(PLACE_SCOPE) { ScopeHandlerImpl() }
    scope<PlaceRepository>(PLACE_SCOPE) { PlaceRepositoryImpl(get(), get(), get()) }
    scope(PLACE_SCOPE) { Geocoder(get(), Locale.getDefault()) }
    scope<TimeZoneSource>(PLACE_SCOPE) { TimeZoneSourceImpl() }
    scope<ContaractPlace.Interactor>(PLACE_SCOPE) {
        PlaceInteractor(
            get(), get(),
            get(name = "place.ScopeHandler"),
            get(name = "place.ErrorSupervisor")
        )
    }
}