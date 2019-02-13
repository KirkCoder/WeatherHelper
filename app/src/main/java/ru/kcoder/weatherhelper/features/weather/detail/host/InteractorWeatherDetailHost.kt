package ru.kcoder.weatherhelper.features.weather.detail.host

import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.data.reposiries.weather.WeatherRepository
import ru.kcoder.weatherhelper.toolkit.farmework.BaseInteractor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ErrorSupervisor
import ru.kcoder.weatherhelper.toolkit.farmework.supevisors.ScopeHandler

class InteractorWeatherDetailHost(
    private val repository: WeatherRepository,
    settingsRepository: SettingsRepository,
    scopeHandler: ScopeHandler,
    errorSupervisor: ErrorSupervisor
) : BaseInteractor(settingsRepository, scopeHandler, errorSupervisor),
    ContractWeatherDetailHost.Interactor {

    override fun getWeatherHolderPositions() =
        repository.getWeatherPositions()
}